class_name DZRunDirector
extends RefCounted

const PHASES := [
    {"start": 0.0, "name": "BREACH", "interval": 0.82, "batch": 1, "max_enemies": 78},
    {"start": 45.0, "name": "SURGE", "interval": 0.68, "batch": 2, "max_enemies": 88},
    {"start": 90.0, "name": "PRESSURE", "interval": 0.54, "batch": 3, "max_enemies": 98},
    {"start": 150.0, "name": "OVERRUN", "interval": 0.40, "batch": 4, "max_enemies": 110},
    {"start": 225.0, "name": "EXTINCTION", "interval": 0.31, "batch": 5, "max_enemies": 118}
]

func opening_roster() -> Array:
    # Establish immediate silhouette and movement contrast without introducing ranged or
    # high-pressure specials before the player has settled into the controls.
    return [
        "shambler", "runner", "shambler", "shambler",
        "runner", "shambler", "shambler", "shambler"
    ]

func profile(elapsed: float, level: int) -> Dictionary:
    var phase: Dictionary = PHASES[0]
    for candidate in PHASES:
        if elapsed >= float(candidate["start"]):
            phase = candidate
        else:
            break
    var phase_age: float = maxf(0.0, elapsed - float(phase["start"]))
    var interval: float = maxf(0.22, float(phase["interval"]) - minf(0.09, phase_age * 0.0009))
    var difficulty: float = 1.0 + elapsed / 210.0 + float(maxi(level - 1, 0)) * 0.035
    return {
        "phase": String(phase["name"]),
        "spawn_interval": interval,
        "batch_size": int(phase["batch"]),
        "difficulty": difficulty,
        "max_enemies": int(phase["max_enemies"])
    }

func choose_enemy(elapsed: float, level: int, rng: RandomNumberGenerator) -> String:
    var weights := _weights(elapsed, level)
    var total := 0.0
    for weight in weights.values():
        total += float(weight)
    var roll := rng.randf() * total
    var cursor := 0.0
    for kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite"]:
        cursor += float(weights.get(kind, 0.0))
        if roll <= cursor:
            return kind
    return "shambler"

func enemy_sequence(elapsed: float, level: int, seed_value: int, count: int) -> Array:
    var rng := RandomNumberGenerator.new()
    rng.seed = seed_value
    var result: Array = []
    for i in range(maxi(count, 0)):
        result.append(choose_enemy(elapsed, level, rng))
    return result

func _weights(elapsed: float, level: int) -> Dictionary:
    var weights := {
        "shambler": 1.0,
        "runner": 0.0,
        "charger": 0.0,
        "harrier": 0.0,
        "regenerator": 0.0,
        "brute": 0.0,
        "elite": 0.0
    }
    if elapsed >= 25.0:
        weights["runner"] = 0.38
    if elapsed >= 45.0:
        weights["charger"] = 0.22
    if elapsed >= 65.0:
        weights["harrier"] = 0.18
    if elapsed >= 82.0:
        weights["regenerator"] = 0.14
    if elapsed >= 100.0:
        weights["brute"] = 0.12
    if elapsed >= 125.0:
        weights["elite"] = 0.08
    var escalation := clampf((elapsed - 90.0) / 180.0, 0.0, 1.0) + clampf(float(level - 4) * 0.035, 0.0, 0.18)
    weights["shambler"] = maxf(0.48, 1.0 - escalation * 0.42)
    weights["charger"] += escalation * 0.08
    weights["harrier"] += escalation * 0.07
    weights["brute"] += escalation * 0.06
    weights["elite"] += escalation * 0.04
    return weights
