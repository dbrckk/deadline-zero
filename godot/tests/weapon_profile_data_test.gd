extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var script := load("res://scripts/WeaponProfiles.gd")
    if script == null:
        push_error("Data-driven weapon profile catalog is missing")
        quit(1)
        return

    var rail: Dictionary = script.profile("rail")
    if rail.is_empty():
        push_error("Rail weapon profile data is missing")
        quit(1)
        return
    if float(rail.get("damage_multiplier", 1.0)) <= 1.0:
        push_error("Rail profile damage multiplier is not represented as data")
        quit(1)
        return
    if int(rail.get("multishot_set", 0)) != 1:
        push_error("Rail profile multishot rule is not represented as data")
        quit(1)
        return

    var cryo: Dictionary = script.profile("cryo")
    if float(cryo.get("projectile_speed_multiplier", 1.0)) <= 1.0:
        push_error("Cryo projectile speed rule is not represented as data")
        quit(1)
        return

    var root := Node3D.new()
    get_root().add_child(root)
    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    if not player.has_method("_apply_weapon_profile_data"):
        push_error("Player generic weapon-profile applicator is missing")
        quit(1)
        return

    var base_damage: float = player.weapon_damage
    var base_speed: float = player.projectile_speed
    var base_interval: float = player.fire_interval
    var custom := {
        "tint": Color(0.9, 0.2, 0.7),
        "damage_multiplier": 2.0,
        "projectile_speed_multiplier": 1.5,
        "fire_interval_multiplier": 0.5,
        "fire_interval_floor": 0.10,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0
    }
    player._apply_weapon_profile_data("test_profile", custom)
    if player.weapon_profile != "test_profile":
        push_error("Generic profile applicator did not set weapon profile")
        quit(1)
        return
    if not is_equal_approx(player.weapon_damage, base_damage * 2.0):
        push_error("Generic profile applicator ignored damage data")
        quit(1)
        return
    if not is_equal_approx(player.projectile_speed, base_speed * 1.5):
        push_error("Generic profile applicator ignored projectile speed data")
        quit(1)
        return
    if not is_equal_approx(player.fire_interval, max(0.10, base_interval * 0.5)):
        push_error("Generic profile applicator ignored cadence data")
        quit(1)
        return

    print("Deadline Zero weapon profile data: OK")
    quit(0)
