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

    scene.elapsed = 82.0
    scene.kills = 46
    scene.level = 4
    scene.xp = 0
    scene.xp_next = 92
    scene.director_profile = scene.run_director.profile(scene.elapsed, scene.level)
    scene.hud.set_progress(scene.xp, scene.xp_next, scene.level, scene.kills, scene.elapsed, get_nodes_in_group("enemies").size())

    seed(20261005)
    scene._on_xp_collected(scene.xp_next)
    for _frame in range(6):
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

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save upgrade Store capture: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_UPGRADE_STORE_FRAME_OK %dx%d choices=%d" % [width, height, scene.pending_upgrades.size()])
    quit(0)
