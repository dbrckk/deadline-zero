extends SceneTree

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

    print("Deadline Zero enemy spatial hash: OK")
    quit(0)
