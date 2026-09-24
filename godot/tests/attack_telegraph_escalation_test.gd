extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var enemy := ENEMY_SCRIPT.new()
    root.add_child(enemy)
    await process_frame

    enemy.kind = "boss"
    enemy.global_position = Vector3.ZERO
    enemy.attack_target_position = Vector3(2.0, 0.0, 0.0)
    enemy._show_telegraph(1.75, 0.40)
    await process_frame

    if enemy.telegraph_visual == null or enemy.telegraph_material == null:
        push_error("Telegraph visual/material missing")
        quit(1)
        return

    var start_energy := enemy.telegraph_material.emission_energy_multiplier
    var start_alpha := enemy.telegraph_material.albedo_color.a
    await create_timer(0.22).timeout

    if enemy.telegraph_material.emission_energy_multiplier <= start_energy:
        push_error("Telegraph emission did not intensify")
        quit(1)
        return
    if enemy.telegraph_material.albedo_color.a <= start_alpha:
        push_error("Telegraph opacity did not intensify")
        quit(1)
        return
    if enemy.telegraph_visual.scale.x <= 0.42:
        push_error("Telegraph scale did not expand")
        quit(1)
        return

    print("Deadline Zero attack telegraph escalation: OK")
    quit(0)
