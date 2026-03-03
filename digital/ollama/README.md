# Ollama LLM Model

This repository hosts a lightweight implementation of an LLM model using **Ollama**. It is designed to provide a simple, extensible framework for experimenting with language models locally using the `ollama run` and `ollama create` commands.

---

## Table of Contents
- [Background](#background)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Development](#development)
- [License](#license)
- [Acknowledgements](#acknowledgements)

---

## Background
Ollama is an open‑source, self‑hosted LLM framework that offers a Python API, command‑line tools, and a REST interface. This project wraps the Ollama client to provide:
- A minimal CLI to load a model, generate completions, and manage embeddings.
- Sample scripts that demonstrate text generation and question‑answering.

## Features
- Load any Ollama model locally (e.g., `llama2`, `gpt4all`).
- Streaming completions via the command line.
- Test harness and examples for quick experimentation.

## Prerequisites
- Python 3.11 or newer.
- Docker (optional, for running the Ollama server).
- A pre‑downloaded Ollama model or the ability to pull one from the Ollama hub.

## Installation
```bash
# 1. Clone the repository
git clone https://github.com/your-org/ollama-llm-model.git
cd ollama-llm-model

# 2. (Optional) Create a virtual environment
python -m venv .venv
source .venv/bin/activate

# 3. Install dependencies
pip install -r requirements.txt
```

## Usage
### Running a model with `ollama run`
```bash
# Ensure Ollama is running locally (see Ollama docs)
export OLLAMA_HOST=http://localhost:11434

# Run a model directly
ollama run llama2
# or, to pass a prompt
ollama run llama2 "Tell me a short joke about AI."
```
The model will stream output to the terminal.

### Creating a custom model instance with `ollama create`
```bash
# Create a new instance based on an existing model
ollama create my-custom-model --from llama2

# Use the new instance
ollama run my-custom-model "What is the capital of France?"
```

## Examples
```python
# example_usage.py
from app.client import OllamaClient

client = OllamaClient()
print(client.complete("llama2", "Tell me a short joke about AI."))
```

## Development
1. Install dev dependencies: `pip install -r dev-requirements.txt`
2. Run tests: `pytest`
3. Linting: `flake8 .`
4. Format: `black .`

### Adding a new model
- Download the model via `ollama pull <model_name>`.
- Update `app/config.py` if needed.
- Write tests in `tests/`.

## License
MIT © 2026 Your Name. See `LICENSE`.

## Acknowledgements
- Ollama team for providing the open‑source LLM framework.
- The open‑source community for tools and libraries.
