import whisper
model=whisper.load_model("base")
result=model.transcribe("1940-06-04_BBC_Winston_Churchill_We_Shall_Never_Surrender.mp3")
print(result["text"])

