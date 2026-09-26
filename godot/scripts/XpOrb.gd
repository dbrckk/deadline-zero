class_name DZXpOrb
extends Node3D

signal collected(amount: int)

var amount := 1
var target: Node3D
var velocity := Vector3.ZERO
var age := 0.0

func _ready() -> void:
    var orb := MeshInstance3D.new()
    orb.name = "XpCore"
    var mesh := SphereMesh.new()
    mesh.radius = 0.12
    mesh.height = 0.24
    orb.mesh = mesh
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(0.18, 0.95, 0.75)
    mat.emission_enabled = true
    mat.emission = Color(0.1, 1.0, 0.7)
    mat.emission_energy_multiplier = 3.0
    mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    orb.material_override = mat
    add_child(orb)

    var halo := MeshInstance3D.new()
    halo.name = "XpHalo"
    var halo_mesh := TorusMesh.new()
    halo_mesh.inner_radius = 0.16
    halo_mesh.outer_radius = 0.22
    halo.mesh = halo_mesh
    halo.rotation_degrees.x = 90.0
    var halo_mat := StandardMaterial3D.new()
    halo_mat.albedo_color = Color(0.12, 0.86, 1.0, 0.32)
    halo_mat.emission_enabled = true
    halo_mat.emission = Color(0.08, 0.72, 1.0)
    halo_mat.emission_energy_multiplier = 2.6
    halo_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    halo_mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    halo.material_override = halo_mat
    add_child(halo)

func _process(delta: float) -> void:
    age += delta
    rotation.y += delta * 4.0
    position.y = 0.18 + sin(age * 5.0) * 0.05
    if target == null or not is_instance_valid(target):
        return
    var flat_target := target.global_position
    flat_target.y = global_position.y
    var distance := global_position.distance_to(flat_target)
    if distance < 5.0:
        var dir := global_position.direction_to(flat_target)
        velocity = velocity.lerp(dir * 10.0, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
