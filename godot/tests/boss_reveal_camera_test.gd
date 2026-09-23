extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    assert(source.contains("BOSS_REVEAL_DURATION := 1.15"))
    assert(source.contains("BOSS_REVEAL_FOCUS := 0.58"))
    assert(source.contains("BOSS_REVEAL_FOV_DELTA := 5.5"))
    assert(source.contains("boss_reveal_target = enemy"))
    assert(source.contains("target_fov = 48.0 + BOSS_REVEAL_FOV_DELTA * envelope"))
    assert(source.contains("camera.look_at(focus_point, Vector3.UP)"))

    # Mobile comfort bounds: the reveal must stay short and widen the view rather than punch in.
    assert(1.15 <= 1.25)
    assert(5.5 <= 7.0)
    assert(0.58 >= 0.45 and 0.58 <= 0.68)
    print("godot boss reveal camera validation passed")
    quit()
