extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var width := int(ProjectSettings.get_setting("display/window/size/viewport_width", 0))
    var height := int(ProjectSettings.get_setting("display/window/size/viewport_height", 0))
    var orientation := int(ProjectSettings.get_setting("display/window/handheld/orientation", -1))
    var mobile_renderer := String(ProjectSettings.get_setting("rendering/renderer/rendering_method.mobile", ""))

    if width <= height:
        push_error("Godot mobile viewport must remain landscape, got %dx%d" % [width, height])
        quit(1)
        return
    if orientation != DisplayServer.SCREEN_SENSOR_LANDSCAPE:
        push_error("Godot handheld orientation must be SCREEN_SENSOR_LANDSCAPE, got %d" % orientation)
        quit(1)
        return
    if mobile_renderer != "gl_compatibility":
        push_error("Godot mobile renderer must remain gl_compatibility, got %s" % mobile_renderer)
        quit(1)
        return

    print("Deadline Zero mobile landscape/rendering contract: OK")
    quit(0)
