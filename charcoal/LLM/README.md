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

Copy https://huggingface.co/ZhongZ/model/blob/main/unsloth.F16.gguf LM Status /home/u/.cache/lm-studio/models/ZhongZ/model

BELLE Train
	https://github.com/LianjiaTech/BELLE/blob/main/train/README_FT.md

1.2.2 LoRA
torchrun --nproc_per_node 8 src/entry_point/sft_train.py \
    --model_name_or_path ${model_name_or_path} \
    --llama \
    --use_lora True \
    --use_int8_training \
    --lora_config configs/lora_config_llama.json \
    --train_file ${train_file} \
    --validation_file ${validation_file} \
    --per_device_train_batch_size 2 \
    --per_device_eval_batch_size 2 \
    --gradient_accumulation_steps 4 \
    --num_train_epochs 2 \
    --model_max_length ${cutoff_len} \
    --save_strategy "steps" \
    --save_total_limit 3 \
    --learning_rate 8e-6 \
    --weight_decay 0.00001 \
    --warmup_ratio 0.05 \
    --lr_scheduler_type "cosine" \
    --logging_steps 10 \
    --evaluation_strategy "steps" \
    --fp16 True \
    --seed 1234 \
    --gradient_checkpointing True \
    --cache_dir ${cache_dir} \
    --output_dir ${output_dir} \
    # --deepspeed configs/deepspeed_config_stage3.json
	
	bash scripts/merge_lora.sh

Deep Seek Run Locally
	https://huggingface.co/deepseek-ai/DeepSeek-V3-Base#6-how-to-run-locally


