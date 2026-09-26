extends SceneTree

func _init() -> void:
    var director_script := load("res://scripts/RunDirector.gd")
    if director_script == null:
        push_error("RunDirector must exist")
        quit(1)
        return

    var director = director_script.new()
    var opening: Dictionary = director.profile(0.0, 1)
    var pressure: Dictionary = director.profile(90.0, 4)
    var late: Dictionary = director.profile(180.0, 7)

    if not _require_keys(opening):
        quit(1)
        return
    if float(opening["spawn_interval"]) <= float(pressure["spawn_interval"]):
        push_error("Pressure phase must spawn faster than opening")
        quit(1)
        return
    if int(opening["batch_size"]) >= int(late["batch_size"]):
        push_error("Late phase must spawn larger batches than opening")
        quit(1)
        return
    if float(opening["difficulty"]) >= float(late["difficulty"]):
        push_error("Late phase difficulty must exceed opening")
        quit(1)
        return
    if String(opening["phase"]) == String(late["phase"]):
        push_error("Run phase identity must escalate over time")
        quit(1)
        return

    var seed_a: Array = director.enemy_sequence(135.0, 5, 24680, 12)
    var seed_b: Array = director.enemy_sequence(135.0, 5, 24680, 12)
    var seed_c: Array = director.enemy_sequence(135.0, 5, 24681, 12)
    if seed_a != seed_b:
        push_error("Enemy sequence must be deterministic for a fixed seed")
        quit(1)
        return
    if seed_a == seed_c:
        push_error("Different seeds must be able to produce different enemy sequences")
        quit(1)
        return
    if not ("brute" in seed_a or "elite" in seed_a or "charger" in seed_a or "harrier" in seed_a or "regenerator" in seed_a):
        push_error("Escalated sequence must contain a pressure archetype")
        quit(1)
        return

    print("run_director_escalation_test: PASS")
    quit(0)

func _require_keys(profile: Dictionary) -> bool:
    for key in ["phase", "spawn_interval", "batch_size", "difficulty", "max_enemies"]:
        if not profile.has(key):
            push_error("Run director profile missing key: %s" % key)
            return false
    return true
