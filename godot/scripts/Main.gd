extends Node3D

const HAPTICS := preload("res://scripts/Haptics.gd")

const UPGRADE_POOL := [
    {"id":"damage", "title":"HEAVY PAYLOAD", "detail":"Damage +25%", "family":"OFFENSE"},
    {"id":"rate", "title":"RAPID FIRE", "detail":"Fire rate +22%", "family":"CADENCE"},
    {"id":"speed", "title":"SCOUT FRAME", "detail":"Move speed +14%", "family":"MOBILITY"},
    {"id":"health", "title":"REACTIVE PLATING", "detail":"Max HP +30", "family":"SURVIVAL"},
    {"id":"projectile", "title":"HYPER VELOCITY", "detail":"Projectile speed +20%", "family":"BALLISTIC"},
    {"id":"multishot", "title":"MULTISHOT", "detail":"+1 projectile", "family":"BARRAGE"},
    {"id":"berserker", "title":"BERSERKER CORE", "detail":"+45% damage / -15% max HP", "family":"RISK"},
    {"id":"overclock", "title":"OVERCLOCK", "detail":"+28% fire speed / -10% damage", "family":"CADENCE"},
    {"id":"fortress", "title":"FORTRESS FRAME", "detail":"+55 max HP / -6% move speed", "family":"SURVIVAL"},
    {"id":"scatter_protocol", "title":"SCATTER PROTOCOL", "detail":"+2 projectiles / wider spread", "family":"WEAPON"},
    {"id":"rail_protocol", "title":"RAIL PROTOCOL", "detail":"Heavy fast rounds / slower cadence", "family":"WEAPON"},
    {"id":"inferno_protocol", "title":"INFERNO PROTOCOL", "detail":"+20% damage / slower cadence", "family":"ELEMENTAL"},
    {"id":"cryo_protocol", "title":"CRYO PROTOCOL", "detail":"Faster rounds / tighter cadence", "family":"ELEMENTAL"},
    {"id":"arc_protocol", "title":"ARC PROTOCOL", "detail":"+1 projectile / tight spread", "family":"ELEMENTAL"}
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
var enemy_spatial_index := DZSpatialHash.new(4.0)
var last_player_health := -1.0
var run_director := DZRunDirector.new()
var spawn_rng := RandomNumberGenerator.new()
var director_profile: Dictionary = {}

const SETTINGS_PATH := "user://deadline-zero-settings.cfg"
const BOSS_REVEAL_DURATION := 1.15
const BOSS_REVEAL_FOCUS := 0.58
const BOSS_REVEAL_FOV_DELTA := 5.5

func _ready() -> void:
    randomize()
    spawn_rng.randomize()
    director_profile = run_director.profile(elapsed, level)
    max_enemies = int(director_profile["max_enemies"])
    _ensure_audio_buses()
    _build_world()

    player = DZPlayer.new()
    add_child(player)
    if player.shot_audio != null:
        player.shot_audio.bus = "SFX"
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
    hud.pause_requested.connect(_on_pause_requested)
    hud.resume_requested.connect(_on_resume_requested)
    hud.master_volume_changed.connect(_on_master_volume_changed)
    hud.sfx_volume_changed.connect(_on_sfx_volume_changed)
    _load_audio_settings()
    last_player_health = player.health
    hud.set_health(player.health, player.max_health)
    hud.set_progress(xp, xp_next, level, kills, elapsed, get_tree().get_node_count_in_group("enemies"))
    _build_combat_audio()

    for opening_kind in run_director.opening_roster():
        _spawn_enemy(String(opening_kind))

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
    director_profile = run_director.profile(elapsed, level)
    max_enemies = int(director_profile["max_enemies"])
    boss_banner_timer = max(0.0, boss_banner_timer - delta)
    if elapsed >= next_boss_time:
        _spawn_enemy("boss")
        boss_banner_timer = 3.2
        next_boss_time += 75.0

    spawn_clock -= delta
    if spawn_clock <= 0.0:
        var batch := int(director_profile["batch_size"])
        for i in range(batch):
            _spawn_enemy()
        spawn_clock = float(director_profile["spawn_interval"])
    enemy_spatial_index.rebuild(get_tree().get_nodes_in_group("enemies"))
    hud.set_progress(xp, xp_next, level, kills, elapsed, get_tree().get_node_count_in_group("enemies"))
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

func query_enemies_near(position: Vector3, radius: float) -> Array:
    return enemy_spatial_index.query(position, radius)

func _spawn_enemy(forced_kind: String = "") -> void:
    if player == null or game_over:
        return
    if forced_kind != "boss" and get_tree().get_nodes_in_group("enemies").size() >= max_enemies:
        return
    var angle := spawn_rng.randf() * TAU
    var radius := spawn_rng.randf_range(12.0, 18.0)
    var pos := player.global_position + Vector3(cos(angle) * radius, 0.0, sin(angle) * radius)
    var kind := forced_kind if not forced_kind.is_empty() else run_director.choose_enemy(elapsed, level, spawn_rng)
    var difficulty := float(director_profile.get("difficulty", 1.0))
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
    if hud:
        hud.show_impact_flash(critical, killed, boss)
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
    var available: Array = []
    for upgrade in UPGRADE_POOL:
        var id := String(upgrade["id"])
        if player == null or player.can_apply_upgrade(id):
            available.append(upgrade.duplicate(true))
    available.shuffle()
    for i in range(mini(3, available.size())):
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
        if last_player_health >= 0.0 and current < last_player_health:
            hud.pulse_damage_screen()
        hud.set_health(current, maximum)
    last_player_health = current

func _ensure_audio_buses() -> void:
    if AudioServer.get_bus_index("SFX") < 0:
        AudioServer.add_bus()
        AudioServer.set_bus_name(AudioServer.bus_count - 1, "SFX")

func _set_bus_linear_volume(bus_name: String, value: float) -> void:
    var bus_index := AudioServer.get_bus_index(bus_name)
    if bus_index < 0:
        return
    var linear := clampf(value, 0.0, 1.0)
    AudioServer.set_bus_volume_db(bus_index, -80.0 if linear <= 0.0 else linear_to_db(linear))

func _load_audio_settings(path := SETTINGS_PATH) -> void:
    var settings := DZGameSettings.load_settings(path)
    var master := float(settings.get("master_volume", 0.85))
    var sfx := float(settings.get("sfx_volume", 0.90))
    if hud != null:
        hud.master_volume.set_value_no_signal(master)
        hud.sfx_volume.set_value_no_signal(sfx)
    _set_bus_linear_volume("Master", master)
    _set_bus_linear_volume("SFX", sfx)

func _save_audio_settings(path := SETTINGS_PATH) -> void:
    var master := hud.master_volume.value if hud != null else 0.85
    var sfx := hud.sfx_volume.value if hud != null else 0.90
    DZGameSettings.save(path, {
        "master_volume": master,
        "sfx_volume": sfx
    })

func _on_master_volume_changed(value: float) -> void:
    _set_bus_linear_volume("Master", value)
    _save_audio_settings()

func _on_sfx_volume_changed(value: float) -> void:
    _set_bus_linear_volume("SFX", value)
    _save_audio_settings()

func _on_pause_requested() -> void:
    if game_over or not pending_upgrades.is_empty():
        return
    hud.show_pause_settings()
    get_tree().paused = true

func _on_resume_requested() -> void:
    hud.hide_pause_settings()
    if not game_over and pending_upgrades.is_empty():
        get_tree().paused = false

func _on_player_died() -> void:
    Engine.time_scale = 1.0
    game_over = true
    _freeze_combat()
    if hud:
        hud.show_game_over(kills, level, elapsed)

func _freeze_combat() -> void:
    if player != null and is_instance_valid(player):
        player.set_combat_enabled(false)
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null:
            enemy.set_combat_enabled(false)
    for node in get_tree().get_nodes_in_group("projectiles"):
        var projectile := node as DZProjectile
        if projectile != null:
            projectile.set_combat_enabled(false)
    for node in get_tree().get_nodes_in_group("hostile_projectiles"):
        var hostile := node as DZEnemyProjectile
        if hostile != null:
            hostile.set_combat_enabled(false)

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
    environment.name = "QuarantineEnvironment"
    var env := Environment.new()
    env.background_mode = Environment.BG_COLOR
    env.background_color = Color(0.008, 0.014, 0.020)
    env.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    env.ambient_light_color = Color(0.16, 0.27, 0.34)
    env.ambient_light_energy = 0.72
    env.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    env.fog_enabled = true
    env.fog_light_color = Color(0.08, 0.16, 0.20)
    env.fog_light_energy = 0.42
    env.fog_density = 0.010
    environment.environment = env
    add_child(environment)

    var sun := DirectionalLight3D.new()
    sun.name = "ColdKeyLight"
    sun.rotation_degrees = Vector3(-58.0, -28.0, 0.0)
    sun.light_color = Color(0.70, 0.84, 1.0)
    sun.light_energy = 1.28
    sun.shadow_enabled = true
    add_child(sun)

    var fill := OmniLight3D.new()
    fill.name = "ContainmentFill"
    fill.position = Vector3(0.0, 7.5, 0.0)
    fill.light_color = Color(0.04, 0.52, 0.92)
    fill.light_energy = 1.7
    fill.omni_range = 24.0
    add_child(fill)

    for side in [-1.0, 1.0]:
        var rim := OmniLight3D.new()
        rim.name = "EmergencyRimL" if side < 0.0 else "EmergencyRimR"
        rim.position = Vector3(side * 18.0, 3.2, -10.0)
        rim.light_color = Color(1.0, 0.17, 0.045)
        rim.light_energy = 3.4
        rim.omni_range = 13.0
        add_child(rim)

    var floor := MeshInstance3D.new()
    floor.name = "QuarantineFloor"
    var plane := PlaneMesh.new()
    plane.size = Vector2(72.0, 72.0)
    floor.mesh = plane
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.045, 0.055, 0.060)
    floor_mat.roughness = 0.91
    floor_mat.metallic = 0.05
    floor.material_override = floor_mat
    add_child(floor)

    _build_floor_panels()
    _build_floor_service_grates()
    _build_midfield_inspection_panels()
    _build_service_pylons()
    _build_floor_wear()
    _build_floor_seams()
    _build_containment_lanes()
    _build_perimeter_bulkheads()
    _build_authored_barrier_clusters()
    _build_authored_world_dressing()
    _build_perimeter_street_lights()
    _build_perimeter_beacons()

func _build_floor_panels() -> void:
    # Broad panels stay dark so the arena floor supports combat silhouettes instead of competing
    # with them. Small edge accents carry the quarantine identity without overexposing whole slabs.
    var plate_material := StandardMaterial3D.new()
    plate_material.albedo_color = Color(0.032, 0.045, 0.052)
    plate_material.metallic = 0.30
    plate_material.roughness = 0.90

    var accent_material := StandardMaterial3D.new()
    accent_material.albedo_color = Color(0.035, 0.24, 0.30)
    accent_material.emission_enabled = true
    accent_material.emission = Color(0.012, 0.11, 0.16)
    accent_material.emission_energy_multiplier = 0.24
    accent_material.roughness = 0.72
    accent_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var placements := [
        Vector3(-10.0, 0.010, -4.8), Vector3(-6.2, 0.010, -8.8),
        Vector3(  6.4, 0.010, -8.5), Vector3(10.2, 0.010, -4.4),
        Vector3(-10.4, 0.010,  4.7), Vector3(-6.0, 0.010,  8.7),
        Vector3(  6.2, 0.010,  8.9), Vector3(10.5, 0.010,  4.5),
    ]
    for index in range(placements.size()):
        var plate := MeshInstance3D.new()
        plate.name = "FloorPlate_%02d" % index
        var mesh := BoxMesh.new()
        var plate_width := 3.0 if index % 2 == 0 else 2.5
        mesh.size = Vector3(plate_width, 0.014, 1.28)
        plate.mesh = mesh
        plate.position = placements[index]
        plate.rotation.y = deg_to_rad(float((index * 23) % 35 - 17))
        plate.material_override = plate_material
        add_child(plate)

        if index % 3 == 0:
            var accent := MeshInstance3D.new()
            accent.name = "FloorPlateAccent_%02d" % index
            var accent_mesh := BoxMesh.new()
            accent_mesh.size = Vector3(plate_width * 0.58, 0.010, 0.045)
            accent.mesh = accent_mesh
            accent.position = Vector3(0.0, 0.014, -0.50)
            accent.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
            accent.material_override = accent_material
            plate.add_child(accent)

func _build_floor_service_grates() -> void:
    # Recessed utility grates give the arena functional industrial detail and a second material
    # frequency without introducing collision or stealing contrast from combat silhouettes.
    var frame_material := StandardMaterial3D.new()
    frame_material.albedo_color = Color(0.055, 0.075, 0.085)
    frame_material.metallic = 0.58
    frame_material.roughness = 0.48

    var recess_material := StandardMaterial3D.new()
    recess_material.albedo_color = Color(0.012, 0.018, 0.022)
    recess_material.metallic = 0.12
    recess_material.roughness = 0.94

    var slat_material := StandardMaterial3D.new()
    slat_material.albedo_color = Color(0.10, 0.14, 0.16)
    slat_material.metallic = 0.72
    slat_material.roughness = 0.40

    var placements := [
        {"position": Vector3(-10.8, 0.012, -8.1), "rotation": 0.18},
        {"position": Vector3(10.7, 0.012, -8.0), "rotation": -0.16},
        {"position": Vector3(-10.6, 0.012, 8.2), "rotation": -0.20},
        {"position": Vector3(10.9, 0.012, 8.0), "rotation": 0.15},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var grate := Node3D.new()
        grate.name = "ServiceGrate_%02d" % index
        grate.position = placement["position"]
        grate.rotation.y = float(placement["rotation"])
        add_child(grate)

        var recess := MeshInstance3D.new()
        recess.name = "Recess"
        var recess_mesh := BoxMesh.new()
        recess_mesh.size = Vector3(2.65, 0.012, 0.92)
        recess.mesh = recess_mesh
        recess.position.y = -0.004
        recess.material_override = recess_material
        grate.add_child(recess)

        for rail_index in range(2):
            var rail := MeshInstance3D.new()
            rail.name = "FrameRail_%d" % rail_index
            var rail_mesh := BoxMesh.new()
            rail_mesh.size = Vector3(2.78, 0.026, 0.075)
            rail.mesh = rail_mesh
            rail.position = Vector3(0.0, 0.012, -0.49 if rail_index == 0 else 0.49)
            rail.material_override = frame_material
            grate.add_child(rail)

        for slat_index in range(9):
            var slat := MeshInstance3D.new()
            slat.name = "Slat_%02d" % slat_index
            var slat_mesh := BoxMesh.new()
            slat_mesh.size = Vector3(0.075, 0.024, 0.80)
            slat.mesh = slat_mesh
            slat.position = Vector3(-1.12 + float(slat_index) * 0.28, 0.014, 0.0)
            slat.material_override = slat_material
            grate.add_child(slat)

func _build_midfield_inspection_panels() -> void:
    # Small recessed access panels fill the otherwise empty midfield with believable service
    # detail while remaining flat, non-colliding, and lower contrast than actors/projectiles.
    var recess_material := StandardMaterial3D.new()
    recess_material.albedo_color = Color(0.020, 0.028, 0.033)
    recess_material.metallic = 0.20
    recess_material.roughness = 0.90

    var frame_material := StandardMaterial3D.new()
    frame_material.albedo_color = Color(0.085, 0.105, 0.115)
    frame_material.metallic = 0.56
    frame_material.roughness = 0.46

    var service_material := StandardMaterial3D.new()
    service_material.albedo_color = Color(0.42, 0.16, 0.025)
    service_material.emission_enabled = true
    service_material.emission = Color(0.30, 0.055, 0.006)
    service_material.emission_energy_multiplier = 0.24
    service_material.roughness = 0.68

    var placements := [
        {"position": Vector3(-6.6, 0.010, -2.4), "rotation": -0.18},
        {"position": Vector3( 6.7, 0.010,  2.2), "rotation":  0.16},
        {"position": Vector3(-2.5, 0.010,  6.6), "rotation":  1.42},
        {"position": Vector3( 2.4, 0.010, -6.7), "rotation": -1.39},
        {"position": Vector3(-6.0, 0.010,  5.8), "rotation":  0.72},
        {"position": Vector3( 6.1, 0.010, -5.9), "rotation": -0.74},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var root := Node3D.new()
        root.name = "InspectionPanel_%02d" % index
        root.position = placement["position"]
        root.rotation.y = float(placement["rotation"])
        add_child(root)

        var recess := MeshInstance3D.new()
        recess.name = "Recess"
        var recess_mesh := BoxMesh.new()
        recess_mesh.size = Vector3(1.42, 0.010, 0.72)
        recess.mesh = recess_mesh
        recess.position.y = -0.004
        recess.material_override = recess_material
        root.add_child(recess)

        for side in [-1.0, 1.0]:
            var rail := MeshInstance3D.new()
            rail.name = "FrameRailL" if side < 0.0 else "FrameRailR"
            var rail_mesh := BoxMesh.new()
            rail_mesh.size = Vector3(0.055, 0.018, 0.78)
            rail.mesh = rail_mesh
            rail.position = Vector3(side * 0.70, 0.004, 0.0)
            rail.material_override = frame_material
            root.add_child(rail)

        for stripe_index in range(2):
            var stripe := MeshInstance3D.new()
            stripe.name = "ServiceStripe_%d" % stripe_index
            var stripe_mesh := BoxMesh.new()
            stripe_mesh.size = Vector3(0.28, 0.014, 0.042)
            stripe.mesh = stripe_mesh
            stripe.position = Vector3(-0.42 + float(stripe_index) * 0.84, 0.009, 0.0)
            stripe.material_override = service_material
            root.add_child(stripe)

func _build_service_pylons() -> void:
    # Low service pylons add vertical depth and local shadow anchors without introducing
    # collision or narrowing the central combat lane.
    var body_material := StandardMaterial3D.new()
    body_material.albedo_color = Color(0.045, 0.065, 0.075)
    body_material.metallic = 0.62
    body_material.roughness = 0.48

    var cap_material := StandardMaterial3D.new()
    cap_material.albedo_color = Color(0.10, 0.15, 0.17)
    cap_material.metallic = 0.72
    cap_material.roughness = 0.38

    var signal_material := StandardMaterial3D.new()
    signal_material.albedo_color = Color(0.05, 0.46, 0.62)
    signal_material.emission_enabled = true
    signal_material.emission = Color(0.015, 0.22, 0.34)
    signal_material.emission_energy_multiplier = 0.62
    signal_material.roughness = 0.52

    var placements := [
        Vector3(-12.2, 0.0, -3.9), Vector3(12.3, 0.0, 4.0),
        Vector3(-4.0, 0.0, 12.1), Vector3(4.1, 0.0, -12.0),
        Vector3(-11.0, 0.0, 8.6), Vector3(11.1, 0.0, -8.5),
    ]
    for index in range(placements.size()):
        var pylon := Node3D.new()
        pylon.name = "ServicePylon_%02d" % index
        pylon.position = placements[index]
        pylon.rotation.y = deg_to_rad(float((index * 41 + 9) % 90 - 45))
        add_child(pylon)

        var base := MeshInstance3D.new()
        base.name = "Base"
        var base_mesh := CylinderMesh.new()
        base_mesh.top_radius = 0.42
        base_mesh.bottom_radius = 0.50
        base_mesh.height = 0.16
        base.mesh = base_mesh
        base.position.y = 0.08
        base.material_override = body_material
        pylon.add_child(base)

        var body := MeshInstance3D.new()
        body.name = "Body"
        var body_mesh := BoxMesh.new()
        body_mesh.size = Vector3(0.52, 0.58, 0.40)
        body.mesh = body_mesh
        body.position.y = 0.43
        body.material_override = body_material
        pylon.add_child(body)

        var cap := MeshInstance3D.new()
        cap.name = "Cap"
        var cap_mesh := BoxMesh.new()
        cap_mesh.size = Vector3(0.62, 0.09, 0.48)
        cap.mesh = cap_mesh
        cap.position.y = 0.765
        cap.material_override = cap_material
        pylon.add_child(cap)

        var signal_mesh_instance := MeshInstance3D.new()
        signal_mesh_instance.name = "Signal"
        var signal_mesh := BoxMesh.new()
        signal_mesh.size = Vector3(0.34, 0.055, 0.025)
        signal_mesh_instance.mesh = signal_mesh
        signal_mesh_instance.position = Vector3(0.0, 0.62, -0.215)
        signal_mesh_instance.material_override = signal_material
        pylon.add_child(signal_mesh_instance)

func _build_floor_wear() -> void:
    # Deterministic, collision-free wear breaks the broad uniform floor without competing
    # with enemy silhouettes. Marks stay low-contrast and outside the immediate player halo.
    var scuff_material := StandardMaterial3D.new()
    scuff_material.albedo_color = Color(0.030, 0.040, 0.046, 0.82)
    scuff_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    scuff_material.roughness = 0.98

    var stain_material := StandardMaterial3D.new()
    stain_material.albedo_color = Color(0.075, 0.050, 0.036, 0.54)
    stain_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    stain_material.roughness = 1.0

    var scuffs := [
        Vector3(-8.2, 0.016, -1.8), Vector3(-5.7, 0.016, 5.4),
        Vector3(7.8, 0.016, 1.9), Vector3(5.5, 0.016, -5.6),
        Vector3(-11.8, 0.016, 7.4), Vector3(11.5, 0.016, -7.6),
        Vector3(-2.4, 0.016, 10.6), Vector3(2.7, 0.016, -10.8),
        Vector3(-13.2, 0.016, -2.8), Vector3(13.0, 0.016, 3.1),
        Vector3(-7.8, 0.016, -10.8), Vector3(8.0, 0.016, 10.5),
    ]
    for index in range(scuffs.size()):
        var scuff := MeshInstance3D.new()
        scuff.name = "FloorWear_%02d" % index
        var mesh := BoxMesh.new()
        var long_mark := index % 3 != 0
        mesh.size = Vector3(1.35 if long_mark else 0.72, 0.006, 0.055 if long_mark else 0.16)
        scuff.mesh = mesh
        scuff.position = scuffs[index]
        scuff.rotation.y = deg_to_rad(float((index * 47 + 13) % 170))
        scuff.material_override = scuff_material if index % 4 else stain_material
        add_child(scuff)

    # Small paired marks suggest dragged equipment and old impact paths rather than decorative
    # stripes. Their asymmetry is intentional so the arena does not read as a tiled test grid.
    for cluster_index in range(6):
        var angle := float(cluster_index) * 1.047 + 0.31
        var center := Vector3(cos(angle) * 7.1, 0.017, sin(angle) * 7.1)
        for mark_index in range(2):
            var chip := MeshInstance3D.new()
            chip.name = "FloorChip_%02d_%d" % [cluster_index, mark_index]
            var chip_mesh := BoxMesh.new()
            chip_mesh.size = Vector3(0.24 + float(mark_index) * 0.10, 0.006, 0.07)
            chip.mesh = chip_mesh
            chip.position = center + Vector3(float(mark_index) * 0.32 - 0.16, 0.0, float(mark_index) * 0.13)
            chip.rotation.y = -angle + float(mark_index) * 0.28
            chip.material_override = scuff_material
            add_child(chip)

func _build_perimeter_bulkheads() -> void:
    # Layered quarantine bulkheads replace bright kit silhouettes in the active camera frame.
    # They are collision-free set dressing: readable industrial mass, restrained hazard identity,
    # and small emissive service signals without adding dynamic-light cost.
    var shell_material := StandardMaterial3D.new()
    shell_material.albedo_color = Color(0.022, 0.034, 0.041)
    shell_material.metallic = 0.68
    shell_material.roughness = 0.43

    var inset_material := StandardMaterial3D.new()
    inset_material.albedo_color = Color(0.008, 0.013, 0.017)
    inset_material.metallic = 0.24
    inset_material.roughness = 0.91

    var trim_material := StandardMaterial3D.new()
    trim_material.albedo_color = Color(0.075, 0.105, 0.115)
    trim_material.metallic = 0.78
    trim_material.roughness = 0.34

    var hazard_material := StandardMaterial3D.new()
    hazard_material.albedo_color = Color(0.66, 0.15, 0.018)
    hazard_material.emission_enabled = true
    hazard_material.emission = Color(0.30, 0.035, 0.004)
    hazard_material.emission_energy_multiplier = 0.34
    hazard_material.roughness = 0.58

    var signal_material := StandardMaterial3D.new()
    signal_material.albedo_color = Color(0.035, 0.40, 0.56)
    signal_material.emission_enabled = true
    signal_material.emission = Color(0.015, 0.23, 0.38)
    signal_material.emission_energy_multiplier = 1.15
    signal_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var placements := [
        {"position": Vector3(-17.4, 0.0, -9.5), "rotation": PI * 0.5},
        {"position": Vector3(-17.4, 0.0, 0.0), "rotation": PI * 0.5},
        {"position": Vector3(-17.4, 0.0, 9.5), "rotation": PI * 0.5},
        {"position": Vector3(17.4, 0.0, -9.5), "rotation": -PI * 0.5},
        {"position": Vector3(17.4, 0.0, 0.0), "rotation": -PI * 0.5},
        {"position": Vector3(17.4, 0.0, 9.5), "rotation": -PI * 0.5},
        {"position": Vector3(-10.5, 0.0, -13.7), "rotation": 0.0},
        {"position": Vector3(10.5, 0.0, -13.7), "rotation": 0.0},
        {"position": Vector3(-10.5, 0.0, 13.7), "rotation": PI},
        {"position": Vector3(10.5, 0.0, 13.7), "rotation": PI},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var bulkhead := Node3D.new()
        bulkhead.name = "PerimeterBulkhead_%02d" % index
        bulkhead.position = placement["position"]
        bulkhead.rotation.y = float(placement["rotation"])
        add_child(bulkhead)

        var foundation := MeshInstance3D.new()
        foundation.name = "Foundation"
        var foundation_mesh := BoxMesh.new()
        foundation_mesh.size = Vector3(3.05, 0.16, 0.86)
        foundation.mesh = foundation_mesh
        foundation.position = Vector3(0.0, 0.08, 0.0)
        foundation.material_override = inset_material
        bulkhead.add_child(foundation)

        var shell := MeshInstance3D.new()
        shell.name = "Shell"
        var shell_mesh := BoxMesh.new()
        shell_mesh.size = Vector3(2.72, 0.78, 0.48)
        shell.mesh = shell_mesh
        shell.position = Vector3(0.0, 0.53, 0.03)
        shell.material_override = shell_material
        bulkhead.add_child(shell)

        for side in [-1.0, 1.0]:
            var post := MeshInstance3D.new()
            post.name = "PostL" if side < 0.0 else "PostR"
            var post_mesh := BoxMesh.new()
            post_mesh.size = Vector3(0.18, 1.02, 0.60)
            post.mesh = post_mesh
            post.position = Vector3(side * 1.39, 0.55, 0.04)
            post.material_override = trim_material
            bulkhead.add_child(post)

        var top_rail := MeshInstance3D.new()
        top_rail.name = "TopRail"
        var top_mesh := BoxMesh.new()
        top_mesh.size = Vector3(2.92, 0.13, 0.57)
        top_rail.mesh = top_mesh
        top_rail.position = Vector3(0.0, 0.98, 0.03)
        top_rail.material_override = trim_material
        bulkhead.add_child(top_rail)

        var inset := MeshInstance3D.new()
        inset.name = "InsetPanel"
        var inset_mesh := BoxMesh.new()
        inset_mesh.size = Vector3(1.32, 0.39, 0.035)
        inset.mesh = inset_mesh
        inset.position = Vector3(0.0, 0.56, -0.258)
        inset.material_override = inset_material
        bulkhead.add_child(inset)

        for stripe_index in range(3):
            var stripe := MeshInstance3D.new()
            stripe.name = "HazardStripe_%d" % stripe_index
            var stripe_mesh := BoxMesh.new()
            stripe_mesh.size = Vector3(0.36, 0.045, 0.020)
            stripe.mesh = stripe_mesh
            stripe.position = Vector3(-0.46 + float(stripe_index) * 0.46, 0.30, -0.282)
            stripe.rotation.z = deg_to_rad(-18.0)
            stripe.material_override = hazard_material
            bulkhead.add_child(stripe)

        var service_signal := MeshInstance3D.new()
        service_signal.name = "Signal"
        var signal_mesh := BoxMesh.new()
        signal_mesh.size = Vector3(0.42, 0.055, 0.028)
        service_signal.mesh = signal_mesh
        service_signal.position = Vector3(0.77 if index % 2 == 0 else -0.77, 0.79, -0.285)
        service_signal.material_override = signal_material
        bulkhead.add_child(service_signal)

func _build_authored_barrier_clusters() -> void:
    # Keep authored cover visible at the arena edge without letting the large source meshes
    # dominate the phone framing. The clusters now read as perimeter fortification, not walls.
    var clusters := [
        {"center": Vector3(-29.2, 0.0, -20.4), "rotation": 0.18},
        {"center": Vector3(28.9, 0.0, -20.0), "rotation": -0.28},
        {"center": Vector3(-28.6, 0.0, 20.6), "rotation": 0.72},
        {"center": Vector3(29.3, 0.0, 20.2), "rotation": -0.66}
    ]
    for cluster_index in range(clusters.size()):
        var cluster: Dictionary = clusters[cluster_index]
        var center: Vector3 = cluster["center"]
        var base_rotation: float = cluster["rotation"]
        for item_index in range(4):
            var barrier := DZAssetLibrary.barrier()
            if barrier == null:
                continue
            barrier.name = "AuthoredBarrier_%d_%d" % [cluster_index, item_index]
            var lateral := (float(item_index) - 1.5) * 1.28
            barrier.position = center + Vector3(lateral, 0.0, sin(float(item_index) * 1.7) * 0.28)
            barrier.rotation.y = base_rotation + (0.08 if item_index % 2 == 0 else -0.08)
            barrier.scale = Vector3.ONE * (0.22 + float(item_index % 3) * 0.018)
            add_child(barrier)

func _build_authored_world_dressing() -> void:
    var factories := [
        Callable(DZAssetLibrary, "barrel"),
        Callable(DZAssetLibrary, "pallet"),
        Callable(DZAssetLibrary, "traffic_cone"),
        Callable(DZAssetLibrary, "trash_bag"),
    ]
    var placements := [
        Vector3(-12.8, 0.0, -6.6), Vector3(-12.2, 0.0, 6.4),
        Vector3(12.9, 0.0, 6.8), Vector3(12.4, 0.0, -6.2),
        Vector3(-7.2, 0.0, -12.2), Vector3(7.4, 0.0, 12.4),
        Vector3(-6.8, 0.0, 12.8), Vector3(7.0, 0.0, -12.5),
        Vector3(-20.5, 0.0, -5.5), Vector3(-18.5, 0.0, 5.0),
        Vector3(20.5, 0.0, 5.5), Vector3(18.2, 0.0, -5.4),
        Vector3(-9.5, 0.0, -16.0), Vector3(9.2, 0.0, 16.0),
        Vector3(-6.5, 0.0, 15.0), Vector3(6.8, 0.0, -15.2),
        Vector3(-22.5, 0.0, 11.5), Vector3(22.0, 0.0, -11.2),
        # Midfield service debris adds real authored volume inside the camera's normal combat
        # framing while preserving an unobstructed ~8.8 m central fighting lane.
        Vector3(-8.9, 0.0, -3.0), Vector3(8.9, 0.0, 3.0),
        Vector3(-3.1, 0.0, 8.9), Vector3(3.0, 0.0, -8.9),
        Vector3(-7.1, 0.0, 5.8), Vector3(7.0, 0.0, -5.9),
        Vector3(-5.9, 0.0, -7.0), Vector3(5.8, 0.0, 7.1),
    ]

    for index in range(placements.size()):
        var factory: Callable = factories[index % factories.size()]
        var prop := factory.call() as Node3D
        if prop == null:
            continue
        prop.name = "EnvironmentProp_%02d" % index
        prop.add_to_group("environment_props")
        prop.position = placements[index]
        prop.rotation.y = float((index * 37) % 360) * PI / 180.0
        var midfield := index >= 18
        var scale_factor := (0.58 + float(index % 4) * 0.035) if midfield else (0.74 + float(index % 5) * 0.045)
        prop.scale = Vector3.ONE * scale_factor
        add_child(prop)

    var crack_positions := [
        Vector3(-12.0, 0.012, -5.8), Vector3(12.2, 0.012, 5.5),
        Vector3(-4.5, 0.012, 11.8), Vector3(4.8, 0.012, -11.6),
        Vector3(-17.0, 0.012, 2.0), Vector3(17.2, 0.012, -2.2),
        Vector3(-8.0, 0.012, 17.0), Vector3(8.0, 0.012, -17.0),
    ]
    for index in range(crack_positions.size()):
        var crack := DZAssetLibrary.street_crack()
        if crack == null:
            continue
        crack.name = "StreetDamage_%02d" % index
        crack.add_to_group("environment_ground_detail")
        crack.position = crack_positions[index]
        crack.rotation.y = float((index * 53) % 360) * PI / 180.0
        crack.scale = Vector3.ONE * (0.94 + float(index % 3) * 0.06)
        add_child(crack)

func _build_floor_seams() -> void:
    var seam_material := StandardMaterial3D.new()
    seam_material.albedo_color = Color(0.018, 0.028, 0.034)
    seam_material.roughness = 0.96

    # Large slab seams create believable scale and break the single-plane look while staying
    # safely below gameplay silhouettes.
    for axis in range(2):
        for offset in [-12.0, -4.0, 4.0, 12.0]:
            var seam := MeshInstance3D.new()
            seam.name = "FloorSeam_%d_%d" % [axis, int(offset)]
            var mesh := BoxMesh.new()
            mesh.size = Vector3(34.0, 0.008, 0.055) if axis == 0 else Vector3(0.055, 0.008, 34.0)
            seam.mesh = mesh
            seam.position = Vector3(0.0, 0.006, offset) if axis == 0 else Vector3(offset, 0.006, 0.0)
            seam.material_override = seam_material
            add_child(seam)

func _build_containment_lanes() -> void:
    var lane_material := StandardMaterial3D.new()
    lane_material.albedo_color = Color(0.78, 0.29, 0.035)
    lane_material.emission_enabled = true
    lane_material.emission = Color(0.46, 0.075, 0.008)
    lane_material.emission_energy_multiplier = 0.54
    lane_material.roughness = 0.64

    for axis in range(2):
        for offset in [-9.6, 9.6]:
            for segment in range(-5, 6):
                var stripe := MeshInstance3D.new()
                stripe.name = "ContainmentLane_%d_%d_%d" % [axis, int(offset), segment]
                var stripe_mesh := BoxMesh.new()
                stripe_mesh.size = Vector3(2.45, 0.014, 0.075) if axis == 0 else Vector3(0.075, 0.014, 2.45)
                stripe.mesh = stripe_mesh
                stripe.position = Vector3(float(segment) * 3.55, 0.012, offset) if axis == 0 else Vector3(offset, 0.012, float(segment) * 3.55)
                stripe.material_override = lane_material
                add_child(stripe)

    var ring_material := StandardMaterial3D.new()
    ring_material.albedo_color = Color(0.025, 0.17, 0.23)
    ring_material.emission_enabled = true
    ring_material.emission = Color(0.008, 0.075, 0.12)
    ring_material.emission_energy_multiplier = 0.20
    ring_material.metallic = 0.16
    ring_material.roughness = 0.52

    for ring_index in range(2):
        var ring_root := Node3D.new()
        ring_root.name = "ContainmentRing_%d" % ring_index
        add_child(ring_root)
        var radius := 3.35 + float(ring_index) * 1.48
        var segment_count := 8
        var segment_length := 1.10 if ring_index == 0 else 1.34
        for segment_index in range(segment_count):
            # Broken arcs read as worn industrial guidance rather than a perfect target reticle.
            # Stagger every second arc so the two rings never form a continuous bullseye.
            if (segment_index + ring_index) % 4 == 1:
                continue
            var angle := TAU * float(segment_index) / float(segment_count) + float(ring_index) * 0.17
            var arc := MeshInstance3D.new()
            arc.name = "Arc_%02d" % segment_index
            var arc_mesh := BoxMesh.new()
            arc_mesh.size = Vector3(segment_length, 0.012, 0.050)
            arc.mesh = arc_mesh
            arc.position = Vector3(cos(angle) * radius, 0.030 + float(ring_index) * 0.003, sin(angle) * radius)
            arc.rotation.y = -angle + PI * 0.5
            arc.material_override = ring_material
            ring_root.add_child(arc)

    var marker_material := StandardMaterial3D.new()
    marker_material.albedo_color = Color(0.035, 0.24, 0.31)
    marker_material.emission_enabled = true
    marker_material.emission = Color(0.01, 0.10, 0.15)
    marker_material.emission_energy_multiplier = 0.18
    marker_material.roughness = 0.62

    for index in range(8):
        var angle := TAU * float(index) / 8.0
        var tick := MeshInstance3D.new()
        tick.name = "ContainmentTick_%02d" % index
        var tick_mesh := BoxMesh.new()
        tick_mesh.size = Vector3(0.58, 0.014, 0.075)
        tick.mesh = tick_mesh
        tick.position = Vector3(cos(angle) * 5.65, 0.018, sin(angle) * 5.65)
        tick.rotation.y = -angle
        tick.material_override = marker_material
        add_child(tick)

func _build_perimeter_street_lights() -> void:
    # Tall authored fixtures restore vertical scale without introducing gameplay collision.
    # Each fixture owns one modest, shadowless pool light to stay inside the mobile light budget.
    var placements := [
        {"position": Vector3(-18.5, 0.0, -12.8), "rotation": 0.42},
        {"position": Vector3(18.5, 0.0, -12.8), "rotation": -0.42},
        {"position": Vector3(-18.5, 0.0, 12.8), "rotation": PI - 0.42},
        {"position": Vector3(18.5, 0.0, 12.8), "rotation": PI + 0.42},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var fixture := DZAssetLibrary.street_lights()
        if fixture == null:
            continue
        fixture.name = "AuthoredStreetLight_%02d" % index
        fixture.add_to_group("environment_vertical_prop")
        fixture.position = placement["position"]
        fixture.rotation.y = float(placement["rotation"])
        fixture.scale = Vector3.ONE * 0.76
        add_child(fixture)

        var pool := OmniLight3D.new()
        pool.name = "StreetLightPool"
        pool.position = Vector3(0.0, 6.05, 2.28)
        pool.light_color = Color(0.42, 0.68, 0.86)
        pool.light_energy = 0.92
        pool.omni_range = 8.0
        pool.shadow_enabled = false
        fixture.add_child(pool)

        var lamp_core := MeshInstance3D.new()
        lamp_core.name = "StreetLightCore"
        var core_mesh := SphereMesh.new()
        core_mesh.radius = 0.12
        core_mesh.height = 0.24
        lamp_core.mesh = core_mesh
        lamp_core.position = Vector3(0.0, 6.18, 2.38)
        var core_material := StandardMaterial3D.new()
        core_material.albedo_color = Color(0.56, 0.82, 1.0)
        core_material.emission_enabled = true
        core_material.emission = Color(0.24, 0.62, 0.92)
        core_material.emission_energy_multiplier = 2.6
        core_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
        lamp_core.material_override = core_material
        fixture.add_child(lamp_core)

func _build_perimeter_beacons() -> void:
    var beacon_material := StandardMaterial3D.new()
    beacon_material.albedo_color = Color(1.0, 0.10, 0.025)
    beacon_material.emission_enabled = true
    beacon_material.emission = Color(1.0, 0.045, 0.01)
    beacon_material.emission_energy_multiplier = 4.0

    for index in range(12):
        var angle := TAU * float(index) / 12.0
        var radius := 27.0
        var beacon := MeshInstance3D.new()
        beacon.name = "PerimeterBeacon_%02d" % index
        var mesh := CylinderMesh.new()
        mesh.top_radius = 0.07
        mesh.bottom_radius = 0.13
        mesh.height = 0.72
        beacon.mesh = mesh
        beacon.position = Vector3(cos(angle) * radius, 0.36, sin(angle) * radius)
        beacon.material_override = beacon_material
        add_child(beacon)

func _build_combat_audio() -> void:
    impact_audio = AudioStreamPlayer.new()
    impact_audio.name = "ImpactAudio"
    impact_audio.bus = "SFX"
    impact_audio.volume_db = -9.0
    add_child(impact_audio)

    boss_audio = AudioStreamPlayer.new()
    boss_audio.name = "BossStinger"
    boss_audio.bus = "SFX"
    boss_audio.volume_db = -6.0
    boss_audio.stream = DZCombatAudio.boss_stinger()
    add_child(boss_audio)

func _play_impact_audio(critical: bool, killed: bool, boss: bool) -> void:
    HAPTICS.pulse(HAPTICS.event_for_impact(critical, killed, boss))
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
    var inside := not camera.is_position_behind(best.global_position) and screen_pos.x >= margin.x and screen_pos.y >= margin.y and screen_pos.x <= viewport_size.x - margin.x and screen_pos.y <= viewport_size.y - margin.y

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
