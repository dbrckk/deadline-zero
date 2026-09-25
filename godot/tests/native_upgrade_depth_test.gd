extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame

    var base_damage := player.weapon_damage
    var base_health := player.max_health
    var base_speed := player.move_speed

    player.apply_upgrade("berserker")
    if player.weapon_damage <= base_damage or player.max_health >= base_health:
        push_error("Berserker tradeoff was not applied")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("fortress")
    if player.max_health <= base_health or player.move_speed >= base_speed:
        push_error("Fortress tradeoff was not applied")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("scatter_protocol")
    if player.weapon_profile != "scatter" or player.multishot < 3 or player.spread_degrees < 11.0:
        push_error("Scatter protocol identity is incomplete")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("rail_protocol")
    if player.weapon_profile != "rail" or player.multishot != 1 or player.projectile_speed <= 19.0:
        push_error("Rail protocol identity is incomplete")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("inferno_protocol")
    if player.weapon_profile != "inferno":
        push_error("Inferno protocol profile missing")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("cryo_protocol")
    if player.weapon_profile != "cryo":
        push_error("Cryo protocol profile missing")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("arc_protocol")
    if player.weapon_profile != "arc" or player.multishot < 2:
        push_error("Arc protocol identity is incomplete")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("inferno_protocol")
    var inferno_damage := player.weapon_damage
    var inferno_interval := player.fire_interval
    player.apply_upgrade("inferno_protocol")
    if not is_equal_approx(player.weapon_damage, inferno_damage) or not is_equal_approx(player.fire_interval, inferno_interval):
        push_error("Weapon protocols should be idempotent when reapplied")
        quit(1)
        return

    print("Deadline Zero native upgrade depth: OK")
    quit(0)
