# 🦞 Guwen RAG 技能包

本地古典中文RAG系统，基于Ollama + ChromaDB，使用nomic-embed-text嵌入。

## 快速开始

```bash
# 1. 进入技能目录
cd skills/guwen-rag

# 2. 安装依赖
pip install -r requirements.txt

# 3. 确保ollama运行并拉取模型
ollama pull nomic-embed-text
ollama pull qwen2.5

# 4. 一键启动
./quickstart.sh
```

## 目录结构

```
guwen-rag/
├── SKILL.md                    # 技能主体
├── requirements.txt            # Python依赖
├── quickstart.sh              # 快速启动脚本
├── example_queries.txt        # 示例查询
├── scripts/
│   ├── setup_chroma.py        # 初始化ChromaDB
│   ├── ingest.py              # 导入典籍
│   ├── query.py               # 单次查询
│   ├── chat.py                # 对话模式
│   └── batch_query.py         # 批量查询
└── references/
    └── config.py              # 配置文件
```

## 使用模式

### A. 单次查询
```bash
python3 scripts/query.py "郑伯克段于鄢的大意"
```

### B. 对话模式
```bash
python3 scripts/chat.py
# 输入 exit 退出
```

### C. 批量查询
```bash
# 创建查询文件 queries.txt，每行一个问题
python3 scripts/batch_query.py -i queries.txt -o results.json
```

## 导入新典籍

```bash
python3 scripts/ingest.py --dir ./我的典籍/
```

支持自动chunking：按`##`标题分割，最大512 tokens，重叠50 tokens。

## 输出格式

所有响应均遵循【原文】【今译】【微旨】三叠式：

- **【原文】**：引用相关原文
- **【今译】**：现代白话翻译
- **【微旨】**：哲学与虚词解析

## 技术栈

- Ollama (localhost:11434)
- ChromaDB (本地持久化，duckdb+parquet)
- nomic-embed-text (768维嵌入)
- qwen2.5 (7B/14B，中文优化)

## 配置

编辑 `references/config.py` 修改：
- 嵌入模型
- 聊天模型
- chunk大小
- 检索top-k
- 系统提示词
