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
    # Short cached one-shots can afford full-band 44.1 kHz. A deterministic layered transient
    # avoids the thin single-sine character of the first-playable fallback without adding
    # runtime DSP cost or per-shot allocations.
    var rate: int = 44100
    var frames: int = maxi(128, int(seconds * rate))
    var bytes := PackedByteArray()
    bytes.resize(frames * 2)
    var phase: float = 0.0
    var sub_phase: float = 0.0
    var noise_state: int = int(absf(start_hz * 131.0 + end_hz * 47.0 + seconds * 100000.0)) | 1

    for i in range(frames):
        var t: float = float(i) / float(maxi(1, frames - 1))
        var hz_curve := t * t * (3.0 - 2.0 * t)
        var hz: float = lerpf(start_hz, end_hz, hz_curve)
        phase += TAU * hz / float(rate)
        sub_phase += TAU * maxf(48.0, hz * 0.47) / float(rate)

        noise_state = int((1103515245 * noise_state + 12345) & 0x7fffffff)
        var noise := (float(noise_state) / 1073741823.5 - 1.0)

        var attack := clampf(t / 0.018, 0.0, 1.0)
        var decay := pow(maxf(0.0, 1.0 - t), 2.05)
        var envelope := attack * decay

        var fundamental := sin(phase)
        var harmonic := sin(phase * 2.03 + 0.35) * 0.24
        var body := sin(sub_phase) * 0.32
        var transient_window := pow(maxf(0.0, 1.0 - t / 0.16), 4.0)
        var transient := noise * transient_window * minf(0.62, noise_mix + 0.18)
        var texture := noise * noise_mix * 0.34

        var tonal_mix := fundamental * 0.72 + harmonic + body
        var raw := (tonal_mix * (1.0 - noise_mix * 0.46) + texture + transient) * envelope * gain
        var sample := tanh(raw * 1.28) / tanh(1.28)
        sample = clampf(sample, -0.985, 0.985)

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
