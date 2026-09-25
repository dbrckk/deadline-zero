class_name DZEnemyProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 0.0
var target: Node3D
var lifetime := 4.0
var hit_radius := 0.72
var combat_enabled := true
var resolved := false

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

func _build_visual() -> void:
    var core := MeshInstance3D.new()
    core.name = "HarrierBoltCore"
    var core_mesh := SphereMesh.new()
    core_mesh.radius = 0.13
    core_mesh.height = 0.26
    core.mesh = core_mesh
    var core_mat := StandardMaterial3D.new()
    core_mat.albedo_color = Color(0.08, 0.78, 1.0)
    core_mat.emission_enabled = true
    core_mat.emission = Color(0.04, 0.66, 1.0)
    core_mat.emission_energy_multiplier = 5.2
    core.material_override = core_mat
    add_child(core)

    var halo := MeshInstance3D.new()
    halo.name = "HarrierBoltHalo"
    var halo_mesh := SphereMesh.new()
    halo_mesh.radius = 0.24
    halo_mesh.height = 0.48
    halo.mesh = halo_mesh
    var halo_mat := StandardMaterial3D.new()
    halo_mat.albedo_color = Color(0.08, 0.72, 1.0, 0.18)
    halo_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    halo_mat.emission_enabled = true
    halo_mat.emission = Color(0.04, 0.55, 1.0)
    halo_mat.emission_energy_multiplier = 2.6
    halo.material_override = halo_mat
    add_child(halo)

    var light := OmniLight3D.new()
    light.name = "HarrierBoltLight"
    light.light_color = Color(0.12, 0.76, 1.0)
    light.light_energy = 2.2
    light.omni_range = 2.4
    add_child(light)
