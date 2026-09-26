extends SceneTree

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

    print("Deadline Zero weapon profile data: OK")
    quit(0)
