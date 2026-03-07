---
name: log-classic-chinese
description: Append user input containing Classical Chinese text to /tmp/logClassicChinese.log. Trigger when the user input contains Classical Chinese (文言文) content including but not limited to texts like 左传, 论语, 诗经, 楚辞, 史记, or any traditional Chinese literary content. Use when the user shares, quotes, or inputs Classical Chinese passages.
---

# logClassicChinese Skill

This skill logs Classical Chinese text input to a file for preservation and reference.

## Usage

When the user inputs Classical Chinese text (文言文), append the content to `/tmp/logClassicChinese.log` with a timestamp.

## Script

Run `python3 scripts/logClassicChinese.py "<input_text>"` to append the content with a timestamp.

## What Counts as Classical Chinese

- Pre-modern Chinese texts (pre-1919)
- Works like 左传, 论语, 诗经, 楚辞, 史记
- Classical poetry, prose, and historical texts
- Any traditional Chinese literary content using 文言文
