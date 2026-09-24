extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame

    var pulse := player.get_node_or_null("DamagePulse") as MeshInstance3D
    if pulse == null:
        push_error("Player damage feedback pulse is missing")
        quit(1)
        return
    if pulse.visible:
        push_error("Damage pulse should start hidden")
        quit(1)
        return

    var initial_health: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, initial_health - 12.0):
        push_error("Player health did not decrease on first hit")
        quit(1)
        return
    if not pulse.visible:
        push_error("Damage pulse did not become visible after damage")
        quit(1)
        return
    if player.invulnerability <= 0.0:
        push_error("Damage hit did not arm invulnerability window")
        quit(1)
        return

    var health_after_first_hit: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, health_after_first_hit):
        push_error("Invulnerability window failed to reject immediate repeated damage")
        quit(1)
        return

    await create_timer(0.20).timeout
    if pulse.visible:
        push_error("Damage pulse did not clear after its presentation window")
        quit(1)
        return

    print("Deadline Zero player damage feedback: OK")
    quit(0)
