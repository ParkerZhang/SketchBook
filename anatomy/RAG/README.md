### What is RAG?

**RAG (Retrieval-Augmented Generation)** is a framework that combines **retrieval-based methods** with **generative models** to improve the performance of language models, especially for tasks that require access to external knowledge or factual accuracy. It was introduced by Facebook AI Research (FAIR) in 2020.

In traditional language models (like GPT), the model generates responses based solely on the patterns it learned during training. While this works well for many tasks, it can struggle with tasks requiring up-to-date, domain-specific, or factual knowledge. RAG addresses this limitation by integrating a **retrieval mechanism** that fetches relevant information from an external knowledge source (e.g., a document database) and then uses a generative model to produce a response based on the retrieved information.

---

### How RAG Works

RAG combines two main components:

1. **Retriever**:
   - The retriever is responsible for fetching relevant documents or passages from a large corpus of text (e.g., Wikipedia, a custom knowledge base, or a document store).
   - It typically uses a dense retrieval method, such as **FAISS** (Facebook AI Similarity Search), which leverages vector embeddings to find semantically similar documents.
   - The retriever encodes both the query and the documents into vector embeddings and retrieves the most relevant documents based on similarity scores.

2. **Generator**:
   - The generator is a pre-trained language model (e.g., GPT, T5, or BART) that takes the retrieved documents and the query as input and generates a coherent and contextually relevant response.
   - The generator is conditioned on both the query and the retrieved documents, allowing it to produce more accurate and informed responses.

---

### How RAG Works with LLMs

RAG enhances the capabilities of large language models (LLMs) by providing them with access to external knowledge. Here's how it works step-by-step:

1. **Input Query**:
   - The user provides a query (e.g., "What is the capital of France?").

2. **Retrieval**:
   - The query is passed to the retriever, which searches a large document corpus for relevant information.
   - The retriever uses vector embeddings to find the most semantically similar documents or passages.

3. **Context Formation**:
   - The retrieved documents are combined with the original query to form a context.
   - This context is then passed to the generator.

4. **Generation**:
   - The generator (an LLM) processes the query and the retrieved context to produce a response.
   - The response is informed by both the LLM's pre-trained knowledge and the specific information retrieved from the external source.

5. **Output**:
   - The final response is returned to the user.

---

### Why RAG is Useful with LLMs

1. **Factual Accuracy**:
   - LLMs can sometimes generate incorrect or outdated information. RAG ensures that the model has access to accurate and up-to-date information from a reliable source.

2. **Domain-Specific Knowledge**:
   - LLMs trained on general corpora may lack domain-specific knowledge. RAG allows the model to retrieve and use specialized information from a custom knowledge base.

3. **Scalability**:
   - Instead of fine-tuning an LLM on a large dataset, RAG allows you to keep the LLM general-purpose and augment it with a retrieval mechanism for specific tasks.

4. **Transparency**:
   - Since the retrieved documents are used to generate the response, it is easier to trace the source of the information, making the system more interpretable.

---

### Example of RAG in Action

#### Query:
"What is the capital of France?"

#### Retrieval:
- The retriever searches a document corpus and retrieves the following passage:
  - "France is a country in Europe. Its capital is Paris."

#### Generation:
- The generator (LLM) takes the query and the retrieved passage as input and produces the response:
  - "The capital of France is Paris."

---

### Tools and Frameworks for RAG

1. **Hugging Face Transformers**:
   - Hugging Face provides pre-trained models and tools for building RAG systems. The `RagTokenizer` and `RagRetriever` are specifically designed for this purpose.

2. **FAISS**:
   - FAISS is a library for efficient similarity search, commonly used for retrieving documents in RAG systems.

3. **Ollama**:
   - Ollama allows you to run LLMs locally, which can be used as the generator in a RAG system.

4. **Haystack**:
   - Haystack is an open-source framework for building search systems and RAG pipelines.

---

### Example RAG Pipeline with Ollama

Here’s a high-level example of how you can build a RAG pipeline with Ollama:

1. **Set Up Ollama**:
   - Run Ollama locally and load a language model (e.g., GPT-2 or LLaMA).

2. **Set Up a Retriever**:
   - Use FAISS to index a document corpus and retrieve relevant documents.

3. **Combine Retrieval and Generation**:
   - Pass the retrieved documents and the query to Ollama for generation.

4. **Output the Response**:
   - Return the generated response to the user.

---

### Summary

RAG is a powerful framework that combines the strengths of retrieval-based systems and generative models. By integrating external knowledge sources, RAG enhances the accuracy, relevance, and domain-specific capabilities of LLMs. Tools like Ollama, FAISS, and Hugging Face Transformers make it easier to build and deploy RAG systems for various applications.

### Handson Lab
Building a small Retrieval-Augmented Generation (RAG) app with Ollama involves several steps. Ollama is a tool that allows you to run large language models locally, which can be useful for building RAG applications. Below is a step-by-step guide to creating a simple RAG app using Ollama.

### Prerequisites

1. **Ollama**: Ensure you have Ollama installed and running on your machine. You can download it from the [Ollama GitHub repository](https://github.com/jmorganca/ollama).

2. **Python**: Make sure you have Python installed. This guide assumes you have Python 3.7 or later.

3. **FAISS**: We'll use Facebook's FAISS library for efficient similarity search. Install it using pip:
   ```bash
   pip install faiss-cpu
   ```

4. **Transformers**: Install the Hugging Face Transformers library:
   ```bash
   pip install transformers
   ```

### Step 1: Set Up Ollama

First, ensure that Ollama is running and you have a language model loaded. For example, you can load a model like GPT-2 or any other model supported by Ollama.

```bash
ollama serve
```

### Step 2: Create a Simple RAG App

Now, let's create a simple RAG app in Python. This app will use Ollama for generation and FAISS for retrieval.

#### 1. Install Required Libraries

```bash
pip install ollama faiss-cpu transformers
```

#### 2. Create the Python Script

Create a file named `rag_app.py` and add the following code:

```python
import ollama
import faiss
import numpy as np
from transformers import AutoTokenizer, AutoModel

# Load a pre-trained model and tokenizer for embedding
tokenizer = AutoTokenizer.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')
model = AutoModel.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')

# Function to get embeddings
def get_embedding(text):
    inputs = tokenizer(text, return_tensors='pt', truncation=True, padding=True)
    outputs = model(**inputs)
    return outputs.last_hidden_state.mean(dim=1).detach().numpy()

# Sample documents for retrieval
documents = [
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
    response = ollama.generate(prompt)
    return response

# Example usage
query = "What is Python?"
response = generate_response(query)
print("Response:", response)
```

### Step 3: Run the App

Run the app using Python:

```bash
python rag_app.py
```

### Explanation

1. **Embedding Generation**: We use a pre-trained model (`sentence-transformers/all-MiniLM-L6-v2`) to generate embeddings for the documents and the query.

2. **FAISS Index**: We create a FAISS index to store the document embeddings and perform efficient similarity search.

3. **Retrieval**: Given a query, we retrieve the most relevant documents using FAISS.

4. **Generation**: We use Ollama to generate a response based on the retrieved documents and the query.

### Customization

- **Documents**: You can replace the sample documents with your own dataset.
- **Model**: You can use different models for embedding and generation depending on your needs.
- **Ollama Configuration**: You can configure Ollama to use different models or settings.

This is a basic example to get you started. Depending on your use case, you might want to add more features like handling larger datasets, optimizing the retrieval process, or improving the generation quality.

### 9133
query = "Who is famous in 9133?"
 
Response: model='deepseek-r1:1.5b' created_at='2025-02-02T12:58:20.214038764Z' done=True done_reason='stop' total_duration=16447194489 load_duration=18658093 prompt_eval_count=45 prompt_eval_duration=326000000 eval_count=563 eval_duration=16101000000 response='<think>\nOkay, so I have this question about who is famous in SJTU\'s 9133 class. Let me try to figure this out step by step.\n\nFirst, the context given includes two statements:\n\n1. "SJTU had 9133 class, Lao Hu is very active in the class."\n2. The cat sat on the mat.\n\nI\'m trying to understand what each of these says about SJTU and Lao Hu. Let\'s break them down one by one.\n\nStarting with the first statement: "SJTU had 9133 class." That probably means SJTU is an institution, maybe a university or college, that has a specific class number, which is 9133 in this case. The second part says, "Lao Hu is very active in the class." So it seems like Lao Hu is a student who\'s pretty involved or focused in that particular class.\n\nNow looking at the second statement: "The cat sat on the mat." That sounds pretty silly and humorous because cats sitting on mats doesn\'t make sense. Maybe this is an example of sarcasm, where someone mocks another person\'s behavior with a humorous twist.\n\nSo putting these together, it seems like Lao Hu is mentioned in the context of SJTU\'s 9133 class as someone who\'s very active or involved there. The cat on the mat could be a joke that\'s meant to be funny but probably doesn\'t relate directly to the first statement about Lao Hu.\n\nThe question asks, "Who is famous in 9133?" So I need to focus on SJTU\'s 9133 class and identify who is famous there. But from the given context, the only mention of a person is Lao Hu in relation to being active in that class. The cat on the mat doesn\'t seem related.\n\nTherefore, based on the information provided, it looks like Lao Hu is the person mentioned as being very active or involved in SJTU\'s 9133 class, and he might be considered famous for his involvement or dedication there.\n\nWait, but I\'m not sure if "famous" is even relevant here. Maybe the question is more about who is the principal or the instructor of the class? But since that information isn\'t given in the context, it\'s hard to say. The only person mentioned is Lao Hu.\n\nSo maybe the answer is that Lao Hu is famous because he\'s very active in the class, which could indicate he\'s an engaging and respected member of SJTU\'s 9133 group.\n</think>\n\nLao Hu is famous for being very active in SJTU\'s 9133 class, demonstrating a level of engagement and respect within the institution.' context=[151644, 1972, 25, 68223, 98582, 1030, 220, 24, 16, 18, 18, 536, 11, 444, 3441, 21793, 374, 1602, 4541, 304, 279, 536, 785, 8251, 7578, 389, 279, 5517, 382, 14582, 25, 10479, 374, 11245, 304, 220, 24, 16, 18, 18, 1939, 16141, 25, 151645, 151648, 198, 32313, 11, 773, 358, 614, 419, 3405, 911, 879, 374, 11245, 304, 68223, 98582, 594, 220, 24, 16, 18, 18, 536, 13, 6771, 752, 1430, 311, 7071, 419, 700, 3019, 553, 3019, 382, 5338, 11, 279, 2266, 2661, 5646, 1378, 12239, 1447, 16, 13, 330, 50, 81181, 52, 1030, 220, 24, 16, 18, 18, 536, 11, 444, 3441, 21793, 374, 1602, 4541, 304, 279, 536, 10040, 17, 13, 576, 8251, 7578, 389, 279, 5517, 382, 40, 2776, 4460, 311, 3535, 1128, 1817, 315, 1493, 2727, 911, 68223, 98582, 323, 444, 3441, 21793, 13, 6771, 594, 1438, 1105, 1495, 825, 553, 825, 382, 24617, 448, 279, 1156, 5114, 25, 330, 50, 81181, 52, 1030, 220, 24, 16, 18, 18, 536, 1189, 2938, 4658, 3363, 68223, 98582, 374, 458, 14898, 11, 7196, 264, 12103, 476, 7770, 11, 429, 702, 264, 3151, 536, 1372, 11, 892, 374, 220, 24, 16, 18, 18, 304, 419, 1142, 13, 576, 2086, 949, 2727, 11, 330, 43, 3441, 21793, 374, 1602, 4541, 304, 279, 536, 1189, 2055, 432, 4977, 1075, 444, 3441, 21793, 374, 264, 5458, 879, 594, 5020, 6398, 476, 10735, 304, 429, 3953, 536, 382, 7039, 3330, 518, 279, 2086, 5114, 25, 330, 785, 8251, 7578, 389, 279, 5517, 1189, 2938, 10362, 5020, 29471, 323, 69846, 1576, 19423, 11699, 389, 61056, 3171, 944, 1281, 5530, 13, 10696, 419, 374, 458, 3110, 315, 78766, 10530, 11, 1380, 4325, 68909, 2441, 1697, 594, 7709, 448, 264, 69846, 26646, 382, 4416, 10687, 1493, 3786, 11, 432, 4977, 1075, 444, 3441, 21793, 374, 9733, 304, 279, 2266, 315, 68223, 98582, 594, 220, 24, 16, 18, 18, 536, 438, 4325, 879, 594, 1602, 4541, 476, 6398, 1052, 13, 576, 8251, 389, 279, 5517, 1410, 387, 264, 21646, 429, 594, 8791, 311, 387, 15173, 714, 4658, 3171, 944, 28143, 5961, 311, 279, 1156, 5114, 911, 444, 3441, 21793, 382, 785, 3405, 17064, 11, 330, 15191, 374, 11245, 304, 220, 24, 16, 18, 18, 7521, 2055, 358, 1184, 311, 5244, 389, 68223, 98582, 594, 220, 24, 16, 18, 18, 536, 323, 10542, 879, 374, 11245, 1052, 13, 1988, 504, 279, 2661, 2266, 11, 279, 1172, 6286, 315, 264, 1697, 374, 444, 3441, 21793, 304, 12687, 311, 1660, 4541, 304, 429, 536, 13, 576, 8251, 389, 279, 5517, 3171, 944, 2803, 5435, 382, 54815, 11, 3118, 389, 279, 1995, 3897, 11, 432, 5868, 1075, 444, 3441, 21793, 374, 279, 1697, 9733, 438, 1660, 1602, 4541, 476, 6398, 304, 68223, 98582, 594, 220, 24, 16, 18, 18, 536, 11, 323, 566, 2578, 387, 6509, 11245, 369, 806, 21587, 476, 38855, 1052, 382, 14190, 11, 714, 358, 2776, 537, 2704, 421, 330, 69, 22517, 1, 374, 1496, 9760, 1588, 13, 10696, 279, 3405, 374, 803, 911, 879, 374, 279, 12435, 476, 279, 32215, 315, 279, 536, 30, 1988, 2474, 429, 1995, 4436, 944, 2661, 304, 279, 2266, 11, 432, 594, 2588, 311, 1977, 13, 576, 1172, 1697, 9733, 374, 444, 3441, 21793, 382, 4416, 7196, 279, 4226, 374, 429, 444, 3441, 21793, 374, 11245, 1576, 566, 594, 1602, 4541, 304, 279, 536, 11, 892, 1410, 13216, 566, 594, 458, 22570, 323, 30287, 4462, 315, 68223, 98582, 594, 220, 24, 16, 18, 18, 1874, 624, 151649, 271, 43, 3441, 21793, 374, 11245, 369, 1660, 1602, 4541, 304, 68223, 98582, 594, 220, 24, 16, 18, 18, 536, 11, 44196, 264, 2188, 315, 19805, 323, 5091, 2878, 279, 14898, 13]
