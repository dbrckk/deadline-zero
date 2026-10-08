extends SceneTree

const MAIN_SCRIPT := preload("res://scripts/Main.gd")

func _initialize() -> void:
    var arena := Node3D.new()
    get_root().add_child(arena)
    current_scene = arena

    var view_camera := Camera3D.new()
    arena.add_child(view_camera)
    # SceneTree._initialize() runs before initial nodes finish entering the tree.
    # Project/look-at calls require a live viewport and a valid world transform.
    await process_frame
    view_camera.global_position = Vector3(0.0, 12.8, 9.15)
    view_camera.look_at(Vector3.ZERO, Vector3.UP)
    view_camera.current = true
    await process_frame

    var viewport_size := get_root().get_visible_rect().size
    var main := MAIN_SCRIPT.new()

    var near_elite := _make_threat(arena, "elite", Vector3(20.0, 0.0, 0.0))
    var far_elite := _make_threat(arena, "elite", Vector3(27.0, 0.0, 0.0))
    var far_boss := _make_threat(arena, "boss", Vector3(-29.0, 0.0, 0.0))
    var visible_boss := _make_threat(arena, "boss", Vector3.ZERO)
    var ordinary := _make_threat(arena, "shambler", Vector3(-24.0, 0.0, 0.0))

    if main._select_offscreen_threat([near_elite, far_boss], Vector3.ZERO, view_camera, viewport_size) != far_boss:
        push_error("Offscreen boss should outrank nearer elite")
        quit(1)
        return

    if main._select_offscreen_threat([visible_boss, near_elite], Vector3.ZERO, view_camera, viewport_size) != near_elite:
        push_error("Visible boss must not hide offscreen elite warning")
        quit(1)
        return

    if main._select_offscreen_threat([far_elite, near_elite], Vector3.ZERO, view_camera, viewport_size) != near_elite:
        push_error("Nearest elite must win same-priority tie")
        quit(1)
        return

    far_boss.dead = true
    if main._select_offscreen_threat([far_boss, far_elite], Vector3.ZERO, view_camera, viewport_size) != far_elite:
        push_error("Dead boss must not suppress live offscreen warning")
        quit(1)
        return

    if main._select_offscreen_threat([visible_boss, ordinary], Vector3.ZERO, view_camera, viewport_size) != null:
        push_error("Visible boss or ordinary zombie triggered an unnecessary warning")
        quit(1)
        return

    if main._select_offscreen_threat([], Vector3.ZERO, view_camera, viewport_size) != null:
        push_error("Empty threat list should clear offscreen indicator")
        quit(1)
        return

    main.free()
    print("Deadline Zero offscreen threat priority and visibility: OK")
    quit(0)

func _make_threat(arena: Node3D, threat_kind: String, at: Vector3) -> DZEnemy:
    var enemy := DZEnemy.new()
    enemy.kind = threat_kind
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    arena.add_child(enemy)
    enemy.global_position = at
    return enemy
