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

    var before := enemy.health
    enemy.apply_burn(8.0, 1.0)
    enemy._process_status_effects(0.5)
    if enemy.health >= before or enemy.burn_left <= 0.0:
        push_error("Burn status did not deal damage over time")
        quit(1)
        return

    enemy._process_status_effects(0.6)
    if enemy.burn_left > 0.0:
        push_error("Burn status did not expire after its duration")
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

    print("Deadline Zero enemy status effects: OK")
    quit(0)
