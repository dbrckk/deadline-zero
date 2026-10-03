extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame

    var pulse := player.get_node_or_null("DamagePulse") as MeshInstance3D
    if pulse == null:
        push_error("Player damage feedback pulse is missing")
        quit(1)
        return
    if pulse.visible:
        push_error("Damage pulse should start hidden")
        quit(1)
        return
    if not pulse.mesh is TorusMesh:
        push_error("Damage pulse must remain a thin ring, not a filled floor disc")
        quit(1)
        return
    var ring := pulse.mesh as TorusMesh
    if ring.outer_radius - ring.inner_radius > 0.20:
        push_error("Damage pulse ring is too visually heavy")
        quit(1)
        return

    var initial_health: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, initial_health - 12.0):
        push_error("Player health did not decrease on first hit")
        quit(1)
        return
    if not pulse.visible:
        push_error("Damage pulse did not become visible after damage")
        quit(1)
        return
    if player.invulnerability <= 0.0:
        push_error("Damage hit did not arm invulnerability window")
        quit(1)
        return

    if player.authored_anim != null and player.authored_anim.has_animation("HitReact"):
        if player.current_anim != "HitReact" or player.hit_reaction_left <= 0.0:
            push_error("Non-lethal player damage did not enter authored HitReact presentation")
            quit(1)
            return

    var health_after_first_hit: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, health_after_first_hit):
        push_error("Invulnerability window failed to reject immediate repeated damage")
        quit(1)
        return

    await create_timer(0.20).timeout
    if pulse.visible:
        push_error("Damage pulse did not clear after its presentation window")
        quit(1)
        return

    if player.hit_reaction_left > 0.0:
        push_error("Player HitReact presentation did not release after its short lock window")
        quit(1)
        return
    if player.authored_anim != null and player.authored_anim.has_animation("Idle_Gun") and player.current_anim == "HitReact":
        push_error("Player remained stuck in HitReact after the presentation window")
        quit(1)
        return

    player.invulnerability = 0.0
    var died_emitted := false
    player.died.connect(func() -> void: died_emitted = true)
    player.take_damage(player.health + 1000.0)
    if player.health > 0.0 or not died_emitted:
        push_error("Lethal player damage did not enter death state")
        quit(1)
        return
    if player.authored_anim != null and player.authored_anim.has_animation("Death") and player.current_anim != "Death":
        push_error("Lethal player damage did not play authored Death animation")
        quit(1)
        return

    print("Deadline Zero player damage feedback: OK")
    quit(0)
