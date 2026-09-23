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
    assert(player.contains("weapon_profile"))
    assert(player.contains("weapon_tint"))
    assert(player.contains("weapon_profile)"))

    print("weapon_presentation_test: PASS")
    quit()
