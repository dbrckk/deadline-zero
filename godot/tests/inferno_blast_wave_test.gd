extends SceneTree

const BLAST := preload("res://scripts/InfernoBlastFx.gd")
const PROJECTILE := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _fail(message: String) -> void:
    push_error(message)
    quit(1)

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var center := Vector3(3.2, 0.0, -1.7)
    var first := BLAST.spawn_blast(root, center, 1.85)
    var duplicate := BLAST.spawn_blast(root, center + Vector3.LEFT, 1.85)
    if first == null or duplicate == null:
        _fail("Inferno ground blast did not spawn within gameplay splash radius")
        return
    # Sample an explicit simulated frame; avoid unstable real-time test aging.
    first.set_process(false)
    duplicate.set_process(false)
    await process_frame

    if first.global_position.distance_to(center) > 0.001:
        _fail("Inferno heatwave is not centered on its real impact")
        return
    var wave := first.get_node_or_null("BlastFront") as MeshInstance3D
    var echo := first.get_node_or_null("BlastAfterglow") as MeshInstance3D
    var scorch := first.get_node_or_null("ScorchResidue") as MeshInstance3D
    var duplicate_scorch := duplicate.get_node_or_null("ScorchResidue") as MeshInstance3D
    var duplicate_wave := duplicate.get_node_or_null("BlastFront") as MeshInstance3D
    if wave == null or echo == null or duplicate_wave == null or scorch == null or duplicate_scorch == null:
        _fail("Inferno heatwave lost its two independently animated luminous layers")
        return
    if wave.mesh != echo.mesh or wave.mesh != duplicate_wave.mesh:
        _fail("Inferno rings must reuse one low-poly mesh resource")
        return
    if wave.material_override != echo.material_override or wave.material_override != duplicate_wave.material_override:
        _fail("Inferno rings must reuse one additive shader resource")
        return
    if wave.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or echo.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Inferno heatwave must remain shadow-free on mobile")
        return
    var mesh := wave.mesh as TorusMesh
    if mesh == null or mesh.rings > 32 or mesh.ring_segments > 4:
        _fail("Inferno heatwave geometry budget regressed")
        return
    var lights := 0
    for child in first.get_children():
        if child is Light3D or child is GPUParticles3D:
            lights += 1
    if lights > 0 or first.get_child_count() != 3:
        _fail("Inferno heatwave must avoid lights, emitters, and expensive draw nodes")
        return

    if scorch.mesh != duplicate_scorch.mesh or not (scorch.mesh is QuadMesh):
        _fail("Inferno residue must reuse one procedural quad without texture allocations")
        return
    if scorch.material_override != duplicate_scorch.material_override or not (scorch.material_override is ShaderMaterial):
        _fail("Inferno residue material must be shared across impacts")
        return
    if scorch.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Inferno scorch decal must remain shadow-free")
        return
    if not is_equal_approx(scorch.scale.x, first.blast_radius) or not is_equal_approx(scorch.rotation_degrees.x, -90.0):
        _fail("Inferno scorch decal no longer covers the actual horizontal blast footprint")
        return
    if scorch.position.y <= 0.0 or scorch.position.y >= 0.10:
        _fail("Inferno scorch decal risks clipping into the floor or floating")
        return

    var initial_scale := wave.scale.x
    first._process(0.075)
    if wave.scale.x <= initial_scale or echo.scale.x <= 0.14 * first.blast_radius:
        _fail("Inferno radial blast does not expand during its lifetime")
        return
    if not (first.opacity > 0.0 and first.opacity < 1.0):
        _fail("Inferno radial blast does not fade smoothly")
        return

    var active_scorch := float(scorch.get_instance_shader_parameter("scorch_opacity"))
    if active_scorch <= 0.0 or active_scorch > 1.0:
        _fail("Inferno thermal residue did not ignite after blast impact")
        return
    first._process(0.34)
    var lingering_scorch := float(scorch.get_instance_shader_parameter("scorch_opacity"))
    if first.is_queued_for_deletion() or lingering_scorch <= 0.0 or lingering_scorch >= active_scorch:
        _fail("Inferno ground scorch must persist after the expanding shockwave, while fading")
        return
    if BLAST.spawn_blast(root, center, 0.05) != null or BLAST.spawn_blast(root, center, 4.0) != null:
        _fail("Inferno visual radius hard limits regressed")
        return

    var target := Node3D.new()
    root.add_child(target)
    var primary := DZEnemy.new()
    primary.configure("shambler", 1.0, target)
    primary.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(primary)
    primary.global_position = Vector3.ZERO
    var secondary := DZEnemy.new()
    secondary.configure("shambler", 1.0, target)
    secondary.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(secondary)
    secondary.global_position = Vector3(1.1, 0.0, 0.0)
    var distant := DZEnemy.new()
    distant.configure("shambler", 1.0, target)
    distant.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(distant)
    distant.global_position = Vector3(5.0, 0.0, 0.0)

    var projectile := PROJECTILE.new()
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    projectile.setup(Vector3.ZERO, Vector3.RIGHT, 16.0, 25.0, Color(1.0, 0.3, 0.02), "inferno")
    root.add_child(projectile)
    await process_frame

    var count_before := get_nodes_in_group("inferno_blast_waves").size()
    projectile._apply_splash(primary, 10.0, projectile.splash_radius)
    if get_nodes_in_group("inferno_blast_waves").size() != count_before + 1:
        _fail("Actual Inferno splash hit must create one radial blast")
        return
    if not is_equal_approx(secondary.health, secondary.max_health - 10.0):
        _fail("Inferno visual addition modified secondary splash damage")
        return
    if not is_equal_approx(distant.health, distant.max_health):
        _fail("Inferno splash must not damage enemies outside its advertised radius")
        return

    projectile.spawn_secondary_fx = false
    projectile._apply_splash(primary, 3.0, projectile.splash_radius)
    if get_nodes_in_group("inferno_blast_waves").size() != count_before + 1:
        _fail("Disabled secondary FX still spawned visual heatwaves")
        return

    for i in range(BLAST.MAX_ACTIVE + 4):
        BLAST.spawn_blast(root, center, 1.85)
    if get_nodes_in_group("inferno_blast_waves").size() != BLAST.MAX_ACTIVE:
        _fail("Inferno heatwave mobile overlap cap was exceeded")
        return

    first._process(0.60)
    if not first.is_queued_for_deletion():
        _fail("Inferno residue lifetime must stay hard-capped for mobile memory budget")
        return

    print("Deadline Zero Inferno 3D radial splash FX: OK")
    quit(0)
