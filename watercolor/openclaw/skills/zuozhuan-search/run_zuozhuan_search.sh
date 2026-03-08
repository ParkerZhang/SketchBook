#!/bin/bash
# ~/.openclaw/skills/zuozhuan-search/run_zuozhuan_search.sh
# Usage: ./run_zuozhuan_search.sh "Find 5 passages about 郑庄公"

set -e

# === CONFIG ===
OLLAMA_URL="http://localhost:11434"
MODEL="qwen3:8b"
SEARCH_SCRIPT="/Users/u/.openclaw/skills/zuozhuan-search/scripts/query_chroma_xml.py"
SYSTEM_PROMPT="You are a Zuo Zhuan scholar. When asked about Zuo Zhuan content, call zuozhuan-search. Quote <content> verbatim, cite [Source: X]."

# === STEP 0: Get user input ===
USER_QUERY="${1:-What does the Zuo Zhuan say about 郑庄公？}"
echo "🔍 User query: $USER_QUERY"

# === STEP 1: Get tool call from LLM ===
echo "🤖 Calling LLM for tool call..."
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
XML_OUTPUT=$(python3 "$SEARCH_SCRIPT" "$QUERY" "$N_RESULTS" 2>/dev/null)

if [ -z "$XML_OUTPUT" ] || ! echo "$XML_OUTPUT" | grep -q "<retrieved_data>"; then
  XML_OUTPUT='<retrieved_data><error>Search returned no results</error></retrieved_data>'
fi

echo "📄 Retrieved $(echo "$XML_OUTPUT" | grep -c '<entry') entries"

# === STEP 4: Send results back to LLM for final answer ===
echo "💬 Generating final answer..."
FINAL_ANSWER=$(curl -s "$OLLAMA_URL/api/chat" -d "{
  \"model\": \"$MODEL\",
  \"messages\": [
    {\"role\": \"system\", \"content\": \"$SYSTEM_PROMPT When you receive <retrieved_data>, quote <content> verbatim and cite [Source: X].\"},
    {\"role\": \"user\", \"content\": \"$USER_QUERY\"},
    {\"role\": \"assistant\", \"tool_calls\": [{\"function\": {\"name\": \"zuozhuan-search\", \"arguments\": $TOOL_CALL}}]},
    {\"role\": \"tool\", \"name\": \"zuozhuan-search\", \"content\": $(echo "$XML_OUTPUT" | jq -sR .)}
  ],
  \"stream\": false
}" | jq -r '.message.content')

# === STEP 5: Output final answer ===
echo ""
echo "═══════════════════════════════════════"
echo "$FINAL_ANSWER"
echo "═══════════════════════════════════════"
