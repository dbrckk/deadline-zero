extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame
    var marker := player.get_node_or_null("TargetLockReticle") as MeshInstance3D
    if marker == null or marker.mesh == null or marker.visible:
        push_error("Target lock must exist but remain hidden without a target")
        quit(1)
        return
    if marker.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Target lock must not cast dynamic shadows")
        quit(1)
        return
    if marker.mesh.get_surface_count() != 1 or marker.mesh.surface_get_array_len(0) != 192:
        push_error("Target lock must use a single bounded 3D arc surface")
        quit(1)
        return
    var enemy := DZEnemy.new()
    enemy.configure("runner", 1.0, player)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    enemy.global_position = Vector3(4.0, 0.0, -2.0)
    player._update_target_lock_marker(enemy, 0.016)
    if not marker.visible or marker.global_position.distance_to(Vector3(4.0, 0.070, -2.0)) > 0.01:
        push_error("World-space aim reticle did not snap to its initial target")
        quit(1)
        return
    enemy.global_position = Vector3(5.0, 0.0, -2.0)
    player._update_target_lock_marker(enemy, 0.016)
    if marker.global_position.x <= 4.0 or marker.global_position.x >= 5.0:
        push_error("World-space reticle must smoothly follow a moving target")
        quit(1)
        return
    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, player)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss)
    boss.global_position = Vector3(-3.0, 0.0, 2.0)
    player._update_target_lock_marker(boss, 0.016)
    if marker.global_position.distance_to(Vector3(-3.0, 0.070, 2.0)) > 0.01 or marker.scale.x < 1.8:
        push_error("Switching aim target must snap instantly and scale for boss silhouette")
        quit(1)
        return
    boss.dead = true
    player._update_target_lock_marker(boss, 0.016)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Dead targets must immediately clear the lock indicator")
        quit(1)
        return
    player._update_target_lock_marker(enemy, 0.016)
    player.set_combat_enabled(false)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Combat shutdown must clear the target indicator")
        quit(1)
        return
    print("Deadline Zero world-space target lock: OK")
    quit(0)
