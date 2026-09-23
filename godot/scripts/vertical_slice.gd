extends Node3D

const PLAYER_SPEED := 7.2
const CAMERA_HEIGHT := 13.5
const CAMERA_DISTANCE := 9.2
const CAMERA_LOOK_AHEAD := 1.35
const ARENA_HALF := Vector2(22.0, 14.0)

@onready var operative: CharacterBody3D = $Operative
@onready var camera: Camera3D = $CameraRig/Camera3D
@onready var camera_rig: Node3D = $CameraRig
@onready var enemies: Node3D = $Enemies
@onready var projectile_root: Node3D = $Projectiles

var shot_cooldown := 0.0

func _ready() -> void:
    _seed_reference_wave()
    camera.look_at(operative.global_position + Vector3(0, 0.8, 0), Vector3.UP)

func _physics_process(delta: float) -> void:
    var input := Input.get_vector("move_left", "move_right", "move_up", "move_down")
    operative.velocity = Vector3(input.x, 0.0, input.y) * PLAYER_SPEED
    operative.move_and_slide()
    operative.position.x = clampf(operative.position.x, -ARENA_HALF.x, ARENA_HALF.x)
    operative.position.z = clampf(operative.position.z, -ARENA_HALF.y, ARENA_HALF.y)

    var look_ahead := Vector3(input.x, 0.0, input.y) * CAMERA_LOOK_AHEAD
    var target := operative.global_position + look_ahead
    camera_rig.global_position = camera_rig.global_position.lerp(target, 1.0 - exp(-delta * 7.5))
    camera.global_position = camera_rig.global_position + Vector3(0.0, CAMERA_HEIGHT, CAMERA_DISTANCE)
    camera.look_at(camera_rig.global_position, Vector3.UP)

    shot_cooldown -= delta
    if shot_cooldown <= 0.0:
        var target_enemy := _nearest_enemy()
        if target_enemy:
            _fire_at(target_enemy)
            shot_cooldown = 0.22

func _nearest_enemy() -> Node3D:
    var best: Node3D
    var best_distance := INF
    for child in enemies.get_children():
        if child is Node3D:
            var distance := operative.global_position.distance_squared_to(child.global_position)
            if distance < best_distance:
                best_distance = distance
                best = child
    return best

func _fire_at(target: Node3D) -> void:
    var projectile := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = 0.10
    mesh.height = 0.20
    projectile.mesh = mesh
    projectile.position = operative.position + Vector3(0, 0.65, 0)
    projectile.set_meta("direction", projectile.global_position.direction_to(target.global_position))
    projectile_root.add_child(projectile)

func _process(delta: float) -> void:
    for projectile in projectile_root.get_children():
        if projectile is Node3D:
            var direction: Vector3 = projectile.get_meta("direction", Vector3.ZERO)
            projectile.global_position += direction * 18.0 * delta
            if projectile.global_position.length() > 45.0:
                projectile.queue_free()

func _seed_reference_wave() -> void:
    var positions := [
        Vector3(-7, 0.7, -4), Vector3(-4, 0.7, -7), Vector3(4, 0.7, -7),
        Vector3(8, 0.7, -3), Vector3(-9, 0.7, 3), Vector3(8, 0.7, 5),
        Vector3(-3, 0.7, 8), Vector3(4, 0.7, 8)
    ]
    for i in positions.size():
        var enemy := MeshInstance3D.new()
        var capsule := CapsuleMesh.new()
        capsule.radius = 0.48 if i % 3 else 0.72
        capsule.height = 1.55 if i % 3 else 2.05
        enemy.mesh = capsule
        enemy.position = positions[i]
        enemies.add_child(enemy)
