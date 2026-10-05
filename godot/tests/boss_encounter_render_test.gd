extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-boss-encounter.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    scene.elapsed = 150.0
    scene.spawn_clock = 999.0
    scene.next_boss_time = 9999.0
    scene.director_profile = scene.run_director.profile(scene.elapsed, 8)
    scene.max_enemies = 24

    var player := scene.player as DZPlayer
    if player == null:
        push_error("Boss capture has no player")
        quit(1)
        return
    player.max_health = 5000.0
    player.health = 5000.0
    player.invulnerability = 20.0
    player.weapon_damage = 8.0
    player.fire_interval = 0.22

    scene._spawn_enemy("boss")
    scene._spawn_enemy("runner")
    scene._spawn_enemy("elite")
    await process_frame

    var boss: DZEnemy
    var runner: DZEnemy
    var elite: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null:
            continue
        match enemy.kind:
            "boss":
                boss = enemy
            "runner":
                runner = enemy
            "elite":
                elite = enemy

    if boss == null or runner == null or elite == null:
        push_error("Boss capture failed to stage the encounter roster")
        quit(1)
        return

    boss.global_position = Vector3(0.0, 0.0, -6.6)
    runner.global_position = Vector3(-4.6, 0.0, -1.6)
    elite.global_position = Vector3(4.7, 0.0, -1.8)
    boss.health = boss.max_health * 0.72
    boss.health_changed.emit(boss.health, boss.max_health)

    scene.current_boss = boss
    scene.boss_reveal_target = boss
    scene.boss_reveal_left = scene.BOSS_REVEAL_DURATION
    scene.hud.show_boss("REVENANT PRIME", boss.max_health)
    scene.hud.set_boss_health(boss.health, boss.max_health)

    for _tick in range(24):
        await physics_frame
    for _frame in range(8):
        await process_frame

    if boss.dead or not scene.hud.boss_panel.visible:
        push_error("Boss capture lost active boss presentation before capture")
        quit(1)
        return

    var boss_screen: Vector2 = scene.camera.unproject_position(boss.global_position + Vector3(0.0, 1.0, 0.0))
    var viewport_size: Vector2 = get_root().get_visible_rect().size
    if scene.camera.is_position_behind(boss.global_position):
        push_error("Boss capture camera placed the boss behind the camera")
        quit(1)
        return
    if boss_screen.x < viewport_size.x * 0.20 or boss_screen.x > viewport_size.x * 0.80:
        push_error("Boss capture framing pushed the boss outside the central readable zone")
        quit(1)
        return
    if boss_screen.y < viewport_size.y * 0.18 or boss_screen.y > viewport_size.y * 0.82:
        push_error("Boss capture framing pushed the boss outside the vertical readable zone")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Boss capture has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Boss capture produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 1280 or height < 720:
        push_error("Boss capture must be landscape and at least 1280x720, got %dx%d" % [width, height])
        quit(1)
        return

    var bright := 0
    var total := 0
    var min_luma := 1.0
    var max_luma := 0.0
    for y in range(0, height, 10):
        for x in range(0, width, 10):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    if bright_fraction < 0.008 or max_luma - min_luma < 0.05:
        push_error("Boss capture lacks readable tonal separation")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save boss encounter capture: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_BOSS_CAPTURE_OK %dx%d boss_screen=(%.1f,%.1f)" % [width, height, boss_screen.x, boss_screen.y])
    quit(0)
