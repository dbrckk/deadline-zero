extends RefCounted

static func pattern_for(kind: String) -> int:
    match kind:
        "hit":
            return 18
        "critical":
            return 38
        "boss":
            return 72
        _:
            return 0

static func amplitude_for(kind: String) -> float:
    match kind:
        "hit":
            return 0.32
        "critical":
            return 0.58
        "boss":
            return 0.82
        _:
            return 0.0

static func pulse(kind: String) -> void:
    var duration := pattern_for(kind)
    if duration <= 0:
        return
    Input.vibrate_handheld(duration, amplitude_for(kind))
