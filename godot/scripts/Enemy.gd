class_name DZEnemy
extends CharacterBody3D

signal died(xp_value: int, at: Vector3)
signal impact(at: Vector3, critical: bool, killed: bool, boss: bool)
signal health_changed(current: float, maximum: float)

var target: Node3D
var kind := "shambler"
var max_health := 60.0
var health := 60.0
var move_speed := 2.1
var contact_damage := 8.0
var xp_value := 2
var dead := false
var attack_cooldown := 0.0
var authored_visual: Node3D
var authored_anim: AnimationPlayer
var current_anim := ""
var attack_windup := 0.0
var attack_target_position := Vector3.ZERO
var elite_burst_clock := 2.4
var boss_slam_clock := 3.6
var boss_phase := 1
var telegraph_visual: Node3D
var telegraph_material: StandardMaterial3D
var slow_multiplier := 1.0
var slow_left := 0.0
var burn_dps := 0.0
var burn_left := 0.0
var burn_tick_accumulator := 0.0
var shock_left := 0.0
var special_clock := 1.8
var regeneration_clock := 1.0
var regeneration_windup := 0.0
var regeneration_visual: Node3D
var regeneration_material: StandardMaterial3D
var pending_special := ""
var spawn_secondary_fx := true
var combat_enabled := true
var charge_active := false
var charge_direction := Vector3.ZERO
var charge_left := 0.0
var charge_hit := false
var hit_flash_visual: MeshInstance3D
var hit_flash_material: StandardMaterial3D
var hit_reaction_tween: Tween
static var _shared_contact_shadow_material: StandardMaterial3D
static var _contact_shadow_mesh_cache := {}
static var _signature_material_cache := {}
static var _signature_mesh_cache := {}
static var _shared_brute_armor_material: StandardMaterial3D
static var _telegraph_ring_mesh_cache := {}
static var _telegraph_tick_mesh_cache := {}

const MAX_DAMAGE_NUMBERS := 18

func configure(enemy_kind: String, difficulty: float, chase_target: Node3D) -> void:
    kind = enemy_kind
    target = chase_target
    match kind:
        "boss":
            max_health = 1450.0 * difficulty
            move_speed = 1.38
            contact_damage = 24.0
            xp_value = 35
        "runner":
            max_health = 42.0 * difficulty
            move_speed = 3.7
            contact_damage = 6.0
            xp_value = 2
        "brute":
            max_health = 150.0 * difficulty
            move_speed = 1.45
            contact_damage = 15.0
            xp_value = 5
        "elite":
            max_health = 300.0 * difficulty
            move_speed = 2.0
            contact_damage = 18.0
            xp_value = 8
        "charger":
            max_health = 105.0 * difficulty
            move_speed = 2.35
            contact_damage = 13.0
            xp_value = 4
        "harrier":
            max_health = 74.0 * difficulty
            move_speed = 2.75
            contact_damage = 9.0
            xp_value = 4
        "regenerator":
            max_health = 128.0 * difficulty
            move_speed = 1.72
            contact_damage = 10.0
            xp_value = 5
        _:
            max_health = 68.0 * difficulty
            move_speed = 2.15
            contact_damage = 8.0
            xp_value = 2
    health = max_health
    health_changed.emit(health, max_health)

func _ready() -> void:
    add_to_group("enemies")
    _build_visual()
    _build_contact_shadow()
    _build_hit_flash()

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        velocity = Vector3.ZERO
        return
    _process_status_effects(delta)
    shock_left = maxf(0.0, shock_left - maxf(delta, 0.0))
    if dead or target == null or not is_instance_valid(target):
        return
    if shock_left > 0.0:
        velocity = Vector3.ZERO
        return
    if kind == "boss":
        _update_boss_phase()
    attack_cooldown = max(0.0, attack_cooldown - delta)
    elite_burst_clock = max(0.0, elite_burst_clock - delta)
    boss_slam_clock = max(0.0, boss_slam_clock - delta)
    special_clock = max(0.0, special_clock - delta)
    regeneration_clock = max(0.0, regeneration_clock - delta)
    slow_left = max(0.0, slow_left - delta)
    if slow_left <= 0.0:
        slow_multiplier = 1.0
    if charge_active:
        _process_charge(delta)
        return
    if regeneration_windup > 0.0:
        _process_regeneration(delta)
        return
    var delta_pos := target.global_position - global_position
    delta_pos.y = 0.0
    var distance := delta_pos.length()

    if kind == "regenerator" and regeneration_clock <= 0.0 and health < max_health:
        _begin_regeneration()
        regeneration_clock = 1.0
        return

    if attack_windup > 0.0:
        velocity = Vector3.ZERO
        attack_windup = max(0.0, attack_windup - delta)
        if attack_windup <= 0.0:
            _resolve_telegraphed_attack()
        return

    if kind == "charger" and special_clock <= 0.0 and distance > 2.2 and distance < 7.2:
        pending_special = "charge"
        _begin_telegraphed_attack(0.52, target.global_position)
        special_clock = 3.4
        return

    if kind == "harrier" and special_clock <= 0.0 and distance >= 3.5 and distance <= 8.5:
        pending_special = "harrier_shot"
        _begin_telegraphed_attack(0.42, target.global_position)
        special_clock = 2.6
        return

    if kind == "elite" and elite_burst_clock <= 0.0 and distance < 5.2:
        _begin_telegraphed_attack(0.46, target.global_position)
        elite_burst_clock = 3.0
        return
    if kind == "boss" and boss_slam_clock <= 0.0 and distance < 4.6:
        _begin_telegraphed_attack(_boss_slam_windup(), target.global_position)
        boss_slam_clock = _boss_slam_cooldown()
        return

    if distance > 0.05:
        var movement_direction: Vector3 = delta_pos.normalized()
        var movement_speed_scale := 1.0
        if kind == "harrier":
            if distance < 4.4:
                movement_direction = -movement_direction
            elif distance <= 6.6:
                movement_direction = Vector3(-movement_direction.z, 0.0, movement_direction.x)

        # Local separation keeps the swarm readable and prevents every body from collapsing
        # onto the same target point. Main.gd serves this from its spatial hash in production.
        var separation_radius := 2.05 if kind in ["boss", "brute", "charger"] else 1.62
        var separation := separation_vector(_nearby_enemies_for_separation(separation_radius), separation_radius)
        if separation.length_squared() > 0.001:
            var separation_weight := 0.88 if kind == "boss" else (1.62 if kind == "harrier" else 1.56)
            movement_direction = (movement_direction + separation * separation_weight).normalized()

        # Maintain a visible melee envelope around the survivor instead of letting bodies
        # occupy the same screen-space footprint. Enemies can still attack from this envelope.
        if kind != "harrier":
            var standoff := _melee_standoff_distance()
            var outward := global_position - target.global_position
            outward.y = 0.0
            if outward.length_squared() > 0.001:
                var radial := outward.normalized()
                var tangent := Vector3(-radial.z, 0.0, radial.x)
                if int(get_instance_id()) % 2 == 0:
                    tangent = -tangent

                if distance < standoff:
                    var penetration := clampf((standoff - distance) / maxf(standoff, 0.01), 0.0, 1.0)
                    movement_direction = (
                        radial * (1.42 + penetration * 1.05)
                        + tangent * 0.58
                        + separation * 0.72
                    ).normalized()
                    movement_speed_scale = lerpf(0.44, 0.90, penetration)
                elif distance < standoff + 0.62:
                    var settle := 1.0 - clampf((distance - standoff) / 0.62, 0.0, 1.0)
                    movement_direction = (movement_direction + tangent * settle * 0.24 + separation * settle * 0.32).normalized()
                    movement_speed_scale = lerpf(0.70, 1.0, 1.0 - settle)

        velocity = movement_direction * move_speed * slow_multiplier * movement_speed_scale
        move_and_slide()
        if velocity.length_squared() > 0.01:
            look_at(global_position + velocity, Vector3.UP)
    _update_authored_animation(distance)
    if distance < _contact_attack_range() and attack_cooldown <= 0.0 and target.has_method("take_damage"):
        target.take_damage(contact_damage)
        attack_cooldown = 0.72

func _melee_standoff_distance() -> float:
    match kind:
        "boss":
            return 1.64
        "brute":
            return 1.42
        "charger":
            return 1.28
        "elite":
            return 1.22
        "regenerator":
            return 1.16
        _:
            return 1.08

func _contact_attack_range() -> float:
    return _melee_standoff_distance() + (0.24 if kind in ["boss", "brute"] else 0.20)

func _nearby_enemies_for_separation(radius: float) -> Array:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene != null and scene.has_method("query_enemies_near"):
        return scene.query_enemies_near(global_position, radius)
    return get_tree().get_nodes_in_group("enemies") if get_tree() != null else []

func separation_vector(neighbors: Array, radius: float) -> Vector3:
    if radius <= 0.0:
        return Vector3.ZERO
    var separation := Vector3.ZERO
    var contributions := 0
    for node in neighbors:
        var other := node as DZEnemy
        if other == null or other == self or other.dead:
            continue
        var away := global_position - other.global_position
        away.y = 0.0
        var distance_sq := away.length_squared()
        if distance_sq >= radius * radius:
            continue

        # Resolve near-perfect overlap deterministically instead of leaving a permanent stack.
        if distance_sq < 0.0004:
            var phase := float(int(get_instance_id() + other.get_instance_id()) % 16) / 16.0 * TAU
            away = Vector3(cos(phase), 0.0, sin(phase))
            distance_sq = 0.0004

        var distance := sqrt(distance_sq)
        var pressure := clampf((radius - distance) / radius, 0.0, 1.0)
        separation += away / distance * pressure * pressure
        contributions += 1

    if contributions == 0 or separation.length_squared() < 0.0001:
        return Vector3.ZERO
    return separation.normalized()

func _update_boss_phase() -> void:
    if kind != "boss" or max_health <= 0.0:
        return
    var ratio := clampf(health / max_health, 0.0, 1.0)
    boss_phase = 3 if ratio <= 0.30 else (2 if ratio <= 0.65 else 1)
    match boss_phase:
        2:
            move_speed = 1.55
            contact_damage = 27.0
        3:
            move_speed = 1.76
            contact_damage = 31.0
        _:
            move_speed = 1.38
            contact_damage = 24.0

func _boss_slam_windup() -> float:
    match boss_phase:
        2: return 0.56
        3: return 0.44
        _: return 0.68

func _boss_slam_cooldown() -> float:
    match boss_phase:
        2: return 3.4
        3: return 2.8
        _: return 4.1

func _process_charge(delta: float) -> void:
    charge_left = max(0.0, charge_left - delta)
    velocity = charge_direction * 9.4
    move_and_slide()
    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity, Vector3.UP)
    if not charge_hit and target != null and is_instance_valid(target):
        var target_offset := target.global_position - global_position
        target_offset.y = 0.0
        if target_offset.length() <= 1.0 and target.has_method("take_damage"):
            target.take_damage(contact_damage * 1.30)
            charge_hit = true
            _spawn_attack_impact(global_position + Vector3(0.0, 0.05, 0.0), 1.05)
    if charge_left <= 0.0:
        charge_active = false
        velocity = Vector3.ZERO
        attack_cooldown = 0.80

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if enabled:
        return
    velocity = Vector3.ZERO
    attack_windup = 0.0
    pending_special = ""
    charge_active = false
    charge_left = 0.0
    charge_hit = false
    regeneration_windup = 0.0
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    regeneration_visual = null
    regeneration_material = null
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = null

func _begin_telegraphed_attack(duration: float, target_position: Vector3) -> void:
    attack_windup = duration
    attack_target_position = target_position
    attack_target_position.y = global_position.y
    _show_telegraph(1.75 if kind == "boss" else 1.05, duration)
    if authored_anim != null and authored_anim.has_animation("Idle_Attack"):
        _play_authored("Idle_Attack")

func _resolve_telegraphed_attack() -> void:
    if target == null or not is_instance_valid(target):
        return
    if pending_special == "charge":
        var direction := attack_target_position - global_position
        direction.y = 0.0
        if direction.length_squared() < 0.001:
            direction = global_transform.basis.z * -1.0
        charge_direction = direction.normalized()
        charge_left = clampf(direction.length() / 9.4, 0.28, 0.72)
        charge_active = true
        charge_hit = false
        pending_special = ""
        return
    if pending_special == "harrier_shot":
        var shot := DZEnemyProjectile.new()
        get_tree().current_scene.add_child(shot)
        shot.global_position = global_position + Vector3(0.0, 0.34, 0.0)
        shot.configure(attack_target_position, target, contact_damage * 0.88)
        pending_special = ""
        attack_cooldown = 0.95
        return
    var radius: float = 1.95 if kind == "boss" else 1.18
    var damage: float = contact_damage * (1.35 if kind == "boss" else 0.82)
    var impact_point: Vector3 = global_position.lerp(attack_target_position, 0.58)
    impact_point.y = 0.05
    if target.global_position.distance_to(impact_point) <= radius and target.has_method("take_damage"):
        target.take_damage(damage)
    _spawn_attack_impact(impact_point, radius)
    attack_cooldown = 0.88 if kind == "boss" else 0.64

func _show_telegraph(radius: float, duration: float) -> void:
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = MeshInstance3D.new()
    telegraph_visual.name = "AttackTelegraphRing"
    telegraph_visual.mesh = _telegraph_ring_mesh(radius, kind == "boss")
    telegraph_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    get_tree().current_scene.add_child(telegraph_visual)
    telegraph_visual.global_position = global_position.lerp(attack_target_position, 0.58) + Vector3(0.0, 0.035, 0.0)
    telegraph_material = StandardMaterial3D.new()
    telegraph_material.albedo_color = Color(1.0, 0.22, 0.025, 0.42)
    telegraph_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    telegraph_material.emission_enabled = true
    telegraph_material.emission = Color(1.0, 0.075, 0.006)
    telegraph_material.emission_energy_multiplier = 1.7
    telegraph_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    telegraph_visual.material_override = telegraph_material

    # Four short ticks make the danger radius readable under bodies/projectiles without filling
    # the entire floor area with an opaque disk.
    for tick_index in range(4):
        var angle := TAU * float(tick_index) / 4.0
        var tick := MeshInstance3D.new()
        tick.name = "TelegraphTick_%d" % tick_index
        tick.mesh = _telegraph_tick_mesh(radius)
        tick.position = Vector3(cos(angle) * radius * 0.72, 0.0, sin(angle) * radius * 0.72)
        tick.rotation.y = -angle
        tick.material_override = telegraph_material
        tick.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        telegraph_visual.add_child(tick)

    var tween := telegraph_visual.create_tween()
    tween.set_parallel(true)
    telegraph_visual.scale = Vector3(0.42, 1.0, 0.42)
    tween.tween_property(telegraph_visual, "scale", Vector3.ONE, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(telegraph_material, "emission_energy_multiplier", 5.8 if kind == "boss" else 4.6, duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.tween_property(telegraph_material, "albedo_color", Color(1.0, 0.07, 0.008, 0.92 if kind == "boss" else 0.78), duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.chain().tween_callback(telegraph_visual.queue_free)

static func _telegraph_ring_mesh(radius: float, boss: bool) -> TorusMesh:
    var key := "%.3f|%s" % [radius, "boss" if boss else "normal"]
    if _telegraph_ring_mesh_cache.has(key):
        return _telegraph_ring_mesh_cache[key] as TorusMesh
    var mesh := TorusMesh.new()
    mesh.inner_radius = radius * (0.82 if boss else 0.86)
    mesh.outer_radius = radius
    mesh.rings = 40 if boss else 32
    mesh.ring_segments = 8
    _telegraph_ring_mesh_cache[key] = mesh
    return mesh

static func _telegraph_tick_mesh(radius: float) -> BoxMesh:
    var key := "%.3f" % radius
    if _telegraph_tick_mesh_cache.has(key):
        return _telegraph_tick_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = Vector3(radius * 0.24, 0.012, maxf(0.035, radius * 0.045))
    _telegraph_tick_mesh_cache[key] = mesh
    return mesh

func _spawn_attack_impact(at: Vector3, radius: float) -> void:
    if not spawn_secondary_fx:
        return
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.22, 0.05) if kind == "boss" else Color(0.72, 0.28, 1.0)
    fx.scale_boost = radius * 1.35
    get_tree().current_scene.add_child(fx)
    fx.global_position = at + Vector3(0.0, 0.10, 0.0)

func _begin_regeneration() -> void:
    if dead or not combat_enabled or health <= 0.0 or health >= max_health:
        return
    regeneration_windup = 0.42
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    var pulse := MeshInstance3D.new()
    pulse.name = "RegenerationPulse"
    var mesh := CylinderMesh.new()
    mesh.top_radius = 0.88
    mesh.bottom_radius = 0.88
    mesh.height = 0.022
    pulse.mesh = mesh
    pulse.position = Vector3(0.0, 0.035, 0.0)
    regeneration_material = StandardMaterial3D.new()
    regeneration_material.albedo_color = Color(0.12, 1.0, 0.42, 0.18)
    regeneration_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    regeneration_material.emission_enabled = true
    regeneration_material.emission = Color(0.08, 1.0, 0.34)
    regeneration_material.emission_energy_multiplier = 1.8
    pulse.material_override = regeneration_material
    regeneration_visual = pulse
    add_child(pulse)
    pulse.scale = Vector3(0.48, 1.0, 0.48)
    var tween := pulse.create_tween()
    tween.set_parallel(true)
    tween.tween_property(pulse, "scale", Vector3(1.18, 1.0, 1.18), regeneration_windup).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(regeneration_material, "emission_energy_multiplier", 4.0, regeneration_windup).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)

func _process_regeneration(delta: float) -> void:
    if regeneration_windup <= 0.0:
        return
    regeneration_windup = max(0.0, regeneration_windup - delta)
    velocity = Vector3.ZERO
    if regeneration_windup > 0.0:
        return
    _regenerate()
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    regeneration_visual = null
    regeneration_material = null

func _regenerate() -> void:
    if dead or health <= 0.0 or health >= max_health:
        return
    var healed: float = minf(max_health * 0.035, max_health - health)
    health += healed
    health_changed.emit(health, max_health)

func apply_slow(multiplier: float, duration: float) -> void:
    slow_multiplier = min(slow_multiplier, clampf(multiplier, 0.30, 1.0))
    slow_left = max(slow_left, max(0.0, duration))

func apply_burn(dps: float, duration: float) -> void:
    if dead or dps <= 0.0 or duration <= 0.0:
        return
    burn_dps = maxf(burn_dps, dps)
    burn_left = maxf(burn_left, duration)

func apply_shock(duration: float) -> void:
    if dead or duration <= 0.0:
        return
    var resistance := 0.45 if kind == "boss" else (0.65 if kind == "elite" else 1.0)
    shock_left = maxf(shock_left, duration * resistance)
    velocity = Vector3.ZERO

func _process_status_effects(delta: float) -> void:
    if dead or burn_left <= 0.0 or burn_dps <= 0.0:
        return
    var active_delta := minf(maxf(delta, 0.0), burn_left)
    burn_left = maxf(0.0, burn_left - maxf(delta, 0.0))
    burn_tick_accumulator += active_delta

    const BURN_TICK := 0.25
    while burn_tick_accumulator >= BURN_TICK and not dead:
        burn_tick_accumulator -= BURN_TICK
        take_damage(burn_dps * BURN_TICK, false)

    if burn_left <= 0.0:
        if burn_tick_accumulator > 0.0 and not dead:
            take_damage(burn_dps * burn_tick_accumulator, false)
        burn_tick_accumulator = 0.0
        burn_dps = 0.0

func take_damage(amount: float, critical := false) -> void:
    if dead:
        return
    health -= amount
    health_changed.emit(max(0.0, health), max_health)
    var killed := health <= 0.0
    impact.emit(global_position + Vector3(0.0, 0.72, 0.0), critical, killed, kind == "boss")
    _spawn_damage_number(amount, critical, killed)
    _play_hit_reaction(critical, killed)
    if killed:
        dead = true
        velocity = Vector3.ZERO
        died.emit(xp_value, global_position)
        if authored_anim != null and authored_anim.has_animation("Death"):
            authored_anim.play("Death", 0.06)
            var timer := get_tree().create_timer(0.62)
            timer.timeout.connect(queue_free)
        else:
            queue_free()

func hit_reaction_profile() -> Dictionary:
    if kind == "boss":
        return {"id": "boss_hit", "punch": 1.035, "flash": 5.0, "duration": 0.13, "recoil": 0.025}
    if kind == "elite":
        return {"id": "elite_hit", "punch": 1.075, "flash": 6.2, "duration": 0.12, "recoil": 0.055}
    return {"id": "normal_hit", "punch": 1.10, "flash": 7.0, "duration": 0.10, "recoil": 0.085}

func _play_hit_reaction(critical: bool, killed: bool) -> void:
    var visual := get_node_or_null("Visual") as Node3D
    if visual == null:
        return
    var profile := hit_reaction_profile()
    if hit_reaction_tween != null and hit_reaction_tween.is_valid():
        hit_reaction_tween.kill()
    var base_scale := visual.scale
    var punch := float(profile["punch"]) * (1.035 if critical else 1.0)
    var duration := float(profile["duration"])
    var recoil := float(profile["recoil"])
    var base_position := visual.position
    var recoil_direction := Vector3.ZERO
    if target != null and is_instance_valid(target):
        recoil_direction = global_position - target.global_position
        recoil_direction.y = 0.0
        if recoil_direction.length_squared() > 0.001:
            recoil_direction = recoil_direction.normalized() * recoil
    if hit_flash_visual != null:
        hit_flash_visual.visible = true
        hit_flash_material.emission_energy_multiplier = float(profile["flash"]) * (1.18 if critical else 1.0)
        hit_flash_material.albedo_color.a = 0.30 if critical else 0.20
    hit_reaction_tween = create_tween()
    hit_reaction_tween.set_parallel(true)
    hit_reaction_tween.tween_property(visual, "scale", base_scale * punch, duration * 0.34).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.tween_property(visual, "position", base_position + recoil_direction, duration * 0.34).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if hit_flash_visual != null:
        hit_reaction_tween.tween_property(hit_flash_material, "emission_energy_multiplier", 0.0, duration)
        hit_reaction_tween.tween_property(hit_flash_material, "albedo_color:a", 0.0, duration)
    hit_reaction_tween.set_parallel(false)
    hit_reaction_tween.tween_property(visual, "scale", base_scale * (1.04 if killed else 1.0), duration * 0.66).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.parallel().tween_property(visual, "position", base_position, duration * 0.66).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.tween_callback(func() -> void:
        if hit_flash_visual != null:
            hit_flash_visual.visible = false
    )

func _build_contact_shadow() -> void:
    var shadow := MeshInstance3D.new()
    shadow.name = "EnemyContactShadow"
    var radius := 0.42
    match kind:
        "runner":
            radius = 0.36
        "charger":
            radius = 0.50
        "harrier":
            radius = 0.40
        "regenerator":
            radius = 0.47
        "brute":
            radius = 0.58
        "elite":
            radius = 0.54
        "boss":
            radius = 0.78
    shadow.mesh = _contact_shadow_mesh(radius)
    shadow.position.y = 0.010
    shadow.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    shadow.material_override = _enemy_contact_shadow_material()
    add_child(shadow)

static func _contact_shadow_mesh(radius: float) -> CylinderMesh:
    var key := "%.3f" % radius
    if _contact_shadow_mesh_cache.has(key):
        return _contact_shadow_mesh_cache[key] as CylinderMesh
    var mesh := CylinderMesh.new()
    mesh.top_radius = radius
    mesh.bottom_radius = radius * 1.04
    mesh.height = 0.008
    mesh.radial_segments = 16
    _contact_shadow_mesh_cache[key] = mesh
    return mesh

static func _enemy_contact_shadow_material() -> StandardMaterial3D:
    if _shared_contact_shadow_material != null:
        return _shared_contact_shadow_material
    _shared_contact_shadow_material = StandardMaterial3D.new()
    _shared_contact_shadow_material.albedo_color = Color(0.005, 0.008, 0.010, 0.34)
    _shared_contact_shadow_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _shared_contact_shadow_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _shared_contact_shadow_material.roughness = 1.0
    return _shared_contact_shadow_material

func _build_hit_flash() -> void:
    hit_flash_visual = MeshInstance3D.new()
    hit_flash_visual.name = "HitFlash"
    var mesh := CylinderMesh.new()
    var scale_factor := 1.0
    if kind == "boss": scale_factor = 1.62
    elif kind in ["elite", "brute", "charger"]: scale_factor = 1.18
    mesh.top_radius = 0.46 * scale_factor
    mesh.bottom_radius = 0.52 * scale_factor
    mesh.height = 1.28 * scale_factor
    hit_flash_visual.mesh = mesh
    hit_flash_visual.position.y = 0.66 * scale_factor
    hit_flash_material = StandardMaterial3D.new()
    hit_flash_material.albedo_color = Color(1.0, 0.86, 0.58, 0.0)
    hit_flash_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    hit_flash_material.emission_enabled = true
    hit_flash_material.emission = Color(1.0, 0.58, 0.16)
    hit_flash_material.emission_energy_multiplier = 0.0
    hit_flash_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    hit_flash_visual.material_override = hit_flash_material
    hit_flash_visual.visible = false
    add_child(hit_flash_visual)

func _spawn_damage_number(amount: float, critical: bool, killed: bool) -> void:
    if get_tree() == null or get_tree().current_scene == null:
        return
    var active_numbers := get_tree().get_nodes_in_group("damage_numbers").size()
    if active_numbers >= MAX_DAMAGE_NUMBERS and not critical and not killed:
        return
    var number := Label3D.new()
    number.name = "DamageNumber_%d" % Time.get_ticks_usec()
    number.add_to_group("damage_numbers")
    number.text = "%d" % int(round(amount))
    number.font_size = 34 if critical else (30 if killed else 26)
    number.outline_size = 8 if critical or killed else 6
    number.modulate = Color(1.0, 0.72, 0.12) if critical else (Color(1.0, 0.42, 0.16) if killed else Color(0.92, 0.97, 1.0))
    number.outline_modulate = Color(0.02, 0.03, 0.05, 0.96)
    number.billboard = BaseMaterial3D.BILLBOARD_ENABLED
    number.no_depth_test = true
    number.pixel_size = 0.0038 if critical else 0.0032
    get_tree().current_scene.add_child(number)
    number.global_position = global_position + Vector3(0.0, 1.28, 0.0)

    var rise := 0.82 if critical else 0.62
    var tween := number.create_tween()
    tween.set_parallel(true)
    tween.tween_property(number, "global_position", number.global_position + Vector3(0.0, rise, 0.0), 0.58).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(number, "modulate:a", 0.0, 0.58).set_delay(0.18)
    tween.chain().tween_callback(number.queue_free)

func _build_visual() -> void:
    authored_visual = DZAssetLibrary.enemy(kind)
    if authored_visual != null:
        authored_visual.name = "Visual"
        var scale_factor := 1.0
        match kind:
            "runner": scale_factor = 0.86
            "brute": scale_factor = 1.22
            "elite": scale_factor = 1.15
            "charger": scale_factor = 1.18
            "harrier": scale_factor = 0.94
            "regenerator": scale_factor = 1.10
            "boss": scale_factor = 1.62
        authored_visual.scale = Vector3.ONE * scale_factor
        add_child(authored_visual)
        authored_anim = DZAssetLibrary.animation_player(authored_visual)
        _play_authored("Run_Arms" if kind == "runner" else "Walk")
        _add_archetype_signature()
        return

    var root := Node3D.new()
    root.name = "Visual"
    add_child(root)

    var body := MeshInstance3D.new()
    var capsule := CapsuleMesh.new()
    capsule.radius = 0.34
    capsule.height = 1.25
    body.mesh = capsule
    body.position.y = 0.66

    var mat := StandardMaterial3D.new()
    match kind:
        "runner":
            mat.albedo_color = Color(0.55, 0.85, 0.24)
            root.scale = Vector3(0.88, 0.92, 0.88)
        "brute":
            mat.albedo_color = Color(0.58, 0.16, 0.12)
            root.scale = Vector3(1.28, 1.22, 1.28)
        "elite":
            mat.albedo_color = Color(0.58, 0.20, 0.78)
            mat.metallic = 0.15
            root.scale = Vector3(1.18, 1.24, 1.18)
        _:
            mat.albedo_color = Color(0.26, 0.58, 0.32)
    mat.roughness = 0.78
    body.material_override = mat
    root.add_child(body)

    var head := MeshInstance3D.new()
    var head_mesh := SphereMesh.new()
    head_mesh.radius = 0.27
    head_mesh.height = 0.54
    head.mesh = head_mesh
    head.position = Vector3(0.0, 1.48, 0.0)
    head.material_override = mat
    root.add_child(head)

    var eye := MeshInstance3D.new()
    var eye_mesh := BoxMesh.new()
    eye_mesh.size = Vector3(0.34, 0.06, 0.05)
    eye.mesh = eye_mesh
    eye.position = Vector3(0.0, 1.5, -0.25)
    var eye_mat := StandardMaterial3D.new()
    eye_mat.albedo_color = Color(1.0, 0.16, 0.08)
    eye_mat.emission_enabled = true
    eye_mat.emission = Color(1.0, 0.06, 0.02)
    eye_mat.emission_energy_multiplier = 3.0
    eye.material_override = eye_mat
    root.add_child(eye)

func _add_archetype_signature() -> void:
    if authored_visual == null:
        return

    var accent := Color(0.82, 0.18, 0.10)
    match kind:
        "runner":
            accent = Color(0.58, 1.0, 0.18)
            _add_runner_blades(accent)
        "brute":
            accent = Color(1.0, 0.28, 0.10)
            _add_brute_shoulders(accent)
        "elite":
            accent = Color(0.72, 0.30, 1.0)
            _add_elite_crown(accent)
        "charger":
            accent = Color(1.0, 0.32, 0.08)
            _add_brute_shoulders(accent)
        "harrier":
            accent = Color(0.12, 0.82, 1.0)
            _add_runner_blades(accent)
        "regenerator":
            accent = Color(0.18, 1.0, 0.48)
            _add_elite_crown(accent)
        "boss":
            accent = Color(1.0, 0.62, 0.12)
            _add_boss_frame(accent)
        _:
            _add_eye_beacon(accent, Vector3(0.0, 1.62, -0.28), 0.055)

static func _signature_box_mesh(key: String, size: Vector3) -> BoxMesh:
    if _signature_mesh_cache.has(key):
        return _signature_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = size
    _signature_mesh_cache[key] = mesh
    return mesh

static func _signature_sphere_mesh(key: String, radius: float, height: float) -> SphereMesh:
    if _signature_mesh_cache.has(key):
        return _signature_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = radius
    mesh.height = height
    mesh.radial_segments = 10
    mesh.rings = 5
    _signature_mesh_cache[key] = mesh
    return mesh

func _signature_material(color: Color, energy := 2.2) -> StandardMaterial3D:
    var key := "%s|%.3f" % [color.to_html(true), energy]
    if _signature_material_cache.has(key):
        return _signature_material_cache[key] as StandardMaterial3D
    var mat := StandardMaterial3D.new()
    mat.albedo_color = color
    mat.metallic = 0.24
    mat.roughness = 0.34
    mat.emission_enabled = true
    mat.emission = color
    mat.emission_energy_multiplier = energy
    _signature_material_cache[key] = mat
    return mat

func _add_eye_beacon(color: Color, at: Vector3, size: float) -> void:
    var beacon := MeshInstance3D.new()
    beacon.mesh = _signature_sphere_mesh("beacon_%.3f" % size, size, size * 2.0)
    beacon.name = "SignatureBeacon"
    beacon.position = at
    beacon.material_override = _signature_material(color, 3.2)
    add_child(beacon)

func _add_runner_blades(color: Color) -> void:
    var mat := _signature_material(color, 1.85)
    for side in [-1.0, 1.0]:
        var blade := MeshInstance3D.new()
        # Extend the signature in the ground plane so it reads from the gameplay camera,
        # rather than relying on vertical geometry that collapses in top-down projection.
        blade.mesh = _signature_box_mesh("runner_blade", Vector3(0.060, 0.22, 0.42))
        blade.name = "RunnerBladeL" if side < 0.0 else "RunnerBladeR"
        blade.position = Vector3(side * 0.43, 0.82, 0.02)
        blade.rotation_degrees = Vector3(0.0, side * 18.0, side * -20.0)
        blade.material_override = mat
        add_child(blade)
    _add_eye_beacon(color, Vector3(0.0, 1.54, -0.30), 0.060)

static func _brute_armor_material() -> StandardMaterial3D:
    if _shared_brute_armor_material != null:
        return _shared_brute_armor_material
    _shared_brute_armor_material = StandardMaterial3D.new()
    _shared_brute_armor_material.albedo_color = Color(0.055, 0.072, 0.080)
    _shared_brute_armor_material.metallic = 0.54
    _shared_brute_armor_material.roughness = 0.56
    return _shared_brute_armor_material

func _add_brute_shoulders(color: Color) -> void:
    var armor := _brute_armor_material()
    var accent := _signature_material(color, 1.55)
    for side in [-1.0, 1.0]:
        var plate := MeshInstance3D.new()
        plate.mesh = _signature_box_mesh("brute_plate", Vector3(0.28, 0.12, 0.34))
        plate.name = "BrutePlateL" if side < 0.0 else "BrutePlateR"
        plate.position = Vector3(side * 0.47, 1.12, 0.03)
        plate.rotation_degrees = Vector3(-4.0, side * 7.0, side * -12.0)
        plate.material_override = armor
        add_child(plate)

        var edge := MeshInstance3D.new()
        edge.mesh = _signature_box_mesh("brute_edge", Vector3(0.045, 0.045, 0.26))
        edge.name = "BruteEdgeL" if side < 0.0 else "BruteEdgeR"
        edge.position = Vector3(side * 0.57, 1.14, -0.02)
        edge.rotation_degrees = plate.rotation_degrees
        edge.material_override = accent
        add_child(edge)
    _add_eye_beacon(color, Vector3(0.0, 1.72, -0.34), 0.060)

func _add_elite_crown(color: Color) -> void:
    var mat := _signature_material(color, 1.95)
    for side in [-1.0, 1.0]:
        var fin := MeshInstance3D.new()
        fin.mesh = _signature_box_mesh("elite_fin", Vector3(0.055, 0.38, 0.10))
        fin.name = "EliteFinL" if side < 0.0 else "EliteFinR"
        fin.position = Vector3(side * 0.31, 1.62, 0.06)
        fin.rotation_degrees.z = side * -28.0
        fin.material_override = mat
        add_child(fin)
    _add_eye_beacon(color, Vector3(0.0, 1.70, -0.34), 0.075)

func _add_boss_frame(color: Color) -> void:
    var mat := _signature_material(color, 3.4)
    for side in [-1.0, 1.0]:
        var wing := MeshInstance3D.new()
        wing.mesh = _signature_box_mesh("boss_wing", Vector3(0.16, 0.30, 0.58))
        wing.name = "BossWingL" if side < 0.0 else "BossWingR"
        wing.position = Vector3(side * 0.72, 1.16, 0.04)
        wing.rotation_degrees = Vector3(0.0, side * 18.0, side * -16.0)
        wing.material_override = mat
        add_child(wing)

        var horn := MeshInstance3D.new()
        horn.mesh = _signature_box_mesh("boss_horn", Vector3(0.12, 0.62, 0.22))
        horn.name = "BossHornL" if side < 0.0 else "BossHornR"
        horn.position = Vector3(side * 0.52, 1.80, 0.06)
        horn.rotation_degrees.z = side * -32.0
        horn.material_override = mat
        add_child(horn)

    var core := MeshInstance3D.new()
    core.mesh = _signature_sphere_mesh("boss_core", 0.145, 0.29)
    core.name = "BossCore"
    core.position = Vector3(0.0, 1.32, -0.48)
    core.material_override = _signature_material(Color(1.0, 0.30, 0.04), 4.6)
    add_child(core)
    _add_eye_beacon(color, Vector3(0.0, 1.82, -0.46), 0.105)

func _melee_attack_animation_range() -> float:
    return _contact_attack_range() + 0.08

func _update_authored_animation(distance: float) -> void:
    if authored_anim == null or dead:
        return
    if kind != "harrier" and distance <= _melee_attack_animation_range() and authored_anim.has_animation("Idle_Attack"):
        _play_authored("Idle_Attack")
    elif kind in ["runner", "elite", "boss"] and authored_anim.has_animation("Run_Arms"):
        _play_authored("Run_Arms")
    else:
        _play_authored("Walk")

func _play_authored(name: String) -> void:
    if authored_anim == null or current_anim == name or not authored_anim.has_animation(name):
        return
    current_anim = name
    authored_anim.play(name, 0.10)

func _flash(critical := false, killed := false) -> void:
    _play_hit_reaction(critical, killed)
