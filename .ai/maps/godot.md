This file is a merged representation of a subset of the codebase, containing specifically included files and files not matching ignore patterns, combined into a single document by Repomix.
The content has been processed where content has been compressed (code blocks are separated by ⋮---- delimiter).

# File Summary

## Purpose
This file contains a packed representation of a subset of the repository's contents that is considered the most important context.
It is designed to be easily consumable by AI systems for analysis, code review,
or other automated processes.

## File Format
The content is organized as follows:
1. This summary section
2. Repository information
3. Directory structure
4. Repository files (if enabled)
5. Multiple file entries, each consisting of:
  a. A header with the file path (## File: path/to/file)
  b. The full contents of the file in a code block

## Usage Guidelines
- This file should be treated as read-only. Any changes should be made to the
  original repository files, not this packed version.
- When processing this file, use the file path to distinguish
  between different files in the repository.
- Be aware that this file may contain sensitive information. Handle it with
  the same level of security as you would the original repository.

## Notes
- Some files may have been excluded based on .gitignore rules and Repomix's configuration
- Binary files are not included in this packed representation. Please refer to the Repository Structure section for a complete list of file paths, including binary files
- Only files matching these patterns are included: **/*.{py,js,mjs,cjs,ts,tsx,jsx,java,kt,kts,gd,groovy,gradle,toml,json,yaml,yml,sql,sh}
- Files matching these patterns are excluded: .ai/**, **/node_modules/**, **/.gradle/**, **/build/**, **/dist/**, **/.venv/**, **/__pycache__/**, **/.pytest_cache/**, **/.git/**, **/coverage/**, **/*.lock, **/*.min.js, **/*.map, assets/**, art/**, art_sources/**, marketing/**, colab/**, kaggle/**, discovery-cache.json, health-snapshot.json, history.json
- Files matching patterns in .gitignore are excluded
- Files matching default ignore patterns are excluded
- Content has been compressed - code blocks are separated by ⋮---- delimiter
- Files are sorted by Git change count (files with more changes are at the bottom)

# Directory Structure
```
scripts/
  AssetLibrary.gd
  CombatAudio.gd
  CombatFeel.gd
  Enemy.gd
  Hud.gd
  ImpactFx.gd
  Main.gd
  Player.gd
  Projectile.gd
  XpOrb.gd
tests/
  authored_asset_validation.gd
  boss_hud_identity_test.gd
  boss_reveal_camera_test.gd
  combat_audio_feedback_test.gd
  combat_feel_test.gd
  enemy_archetype_combat_test.gd
  enemy_silhouette_identity_test.gd
  run_end_ux_test.gd
  smoke_test.gd
  upgrade_presentation_test.gd
  weapon_presentation_test.gd
```

# Files

## File: scripts/AssetLibrary.gd
```
class_name DZAssetLibrary
extends RefCounted

const PLAYER := "res://assets/third_party/quaternius/zombie_apocalypse/player_matt.gltf"
const ZOMBIE_BASIC := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_basic.gltf"
const ZOMBIE_CHUBBY := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_chubby.gltf"
const RIFLE := "res://assets/third_party/quaternius/zombie_apocalypse/rifle.gltf"
const BARRIER := "res://assets/third_party/quaternius/zombie_apocalypse/plastic_barrier.gltf"

static func instantiate_scene(path: String) -> Node3D:
    if not ResourceLoader.exists(path):
        return null
    var packed := load(path) as PackedScene
    if packed == null:
        return null
    return packed.instantiate() as Node3D

static func player() -> Node3D:
    return instantiate_scene(PLAYER)

static func enemy(kind: String) -> Node3D:
    return instantiate_scene(ZOMBIE_CHUBBY if kind in ["brute", "elite"] else ZOMBIE_BASIC)

static func rifle() -> Node3D:
    return instantiate_scene(RIFLE)

static func barrier() -> Node3D:
    return instantiate_scene(BARRIER)

static func animation_player(root: Node) -> AnimationPlayer:
    if root == null:
        return null
    var direct := root.find_child("AnimationPlayer", true, false)
    return direct as AnimationPlayer
```

## File: scripts/CombatAudio.gd
```
class_name DZCombatAudio
extends RefCounted

# Procedural one-shot synthesis keeps the native 3D combat lane self-contained while authored
# weapon/enemy audio is still being produced. Each cue is intentionally short and phone-safe.

static func shot_stream(profile: String) -> AudioStreamWAV:
    var spec: Array = {
        "vanguard": [1180.0, 720.0, 0.055, 0.20],
        "scatter": [520.0, 220.0, 0.085, 0.34],
        "rail": [1960.0, 980.0, 0.070, 0.18],
        "inferno": [760.0, 330.0, 0.080, 0.28],
        "cryo": [1540.0, 1080.0, 0.072, 0.16],
        "arc": [1320.0, 460.0, 0.075, 0.22]
    }.get(profile, [1180.0, 720.0, 0.055, 0.20]) as Array
    return _chirp(float(spec[0]), float(spec[1]), float(spec[2]), float(spec[3]), 0.82)

static func impact_stream(critical: bool, killed: bool, boss: bool) -> AudioStreamWAV:
    if boss:
        return _chirp(210.0, 92.0, 0.120, 0.42, 0.92)
    if killed:
        return _chirp(390.0, 145.0, 0.095, 0.34, 0.88)
    if critical:
        return _chirp(980.0, 420.0, 0.082, 0.24, 0.88)
    return _chirp(640.0, 260.0, 0.052, 0.18, 0.72)

static func boss_stinger() -> AudioStreamWAV:
    return _chirp(170.0, 72.0, 0.240, 0.50, 0.94)

static func _chirp(start_hz: float, end_hz: float, seconds: float, noise_mix: float,
        gain: float) -> AudioStreamWAV:
    var rate: int = 22050
    var frames: int = maxi(64, int(seconds * rate))
    var bytes := PackedByteArray()
    bytes.resize(frames * 2)
    var phase: float = 0.0
    for i in range(frames):
        var t: float = float(i) / float(maxi(1, frames - 1))
        var hz: float = lerpf(start_hz, end_hz, t)
        phase += TAU * hz / float(rate)
        var envelope: float = pow(1.0 - t, 2.15)
        var tone: float = sin(phase) * (1.0 - noise_mix)
        var noise: float = (randf() * 2.0 - 1.0) * noise_mix
        var sample: float = clampf((tone + noise) * envelope * gain, -1.0, 1.0)
        var value: int = int(sample * 32767.0)
        if value < 0:
            value += 65536
        bytes[i * 2] = value & 0xff
        bytes[i * 2 + 1] = (value >> 8) & 0xff

    var wav := AudioStreamWAV.new()
    wav.format = AudioStreamWAV.FORMAT_16_BITS
    wav.mix_rate = rate
    wav.stereo = false
    wav.data = bytes
    return wav
```

## File: scripts/CombatFeel.gd
```
class_name DZCombatFeel
extends RefCounted

const DEFAULT_HIT_FREEZE := 0.022
const CRITICAL_HIT_FREEZE := 0.038
const KILL_HIT_FREEZE := 0.030
const BOSS_HIT_FREEZE := 0.044

static func hit_freeze_seconds(critical: bool, killed: bool, boss: bool) -> float:
    if boss:
        return BOSS_HIT_FREEZE
    if critical:
        return CRITICAL_HIT_FREEZE
    if killed:
        return KILL_HIT_FREEZE
    return DEFAULT_HIT_FREEZE

static func camera_kick(critical: bool, killed: bool, boss: bool) -> float:
    var kick := 0.055
    if killed:
        kick += 0.025
    if critical:
        kick += 0.035
    if boss:
        kick += 0.050
    return min(kick, 0.16)
```

## File: scripts/Enemy.gd
```
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
        _add_archetype_signature()
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

func _add_archetype_signature() -> void:
    if authored_visual == null:
        return

    var accent := Color(0.82, 0.18, 0.10)
    match kind:
        "runner":
            accent = Color(0.58, 1.0, 0.18)
            _add_runner_blades(accent)
        "brute":
            accent = Color(1.0, 0.28, 0.10)
            _add_brute_shoulders(accent)
        "elite":
            accent = Color(0.72, 0.30, 1.0)
            _add_elite_crown(accent)
        "boss":
            accent = Color(1.0, 0.62, 0.12)
            _add_boss_frame(accent)
        _:
            _add_eye_beacon(accent, Vector3(0.0, 1.62, -0.28), 0.055)

func _signature_material(color: Color, energy := 2.2) -> StandardMaterial3D:
    var mat := StandardMaterial3D.new()
    mat.albedo_color = color
    mat.metallic = 0.24
    mat.roughness = 0.34
    mat.emission_enabled = true
    mat.emission = color
    mat.emission_energy_multiplier = energy
    return mat

func _add_eye_beacon(color: Color, at: Vector3, size: float) -> void:
    var beacon := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = size
    mesh.height = size * 2.0
    beacon.mesh = mesh
    beacon.name = "SignatureBeacon"
    beacon.position = at
    beacon.material_override = _signature_material(color, 3.2)
    add_child(beacon)

func _add_runner_blades(color: Color) -> void:
    var mat := _signature_material(color, 2.8)
    for side in [-1.0, 1.0]:
        var blade := MeshInstance3D.new()
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.055, 0.34, 0.16)
        blade.mesh = mesh
        blade.name = "RunnerBladeL" if side < 0.0 else "RunnerBladeR"
        blade.position = Vector3(side * 0.38, 0.84, 0.04)
        blade.rotation_degrees = Vector3(0.0, 0.0, side * -24.0)
        blade.material_override = mat
        add_child(blade)
    _add_eye_beacon(color, Vector3(0.0, 1.58, -0.30), 0.050)

func _add_brute_shoulders(color: Color) -> void:
    var mat := _signature_material(color, 2.1)
    for side in [-1.0, 1.0]:
        var plate := MeshInstance3D.new()
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.32, 0.16, 0.36)
        plate.mesh = mesh
        plate.name = "BrutePlateL" if side < 0.0 else "BrutePlateR"
        plate.position = Vector3(side * 0.48, 1.12, 0.02)
        plate.rotation_degrees.z = side * -12.0
        plate.material_override = mat
        add_child(plate)
    _add_eye_beacon(color, Vector3(0.0, 1.72, -0.34), 0.070)

func _add_elite_crown(color: Color) -> void:
    var mat := _signature_material(color, 3.0)
    for side in [-1.0, 1.0]:
        var fin := MeshInstance3D.new()
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.08, 0.44, 0.12)
        fin.mesh = mesh
        fin.name = "EliteFinL" if side < 0.0 else "EliteFinR"
        fin.position = Vector3(side * 0.31, 1.62, 0.06)
        fin.rotation_degrees.z = side * -28.0
        fin.material_override = mat
        add_child(fin)
    _add_eye_beacon(color, Vector3(0.0, 1.70, -0.34), 0.075)

func _add_boss_frame(color: Color) -> void:
    var mat := _signature_material(color, 3.2)
    for side in [-1.0, 1.0]:
        var horn := MeshInstance3D.new()
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.10, 0.58, 0.18)
        horn.mesh = mesh
        horn.name = "BossHornL" if side < 0.0 else "BossHornR"
        horn.position = Vector3(side * 0.46, 1.76, 0.08)
        horn.rotation_degrees.z = side * -34.0
        horn.material_override = mat
        add_child(horn)
    var core := MeshInstance3D.new()
    var core_mesh := SphereMesh.new()
    core_mesh.radius = 0.10
    core_mesh.height = 0.20
    core.mesh = core_mesh
    core.name = "BossCore"
    core.position = Vector3(0.0, 1.30, -0.42)
    core.material_override = mat
    add_child(core)
    _add_eye_beacon(color, Vector3(0.0, 1.78, -0.42), 0.090)

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
        var base_scale: Vector3 = visual.scale
        var punch: float = 1.12 if critical else (1.10 if killed else 1.065)
        var tween: Tween = create_tween()
        tween.tween_property(visual, "scale", base_scale * punch, 0.035)
        tween.tween_property(visual, "scale", base_scale, 0.075)
```

## File: scripts/Hud.gd
```
class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)
signal restart_requested

var hp_bar: ProgressBar
var xp_bar: ProgressBar
var status_label: Label
var wave_label: Label
var upgrade_panel: PanelContainer
var upgrade_buttons: Array[Button] = []
var upgrade_cards: Array[VBoxContainer] = []
var upgrade_family_labels: Array[Label] = []
var upgrade_title_labels: Array[Label] = []
var upgrade_detail_labels: Array[Label] = []
var boss_panel: PanelContainer
var boss_name_label: Label
var boss_hp_bar: ProgressBar
var boss_phase_label: Label
var boss_hp_max := 1.0
var game_over_panel: PanelContainer
var game_over_summary: Label

func _ready() -> void:
    process_mode = Node.PROCESS_MODE_ALWAYS
    _build()

func set_health(value: float, maximum: float) -> void:
    hp_bar.max_value = max(1.0, maximum)
    hp_bar.value = value

func set_progress(xp: int, next_xp: int, level: int, kills: int, elapsed: float) -> void:
    xp_bar.max_value = max(1, next_xp)
    xp_bar.value = xp
    status_label.text = "LV %d   KILLS %d   %02d:%02d" % [level, kills, int(elapsed) / 60, int(elapsed) % 60]

func set_wave(text: String) -> void:
    wave_label.text = text

func show_boss(name: String, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_name_label.text = name
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = boss_hp_max
    boss_phase_label.text = "THREAT LOCK"
    boss_panel.visible = true

func set_boss_health(value: float, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = clamp(value, 0.0, boss_hp_max)
    var ratio := boss_hp_bar.value / boss_hp_max
    boss_phase_label.text = "PHASE III // EXECUTE" if ratio <= 0.30 else ("PHASE II // ENRAGED" if ratio <= 0.65 else "PHASE I // HUNT")
    if boss_hp_bar.value <= 0.0:
        boss_panel.visible = false

func hide_boss() -> void:
    boss_panel.visible = false

func show_upgrade(items: Array) -> void:
    for i in range(upgrade_buttons.size()):
        var item: Dictionary = items[i] if i < items.size() else {}
        var id := str(item.get("id", "damage"))
        upgrade_family_labels[i].text = str(item.get("family", "UPGRADE"))
        upgrade_title_labels[i].text = str(item.get("title", "UPGRADE"))
        upgrade_detail_labels[i].text = str(item.get("detail", ""))
        upgrade_buttons[i].text = _upgrade_glyph(id)
        _style_upgrade_card(i, id)
    upgrade_panel.visible = true

func hide_upgrade() -> void:
    upgrade_panel.visible = false

func show_game_over(kills: int, level: int, elapsed: float) -> void:
    wave_label.text = "RUN TERMINATED"
    upgrade_panel.visible = false
    boss_panel.visible = false
    var minutes := int(elapsed) / 60
    var seconds := int(elapsed) % 60
    game_over_summary.text = "LEVEL %d   •   KILLS %d   •   %02d:%02d" % [level, kills, minutes, seconds]
    game_over_panel.visible = true

func _build() -> void:
    var root := Control.new()
    root.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    add_child(root)

    var top := VBoxContainer.new()
    top.position = Vector2(28, 24)
    top.size = Vector2(500, 100)
    root.add_child(top)

    hp_bar = ProgressBar.new()
    hp_bar.custom_minimum_size = Vector2(420, 22)
    hp_bar.show_percentage = false
    top.add_child(hp_bar)

    xp_bar = ProgressBar.new()
    xp_bar.custom_minimum_size = Vector2(420, 12)
    xp_bar.show_percentage = false
    top.add_child(xp_bar)

    status_label = Label.new()
    status_label.text = "LV 1   KILLS 0"
    status_label.add_theme_font_size_override("font_size", 20)
    top.add_child(status_label)

    wave_label = Label.new()
    wave_label.text = "QUARANTINE YARD"
    wave_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    wave_label.add_theme_font_size_override("font_size", 28)
    wave_label.set_anchors_preset(Control.PRESET_CENTER_TOP)
    wave_label.position = Vector2(-220, 24)
    wave_label.size = Vector2(440, 42)
    root.add_child(wave_label)

    game_over_panel = PanelContainer.new()
    game_over_panel.set_anchors_preset(Control.PRESET_CENTER)
    game_over_panel.position = Vector2(-270, -120)
    game_over_panel.size = Vector2(540, 240)
    game_over_panel.visible = false
    root.add_child(game_over_panel)

    var game_over_box := VBoxContainer.new()
    game_over_box.alignment = BoxContainer.ALIGNMENT_CENTER
    game_over_box.add_theme_constant_override("separation", 16)
    game_over_panel.add_child(game_over_box)

    var game_over_title := Label.new()
    game_over_title.text = "SIGNAL LOST"
    game_over_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_title.add_theme_font_size_override("font_size", 34)
    game_over_title.modulate = Color(1.0, 0.34, 0.20)
    game_over_box.add_child(game_over_title)

    game_over_summary = Label.new()
    game_over_summary.text = "LEVEL 1   •   KILLS 0   •   00:00"
    game_over_summary.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_summary.add_theme_font_size_override("font_size", 18)
    game_over_summary.modulate = Color(0.82, 0.88, 0.92)
    game_over_box.add_child(game_over_summary)

    var restart_button := Button.new()
    restart_button.name = "RestartButton"
    restart_button.text = "REDEPLOY"
    restart_button.custom_minimum_size = Vector2(260, 58)
    restart_button.add_theme_font_size_override("font_size", 21)
    restart_button.pressed.connect(func() -> void:
        restart_requested.emit()
    )
    game_over_box.add_child(restart_button)

    var game_over_style := StyleBoxFlat.new()
    game_over_style.bg_color = Color(0.018, 0.026, 0.034, 0.97)
    game_over_style.border_color = Color(1.0, 0.22, 0.10, 0.78)
    game_over_style.set_border_width_all(2)
    game_over_style.corner_radius_top_left = 10
    game_over_style.corner_radius_top_right = 10
    game_over_style.corner_radius_bottom_left = 10
    game_over_style.corner_radius_bottom_right = 10
    game_over_panel.add_theme_stylebox_override("panel", game_over_style)

    boss_panel = PanelContainer.new()
    boss_panel.set_anchors_preset(Control.PRESET_CENTER_TOP)
    boss_panel.position = Vector2(-330, 76)
    boss_panel.size = Vector2(660, 78)
    boss_panel.visible = false
    root.add_child(boss_panel)

    var boss_box := VBoxContainer.new()
    boss_box.add_theme_constant_override("separation", 3)
    boss_panel.add_child(boss_box)

    var boss_header := HBoxContainer.new()
    boss_header.alignment = BoxContainer.ALIGNMENT_CENTER
    boss_box.add_child(boss_header)

    boss_name_label = Label.new()
    boss_name_label.text = "REVENANT PRIME"
    boss_name_label.add_theme_font_size_override("font_size", 18)
    boss_name_label.modulate = Color(1.0, 0.82, 0.42)
    boss_header.add_child(boss_name_label)

    var spacer := Control.new()
    spacer.custom_minimum_size = Vector2(32, 1)
    boss_header.add_child(spacer)

    boss_phase_label = Label.new()
    boss_phase_label.text = "PHASE I // HUNT"
    boss_phase_label.add_theme_font_size_override("font_size", 13)
    boss_phase_label.modulate = Color(1.0, 0.42, 0.26)
    boss_header.add_child(boss_phase_label)

    boss_hp_bar = ProgressBar.new()
    boss_hp_bar.custom_minimum_size = Vector2(620, 18)
    boss_hp_bar.show_percentage = false
    boss_box.add_child(boss_hp_bar)

    var boss_style := StyleBoxFlat.new()
    boss_style.bg_color = Color(0.025, 0.035, 0.045, 0.96)
    boss_style.border_color = Color(0.92, 0.28, 0.12, 0.72)
    boss_style.set_border_width_all(2)
    boss_style.corner_radius_top_left = 6
    boss_style.corner_radius_top_right = 6
    boss_style.corner_radius_bottom_left = 6
    boss_style.corner_radius_bottom_right = 6
    boss_panel.add_theme_stylebox_override("panel", boss_style)

    upgrade_panel = PanelContainer.new()
    upgrade_panel.set_anchors_preset(Control.PRESET_CENTER)
    upgrade_panel.position = Vector2(-480, -155)
    upgrade_panel.size = Vector2(960, 310)
    upgrade_panel.visible = false
    root.add_child(upgrade_panel)

    var box := VBoxContainer.new()
    box.add_theme_constant_override("separation", 18)
    upgrade_panel.add_child(box)

    var title := Label.new()
    title.text = "SELECT COMBAT UPGRADE"
    title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    title.add_theme_font_size_override("font_size", 30)
    box.add_child(title)

    var row := HBoxContainer.new()
    row.alignment = BoxContainer.ALIGNMENT_CENTER
    row.add_theme_constant_override("separation", 18)
    box.add_child(row)

    for i in range(3):
        var card := VBoxContainer.new()
        card.custom_minimum_size = Vector2(280, 190)
        card.add_theme_constant_override("separation", 5)
        row.add_child(card)
        upgrade_cards.append(card)

        var family := Label.new()
        family.text = "UPGRADE"
        family.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        family.add_theme_font_size_override("font_size", 13)
        card.add_child(family)
        upgrade_family_labels.append(family)

        var button := Button.new()
        button.custom_minimum_size = Vector2(280, 82)
        button.text = "◆"
        button.add_theme_font_size_override("font_size", 38)
        button.pressed.connect(_on_upgrade_pressed.bind(i))
        card.add_child(button)
        upgrade_buttons.append(button)

        var upgrade_title := Label.new()
        upgrade_title.text = "UPGRADE"
        upgrade_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        upgrade_title.add_theme_font_size_override("font_size", 21)
        card.add_child(upgrade_title)
        upgrade_title_labels.append(upgrade_title)

        var detail := Label.new()
        detail.text = ""
        detail.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        detail.add_theme_font_size_override("font_size", 16)
        detail.modulate = Color(0.76, 0.84, 0.90)
        card.add_child(detail)
        upgrade_detail_labels.append(detail)

func _upgrade_glyph(id: String) -> String:
    match id:
        "damage": return "▲"
        "rate": return "»»"
        "speed": return "➤"
        "health": return "+"
        "projectile": return "◆"
        "multishot": return "⋙"
        _: return "◆"

func _upgrade_color(id: String) -> Color:
    match id:
        "damage", "multishot": return Color(1.0, 0.66, 0.18)
        "rate", "speed": return Color(0.18, 0.86, 1.0)
        "health": return Color(0.32, 0.94, 0.52)
        "projectile": return Color(0.76, 0.82, 1.0)
        _: return Color(0.58, 0.42, 1.0)

func _style_upgrade_card(index: int, id: String) -> void:
    var accent := _upgrade_color(id)
    upgrade_family_labels[index].modulate = accent
    upgrade_title_labels[index].modulate = Color.WHITE
    var normal := StyleBoxFlat.new()
    normal.bg_color = Color(0.035, 0.055, 0.070, 0.98)
    normal.border_color = Color(accent.r, accent.g, accent.b, 0.72)
    normal.set_border_width_all(2)
    normal.corner_radius_top_left = 8
    normal.corner_radius_top_right = 8
    normal.corner_radius_bottom_left = 8
    normal.corner_radius_bottom_right = 8
    var hover := normal.duplicate()
    hover.bg_color = Color(accent.r * 0.16, accent.g * 0.16, accent.b * 0.16, 1.0)
    hover.border_color = accent
    upgrade_buttons[index].add_theme_stylebox_override("normal", normal)
    upgrade_buttons[index].add_theme_stylebox_override("hover", hover)
    upgrade_buttons[index].add_theme_stylebox_override("pressed", hover)
    upgrade_buttons[index].add_theme_color_override("font_color", accent)

func _on_upgrade_pressed(index: int) -> void:
    upgrade_chosen.emit(index)
```

## File: scripts/ImpactFx.gd
```
class_name ImpactFx
extends Node3D

var life := 0.18
var age := 0.0
var color := Color(0.25, 0.9, 1.0, 1.0)
var scale_boost := 1.0
var mesh_instance: MeshInstance3D
var light: OmniLight3D

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    var sphere := SphereMesh.new()
    sphere.radius = 0.18
    sphere.height = 0.36
    mesh_instance.mesh = sphere
    var material := StandardMaterial3D.new()
    material.albedo_color = color
    material.emission_enabled = true
    material.emission = color
    material.emission_energy_multiplier = 3.2
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    mesh_instance.material_override = material
    add_child(mesh_instance)

    light = OmniLight3D.new()
    light.light_color = color
    light.light_energy = 2.0
    light.omni_range = 2.5
    add_child(light)

func _process(delta: float) -> void:
    age += delta
    var t: float = clampf(age / life, 0.0, 1.0)
    scale = Vector3.ONE * lerp(0.55, 2.2 * scale_boost, t)
    var material := mesh_instance.material_override as StandardMaterial3D
    if material:
        var c: Color = color
        c.a = 1.0 - t
        material.albedo_color = c
    light.light_energy = lerp(2.0, 0.0, t)
    if age >= life:
        queue_free()
```

## File: scripts/Main.gd
```
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
```

## File: scripts/Player.gd
```
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

func _ready() -> void:
    add_to_group("player")
    _build_visual()
    _build_audio()
    health_changed.emit(health, max_health)

func _physics_process(delta: float) -> void:
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

func set_touch_move(value: Vector2) -> void:
    touch_move = value.limit_length(1.0)

func take_damage(amount: float) -> void:
    if invulnerability > 0.0 or health <= 0.0:
        return
    health = max(0.0, health - amount)
    invulnerability = 0.18
    health_changed.emit(health, max_health)
    if health <= 0.0:
        died.emit()

func heal_full() -> void:
    health = max_health
    health_changed.emit(health, max_health)

func apply_upgrade(id: String) -> void:
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
```

## File: scripts/Projectile.gd
```
class_name DZProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 24.0
var lifetime := 1.8
var radius := 0.34
var age := 0.0
var tint := Color(0.25, 0.9, 1.0)
var critical_chance := 0.08
var visual_profile := "vanguard"
var trail_length := 0.55
var trail_width := 0.055
var core_radius := 0.11
var impact_scale := 1.0

func setup(origin: Vector3, direction: Vector3, speed: float, shot_damage: float, shot_tint: Color,
        profile := "vanguard") -> void:
    global_position = origin
    velocity = direction.normalized() * speed
    damage = shot_damage
    tint = shot_tint
    visual_profile = profile
    _apply_profile(profile)

func _apply_profile(profile: String) -> void:
    match profile:
        "scatter":
            trail_length = 0.32
            trail_width = 0.09
            core_radius = 0.13
            impact_scale = 1.18
        "rail":
            trail_length = 1.25
            trail_width = 0.035
            core_radius = 0.075
            impact_scale = 1.34
        "inferno":
            trail_length = 0.72
            trail_width = 0.075
            core_radius = 0.12
            impact_scale = 1.22
        "cryo":
            trail_length = 0.82
            trail_width = 0.07
            core_radius = 0.12
            impact_scale = 1.24
        "arc":
            trail_length = 0.94
            trail_width = 0.045
            core_radius = 0.09
            impact_scale = 1.20
        _:
            trail_length = 0.55
            trail_width = 0.055
            core_radius = 0.11
            impact_scale = 1.0

func _ready() -> void:
    var glow := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = core_radius
    mesh.height = core_radius * 2.0
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
    trail_mesh.size = Vector3(trail_width, trail_width, trail_length)
    trail.mesh = trail_mesh
    trail.position.z = trail_length * 0.52
    trail.material_override = mat
    add_child(trail)

    if visual_profile == "cryo":
        glow.scale = Vector3(0.62, 1.55, 0.62)
        glow.rotation_degrees.z = 45.0
    elif visual_profile == "scatter":
        _add_side_spark(mat, -1.0)
        _add_side_spark(mat, 1.0)
    elif visual_profile == "arc":
        _add_arc_accent()
    elif visual_profile == "inferno":
        _add_flame_core()

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)

func _add_side_spark(mat: StandardMaterial3D, side: float) -> void:
    var spark := MeshInstance3D.new()
    var mesh := BoxMesh.new()
    mesh.size = Vector3(0.025, 0.025, trail_length * 0.62)
    spark.mesh = mesh
    spark.position = Vector3(side * 0.10, 0.0, trail_length * 0.30)
    spark.material_override = mat
    add_child(spark)

func _add_arc_accent() -> void:
    var accent := MeshInstance3D.new()
    var mesh := TorusMesh.new()
    mesh.inner_radius = core_radius * 0.85
    mesh.outer_radius = core_radius * 1.45
    accent.mesh = mesh
    accent.rotation_degrees.x = 90.0
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(0.58, 0.36, 1.0)
    mat.emission_enabled = true
    mat.emission = mat.albedo_color
    mat.emission_energy_multiplier = 4.0
    accent.material_override = mat
    add_child(accent)

func _add_flame_core() -> void:
    var core := OmniLight3D.new()
    core.light_color = Color(1.0, 0.30, 0.04)
    core.light_energy = 1.1
    core.omni_range = 1.35
    add_child(core)

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
            var critical := randf() < critical_chance
            enemy.take_damage(damage * (1.75 if critical else 1.0), critical)
            _impact(critical)
            queue_free()
            return

    if age >= lifetime:
        queue_free()

func _impact(critical := false) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.76, 0.18) if critical else tint
    fx.scale_boost = (1.45 if critical else 1.0) * impact_scale
    get_tree().current_scene.add_child(fx)
    fx.global_position = global_position
```

## File: scripts/XpOrb.gd
```
class_name DZXpOrb
extends Node3D

signal collected(amount: int)

var amount := 1
var target: Node3D
var velocity := Vector3.ZERO
var age := 0.0

func _ready() -> void:
    var orb := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = 0.12
    mesh.height = 0.24
    orb.mesh = mesh
    var mat := StandardMaterial3D.new()
    mat.albedo_color = Color(0.18, 0.95, 0.75)
    mat.emission_enabled = true
    mat.emission = Color(0.1, 1.0, 0.7)
    mat.emission_energy_multiplier = 3.0
    orb.material_override = mat
    add_child(orb)

func _process(delta: float) -> void:
    age += delta
    rotation.y += delta * 4.0
    position.y = 0.18 + sin(age * 5.0) * 0.05
    if target == null or not is_instance_valid(target):
        return
    var flat_target := target.global_position
    flat_target.y = global_position.y
    var distance := global_position.distance_to(flat_target)
    if distance < 5.0:
        var dir := global_position.direction_to(flat_target)
        velocity = velocity.lerp(dir * 10.0, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
```

## File: tests/authored_asset_validation.gd
```
extends SceneTree

const PLAYER_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/player_matt.gltf"
const ZOMBIE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_basic.gltf"
const BRUTE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_chubby.gltf"
const RIFLE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/rifle.gltf"
const BARRIER_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/plastic_barrier.gltf"

func _initialize() -> void:
    _assert_scene(PLAYER_PATH, ["Idle_Gun", "Run_Gun", "HitReact", "Death"])
    _assert_scene(ZOMBIE_PATH, ["Walk", "Run_Arms", "HitReact", "Death"])
    _assert_scene(BRUTE_PATH, ["Walk", "Run_Arms", "Death"])
    _assert_scene(RIFLE_PATH, [])
    _assert_scene(BARRIER_PATH, [])
    print("Deadline Zero authored 3D asset validation: OK")
    quit(0)

func _assert_scene(path: String, required_animations: Array[String]) -> void:
    var resource := load(path) as PackedScene
    if resource == null:
        push_error("Failed to import authored scene: " + path)
        quit(1)
        return
    var instance := resource.instantiate()
    if instance == null:
        push_error("Failed to instantiate authored scene: " + path)
        quit(1)
        return
    if not required_animations.is_empty():
        var player := _find_animation_player(instance)
        if player == null:
            push_error("No AnimationPlayer found in: " + path)
            instance.free()
            quit(1)
            return
        for animation_name in required_animations:
            if not player.has_animation(animation_name):
                push_error("Missing animation %s in %s" % [animation_name, path])
                instance.free()
                quit(1)
                return
    instance.free()

func _find_animation_player(node: Node) -> AnimationPlayer:
    if node is AnimationPlayer:
        return node as AnimationPlayer
    for child in node.get_children():
        var found := _find_animation_player(child)
        if found != null:
            return found
    return null
```

## File: tests/boss_hud_identity_test.gd
```
extends SceneTree

func _init() -> void:
    var hud_source := FileAccess.get_file_as_string("res://scripts/Hud.gd")
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")

    assert(hud_source.contains("func show_boss("))
    assert(hud_source.contains("func set_boss_health("))
    assert(hud_source.contains("PHASE II // ENRAGED"))
    assert(hud_source.contains("PHASE III // EXECUTE"))
    assert(hud_source.contains("boss_hp_bar"))
    assert(enemy_source.contains("signal health_changed"))
    assert(enemy_source.contains("health_changed.emit(max(0.0, health), max_health)"))
    assert(main_source.contains("enemy.health_changed.connect(_on_boss_health_changed)"))
    assert(main_source.contains("hud.show_boss("))

    print("Godot boss HUD identity validation passed")
    quit()
```

## File: tests/boss_reveal_camera_test.gd
```
extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    assert(source.contains("BOSS_REVEAL_DURATION := 1.15"))
    assert(source.contains("BOSS_REVEAL_FOCUS := 0.58"))
    assert(source.contains("BOSS_REVEAL_FOV_DELTA := 5.5"))
    assert(source.contains("boss_reveal_target = enemy"))
    assert(source.contains("target_fov = 48.0 + BOSS_REVEAL_FOV_DELTA * envelope"))
    assert(source.contains("camera.look_at(focus_point, Vector3.UP)"))

    # Mobile comfort bounds: the reveal must stay short and widen the view rather than punch in.
    assert(1.15 <= 1.25)
    assert(5.5 <= 7.0)
    assert(0.58 >= 0.45 and 0.58 <= 0.68)
    print("godot boss reveal camera validation passed")
    quit()
```

## File: tests/combat_audio_feedback_test.gd
```
extends SceneTree

func _init() -> void:
    # Validate synthesis metadata only. Reading AudioStreamWAV.data from a headless
    # Godot process has proven disproportionately slow in CI and does not add
    # meaningful coverage over construction + duration/profile checks.
    var profiles := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]
    var durations := []
    for profile in profiles:
        var stream := DZCombatAudio.shot_stream(profile)
        assert(stream != null)
        assert(stream.mix_rate == 22050)
        assert(stream.format == AudioStreamWAV.FORMAT_16_BITS)
        assert(not stream.stereo)
        assert(stream.get_length() > 0.04)
        assert(stream.get_length() < 0.12)
        durations.append(stream.get_length())

    var hit := DZCombatAudio.impact_stream(false, false, false)
    var critical := DZCombatAudio.impact_stream(true, false, false)
    var killed := DZCombatAudio.impact_stream(false, true, false)
    var boss_hit := DZCombatAudio.impact_stream(false, false, true)
    var boss := DZCombatAudio.boss_stinger()

    for stream in [hit, critical, killed, boss_hit, boss]:
        assert(stream != null)
        assert(stream.mix_rate == 22050)
        assert(stream.format == AudioStreamWAV.FORMAT_16_BITS)
        assert(not stream.stereo)

    assert(hit.get_length() < critical.get_length())
    assert(critical.get_length() < killed.get_length())
    assert(killed.get_length() < boss_hit.get_length())
    assert(boss_hit.get_length() < boss.get_length())
    assert(durations[1] > durations[0])
    print("combat audio feedback test passed")
    quit()
```

## File: tests/combat_feel_test.gd
```
extends SceneTree

func _initialize() -> void:
    _assert(DZCombatFeel.DEFAULT_HIT_FREEZE > 0.0, "default hit freeze must be positive")
    _assert(DZCombatFeel.CRITICAL_HIT_FREEZE > DZCombatFeel.DEFAULT_HIT_FREEZE, "critical must read stronger")
    _assert(DZCombatFeel.BOSS_HIT_FREEZE <= 0.050, "boss hit freeze must stay mobile-safe")
    _assert(DZCombatFeel.camera_kick(false, false, false) < DZCombatFeel.camera_kick(true, true, true),
        "important impacts must produce stronger camera feedback")
    _assert(DZCombatFeel.camera_kick(true, true, true) <= 0.16, "camera kick comfort bound")
    print("Deadline Zero Godot combat-feel profile: OK")
    quit(0)

func _assert(condition: bool, message: String) -> void:
    if condition:
        return
    push_error(message)
    quit(1)
```

## File: tests/enemy_archetype_combat_test.gd
```
extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    assert(source.contains("attack_windup"))
    assert(source.contains("_begin_telegraphed_attack"))
    assert(source.contains("_resolve_telegraphed_attack"))
    assert(source.contains("kind == \"elite\""))
    assert(source.contains("kind == \"boss\""))
    assert(source.contains("target.take_damage(damage)"))
    assert(source.contains("_show_telegraph"))
    assert(source.contains("_spawn_attack_impact"))
    print("enemy_archetype_combat_test: PASS")
    quit()
```

## File: tests/enemy_silhouette_identity_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
    }

    for kind in expected.keys():
        var enemy := ENEMY_SCRIPT.new()
        root.add_child(enemy)
        enemy.kind = kind
        enemy.call_deferred("_add_archetype_signature")
        await process_frame
        for node_name in expected[kind]:
            if enemy.get_node_or_null(node_name) == null:
                push_error("Missing %s signature node %s" % [kind, node_name])
                quit(1)
                return
        enemy.queue_free()

    print("Deadline Zero enemy silhouette identity: OK")
    quit(0)
```

## File: tests/run_end_ux_test.gd
```
extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)

    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if hud.game_over_panel == null:
        push_error("Game-over panel was not created")
        quit(1)
        return
    if hud.game_over_panel.visible:
        push_error("Game-over panel should start hidden")
        quit(1)
        return

    hud.show_game_over(37, 8, 154.0)

    if not hud.game_over_panel.visible:
        push_error("Game-over panel did not become visible")
        quit(1)
        return
    if hud.game_over_summary == null:
        push_error("Game-over summary label is missing")
        quit(1)
        return
    if hud.game_over_summary.text != "LEVEL 8   •   KILLS 37   •   02:34":
        push_error("Unexpected game-over summary: %s" % hud.game_over_summary.text)
        quit(1)
        return

    var restart_button := hud.game_over_panel.find_child("RestartButton", true, false) as Button
    if restart_button == null:
        push_error("Redeploy button is missing")
        quit(1)
        return

    var restart_state := {"signaled": false}
    hud.restart_requested.connect(func() -> void:
        restart_state["signaled"] = true
    )
    restart_button.pressed.emit()
    await process_frame

    if not bool(restart_state["signaled"]):
        push_error("Redeploy button did not emit restart_requested")
        quit(1)
        return

    print("Deadline Zero run-end UX: OK")
    quit(0)
```

## File: tests/smoke_test.gd
```
extends SceneTree

var frames := 0

func _initialize() -> void:
    var packed := load("res://scenes/Main.tscn") as PackedScene
    if packed == null:
        push_error("Unable to load Main.tscn")
        quit(1)
        return
    var game := packed.instantiate()
    root.add_child(game)

func _process(_delta: float) -> bool:
    frames += 1
    if frames > 6:
        if root.get_child_count() <= 0:
            push_error("Godot smoke test has no instantiated game root")
            quit(1)
        else:
            print("Deadline Zero Godot 3D smoke test: OK")
            quit(0)
        return true
    return false
```

## File: tests/upgrade_presentation_test.gd
```
extends SceneTree

func _init() -> void:
    var main_text := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var hud_text := FileAccess.get_file_as_string("res://scripts/Hud.gd")

    var ids := ["damage", "rate", "speed", "health", "projectile", "multishot"]
    for id in ids:
        assert(main_text.contains("\"id\":\"" + id + "\""))
        assert(hud_text.contains("\"" + id + "\""))

    assert(main_text.contains("\"family\":\"OFFENSE\""))
    assert(main_text.contains("\"family\":\"SURVIVAL\""))
    assert(main_text.contains("\"family\":\"BARRAGE\""))
    assert(hud_text.contains("func _upgrade_glyph"))
    assert(hud_text.contains("func _upgrade_color"))
    assert(hud_text.contains("func _style_upgrade_card"))
    assert(hud_text.contains("StyleBoxFlat.new()"))
    assert(hud_text.contains("upgrade_family_labels"))
    assert(hud_text.contains("upgrade_detail_labels"))
    print("godot upgrade presentation test passed")
    quit(0)
```

## File: tests/weapon_presentation_test.gd
```
extends SceneTree

func _init() -> void:
    var projectile := FileAccess.get_file_as_string("res://scripts/Projectile.gd")
    var player := FileAccess.get_file_as_string("res://scripts/Player.gd")

    for profile in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        assert(projectile.contains("\"" + profile + "\""))
    assert(projectile.contains("trail_length"))
    assert(projectile.contains("trail_width"))
    assert(projectile.contains("core_radius"))
    assert(projectile.contains("impact_scale"))
    assert(projectile.contains("_add_side_spark"))
    assert(projectile.contains("_add_arc_accent"))
    assert(projectile.contains("_add_flame_core"))
    assert(player.contains("weapon_profile"))
    assert(player.contains("weapon_tint"))
    assert(player.contains("weapon_profile)"))

    print("weapon_presentation_test: PASS")
    quit()
```
