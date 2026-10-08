extends SceneTree

# Real imported GLTF AnimationPlayer nodes, not string-only source assertions.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var survivor := DZPlayer.new()
    survivor.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(survivor)
    await process_frame
    if survivor.authored_anim == null:
        push_error("Authored survivor AnimationPlayer missing")
        quit(1)
        return

    survivor.velocity = Vector3(0.65, 0.0, 0.0)
    survivor._update_authored_animation()
    var slow_rate := survivor.authored_anim.speed_scale
    if survivor.current_anim != "Run_Gun" or slow_rate >= 1.0 or slow_rate < 0.68:
        push_error("Survivor walk acceleration does not reduce imported run clip speed")
        quit(1)
        return

    survivor.velocity = Vector3(survivor.move_speed, 0.0, 0.0)
    for _step in range(4):
        survivor._update_authored_animation()
    var full_rate := survivor.authored_anim.speed_scale
    if full_rate <= slow_rate or full_rate > 1.121:
        push_error("Survivor full-speed gait is not faster than acceleration gait")
        quit(1)
        return

    survivor.velocity = Vector3.ZERO
    survivor._update_authored_animation()
    if survivor.current_anim != "Idle_Gun" or not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor idle animation did not restore normal playback")
        quit(1)
        return

    survivor.velocity = Vector3(0.80, 0.0, 0.0)
    survivor._update_authored_animation()
    survivor.take_damage(4.0)
    if survivor.current_anim != "HitReact" or not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor hit reaction inherited slowed locomotion playback")
        quit(1)
        return
    survivor.set_combat_enabled(false)
    if not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor combat shutdown retained altered animation speed")
        quit(1)
        return

    for kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.kind = kind
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        enemy.configure(kind, 1.0, survivor)
        root.add_child(enemy)
        await process_frame
        if enemy.authored_anim == null:
            push_error("Imported enemy AnimationPlayer missing: %s" % kind)
            quit(1)
            return

        enemy.velocity = Vector3(enemy.move_speed * 0.42, 0.0, 0.0)
        enemy._update_authored_animation(10.0)
        var reduced_rate := enemy.authored_anim.speed_scale
        if reduced_rate < 0.38 or reduced_rate >= 1.0:
            push_error("Slow/standoff enemy gait did not retime: %s (%.3f)" % [kind, reduced_rate])
            quit(1)
            return

        enemy.velocity = Vector3(enemy.move_speed, 0.0, 0.0)
        for _step in range(4):
            enemy._update_authored_animation(10.0)
        if enemy.authored_anim.speed_scale <= reduced_rate:
            push_error("Full enemy pursuit kept a slower gait than standoff: %s" % kind)
            quit(1)
            return
        if enemy.kind == "boss" and enemy.authored_anim.speed_scale > 0.84:
            push_error("Boss must retain a weightier imported 3D gait")
            quit(1)
            return

        enemy.apply_shock(0.24)
        enemy._physics_process(0.02)
        if enemy.authored_anim.speed_scale != 0.0:
            push_error("Stunned enemy kept cycling its legs in place: %s" % kind)
            quit(1)
            return
        enemy.shock_left = 0.0
        enemy.velocity = Vector3(enemy.move_speed, 0.0, 0.0)
        enemy._update_authored_animation(10.0)
        if enemy.authored_anim.speed_scale <= 0.0:
            push_error("Enemy gait remained frozen after shock: %s" % kind)
            quit(1)
            return
        enemy.set_combat_enabled(false)
        if not is_equal_approx(enemy.authored_anim.speed_scale, 1.0):
            push_error("Enemy combat shutdown did not reset clip rate: %s" % kind)
            quit(1)
            return

        enemy.queue_free()
        await process_frame

    print("Deadline Zero authored 3D gait retiming and shock recovery: OK")
    quit(0)
