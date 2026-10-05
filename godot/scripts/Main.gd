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
var impact_fov_pulse := 0.0
var hit_freeze_left := 0.0
var boss_reveal_target: DZEnemy
var boss_reveal_left := 0.0
var current_boss: DZEnemy
var impact_audio: AudioStreamPlayer
var impact_audio_voices: Array[AudioStreamPlayer] = []
var impact_voice_index := 0
var boss_audio: AudioStreamPlayer
var ui_audio_voices: Array[AudioStreamPlayer] = []
var ui_audio_index := 0
var ui_audio_streams := {}
var music_audio: AudioStreamPlayer
var music_pressure_audio: AudioStreamPlayer
var music_duck_tween: Tween
var music_end_tween: Tween
var impact_streams := {}
var enemy_spatial_index := DZSpatialHash.new(4.0)
var last_player_health := -1.0
var run_director := DZRunDirector.new()
var spawn_rng := RandomNumberGenerator.new()
var director_profile: Dictionary = {}
var hud_refresh_clock := 0.0
var threat_indicator_refresh_clock := 0.0
var director_refresh_clock := 0.0
var haptics_enabled := true
var reduced_flashes := false
var camera_shake_enabled := true
var hit_stop_enabled := true
var onboarding_hint_active := true
var onboarding_hint_left := 4.8
var debug_perf_probe_clock := 5.0

const SETTINGS_PATH := "user://deadline-zero-settings.cfg"
const TOUCH_STICK_RADIUS := 90.0
const TOUCH_STICK_DEADZONE := 10.0
const BOSS_REVEAL_DURATION := 1.15
const BOSS_REVEAL_FOCUS := 0.58
const BOSS_INTERVAL := 75.0
const BOSS_RETRY_DELAY := 15.0
const BOSS_REVEAL_FOV_DELTA := 5.5
const CAMERA_BASE_HEIGHT := 12.8
const CAMERA_BASE_TRAIL := 9.15
const CAMERA_BASE_FOV := 46.0
const CAMERA_LOOK_AHEAD_MAX := 1.25
const CAMERA_REVEAL_HEIGHT := 14.0
const CAMERA_REVEAL_TRAIL := 10.4
const HUD_REFRESH_INTERVAL := 0.10
const MUSIC_BASE_DB := -20.0
const MUSIC_DUCK_DB := -27.0
const MUSIC_PRESSURE_BREACH_DB := -48.0
const MUSIC_PRESSURE_BOSS_DB := -11.0
const MUSIC_GAME_OVER_DB := -32.0
const SPAWN_ARENA_HALF_EXTENT := 34.0
const THREAT_INDICATOR_REFRESH_INTERVAL := 0.10
const DIRECTOR_REFRESH_INTERVAL := 0.25
const DEBUG_PERF_PROBE_INTERVAL := 5.0

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
    camera.fov = CAMERA_BASE_FOV
    add_child(camera)
    camera.global_position = Vector3(0.0, CAMERA_BASE_HEIGHT, CAMERA_BASE_TRAIL)
    camera.look_at(Vector3(0.0, 0.6, 0.0), Vector3.UP)

    hud = DZHud.new()
    add_child(hud)
    hud.upgrade_chosen.connect(_on_upgrade_chosen)
    hud.restart_requested.connect(_on_restart_requested)
    hud.pause_requested.connect(_on_pause_requested)
    hud.resume_requested.connect(_on_resume_requested)
    hud.master_volume_changed.connect(_on_master_volume_changed)
    hud.sfx_volume_changed.connect(_on_sfx_volume_changed)
    hud.music_volume_changed.connect(_on_music_volume_changed)
    hud.haptics_changed.connect(_on_haptics_changed)
    hud.reduced_flashes_changed.connect(_on_reduced_flashes_changed)
    hud.camera_shake_changed.connect(_on_camera_shake_changed)
    hud.hit_stop_changed.connect(_on_hit_stop_changed)
    _load_audio_settings()
    last_player_health = player.health
    hud.set_health(player.health, player.max_health)
    hud.set_progress(xp, xp_next, level, kills, elapsed, get_tree().get_node_count_in_group("enemies"))
    hud.show_onboarding_hint()
    _build_combat_audio()

    for opening_kind in run_director.opening_roster():
        _spawn_enemy(String(opening_kind))

func _process(delta: float) -> void:
    if OS.is_debug_build():
        debug_perf_probe_clock = maxf(0.0, debug_perf_probe_clock - maxf(delta, 0.0))
        if debug_perf_probe_clock <= 0.0:
            debug_perf_probe_clock = DEBUG_PERF_PROBE_INTERVAL
            _emit_debug_perf_probe()

    if hit_freeze_left > 0.0:
        var real_delta := DZCombatFeel.unscaled_delta(delta, Engine.time_scale)
        hit_freeze_left = maxf(0.0, hit_freeze_left - real_delta)
        Engine.time_scale = 0.12 if hit_freeze_left > 0.0 else 1.0
    else:
        Engine.time_scale = 1.0

    camera_kick = move_toward(camera_kick, 0.0, delta * 0.95)
    camera_kick_phase += delta * 38.0
    impact_fov_pulse = move_toward(impact_fov_pulse, 0.0, delta * 4.8)

    if onboarding_hint_active and not game_over:
        onboarding_hint_left = maxf(0.0, onboarding_hint_left - maxf(delta, 0.0))
        if onboarding_hint_left <= 0.0 or (player != null and player.velocity.length_squared() > 0.16):
            _dismiss_onboarding_hint()

    if player and is_instance_valid(player):
        var movement_look_ahead := _camera_motion_look_ahead(player.velocity)
        var focus_point := player.global_position + Vector3(0.0, 0.65, 0.0) + movement_look_ahead * 0.72
        var desired := player.global_position + Vector3(0.0, CAMERA_BASE_HEIGHT, CAMERA_BASE_TRAIL) + movement_look_ahead * 0.42
        var target_fov := CAMERA_BASE_FOV

        if boss_reveal_left > 0.0 and boss_reveal_target != null and is_instance_valid(boss_reveal_target) and not boss_reveal_target.dead:
            boss_reveal_left = max(0.0, boss_reveal_left - delta)
            var normalized: float = clampf(boss_reveal_left / BOSS_REVEAL_DURATION, 0.0, 1.0)
            var envelope: float = sin((1.0 - normalized) * PI)
            var midpoint: Vector3 = player.global_position.lerp(boss_reveal_target.global_position, BOSS_REVEAL_FOCUS)
            focus_point = focus_point.lerp(midpoint + Vector3(0.0, 0.78, 0.0), envelope)
            desired = desired.lerp(midpoint + Vector3(0.0, CAMERA_REVEAL_HEIGHT, CAMERA_REVEAL_TRAIL), envelope * 0.72)
            target_fov = CAMERA_BASE_FOV + BOSS_REVEAL_FOV_DELTA * envelope
        else:
            boss_reveal_left = 0.0
            boss_reveal_target = null

        target_fov += impact_fov_pulse

        var kick_offset := Vector3(sin(camera_kick_phase), 0.0, cos(camera_kick_phase * 1.27)) * camera_kick
        camera.global_position = camera.global_position.lerp(desired + kick_offset, 1.0 - exp(-delta * 4.5))
        camera.fov = lerpf(camera.fov, target_fov, 1.0 - exp(-delta * 5.5))
        camera.look_at(focus_point, Vector3.UP)
        threat_indicator_refresh_clock -= delta
        if threat_indicator_refresh_clock <= 0.0:
            threat_indicator_refresh_clock = THREAT_INDICATOR_REFRESH_INTERVAL
            _update_offscreen_threat_indicator()

func _camera_motion_look_ahead(velocity: Vector3) -> Vector3:
    var planar := Vector3(velocity.x, 0.0, velocity.z)
    var speed := planar.length()
    if speed <= 0.05:
        return Vector3.ZERO
    return planar / speed * minf(speed * 0.24, CAMERA_LOOK_AHEAD_MAX)

func _performance_snapshot() -> Dictionary:
    return {
        "fps": float(Engine.get_frames_per_second()),
        "memory_bytes": int(Performance.get_monitor(Performance.MEMORY_STATIC)),
        "enemies": get_tree().get_node_count_in_group("enemies"),
        "projectiles": get_tree().get_node_count_in_group("player_projectiles"),
        "hostile_projectiles": get_tree().get_node_count_in_group("hostile_projectiles"),
        "xp_orbs": get_tree().get_node_count_in_group("xp_orbs"),
        "elapsed": elapsed
    }

func _emit_debug_perf_probe() -> void:
    var snapshot := _performance_snapshot()
    print(
        "DZ_PERF fps=%.1f mem_mb=%.1f enemies=%d projectiles=%d hostile=%d xp=%d elapsed=%.1f"
        % [
            float(snapshot["fps"]),
            float(snapshot["memory_bytes"]) / (1024.0 * 1024.0),
            int(snapshot["enemies"]),
            int(snapshot["projectiles"]),
            int(snapshot["hostile_projectiles"]),
            int(snapshot["xp_orbs"]),
            float(snapshot["elapsed"])
        ]
    )

func _physics_process(delta: float) -> void:
    if game_over:
        return
    elapsed += delta
    director_refresh_clock -= delta
    if director_refresh_clock <= 0.0:
        director_refresh_clock = DIRECTOR_REFRESH_INTERVAL
        director_profile = run_director.profile(elapsed, level)
        max_enemies = int(director_profile["max_enemies"])
    boss_banner_timer = max(0.0, boss_banner_timer - delta)
    _update_music_pressure(delta)
    if elapsed >= next_boss_time:
        if _has_active_boss():
            next_boss_time = elapsed + BOSS_RETRY_DELAY
        else:
            _spawn_enemy("boss")
            boss_banner_timer = 3.2
            next_boss_time = elapsed + BOSS_INTERVAL

    spawn_clock -= delta
    if spawn_clock <= 0.0:
        var batch := int(director_profile["batch_size"])
        for i in range(batch):
            _spawn_enemy()
        spawn_clock = float(director_profile["spawn_interval"])
    var enemies := get_tree().get_nodes_in_group("enemies")
    enemy_spatial_index.rebuild(enemies)
    hud_refresh_clock -= delta
    if hud_refresh_clock <= 0.0:
        hud_refresh_clock = HUD_REFRESH_INTERVAL
        hud.set_progress(xp, xp_next, level, kills, elapsed, enemies.size())
        hud.set_wave(_wave_name())

func _dismiss_onboarding_hint() -> void:
    if not onboarding_hint_active:
        return
    onboarding_hint_active = false
    onboarding_hint_left = 0.0
    if hud != null:
        hud.hide_onboarding_hint()

func _unhandled_input(event: InputEvent) -> void:
    if player == null or game_over or not pending_upgrades.is_empty() or get_tree().paused:
        return
    if event is InputEventScreenTouch:
        var touch := event as InputEventScreenTouch
        if touch.pressed and touch.position.x < get_viewport().get_visible_rect().size.x * 0.55 and touch_id < 0:
            touch_id = touch.index
            touch_origin = touch.position
            if hud != null:
                hud.show_touch_stick(touch_origin)
        elif not touch.pressed and touch.index == touch_id:
            touch_id = -1
            player.set_touch_move(Vector2.ZERO)
            if hud != null:
                hud.hide_touch_stick()
    elif event is InputEventScreenDrag:
        var drag := event as InputEventScreenDrag
        if drag.index == touch_id:
            var vector := _touch_input_vector(drag.position)
            player.set_touch_move(vector)
            if vector.length_squared() > 0.04:
                _dismiss_onboarding_hint()
            if hud != null:
                hud.update_touch_stick(touch_origin, vector)

func _touch_input_vector(current_position: Vector2) -> Vector2:
    var delta := current_position - touch_origin
    var distance := delta.length()
    if distance <= TOUCH_STICK_DEADZONE:
        return Vector2.ZERO
    var strength := clampf(
        (distance - TOUCH_STICK_DEADZONE) / maxf(TOUCH_STICK_RADIUS - TOUCH_STICK_DEADZONE, 0.001),
        0.0,
        1.0
    )
    return delta.normalized() * strength

func _music_pressure_target_db() -> float:
    if _has_active_boss():
        return MUSIC_PRESSURE_BOSS_DB
    match String(director_profile.get("phase", "BREACH")):
        "SURGE":
            return -31.0
        "PRESSURE":
            return -24.0
        "OVERRUN":
            return -18.0
        "EXTINCTION":
            return -13.0
        _:
            return MUSIC_PRESSURE_BREACH_DB

func _update_music_pressure(delta: float) -> void:
    if music_pressure_audio == null or music_pressure_audio.stream == null:
        return
    music_pressure_audio.volume_db = move_toward(
        music_pressure_audio.volume_db,
        _music_pressure_target_db(),
        maxf(delta, 0.0) * 8.0
    )

func _has_active_boss() -> bool:
    return current_boss != null and is_instance_valid(current_boss) and not current_boss.dead

func query_enemies_near(position: Vector3, radius: float) -> Array:
    return enemy_spatial_index.query(position, radius)

func _clamp_spawn_position(position: Vector3) -> Vector3:
    return Vector3(
        clampf(position.x, -SPAWN_ARENA_HALF_EXTENT, SPAWN_ARENA_HALF_EXTENT),
        position.y,
        clampf(position.z, -SPAWN_ARENA_HALF_EXTENT, SPAWN_ARENA_HALF_EXTENT)
    )

func _spawn_position_around_player(radius: float) -> Vector3:
    var safe_radius := clampf(radius, 12.0, 18.0)
    for _attempt in range(8):
        var angle := spawn_rng.randf() * TAU
        var candidate := player.global_position + Vector3(cos(angle) * safe_radius, 0.0, sin(angle) * safe_radius)
        if absf(candidate.x) <= SPAWN_ARENA_HALF_EXTENT and absf(candidate.z) <= SPAWN_ARENA_HALF_EXTENT:
            return candidate

    var toward_center := -player.global_position
    toward_center.y = 0.0
    if toward_center.length_squared() < 0.001:
        toward_center = Vector3.FORWARD
    return _clamp_spawn_position(player.global_position + toward_center.normalized() * safe_radius)

func _spawn_enemy(forced_kind: String = "") -> void:
    if player == null or game_over:
        return
    if forced_kind != "boss" and get_tree().get_nodes_in_group("enemies").size() >= max_enemies:
        return
    var radius := spawn_rng.randf_range(12.0, 18.0)
    var pos := _spawn_position_around_player(radius)
    var kind := forced_kind if not forced_kind.is_empty() else run_director.choose_enemy(elapsed, level, spawn_rng)
    var difficulty := float(director_profile.get("difficulty", 1.0))
    var enemy := DZEnemy.new()
    enemy.configure(kind, difficulty, player)
    enemy.died.connect(_on_enemy_died)
    enemy.impact.connect(_on_enemy_impact)
    add_child(enemy)
    enemy.global_position = pos
    if kind == "boss":
        current_boss = enemy
        _play_boss_stinger()
        boss_reveal_target = enemy
        boss_reveal_left = BOSS_REVEAL_DURATION
        enemy.health_changed.connect(_on_boss_health_changed)
        enemy.boss_phase_changed.connect(_on_boss_phase_changed)
        hud.show_boss("REVENANT PRIME", enemy.max_health)

func _on_boss_health_changed(current: float, maximum: float) -> void:
    if hud:
        hud.set_boss_health(current, maximum)
    if current <= 0.0:
        next_boss_time = maxf(next_boss_time, elapsed + BOSS_RETRY_DELAY)

func _on_boss_phase_changed(phase: int, at: Vector3) -> void:
    if hud != null:
        hud.pulse_boss_phase(phase)
    var fx := ImpactFx.new()
    fx.name = "BossPhaseBurst_%d" % phase
    fx.color = Color(1.0, 0.24, 0.035) if phase == 2 else Color(1.0, 0.055, 0.12)
    fx.scale_boost = 1.55 if phase == 2 else 1.95
    add_child(fx)
    fx.global_position = at
    if camera_shake_enabled:
        impact_fov_pulse = maxf(impact_fov_pulse, 0.82 if phase == 2 else 1.15)
        camera_kick = maxf(camera_kick, 0.095 if phase == 2 else 0.125)

func _on_enemy_impact(at: Vector3, critical: bool, killed: bool, boss: bool) -> void:
    if hit_stop_enabled:
        hit_freeze_left = max(hit_freeze_left, DZCombatFeel.hit_freeze_seconds(critical, killed, boss))
    if camera_shake_enabled:
        camera_kick = max(camera_kick, DZCombatFeel.camera_kick(critical, killed, boss))
        impact_fov_pulse = maxf(impact_fov_pulse, DZCombatFeel.impact_fov_pulse(critical, killed, boss))
    if hud:
        hud.show_impact_flash(critical, killed, boss)
    if killed:
        _spawn_kill_confirmation_fx(at, boss)
    _play_impact_audio(critical, killed, boss)

func _spawn_kill_confirmation_fx(at: Vector3, boss: bool) -> void:
    var fx := ImpactFx.new()
    fx.name = "BossKillBurst" if boss else "KillBurst"
    fx.color = Color(1.0, 0.18, 0.035) if boss else Color(1.0, 0.50, 0.10)
    fx.scale_boost = 2.15 if boss else 1.42
    add_child(fx)
    fx.global_position = at

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
    if hud != null:
        hud.pulse_xp_collection(amount)
    _play_ui_audio("xp_collect", clampf(0.98 + float(mini(amount, 10)) * 0.018, 0.98, 1.16))
    _try_offer_banked_level_up()

func _try_offer_banked_level_up() -> bool:
    if game_over or not pending_upgrades.is_empty() or xp < xp_next:
        return false
    xp -= xp_next
    level += 1
    director_refresh_clock = 0.0
    _play_ui_audio("level_up")
    xp_next = int(round(float(xp_next) * 1.24 + 4.0))
    _offer_upgrade()
    return true

func _offer_upgrade() -> void:
    _clear_hit_freeze()
    pending_upgrades.clear()
    touch_id = -1
    if player != null and is_instance_valid(player):
        player.set_touch_move(Vector2.ZERO)
        player._clear_player_marker_pressure()
    if hud != null:
        hud.hide_touch_stick()
    var available: Array = []
    for upgrade in UPGRADE_POOL:
        var id := String(upgrade["id"])
        if player == null or player.can_apply_upgrade(id):
            available.append(upgrade.duplicate(true))
    available.shuffle()
    for i in range(mini(3, available.size())):
        pending_upgrades.append(available[i])
    _dismiss_onboarding_hint()
    hud.show_upgrade(pending_upgrades)
    get_tree().paused = true

func _on_upgrade_chosen(index: int) -> void:
    if index < 0 or index >= pending_upgrades.size():
        return
    player.apply_upgrade(pending_upgrades[index]["id"])
    _play_ui_audio("upgrade_confirm")
    pending_upgrades.clear()
    hud.hide_upgrade()
    if _try_offer_banked_level_up():
        return
    get_tree().paused = false

func _on_health_changed(current: float, maximum: float) -> void:
    if last_player_health >= 0.0 and current < last_player_health:
        var damage_taken := last_player_health - current
        if camera_shake_enabled:
            camera_kick = max(camera_kick, DZCombatFeel.damage_received_camera_kick(damage_taken, maximum))
        if hud:
            hud.pulse_damage_screen()
    if hud:
        hud.set_health(current, maximum)
    last_player_health = current

func _ensure_audio_buses() -> void:
    if AudioServer.get_bus_index("SFX") < 0:
        AudioServer.add_bus()
        AudioServer.set_bus_name(AudioServer.bus_count - 1, "SFX")
    if AudioServer.get_bus_index("Music") < 0:
        AudioServer.add_bus()
        AudioServer.set_bus_name(AudioServer.bus_count - 1, "Music")

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
    var music := float(settings.get("music_volume", 0.62))
    haptics_enabled = bool(settings.get("haptics_enabled", true))
    reduced_flashes = bool(settings.get("reduced_flashes", false))
    camera_shake_enabled = bool(settings.get("camera_shake_enabled", true))
    hit_stop_enabled = bool(settings.get("hit_stop_enabled", true))
    if hud != null:
        hud.master_volume.set_value_no_signal(master)
        hud.sfx_volume.set_value_no_signal(sfx)
        hud.music_volume.set_value_no_signal(music)
        hud.haptics_toggle.set_pressed_no_signal(haptics_enabled)
        hud.set_reduced_flashes(reduced_flashes)
        hud.camera_shake_toggle.set_pressed_no_signal(camera_shake_enabled)
        hud.hit_stop_toggle.set_pressed_no_signal(hit_stop_enabled)
    if player != null:
        player.set_reduced_flashes(reduced_flashes)
    _set_bus_linear_volume("Master", master)
    _set_bus_linear_volume("SFX", sfx)
    _set_bus_linear_volume("Music", music)

func _save_audio_settings(path := SETTINGS_PATH) -> void:
    var master := hud.master_volume.value if hud != null else 0.85
    var sfx := hud.sfx_volume.value if hud != null else 0.90
    var music := hud.music_volume.value if hud != null else 0.62
    DZGameSettings.save(path, {
        "master_volume": master,
        "sfx_volume": sfx,
        "music_volume": music,
        "haptics_enabled": haptics_enabled,
        "reduced_flashes": reduced_flashes,
        "camera_shake_enabled": camera_shake_enabled,
        "hit_stop_enabled": hit_stop_enabled
    })

func _on_master_volume_changed(value: float) -> void:
    _set_bus_linear_volume("Master", value)
    _save_audio_settings()

func _on_sfx_volume_changed(value: float) -> void:
    _set_bus_linear_volume("SFX", value)
    _save_audio_settings()

func _on_music_volume_changed(value: float) -> void:
    _set_bus_linear_volume("Music", value)
    _save_audio_settings()

func _on_haptics_changed(enabled: bool) -> void:
    haptics_enabled = enabled
    _save_audio_settings()

func _on_reduced_flashes_changed(enabled: bool) -> void:
    reduced_flashes = enabled
    if hud != null:
        hud.set_reduced_flashes(enabled)
    if player != null:
        player.set_reduced_flashes(enabled)
    _save_audio_settings()

func _on_camera_shake_changed(enabled: bool) -> void:
    camera_shake_enabled = enabled
    if not enabled:
        camera_kick = 0.0
        impact_fov_pulse = 0.0
    _save_audio_settings()

func _on_hit_stop_changed(enabled: bool) -> void:
    hit_stop_enabled = enabled
    if not enabled:
        _clear_hit_freeze()
    _save_audio_settings()

func _clear_hit_freeze() -> void:
    hit_freeze_left = 0.0
    Engine.time_scale = 1.0

func _on_pause_requested() -> void:
    if game_over or not pending_upgrades.is_empty():
        return
    _clear_hit_freeze()
    if player != null and is_instance_valid(player):
        player._clear_player_marker_pressure()
        player.set_touch_move(Vector2.ZERO)
    touch_id = -1
    if hud != null:
        hud.hide_touch_stick()
        _dismiss_onboarding_hint()
    hud.show_pause_settings()
    _play_ui_audio("pause_toggle", 0.92)
    get_tree().paused = true

func _on_resume_requested() -> void:
    hud.hide_pause_settings()
    if not game_over and pending_upgrades.is_empty():
        get_tree().paused = false
        _play_ui_audio("pause_toggle", 1.08)

func _on_player_died() -> void:
    _clear_hit_freeze()
    camera_kick = 0.0
    impact_fov_pulse = 0.0
    boss_reveal_left = 0.0
    boss_reveal_target = null
    game_over = true
    _play_ui_audio("game_over")
    touch_id = -1
    if hud != null:
        hud.hide_touch_stick()
    _fade_music_for_run_end()
    _freeze_combat()
    if hud:
        hud.show_game_over(kills, level, elapsed)

func _fade_music_for_run_end() -> void:
    if boss_audio != null:
        boss_audio.stop()
    if music_duck_tween != null and music_duck_tween.is_valid():
        music_duck_tween.kill()
    if music_end_tween != null and music_end_tween.is_valid():
        music_end_tween.kill()

    music_end_tween = create_tween()
    music_end_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    music_end_tween.set_parallel(true)
    if music_audio != null and music_audio.playing:
        music_end_tween.tween_property(music_audio, "volume_db", MUSIC_GAME_OVER_DB, 0.55).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if music_pressure_audio != null and music_pressure_audio.playing:
        music_end_tween.tween_property(music_pressure_audio, "volume_db", MUSIC_PRESSURE_BREACH_DB, 0.36).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

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
    for node in get_tree().get_nodes_in_group("xp_orbs"):
        var orb := node as DZXpOrb
        if orb != null:
            orb.set_combat_enabled(false)

func _on_restart_requested() -> void:
    _clear_hit_freeze()
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
    env.adjustment_enabled = true
    env.adjustment_brightness = 1.02
    env.adjustment_contrast = 1.08
    env.adjustment_saturation = 1.06
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
    floor.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    floor.material_override = _build_quarantine_floor_material()
    add_child(floor)

    _build_floor_panels()
    _build_floor_service_grates()
    _build_midfield_inspection_panels()
    _build_service_pylons()
    _build_floor_wear()
    _build_floor_seams()
    _build_light_pool_decals()
    _build_containment_lanes()
    _build_perimeter_bulkheads()
    _build_authored_barrier_clusters()
    _build_authored_world_dressing()
    _build_perimeter_street_lights()
    _build_perimeter_beacons()

func _build_quarantine_floor_material() -> ShaderMaterial:
    # One lightweight procedural material gives the broad arena plane real surface hierarchy
    # without shipping another texture or adding draw calls. Geometry overlays still carry
    # authored seams, grates, wear and hazard identity above this subtle base.
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode diffuse_burley, specular_schlick_ggx;

uniform vec3 base_tone = vec3(0.040, 0.052, 0.060);

float hash21(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

void fragment() {
    vec2 panel_uv = UV * 18.0;
    vec2 panel_cell = floor(panel_uv);
    vec2 local_uv = fract(panel_uv);
    float panel_edge_distance = min(
        min(local_uv.x, 1.0 - local_uv.x),
        min(local_uv.y, 1.0 - local_uv.y)
    );
    float panel_edge = 1.0 - smoothstep(0.0, 0.030, panel_edge_distance);

    float panel_variation = hash21(panel_cell);
    float micro_variation = hash21(floor(UV * 240.0));
    float macro_a = hash21(floor(UV * 11.0) + vec2(17.0, 9.0));
    float macro_b = hash21(floor(UV * 23.0) + vec2(7.0, 19.0));
    float macro_variation = macro_a * 0.68 + macro_b * 0.32;
    float radial = distance(UV, vec2(0.5));
    float center_lift = 1.0 - smoothstep(0.12, 0.72, radial);
    float perimeter_heat = smoothstep(0.34, 0.70, radial);
    float grime = smoothstep(0.70, 0.96, macro_variation + micro_variation * 0.10);

    vec3 tone = base_tone;
    tone *= 0.94 + panel_variation * 0.075;
    tone *= 0.965 + micro_variation * 0.055;
    tone *= 1.0 - panel_edge * 0.055;
    tone *= 1.0 - grime * 0.055;
    tone += vec3(0.005, 0.011, 0.015) * center_lift;
    tone += vec3(0.010, 0.0025, 0.0010) * perimeter_heat;

    ALBEDO = tone;
    ROUGHNESS = clamp(0.83 + (micro_variation - 0.5) * 0.10 + panel_edge * 0.05 + grime * 0.04, 0.75, 0.97);
    METALLIC = 0.055 + panel_variation * 0.045 - grime * 0.012;
}
"""
    var material := ShaderMaterial.new()
    material.shader = shader
    return material

func _build_light_pool_decals() -> void:
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_add, depth_draw_never, cull_disabled;

uniform vec4 tint : source_color = vec4(0.08, 0.45, 0.70, 0.22);

void fragment() {
    vec2 p = UV * 2.0 - 1.0;
    float d = length(p);
    float falloff = pow(1.0 - smoothstep(0.08, 1.0, d), 2.2);
    ALBEDO = tint.rgb;
    EMISSION = tint.rgb * falloff * 1.35;
    ALPHA = falloff * tint.a;
}
"""

    var cool_material := ShaderMaterial.new()
    cool_material.shader = shader
    cool_material.set_shader_parameter("tint", Color(0.04, 0.46, 0.78, 0.22))

    var warm_material := ShaderMaterial.new()
    warm_material.shader = shader
    warm_material.set_shader_parameter("tint", Color(1.0, 0.18, 0.035, 0.20))

    var mesh := QuadMesh.new()
    mesh.size = Vector2(5.8, 3.4)

    var placements := [
        {"position": Vector3(-7.4, 0.020, -3.6), "rotation": 14.0, "warm": false},
        {"position": Vector3( 7.6, 0.020,  3.5), "rotation": -18.0, "warm": false},
        {"position": Vector3(-13.6, 0.020, -7.8), "rotation": -10.0, "warm": true},
        {"position": Vector3( 13.8, 0.020, -7.5), "rotation": 12.0, "warm": true},
        {"position": Vector3(-13.4, 0.020,  8.0), "rotation": 16.0, "warm": true},
        {"position": Vector3( 13.5, 0.020,  7.9), "rotation": -14.0, "warm": true},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var pool := MeshInstance3D.new()
        pool.name = "ArenaLightPool_%02d" % index
        pool.mesh = mesh
        pool.position = placement["position"]
        pool.rotation_degrees = Vector3(-90.0, float(placement["rotation"]), 0.0)
        pool.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        pool.material_override = warm_material if bool(placement["warm"]) else cool_material
        add_child(pool)

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

    # The Quaternius asset named street-straight-crack1 is an entire 8x8 m street tile,
    # not a crack decal. Repeating it as ground damage created the bright white road strips that
    # dominated the combat frame. Build actual low-profile crack geometry instead.
    var crack_material := StandardMaterial3D.new()
    crack_material.albedo_color = Color(0.008, 0.012, 0.014)
    crack_material.metallic = 0.04
    crack_material.roughness = 1.0

    var crack_edge_material := StandardMaterial3D.new()
    crack_edge_material.albedo_color = Color(0.065, 0.034, 0.018, 0.52)
    crack_edge_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    crack_edge_material.roughness = 1.0

    var crack_positions := [
        Vector3(-12.0, 0.012, -5.8), Vector3(12.2, 0.012, 5.5),
        Vector3(-4.5, 0.012, 11.8), Vector3(4.8, 0.012, -11.6),
        Vector3(-17.0, 0.012, 2.0), Vector3(17.2, 0.012, -2.2),
        Vector3(-8.0, 0.012, 17.0), Vector3(8.0, 0.012, -17.0),
    ]
    for index in range(crack_positions.size()):
        var crack_root := Node3D.new()
        crack_root.name = "StreetDamage_%02d" % index
        crack_root.add_to_group("environment_ground_detail")
        crack_root.position = crack_positions[index]
        crack_root.rotation.y = float((index * 53) % 360) * PI / 180.0
        add_child(crack_root)

        var segments := [
            {"offset": Vector3(-0.28, 0.0, -0.03), "length": 0.78, "angle": 0.08},
            {"offset": Vector3(0.28, 0.0, 0.08), "length": 0.60, "angle": 0.54},
            {"offset": Vector3(0.48, 0.0, -0.18), "length": 0.42, "angle": -0.42},
            {"offset": Vector3(-0.08, 0.0, 0.25), "length": 0.36, "angle": 1.02},
        ]
        for segment_index in range(segments.size()):
            var segment_data: Dictionary = segments[segment_index]
            var segment := MeshInstance3D.new()
            segment.name = "CrackSegment_%d" % segment_index
            var segment_mesh := BoxMesh.new()
            segment_mesh.size = Vector3(float(segment_data["length"]), 0.006, 0.030)
            segment.mesh = segment_mesh
            segment.position = segment_data["offset"] + Vector3(0.0, 0.006, 0.0)
            segment.rotation.y = float(segment_data["angle"])
            segment.material_override = crack_material
            crack_root.add_child(segment)

            if segment_index < 2:
                var edge := MeshInstance3D.new()
                edge.name = "CrackEdge_%d" % segment_index
                var edge_mesh := BoxMesh.new()
                edge_mesh.size = Vector3(float(segment_data["length"]) * 0.72, 0.004, 0.010)
                edge.mesh = edge_mesh
                edge.position = Vector3(0.0, 0.005, 0.026 if segment_index == 0 else -0.026)
                edge.material_override = crack_edge_material
                segment.add_child(edge)

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

    var boundary_material := StandardMaterial3D.new()
    boundary_material.albedo_color = Color(0.50, 0.12, 0.018)
    boundary_material.emission_enabled = true
    boundary_material.emission = Color(0.18, 0.025, 0.003)
    boundary_material.emission_energy_multiplier = 0.22
    boundary_material.roughness = 0.72
    boundary_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var horizontal_boundary_mesh := BoxMesh.new()
    horizontal_boundary_mesh.size = Vector3(3.25, 0.010, 0.070)
    var vertical_boundary_mesh := BoxMesh.new()
    vertical_boundary_mesh.size = Vector3(0.070, 0.010, 3.25)

    for axis in range(2):
        for side in [-1.0, 1.0]:
            for segment in range(-5, 6):
                var boundary := MeshInstance3D.new()
                boundary.name = "ArenaBoundary_%d_%d_%d" % [axis, int(side), segment]
                boundary.mesh = horizontal_boundary_mesh if axis == 0 else vertical_boundary_mesh
                boundary.position = Vector3(float(segment) * 5.35, 0.016, side * DZPlayer.ARENA_HALF_EXTENT) if axis == 0 else Vector3(side * DZPlayer.ARENA_HALF_EXTENT, 0.016, float(segment) * 5.35)
                boundary.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
                boundary.material_override = boundary_material
                add_child(boundary)

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
    # Keep the licensed authored fixture in the production scene, but outside normal phone
    # framing: its tall dual-arm silhouette reads as bright geometry from the gameplay camera.
    # Compact procedural masts carry the visible perimeter-light identity instead.
    var authored_placements := [
        {"position": Vector3(-28.0, 0.0, -21.0), "rotation": 0.42},
        {"position": Vector3(28.0, 0.0, -21.0), "rotation": -0.42},
        {"position": Vector3(-28.0, 0.0, 21.0), "rotation": PI - 0.42},
        {"position": Vector3(28.0, 0.0, 21.0), "rotation": PI + 0.42},
    ]

    for index in range(authored_placements.size()):
        var placement: Dictionary = authored_placements[index]
        var fixture := DZAssetLibrary.street_lights()
        if fixture == null:
            continue
        fixture.name = "AuthoredStreetLight_%02d" % index
        fixture.add_to_group("environment_vertical_prop")
        fixture.position = placement["position"]
        fixture.rotation.y = float(placement["rotation"])
        fixture.scale = Vector3.ONE * 0.36
        add_child(fixture)

        var pool := OmniLight3D.new()
        pool.name = "StreetLightPool"
        pool.position = Vector3(0.0, 6.05, 2.28)
        pool.light_color = Color(0.32, 0.56, 0.74)
        pool.light_energy = 0.48
        pool.omni_range = 6.0
        pool.shadow_enabled = false
        fixture.add_child(pool)

    var pole_material := StandardMaterial3D.new()
    pole_material.albedo_color = Color(0.018, 0.030, 0.036)
    pole_material.metallic = 0.74
    pole_material.roughness = 0.40

    var collar_material := StandardMaterial3D.new()
    collar_material.albedo_color = Color(0.52, 0.12, 0.015)
    collar_material.emission_enabled = true
    collar_material.emission = Color(0.20, 0.025, 0.003)
    collar_material.emission_energy_multiplier = 0.28
    collar_material.roughness = 0.58

    var lamp_material := StandardMaterial3D.new()
    lamp_material.albedo_color = Color(0.30, 0.72, 0.92)
    lamp_material.emission_enabled = true
    lamp_material.emission = Color(0.08, 0.50, 0.82)
    lamp_material.emission_energy_multiplier = 2.25
    lamp_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var mast_placements := [
        {"position": Vector3(-17.8, 0.0, -12.1), "rotation": 0.58},
        {"position": Vector3(17.8, 0.0, -12.1), "rotation": -0.58},
        {"position": Vector3(-17.8, 0.0, 12.1), "rotation": PI - 0.58},
        {"position": Vector3(17.8, 0.0, 12.1), "rotation": PI + 0.58},
    ]

    for index in range(mast_placements.size()):
        var placement: Dictionary = mast_placements[index]
        var mast := Node3D.new()
        mast.name = "QuarantineMast_%02d" % index
        mast.position = placement["position"]
        mast.rotation.y = float(placement["rotation"])
        add_child(mast)

        var base := MeshInstance3D.new()
        base.name = "Base"
        var base_mesh := CylinderMesh.new()
        base_mesh.top_radius = 0.30
        base_mesh.bottom_radius = 0.36
        base_mesh.height = 0.16
        base.mesh = base_mesh
        base.position.y = 0.08
        base.material_override = pole_material
        mast.add_child(base)

        var pole := MeshInstance3D.new()
        pole.name = "Pole"
        var pole_mesh := CylinderMesh.new()
        pole_mesh.top_radius = 0.075
        pole_mesh.bottom_radius = 0.095
        pole_mesh.height = 3.25
        pole.mesh = pole_mesh
        pole.position.y = 1.70
        pole.material_override = pole_material
        mast.add_child(pole)

        var collar := MeshInstance3D.new()
        collar.name = "HazardCollar"
        var collar_mesh := CylinderMesh.new()
        collar_mesh.top_radius = 0.115
        collar_mesh.bottom_radius = 0.115
        collar_mesh.height = 0.11
        collar.mesh = collar_mesh
        collar.position.y = 0.62
        collar.material_override = collar_material
        mast.add_child(collar)

        var arm := MeshInstance3D.new()
        arm.name = "Arm"
        var arm_mesh := BoxMesh.new()
        arm_mesh.size = Vector3(0.62, 0.085, 0.10)
        arm.mesh = arm_mesh
        arm.position = Vector3(0.0, 3.28, -0.25)
        arm.material_override = pole_material
        mast.add_child(arm)

        var lamp := MeshInstance3D.new()
        lamp.name = "Lamp"
        var lamp_mesh := BoxMesh.new()
        lamp_mesh.size = Vector3(0.34, 0.10, 0.16)
        lamp.mesh = lamp_mesh
        lamp.position = Vector3(0.0, 3.24, -0.55)
        lamp.material_override = lamp_material
        mast.add_child(lamp)

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
    impact_audio_voices.clear()
    for voice_index in range(4):
        var voice := AudioStreamPlayer.new()
        voice.name = "ImpactAudio" if voice_index == 0 else "ImpactAudio_%d" % voice_index
        voice.bus = "SFX"
        voice.volume_db = -11.0
        add_child(voice)
        impact_audio_voices.append(voice)
    impact_audio = impact_audio_voices[0]

    ui_audio_voices.clear()
    for voice_index in range(2):
        var ui_voice := AudioStreamPlayer.new()
        ui_voice.name = "UiAudio" if voice_index == 0 else "UiAudio_%d" % voice_index
        ui_voice.bus = "SFX"
        ui_voice.volume_db = -8.5
        ui_voice.process_mode = Node.PROCESS_MODE_ALWAYS
        add_child(ui_voice)
        ui_audio_voices.append(ui_voice)

    boss_audio = AudioStreamPlayer.new()
    boss_audio.name = "BossStinger"
    boss_audio.bus = "SFX"
    boss_audio.volume_db = -6.0
    boss_audio.stream = DZCombatAudio.boss_stinger()
    add_child(boss_audio)

    music_audio = AudioStreamPlayer.new()
    music_audio.name = "RunMusic"
    music_audio.bus = "Music"
    music_audio.volume_db = MUSIC_BASE_DB
    music_audio.stream = DZCombatAudio.run_music_stream()
    add_child(music_audio)
    if music_audio.stream != null:
        music_audio.play()

    music_pressure_audio = AudioStreamPlayer.new()
    music_pressure_audio.name = "PressureMusic"
    music_pressure_audio.bus = "Music"
    music_pressure_audio.volume_db = MUSIC_PRESSURE_BREACH_DB
    music_pressure_audio.stream = DZCombatAudio.pressure_music_stream()
    add_child(music_pressure_audio)
    if music_pressure_audio.stream != null:
        music_pressure_audio.play()

func _play_ui_audio(key: String, pitch_scale := 1.0) -> void:
    if ui_audio_voices.is_empty():
        return
    if not ui_audio_streams.has(key):
        ui_audio_streams[key] = DZCombatAudio.ui_stream(key)
    var stream := ui_audio_streams.get(key) as AudioStream
    if stream == null:
        return
    var voice := ui_audio_voices[ui_audio_index % ui_audio_voices.size()]
    ui_audio_index = (ui_audio_index + 1) % ui_audio_voices.size()
    voice.stream = stream
    voice.pitch_scale = pitch_scale
    voice.play()

func _play_impact_audio(critical: bool, killed: bool, boss: bool) -> void:
    if haptics_enabled:
        HAPTICS.pulse(HAPTICS.event_for_impact(critical, killed, boss))
    if impact_audio_voices.is_empty():
        return
    var key := "boss" if boss else ("kill" if killed else ("critical" if critical else "hit"))
    if not impact_streams.has(key):
        impact_streams[key] = DZCombatAudio.impact_stream(critical, killed, boss)
    var voice := impact_audio_voices[impact_voice_index % impact_audio_voices.size()]
    impact_voice_index = (impact_voice_index + 1) % impact_audio_voices.size()
    voice.stream = impact_streams[key]
    voice.pitch_scale = randf_range(0.95, 1.05)
    voice.play()

func _play_boss_stinger() -> void:
    if boss_audio != null and boss_audio.stream != null:
        boss_audio.play()
    if music_audio != null and music_audio.playing:
        if music_duck_tween != null and music_duck_tween.is_valid():
            music_duck_tween.kill()
        music_duck_tween = create_tween()
        music_duck_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
        music_duck_tween.tween_property(music_audio, "volume_db", MUSIC_DUCK_DB, 0.08)
        music_duck_tween.tween_interval(1.55)
        music_duck_tween.tween_property(music_audio, "volume_db", MUSIC_BASE_DB, 0.70).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

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
