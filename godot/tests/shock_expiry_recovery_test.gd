extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := Node3D.new()
    target.position = Vector3(9.0, 0.0, 0.0)
    root.add_child(target)
    await process_frame

    for kind in ["shambler", "runner", "charger", "harrier", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, target)
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        root.add_child(enemy)
        await process_frame
        enemy.shock_left = 0.005
        enemy.velocity = Vector3(2.0, 0.0, 0.0)
        enemy._physics_process(0.016)
        if enemy.shock_left > 0.0 or enemy.velocity.length_squared() > 0.0001:
            push_error("Final stunned physics frame leaked movement: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim != null and enemy.authored_anim.speed_scale != 0.0:
            push_error("Final stunned frame did not freeze imported animation: %s" % kind)
            quit(1)
            return

        enemy._physics_process(0.016)
        if enemy.velocity.length_squared() < 0.01:
            push_error("Enemy failed to recover pursuit on next physics frame: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim != null and enemy.authored_anim.speed_scale <= 0.0:
            push_error("Enemy remained animation-frozen after shock recovery: %s" % kind)
            quit(1)
            return
        enemy.queue_free()
        await process_frame

    print("Deadline Zero shock expiry recovery: OK (7 imported enemy archetypes)")
    quit(0)
