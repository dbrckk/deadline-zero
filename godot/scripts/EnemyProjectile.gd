class_name DZEnemyProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 0.0
var target: Node3D
var lifetime := 4.0
var hit_radius := 0.72
var combat_enabled := true
var resolved := false

static var _core_mesh: SphereMesh
static var _halo_mesh: SphereMesh
static var _trail_mesh: BoxMesh
static var _core_material: StandardMaterial3D
static var _halo_material: StandardMaterial3D
static var _trail_material: StandardMaterial3D

func configure(target_position: Vector3, chase_target: Node3D, amount: float, speed := 8.6) -> void:
    target = chase_target
    damage = amount
    var direction := target_position - global_position
    direction.y = 0.0
    if direction.length_squared() < 0.001:
        direction = Vector3.FORWARD
    velocity = direction.normalized() * speed

func _ready() -> void:
    add_to_group("hostile_projectiles")
    _build_visual()

func _physics_process(delta: float) -> void:
    if not combat_enabled or resolved:
        return
    lifetime -= delta
    if lifetime <= 0.0:
        queue_free()
        return
    global_position += velocity * delta
    if target == null or not is_instance_valid(target):
        return
    var offset := target.global_position - global_position
    offset.y = 0.0
    if offset.length() <= hit_radius:
        _hit_target()

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _hit_target() -> void:
    if resolved:
        return
    resolved = true
    if target != null and is_instance_valid(target) and target.has_method("take_damage"):
        target.take_damage(damage)
    queue_free()

static func _shared_core_mesh() -> SphereMesh:
    if _core_mesh != null:
        return _core_mesh
    _core_mesh = SphereMesh.new()
    _core_mesh.radius = 0.13
    _core_mesh.height = 0.26
    _core_mesh.radial_segments = 10
    _core_mesh.rings = 5
    return _core_mesh

static func _shared_halo_mesh() -> SphereMesh:
    if _halo_mesh != null:
        return _halo_mesh
    _halo_mesh = SphereMesh.new()
    _halo_mesh.radius = 0.24
    _halo_mesh.height = 0.48
    _halo_mesh.radial_segments = 10
    _halo_mesh.rings = 5
    return _halo_mesh

static func _shared_trail_mesh() -> BoxMesh:
    if _trail_mesh != null:
        return _trail_mesh
    _trail_mesh = BoxMesh.new()
    _trail_mesh.size = Vector3(0.07, 0.07, 0.78)
    return _trail_mesh

static func _shared_core_material() -> StandardMaterial3D:
    if _core_material != null:
        return _core_material
    _core_material = StandardMaterial3D.new()
    _core_material.albedo_color = Color(0.08, 0.78, 1.0)
    _core_material.emission_enabled = true
    _core_material.emission = Color(0.04, 0.66, 1.0)
    _core_material.emission_energy_multiplier = 5.2
    _core_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _core_material

static func _shared_halo_material() -> StandardMaterial3D:
    if _halo_material != null:
        return _halo_material
    _halo_material = StandardMaterial3D.new()
    _halo_material.albedo_color = Color(0.08, 0.72, 1.0, 0.18)
    _halo_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _halo_material.emission_enabled = true
    _halo_material.emission = Color(0.04, 0.55, 1.0)
    _halo_material.emission_energy_multiplier = 2.6
    _halo_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _halo_material

static func _shared_trail_material() -> StandardMaterial3D:
    if _trail_material != null:
        return _trail_material
    _trail_material = StandardMaterial3D.new()
    _trail_material.albedo_color = Color(0.05, 0.64, 1.0, 0.42)
    _trail_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _trail_material.emission_enabled = true
    _trail_material.emission = Color(0.04, 0.58, 1.0)
    _trail_material.emission_energy_multiplier = 3.8
    _trail_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _trail_material

func _build_visual() -> void:
    var core := MeshInstance3D.new()
    core.name = "HarrierBoltCore"
    core.mesh = _shared_core_mesh()
    core.material_override = _shared_core_material()
    core.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(core)

    var halo := MeshInstance3D.new()
    halo.name = "HarrierBoltHalo"
    halo.mesh = _shared_halo_mesh()
    halo.material_override = _shared_halo_material()
    halo.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(halo)

    var trail := MeshInstance3D.new()
    trail.name = "HarrierBoltTrail"
    trail.mesh = _shared_trail_mesh()
    trail.position = Vector3(0.0, 0.0, 0.42)
    trail.material_override = _shared_trail_material()
    trail.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(trail)

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)
