extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    player.target_refresh_clock = 0.0
    if player._combat_target() != null or player.target_refresh_clock <= 0.0:
        push_error("Auto-aim did not arm a no-target refresh cooldown")
        quit(1)
        return

    var first := DZEnemy.new()
    first.configure("shambler", 1.0, player)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(DZPlayer.TARGET_ACQUIRE_RADIUS + 4.0, 0.0, 0.0)

    player.target_refresh_clock = 0.0
    if player._combat_target() != null:
        push_error("Auto-aim acquired an enemy beyond the gameplay camera engagement radius")
        quit(1)
        return

    first.global_position = Vector3(4.0, 0.0, 0.0)

    var second := DZEnemy.new()
    second.configure("runner", 1.0, player)
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    second.global_position = Vector3(4.5, 0.0, 0.0)
    await process_frame

    player.global_position = Vector3.ZERO
    if player._combat_target() != null:
        push_error("Auto-aim bypassed its no-target refresh window")
        quit(1)
        return
    player.target_refresh_clock = 0.0
    var selected := player._combat_target()
    if selected != first:
        push_error("Auto-aim did not select the nearest initial target")
        quit(1)
        return

    second.global_position = Vector3(3.5, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim switched targets for an insignificant distance gain")
        quit(1)
        return

    if player.nearest_threat != second:
        push_error("Close-threat tracking must follow the actual nearest enemy independently of aim lock")
        quit(1)
        return

    second.global_position = Vector3(2.8, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim did not switch to a materially closer threat")
        quit(1)
        return

    first.global_position = Vector3(1.5, 0.0, 0.0)
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim ignored its target refresh window")
        quit(1)
        return

    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim did not reconsider targets after refresh interval")
        quit(1)
        return

    first.global_position = Vector3(DZPlayer.TARGET_ACQUIRE_RADIUS + 0.5, 0.0, 0.0)
    player.target_refresh_clock = DZPlayer.TARGET_REFRESH_INTERVAL
    selected = player._combat_target()
    if selected == first:
        push_error("Cached auto-aim target remained locked after leaving engagement radius")
        quit(1)
        return

    first.global_position = Vector3(1.5, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim failed to reacquire target after returning inside engagement radius")
        quit(1)
        return

    first.dead = true
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim did not immediately abandon a dead target")
        quit(1)
        return

    first.dead = false
    first.global_position = Vector3(1.5, 0.0, 0.0)
    player.nearest_threat = second
    second.free()
    var pressure_target := player._pressure_target(first)
    if pressure_target != first or player.nearest_threat != null:
        push_error("Pressure targeting retained a freed nearest-threat reference")
        quit(1)
        return

    player.set_combat_enabled(false)
    if player.current_target != null or player.nearest_threat != null or player.target_refresh_clock > 0.0:
        push_error("Combat shutdown did not clear auto-aim/threat target state")
        quit(1)
        return

    print("Deadline Zero player targeting stability: OK")
    quit(0)
