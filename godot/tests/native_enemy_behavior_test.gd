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

    var charger := ENEMY_SCRIPT.new()
    charger.configure("charger", 1.0, target)
    charger.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(charger)
    if charger.move_speed <= 2.0 or charger.contact_damage < 12.0 or charger.xp_value < 4:
        push_error("Charger baseline identity is incorrect")
        quit(1)
        return

    var harrier := ENEMY_SCRIPT.new()
    harrier.configure("harrier", 1.0, target)
    harrier.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(harrier)
    if harrier.move_speed <= charger.move_speed or harrier.max_health >= charger.max_health:
        push_error("Harrier mobility/risk identity is incorrect")
        quit(1)
        return

    var regenerator := ENEMY_SCRIPT.new()
    regenerator.configure("regenerator", 1.0, target)
    regenerator.process_mode = Node.PROCESS_MODE_DISABLED
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
    charger._resolve_telegraphed_attack()
    if charger.global_position.x <= 2.5 or target.damage_taken <= 0.0:
        push_error("Charger special did not advance and damage target")
        quit(1)
        return

    var harrier_target := DummyTarget.new()
    root.add_child(harrier_target)
    var shooter := ENEMY_SCRIPT.new()
    shooter.configure("harrier", 1.0, harrier_target)
    shooter.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(shooter)
    shooter.pending_special = "harrier_shot"
    shooter._resolve_telegraphed_attack()
    if harrier_target.damage_taken <= 0.0:
        push_error("Harrier ranged special did not damage target")
        quit(1)
        return

    print("Deadline Zero native enemy behaviors: OK")
    quit(0)
