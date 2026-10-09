extends SceneTree

const FX := preload("res://scripts/BurnStatusFx.gd")
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
    var player := Node3D.new()
    root.add_child(player)

    var ordinary := DZEnemy.new()
    ordinary.configure("shambler", 1.0, player)
    ordinary.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(ordinary)
    ordinary.global_position = Vector3(2.2, 0.0, -1.0)

    var elite := DZEnemy.new()
    elite.configure("elite", 1.0, player)
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(elite)
    elite.global_position = Vector3(-2.2, 0.0, 1.0)
    await process_frame

    if FX.attach_to(ordinary) != null:
        _fail("Inferno burn status must not appear before real DoT is applied")
        return

    var shot := PROJECTILE.new()
    shot.process_mode = Node.PROCESS_MODE_DISABLED
    shot.setup(Vector3.ZERO, Vector3.RIGHT, 15.0, 20.0, Color.ORANGE_RED, "inferno")
    root.add_child(shot)
    await process_frame
    shot._apply_protocol_hit(ordinary, 20.0)
    var first := ordinary.get_node_or_null("BurnStatusFlames") as DZBurnStatusFx
    if ordinary.burn_left <= 0.0 or ordinary.burn_dps <= 0.0 or first == null:
        _fail("Inferno protocol must apply real burning and attach its 3D fire status")
        return
    first.set_process(false)
    if FX.attach_to(ordinary) != first:
        _fail("Repeated Inferno burns must never duplicate victim flame geometry")
        return

    elite.apply_burn(3.0, 2.0)
    var second := FX.attach_to(elite)
    if second == null or second == first:
        _fail("A separately burning elite needs its own attached flame marker")
        return
    second.set_process(false)

    var flames_a := first.get_node_or_null("InfernoEmbers") as MeshInstance3D
    var flames_b := second.get_node_or_null("InfernoEmbers") as MeshInstance3D
    if flames_a == null or flames_b == null:
        _fail("World-space flame ribbons were not constructed")
        return
    if first.get_parent() != ordinary or second.get_parent() != elite:
        _fail("Burning visual must follow the affected enemy")
        return
    if flames_a.mesh != flames_b.mesh:
        _fail("Burn status must share one baked ribbon mesh across all burning enemies")
        return
    if flames_a.material_override != flames_b.material_override:
        _fail("Burn status must share the same animated emission shader")
        return
    var flame_mesh := flames_a.mesh as ArrayMesh
    if flame_mesh == null or flame_mesh.get_surface_count() != 1:
        _fail("Mobile burn effect must draw in exactly one shared mesh surface")
        return
    var vertices: PackedVector3Array = flame_mesh.surface_get_arrays(0)[Mesh.ARRAY_VERTEX]
    var uv: PackedVector2Array = flame_mesh.surface_get_arrays(0)[Mesh.ARRAY_TEX_UV]
    if vertices.size() != FX.FLAME_COUNT * 6 or uv.size() != vertices.size() or FX.FLAME_COUNT > 8:
        _fail("Inferno must use exactly seven two-triangle flames with one UV layout")
        return
    var highest := 0.0
    var furthest := 0.0
    for vertex in vertices:
        highest = maxf(highest, vertex.y)
        furthest = maxf(furthest, Vector2(vertex.x, vertex.z).length())
    if highest < 1.15 or furthest < 0.48:
        _fail("Inferno flames too small/inside body: height=%.3f radius=%.3f" % [highest, furthest])
        return
    if flames_a.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Emissive burn FX must never consume shadow-map draws")
        return
    if flames_b.scale.x <= flames_a.scale.x:
        _fail("Elite burning silhouette should scale with its larger body")
        return
    var shader := (flames_a.material_override as ShaderMaterial).shader.code
    if not shader.contains("TIME") or not shader.contains("EMISSION") or not shader.contains("burn_opacity"):
        _fail("Burning ribbons lost animated emissive shading and per-victim fade")
        return

    var previous := flames_a.global_position
    ordinary.global_position += Vector3(0.75, 0.0, 0.5)
    if not is_equal_approx(flames_a.global_position.x - previous.x, 0.75):
        _fail("Fire must stay anchored to the enemy as it moves")
        return

    ordinary.burn_left = 0.08
    first._process(0.0)
    var fade := float(flames_a.get_instance_shader_parameter("burn_opacity"))
    if fade <= 0.0 or fade >= 1.0:
        _fail("Inferno ribbons must fade near the true DoT expiry")
        return
    ordinary.apply_burn(4.0, 2.0)
    first._process(0.0)
    if not is_equal_approx(float(flames_a.get_instance_shader_parameter("burn_opacity")), 1.0):
        _fail("Refreshing DoT must restore full ember intensity without duplicating FX")
        return

    # Existing visuals occupy two of the eighteen active world-space slots.
    var placeholders: Array[Node3D] = []
    for i in range(FX.MAX_ACTIVE - 2):
        var placeholder := Node3D.new()
        placeholder.add_to_group("burn_status_flames")
        root.add_child(placeholder)
        placeholders.append(placeholder)
    var third := DZEnemy.new()
    third.configure("shambler", 1.0, player)
    third.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(third)
    third.apply_burn(3.0, 1.5)
    if FX.attach_to(third) != null:
        _fail("Inferno burn visuals must respect the mobile overlap limit")
        return
    for placeholder in placeholders:
        placeholder.queue_free()
    await process_frame

    shot.spawn_secondary_fx = false
    shot._apply_protocol_hit(third, 10.0)
    if third.get_node_or_null("BurnStatusFlames") != null:
        _fail("Gameplay-only simulation must not create decorative DoT flames")
        return

    ordinary.burn_left = 0.0
    first._process(0.0)
    if not first.is_queued_for_deletion():
        _fail("Inferno fire visual survived after DoT expiry")
        return

    elite.dead = true
    second._process(0.0)
    if not second.is_queued_for_deletion():
        _fail("Inferno fire visual survived the enemy's death")
        return

    print("Deadline Zero native 3D Inferno burning status FX: OK")
    quit(0)
