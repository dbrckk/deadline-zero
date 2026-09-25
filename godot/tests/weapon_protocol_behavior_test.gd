extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    if PROJECTILE_SCRIPT.protocol_pierce_budget("rail") != 2:
        push_error("Rail pierce budget is incorrect")
        quit(1)
        return

    if not is_equal_approx(PROJECTILE_SCRIPT.protocol_splash_radius("inferno"), 1.85):
        push_error("Inferno splash radius is incorrect")
        quit(1)
        return

    if PROJECTILE_SCRIPT.protocol_chain_targets("arc") != 2:
        push_error("Arc chain target count is incorrect")
        quit(1)
        return

    var slow := PROJECTILE_SCRIPT.protocol_slow("cryo")
    if slow.x >= 1.0 or slow.y <= 0.0:
        push_error("Cryo slow rule is incorrect")
        quit(1)
        return

    var target := Node3D.new()
    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.apply_slow(slow.x, slow.y)
    if enemy.slow_multiplier >= 1.0 or enemy.slow_left <= 0.0:
        push_error("Enemy slow state was not applied")
        quit(1)
        return

    var rail := PROJECTILE_SCRIPT.new()
    rail._apply_profile("rail")
    if rail.pierce_remaining != 2:
        push_error("Rail runtime profile did not consume deterministic rule")
        quit(1)
        return

    var inferno := PROJECTILE_SCRIPT.new()
    inferno._apply_profile("inferno")
    if not is_equal_approx(inferno.splash_radius, 1.85):
        push_error("Inferno runtime profile did not consume deterministic rule")
        quit(1)
        return

    var arc := PROJECTILE_SCRIPT.new()
    arc._apply_profile("arc")
    if arc.chain_targets != 2:
        push_error("Arc runtime profile did not consume deterministic rule")
        quit(1)
        return

    var parent := Node3D.new()
    parent.position = Vector3(9.0, 0.0, -4.0)
    get_root().add_child(parent)
    await process_frame
    var spawned := PROJECTILE_SCRIPT.new()
    spawned.process_mode = Node.PROCESS_MODE_DISABLED
    var spawn_origin := Vector3(2.0, 0.7, 3.0)
    spawned.setup(spawn_origin, Vector3.FORWARD, 10.0, 10.0, Color.WHITE, "vanguard")
    parent.add_child(spawned)
    await process_frame
    if spawned.global_position.distance_to(spawn_origin) > 0.001:
        push_error("Projectile setup did not preserve global spawn origin under a transformed parent")
        quit(1)
        return

    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
