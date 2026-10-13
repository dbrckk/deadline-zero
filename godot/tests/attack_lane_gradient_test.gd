extends SceneTree

# Visual contract for restrained danger corridors: a feathered animation
# is readable on mobile without a full-width opaque hazard bar.
func _initialize() -> void:
    call_deferred("_run_test")

func _fail(message: String) -> void:
    push_error(message)
    quit(1)

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var survivor := Node3D.new()
    root.add_child(survivor)

    var charger := DZEnemy.new()
    charger.configure("charger", 1.0, survivor)
    charger.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(charger)
    await process_frame
    charger.global_position = Vector3.ZERO
    charger.pending_special = "charge"
    charger.attack_target_position = Vector3(4.8, 0.0, 1.0)
    charger._show_telegraph(1.05, 0.44)

    var harrier := DZEnemy.new()
    harrier.configure("harrier", 1.0, survivor)
    harrier.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(harrier)
    harrier.global_position = Vector3(0.0, 0.0, -1.5)
    harrier.pending_special = "harrier_shot"
    harrier.attack_target_position = Vector3(-5.0, 0.0, 2.0)
    harrier._show_telegraph(1.05, 0.44)
    await process_frame

    var charge_lane := charger.telegraph_visual.get_node_or_null("ChargeLane") as MeshInstance3D
    var aim_lane := harrier.telegraph_visual.get_node_or_null("HarrierAimLane") as MeshInstance3D
    if charge_lane == null or aim_lane == null:
        _fail("Charge and Harrier must retain actionable target-locked ground warnings")
        return
    if not (charge_lane.mesh is BoxMesh) or not (aim_lane.mesh is BoxMesh):
        _fail("Feathered aim shaders must preserve existing accurate collision corridors")
        return
    var charge_mesh := charge_lane.mesh as BoxMesh
    var aim_mesh := aim_lane.mesh as BoxMesh
    if charge_mesh.size.x < 0.26 or aim_mesh.size.x > 0.14:
        _fail("Danger lanes lost role-specific widths")
        return
    if charge_mesh.size.z < 4.7 or aim_mesh.size.z < 5.9:
        _fail("Danger lane bounds no longer communicate the locked attack range")
        return
    if charge_lane.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or aim_lane.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        _fail("Floor warnings must never cast shadows on Android")
        return

    var heat := charge_lane.material_override as ShaderMaterial
    if heat == null or heat != aim_lane.material_override:
        _fail("All enemy aim lanes must reuse one GPU shader resource")
        return
    if heat == charger.telegraph_material or heat == harrier.telegraph_material:
        _fail("Feathered directional lanes must not use fully opaque escalating ring material")
        return
    var code := heat.shader.code
    if not code.contains("smoothstep") or not code.contains("UV.y") or not code.contains("UV.x"):
        _fail("Aim lane lost feathered cross-section and tapered endpoints")
        return
    if not code.contains("TIME") or not code.contains("EMISSION") or not code.contains("ALPHA"):
        _fail("Aim corridor must remain subtle, animated and emissive")
        return
    if not code.contains("blend_mix") or not code.contains("depth_draw_never"):
        _fail("Corridor shader should blend with asphalt without depth writing")
        return

    if charger.telegraph_visual.material_override != charger.telegraph_material:
        _fail("New lane shading must not weaken the original attack ring threat contrast")
        return

    # The authoritative attack target is locked at anticipation start. The
    # danger corridor must remain on that world-space line even while the
    # parent's luminous ring scales/rotates to announce the strike.
    var charge_origin := charger.global_position
    var charge_target := charger.attack_target_position
    var expected_charge_midpoint := charge_origin.lerp(charge_target, 0.5) + Vector3(0.0, 0.006, 0.0)
    var expected_aim_midpoint := harrier.global_position.lerp(harrier.attack_target_position, 0.5) + Vector3(0.0, 0.006, 0.0)
    if not charge_lane.top_level or not aim_lane.top_level:
        _fail("Aim warning must ignore scaling of its animated parent telegraph ring")
        return
    if charge_lane.get_parent() != charger.telegraph_visual or aim_lane.get_parent() != harrier.telegraph_visual:
        _fail("World-locked aim corridor must still be owned by its timed ring for cleanup")
        return
    if charge_lane.global_position.distance_to(expected_charge_midpoint) > 0.015:
        _fail("Charge danger corridor does not cover the actual locked attack path")
        return
    if aim_lane.global_position.distance_to(expected_aim_midpoint) > 0.015:
        _fail("Harrier danger corridor does not cover the actual locked shot path")
        return
    var charge_heading := Vector3(charge_target.x - charge_origin.x, 0.0, charge_target.z - charge_origin.z).normalized()
    var lane_heading := charge_lane.global_transform.basis.z.normalized()
    if charge_heading.dot(lane_heading) < 0.997:
        _fail("Charge danger corridor orientation no longer points along the committed charge")
        return
    var charge_locked_transform := charge_lane.global_transform
    var aim_locked_transform := aim_lane.global_transform
    await create_timer(0.16).timeout
    if charger.telegraph_visual.scale.x <= 0.46:
        _fail("Attack telegraph did not animate its ring during charge warning")
        return
    if charge_lane.global_transform.origin.distance_to(charge_locked_transform.origin) > 0.015 or aim_lane.global_transform.origin.distance_to(aim_locked_transform.origin) > 0.015:
        _fail("Expanding attack ring displaced a locked ground warning mid-windup")
        return
    if charge_lane.global_transform.basis.z.distance_to(charge_locked_transform.basis.z) > 0.015 or aim_lane.global_transform.basis.z.distance_to(aim_locked_transform.basis.z) > 0.015:
        _fail("Attack ring animation rotated or shrank actual target-locked warning lane")
        return

    print("Deadline Zero feathered mobile 3D enemy aim lane shaders: OK")
    quit(0)
