import ollama
import faiss
import numpy as np
from transformers import AutoTokenizer, AutoModel

# Load a pre-trained model and tokenizer for embedding
tokenizer = AutoTokenizer.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')
model = AutoModel.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')
o_model = 'deepseek-r1:1.5b'
# Function to get embeddings
def get_embedding(text):
    inputs = tokenizer(text, return_tensors='pt', truncation=True, padding=True)
    outputs = model(**inputs)
    return outputs.last_hidden_state.mean(dim=1).detach().numpy()

# Sample documents for retrieval
documents = [
    "SJTU had 9133 class, Lao Hu is very active in the class",
    "Bird Man studied in 33174 class, he is always a deep thinker when he was in SJTU or graduated from the university.",
    "The cat sat on the mat.",
    "Dogs are great companions.",
    "The sun rises in the east.",
    "Python is a popular programming language.",
    "The Eiffel Tower is in Paris."
]

# Create FAISS index
dimension = 384  # Dimension of the embeddings
index = faiss.IndexFlatL2(dimension)

# Add document embeddings to the index
document_embeddings = np.vstack([get_embedding(doc) for doc in documents])
index.add(document_embeddings)

# Function to retrieve the most relevant document
def retrieve_document(query, k=1):
    query_embedding = get_embedding(query)
    distances, indices = index.search(query_embedding, k)
    return [documents[i] for i in indices[0]]

# Function to generate a response using Ollama
def generate_response(query):
    retrieved_docs = retrieve_document(query)
    context = " ".join(retrieved_docs)
    prompt = f"Context: {context}\n\nQuestion: {query}\n\nAnswer:"
    response = ollama.generate(model=o_model,prompt=prompt)
    return response

# Example usage
query = "Who is the smart guy in 33174?"
response = generate_response(query)
print("Response:", response['response'])
