extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)

    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if hud.threat_panel == null or hud.threat_label == null:
        push_error("Threat indicator UI was not created")
        quit(1)
        return

    hud.hide_offscreen_threat()
    if hud.threat_panel.visible:
        push_error("Threat indicator should start hidden")
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.RIGHT, "elite", 18.4)
    if not hud.threat_panel.visible:
        push_error("Threat indicator did not become visible")
        quit(1)
        return
    if hud.threat_label.text != "→  ELITE  18m":
        push_error("Unexpected right indicator: %s" % hud.threat_label.text)
        quit(1)
        return

    hud.set_offscreen_threat(Vector2(-1.0, -1.0), "boss", 27.6)
    if hud.threat_label.text != "↖  BOSS  28m":
        push_error("Unexpected diagonal indicator: %s" % hud.threat_label.text)
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.ZERO, "boss", 10.0)
    if hud.threat_panel.visible:
        push_error("Zero direction should hide threat indicator")
        quit(1)
        return

    print("Deadline Zero offscreen threat indicator: OK")
    quit(0)
