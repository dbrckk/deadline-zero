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

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    mesh_instance.name = "ImpactCore"
    var sphere := SphereMesh.new()
    sphere.radius = 0.18
    sphere.height = 0.36
    mesh_instance.mesh = sphere
    core_material = _make_material(color, 4.2)
    mesh_instance.material_override = core_material
    add_child(mesh_instance)

    ring_instance = MeshInstance3D.new()
    ring_instance.name = "ImpactRing"
    var ring := TorusMesh.new()
    ring.inner_radius = 0.24
    ring.outer_radius = 0.34
    ring_instance.mesh = ring
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

    var particle_material := ParticleProcessMaterial.new()
    particle_material.direction = Vector3(0.0, 1.0, 0.0)
    particle_material.spread = 70.0
    particle_material.initial_velocity_min = 2.2
    particle_material.initial_velocity_max = 4.2
    particle_material.gravity = Vector3(0.0, -7.0, 0.0)
    particle_material.scale_min = 0.45
    particle_material.scale_max = 1.0
    particle_material.color = color
    sparks.process_material = particle_material

    var spark_mesh := QuadMesh.new()
    spark_mesh.size = Vector2(0.055, 0.16)
    var spark_material := StandardMaterial3D.new()
    spark_material.albedo_color = color
    spark_material.emission_enabled = true
    spark_material.emission = color
    spark_material.emission_energy_multiplier = 4.5
    spark_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    spark_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    spark_material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    spark_mesh.material = spark_material
    sparks.draw_pass_1 = spark_mesh
    add_child(sparks)
    sparks.emitting = true

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
