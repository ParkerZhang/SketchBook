#!/bin/bash
# Quick Start Script for Guwen RAG

echo "================================"
echo "🦞 Guwen RAG 快速启动"
echo "================================"

# Check if ollama is running
if ! curl -s http://localhost:11434/api/tags > /dev/null 2>&1; then
    echo "❌ Ollama 未运行。请先启动 ollama serve"
    exit 1
fi

echo "✓ Ollama 运行中"

# Check/pull models
echo ""
echo "📥 检查模型..."

if ! ollama list | grep -q "nomic-embed-text"; then
    echo "  拉取 nomic-embed-text..."
    ollama pull nomic-embed-text
fi
echo "  ✓ nomic-embed-text"

if ! ollama list | grep -q "qwen2.5"; then
    echo "  拉取 qwen2.5..."
    ollama pull qwen2.5
fi
echo "  ✓ qwen2.5"

# Setup ChromaDB
echo ""
echo "🔧 初始化 ChromaDB..."
python3 scripts/setup_chroma.py

# Check for documents
echo ""
GUWEN_DIR="${1:-~/.openclaw/workspace/guwen}"
if [ -d "$GUWEN_DIR" ]; then
    echo "📚 发现典籍目录: $GUWEN_DIR"
    echo "  是否导入典籍? (y/n)"
    read -r response
    if [[ "$response" =~ ^([yY][eE][sS]|[yY])$ ]]; then
        python3 scripts/ingest.py --dir "$GUWEN_DIR"
    fi
else
    echo "⚠️  未找到典籍目录，跳过导入"
fi

echo ""
echo "================================"
echo "🎉 启动完成！可用命令："
echo ""
echo "  单次查询:"
echo "    python3 scripts/query.py '北风 含义'"
echo ""
echo "  对话模式:"
echo "    python3 scripts/chat.py"
echo ""
echo "  批量查询:"
echo "    python3 scripts/batch_query.py -i queries.txt"
echo ""
echo "================================"
