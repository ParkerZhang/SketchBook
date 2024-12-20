venvstack:https://lmstudio.ai/blog/venvstacks
https://coda.io/@peter-sigurdson/lm-studio-is-a-powerful-tool-for-creating-and-managing-language-

Fine tune data model
	https://www.youtube.com/watch?v=pxhkDaKzBaY

Dataset used: https://huggingface.co/datasets/grete...
Ollama: https://github.com/ollama/ollama
Unsloth: https://github.com/unslothai/unsloth

		pip install datasets
		python
			
			from datasets import load_dataset
			dataset = load_dataset("gretelai/synthetic_text_to_sql")
			train_data = dataset['train']
			test_data = dataset['test']

			# Display the first few records in the training dataset
			print(train_data[0])
