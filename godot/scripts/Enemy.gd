class_name DZEnemy
extends CharacterBody3D

signal died(xp_value: int, at: Vector3)
signal impact(at: Vector3, critical: bool, killed: bool, boss: bool)
signal health_changed(current: float, maximum: float)

var target: Node3D
var kind := "shambler"
var max_health := 60.0
var health := 60.0
var move_speed := 2.1
var contact_damage := 8.0
var xp_value := 2
var dead := false
var attack_cooldown := 0.0
var authored_visual: Node3D
var authored_anim: AnimationPlayer
var current_anim := ""
var attack_windup := 0.0
var attack_target_position := Vector3.ZERO
var elite_burst_clock := 2.4
var boss_slam_clock := 3.6
var telegraph_visual: Node3D

func configure(enemy_kind: String, difficulty: float, chase_target: Node3D) -> void:
    kind = enemy_kind
    target = chase_target
    match kind:
        "boss":
            max_health = 1450.0 * difficulty
            move_speed = 1.38
            contact_damage = 24.0
            xp_value = 35
        "runner":
            max_health = 42.0 * difficulty
            move_speed = 3.7
            contact_damage = 6.0
            xp_value = 2
        "brute":
            max_health = 150.0 * difficulty
            move_speed = 1.45
            contact_damage = 15.0
            xp_value = 5
        "elite":
            max_health = 300.0 * difficulty
            move_speed = 2.0
            contact_damage = 18.0
            xp_value = 8
        _:
            max_health = 68.0 * difficulty
            move_speed = 2.15
            contact_damage = 8.0
            xp_value = 2
    health = max_health
    health_changed.emit(health, max_health)

func _ready() -> void:
    add_to_group("enemies")
    _build_visual()

func _physics_process(delta: float) -> void:
    if dead or target == null or not is_instance_valid(target):
        return
    attack_cooldown = max(0.0, attack_cooldown - delta)
    elite_burst_clock = max(0.0, elite_burst_clock - delta)
    boss_slam_clock = max(0.0, boss_slam_clock - delta)
    var delta_pos := target.global_position - global_position
    delta_pos.y = 0.0
    var distance := delta_pos.length()

    if attack_windup > 0.0:
        velocity = Vector3.ZERO
        attack_windup = max(0.0, attack_windup - delta)
        if attack_windup <= 0.0:
            _resolve_telegraphed_attack()
        return

    if kind == "elite" and elite_burst_clock <= 0.0 and distance < 5.2:
        _begin_telegraphed_attack(0.46, target.global_position)
        elite_burst_clock = 3.0
        return
    if kind == "boss" and boss_slam_clock <= 0.0 and distance < 4.6:
        _begin_telegraphed_attack(0.68, target.global_position)
        boss_slam_clock = 4.1
        return

    if distance > 0.05:
        velocity = delta_pos.normalized() * move_speed
        move_and_slide()
        if velocity.length_squared() > 0.01:
            look_at(global_position + velocity, Vector3.UP)
    _update_authored_animation(distance)
    if distance < 0.85 and attack_cooldown <= 0.0 and target.has_method("take_damage"):
        target.take_damage(contact_damage)
        attack_cooldown = 0.72

func _begin_telegraphed_attack(duration: float, target_position: Vector3) -> void:
    attack_windup = duration
    attack_target_position = target_position
    attack_target_position.y = global_position.y
    _show_telegraph(1.75 if kind == "boss" else 1.05, duration)
    if authored_anim != null and authored_anim.has_animation("Idle_Attack"):
        _play_authored("Idle_Attack")

func _resolve_telegraphed_attack() -> void:
    if target == null or not is_instance_valid(target):
        return
    var radius := 1.95 if kind == "boss" else 1.18
    var damage := contact_damage * (1.35 if kind == "boss" else 0.82)
    var impact_point := global_position.lerp(attack_target_position, 0.58)
    impact_point.y = 0.05
    if target.global_position.distance_to(impact_point) <= radius and target.has_method("take_damage"):
        target.take_damage(damage)
    _spawn_attack_impact(impact_point, radius)
    attack_cooldown = 0.88 if kind == "boss" else 0.64

func _show_telegraph(radius: float, duration: float) -> void:
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = MeshInstance3D.new()
    var mesh := CylinderMesh.new()
    mesh.top_radius = radius
    mesh.bottom_radius = radius
    mesh.height = 0.018
    telegraph_visual.mesh = mesh
    telegraph_visual.global_position = global_position.lerp(attack_target_position, 0.58) + Vector3(0.0, 0.025, 0.0)
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(1.0, 0.16, 0.04, 0.20)
    mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    mat.emission_enabled = true
    mat.emission = Color(1.0, 0.08, 0.01)
    mat.emission_energy_multiplier = 1.5
    telegraph_visual.material_override = mat
    get_tree().current_scene.add_child(telegraph_visual)
    var tween := telegraph_visual.create_tween()
    telegraph_visual.scale = Vector3(0.42, 1.0, 0.42)
    tween.tween_property(telegraph_visual, "scale", Vector3.ONE, duration)
    tween.tween_callback(telegraph_visual.queue_free)

func _spawn_attack_impact(at: Vector3, radius: float) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.22, 0.05) if kind == "boss" else Color(0.72, 0.28, 1.0)
    fx.scale_boost = radius * 1.35
    get_tree().current_scene.add_child(fx)
    fx.global_position = at + Vector3(0.0, 0.10, 0.0)

func take_damage(amount: float, critical := false) -> void:
    if dead:
        return
    health -= amount
    health_changed.emit(max(0.0, health), max_health)
    var killed := health <= 0.0
    impact.emit(global_position + Vector3(0.0, 0.72, 0.0), critical, killed, kind == "boss")
    _flash(critical, killed)
    if killed:
        dead = true
        velocity = Vector3.ZERO
        died.emit(xp_value, global_position)
        if authored_anim != null and authored_anim.has_animation("Death"):
            authored_anim.play("Death", 0.06)
            var timer := get_tree().create_timer(0.62)
            timer.timeout.connect(queue_free)
        else:
            queue_free()

func _build_visual() -> void:
    authored_visual = DZAssetLibrary.enemy(kind)
    if authored_visual != null:
        authored_visual.name = "Visual"
        var scale_factor := 1.0
        match kind:
            "runner": scale_factor = 0.92
            "brute": scale_factor = 1.22
            "elite": scale_factor = 1.15
            "boss": scale_factor = 1.72
        authored_visual.scale = Vector3.ONE * scale_factor
        add_child(authored_visual)
        authored_anim = DZAssetLibrary.animation_player(authored_visual)
        _play_authored("Run_Arms" if kind == "runner" else "Walk")
        return

    var root := Node3D.new()
    root.name = "Visual"
    add_child(root)

    var body := MeshInstance3D.new()
    var capsule := CapsuleMesh.new()
    capsule.radius = 0.34
    capsule.height = 1.25
    body.mesh = capsule
    body.position.y = 0.66

    var mat := StandardMaterial3D.new()
    match kind:
        "runner":
            mat.albedo_color = Color(0.55, 0.85, 0.24)
            root.scale = Vector3(0.88, 0.92, 0.88)
        "brute":
            mat.albedo_color = Color(0.58, 0.16, 0.12)
            root.scale = Vector3(1.28, 1.22, 1.28)
        "elite":
            mat.albedo_color = Color(0.58, 0.20, 0.78)
            mat.metallic = 0.15
            root.scale = Vector3(1.18, 1.24, 1.18)
        _:
            mat.albedo_color = Color(0.26, 0.58, 0.32)
    mat.roughness = 0.78
    body.material_override = mat
    root.add_child(body)

    var head := MeshInstance3D.new()
    var head_mesh := SphereMesh.new()
    head_mesh.radius = 0.27
    head_mesh.height = 0.54
    head.mesh = head_mesh
    head.position = Vector3(0.0, 1.48, 0.0)
    head.material_override = mat
    root.add_child(head)

    var eye := MeshInstance3D.new()
    var eye_mesh := BoxMesh.new()
    eye_mesh.size = Vector3(0.34, 0.06, 0.05)
    eye.mesh = eye_mesh
    eye.position = Vector3(0.0, 1.5, -0.25)
    var eye_mat := StandardMaterial3D.new()
    eye_mat.albedo_color = Color(1.0, 0.16, 0.08)
    eye_mat.emission_enabled = true
    eye_mat.emission = Color(1.0, 0.06, 0.02)
    eye_mat.emission_energy_multiplier = 3.0
    eye.material_override = eye_mat
    root.add_child(eye)

func _update_authored_animation(distance: float) -> void:
    if authored_anim == null or dead:
        return
    if distance < 1.05 and authored_anim.has_animation("Idle_Attack"):
        _play_authored("Idle_Attack")
    elif kind in ["runner", "elite", "boss"] and authored_anim.has_animation("Run_Arms"):
        _play_authored("Run_Arms")
    else:
        _play_authored("Walk")

func _play_authored(name: String) -> void:
    if authored_anim == null or current_anim == name or not authored_anim.has_animation(name):
        return
    current_anim = name
    authored_anim.play(name, 0.10)

func _flash(critical := false, killed := false) -> void:
    var visual := get_node_or_null("Visual")
    if visual:
        var base_scale := visual.scale
        var punch := 1.12 if critical else (1.10 if killed else 1.065)
        var tween := create_tween()
        tween.tween_property(visual, "scale", base_scale * punch, 0.035)
        tween.tween_property(visual, "scale", base_scale, 0.075)
