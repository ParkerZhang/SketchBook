from langchain_core.documents import Document
from langchain_experimental.graph_transformers import LLMGraphTransformer
from langchain_ollama import OllamaLLM
import kuzu

db = kuzu.Database("test_db")
conn = kuzu.Connection(db)
from langchain_kuzu.graphs.kuzu_graph import KuzuGraph

graph = KuzuGraph(db, allow_dangerous_requests=True)


from langchain_kuzu.graphs.kuzu_graph import KuzuGraph

graph = KuzuGraph(db, allow_dangerous_requests=True)

# Define schema
allowed_nodes = ["Person", "Company", "Location"]
allowed_relationships = [
    ("Person", "IS_CEO_OF", "Company"),
    ("Company", "HAS_HEADQUARTERS_IN", "Location"),
]
text = "Tim Cook is the CEO of Apple. Apple has its headquarters in California."

# Initialize the local Ollama LLM with the updated class
llm = OllamaLLM(
    model="deepseek-r1:1.5b",  # Replace with your preferred model (e.g., "mistral", "llama3.1")
    base_url="http://localhost:11434",  # Default Ollama server URL
    temperature=0,  # Controls randomness; 0 for deterministic output
)

# Define the LLMGraphTransformer
llm_transformer = LLMGraphTransformer(
    llm=llm,
    allowed_nodes=allowed_nodes,
    allowed_relationships=allowed_relationships,
)


# Create a LangChain Document from the text
documents = [Document(page_content=text)]

# Convert text to graph documents
graph_documents = llm_transformer.convert_to_graph_documents(documents)
print(graph_documents[:2])

# Print the resulting graph structure
print("Nodes:")
for node in graph_documents[0].nodes:
    print(f" - {node.id} ({node.type})")

print("\nRelationships:")
for rel in graph_documents[0].relationships:
    print(f" - {rel.source.id} -> {rel.type} -> {rel.target.id}")

# Add the graph document to the graph
graph.add_graph_documents(
    graph_documents,
    include_source=True,
)
