#!/usr/bin/env python
"""
Simple keyword search for Zuo Zhuan (no vector DB required).

This script searches the raw chunks file using character-level matching,
which works well for classical Chinese where exact character matches
are often more useful than semantic similarity.

Usage:
    python search_keyword.py <query> [n_results]
    pyenv exec python3 search_keyword.py <query> [n_results]
    
Examples:
    python search_keyword.py "郑伯克段" 5
    python search_chinese.py "二年春" 10
"""

import json
import sys
from pathlib import Path

CHUNKS_FILE = "/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl"


def load_chunks(filepath: str) -> list[dict]:
    """Load all chunks from JSONL file."""
    chunks = []
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            chunks.append(json.loads(line))
    return chunks


def keyword_score(text: str, query: str) -> float:
    """
    Calculate keyword match score.
    
    Returns a score from 0.0 to 1.0 based on what fraction of
    query characters appear in the text.
    """
    if not query:
        return 0.0
    
    matches = sum(1 for c in query if c in text)
    return matches / len(query)


def search(chunks: list[dict], query: str, n_results: int = 5) -> list[tuple]:
    """
    Search chunks by keyword matching.
    
    Returns list of (chunk, score) tuples sorted by score descending.
    """
    scored = []
    for chunk in chunks:
        score = keyword_score(chunk['text'], query)
        if score > 0:
            scored.append((chunk, score))
    
    # Sort by score descending
    scored.sort(key=lambda x: -x[1])
    return scored[:n_results]


def highlight_matches(text: str, query: str, max_len: int = 150) -> str:
    """
    Find and highlight the first occurrence of query chars in text.
    Returns a snippet with context.
    """
    # Find first matching character position
    for i, c in enumerate(text):
        if c in query:
            start = max(0, i - 10)
            end = min(len(text), i + max_len)
            snippet = text[start:end].replace('\n', ' ')
            if start > 0:
                snippet = "..." + snippet
            if end < len(text):
                snippet = snippet + "..."
            return snippet
    return text[:max_len].replace('\n', ' ')


def main():
    if len(sys.argv) < 2:
        print("Usage: python search_keyword.py <query> [n_results]")
        print("Examples:")
        print("  python search_keyword.py '郑伯克段' 5")
        print("  python search_keyword.py '二年春' 10")
        sys.exit(1)
    
    query = sys.argv[1]
    n_results = int(sys.argv[2]) if len(sys.argv) > 2 else 5
    
    print(f"Loading chunks from {CHUNKS_FILE}...")
    chunks = load_chunks(CHUNKS_FILE)
    print(f"Loaded {len(chunks)} chunks\n")
    
    print(f"Searching for: {query}")
    print("=" * 60)
    
    results = search(chunks, query, n_results)
    
    if not results:
        print("No results found.")
        return
    
    print(f"Found {len(results)} matches\n")
    
    for i, (chunk, score) in enumerate(results, 1):
        print(f"\n[{i}] {chunk['year_label']} (match: {score:.0%})")
        print("-" * 40)
        snippet = highlight_matches(chunk['text'], query)
        print(snippet)


if __name__ == "__main__":
    main()
