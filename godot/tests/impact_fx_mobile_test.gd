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
    var flare_found := false
    for child in fx.get_children():
        if child is OmniLight3D:
            light_count += 1
        if child is MeshInstance3D:
            mesh_count += 1
            if child.name == "ImpactRing":
                ring_found = true
            elif child.name == "ImpactFlare":
                flare_found = true

    if light_count != 0:
        push_error("Impact FX should avoid per-hit dynamic lights on mobile")
        quit(1)
        return
    if mesh_count < 3 or not ring_found or not flare_found:
        push_error("Impact FX is missing premium layered core/ring/flare geometry")
        quit(1)
        return

    var core := fx.get_node_or_null("ImpactCore") as MeshInstance3D
    var ring := fx.get_node_or_null("ImpactRing") as MeshInstance3D
    var flare := fx.get_node_or_null("ImpactFlare") as MeshInstance3D
    if core == null or ring == null or flare == null:
        push_error("Impact FX core/ring/flare nodes are missing")
        quit(1)
        return
    if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or flare.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
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
    var duplicate_flare := duplicate.get_node_or_null("ImpactFlare") as MeshInstance3D
    if duplicate_core == null or duplicate_ring == null or duplicate_flare == null:
        push_error("Duplicate impact FX did not build geometry")
        quit(1)
        return
    if duplicate_core.mesh != core.mesh or duplicate_ring.mesh != ring.mesh or duplicate_flare.mesh != flare.mesh:
        push_error("Impact FX instances must reuse shared core/ring/flare mesh resources")
        quit(1)
        return

    # Dense combat creates hundreds of overlapping hits. Their PBR-grade
    # luminous materials must be reused without coupling lifetime fades.
    if core.material_override != duplicate_core.material_override:
        push_error("Impact cores must reuse the same luminous material")
        quit(1)
        return
    if ring.material_override != duplicate_ring.material_override:
        push_error("Impact rings must reuse the same luminous material")
        quit(1)
        return
    if flare.material_override != duplicate_flare.material_override:
        push_error("Impact billboard flares must reuse the same luminous material")
        quit(1)
        return
    if (flare.material_override as StandardMaterial3D).billboard_mode != BaseMaterial3D.BILLBOARD_ENABLED:
        push_error("Impact flares lost their camera-facing billboard")
        quit(1)
        return

    fx._process(fx.life * 0.5)
    if absf(core.transparency - 0.5) > 0.01 or absf(ring.transparency - 0.5) > 0.01:
        push_error("Impact core/ring lost their smooth independent fade")
        quit(1)
        return
    if flare.transparency <= core.transparency:
        push_error("Impact flare must disappear before the core")
        quit(1)
        return
    if duplicate_core.transparency > 0.01 or duplicate_ring.transparency > 0.01 or duplicate_flare.transparency > 0.01:
        push_error("Fading one impact changed the duplicate's opacity")
        quit(1)
        return
    if (duplicate_core.material_override as StandardMaterial3D).albedo_color.a < 0.99:
        push_error("Fading an impact mutated its shared material")
        quit(1)
        return

    var alternate := IMPACT_SCRIPT.new()
    alternate.color = Color(1.0, 0.22, 0.08)
    alternate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(alternate)
    await process_frame
    var alternate_core := alternate.get_node_or_null("ImpactCore") as MeshInstance3D
    if alternate_core == null or alternate_core.material_override == core.material_override:
        push_error("Different impact colors must not share the same tint material")
        quit(1)
        return
    if alternate_core.mesh != core.mesh:
        push_error("Different impact colors must still reuse the same core geometry")
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

    var projectile := DZProjectile.new()
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(projectile)
    current_scene = null
    var children_before := root.get_child_count()
    projectile._impact(false, projectile.global_position)
    await process_frame
    if root.get_child_count() <= children_before:
        push_error("Projectile impact FX did not fall back to projectile parent without current_scene")
        quit(1)
        return
    current_scene = root

    print("Deadline Zero mobile-safe impact FX: OK")
    quit(0)
