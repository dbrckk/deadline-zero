extends SceneTree

class SpatialProbe:
    extends Node3D
    var query_calls := 0
    var last_radius := 0.0

    func query_enemies_near(_position: Vector3, radius: float) -> Array:
        query_calls += 1
        last_radius = radius
        return []

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const WEAPON_PROFILES := preload("res://scripts/WeaponProfiles.gd")

func _initialize() -> void:
    if PROJECTILE_SCRIPT.protocol_pierce_budget("rail") != 2:
        push_error("Rail pierce budget is incorrect")
        quit(1)
        return
    if not is_equal_approx(PROJECTILE_SCRIPT.protocol_splash_radius("inferno"), 1.85):
        push_error("Inferno splash radius is incorrect")
        quit(1)
        return
    if PROJECTILE_SCRIPT.protocol_chain_targets("arc") != 2:
        push_error("Arc chain target count is incorrect")
        quit(1)
        return
    var slow := PROJECTILE_SCRIPT.protocol_slow("cryo")
    if slow.x >= 1.0 or slow.y <= 0.0:
        push_error("Cryo slow rule is incorrect")
        quit(1)
        return

    var signatures := {}
    for id in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        var profile := WEAPON_PROFILES.profile(id)
        for key in ["projectile_scale", "trail_length", "impact_weight"]:
            if not profile.has(key):
                push_error("Weapon profile %s is missing feedback key %s" % [id, key])
                quit(1)
                return
        var signature := "%s|%s|%s" % [profile["projectile_scale"], profile["trail_length"], profile["impact_weight"]]
        if signatures.has(signature):
            push_error("Weapon feedback signature is not distinct: %s and %s" % [signatures[signature], id])
            quit(1)
            return
        signatures[signature] = id
    if float(WEAPON_PROFILES.profile("rail")["impact_weight"]) <= float(WEAPON_PROFILES.profile("vanguard")["impact_weight"]):
        push_error("Rail impact should read heavier than Vanguard")
        quit(1)
        return
    if float(WEAPON_PROFILES.profile("scatter")["trail_length"]) >= float(WEAPON_PROFILES.profile("rail")["trail_length"]):
        push_error("Scatter should read shorter-ranged than Rail")
        quit(1)
        return

    var target := Node3D.new()
    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.apply_slow(slow.x, slow.y)
    if enemy.slow_multiplier >= 1.0 or enemy.slow_left <= 0.0:
        push_error("Enemy slow state was not applied")
        quit(1)
        return

    var rail := PROJECTILE_SCRIPT.new()
    rail._apply_profile("rail")
    if rail.pierce_remaining != 2:
        push_error("Rail runtime profile did not consume deterministic rule")
        quit(1)
        return
    var inferno := PROJECTILE_SCRIPT.new()
    inferno._apply_profile("inferno")
    if not is_equal_approx(inferno.splash_radius, 1.85):
        push_error("Inferno runtime profile did not consume deterministic rule")
        quit(1)
        return
    var arc := PROJECTILE_SCRIPT.new()
    arc._apply_profile("arc")
    if arc.chain_targets != 2:
        push_error("Arc runtime profile did not consume deterministic rule")
        quit(1)
        return

    var parent := Node3D.new()
    parent.position = Vector3(9.0, 0.0, -4.0)
    get_root().add_child(parent)
    await process_frame
    var sweep_enemy := ENEMY_SCRIPT.new()
    sweep_enemy.configure("shambler", 1.0, target)
    sweep_enemy.process_mode = Node.PROCESS_MODE_DISABLED
    parent.add_child(sweep_enemy)
    sweep_enemy.global_position = Vector3(0.0, 0.0, -0.55)
    await process_frame

    var swept_projectile := PROJECTILE_SCRIPT.new()
    swept_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    swept_projectile.setup(Vector3.ZERO, Vector3.FORWARD, 40.0, 10.0, Color.WHITE, "rail")
    parent.add_child(swept_projectile)
    await process_frame
    var swept_hits := swept_projectile._swept_hit_candidates(Vector3.ZERO, Vector3(0.0, 0.0, -0.80))
    if not swept_hits.has(sweep_enemy):
        push_error("Fast projectile swept collision missed an enemy crossed between physics samples")
        quit(1)
        return

    var spawned := PROJECTILE_SCRIPT.new()
    spawned.process_mode = Node.PROCESS_MODE_DISABLED
    var spawn_origin := Vector3(2.0, 0.7, 3.0)
    spawned.setup(spawn_origin, Vector3.FORWARD, 10.0, 10.0, Color.WHITE, "vanguard")
    parent.add_child(spawned)
    await process_frame
    if spawned.global_position.distance_to(spawn_origin) > 0.001:
        push_error("Projectile setup did not preserve global spawn origin under a transformed parent")
        quit(1)
        return

    parent.queue_free()
    enemy.free()
    target.free()
    rail.free()
    inferno.free()
    arc.free()
    await process_frame

    var spatial_probe := SpatialProbe.new()
    get_root().add_child(spatial_probe)
    current_scene = spatial_probe
    var spatial_projectile := PROJECTILE_SCRIPT.new()
    spatial_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    spatial_probe.add_child(spatial_projectile)
    await process_frame
    spatial_projectile._enemies_near(Vector3.ZERO, 3.8)
    if spatial_probe.query_calls != 1 or not is_equal_approx(spatial_probe.last_radius, 3.8):
        push_error("Weapon protocol neighborhood queries did not route through scene spatial hash")
        quit(1)
        return
    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
