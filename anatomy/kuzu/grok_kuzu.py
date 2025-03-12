import os
import csv
from langchain_core.documents import Document
from langchain_experimental.graph_transformers import LLMGraphTransformer
from langchain_ollama import OllamaLLM

# Define allowed nodes and relationships for the graph
allowed_nodes = ["Instrument", "Exchange", "Country", "Currency"]
allowed_relationships = [
    "TRADED_ON",  # Instrument traded on Exchange
    "ISSUED_IN",  # Instrument issued in Country
    "DENOMINATED_IN",  # Instrument denominated in Currency
    "RISK_IN",  # Instrument has risk in Country
    "DOMICILED_IN"  # Instrument domiciled in Country
]

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

# Function to convert CSV rows to text for LLM processing
def csv_to_text(csv_file_path):
    text = ""
    with open(csv_file_path, 'r', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            text += (
                f"Instrument {row['Instrument Name']} with ticker {row['Ticker']} "
                f"has ISIN {row['ISIN']}, SEDOL {row['SEDOL']}, and CUSIP {row['CUSIP']}. "
                f"It is traded on {row['Exchange']} in {row['Currency']}. "
                f"It was issued in {row['Issue Country']}, has risk in {row['Risk Country']}, "
                f"and is domiciled in {row['Issue Domicile']}.\n"
            )
    return text

# Path to your Security Master File CSV (adjust as needed)
csv_file_path = "security_master_updated.csv"

# Convert CSV to text
text = csv_to_text(csv_file_path)

# Create a LangChain Document from the text
documents = [Document(page_content=text)]

# Convert text to graph documents
graph_documents = llm_transformer.convert_to_graph_documents(documents)

# Print the resulting graph structure
print("Nodes:")
for node in graph_documents[0].nodes:
    print(f" - {node.id} ({node.type})")

print("\nRelationships:")
for rel in graph_documents[0].relationships:
    print(f" - {rel.source.id} -> {rel.type} -> {rel.target.id}")

# Optional: Connect to Kùzu and store the graph (uncomment and adjust as needed)
"""
import kuzu
db = kuzu.Database("path_to_kuzu_database")
conn = kuzu.Connection(db)

# Define schema (example)
conn.execute("CREATE NODE TABLE Instrument(name STRING, ticker STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Exchange(name STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Country(name STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Currency(code STRING, PRIMARY KEY(code))")
conn.execute("CREATE REL TABLE TRADED_ON(FROM Instrument TO Exchange)")
conn.execute("CREATE REL TABLE ISSUED_IN(FROM Instrument TO Country)")
conn.execute("CREATE REL TABLE DENOMINATED_IN(FROM Instrument TO Currency)")
conn.execute("CREATE REL TABLE RISK_IN(FROM Instrument TO Country)")
conn.execute("CREATE REL TABLE DOMICILED_IN(FROM Instrument TO Country)")

# Insert nodes and relationships
for node in graph_documents[0].nodes:
    if node.type == "Instrument":
        conn.execute(f"CREATE (i:Instrument {{name: '{node.id}', ticker: '{node.properties.get('ticker', '')}'}})")
    elif node.type == "Exchange":
        conn.execute(f"CREATE (e:Exchange {{name: '{node.id}'}})")
    elif node.type == "Country":
        conn.execute(f"CREATE (c:Country {{name: '{node.id}'}})")
    elif node.type == "Currency":
        conn.execute(f"CREATE (c:Currency {{code: '{node.id}'}})")

for rel in graph_documents[0].relationships:
    conn.execute(f"MATCH (source:{rel.source.type} {{name: '{rel.source.id}'}}), "
                 f"(target:{rel.target.type} {{name: '{rel.target.id}'}}) "
                 f"CREATE (source)-[:{rel.type}]->(target)")
"""
