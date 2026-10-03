extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-feature-graphic-candidate.png"

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var viewport := SubViewport.new()
    viewport.size = Vector2i(1024, 500)
    viewport.render_target_update_mode = SubViewport.UPDATE_ALWAYS
    viewport.msaa_3d = Viewport.MSAA_4X
    get_root().add_child(viewport)

    var world := Node3D.new()
    viewport.add_child(world)

    var environment_node := WorldEnvironment.new()
    var environment := Environment.new()
    environment.background_mode = Environment.BG_COLOR
    environment.background_color = Color(0.006, 0.012, 0.018)
    environment.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    environment.ambient_light_color = Color(0.09, 0.15, 0.19)
    environment.ambient_light_energy = 0.72
    environment.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    environment_node.environment = environment
    world.add_child(environment_node)

    var key := DirectionalLight3D.new()
    key.rotation_degrees = Vector3(-48.0, -28.0, 0.0)
    key.light_color = Color(0.62, 0.90, 1.0)
    key.light_energy = 2.0
    key.shadow_enabled = true
    world.add_child(key)

    var warm := OmniLight3D.new()
    warm.position = Vector3(4.8, 2.4, 0.0)
    warm.light_color = Color(1.0, 0.16, 0.025)
    warm.light_energy = 8.5
    warm.omni_range = 10.0
    warm.shadow_enabled = false
    world.add_child(warm)

    var cool := OmniLight3D.new()
    cool.position = Vector3(-4.8, 2.3, 1.0)
    cool.light_color = Color(0.08, 0.72, 1.0)
    cool.light_energy = 6.5
    cool.omni_range = 10.0
    cool.shadow_enabled = false
    world.add_child(cool)

    var floor := MeshInstance3D.new()
    var floor_mesh := PlaneMesh.new()
    floor_mesh.size = Vector2(18.0, 9.0)
    floor.mesh = floor_mesh
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.016, 0.025, 0.032)
    floor_mat.metallic = 0.30
    floor_mat.roughness = 0.84
    floor.material_override = floor_mat
    world.add_child(floor)

    for index in range(5):
        var barrier := DZAssetLibrary.barrier()
        if barrier == null:
            continue
        barrier.position = Vector3(-6.4 + float(index) * 3.2, 0.0, -3.0 + absf(float(index) - 2.0) * 0.16)
        barrier.rotation.y = 0.08 * float(index - 2)
        barrier.scale = Vector3.ONE * 0.34
        world.add_child(barrier)

    var target := Node3D.new()
    world.add_child(target)

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    world.add_child(boss)
    boss.position = Vector3(3.6, 0.0, -1.25)
    boss.rotation.y = deg_to_rad(150.0)
    boss.scale = Vector3.ONE * 1.22

    var elite := DZEnemy.new()
    elite.configure("elite", 1.0, target)
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    elite.spawn_secondary_fx = false
    world.add_child(elite)
    elite.position = Vector3(1.6, 0.0, -1.85)
    elite.rotation.y = deg_to_rad(160.0)

    var player := DZAssetLibrary.player()
    if player == null:
        push_error("Feature graphic capture could not load authored player")
        quit(1)
        return
    player.position = Vector3(-3.3, 0.0, 0.25)
    player.rotation.y = deg_to_rad(-22.0)
    player.scale = Vector3.ONE * 1.45
    world.add_child(player)

    var rifle := DZAssetLibrary.rifle()
    if rifle != null:
        rifle.position = Vector3(0.33, 0.93, -0.38)
        rifle.rotation_degrees = Vector3(-8.0, 180.0, -4.0)
        rifle.scale = Vector3.ONE * 0.92
        player.add_child(rifle)

    var camera := Camera3D.new()
    camera.position = Vector3(0.0, 3.2, 9.5)
    camera.fov = 39.0
    world.add_child(camera)
    camera.current = true
    camera.look_at(Vector3(0.1, 0.9, -0.75), Vector3.UP)

    for _frame in range(8):
        await process_frame

    var image := viewport.get_texture().get_image()
    if image == null or image.is_empty() or image.get_width() != 1024 or image.get_height() != 500:
        push_error("Feature graphic capture did not render exact 1024x500")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save feature graphic candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_FEATURE_GRAPHIC_CANDIDATE_OK 1024x500")
    quit(0)
