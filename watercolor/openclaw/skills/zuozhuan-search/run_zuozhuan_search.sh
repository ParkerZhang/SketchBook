#!/bin/bash
# ~/.openclaw/workspace/skills/zuozhuan-search/run_zuozhuan_search.sh
# Usage: ./run_zuozhuan_search.sh "Find 5 passages about 郑庄公"

set -e

# === CONFIG ===
OLLAMA_URL="http://localhost:11434"
MODEL="qwen3:8b"
MODEL="qwen3.5:0.8b"
#SEARCH_SCRIPT="/Users/u/.openclaw/workspace/skills/zuozhuan-search/scripts/query_chroma.py"
SEARCH_SCRIPT="/Users/u/.openclaw/workspace/skills/zuozhuan-search/scripts/query_chroma_xml.py"
SYSTEM_PROMPT="You are a Zuo Zhuan scholar. When asked about Zuo Zhuan content, call zuozhuan-search. Quote <content> verbatim, cite [Source: X]."

# === STEP 0: Get user input ===
USER_QUERY="${1:-What does the Zuo Zhuan say about 郑庄公？}"
echo "🔍 User query: $USER_QUERY"

# === STEP 1: Get tool call from LLM ===
echo "🤖 Calling LLM for tool call..."
CURL1="curl -s \"$OLLAMA_URL/api/chat\" -d \"{
  \"model\": \"$MODEL\",
  \"messages\": [
    {\"role\": \"system\", \"content\": \"$SYSTEM_PROMPT\"},
    {\"role\": \"user\", \"content\": \"$USER_QUERY\"}
  ],
  \"tools\": [{
    \"type\": \"function\",
    \"function\": {
      \"name\": \"zuozhuan-search\",
      \"description\": \"Search the Zuo Zhuan database for passages about people, events, or years\",
      \"parameters\": {
        \"type\": \"object\",
        \"properties\": {
          \"query\": {\"type\": \"string\", \"description\": \"Search query in Chinese or English\"},
          \"n_results\": {\"type\": \"integer\", \"description\": \"Number of results (1-10)\", \"minimum\": 1, \"maximum\": 10}
        },
        \"required\": [\"query\"]
      }
    }
  }],
  \"stream\": false
}"
#echo $CURL1

TOOL_CALL=$(curl -s "$OLLAMA_URL/api/chat" -d "{
  \"model\": \"$MODEL\",
  \"messages\": [
    {\"role\": \"system\", \"content\": \"$SYSTEM_PROMPT\"},
    {\"role\": \"user\", \"content\": \"$USER_QUERY\"}
  ],
  \"tools\": [{
    \"type\": \"function\",
    \"function\": {
      \"name\": \"zuozhuan-search\",
      \"description\": \"Search the Zuo Zhuan database for passages about people, events, or years\",
      \"parameters\": {
        \"type\": \"object\",
        \"properties\": {
          \"query\": {\"type\": \"string\", \"description\": \"Search query in Chinese or English\"},
          \"n_results\": {\"type\": \"integer\", \"description\": \"Number of results (1-10)\", \"minimum\": 1, \"maximum\": 10}
        },
        \"required\": [\"query\"]
      }
    }
  }],
  \"stream\": false
}" | jq -c '.message.tool_calls[0].function.arguments // empty')

if [ -z "$TOOL_CALL" ]; then
  echo "❌ No tool call returned. LLM response:"
  curl -s "$OLLAMA_URL/api/chat" -d "{
    \"model\": \"$MODEL\",
    \"messages\": [
      {\"role\": \"system\", \"content\": \"$SYSTEM_PROMPT\"},
      {\"role\": \"user\", \"content\": \"$USER_QUERY\"}
    ],
    \"stream\": false
  }" | jq -r '.message.content'
  exit 1
fi

echo "🔧 Tool call: $TOOL_CALL"

# === STEP 2: Extract parameters ===
QUERY=$(echo "$TOOL_CALL" | jq -r '.query')
N_RESULTS=$(echo "$TOOL_CALL" | jq -r '.n_results // 3')

echo "📦 Extracted: query='$QUERY', n_results=$N_RESULTS"

# === STEP 3: Run search script ===
echo "🗄️  Running Chroma search..."
CMD3="pyenv exec python3 \"$SEARCH_SCRIPT\" \"$QUERY\" \"$N_RESULTS\" 2>/dev/null"
#echo $CMD3
XML_OUTPUT=$(pyenv exec python3 "$SEARCH_SCRIPT" "$QUERY" "$N_RESULTS" 2>/dev/null)
if [ -z "$XML_OUTPUT" ] || ! echo "$XML_OUTPUT" | grep -q "<retrieved_data>"; then
  XML_OUTPUT='<retrieved_data><error>Search returned no results</error></retrieved_data>'
fi

echo "📄 Retrieved $(echo "$XML_OUTPUT" | grep -c '<entry') entries"
#echo $XML_OUTPUT
# Limit XML entries to first N (e.g., 3)
XML_SNIPPET=$(echo "$XML_OUTPUT" | awk '/<entry/{count++} {print} count==3 && /<\/entry>/{exit}')
echo $XML_SNIPPET
XML_SNIPPET=$XML_OUTPUT
# === STEP 4: Send results back to LLM for final answer ===
echo "💬 Generating final answer..."


# === STEP 4: LLM reasoning using strict quoting ===

PROMPT_STEP4=$(cat <<'EOF'
You are a scholar familiar with the Zuo Zhuan. You will receive <retrieved_data> containing multiple <entry> elements. Each entry has: <source>, <year>, <content>.

Task:
For each <entry> in <retrieved_data>:
1. Create a Markdown table with columns:
   | Source | Original Text | Reasoning |
   - Source = <source>
   - Original Text = <content> exactly as written
   - Reasoning = 1-2 sentences summarizing the factual significance or meaning of the entry, based only on the text
2. After the table, write a Conclusion paragraph summarizing only the information contained in the quoted entries

Requirements:
- Preserve all content from <content> exactly.
- Reasoning must be concise, factual, and based only on the content (no hallucinations).
- Output must be in Markdown table format.
EOF
)

# Step 4b: construct JSON for Ollama API using jq -Rn to escape special chars
LLM_JSON=$(jq -Rn \
  --arg prompt "$PROMPT_STEP4" \
  --arg xml "$XML_OUTPUT" \
  --arg query "$USER_QUERY" \
  --arg model "$MODEL" \
'{
  model: $model,
  messages: [
    {role: "system", content: $prompt},
    {role: "user", content: "Here is the filtered XML:\n\n\($xml)\n\nTask:\n- For each <entry> create a Markdown table with Source, Original Text, Reasoning.\n- After table, write a Conclusion summarizing only the quoted entries.\n- Answer user query: \($query)\nRequirements: preserve all <content> exactly, reasoning concise and factual, output in Markdown table format."}
  ],
  stream: false
}')


LLM_JSON=$(jq -Rn \
  --arg prompt "$PROMPT_STEP4" \
  --arg xml "$XML_OUTPUT" \
  --arg query "$USER_QUERY" \
  --arg model "$MODEL" \
'{
  model: $model,
  messages: [
    {
      role: "system",
      content: $prompt
    },
    {
      role: "user",
      content: "Here is the filtered XML:\n\n\($xml)\n\nTask:\n- For each <entry> create a Markdown table with columns: Source, Original Text, Reasoning.\n  - Source: exactly as in the entry\n  - Original Text: exactly as in the entry\n  - Reasoning: 1–2 sentences summarizing only the factual events explicitly mentioned in the Original Text; do NOT interpret, generalize, or add context\n- After the table, write a Conclusion summarizing only the factual content of the entries regarding the relevant ruler; do NOT add opinions or broader historical analysis\n- Answer the user query: \($query)\nRequirements: preserve all <content> exactly, reasoning concise and factual, output in Markdown table format."
    }
  ],
  stream: false
}')



CURL_CMD="curl -s \"$OLLAMA_URL/api/chat\" -H 'Content-Type: application/json' -d '$LLM_JSON'"
# Preview the command
echo "🔹 CURL command string:"
echo "$CURL_CMD"


# Step 4d: run the curl command
FINAL_ANSWER=$(eval $CURL_CMD | jq -r '.message.content // .message[0].content // "❌ No content returned"')

echo "═══════════════════════════════════════"
echo "$FINAL_ANSWER"
echo "═══════════════════════════════════════"
