extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var width := int(ProjectSettings.get_setting("display/window/size/viewport_width", 0))
    var height := int(ProjectSettings.get_setting("display/window/size/viewport_height", 0))
    var orientation := int(ProjectSettings.get_setting("display/window/handheld/orientation", -1))

    if width <= height:
        push_error("Godot mobile viewport must remain landscape, got %dx%d" % [width, height])
        quit(1)
        return
    if orientation != DisplayServer.SCREEN_SENSOR_LANDSCAPE:
        push_error("Godot handheld orientation must be SCREEN_SENSOR_LANDSCAPE, got %d" % orientation)
        quit(1)
        return

    print("Deadline Zero mobile landscape contract: OK")
    quit(0)
