---
name: guwen-rag
description: Classical Chinese RAG (Retrieval-Augmented Generation) system using local Ollama + ChromaDB. Embeds classical Chinese texts with nomic-embed-text, supports single queries, conversational follow-ups, and batch processing. Use when users need to query classical Chinese knowledge bases, set up local RAG with ollama, or perform semantic search on guwen (ancient Chinese) texts.
---

# Guwen RAG - Classical Chinese Semantic Search

Local RAG system for classical Chinese texts using Ollama + ChromaDB.

## Prerequisites

- Python 3.8+
- Ollama installed and running locally
- ChromaDB installed (`pip install chromadb`)

## Quick Start

```bash
# 1. Start ollama with embedding model
ollama pull nomic-embed-text
ollama pull qwen2.5

# 2. Run setup script
python scripts/setup_chroma.py

# 3. Ingest documents
python scripts/ingest.py --dir ~/.openclaw/workspace/guwen/

# 4. Query
python scripts/query.py "郑伯克段于鄢的大意"
```

## Core Scripts

### `scripts/setup_chroma.py`
Initialize ChromaDB local persistence.

```python
import chromadb
chroma_client = chromadb.PersistentClient(path="./chroma_db")
```

### `scripts/ingest.py`
Ingest markdown files from directory.

```bash
python scripts/ingest.py --dir ./guwen/ --collection classical_chinese
```

### `scripts/query.py`
Single query mode.

```bash
python scripts/query.py "归去来兮辞中鸟的含义"
```

### `scripts/chat.py`
Conversational mode with context.

```bash
python scripts/chat.py
```

### `scripts/batch_query.py`
Batch process multiple queries.

```bash
python scripts/batch_query.py --input queries.txt --output results.json
```

## Configuration

See `references/config.py` for all settings.

## Workflow Patterns

### Single Query
1. Embed query with nomic-embed-text
2. Search ChromaDB (top-k=5)
3. Format context with 【原文】【今译】
4. Generate response with qwen2.5

### Conversational
1. Maintain conversation history
2. Use previous context for follow-ups
3. Re-query if topic shifts

### Batch Processing
1. Read queries from file
2. Parallel embedding + search
3. Output JSON with sources

## Chunking Strategy

- Split by `##` headers (chapters)
- Max 512 tokens per chunk
- Overlap 50 tokens between chunks
- Store metadata: source file, author, dynasty
Configuration
See references/config.py for all settings.
Workflow Patterns
Single Query
Embed query with nomic-embed-text
Search sqlite-vec (top-k=5)
Format context with 【原文】【今译】
Generate response with qwen2.5
Conversational
Maintain conversation history
Use previous context for follow-ups
Re-query if topic shifts
Batch Processing
Read queries from file
Parallel embedding + search
Output JSON with sources
Chunking Strategy
Split by ## headers (chapters)
Max 512 tokens per chunk
Overlap 50 tokens between chunks
Store metadata: source file, author, dynasty
Migration from ChromaDB
This skill now uses sqlite-vec instead of ChromaDB. Key differences:
Database: Single SQLite file (~/.openclaw/workspace/guwen/guwen_vec.db)
Query syntax: WHERE embedding MATCH ? AND k = ?
Embeddings stored as binary blobs (struct.pack)
No separate server process required

