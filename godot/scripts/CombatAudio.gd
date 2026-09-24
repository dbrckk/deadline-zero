class_name DZCombatAudio
extends RefCounted

# Procedural one-shot synthesis keeps the native 3D combat lane self-contained while authored
# weapon/enemy audio is still being produced. Each cue is intentionally short and phone-safe.

static func shot_stream(profile: String) -> AudioStreamWAV:
    var spec: Array = {
        "vanguard": [1180.0, 720.0, 0.055, 0.20],
        "scatter": [520.0, 220.0, 0.085, 0.34],
        "rail": [1960.0, 980.0, 0.070, 0.18],
        "inferno": [760.0, 330.0, 0.080, 0.28],
        "cryo": [1540.0, 1080.0, 0.072, 0.16],
        "arc": [1320.0, 460.0, 0.075, 0.22]
    }.get(profile, [1180.0, 720.0, 0.055, 0.20]) as Array
    return _chirp(float(spec[0]), float(spec[1]), float(spec[2]), float(spec[3]), 0.82)

static func impact_stream(critical: bool, killed: bool, boss: bool) -> AudioStreamWAV:
    if boss:
        return _chirp(210.0, 92.0, 0.120, 0.42, 0.92)
    if killed:
        return _chirp(390.0, 145.0, 0.095, 0.34, 0.88)
    if critical:
        return _chirp(980.0, 420.0, 0.082, 0.24, 0.88)
    return _chirp(640.0, 260.0, 0.052, 0.18, 0.72)

static func boss_stinger() -> AudioStreamWAV:
    return _chirp(170.0, 72.0, 0.240, 0.50, 0.94)

static func _chirp(start_hz: float, end_hz: float, seconds: float, noise_mix: float,
        gain: float) -> AudioStreamWAV:
    var rate: int = 22050
    var frames: int = maxi(64, int(seconds * rate))
    var bytes := PackedByteArray()
    bytes.resize(frames * 2)
    var phase: float = 0.0
    for i in range(frames):
        var t: float = float(i) / float(maxi(1, frames - 1))
        var hz: float = lerpf(start_hz, end_hz, t)
        phase += TAU * hz / float(rate)
        var envelope: float = pow(1.0 - t, 2.15)
        var tone: float = sin(phase) * (1.0 - noise_mix)
        var noise: float = (randf() * 2.0 - 1.0) * noise_mix
        var sample: float = clampf((tone + noise) * envelope * gain, -1.0, 1.0)
        var value: int = int(sample * 32767.0)
        if value < 0:
            value += 65536
        bytes[i * 2] = value & 0xff
        bytes[i * 2 + 1] = (value >> 8) & 0xff

    var wav := AudioStreamWAV.new()
    wav.format = AudioStreamWAV.FORMAT_16_BITS
    wav.mix_rate = rate
    wav.stereo = false
    wav.data = bytes
    return wav
