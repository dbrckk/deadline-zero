extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-pressure-frame.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")
const REQUIRED_KINDS := ["runner", "charger", "harrier", "regenerator", "brute", "elite"]

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    # Remove the opening roster so this evidence isolates real mid-run archetype readability.
    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    scene.elapsed = 72.0
    scene.spawn_clock = 999.0
    scene.next_boss_time = 9999.0
    scene.director_profile = scene.run_director.profile(scene.elapsed, 5)
    scene.max_enemies = 24

    var player := scene.player as DZPlayer
    if player == null:
        push_error("Pressure-frame QA has no player")
        quit(1)
        return
    player.max_health = 5000.0
    player.health = 5000.0
    player.invulnerability = 10.0
    player.weapon_damage = 6.0
    player.fire_interval = 0.18

    var positions := {
        "runner": Vector3(-4.8, 0.0, -2.0),
        "charger": Vector3(4.8, 0.0, -2.6),
        "harrier": Vector3(-5.8, 0.0, 3.0),
        "regenerator": Vector3(5.7, 0.0, 2.8),
        "brute": Vector3(-2.5, 0.0, 6.0),
        "elite": Vector3(2.8, 0.0, 5.8),
    }

    for kind in REQUIRED_KINDS:
        scene._spawn_enemy(kind)
        await process_frame

    var seen := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null:
            continue
        if positions.has(enemy.kind):
            enemy.global_position = positions[enemy.kind]
            seen[enemy.kind] = true

    for kind in REQUIRED_KINDS:
        if not seen.has(kind):
            push_error("Pressure-frame QA missing archetype: %s" % kind)
            quit(1)
            return

    # Let the real combat loop produce movement, targeting, projectiles and telegraph states.
    for _frame in range(72):
        await process_frame

    if bool(scene.get("game_over")):
        push_error("Pressure-frame QA reached game over")
        quit(1)
        return

    var active_kinds := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and not enemy.dead:
            active_kinds[enemy.kind] = true
    if active_kinds.size() < 4:
        push_error("Pressure-frame QA lost too many archetypes before capture: %d remain" % active_kinds.size())
        quit(1)
        return

    # Validate phone-scale readability in screen space, not just world-space spacing.
    # A visually black/non-black image gate cannot catch actors collapsing into one blob.
    var viewport_size := get_root().get_visible_rect().size
    var player_screen := scene.camera.unproject_position(player.global_position + Vector3(0.0, 0.9, 0.0))
    var projected_enemies: Array[Dictionary] = []
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or not active_kinds.has(enemy.kind):
            continue
        if scene.camera.is_position_behind(enemy.global_position):
            continue
        var screen_pos := scene.camera.unproject_position(enemy.global_position + Vector3(0.0, 0.9, 0.0))
        if screen_pos.x < 28.0 or screen_pos.y < 28.0 or screen_pos.x > viewport_size.x - 28.0 or screen_pos.y > viewport_size.y - 28.0:
            continue
        projected_enemies.append({"kind": enemy.kind, "screen": screen_pos})

    if projected_enemies.size() < 4:
        push_error("Pressure-frame QA has too few readable on-screen archetypes: %d" % projected_enemies.size())
        quit(1)
        return

    var min_player_distance := INF
    var min_enemy_distance := INF
    for index in range(projected_enemies.size()):
        var sample: Dictionary = projected_enemies[index]
        var sample_screen: Vector2 = sample["screen"]
        min_player_distance = minf(min_player_distance, sample_screen.distance_to(player_screen))
        for other_index in range(index + 1, projected_enemies.size()):
            var other: Dictionary = projected_enemies[other_index]
            var other_screen: Vector2 = other["screen"]
            min_enemy_distance = minf(min_enemy_distance, sample_screen.distance_to(other_screen))

    if min_player_distance < 36.0:
        push_error("Pressure-frame actor overlap hides the player silhouette: min_player_px=%.2f" % min_player_distance)
        quit(1)
        return
    if min_enemy_distance < 28.0:
        push_error("Pressure-frame enemy silhouettes collapse together: min_enemy_px=%.2f" % min_enemy_distance)
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Pressure-frame QA has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Pressure-frame QA produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Pressure frame must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var bright := 0
    var total := 0
    var sum_luma := 0.0
    for y in range(0, height, 8):
        for x in range(0, width, 8):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            sum_luma += luma
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    var average_luma := sum_luma / float(maxi(total, 1))
    if bright_fraction < 0.008 or average_luma < 0.006:
        push_error("Pressure frame is effectively black: bright=%.5f avg=%.5f" % [bright_fraction, average_luma])
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save pressure-frame artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PRESSURE_FRAME_OK %dx%d kinds=%d onscreen=%d player_px=%.2f enemy_px=%.2f bright=%.5f avg=%.5f" % [
        width, height, active_kinds.size(), projected_enemies.size(), min_player_distance, min_enemy_distance, bright_fraction, average_luma
    ])
    quit(0)
