#!/usr/bin/env python3
"""Log Classical Chinese input with timestamp."""

import sys
import datetime

LOG_FILE = "/tmp/logClassicChinese.log"

def log_classic_chinese(text):
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    with open(LOG_FILE, "a", encoding="utf-8") as f:
        f.write(f"[{timestamp}] {text}\n")
    print(f"✓ Logged to {LOG_FILE}")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        log_classic_chinese(sys.argv[1])
    else:
        print("Usage: python3 logClassicChinese.py '<text>'")
        sys.exit(1)
