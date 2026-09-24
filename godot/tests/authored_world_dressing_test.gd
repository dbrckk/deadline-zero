extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var scene := load("res://scenes/Main.tscn") as PackedScene
    if scene == null:
        push_error("Main scene failed to load")
        quit(1)
        return

    var main := scene.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    var props := get_nodes_in_group("environment_props")
    var ground_details := get_nodes_in_group("environment_ground_detail")

    if props.size() != 34:
        push_error("Expected 34 authored environment props, got %d" % props.size())
        quit(1)
        return
    if ground_details.size() != 10:
        push_error("Expected 10 authored street-damage details, got %d" % ground_details.size())
        quit(1)
        return

    for node in props:
        if not node is Node3D:
            push_error("Environment prop is not a Node3D")
            quit(1)
            return
        if not str(node.name).begins_with("EnvironmentProp_"):
            push_error("Unexpected environment prop name: %s" % node.name)
            quit(1)
            return

    print("Deadline Zero authored world dressing: OK")
    quit(0)
