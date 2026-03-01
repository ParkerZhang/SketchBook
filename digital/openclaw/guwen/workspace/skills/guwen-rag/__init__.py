"""
guwen-rag skill for OpenClaw
Classical Chinese RAG using sqlite-vec + Ollama
"""
import os
import sys
import struct
import json
import requests

# Ensure references is importable
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from references.config import get_db, OLLAMA_HOST, EMBEDDING_MODEL, CHAT_MODEL, TOP_K_RESULTS, SYSTEM_PROMPT

class GuwenRAGSkill:
    def __init__(self):
        self.db = get_db()
    
    def get_embedding(self, text):
        """Get embedding from Ollama"""
        response = requests.post(
            f"{OLLAMA_HOST}/api/embeddings",
            json={"model": EMBEDDING_MODEL, "prompt": text}
        )
        response.raise_for_status()
        return response.json()["embedding"]
    
    def search(self, query, k=TOP_K_RESULTS):
        """Search sqlite-vec for relevant documents"""
        query_embedding = self.get_embedding(query)
        query_bytes = struct.pack(f'{len(query_embedding)}f', *query_embedding)
        
        results = self.db.execute("""
            SELECT text, source, distance 
            FROM documents 
            WHERE embedding MATCH ?
              AND k = ?
            ORDER BY distance
        """, (query_bytes, k)).fetchall()
        
        return results
    
    def format_context(self, results):
        """Format search results for LLM context"""
        context_parts = []
        for text, source, distance in results:
            meta = json.loads(source) if source else {}
            filename = meta.get("filename", "Unknown")
            title = meta.get("title", "")
            context_parts.append(f"[{filename} - {title}]\n{text}")
        
        return "\n\n---\n\n".join(context_parts)
    
    def generate(self, query, context):
        """Generate response using Ollama"""
        prompt = SYSTEM_PROMPT.format(context=context)
        
        response = requests.post(
            f"{OLLAMA_HOST}/api/generate",
            json={
                "model": CHAT_MODEL,
                "system": prompt,
                "prompt": f"学友问：{query}\n\n请依据圣典作答：",
                "stream": False,
                "options": {
                    "temperature": 0.7,
                    "num_predict": 2048
                }
            }
        )
        response.raise_for_status()
        return response.json()["response"]
    
    def query(self, question):
        """Main entry point for OpenClaw"""
        # Search
        results = self.search(question)
        
        if not results:
            return "未找到相关典籍。请先使用 ingest.py 导入文献。"
        
        # Format context
        context = self.format_context(results)
        
        # Generate response
        answer = self.generate(question, context)
        
        # Format output with sources
        sources = []
        for text, source, dist in results:
            meta = json.loads(source) if source else {}
            sources.append(f"• {meta.get('filename', 'Unknown')} (dist: {dist:.3f})")
        
        return f"{answer}\n\n【参考来源】\n" + "\n".join(sources)


# OpenClaw entry point
def main(question, **kwargs):
    """OpenClaw skill entry point"""
    skill = GuwenRAGSkill()
    return skill.query(question)


# For testing
if __name__ == "__main__":
    import sys
    if len(sys.argv) > 1:
        question = " ".join(sys.argv[1:])
    else:
        question = "What did Confucius say about learning?"
    
    print(main(question))
