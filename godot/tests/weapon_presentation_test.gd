extends SceneTree

func _init() -> void:
    var projectile := FileAccess.get_file_as_string("res://scripts/Projectile.gd")
    var player := FileAccess.get_file_as_string("res://scripts/Player.gd")

    for profile in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        assert(projectile.contains("\"" + profile + "\""))
    assert(projectile.contains("trail_length"))
    assert(projectile.contains("trail_width"))
    assert(projectile.contains("core_radius"))
    assert(projectile.contains("impact_scale"))
    assert(projectile.contains("_add_side_spark"))
    assert(projectile.contains("_add_arc_accent"))
    assert(projectile.contains("_add_flame_core"))
    assert(projectile.contains("ProjectileCore"))
    assert(projectile.contains("ProjectileTrail"))
    assert(projectile.contains("SHADOW_CASTING_SETTING_OFF"))
    assert(projectile.contains("trail_mat.emission_energy_multiplier = 1.65"))
    assert(player.contains("weapon_profile"))
    assert(player.contains("weapon_tint"))
    assert(player.contains("weapon_profile)"))
    assert(player.contains("PlayerMarkerRing"))
    assert(player.contains("PlayerAimTick"))
    assert(player.contains("MuzzleFlash"))
    assert(player.contains("_trigger_muzzle_flash()"))

    print("weapon_presentation_test: PASS")
    quit()
