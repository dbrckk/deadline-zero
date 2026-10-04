# Deadline: Zero authored audio

The production audio pack is original project work generated deterministically from source code.

- Generator: `tools/audio/generate_deadline_zero_audio.py`
- Deterministic seed: `20261004`
- Source sample rate: 44.1 kHz
- Generated delivery format: 16-bit WAV; Godot imports WAV using its normal asset pipeline.
- No third-party samples, loops, recordings, or copyrighted source audio are used.
- No external attribution is required because these files are created specifically for Deadline: Zero.

## Generated production set

Weapons: Vanguard, Scatter, Rail, Inferno, Cryo, Arc.

Combat impacts: standard hit, critical hit, kill, boss hit.

Music: a 12-second loopable dark-industrial base score plus a synchronized 12-second pressure layer that rises across run phases.

Boss: a 2.4-second authored encounter stinger.

The generated WAV files are build artifacts and are intentionally git-ignored. Official Godot CI/release workflows run the generator before the first Godot import so the exact deterministic audio is packaged into APK/AAB builds.
