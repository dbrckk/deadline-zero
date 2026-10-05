extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-upgrade-choice.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

    scene.elapsed = 82.0
    scene.kills = 46
    scene.level = 4
    scene.xp = 0
    scene.xp_next = 92
    scene.director_profile = scene.run_director.profile(scene.elapsed, scene.level)
    scene.hud.set_progress(scene.xp, scene.xp_next, scene.level, scene.kills, scene.elapsed, get_nodes_in_group("enemies").size())

    seed(20261005)
    scene._on_xp_collected(scene.xp_next)
    for _frame in range(18):
        await process_frame

    if scene.pending_upgrades.size() != 3:
        push_error("Upgrade Store capture did not produce three choices")
        quit(1)
        return
    if not scene.hud.upgrade_panel.visible or not paused:
        push_error("Upgrade Store capture did not enter paused upgrade presentation")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Upgrade Store capture has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Upgrade Store capture produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 1280 or height < 720:
        push_error("Upgrade Store capture must remain landscape and at least 1280x720, got %dx%d" % [width, height])
        quit(1)
        return

    var center := image.get_pixel(width / 2, height / 2)
    if center.get_luminance() < 0.02:
        push_error("Upgrade Store capture center rendered effectively black")
        quit(1)
        return

    var panel_min_luma := 1.0
    var panel_max_luma := 0.0
    var accent_pixels := 0
    var sampled_pixels := 0
    var x_start := int(width * 0.23)
    var x_end := int(width * 0.77)
    var y_start := int(height * 0.30)
    var y_end := int(height * 0.72)
    for y in range(y_start, y_end, 6):
        for x in range(x_start, x_end, 6):
            var pixel := image.get_pixel(x, y)
            var luma := pixel.get_luminance()
            panel_min_luma = minf(panel_min_luma, luma)
            panel_max_luma = maxf(panel_max_luma, luma)
            if pixel.b > pixel.r * 1.10 or pixel.r > pixel.b * 1.18:
                accent_pixels += 1
            sampled_pixels += 1
    if panel_max_luma - panel_min_luma < 0.08:
        push_error("Upgrade Store lacks premium tonal separation")
        quit(1)
        return
    if accent_pixels < maxi(18, int(sampled_pixels * 0.006)):
        push_error("Upgrade Store lacks readable accent-color hierarchy")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save upgrade Store capture: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_UPGRADE_STORE_FRAME_OK %dx%d choices=%d" % [width, height, scene.pending_upgrades.size()])
    quit(0)
