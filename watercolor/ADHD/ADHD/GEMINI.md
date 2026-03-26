# ADHD Obsidian Vault Overview

This directory is an Obsidian vault configured for a software engineer's "Second Brain," utilizing a PARA-inspired organizational structure to balance rapid daily capture with long-term knowledge synthesis.

## 📂 Directory Overview

The vault is organized into a hierarchical structure to keep the root directory clean and ensure notes have a clear purpose:

- **`00-Journal/`**: The primary location for Daily Notes, stand-up logs, and interstitial journaling.
- **`10-Projects/`**: Active work with defined scope and deadlines (e.g., specific feature implementations or refactors).
- **`20-Knowledge/`**: Atomic, evergreen notes on technical concepts, languages, patterns, and tools.
- **`30-Resources/`**: Static references, cheat sheets, and links to external documentation.
- **`40-Archive/`**: Completed projects or knowledge that is no longer active but worth preserving.
- **`90-System/`**: Internal vault management.
  - `Templates/`: Standardized starting points for different types of notes.
  - `Attachments/`: Binary files, images, and PDFs to prevent root clutter.

## 📝 Key Files & Templates

The vault uses specific templates to maintain consistency and capture structured metadata:

- **`90-System/Templates/Daily Note.md`**: Optimized for software engineering workflows. Includes sections for:
  - Daily Goals
  - Stand-up Notes (Yesterday, Today, Blockers)
  - Interstitial Journal (Flow tracking)
  - Daily Reflection (Wins and learnings)
- **`90-System/Templates/Technical Note.md`**: A blueprint for capturing atomic technical knowledge. Includes:
  - YAML frontmatter for tags and status.
  - Context/Motivation
  - Code snippet placeholders
  - Resource links and related note connections.

## 🛠️ Usage & Integration

- **Daily Capture**: Use the `Daily Note` template via the Daily Notes plugin (or Calendar) to track work-in-progress.
- **Knowledge Synthesis**: When a concept becomes clear during a project, create a new note in `20-Knowledge/` using the `Technical Note` template.
- **Plugins**:
  - **Templater**: Used for dynamic template insertion.
  - **Calendar**: Integrated for visual navigation of the `00-Journal/` folder.
  - **Terminal**: Integrated for quick command-line access directly within the Obsidian UI.

## ⚙️ Maintenance
Keep the root directory clean by ensuring all new notes are moved into one of the top-level PARA folders. Use `90-System/Attachments` for all non-markdown files.
