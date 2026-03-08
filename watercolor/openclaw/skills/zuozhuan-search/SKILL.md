---
name: zuozhuan-search
description: Search the Zuo Zhuan database for passages about people, events, or years
author: local
version: 1.0.0
tags:
  - zuozhuan
  - search
  - chinese-text
  - history
enabled: true
---

# Zuo Zhuan Search Skill

## Purpose
Search the Zuo Zhuan database for passages about people, events, or years using semantic search.

## Behavior
- Uses ChromaDB for semantic search of Zuo Zhuan passages
- Integrates with Ollama for intelligent query processing
- Returns properly cited results with source information
- Handles both Chinese and English queries

## Configuration
{
  "default": false,
  "priority": 5,
  "handler": {
    "type": "script",
    "path": "/Users/u/.openclaw/workspace/skills/zuozhuan-search/run_zuozhuan_search.sh"
  }
}
