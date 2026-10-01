extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-archetype-roster.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")
const KINDS := ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]
const POSITIONS := [
    Vector3(-6.0, 0.0, -2.2),
    Vector3(-3.0, 0.0, -2.2),
    Vector3(0.0, 0.0, -2.2),
    Vector3(3.0, 0.0, -2.2),
    Vector3(-4.2, 0.0, 2.6),
    Vector3(0.0, 0.0, 2.6),
    Vector3(4.2, 0.0, 2.6),
]

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var main := MAIN_SCENE.instantiate()
    if main == null:
        push_error("Unable to instantiate Main.tscn for archetype roster")
        quit(1)
        return

    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    if main.player == null or main.camera == null:
        push_error("Roster visual QA requires real player/camera runtime anchors")
        quit(1)
        return

    main.player.set_combat_enabled(false)
    main.player.visible = false
    if main.hud != null:
        main.hud.visible = false

    for index in range(KINDS.size()):
        var enemy := DZEnemy.new()
        enemy.configure(KINDS[index], 1.0, main.player)
        enemy.set_combat_enabled(false)
        main.add_child(enemy)
        enemy.global_position = POSITIONS[index]
        if enemy.global_position.length_squared() > 0.01:
            enemy.look_at(Vector3(0.0, enemy.global_position.y, 0.0), Vector3.UP)

    main.camera.global_position = Vector3(0.0, 11.8, 11.8)
    main.camera.fov = 43.0
    main.camera.look_at(Vector3(0.0, 0.85, 0.0), Vector3.UP)

    for _frame in range(8):
        await process_frame

    var seen := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null:
            seen[enemy.kind] = true
    for kind in KINDS:
        if not seen.has(kind):
            push_error("Archetype roster missing runtime enemy: %s" % kind)
            quit(1)
            return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Archetype roster viewport has no render texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Archetype roster produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Archetype roster must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var bright_samples := 0
    var total_samples := 0
    for y in range(0, height, 10):
        for x in range(0, width, 10):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total_samples += 1
            if luma > 0.045:
                bright_samples += 1
    if float(bright_samples) / float(maxi(total_samples, 1)) < 0.01:
        push_error("Archetype roster render is effectively black")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save archetype roster artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_ARCHETYPE_ROSTER_OK %dx%d kinds=%d" % [width, height, KINDS.size()])
    quit(0)
