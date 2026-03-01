"""
RAG System Configuration
"""
import os
import sqlite3
import sqlite_vec

# Paths
DB_DIR = os.path.expanduser("~/.openclaw/workspace/guwen")
DB_PATH = os.getenv("GUWEN_DB_PATH", os.path.join(DB_DIR, "guwen_vec.db"))
print(f"DEBUG: DB_DIR = {DB_DIR}")  # Add this
# Ensure directory exists
os.makedirs(DB_DIR, exist_ok=True)
print(f"DEBUG: Directory exists? {os.path.exists(DB_DIR)}")  # Add this
# Ollama Configuration
OLLAMA_HOST = os.getenv("OLLAMA_HOST", "http://localhost:11434")
EMBEDDING_MODEL = "nomic-embed-text"
CHAT_MODEL = "qwen2.5"

# Embedding Configuration
EMBEDDING_DIMENSION = 768  # nomic-embed-text dimension
MAX_TOKENS_PER_CHUNK = 512
CHUNK_OVERLAP = 50

# Retrieval Configuration
TOP_K_RESULTS = 5
SIMILARITY_THRESHOLD = 0.7

# System Prompt for Classical Chinese Scholar
SYSTEM_PROMPT = """你是经学大师。请严格按照以下格式回答：

【原文】直接引用相关原文
【今译】现代汉语翻译  
【微旨】解析哲学内涵，注意虚词（之、乎、者、也、矣、焉）

称用户为"学友"。文本如下：
{context}
"""


def get_db():
    """Get sqlite-vec database connection"""
    db = sqlite3.connect(DB_PATH)
    db.enable_load_extension(True)
    sqlite_vec.load(db)
    db.enable_load_extension(False)
    return db


def init_db():
    """Initialize vector table"""
    db = get_db()
    db.execute(f"""
        CREATE VIRTUAL TABLE IF NOT EXISTS documents USING vec0(
            embedding FLOAT[{EMBEDDING_DIMENSION}],
            text TEXT,
            source TEXT,
            chunk_id INTEGER
        )
    """)
    db.commit()
    return db
