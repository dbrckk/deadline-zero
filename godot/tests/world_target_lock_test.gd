extends SceneTree

# Production combat QA: only one aim marker, unlit/shadow-free, follows
# the actual selected enemy and disappears on death/combat shutdown.
func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    var marker := player.target_lock_marker
    if marker == null or marker.name != "TargetLockReticle" or marker.visible:
        push_error("World target marker must be built and hidden until an enemy is selected")
        quit(1)
        return
    if not marker.top_level or marker.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Target lock must stay in world space and out of mobile shadow maps")
        quit(1)
        return
    var mesh := marker.mesh as ArrayMesh
    if mesh == null or mesh.get_surface_count() != 1:
        push_error("Target reticle must be a single curved 3D surface")
        quit(1)
        return
    var mesh_arrays := mesh.surface_get_arrays(0)
    var vertices := mesh_arrays[Mesh.ARRAY_VERTEX] as PackedVector3Array
    if vertices.size() != 192:
        push_error("Target reticle lost its four segmented low-cost aiming arcs")
        quit(1)
        return
    var mat := marker.material_override as StandardMaterial3D
    if mat == null or not mat.emission_enabled or mat.transparency != BaseMaterial3D.TRANSPARENCY_ALPHA:
        push_error("World aim confirmation lost restrained unlit transparent shading")
        quit(1)
        return
    if mat.emission_energy_multiplier > 1.0:
        push_error("Target lock must not become a distracting neon visual effect")
        quit(1)
        return

    var first := DZEnemy.new()
    first.configure("runner", 1.0, player)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(3.0, 0.0, 2.0)
    await process_frame

    player._update_target_lock_marker(first, 0.016)
    if not marker.visible or marker.global_position.distance_to(Vector3(3.0, 0.070, 2.0)) > 0.005:
        push_error("Target lock failed to snap to the live autoaim enemy")
        quit(1)
        return

    first.global_position.x = 4.0
    player._update_target_lock_marker(first, 0.016)
    if marker.global_position.x <= 3.0 or marker.global_position.x >= 4.0:
        push_error("Target lock lost stable motion interpolation")
        quit(1)
        return

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, player)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss)
    boss.global_position = Vector3(-5.0, 0.0, -2.0)
    await process_frame
    player._update_target_lock_marker(boss, 0.016)
    if marker.global_position.distance_to(Vector3(-5.0, 0.070, -2.0)) > 0.005 or marker.scale.x < 1.75:
        push_error("Switching target did not snap and scale the readable boss lock")
        quit(1)
        return

    boss.dead = true
    player._update_target_lock_marker(boss, 0.016)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Target lock retained a defeated boss")
        quit(1)
        return

    player._update_target_lock_marker(first, 0.016)
    player.set_combat_enabled(false)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Combat shutdown left auto-aim confirmation visible")
        quit(1)
        return

    print("Deadline Zero world-space aim lock: OK (4 arcs, target motion, boss switch and death)")
    quit(0)
