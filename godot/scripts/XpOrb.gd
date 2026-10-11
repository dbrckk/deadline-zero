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
static var _core_materials := {}
static var _halo_materials := {}

const ELITE_XP_AMOUNT := 8
const BOSS_XP_AMOUNT := 30

var value_tier := "standard"
var visual_tier_scale := 1.0

var orb_visual: MeshInstance3D
var halo_visual: MeshInstance3D

static func tier_for_amount(xp_amount: int) -> String:
    if xp_amount >= BOSS_XP_AMOUNT:
        return "boss"
    if xp_amount >= ELITE_XP_AMOUNT:
        return "elite"
    return "standard"

func _ready() -> void:
    add_to_group("xp_orbs")
    value_tier = tier_for_amount(amount)
    visual_tier_scale = 2.0 if value_tier == "boss" else (1.32 if value_tier == "elite" else 1.0)
    orb_visual = MeshInstance3D.new()
    orb_visual.name = "XpOrbVisual"
    orb_visual.mesh = _orb_mesh()
    orb_visual.material_override = _orb_material(value_tier)
    orb_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(orb_visual)

    halo_visual = MeshInstance3D.new()
    halo_visual.name = "XpOrbHalo"
    halo_visual.mesh = _halo_mesh()
    halo_visual.material_override = _halo_material(value_tier)
    halo_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    halo_visual.position.y = -0.11
    halo_visual.scale = Vector3.ONE * 0.82 * visual_tier_scale
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

static func _halo_material(tier: String = "standard") -> StandardMaterial3D:
    if _halo_materials.has(tier):
        return _halo_materials[tier] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    match tier:
        "boss":
            material.albedo_color = Color(1.0, 0.65, 0.12, 0.66)
            material.emission = Color(1.0, 0.63, 0.08)
            material.emission_energy_multiplier = 3.2
        "elite":
            material.albedo_color = Color(0.24, 0.67, 1.0, 0.60)
            material.emission = Color(0.14, 0.70, 1.0)
            material.emission_energy_multiplier = 2.35
        _:
            material.albedo_color = Color(0.12, 0.92, 0.74, 0.54)
            material.emission = Color(0.06, 1.0, 0.68)
            material.emission_energy_multiplier = 1.75
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.emission_enabled = true
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _halo_materials[tier] = material
    return material

static func _orb_material(tier: String = "standard") -> StandardMaterial3D:
    if _core_materials.has(tier):
        return _core_materials[tier] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    match tier:
        "boss":
            material.albedo_color = Color(1.0, 0.80, 0.30)
            material.emission = Color(1.0, 0.52, 0.10)
            material.emission_energy_multiplier = 4.2
        "elite":
            material.albedo_color = Color(0.38, 0.82, 1.0)
            material.emission = Color(0.18, 0.65, 1.0)
            material.emission_energy_multiplier = 3.5
        _:
            material.albedo_color = Color(0.18, 0.95, 0.75)
            material.emission = Color(0.1, 1.0, 0.7)
            material.emission_energy_multiplier = 3.0
    material.emission_enabled = true
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _core_materials[tier] = material
    return material

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
        orb_visual.scale = Vector3.ONE * idle_pulse * visual_tier_scale
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
        halo_visual.scale = Vector3.ONE * halo_pulse * magnet_boost * visual_tier_scale
    if magnetized:
        var dir := global_position.direction_to(flat_target)
        var target_speed := FORCED_MAGNET_SPEED if forced_magnet else MAGNET_SPEED
        velocity = velocity.lerp(dir * target_speed, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
