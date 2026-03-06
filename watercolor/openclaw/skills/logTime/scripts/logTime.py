#!/usr/bin/env python3
"""
logTime - Append current timestamp to log file
"""
import datetime
import sys

LOG_FILE = "/tmp/logTimePy.log"

def main():
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    try:
        with open(LOG_FILE, "a") as f:
            f.write(f"{timestamp}\n")
        print(f"Logged: {timestamp}")
    except IOError as e:
        print(f"Error writing to {LOG_FILE}: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()
