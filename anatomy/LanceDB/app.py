import fitz
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.embeddings import OllamaEmbeddings
from langchain.vectorstores import LanceDB
from langchain.chains import RetrievalQA
from langchain_ollama import OllamaEmbeddings
from langchain_ollama import OllamaLLM

doc = fitz.open("DS.pdf")
texts, metadatas = [], []
text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
document=text_splitter.create_documents(texts,metadatas=metadatas)
embeddings = OllamaEmbeddings(model="deepseek-r1:1.5b")
db = lancedb.connect("/tmp/lancedb")
vectorstore = LanceDB.from_documents(document, embeddings, connection=db)
llm = OllamaLLM(model="deepseek-r1:1.5b")
retriever = vectorstore.as_retriever(search_type="similarity", search_kwargs={"k": 4})
qa = RetrievalQA.from_chain_type(llm=llm, chain_type="stuff", retriever=retriever)
query = "What are the main topics discussed in the documents?"
result = qa.invoke(query)
print(result)