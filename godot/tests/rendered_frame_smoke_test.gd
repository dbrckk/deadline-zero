extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-rendered-frame.png"
const SAMPLE_STEP := 8
const MIN_BRIGHT_FRACTION := 0.008
const MIN_AVERAGE_LUMA := 0.006
const MIN_LUMA_RANGE := 0.04

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var packed := load("res://scenes/Main.tscn") as PackedScene
    if packed == null:
        push_error("Unable to load Main.tscn for rendered-frame smoke")
        quit(1)
        return

    var scene := packed.instantiate()
    get_root().add_child(scene)

    await process_frame
    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

    # Capture early enough to prove active combat rather than the run-end overlay, while still
    # giving imported meshes, materials, HUD and camera enough real render frames to settle.
    for _frame in range(30):
        await process_frame

    if bool(scene.get("game_over")):
        push_error("Rendered-frame smoke reached game over before active-combat capture")
        quit(1)
        return
    if get_nodes_in_group("enemies").is_empty():
        push_error("Rendered-frame smoke has no active enemies")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Root viewport has no render texture")
        quit(1)
        return

    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Rendered-frame smoke produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Rendered frame must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var total := 0
    var bright := 0
    var sum_luma := 0.0
    var min_luma := 1.0
    var max_luma := 0.0
    for y in range(0, height, SAMPLE_STEP):
        for x in range(0, width, SAMPLE_STEP):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            sum_luma += luma
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    var average_luma := sum_luma / float(maxi(total, 1))
    var luma_range := max_luma - min_luma

    if bright_fraction < MIN_BRIGHT_FRACTION:
        push_error("Rendered frame is effectively black: bright_fraction=%.5f" % bright_fraction)
        quit(1)
        return
    if average_luma < MIN_AVERAGE_LUMA:
        push_error("Rendered frame average luminance is too low: %.5f" % average_luma)
        quit(1)
        return
    if luma_range < MIN_LUMA_RANGE:
        push_error("Rendered frame lacks visual range: %.5f" % luma_range)
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save rendered-frame artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_RENDERED_FRAME_OK %dx%d bright=%.5f avg=%.5f range=%.5f" % [
        width, height, bright_fraction, average_luma, luma_range
    ])
    quit(0)
