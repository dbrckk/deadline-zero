extends SceneTree

const ORB_SCRIPT := preload("res://scripts/XpOrb.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var orb := ORB_SCRIPT.new()
    root.add_child(orb)
    await process_frame

    var core := orb.get_node_or_null("XpCore") as MeshInstance3D
    var halo := orb.get_node_or_null("XpHalo") as MeshInstance3D
    if core == null or halo == null:
        push_error("XP orb is missing layered premium geometry")
        quit(1)
        return

    for child in orb.get_children():
        if child is OmniLight3D:
            push_error("XP orb should not use per-orb dynamic lights")
            quit(1)
            return

    print("Deadline Zero XP orb visual: OK")
    quit(0)
