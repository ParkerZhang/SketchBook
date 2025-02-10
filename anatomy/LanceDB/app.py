from langchain_community.embeddings import OllamaEmbeddings
import lancedb
from langchain.vectorstores import LanceDB
from langchain.chains import RetrievalQA
from langchain_ollama import OllamaEmbeddings
from langchain_ollama import OllamaLLM


doc = fitz.open("DS.pdf")
texts, metadatas = [], []
for page_num in range(len(doc)):
    page = doc.load_page(page_num)
    texts.append(page.get_text())
    metadatas.append(doc.metadata)


text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
document=text_splitter.create_documents(texts,metadatas=metadatas)
embeddings = OllamaEmbeddings(model="deepseek-r1:14b")
db = lancedb.connect("/tmp/lancedb")
vectorstore = LanceDB.from_documents(document, embeddings, connection=db)

#for page_num in range(len(doc)):
#    page = doc.load_page(page_num)
#    texts.extend(page.get_text().split("\n"))
#text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
#vectorstore = LanceDB.from_texts(texts=texts, embedding=embeddings, connection=db)


llm = OllamaLLM(model="deepseek-r1:14b")
retriever = vectorstore.as_retriever(search_type="similarity", search_kwargs={"k": 4})
qa = RetrievalQA.from_chain_type(llm=llm, chain_type="stuff", retriever=retriever)
query = "What are the main topics discussed in the documents?"
result = qa.invoke(query)
print(result)