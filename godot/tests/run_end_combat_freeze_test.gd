extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const XP_ORB_SCRIPT := preload("res://scripts/XpOrb.gd")

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

    var orb := XP_ORB_SCRIPT.new()
    orb.target = target
    root.add_child(orb)
    orb.position = Vector3(1.0, 0.18, 0.0)
    orb.velocity = Vector3(-6.0, 0.0, 0.0)

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

    var orb_before := orb.global_position
    orb.set_combat_enabled(false)
    orb._process(0.5)
    if orb.combat_enabled:
        push_error("XP orb combat freeze flag was not disabled")
        quit(1)
        return
    if orb.velocity.length_squared() > 0.0:
        push_error("XP orb velocity was not cleared")
        quit(1)
        return
    if orb.global_position.distance_to(orb_before) > 0.0001:
        push_error("XP orb moved after run-end combat freeze")
        quit(1)
        return

    print("Deadline Zero run-end combat freeze: OK")
    quit(0)
