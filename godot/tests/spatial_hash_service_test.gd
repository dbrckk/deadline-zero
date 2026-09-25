extends SceneTree

const SPATIAL_HASH := preload("res://scripts/SpatialHash.gd")

func _initialize() -> void:
    var hash = SPATIAL_HASH.new(4.0)
    var near := Node3D.new()
    var far := Node3D.new()
    near.position = Vector3(1.0, 0.0, 1.0)
    far.position = Vector3(12.0, 0.0, 0.0)

    hash.insert(near, near.position)
    hash.insert(far, far.position)

    var local_candidates: Array = hash.query(Vector3.ZERO, 3.5)
    if not local_candidates.has(near):
        push_error("Spatial hash missed nearby candidate")
        quit(1)
        return
    if local_candidates.has(far):
        push_error("Spatial hash returned distant candidate")
        quit(1)
        return

    hash.clear()
    if not hash.query(Vector3.ZERO, 20.0).is_empty():
        push_error("Spatial hash clear did not remove buckets")
        quit(1)
        return

    print("Deadline Zero spatial hash service: OK")
    quit(0)
