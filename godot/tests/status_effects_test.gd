extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    enemy.spawn_secondary_fx = false
    root.add_child(enemy)
    await process_frame

    if not enemy.has_method("apply_burn") or not enemy.has_method("_process_status_effects"):
        push_error("Enemy burn status API is missing")
        quit(1)
        return

    var before := enemy.health
    enemy.apply_burn(8.0, 1.0)
    enemy._process_status_effects(0.5)
    if enemy.health >= before or enemy.burn_left <= 0.0:
        push_error("Burn status did not deal damage over time")
        quit(1)
        return

    enemy._process_status_effects(0.6)
    if enemy.burn_left > 0.0:
        push_error("Burn status did not expire after its duration")
        quit(1)
        return

    print("Deadline Zero enemy burn status: OK")
    quit(0)
