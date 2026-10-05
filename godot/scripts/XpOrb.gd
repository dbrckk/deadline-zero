class_name DZXpOrb
extends Node3D

signal collected(amount: int)

var amount := 1
var target: Node3D
var velocity := Vector3.ZERO
var age := 0.0
var combat_enabled := true

const MAGNET_RADIUS := 5.0
const FORCED_MAGNET_AGE := 5.0
const MAGNET_SPEED := 10.0
const FORCED_MAGNET_SPEED := 14.0

static var _shared_mesh: SphereMesh
static var _shared_halo_mesh: TorusMesh
static var _shared_material: StandardMaterial3D
static var _shared_halo_material: StandardMaterial3D

var orb_visual: MeshInstance3D
var halo_visual: MeshInstance3D

func _ready() -> void:
    add_to_group("xp_orbs")
    orb_visual = MeshInstance3D.new()
    orb_visual.name = "XpOrbVisual"
    orb_visual.mesh = _orb_mesh()
    orb_visual.material_override = _orb_material()
    orb_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(orb_visual)

    halo_visual = MeshInstance3D.new()
    halo_visual.name = "XpOrbHalo"
    halo_visual.mesh = _halo_mesh()
    halo_visual.material_override = _halo_material()
    halo_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    halo_visual.position.y = -0.11
    halo_visual.scale = Vector3.ONE * 0.82
    add_child(halo_visual)

static func _orb_mesh() -> SphereMesh:
    if _shared_mesh != null:
        return _shared_mesh
    _shared_mesh = SphereMesh.new()
    _shared_mesh.radius = 0.12
    _shared_mesh.height = 0.24
    _shared_mesh.radial_segments = 12
    _shared_mesh.rings = 6
    return _shared_mesh

static func _halo_mesh() -> TorusMesh:
    if _shared_halo_mesh != null:
        return _shared_halo_mesh
    _shared_halo_mesh = TorusMesh.new()
    _shared_halo_mesh.inner_radius = 0.16
    _shared_halo_mesh.outer_radius = 0.205
    _shared_halo_mesh.rings = 16
    _shared_halo_mesh.ring_segments = 6
    return _shared_halo_mesh

static func _halo_material() -> StandardMaterial3D:
    if _shared_halo_material != null:
        return _shared_halo_material
    _shared_halo_material = StandardMaterial3D.new()
    _shared_halo_material.albedo_color = Color(0.12, 0.92, 0.74, 0.54)
    _shared_halo_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _shared_halo_material.emission_enabled = true
    _shared_halo_material.emission = Color(0.06, 1.0, 0.68)
    _shared_halo_material.emission_energy_multiplier = 1.75
    _shared_halo_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_halo_material

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

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _process(delta: float) -> void:
    if not combat_enabled:
        return
    age += delta
    rotation.y += delta * 4.0
    position.y = 0.18 + sin(age * 5.0) * 0.05
    var idle_pulse := 0.93 + (0.5 + 0.5 * sin(age * 6.4)) * 0.10
    if orb_visual != null:
        orb_visual.scale = Vector3.ONE * idle_pulse
    if target == null or not is_instance_valid(target):
        return
    var flat_target := target.global_position
    flat_target.y = global_position.y
    var distance := global_position.distance_to(flat_target)
    var forced_magnet := age >= FORCED_MAGNET_AGE
    var magnetized := distance < MAGNET_RADIUS or forced_magnet
    if halo_visual != null:
        var halo_pulse := 0.86 + (0.5 + 0.5 * sin(age * 7.2)) * 0.14
        var magnet_boost := 1.34 if magnetized else 1.0
        halo_visual.scale = Vector3.ONE * halo_pulse * magnet_boost
    if magnetized:
        var dir := global_position.direction_to(flat_target)
        var target_speed := FORCED_MAGNET_SPEED if forced_magnet else MAGNET_SPEED
        velocity = velocity.lerp(dir * target_speed, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
