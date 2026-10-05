extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    target.global_position = Vector3.ZERO
    root.add_child(target)

    var first := DZXpOrb.new()
    first.target = target
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(12.0, 0.18, 0.0)

    var second := DZXpOrb.new()
    second.target = target
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    second.global_position = Vector3(14.0, 0.18, 0.0)
    await process_frame

    var first_visual := first.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var second_visual := second.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var first_halo := first.get_node_or_null("XpOrbHalo") as MeshInstance3D
    var second_halo := second.get_node_or_null("XpOrbHalo") as MeshInstance3D
    if first_visual == null or second_visual == null or first_halo == null or second_halo == null:
        push_error("XP orb premium core/halo production visual is missing")
        quit(1)
        return
    if first_visual.mesh != second_visual.mesh:
        push_error("XP orbs must reuse one shared mesh resource")
        quit(1)
        return
    if first_visual.material_override != second_visual.material_override:
        push_error("XP orbs must reuse one shared core material resource")
        quit(1)
        return
    if first_halo.mesh != second_halo.mesh or first_halo.material_override != second_halo.material_override:
        push_error("XP orbs must reuse shared halo mesh/material resources")
        quit(1)
        return
    if first_halo.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb halo must remain shadow-free")
        quit(1)
        return
    var halo_mesh := first_halo.mesh as TorusMesh
    if halo_mesh == null or halo_mesh.rings > 16 or halo_mesh.ring_segments > 6:
        push_error("XP orb halo geometry budget regressed")
        quit(1)
        return
    if first_visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb visual must not cast mobile-costly dynamic shadows")
        quit(1)
        return

    var mesh := first_visual.mesh as SphereMesh
    if mesh == null or mesh.radial_segments > 12 or mesh.rings > 6:
        push_error("XP orb geometry budget regressed")
        quit(1)
        return

    var initial_distance := first.global_position.distance_to(target.global_position)
    first.age = DZXpOrb.FORCED_MAGNET_AGE
    var halo_scale_before := first_halo.scale.x
    first._process(0.20)
    var forced_distance := first.global_position.distance_to(target.global_position)
    if forced_distance >= initial_distance:
        push_error("Old XP orb did not force-magnet toward the player")
        quit(1)
        return
    if first_halo.scale.x <= halo_scale_before:
        push_error("XP orb halo did not expand when entering magnetized pickup state")
        quit(1)
        return

    second.age = 0.0
    var second_initial := second.global_position
    second._process(0.20)
    if second.global_position.distance_to(second_initial) > 0.05:
        push_error("Fresh distant XP orb should remain parked until magnet range/age threshold")
        quit(1)
        return

    print("Deadline Zero XP orb runtime: OK")
    quit(0)
