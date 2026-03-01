#!/usr/bin/env python3
"""
Batch query mode - process multiple queries from file using sqlite-vec
"""
import os
import sys
import struct
import json
import requests
from concurrent.futures import ThreadPoolExecutor, as_completed

# Add parent to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from references.config import get_db, OLLAMA_HOST, EMBEDDING_MODEL, CHAT_MODEL, TOP_K_RESULTS

def get_embedding(text):
    response = requests.post(
        f"{OLLAMA_HOST}/api/embeddings",
        json={"model": EMBEDDING_MODEL, "prompt": text}
    )
    return response.json()["embedding"]

def search_documents(query_embedding):
    """Search sqlite-vec for relevant documents"""
    query_bytes = struct.pack(f'{len(query_embedding)}f', *query_embedding)
    
    db = get_db()
    results = db.execute("""
        SELECT text, source, distance 
        FROM documents 
        WHERE embedding MATCH ?
          AND k = ?
        ORDER BY distance
    """, (query_bytes, TOP_K_RESULTS)).fetchall()
    
    sources = []
    for text, source, dist in results:
        meta = json.loads(source) if source else {}
        sources.append({
            "content": text[:500] + "..." if len(text) > 500 else text,
            "source": meta.get("source", "unknown"),
            "title": meta.get("title", ""),
            "distance": float(dist)
        })
    
    return sources

def generate_response(query, context_sources):
    context = "\n\n".join([s["content"] for s in context_sources])
    
    system_prompt = f"""你是经学大师。回答遵循【原文】【今译】【微旨】三式。

相关文本：
{context}
"""
    
    response = requests.post(
        f"{OLLAMA_HOST}/api/generate",
        json={
            "model": CHAT_MODEL,
            "system": system_prompt,
            "prompt": f"学友问：{query}",
            "stream": False,
            "options": {"temperature": 0.7, "num_predict": 2048}
        }
    )
    
    return response.json()["response"]

def process_single_query(query_data):
    """Process a single query"""
    if isinstance(query_data, str):
        query_text = query_data
        query_id = None
    else:
        query_text = query_data.get("query", "")
        query_id = query_data.get("id")
    
    # Embed
    query_embedding = get_embedding(query_text)
    
    # Retrieve
    sources = search_documents(query_embedding)
    
    # Generate
    response = generate_response(query_text, sources)
    
    return {
        "id": query_id,
        "query": query_text,
        "response": response,
        "sources": sources
    }

def batch_process(input_file, output_file, max_workers=4):
    """Process batch queries from file"""
    
    # Read queries
    with open(input_file, 'r', encoding='utf-8') as f:
        if input_file.endswith('.json'):
            queries = json.load(f)
        else:
            queries = [line.strip() for line in f if line.strip()]
    
    print(f"📚 批量处理 {len(queries)} 个查询...")
    
    # Process in parallel
    results = []
    with ThreadPoolExecutor(max_workers=max_workers) as executor:
        futures = {
            executor.submit(process_single_query, q): i 
            for i, q in enumerate(queries)
        }
        
        for future in as_completed(futures):
            i = futures[future]
            try:
                result = future.result()
                results.append((i, result))
                print(f"  ✓ 完成 [{i+1}/{len(queries)}]: {result['query'][:30]}...")
            except Exception as e:
                print(f"  ✗ 失败 [{i+1}/{len(queries)}]: {e}")
                results.append((i, {"error": str(e)}))
    
    # Sort by original order
    results.sort(key=lambda x: x[0])
    final_results = [r[1] for r in results]
    
    # Save
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(final_results, f, ensure_ascii=False, indent=2)
    
    print(f"\n✓ 结果已保存至: {output_file}")
    return final_results

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Batch process queries")
    parser.add_argument("--input", "-i", required=True, help="Input file (txt or json)")
    parser.add_argument("--output", "-o", default="batch_results.json", help="Output JSON file")
    parser.add_argument("--workers", "-w", type=int, default=4, help="Parallel workers")
    
    args = parser.parse_args()
    batch_process(args.input, args.output, args.workers)
