extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)

    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if hud.low_health_panel == null or hud.low_health_label == null:
        push_error("Low-health warning UI was not created")
        quit(1)
        return

    hud.set_health(100.0, 100.0)
    if hud.low_health_panel.visible:
        push_error("Low-health warning should be hidden at full health")
        quit(1)
        return

    hud.set_health(30.0, 100.0)
    if not hud.low_health_panel.visible:
        push_error("Low-health warning should appear at 30 percent")
        quit(1)
        return
    if hud.low_health_label.text != "CRITICAL INTEGRITY  •  30%":
        push_error("Unexpected low-health label: %s" % hud.low_health_label.text)
        quit(1)
        return

    hud.set_health(31.0, 100.0)
    if hud.low_health_panel.visible:
        push_error("Low-health warning should clear above threshold")
        quit(1)
        return

    hud.set_health(0.0, 100.0)
    if hud.low_health_panel.visible:
        push_error("Low-health warning should hide after death")
        quit(1)
        return

    print("Deadline Zero low-health danger readability: OK")
    quit(0)
