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
    boss.position = Vector3(3.15, 0.0, -1.02)
    boss.rotation.y = deg_to_rad(-18.0)
    boss.scale = Vector3.ONE * 1.38

    var elite := DZEnemy.new()
    elite.configure("elite", 1.0, target)
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    elite.spawn_secondary_fx = false
    world.add_child(elite)
    elite.position = Vector3(1.35, 0.0, -1.42)
    elite.rotation.y = deg_to_rad(12.0)
    elite.scale = Vector3.ONE * 1.08

    var player := DZAssetLibrary.player()
    if player == null:
        push_error("Feature graphic capture could not load authored player")
        quit(1)
        return
    player.position = Vector3(-3.05, 0.0, 0.14)
    player.rotation.y = deg_to_rad(12.0)
    player.scale = Vector3.ONE * 1.72
    world.add_child(player)

    var rifle := DZAssetLibrary.rifle()
    if rifle != null:
        rifle.position = Vector3(0.42, 0.83, -0.30)
        rifle.rotation_degrees = Vector3(-11.0, 166.0, -9.0)
        rifle.scale = Vector3.ONE * 0.84
        player.add_child(rifle)

    var camera := Camera3D.new()
    camera.position = Vector3(0.0, 2.95, 8.65)
    camera.fov = 36.0
    world.add_child(camera)
    camera.current = true
    camera.look_at(Vector3(0.10, 0.98, -0.58), Vector3.UP)

    var brand_layer := CanvasLayer.new()
    brand_layer.layer = 4
    viewport.add_child(brand_layer)

    var title_backdrop := ColorRect.new()
    title_backdrop.position = Vector2(40.0, 34.0)
    title_backdrop.size = Vector2(520.0, 132.0)
    title_backdrop.color = Color(0.005, 0.010, 0.014, 0.84)
    brand_layer.add_child(title_backdrop)

    var cyan_rule := ColorRect.new()
    cyan_rule.position = Vector2(40.0, 34.0)
    cyan_rule.size = Vector2(7.0, 132.0)
    cyan_rule.color = Color(0.08, 0.82, 1.0, 0.95)
    brand_layer.add_child(cyan_rule)

    var orange_rule := ColorRect.new()
    orange_rule.position = Vector2(47.0, 158.0)
    orange_rule.size = Vector2(238.0, 8.0)
    orange_rule.color = Color(1.0, 0.26, 0.035, 0.92)
    brand_layer.add_child(orange_rule)

    var title := Label.new()
    title.position = Vector2(70.0, 50.0)
    title.size = Vector2(470.0, 64.0)
    title.text = "DEADLINE: ZERO"
    title.add_theme_font_size_override("font_size", 48)
    title.add_theme_color_override("font_color", Color(0.92, 0.97, 1.0))
    title.add_theme_color_override("font_outline_color", Color(0.0, 0.0, 0.0, 0.86))
    title.add_theme_constant_override("outline_size", 4)
    brand_layer.add_child(title)

    var subtitle := Label.new()
    subtitle.position = Vector2(72.0, 111.0)
    subtitle.size = Vector2(460.0, 38.0)
    subtitle.text = "SURVIVE THE QUARANTINE"
    subtitle.add_theme_font_size_override("font_size", 18)
    subtitle.add_theme_color_override("font_color", Color(0.35, 0.86, 1.0))
    brand_layer.add_child(subtitle)

    var emblem := TextureRect.new()
    emblem.position = Vector2(902.0, 26.0)
    emblem.size = Vector2(86.0, 86.0)
    emblem.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
    emblem.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
    emblem.texture = load("res://assets/ui/deadline_zero_icon.svg") as Texture2D
    brand_layer.add_child(emblem)

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
