extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var target := Node3D.new()
    root.add_child(target)

    var primary := ENEMY_SCRIPT.new()
    primary.configure("shambler", 1.0, target)
    root.add_child(primary)
    primary.process_mode = Node.PROCESS_MODE_DISABLED
    primary.global_position = Vector3.ZERO

    var nearby := ENEMY_SCRIPT.new()
    nearby.configure("shambler", 1.0, target)
    root.add_child(nearby)
    nearby.process_mode = Node.PROCESS_MODE_DISABLED
    nearby.global_position = Vector3(1.2, 0.0, 0.0)

    var nearby_two := ENEMY_SCRIPT.new()
    nearby_two.configure("shambler", 1.0, target)
    root.add_child(nearby_two)
    nearby_two.process_mode = Node.PROCESS_MODE_DISABLED
    nearby_two.global_position = Vector3(2.2, 0.0, 0.0)
    await process_frame

    var cryo := PROJECTILE_SCRIPT.new()
    root.add_child(cryo)
    cryo.process_mode = Node.PROCESS_MODE_DISABLED
    cryo.spawn_secondary_fx = false
    cryo.setup(Vector3.ZERO, Vector3.RIGHT, 10.0, 20.0, Color.WHITE, "cryo")
    cryo._apply_protocol_hit(primary, 20.0)
    if primary.slow_multiplier >= 1.0 or primary.slow_left <= 0.0:
        push_error("Cryo did not apply slow")
        quit(1)
        return

    var inferno := PROJECTILE_SCRIPT.new()
    root.add_child(inferno)
    inferno.process_mode = Node.PROCESS_MODE_DISABLED
    inferno.spawn_secondary_fx = false
    inferno.setup(Vector3.ZERO, Vector3.RIGHT, 10.0, 20.0, Color.WHITE, "inferno")
    var nearby_before := nearby.health
    inferno._apply_protocol_hit(primary, 20.0)
    if nearby.health >= nearby_before:
        push_error("Inferno splash did not damage nearby enemy")
        quit(1)
        return

    var arc := PROJECTILE_SCRIPT.new()
    root.add_child(arc)
    arc.process_mode = Node.PROCESS_MODE_DISABLED
    arc.spawn_secondary_fx = false
    arc.setup(Vector3.ZERO, Vector3.RIGHT, 10.0, 20.0, Color.WHITE, "arc")
    var arc_one_before := nearby.health
    var arc_two_before := nearby_two.health
    arc._apply_protocol_hit(primary, 20.0)
    if nearby.health >= arc_one_before or nearby_two.health >= arc_two_before:
        push_error("Arc did not chain to two nearby enemies")
        quit(1)
        return

    var rail := PROJECTILE_SCRIPT.new()
    rail.setup(Vector3.ZERO, Vector3.RIGHT, 10.0, 20.0, Color.WHITE, "rail")
    if rail.pierce_remaining != 2:
        push_error("Rail pierce budget is incorrect")
        quit(1)
        return

    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
