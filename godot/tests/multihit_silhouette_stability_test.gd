extends SceneTree

# Combat-grade GLTF regression: simultaneous scatter/arc hits must not enlarge
# or displace an enemy silhouette by multiplying interrupted recoil tweens.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var survivor := Node3D.new()
    survivor.name = "TestSurvivor"
    survivor.position = Vector3(-2.0, 0.0, 0.0)
    root.add_child(survivor)
    await process_frame

    var archetypes := ["shambler", "runner", "charger", "harrier",
        "regenerator", "brute", "elite", "boss"]
    for kind in archetypes:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, survivor)
        root.add_child(enemy)
        enemy.set_combat_enabled(false)
        await process_frame

        var visual := enemy.get_node_or_null("Visual") as Node3D
        if visual == null or enemy.authored_visual == null:
            push_error("Imported enemy GLTF visual is absent: %s" % kind)
            quit(1)
            return
        var rest_scale := enemy.visual_rest_scale
        var rest_position := enemy.visual_rest_position
        if rest_scale.length_squared() < 0.001:
            push_error("Enemy authored silhouette baseline was not captured: %s" % kind)
            quit(1)
            return

        # An early hit must cancel the unfinished spawn reveal before retiming
        # the exact same visual root.
        enemy._play_hit_reaction(false, false)
        if enemy.spawn_reveal_tween != null and enemy.spawn_reveal_tween.is_running():
            push_error("Early shot left competing spawn/recoil tweens active: %s" % kind)
            quit(1)
            return

        for index in range(9):
            # Model the worst-case previous frame's recoil peak. The new hit
            # must discard this temporary visual displacement immediately.
            visual.scale = rest_scale * 1.35
            visual.position = rest_position + Vector3(0.5, 0.1, -0.4)
            enemy._play_hit_reaction(index % 2 == 0, false)
            if visual.scale.distance_to(rest_scale) > 0.0001 or visual.position.distance_to(rest_position) > 0.0001:
                push_error("Stacked recoil inflated or displaced silhouette: %s hit %d" % [kind, index])
                quit(1)
                return
            if enemy.hit_flash_visual == null or not enemy.hit_flash_visual.visible:
                push_error("Repeated hit lost visual confirmation: %s" % kind)
                quit(1)
                return

        await create_timer(0.28).timeout
        if visual.scale.distance_to(rest_scale) > 0.002 or visual.position.distance_to(rest_position) > 0.002:
            push_error("Interrupted recoil never settled back to imported rest pose: %s" % kind)
            quit(1)
            return
        if enemy.hit_flash_visual.visible:
            push_error("Repeated hit flash did not finish: %s" % kind)
            quit(1)
            return

        enemy._play_hit_reaction(true, true)
        await create_timer(0.28).timeout
        var kill_pose := rest_scale * 1.04
        if visual.scale.distance_to(kill_pose) > 0.004:
            push_error("Death punch did not respect bounded authored silhouette: %s" % kind)
            quit(1)
            return
        enemy.free()

    print("Deadline Zero rapid multihit 3D silhouette stability: OK (8 archetypes x 10 hits)")
    quit(0)
