#!/usr/bin/env python
"""
Embed zuozhuan chunks into Chroma vector database.

Usage:
    python embed_chroma.py
    pyenv exec python3 embed_chroma.py

Output:
    Creates/updates Chroma DB at ~/.openclaw/workspace/zuozhuan_chroma/
"""

import json
import os
from pathlib import Path

import chromadb

INPUT_FILE = "/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl"
CHROMA_DIR = "/Users/u/.openclaw/workspace/zuozhuan_chroma/"
COLLECTION_NAME = "zuozhuan"


def load_chunks(filepath: str) -> list[dict]:
    """Load chunks from JSONL file."""
    chunks = []
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            chunks.append(json.loads(line))
    return chunks


def main():
    print(f"Loading chunks from {INPUT_FILE}...")
    chunks = load_chunks(INPUT_FILE)
    print(f"Loaded {len(chunks)} chunks")

    # Initialize Chroma (persistent) - API for v0.3.x
    print(f"\nInitializing Chroma DB at {CHROMA_DIR}...")
    client = chromadb.PersistentClient(path=CHROMA_DIR)
    # Get or create collection
    collection = client.get_or_create_collection(
        name=COLLECTION_NAME,
        metadata={"description": "Zuo Zhuan (左传) classical Chinese text"}
    )

    # Check if collection already has data
    existing_count = collection.count()
    if existing_count > 0:
        print(f"Collection already has {existing_count} documents")
        response = input("Delete existing collection and re-embed? (y/N): ")
        if response.lower() == 'y':
            client.delete_collection(COLLECTION_NAME)
            collection = client.create_collection(
                name=COLLECTION_NAME,
                metadata={"description": "Zuo Zhuan (左传) classical Chinese text"}
            )
            print("Deleted existing collection, creating new one...")
        else:
            print("Aborted.")
            return

    # Prepare data for Chroma
    ids = []
    documents = []
    metadatas = []

    for i, chunk in enumerate(chunks):
        chunk_id = f"{chunk['duke']}_{chunk['year']:02d}"
        
        # Handle duplicate IDs (e.g., multiple 襄公 sections)
        if chunk_id in ids:
            chunk_id = f"{chunk['duke']}_{chunk['year']:02d}_{i}"
        
        ids.append(chunk_id)
        documents.append(chunk['text'])
        metadatas.append({
            "duke": chunk['duke'],
            "year": chunk['year'],
            "year_label": chunk['year_label']
        })

    # Add to Chroma (batch processing for large datasets)
    BATCH_SIZE = 100
    print(f"\nEmbedding {len(documents)} chunks...")
    
    for i in range(0, len(documents), BATCH_SIZE):
        batch_end = min(i + BATCH_SIZE, len(documents))
        collection.add(
            ids=ids[i:batch_end],
            documents=documents[i:batch_end],
            metadatas=metadatas[i:batch_end]
        )
        print(f"  Embedded {batch_end}/{len(documents)} chunks")

    # Verify
    final_count = collection.count()
    print(f"\n✓ Complete! Collection '{COLLECTION_NAME}' has {final_count} documents")
    print(f"  DB location: {CHROMA_DIR}")

    # Quick test query
    print("\n--- Test Query ---")
    results = collection.query(
        query_texts=["郑伯克段于鄢"],
        n_results=2,
        include=["documents", "metadatas", "distances"]
    )
    
    for i, (doc, meta, dist) in enumerate(zip(
        results['documents'][0], 
        results['metadatas'][0],
        results['distances'][0]
    )):
        print(f"\n{i+1}. {meta['year_label']} (distance: {dist:.4f})")
        print(f"   {doc[:100]}...")


if __name__ == "__main__":
    main()
