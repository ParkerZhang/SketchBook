#!/bin/bash
# Watch folder for new markdown files, ingest them, then remove

WATCH_DIR="$HOME/.openclaw/workspace/guwen/inbox"
SKILL_DIR="$HOME/.openclaw/workspace/skills/guwen-rag"
LOG_FILE="$HOME/.openclaw/workspace/guwen/watcher.log"

mkdir -p "$WATCH_DIR"

echo "$(date): Watching $WATCH_DIR for new files..." >> "$LOG_FILE"

while true; do
    # Find markdown files
    for file in "$WATCH_DIR"/*.md; do
        [ -e "$file" ] || continue  # Skip if no matches
        
        echo "$(date): Found $file" >> "$LOG_FILE"
        
        # Ingest single file
        cd "$SKILL_DIR"
        python3 scripts/ingest.py --dir "$WATCH_DIR" 2>&1 >> "$LOG_FILE"
        
        if [ $? -eq 0 ]; then
            # Remove after successful ingest
            rm "$file"
            echo "$(date): Ingested and removed $file" >> "$LOG_FILE"
        else
            echo "$(date): ERROR ingesting $file" >> "$LOG_FILE"
        fi
    done
    
    # Check every 10 seconds
    sleep 10
done
