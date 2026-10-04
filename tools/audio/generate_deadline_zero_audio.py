#!/usr/bin/env python3
"""Generate Deadline: Zero's original deterministic audio pack.

No third-party samples or Python packages are required.
The generated WAV files are build artifacts consumed by Godot's importer.
"""
from __future__ import annotations

import math
import random
import wave
from array import array
from pathlib import Path

SR = 44_100
SEED = 20_261_004
OUT = Path(__file__).resolve().parents[2] / "godot" / "assets" / "audio" / "authored"
OUT.mkdir(parents=True, exist_ok=True)


def clamp(x: float, lo: float = -0.985, hi: float = 0.985) -> float:
    return lo if x < lo else hi if x > hi else x


def softclip(x: float) -> float:
    return math.tanh(x * 1.18) / math.tanh(1.18)


def save_mono(name: str, samples: list[float]) -> None:
    peak = max(1e-9, max(abs(v) for v in samples))
    pcm = array("h", (int(clamp(softclip(v / peak * 0.84)) * 32767.0) for v in samples))
    with wave.open(str(OUT / f"{name}.wav"), "wb") as out:
        out.setnchannels(1)
        out.setsampwidth(2)
        out.setframerate(SR)
        out.writeframes(pcm.tobytes())


def save_stereo(name: str, left: list[float], right: list[float]) -> None:
    peak = max(1e-9, max(max(abs(v) for v in left), max(abs(v) for v in right)))
    pcm = array("h")
    for l, r in zip(left, right):
        pcm.append(int(clamp(softclip(l / peak * 0.80)) * 32767.0))
        pcm.append(int(clamp(softclip(r / peak * 0.80)) * 32767.0))
    with wave.open(str(OUT / f"{name}.wav"), "wb") as out:
        out.setnchannels(2)
        out.setsampwidth(2)
        out.setframerate(SR)
        out.writeframes(pcm.tobytes())


def exp_chirp(f0: float, f1: float, seconds: float, noise_mix: float, body: float, seed: int) -> list[float]:
    rng = random.Random(seed)
    frames = max(128, int(seconds * SR))
    phase = 0.0
    sub_phase = 0.0
    prev_noise = 0.0
    result: list[float] = []
    ratio = max(1e-6, f1 / max(f0, 1e-6))
    for i in range(frames):
        t = i / max(1, frames - 1)
        freq = f0 * (ratio ** t)
        phase += math.tau * freq / SR
        sub_phase += math.tau * max(38.0, freq * 0.43) / SR
        attack = min(1.0, t / 0.012)
        decay = max(0.0, 1.0 - t) ** 2.15
        envelope = attack * decay
        raw_noise = rng.uniform(-1.0, 1.0)
        bright_noise = raw_noise - prev_noise * 0.72
        prev_noise = raw_noise
        transient = bright_noise * (max(0.0, 1.0 - t / 0.18) ** 4.0)
        tonal = math.sin(phase) * 0.66 + math.sin(phase * 2.01 + 0.31) * 0.18
        sub = math.sin(sub_phase) * body
        result.append((tonal + sub + transient * noise_mix) * envelope)
    return result


def weapon(name: str, spec: tuple[float, float, float, float, float], seed: int) -> None:
    seconds, f0, f1, noise_mix, body = spec
    save_mono(f"weapon_{name}", exp_chirp(f0, f1, seconds, noise_mix, body, seed))


def impact(name: str, spec: tuple[float, float, float, float, float], seed: int) -> None:
    seconds, f0, f1, noise_mix, body = spec
    samples = exp_chirp(f0, f1, seconds, noise_mix, body, seed)
    for i in range(len(samples)):
        t = i / SR
        samples[i] += math.sin(math.tau * 930.0 * t) * math.exp(-t * 18.0) * 0.16
    save_mono(f"impact_{name}", samples)


def snap_loop_frequency(freq: float, seconds: float) -> float:
    return round(freq * seconds) / seconds


def run_music() -> None:
    seconds = 12.0
    frames = int(seconds * SR)
    bpm = 120.0
    beat = 60.0 / bpm
    half_step = beat * 0.5
    notes = [55.0, 55.0, 65.406, 55.0, 73.416, 65.406, 49.0, 55.0]
    drone_freqs = [snap_loop_frequency(v, seconds) for v in (55.0, 82.5, 110.0)]
    left: list[float] = []
    right: list[float] = []

    for i in range(frames):
        t = i / SR
        drone = sum(
            math.sin(math.tau * f * t + j * 0.53) * (0.075 / (j + 1))
            for j, f in enumerate(drone_freqs)
        )
        step_index = int(t / half_step)
        local_step = t - step_index * half_step
        bass_f = snap_loop_frequency(notes[step_index % len(notes)], seconds)
        bass_env = math.exp(-local_step * 5.6)
        bass = (
            math.sin(math.tau * bass_f * t)
            + 0.23 * math.sin(math.tau * bass_f * 2.0 * t + 0.2)
        ) * bass_env * 0.16

        beat_index = int(t / beat)
        local_beat = t - beat_index * beat
        kick = 0.0
        if local_beat < 0.22:
            kick_phase = math.tau * (42.0 * local_beat + 4.8 * (1.0 - math.exp(-15.0 * local_beat)))
            kick = math.sin(kick_phase) * math.exp(-local_beat * 18.0) * 0.42

        metal = 0.0
        if beat_index % 2 == 1 and local_beat < 0.11:
            metal = (
                math.sin(math.tau * 3760.0 * local_beat)
                + 0.42 * math.sin(math.tau * 5120.0 * local_beat + 0.3)
            ) * math.exp(-local_beat * 34.0) * 0.045

        air = (
            math.sin(math.tau * 7.0 * t / seconds + 0.4)
            * math.sin(math.tau * 13.0 * t / seconds + 1.1)
            * 0.025
        )
        sweep_local = t % 4.0
        sweep_env = math.sin(math.pi * sweep_local / 4.0) ** 2
        sweep = math.sin(math.tau * (410.0 + 95.0 * sweep_local) * t) * sweep_env * 0.022

        mono = drone + bass + kick + metal + air + sweep
        left.append(mono + math.sin(math.tau * snap_loop_frequency(27.5, seconds) * t) * 0.018)
        right.append(mono * 0.96 + math.sin(math.tau * snap_loop_frequency(29.0, seconds) * t + 0.72) * 0.020)

    save_stereo("music_run_loop", left, right)


def boss_stinger() -> None:
    seconds = 2.4
    frames = int(seconds * SR)
    rng = random.Random(SEED + 800)
    left: list[float] = []
    right: list[float] = []
    phase = 0.0

    for i in range(frames):
        t = i / SR
        progress = t / seconds
        freq = 128.0 * math.exp(-t * 1.25) + 30.0
        phase += math.tau * freq / SR
        envelope = min(1.0, t / 0.010) * (max(0.0, 1.0 - progress) ** 1.35)
        sub = math.sin(phase) * 0.48
        horn = (
            math.sin(math.tau * 92.0 * t)
            + 0.42 * math.sin(math.tau * 138.0 * t + 0.4)
        ) * 0.19
        noise = rng.uniform(-1.0, 1.0) * math.exp(-t * 8.5) * 0.13
        clang = math.sin(math.tau * 1470.0 * t) * math.exp(-t * 7.0) * 0.10
        rise = math.sin(math.tau * (235.0 + 410.0 * progress * progress) * t) * (progress ** 1.4) * 0.075
        mono = (sub + horn + noise + clang + rise) * envelope
        left.append(mono + math.sin(math.tau * 58.0 * t) * envelope * 0.028)
        right.append(mono * 0.95 + math.sin(math.tau * 61.0 * t + 0.8) * envelope * 0.030)

    save_stereo("boss_stinger", left, right)


WEAPONS = {
    "vanguard": (0.24, 1180.0, 105.0, 0.46, 0.52),
    "scatter": (0.34, 520.0, 58.0, 0.66, 0.70),
    "rail": (0.42, 2050.0, 215.0, 0.36, 0.52),
    "inferno": (0.38, 790.0, 165.0, 0.48, 0.68),
    "cryo": (0.31, 1580.0, 510.0, 0.32, 0.36),
    "arc": (0.29, 2780.0, 470.0, 0.38, 0.34),
}
IMPACTS = {
    "hit": (0.18, 220.0, 72.0, 0.38, 0.34),
    "critical": (0.28, 420.0, 86.0, 0.48, 0.48),
    "kill": (0.42, 260.0, 48.0, 0.56, 0.62),
    "boss": (0.58, 170.0, 34.0, 0.62, 0.76),
}

for index, (name, spec) in enumerate(WEAPONS.items()):
    weapon(name, spec, SEED + index * 17)
for index, (name, spec) in enumerate(IMPACTS.items()):
    impact(name, spec, SEED + 200 + index * 29)

boss_stinger()
run_music()
print("Generated 12 original Deadline: Zero WAV assets in", OUT)
