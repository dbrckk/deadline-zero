extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

class QueryScene:
    extends Node3D
    var query_called := false

    func query_enemies_near(_position: Vector3, _radius: float) -> Array:
        query_called = true
        return []

func _initialize() -> void:
    var script := load("res://scripts/SpatialHash.gd")
    if script == null:
        push_error("Spatial hash service is missing")
        quit(1)
        return

    var root := Node3D.new()
    get_root().add_child(root)
    var index = script.new(4.0)
    var near_enemy := Node3D.new()
    near_enemy.position = Vector3(1.0, 0.0, 1.0)
    root.add_child(near_enemy)
    var far_enemy := Node3D.new()
    far_enemy.position = Vector3(12.0, 0.0, 12.0)
    root.add_child(far_enemy)
    await process_frame

    index.rebuild([near_enemy, far_enemy])
    var nearby: Array = index.query(Vector3.ZERO, 3.0)
    if not nearby.has(near_enemy) or nearby.has(far_enemy):
        push_error("Spatial hash query did not isolate nearby enemies")
        quit(1)
        return

    # A 60 Hz rebuild must never return stale nodes from the previous cell.
    # Exercise both moving enemies and a completely empty frame: pooled arrays
    # must be cleared before any subsequent query can reuse them.
    near_enemy.position = Vector3(18.0, 0.0, 18.0)
    index.rebuild([near_enemy, far_enemy])
    if not index.query(Vector3(18.0, 0.0, 18.0), 2.0).has(near_enemy) or not index.query(Vector3.ZERO, 3.0).is_empty():
        push_error("Spatial hash retained stale occupants after enemy moved")
        quit(1)
        return
    index.rebuild([])
    if not index.query(Vector3(18.0, 0.0, 18.0), 2.0).is_empty() or not index.buckets.is_empty():
        push_error("Spatial hash recycled an uncleared bucket after empty rebuild")
        quit(1)
        return
    near_enemy.position = Vector3(1.0, 0.0, 1.0)
    index.rebuild([near_enemy, far_enemy])
    if not index.query(Vector3.ZERO, 3.0).has(near_enemy) or index.query(Vector3.ZERO, 3.0).has(far_enemy):
        push_error("Spatial hash recycled bucket no longer isolates the original near enemy")
        quit(1)
        return
    if index._recycled_buckets.size() > 2:
        push_error("Spatial hash pool grew beyond the peak simultaneously occupied buckets")
        quit(1)
        return

    var query_scene := QueryScene.new()
    get_root().add_child(query_scene)
    current_scene = query_scene
    var projectile := PROJECTILE_SCRIPT.new()
    query_scene.add_child(projectile)
    await process_frame
    if not projectile.has_method("_candidate_enemies"):
        push_error("Projectile spatial-query integration is missing")
        quit(1)
        return
    projectile._candidate_enemies()
    if not query_scene.query_called:
        push_error("Projectile did not consume scene spatial query")
        quit(1)
        return

    print("Deadline Zero enemy spatial hash: OK")
    quit(0)
