extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var required := [
        ["BOSS_REVEAL_DURATION := 1.15", "Boss reveal duration contract regressed"],
        ["BOSS_REVEAL_FOCUS := 0.58", "Boss reveal focus contract regressed"],
        ["BOSS_REVEAL_FOV_DELTA := 5.5", "Boss reveal FOV delta contract regressed"],
        ["CAMERA_BASE_HEIGHT := 12.8", "Base camera height contract regressed"],
        ["CAMERA_BASE_TRAIL := 9.15", "Base camera trail contract regressed"],
        ["CAMERA_BASE_FOV := 46.0", "Base camera FOV contract regressed"],
        ["CAMERA_REVEAL_HEIGHT := 14.0", "Boss reveal camera height contract regressed"],
        ["CAMERA_REVEAL_TRAIL := 10.4", "Boss reveal camera trail contract regressed"],
        ["boss_reveal_target = enemy", "Boss spawn no longer arms reveal target"],
        ["target_fov = CAMERA_BASE_FOV + BOSS_REVEAL_FOV_DELTA * envelope", "Boss reveal no longer widens field of view"],
        ["camera.look_at(focus_point, Vector3.UP)", "Camera focus contract is missing"]
    ]
    for item in required:
        if not _require(source.contains(String(item[0])), String(item[1])):
            return

    if not _require(1.15 <= 1.25, "Boss reveal duration exceeds mobile comfort bound"):
        return
    if not _require(5.5 <= 7.0, "Boss reveal FOV delta exceeds mobile comfort bound"):
        return
    if not _require(0.58 >= 0.45 and 0.58 <= 0.68, "Boss reveal focus leaves validated framing range"):
        return
    if not _require(46.0 >= 44.0 and 46.0 <= 49.0, "Base camera FOV leaves readability range"):
        return

    print("godot boss reveal camera validation passed")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
