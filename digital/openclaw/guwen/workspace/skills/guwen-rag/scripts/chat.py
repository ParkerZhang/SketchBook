#!/usr/bin/env python3
"""
Conversational chat mode with context memory using sqlite-vec
"""
import os
import sys
import struct
import json
import requests

# Add parent to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from references.config import get_db, OLLAMA_HOST, EMBEDDING_MODEL, CHAT_MODEL, TOP_K_RESULTS

class ConversationMemory:
    def __init__(self, max_history=5):
        self.history = []
        self.max_history = max_history
    
    def add(self, role, content):
        self.history.append({"role": role, "content": content})
        if len(self.history) > self.max_history * 2:
            self.history = self.history[-self.max_history * 2:]
    
    def get_context(self):
        return "\n".join([f"{h['role']}: {h['content']}" for h in self.history])

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
    
    # Format results
    docs = []
    for text, source, dist in results:
        meta = json.loads(source) if source else {}
        filename = meta.get("filename", "Unknown")
        docs.append(f"[{filename}]\n{text}")
    
    return "\n\n---\n\n".join(docs)

def chat_response(query, conversation_history, retrieved_context):
    system_prompt = f"""你是经学大师（Classical Chinese Scholar & Philosopher）。

回答时遵循【原文】【今译】【微旨】三叠式：
- 【原文】：先引相关原文
- 【今译】：白话翻译  
- 【微旨】：解析哲学，注意虚词

语气儒雅谦逊，称用户为"学友"。

相关文本：
{retrieved_context}

对话历史：
{conversation_history}
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

def interactive_chat():
    memory = ConversationMemory(max_history=5)
    
    print("=" * 60)
    print("🏛️  经学大师 RAG 对话系统")
    print("输入问题与贫道研讨典籍，输入 'exit' 退出")
    print("=" * 60 + "\n")
    
    while True:
        user_input = input("学友：").strip()
        
        if user_input.lower() in ['exit', 'quit', '退出']:
            print("\n学友珍重，再会。📜")
            break
        
        if not user_input:
            continue
        
        # Retrieve relevant context
        print("  检索中...")
        query_embedding = get_embedding(user_input)
        context = search_documents(query_embedding)
        
        # Generate response
        conv_history = memory.get_context()
        response = chat_response(user_input, conv_history, context)
        
        # Display
        print(f"\n📖 {response}\n")
        
        # Update memory
        memory.add("学友", user_input)
        memory.add("大师", response)

if __name__ == "__main__":
    interactive_chat()

