extends SceneTree

const FX := preload("res://scripts/CryoStatusFx.gd")
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
    var target := Node3D.new()
    root.add_child(target)

    var first_enemy := DZEnemy.new()
    first_enemy.configure("shambler", 1.0, target)
    first_enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first_enemy)
    first_enemy.global_position = Vector3(2.0, 0.0, -1.0)
    var second_enemy := DZEnemy.new()
    second_enemy.configure("elite", 1.0, target)
    second_enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second_enemy)
    second_enemy.global_position = Vector3(-2.0, 0.0, 0.0)
    await process_frame

    if FX.attach_to(first_enemy) != null:
        _fail("Cryo status FX must not appear without a real slow debuff")
        return

    var shot := PROJECTILE.new()
    shot.process_mode = Node.PROCESS_MODE_DISABLED
    shot.setup(Vector3.ZERO, Vector3.RIGHT, 16.0, 22.0, Color(0.23, 0.88, 1.0), "cryo")
    root.add_child(shot)
    await process_frame
    shot._apply_protocol_hit(first_enemy, 15.0)
    var first := first_enemy.get_node_or_null("CryoStatusCrown") as DZCryoStatusFx
    if first == null or first_enemy.slow_left <= 0.0 or first_enemy.slow_multiplier >= 1.0:
        _fail("Cryo impact failed to apply an actual slow and attach its world visual")
        return
    first.set_process(false)
    if FX.attach_to(first_enemy) != first:
        _fail("Repeated Cryo hits must reuse one existing slow marker")
        return
    if first.get_parent() != first_enemy:
        _fail("Cryo mark must follow its affected enemy in 3D")
        return

    second_enemy.apply_slow(0.62, 1.6)
    var second := FX.attach_to(second_enemy)
    if second == null or second == first:
        _fail("A second slowed enemy did not get its own 3D status marker")
        return
    second.set_process(false)
    var ring := first.get_node_or_null("FrostFootprint") as MeshInstance3D
    var shards := first.get_node_or_null("FrostCrystals") as MultiMeshInstance3D
    var ring_b := second.get_node_or_null("FrostFootprint") as MeshInstance3D
    var shards_b := second.get_node_or_null("FrostCrystals") as MultiMeshInstance3D
    if ring == null or shards == null or ring_b == null or shards_b == null:
        _fail("Cryo status lost its ring or instanced crystalline silhouette")
        return
    if ring.mesh != ring_b.mesh or shards.multimesh.mesh != shards_b.multimesh.mesh:
        _fail("Cryo geometry must be shared across all slowed enemies")
        return
    if ring.material_override != shards.material_override or ring.material_override != ring_b.material_override:
        _fail("Cryo status must reuse one shader across rings and shard crowns")
        return
    if ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or shards.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Cryo status FX must not create mobile shadow casters")
        return
    var torus := ring.mesh as TorusMesh
    var box := shards.multimesh.mesh as BoxMesh
    if torus == null or torus.rings > 24 or torus.ring_segments > 4 or box == null:
        _fail("Cryo geometry exceeds its deliberately low-poly budget")
        return
    if shards.multimesh.instance_count != FX.CRYSTAL_COUNT or FX.CRYSTAL_COUNT > 8:
        _fail("Cryo crown instanced shard count exceeded mobile budget")
        return
    if second.ring.scale.x <= first.ring.scale.x:
        _fail("Elite Cryo markers must adapt to the larger enemy silhouette")
        return

    var original_ring_position := ring.global_position
    first_enemy.global_position += Vector3(1.5, 0.0, -0.5)
    if not is_equal_approx(ring.global_position.x - original_ring_position.x, 1.5):
        _fail("Cryo crown must remain anchored to the victim during world movement")
        return

    var previous_rotation := shards.rotation.y
    first._process(0.06)
    if shards.rotation.y <= previous_rotation:
        _fail("Cryo status no longer provides subtle 3D motion")
        return
    first_enemy.slow_left = 0.05
    first._process(0.02)
    var thaw_opacity := float(ring.get_instance_shader_parameter("frost_opacity"))
    if thaw_opacity <= 0.0 or thaw_opacity >= 1.0:
        _fail("Cryo status must visibly fade during actual slow recovery")
        return

    # Do not allocate a new particle system or mesh when the overlap cap is full.
    var placeholders: Array[Node3D] = []
    for i in range(FX.MAX_ACTIVE - 2):
        var placeholder := Node3D.new()
        placeholder.add_to_group("cryo_status_crowns")
        root.add_child(placeholder)
        placeholders.append(placeholder)
    var third := DZEnemy.new()
    third.configure("shambler", 1.0, target)
    third.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(third)
    third.apply_slow(0.62, 1.6)
    if FX.attach_to(third) != null:
        _fail("Cryo visual effect must respect its mobile overlap cap")
        return
    for placeholder in placeholders:
        placeholder.queue_free()

    first_enemy.slow_left = 0.0
    first._process(0.1)
    if not first.is_queued_for_deletion():
        _fail("Cryo frost crown survived after its victim thawed")
        return

    shot.spawn_secondary_fx = false
    shot._apply_protocol_hit(third, 10.0)
    if third.get_node_or_null("CryoStatusCrown") != null:
        _fail("Gameplay-only mode must not create Cryo secondary visual FX")
        return

    second_enemy.dead = true
    second._process(0.1)
    if not second.is_queued_for_deletion():
        _fail("Cryo crown survived after its victim died")
        return

    print("Deadline Zero Cryo 3D slow status FX: OK")
    quit(0)
