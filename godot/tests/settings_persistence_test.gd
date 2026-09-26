extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")

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

    var integration_path := "user://deadline-zero-settings-integration-test.cfg"
    script.save(integration_path, {
        "master_volume": 0.37,
        "sfx_volume": 0.58
    })

    var main := MAIN_SCENE.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    if not main.has_method("_load_audio_settings") or not main.has_method("_save_audio_settings"):
        push_error("Main settings persistence integration is missing")
        quit(1)
        return

    main._load_audio_settings(integration_path)
    if not is_equal_approx(main.hud.master_volume.value, 0.37):
        push_error("Persisted master volume was not restored into pause settings")
        quit(1)
        return
    if not is_equal_approx(main.hud.sfx_volume.value, 0.58):
        push_error("Persisted SFX volume was not restored into pause settings")
        quit(1)
        return

    main.hud.master_volume.value = 0.61
    main.hud.sfx_volume.value = 0.47
    main._save_audio_settings(integration_path)
    var round_trip: Dictionary = script.load_settings(integration_path)
    if not is_equal_approx(float(round_trip.get("master_volume", -1.0)), 0.61):
        push_error("Updated master volume was not saved from pause settings")
        quit(1)
        return
    if not is_equal_approx(float(round_trip.get("sfx_volume", -1.0)), 0.47):
        push_error("Updated SFX volume was not saved from pause settings")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(integration_path))
    print("Deadline Zero settings persistence: OK")
    quit(0)
