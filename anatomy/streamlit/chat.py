import streamlit as st
import ollama

st.title("Hello DeepSeek")

available_models = ollama.list()

# Extract model names (assuming the 'name' key exists in the response)
model_names = [model.model  for model in available_models.models]

# Add a selectbox for model selection
selected_model = st.selectbox("Select a model", model_names)

user_input = st.text_area("Enter your prompt")

if st.button("Submit"):
    if selected_model:  # Check if a model is selected
        with st.spinner("Generating response..."):
            try:
                response = ollama.generate(model=selected_model, prompt=user_input)
                st.write(response['response'])  # Access the 'response' key
            except Exception as e:  # Handle potential errors
                st.error(f"Error generating response: {e}")
    else:
        st.warning("Please select a model.")
