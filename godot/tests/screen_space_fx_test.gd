extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")
const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

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

    var normal_alpha := hud.impact_flash.color.a
    hud.set_reduced_flashes(true)
    hud.show_impact_flash(true, false, false)
    if hud.impact_flash.color.a <= 0.0 or hud.impact_flash.color.a >= normal_alpha * 0.5:
        push_error("Reduced-flashes mode did not substantially lower impact flash intensity")
        quit(1)
        return
    hud.pulse_damage_screen()
    if hud.damage_vignette.modulate.a > 0.40:
        push_error("Reduced-flashes mode did not lower damage vignette intensity")
        quit(1)
        return

    var player := PLAYER_SCRIPT.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    get_root().add_child(player)
    await process_frame
    player.set_reduced_flashes(true)
    player._trigger_muzzle_flash()
    if player.muzzle_flash_material == null or player.muzzle_flash_material.emission_energy_multiplier > 2.3:
        push_error("Reduced-flashes mode did not lower player muzzle-flash emission")
        quit(1)
        return
    if player.muzzle_flash.scale.x > 0.50:
        push_error("Reduced-flashes mode did not reduce player muzzle-flash footprint")
        quit(1)
        return

    print("Deadline Zero screen-space impact FX: OK")
    quit(0)
