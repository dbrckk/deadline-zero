extends Node3D

const UPGRADE_POOL := [
    {"id":"damage", "title":"HEAVY PAYLOAD", "detail":"Damage +25%", "family":"OFFENSE"},
    {"id":"rate", "title":"RAPID FIRE", "detail":"Fire rate +22%", "family":"CADENCE"},
    {"id":"speed", "title":"SCOUT FRAME", "detail":"Move speed +14%", "family":"MOBILITY"},
    {"id":"health", "title":"REACTIVE PLATING", "detail":"Max HP +30", "family":"SURVIVAL"},
    {"id":"projectile", "title":"HYPER VELOCITY", "detail":"Projectile speed +20%", "family":"BALLISTIC"},
    {"id":"multishot", "title":"MULTISHOT", "detail":"+1 projectile", "family":"BARRAGE"}
]

var player: DZPlayer
var camera: Camera3D
var hud: DZHud
var spawn_clock := 0.0
var next_boss_time := 75.0
var boss_banner_timer := 0.0
var max_enemies := 110
var elapsed := 0.0
var kills := 0
var level := 1
var xp := 0
var xp_next := 10
var game_over := false
var pending_upgrades: Array = []
var touch_id := -1
var touch_origin := Vector2.ZERO
var camera_kick := 0.0
var camera_kick_phase := 0.0
var hit_freeze_left := 0.0
var boss_reveal_target: DZEnemy
var boss_reveal_left := 0.0
var impact_audio: AudioStreamPlayer
var boss_audio: AudioStreamPlayer
var impact_streams := {}

const BOSS_REVEAL_DURATION := 1.15
const BOSS_REVEAL_FOCUS := 0.58
const BOSS_REVEAL_FOV_DELTA := 5.5

func _ready() -> void:
    randomize()
    _build_world()

    player = DZPlayer.new()
    add_child(player)
    player.global_position = Vector3.ZERO
    player.health_changed.connect(_on_health_changed)
    player.died.connect(_on_player_died)

    camera = Camera3D.new()
    camera.current = true
    camera.fov = 48.0
    add_child(camera)
    camera.global_position = Vector3(0.0, 14.0, 10.0)
    camera.look_at(Vector3(0.0, 0.6, 0.0), Vector3.UP)

    hud = DZHud.new()
    add_child(hud)
    hud.upgrade_chosen.connect(_on_upgrade_chosen)
    hud.restart_requested.connect(_on_restart_requested)
    hud.set_health(player.health, player.max_health)
    hud.set_progress(xp, xp_next, level, kills, elapsed)
    _build_combat_audio()

    for i in range(8):
        _spawn_enemy()

func _process(delta: float) -> void:
    if hit_freeze_left > 0.0:
        hit_freeze_left = max(0.0, hit_freeze_left - delta)
        Engine.time_scale = 0.12
    else:
        Engine.time_scale = 1.0

    camera_kick = move_toward(camera_kick, 0.0, delta * 0.95)
    camera_kick_phase += delta * 38.0

    if player and is_instance_valid(player):
        var focus_point := player.global_position + Vector3(0.0, 0.65, 0.0)
        var desired := player.global_position + Vector3(0.0, 14.0, 10.0)
        var target_fov := 48.0

        if boss_reveal_left > 0.0 and boss_reveal_target != null and is_instance_valid(boss_reveal_target) and not boss_reveal_target.dead:
            boss_reveal_left = max(0.0, boss_reveal_left - delta)
            var normalized: float = clampf(boss_reveal_left / BOSS_REVEAL_DURATION, 0.0, 1.0)
            var envelope: float = sin((1.0 - normalized) * PI)
            var midpoint: Vector3 = player.global_position.lerp(boss_reveal_target.global_position, BOSS_REVEAL_FOCUS)
            focus_point = focus_point.lerp(midpoint + Vector3(0.0, 0.78, 0.0), envelope)
            desired = desired.lerp(midpoint + Vector3(0.0, 15.0, 11.2), envelope * 0.72)
            target_fov = 48.0 + BOSS_REVEAL_FOV_DELTA * envelope
        else:
            boss_reveal_left = 0.0
            boss_reveal_target = null

        var kick_offset := Vector3(sin(camera_kick_phase), 0.0, cos(camera_kick_phase * 1.27)) * camera_kick
        camera.global_position = camera.global_position.lerp(desired + kick_offset, 1.0 - exp(-delta * 4.5))
        camera.fov = lerpf(camera.fov, target_fov, 1.0 - exp(-delta * 5.5))
        camera.look_at(focus_point, Vector3.UP)
        _update_offscreen_threat_indicator()

func _physics_process(delta: float) -> void:
    if game_over:
        return
    elapsed += delta
    boss_banner_timer = max(0.0, boss_banner_timer - delta)
    if elapsed >= next_boss_time:
        _spawn_enemy("boss")
        boss_banner_timer = 3.2
        next_boss_time += 75.0

    spawn_clock -= delta
    if spawn_clock <= 0.0:
        var batch := 1 + int(elapsed / 45.0)
        for i in range(min(batch, 4)):
            _spawn_enemy()
        spawn_clock = max(0.20, 0.82 - elapsed * 0.0035)
    hud.set_progress(xp, xp_next, level, kills, elapsed)
    hud.set_wave(_wave_name())

func _unhandled_input(event: InputEvent) -> void:
    if player == null:
        return
    if event is InputEventScreenTouch:
        var touch := event as InputEventScreenTouch
        if touch.pressed and touch.position.x < get_viewport().get_visible_rect().size.x * 0.55 and touch_id < 0:
            touch_id = touch.index
            touch_origin = touch.position
        elif not touch.pressed and touch.index == touch_id:
            touch_id = -1
            player.set_touch_move(Vector2.ZERO)
    elif event is InputEventScreenDrag:
        var drag := event as InputEventScreenDrag
        if drag.index == touch_id:
            var vector := (drag.position - touch_origin) / 90.0
            player.set_touch_move(Vector2(vector.x, vector.y).limit_length(1.0))

func _spawn_enemy(forced_kind: String = "") -> void:
    if player == null or game_over:
        return
    if forced_kind != "boss" and get_tree().get_nodes_in_group("enemies").size() >= max_enemies:
        return
    var angle := randf() * TAU
    var radius := randf_range(12.0, 18.0)
    var pos := player.global_position + Vector3(cos(angle) * radius, 0.0, sin(angle) * radius)
    var roll := randf()
    var kind := forced_kind if not forced_kind.is_empty() else "shambler"
    if forced_kind.is_empty():
        if elapsed > 25.0 and roll > 0.72:
            kind = "runner"
        if elapsed > 55.0 and roll > 0.88:
            kind = "brute"
        if elapsed > 100.0 and roll > 0.96:
            kind = "elite"
    var difficulty := 1.0 + elapsed / 210.0 + float(level - 1) * 0.035
    var enemy := DZEnemy.new()
    enemy.configure(kind, difficulty, player)
    enemy.died.connect(_on_enemy_died)
    enemy.impact.connect(_on_enemy_impact)
    add_child(enemy)
    enemy.global_position = pos
    if kind == "boss":
        _play_boss_stinger()
        boss_reveal_target = enemy
        boss_reveal_left = BOSS_REVEAL_DURATION
        enemy.health_changed.connect(_on_boss_health_changed)
        hud.show_boss("REVENANT PRIME", enemy.max_health)

func _on_boss_health_changed(current: float, maximum: float) -> void:
    if hud:
        hud.set_boss_health(current, maximum)

func _on_enemy_impact(at: Vector3, critical: bool, killed: bool, boss: bool) -> void:
    hit_freeze_left = max(hit_freeze_left, DZCombatFeel.hit_freeze_seconds(critical, killed, boss))
    camera_kick = max(camera_kick, DZCombatFeel.camera_kick(critical, killed, boss))
    _play_impact_audio(critical, killed, boss)

func _on_enemy_died(xp_value: int, at: Vector3) -> void:
    kills += 1
    var orb := DZXpOrb.new()
    orb.amount = xp_value
    orb.target = player
    orb.collected.connect(_on_xp_collected)
    add_child(orb)
    orb.global_position = at + Vector3(0.0, 0.18, 0.0)

func _on_xp_collected(amount: int) -> void:
    xp += amount
    while xp >= xp_next:
        xp -= xp_next
        level += 1
        xp_next = int(round(float(xp_next) * 1.24 + 4.0))
        _offer_upgrade()
        break

func _offer_upgrade() -> void:
    pending_upgrades.clear()
    var available := UPGRADE_POOL.duplicate(true)
    available.shuffle()
    for i in range(3):
        pending_upgrades.append(available[i])
    hud.show_upgrade(pending_upgrades)
    get_tree().paused = true

func _on_upgrade_chosen(index: int) -> void:
    if index < 0 or index >= pending_upgrades.size():
        return
    player.apply_upgrade(pending_upgrades[index]["id"])
    pending_upgrades.clear()
    hud.hide_upgrade()
    get_tree().paused = false

func _on_health_changed(current: float, maximum: float) -> void:
    if hud:
        hud.set_health(current, maximum)

func _on_player_died() -> void:
    Engine.time_scale = 1.0
    game_over = true
    if hud:
        hud.show_game_over(kills, level, elapsed)

func _on_restart_requested() -> void:
    Engine.time_scale = 1.0
    get_tree().paused = false
    get_tree().reload_current_scene()

func _wave_name() -> String:
    if boss_banner_timer > 0.0:
        return "BOSS INBOUND // ELIMINATE THE THREAT"
    if elapsed < 45.0:
        return "QUARANTINE YARD"
    if elapsed < 90.0:
        return "CINDER SURGE"
    if elapsed < 150.0:
        return "NULL SECTOR"
    return "OVERRUN // THREAT ESCALATING"

func _build_world() -> void:
    var environment := WorldEnvironment.new()
    var env := Environment.new()
    env.background_mode = Environment.BG_COLOR
    env.background_color = Color(0.012, 0.020, 0.027)
    env.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    env.ambient_light_color = Color(0.22, 0.34, 0.42)
    env.ambient_light_energy = 0.85
    env.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    environment.environment = env
    add_child(environment)

    var sun := DirectionalLight3D.new()
    sun.rotation_degrees = Vector3(-58.0, -28.0, 0.0)
    sun.light_color = Color(0.76, 0.88, 1.0)
    sun.light_energy = 1.4
    sun.shadow_enabled = true
    add_child(sun)

    var fill := OmniLight3D.new()
    fill.position = Vector3(0.0, 8.0, 0.0)
    fill.light_color = Color(0.08, 0.65, 1.0)
    fill.light_energy = 2.2
    fill.omni_range = 28.0
    add_child(fill)

    var floor := MeshInstance3D.new()
    var plane := PlaneMesh.new()
    plane.size = Vector2(72.0, 72.0)
    floor.mesh = plane
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.075, 0.09, 0.095)
    floor_mat.roughness = 0.86
    floor_mat.metallic = 0.08
    floor.material_override = floor_mat
    add_child(floor)

    for i in range(34):
        if i < 12:
            var authored_prop := DZAssetLibrary.barrier()
            if authored_prop != null:
                authored_prop.position = Vector3(randf_range(-28.0, 28.0), 0.0, randf_range(-28.0, 28.0))
                authored_prop.rotation.y = randf_range(0.0, TAU)
                authored_prop.scale = Vector3.ONE * randf_range(0.85, 1.15)
                add_child(authored_prop)
                continue
        var prop := MeshInstance3D.new()
        var box := BoxMesh.new()
        box.size = Vector3(randf_range(0.5, 1.8), randf_range(0.25, 1.1), randf_range(0.5, 1.8))
        prop.mesh = box
        prop.position = Vector3(randf_range(-28.0, 28.0), box.size.y * 0.5, randf_range(-28.0, 28.0))
        var mat := StandardMaterial3D.new()
        mat.albedo_color = Color(0.11, 0.13, 0.14).lerp(Color(0.22, 0.12, 0.06), randf() * 0.35)
        mat.roughness = 0.74
        mat.metallic = 0.35
        prop.material_override = mat
        add_child(prop)

    for i in range(18):
        var stripe := MeshInstance3D.new()
        var stripe_mesh := BoxMesh.new()
        stripe_mesh.size = Vector3(randf_range(1.5, 4.0), 0.015, 0.08)
        stripe.mesh = stripe_mesh
        stripe.position = Vector3(randf_range(-26.0, 26.0), 0.012, randf_range(-26.0, 26.0))
        stripe.rotation.y = randf_range(0.0, TAU)
        var stripe_mat := StandardMaterial3D.new()
        stripe_mat.albedo_color = Color(0.82, 0.42, 0.06)
        stripe_mat.emission_enabled = true
        stripe_mat.emission = Color(0.45, 0.10, 0.01)
        stripe_mat.emission_energy_multiplier = 0.45
        stripe.material_override = stripe_mat
        add_child(stripe)

func _build_combat_audio() -> void:
    impact_audio = AudioStreamPlayer.new()
    impact_audio.name = "ImpactAudio"
    impact_audio.volume_db = -9.0
    add_child(impact_audio)

    boss_audio = AudioStreamPlayer.new()
    boss_audio.name = "BossStinger"
    boss_audio.volume_db = -6.0
    boss_audio.stream = DZCombatAudio.boss_stinger()
    add_child(boss_audio)

func _play_impact_audio(critical: bool, killed: bool, boss: bool) -> void:
    if impact_audio == null:
        return
    var key := "boss" if boss else ("kill" if killed else ("critical" if critical else "hit"))
    if not impact_streams.has(key):
        impact_streams[key] = DZCombatAudio.impact_stream(critical, killed, boss)
    impact_audio.stream = impact_streams[key]
    impact_audio.pitch_scale = randf_range(0.96, 1.04)
    impact_audio.play()

func _play_boss_stinger() -> void:
    if boss_audio != null:
        boss_audio.play()


func _update_offscreen_threat_indicator() -> void:
    if hud == null or camera == null or player == null or game_over:
        if hud:
            hud.hide_offscreen_threat()
        return

    var best: DZEnemy
    var best_distance := INF
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or (enemy.kind != "elite" and enemy.kind != "boss"):
            continue
        var distance := player.global_position.distance_to(enemy.global_position)
        if distance < best_distance:
            best_distance = distance
            best = enemy

    if best == null:
        hud.hide_offscreen_threat()
        return

    var viewport_size := get_viewport().get_visible_rect().size
    var screen_pos := camera.unproject_position(best.global_position + Vector3(0.0, 0.9, 0.0))
    var margin := Vector2(84.0, 72.0)
    var inside := not camera.is_position_behind(best.global_position)         and screen_pos.x >= margin.x         and screen_pos.y >= margin.y         and screen_pos.x <= viewport_size.x - margin.x         and screen_pos.y <= viewport_size.y - margin.y

    if inside:
        hud.hide_offscreen_threat()
        return

    var center := viewport_size * 0.5
    var direction := screen_pos - center
    if camera.is_position_behind(best.global_position):
        direction = -direction
    if direction.length_squared() < 0.001:
        direction = Vector2.RIGHT

    hud.set_offscreen_threat(direction.normalized(), best.kind, best_distance)
