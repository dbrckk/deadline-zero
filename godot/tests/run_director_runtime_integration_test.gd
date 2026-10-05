extends SceneTree

func _init() -> void:
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if main_source.is_empty():
        push_error("Main.gd must be readable")
        quit(1)
        return

    for required in [
        "var run_director := DZRunDirector.new()",
        "run_director.profile(elapsed, level)",
        "run_director.choose_enemy(elapsed, level, spawn_rng)",
        "director_profile[\"spawn_interval\"]",
        "director_profile[\"batch_size\"]",
        "director_profile[\"max_enemies\"]",
        "director_profile[\"difficulty\"]",
        "BOSS_DEFEAT_RELIEF_DURATION",
        "MUSIC_PRESSURE_RELIEF_DB",
        "THREAT NEUTRALIZED // PRESSURE DROPPING"
    ]:
        if main_source.find(required) < 0:
            push_error("Main runtime is not wired to RunDirector: %s" % required)
            quit(1)
            return

    for legacy in [
        "1 + int(elapsed / 45.0)",
        "0.82 - elapsed * 0.0035",
        "if elapsed > 25.0 and roll > 0.72",
        "var difficulty := 1.0 + elapsed / 210.0"
    ]:
        if main_source.find(legacy) >= 0:
            push_error("Legacy hard-coded pacing remains in Main.gd: %s" % legacy)
            quit(1)
            return

    print("run_director_runtime_integration_test: PASS")
    quit(0)
