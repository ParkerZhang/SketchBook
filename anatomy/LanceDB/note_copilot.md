Sure, let's create a simple Python app that loads a PDF file and saves it to LanceDB. LanceDB is a vector database that can store embeddings of the PDF content. Here's a step-by-step guide to create the app:

1. **Install Required Libraries**:
   - `PyMuPDF` for handling PDF files.
   - `LanceDB` for storing the data.

```bash
pip install pymupdf lancedb
```

2. **Create the App**:

```python
import fitz  # PyMuPDF
import lancedb

# Function to load PDF and extract text
def load_pdf(file_path):
    doc = fitz.open(file_path)
    text = ""
    for page_num in range(len(doc)):
        page = doc.load_page(page_num)
        text += page.get_text()
    return text

# Function to save text to LanceDB
def save_to_lancedb(text, db_path, table_name):
    db = lancedb.connect(db_path)
    table = db.table(table_name)
    table.insert({"text": text})

# Main function
def main(pdf_path, db_path, table_name):
    pdf_text = load_pdf(pdf_path)
    save_to_lancedb(pdf_text, db_path, table_name)

# Example usage
if __name__ == "__main__":
    main("example.pdf", "lancedb.db", "pdf_table")
```

### Explanation:
- **Loading PDF**: The `load_pdf` function uses `PyMuPDF` to open and read the text from each page of the PDF.
- **Saving to LanceDB**: The `save_to_lancedb` function connects to a LanceDB database and inserts the extracted text into a specified table.

Feel free to modify the code to suit your needs. If you have any questions or need further customization, let me know!