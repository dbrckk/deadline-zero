extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Control.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if not hud.has_method("show_impact_flash") or not "impact_flash" in hud:
        push_error("HUD screen-space impact flash API is missing")
        quit(1)
        return

    hud.show_impact_flash(true, false, false)
    if hud.impact_flash == null or not hud.impact_flash.visible:
        push_error("Critical enemy hit did not trigger screen-space impact flash")
        quit(1)
        return
    if hud.impact_flash.color.a <= 0.0:
        push_error("Impact flash alpha was not visible")
        quit(1)
        return

    print("Deadline Zero screen-space impact FX: OK")
    quit(0)
