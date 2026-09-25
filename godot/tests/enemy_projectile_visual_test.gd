extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/EnemyProjectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var projectile := PROJECTILE_SCRIPT.new()
    root.add_child(projectile)
    await process_frame

    var light_count := 0
    var trail := projectile.get_node_or_null("HarrierBoltTrail") as MeshInstance3D
    for child in projectile.get_children():
        if child is OmniLight3D:
            light_count += 1

    if light_count != 0:
        push_error("Harrier bolt should avoid per-projectile dynamic lights on mobile")
        quit(1)
        return
    if trail == null:
        push_error("Harrier bolt is missing emissive travel-direction trail")
        quit(1)
        return

    print("Deadline Zero enemy projectile visual: OK")
    quit(0)
