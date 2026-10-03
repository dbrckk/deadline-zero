class_name DZXpOrb
extends Node3D

signal collected(amount: int)

var amount := 1
var target: Node3D
var velocity := Vector3.ZERO
var age := 0.0

const MAGNET_RADIUS := 5.0
const FORCED_MAGNET_AGE := 5.0
const MAGNET_SPEED := 10.0
const FORCED_MAGNET_SPEED := 14.0

static var _shared_mesh: SphereMesh
static var _shared_material: StandardMaterial3D

func _ready() -> void:
    var orb := MeshInstance3D.new()
    orb.name = "XpOrbVisual"
    orb.mesh = _orb_mesh()
    orb.material_override = _orb_material()
    orb.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(orb)

static func _orb_mesh() -> SphereMesh:
    if _shared_mesh != null:
        return _shared_mesh
    _shared_mesh = SphereMesh.new()
    _shared_mesh.radius = 0.12
    _shared_mesh.height = 0.24
    _shared_mesh.radial_segments = 12
    _shared_mesh.rings = 6
    return _shared_mesh

static func _orb_material() -> StandardMaterial3D:
    if _shared_material != null:
        return _shared_material
    _shared_material = StandardMaterial3D.new()
    _shared_material.albedo_color = Color(0.18, 0.95, 0.75)
    _shared_material.emission_enabled = true
    _shared_material.emission = Color(0.1, 1.0, 0.7)
    _shared_material.emission_energy_multiplier = 3.0
    _shared_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_material

func _process(delta: float) -> void:
    age += delta
    rotation.y += delta * 4.0
    position.y = 0.18 + sin(age * 5.0) * 0.05
    if target == null or not is_instance_valid(target):
        return
    var flat_target := target.global_position
    flat_target.y = global_position.y
    var distance := global_position.distance_to(flat_target)
    var forced_magnet := age >= FORCED_MAGNET_AGE
    if distance < MAGNET_RADIUS or forced_magnet:
        var dir := global_position.direction_to(flat_target)
        var target_speed := FORCED_MAGNET_SPEED if forced_magnet else MAGNET_SPEED
        velocity = velocity.lerp(dir * target_speed, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
