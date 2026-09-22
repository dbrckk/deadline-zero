class_name DZProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 24.0
var lifetime := 1.8
var radius := 0.34
var age := 0.0
var tint := Color(0.25, 0.9, 1.0)

func setup(origin: Vector3, direction: Vector3, speed: float, shot_damage: float, shot_tint: Color) -> void:
    global_position = origin
    velocity = direction.normalized() * speed
    damage = shot_damage
    tint = shot_tint

func _ready() -> void:
    var glow := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = 0.11
    mesh.height = 0.22
    glow.mesh = mesh
    var mat := StandardMaterial3D.new()
    mat.albedo_color = tint
    mat.emission_enabled = true
    mat.emission = tint
    mat.emission_energy_multiplier = 5.0
    glow.material_override = mat
    add_child(glow)

    var trail := MeshInstance3D.new()
    var trail_mesh := BoxMesh.new()
    trail_mesh.size = Vector3(0.055, 0.055, 0.55)
    trail.mesh = trail_mesh
    trail.position.z = 0.28
    trail.material_override = mat
    add_child(trail)
    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)

func _physics_process(delta: float) -> void:
    age += delta
    global_position += velocity * delta

    for node in get_tree().get_nodes_in_group("enemies"):
        if not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead:
            continue
        if global_position.distance_squared_to(enemy.global_position) <= radius * radius:
            enemy.take_damage(damage)
            _impact()
            queue_free()
            return

    if age >= lifetime:
        queue_free()

func _impact() -> void:
    var fx := ImpactFx.new()
    fx.color = tint
    get_tree().current_scene.add_child(fx)
    fx.global_position = global_position
