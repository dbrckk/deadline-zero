extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var boss := ENEMY_SCRIPT.new()
    boss.configure("boss", 1.0, target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    root.add_child(boss)
    await process_frame

    if not boss.has_method("_update_boss_phase"):
        push_error("Boss runtime phase API is missing")
        quit(1)
        return

    if boss.boss_aura_root == null or boss.boss_aura_inner_material == null or boss.boss_aura_outer_material == null:
        push_error("Boss premium threat aura did not initialize")
        quit(1)
        return
    var phase1_aura_color: Color = boss.boss_aura_inner_material.emission

    var phase_events: Array[int] = []
    boss.boss_phase_changed.connect(func(phase: int, _at: Vector3) -> void: phase_events.append(phase))

    var phase1_speed: float = boss.move_speed
    var phase1_damage: float = boss.contact_damage

    boss.health = boss.max_health * 0.60
    boss._update_boss_phase()
    if boss.boss_phase != 2:
        push_error("Boss did not enter phase II below 65% health")
        quit(1)
        return
    if boss.move_speed <= phase1_speed or boss.contact_damage <= phase1_damage:
        push_error("Boss phase II did not escalate movement and damage")
        quit(1)
        return
    if phase_events != [2]:
        push_error("Boss phase II transition event was not emitted exactly once")
        quit(1)
        return
    if boss._boss_aftershock_count() != 1:
        push_error("Boss phase II did not unlock one telegraphed aftershock")
        quit(1)
        return

    var phase2_speed: float = boss.move_speed
    var phase2_damage: float = boss.contact_damage
    boss.health = boss.max_health * 0.25
    boss._update_boss_phase()
    if boss.boss_phase != 3:
        push_error("Boss did not enter phase III below 30% health")
        quit(1)
        return
    if boss.move_speed <= phase2_speed or boss.contact_damage <= phase2_damage:
        push_error("Boss phase III did not escalate movement and damage")
        quit(1)
        return
    if phase_events != [2, 3]:
        push_error("Boss phase III transition event was not emitted exactly once")
        quit(1)
        return
    if boss._boss_aftershock_count() != 2:
        push_error("Boss phase III did not escalate to two telegraphed aftershocks")
        quit(1)
        return

    var phase3_aura_color: Color = boss.boss_aura_inner_material.emission
    if phase3_aura_color.is_equal_approx(phase1_aura_color):
        push_error("Boss phase escalation did not recolor threat aura")
        quit(1)
        return
    boss._update_boss_presence(0.16)
    if boss.boss_aura_root.rotation.y <= 0.0:
        push_error("Boss threat aura is not rotating")
        quit(1)
        return

    if boss._boss_slam_windup() >= 0.68 or boss._boss_slam_cooldown() >= 4.1:
        push_error("Boss phase III did not accelerate slam cadence")
        quit(1)
        return

    print("Deadline Zero boss phase runtime: OK")
    quit(0)
