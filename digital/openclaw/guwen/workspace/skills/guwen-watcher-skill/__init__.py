"""
Guwen Watcher Skill - Auto-starts file watcher
"""
import os
import subprocess

WATCHER_SCRIPT = os.path.expanduser("~/.openclaw/workspace/guwen-watcher.sh")

def start_watcher():
    """Start the file watcher if not running"""
    # Check if already running
    result = subprocess.run(
        ["pgrep", "-f", "guwen-watcher.sh"],
        capture_output=True
    )
    
    if result.returncode != 0:
        # Not running, start it
        subprocess.Popen(
            [WATCHER_SCRIPT],
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL,
            start_new_session=True
        )
        return "Watcher started"
    return "Watcher already running"

# Auto-start on skill load
status = start_watcher()

def main(action="status", **kwargs):
    """Skill entry point"""
    if action == "start":
        return start_watcher()
    elif action == "stop":
        subprocess.run(["pkill", "-f", "guwen-watcher.sh"])
        return "Watcher stopped"
    elif action == "status":
        result = subprocess.run(["pgrep", "-f", "guwen-watcher.sh"], capture_output=True)
        return "Running" if result.returncode == 0 else "Stopped"
    return status
