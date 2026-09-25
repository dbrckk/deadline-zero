class_name DZPlayer
extends CharacterBody3D

signal health_changed(current: float, maximum: float)
signal died

var move_speed := 6.2
var max_health := 100.0
var health := 100.0
var weapon_damage := 26.0
var fire_interval := 0.34
var projectile_speed := 19.0
var multishot := 1
var spread_degrees := 7.0
var weapon_profile := "vanguard"
var weapon_tint := Color(0.18, 0.90, 1.0)
var touch_move := Vector2.ZERO
var fire_clock := 0.0
var invulnerability := 0.0
var authored_visual: Node3D
var authored_anim: AnimationPlayer
var current_anim := ""
var shot_audio: AudioStreamPlayer3D
var shot_streams := {}
var damage_pulse: MeshInstance3D
var combat_enabled := true
var applied_protocols := {}

func _ready() -> void:
    add_to_group("player")
    _build_visual()
    _build_audio()
    health_changed.emit(health, max_health)

func _physics_process(delta: float) -> void:
    if not combat_enabled or health <= 0.0:
        velocity = Vector3.ZERO
        touch_move = Vector2.ZERO
        return

    invulnerability = max(0.0, invulnerability - delta)
    fire_clock -= delta

    var input := Vector2.ZERO
    if Input.is_key_pressed(KEY_A) or Input.is_key_pressed(KEY_LEFT):
        input.x -= 1.0
    if Input.is_key_pressed(KEY_D) or Input.is_key_pressed(KEY_RIGHT):
        input.x += 1.0
    if Input.is_key_pressed(KEY_W) or Input.is_key_pressed(KEY_UP):
        input.y -= 1.0
    if Input.is_key_pressed(KEY_S) or Input.is_key_pressed(KEY_DOWN):
        input.y += 1.0
    if touch_move.length_squared() > input.length_squared():
        input = touch_move
    if input.length() > 1.0:
        input = input.normalized()

    velocity = Vector3(input.x, 0.0, input.y) * move_speed
    move_and_slide()
    _update_authored_animation()

    var target := _nearest_enemy()
    if target != null:
        var facing := target.global_position
        facing.y = global_position.y
        if global_position.distance_squared_to(facing) > 0.01:
            look_at(facing, Vector3.UP)
        if fire_clock <= 0.0:
            _fire_at(target)
            fire_clock = fire_interval

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if enabled:
        return
    velocity = Vector3.ZERO
    touch_move = Vector2.ZERO
    fire_clock = max(fire_clock, fire_interval)

func set_touch_move(value: Vector2) -> void:
    touch_move = value.limit_length(1.0)

func take_damage(amount: float) -> void:
    if invulnerability > 0.0 or health <= 0.0:
        return
    health = max(0.0, health - amount)
    invulnerability = 0.18
    health_changed.emit(health, max_health)
    _trigger_damage_feedback()
    if health <= 0.0:
        died.emit()

func heal_full() -> void:
    health = max_health
    health_changed.emit(health, max_health)

func can_apply_upgrade(id: String) -> bool:
    if not id.ends_with("_protocol"):
        return true
    return applied_protocols.is_empty()

func apply_upgrade(id: String) -> void:
    if not can_apply_upgrade(id):
        return
    if id.ends_with("_protocol"):
        if applied_protocols.has(id):
            return
        applied_protocols[id] = true
    match id:
        "damage":
            weapon_damage *= 1.25
        "rate":
            fire_interval = max(0.10, fire_interval * 0.82)
        "speed":
            move_speed *= 1.14
        "health":
            max_health += 30.0
            health = min(max_health, health + 30.0)
            health_changed.emit(health, max_health)
        "projectile":
            projectile_speed *= 1.20
        "multishot":
            multishot = min(multishot + 1, 5)
        "berserker":
            weapon_damage *= 1.45
            max_health = max(40.0, max_health * 0.85)
            health = min(health, max_health)
            health_changed.emit(health, max_health)
        "overclock":
            fire_interval = max(0.09, fire_interval * 0.72)
            weapon_damage *= 0.90
        "fortress":
            max_health += 55.0
            health = min(max_health, health + 55.0)
            move_speed *= 0.94
            health_changed.emit(health, max_health)
        "scatter_protocol":
            weapon_profile = "scatter"
            weapon_tint = Color(1.0, 0.56, 0.18)
            multishot = min(multishot + 2, 5)
            spread_degrees = max(spread_degrees, 11.0)
            weapon_damage *= 0.82
        "rail_protocol":
            weapon_profile = "rail"
            weapon_tint = Color(0.72, 0.58, 1.0)
            weapon_damage *= 1.50
            projectile_speed *= 1.40
            fire_interval = min(0.80, fire_interval * 1.22)
            multishot = 1
            spread_degrees = 3.0
        "inferno_protocol":
            weapon_profile = "inferno"
            weapon_tint = Color(1.0, 0.24, 0.035)
            weapon_damage *= 1.20
            fire_interval = min(0.80, fire_interval * 1.08)
        "cryo_protocol":
            weapon_profile = "cryo"
            weapon_tint = Color(0.30, 0.90, 1.0)
            projectile_speed *= 1.12
            fire_interval = max(0.09, fire_interval * 0.90)
            weapon_damage *= 0.95
        "arc_protocol":
            weapon_profile = "arc"
            weapon_tint = Color(0.64, 0.42, 1.0)
            multishot = min(multishot + 1, 5)
            spread_degrees = min(spread_degrees, 4.0)
            fire_interval = max(0.09, fire_interval * 0.92)
            weapon_damage *= 0.90

func _nearest_enemy() -> DZEnemy:
    var best: DZEnemy
    var best_d2 := INF
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead:
            continue
        var d2 := global_position.distance_squared_to(enemy.global_position)
        if d2 < best_d2:
            best_d2 = d2
            best = enemy
    return best

func _fire_at(enemy: DZEnemy) -> void:
    _play_shot_audio()
    var base_dir := global_position.direction_to(enemy.global_position)
    base_dir.y = 0.0
    base_dir = base_dir.normalized()
    if base_dir.length_squared() < 0.01:
        return
    for i in range(multishot):
        var offset := float(i) - float(multishot - 1) * 0.5
        var dir := base_dir.rotated(Vector3.UP, deg_to_rad(offset * spread_degrees))
        var projectile := DZProjectile.new()
        projectile.setup(global_position + Vector3(0.0, 0.72, 0.0) + dir * 0.5,
            dir, projectile_speed, weapon_damage, weapon_tint, weapon_profile)
        get_tree().current_scene.add_child(projectile)

func _build_visual() -> void:
    authored_visual = DZAssetLibrary.player()
    if authored_visual != null:
        authored_visual.name = "Visual"
        authored_visual.scale = Vector3.ONE * 1.02
        add_child(authored_visual)
        authored_anim = DZAssetLibrary.animation_player(authored_visual)
        _play_authored("Idle_Gun")

        for hidden_name in ["Knife", "WoodenBat_Saw"]:
            var hidden := authored_visual.find_child(hidden_name, true, false)
            if hidden is GeometryInstance3D:
                (hidden as GeometryInstance3D).visible = false

        var rifle := DZAssetLibrary.rifle()
        if rifle != null:
            rifle.name = "Rifle"
            rifle.position = Vector3(0.33, 0.93, -0.38)
            rifle.rotation_degrees = Vector3(-8.0, 180.0, -4.0)
            rifle.scale = Vector3.ONE * 0.92
            add_child(rifle)

        var ring := MeshInstance3D.new()
        var ring_mesh := CylinderMesh.new()
        ring_mesh.top_radius = 0.54
        ring_mesh.bottom_radius = 0.54
        ring_mesh.height = 0.025
        ring.mesh = ring_mesh
        ring.position.y = 0.02
        var ring_mat := StandardMaterial3D.new()
        ring_mat.albedo_color = Color(0.04, 0.78, 1.0, 0.26)
        ring_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
        ring_mat.emission_enabled = true
        ring_mat.emission = Color(0.02, 0.42, 0.70)
        ring_mat.emission_energy_multiplier = 1.6
        ring.material_override = ring_mat
        add_child(ring)
        _build_damage_feedback()
        return

    var visual := Node3D.new()
    visual.name = "Visual"
    add_child(visual)

    var body := MeshInstance3D.new()
    var capsule := CapsuleMesh.new()
    capsule.radius = 0.34
    capsule.height = 1.28
    body.mesh = capsule
    body.position.y = 0.70
    var body_mat := StandardMaterial3D.new()
    body_mat.albedo_color = Color(0.10, 0.20, 0.25)
    body_mat.metallic = 0.72
    body_mat.roughness = 0.30
    body.material_override = body_mat
    visual.add_child(body)

    var visor := MeshInstance3D.new()
    var visor_mesh := BoxMesh.new()
    visor_mesh.size = Vector3(0.44, 0.16, 0.08)
    visor.mesh = visor_mesh
    visor.position = Vector3(0.0, 1.34, -0.30)
    var visor_mat := StandardMaterial3D.new()
    visor_mat.albedo_color = Color(0.05, 0.85, 1.0)
    visor_mat.emission_enabled = true
    visor_mat.emission = Color(0.05, 0.75, 1.0)
    visor_mat.emission_energy_multiplier = 4.0
    visor.material_override = visor_mat
    visual.add_child(visor)

    var gun := MeshInstance3D.new()
    var gun_mesh := BoxMesh.new()
    gun_mesh.size = Vector3(0.15, 0.14, 0.95)
    gun.mesh = gun_mesh
    gun.position = Vector3(0.42, 0.88, -0.38)
    gun.rotation.x = deg_to_rad(-8.0)
    gun.material_override = body_mat
    visual.add_child(gun)
    _build_damage_feedback()

func _build_damage_feedback() -> void:
    damage_pulse = MeshInstance3D.new()
    damage_pulse.name = "DamagePulse"
    var pulse_mesh := CylinderMesh.new()
    pulse_mesh.top_radius = 0.82
    pulse_mesh.bottom_radius = 0.82
    pulse_mesh.height = 0.035
    damage_pulse.mesh = pulse_mesh
    damage_pulse.position.y = 0.06
    damage_pulse.visible = false

    var pulse_mat := StandardMaterial3D.new()
    pulse_mat.albedo_color = Color(1.0, 0.08, 0.035, 0.34)
    pulse_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    pulse_mat.emission_enabled = true
    pulse_mat.emission = Color(1.0, 0.035, 0.01)
    pulse_mat.emission_energy_multiplier = 3.2
    pulse_mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    damage_pulse.material_override = pulse_mat
    add_child(damage_pulse)

func _trigger_damage_feedback() -> void:
    if damage_pulse != null and is_instance_valid(damage_pulse):
        damage_pulse.visible = true
        damage_pulse.scale = Vector3(0.72, 1.0, 0.72)
        var pulse_tween: Tween = create_tween()
        pulse_tween.set_trans(Tween.TRANS_QUAD)
        pulse_tween.set_ease(Tween.EASE_OUT)
        pulse_tween.tween_property(damage_pulse, "scale", Vector3(1.42, 1.0, 1.42), 0.16)
        pulse_tween.tween_callback(func() -> void:
            if damage_pulse != null and is_instance_valid(damage_pulse):
                damage_pulse.visible = false
        )

    var visual := get_node_or_null("Visual") as Node3D
    if visual != null:
        var base_scale: Vector3 = visual.scale
        var recoil_tween: Tween = create_tween()
        recoil_tween.tween_property(visual, "scale", base_scale * Vector3(1.08, 0.94, 1.08), 0.035)
        recoil_tween.tween_property(visual, "scale", base_scale, 0.085)

func _update_authored_animation() -> void:
    if authored_anim == null:
        return
    _play_authored("Run_Gun" if velocity.length_squared() > 0.08 else "Idle_Gun")

func _play_authored(name: String) -> void:
    if authored_anim == null or current_anim == name or not authored_anim.has_animation(name):
        return
    current_anim = name
    authored_anim.play(name, 0.12)

func _build_audio() -> void:
    shot_audio = AudioStreamPlayer3D.new()
    shot_audio.name = "ShotAudio"
    shot_audio.max_distance = 28.0
    shot_audio.unit_size = 5.0
    shot_audio.volume_db = -11.0
    add_child(shot_audio)

func _play_shot_audio() -> void:
    if shot_audio == null:
        return
    if not shot_streams.has(weapon_profile):
        shot_streams[weapon_profile] = DZCombatAudio.shot_stream(weapon_profile)
    shot_audio.stream = shot_streams[weapon_profile]
    shot_audio.pitch_scale = randf_range(0.97, 1.03)
    shot_audio.play()
