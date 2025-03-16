import os
import csv
from langchain_core.documents import Document
from langchain_experimental.graph_transformers import LLMGraphTransformer
from langchain_ollama import OllamaLLM
import kuzu

# Define allowed nodes and relationships for the graph
allowed_nodes = ["Instrument", "Exchange", "Country", "Currency"]
allowed_relationships = [
    "TRADED_ON",  # Instrument traded on Exchange
    "ISSUED_IN",  # Instrument issued in Country
    "DENOMINATED_IN",  # Instrument denominated in Currency
    "RISK_IN",  # Instrument has risk in Country
    "DOMICILED_IN"  # Instrument domiciled in Country
]

# Initialize the local Ollama LLM
llm = OllamaLLM(
    model="deepseek-r1:7b",  # Replace with your preferred model (e.g., "mistral")
    base_url="http://localhost:11434",  # Default Ollama server URL
    temperature=0,  # Deterministic output
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
print(text)
# Create a LangChain Document from the text
documents = [Document(page_content=text)]

# Convert text to graph documents
graph_documents = llm_transformer.convert_to_graph_documents(documents)
print(graph_documents)
# Print the resulting graph structure
print("Nodes:")
for node in graph_documents[0].nodes:
    print(f" - {node.id} ({node.type})")

print("\nRelationships:")
for rel in graph_documents[0].relationships:
    print(f" - {rel.source.id} -> {rel.type} -> {rel.target.id}")

# Connect to Kùzu and store the graph
db_path = "./kuzu_db"  # Directory for Kùzu database (will be created if it doesn’t exist)
if not os.path.exists(db_path):
    os.makedirs(db_path)

# Initialize Kùzu database and connection
db = kuzu.Database(db_path)
conn = kuzu.Connection(db)

# Define schema
conn.execute("CREATE NODE TABLE Instrument(name STRING, ticker STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Exchange(name STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Country(name STRING, PRIMARY KEY(name))")
conn.execute("CREATE NODE TABLE Currency(code STRING, PRIMARY KEY(code))")
conn.execute("CREATE REL TABLE TRADED_ON(FROM Instrument TO Exchange)")
conn.execute("CREATE REL TABLE ISSUED_IN(FROM Instrument TO Country)")
conn.execute("CREATE REL TABLE DENOMINATED_IN(FROM Instrument TO Currency)")
conn.execute("CREATE REL TABLE RISK_IN(FROM Instrument TO Country)")
conn.execute("CREATE REL TABLE DOMICILED_IN(FROM Instrument TO Country)")

# Insert nodes
for node in graph_documents[0].nodes:
    if node.type == "Instrument":
        ticker = node.properties.get('ticker', '')  # Safely get ticker if it exists
        conn.execute(f"CREATE (i:Instrument {{name: '{node.id}', ticker: '{ticker}'}})")
    elif node.type == "Exchange":
        conn.execute(f"CREATE (e:Exchange {{name: '{node.id}'}})")
    elif node.type == "Country":
        conn.execute(f"CREATE (c:Country {{name: '{node.id}'}})")
    elif node.type == "Currency":
        conn.execute(f"CREATE (c:Currency {{code: '{node.id}'}})")

# Insert relationships
for rel in graph_documents[0].relationships:
    # Handle cases where node types might differ in schema (e.g., Currency uses 'code' not 'name')
    target_key = 'code' if rel.target.type == "Currency" else 'name'
    conn.execute(
        f"MATCH (source:{rel.source.type} {{name: '{rel.source.id}'}}), "
        f"(target:{rel.target.type} {{{target_key}: '{rel.target.id}'}}) "
        f"CREATE (source)-[:{rel.type}]->(target)"
    )

# Verify the data in Kùzu (example query)
result = conn.execute("MATCH (i:Instrument)-[r:TRADED_ON]->(e:Exchange) RETURN i.name, e.name LIMIT 5")
print("\nSample Kùzu Query Result (Instrument -> TRADED_ON -> Exchange):")
while result.has_next():
    row = result.get_next()
    print(f" - {row[0]} traded on {row[1]}")
