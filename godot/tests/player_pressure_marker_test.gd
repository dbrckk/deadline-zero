extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    var enemy := DZEnemy.new()
    enemy.configure("shambler", 1.0, player)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    player.global_position = Vector3.ZERO
    enemy.global_position = Vector3(1.6, 0.0, 0.0)
    player._update_player_marker_pressure(enemy)

    var locator := player.get_node_or_null("PlayerPressureLocator") as Node3D
    if locator == null or not locator.visible:
        push_error("Close melee pressure must show the elevated player locator")
        quit(1)
        return
    if not player.player_marker_pressure:
        push_error("Player pressure state was not activated")
        quit(1)
        return
    if player.player_marker_ring == null or player.player_marker_ring.scale.x < 1.09:
        push_error("Player pressure ring did not expand")
        quit(1)
        return

    player.set_combat_enabled(false)
    if player.player_marker_pressure:
        push_error("Combat shutdown must clear stale player pressure state")
        quit(1)
        return
    if locator.visible:
        push_error("Combat shutdown must hide the player pressure locator")
        quit(1)
        return
    if player.player_marker_ring == null or not player.player_marker_ring.scale.is_equal_approx(Vector3.ONE):
        push_error("Combat shutdown must restore the player marker ring scale")
        quit(1)
        return

    player.set_combat_enabled(true)
    player._update_player_marker_pressure(enemy)
    if not player.player_marker_pressure or not locator.visible:
        push_error("Player pressure feedback must recover when combat resumes")
        quit(1)
        return

    enemy.global_position = Vector3(4.0, 0.0, 0.0)
    player._update_player_marker_pressure(enemy)
    if player.player_marker_pressure or locator.visible:
        push_error("Distant enemies must not keep the pressure locator active")
        quit(1)
        return

    print("Deadline Zero player pressure marker: OK")
    quit(0)
