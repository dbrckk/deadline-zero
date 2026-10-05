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

    var wall_material := StandardMaterial3D.new()
    wall_material.albedo_color = Color(0.020, 0.032, 0.040)
    wall_material.metallic = 0.58
    wall_material.roughness = 0.68

    var recess_material := StandardMaterial3D.new()
    recess_material.albedo_color = Color(0.006, 0.010, 0.014)
    recess_material.metallic = 0.38
    recess_material.roughness = 0.82

    var cyan_material := StandardMaterial3D.new()
    cyan_material.albedo_color = Color(0.06, 0.70, 0.88)
    cyan_material.emission_enabled = true
    cyan_material.emission = Color(0.03, 0.52, 0.78)
    cyan_material.emission_energy_multiplier = 1.65
    cyan_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var orange_material := StandardMaterial3D.new()
    orange_material.albedo_color = Color(0.98, 0.20, 0.025)
    orange_material.emission_enabled = true
    orange_material.emission = Color(0.82, 0.08, 0.008)
    orange_material.emission_energy_multiplier = 1.85
    orange_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var panel_mesh := BoxMesh.new()
    panel_mesh.size = Vector3(3.60, 3.30, 0.18)
    var recess_mesh := BoxMesh.new()
    recess_mesh.size = Vector3(2.66, 2.20, 0.08)
    var light_bar_mesh := BoxMesh.new()
    light_bar_mesh.size = Vector3(2.35, 0.055, 0.055)

    for panel_index in range(4):
        var wall_panel := MeshInstance3D.new()
        wall_panel.name = "FeatureBulkhead_%d" % panel_index
        wall_panel.mesh = panel_mesh
        wall_panel.position = Vector3(-5.35 + float(panel_index) * 3.55, 1.62, -3.78)
        wall_panel.material_override = wall_material
        world.add_child(wall_panel)

        var recess := MeshInstance3D.new()
        recess.name = "FeatureBulkheadRecess_%d" % panel_index
        recess.mesh = recess_mesh
        recess.position = wall_panel.position + Vector3(0.0, 0.02, 0.135)
        recess.material_override = recess_material
        world.add_child(recess)

        var upper_bar := MeshInstance3D.new()
        upper_bar.name = "FeatureBulkheadLight_%d" % panel_index
        upper_bar.mesh = light_bar_mesh
        upper_bar.position = wall_panel.position + Vector3(0.0, 1.18, 0.205)
        upper_bar.material_override = cyan_material if panel_index < 2 else orange_material
        upper_bar.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        world.add_child(upper_bar)

    var containment_ring := MeshInstance3D.new()
    var containment_mesh := TorusMesh.new()
    containment_mesh.inner_radius = 3.65
    containment_mesh.outer_radius = 3.73
    containment_mesh.rings = 44
    containment_mesh.ring_segments = 8
    containment_ring.mesh = containment_mesh
    containment_ring.position = Vector3(0.55, 0.022, -0.35)
    containment_ring.material_override = orange_material
    containment_ring.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    world.add_child(containment_ring)

    for side in [-1.0, 1.0]:
        var runway := MeshInstance3D.new()
        var runway_mesh := BoxMesh.new()
        runway_mesh.size = Vector3(0.075, 0.018, 5.8)
        runway.mesh = runway_mesh
        runway.position = Vector3(side * 4.55, 0.018, -0.15)
        runway.material_override = cyan_material if side < 0.0 else orange_material
        runway.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        world.add_child(runway)

    for index in range(5):
        var barrier := DZAssetLibrary.barrier()
        if barrier == null:
            continue
        barrier.position = Vector3(-6.25 + float(index) * 3.1, 0.0, -2.85 + absf(float(index) - 2.0) * 0.10)
        barrier.rotation.y = 0.08 * float(index - 2)
        barrier.scale = Vector3.ONE * 0.28
        world.add_child(barrier)

    var target := Node3D.new()
    world.add_child(target)

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    world.add_child(boss)
    boss.position = Vector3(3.20, 0.0, -0.92)
    boss.rotation.y = deg_to_rad(-24.0)
    boss.scale = Vector3.ONE * 1.46

    var elite := DZEnemy.new()
    elite.configure("elite", 1.0, target)
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    elite.spawn_secondary_fx = false
    world.add_child(elite)
    elite.position = Vector3(1.22, 0.0, -1.32)
    elite.rotation.y = deg_to_rad(12.0)
    elite.scale = Vector3.ONE * 1.08

    var player := DZAssetLibrary.player()
    if player == null:
        push_error("Feature graphic capture could not load authored player")
        quit(1)
        return
    player.position = Vector3(-3.20, 0.0, 0.22)
    player.rotation.y = deg_to_rad(18.0)
    player.scale = Vector3.ONE * 1.82
    world.add_child(player)

    var rifle := DZAssetLibrary.rifle()
    if rifle != null:
        rifle.position = Vector3(0.42, 0.83, -0.30)
        rifle.rotation_degrees = Vector3(-11.0, 166.0, -9.0)
        rifle.scale = Vector3.ONE * 0.84
        player.add_child(rifle)

    var camera := Camera3D.new()
    camera.position = Vector3(0.15, 2.62, 8.15)
    camera.fov = 34.5
    world.add_child(camera)
    camera.current = true
    camera.look_at(Vector3(0.05, 1.04, -0.62), Vector3.UP)

    var brand_layer := CanvasLayer.new()
    brand_layer.layer = 4
    viewport.add_child(brand_layer)

    var cinematic_overlay := ColorRect.new()
    cinematic_overlay.position = Vector2.ZERO
    cinematic_overlay.size = Vector2(1024.0, 500.0)
    var overlay_material := ShaderMaterial.new()
    var overlay_shader := Shader.new()
    overlay_shader.code = """
shader_type canvas_item;

void fragment() {
    vec2 uv = UV;
    float edge = smoothstep(0.72, 0.98, length((uv - vec2(0.5)) * vec2(1.0, 1.35)));
    float top = smoothstep(0.72, 0.04, uv.y);
    float diagonal = smoothstep(0.0, 0.85, uv.x + (1.0 - uv.y) * 0.45);
    vec3 cyan = vec3(0.02, 0.18, 0.24) * (1.0 - diagonal) * 0.36;
    vec3 orange = vec3(0.28, 0.055, 0.005) * diagonal * 0.28;
    vec3 tint = cyan + orange;
    float alpha = clamp(edge * 0.52 + top * 0.08, 0.0, 0.62);
    COLOR = vec4(tint, alpha);
}
"""
    overlay_material.shader = overlay_shader
    cinematic_overlay.material = overlay_material
    cinematic_overlay.mouse_filter = Control.MOUSE_FILTER_IGNORE
    brand_layer.add_child(cinematic_overlay)

    var title_backdrop := ColorRect.new()
    title_backdrop.position = Vector2(38.0, 32.0)
    title_backdrop.size = Vector2(500.0, 136.0)
    title_backdrop.color = Color(0.004, 0.009, 0.013, 0.90)
    brand_layer.add_child(title_backdrop)

    var cyan_rule := ColorRect.new()
    cyan_rule.position = Vector2(38.0, 32.0)
    cyan_rule.size = Vector2(7.0, 136.0)
    cyan_rule.color = Color(0.08, 0.82, 1.0, 0.95)
    brand_layer.add_child(cyan_rule)

    var orange_rule := ColorRect.new()
    orange_rule.position = Vector2(45.0, 160.0)
    orange_rule.size = Vector2(255.0, 8.0)
    orange_rule.color = Color(1.0, 0.26, 0.035, 0.92)
    brand_layer.add_child(orange_rule)

    var title := Label.new()
    title.position = Vector2(68.0, 49.0)
    title.size = Vector2(470.0, 64.0)
    title.text = "DEADLINE: ZERO"
    title.add_theme_font_size_override("font_size", 48)
    title.add_theme_color_override("font_color", Color(0.92, 0.97, 1.0))
    title.add_theme_color_override("font_outline_color", Color(0.0, 0.0, 0.0, 0.86))
    title.add_theme_constant_override("outline_size", 4)
    brand_layer.add_child(title)

    var subtitle := Label.new()
    subtitle.position = Vector2(70.0, 113.0)
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

    var min_luma := 1.0
    var max_luma := 0.0
    var bright_samples := 0
    var samples := 0
    for y in range(0, image.get_height(), 16):
        for x in range(0, image.get_width(), 16):
            var luma := image.get_pixel(x, y).get_luminance()
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.18:
                bright_samples += 1
            samples += 1
    if max_luma - min_luma < 0.18 or float(bright_samples) / float(maxi(samples, 1)) < 0.015:
        push_error("Feature graphic lacks premium tonal separation")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save feature graphic candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_FEATURE_GRAPHIC_CANDIDATE_OK 1024x500")
    quit(0)
