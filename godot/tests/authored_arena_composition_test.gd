extends SceneTree

const MAIN_SCRIPT := preload("res://scripts/Main.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var world := Node3D.new()
    world.set_script(MAIN_SCRIPT)
    root.add_child(world)
    await process_frame

    var authored := get_nodes_in_group("authored_environment")
    if authored.size() < 16:
        push_error("Expected authored environment barrier composition")
        quit(1)
        return

    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if source.contains("var prop := MeshInstance3D.new()") or source.contains("var box := BoxMesh.new()"):
        push_error("Generic random box environment fallback still present")
        quit(1)
        return
    if not source.contains("env.fog_enabled = true"):
        push_error("Arena atmosphere fog is missing")
        quit(1)
        return

    print("Deadline Zero authored arena composition: OK")
    quit(0)
