extends SceneTree

const IMPACT_SCRIPT := preload("res://scripts/ImpactFx.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var fx := IMPACT_SCRIPT.new()
    fx.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(fx)
    await process_frame

    var light_count := 0
    var mesh_count := 0
    var ring_found := false
    for child in fx.get_children():
        if child is OmniLight3D:
            light_count += 1
        if child is MeshInstance3D:
            mesh_count += 1
            if child.name == "ImpactRing":
                ring_found = true

    if light_count != 0:
        push_error("Impact FX should avoid per-hit dynamic lights on mobile")
        quit(1)
        return
    if mesh_count < 2 or not ring_found:
        push_error("Impact FX is missing layered emissive geometry")
        quit(1)
        return

    var core := fx.get_node_or_null("ImpactCore") as MeshInstance3D
    var ring := fx.get_node_or_null("ImpactRing") as MeshInstance3D
    if core == null or ring == null:
        push_error("Impact FX core/ring nodes are missing")
        quit(1)
        return
    if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Impact FX emissive geometry must not cast dynamic shadows")
        quit(1)
        return
    var core_mesh := core.mesh as SphereMesh
    var ring_mesh := ring.mesh as TorusMesh
    if core_mesh == null or core_mesh.radial_segments > 12 or core_mesh.rings > 6:
        push_error("Impact core geometry budget regressed")
        quit(1)
        return
    if ring_mesh == null or ring_mesh.rings > 16 or ring_mesh.ring_segments > 6:
        push_error("Impact ring geometry budget regressed")
        quit(1)
        return

    var duplicate := IMPACT_SCRIPT.new()
    duplicate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(duplicate)
    await process_frame
    var duplicate_core := duplicate.get_node_or_null("ImpactCore") as MeshInstance3D
    var duplicate_ring := duplicate.get_node_or_null("ImpactRing") as MeshInstance3D
    if duplicate_core == null or duplicate_ring == null:
        push_error("Duplicate impact FX did not build geometry")
        quit(1)
        return
    if duplicate_core.mesh != core.mesh or duplicate_ring.mesh != ring.mesh:
        push_error("Impact FX instances must reuse shared mesh resources")
        quit(1)
        return

    var sparks := fx.get_node_or_null("ImpactSparks") as GPUParticles3D
    if sparks == null:
        push_error("Impact FX is missing mobile-safe GPU sparks")
        quit(1)
        return
    if sparks.amount > 12 or sparks.amount < 4:
        push_error("Impact spark count must stay within mobile budget")
        quit(1)
        return
    if sparks.lifetime > 0.35:
        push_error("Impact sparks live too long for dense mobile combat")
        quit(1)
        return

    var duplicate_sparks := duplicate.get_node_or_null("ImpactSparks") as GPUParticles3D
    if duplicate_sparks == null:
        push_error("Duplicate impact FX is missing GPU sparks")
        quit(1)
        return
    if duplicate_sparks.process_material != sparks.process_material:
        push_error("Impact FX instances must reuse spark process materials for identical colors")
        quit(1)
        return
    if duplicate_sparks.draw_pass_1 != sparks.draw_pass_1:
        push_error("Impact FX instances must reuse spark draw meshes for identical colors")
        quit(1)
        return

    print("Deadline Zero mobile-safe impact FX: OK")
    quit(0)
