class_name DZProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 24.0
var lifetime := 1.8
var radius := 0.34
var age := 0.0
var tint := Color(0.25, 0.9, 1.0)
var critical_chance := 0.08
var visual_profile := "vanguard"
var trail_length := 0.55
var trail_width := 0.055
var core_radius := 0.11
var impact_scale := 1.0
var pierce_remaining := 0
var splash_radius := 0.0
var chain_targets := 0
var slow_multiplier := 1.0
var slow_duration := 0.0
var hit_enemy_ids := {}
var spawn_secondary_fx := true
var combat_enabled := true
var configured_origin := Vector3.ZERO
var has_configured_origin := false

func setup(origin: Vector3, direction: Vector3, speed: float, shot_damage: float, shot_tint: Color,
        profile := "vanguard") -> void:
    configured_origin = origin
    has_configured_origin = true
    if is_inside_tree():
        global_position = origin
    velocity = direction.normalized() * speed
    damage = shot_damage
    tint = shot_tint
    visual_profile = profile
    _apply_profile(profile)

static func protocol_pierce_budget(profile: String) -> int:
    return 2 if profile == "rail" else 0

static func protocol_splash_radius(profile: String) -> float:
    return 1.85 if profile == "inferno" else 0.0

static func protocol_chain_targets(profile: String) -> int:
    return 2 if profile == "arc" else 0

static func protocol_slow(profile: String) -> Vector2:
    return Vector2(0.62, 1.6) if profile == "cryo" else Vector2(1.0, 0.0)

func _apply_profile(profile: String) -> void:
    match profile:
        "scatter":
            trail_length = 0.32
            trail_width = 0.09
            core_radius = 0.13
            impact_scale = 1.18
        "rail":
            trail_length = 1.25
            trail_width = 0.035
            core_radius = 0.075
            impact_scale = 1.34
            pierce_remaining = protocol_pierce_budget(profile)
        "inferno":
            trail_length = 0.72
            trail_width = 0.075
            core_radius = 0.12
            impact_scale = 1.22
            splash_radius = protocol_splash_radius(profile)
        "cryo":
            trail_length = 0.82
            trail_width = 0.07
            core_radius = 0.12
            impact_scale = 1.24
            var slow := protocol_slow(profile)
            slow_multiplier = slow.x
            slow_duration = slow.y
        "arc":
            trail_length = 0.94
            trail_width = 0.045
            core_radius = 0.09
            impact_scale = 1.20
            chain_targets = protocol_chain_targets(profile)
        _:
            trail_length = 0.55
            trail_width = 0.055
            core_radius = 0.11
            impact_scale = 1.0

func _ready() -> void:
    if has_configured_origin:
        global_position = configured_origin
    add_to_group("projectiles")
    var glow := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = core_radius
    mesh.height = core_radius * 2.0
    glow.mesh = mesh
    var mat := StandardMaterial3D.new()
    mat.albedo_color = tint
    mat.emission_enabled = true
    mat.emission = tint
    mat.emission_energy_multiplier = 5.0
    glow.material_override = mat
    add_child(glow)

    var trail := MeshInstance3D.new()
    var trail_mesh := BoxMesh.new()
    trail_mesh.size = Vector3(trail_width, trail_width, trail_length)
    trail.mesh = trail_mesh
    trail.position.z = trail_length * 0.52
    trail.material_override = mat
    add_child(trail)

    if visual_profile == "cryo":
        glow.scale = Vector3(0.62, 1.55, 0.62)
        glow.rotation_degrees.z = 45.0
    elif visual_profile == "scatter":
        _add_side_spark(mat, -1.0)
        _add_side_spark(mat, 1.0)
    elif visual_profile == "arc":
        _add_arc_accent()
    elif visual_profile == "inferno":
        _add_flame_core()

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)

func _add_side_spark(mat: StandardMaterial3D, side: float) -> void:
    var spark := MeshInstance3D.new()
    var mesh := BoxMesh.new()
    mesh.size = Vector3(0.025, 0.025, trail_length * 0.62)
    spark.mesh = mesh
    spark.position = Vector3(side * 0.10, 0.0, trail_length * 0.30)
    spark.material_override = mat
    add_child(spark)

func _add_arc_accent() -> void:
    var accent := MeshInstance3D.new()
    var mesh := TorusMesh.new()
    mesh.inner_radius = core_radius * 0.85
    mesh.outer_radius = core_radius * 1.45
    accent.mesh = mesh
    accent.rotation_degrees.x = 90.0
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(0.58, 0.36, 1.0)
    mat.emission_enabled = true
    mat.emission = mat.albedo_color
    mat.emission_energy_multiplier = 4.0
    accent.material_override = mat
    add_child(accent)

func _add_flame_core() -> void:
    var core := OmniLight3D.new()
    core.light_color = Color(1.0, 0.30, 0.04)
    core.light_energy = 1.1
    core.omni_range = 1.35
    add_child(core)

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        return
    age += delta
    global_position += velocity * delta

    for node in get_tree().get_nodes_in_group("enemies"):
        if not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
        if global_position.distance_squared_to(enemy.global_position) <= radius * radius:
            var critical := randf() < critical_chance
            var dealt_damage := damage * (1.75 if critical else 1.0)
            hit_enemy_ids[enemy.get_instance_id()] = true
            enemy.take_damage(dealt_damage, critical)
            _apply_protocol_hit(enemy, dealt_damage)
            _impact(critical)
            if visual_profile == "rail" and pierce_remaining > 0:
                pierce_remaining -= 1
                continue
            queue_free()
            return

    if age >= lifetime:
        queue_free()

func _impact(critical := false) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.76, 0.18) if critical else tint
    fx.scale_boost = (1.45 if critical else 1.0) * impact_scale
    get_tree().current_scene.add_child(fx)
    fx.global_position = global_position


func _apply_protocol_hit(primary: DZEnemy, dealt_damage: float) -> void:
    match visual_profile:
        "inferno":
            _apply_splash(primary, dealt_damage * 0.45, splash_radius)
        "cryo":
            primary.apply_slow(slow_multiplier, slow_duration)
        "arc":
            _apply_chain(primary, dealt_damage)
        _:
            pass

func _apply_splash(primary: DZEnemy, splash_damage: float, range_radius: float) -> void:
    if range_radius <= 0.0:
        return
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
        if primary.global_position.distance_to(enemy.global_position) <= range_radius:
            enemy.take_damage(splash_damage, false)
            if spawn_secondary_fx:
                var fx := ImpactFx.new()
                fx.color = Color(1.0, 0.24, 0.035)
                fx.scale_boost = 0.72
                get_tree().current_scene.add_child(fx)
                fx.global_position = enemy.global_position + Vector3(0.0, 0.45, 0.0)

func _apply_chain(primary: DZEnemy, dealt_damage: float) -> void:
    if chain_targets <= 0:
        return
    var candidates: Array[DZEnemy] = []
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
        if primary.global_position.distance_to(enemy.global_position) <= 3.8:
            candidates.append(enemy)
    candidates.sort_custom(func(a: DZEnemy, b: DZEnemy) -> bool:
        return primary.global_position.distance_squared_to(a.global_position) < primary.global_position.distance_squared_to(b.global_position)
    )
    var count: int = mini(chain_targets, candidates.size())
    for i in range(count):
        var chained := candidates[i]
        var falloff := 0.56 if i == 0 else 0.38
        chained.take_damage(dealt_damage * falloff, false)
        if spawn_secondary_fx:
            var fx := ImpactFx.new()
            fx.color = Color(0.64, 0.42, 1.0)
            fx.scale_boost = 0.78
            get_tree().current_scene.add_child(fx)
            fx.global_position = chained.global_position + Vector3(0.0, 0.55, 0.0)
