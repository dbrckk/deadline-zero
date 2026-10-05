extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-play-icon-candidate.png"
const ICON_TEXTURE := preload("res://assets/ui/deadline_zero_icon.svg")

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var viewport := SubViewport.new()
    viewport.size = Vector2i(512, 512)
    viewport.render_target_update_mode = SubViewport.UPDATE_ALWAYS
    viewport.transparent_bg = false
    get_root().add_child(viewport)

    var background := ColorRect.new()
    background.position = Vector2.ZERO
    background.size = Vector2(512.0, 512.0)
    background.color = Color(0.005, 0.008, 0.010)
    viewport.add_child(background)

    var emblem := TextureRect.new()
    emblem.position = Vector2.ZERO
    emblem.size = Vector2(512.0, 512.0)
    emblem.texture = ICON_TEXTURE
    emblem.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
    emblem.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
    emblem.texture_filter = CanvasItem.TEXTURE_FILTER_LINEAR_WITH_MIPMAPS
    viewport.add_child(emblem)

    for _frame in range(4):
        await process_frame

    var image := viewport.get_texture().get_image()
    if image == null or image.is_empty() or image.get_width() != 512 or image.get_height() != 512:
        push_error("Brand icon capture did not render exact 512x512")
        quit(1)
        return

    var sample_points := [
        Vector2i(256, 256),
        Vector2i(256, 92),
        Vector2i(150, 256),
        Vector2i(362, 256)
    ]
    var luminance_range := 0.0
    var min_luma := 1.0
    var max_luma := 0.0
    for point in sample_points:
        var color := image.get_pixelv(point)
        var luma := color.get_luminance()
        min_luma = minf(min_luma, luma)
        max_luma = maxf(max_luma, luma)
    luminance_range = max_luma - min_luma
    if luminance_range < 0.08:
        push_error("Play icon emblem rendered without enough visual contrast")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save brand icon candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PLAY_ICON_CANDIDATE_OK 512x512 contrast=%.3f" % luminance_range)
    quit(0)
