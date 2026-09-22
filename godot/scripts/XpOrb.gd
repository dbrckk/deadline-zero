class_name DZXpOrb
extends Node3D

signal collected(amount: int)

var amount := 1
var target: Node3D
var velocity := Vector3.ZERO
var age := 0.0

func _ready() -> void:
    var orb := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = 0.12
    mesh.height = 0.24
    orb.mesh = mesh
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(0.18, 0.95, 0.75)
    mat.emission_enabled = true
    mat.emission = Color(0.1, 1.0, 0.7)
    mat.emission_energy_multiplier = 3.0
    orb.material_override = mat
    add_child(orb)

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
