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

    var tactical_rig := main.player.get_node_or_null("TacticalRig") as Node3D
    var tactical_backplate := main.player.get_node_or_null("TacticalRig/TacticalBackplate") as MeshInstance3D
    var weapon_accent := main.player.get_node_or_null("WeaponAccent") as MeshInstance3D
    var authored_rifle := main.player.get_node_or_null("Rifle") as Node3D
    if tactical_rig == null or tactical_backplate == null or weapon_accent == null or authored_rifle == null:
        push_error("Player production presentation is missing tactical rig/rifle identity")
        quit(1)
        return

    main.hud.show_touch_stick(Vector2(4.0, 716.0))
    var edge_pos := main.hud.touch_stick_root.position
    var edge_size := main.hud.touch_stick_root.size
    var viewport_size := main.get_viewport().get_visible_rect().size
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
    var initial_knob_center := (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
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
    var knob_center := (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
    var knob_distance := main.hud.touch_stick_knob.position.distance_to(knob_center)
    var max_knob_travel := (main.hud.touch_stick_root.size.x - main.hud.touch_stick_knob.size.x) * 0.5 - 4.0
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

    pause_button.pressed.emit()
    await process_frame
    if not paused or not pause_panel.visible:
        push_error("Pause action did not pause gameplay and show settings")
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
    master_slider.value = 0.35
    sfx_slider.value = 0.45
    await process_frame
    var master_bus := AudioServer.get_bus_index("Master")
    var sfx_bus := AudioServer.get_bus_index("SFX")
    if sfx_bus < 0:
        push_error("Pause settings did not create dedicated SFX audio bus")
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

    var previous_level: int = main.level
    var threshold: int = main.xp_next
    main._on_xp_collected(threshold)
    if main.level != previous_level + 1 or main.pending_upgrades.size() != 3:
        push_error("XP progression did not open a three-choice upgrade")
        quit(1)
        return
    if main.touch_id != -1 or main.player.touch_move.length_squared() > 0.0001 or main.hud.touch_stick_root.visible:
        push_error("Upgrade overlay retained stale mobile movement state")
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

    var projectile := PROJECTILE_SCRIPT.new()
    main.add_child(projectile)
    projectile.velocity = Vector3(8.0, 0.0, 0.0)
    await process_frame

    main._on_player_died()
    if not main.game_over or not main.hud.game_over_panel.visible:
        push_error("Player death did not enter visible game-over state")
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
