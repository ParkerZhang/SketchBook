# Zuo Zhuan Search - Chroma Vector DB

## Files

- `chunk_zuozhuan.py` - Chunks zuozhuan.txt into structured JSON
- `embed_chroma.py` - Embeds chunks into Chroma vector database
- `query_chroma.py` - Query script for searching

## Usage

### 1. Chunk the source text (already done)
```bash
python3 chunk_zuozhuan.py
```

### 2. Embed into Chroma (already done)
```bash
python3 embed_chroma.py
```

### 3. Search
```bash
# Basic search
python3 query_chroma.py "郑伯克段于鄢"

# Get more results
python3 query_chroma.py "郑庄公" 10

# Search by year/season
python3 query_chroma.py "二年春" 5
```

## Database Location

Chroma DB is stored at: `/Users/u/.openclaw/workspace/zuozhuan_chroma/`

## Data Structure

Each chunk contains:
- `duke`: 公爵 name (e.g., 隐公，桓公)
- `year`: Year number (Arabic numeral)
- `year_label`: Combined label (e.g., "隐公 1 年")
- `text`: Full 经 + 传 text for that year

## Search Method

The query script uses **hybrid search**:
1. **Keyword matching** - Prioritizes exact character matches (good for classical Chinese)
2. **Semantic search** - Uses embeddings for conceptual similarity

Results with ≥50% keyword match are shown first, followed by semantic matches.

## Source Data

- Input: `/Users/u/.openclaw/workspace/knowledge/zuozhuan.txt`
- Chunks: `/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl` (174 chunks)
- Coverage: 隐公 through 襄公 (partial - source file is incomplete)
