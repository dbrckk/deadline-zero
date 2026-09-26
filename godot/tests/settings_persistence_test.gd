extends SceneTree

func _initialize() -> void:
    var script := load("res://scripts/GameSettings.gd")
    if script == null:
        push_error("Game settings persistence service is missing")
        quit(1)
        return

    var path := "user://deadline-zero-settings-test.cfg"
    var expected := {
        "master_volume": 0.42,
        "sfx_volume": 0.33
    }
    script.save(path, expected)
    var loaded: Dictionary = script.load_settings(path)
    if not is_equal_approx(float(loaded.get("master_volume", -1.0)), 0.42):
        push_error("Master volume setting did not persist")
        quit(1)
        return
    if not is_equal_approx(float(loaded.get("sfx_volume", -1.0)), 0.33):
        push_error("SFX volume setting did not persist")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(path))
    print("Deadline Zero settings persistence: OK")
    quit(0)
