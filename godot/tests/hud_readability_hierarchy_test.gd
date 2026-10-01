extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    for node_name in ["VitalPanel", "VitalAccent", "ThreatPanel", "BossPanel", "UpgradePanel"]:
        if hud.find_child(node_name, true, false) == null:
            push_error("HUD readability hierarchy missing node: %s" % node_name)
            quit(1)
            return

    var vital_panel := hud.find_child("VitalPanel", true, false) as Control
    if vital_panel == null or vital_panel.size.x < 300.0 or vital_panel.size.y < 82.0:
        push_error("Vital panel must reserve a readable combat-safe footprint")
        quit(1)
        return

    if vital_panel.size.x > 410.0 or vital_panel.size.y > 94.0:
        push_error("Vital panel regressed into an oversized combat-obscuring footprint")
        quit(1)
        return

    if hud.health_bar == null or hud.health_bar.custom_minimum_size.y < 18.0:
        push_error("Health bar must remain readable under combat pressure")
        quit(1)
        return
    if hud.xp_bar == null or hud.xp_bar.custom_minimum_size.y < 8.0:
        push_error("XP bar must retain a distinct secondary hierarchy")
        quit(1)
        return

    if hud.wave_label == null or hud.wave_label.get_theme_font_size("font_size") > 24:
        push_error("Wave label must not dominate the active combat frame")
        quit(1)
        return

    hud.set_health(24.0, 100.0)
    if not hud.low_health_panel.visible:
        push_error("Critical health state must remain immediately visible")
        quit(1)
        return
    if hud.low_health_panel.modulate.a < 0.95:
        push_error("Critical health warning must not be visually muted")
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.RIGHT, "boss", 18.0)
    if not hud.threat_panel.visible or hud.threat_label == null:
        push_error("Boss threat must remain readable while off screen")
        quit(1)
        return
    if hud.threat_label.get_theme_font_size("font_size") < 18:
        push_error("Threat typography is too small for mobile combat")
        quit(1)
        return

    print("hud_readability_hierarchy_test: PASS")
    quit(0)
