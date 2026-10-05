extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var main := MAIN_SCENE.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    if main.player == null or main.hud == null or main.camera == null:
        push_error("Run path did not initialize player, HUD and camera")
        quit(1)
        return
    if main.player.shot_audio_voices.size() != 3:
        push_error("Player weapon audio did not initialize bounded 3-voice polyphony")
        quit(1)
        return
    for voice in main.player.shot_audio_voices:
        if voice.bus != "SFX":
            push_error("Player weapon audio voice escaped the SFX bus")
            quit(1)
            return
    if main.impact_audio_voices.size() != 4:
        push_error("Combat impacts did not initialize bounded 4-voice polyphony")
        quit(1)
        return
    if main.music_audio == null or main.music_audio.stream == null:
        push_error("Run path did not initialize authored background music")
        quit(1)
        return
    if main.music_audio.stream.get_length() < 11.5 or not main.music_audio.playing:
        push_error("Authored background music did not enter looping playback")
        quit(1)
        return
    if main.music_pressure_audio == null or main.music_pressure_audio.stream == null:
        push_error("Run path did not initialize adaptive pressure music")
        quit(1)
        return
    if main.music_pressure_audio.stream.get_length() < 11.5 or not main.music_pressure_audio.playing:
        push_error("Adaptive pressure music did not enter synchronized playback")
        quit(1)
        return
    main.director_profile["phase"] = "EXTINCTION"
    main._update_music_pressure(1.0)
    if main.music_pressure_audio.volume_db <= main.MUSIC_PRESSURE_BREACH_DB:
        push_error("Adaptive music layer did not rise with run pressure")
        quit(1)
        return
    if main.boss_audio == null or main.boss_audio.stream == null or main.boss_audio.stream.get_length() < 2.0:
        push_error("Run path did not initialize authored boss stinger")
        quit(1)
        return
    if get_nodes_in_group("enemies").size() < 8:
        push_error("Run path did not create initial enemy population")
        quit(1)
        return

    main.camera_kick = 0.0
    main.player.invulnerability = 0.0
    var health_before_camera_feedback: float = main.player.health
    main.player.take_damage(5.0)
    if not is_equal_approx(main.player.health, health_before_camera_feedback - 5.0):
        push_error("First-playable damage probe did not reduce player health")
        quit(1)
        return
    if main.camera_kick <= 0.0 or main.camera_kick > 0.115:
        push_error("Received player damage did not produce bounded camera feedback")
        quit(1)
        return

    main._on_camera_shake_changed(false)
    main.camera_kick = 0.0
    main.player.invulnerability = 0.0
    var no_shake_health: float = main.player.health
    main.player.take_damage(5.0)
    if not is_equal_approx(main.player.health, no_shake_health - 5.0):
        push_error("Camera-shake-off damage probe did not reduce player health")
        quit(1)
        return
    if main.camera_kick > 0.0001:
        push_error("Received damage bypassed disabled camera-shake setting")
        quit(1)
        return
    main._on_camera_shake_changed(true)

    Engine.time_scale = 0.12
    main.hit_freeze_left = 0.020
    main._process(0.020 * 0.12)
    if main.hit_freeze_left > 0.0001 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Hit-freeze duration remained coupled to slowed Engine.time_scale")
        quit(1)
        return

    main._on_hit_stop_changed(false)
    main._on_camera_shake_changed(false)
    main._on_haptics_changed(false)
    main.hit_freeze_left = 0.0
    main.camera_kick = 0.0
    main._on_enemy_impact(Vector3.ZERO, true, true, false)
    if main.hit_freeze_left > 0.0001:
        push_error("Enemy impact bypassed disabled hit-stop setting")
        quit(1)
        return
    if main.camera_kick > 0.0001:
        push_error("Enemy impact bypassed disabled camera-shake setting")
        quit(1)
        return
    main._on_hit_stop_changed(true)
    main._on_camera_shake_changed(true)
    main._on_haptics_changed(true)

    main.player.global_position = Vector3(42.0, 0.0, -44.0)
    main.player.velocity = Vector3(3.0, 0.0, -2.0)
    main.player._constrain_to_arena()
    if absf(main.player.global_position.x) > DZPlayer.ARENA_HALF_EXTENT + 0.001 or absf(main.player.global_position.z) > DZPlayer.ARENA_HALF_EXTENT + 0.001:
        push_error("Player escaped the authored arena floor bounds")
        quit(1)
        return
    if absf(main.player.velocity.x) > 0.001 or absf(main.player.velocity.z) > 0.001:
        push_error("Arena clamp did not cancel outward player velocity")
        quit(1)
        return
    main.player.global_position = Vector3.ZERO

    var clamped_spawn: Vector3 = main._clamp_spawn_position(Vector3(60.0, 0.0, -60.0))
    if absf(clamped_spawn.x) > 34.001 or absf(clamped_spawn.z) > 34.001:
        push_error("Enemy spawn position can escape the authored arena floor")
        quit(1)
        return

    main.player.global_position = Vector3(30.0, 0.0, 30.0)
    main.spawn_rng.seed = 777
    var edge_spawn: Vector3 = main._spawn_position_around_player(18.0)
    var edge_spawn_distance: float = main.player.global_position.distance_to(edge_spawn)
    if absf(edge_spawn.x) > 34.001 or absf(edge_spawn.z) > 34.001:
        push_error("Edge-player spawn escaped arena-safe bounds")
        quit(1)
        return
    if edge_spawn_distance < 11.99 or edge_spawn_distance > 18.01:
        push_error("Arena-safe spawn collapsed the intended enemy spawn distance")
        quit(1)
        return
    main.player.global_position = Vector3.ZERO

    var tactical_rig := main.player.get_node_or_null("TacticalRig") as Node3D
    var tactical_backplate := main.player.get_node_or_null("TacticalRig/TacticalBackplate") as MeshInstance3D
    var weapon_accent := main.player.get_node_or_null("WeaponAccent") as MeshInstance3D
    var authored_rifle := main.player.get_node_or_null("Rifle") as Node3D
    if tactical_rig == null or tactical_backplate == null or weapon_accent == null or authored_rifle == null:
        push_error("Player production presentation is missing tactical rig/rifle identity")
        quit(1)
        return

    main.hud.show_touch_stick(Vector2(4.0, 716.0))
    var edge_pos: Vector2 = main.hud.touch_stick_root.position
    var edge_size: Vector2 = main.hud.touch_stick_root.size
    var viewport_size: Vector2 = main.get_viewport().get_visible_rect().size
    if edge_pos.x < 7.5 or edge_pos.y < 7.5:
        push_error("Touch stick can clip beyond top/left phone bounds")
        quit(1)
        return
    if edge_pos.x + edge_size.x > viewport_size.x - 7.5 or edge_pos.y + edge_size.y > viewport_size.y - 7.5:
        push_error("Touch stick can clip beyond bottom/right phone bounds")
        quit(1)
        return
    main.hud.hide_touch_stick()

    var touch_press := InputEventScreenTouch.new()
    touch_press.index = 7
    touch_press.position = Vector2(180.0, 520.0)
    touch_press.pressed = true
    main._unhandled_input(touch_press)
    if main.touch_id != 7 or main.hud.touch_stick_root == null or not main.hud.touch_stick_root.visible:
        push_error("Touch press did not activate floating movement stick")
        quit(1)
        return

    var micro_drag := InputEventScreenDrag.new()
    micro_drag.index = 7
    micro_drag.position = Vector2(186.0, 524.0)
    main._unhandled_input(micro_drag)
    var initial_knob_center: Vector2 = (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
    if main.player.touch_move.length_squared() > 0.0001:
        push_error("Touch-stick deadzone allowed unintended player drift")
        quit(1)
        return
    if main.hud.touch_stick_knob.position.distance_to(initial_knob_center) > 0.5:
        push_error("Touch-stick visual moved inside control deadzone")
        quit(1)
        return

    var touch_drag := InputEventScreenDrag.new()
    touch_drag.index = 7
    touch_drag.position = Vector2(250.0, 455.0)
    main._unhandled_input(touch_drag)
    if main.player.touch_move.length() < 0.50:
        push_error("Touch drag did not drive player movement vector")
        quit(1)
        return
    var knob_center: Vector2 = (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
    var knob_distance: float = main.hud.touch_stick_knob.position.distance_to(knob_center)
    var max_knob_travel: float = (main.hud.touch_stick_root.size.x - main.hud.touch_stick_knob.size.x) * 0.5 - 4.0
    if knob_distance < 8.0:
        push_error("Touch drag did not move floating stick knob")
        quit(1)
        return
    if knob_distance > max_knob_travel + 0.5:
        push_error("Touch stick knob escaped its visual base")
        quit(1)
        return

    var touch_release := InputEventScreenTouch.new()
    touch_release.index = 7
    touch_release.position = touch_drag.position
    touch_release.pressed = false
    main._unhandled_input(touch_release)
    if main.touch_id != -1 or main.player.touch_move.length_squared() > 0.0001 or main.hud.touch_stick_root.visible:
        push_error("Touch release did not reset movement stick state")
        quit(1)
        return

    var pause_button := main.hud.get_node_or_null("PauseButton") as Button
    var pause_panel := main.hud.get_node_or_null("PausePanel") as PanelContainer
    if pause_button == null or pause_panel == null:
        push_error("Pause controls are unavailable in first-playable path")
        quit(1)
        return

    var pressure_enemy: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var candidate := node as DZEnemy
        if candidate != null:
            pressure_enemy = candidate
            break
    if pressure_enemy == null:
        push_error("Pause pressure-cleanup test has no enemy")
        quit(1)
        return
    pressure_enemy.global_position = main.player.global_position + Vector3(1.6, 0.0, 0.0)
    main.player._update_player_marker_pressure(pressure_enemy)
    var pressure_locator := main.player.get_node_or_null("PlayerPressureLocator") as Node3D
    if pressure_locator == null or not pressure_locator.visible:
        push_error("First-playable pressure locator did not activate before pause")
        quit(1)
        return

    main.hit_freeze_left = 0.030
    Engine.time_scale = 0.12
    pause_button.pressed.emit()
    await process_frame
    if not paused or not pause_panel.visible:
        push_error("Pause action did not pause gameplay and show settings")
        quit(1)
        return
    if main.hit_freeze_left > 0.0 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Pause transition retained global hit-freeze state")
        quit(1)
        return
    if main.player.player_marker_pressure or pressure_locator.visible:
        push_error("Pause overlay must clear stale combat pressure feedback")
        quit(1)
        return
    var resume_button := pause_panel.find_child("ResumeButton", true, false) as Button
    resume_button.pressed.emit()
    await process_frame
    if paused or pause_panel.visible:
        push_error("Resume action did not restore gameplay")
        quit(1)
        return

    var master_slider := pause_panel.find_child("MasterVolume", true, false) as HSlider
    var sfx_slider := pause_panel.find_child("SfxVolume", true, false) as HSlider
    var music_slider := pause_panel.find_child("MusicVolume", true, false) as HSlider
    master_slider.value = 0.35
    sfx_slider.value = 0.45
    music_slider.value = 0.40
    await process_frame
    var master_bus := AudioServer.get_bus_index("Master")
    var sfx_bus := AudioServer.get_bus_index("SFX")
    var music_bus := AudioServer.get_bus_index("Music")
    if sfx_bus < 0 or music_bus < 0:
        push_error("Pause settings did not create dedicated SFX/Music audio buses")
        quit(1)
        return
    if abs(AudioServer.get_bus_volume_db(master_bus) - linear_to_db(0.35)) > 0.25:
        push_error("Master volume slider did not update Master bus")
        quit(1)
        return
    if abs(AudioServer.get_bus_volume_db(sfx_bus) - linear_to_db(0.45)) > 0.25:
        push_error("SFX volume slider did not update SFX bus")
        quit(1)
        return
    if abs(AudioServer.get_bus_volume_db(music_bus) - linear_to_db(0.40)) > 0.25:
        push_error("Music volume slider did not update Music bus")
        quit(1)
        return

    var upgrade_touch_press := InputEventScreenTouch.new()
    upgrade_touch_press.index = 9
    upgrade_touch_press.position = Vector2(190.0, 525.0)
    upgrade_touch_press.pressed = true
    main._unhandled_input(upgrade_touch_press)

    var upgrade_touch_drag := InputEventScreenDrag.new()
    upgrade_touch_drag.index = 9
    upgrade_touch_drag.position = Vector2(260.0, 460.0)
    main._unhandled_input(upgrade_touch_drag)
    if main.player.touch_move.length_squared() <= 0.01 or not main.hud.touch_stick_root.visible:
        push_error("Upgrade transition test could not establish active touch movement")
        quit(1)
        return

    main.hit_freeze_left = 0.030
    Engine.time_scale = 0.12
    var previous_level: int = main.level
    var threshold: int = main.xp_next
    main._on_xp_collected(threshold)
    if main.level != previous_level + 1 or main.pending_upgrades.size() != 3:
        push_error("XP progression did not open a three-choice upgrade")
        quit(1)
        return
    if main.director_refresh_clock > 0.0:
        push_error("Level-up must force the run director to refresh on the next physics tick")
        quit(1)
        return
    if main.touch_id != -1 or main.player.touch_move.length_squared() > 0.0001 or main.hud.touch_stick_root.visible:
        push_error("Upgrade overlay retained stale mobile movement state")
        quit(1)
        return
    if main.hit_freeze_left > 0.0 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Upgrade overlay retained global hit-freeze state")
        quit(1)
        return
    if not main.hud.upgrade_panel.visible or not paused:
        push_error("Upgrade state did not pause combat and show the upgrade panel")
        quit(1)
        return

    main._on_upgrade_chosen(0)
    if main.pending_upgrades.size() != 0 or main.hud.upgrade_panel.visible or paused:
        push_error("Upgrade selection did not resume the run cleanly")
        quit(1)
        return

    var chain_start_level: int = int(main.level)
    var chain_first_threshold: int = int(main.xp_next)
    var chain_second_threshold := int(round(float(chain_first_threshold) * 1.24 + 4.0))
    main._on_xp_collected(chain_first_threshold + chain_second_threshold + 3)
    if main.level != chain_start_level + 1 or main.pending_upgrades.size() != 3 or not paused:
        push_error("Banked multi-level XP did not open the first upgrade choice")
        quit(1)
        return
    main._on_upgrade_chosen(0)
    if main.level != chain_start_level + 2 or main.pending_upgrades.size() != 3 or not main.hud.upgrade_panel.visible or not paused:
        push_error("Banked XP did not immediately chain the next level-up choice")
        quit(1)
        return
    if main.xp != 3:
        push_error("Multi-level XP chain did not preserve overflow XP")
        quit(1)
        return
    main._on_upgrade_chosen(0)
    if not main.pending_upgrades.is_empty() or main.hud.upgrade_panel.visible or paused:
        push_error("Final chained upgrade did not resume gameplay cleanly")
        quit(1)
        return

    main.player.apply_upgrade("inferno_protocol")
    seed(424242)
    for offer_index in range(12):
        main._offer_upgrade()
        for upgrade in main.pending_upgrades:
            if String(upgrade["id"]).ends_with("_protocol"):
                push_error("Protocol upgrade remained in offer after a protocol was locked")
                quit(1)
                return
        main.pending_upgrades.clear()
        main.hud.hide_upgrade()
        paused = false

    var bosses_before := _count_kind("boss")
    main._spawn_enemy("boss")
    await process_frame
    if _count_kind("boss") != bosses_before + 1:
        push_error("Forced boss spawn failed")
        quit(1)
        return
    if not main.hud.boss_panel.visible or main.boss_reveal_target == null:
        push_error("Boss spawn did not activate boss HUD/reveal state")
        quit(1)
        return

    if main.current_boss == null or main.current_boss.dead:
        push_error("Boss spawn did not register the active boss singleton")
        quit(1)
        return
    var boss_count_before_deferred_spawn := _count_kind("boss")
    main.next_boss_time = main.elapsed
    main._physics_process(0.016)
    if _count_kind("boss") != boss_count_before_deferred_spawn:
        push_error("Boss scheduler spawned a second boss while one was still active")
        quit(1)
        return
    if main.next_boss_time < main.elapsed + main.BOSS_RETRY_DELAY - 0.05:
        push_error("Active boss did not create a recovery window before the next boss check")
        quit(1)
        return

    main.next_boss_time = main.elapsed + 0.5
    main._on_boss_health_changed(0.0, 100.0)
    if main.next_boss_time < main.elapsed + main.BOSS_RETRY_DELAY - 0.05:
        push_error("Boss death did not guarantee a full post-kill recovery window")
        quit(1)
        return

    var projectile := PROJECTILE_SCRIPT.new()
    main.add_child(projectile)
    projectile.velocity = Vector3(8.0, 0.0, 0.0)
    await process_frame

    var active_boss: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var candidate_boss := node as DZEnemy
        if candidate_boss != null and candidate_boss.kind == "boss" and not candidate_boss.dead:
            active_boss = candidate_boss
            break
    main.camera_kick = 0.12
    main.boss_reveal_left = 0.8
    main.boss_reveal_target = active_boss
    main.hud.impact_flash.visible = true
    main.hud.damage_vignette.visible = true
    main._on_player_died()
    if not main.game_over or not main.hud.game_over_panel.visible:
        push_error("Player death did not enter visible game-over state")
        quit(1)
        return
    if main.camera_kick > 0.0 or main.boss_reveal_left > 0.0 or main.boss_reveal_target != null:
        push_error("Run end retained transient camera combat state")
        quit(1)
        return
    if main.hud.impact_flash.visible or main.hud.damage_vignette.visible or main.hud.boss_panel.visible:
        push_error("Run end retained transient combat HUD overlays")
        quit(1)
        return
    if main.hud.wave_label.text != "RUN TERMINATED":
        push_error("Run-end HUD did not enter terminated state")
        quit(1)
        return
    if projectile.combat_enabled or projectile.velocity.length_squared() > 0.0:
        push_error("Active projectile was not frozen at run end")
        quit(1)
        return

    var game_over_touch := InputEventScreenTouch.new()
    game_over_touch.index = 12
    game_over_touch.position = Vector2(180.0, 520.0)
    game_over_touch.pressed = true
    main._unhandled_input(game_over_touch)
    if main.touch_id != -1 or main.hud.touch_stick_root.visible:
        push_error("Game-over input leaked into the mobile movement stick")
        quit(1)
        return

    var projectiles_after_freeze := get_nodes_in_group("projectiles").size()
    main.player.fire_clock = 0.0
    main.player._physics_process(0.016)
    if get_nodes_in_group("projectiles").size() != projectiles_after_freeze:
        push_error("Player continued auto-firing after death")
        quit(1)
        return
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and enemy.combat_enabled:
            push_error("Enemy remained combat-enabled after run end")
            quit(1)
            return

    var restart_button := main.hud.game_over_panel.find_child("RestartButton", true, false) as Button
    if restart_button == null or restart_button.disabled:
        push_error("Run-end restart action is unavailable")
        quit(1)
        return

    print("Deadline Zero first-playable run path: OK")
    quit(0)

func _count_kind(kind: String) -> int:
    var count := 0
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and enemy.kind == kind:
            count += 1
    return count
