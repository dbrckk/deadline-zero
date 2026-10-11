extends SceneTree

class AutoAimSpatialScene:
    extends Node3D
    var candidates: Array = []
    var query_calls := 0
    var query_radius := -1.0

    func query_enemies_near(_position: Vector3, radius: float) -> Array:
        query_calls += 1
        query_radius = radius
        return candidates

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

    # The actual player search must reuse the production scene's spatial
    # query rather than scanning all enemy group members on every refresh.
    # Existing standalone fixtures above also verify the no-service fallback.
    var indexed_scene := AutoAimSpatialScene.new()
    get_root().add_child(indexed_scene)
    current_scene = indexed_scene
    indexed_scene.candidates = [first]
    player.current_target = null
    player.nearest_threat = null
    player.target_refresh_clock = 0.0
    if player._combat_target() != first:
        push_error("Spatial-indexed autoaim did not select a nearby live enemy")
        quit(1)
        return
    if indexed_scene.query_calls != 1 or absf(indexed_scene.query_radius - DZPlayer.TARGET_ACQUIRE_RADIUS) > 0.001:
        push_error("Spatial-indexed autoaim ignored production neighborhood radius")
        quit(1)
        return

    # A stale index may contain a freed or dead enemy. It must never be
    # selected, including after a target-refresh invalidation.
    var dead_neighbor := DZEnemy.new()
    dead_neighbor.configure("runner", 1.0, player)
    dead_neighbor.process_mode = Node.PROCESS_MODE_DISABLED
    indexed_scene.add_child(dead_neighbor)
    dead_neighbor.global_position = Vector3(0.8, 0.0, 0.0)
    dead_neighbor.dead = true
    indexed_scene.candidates = [dead_neighbor, first]
    player.current_target = null
    player.target_refresh_clock = 0.0
    if player._combat_target() != first or indexed_scene.query_calls != 2:
        push_error("Spatial autoaim selected an excluded/dead candidate or bypassed refresh")
        quit(1)
        return

    print("Deadline Zero player targeting stability and spatial autoaim: OK")
    quit(0)
