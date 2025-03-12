from langchain_community.chat_models import Ollama
from langchain_kuzu.chains.graph_qa.kuzu import KuzuQAChain
from langchain.graphs import KuzuGraph

# Assuming you have already set up your graph instance
graph = KuzuGraph(database_path="test.db")

# Create the KuzuQAChain with Ollama as the language model
chain = KuzuQAChain.from_llm(
    llm=Ollama(
        model="deepseek-r1:1.5b",
        base_url="http://localhost:11434",  # Default Ollama local server endpoint
        temperature=0.3
    ),
    graph=graph,
    verbose=True,
    allow_dangerous_requests=True,
)

# Example query
response = chain.run("What insights can you give about the graph data?")
print(response)

