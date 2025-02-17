## Following GAIR-NLP
## Dataset 
	GAIR/OlympicArena

```python
from datasets import load_dataset
dataset=load_dataset("GAIR/OlympicArena", "Math", split="val")

print(dataset[0])
```

Load all datasets
```python
from datasets import load_dataset, get_dataset_config_names

# Replace 'dataset_name' with the name of the dataset you're interested in
dataset_name = "GAIR/OlympicArena"

# List all configurations of the dataset
configs = get_dataset_config_names(dataset_name)
print("Available configurations:", configs)

# Iterate through available configurations
for config_name in configs:
	print(f"Config: {config_name}")
	# Load each configuration and its splits
	
	dataset_split = load_dataset(dataset_name, config_name)
	for split in dataset_split:
		print(f"  Split: {split}")
		load_dataset(dataset_name,config_name)


```

