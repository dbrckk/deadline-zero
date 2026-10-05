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
var shot_audio_voices: Array[AudioStreamPlayer3D] = []
var shot_voice_index := 0
var shot_streams := {}
var damage_pulse: MeshInstance3D
var damage_pulse_material: StandardMaterial3D
var muzzle_flash: MeshInstance3D
var muzzle_flash_material: StandardMaterial3D
var muzzle_flash_tween: Tween
var rifle_visual: Node3D
var rifle_rest_position := Vector3.ZERO
var rifle_recoil_tween: Tween
var weapon_accent_material: StandardMaterial3D
var weapon_accent_tween: Tween
var player_marker_ring: MeshInstance3D
var player_marker_material: StandardMaterial3D
var player_marker_pressure := false
var hit_reaction_left := 0.0
var reduced_flashes := false
var combat_enabled := true
var applied_protocols := {}
var current_target: DZEnemy
var nearest_threat: DZEnemy
var target_refresh_clock := 0.0

const TARGET_REFRESH_INTERVAL := 0.08
const TARGET_SWITCH_RATIO := 0.78
const TARGET_ACQUIRE_RADIUS := 18.0
const ARENA_HALF_EXTENT := 30.0
const MOVE_ACCELERATION := 46.0
const MOVE_DECELERATION := 62.0
const MOVE_TURN_ACCELERATION := 78.0

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
    hit_reaction_left = maxf(0.0, hit_reaction_left - maxf(delta, 0.0))
    fire_clock -= delta
    target_refresh_clock = maxf(0.0, target_refresh_clock - maxf(delta, 0.0))

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

    velocity = _smoothed_movement_velocity(input, delta)
    move_and_slide()
    _constrain_to_arena()
    if hit_reaction_left <= 0.0:
        _update_authored_animation()

    var target := _combat_target()
    _update_player_marker_pressure(_pressure_target(target))
    if target != null:
        var facing := target.global_position
        facing.y = global_position.y
        if global_position.distance_squared_to(facing) > 0.01:
            look_at(facing, Vector3.UP)
        if fire_clock <= 0.0:
            _fire_at(target)
            fire_clock = fire_interval

func _smoothed_movement_velocity(input: Vector2, delta: float) -> Vector3:
    var desired := Vector2(input.x, input.y) * move_speed
    var current := Vector2(velocity.x, velocity.z)
    var rate := MOVE_ACCELERATION
    if input.length_squared() <= 0.0001:
        rate = MOVE_DECELERATION
    elif current.length_squared() > 0.0001 and current.dot(desired) < 0.0:
        rate = MOVE_TURN_ACCELERATION
    var next := current.move_toward(desired, rate * maxf(delta, 0.0))
    return Vector3(next.x, 0.0, next.y)

func _pressure_target(fallback: DZEnemy) -> DZEnemy:
    if nearest_threat != null:
        if is_instance_valid(nearest_threat) and not nearest_threat.dead:
            return nearest_threat
        nearest_threat = null
    if fallback != null and is_instance_valid(fallback) and not fallback.dead:
        return fallback
    return null

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if enabled:
        return
    velocity = Vector3.ZERO
    touch_move = Vector2.ZERO
    fire_clock = max(fire_clock, fire_interval)
    current_target = null
    nearest_threat = null
    target_refresh_clock = 0.0
    _clear_player_marker_pressure()

func _clear_player_marker_pressure() -> void:
    if not player_marker_pressure:
        var idle_locator := get_node_or_null("PlayerPressureLocator") as Node3D
        if idle_locator != null:
            idle_locator.visible = false
        return
    player_marker_pressure = false
    if player_marker_material != null:
        player_marker_material.albedo_color = Color(0.05, 0.72, 1.0, 0.78)
        player_marker_material.emission = Color(0.025, 0.42, 0.72)
        player_marker_material.emission_energy_multiplier = 1.9
    if player_marker_ring != null:
        player_marker_ring.scale = Vector3.ONE
    var locator := get_node_or_null("PlayerPressureLocator") as Node3D
    if locator != null:
        locator.visible = false

func _constrain_to_arena() -> void:
    var clamped_x := clampf(global_position.x, -ARENA_HALF_EXTENT, ARENA_HALF_EXTENT)
    var clamped_z := clampf(global_position.z, -ARENA_HALF_EXTENT, ARENA_HALF_EXTENT)
    if not is_equal_approx(clamped_x, global_position.x):
        velocity.x = 0.0
    if not is_equal_approx(clamped_z, global_position.z):
        velocity.z = 0.0
    global_position.x = clamped_x
    global_position.z = clamped_z

func set_touch_move(value: Vector2) -> void:
    touch_move = value.limit_length(1.0)

func set_reduced_flashes(enabled: bool) -> void:
    reduced_flashes = enabled

func take_damage(amount: float) -> void:
    if invulnerability > 0.0 or health <= 0.0:
        return
    health = max(0.0, health - amount)
    invulnerability = 0.18
    health_changed.emit(health, max_health)
    _trigger_damage_feedback()
    if health <= 0.0:
        hit_reaction_left = 0.0
        if authored_anim != null and authored_anim.has_animation("Death"):
            _play_authored("Death")
        died.emit()
    elif authored_anim != null and authored_anim.has_animation("HitReact"):
        hit_reaction_left = 0.12
        _play_authored("HitReact")

func heal_full() -> void:
    health = max_health
    health_changed.emit(health, max_health)

func can_apply_upgrade(id: String) -> bool:
    if id == "multishot" and weapon_profile == "rail":
        return false
    if not id.ends_with("_protocol"):
        return true
    return applied_protocols.is_empty()

func _apply_weapon_profile_data(profile_id: String, data: Dictionary) -> void:
    weapon_profile = profile_id
    if data.has("tint"):
        weapon_tint = data["tint"]
        if weapon_accent_material != null:
            weapon_accent_material.albedo_color = weapon_tint
            weapon_accent_material.emission = weapon_tint

    weapon_damage *= float(data.get("damage_multiplier", 1.0))
    projectile_speed *= float(data.get("projectile_speed_multiplier", 1.0))

    var interval_multiplier := float(data.get("fire_interval_multiplier", 1.0))
    fire_interval *= interval_multiplier
    if data.has("fire_interval_floor"):
        fire_interval = max(float(data["fire_interval_floor"]), fire_interval)
    if data.has("fire_interval_cap"):
        fire_interval = min(float(data["fire_interval_cap"]), fire_interval)

    if data.has("multishot_set"):
        multishot = int(data["multishot_set"])
    elif data.has("multishot_add"):
        multishot = min(
            multishot + int(data["multishot_add"]),
            int(data.get("multishot_cap", 5))
        )

    if data.has("spread_set"):
        spread_degrees = float(data["spread_set"])
    if data.has("spread_min"):
        spread_degrees = max(spread_degrees, float(data["spread_min"]))
    if data.has("spread_max"):
        spread_degrees = min(spread_degrees, float(data["spread_max"]))

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
            _apply_weapon_profile_data("scatter", DZWeaponProfiles.profile("scatter"))
        "rail_protocol":
            _apply_weapon_profile_data("rail", DZWeaponProfiles.profile("rail"))
        "inferno_protocol":
            _apply_weapon_profile_data("inferno", DZWeaponProfiles.profile("inferno"))
        "cryo_protocol":
            _apply_weapon_profile_data("cryo", DZWeaponProfiles.profile("cryo"))
        "arc_protocol":
            _apply_weapon_profile_data("arc", DZWeaponProfiles.profile("arc"))

func _combat_target() -> DZEnemy:
    var max_target_d2 := TARGET_ACQUIRE_RADIUS * TARGET_ACQUIRE_RADIUS
    if current_target != null and (
        not is_instance_valid(current_target)
        or current_target.dead
        or global_position.distance_squared_to(current_target.global_position) > max_target_d2
    ):
        current_target = null
        target_refresh_clock = 0.0
    if nearest_threat != null and (
        not is_instance_valid(nearest_threat)
        or nearest_threat.dead
        or global_position.distance_squared_to(nearest_threat.global_position) > max_target_d2
    ):
        nearest_threat = null
        target_refresh_clock = 0.0

    if target_refresh_clock > 0.0:
        return current_target

    target_refresh_clock = TARGET_REFRESH_INTERVAL
    var nearest := _nearest_enemy()
    nearest_threat = nearest
    if nearest == null:
        current_target = null
        return null
    if current_target == null:
        current_target = nearest
        return current_target
    if nearest == current_target:
        return current_target

    var current_d2 := global_position.distance_squared_to(current_target.global_position)
    var nearest_d2 := global_position.distance_squared_to(nearest.global_position)
    var switch_threshold := current_d2 * TARGET_SWITCH_RATIO * TARGET_SWITCH_RATIO
    if nearest_d2 < switch_threshold:
        current_target = nearest
    return current_target

func _nearest_enemy() -> DZEnemy:
    var best: DZEnemy
    var best_d2 := TARGET_ACQUIRE_RADIUS * TARGET_ACQUIRE_RADIUS
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead:
            continue
        var d2 := global_position.distance_squared_to(enemy.global_position)
        if d2 <= best_d2:
            best_d2 = d2
            best = enemy
    return best

func _fire_at(enemy: DZEnemy) -> void:
    _play_shot_audio()
    _trigger_muzzle_flash()
    _trigger_rifle_recoil()
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
        var projectile_parent: Node = get_tree().current_scene
        if projectile_parent == null:
            projectile_parent = get_parent()
        if projectile_parent == null:
            projectile.queue_free()
            return
        projectile_parent.add_child(projectile)

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

        rifle_visual = DZAssetLibrary.rifle()
        if rifle_visual != null:
            rifle_visual.name = "Rifle"
            rifle_rest_position = Vector3(0.33, 0.93, -0.38)
            rifle_visual.position = rifle_rest_position
            rifle_visual.rotation_degrees = Vector3(-8.0, 180.0, -4.0)
            rifle_visual.scale = Vector3.ONE * 0.92
            add_child(rifle_visual)

        _build_tactical_rig()
        _build_player_marker()
        _build_muzzle_flash()
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
    _build_tactical_rig()
    _build_player_marker()
    _build_muzzle_flash()
    _build_damage_feedback()

func _build_tactical_rig() -> void:
    var rig := Node3D.new()
    rig.name = "TacticalRig"
    add_child(rig)

    var armor_material := StandardMaterial3D.new()
    armor_material.albedo_color = Color(0.045, 0.080, 0.105)
    armor_material.metallic = 0.72
    armor_material.roughness = 0.34

    var armor_edge_material := StandardMaterial3D.new()
    armor_edge_material.albedo_color = Color(0.12, 0.20, 0.24)
    armor_edge_material.metallic = 0.82
    armor_edge_material.roughness = 0.26

    weapon_accent_material = StandardMaterial3D.new()
    weapon_accent_material.albedo_color = weapon_tint
    weapon_accent_material.emission_enabled = true
    weapon_accent_material.emission = weapon_tint
    weapon_accent_material.emission_energy_multiplier = 1.85
    weapon_accent_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var backplate := MeshInstance3D.new()
    backplate.name = "TacticalBackplate"
    var backplate_mesh := BoxMesh.new()
    backplate_mesh.size = Vector3(0.48, 0.075, 0.34)
    backplate.mesh = backplate_mesh
    backplate.position = Vector3(0.0, 1.17, 0.10)
    backplate.rotation_degrees.x = -7.0
    backplate.material_override = armor_material
    rig.add_child(backplate)

    var spine := MeshInstance3D.new()
    spine.name = "TacticalSpine"
    var spine_mesh := BoxMesh.new()
    spine_mesh.size = Vector3(0.12, 0.055, 0.42)
    spine.mesh = spine_mesh
    spine.position = Vector3(0.0, 1.22, 0.12)
    spine.material_override = armor_edge_material
    rig.add_child(spine)

    var shoulder_edge_mesh := BoxMesh.new()
    shoulder_edge_mesh.size = Vector3(0.045, 0.030, 0.255)
    for side in [-1.0, 1.0]:
        var shoulder := MeshInstance3D.new()
        shoulder.name = "TacticalShoulderL" if side < 0.0 else "TacticalShoulderR"
        var shoulder_mesh := BoxMesh.new()
        shoulder_mesh.size = Vector3(0.23, 0.09, 0.31)
        shoulder.mesh = shoulder_mesh
        shoulder.position = Vector3(side * 0.33, 1.12, 0.035)
        shoulder.rotation_degrees = Vector3(-5.0, side * -8.0, side * -13.0)
        shoulder.material_override = armor_material
        rig.add_child(shoulder)

        var shoulder_edge := MeshInstance3D.new()
        shoulder_edge.name = "TacticalShoulderEdgeL" if side < 0.0 else "TacticalShoulderEdgeR"
        shoulder_edge.mesh = shoulder_edge_mesh
        shoulder_edge.position = Vector3(side * 0.415, 1.155, 0.015)
        shoulder_edge.rotation_degrees = shoulder.rotation_degrees
        shoulder_edge.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        shoulder_edge.material_override = weapon_accent_material
        rig.add_child(shoulder_edge)

    var core := MeshInstance3D.new()
    core.name = "TacticalCore"
    var core_mesh := BoxMesh.new()
    core_mesh.size = Vector3(0.24, 0.025, 0.055)
    core.mesh = core_mesh
    core.position = Vector3(0.0, 1.225, -0.085)
    core.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    core.material_override = weapon_accent_material
    rig.add_child(core)

    var weapon_accent := MeshInstance3D.new()
    weapon_accent.name = "WeaponAccent"
    var accent_mesh := BoxMesh.new()
    accent_mesh.size = Vector3(0.038, 0.030, 0.44)
    weapon_accent.mesh = accent_mesh
    weapon_accent.position = Vector3(0.33, 1.015, -0.59)
    weapon_accent.rotation_degrees.x = -8.0
    weapon_accent.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    weapon_accent.material_override = weapon_accent_material
    add_child(weapon_accent)

func _trigger_rifle_recoil() -> void:
    _trigger_weapon_accent_pulse()
    if rifle_visual == null or not is_instance_valid(rifle_visual):
        return
    if rifle_recoil_tween != null and rifle_recoil_tween.is_valid():
        rifle_recoil_tween.kill()
    rifle_visual.position = rifle_rest_position
    rifle_recoil_tween = create_tween()
    rifle_recoil_tween.tween_property(rifle_visual, "position", rifle_rest_position + Vector3(0.0, 0.015, 0.085), 0.035).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    rifle_recoil_tween.tween_property(rifle_visual, "position", rifle_rest_position, 0.075).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

func _trigger_weapon_accent_pulse() -> void:
    if weapon_accent_material == null:
        return
    if weapon_accent_tween != null and weapon_accent_tween.is_valid():
        weapon_accent_tween.kill()
    weapon_accent_material.emission_energy_multiplier = 4.6
    weapon_accent_tween = create_tween()
    weapon_accent_tween.tween_property(weapon_accent_material, "emission_energy_multiplier", 1.85, 0.085).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

func _build_player_marker() -> void:
    player_marker_material = StandardMaterial3D.new()
    player_marker_material.albedo_color = Color(0.05, 0.72, 1.0, 0.78)
    player_marker_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    player_marker_material.emission_enabled = true
    player_marker_material.emission = Color(0.025, 0.42, 0.72)
    player_marker_material.emission_energy_multiplier = 1.9
    player_marker_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var ring := MeshInstance3D.new()
    ring.name = "PlayerMarkerRing"
    var ring_mesh := TorusMesh.new()
    ring_mesh.inner_radius = 0.53
    ring_mesh.outer_radius = 0.60
    ring_mesh.rings = 40
    ring_mesh.ring_segments = 8
    ring.mesh = ring_mesh
    ring.position.y = 0.045
    ring.material_override = player_marker_material
    add_child(ring)
    player_marker_ring = ring

    var aim_tick := MeshInstance3D.new()
    aim_tick.name = "PlayerAimTick"
    var tick_mesh := BoxMesh.new()
    tick_mesh.size = Vector3(0.10, 0.018, 0.34)
    aim_tick.mesh = tick_mesh
    aim_tick.position = Vector3(0.0, 0.055, -0.73)
    aim_tick.material_override = player_marker_material
    add_child(aim_tick)

    var locator := Node3D.new()
    locator.name = "PlayerPressureLocator"
    locator.position = Vector3(0.0, 1.88, 0.0)
    locator.visible = false
    add_child(locator)
    for side in [-1.0, 1.0]:
        var chevron := MeshInstance3D.new()
        chevron.name = "PlayerPressureChevron_%s" % ("L" if side < 0.0 else "R")
        var chevron_mesh := BoxMesh.new()
        chevron_mesh.size = Vector3(0.24, 0.045, 0.055)
        chevron.mesh = chevron_mesh
        chevron.position = Vector3(side * 0.11, 0.0, 0.0)
        chevron.rotation.z = deg_to_rad(side * 32.0)
        chevron.material_override = player_marker_material
        locator.add_child(chevron)

func _update_player_marker_pressure(target: DZEnemy) -> void:
    if player_marker_ring == null or player_marker_material == null:
        return
    var pressured := target != null and global_position.distance_squared_to(target.global_position) <= 8.41
    if pressured == player_marker_pressure:
        return
    player_marker_pressure = pressured
    if pressured:
        player_marker_material.albedo_color = Color(1.0, 0.52, 0.08, 0.92)
        player_marker_material.emission = Color(0.90, 0.20, 0.015)
        player_marker_material.emission_energy_multiplier = 2.65
        player_marker_ring.scale = Vector3(1.10, 1.0, 1.10)
    else:
        player_marker_material.albedo_color = Color(0.05, 0.72, 1.0, 0.78)
        player_marker_material.emission = Color(0.025, 0.42, 0.72)
        player_marker_material.emission_energy_multiplier = 1.9
        player_marker_ring.scale = Vector3.ONE
    var locator := get_node_or_null("PlayerPressureLocator") as Node3D
    if locator != null:
        locator.visible = pressured

func _build_muzzle_flash() -> void:
    muzzle_flash = MeshInstance3D.new()
    muzzle_flash.name = "MuzzleFlash"
    var flash_mesh := SphereMesh.new()
    flash_mesh.radius = 0.105
    flash_mesh.height = 0.21
    muzzle_flash.mesh = flash_mesh
    muzzle_flash.position = Vector3(0.33, 0.98, -0.90)
    muzzle_flash.scale = Vector3(1.0, 0.70, 1.55)
    muzzle_flash.visible = false

    muzzle_flash_material = StandardMaterial3D.new()
    muzzle_flash_material.albedo_color = Color(1.0, 0.74, 0.18)
    muzzle_flash_material.emission_enabled = true
    muzzle_flash_material.emission = Color(1.0, 0.42, 0.035)
    muzzle_flash_material.emission_energy_multiplier = 5.6
    muzzle_flash_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    muzzle_flash.material_override = muzzle_flash_material
    add_child(muzzle_flash)

func _trigger_muzzle_flash() -> void:
    if muzzle_flash == null or not is_instance_valid(muzzle_flash):
        return
    if muzzle_flash_tween != null and muzzle_flash_tween.is_valid():
        muzzle_flash_tween.kill()
    muzzle_flash.visible = true
    muzzle_flash.scale = Vector3(0.42, 0.30, 0.58) if reduced_flashes else Vector3(0.58, 0.42, 0.82)
    if muzzle_flash_material != null:
        muzzle_flash_material.emission_energy_multiplier = 2.2 if reduced_flashes else 6.2
    var flash_duration := 0.032 if reduced_flashes else 0.055
    var target_scale := Vector3(0.72, 0.48, 1.02) if reduced_flashes else Vector3(1.22, 0.76, 1.72)
    muzzle_flash_tween = create_tween()
    muzzle_flash_tween.set_parallel(true)
    muzzle_flash_tween.tween_property(muzzle_flash, "scale", target_scale, flash_duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if muzzle_flash_material != null:
        muzzle_flash_tween.tween_property(muzzle_flash_material, "emission_energy_multiplier", 0.7 if reduced_flashes else 1.0, flash_duration)
    muzzle_flash_tween.chain().tween_callback(func() -> void:
        if muzzle_flash != null and is_instance_valid(muzzle_flash):
            muzzle_flash.visible = false
    )

func _build_damage_feedback() -> void:
    damage_pulse = MeshInstance3D.new()
    damage_pulse.name = "DamagePulse"
    var pulse_mesh := TorusMesh.new()
    pulse_mesh.inner_radius = 0.68
    pulse_mesh.outer_radius = 0.82
    pulse_mesh.rings = 40
    pulse_mesh.ring_segments = 8
    damage_pulse.mesh = pulse_mesh
    damage_pulse.position.y = 0.055
    damage_pulse.visible = false

    damage_pulse_material = StandardMaterial3D.new()
    damage_pulse_material.albedo_color = Color(1.0, 0.08, 0.035, 0.52)
    damage_pulse_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    damage_pulse_material.emission_enabled = true
    damage_pulse_material.emission = Color(1.0, 0.035, 0.01)
    damage_pulse_material.emission_energy_multiplier = 4.2
    damage_pulse_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    damage_pulse.material_override = damage_pulse_material
    add_child(damage_pulse)

func _trigger_damage_feedback() -> void:
    if damage_pulse != null and is_instance_valid(damage_pulse):
        damage_pulse.visible = true
        damage_pulse.scale = Vector3(0.76, 1.0, 0.76)
        if damage_pulse_material != null:
            damage_pulse_material.albedo_color.a = 0.52
            damage_pulse_material.emission_energy_multiplier = 4.2
        var pulse_tween: Tween = create_tween()
        pulse_tween.set_parallel(true)
        pulse_tween.set_trans(Tween.TRANS_QUAD)
        pulse_tween.set_ease(Tween.EASE_OUT)
        pulse_tween.tween_property(damage_pulse, "scale", Vector3(1.56, 1.0, 1.56), 0.16)
        if damage_pulse_material != null:
            pulse_tween.tween_property(damage_pulse_material, "albedo_color:a", 0.0, 0.16)
            pulse_tween.tween_property(damage_pulse_material, "emission_energy_multiplier", 0.8, 0.16)
        pulse_tween.chain().tween_callback(func() -> void:
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
    shot_audio_voices.clear()
    for voice_index in range(3):
        var voice := AudioStreamPlayer3D.new()
        voice.name = "ShotAudio" if voice_index == 0 else "ShotAudio_%d" % voice_index
        voice.max_distance = 28.0
        voice.unit_size = 5.0
        voice.volume_db = -12.5
        voice.bus = "SFX"
        add_child(voice)
        shot_audio_voices.append(voice)
    shot_audio = shot_audio_voices[0]

func _play_shot_audio() -> void:
    if shot_audio_voices.is_empty():
        return
    if not shot_streams.has(weapon_profile):
        shot_streams[weapon_profile] = DZCombatAudio.shot_stream(weapon_profile)
    var voice := shot_audio_voices[shot_voice_index % shot_audio_voices.size()]
    shot_voice_index = (shot_voice_index + 1) % shot_audio_voices.size()
    voice.stream = shot_streams[weapon_profile]
    voice.pitch_scale = randf_range(0.965, 1.035)
    voice.play()
