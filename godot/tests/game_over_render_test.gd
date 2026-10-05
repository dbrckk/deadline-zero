extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-game-over-frame.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    scene.kills = 37
    scene.level = 8
    scene.elapsed = 154.0
    scene._on_player_died()
    for _frame in range(6):
        await process_frame

    if not scene.game_over or scene.hud == null or not scene.hud.game_over_panel.visible:
        push_error("Game-over render QA did not enter final run state")
        quit(1)
        return
    if scene.hud.game_over_scrim == null or not scene.hud.game_over_scrim.visible:
        push_error("Game-over render QA is missing dimmed backdrop")
        quit(1)
        return
    if scene.hud.game_over_summary.text != "LEVEL 8   •   KILLS 37   •   02:34":
        push_error("Game-over render QA summary is incorrect")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Game-over render QA has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Game-over render QA produced no image")
        quit(1)
        return
    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Game-over frame must remain landscape, got %dx%d" % [width, height])
        quit(1)
        return

    var center := image.get_pixel(width / 2, height / 2)
    if center.get_luminance() < 0.02:
        push_error("Game-over panel center rendered effectively black")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save game-over render artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_GAME_OVER_FRAME_OK %dx%d" % [width, height])
    quit(0)
