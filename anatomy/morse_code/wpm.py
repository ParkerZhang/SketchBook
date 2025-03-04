from pydub import AudioSegment
from scipy.signal import find_peaks
import numpy as np
import sys

def estimate_wpm(peaks, frame_rate):
    """Estimate WPM based on detected Morse code peaks."""
    if len(peaks) < 2:
        return 0  # Not enough data to estimate
    
    # Calculate dot duration as the shortest peak interval
    peak_diffs = np.diff(peaks) / frame_rate  # Convert to seconds
    dot_duration = np.min(peak_diffs)  # Smallest detected unit

    # Standard formula: WPM = 1.2 / dot duration (in seconds)
    wpm = 1.2 / dot_duration
    return round(wpm, 2)

def process_audio(file_path):
    """Process the audio file and detect WPM in every 20-second segment."""
    audio = AudioSegment.from_file(file_path)
    audio = audio.set_channels(1)  # Ensure mono channel
    samples = np.array(audio.get_array_of_samples())

    segment_duration_ms = 20000  # 20 seconds in milliseconds
    num_segments = len(audio) // segment_duration_ms

    wpm_results = []
    for i in range(num_segments):
        start = i * segment_duration_ms
        end = start + segment_duration_ms
        segment = audio[start:end]

        # Convert segment to samples and detect peaks
        samples_segment = np.array(segment.get_array_of_samples())
        abs_samples_segment = np.abs(samples_segment) / np.max(np.abs(samples_segment))
        peaks_segment, _ = find_peaks(abs_samples_segment, height=0.5, distance=200)

        # Estimate WPM for the segment
        wpm = estimate_wpm(peaks_segment, audio.frame_rate)
        wpm_results.append((i * 20, wpm))  # Store (time in seconds, WPM)

    # Print detected WPM values
    for time_sec, wpm in wpm_results:
        print(f"Time: {time_sec}s - WPM: {wpm}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python script.py <audio_file>")
    else:
        process_audio(sys.argv[1])
