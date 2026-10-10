extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    enemy.spawn_secondary_fx = false
    root.add_child(enemy)
    await process_frame

    if not enemy.has_method("apply_burn") or not enemy.has_method("_process_status_effects"):
        push_error("Enemy burn status API is missing")
        quit(1)
        return

    # Integration check: repeated Inferno ticks change health without sending
    # the same noisy impact feedback as a direct rifle hit.
    var direct_hit_feedback: Array[bool] = []
    enemy.impact.connect(func(_at: Vector3, _crit: bool, killed: bool, _boss: bool) -> void:
        direct_hit_feedback.append(killed)
    )
    var before := enemy.health
    enemy.apply_burn(8.0, 1.0)
    enemy._process_status_effects(0.5)
    if enemy.health >= before or enemy.burn_left <= 0.0:
        push_error("Burn status did not deal damage over time")
        quit(1)
        return

    if not direct_hit_feedback.is_empty() or enemy.hit_reaction_tween != null:
        push_error("Nonlethal Inferno DoT must not emit per-tick impact or recoil")
        quit(1)
        return
    if get_nodes_in_group("damage_numbers").size() != 0:
        push_error("Nonlethal burn ticks must not spam floating damage labels")
        quit(1)
        return

    enemy._process_status_effects(0.6)
    if enemy.burn_left > 0.0:
        push_error("Burn status did not expire after its duration")
        quit(1)
        return
    if not direct_hit_feedback.is_empty():
        push_error("Burn expiry must not trigger conventional impact feedback")
        quit(1)
        return
    if not is_equal_approx(enemy.health, before - 8.0):
        push_error("Feedback suppression must preserve the exact total burn DPS")
        quit(1)
        return

    enemy.take_damage(5.0, false)
    if direct_hit_feedback.size() != 1 or direct_hit_feedback[0]:
        push_error("Conventional rifle damage must still emit nonlethal impacts")
        quit(1)
        return
    if enemy.hit_reaction_tween == null:
        push_error("Direct projectile damage lost enemy recoil feedback")
        quit(1)
        return

    # A lethal DoT should retain the ordinary one-shot kill confirmation,
    # XP signal and destruction path rather than silently removing enemies.
    var lethal_target := ENEMY_SCRIPT.new()
    lethal_target.configure("shambler", 1.0, target)
    lethal_target.process_mode = Node.PROCESS_MODE_DISABLED
    lethal_target.spawn_secondary_fx = false
    root.add_child(lethal_target)
    await process_frame
    var lethal_impacts: Array[bool] = []
    var death_rewards: Array[int] = []
    lethal_target.impact.connect(func(_at: Vector3, _crit: bool, killed: bool, _boss: bool) -> void:
        lethal_impacts.append(killed)
    )
    lethal_target.died.connect(func(reward: int, _at: Vector3) -> void:
        death_rewards.append(reward)
    )
    lethal_target.health = 2.0
    lethal_target.apply_burn(8.0, 1.0)
    lethal_target._process_status_effects(0.25)
    if not lethal_target.dead or lethal_impacts.size() != 1 or not lethal_impacts[0]:
        push_error("Lethal Inferno tick must preserve one normal kill impact")
        quit(1)
        return
    if death_rewards != [lethal_target.xp_value]:
        push_error("Lethal Inferno tick did not award the enemy's normal XP")
        quit(1)
        return

    var inferno_target := ENEMY_SCRIPT.new()
    inferno_target.configure("shambler", 1.0, target)
    inferno_target.process_mode = Node.PROCESS_MODE_DISABLED
    inferno_target.spawn_secondary_fx = false
    root.add_child(inferno_target)
    await process_frame

    var projectile := PROJECTILE_SCRIPT.new()
    projectile.visual_profile = "inferno"
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    projectile.spawn_secondary_fx = false
    root.add_child(projectile)
    await process_frame
    projectile._apply_profile("inferno")
    projectile._apply_protocol_hit(inferno_target, 24.0)
    if inferno_target.burn_left <= 0.0 or inferno_target.burn_dps <= 0.0:
        push_error("Inferno projectile did not apply persistent burn")
        quit(1)
        return

    if not enemy.has_method("apply_shock"):
        push_error("Enemy shock status API is missing")
        quit(1)
        return
    enemy.velocity = Vector3(3.0, 0.0, 0.0)
    enemy.apply_shock(0.40)
    if enemy.shock_left <= 0.0 or enemy.velocity.length_squared() > 0.001:
        push_error("Shock status did not immediately immobilize enemy")
        quit(1)
        return

    var arc_target := ENEMY_SCRIPT.new()
    arc_target.configure("shambler", 1.0, target)
    arc_target.process_mode = Node.PROCESS_MODE_DISABLED
    arc_target.spawn_secondary_fx = false
    root.add_child(arc_target)
    await process_frame

    var arc_projectile := PROJECTILE_SCRIPT.new()
    arc_projectile.visual_profile = "arc"
    arc_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    arc_projectile.spawn_secondary_fx = false
    root.add_child(arc_projectile)
    await process_frame
    arc_projectile._apply_profile("arc")
    arc_projectile.chain_targets = 0
    arc_projectile._apply_protocol_hit(arc_target, 24.0)
    if arc_target.shock_left <= 0.0:
        push_error("Arc projectile did not apply shock control")
        quit(1)
        return

    var regenerator_a := ENEMY_SCRIPT.new()
    regenerator_a.configure("regenerator", 1.0, target)
    regenerator_a.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(regenerator_a)
    var regenerator_b := ENEMY_SCRIPT.new()
    regenerator_b.configure("regenerator", 1.0, target)
    regenerator_b.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(regenerator_b)
    await process_frame
    regenerator_a.health = regenerator_a.max_health * 0.5
    regenerator_b.health = regenerator_b.max_health * 0.5
    regenerator_a._begin_regeneration()
    regenerator_b._begin_regeneration()
    var pulse_a := regenerator_a.regeneration_visual as MeshInstance3D
    var pulse_b := regenerator_b.regeneration_visual as MeshInstance3D
    if pulse_a == null or pulse_b == null:
        push_error("Regenerator pulse visual is missing")
        quit(1)
        return
    if pulse_a.mesh != pulse_b.mesh:
        push_error("Regenerator pulses must reuse one mesh resource")
        quit(1)
        return
    if pulse_a.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Regenerator pulse must not cast dynamic shadows")
        quit(1)
        return
    if regenerator_a.regeneration_material == regenerator_b.regeneration_material:
        push_error("Regenerator pulse materials must stay instance-local for independent animation")
        quit(1)
        return

    print("Deadline Zero enemy status effects: OK")
    quit(0)
