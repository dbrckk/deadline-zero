extends SceneTree

func _init() -> void:
    var projectile := FileAccess.get_file_as_string("res://scripts/Projectile.gd")
    var player := FileAccess.get_file_as_string("res://scripts/Player.gd")

    for profile in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        if not _require(projectile.contains("\"" + profile + "\""), "Projectile profile missing presentation identity: %s" % profile):
            return

    var projectile_contract := [
        ["trail_length", "Projectile trail length contract is missing"],
        ["trail_width", "Projectile trail width contract is missing"],
        ["core_radius", "Projectile core radius contract is missing"],
        ["impact_scale", "Projectile impact scale contract is missing"],
        ["_add_side_spark", "Scatter projectile side-spark presentation is missing"],
        ["_add_arc_accent", "Arc projectile accent presentation is missing"],
        ["_add_flame_core", "Inferno projectile core presentation is missing"],
        ["ProjectileCore", "Projectile core node identity is missing"],
        ["ProjectileTrail", "Projectile trail node identity is missing"],
        ["SHADOW_CASTING_SETTING_OFF", "Projectile visual geometry must remain shadow-free"],
        ["material.emission_energy_multiplier = 2.15", "Premium projectile trail emission contract regressed"],
        ["_add_side_spark(core_mat, -1.0)", "Left scatter spark identity is missing"],
        ["_add_side_spark(core_mat, 1.0)", "Right scatter spark identity is missing"]
    ]
    for requirement in projectile_contract:
        if not _require(projectile.contains(String(requirement[0])), String(requirement[1])):
            return
    if not _require(not projectile.contains("_add_side_spark(mat,"), "Legacy scatter spark material call returned"):
        return

    var player_contract := [
        ["weapon_profile", "Player weapon profile routing is missing"],
        ["weapon_tint", "Player weapon tint routing is missing"],
        ["weapon_profile)", "Projectile setup no longer forwards weapon profile"],
        ["PlayerMarkerRing", "Player marker ring presentation is missing"],
        ["PlayerAimTick", "Player aim tick presentation is missing"],
        ["MuzzleFlash", "Player muzzle flash presentation is missing"],
        ["_trigger_muzzle_flash()", "Muzzle flash is not triggered during fire"],
        ["PlayerPressureLocator", "Player pressure locator is missing"],
        ["PlayerPressureChevron_", "Player pressure chevrons are missing"],
        ["_update_player_marker_pressure(_pressure_target(target))", "Pressure marker is not routed through safe threat validation"],
        ["distance_squared_to(target.global_position) <= 8.41", "Close-pressure threshold contract regressed"],
        ["TacticalRig", "Player tactical rig presentation is missing"],
        ["TacticalBackplate", "Player backplate presentation is missing"],
        ["TacticalShoulderL", "Player left shoulder presentation is missing"],
        ["TacticalShoulderR", "Player right shoulder presentation is missing"],
        ["TacticalCore", "Player tactical core presentation is missing"],
        ["WeaponAccent", "Weapon accent presentation is missing"],
        ["_trigger_rifle_recoil()", "Rifle recoil presentation is not triggered"]
    ]
    for requirement in player_contract:
        if not _require(player.contains(String(requirement[0])), String(requirement[1])):
            return

    print("weapon_presentation_test: PASS")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
