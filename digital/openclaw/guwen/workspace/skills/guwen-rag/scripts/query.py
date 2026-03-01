#!/usr/bin/env python3
"""
Single query mode - retrieve and generate response using sqlite-vec
"""
import os
import sys
import struct
import json
import requests

# Add parent to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from references.config import get_db, OLLAMA_HOST, EMBEDDING_MODEL, CHAT_MODEL, TOP_K_RESULTS

def get_query_embedding(query):
    """Get embedding for query"""
    response = requests.post(
        f"{OLLAMA_HOST}/api/embeddings",
        json={"model": EMBEDDING_MODEL, "prompt": query}
    )
    if response.status_code == 200:
        return response.json()["embedding"]
    else:
        raise Exception(f"Embedding failed: {response.text}")

def search_collection(query_embedding, top_k=TOP_K_RESULTS):
    """Search sqlite-vec for relevant documents"""
    query_bytes = struct.pack(f'{len(query_embedding)}f', *query_embedding)
    
    db = get_db()
    # Use k = ? constraint instead of LIMIT
    results = db.execute("""
        SELECT text, source, distance 
        FROM documents 
        WHERE embedding MATCH ?
          AND k = ?
        ORDER BY distance
    """, (query_bytes, top_k)).fetchall()
    
    return results

def format_context(results):
    """Format retrieved documents into context string"""
    context_parts = []
    
    for i, (text, source, dist) in enumerate(results):
        meta = json.loads(source) if source else {}
        filename = meta.get("filename", "Unknown")
        title = meta.get("title", "")
        context_parts.append(f"\n[来源 {i+1}: {filename} - {title} (dist: {dist:.3f})]\n{text}\n")
    
    return "\n".join(context_parts)

def generate_response(query, context):
    """Generate response using Ollama"""
    
    system_prompt = f"""你是经学大师（Classical Chinese Scholar & Philosopher）。

你的知识库来源于以下经典文本。回答时务必遵循【原文】【今译】【微旨】三叠式格式。

【原文】：先引用相关原文
【今译】：提供精准白话文翻译  
【微旨】：解析哲学内涵，特别注意虚词（之、乎、者、也、矣、焉）的用法

语气需儒雅、谦逊，称用户为"学友"。

相关文本：
{context}
"""
    
    prompt = f"学友问：{query}\n\n请依据圣典作答："
    
    response = requests.post(
        f"{OLLAMA_HOST}/api/generate",
        json={
            "model": CHAT_MODEL,
            "system": system_prompt,
            "prompt": prompt,
            "stream": False,
            "options": {
                "temperature": 0.7,
                "num_predict": 2048
            }
        }
    )
    
    if response.status_code == 200:
        return response.json()["response"]
    else:
        raise Exception(f"Generation failed: {response.text}")

def query(query_text):
    """Main query function"""
    print(f"🔍 学友问：{query_text}\n")
    
    # Get embedding
    print("  生成查询向量...")
    query_embedding = get_query_embedding(query_text)
    
    # Search
    print("  检索相关文本...")
    results = search_collection(query_embedding, TOP_K_RESULTS)
    
    # Format context
    context = format_context(results)
    print(f"  找到 {len(results)} 篇相关文本\n")
    
    # Generate
    print("  生成回答...\n")
    print("=" * 60)
    response = generate_response(query_text, context)
    print(response)
    print("=" * 60)
    
    return response

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Query the RAG system")
    parser.add_argument("query", help="Query text")
    
    args = parser.parse_args()
    query(args.query)
