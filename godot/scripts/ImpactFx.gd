class_name ImpactFx
extends Node3D

var life := 0.18
var age := 0.0
var color := Color(0.25, 0.9, 1.0, 1.0)
var scale_boost := 1.0
var mesh_instance: MeshInstance3D
var light: OmniLight3D

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    var sphere := SphereMesh.new()
    sphere.radius = 0.18
    sphere.height = 0.36
    mesh_instance.mesh = sphere
    var material := StandardMaterial3D.new()
    material.albedo_color = color
    material.emission_enabled = true
    material.emission = color
    material.emission_energy_multiplier = 3.2
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    mesh_instance.material_override = material
    add_child(mesh_instance)

    light = OmniLight3D.new()
    light.light_color = color
    light.light_energy = 2.0
    light.omni_range = 2.5
    add_child(light)

func _process(delta: float) -> void:
    age += delta
    var t: float = clampf(age / life, 0.0, 1.0)
    scale = Vector3.ONE * lerp(0.55, 2.2 * scale_boost, t)
    var material := mesh_instance.material_override as StandardMaterial3D
    if material:
        var c: Color = color
        c.a = 1.0 - t
        material.albedo_color = c
    light.light_energy = lerp(2.0, 0.0, t)
    if age >= life:
        queue_free()
