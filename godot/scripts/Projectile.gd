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

static var _core_mesh_cache := {}
static var _trail_mesh_cache := {}
static var _core_material_cache := {}
static var _trail_material_cache := {}
static var _accent_mesh_cache := {}
static var _shared_arc_accent_material: StandardMaterial3D

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
    var feedback := DZWeaponProfiles.profile(profile)
    trail_length = float(feedback.get("trail_length", 0.55))
    impact_scale = float(feedback.get("impact_weight", 1.0))
    var projectile_scale := float(feedback.get("projectile_scale", 1.0))
    core_radius = 0.11 * projectile_scale
    trail_width = 0.055 * projectile_scale
    match profile:
        "scatter":
            trail_width *= 1.40
        "rail":
            trail_width *= 0.64
            pierce_remaining = protocol_pierce_budget(profile)
        "inferno":
            trail_width *= 1.24
            splash_radius = protocol_splash_radius(profile)
        "cryo":
            trail_width *= 1.18
            var slow := protocol_slow(profile)
            slow_multiplier = slow.x
            slow_duration = slow.y
        "arc":
            trail_width *= 0.82
            chain_targets = protocol_chain_targets(profile)

func _ready() -> void:
    top_level = true
    if has_configured_origin:
        global_position = configured_origin
    add_to_group("projectiles")
    var glow := MeshInstance3D.new()
    glow.name = "ProjectileCore"
    glow.mesh = _cached_core_mesh()
    glow.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var core_mat := _cached_core_material()
    glow.material_override = core_mat
    add_child(glow)

    var trail := MeshInstance3D.new()
    trail.name = "ProjectileTrail"
    trail.mesh = _cached_trail_mesh()
    trail.position.z = trail_length * 0.52
    trail.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var trail_mat := _cached_trail_material()
    trail.material_override = trail_mat
    add_child(trail)

    if visual_profile == "cryo":
        glow.scale = Vector3(0.62, 1.55, 0.62)
        glow.rotation_degrees.z = 45.0
    elif visual_profile == "scatter":
        _add_side_spark(core_mat, -1.0)
        _add_side_spark(core_mat, 1.0)
    elif visual_profile == "arc":
        _add_arc_accent()
    elif visual_profile == "inferno":
        _add_flame_core(core_mat)

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)

func _visual_cache_key() -> String:
    return "%s|%s|%.4f|%.4f|%.4f" % [
        visual_profile,
        tint.to_html(true),
        core_radius,
        trail_width,
        trail_length
    ]

func _cached_core_mesh() -> SphereMesh:
    var key := _visual_cache_key()
    if _core_mesh_cache.has(key):
        return _core_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = core_radius * 0.82
    mesh.height = core_radius * 1.64
    mesh.radial_segments = 10
    mesh.rings = 5
    _core_mesh_cache[key] = mesh
    return mesh

func _cached_trail_mesh() -> BoxMesh:
    var key := _visual_cache_key()
    if _trail_mesh_cache.has(key):
        return _trail_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = Vector3(trail_width, trail_width * 0.72, trail_length)
    _trail_mesh_cache[key] = mesh
    return mesh

func _cached_core_material() -> StandardMaterial3D:
    var key := _visual_cache_key()
    if _core_material_cache.has(key):
        return _core_material_cache[key] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = 3.2
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _core_material_cache[key] = material
    return material

func _cached_trail_material() -> StandardMaterial3D:
    var key := _visual_cache_key()
    if _trail_material_cache.has(key):
        return _trail_material_cache[key] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    material.albedo_color = Color(tint.r * 0.68, tint.g * 0.68, tint.b * 0.68)
    material.emission_enabled = true
    material.emission = Color(tint.r * 0.74, tint.g * 0.74, tint.b * 0.74)
    material.emission_energy_multiplier = 1.65
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _trail_material_cache[key] = material
    return material

func _cached_accent_box_mesh(key: String, size: Vector3) -> BoxMesh:
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = size
    _accent_mesh_cache[key] = mesh
    return mesh

func _cached_accent_sphere_mesh(key: String, radius: float, height: float) -> SphereMesh:
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = radius
    mesh.height = height
    mesh.radial_segments = 10
    mesh.rings = 5
    _accent_mesh_cache[key] = mesh
    return mesh

func _cached_arc_accent_mesh() -> TorusMesh:
    var key := "arc|%.4f" % core_radius
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as TorusMesh
    var mesh := TorusMesh.new()
    mesh.inner_radius = core_radius * 0.85
    mesh.outer_radius = core_radius * 1.45
    mesh.rings = 12
    mesh.ring_segments = 6
    _accent_mesh_cache[key] = mesh
    return mesh

static func _arc_accent_material() -> StandardMaterial3D:
    if _shared_arc_accent_material != null:
        return _shared_arc_accent_material
    _shared_arc_accent_material = StandardMaterial3D.new()
    _shared_arc_accent_material.albedo_color = Color(0.58, 0.36, 1.0)
    _shared_arc_accent_material.emission_enabled = true
    _shared_arc_accent_material.emission = _shared_arc_accent_material.albedo_color
    _shared_arc_accent_material.emission_energy_multiplier = 2.8
    _shared_arc_accent_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_arc_accent_material

func _add_side_spark(mat: StandardMaterial3D, side: float) -> void:
    var spark := MeshInstance3D.new()
    var key := "scatter_spark|%.4f" % trail_length
    spark.mesh = _cached_accent_box_mesh(key, Vector3(0.025, 0.025, trail_length * 0.62))
    spark.position = Vector3(side * 0.10, 0.0, trail_length * 0.30)
    spark.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    spark.material_override = mat
    add_child(spark)

func _add_arc_accent() -> void:
    var accent := MeshInstance3D.new()
    accent.mesh = _cached_arc_accent_mesh()
    accent.rotation_degrees.x = 90.0
    accent.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    accent.material_override = _arc_accent_material()
    add_child(accent)

func _add_flame_core(base_material: StandardMaterial3D) -> void:
    var flame := MeshInstance3D.new()
    var key := "inferno_flame|%.4f" % core_radius
    flame.mesh = _cached_accent_sphere_mesh(key, core_radius * 0.58, core_radius * 1.55)
    flame.scale = Vector3(0.72, 0.72, 1.42)
    flame.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    flame.material_override = base_material
    add_child(flame)

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        return
    age += delta
    var previous_position := global_position
    global_position += velocity * delta
    var hit_candidates := _swept_hit_candidates(previous_position, global_position)
    if hit_candidates.size() > 1:
        hit_candidates.sort_custom(func(a: DZEnemy, b: DZEnemy) -> bool:
            return previous_position.distance_squared_to(a.global_position) < previous_position.distance_squared_to(b.global_position)
        )
    for enemy in hit_candidates:
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
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

func _swept_hit_candidates(from: Vector3, to: Vector3) -> Array[DZEnemy]:
    var segment := to - from
    segment.y = 0.0
    var travel := segment.length()
    var midpoint := from.lerp(to, 0.5)
    var query_radius := radius + travel * 0.5
    var result: Array[DZEnemy] = []
    for node in _enemies_near(midpoint, query_radius):
        if not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
        var offset := enemy.global_position - from
        offset.y = 0.0
        var t := 0.0
        if segment.length_squared() > 0.000001:
            t = clampf(offset.dot(segment) / segment.length_squared(), 0.0, 1.0)
        var closest := from + segment * t
        var miss := enemy.global_position - closest
        miss.y = 0.0
        if miss.length_squared() <= radius * radius:
            result.append(enemy)
    return result

func _candidate_enemies() -> Array:
    return _enemies_near(global_position, radius)

func _enemies_near(position: Vector3, range_radius: float) -> Array:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene != null and scene.has_method("query_enemies_near"):
        return scene.query_enemies_near(position, range_radius)
    return get_tree().get_nodes_in_group("enemies") if get_tree() != null else []

func _impact(critical := false) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.76, 0.18) if critical else tint
    fx.scale_boost = (1.45 if critical else 1.0) * impact_scale
    get_tree().current_scene.add_child(fx)
    fx.global_position = global_position

func _apply_protocol_hit(primary: DZEnemy, dealt_damage: float) -> void:
    match visual_profile:
        "inferno":
            primary.apply_burn(dealt_damage * 0.16, 2.0)
            _apply_splash(primary, dealt_damage * 0.45, splash_radius)
        "cryo":
            primary.apply_slow(slow_multiplier, slow_duration)
        "arc":
            primary.apply_shock(0.24)
            _apply_chain(primary, dealt_damage)

func _apply_splash(primary: DZEnemy, splash_damage: float, range_radius: float) -> void:
    if range_radius <= 0.0:
        return
    for node in _enemies_near(primary.global_position, range_radius):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
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
    for node in _enemies_near(primary.global_position, 3.8):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
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
