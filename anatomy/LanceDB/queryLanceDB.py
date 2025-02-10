from langchain_community.embeddings import OllamaEmbeddings
import lancedb
from langchain.vectorstores import LanceDB
from langchain.chains import RetrievalQA
from langchain_ollama import OllamaEmbeddings
from langchain_ollama import OllamaLLM


embeddings = OllamaEmbeddings(model="deepseek-r1:14b")
db = lancedb.connect("./lancedb")

#table_name=db.table_names()[0] 
#table=db.open_table(table_name)
#print(table.version)
#table.checkout(1)
#print(table.version)

vectorstore = LanceDB(embedding=embeddings, connection=db)
llm = OllamaLLM(model="deepseek-r1:7b")
retriever = vectorstore.as_retriever(search_type="similarity", search_kwargs={"k": 4})
qa = RetrievalQA.from_chain_type(llm=llm, chain_type="stuff", retriever=retriever)
query = "What are the main topics discussed in the documents?"
result = qa.invoke(query)
print(result)