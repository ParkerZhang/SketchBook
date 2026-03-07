#!/usr/bin/env python3
"""
Chunk zuozhuan.txt into structured JSON for vector embedding.

Each chunk represents one year section with metadata:
- duke: 公爵 (e.g., 隐公，桓公)
- year: year number
- text: full 经 + 传 text for that year
"""

import json
import re
from pathlib import Path

INPUT_FILE = "/Users/u/.openclaw/workspace/knowledge/zuozhuan.txt"
OUTPUT_FILE = "/Users/u/.openclaw/workspace/knowledge/zuozhuan_chunks.jsonl"

# Regex patterns - build carefully to avoid accidental spaces
DUKE_CHARS = "隐桓庄闵僖文宣成襄昭定哀"
YEAR_CHARS = "元〇一二三四五六七八九十百"

# Build YEAR_PATTERN using parts to avoid accidental spaces between Chinese chars
YEAR_PATTERN = re.compile(''.join([
    '^###',           # start
    r'\s*\*\*([',     # whitespace + ** + capture group start
    DUKE_CHARS,       # duke characters
    '])',             # end duke group
    '公',              # literal 公 (NO space before or after)
    '([',             # start year group
    YEAR_CHARS,       # year characters  
    ']+)',            # end year group
    '年',              # literal 年 (NO space before)
    r'\*\*',          # literal **
]))

# Build DUKE_PATTERN - format: ## **隐公**
DUKE_PATTERN = re.compile(''.join([
    '^##',
    r'\s*\*\*([',
    DUKE_CHARS,
    '])',
    '公',
    r'\*\*'
]))

# Chinese number to Arabic conversion
CHINESE_NUMS = {
    '〇': 0, '一': 1, '二': 2, '两': 2, '三': 3, '四': 4, '五': 5,
    '六': 6, '七': 7, '八': 8, '九': 9, '十': 10, '百': 100
}

def chinese_to_arabic(num_str: str) -> int:
    """Convert Chinese year number to Arabic numeral."""
    num_str = num_str.strip()
    if not num_str:
        return 0
    if num_str == '元':
        return 1

    # Handle simple cases
    if len(num_str) == 1:
        return CHINESE_NUMS.get(num_str, 0)

    # Handle 十 (10), 二十 (20), etc.
    result = 0
    temp = 0
    for char in num_str:
        if char == '十':
            if temp == 0:
                temp = 1
            result += temp * 10
            temp = 0
        else:
            temp = temp * 10 + CHINESE_NUMS.get(char, 0)
    return result + temp


def parse_zuozhuan(filepath: str) -> list[dict]:
    """Parse zuozhuan.txt and return list of chunk dictionaries."""

    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    lines = content.split('\n')
    chunks = []

    current_duke = None
    current_year = None
    current_text_lines = []
    in_year_section = False

    for line in lines:
        # Check for Duke header (## level)
        duke_match = DUKE_PATTERN.match(line)
        if duke_match:
            # Save previous year section before changing duke
            if in_year_section and current_text_lines:
                text = '\n'.join(current_text_lines).strip()
                if text:
                    chunks.append({
                        "duke": current_duke,
                        "year": current_year,
                        "year_label": f"{current_duke}{current_year}年",
                        "text": text
                    })
            current_duke = duke_match.group(1) + "公"
            current_text_lines = []
            in_year_section = False
            continue

        # Check for Year header (### level)
        year_match = YEAR_PATTERN.match(line)
        if year_match:
            # Save previous section if exists
            if in_year_section and current_text_lines:
                text = '\n'.join(current_text_lines).strip()
                if text:
                    chunks.append({
                        "duke": current_duke,
                        "year": current_year,
                        "year_label": f"{current_duke}{current_year}年",
                        "text": text
                    })

            # Start new section
            current_year = chinese_to_arabic(year_match.group(2))
            current_text_lines = []
            in_year_section = True
            continue

        # Accumulate text content
        if in_year_section:
            # Skip empty lines at the start
            if current_text_lines or line.strip():
                current_text_lines.append(line)

    # Don't forget the last section
    if in_year_section and current_text_lines:
        text = '\n'.join(current_text_lines).strip()
        if text:
            chunks.append({
                "duke": current_duke,
                "year": current_year,
                "year_label": f"{current_duke}{current_year}年",
                "text": text
            })

    return chunks


def main():
    print(f"Parsing {INPUT_FILE}...")
    chunks = parse_zuozhuan(INPUT_FILE)

    print(f"Found {len(chunks)} year sections")

    # Write to JSONL file
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        for chunk in chunks:
            f.write(json.dumps(chunk, ensure_ascii=False) + '\n')

    print(f"Written to {OUTPUT_FILE}")

    # Print sample
    print("\n--- Sample chunks ---")
    for i, chunk in enumerate(chunks[:3]):
        print(f"\n[{i+1}] {chunk['year_label']}")
        print(f"    Text preview: {chunk['text'][:80]}...")


if __name__ == "__main__":
    main()
