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
        for key in ["projectile_scale", "trail_length", "impact_weight", "muzzle_color",
            "muzzle_size", "recoil_kick", "recoil_recover", "flash_duration"]:
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
    # Six gameplay protocols must feel physically distinct at the *actual*
    # 3D rifle: authored flash colors/width and kick/recovery, no per-shot
    # particle emitter or duplicated muzzle shader.
    var flashes := {}
    for id in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        var feedback := WEAPON_PROFILES.profile(id)
        var flash_color: Color = feedback["muzzle_color"]
        var flash_signature := flash_color.to_html() + "|%.3f" % float(feedback["muzzle_size"])
        if flashes.has(flash_signature):
            push_error("Weapon muzzle signature is duplicated across gameplay protocols")
            quit(1)
            return
        flashes[flash_signature] = true
        if float(feedback["recoil_kick"]) <= 0.03 or float(feedback["recoil_kick"]) > 0.19:
            push_error("Weapon recoil escaped plausible real-rifle motion budget for %s" % id)
            quit(1)
            return
        if float(feedback["flash_duration"]) >= 0.085 or float(feedback["flash_duration"]) < 0.03:
            push_error("Weapon flash exceeded safe rapid-fire visual lifetime for %s" % id)
            quit(1)
            return
    if float(WEAPON_PROFILES.profile("rail")["recoil_kick"]) <= float(WEAPON_PROFILES.profile("vanguard")["recoil_kick"]):
        push_error("Heavy railgun must kick farther than the starter weapon")
        quit(1)
        return
    if float(WEAPON_PROFILES.profile("scatter")["muzzle_size"]) <= float(WEAPON_PROFILES.profile("vanguard")["muzzle_size"]):
        push_error("Scatter barrel discharge must read wider than Vanguard")
        quit(1)
        return

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

    # Mobile top-down aim must match the actual 3D silhouette: a boss can
    # absorb a grazing shot while a small runner at the same offset cannot.
    var broad_boss := ENEMY_SCRIPT.new()
    broad_boss.configure("boss", 1.0, target)
    broad_boss.process_mode = Node.PROCESS_MODE_DISABLED
    parent.add_child(broad_boss)
    broad_boss.global_position = Vector3(0.69, 0.0, -0.55)

    var tight_runner := ENEMY_SCRIPT.new()
    tight_runner.configure("runner", 1.0, target)
    tight_runner.process_mode = Node.PROCESS_MODE_DISABLED
    parent.add_child(tight_runner)
    tight_runner.global_position = Vector3(-0.69, 0.0, -0.55)
    await process_frame

    if broad_boss.projectile_hit_radius() <= sweep_enemy.projectile_hit_radius():
        push_error("Boss shootable silhouette must be wider than an ordinary enemy")
        quit(1)
        return
    var silhouette_hits := swept_projectile._swept_hit_candidates(Vector3.ZERO, Vector3(0.0, 0.0, -0.80))
    if not silhouette_hits.has(broad_boss):
        push_error("Swept projectile missed the visible outer edge of a boss silhouette")
        quit(1)
        return
    if silhouette_hits.has(tight_runner):
        push_error("Swept projectile incorrectly hit a small runner outside its silhouette")
        quit(1)
        return
    # The boss is farther from the muzzle by center distance because of
    # its lateral offset, but its large real silhouette is struck *first*.
    # A center-distance sort incorrectly damages the smaller shambler first.
    if silhouette_hits.is_empty() or silhouette_hits[0] != broad_boss:
        push_error("Swept collision must resolve the first actual silhouette contact before a closer center")
        quit(1)
        return
    var boss_fraction := swept_projectile._swept_entry_fraction(Vector3.ZERO, Vector3(0.0, 0.0, -0.80), broad_boss)
    var shambler_fraction := swept_projectile._swept_entry_fraction(Vector3.ZERO, Vector3(0.0, 0.0, -0.80), sweep_enemy)
    if boss_fraction >= shambler_fraction or boss_fraction < 0.0 or shambler_fraction > 1.0:
        push_error("Swept physical entry fraction does not match footprint collision ordering")
        quit(1)
        return

    # Visual feedback must appear at the physical segment/footprint entry,
    # not deep inside the zombie or at its center when grazing the boss.
    var shot_from := Vector3.ZERO
    var shot_to := Vector3(0.0, 0.0, -0.80)
    var shambler_contact := swept_projectile._swept_contact_point(shot_from, shot_to, sweep_enemy)
    if absf(shambler_contact.z + 0.21) > 0.015 or absf(shambler_contact.y - 0.55) > 0.001:
        push_error("Swept shot FX was not anchored to the first shambler silhouette entry")
        quit(1)
        return

    var boss_contact := swept_projectile._swept_contact_point(shot_from, shot_to, broad_boss)
    var boss_offset := Vector2(boss_contact.x - broad_boss.global_position.x, boss_contact.z - broad_boss.global_position.z)
    if absf(boss_offset.length() - broad_boss.projectile_hit_radius()) > 0.015:
        push_error("Grazing boss FX did not touch the actual large silhouette boundary")
        quit(1)
        return
    if boss_contact.z > shot_from.z + 0.001 or boss_contact.z < shot_to.z - 0.001:
        push_error("Grazing boss FX escaped its swept projectile frame")
        quit(1)
        return
    var inside_contact := swept_projectile._swept_contact_point(
        broad_boss.global_position, broad_boss.global_position + Vector3.FORWARD * 0.3, broad_boss
    )
    if inside_contact.distance_to(broad_boss.global_position + Vector3(0.0, 0.55, 0.0)) > 0.001:
        push_error("Shots originating inside a large enemy must use the current contact point")
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
    spatial_projectile._swept_hit_candidates(Vector3.ZERO, Vector3(0.0, 0.0, -0.80))
    var minimum_broad_radius := maxf(spatial_projectile.radius, ENEMY_SCRIPT.MAX_PROJECTILE_HIT_RADIUS) + 0.4
    if spatial_probe.query_calls != 2 or spatial_probe.last_radius + 0.001 < minimum_broad_radius:
        push_error("Spatial broad phase would cull boss silhouette hits before narrow-phase checks")
        quit(1)
        return
    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
