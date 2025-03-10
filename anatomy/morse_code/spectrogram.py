#!/usr/bin/env python3
# requirements:
#   librosa==0.9.2
#   numpy
#   matplotlib
#   pathlib
#   soundfile
from pathlib import Path
import librosa
import librosa.display
import numpy as np
import matplotlib.pyplot as plt
import soundfile as sf

IMG_OUTPUT_PATH = Path("img")
SAVE_PARAMS = {"dpi": 300, "bbox_inches": "tight", "transparent": True}

TICKS = np.array([31.25, 62.5, 125, 250, 500, 1000, 2000, 4000, 8000])
TICK_LABELS = np.array(["31.25", "62.5", "125", "250", "500", "1k", "2k", "4k", "8k"])


def plot_spectrogram_and_save(
    signal, fs, output_path: Path, fft_size=2048, hop_size=None, window_size=None
):
    # default values taken from the librosa documentation
    if not window_size:
        window_size = fft_size

    if not hop_size:
        hop_size = window_size // 4

    stft = librosa.stft(
        signal,
        n_fft=fft_size,
        hop_length=hop_size,
        win_length=window_size,
        center=False,
    )
    spectrogram = np.abs(stft)
    spectrogram_db = librosa.amplitude_to_db(spectrogram, ref=np.max)

    plt.figure(figsize=(10, 4))
    img = librosa.display.specshow(
        spectrogram_db,
        y_axis="log",
        x_axis="time",
        sr=fs,
        hop_length=hop_size,
        cmap="inferno",
    )
    plt.xlabel("Time [s]")
    plt.ylabel("Frequency [Hz]")
    plt.yticks(TICKS, TICK_LABELS)
    plt.colorbar(img, format="%+2.f dBFS")

    # plt.show()
    output_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(
        output_path.with_stem(
            f"{output_path.stem}_spectrogram_WINLEN={window_size}_HOPLEN={hop_size}_NFFT={fft_size}"
        ),
        **SAVE_PARAMS,
    )
    plt.close()


def main():
    plt.rcParams.update({"font.size": 20})
    #signal, sample_rate = sf.read(Path("data") / "LibriSpeech-84-121123-0001.flac")
    signal, sample_rate = sf.read("temp.wav")
    print(f"Signal's sample rate: {sample_rate}")
    plot_spectrogram_and_save(signal, sample_rate, IMG_OUTPUT_PATH / "spectrogram.png")


if __name__ == "__main__":
    main()
