extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("charger", 1.0, target)
    enemy.spawn_secondary_fx = false
    root.add_child(enemy)
    enemy.velocity = Vector3(3.0, 0.0, 0.0)
    enemy.attack_windup = 0.5
    enemy.pending_special = "charge"

    var telegraph := Node3D.new()
    root.add_child(telegraph)
    enemy.telegraph_visual = telegraph

    var projectile := PROJECTILE_SCRIPT.new()
    root.add_child(projectile)
    projectile.velocity = Vector3(5.0, 0.0, 0.0)

    enemy.set_combat_enabled(false)
    projectile.set_combat_enabled(false)
    await process_frame

    if enemy.combat_enabled:
        push_error("Enemy combat freeze flag was not disabled")
        quit(1)
        return
    if enemy.velocity.length_squared() > 0.0:
        push_error("Enemy velocity was not cleared")
        quit(1)
        return
    if enemy.attack_windup > 0.0 or not enemy.pending_special.is_empty():
        push_error("Enemy telegraphed attack was not cancelled")
        quit(1)
        return
    if enemy.telegraph_visual != null:
        push_error("Enemy telegraph reference was not cleared")
        quit(1)
        return
    if projectile.combat_enabled:
        push_error("Projectile combat freeze flag was not disabled")
        quit(1)
        return
    if projectile.velocity.length_squared() > 0.0:
        push_error("Projectile velocity was not cleared")
        quit(1)
        return

    print("Deadline Zero run-end combat freeze: OK")
    quit(0)
