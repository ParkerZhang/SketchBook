#!/usr/bin/env python
"""
Compare keyword search vs vector search results.

Usage:
    python compare_search.py <query> [n_results]
"""

import json
import sys

import chromadb
from chromadb.config import Settings

CHUNKS_FILE = "/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl"
CHROMA_DIR = "/Users/u/.openclaw/workspace/zuozhuan_chroma/"


def load_chunks():
    chunks = []
    with open(CHUNKS_FILE, 'r', encoding='utf-8') as f:
        for line in f:
            chunks.append(json.loads(line))
    return chunks


def keyword_search(chunks, query, n_results=5):
    """Simple character-level keyword matching."""
    scored = []
    for i, chunk in enumerate(chunks):
        score = sum(1 for c in query if c in chunk['text']) / len(query)
        if score > 0:
            scored.append((i, score))
    scored.sort(key=lambda x: -x[1])
    return scored[:n_results]


def vector_search(query, n_results=5):
    """Chroma vector search."""
    settings = Settings(
        chroma_db_impl="duckdb+parquet",
        persist_directory=CHROMA_DIR
    )
    client = chromadb.Client(settings)
    collection = client.get_collection("zuozhuan")
    
    results = collection.query(
        query_texts=[query],
        n_results=n_results,
        include=["metadatas", "distances"]
    )
    
    # Convert to (index, score) format
    # Note: We need to map metadata back to chunk indices
    all_data = collection.get(include=['metadatas'])
    meta_to_idx = {}
    for i, meta in enumerate(all_data['metadatas']):
        key = f"{meta['duke']}_{meta['year']}"
        meta_to_idx[key] = i
    
    indexed = []
    for meta, dist in zip(results['metadatas'][0], results['distances'][0]):
        key = f"{meta['duke']}_{meta['year']}"
        idx = meta_to_idx.get(key, -1)
        # Convert distance to similarity score (lower distance = better)
        score = 1 / (1 + dist)
        indexed.append((idx, score, meta['year_label']))
    
    return indexed


def main():
    if len(sys.argv) < 2:
        print("Usage: python compare_search.py <query> [n_results]")
        sys.exit(1)
    
    query = sys.argv[1]
    n_results = int(sys.argv[2]) if len(sys.argv) > 2 else 5
    
    chunks = load_chunks()
    
    print(f"Query: {query}")
    print("=" * 70)
    
    # Keyword search
    print("\n📝 KEYWORD SEARCH")
    print("-" * 70)
    keyword_results = keyword_search(chunks, query, n_results)
    for i, (idx, score) in enumerate(keyword_results, 1):
        chunk = chunks[idx]
        print(f"{i}. {chunk['year_label']} (score: {score:.0%})")
    
    # Vector search
    print("\n🧠 VECTOR SEARCH")
    print("-" * 70)
    vector_results = vector_search(query, n_results)
    for i, (idx, score, label) in enumerate(vector_results, 1):
        print(f"{i}. {label} (similarity: {score:.4f})")


if __name__ == "__main__":
    main()
