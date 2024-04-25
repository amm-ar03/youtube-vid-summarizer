import os
import shutil
import librosa
import soundfile as sf
import youtube_dl
import openai
from youtube_dl.utils import DownloadError

api_key=os.environ.get("openai") #in here you generally enter the API key

#checks for audio files ending in .mp3 & adds it to your folder
def get_audio_files(path, extension=".mp3"):
    audio_files = []
    for root, dirs, files in os.walk(path):
        for f in files:
            if f.endswith(extension):
                audio_files.append(os.path.join(root, f))

    return audio_files

def youtube_mp3(youtube_url: str, output_dir: str ) -> str:
    ydl_config = {
        "format" : "bestaudio/best",
        "postprocessors": [
            {
                "key": "FFmpegExtractAudio",
                "preferredcodec": "mp3",
                "preferredquality": "192",
            }
        ],
        "outtmp1": os.path.join(output_dir, "%(title)s.%(ext)s"),
        "verbose": True,
    
    }

    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    print(f"Downloading video from {youtube_url}")

    try:
        with youtube_dl.YoutubeDL(ydl_config) as ydl:
            ydl.download([youtube_url])
    except DownloadError:
        with youtube_dl.YoutubeDL(ydl_config) as ydl:
            ydl.download([youtube_url])

    audio_filename = get_audio_files(output_dir)[0]
    return audio_filename

#splits the audio into pieces to make transcribing easier
def chunk_audio(filename, segment_length: int, output_dir):

    print(f"Chunking audio to {segment_length} second segments...")

    if not os.path.isdir(output_dir):
        os.mkdir(output_dir)

    audio, sr = librosa.load(filename, sr = 44100)

    duration = librosa.get_duration(y=audio, sr=sr)

    num_segments = int(duration / segment_length) + 1

    print(f"chunking {num_segments} chunks...")

    for i in range(num_segments):
        start = i * segment_length * sr
        end = (i + 1) * segment_length * sr
        segment = audio[start:end]
        sf.write(os.path.join(output_dir, f"segment_{i}.mp3"), segment, sr)
        chunked_audio_files = get_audio_files(output_dir)
        return sorted(chunked_audio_files)
    
#using the whisper model, we convert the audio(.mp3) into text   
def transcribe_audio(audio_files: list, output_file=None, model = "whisper-1") -> list:

    print("converting audio to text")

    transcript = []
    for audio_files in audio_files:
        audio = open(audio_files, "rb")
        response = openai.Audio.transcribe(model, audio)
        transcript.append(response["text"])

    if output_file is not None:
        with open(output_file, "w") as file:
            for transcripts in transcript:

                file.write(transcripts + "\n")
                
    return transcript

#this function helps us later on to summarize the transcribed audio using ChatGPT
def summarize_yt(chunks: list[str], system_prompt: str, model="gpt-3.5-turbo", output_file=None):

    print(f"Summarizing with {model=}")

    summaries = []
    for chunk in chunks:
        response = openai.ChatCompletion.create(
            model = model,
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": chunk},
            ],
        )
        summary = response["choices"][0]["messages"]["content"]
        summaries.append(summary)

    if output_file is not None:
        with open(output_file, "w") as file:
            for summary in summaries:
                file.write(summary + "\n")

    return summaries

#finally bringing everything together, we specify paths for different files
#we use in this project. pre-loaded prompts to ChatGPT to summarize the text for us
#into a long summary and a short summary
def summary_yt(youtube_url, output_dir):
    raw_audio_dir = f"{output_dir}/raw_audio/"
    chunks_dir = f"{output_dir}/chunks_audio"
    transcripts_file = f"{output_dir}/transcripts.txt"
    summary_file = f"{output_dir}/summary/txt"
    segment_length = 10 * 60

    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)
        os.mkdir(output_dir)

    audio_filename = youtube_mp3(youtube_url, output_dir=raw_audio_dir)

    chunked_audio_files = chunk_audio(
        audio_filename, segment_length=segment_length, output_dir=chunks_dir
    )

    transcriptions = transcribe_audio(chunked_audio_files, transcripts_file)

    system_prompt = """
    You are provided with chunbks of raw audio that is transcrbed from a videos audio.
    Summarize the current chunks to clear bullet points.
    """

    summaries = summarize_yt(
        transcriptions, system_prompt = system_prompt, output_file = summary_file
    )

    system_prompt_tldr = """
    Someone has already summarized the key points, summarise it even more to make it shorter
"""

    long_summary = '\n'.join(summaries)
    short_summary = summaries(
        [long_summary], system_prompt=system_prompt_tldr, output_file=summary_file
    )[0]

    return long_summary, short_summary

youtube_url = "https://www.youtube.com/watch?v=GwT6gGMRr9s"
output_dir = "output/"

long_summary, short_summary = summary_yt(youtube_url, output_dir)
print("Long Summary: " + long_summary)
print("Short Summary: " + short_summary)