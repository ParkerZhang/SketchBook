import librosa
import numpy as np

# Morse code dictionary
MORSE_CODE_DICT = {
    '.-': 'A', '-...': 'B', '-.-.': 'C', '-..': 'D', '.': 'E',
    '..-.': 'F', '--.': 'G', '....': 'H', '..': 'I', '.---': 'J',
    '-.-': 'K', '.-..': 'L', '--': 'M', '-.': 'N', '---': 'O',
    '.--.': 'P', '--.-': 'Q', '.-.': 'R', '...': 'S', '-': 'T',
    '..-': 'U', '...-': 'V', '.--': 'W', '-..-': 'X', '-.--': 'Y',
    '--..': 'Z', '-----': '0', '.----': '1', '..---': '2', '...--': '3',
    '....-': '4', '.....': '5', '-....': '6', '--...': '7', '---..': '8',
    '----.': '9'
}

def load_audio(file_path):
    # Load MP3 file, return samples and sample rate
    y, sr = librosa.load(file_path, sr=None)
    return y, sr

def detect_tones(y, sr, threshold=0.02):
    # Compute RMS energy to detect sound vs silence
    frame_length = int(sr * 0.01)  # 10ms frames
    hop_length = frame_length // 2
    rms = librosa.feature.rms(y=y, frame_length=frame_length, hop_length=hop_length)[0]
    
    # Classify frames as tone (1) or silence (0)
    tones = (rms > threshold).astype(int)
    return tones, hop_length / sr  # Time per frame

def analyze_timing(tones, time_per_frame):
    # Convert tone array into durations of tones and silences
    durations = []
    current_duration = 0
    current_state = tones[0]
    
    for tone in tones[1:]:
        if tone == current_state:
            current_duration += time_per_frame
        else:
            durations.append((current_state, current_duration))
            current_duration = time_per_frame
            current_state = tone
    durations.append((current_state, current_duration))
    
    return durations

def classify_morse(durations):
    # Estimate dot length as the shortest tone
    tone_durations = [d[1] for d in durations if d[0] == 1]
    if not tone_durations:
        return []
    dot_length = min(tone_durations)
    dash_length = dot_length * 3
    space_length = dot_length * 3  # Between letters
    word_space = dot_length * 7
    
    # Classify each duration
    morse = []
    current_letter = []
    
    for state, duration in durations:
        if state == 1:  # Tone
            if duration < dash_length * 0.75:
                current_letter.append('.')
            else:
                current_letter.append('-')
        elif state == 0 and current_letter:  # Silence after a tone
            if duration > word_space * 0.75:
                morse.append(''.join(current_letter))
                morse.append(' ')  # Word break
                current_letter = []
            elif duration > space_length * 0.75:
                morse.append(''.join(current_letter))
                current_letter = []
    
    if current_letter:
        morse.append(''.join(current_letter))
    
    return morse

def decode_morse(morse):
    # Convert Morse sequence to text
    text = []
    for symbol in morse:
        if symbol == ' ':
            text.append(' ')
        elif symbol in MORSE_CODE_DICT:
            text.append(MORSE_CODE_DICT[symbol])
    return ''.join(text)

def extract_morse_from_mp3(file_path):
    # Main function
    y, sr = load_audio(file_path)
    tones, time_per_frame = detect_tones(y, sr)
    durations = analyze_timing(tones, time_per_frame)
    morse = classify_morse(durations)
    text = decode_morse(morse)
    
    print("Morse sequence:", ' '.join(morse))
    print("Decoded text:", text)
    return text

# Example usage
if __name__ == "__main__":
    file_path = "temp.wav"  # Replace with your MP3 file
    extract_morse_from_mp3(file_path)