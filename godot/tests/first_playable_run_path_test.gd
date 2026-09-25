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

    var previous_level: int = main.level
    var threshold: int = main.xp_next
    main._on_xp_collected(threshold)
    if main.level != previous_level + 1 or main.pending_upgrades.size() != 3:
        push_error("XP progression did not open a three-choice upgrade")
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
