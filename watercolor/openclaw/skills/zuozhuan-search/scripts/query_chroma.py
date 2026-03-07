#!/usr/bin/env python3
"""
Query the Zuo Zhuan Chroma database.

Usage:
    python3 query_chroma.py <search_query> [n_results]
    
Examples:
    python3 query_chroma.py "郑庄公" 5
    python3 query_chroma.py "二年春" 3
"""

import sys
import json
import re

import chromadb
from chromadb.config import Settings

CHROMA_DIR = "/Users/u/.openclaw/workspace/zuozhuan_chroma/"
COLLECTION_NAME = "zuozhuan"


def keyword_search(chunks, query, n_results=5):
    """Simple keyword matching for classical Chinese."""
    scores = []
    for i, chunk in enumerate(chunks):
        text = chunk['text']
        # Count query character matches
        matches = sum(1 for c in query if c in text)
        scores.append((i, matches / len(query) if query else 0))
    
    # Sort by score descending
    scores.sort(key=lambda x: -x[1])
    return scores[:n_results]


def main():
    if len(sys.argv) < 2:
        print("Usage: python3 query_chroma.py <search_query> [n_results]")
        print("Examples:")
        print("  python3 query_chroma.py '郑庄公' 5")
        print("  python3 query_chroma.py '二年春' 3")
        sys.exit(1)
    
    query = sys.argv[1]
    n_results = int(sys.argv[2]) if len(sys.argv) > 2 else 5
    
    # Initialize Chroma
    settings = Settings(
        chroma_db_impl="duckdb+parquet",
        persist_directory=CHROMA_DIR
    )
    client = chromadb.Client(settings)
    
    # Get collection
    collection = client.get_collection(COLLECTION_NAME)
    
    # Get all data for keyword search fallback
    all_data = collection.get(include=['documents', 'metadatas'])
    chunks = [
        {'text': doc, 'metadata': meta}
        for doc, meta in zip(all_data['documents'], all_data['metadatas'])
    ]
    
    # Try semantic search first
    semantic_results = collection.query(
        query_texts=[query],
        n_results=n_results,
        include=["documents", "metadatas", "distances"]
    )
    
    # Also do keyword search
    keyword_indices = keyword_search(chunks, query, n_results * 2)
    
    # Combine results - prioritize exact matches
    print(f"Search: {query}")
    print(f"Found {len(semantic_results['documents'][0])} semantic results\n")
    print("=" * 60)
    
    seen = set()
    result_num = 0
    
    # Show keyword matches first (if high confidence)
    for idx, score in keyword_indices:
        if score >= 0.5 and result_num < n_results:  # At least 50% char match
            doc = chunks[idx]['text']
            meta = chunks[idx]['metadata']
            key = f"{meta['duke']}_{meta['year']}"
            if key not in seen:
                seen.add(key)
                result_num += 1
                print(f"\n[{result_num}] {meta['year_label']} (keyword match: {score:.2f})")
                print("-" * 40)
                text_preview = doc[:200].replace('\n', ' ')
                print(f"{text_preview}...")
    
    # Then show semantic matches
    for i, (doc, meta, dist) in enumerate(zip(
        semantic_results['documents'][0], 
        semantic_results['metadatas'][0],
        semantic_results['distances'][0]
    )):
        key = f"{meta['duke']}_{meta['year']}"
        if key not in seen and result_num < n_results:
            seen.add(key)
            result_num += 1
            print(f"\n[{result_num}] {meta['year_label']} (semantic distance: {dist:.4f})")
            print("-" * 40)
            text_preview = doc[:200].replace('\n', ' ')
            print(f"{text_preview}...")


if __name__ == "__main__":
    main()
