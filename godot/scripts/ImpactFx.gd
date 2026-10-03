class_name ImpactFx
extends Node3D

var life := 0.18
var age := 0.0
var color := Color(0.25, 0.9, 1.0, 1.0)
var scale_boost := 1.0
var mesh_instance: MeshInstance3D
var ring_instance: MeshInstance3D
var core_material: StandardMaterial3D
var ring_material: StandardMaterial3D

static var _shared_core_mesh: SphereMesh
static var _shared_ring_mesh: TorusMesh
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

    var sparks := GPUParticles3D.new()
    sparks.name = "ImpactSparks"
    sparks.amount = 8
    sparks.lifetime = 0.22
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
    _shared_core_mesh.radius = 0.18
    _shared_core_mesh.height = 0.36
    _shared_core_mesh.radial_segments = 12
    _shared_core_mesh.rings = 6
    return _shared_core_mesh

static func _ring_mesh() -> TorusMesh:
    if _shared_ring_mesh != null:
        return _shared_ring_mesh
    _shared_ring_mesh = TorusMesh.new()
    _shared_ring_mesh.inner_radius = 0.24
    _shared_ring_mesh.outer_radius = 0.34
    _shared_ring_mesh.rings = 16
    _shared_ring_mesh.ring_segments = 6
    return _shared_ring_mesh

static func _spark_cache_key(tint: Color) -> String:
    return tint.to_html(true)

static func _spark_process_material(tint: Color) -> ParticleProcessMaterial:
    var key := _spark_cache_key(tint)
    if _spark_process_cache.has(key):
        return _spark_process_cache[key] as ParticleProcessMaterial
    var material := ParticleProcessMaterial.new()
    material.direction = Vector3(0.0, 1.0, 0.0)
    material.spread = 70.0
    material.initial_velocity_min = 2.2
    material.initial_velocity_max = 4.2
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
    scale = Vector3.ONE * lerp(0.55, 2.2 * scale_boost, t)
    _fade_material(core_material, t)
    _fade_material(ring_material, t)
    if ring_instance != null:
        ring_instance.scale = Vector3.ONE * lerp(0.72, 1.45, t)
    if age >= life:
        queue_free()

func _fade_material(material: StandardMaterial3D, t: float) -> void:
    if material == null:
        return
    var faded := material.albedo_color
    faded.a = 1.0 - t
    material.albedo_color = faded
    material.emission_energy_multiplier = lerp(4.0, 0.5, t)
