#!/usr/bin/env python3
import sys
import os

# Add parent to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from references.config import init_db

if __name__ == "__main__":
    db = init_db()
    print(f"✓ sqlite-vec database initialized at ~/.openclaw/workspace/guwen/guwen_vec.db")
