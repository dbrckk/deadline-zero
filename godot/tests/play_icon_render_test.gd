extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-play-icon-candidate.png"

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var viewport := SubViewport.new()
    viewport.size = Vector2i(512, 512)
    viewport.render_target_update_mode = SubViewport.UPDATE_ALWAYS
    viewport.msaa_3d = Viewport.MSAA_4X
    get_root().add_child(viewport)

    var world := Node3D.new()
    viewport.add_child(world)

    var environment_node := WorldEnvironment.new()
    var environment := Environment.new()
    environment.background_mode = Environment.BG_COLOR
    environment.background_color = Color(0.008, 0.014, 0.020)
    environment.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    environment.ambient_light_color = Color(0.10, 0.16, 0.20)
    environment.ambient_light_energy = 0.78
    environment.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    environment_node.environment = environment
    world.add_child(environment_node)

    var key := DirectionalLight3D.new()
    key.rotation_degrees = Vector3(-42.0, -32.0, 0.0)
    key.light_color = Color(0.58, 0.88, 1.0)
    key.light_energy = 2.1
    key.shadow_enabled = true
    world.add_child(key)

    var rim := OmniLight3D.new()
    rim.position = Vector3(2.2, 2.4, 1.8)
    rim.light_color = Color(1.0, 0.22, 0.035)
    rim.light_energy = 7.0
    rim.omni_range = 8.0
    rim.shadow_enabled = false
    world.add_child(rim)

    var floor := MeshInstance3D.new()
    var floor_mesh := PlaneMesh.new()
    floor_mesh.size = Vector2(8.0, 8.0)
    floor.mesh = floor_mesh
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.018, 0.027, 0.034)
    floor_mat.metallic = 0.26
    floor_mat.roughness = 0.82
    floor.material_override = floor_mat
    world.add_child(floor)

    var boss_target := Node3D.new()
    boss_target.position = Vector3.ZERO
    world.add_child(boss_target)

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, boss_target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    world.add_child(boss)
    boss.position = Vector3(0.88, 0.0, -1.22)
    boss.rotation.y = deg_to_rad(-20.0)
    boss.scale = Vector3.ONE * 1.18

    var player := DZAssetLibrary.player()
    if player == null:
        push_error("Brand icon capture could not load authored player")
        quit(1)
        return
    player.position = Vector3(-0.68, 0.0, 0.24)
    player.rotation.y = deg_to_rad(12.0)
    player.scale = Vector3.ONE * 1.58
    world.add_child(player)

    var rifle := DZAssetLibrary.rifle()
    if rifle != null:
        rifle.position = Vector3(0.44, 0.80, -0.30)
        rifle.rotation_degrees = Vector3(-12.0, 166.0, -10.0)
        rifle.scale = Vector3.ONE * 0.82
        player.add_child(rifle)

    var hazard_ring := MeshInstance3D.new()
    var ring_mesh := TorusMesh.new()
    ring_mesh.inner_radius = 2.28
    ring_mesh.outer_radius = 2.36
    ring_mesh.rings = 32
    ring_mesh.ring_segments = 6
    hazard_ring.mesh = ring_mesh
    hazard_ring.position = Vector3(0.0, 0.035, -0.20)
    var ring_mat := StandardMaterial3D.new()
    ring_mat.albedo_color = Color(0.80, 0.12, 0.018)
    ring_mat.emission_enabled = true
    ring_mat.emission = Color(0.62, 0.055, 0.004)
    ring_mat.emission_energy_multiplier = 1.2
    ring_mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    hazard_ring.material_override = ring_mat
    hazard_ring.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    world.add_child(hazard_ring)

    var camera := Camera3D.new()
    camera.position = Vector3(0.0, 2.05, 5.35)
    camera.fov = 32.0
    world.add_child(camera)
    camera.current = true
    camera.look_at(Vector3(0.0, 1.04, -0.18), Vector3.UP)

    for _frame in range(8):
        await process_frame

    var image := viewport.get_texture().get_image()
    if image == null or image.is_empty() or image.get_width() != 512 or image.get_height() != 512:
        push_error("Brand icon capture did not render exact 512x512")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save brand icon candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PLAY_ICON_CANDIDATE_OK 512x512")
    quit(0)
