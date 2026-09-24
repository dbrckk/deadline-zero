extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    hud.set_health(30.0, 100.0)
    if not hud.low_health_panel.visible:
        push_error("Low-health warning missing at 30 percent")
        quit(1)
        return
    if hud.low_health_label.text != "CRITICAL INTEGRITY  •  30%":
        push_error("Unexpected low-health label")
        quit(1)
        return

    hud.set_health(31.0, 100.0)
    if hud.low_health_panel.visible:
        push_error("Low-health warning should clear above threshold")
        quit(1)
        return

    hud.set_offscreen_threat(Vector2(-1.0, -1.0), "boss", 27.6)
    if not hud.threat_panel.visible:
        push_error("Threat indicator should be visible")
        quit(1)
        return
    if hud.threat_label.text != "↖  BOSS  28m":
        push_error("Unexpected threat label: %s" % hud.threat_label.text)
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.ZERO, "boss", 10.0)
    if hud.threat_panel.visible:
        push_error("Zero direction should clear threat indicator")
        quit(1)
        return

    print("Deadline Zero combat danger HUD: OK")
    quit(0)
