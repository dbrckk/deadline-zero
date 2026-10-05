extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var show_start := enemy_source.find("func _show_telegraph")
    var impact_start := enemy_source.find("func _spawn_attack_impact")
    var show_block := enemy_source.substr(show_start, impact_start - show_start)
    var add_index := show_block.find("add_child(telegraph_visual)")
    var global_index := show_block.find("telegraph_visual.global_position")
    if add_index < 0 or global_index < 0 or global_index < add_index:
        push_error("Telegraph global transform must be assigned only after scene insertion")
        quit(1)
        return

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

    var telegraph_mesh := enemy.telegraph_visual as MeshInstance3D
    if telegraph_mesh == null or telegraph_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Attack telegraph ring must not cast dynamic shadows")
        quit(1)
        return
    var tick_mesh_resource: Mesh
    var tick_count := 0
    for child in enemy.telegraph_visual.get_children():
        if not child is MeshInstance3D:
            continue
        var tick := child as MeshInstance3D
        if not tick.name.begins_with("TelegraphTick_"):
            continue
        tick_count += 1
        if tick.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Telegraph ticks must not cast dynamic shadows")
            quit(1)
            return
        if tick_mesh_resource == null:
            tick_mesh_resource = tick.mesh
        elif tick.mesh != tick_mesh_resource:
            push_error("Telegraph ticks must reuse shared geometry for one attack radius")
            quit(1)
            return
    if tick_count != 8:
        push_error("Boss telegraph must expose eight premium directional ticks")
        quit(1)
        return
    var inner_ring := enemy.telegraph_visual.get_node_or_null("TelegraphInnerRing") as MeshInstance3D
    if inner_ring == null or inner_ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Boss telegraph must include a shadow-free inner danger ring")
        quit(1)
        return

    var first_ring_mesh := telegraph_mesh.mesh
    enemy._show_telegraph(1.75, 0.40)
    await process_frame
    var replacement_telegraph := enemy.telegraph_visual as MeshInstance3D
    if replacement_telegraph == null or replacement_telegraph.mesh != first_ring_mesh:
        push_error("Same-radius attack telegraphs must reuse ring mesh resources")
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
    if absf(enemy.telegraph_visual.rotation.y) <= 0.01:
        push_error("Boss telegraph ticks did not gain rotational escalation")
        quit(1)
        return

    print("Deadline Zero attack telegraph escalation: OK")
    quit(0)
