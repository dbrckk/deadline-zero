class_name ImpactFx
extends Node3D

var life := 0.20
var age := 0.0
var color := Color(0.25, 0.9, 1.0, 1.0)
var scale_boost := 1.0
var mesh_instance: MeshInstance3D
var ring_instance: MeshInstance3D
var flare_instance: MeshInstance3D
var core_material: StandardMaterial3D
var ring_material: StandardMaterial3D
var flare_material: StandardMaterial3D

static var _shared_core_mesh: SphereMesh
static var _shared_ring_mesh: TorusMesh
static var _shared_flare_mesh: QuadMesh
static var _spark_process_cache := {}
static var _spark_mesh_cache := {}

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    mesh_instance.name = "ImpactCore"
    mesh_instance.mesh = _core_mesh()
    mesh_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    core_material = _make_material(color, 4.2)
    mesh_instance.material_override = core_material
    add_child(mesh_instance)

    ring_instance = MeshInstance3D.new()
    ring_instance.name = "ImpactRing"
    ring_instance.mesh = _ring_mesh()
    ring_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    ring_instance.rotation_degrees.x = 90.0
    ring_material = _make_material(color.lightened(0.18), 3.4)
    ring_instance.material_override = ring_material
    add_child(ring_instance)

    flare_instance = MeshInstance3D.new()
    flare_instance.name = "ImpactFlare"
    flare_instance.mesh = _flare_mesh()
    flare_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    flare_material = _make_material(color.lightened(0.24), 5.6)
    flare_material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    flare_instance.material_override = flare_material
    add_child(flare_instance)

    var sparks := GPUParticles3D.new()
    sparks.name = "ImpactSparks"
    sparks.amount = 10
    sparks.lifetime = 0.20
    sparks.one_shot = true
    sparks.explosiveness = 1.0
    sparks.randomness = 0.35
    sparks.local_coords = false

    sparks.process_material = _spark_process_material(color)
    sparks.draw_pass_1 = _spark_mesh(color)
    add_child(sparks)
    sparks.emitting = true

static func _core_mesh() -> SphereMesh:
    if _shared_core_mesh != null:
        return _shared_core_mesh
    _shared_core_mesh = SphereMesh.new()
    _shared_core_mesh.radius = 0.20
    _shared_core_mesh.height = 0.40
    _shared_core_mesh.radial_segments = 12
    _shared_core_mesh.rings = 6
    return _shared_core_mesh

static func _ring_mesh() -> TorusMesh:
    if _shared_ring_mesh != null:
        return _shared_ring_mesh
    _shared_ring_mesh = TorusMesh.new()
    _shared_ring_mesh.inner_radius = 0.27
    _shared_ring_mesh.outer_radius = 0.39
    _shared_ring_mesh.rings = 16
    _shared_ring_mesh.ring_segments = 6
    return _shared_ring_mesh

static func _flare_mesh() -> QuadMesh:
    if _shared_flare_mesh != null:
        return _shared_flare_mesh
    _shared_flare_mesh = QuadMesh.new()
    _shared_flare_mesh.size = Vector2(0.62, 0.22)
    return _shared_flare_mesh

static func _spark_cache_key(tint: Color) -> String:
    return tint.to_html(true)

static func _spark_process_material(tint: Color) -> ParticleProcessMaterial:
    var key := _spark_cache_key(tint)
    if _spark_process_cache.has(key):
        return _spark_process_cache[key] as ParticleProcessMaterial
    var material := ParticleProcessMaterial.new()
    material.direction = Vector3(0.0, 1.0, 0.0)
    material.spread = 70.0
    material.initial_velocity_min = 2.8
    material.initial_velocity_max = 5.0
    material.gravity = Vector3(0.0, -7.0, 0.0)
    material.scale_min = 0.45
    material.scale_max = 1.0
    material.color = tint
    _spark_process_cache[key] = material
    return material

static func _spark_mesh(tint: Color) -> QuadMesh:
    var key := _spark_cache_key(tint)
    if _spark_mesh_cache.has(key):
        return _spark_mesh_cache[key] as QuadMesh
    var mesh := QuadMesh.new()
    mesh.size = Vector2(0.055, 0.16)
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = 4.5
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    mesh.material = material
    _spark_mesh_cache[key] = mesh
    return mesh

func _make_material(tint: Color, energy: float) -> StandardMaterial3D:
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = energy
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return material

func _process(delta: float) -> void:
    age += delta
    var t: float = clampf(age / life, 0.0, 1.0)
    scale = Vector3.ONE * lerp(0.50, 2.45 * scale_boost, t)
    _fade_material(core_material, t)
    _fade_material(ring_material, t)
    _fade_material(flare_material, minf(1.0, t * 1.55))
    if ring_instance != null:
        ring_instance.scale = Vector3.ONE * lerp(0.70, 1.62, t)
    if flare_instance != null:
        var flare_t := minf(1.0, t * 1.70)
        flare_instance.scale = Vector3(
            lerp(0.58, 2.05 * scale_boost, flare_t),
            lerp(0.74, 1.28 * scale_boost, flare_t),
            1.0
        )
    if age >= life:
        queue_free()

func _fade_material(material: StandardMaterial3D, t: float) -> void:
    if material == null:
        return
    var faded := material.albedo_color
    faded.a = 1.0 - t
    material.albedo_color = faded
    material.emission_energy_multiplier = lerp(4.0, 0.5, t)
