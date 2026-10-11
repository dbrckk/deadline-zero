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

    # Normal movement must turn imported 3D bodies smoothly: agile runners
    # react faster than a massive boss, while attacks retain their exact
    # immutable windup facing (verified above).
    var runner_turn := DZEnemy.new()
    runner_turn.configure("runner", 1.0, player)
    runner_turn.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(runner_turn)
    var boss_turn := DZEnemy.new()
    boss_turn.configure("boss", 1.0, player)
    boss_turn.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss_turn)
    await process_frame
    runner_turn.rotation.y = 0.0
    boss_turn.rotation.y = 0.0
    runner_turn.velocity = Vector3.RIGHT * runner_turn.move_speed
    boss_turn.velocity = Vector3.RIGHT * boss_turn.move_speed
    runner_turn._update_locomotion_facing(0.016)
    boss_turn._update_locomotion_facing(0.016)
    if runner_turn.rotation.y >= -0.05 or runner_turn.rotation.y <= -1.5:
        push_error("Runner locomotion snapped or refused to turn toward actual planar velocity")
        quit(1)
        return
    if boss_turn.rotation.y <= runner_turn.rotation.y or boss_turn.rotation.y >= -0.01:
        push_error("Boss locomotion should turn deliberately slower than an agile runner")
        quit(1)
        return

    # A telegraphed attack/charge must not inherit the smooth chasing turn.
    var locked_turn := runner_turn.rotation.y
    runner_turn.attack_windup = 0.30
    runner_turn._update_locomotion_facing(0.25)
    if absf(angle_difference(locked_turn, runner_turn.rotation.y)) > 0.001:
        push_error("Locomotion smoothing broke locked windup/attack alignment")
        quit(1)
        return
    runner_turn.attack_windup = 0.0
    runner_turn.charge_active = true
    runner_turn._update_locomotion_facing(0.25)
    if absf(angle_difference(locked_turn, runner_turn.rotation.y)) > 0.001:
        push_error("Locomotion turn interfered with committed charge direction")
        quit(1)
        return
    runner_turn.charge_active = false
    runner_turn.velocity = Vector3.ZERO
    runner_turn._update_locomotion_facing(0.25)
    if absf(angle_difference(locked_turn, runner_turn.rotation.y)) > 0.001:
        push_error("Stopped enemy continued spinning on the spot")
        quit(1)
        return

    # -179° / 179° crossing must turn across the short seam, never through
    # the entire arena in the opposite direction.
    runner_turn.rotation.y = deg_to_rad(179.0)
    runner_turn.velocity = Vector3(0.01745, 0.0, 0.99985)
    runner_turn._update_locomotion_facing(0.016)
    if absf(angle_difference(deg_to_rad(179.0), runner_turn.rotation.y)) > 0.15:
        push_error("Enemy locomotion lost shortest-path wraparound yaw")
        quit(1)
        return

    print("Deadline Zero locked 3D attacks and weighted locomotion facing: OK")
    quit(0)
