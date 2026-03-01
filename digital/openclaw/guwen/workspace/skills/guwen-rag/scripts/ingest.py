#!/usr/bin/env python3
"""
Ingest documents into sqlite-vec using Ollama embeddings
"""
import os
import sys
import re
import struct
import json
import requests

# Add parent to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from references.config import get_db, init_db, OLLAMA_HOST, EMBEDDING_MODEL, EMBEDDING_DIMENSION

def get_embeddings(texts):
    """Get embeddings from Ollama"""
    embeddings = []
    for text in texts:
        response = requests.post(
            f"{OLLAMA_HOST}/api/embeddings",
            json={"model": EMBEDDING_MODEL, "prompt": text}
        )
        if response.status_code == 200:
            embeddings.append(response.json()["embedding"])
        else:
            print(f"Error: {response.text}")
            embeddings.append([0.0] * EMBEDDING_DIMENSION)
    return embeddings

def chunk_markdown(content, max_length=512, overlap=50):
    """Split markdown by headers with overlap"""
    # Split by ## headers
    sections = re.split(r'\n(?=##\s)', content)
    
    chunks = []
    for section in sections:
        if len(section) > max_length:
            # Further split long sections
            words = section.split()
            for i in range(0, len(words), max_length - overlap):
                chunk = ' '.join(words[i:i + max_length])
                chunks.append(chunk)
        else:
            chunks.append(section)
    
    return chunks

def extract_metadata(content, filepath):
    """Extract metadata from content and filepath"""
    metadata = {
        "source": filepath,
        "filename": os.path.basename(filepath),
    }
    
    # Try to extract title
    title_match = re.search(r'^#+\s*(.+?)$', content, re.MULTILINE)
    if title_match:
        metadata["title"] = title_match.group(1)
    
    # Try to extract author
    author_match = re.search(r'\*\*作者：\*\*\s*(.+?)$', content, re.MULTILINE)
    if author_match:
        metadata["author"] = author_match.group(1)
    
    return metadata

def ingest_directory(directory):
    """Ingest all markdown files from directory"""
    
    # Ensure DB exists
    init_db()
    db = get_db()
    
    # Find all markdown files
    markdown_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.md'):
                markdown_files.append(os.path.join(root, file))
    
    print(f"Found {len(markdown_files)} markdown files")
    
    # Process each file
    for filepath in markdown_files:
        print(f"\nProcessing: {filepath}")
        
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Extract metadata
        metadata = extract_metadata(content, filepath)
        
        # Chunk content
        chunks = chunk_markdown(content)
        print(f"  Created {len(chunks)} chunks")
        
        # Get embeddings
        print("  Generating embeddings...")
        embeddings = get_embeddings(chunks)
        
        # Insert into sqlite-vec
        base_id = os.path.basename(filepath).replace('.', '_')
        
        for i, (chunk, embedding) in enumerate(zip(chunks, embeddings)):
            # Convert embedding to bytes
            embedding_bytes = struct.pack(f'{len(embedding)}f', *embedding)
            
            # Prepare metadata JSON
            meta = metadata.copy()
            meta["chunk_index"] = i
            meta["chunk_id"] = f"{base_id}_{i}"
            
            db.execute(
                "INSERT INTO documents(embedding, text, source, chunk_id) VALUES (?, ?, ?, ?)",
                (embedding_bytes, chunk, json.dumps(meta), i)
            )
        
        db.commit()
        print(f"  ✓ Added {len(chunks)} documents")
    
    # Get count
    count = db.execute("SELECT COUNT(*) FROM documents").fetchone()[0]
    print(f"\n✓ Ingestion complete! Total documents: {count}")

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Ingest documents into sqlite-vec")
    parser.add_argument("--dir", default="../../guwen", help="Directory containing markdown files")
    args = parser.parse_args()
    
    ingest_directory(args.dir)
