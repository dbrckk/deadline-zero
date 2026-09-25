extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

class DummyTarget:
    extends Node3D
    var damage_taken := 0.0
    func take_damage(amount: float) -> void:
        damage_taken += amount

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := DummyTarget.new()
    root.add_child(target)
    await physics_frame

    var charger := ENEMY_SCRIPT.new()
    charger.configure("charger", 1.0, target)
    charger.process_mode = Node.PROCESS_MODE_DISABLED
    charger.spawn_secondary_fx = false
    root.add_child(charger)
    await physics_frame
    if charger.move_speed <= 2.0 or charger.contact_damage < 12.0 or charger.xp_value < 4:
        push_error("Charger baseline identity is incorrect")
        quit(1)
        return

    var harrier := ENEMY_SCRIPT.new()
    harrier.configure("harrier", 1.0, target)
    harrier.process_mode = Node.PROCESS_MODE_DISABLED
    harrier.spawn_secondary_fx = false
    root.add_child(harrier)
    if harrier.move_speed <= charger.move_speed or harrier.max_health >= charger.max_health:
        push_error("Harrier mobility/risk identity is incorrect")
        quit(1)
        return

    var regenerator := ENEMY_SCRIPT.new()
    regenerator.configure("regenerator", 1.0, target)
    regenerator.process_mode = Node.PROCESS_MODE_DISABLED
    regenerator.spawn_secondary_fx = false
    root.add_child(regenerator)
    var full_health: float = regenerator.health
    regenerator.health = full_health * 0.50
    regenerator._regenerate()
    if regenerator.health <= full_health * 0.50 or regenerator.health > full_health:
        push_error("Regenerator healing behavior is incorrect")
        quit(1)
        return

    charger.pending_special = "charge"
    charger.attack_target_position = Vector3(4.0, 0.0, 0.0)
    charger.global_position = Vector3.ZERO
    target.global_position = Vector3(3.6, 0.0, 0.0)
    target.damage_taken = 0.0
    charger._resolve_telegraphed_attack()
    if not charger.charge_active or target.damage_taken > 0.0:
        push_error("Charger special should start a real dash before dealing damage")
        quit(1)
        return
    for i in range(5):
        charger._process_charge(0.10)
    if target.damage_taken <= 0.0 or charger.global_position.x <= 2.5:
        push_error("Charger dash did not advance through and damage its target")
        quit(1)
        return

    var dodge_target := DummyTarget.new()
    root.add_child(dodge_target)
    dodge_target.global_position = Vector3(4.0, 0.0, 0.0)
    await physics_frame
    var dodge_charger := ENEMY_SCRIPT.new()
    dodge_charger.configure("charger", 1.0, dodge_target)
    dodge_charger.process_mode = Node.PROCESS_MODE_DISABLED
    dodge_charger.spawn_secondary_fx = false
    root.add_child(dodge_charger)
    await physics_frame
    dodge_charger.global_position = Vector3.ZERO
    dodge_charger.attack_target_position = dodge_target.global_position
    dodge_charger.pending_special = "charge"
    dodge_charger._resolve_telegraphed_attack()
    dodge_target.global_position = Vector3(4.0, 0.0, 3.0)
    for i in range(6):
        dodge_charger._process_charge(0.10)
    if dodge_target.damage_taken > 0.0:
        push_error("Charger dash incorrectly tracked a laterally dodging target")
        quit(1)
        return

    var harrier_target := DummyTarget.new()
    root.add_child(harrier_target)
    await process_frame
    var shooter := ENEMY_SCRIPT.new()
    shooter.configure("harrier", 1.0, harrier_target)
    shooter.process_mode = Node.PROCESS_MODE_DISABLED
    shooter.spawn_secondary_fx = false
    root.add_child(shooter)
    await process_frame
    shooter.global_position = Vector3.ZERO
    harrier_target.global_position = Vector3(4.0, 0.0, 0.0)
    shooter.attack_target_position = harrier_target.global_position
    shooter.pending_special = "harrier_shot"
    shooter._resolve_telegraphed_attack()
    await process_frame
    var hostile_projectiles := get_nodes_in_group("hostile_projectiles")
    if hostile_projectiles.size() != 1:
        push_error("Harrier ranged special did not spawn exactly one hostile projectile")
        quit(1)
        return
    var shot := hostile_projectiles[0] as DZEnemyProjectile
    for i in range(8):
        shot._physics_process(0.10)
    if harrier_target.damage_taken <= 0.0:
        push_error("Harrier projectile did not damage target on impact")
        quit(1)
        return

    var harrier_dodge_target := DummyTarget.new()
    root.add_child(harrier_dodge_target)
    harrier_dodge_target.global_position = Vector3(4.0, 0.0, 0.0)
    await process_frame
    var dodge_shot := DZEnemyProjectile.new()
    root.add_child(dodge_shot)
    dodge_shot.global_position = Vector3.ZERO
    dodge_shot.configure(harrier_dodge_target.global_position, harrier_dodge_target, 10.0, 8.0)
    harrier_dodge_target.global_position = Vector3(4.0, 0.0, 3.0)
    for i in range(10):
        dodge_shot._physics_process(0.10)
    if harrier_dodge_target.damage_taken > 0.0:
        push_error("Harrier projectile incorrectly homed into a dodging target")
        quit(1)
        return

    print("Deadline Zero native enemy behaviors: OK")
    quit(0)
