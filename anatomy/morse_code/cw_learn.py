import librosa
import numpy as np
from sklearn.cluster import KMeans

MORSE_CODE_DICT = {
    '.-': 'A', '-...': 'B', '-.-.': 'C', '-..': 'D', '.': 'E',
    '..-.': 'F', '--.': 'G', '....': 'H', '..': 'I', '.---': 'J',
    '-.-': 'K', '.-..': 'L', '--': 'M', '-.': 'N', '---': 'O',
    '.--.': 'P', '--.-': 'Q', '.-.': 'R', '...': 'S', '-': 'T',
    '..-': 'U', '...-': 'V', '.--': 'W', '-..-': 'X', '-.--': 'Y',
    '--..': 'Z', '-----': '0', '.----': '1', '..---': '2', '...--': '3',
    '....-': '4', '.....': '5', '-....': '6', '--...': '7', '---..': '8',
    '----.': '9', '.-.-.-': '.', '--..--': ',', '..--..': '?', '-.-.--': '!',
    '.-..-.': '"', '.----..': "'", '-....-': '-', '-..-.': '/', '-.--.': '(',
    '-.--.-': ')', '.-...': '&', '---...': ':', '-.-.-.': ';', '-...-': '=',
    '.-.-.': '+', '..-.-': '_', '...-..-': '$', '.--.-.': '@', '.-.-': 'AA',
    '...-.-': 'SK', '.-...': 'AR', '-...-...': 'BT', '-.-.-': 'KN'
}

def load_audio(file_path):
    y, sr = librosa.load(file_path, sr=None)
    return y, sr

def detect_tones(y, sr, threshold=0.02):
    frame_length = int(sr * 0.01)  # 10ms frames
    hop_length = frame_length // 2
    rms = librosa.feature.rms(y=y, frame_length=frame_length, hop_length=hop_length)[0]
    tones = (rms > threshold).astype(int)
    return tones, hop_length / sr

def analyze_timing(tones, time_per_frame):
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

def learn_timing(durations):
    tone_durations = [d[1] for d in durations if d[0] == 1]
    silence_durations = [d[1] for d in durations if d[0] == 0 and d[1] > 0.01]
    if not tone_durations or not silence_durations:
        return 0.05, 0.15, 0.15, 0.35
    tone_data = np.array(tone_durations).reshape(-1, 1)
    kmeans_tones = KMeans(n_clusters=2, random_state=0).fit(tone_data)
    dot_length, dash_length = sorted(kmeans_tones.cluster_centers_.flatten())
    silence_data = np.array(silence_durations).reshape(-1, 1)
    kmeans_silences = KMeans(n_clusters=2, random_state=0).fit(silence_data)
    letter_space, word_space = sorted(kmeans_silences.cluster_centers_.flatten())
    return dot_length, dash_length, letter_space, word_space

def classify_morse(durations, dot_length, dash_length, letter_space, word_space):
    morse = []
    current_letter = []
    
    for state, duration in durations:
        if state == 1:
            if duration < (dot_length + dash_length) / 2:
                current_letter.append('.')
            else:
                current_letter.append('-')
        elif state == 0 and current_letter:
            if duration > word_space * 0.75:
                morse.append(' '.join(current_letter))
                morse.append('/')
                current_letter = []
            elif duration > letter_space * 0.75:
                morse.append(' '.join(current_letter))
                current_letter = []
    
    if current_letter:
        morse.append(' '.join(current_letter))
    if morse and morse[-1] == '/':
        morse.pop()
    return morse

def decode_morse(morse):
    text = []
    for symbol in morse:
        if symbol == '/':
            text.append(' ')
        else:
            clean_symbol = ''.join(symbol.split())
            if clean_symbol in MORSE_CODE_DICT:
                text.append(MORSE_CODE_DICT[clean_symbol])
    return ''.join(text)

def extract_morse_from_mp3(file_path):
    y, sr = load_audio(file_path)
    tones, time_per_frame = detect_tones(y, sr)
    durations = analyze_timing(tones, time_per_frame)
    dot_length, dash_length, letter_space, word_space = learn_timing(durations)
    print(f"Learned timing: dot={dot_length:.3f}s, dash={dash_length:.3f}s, "
          f"letter_space={letter_space:.3f}s, word_space={word_space:.3f}s")
    morse = classify_morse(durations, dot_length, dash_length, letter_space, word_space)
    text = decode_morse(morse)
    print("Morse sequence:", ' '.join(morse))
    print("Decoded text:", text)
    return morse, text

if __name__ == "__main__":
    file_path = "temp.wav"
    morse, text = extract_morse_from_mp3(file_path)