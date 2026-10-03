extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/EnemyProjectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var projectile := PROJECTILE_SCRIPT.new()
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
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

    var core := projectile.get_node_or_null("HarrierBoltCore") as MeshInstance3D
    var halo := projectile.get_node_or_null("HarrierBoltHalo") as MeshInstance3D
    if core == null or halo == null:
        push_error("Harrier bolt core/halo visual is missing")
        quit(1)
        return
    for mesh_instance in [core, halo, trail]:
        if (mesh_instance as MeshInstance3D).cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Harrier bolt visuals must not cast mobile-costly shadows")
            quit(1)
            return
    var core_mesh := core.mesh as SphereMesh
    var halo_mesh := halo.mesh as SphereMesh
    if core_mesh == null or core_mesh.radial_segments > 10 or core_mesh.rings > 5:
        push_error("Harrier bolt core geometry budget regressed")
        quit(1)
        return
    if halo_mesh == null or halo_mesh.radial_segments > 10 or halo_mesh.rings > 5:
        push_error("Harrier bolt halo geometry budget regressed")
        quit(1)
        return

    var duplicate := PROJECTILE_SCRIPT.new()
    duplicate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(duplicate)
    await process_frame
    var duplicate_core := duplicate.get_node_or_null("HarrierBoltCore") as MeshInstance3D
    var duplicate_halo := duplicate.get_node_or_null("HarrierBoltHalo") as MeshInstance3D
    var duplicate_trail := duplicate.get_node_or_null("HarrierBoltTrail") as MeshInstance3D
    if duplicate_core == null or duplicate_halo == null or duplicate_trail == null:
        push_error("Duplicate harrier bolt visual is incomplete")
        quit(1)
        return
    if duplicate_core.mesh != core.mesh or duplicate_halo.mesh != halo.mesh or duplicate_trail.mesh != trail.mesh:
        push_error("Harrier bolt instances must reuse shared mesh resources")
        quit(1)
        return
    if duplicate_core.material_override != core.material_override or duplicate_halo.material_override != halo.material_override or duplicate_trail.material_override != trail.material_override:
        push_error("Harrier bolt instances must reuse shared materials")
        quit(1)
        return

    print("Deadline Zero enemy projectile visual: OK")
    quit(0)
