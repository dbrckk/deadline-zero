extends SceneTree

# An authored attack clip should face its world-space warning, never the
# sideways steering direction inherited from swarm avoidance / strafing.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var player := Node3D.new()
    player.position = Vector3(4.0, 0.0, 1.6)
    root.add_child(player)
    await process_frame

    for kind in ["charger", "harrier", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, player)
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        root.add_child(enemy)
        await process_frame
        enemy.global_position = Vector3.ZERO
        enemy.look_at(Vector3(-3.0, 0.0, 0.0), Vector3.UP)
        enemy.velocity = Vector3(2.0, 0.0, 1.0)

        enemy.pending_special = "charge" if kind == "charger" else (
            "harrier_shot" if kind == "harrier" else ""
        )
        var locked := player.global_position
        enemy._begin_telegraphed_attack(0.52, locked)
        var expected := (locked - enemy.global_position).normalized()
        var facing := (-enemy.global_transform.basis.z).normalized()
        if facing.dot(expected) < 0.995:
            push_error("Attack anticipation body did not face its danger corridor: %s" % kind)
            quit(1)
            return
        if enemy.velocity.length_squared() > 0.0001 or enemy.attack_windup < 0.5:
            push_error("Attack anticipation did not freeze momentum before warning: %s" % kind)
            quit(1)
            return
        if enemy.telegraph_visual == null or enemy.authored_anim == null:
            push_error("Attack no longer drives real GLTF body and danger telegraph: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim.has_animation("Idle_Attack") and enemy.current_anim != "Idle_Attack":
            push_error("Enemy windup did not enter imported attack animation: %s" % kind)
            quit(1)
            return

        # Moving the target during the warning must *not* retarget the tell or
        # rotate the attacker. A dodge remains meaningful.
        player.global_position = Vector3(-1.0, 0.0, 6.0)
        enemy._physics_process(0.10)
        if enemy.attack_target_position.distance_to(locked) > 0.001:
            push_error("Enemy telegraph unfairly followed survivor dodge: %s" % kind)
            quit(1)
            return
        if (-enemy.global_transform.basis.z).normalized().dot(expected) < 0.995:
            push_error("Enemy windup turned toward dodging player: %s" % kind)
            quit(1)
            return
        enemy.set_combat_enabled(false)
        enemy.queue_free()
        player.global_position = locked
        await process_frame

    var overlap := DZEnemy.new()
    overlap.configure("elite", 1.0, player)
    overlap.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(overlap)
    await process_frame
    overlap.global_position = player.global_position
    overlap._begin_telegraphed_attack(0.32, overlap.global_position)
    if overlap.attack_windup <= 0.0 or overlap.telegraph_visual == null:
        push_error("Zero-range telegraph cannot safely begin")
        quit(1)
        return

    print("Deadline Zero locked 3D attack anticipation alignment: OK (4 archetypes)")
    quit(0)
