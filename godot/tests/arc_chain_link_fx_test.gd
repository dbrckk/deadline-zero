extends SceneTree

const ARC_FX := preload("res://scripts/ArcLinkFx.gd")
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

    var start := Vector3(1.3, 0.62, -0.4)
    var finish := Vector3(4.0, 0.62, 1.8)
    var first := ARC_FX.spawn_link(root, start, finish)
    var duplicate := ARC_FX.spawn_link(root, start + Vector3.FORWARD, finish + Vector3.FORWARD, 1)
    if first == null or duplicate == null:
        _fail("Arc weapon did not spawn two valid world-space lightning links")
        return
    # Keep timing deterministic under slow headless runners; manually sample
    # the fade once instead of depending on real frame scheduling.
    first.set_process(false)
    duplicate.set_process(false)
    await process_frame

    if first.global_position.distance_to(start) > 0.001:
        _fail("Arc ribbon did not retain its actual source world position")
        return
    var ribbon := first.get_node_or_null("ArcRibbon") as MeshInstance3D
    var other := duplicate.get_node_or_null("ArcRibbon") as MeshInstance3D
    if ribbon == null or other == null:
        _fail("Arc lightning lacks its world-space ribbon geometry")
        return
    if ribbon.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Arc lightning must not render costly dynamic shadows")
        return
    if not (ribbon.material_override is ShaderMaterial) or ribbon.material_override != other.material_override:
        _fail("All Arc chains must reuse the same emissive material")
        return
    var mesh := ribbon.mesh as ArrayMesh
    if mesh == null or mesh.get_surface_count() != 1:
        _fail("Arc lightning must be one bounded draw surface")
        return
    var arrays := mesh.surface_get_arrays(0)
    var vertices: PackedVector3Array = arrays[Mesh.ARRAY_VERTEX]
    var colors: PackedColorArray = arrays[Mesh.ARRAY_COLOR]
    if vertices.size() != ARC_FX.SEGMENTS * ARC_FX.BAND_WIDTHS.size() * 6 or colors.size() != vertices.size():
        _fail("Layered Arc ribbon geometry exceeded its mobile vertex budget")
        return
    var first_distance := INF
    var last_distance := INF
    for vertex in vertices:
        first_distance = minf(first_distance, vertex.distance_to(Vector3.ZERO))
        last_distance = minf(last_distance, vertex.distance_to(finish - start))
    if first_distance > 0.09 or last_distance > 0.09:
        _fail("Arc endpoint geometry does not connect its source and target")
        return
    first._process(0.05)
    if first.opacity >= 1.0 or first.opacity <= 0.0:
        _fail("Arc lightning did not fade through per-instance shader opacity")
        return

    if ARC_FX.spawn_link(root, start, start) != null:
        _fail("Arc links should not spawn degenerate geometry")
        return
    if ARC_FX.spawn_link(root, start, start + Vector3.RIGHT * (ARC_FX.MAX_LENGTH + 1.0)) != null:
        _fail("Arc links should reject out-of-range bolts")
        return

    var target := Node3D.new()
    root.add_child(target)
    var primary := DZEnemy.new()
    primary.process_mode = Node.PROCESS_MODE_DISABLED
    primary.configure("shambler", 1.0, target)
    root.add_child(primary)
    primary.global_position = Vector3(0.0, 0.0, 0.0)
    var chained_a := DZEnemy.new()
    chained_a.process_mode = Node.PROCESS_MODE_DISABLED
    chained_a.configure("shambler", 1.0, target)
    root.add_child(chained_a)
    chained_a.global_position = Vector3(1.6, 0.0, 0.0)
    var chained_b := DZEnemy.new()
    chained_b.process_mode = Node.PROCESS_MODE_DISABLED
    chained_b.configure("shambler", 1.0, target)
    root.add_child(chained_b)
    chained_b.global_position = Vector3(2.8, 0.0, 0.0)

    var projectile := PROJECTILE.new()
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    projectile.setup(Vector3.ZERO, Vector3.RIGHT, 12.0, 24.0, Color(0.65, 0.45, 1.0), "arc")
    root.add_child(projectile)
    await process_frame

    var count_before := get_nodes_in_group("arc_chain_links").size()
    projectile._apply_chain(primary, 20.0)
    var count_after := get_nodes_in_group("arc_chain_links").size()
    if count_after - count_before != 2:
        _fail("Arc chain damage must spawn exactly two real connecting lightning links")
        return
    if not is_equal_approx(chained_a.health, chained_a.max_health - 11.2):
        _fail("Arc first-chain damage changed while adding visual links")
        return
    if not is_equal_approx(chained_b.health, chained_b.max_health - 7.6):
        _fail("Arc second-chain damage changed while adding visual links")
        return

    projectile.spawn_secondary_fx = false
    projectile._apply_chain(primary, 4.0)
    if get_nodes_in_group("arc_chain_links").size() != count_after:
        _fail("Disabling secondary FX must also disable Arc link geometry")
        return

    # Dense multi-shot waves cannot spawn unbounded additive ribbons.
    for index in range(ARC_FX.MAX_ACTIVE + 4):
        ARC_FX.spawn_link(root, start, finish, index)
    if get_nodes_in_group("arc_chain_links").size() != ARC_FX.MAX_ACTIVE:
        _fail("Arc chain hard active-link draw budget was exceeded")
        return

    print("Deadline Zero connected Arc chain 3D lighting: OK")
    quit(0)
