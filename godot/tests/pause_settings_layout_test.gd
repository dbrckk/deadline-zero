extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var hud := HUD_SCRIPT.new()
    get_root().add_child(hud)
    await process_frame
    await process_frame

    var panel := hud.pause_panel
    if panel == null:
        push_error("Pause/settings panel is missing")
        quit(1)
        return

    var viewport_size := get_root().get_visible_rect().size
    var panel_rect := panel.get_global_rect()
    var safe_margin := 40.0
    if panel_rect.position.x < safe_margin or panel_rect.position.y < safe_margin:
        push_error("Pause/settings panel violates top/left mobile safe margin")
        quit(1)
        return
    if panel_rect.end.x > viewport_size.x - safe_margin or panel_rect.end.y > viewport_size.y - safe_margin:
        push_error("Pause/settings panel clips beyond mobile safe viewport margin")
        quit(1)
        return

    var required_controls := [
        hud.master_volume,
        hud.sfx_volume,
        hud.haptics_toggle,
        hud.reduced_flashes_toggle,
        hud.camera_shake_toggle,
        hud.hit_stop_toggle,
        panel.find_child("ResumeButton", true, false)
    ]
    for control in required_controls:
        if control == null or not control is Control:
            push_error("Pause/settings control is missing")
            quit(1)
            return
        var ui := control as Control
        if ui.size.y < 47.5:
            push_error("Pause/settings touch target is below 48px: %s %.1fpx" % [ui.name, ui.size.y])
            quit(1)
            return
        var rect := ui.get_global_rect()
        if rect.position.x < panel_rect.position.x - 1.0 or rect.end.x > panel_rect.end.x + 1.0:
            push_error("Pause/settings control escapes panel horizontally: %s" % ui.name)
            quit(1)
            return
        if rect.position.y < panel_rect.position.y - 1.0 or rect.end.y > panel_rect.end.y + 1.0:
            push_error("Pause/settings control escapes panel vertically: %s" % ui.name)
            quit(1)
            return

    print("Deadline Zero Godot pause/settings layout: OK")
    quit(0)
