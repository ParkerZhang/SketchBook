---
name: zuozhuan-search
description: Search《左传》(Zuo Zhuan) using Chroma vector database with hybrid keyword + semantic search.
args:
  query: "Search query (person, event, phrase, year, etc.)"
  n_results: "Number of results (default: 5)"
---

# zuozhuan-search Skill

**Trigger:** When user asks about《左传》, Zuo Zhuan, or queries about ancient Chinese historical texts.

## Data Source

- **Source text:** `/Users/u/.openclaw/workspace/knowledge/zuozhuan.txt`
- **Chunks:** `/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl` (174 year-sections)
- **Vector DB:** `/Users/u/.openclaw/workspace/zuozhuan_chroma/` (Chroma)

## Usage

Run the query script:
```bash
python3 scripts/query_chroma.py "<query>" [n_results]
```

Examples:
```bash
python3 scripts/query_chroma.py "郑伯克段于鄢" 3
python3 scripts/query_chroma.py "郑庄公" 5
python3 scripts/query_chroma.py "二年春" 5
```

Or import in Python:
```python
import chromadb
from chromadb.config import Settings

settings = Settings(
    chroma_db_impl="duckdb+parquet",
    persist_directory="/Users/u/.openclaw/workspace/zuozhuan_chroma/"
)
client = chromadb.Client(settings)
collection = client.get_collection("zuozhuan")

results = collection.query(
    query_texts=["郑伯克段于鄢"],
    n_results=3,
    include=["documents", "metadatas", "distances"]
)
```

## Response Format

For each result, return:

```
[Result #] {year_label} (match score/distance)
----------------------------------------
{text preview}...
```

**Metadata fields:**
- `duke`: 公爵 (e.g., 隐公，桓公，庄公)
- `year`: Year number (Arabic numeral)
- `year_label`: Combined label (e.g., "隐公 1 年")

## Example Queries

| Query | Expected Results |
|-------|-----------------|
| "郑伯克段于鄢" | 隐公 1 年 (famous story of Zheng Bo conquering Duan at Yan) |
| "郑庄公" | Passages mentioning Duke Zhuang of Zheng |
| "二年春" | All "Year 2, Spring" passages across dukes |
| "颍考叔" | 隐公 1 年 (story of Ying Kaoshu's filial piety) |
| "宋公" | Passages about Dukes of Song |

## Search Method

**Hybrid search** combining:
1. **Keyword matching** - Character-level overlap (prioritized for classical Chinese)
2. **Semantic search** - Embedding-based similarity (all-MiniLM-L6-v2)

Results with ≥50% keyword match are shown first.

## Notes

- Preserve classical Chinese characters exactly
- Each chunk = one complete year section (经 + 传 combined)
- Source file is incomplete (ends at 襄公 year 3)
- 174 total chunks covering: 隐公，桓公，庄公，闵公，僖公，文公，宣公，成公，襄公 (partial)
