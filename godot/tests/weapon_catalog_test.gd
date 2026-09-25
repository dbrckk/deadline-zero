extends SceneTree

const CATALOG := preload("res://scripts/WeaponCatalog.gd")

func _initialize() -> void:
    var required := ["scatter_protocol", "rail_protocol", "inferno_protocol", "cryo_protocol", "arc_protocol"]
    for id in required:
        var profile: Dictionary = CATALOG.protocol(id)
        if profile.is_empty():
            push_error("Weapon catalog is missing protocol %s" % id)
            quit(1)
            return
        if not profile.has("weapon_profile") or not profile.has("tint"):
            push_error("Weapon catalog protocol %s lacks identity data" % id)
            quit(1)
            return

    var rail: Dictionary = CATALOG.protocol("rail_protocol")
    if float(rail.get("damage_mult", 0.0)) <= 1.0 or float(rail.get("projectile_speed_mult", 0.0)) <= 1.0:
        push_error("Rail protocol catalog tuning is incomplete")
        quit(1)
        return
    if not CATALOG.protocol("unknown").is_empty():
        push_error("Unknown weapon protocol should return empty config")
        quit(1)
        return

    print("Deadline Zero weapon catalog: OK")
    quit(0)
