extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    var vignette := hud.find_child("DamageVignette", true, false) as ColorRect
    if vignette == null:
        push_error("Screen-space damage vignette is missing")
        quit(1)
        return
    if vignette.visible:
        push_error("Damage vignette should start hidden")
        quit(1)
        return
    hud.pulse_damage_screen()
    if not vignette.visible:
        push_error("Damage vignette did not become visible")
        quit(1)
        return
    await create_timer(0.35).timeout
    if vignette.visible:
        push_error("Damage vignette did not clear after pulse")
        quit(1)
        return

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

    var upgrades := [
        {"id":"damage", "title":"HEAVY PAYLOAD", "detail":"Damage +25%", "family":"OFFENSE"},
        {"id":"speed", "title":"SCOUT FRAME", "detail":"Move speed +14%", "family":"MOBILITY"},
        {"id":"health", "title":"REACTIVE PLATING", "detail":"Max HP +30", "family":"SURVIVAL"}
    ]
    hud.show_upgrade(upgrades)
    var first_card := hud.upgrade_cards[0]
    if first_card.scale.x >= 0.999 or first_card.modulate.a >= 0.999:
        push_error("Upgrade cards should enter with lightweight motion")
        quit(1)
        return
    await create_timer(0.32).timeout
    if absf(first_card.scale.x - 1.0) > 0.01 or first_card.modulate.a < 0.98:
        push_error("Upgrade card entrance motion did not settle cleanly")
        quit(1)
        return

    print("Deadline Zero combat danger HUD: OK")
    quit(0)
