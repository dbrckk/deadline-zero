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
  combat_feel_test.gd
  enemy_archetype_combat_test.gd
  smoke_test.gd
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
```

## File: scripts/Hud.gd
```
class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)

var hp_bar: ProgressBar
var xp_bar: ProgressBar
var status_label: Label
var wave_label: Label
var upgrade_panel: PanelContainer
var upgrade_buttons: Array[Button] = []

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

func show_upgrade(labels: Array[String]) -> void:
    for i in range(upgrade_buttons.size()):
        upgrade_buttons[i].text = labels[i] if i < labels.size() else "UPGRADE"
    upgrade_panel.visible = true

func hide_upgrade() -> void:
    upgrade_panel.visible = false

func show_game_over() -> void:
    wave_label.text = "RUN TERMINATED"

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
        var button := Button.new()
        button.custom_minimum_size = Vector2(280, 190)
        button.text = "UPGRADE"
        button.add_theme_font_size_override("font_size", 22)
        button.pressed.connect(_on_upgrade_pressed.bind(i))
        row.add_child(button)
        upgrade_buttons.append(button)

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
    var t := clamp(age / life, 0.0, 1.0)
    scale = Vector3.ONE * lerp(0.55, 2.2 * scale_boost, t)
    var material := mesh_instance.material_override as StandardMaterial3D
    if material:
        var c := color
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
    {"id":"damage", "label":"HEAVY PAYLOAD\nDamage +25%"},
    {"id":"rate", "label":"RAPID FIRE\nFire rate +22%"},
    {"id":"speed", "label":"SCOUT FRAME\nMove speed +14%"},
    {"id":"health", "label":"REACTIVE PLATING\nMax HP +30"},
    {"id":"projectile", "label":"HYPER VELOCITY\nProjectile speed +20%"},
    {"id":"multishot", "label":"MULTISHOT\n+1 projectile"}
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
    hud.set_health(player.health, player.max_health)
    hud.set_progress(xp, xp_next, level, kills, elapsed)

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
        var desired := player.global_position + Vector3(0.0, 14.0, 10.0)
        var kick_offset := Vector3(sin(camera_kick_phase), 0.0, cos(camera_kick_phase * 1.27)) * camera_kick
        camera.global_position = camera.global_position.lerp(desired + kick_offset, 1.0 - exp(-delta * 4.5))
        camera.look_at(player.global_position + Vector3(0.0, 0.65, 0.0), Vector3.UP)

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

func _on_enemy_impact(at: Vector3, critical: bool, killed: bool, boss: bool) -> void:
    hit_freeze_left = max(hit_freeze_left, DZCombatFeel.hit_freeze_seconds(critical, killed, boss))
    camera_kick = max(camera_kick, DZCombatFeel.camera_kick(critical, killed, boss))

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
    var labels: Array[String] = []
    for item in pending_upgrades:
        labels.append(item["label"])
    hud.show_upgrade(labels)
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
        hud.show_game_over()

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
var touch_move := Vector2.ZERO
var fire_clock := 0.0
var invulnerability := 0.0
var authored_visual: Node3D
var authored_anim: AnimationPlayer
var current_anim := ""

func _ready() -> void:
    add_to_group("player")
    _build_visual()
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
            dir, projectile_speed, weapon_damage, Color(0.18, 0.90, 1.0))
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
    fx.scale_boost = 1.45 if critical else 1.0
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
