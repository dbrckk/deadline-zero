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
  EnemyProjectile.gd
  GameSettings.gd
  Haptics.gd
  Hud.gd
  ImpactFx.gd
  Main.gd
  Player.gd
  Projectile.gd
  RunDirector.gd
  SpatialHash.gd
  WeaponProfiles.gd
  XpOrb.gd
tests/
  archetype_roster_render_test.gd
  attack_telegraph_escalation_test.gd
  authored_asset_validation.gd
  authored_world_dressing_test.gd
  boss_hud_identity_test.gd
  boss_phase_runtime_test.gd
  boss_reveal_camera_test.gd
  combat_audio_feedback_test.gd
  combat_danger_hud_test.gd
  combat_feel_test.gd
  enemy_archetype_combat_test.gd
  enemy_hit_reaction_test.gd
  enemy_projectile_visual_test.gd
  enemy_silhouette_identity_test.gd
  environment_asset_validation_test.gd
  environment_identity_test.gd
  first_playable_run_path_test.gd
  haptics_service_test.gd
  hud_readability_hierarchy_test.gd
  impact_fx_mobile_test.gd
  mobile_orientation_test.gd
  native_enemy_behavior_test.gd
  native_upgrade_depth_test.gd
  player_damage_feedback_test.gd
  pressure_frame_render_test.gd
  rendered_frame_smoke_test.gd
  run_director_escalation_test.gd
  run_director_runtime_integration_test.gd
  run_end_combat_freeze_test.gd
  run_end_ux_test.gd
  screen_space_fx_test.gd
  settings_persistence_test.gd
  smoke_test.gd
  spatial_hash_test.gd
  status_effects_test.gd
  upgrade_presentation_test.gd
  weapon_presentation_test.gd
  weapon_profile_data_test.gd
  weapon_protocol_behavior_test.gd
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
const BARREL := "res://assets/third_party/quaternius/zombie_apocalypse/barrel.gltf"
const PALLET := "res://assets/third_party/quaternius/zombie_apocalypse/pallet.gltf"
const STREET_LIGHTS := "res://assets/third_party/quaternius/zombie_apocalypse/streetlights.gltf"
const TRAFFIC_CONE := "res://assets/third_party/quaternius/zombie_apocalypse/trafficcone-1.gltf"
const TRASH_BAG := "res://assets/third_party/quaternius/zombie_apocalypse/trashbag-1.gltf"
const STREET_CRACK := "res://assets/third_party/quaternius/zombie_apocalypse/street-straight-crack1.gltf"

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
    var root := instantiate_scene(ZOMBIE_CHUBBY if kind in ["brute", "elite", "boss"] else ZOMBIE_BASIC)
    var tint := Color(0.82, 0.92, 0.80)
    match kind:
        "runner": tint = Color(0.72, 1.00, 0.74)
        "charger": tint = Color(1.00, 0.68, 0.48)
        "harrier": tint = Color(0.58, 0.88, 1.00)
        "regenerator": tint = Color(0.58, 1.00, 0.68)
        "brute": tint = Color(0.92, 0.56, 0.46)
        "elite": tint = Color(0.78, 0.58, 1.00)
        "boss": tint = Color(0.96, 0.62, 0.40)
    _grade_mesh_tree(root, tint, 0.74, 0.0)
    return root

static func rifle() -> Node3D:
    return instantiate_scene(RIFLE)

static func barrier() -> Node3D:
    var root := instantiate_scene(BARRIER)
    _apply_barrier_industrial_material(root)
    _add_barrier_hazard_signature(root)
    return root

static func barrel() -> Node3D:
    return instantiate_scene(BARREL)

static func pallet() -> Node3D:
    return instantiate_scene(PALLET)

static func street_lights() -> Node3D:
    var root := instantiate_scene(STREET_LIGHTS)
    _grade_mesh_tree(root, Color(0.20, 0.27, 0.32), 0.86, 0.18)
    return root

static func traffic_cone() -> Node3D:
    return instantiate_scene(TRAFFIC_CONE)

static func trash_bag() -> Node3D:
    return instantiate_scene(TRASH_BAG)

static func street_crack() -> Node3D:
    return instantiate_scene(STREET_CRACK)

static func _apply_barrier_industrial_material(root: Node3D) -> void:
    if root == null:
        return
    var meshes: Array[MeshInstance3D] = []
    if root is MeshInstance3D:
        meshes.append(root as MeshInstance3D)
    for node in root.find_children("*", "MeshInstance3D", true, false):
        meshes.append(node as MeshInstance3D)

    for mesh_instance in meshes:
        if mesh_instance == null or mesh_instance.mesh == null:
            continue
        var material := StandardMaterial3D.new()
        material.albedo_color = Color(0.075, 0.105, 0.125)
        material.metallic = 0.34
        material.roughness = 0.82
        mesh_instance.material_override = material

static func _add_barrier_hazard_signature(root: Node3D) -> void:
    if root == null:
        return
    var material := StandardMaterial3D.new()
    material.albedo_color = Color(0.92, 0.26, 0.035)
    material.emission_enabled = true
    material.emission = Color(0.68, 0.10, 0.01)
    material.emission_energy_multiplier = 0.72
    material.roughness = 0.54

    for side in [-1.0, 1.0]:
        var strip := MeshInstance3D.new()
        strip.name = "BarrierHazardFront" if side < 0.0 else "BarrierHazardRear"
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.74, 0.075, 0.018)
        strip.mesh = mesh
        strip.position = Vector3(0.0, 0.42, side * 0.176)
        strip.material_override = material
        root.add_child(strip)

static func _grade_mesh_tree(root: Node3D, tint: Color, roughness: float, metallic: float) -> void:
    if root == null:
        return
    if root is MeshInstance3D:
        _grade_mesh_instance(root as MeshInstance3D, tint, roughness, metallic)
    for node in root.find_children("*", "MeshInstance3D", true, false):
        _grade_mesh_instance(node as MeshInstance3D, tint, roughness, metallic)

static func _grade_mesh_instance(mesh_instance: MeshInstance3D, tint: Color, roughness: float, metallic: float) -> void:
    if mesh_instance == null or mesh_instance.mesh == null or mesh_instance.mesh.get_surface_count() == 0:
        return
    var source := mesh_instance.mesh.surface_get_material(0)
    if not source is BaseMaterial3D:
        return
    var graded := source.duplicate(true) as BaseMaterial3D
    graded.albedo_color = Color(
        graded.albedo_color.r * tint.r,
        graded.albedo_color.g * tint.g,
        graded.albedo_color.b * tint.b,
        graded.albedo_color.a
    )
    graded.roughness = maxf(graded.roughness, roughness)
    graded.metallic = maxf(graded.metallic, metallic)
    mesh_instance.material_override = graded

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
var boss_phase := 1
var telegraph_visual: Node3D
var telegraph_material: StandardMaterial3D
var slow_multiplier := 1.0
var slow_left := 0.0
var burn_dps := 0.0
var burn_left := 0.0
var burn_tick_accumulator := 0.0
var shock_left := 0.0
var special_clock := 1.8
var regeneration_clock := 1.0
var regeneration_windup := 0.0
var regeneration_visual: Node3D
var regeneration_material: StandardMaterial3D
var pending_special := ""
var spawn_secondary_fx := true
var combat_enabled := true
var charge_active := false
var charge_direction := Vector3.ZERO
var charge_left := 0.0
var charge_hit := false
var hit_flash_visual: MeshInstance3D
var hit_flash_material: StandardMaterial3D
var hit_reaction_tween: Tween

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
        "charger":
            max_health = 105.0 * difficulty
            move_speed = 2.35
            contact_damage = 13.0
            xp_value = 4
        "harrier":
            max_health = 74.0 * difficulty
            move_speed = 2.75
            contact_damage = 9.0
            xp_value = 4
        "regenerator":
            max_health = 128.0 * difficulty
            move_speed = 1.72
            contact_damage = 10.0
            xp_value = 5
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
    _build_hit_flash()

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        velocity = Vector3.ZERO
        return
    _process_status_effects(delta)
    shock_left = maxf(0.0, shock_left - maxf(delta, 0.0))
    if dead or target == null or not is_instance_valid(target):
        return
    if shock_left > 0.0:
        velocity = Vector3.ZERO
        return
    if kind == "boss":
        _update_boss_phase()
    attack_cooldown = max(0.0, attack_cooldown - delta)
    elite_burst_clock = max(0.0, elite_burst_clock - delta)
    boss_slam_clock = max(0.0, boss_slam_clock - delta)
    special_clock = max(0.0, special_clock - delta)
    regeneration_clock = max(0.0, regeneration_clock - delta)
    slow_left = max(0.0, slow_left - delta)
    if slow_left <= 0.0:
        slow_multiplier = 1.0
    if charge_active:
        _process_charge(delta)
        return
    if regeneration_windup > 0.0:
        _process_regeneration(delta)
        return
    var delta_pos := target.global_position - global_position
    delta_pos.y = 0.0
    var distance := delta_pos.length()

    if kind == "regenerator" and regeneration_clock <= 0.0 and health < max_health:
        _begin_regeneration()
        regeneration_clock = 1.0
        return

    if attack_windup > 0.0:
        velocity = Vector3.ZERO
        attack_windup = max(0.0, attack_windup - delta)
        if attack_windup <= 0.0:
            _resolve_telegraphed_attack()
        return

    if kind == "charger" and special_clock <= 0.0 and distance > 2.2 and distance < 7.2:
        pending_special = "charge"
        _begin_telegraphed_attack(0.52, target.global_position)
        special_clock = 3.4
        return

    if kind == "harrier" and special_clock <= 0.0 and distance >= 3.5 and distance <= 8.5:
        pending_special = "harrier_shot"
        _begin_telegraphed_attack(0.42, target.global_position)
        special_clock = 2.6
        return

    if kind == "elite" and elite_burst_clock <= 0.0 and distance < 5.2:
        _begin_telegraphed_attack(0.46, target.global_position)
        elite_burst_clock = 3.0
        return
    if kind == "boss" and boss_slam_clock <= 0.0 and distance < 4.6:
        _begin_telegraphed_attack(_boss_slam_windup(), target.global_position)
        boss_slam_clock = _boss_slam_cooldown()
        return

    if distance > 0.05:
        var movement_direction: Vector3 = delta_pos.normalized()
        if kind == "harrier":
            if distance < 4.4:
                movement_direction = -movement_direction
            elif distance <= 6.6:
                movement_direction = Vector3(-movement_direction.z, 0.0, movement_direction.x)

        # Local separation keeps the swarm readable and prevents every body from collapsing
        # onto the same target point. Main.gd serves this from its spatial hash in production.
        var separation_radius := 1.85 if kind in ["boss", "brute", "charger"] else 1.42
        var separation := separation_vector(_nearby_enemies_for_separation(separation_radius), separation_radius)
        if separation.length_squared() > 0.001:
            var separation_weight := 0.78 if kind == "boss" else (1.55 if kind == "harrier" else 1.32)
            movement_direction = (movement_direction + separation * separation_weight).normalized()

        # Once enemies enter melee distance, bias them toward a contact ring instead of the
        # player's exact origin. They can still cross the attack threshold, but do not remain
        # stacked on the same point after contact.
        if kind != "harrier" and distance < 1.08:
            var outward := global_position - target.global_position
            outward.y = 0.0
            if outward.length_squared() > 0.001:
                var crowd_pressure := clampf((1.08 - distance) / 0.38, 0.0, 1.0)
                movement_direction = (movement_direction + outward.normalized() * crowd_pressure * 1.45).normalized()

        velocity = movement_direction * move_speed * slow_multiplier
        move_and_slide()
        if velocity.length_squared() > 0.01:
            look_at(global_position + velocity, Vector3.UP)
    _update_authored_animation(distance)
    if distance < 0.85 and attack_cooldown <= 0.0 and target.has_method("take_damage"):
        target.take_damage(contact_damage)
        attack_cooldown = 0.72

func _nearby_enemies_for_separation(radius: float) -> Array:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene != null and scene.has_method("query_enemies_near"):
        return scene.query_enemies_near(global_position, radius)
    return get_tree().get_nodes_in_group("enemies") if get_tree() != null else []

func separation_vector(neighbors: Array, radius: float) -> Vector3:
    if radius <= 0.0:
        return Vector3.ZERO
    var separation := Vector3.ZERO
    var contributions := 0
    for node in neighbors:
        var other := node as DZEnemy
        if other == null or other == self or other.dead:
            continue
        var away := global_position - other.global_position
        away.y = 0.0
        var distance_sq := away.length_squared()
        if distance_sq >= radius * radius:
            continue

        # Resolve near-perfect overlap deterministically instead of leaving a permanent stack.
        if distance_sq < 0.0004:
            var phase := float(int(get_instance_id() + other.get_instance_id()) % 16) / 16.0 * TAU
            away = Vector3(cos(phase), 0.0, sin(phase))
            distance_sq = 0.0004

        var distance := sqrt(distance_sq)
        var pressure := clampf((radius - distance) / radius, 0.0, 1.0)
        separation += away / distance * pressure * pressure
        contributions += 1

    if contributions == 0 or separation.length_squared() < 0.0001:
        return Vector3.ZERO
    return separation.normalized()

func _update_boss_phase() -> void:
    if kind != "boss" or max_health <= 0.0:
        return
    var ratio := clampf(health / max_health, 0.0, 1.0)
    boss_phase = 3 if ratio <= 0.30 else (2 if ratio <= 0.65 else 1)
    match boss_phase:
        2:
            move_speed = 1.55
            contact_damage = 27.0
        3:
            move_speed = 1.76
            contact_damage = 31.0
        _:
            move_speed = 1.38
            contact_damage = 24.0

func _boss_slam_windup() -> float:
    match boss_phase:
        2: return 0.56
        3: return 0.44
        _: return 0.68

func _boss_slam_cooldown() -> float:
    match boss_phase:
        2: return 3.4
        3: return 2.8
        _: return 4.1

func _process_charge(delta: float) -> void:
    charge_left = max(0.0, charge_left - delta)
    velocity = charge_direction * 9.4
    move_and_slide()
    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity, Vector3.UP)
    if not charge_hit and target != null and is_instance_valid(target):
        var target_offset := target.global_position - global_position
        target_offset.y = 0.0
        if target_offset.length() <= 1.0 and target.has_method("take_damage"):
            target.take_damage(contact_damage * 1.30)
            charge_hit = true
            _spawn_attack_impact(global_position + Vector3(0.0, 0.05, 0.0), 1.05)
    if charge_left <= 0.0:
        charge_active = false
        velocity = Vector3.ZERO
        attack_cooldown = 0.80

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if enabled:
        return
    velocity = Vector3.ZERO
    attack_windup = 0.0
    pending_special = ""
    charge_active = false
    charge_left = 0.0
    charge_hit = false
    regeneration_windup = 0.0
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    regeneration_visual = null
    regeneration_material = null
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = null

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
    if pending_special == "charge":
        var direction := attack_target_position - global_position
        direction.y = 0.0
        if direction.length_squared() < 0.001:
            direction = global_transform.basis.z * -1.0
        charge_direction = direction.normalized()
        charge_left = clampf(direction.length() / 9.4, 0.28, 0.72)
        charge_active = true
        charge_hit = false
        pending_special = ""
        return
    if pending_special == "harrier_shot":
        var shot := DZEnemyProjectile.new()
        get_tree().current_scene.add_child(shot)
        shot.global_position = global_position + Vector3(0.0, 0.34, 0.0)
        shot.configure(attack_target_position, target, contact_damage * 0.88)
        pending_special = ""
        attack_cooldown = 0.95
        return
    var radius: float = 1.95 if kind == "boss" else 1.18
    var damage: float = contact_damage * (1.35 if kind == "boss" else 0.82)
    var impact_point: Vector3 = global_position.lerp(attack_target_position, 0.58)
    impact_point.y = 0.05
    if target.global_position.distance_to(impact_point) <= radius and target.has_method("take_damage"):
        target.take_damage(damage)
    _spawn_attack_impact(impact_point, radius)
    attack_cooldown = 0.88 if kind == "boss" else 0.64

func _show_telegraph(radius: float, duration: float) -> void:
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = MeshInstance3D.new()
    telegraph_visual.name = "AttackTelegraphRing"
    var mesh := TorusMesh.new()
    mesh.inner_radius = radius * (0.82 if kind == "boss" else 0.86)
    mesh.outer_radius = radius
    mesh.rings = 40 if kind == "boss" else 32
    mesh.ring_segments = 8
    telegraph_visual.mesh = mesh
    get_tree().current_scene.add_child(telegraph_visual)
    telegraph_visual.global_position = global_position.lerp(attack_target_position, 0.58) + Vector3(0.0, 0.035, 0.0)
    telegraph_material = StandardMaterial3D.new()
    telegraph_material.albedo_color = Color(1.0, 0.22, 0.025, 0.42)
    telegraph_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    telegraph_material.emission_enabled = true
    telegraph_material.emission = Color(1.0, 0.075, 0.006)
    telegraph_material.emission_energy_multiplier = 1.7
    telegraph_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    telegraph_visual.material_override = telegraph_material

    # Four short ticks make the danger radius readable under bodies/projectiles without filling
    # the entire floor area with an opaque disk.
    for tick_index in range(4):
        var angle := TAU * float(tick_index) / 4.0
        var tick := MeshInstance3D.new()
        tick.name = "TelegraphTick_%d" % tick_index
        var tick_mesh := BoxMesh.new()
        tick_mesh.size = Vector3(radius * 0.24, 0.012, maxf(0.035, radius * 0.045))
        tick.mesh = tick_mesh
        tick.position = Vector3(cos(angle) * radius * 0.72, 0.0, sin(angle) * radius * 0.72)
        tick.rotation.y = -angle
        tick.material_override = telegraph_material
        telegraph_visual.add_child(tick)

    var tween := telegraph_visual.create_tween()
    tween.set_parallel(true)
    telegraph_visual.scale = Vector3(0.42, 1.0, 0.42)
    tween.tween_property(telegraph_visual, "scale", Vector3.ONE, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(telegraph_material, "emission_energy_multiplier", 5.8 if kind == "boss" else 4.6, duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.tween_property(telegraph_material, "albedo_color", Color(1.0, 0.07, 0.008, 0.92 if kind == "boss" else 0.78), duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.chain().tween_callback(telegraph_visual.queue_free)

func _spawn_attack_impact(at: Vector3, radius: float) -> void:
    if not spawn_secondary_fx:
        return
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.22, 0.05) if kind == "boss" else Color(0.72, 0.28, 1.0)
    fx.scale_boost = radius * 1.35
    get_tree().current_scene.add_child(fx)
    fx.global_position = at + Vector3(0.0, 0.10, 0.0)

func _begin_regeneration() -> void:
    if dead or not combat_enabled or health <= 0.0 or health >= max_health:
        return
    regeneration_windup = 0.42
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    var pulse := MeshInstance3D.new()
    pulse.name = "RegenerationPulse"
    var mesh := CylinderMesh.new()
    mesh.top_radius = 0.88
    mesh.bottom_radius = 0.88
    mesh.height = 0.022
    pulse.mesh = mesh
    pulse.position = Vector3(0.0, 0.035, 0.0)
    regeneration_material = StandardMaterial3D.new()
    regeneration_material.albedo_color = Color(0.12, 1.0, 0.42, 0.18)
    regeneration_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    regeneration_material.emission_enabled = true
    regeneration_material.emission = Color(0.08, 1.0, 0.34)
    regeneration_material.emission_energy_multiplier = 1.8
    pulse.material_override = regeneration_material
    regeneration_visual = pulse
    add_child(pulse)
    pulse.scale = Vector3(0.48, 1.0, 0.48)
    var tween := pulse.create_tween()
    tween.set_parallel(true)
    tween.tween_property(pulse, "scale", Vector3(1.18, 1.0, 1.18), regeneration_windup).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(regeneration_material, "emission_energy_multiplier", 4.0, regeneration_windup).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)

func _process_regeneration(delta: float) -> void:
    if regeneration_windup <= 0.0:
        return
    regeneration_windup = max(0.0, regeneration_windup - delta)
    velocity = Vector3.ZERO
    if regeneration_windup > 0.0:
        return
    _regenerate()
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    regeneration_visual = null
    regeneration_material = null

func _regenerate() -> void:
    if dead or health <= 0.0 or health >= max_health:
        return
    var healed: float = minf(max_health * 0.035, max_health - health)
    health += healed
    health_changed.emit(health, max_health)

func apply_slow(multiplier: float, duration: float) -> void:
    slow_multiplier = min(slow_multiplier, clampf(multiplier, 0.30, 1.0))
    slow_left = max(slow_left, max(0.0, duration))

func apply_burn(dps: float, duration: float) -> void:
    if dead or dps <= 0.0 or duration <= 0.0:
        return
    burn_dps = maxf(burn_dps, dps)
    burn_left = maxf(burn_left, duration)

func apply_shock(duration: float) -> void:
    if dead or duration <= 0.0:
        return
    var resistance := 0.45 if kind == "boss" else (0.65 if kind == "elite" else 1.0)
    shock_left = maxf(shock_left, duration * resistance)
    velocity = Vector3.ZERO

func _process_status_effects(delta: float) -> void:
    if dead or burn_left <= 0.0 or burn_dps <= 0.0:
        return
    var active_delta := minf(maxf(delta, 0.0), burn_left)
    burn_left = maxf(0.0, burn_left - maxf(delta, 0.0))
    burn_tick_accumulator += active_delta

    const BURN_TICK := 0.25
    while burn_tick_accumulator >= BURN_TICK and not dead:
        burn_tick_accumulator -= BURN_TICK
        take_damage(burn_dps * BURN_TICK, false)

    if burn_left <= 0.0:
        if burn_tick_accumulator > 0.0 and not dead:
            take_damage(burn_dps * burn_tick_accumulator, false)
        burn_tick_accumulator = 0.0
        burn_dps = 0.0

func take_damage(amount: float, critical := false) -> void:
    if dead:
        return
    health -= amount
    health_changed.emit(max(0.0, health), max_health)
    var killed := health <= 0.0
    impact.emit(global_position + Vector3(0.0, 0.72, 0.0), critical, killed, kind == "boss")
    _spawn_damage_number(amount, critical)
    _play_hit_reaction(critical, killed)
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

func hit_reaction_profile() -> Dictionary:
    if kind == "boss":
        return {"id": "boss_hit", "punch": 1.035, "flash": 5.0, "duration": 0.13, "recoil": 0.025}
    if kind == "elite":
        return {"id": "elite_hit", "punch": 1.075, "flash": 6.2, "duration": 0.12, "recoil": 0.055}
    return {"id": "normal_hit", "punch": 1.10, "flash": 7.0, "duration": 0.10, "recoil": 0.085}

func _play_hit_reaction(critical: bool, killed: bool) -> void:
    var visual := get_node_or_null("Visual") as Node3D
    if visual == null:
        return
    var profile := hit_reaction_profile()
    if hit_reaction_tween != null and hit_reaction_tween.is_valid():
        hit_reaction_tween.kill()
    var base_scale := visual.scale
    var punch := float(profile["punch"]) * (1.035 if critical else 1.0)
    var duration := float(profile["duration"])
    var recoil := float(profile["recoil"])
    var base_position := visual.position
    var recoil_direction := Vector3.ZERO
    if target != null and is_instance_valid(target):
        recoil_direction = global_position - target.global_position
        recoil_direction.y = 0.0
        if recoil_direction.length_squared() > 0.001:
            recoil_direction = recoil_direction.normalized() * recoil
    if hit_flash_visual != null:
        hit_flash_visual.visible = true
        hit_flash_material.emission_energy_multiplier = float(profile["flash"]) * (1.18 if critical else 1.0)
        hit_flash_material.albedo_color.a = 0.30 if critical else 0.20
    hit_reaction_tween = create_tween()
    hit_reaction_tween.set_parallel(true)
    hit_reaction_tween.tween_property(visual, "scale", base_scale * punch, duration * 0.34).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.tween_property(visual, "position", base_position + recoil_direction, duration * 0.34).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if hit_flash_visual != null:
        hit_reaction_tween.tween_property(hit_flash_material, "emission_energy_multiplier", 0.0, duration)
        hit_reaction_tween.tween_property(hit_flash_material, "albedo_color:a", 0.0, duration)
    hit_reaction_tween.set_parallel(false)
    hit_reaction_tween.tween_property(visual, "scale", base_scale * (1.04 if killed else 1.0), duration * 0.66).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.parallel().tween_property(visual, "position", base_position, duration * 0.66).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    hit_reaction_tween.tween_callback(func() -> void:
        if hit_flash_visual != null:
            hit_flash_visual.visible = false
    )

func _build_hit_flash() -> void:
    hit_flash_visual = MeshInstance3D.new()
    hit_flash_visual.name = "HitFlash"
    var mesh := CylinderMesh.new()
    var scale_factor := 1.0
    if kind == "boss": scale_factor = 1.62
    elif kind in ["elite", "brute", "charger"]: scale_factor = 1.18
    mesh.top_radius = 0.46 * scale_factor
    mesh.bottom_radius = 0.52 * scale_factor
    mesh.height = 1.28 * scale_factor
    hit_flash_visual.mesh = mesh
    hit_flash_visual.position.y = 0.66 * scale_factor
    hit_flash_material = StandardMaterial3D.new()
    hit_flash_material.albedo_color = Color(1.0, 0.86, 0.58, 0.0)
    hit_flash_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    hit_flash_material.emission_enabled = true
    hit_flash_material.emission = Color(1.0, 0.58, 0.16)
    hit_flash_material.emission_energy_multiplier = 0.0
    hit_flash_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    hit_flash_visual.material_override = hit_flash_material
    hit_flash_visual.visible = false
    add_child(hit_flash_visual)

func _spawn_damage_number(amount: float, critical: bool) -> void:
    if get_tree() == null or get_tree().current_scene == null:
        return
    var number := Label3D.new()
    number.name = "DamageNumber_%d" % Time.get_ticks_usec()
    number.text = "%d" % int(round(amount))
    number.font_size = 34 if critical else 26
    number.outline_size = 8 if critical else 6
    number.modulate = Color(1.0, 0.72, 0.12) if critical else Color(0.92, 0.97, 1.0)
    number.outline_modulate = Color(0.02, 0.03, 0.05, 0.96)
    number.billboard = BaseMaterial3D.BILLBOARD_ENABLED
    number.no_depth_test = true
    number.pixel_size = 0.0038 if critical else 0.0032
    get_tree().current_scene.add_child(number)
    number.global_position = global_position + Vector3(0.0, 1.28, 0.0)

    var rise := 0.82 if critical else 0.62
    var tween := number.create_tween()
    tween.set_parallel(true)
    tween.tween_property(number, "global_position", number.global_position + Vector3(0.0, rise, 0.0), 0.58).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(number, "modulate:a", 0.0, 0.58).set_delay(0.18)
    tween.chain().tween_callback(number.queue_free)

func _build_visual() -> void:
    authored_visual = DZAssetLibrary.enemy(kind)
    if authored_visual != null:
        authored_visual.name = "Visual"
        var scale_factor := 1.0
        match kind:
            "runner": scale_factor = 0.86
            "brute": scale_factor = 1.22
            "elite": scale_factor = 1.15
            "charger": scale_factor = 1.18
            "harrier": scale_factor = 0.94
            "regenerator": scale_factor = 1.10
            "boss": scale_factor = 1.62
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
        "charger":
            accent = Color(1.0, 0.32, 0.08)
            _add_brute_shoulders(accent)
        "harrier":
            accent = Color(0.12, 0.82, 1.0)
            _add_runner_blades(accent)
        "regenerator":
            accent = Color(0.18, 1.0, 0.48)
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
    var mat := _signature_material(color, 3.0)
    for side in [-1.0, 1.0]:
        var blade := MeshInstance3D.new()
        var mesh := BoxMesh.new()
        # Extend the signature in the ground plane so it reads from the gameplay camera,
        # rather than relying on vertical geometry that collapses in top-down projection.
        mesh.size = Vector3(0.085, 0.30, 0.38)
        blade.mesh = mesh
        blade.name = "RunnerBladeL" if side < 0.0 else "RunnerBladeR"
        blade.position = Vector3(side * 0.43, 0.82, 0.02)
        blade.rotation_degrees = Vector3(0.0, side * 18.0, side * -20.0)
        blade.material_override = mat
        add_child(blade)
    _add_eye_beacon(color, Vector3(0.0, 1.54, -0.30), 0.060)

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
    var mat := _signature_material(color, 3.4)
    for side in [-1.0, 1.0]:
        var wing := MeshInstance3D.new()
        var wing_mesh := BoxMesh.new()
        wing_mesh.size = Vector3(0.16, 0.30, 0.58)
        wing.mesh = wing_mesh
        wing.name = "BossWingL" if side < 0.0 else "BossWingR"
        wing.position = Vector3(side * 0.72, 1.16, 0.04)
        wing.rotation_degrees = Vector3(0.0, side * 18.0, side * -16.0)
        wing.material_override = mat
        add_child(wing)

        var horn := MeshInstance3D.new()
        var horn_mesh := BoxMesh.new()
        horn_mesh.size = Vector3(0.12, 0.62, 0.22)
        horn.mesh = horn_mesh
        horn.name = "BossHornL" if side < 0.0 else "BossHornR"
        horn.position = Vector3(side * 0.52, 1.80, 0.06)
        horn.rotation_degrees.z = side * -32.0
        horn.material_override = mat
        add_child(horn)

    var core := MeshInstance3D.new()
    var core_mesh := SphereMesh.new()
    core_mesh.radius = 0.145
    core_mesh.height = 0.29
    core.mesh = core_mesh
    core.name = "BossCore"
    core.position = Vector3(0.0, 1.32, -0.48)
    core.material_override = _signature_material(Color(1.0, 0.30, 0.04), 4.6)
    add_child(core)
    _add_eye_beacon(color, Vector3(0.0, 1.82, -0.46), 0.105)

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
    _play_hit_reaction(critical, killed)
```

## File: scripts/EnemyProjectile.gd
```
class_name DZEnemyProjectile
extends Node3D

var velocity := Vector3.ZERO
var damage := 0.0
var target: Node3D
var lifetime := 4.0
var hit_radius := 0.72
var combat_enabled := true
var resolved := false

func configure(target_position: Vector3, chase_target: Node3D, amount: float, speed := 8.6) -> void:
    target = chase_target
    damage = amount
    var direction := target_position - global_position
    direction.y = 0.0
    if direction.length_squared() < 0.001:
        direction = Vector3.FORWARD
    velocity = direction.normalized() * speed

func _ready() -> void:
    add_to_group("hostile_projectiles")
    _build_visual()

func _physics_process(delta: float) -> void:
    if not combat_enabled or resolved:
        return
    lifetime -= delta
    if lifetime <= 0.0:
        queue_free()
        return
    global_position += velocity * delta
    if target == null or not is_instance_valid(target):
        return
    var offset := target.global_position - global_position
    offset.y = 0.0
    if offset.length() <= hit_radius:
        _hit_target()

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _hit_target() -> void:
    if resolved:
        return
    resolved = true
    if target != null and is_instance_valid(target) and target.has_method("take_damage"):
        target.take_damage(damage)
    queue_free()

func _build_visual() -> void:
    var core := MeshInstance3D.new()
    core.name = "HarrierBoltCore"
    var core_mesh := SphereMesh.new()
    core_mesh.radius = 0.13
    core_mesh.height = 0.26
    core.mesh = core_mesh
    var core_mat := StandardMaterial3D.new()
    core_mat.albedo_color = Color(0.08, 0.78, 1.0)
    core_mat.emission_enabled = true
    core_mat.emission = Color(0.04, 0.66, 1.0)
    core_mat.emission_energy_multiplier = 5.2
    core.material_override = core_mat
    add_child(core)

    var halo := MeshInstance3D.new()
    halo.name = "HarrierBoltHalo"
    var halo_mesh := SphereMesh.new()
    halo_mesh.radius = 0.24
    halo_mesh.height = 0.48
    halo.mesh = halo_mesh
    var halo_mat := StandardMaterial3D.new()
    halo_mat.albedo_color = Color(0.08, 0.72, 1.0, 0.18)
    halo_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    halo_mat.emission_enabled = true
    halo_mat.emission = Color(0.04, 0.55, 1.0)
    halo_mat.emission_energy_multiplier = 2.6
    halo.material_override = halo_mat
    add_child(halo)

    var trail := MeshInstance3D.new()
    trail.name = "HarrierBoltTrail"
    var trail_mesh := BoxMesh.new()
    trail_mesh.size = Vector3(0.07, 0.07, 0.78)
    trail.mesh = trail_mesh
    trail.position = Vector3(0.0, 0.0, 0.42)
    var trail_mat := StandardMaterial3D.new()
    trail_mat.albedo_color = Color(0.05, 0.64, 1.0, 0.42)
    trail_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    trail_mat.emission_enabled = true
    trail_mat.emission = Color(0.04, 0.58, 1.0)
    trail_mat.emission_energy_multiplier = 3.8
    trail_mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    trail.material_override = trail_mat
    add_child(trail)

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)
```

## File: scripts/GameSettings.gd
```
class_name DZGameSettings
extends RefCounted

const DEFAULTS := {
    "master_volume": 0.85,
    "sfx_volume": 0.90
}

static func save(path: String, settings: Dictionary) -> Error:
    var config := ConfigFile.new()
    config.set_value("audio", "master_volume", clampf(float(settings.get("master_volume", DEFAULTS["master_volume"])), 0.0, 1.0))
    config.set_value("audio", "sfx_volume", clampf(float(settings.get("sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0))
    return config.save(path)

static func load_settings(path: String) -> Dictionary:
    var result := DEFAULTS.duplicate(true)
    var config := ConfigFile.new()
    if config.load(path) != OK:
        return result
    result["master_volume"] = clampf(float(config.get_value("audio", "master_volume", DEFAULTS["master_volume"])), 0.0, 1.0)
    result["sfx_volume"] = clampf(float(config.get_value("audio", "sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0)
    return result
```

## File: scripts/Haptics.gd
```
extends RefCounted

static func pattern_for(kind: String) -> int:
    match kind:
        "hit":
            return 18
        "critical":
            return 38
        "boss":
            return 72
        _:
            return 0

static func event_for_impact(critical: bool, killed: bool, boss: bool) -> String:
    if boss:
        return "boss"
    if critical or killed:
        return "critical"
    return "hit"

static func amplitude_for(kind: String) -> float:
    match kind:
        "hit":
            return 0.32
        "critical":
            return 0.58
        "boss":
            return 0.82
        _:
            return 0.0

static func pulse(kind: String) -> void:
    var duration := pattern_for(kind)
    if duration <= 0:
        return
    Input.vibrate_handheld(duration, amplitude_for(kind))
```

## File: scripts/Hud.gd
```
class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)
signal restart_requested
signal pause_requested
signal resume_requested
signal master_volume_changed(value: float)
signal sfx_volume_changed(value: float)

var hp_bar: ProgressBar
var health_bar: ProgressBar
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
var low_health_panel: PanelContainer
var low_health_label: Label
var threat_panel: PanelContainer
var threat_label: Label
var pause_panel: PanelContainer
var pause_button: Button
var master_volume: HSlider
var sfx_volume: HSlider
var impact_flash: ColorRect
var impact_flash_tween: Tween
var damage_vignette: ColorRect
var damage_vignette_tween: Tween

func _ready() -> void:
    process_mode = Node.PROCESS_MODE_ALWAYS
    _build()

func pulse_damage_screen() -> void:
    if damage_vignette == null:
        return
    if damage_vignette_tween != null and damage_vignette_tween.is_valid():
        damage_vignette_tween.kill()
    damage_vignette.visible = true
    damage_vignette.modulate.a = 1.0
    damage_vignette_tween = damage_vignette.create_tween()
    damage_vignette_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    damage_vignette_tween.tween_property(damage_vignette, "modulate:a", 0.0, 0.26).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    damage_vignette_tween.tween_callback(func() -> void:
        if damage_vignette != null:
            damage_vignette.visible = false
            damage_vignette.modulate.a = 1.0
    )

func set_health(value: float, maximum: float) -> void:
    hp_bar.max_value = max(1.0, maximum)
    hp_bar.value = value
    var ratio: float = clampf(value / max(1.0, maximum), 0.0, 1.0)
    low_health_panel.visible = value > 0.0 and ratio <= 0.30
    if low_health_panel.visible:
        low_health_label.text = "CRITICAL INTEGRITY  •  %d%%" % int(round(ratio * 100.0))

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

func set_offscreen_threat(direction: Vector2, threat_kind: String, distance: float) -> void:
    if direction.length_squared() < 0.001:
        hide_offscreen_threat()
        return
    var arrow := _direction_arrow(direction.normalized())
    threat_label.text = "%s  %s  %dm" % [arrow, threat_kind.to_upper(), int(round(distance))]
    threat_panel.visible = true

func hide_offscreen_threat() -> void:
    threat_panel.visible = false

func _direction_arrow(direction: Vector2) -> String:
    var angle := atan2(direction.y, direction.x)
    var octant := int(round(angle / (PI / 4.0)))
    match octant:
        0: return "→"
        1: return "↘"
        2: return "↓"
        3: return "↙"
        4, -4: return "←"
        -3: return "↖"
        -2: return "↑"
        -1: return "↗"
        _: return "→"

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

func show_pause_settings() -> void:
    pause_panel.visible = true

func hide_pause_settings() -> void:
    pause_panel.visible = false

func show_impact_flash(critical: bool, killed: bool, boss: bool) -> void:
    if impact_flash == null:
        return
    if impact_flash_tween != null and impact_flash_tween.is_valid():
        impact_flash_tween.kill()
    var alpha := 0.055
    var tint := Color(0.68, 0.90, 1.0, alpha)
    if critical:
        alpha = 0.10
        tint = Color(1.0, 0.74, 0.20, alpha)
    if killed:
        alpha = maxf(alpha, 0.13)
        tint = Color(1.0, 0.38, 0.16, alpha)
    if boss:
        alpha = maxf(alpha, 0.18)
        tint = Color(1.0, 0.12, 0.055, alpha)
    impact_flash.color = tint
    impact_flash.visible = true
    impact_flash_tween = create_tween()
    impact_flash_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    impact_flash_tween.tween_property(impact_flash, "color:a", 0.0, 0.16 if boss else 0.11)
    impact_flash_tween.tween_callback(func() -> void:
        if impact_flash != null:
            impact_flash.visible = false
    )

func show_game_over(kills: int, level: int, elapsed: float) -> void:
    wave_label.text = "RUN TERMINATED"
    upgrade_panel.visible = false
    boss_panel.visible = false
    var minutes := int(elapsed) / 60
    var seconds := int(elapsed) % 60
    game_over_summary.text = "LEVEL %d   •   KILLS %d   •   %02d:%02d" % [level, kills, minutes, seconds]
    low_health_panel.visible = false
    threat_panel.visible = false
    game_over_panel.visible = true

func _build() -> void:
    var root := Control.new()
    root.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    add_child(root)

    impact_flash = ColorRect.new()
    impact_flash.name = "ImpactFlash"
    impact_flash.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    impact_flash.color = Color(1.0, 1.0, 1.0, 0.0)
    impact_flash.mouse_filter = Control.MOUSE_FILTER_IGNORE
    impact_flash.visible = false
    root.add_child(impact_flash)

    damage_vignette = ColorRect.new()
    damage_vignette.name = "DamageVignette"
    damage_vignette.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    damage_vignette.color = Color(0.58, 0.015, 0.0, 0.30)
    damage_vignette.mouse_filter = Control.MOUSE_FILTER_IGNORE
    damage_vignette.visible = false
    root.add_child(damage_vignette)

    var vital_panel := PanelContainer.new()
    vital_panel.name = "VitalPanel"
    vital_panel.position = Vector2(22, 18)
    vital_panel.size = Vector2(360, 86)
    vital_panel.custom_minimum_size = Vector2(340, 82)
    vital_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(vital_panel)

    var vital_style := StyleBoxFlat.new()
    vital_style.bg_color = Color(0.010, 0.020, 0.028, 0.94)
    vital_style.border_color = Color(0.12, 0.52, 0.72, 0.62)
    vital_style.set_border_width_all(1)
    vital_style.border_width_left = 3
    vital_style.corner_radius_top_left = 6
    vital_style.corner_radius_top_right = 6
    vital_style.corner_radius_bottom_left = 6
    vital_style.corner_radius_bottom_right = 6
    vital_style.content_margin_left = 12.0
    vital_style.content_margin_right = 11.0
    vital_style.content_margin_top = 6.0
    vital_style.content_margin_bottom = 6.0
    vital_panel.add_theme_stylebox_override("panel", vital_style)

    var vital_stack := VBoxContainer.new()
    vital_stack.name = "VitalStack"
    vital_stack.add_theme_constant_override("separation", 3)
    vital_panel.add_child(vital_stack)

    var header_row := HBoxContainer.new()
    header_row.name = "CombatHeader"
    vital_stack.add_child(header_row)

    var combat_link := Label.new()
    combat_link.name = "CombatLinkLabel"
    combat_link.text = "SURVIVOR // COMBAT LINK"
    combat_link.add_theme_font_size_override("font_size", 11)
    combat_link.modulate = Color(0.28, 0.78, 0.96)
    header_row.add_child(combat_link)

    var header_spacer := Control.new()
    header_spacer.size_flags_horizontal = Control.SIZE_EXPAND_FILL
    header_row.add_child(header_spacer)

    var signal_label := Label.new()
    signal_label.name = "SignalLabel"
    signal_label.text = "ONLINE"
    signal_label.add_theme_font_size_override("font_size", 10)
    signal_label.modulate = Color(0.48, 0.92, 0.68)
    header_row.add_child(signal_label)

    var vital_accent := ColorRect.new()
    vital_accent.name = "VitalAccent"
    vital_accent.color = Color(0.10, 0.58, 0.80, 0.90)
    vital_accent.custom_minimum_size = Vector2(74, 2)
    vital_accent.mouse_filter = Control.MOUSE_FILTER_IGNORE
    vital_stack.add_child(vital_accent)

    hp_bar = ProgressBar.new()
    hp_bar.name = "HealthBar"
    hp_bar.custom_minimum_size = Vector2(320, 18)
    hp_bar.show_percentage = false
    var hp_bg := StyleBoxFlat.new()
    hp_bg.bg_color = Color(0.06, 0.075, 0.085, 0.94)
    hp_bg.corner_radius_top_left = 3
    hp_bg.corner_radius_top_right = 3
    hp_bg.corner_radius_bottom_left = 3
    hp_bg.corner_radius_bottom_right = 3
    var hp_fill := StyleBoxFlat.new()
    hp_fill.bg_color = Color(0.16, 0.72, 0.88, 0.98)
    hp_fill.corner_radius_top_left = 3
    hp_fill.corner_radius_top_right = 3
    hp_fill.corner_radius_bottom_left = 3
    hp_fill.corner_radius_bottom_right = 3
    hp_bar.add_theme_stylebox_override("background", hp_bg)
    hp_bar.add_theme_stylebox_override("fill", hp_fill)
    vital_stack.add_child(hp_bar)
    health_bar = hp_bar

    xp_bar = ProgressBar.new()
    xp_bar.name = "XpBar"
    xp_bar.custom_minimum_size = Vector2(320, 8)
    xp_bar.show_percentage = false
    var xp_bg := StyleBoxFlat.new()
    xp_bg.bg_color = Color(0.045, 0.055, 0.065, 0.90)
    xp_bg.corner_radius_top_left = 2
    xp_bg.corner_radius_top_right = 2
    xp_bg.corner_radius_bottom_left = 2
    xp_bg.corner_radius_bottom_right = 2
    var xp_fill := StyleBoxFlat.new()
    xp_fill.bg_color = Color(0.52, 0.36, 0.92, 0.96)
    xp_fill.corner_radius_top_left = 2
    xp_fill.corner_radius_top_right = 2
    xp_fill.corner_radius_bottom_left = 2
    xp_fill.corner_radius_bottom_right = 2
    xp_bar.add_theme_stylebox_override("background", xp_bg)
    xp_bar.add_theme_stylebox_override("fill", xp_fill)
    vital_stack.add_child(xp_bar)

    status_label = Label.new()
    status_label.name = "CombatStatus"
    status_label.text = "LV 1   KILLS 0"
    status_label.add_theme_font_size_override("font_size", 15)
    status_label.modulate = Color(0.82, 0.90, 0.94)
    vital_stack.add_child(status_label)

    var wave_panel := PanelContainer.new()
    wave_panel.name = "WavePanel"
    wave_panel.set_anchors_preset(Control.PRESET_CENTER_TOP)
    wave_panel.position = Vector2(-170, 18)
    wave_panel.size = Vector2(340, 42)
    wave_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(wave_panel)
    var wave_style := StyleBoxFlat.new()
    wave_style.bg_color = Color(0.010, 0.020, 0.028, 0.76)
    wave_style.border_color = Color(0.16, 0.56, 0.72, 0.46)
    wave_style.border_width_bottom = 2
    wave_style.corner_radius_bottom_left = 7
    wave_style.corner_radius_bottom_right = 7
    wave_panel.add_theme_stylebox_override("panel", wave_style)

    wave_label = Label.new()
    wave_label.name = "WaveLabel"
    wave_label.text = "QUARANTINE YARD"
    wave_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    wave_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    wave_label.add_theme_font_size_override("font_size", 21)
    wave_label.modulate = Color(0.90, 0.95, 0.98)
    wave_panel.add_child(wave_label)

    pause_button = Button.new()
    pause_button.name = "PauseButton"
    pause_button.text = "Ⅱ"
    pause_button.set_anchors_preset(Control.PRESET_TOP_RIGHT)
    pause_button.position = Vector2(-68, 18)
    pause_button.size = Vector2(46, 46)
    pause_button.add_theme_font_size_override("font_size", 18)
    var pause_normal := StyleBoxFlat.new()
    pause_normal.bg_color = Color(0.010, 0.020, 0.028, 0.90)
    pause_normal.border_color = Color(0.16, 0.56, 0.72, 0.46)
    pause_normal.set_border_width_all(1)
    pause_normal.corner_radius_top_left = 6
    pause_normal.corner_radius_top_right = 6
    pause_normal.corner_radius_bottom_left = 6
    pause_normal.corner_radius_bottom_right = 6
    var pause_hover := pause_normal.duplicate()
    pause_hover.bg_color = Color(0.025, 0.075, 0.10, 0.96)
    pause_hover.border_color = Color(0.24, 0.78, 1.0, 0.78)
    pause_button.add_theme_stylebox_override("normal", pause_normal)
    pause_button.add_theme_stylebox_override("hover", pause_hover)
    pause_button.add_theme_stylebox_override("pressed", pause_hover)
    pause_button.add_theme_color_override("font_color", Color(0.78, 0.90, 0.96))
    pause_button.pressed.connect(func() -> void: pause_requested.emit())
    add_child(pause_button)

    pause_panel = PanelContainer.new()
    pause_panel.name = "PausePanel"
    pause_panel.set_anchors_preset(Control.PRESET_CENTER)
    pause_panel.position = Vector2(-250, -210)
    pause_panel.size = Vector2(500, 420)
    pause_panel.visible = false
    add_child(pause_panel)
    var pause_box := VBoxContainer.new()
    pause_box.alignment = BoxContainer.ALIGNMENT_CENTER
    pause_box.add_theme_constant_override("separation", 18)
    pause_panel.add_child(pause_box)
    var pause_title := Label.new()
    pause_title.text = "SYSTEM PAUSED"
    pause_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    pause_title.add_theme_font_size_override("font_size", 30)
    pause_title.modulate = Color(0.72, 0.92, 1.0)
    pause_box.add_child(pause_title)
    var master_label := Label.new()
    master_label.text = "MASTER VOLUME"
    master_label.add_theme_font_size_override("font_size", 16)
    pause_box.add_child(master_label)
    master_volume = HSlider.new()
    master_volume.name = "MasterVolume"
    master_volume.min_value = 0.0
    master_volume.max_value = 1.0
    master_volume.step = 0.05
    master_volume.value = 0.85
    master_volume.custom_minimum_size = Vector2(360, 42)
    master_volume.value_changed.connect(func(value: float) -> void: master_volume_changed.emit(value))
    pause_box.add_child(master_volume)
    var sfx_label := Label.new()
    sfx_label.text = "SFX VOLUME"
    sfx_label.add_theme_font_size_override("font_size", 16)
    pause_box.add_child(sfx_label)
    sfx_volume = HSlider.new()
    sfx_volume.name = "SfxVolume"
    sfx_volume.min_value = 0.0
    sfx_volume.max_value = 1.0
    sfx_volume.step = 0.05
    sfx_volume.value = 0.90
    sfx_volume.custom_minimum_size = Vector2(360, 42)
    sfx_volume.value_changed.connect(func(value: float) -> void: sfx_volume_changed.emit(value))
    pause_box.add_child(sfx_volume)
    var resume_button := Button.new()
    resume_button.name = "ResumeButton"
    resume_button.text = "RESUME"
    resume_button.custom_minimum_size = Vector2(280, 62)
    resume_button.add_theme_font_size_override("font_size", 21)
    resume_button.pressed.connect(func() -> void: resume_requested.emit())
    pause_box.add_child(resume_button)
    var pause_style := StyleBoxFlat.new()
    pause_style.bg_color = Color(0.018, 0.028, 0.038, 0.98)
    pause_style.border_color = Color(0.20, 0.78, 1.0, 0.72)
    pause_style.set_border_width_all(2)
    pause_style.corner_radius_top_left = 12
    pause_style.corner_radius_top_right = 12
    pause_style.corner_radius_bottom_left = 12
    pause_style.corner_radius_bottom_right = 12
    pause_panel.add_theme_stylebox_override("panel", pause_style)

    low_health_panel = PanelContainer.new()
    low_health_panel.name = "LowHealthPanel"
    low_health_panel.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
    low_health_panel.position = Vector2(-210, -92)
    low_health_panel.size = Vector2(420, 52)
    low_health_panel.visible = false
    low_health_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(low_health_panel)
    low_health_label = Label.new()
    low_health_label.name = "LowHealthLabel"
    low_health_label.text = "CRITICAL INTEGRITY"
    low_health_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    low_health_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    low_health_label.add_theme_font_size_override("font_size", 19)
    low_health_label.modulate = Color(1.0, 0.58, 0.44)
    low_health_panel.add_child(low_health_label)
    var low_health_style := StyleBoxFlat.new()
    low_health_style.bg_color = Color(0.16, 0.015, 0.01, 0.96)
    low_health_style.border_color = Color(1.0, 0.18, 0.08, 0.98)
    low_health_style.set_border_width_all(2)
    low_health_style.corner_radius_top_left = 8
    low_health_style.corner_radius_top_right = 8
    low_health_style.corner_radius_bottom_left = 8
    low_health_style.corner_radius_bottom_right = 8
    low_health_panel.add_theme_stylebox_override("panel", low_health_style)

    threat_panel = PanelContainer.new()
    threat_panel.name = "ThreatPanel"
    threat_panel.set_anchors_preset(Control.PRESET_CENTER_RIGHT)
    threat_panel.position = Vector2(-210, -34)
    threat_panel.size = Vector2(180, 68)
    threat_panel.visible = false
    threat_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(threat_panel)
    threat_label = Label.new()
    threat_label.name = "ThreatLabel"
    threat_label.text = "→  ELITE  18m"
    threat_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    threat_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    threat_label.add_theme_font_size_override("font_size", 18)
    threat_label.modulate = Color(1.0, 0.56, 0.22)
    threat_panel.add_child(threat_label)
    var threat_style := StyleBoxFlat.new()
    threat_style.bg_color = Color(0.06, 0.025, 0.01, 0.88)
    threat_style.border_color = Color(1.0, 0.42, 0.08, 0.86)
    threat_style.set_border_width_all(2)
    threat_style.corner_radius_top_left = 8
    threat_style.corner_radius_top_right = 8
    threat_style.corner_radius_bottom_left = 8
    threat_style.corner_radius_bottom_right = 8
    threat_panel.add_theme_stylebox_override("panel", threat_style)

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
    restart_button.pressed.connect(func() -> void: restart_requested.emit())
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
    boss_panel.name = "BossPanel"
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
    upgrade_panel.name = "UpgradePanel"
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
        "berserker": return "✦"
        "overclock": return "⚡"
        "fortress": return "⬢"
        "scatter_protocol": return "⋰"
        "rail_protocol": return "━"
        "inferno_protocol": return "▲"
        "cryo_protocol": return "◇"
        "arc_protocol": return "⌁"
        _: return "◆"

func _upgrade_color(id: String) -> Color:
    match id:
        "damage", "multishot": return Color(1.0, 0.66, 0.18)
        "rate", "speed": return Color(0.18, 0.86, 1.0)
        "health": return Color(0.32, 0.94, 0.52)
        "projectile": return Color(0.76, 0.82, 1.0)
        "berserker": return Color(1.0, 0.22, 0.12)
        "overclock": return Color(1.0, 0.82, 0.18)
        "fortress": return Color(0.38, 0.86, 0.72)
        "scatter_protocol": return Color(1.0, 0.56, 0.18)
        "rail_protocol": return Color(0.72, 0.58, 1.0)
        "inferno_protocol": return Color(1.0, 0.24, 0.035)
        "cryo_protocol": return Color(0.30, 0.90, 1.0)
        "arc_protocol": return Color(0.64, 0.42, 1.0)
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
var ring_instance: MeshInstance3D
var core_material: StandardMaterial3D
var ring_material: StandardMaterial3D

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    mesh_instance.name = "ImpactCore"
    var sphere := SphereMesh.new()
    sphere.radius = 0.18
    sphere.height = 0.36
    mesh_instance.mesh = sphere
    core_material = _make_material(color, 4.2)
    mesh_instance.material_override = core_material
    add_child(mesh_instance)

    ring_instance = MeshInstance3D.new()
    ring_instance.name = "ImpactRing"
    var ring := TorusMesh.new()
    ring.inner_radius = 0.24
    ring.outer_radius = 0.34
    ring_instance.mesh = ring
    ring_instance.rotation_degrees.x = 90.0
    ring_material = _make_material(color.lightened(0.18), 3.4)
    ring_instance.material_override = ring_material
    add_child(ring_instance)

    var sparks := GPUParticles3D.new()
    sparks.name = "ImpactSparks"
    sparks.amount = 8
    sparks.lifetime = 0.22
    sparks.one_shot = true
    sparks.explosiveness = 1.0
    sparks.randomness = 0.35
    sparks.local_coords = false

    var particle_material := ParticleProcessMaterial.new()
    particle_material.direction = Vector3(0.0, 1.0, 0.0)
    particle_material.spread = 70.0
    particle_material.initial_velocity_min = 2.2
    particle_material.initial_velocity_max = 4.2
    particle_material.gravity = Vector3(0.0, -7.0, 0.0)
    particle_material.scale_min = 0.45
    particle_material.scale_max = 1.0
    particle_material.color = color
    sparks.process_material = particle_material

    var spark_mesh := QuadMesh.new()
    spark_mesh.size = Vector2(0.055, 0.16)
    var spark_material := StandardMaterial3D.new()
    spark_material.albedo_color = color
    spark_material.emission_enabled = true
    spark_material.emission = color
    spark_material.emission_energy_multiplier = 4.5
    spark_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    spark_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    spark_material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    spark_mesh.material = spark_material
    sparks.draw_pass_1 = spark_mesh
    add_child(sparks)
    sparks.emitting = true

func _make_material(tint: Color, energy: float) -> StandardMaterial3D:
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = energy
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return material

func _process(delta: float) -> void:
    age += delta
    var t: float = clampf(age / life, 0.0, 1.0)
    scale = Vector3.ONE * lerp(0.55, 2.2 * scale_boost, t)
    _fade_material(core_material, t)
    _fade_material(ring_material, t)
    if ring_instance != null:
        ring_instance.scale = Vector3.ONE * lerp(0.72, 1.45, t)
    if age >= life:
        queue_free()

func _fade_material(material: StandardMaterial3D, t: float) -> void:
    if material == null:
        return
    var faded := material.albedo_color
    faded.a = 1.0 - t
    material.albedo_color = faded
    material.emission_energy_multiplier = lerp(4.0, 0.5, t)
```

## File: scripts/Main.gd
```
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
    hud.set_progress(xp, xp_next, level, kills, elapsed)
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
    _build_authored_barrier_clusters()
    _build_authored_world_dressing()
    _build_perimeter_street_lights()
    _build_perimeter_beacons()

func _build_floor_panels() -> void:
    # Low-profile industrial plates break the large flat center without adding collision or
    # competing with enemies/projectiles. Their low contrast keeps the combat lane readable.
    var plate_material := StandardMaterial3D.new()
    plate_material.albedo_color = Color(0.065, 0.083, 0.092)
    plate_material.metallic = 0.18
    plate_material.roughness = 0.78

    var edge_material := StandardMaterial3D.new()
    edge_material.albedo_color = Color(0.035, 0.22, 0.27)
    edge_material.emission_enabled = true
    edge_material.emission = Color(0.015, 0.16, 0.21)
    edge_material.emission_energy_multiplier = 0.34
    edge_material.roughness = 0.70

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
        mesh.size = Vector3(3.2 if index % 2 == 0 else 2.6, 0.018, 1.45)
        plate.mesh = mesh
        plate.position = placements[index]
        plate.rotation.y = deg_to_rad(float((index * 23) % 35 - 17))
        plate.material_override = plate_material if index % 3 else edge_material
        add_child(plate)

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

func _build_authored_barrier_clusters() -> void:
    # Keep authored cover visible at the arena edge without letting the large source meshes
    # dominate the phone framing. The clusters now read as perimeter fortification, not walls.
    var clusters := [
        {"center": Vector3(-21.0, 0.0, -14.2), "rotation": 0.18},
        {"center": Vector3(20.6, 0.0, -13.8), "rotation": -0.28},
        {"center": Vector3(-20.2, 0.0, 15.0), "rotation": 0.72},
        {"center": Vector3(21.2, 0.0, 14.6), "rotation": -0.66}
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
            barrier.scale = Vector3.ONE * (0.48 + float(item_index % 3) * 0.035)
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
var damage_pulse: MeshInstance3D
var damage_pulse_material: StandardMaterial3D
var muzzle_flash: MeshInstance3D
var muzzle_flash_material: StandardMaterial3D
var muzzle_flash_tween: Tween
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
    if id == "multishot" and weapon_profile == "rail":
        return false
    if not id.ends_with("_protocol"):
        return true
    return applied_protocols.is_empty()

func _apply_weapon_profile_data(profile_id: String, data: Dictionary) -> void:
    weapon_profile = profile_id
    if data.has("tint"):
        weapon_tint = data["tint"]

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
    _trigger_muzzle_flash()
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
    _build_player_marker()
    _build_muzzle_flash()
    _build_damage_feedback()

func _build_player_marker() -> void:
    var marker_mat := StandardMaterial3D.new()
    marker_mat.albedo_color = Color(0.05, 0.72, 1.0, 0.78)
    marker_mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    marker_mat.emission_enabled = true
    marker_mat.emission = Color(0.025, 0.42, 0.72)
    marker_mat.emission_energy_multiplier = 1.9
    marker_mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var ring := MeshInstance3D.new()
    ring.name = "PlayerMarkerRing"
    var ring_mesh := TorusMesh.new()
    ring_mesh.inner_radius = 0.53
    ring_mesh.outer_radius = 0.60
    ring_mesh.rings = 40
    ring_mesh.ring_segments = 8
    ring.mesh = ring_mesh
    ring.position.y = 0.045
    ring.material_override = marker_mat
    add_child(ring)

    var aim_tick := MeshInstance3D.new()
    aim_tick.name = "PlayerAimTick"
    var tick_mesh := BoxMesh.new()
    tick_mesh.size = Vector3(0.10, 0.018, 0.34)
    aim_tick.mesh = tick_mesh
    aim_tick.position = Vector3(0.0, 0.055, -0.73)
    aim_tick.material_override = marker_mat
    add_child(aim_tick)

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
    muzzle_flash.scale = Vector3(0.58, 0.42, 0.82)
    if muzzle_flash_material != null:
        muzzle_flash_material.emission_energy_multiplier = 6.2
    muzzle_flash_tween = create_tween()
    muzzle_flash_tween.set_parallel(true)
    muzzle_flash_tween.tween_property(muzzle_flash, "scale", Vector3(1.22, 0.76, 1.72), 0.055).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if muzzle_flash_material != null:
        muzzle_flash_tween.tween_property(muzzle_flash_material, "emission_energy_multiplier", 1.0, 0.055)
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
var pierce_remaining := 0
var splash_radius := 0.0
var chain_targets := 0
var slow_multiplier := 1.0
var slow_duration := 0.0
var hit_enemy_ids := {}
var spawn_secondary_fx := true
var combat_enabled := true
var configured_origin := Vector3.ZERO
var has_configured_origin := false

func setup(origin: Vector3, direction: Vector3, speed: float, shot_damage: float, shot_tint: Color,
        profile := "vanguard") -> void:
    configured_origin = origin
    has_configured_origin = true
    if is_inside_tree():
        global_position = origin
    velocity = direction.normalized() * speed
    damage = shot_damage
    tint = shot_tint
    visual_profile = profile
    _apply_profile(profile)

static func protocol_pierce_budget(profile: String) -> int:
    return 2 if profile == "rail" else 0

static func protocol_splash_radius(profile: String) -> float:
    return 1.85 if profile == "inferno" else 0.0

static func protocol_chain_targets(profile: String) -> int:
    return 2 if profile == "arc" else 0

static func protocol_slow(profile: String) -> Vector2:
    return Vector2(0.62, 1.6) if profile == "cryo" else Vector2(1.0, 0.0)

func _apply_profile(profile: String) -> void:
    var feedback := DZWeaponProfiles.profile(profile)
    trail_length = float(feedback.get("trail_length", 0.55))
    impact_scale = float(feedback.get("impact_weight", 1.0))
    var projectile_scale := float(feedback.get("projectile_scale", 1.0))
    core_radius = 0.11 * projectile_scale
    trail_width = 0.055 * projectile_scale
    match profile:
        "scatter":
            trail_width *= 1.40
        "rail":
            trail_width *= 0.64
            pierce_remaining = protocol_pierce_budget(profile)
        "inferno":
            trail_width *= 1.24
            splash_radius = protocol_splash_radius(profile)
        "cryo":
            trail_width *= 1.18
            var slow := protocol_slow(profile)
            slow_multiplier = slow.x
            slow_duration = slow.y
        "arc":
            trail_width *= 0.82
            chain_targets = protocol_chain_targets(profile)

func _ready() -> void:
    top_level = true
    if has_configured_origin:
        global_position = configured_origin
    add_to_group("projectiles")
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
        _add_flame_core(mat)

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

func _add_flame_core(base_material: StandardMaterial3D) -> void:
    var flame := MeshInstance3D.new()
    var mesh := SphereMesh.new()
    mesh.radius = core_radius * 0.58
    mesh.height = core_radius * 1.55
    flame.mesh = mesh
    flame.scale = Vector3(0.72, 0.72, 1.42)
    flame.material_override = base_material
    add_child(flame)

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        return
    age += delta
    global_position += velocity * delta
    for node in _candidate_enemies():
        if not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
        if global_position.distance_squared_to(enemy.global_position) <= radius * radius:
            var critical := randf() < critical_chance
            var dealt_damage := damage * (1.75 if critical else 1.0)
            hit_enemy_ids[enemy.get_instance_id()] = true
            enemy.take_damage(dealt_damage, critical)
            _apply_protocol_hit(enemy, dealt_damage)
            _impact(critical)
            if visual_profile == "rail" and pierce_remaining > 0:
                pierce_remaining -= 1
                continue
            queue_free()
            return
    if age >= lifetime:
        queue_free()

func _candidate_enemies() -> Array:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene != null and scene.has_method("query_enemies_near"):
        return scene.query_enemies_near(global_position, radius)
    return get_tree().get_nodes_in_group("enemies") if get_tree() != null else []

func _impact(critical := false) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.76, 0.18) if critical else tint
    fx.scale_boost = (1.45 if critical else 1.0) * impact_scale
    get_tree().current_scene.add_child(fx)
    fx.global_position = global_position

func _apply_protocol_hit(primary: DZEnemy, dealt_damage: float) -> void:
    match visual_profile:
        "inferno":
            primary.apply_burn(dealt_damage * 0.16, 2.0)
            _apply_splash(primary, dealt_damage * 0.45, splash_radius)
        "cryo":
            primary.apply_slow(slow_multiplier, slow_duration)
        "arc":
            primary.apply_shock(0.24)
            _apply_chain(primary, dealt_damage)

func _apply_splash(primary: DZEnemy, splash_damage: float, range_radius: float) -> void:
    if range_radius <= 0.0:
        return
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
        if primary.global_position.distance_to(enemy.global_position) <= range_radius:
            enemy.take_damage(splash_damage, false)
            if spawn_secondary_fx:
                var fx := ImpactFx.new()
                fx.color = Color(1.0, 0.24, 0.035)
                fx.scale_boost = 0.72
                get_tree().current_scene.add_child(fx)
                fx.global_position = enemy.global_position + Vector3(0.0, 0.45, 0.0)

func _apply_chain(primary: DZEnemy, dealt_damage: float) -> void:
    if chain_targets <= 0:
        return
    var candidates: Array[DZEnemy] = []
    for node in get_tree().get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
        if primary.global_position.distance_to(enemy.global_position) <= 3.8:
            candidates.append(enemy)
    candidates.sort_custom(func(a: DZEnemy, b: DZEnemy) -> bool:
        return primary.global_position.distance_squared_to(a.global_position) < primary.global_position.distance_squared_to(b.global_position)
    )
    var count: int = mini(chain_targets, candidates.size())
    for i in range(count):
        var chained := candidates[i]
        var falloff := 0.56 if i == 0 else 0.38
        chained.take_damage(dealt_damage * falloff, false)
        if spawn_secondary_fx:
            var fx := ImpactFx.new()
            fx.color = Color(0.64, 0.42, 1.0)
            fx.scale_boost = 0.78
            get_tree().current_scene.add_child(fx)
            fx.global_position = chained.global_position + Vector3(0.0, 0.55, 0.0)
```

## File: scripts/RunDirector.gd
```
class_name DZRunDirector
extends RefCounted

const PHASES := [
    {"start": 0.0, "name": "BREACH", "interval": 0.82, "batch": 1, "max_enemies": 78},
    {"start": 45.0, "name": "SURGE", "interval": 0.68, "batch": 2, "max_enemies": 88},
    {"start": 90.0, "name": "PRESSURE", "interval": 0.54, "batch": 3, "max_enemies": 98},
    {"start": 150.0, "name": "OVERRUN", "interval": 0.40, "batch": 4, "max_enemies": 110},
    {"start": 225.0, "name": "EXTINCTION", "interval": 0.31, "batch": 5, "max_enemies": 118}
]

func opening_roster() -> Array:
    # Establish immediate silhouette and movement contrast without introducing ranged or
    # high-pressure specials before the player has settled into the controls.
    return [
        "shambler", "runner", "shambler", "shambler",
        "runner", "shambler", "shambler", "shambler"
    ]

func profile(elapsed: float, level: int) -> Dictionary:
    var phase: Dictionary = PHASES[0]
    for candidate in PHASES:
        if elapsed >= float(candidate["start"]):
            phase = candidate
        else:
            break
    var phase_age: float = maxf(0.0, elapsed - float(phase["start"]))
    var interval: float = maxf(0.22, float(phase["interval"]) - minf(0.09, phase_age * 0.0009))
    var difficulty: float = 1.0 + elapsed / 210.0 + float(maxi(level - 1, 0)) * 0.035
    return {
        "phase": String(phase["name"]),
        "spawn_interval": interval,
        "batch_size": int(phase["batch"]),
        "difficulty": difficulty,
        "max_enemies": int(phase["max_enemies"])
    }

func choose_enemy(elapsed: float, level: int, rng: RandomNumberGenerator) -> String:
    var weights := _weights(elapsed, level)
    var total := 0.0
    for weight in weights.values():
        total += float(weight)
    var roll := rng.randf() * total
    var cursor := 0.0
    for kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite"]:
        cursor += float(weights.get(kind, 0.0))
        if roll <= cursor:
            return kind
    return "shambler"

func enemy_sequence(elapsed: float, level: int, seed_value: int, count: int) -> Array:
    var rng := RandomNumberGenerator.new()
    rng.seed = seed_value
    var result: Array = []
    for i in range(maxi(count, 0)):
        result.append(choose_enemy(elapsed, level, rng))
    return result

func _weights(elapsed: float, level: int) -> Dictionary:
    var weights := {
        "shambler": 1.0,
        "runner": 0.0,
        "charger": 0.0,
        "harrier": 0.0,
        "regenerator": 0.0,
        "brute": 0.0,
        "elite": 0.0
    }
    if elapsed >= 25.0:
        weights["runner"] = 0.38
    if elapsed >= 45.0:
        weights["charger"] = 0.22
    if elapsed >= 65.0:
        weights["harrier"] = 0.18
    if elapsed >= 82.0:
        weights["regenerator"] = 0.14
    if elapsed >= 100.0:
        weights["brute"] = 0.12
    if elapsed >= 125.0:
        weights["elite"] = 0.08
    var escalation := clampf((elapsed - 90.0) / 180.0, 0.0, 1.0) + clampf(float(level - 4) * 0.035, 0.0, 0.18)
    weights["shambler"] = maxf(0.48, 1.0 - escalation * 0.42)
    weights["charger"] += escalation * 0.08
    weights["harrier"] += escalation * 0.07
    weights["brute"] += escalation * 0.06
    weights["elite"] += escalation * 0.04
    return weights
```

## File: scripts/SpatialHash.gd
```
class_name DZSpatialHash
extends RefCounted

var cell_size := 4.0
var buckets := {}

func _init(size := 4.0) -> void:
    cell_size = maxf(0.5, float(size))

func rebuild(nodes: Array) -> void:
    buckets.clear()
    for node in nodes:
        if node == null or not is_instance_valid(node) or not node is Node3D:
            continue
        var key := _cell((node as Node3D).global_position)
        if not buckets.has(key):
            buckets[key] = []
        buckets[key].append(node)

func query(position: Vector3, radius: float) -> Array:
    var result: Array = []
    var safe_radius := maxf(0.0, radius)
    var min_key := _cell(position - Vector3(safe_radius, 0.0, safe_radius))
    var max_key := _cell(position + Vector3(safe_radius, 0.0, safe_radius))
    var radius_sq := safe_radius * safe_radius
    for x in range(min_key.x, max_key.x + 1):
        for z in range(min_key.y, max_key.y + 1):
            var key := Vector2i(x, z)
            if not buckets.has(key):
                continue
            for node in buckets[key]:
                if node == null or not is_instance_valid(node) or not node is Node3D:
                    continue
                var delta := (node as Node3D).global_position - position
                delta.y = 0.0
                if delta.length_squared() <= radius_sq:
                    result.append(node)
    return result

func _cell(position: Vector3) -> Vector2i:
    return Vector2i(
        int(floor(position.x / cell_size)),
        int(floor(position.z / cell_size))
    )
```

## File: scripts/WeaponProfiles.gd
```
class_name DZWeaponProfiles
extends RefCounted

const PROFILES := {
    "vanguard": {
        "tint": Color(0.18, 0.90, 1.0),
        "damage_multiplier": 1.0,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "projectile_scale": 1.0,
        "trail_length": 0.55,
        "impact_weight": 1.0
    },
    "scatter": {
        "tint": Color(1.0, 0.56, 0.18),
        "damage_multiplier": 0.82,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "multishot_add": 2,
        "multishot_cap": 5,
        "spread_min": 11.0,
        "projectile_scale": 1.16,
        "trail_length": 0.32,
        "impact_weight": 1.18
    },
    "rail": {
        "tint": Color(0.72, 0.58, 1.0),
        "damage_multiplier": 1.50,
        "projectile_speed_multiplier": 1.40,
        "fire_interval_multiplier": 1.22,
        "fire_interval_cap": 0.80,
        "multishot_set": 1,
        "spread_set": 3.0,
        "projectile_scale": 0.78,
        "trail_length": 1.25,
        "impact_weight": 1.34
    },
    "inferno": {
        "tint": Color(1.0, 0.24, 0.035),
        "damage_multiplier": 1.20,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.08,
        "fire_interval_cap": 0.80,
        "projectile_scale": 1.10,
        "trail_length": 0.72,
        "impact_weight": 1.22
    },
    "cryo": {
        "tint": Color(0.30, 0.90, 1.0),
        "damage_multiplier": 0.95,
        "projectile_speed_multiplier": 1.12,
        "fire_interval_multiplier": 0.90,
        "fire_interval_floor": 0.09,
        "projectile_scale": 1.08,
        "trail_length": 0.82,
        "impact_weight": 1.24
    },
    "arc": {
        "tint": Color(0.64, 0.42, 1.0),
        "damage_multiplier": 0.90,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 0.92,
        "fire_interval_floor": 0.09,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0,
        "projectile_scale": 0.92,
        "trail_length": 0.94,
        "impact_weight": 1.20
    }
}

static func profile(id: String) -> Dictionary:
    var key := id if PROFILES.has(id) else "vanguard"
    return (PROFILES[key] as Dictionary).duplicate(true)
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

## File: tests/archetype_roster_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-archetype-roster.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")
const KINDS := ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]
const POSITIONS := [
    Vector3(-6.0, 0.0, -2.2),
    Vector3(-3.0, 0.0, -2.2),
    Vector3(0.0, 0.0, -2.2),
    Vector3(3.0, 0.0, -2.2),
    Vector3(-4.2, 0.0, 2.6),
    Vector3(0.0, 0.0, 2.6),
    Vector3(4.2, 0.0, 2.6),
]

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var main := MAIN_SCENE.instantiate()
    if main == null:
        push_error("Unable to instantiate Main.tscn for archetype roster")
        quit(1)
        return

    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    if main.player == null or main.camera == null:
        push_error("Roster visual QA requires real player/camera runtime anchors")
        quit(1)
        return

    main.player.set_combat_enabled(false)
    main.player.visible = false
    if main.hud != null:
        main.hud.visible = false

    for index in range(KINDS.size()):
        var enemy := DZEnemy.new()
        enemy.configure(KINDS[index], 1.0, main.player)
        enemy.set_combat_enabled(false)
        main.add_child(enemy)
        enemy.global_position = POSITIONS[index]
        if enemy.global_position.length_squared() > 0.01:
            enemy.look_at(Vector3(0.0, enemy.global_position.y, 0.0), Vector3.UP)

    main.camera.global_position = Vector3(0.0, 11.8, 11.8)
    main.camera.fov = 43.0
    main.camera.look_at(Vector3(0.0, 0.85, 0.0), Vector3.UP)

    for _frame in range(8):
        await process_frame

    var seen := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null:
            seen[enemy.kind] = true
    for kind in KINDS:
        if not seen.has(kind):
            push_error("Archetype roster missing runtime enemy: %s" % kind)
            quit(1)
            return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Archetype roster viewport has no render texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Archetype roster produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Archetype roster must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var bright_samples := 0
    var total_samples := 0
    for y in range(0, height, 10):
        for x in range(0, width, 10):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total_samples += 1
            if luma > 0.045:
                bright_samples += 1
    if float(bright_samples) / float(maxi(total_samples, 1)) < 0.01:
        push_error("Archetype roster render is effectively black")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save archetype roster artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_ARCHETYPE_ROSTER_OK %dx%d kinds=%d" % [width, height, KINDS.size()])
    quit(0)
```

## File: tests/attack_telegraph_escalation_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var show_start := enemy_source.find("func _show_telegraph")
    var impact_start := enemy_source.find("func _spawn_attack_impact")
    var show_block := enemy_source.substr(show_start, impact_start - show_start)
    var add_index := show_block.find("add_child(telegraph_visual)")
    var global_index := show_block.find("telegraph_visual.global_position")
    if add_index < 0 or global_index < 0 or global_index < add_index:
        push_error("Telegraph global transform must be assigned only after scene insertion")
        quit(1)
        return

    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var enemy := ENEMY_SCRIPT.new()
    root.add_child(enemy)
    await process_frame

    enemy.kind = "boss"
    enemy.global_position = Vector3.ZERO
    enemy.attack_target_position = Vector3(2.0, 0.0, 0.0)
    enemy._show_telegraph(1.75, 0.40)
    await process_frame

    if enemy.telegraph_visual == null or enemy.telegraph_material == null:
        push_error("Telegraph visual/material missing")
        quit(1)
        return

    var start_energy := enemy.telegraph_material.emission_energy_multiplier
    var start_alpha := enemy.telegraph_material.albedo_color.a
    await create_timer(0.22).timeout

    if enemy.telegraph_material.emission_energy_multiplier <= start_energy:
        push_error("Telegraph emission did not intensify")
        quit(1)
        return
    if enemy.telegraph_material.albedo_color.a <= start_alpha:
        push_error("Telegraph opacity did not intensify")
        quit(1)
        return
    if enemy.telegraph_visual.scale.x <= 0.42:
        push_error("Telegraph scale did not expand")
        quit(1)
        return

    print("Deadline Zero attack telegraph escalation: OK")
    quit(0)
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

## File: tests/authored_world_dressing_test.gd
```
extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var scene := load("res://scenes/Main.tscn") as PackedScene
    if scene == null:
        push_error("Main scene failed to load")
        quit(1)
        return

    var main := scene.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    var props := get_nodes_in_group("environment_props")
    var ground_details := get_nodes_in_group("environment_ground_detail")

    if props.size() != 26:
        push_error("Expected 26 authored environment props, got %d" % props.size())
        quit(1)
        return
    if ground_details.size() != 8:
        push_error("Expected 8 authored street-damage details, got %d" % ground_details.size())
        quit(1)
        return

    for node in props:
        if not node is Node3D:
            push_error("Environment prop is not a Node3D")
            quit(1)
            return
        if not str(node.name).begins_with("EnvironmentProp_"):
            push_error("Unexpected environment prop name: %s" % node.name)
            quit(1)
            return
        var flat_distance := Vector2(node.position.x, node.position.z).length()
        if flat_distance < 8.75:
            push_error("Environment prop intrudes into protected central combat lane: %s" % node.name)
            quit(1)
            return

    print("Deadline Zero authored world dressing: OK")
    quit(0)
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

## File: tests/boss_phase_runtime_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var boss := ENEMY_SCRIPT.new()
    boss.configure("boss", 1.0, target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    root.add_child(boss)
    await process_frame

    if not boss.has_method("_update_boss_phase"):
        push_error("Boss runtime phase API is missing")
        quit(1)
        return

    var phase1_speed: float = boss.move_speed
    var phase1_damage: float = boss.contact_damage

    boss.health = boss.max_health * 0.60
    boss._update_boss_phase()
    if boss.boss_phase != 2:
        push_error("Boss did not enter phase II below 65% health")
        quit(1)
        return
    if boss.move_speed <= phase1_speed or boss.contact_damage <= phase1_damage:
        push_error("Boss phase II did not escalate movement and damage")
        quit(1)
        return

    var phase2_speed: float = boss.move_speed
    var phase2_damage: float = boss.contact_damage
    boss.health = boss.max_health * 0.25
    boss._update_boss_phase()
    if boss.boss_phase != 3:
        push_error("Boss did not enter phase III below 30% health")
        quit(1)
        return
    if boss.move_speed <= phase2_speed or boss.contact_damage <= phase2_damage:
        push_error("Boss phase III did not escalate movement and damage")
        quit(1)
        return

    if boss._boss_slam_windup() >= 0.68 or boss._boss_slam_cooldown() >= 4.1:
        push_error("Boss phase III did not accelerate slam cadence")
        quit(1)
        return

    print("Deadline Zero boss phase runtime: OK")
    quit(0)
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

## File: tests/combat_danger_hud_test.gd
```
extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    var vignette := hud.find_child("DamageVignette", true, false) as ColorRect
    if vignette == null:
        push_error("Screen-space damage vignette is missing")
        quit(1)
        return
    if vignette.visible:
        push_error("Damage vignette should start hidden")
        quit(1)
        return
    hud.pulse_damage_screen()
    if not vignette.visible:
        push_error("Damage vignette did not become visible")
        quit(1)
        return
    await create_timer(0.35).timeout
    if vignette.visible:
        push_error("Damage vignette did not clear after pulse")
        quit(1)
        return

    hud.set_health(30.0, 100.0)
    if not hud.low_health_panel.visible:
        push_error("Low-health warning missing at 30 percent")
        quit(1)
        return
    if hud.low_health_label.text != "CRITICAL INTEGRITY  •  30%":
        push_error("Unexpected low-health label")
        quit(1)
        return

    hud.set_health(31.0, 100.0)
    if hud.low_health_panel.visible:
        push_error("Low-health warning should clear above threshold")
        quit(1)
        return

    hud.set_offscreen_threat(Vector2(-1.0, -1.0), "boss", 27.6)
    if not hud.threat_panel.visible:
        push_error("Threat indicator should be visible")
        quit(1)
        return
    if hud.threat_label.text != "↖  BOSS  28m":
        push_error("Unexpected threat label: %s" % hud.threat_label.text)
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.ZERO, "boss", 10.0)
    if hud.threat_panel.visible:
        push_error("Zero direction should clear threat indicator")
        quit(1)
        return

    print("Deadline Zero combat danger HUD: OK")
    quit(0)
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

## File: tests/enemy_hit_reaction_test.gd
```
extends SceneTree

func _initialize() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var required := {
        "hit_reaction_profile": "Enemy must expose a hit reaction profile",
        "_play_hit_reaction": "Enemy damage must trigger a hit reaction",
        "normal_hit": "Normal enemies need a readable hit reaction",
        "elite_hit": "Elites need a heavier hit reaction",
        "boss_hit": "Bosses need a restrained but weighty hit reaction",
        "hit_flash_material": "Hit reaction must include a material flash without dynamic lights"
    }
    for token in required:
        if not source.contains(token):
            push_error(required[token])
            quit(1)
            return
    if source.contains("hit_reaction_light"):
        push_error("Hit reactions must not allocate per-hit dynamic lights")
        quit(1)
        return
    print("enemy_hit_reaction_test: PASS")
    quit(0)
```

## File: tests/enemy_projectile_visual_test.gd
```
extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/EnemyProjectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var projectile := PROJECTILE_SCRIPT.new()
    root.add_child(projectile)
    await process_frame

    var light_count := 0
    var trail := projectile.get_node_or_null("HarrierBoltTrail") as MeshInstance3D
    for child in projectile.get_children():
        if child is OmniLight3D:
            light_count += 1

    if light_count != 0:
        push_error("Harrier bolt should avoid per-projectile dynamic lights on mobile")
        quit(1)
        return
    if trail == null:
        push_error("Harrier bolt is missing emissive travel-direction trail")
        quit(1)
        return

    print("Deadline Zero enemy projectile visual: OK")
    quit(0)
```

## File: tests/enemy_silhouette_identity_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var palette_samples := {}
    for palette_kind in ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var visual := DZAssetLibrary.enemy(palette_kind)
        if visual == null:
            push_error("Missing authored visual for palette kind %s" % palette_kind)
            quit(1)
            return
        var mesh_instance := visual as MeshInstance3D
        if mesh_instance == null:
            var meshes := visual.find_children("*", "MeshInstance3D", true, false)
            mesh_instance = meshes[0] as MeshInstance3D if not meshes.is_empty() else null
        if mesh_instance == null or not mesh_instance.material_override is BaseMaterial3D:
            push_error("Enemy palette grading missing for %s" % palette_kind)
            quit(1)
            return
        var material := mesh_instance.material_override as BaseMaterial3D
        if material.albedo_texture == null:
            push_error("Enemy palette grading must preserve authored atlas for %s" % palette_kind)
            quit(1)
            return
        palette_samples[palette_kind] = material.albedo_color
        visual.free()

    var runner_color: Color = palette_samples["runner"]
    var charger_color: Color = palette_samples["charger"]
    var harrier_color: Color = palette_samples["harrier"]
    var brute_color: Color = palette_samples["brute"]
    if runner_color.is_equal_approx(charger_color):
        push_error("Runner and charger must not collapse to the same authored palette")
        quit(1)
        return
    if harrier_color.is_equal_approx(brute_color):
        push_error("Harrier and brute must retain distinct authored palettes")
        quit(1)
        return

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossWingL", "BossWingR", "BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
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
        if kind == "runner":
            var blade := enemy.get_node_or_null("RunnerBladeL") as MeshInstance3D
            var blade_mesh := blade.mesh as BoxMesh if blade != null else null
            if blade_mesh == null or blade_mesh.size.z < 0.35 or absf(blade.position.x) < 0.40:
                push_error("Runner signature must remain wide and readable in top-down projection")
                quit(1)
                return
        if kind == "boss":
            var wing := enemy.get_node_or_null("BossWingL") as MeshInstance3D
            var wing_mesh := wing.mesh as BoxMesh if wing != null else null
            if wing_mesh == null or wing_mesh.size.z < 0.50 or absf(wing.position.x) < 0.65:
                push_error("Boss signature must remain broad and dominant in top-down projection")
                quit(1)
                return
        enemy.queue_free()

    print("Deadline Zero enemy silhouette identity: OK")
    quit(0)
```

## File: tests/environment_asset_validation_test.gd
```
extends SceneTree

const ASSETS := preload("res://scripts/AssetLibrary.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var factories := {
        "barrel": Callable(ASSETS, "barrel"),
        "pallet": Callable(ASSETS, "pallet"),
        "street_lights": Callable(ASSETS, "street_lights"),
        "traffic_cone": Callable(ASSETS, "traffic_cone"),
        "trash_bag": Callable(ASSETS, "trash_bag"),
        "street_crack": Callable(ASSETS, "street_crack"),
    }

    for name in factories:
        var node := factories[name].call() as Node3D
        if node == null:
            push_error("Environment asset failed to instantiate: %s" % name)
            quit(1)
            return
        root.add_child(node)
        await process_frame
        if node.get_child_count() == 0:
            push_error("Environment asset has no imported scene content: %s" % name)
            quit(1)
            return
        node.queue_free()
        await process_frame

    print("Deadline Zero authored environment assets: OK")
    quit(0)
```

## File: tests/environment_identity_test.gd
```
extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    var floor := scene.get_node_or_null("QuarantineFloor")
    var env := scene.get_node_or_null("QuarantineEnvironment")
    var fill := scene.get_node_or_null("ContainmentFill")
    if floor == null or env == null or fill == null:
        push_error("Authored quarantine environment anchors are missing")
        quit(1)
        return

    var barrier_count := 0
    var lane_count := 0
    var beacon_count := 0
    var floor_plate_count := 0
    var floor_seam_count := 0
    var floor_wear_count := 0
    var floor_chip_count := 0
    var containment_ring_count := 0
    var street_light_count := 0
    var street_light_pool_count := 0
    var graded_street_light_meshes := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    var service_pylon_count := 0
    var service_grate_count := 0
    var inspection_panel_count := 0
    var inspection_service_stripe_count := 0
    var service_grate_slat_count := 0
    var oversized_barrier_count := 0
    for child in scene.get_children():
        if child.name.begins_with("AuthoredBarrier_"):
            barrier_count += 1
            if child.scale.x > 0.56 or Vector2(child.position.x, child.position.z).length() < 22.0:
                oversized_barrier_count += 1
            var barrier_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                barrier_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                barrier_meshes.append(mesh_node as MeshInstance3D)
            for mesh_instance in barrier_meshes:
                if mesh_instance != null and mesh_instance.material_override is StandardMaterial3D:
                    var material := mesh_instance.material_override as StandardMaterial3D
                    if material.roughness >= 0.80 and material.metallic >= 0.30 and material.albedo_color.get_luminance() < 0.16 and material.albedo_texture == null:
                        graded_barrier_meshes += 1
            hazard_strip_count += int(child.find_child("BarrierHazardFront", true, false) != null)
            hazard_strip_count += int(child.find_child("BarrierHazardRear", true, false) != null)
        elif child.name.begins_with("ContainmentLane_"):
            lane_count += 1
        elif child.name.begins_with("PerimeterBeacon_"):
            beacon_count += 1
        elif child.name.begins_with("FloorPlate_"):
            floor_plate_count += 1
        elif child.name.begins_with("FloorSeam_"):
            floor_seam_count += 1
        elif child.name.begins_with("ServiceGrate_"):
            service_grate_count += 1
            for slat in child.find_children("Slat_*", "MeshInstance3D", true, false):
                if slat is MeshInstance3D:
                    service_grate_slat_count += 1
        elif child.name.begins_with("InspectionPanel_"):
            inspection_panel_count += 1
            inspection_service_stripe_count += child.find_children("ServiceStripe_*", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("FloorWear_"):
            floor_wear_count += 1
        elif child.name.begins_with("FloorChip_"):
            floor_chip_count += 1
        elif child.name.begins_with("ContainmentRing_"):
            containment_ring_count += 1
        elif child.name.begins_with("ServicePylon_"):
            service_pylon_count += 1
        elif child.name.begins_with("AuthoredStreetLight_"):
            street_light_count += 1
            var light_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                light_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                var mesh_instance := mesh_node as MeshInstance3D
                if mesh_instance != null and mesh_instance.name != "StreetLightCore":
                    light_meshes.append(mesh_instance)
            for mesh_instance in light_meshes:
                if mesh_instance.material_override is BaseMaterial3D:
                    var material := mesh_instance.material_override as BaseMaterial3D
                    if material.roughness >= 0.86 and material.albedo_color.get_luminance() < 0.55:
                        graded_street_light_meshes += 1
            var pool := child.get_node_or_null("StreetLightPool") as OmniLight3D
            if pool != null:
                if pool.shadow_enabled:
                    push_error("Street-light pool must remain shadowless for mobile budget")
                    quit(1)
                    return
                if pool.omni_range > 8.5 or pool.light_energy > 1.0:
                    push_error("Street-light pool exceeded mobile-safe range/energy budget")
                    quit(1)
                    return
                street_light_pool_count += 1

    if barrier_count < 12:
        push_error("Expected authored barrier clusters, got %d" % barrier_count)
        quit(1)
        return
    if lane_count < 40:
        push_error("Expected structured containment lanes, got %d" % lane_count)
        quit(1)
        return
    if beacon_count != 12:
        push_error("Expected 12 perimeter beacons, got %d" % beacon_count)
        quit(1)
        return
    if floor_plate_count < 8:
        push_error("Expected midfield floor variation plates, got %d" % floor_plate_count)
        quit(1)
        return
    if floor_seam_count < 8:
        push_error("Expected industrial floor seam structure, got %d" % floor_seam_count)
        quit(1)
        return
    if service_grate_count != 4 or service_grate_slat_count < 36:
        push_error("Expected 4 detailed service grates with at least 36 slats, got %d grates / %d slats" % [service_grate_count, service_grate_slat_count])
        quit(1)
        return
    if inspection_panel_count != 6 or inspection_service_stripe_count != 12:
        push_error("Expected 6 midfield inspection panels / 12 service stripes, got %d/%d" % [inspection_panel_count, inspection_service_stripe_count])
        quit(1)
        return
    if floor_wear_count < 12 or floor_chip_count < 12:
        push_error("Expected deterministic floor wear/chip dressing, got %d/%d" % [floor_wear_count, floor_chip_count])
        quit(1)
        return
    if containment_ring_count != 2:
        push_error("Expected 2 thin containment rings, got %d" % containment_ring_count)
        quit(1)
        return
    if service_pylon_count != 6:
        push_error("Expected 6 low service pylons, got %d" % service_pylon_count)
        quit(1)
        return
    if street_light_count != 4 or street_light_pool_count != 4:
        push_error("Expected 4 authored vertical light fixtures with safe pools, got %d/%d" % [street_light_count, street_light_pool_count])
        quit(1)
        return
    if graded_street_light_meshes < 4:
        push_error("Authored street lights must receive dark steel grading, got %d graded meshes" % graded_street_light_meshes)
        quit(1)
        return
    if graded_barrier_meshes < 12:
        push_error("Authored barriers must use the dedicated dark industrial material, got %d graded meshes" % graded_barrier_meshes)
        quit(1)
        return
    if hazard_strip_count < 24:
        push_error("Authored barriers must expose hazard signatures, got %d strips" % hazard_strip_count)
        quit(1)
        return
    if oversized_barrier_count != 0:
        push_error("Authored barriers must stay compact and perimeter-biased, got %d violations" % oversized_barrier_count)
        quit(1)
        return

    for child in scene.get_children():
        if child is MeshInstance3D and child.name.begins_with("PrototypeProp"):
            push_error("Prototype prop remained in production arena")
            quit(1)
            return

    print("Deadline Zero environment identity: OK")
    quit(0)
```

## File: tests/first_playable_run_path_test.gd
```
extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var main := MAIN_SCENE.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    if main.player == null or main.hud == null or main.camera == null:
        push_error("Run path did not initialize player, HUD and camera")
        quit(1)
        return
    if get_nodes_in_group("enemies").size() < 8:
        push_error("Run path did not create initial enemy population")
        quit(1)
        return

    var pause_button := main.hud.get_node_or_null("PauseButton") as Button
    var pause_panel := main.hud.get_node_or_null("PausePanel") as PanelContainer
    if pause_button == null or pause_panel == null:
        push_error("Pause controls are unavailable in first-playable path")
        quit(1)
        return
    pause_button.pressed.emit()
    await process_frame
    if not paused or not pause_panel.visible:
        push_error("Pause action did not pause gameplay and show settings")
        quit(1)
        return
    var resume_button := pause_panel.find_child("ResumeButton", true, false) as Button
    resume_button.pressed.emit()
    await process_frame
    if paused or pause_panel.visible:
        push_error("Resume action did not restore gameplay")
        quit(1)
        return

    var master_slider := pause_panel.find_child("MasterVolume", true, false) as HSlider
    var sfx_slider := pause_panel.find_child("SfxVolume", true, false) as HSlider
    master_slider.value = 0.35
    sfx_slider.value = 0.45
    await process_frame
    var master_bus := AudioServer.get_bus_index("Master")
    var sfx_bus := AudioServer.get_bus_index("SFX")
    if sfx_bus < 0:
        push_error("Pause settings did not create dedicated SFX audio bus")
        quit(1)
        return
    if abs(AudioServer.get_bus_volume_db(master_bus) - linear_to_db(0.35)) > 0.25:
        push_error("Master volume slider did not update Master bus")
        quit(1)
        return
    if abs(AudioServer.get_bus_volume_db(sfx_bus) - linear_to_db(0.45)) > 0.25:
        push_error("SFX volume slider did not update SFX bus")
        quit(1)
        return

    var previous_level: int = main.level
    var threshold: int = main.xp_next
    main._on_xp_collected(threshold)
    if main.level != previous_level + 1 or main.pending_upgrades.size() != 3:
        push_error("XP progression did not open a three-choice upgrade")
        quit(1)
        return
    if not main.hud.upgrade_panel.visible or not paused:
        push_error("Upgrade state did not pause combat and show the upgrade panel")
        quit(1)
        return

    main._on_upgrade_chosen(0)
    if main.pending_upgrades.size() != 0 or main.hud.upgrade_panel.visible or paused:
        push_error("Upgrade selection did not resume the run cleanly")
        quit(1)
        return

    main.player.apply_upgrade("inferno_protocol")
    seed(424242)
    for offer_index in range(12):
        main._offer_upgrade()
        for upgrade in main.pending_upgrades:
            if String(upgrade["id"]).ends_with("_protocol"):
                push_error("Protocol upgrade remained in offer after a protocol was locked")
                quit(1)
                return
        main.pending_upgrades.clear()
        main.hud.hide_upgrade()
        paused = false

    var bosses_before := _count_kind("boss")
    main._spawn_enemy("boss")
    await process_frame
    if _count_kind("boss") != bosses_before + 1:
        push_error("Forced boss spawn failed")
        quit(1)
        return
    if not main.hud.boss_panel.visible or main.boss_reveal_target == null:
        push_error("Boss spawn did not activate boss HUD/reveal state")
        quit(1)
        return

    var projectile := PROJECTILE_SCRIPT.new()
    main.add_child(projectile)
    projectile.velocity = Vector3(8.0, 0.0, 0.0)
    await process_frame

    main._on_player_died()
    if not main.game_over or not main.hud.game_over_panel.visible:
        push_error("Player death did not enter visible game-over state")
        quit(1)
        return
    if main.hud.wave_label.text != "RUN TERMINATED":
        push_error("Run-end HUD did not enter terminated state")
        quit(1)
        return
    if projectile.combat_enabled or projectile.velocity.length_squared() > 0.0:
        push_error("Active projectile was not frozen at run end")
        quit(1)
        return

    var projectiles_after_freeze := get_nodes_in_group("projectiles").size()
    main.player.fire_clock = 0.0
    main.player._physics_process(0.016)
    if get_nodes_in_group("projectiles").size() != projectiles_after_freeze:
        push_error("Player continued auto-firing after death")
        quit(1)
        return
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and enemy.combat_enabled:
            push_error("Enemy remained combat-enabled after run end")
            quit(1)
            return

    var restart_button := main.hud.game_over_panel.find_child("RestartButton", true, false) as Button
    if restart_button == null or restart_button.disabled:
        push_error("Run-end restart action is unavailable")
        quit(1)
        return

    print("Deadline Zero first-playable run path: OK")
    quit(0)

func _count_kind(kind: String) -> int:
    var count := 0
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and enemy.kind == kind:
            count += 1
    return count
```

## File: tests/haptics_service_test.gd
```
extends SceneTree

const HAPTICS := preload("res://scripts/Haptics.gd")

func _initialize() -> void:
    if HAPTICS.pattern_for("hit") <= 0:
        push_error("Hit haptic pattern is missing")
        quit(1)
        return
    if HAPTICS.pattern_for("critical") <= HAPTICS.pattern_for("hit"):
        push_error("Critical haptic should be stronger than regular hit")
        quit(1)
        return
    if HAPTICS.pattern_for("boss") <= HAPTICS.pattern_for("critical"):
        push_error("Boss haptic should be strongest combat pulse")
        quit(1)
        return
    if HAPTICS.pattern_for("none") != 0:
        push_error("Unknown haptic pattern should be silent")
        quit(1)
        return

    if HAPTICS.event_for_impact(false, false, false) != "hit":
        push_error("Regular impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(true, false, false) != "critical":
        push_error("Critical impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(false, true, false) != "critical":
        push_error("Kill impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(false, false, true) != "boss":
        push_error("Boss impact haptic mapping is incorrect")
        quit(1)
        return

    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if not main_source.contains("HAPTICS.pulse(HAPTICS.event_for_impact"):
        push_error("Main impact path is not wired to combat haptics")
        quit(1)
        return

    print("Deadline Zero haptics service: OK")
    quit(0)
```

## File: tests/hud_readability_hierarchy_test.gd
```
extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Node.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    for node_name in ["VitalPanel", "VitalAccent", "CombatLinkLabel", "SignalLabel", "WavePanel", "ThreatPanel", "BossPanel", "UpgradePanel"]:
        if hud.find_child(node_name, true, false) == null:
            push_error("HUD readability hierarchy missing node: %s" % node_name)
            quit(1)
            return

    var vital_panel := hud.find_child("VitalPanel", true, false) as Control
    if vital_panel == null or vital_panel.size.x < 300.0 or vital_panel.size.y < 82.0:
        push_error("Vital panel must reserve a readable combat-safe footprint")
        quit(1)
        return

    if vital_panel.size.x > 410.0 or vital_panel.size.y > 94.0:
        push_error("Vital panel regressed into an oversized combat-obscuring footprint")
        quit(1)
        return

    if hud.health_bar == null or hud.health_bar.custom_minimum_size.y < 18.0:
        push_error("Health bar must remain readable under combat pressure")
        quit(1)
        return
    if hud.xp_bar == null or hud.xp_bar.custom_minimum_size.y < 8.0:
        push_error("XP bar must retain a distinct secondary hierarchy")
        quit(1)
        return

    if hud.wave_label == null or hud.wave_label.get_theme_font_size("font_size") > 22:
        push_error("Wave label must not dominate the active combat frame")
        quit(1)
        return

    hud.set_health(24.0, 100.0)
    if not hud.low_health_panel.visible:
        push_error("Critical health state must remain immediately visible")
        quit(1)
        return
    if hud.low_health_panel.modulate.a < 0.95:
        push_error("Critical health warning must not be visually muted")
        quit(1)
        return

    hud.set_offscreen_threat(Vector2.RIGHT, "boss", 18.0)
    if not hud.threat_panel.visible or hud.threat_label == null:
        push_error("Boss threat must remain readable while off screen")
        quit(1)
        return
    if hud.threat_label.get_theme_font_size("font_size") < 18:
        push_error("Threat typography is too small for mobile combat")
        quit(1)
        return

    print("hud_readability_hierarchy_test: PASS")
    quit(0)
```

## File: tests/impact_fx_mobile_test.gd
```
extends SceneTree

const IMPACT_SCRIPT := preload("res://scripts/ImpactFx.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var fx := IMPACT_SCRIPT.new()
    root.add_child(fx)
    await process_frame

    var light_count := 0
    var mesh_count := 0
    var ring_found := false
    for child in fx.get_children():
        if child is OmniLight3D:
            light_count += 1
        if child is MeshInstance3D:
            mesh_count += 1
            if child.name == "ImpactRing":
                ring_found = true

    if light_count != 0:
        push_error("Impact FX should avoid per-hit dynamic lights on mobile")
        quit(1)
        return
    if mesh_count < 2 or not ring_found:
        push_error("Impact FX is missing layered emissive geometry")
        quit(1)
        return

    var sparks := fx.get_node_or_null("ImpactSparks") as GPUParticles3D
    if sparks == null:
        push_error("Impact FX is missing mobile-safe GPU sparks")
        quit(1)
        return
    if sparks.amount > 12 or sparks.amount < 4:
        push_error("Impact spark count must stay within mobile budget")
        quit(1)
        return
    if sparks.lifetime > 0.35:
        push_error("Impact sparks live too long for dense mobile combat")
        quit(1)
        return

    print("Deadline Zero mobile-safe impact FX: OK")
    quit(0)
```

## File: tests/mobile_orientation_test.gd
```
extends SceneTree

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var width := int(ProjectSettings.get_setting("display/window/size/viewport_width", 0))
    var height := int(ProjectSettings.get_setting("display/window/size/viewport_height", 0))
    var orientation := int(ProjectSettings.get_setting("display/window/handheld/orientation", -1))
    var mobile_renderer := String(ProjectSettings.get_setting("rendering/renderer/rendering_method.mobile", ""))

    if width <= height:
        push_error("Godot mobile viewport must remain landscape, got %dx%d" % [width, height])
        quit(1)
        return
    if orientation != DisplayServer.SCREEN_SENSOR_LANDSCAPE:
        push_error("Godot handheld orientation must be SCREEN_SENSOR_LANDSCAPE, got %d" % orientation)
        quit(1)
        return
    if mobile_renderer != "mobile":
        push_error("Godot Android renderer must remain mobile, got %s" % mobile_renderer)
        quit(1)
        return

    print("Deadline Zero mobile landscape/rendering contract: OK")
    quit(0)
```

## File: tests/native_enemy_behavior_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

class DummyTarget:
    extends Node3D
    var damage_taken := 0.0
    func take_damage(amount: float) -> void:
        damage_taken += amount

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := DummyTarget.new()
    target.position = Vector3(100.0, 0.0, 0.0)
    root.add_child(target)
    await physics_frame

    var charger := ENEMY_SCRIPT.new()
    charger.configure("charger", 1.0, target)
    charger.spawn_secondary_fx = false
    root.add_child(charger)
    await physics_frame
    if charger.move_speed <= 2.0 or charger.contact_damage < 12.0 or charger.xp_value < 4:
        push_error("Charger baseline identity is incorrect")
        quit(1)
        return

    var harrier := ENEMY_SCRIPT.new()
    harrier.configure("harrier", 1.0, target)
    harrier.process_mode = Node.PROCESS_MODE_DISABLED
    harrier.spawn_secondary_fx = false
    root.add_child(harrier)
    if harrier.move_speed <= charger.move_speed or harrier.max_health >= charger.max_health:
        push_error("Harrier mobility/risk identity is incorrect")
        quit(1)
        return

    var regenerator := ENEMY_SCRIPT.new()
    regenerator.configure("regenerator", 1.0, target)
    regenerator.process_mode = Node.PROCESS_MODE_DISABLED
    regenerator.spawn_secondary_fx = false
    root.add_child(regenerator)
    var full_health: float = regenerator.health
    regenerator.health = full_health * 0.50
    var before_channel: float = regenerator.health
    regenerator._begin_regeneration()
    if regenerator.health != before_channel or regenerator.regeneration_windup <= 0.0:
        push_error("Regenerator should telegraph healing before restoring health")
        quit(1)
        return
    if regenerator.regeneration_visual == null or not is_instance_valid(regenerator.regeneration_visual):
        push_error("Regenerator healing telegraph is missing")
        quit(1)
        return
    regenerator._process_regeneration(0.50)
    if regenerator.health <= before_channel or regenerator.health > full_health:
        push_error("Regenerator healing resolution is incorrect")
        quit(1)
        return
    regenerator.health = full_health - 1.0
    regenerator._begin_regeneration()
    regenerator._process_regeneration(0.50)
    if regenerator.health > full_health:
        push_error("Regenerator healing exceeded max health")
        quit(1)
        return
    regenerator.health = full_health * 0.50
    regenerator._begin_regeneration()
    regenerator.set_combat_enabled(false)
    if regenerator.regeneration_windup > 0.0 or regenerator.regeneration_visual != null:
        push_error("Regenerator healing telegraph was not cancelled with combat")
        quit(1)
        return
    regenerator.set_combat_enabled(true)

    charger.pending_special = "charge"
    charger.attack_target_position = Vector3(4.0, 0.0, 0.0)
    charger.global_position = Vector3.ZERO
    target.global_position = Vector3(3.6, 0.0, 0.0)
    target.damage_taken = 0.0
    charger._resolve_telegraphed_attack()
    if not charger.charge_active or target.damage_taken > 0.0:
        push_error("Charger special should start a real dash before dealing damage")
        quit(1)
        return
    for i in range(30):
        await physics_frame
    if target.damage_taken <= 0.0 or charger.global_position.x <= 2.5:
        push_error("Charger dash did not advance through and damage its target")
        quit(1)
        return

    var dodge_target := DummyTarget.new()
    dodge_target.position = Vector3(100.0, 0.0, 0.0)
    root.add_child(dodge_target)
    await physics_frame
    var dodge_charger := ENEMY_SCRIPT.new()
    dodge_charger.configure("charger", 1.0, dodge_target)
    dodge_charger.spawn_secondary_fx = false
    root.add_child(dodge_charger)
    await physics_frame
    dodge_target.global_position = Vector3(4.0, 0.0, 0.0)
    dodge_charger.global_position = Vector3.ZERO
    dodge_charger.attack_target_position = dodge_target.global_position
    dodge_charger.pending_special = "charge"
    dodge_charger._resolve_telegraphed_attack()
    dodge_target.global_position = Vector3(4.0, 0.0, 3.0)
    for i in range(34):
        await physics_frame
    if dodge_target.damage_taken > 0.0:
        push_error("Charger dash incorrectly tracked a laterally dodging target")
        quit(1)
        return

    var harrier_target := DummyTarget.new()
    root.add_child(harrier_target)
    await process_frame
    var shooter := ENEMY_SCRIPT.new()
    shooter.configure("harrier", 1.0, harrier_target)
    shooter.process_mode = Node.PROCESS_MODE_DISABLED
    shooter.spawn_secondary_fx = false
    root.add_child(shooter)
    await process_frame
    shooter.global_position = Vector3.ZERO
    harrier_target.global_position = Vector3(4.0, 0.0, 0.0)
    shooter.attack_target_position = harrier_target.global_position
    shooter.pending_special = "harrier_shot"
    shooter._resolve_telegraphed_attack()
    await process_frame
    var hostile_projectiles := get_nodes_in_group("hostile_projectiles")
    if hostile_projectiles.size() != 1:
        push_error("Harrier ranged special did not spawn exactly one hostile projectile")
        quit(1)
        return
    var shot := hostile_projectiles[0] as DZEnemyProjectile
    for i in range(8):
        shot._physics_process(0.10)
    if harrier_target.damage_taken <= 0.0:
        push_error("Harrier projectile did not damage target on impact")
        quit(1)
        return

    var harrier_dodge_target := DummyTarget.new()
    root.add_child(harrier_dodge_target)
    harrier_dodge_target.global_position = Vector3(4.0, 0.0, 0.0)
    await process_frame
    var dodge_shot := DZEnemyProjectile.new()
    root.add_child(dodge_shot)
    dodge_shot.global_position = Vector3.ZERO
    dodge_shot.configure(harrier_dodge_target.global_position, harrier_dodge_target, 10.0, 8.0)
    harrier_dodge_target.global_position = Vector3(4.0, 0.0, 3.0)
    for i in range(10):
        dodge_shot._physics_process(0.10)
    if harrier_dodge_target.damage_taken > 0.0:
        push_error("Harrier projectile incorrectly homed into a dodging target")
        quit(1)
        return

    var separation_a := ENEMY_SCRIPT.new()
    separation_a.configure("shambler", 1.0, target)
    separation_a.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(separation_a)
    var separation_b := ENEMY_SCRIPT.new()
    separation_b.configure("shambler", 1.0, target)
    separation_b.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(separation_b)
    await process_frame
    separation_a.global_position = Vector3.ZERO
    separation_b.global_position = Vector3(0.35, 0.0, 0.0)
    var separation_direction: Vector3 = separation_a.separation_vector([separation_a, separation_b], 1.18)
    if separation_direction.x >= -0.80 or absf(separation_direction.z) > 0.25:
        push_error("Enemy separation steering did not push away from a close neighbor")
        quit(1)
        return
    separation_b.dead = true
    if separation_a.separation_vector([separation_b], 1.18) != Vector3.ZERO:
        push_error("Enemy separation steering must ignore dead neighbors")
        quit(1)
        return

    var damage_enemy := ENEMY_SCRIPT.new()
    damage_enemy.configure("shambler", 1.0, target)
    damage_enemy.process_mode = Node.PROCESS_MODE_DISABLED
    damage_enemy.spawn_secondary_fx = false
    root.add_child(damage_enemy)
    await process_frame
    damage_enemy.take_damage(12.0, true)
    await process_frame
    var damage_number := root.find_child("DamageNumber*", true, false) as Label3D
    if damage_number == null or not damage_number.text.contains("12"):
        push_error("Enemy hit did not spawn readable world-space damage number")
        quit(1)
        return

    print("Deadline Zero native enemy behaviors: OK")
    quit(0)
```

## File: tests/native_upgrade_depth_test.gd
```
extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame

    var base_damage := player.weapon_damage
    var base_health := player.max_health
    var base_speed := player.move_speed

    player.apply_upgrade("berserker")
    if player.weapon_damage <= base_damage or player.max_health >= base_health:
        push_error("Berserker tradeoff was not applied")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("fortress")
    if player.max_health <= base_health or player.move_speed >= base_speed:
        push_error("Fortress tradeoff was not applied")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("scatter_protocol")
    if player.weapon_profile != "scatter" or player.multishot < 3 or player.spread_degrees < 11.0:
        push_error("Scatter protocol identity is incomplete")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("rail_protocol")
    if player.weapon_profile != "rail" or player.multishot != 1 or player.projectile_speed <= 19.0:
        push_error("Rail protocol identity is incomplete")
        quit(1)
        return

    if player.can_apply_upgrade("multishot"):
        push_error("Rail protocol must reject multishot upgrades to preserve its single-shot identity")
        quit(1)
        return
    player.apply_upgrade("multishot")
    if player.multishot != 1:
        push_error("Rail protocol allowed multishot to bypass its single-shot identity")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("inferno_protocol")
    if player.weapon_profile != "inferno":
        push_error("Inferno protocol profile missing")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("cryo_protocol")
    if player.weapon_profile != "cryo":
        push_error("Cryo protocol profile missing")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("arc_protocol")
    if player.weapon_profile != "arc" or player.multishot < 2:
        push_error("Arc protocol identity is incomplete")
        quit(1)
        return

    player = PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    player.apply_upgrade("inferno_protocol")
    var inferno_damage := player.weapon_damage
    var inferno_interval := player.fire_interval
    player.apply_upgrade("inferno_protocol")
    if not is_equal_approx(player.weapon_damage, inferno_damage) or not is_equal_approx(player.fire_interval, inferno_interval):
        push_error("Weapon protocols should be idempotent when reapplied")
        quit(1)
        return

    player.apply_upgrade("rail_protocol")
    if player.weapon_profile != "inferno" or not is_equal_approx(player.weapon_damage, inferno_damage) or not is_equal_approx(player.fire_interval, inferno_interval):
        push_error("Weapon protocols should be mutually exclusive")
        quit(1)
        return

    print("Deadline Zero native upgrade depth: OK")
    quit(0)
```

## File: tests/player_damage_feedback_test.gd
```
extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame

    var pulse := player.get_node_or_null("DamagePulse") as MeshInstance3D
    if pulse == null:
        push_error("Player damage feedback pulse is missing")
        quit(1)
        return
    if pulse.visible:
        push_error("Damage pulse should start hidden")
        quit(1)
        return
    if not pulse.mesh is TorusMesh:
        push_error("Damage pulse must remain a thin ring, not a filled floor disc")
        quit(1)
        return
    var ring := pulse.mesh as TorusMesh
    if ring.outer_radius - ring.inner_radius > 0.20:
        push_error("Damage pulse ring is too visually heavy")
        quit(1)
        return

    var initial_health: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, initial_health - 12.0):
        push_error("Player health did not decrease on first hit")
        quit(1)
        return
    if not pulse.visible:
        push_error("Damage pulse did not become visible after damage")
        quit(1)
        return
    if player.invulnerability <= 0.0:
        push_error("Damage hit did not arm invulnerability window")
        quit(1)
        return

    var health_after_first_hit: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, health_after_first_hit):
        push_error("Invulnerability window failed to reject immediate repeated damage")
        quit(1)
        return

    await create_timer(0.20).timeout
    if pulse.visible:
        push_error("Damage pulse did not clear after its presentation window")
        quit(1)
        return

    print("Deadline Zero player damage feedback: OK")
    quit(0)
```

## File: tests/pressure_frame_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-pressure-frame.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")
const REQUIRED_KINDS := ["runner", "charger", "harrier", "regenerator", "brute", "elite"]

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    # Remove the opening roster so this evidence isolates real mid-run archetype readability.
    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    scene.elapsed = 72.0
    scene.spawn_clock = 999.0
    scene.next_boss_time = 9999.0
    scene.director_profile = scene.run_director.profile(scene.elapsed, 5)
    scene.max_enemies = 24

    var player := scene.player as DZPlayer
    if player == null:
        push_error("Pressure-frame QA has no player")
        quit(1)
        return
    player.max_health = 5000.0
    player.health = 5000.0
    player.invulnerability = 10.0
    player.weapon_damage = 6.0
    player.fire_interval = 0.18

    var positions := {
        "runner": Vector3(-4.8, 0.0, -2.0),
        "charger": Vector3(4.8, 0.0, -2.6),
        "harrier": Vector3(-5.8, 0.0, 3.0),
        "regenerator": Vector3(5.7, 0.0, 2.8),
        "brute": Vector3(-2.5, 0.0, 6.0),
        "elite": Vector3(2.8, 0.0, 5.8),
    }

    for kind in REQUIRED_KINDS:
        scene._spawn_enemy(kind)
        await process_frame

    var seen := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null:
            continue
        if positions.has(enemy.kind):
            enemy.global_position = positions[enemy.kind]
            seen[enemy.kind] = true

    for kind in REQUIRED_KINDS:
        if not seen.has(kind):
            push_error("Pressure-frame QA missing archetype: %s" % kind)
            quit(1)
            return

    # Let the real combat loop produce movement, targeting, projectiles and telegraph states.
    for _frame in range(72):
        await process_frame

    if bool(scene.get("game_over")):
        push_error("Pressure-frame QA reached game over")
        quit(1)
        return

    var active_kinds := {}
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy != null and not enemy.dead:
            active_kinds[enemy.kind] = true
    if active_kinds.size() < 4:
        push_error("Pressure-frame QA lost too many archetypes before capture: %d remain" % active_kinds.size())
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Pressure-frame QA has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Pressure-frame QA produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Pressure frame must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var bright := 0
    var total := 0
    var sum_luma := 0.0
    for y in range(0, height, 8):
        for x in range(0, width, 8):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            sum_luma += luma
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    var average_luma := sum_luma / float(maxi(total, 1))
    if bright_fraction < 0.008 or average_luma < 0.006:
        push_error("Pressure frame is effectively black: bright=%.5f avg=%.5f" % [bright_fraction, average_luma])
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save pressure-frame artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PRESSURE_FRAME_OK %dx%d kinds=%d bright=%.5f avg=%.5f" % [
        width, height, active_kinds.size(), bright_fraction, average_luma
    ])
    quit(0)
```

## File: tests/rendered_frame_smoke_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-rendered-frame.png"
const SAMPLE_STEP := 8
const MIN_BRIGHT_FRACTION := 0.008
const MIN_AVERAGE_LUMA := 0.006
const MIN_LUMA_RANGE := 0.04

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var packed := load("res://scenes/Main.tscn") as PackedScene
    if packed == null:
        push_error("Unable to load Main.tscn for rendered-frame smoke")
        quit(1)
        return

    var scene := packed.instantiate()
    get_root().add_child(scene)

    # Capture early enough to prove active combat rather than the run-end overlay, while still
    # giving imported meshes, materials, HUD and camera enough real render frames to settle.
    for _frame in range(30):
        await process_frame

    if bool(scene.get("game_over")):
        push_error("Rendered-frame smoke reached game over before active-combat capture")
        quit(1)
        return
    if get_nodes_in_group("enemies").is_empty():
        push_error("Rendered-frame smoke has no active enemies")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Root viewport has no render texture")
        quit(1)
        return

    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Rendered-frame smoke produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Rendered frame must be landscape and at least 640x360, got %dx%d" % [width, height])
        quit(1)
        return

    var total := 0
    var bright := 0
    var sum_luma := 0.0
    var min_luma := 1.0
    var max_luma := 0.0
    for y in range(0, height, SAMPLE_STEP):
        for x in range(0, width, SAMPLE_STEP):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            sum_luma += luma
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    var average_luma := sum_luma / float(maxi(total, 1))
    var luma_range := max_luma - min_luma

    if bright_fraction < MIN_BRIGHT_FRACTION:
        push_error("Rendered frame is effectively black: bright_fraction=%.5f" % bright_fraction)
        quit(1)
        return
    if average_luma < MIN_AVERAGE_LUMA:
        push_error("Rendered frame average luminance is too low: %.5f" % average_luma)
        quit(1)
        return
    if luma_range < MIN_LUMA_RANGE:
        push_error("Rendered frame lacks visual range: %.5f" % luma_range)
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save rendered-frame artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_RENDERED_FRAME_OK %dx%d bright=%.5f avg=%.5f range=%.5f" % [
        width, height, bright_fraction, average_luma, luma_range
    ])
    quit(0)
```

## File: tests/run_director_escalation_test.gd
```
extends SceneTree

func _init() -> void:
    var director_script := load("res://scripts/RunDirector.gd")
    if director_script == null:
        push_error("RunDirector must exist")
        quit(1)
        return

    var director = director_script.new()
    var opening_roster: Array = director.opening_roster()
    if opening_roster.size() != 8:
        push_error("Opening roster must contain exactly 8 enemies")
        quit(1)
        return
    if opening_roster.count("runner") != 2 or opening_roster.count("shambler") != 6:
        push_error("Opening roster must establish 6 shambler / 2 runner combat contrast")
        quit(1)
        return

    var opening: Dictionary = director.profile(0.0, 1)
    var pressure: Dictionary = director.profile(90.0, 4)
    var late: Dictionary = director.profile(180.0, 7)

    if not _require_keys(opening):
        quit(1)
        return
    if float(opening["spawn_interval"]) <= float(pressure["spawn_interval"]):
        push_error("Pressure phase must spawn faster than opening")
        quit(1)
        return
    if int(opening["batch_size"]) >= int(late["batch_size"]):
        push_error("Late phase must spawn larger batches than opening")
        quit(1)
        return
    if float(opening["difficulty"]) >= float(late["difficulty"]):
        push_error("Late phase difficulty must exceed opening")
        quit(1)
        return
    if String(opening["phase"]) == String(late["phase"]):
        push_error("Run phase identity must escalate over time")
        quit(1)
        return

    var seed_a: Array = director.enemy_sequence(135.0, 5, 24680, 12)
    var seed_b: Array = director.enemy_sequence(135.0, 5, 24680, 12)
    var seed_c: Array = director.enemy_sequence(135.0, 5, 24681, 12)
    if seed_a != seed_b:
        push_error("Enemy sequence must be deterministic for a fixed seed")
        quit(1)
        return
    if seed_a == seed_c:
        push_error("Different seeds must be able to produce different enemy sequences")
        quit(1)
        return
    if not ("brute" in seed_a or "elite" in seed_a or "charger" in seed_a or "harrier" in seed_a or "regenerator" in seed_a):
        push_error("Escalated sequence must contain a pressure archetype")
        quit(1)
        return

    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    for required in [
        "var run_director := DZRunDirector.new()",
        "run_director.profile(elapsed, level)",
        "run_director.opening_roster()",
        "run_director.choose_enemy(elapsed, level, spawn_rng)",
        "director_profile[\"spawn_interval\"]",
        "director_profile[\"batch_size\"]",
        "director_profile[\"max_enemies\"]"
    ]:
        if main_source.find(required) < 0:
            push_error("Main runtime is not wired to RunDirector: %s" % required)
            quit(1)
            return
    if main_source.find("director_profile[\"difficulty\"]") < 0 and main_source.find("director_profile.get(\"difficulty\"") < 0:
        push_error("Main runtime is not wired to RunDirector difficulty")
        quit(1)
        return
    for legacy in ["1 + int(elapsed / 45.0)", "0.82 - elapsed * 0.0035", "if elapsed > 25.0 and roll > 0.72", "var difficulty := 1.0 + elapsed / 210.0"]:
        if main_source.find(legacy) >= 0:
            push_error("Legacy hard-coded pacing remains in Main.gd: %s" % legacy)
            quit(1)
            return

    print("run_director_escalation_test: PASS")
    quit(0)

func _require_keys(profile: Dictionary) -> bool:
    for key in ["phase", "spawn_interval", "batch_size", "difficulty", "max_enemies"]:
        if not profile.has(key):
            push_error("Run director profile missing key: %s" % key)
            return false
    return true
```

## File: tests/run_director_runtime_integration_test.gd
```
extends SceneTree

func _init() -> void:
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if main_source.is_empty():
        push_error("Main.gd must be readable")
        quit(1)
        return

    for required in [
        "var run_director := DZRunDirector.new()",
        "run_director.profile(elapsed, level)",
        "run_director.choose_enemy(elapsed, level, spawn_rng)",
        "director_profile[\"spawn_interval\"]",
        "director_profile[\"batch_size\"]",
        "director_profile[\"max_enemies\"]",
        "director_profile[\"difficulty\"]"
    ]:
        if main_source.find(required) < 0:
            push_error("Main runtime is not wired to RunDirector: %s" % required)
            quit(1)
            return

    for legacy in [
        "1 + int(elapsed / 45.0)",
        "0.82 - elapsed * 0.0035",
        "if elapsed > 25.0 and roll > 0.72",
        "var difficulty := 1.0 + elapsed / 210.0"
    ]:
        if main_source.find(legacy) >= 0:
            push_error("Legacy hard-coded pacing remains in Main.gd: %s" % legacy)
            quit(1)
            return

    print("run_director_runtime_integration_test: PASS")
    quit(0)
```

## File: tests/run_end_combat_freeze_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("charger", 1.0, target)
    enemy.spawn_secondary_fx = false
    root.add_child(enemy)
    enemy.velocity = Vector3(3.0, 0.0, 0.0)
    enemy.attack_windup = 0.5
    enemy.pending_special = "charge"

    var telegraph := Node3D.new()
    root.add_child(telegraph)
    enemy.telegraph_visual = telegraph

    var projectile := PROJECTILE_SCRIPT.new()
    root.add_child(projectile)
    projectile.velocity = Vector3(5.0, 0.0, 0.0)

    enemy.set_combat_enabled(false)
    projectile.set_combat_enabled(false)
    await process_frame

    if enemy.combat_enabled:
        push_error("Enemy combat freeze flag was not disabled")
        quit(1)
        return
    if enemy.velocity.length_squared() > 0.0:
        push_error("Enemy velocity was not cleared")
        quit(1)
        return
    if enemy.attack_windup > 0.0 or not enemy.pending_special.is_empty():
        push_error("Enemy telegraphed attack was not cancelled")
        quit(1)
        return
    if enemy.telegraph_visual != null:
        push_error("Enemy telegraph reference was not cleared")
        quit(1)
        return
    if projectile.combat_enabled:
        push_error("Projectile combat freeze flag was not disabled")
        quit(1)
        return
    if projectile.velocity.length_squared() > 0.0:
        push_error("Projectile velocity was not cleared")
        quit(1)
        return

    print("Deadline Zero run-end combat freeze: OK")
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

    var pause_button := hud.get_node_or_null("PauseButton") as Button
    var pause_panel := hud.get_node_or_null("PausePanel") as PanelContainer
    if pause_button == null or pause_panel == null:
        push_error("Pause/settings controls were not created")
        quit(1)
        return
    if pause_panel.visible:
        push_error("Pause/settings panel should start hidden")
        quit(1)
        return
    if pause_panel.find_child("ResumeButton", true, false) == null:
        push_error("Pause/settings panel is missing resume control")
        quit(1)
        return
    if pause_panel.find_child("MasterVolume", true, false) == null or pause_panel.find_child("SfxVolume", true, false) == null:
        push_error("Pause/settings panel is missing audio sliders")
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

## File: tests/screen_space_fx_test.gd
```
extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var root := Control.new()
    get_root().add_child(root)
    var hud := HUD_SCRIPT.new()
    root.add_child(hud)
    await process_frame

    if not hud.has_method("show_impact_flash") or not "impact_flash" in hud:
        push_error("HUD screen-space impact flash API is missing")
        quit(1)
        return

    hud.show_impact_flash(true, false, false)
    if hud.impact_flash == null or not hud.impact_flash.visible:
        push_error("Critical enemy hit did not trigger screen-space impact flash")
        quit(1)
        return
    if hud.impact_flash.color.a <= 0.0:
        push_error("Impact flash alpha was not visible")
        quit(1)
        return

    print("Deadline Zero screen-space impact FX: OK")
    quit(0)
```

## File: tests/settings_persistence_test.gd
```
extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    var script := load("res://scripts/GameSettings.gd")
    if script == null:
        push_error("Game settings persistence service is missing")
        quit(1)
        return

    var path := "user://deadline-zero-settings-test.cfg"
    var expected := {
        "master_volume": 0.42,
        "sfx_volume": 0.33
    }
    script.save(path, expected)
    var loaded: Dictionary = script.load_settings(path)
    if not is_equal_approx(float(loaded.get("master_volume", -1.0)), 0.42):
        push_error("Master volume setting did not persist")
        quit(1)
        return
    if not is_equal_approx(float(loaded.get("sfx_volume", -1.0)), 0.33):
        push_error("SFX volume setting did not persist")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(path))

    var integration_path := "user://deadline-zero-settings-integration-test.cfg"
    script.save(integration_path, {
        "master_volume": 0.35,
        "sfx_volume": 0.60
    })

    var main := MAIN_SCENE.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame
    await process_frame

    if not main.has_method("_load_audio_settings") or not main.has_method("_save_audio_settings"):
        push_error("Main settings persistence integration is missing")
        quit(1)
        return

    main._load_audio_settings(integration_path)
    if not is_equal_approx(main.hud.master_volume.value, 0.35):
        push_error("Persisted master volume was not restored into pause settings")
        quit(1)
        return
    if not is_equal_approx(main.hud.sfx_volume.value, 0.60):
        push_error("Persisted SFX volume was not restored into pause settings")
        quit(1)
        return

    main.hud.master_volume.value = 0.60
    main.hud.sfx_volume.value = 0.45
    main._save_audio_settings(integration_path)
    var round_trip: Dictionary = script.load_settings(integration_path)
    if not is_equal_approx(float(round_trip.get("master_volume", -1.0)), 0.60):
        push_error("Updated master volume was not saved from pause settings")
        quit(1)
        return
    if not is_equal_approx(float(round_trip.get("sfx_volume", -1.0)), 0.45):
        push_error("Updated SFX volume was not saved from pause settings")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(integration_path))
    print("Deadline Zero settings persistence: OK")
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
    current_scene = game
    if current_scene != game:
        push_error("Smoke test must install Main as current_scene")
        quit(1)
        return

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

## File: tests/spatial_hash_test.gd
```
extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

class QueryScene:
    extends Node3D
    var query_called := false

    func query_enemies_near(_position: Vector3, _radius: float) -> Array:
        query_called = true
        return []

func _initialize() -> void:
    var script := load("res://scripts/SpatialHash.gd")
    if script == null:
        push_error("Spatial hash service is missing")
        quit(1)
        return

    var root := Node3D.new()
    get_root().add_child(root)
    var index = script.new(4.0)
    var near_enemy := Node3D.new()
    near_enemy.position = Vector3(1.0, 0.0, 1.0)
    root.add_child(near_enemy)
    var far_enemy := Node3D.new()
    far_enemy.position = Vector3(12.0, 0.0, 12.0)
    root.add_child(far_enemy)
    await process_frame

    index.rebuild([near_enemy, far_enemy])
    var nearby: Array = index.query(Vector3.ZERO, 3.0)
    if not nearby.has(near_enemy) or nearby.has(far_enemy):
        push_error("Spatial hash query did not isolate nearby enemies")
        quit(1)
        return

    var query_scene := QueryScene.new()
    get_root().add_child(query_scene)
    current_scene = query_scene
    var projectile := PROJECTILE_SCRIPT.new()
    query_scene.add_child(projectile)
    await process_frame
    if not projectile.has_method("_candidate_enemies"):
        push_error("Projectile spatial-query integration is missing")
        quit(1)
        return
    projectile._candidate_enemies()
    if not query_scene.query_called:
        push_error("Projectile did not consume scene spatial query")
        quit(1)
        return

    print("Deadline Zero enemy spatial hash: OK")
    quit(0)
```

## File: tests/status_effects_test.gd
```
extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    root.add_child(target)

    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    enemy.spawn_secondary_fx = false
    root.add_child(enemy)
    await process_frame

    if not enemy.has_method("apply_burn") or not enemy.has_method("_process_status_effects"):
        push_error("Enemy burn status API is missing")
        quit(1)
        return

    var before := enemy.health
    enemy.apply_burn(8.0, 1.0)
    enemy._process_status_effects(0.5)
    if enemy.health >= before or enemy.burn_left <= 0.0:
        push_error("Burn status did not deal damage over time")
        quit(1)
        return

    enemy._process_status_effects(0.6)
    if enemy.burn_left > 0.0:
        push_error("Burn status did not expire after its duration")
        quit(1)
        return

    var inferno_target := ENEMY_SCRIPT.new()
    inferno_target.configure("shambler", 1.0, target)
    inferno_target.process_mode = Node.PROCESS_MODE_DISABLED
    inferno_target.spawn_secondary_fx = false
    root.add_child(inferno_target)
    await process_frame

    var projectile := PROJECTILE_SCRIPT.new()
    projectile.visual_profile = "inferno"
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    projectile.spawn_secondary_fx = false
    root.add_child(projectile)
    await process_frame
    projectile._apply_profile("inferno")
    projectile._apply_protocol_hit(inferno_target, 24.0)
    if inferno_target.burn_left <= 0.0 or inferno_target.burn_dps <= 0.0:
        push_error("Inferno projectile did not apply persistent burn")
        quit(1)
        return

    if not enemy.has_method("apply_shock"):
        push_error("Enemy shock status API is missing")
        quit(1)
        return
    enemy.velocity = Vector3(3.0, 0.0, 0.0)
    enemy.apply_shock(0.40)
    if enemy.shock_left <= 0.0 or enemy.velocity.length_squared() > 0.001:
        push_error("Shock status did not immediately immobilize enemy")
        quit(1)
        return

    var arc_target := ENEMY_SCRIPT.new()
    arc_target.configure("shambler", 1.0, target)
    arc_target.process_mode = Node.PROCESS_MODE_DISABLED
    arc_target.spawn_secondary_fx = false
    root.add_child(arc_target)
    await process_frame

    var arc_projectile := PROJECTILE_SCRIPT.new()
    arc_projectile.visual_profile = "arc"
    arc_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    arc_projectile.spawn_secondary_fx = false
    root.add_child(arc_projectile)
    await process_frame
    arc_projectile._apply_profile("arc")
    arc_projectile.chain_targets = 0
    arc_projectile._apply_protocol_hit(arc_target, 24.0)
    if arc_target.shock_left <= 0.0:
        push_error("Arc projectile did not apply shock control")
        quit(1)
        return

    print("Deadline Zero enemy status effects: OK")
    quit(0)
```

## File: tests/upgrade_presentation_test.gd
```
extends SceneTree

func _fail(message: String) -> void:
    push_error(message)
    quit(1)

func _init() -> void:
    var main_text := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var hud_text := FileAccess.get_file_as_string("res://scripts/Hud.gd")

    var ids := ["damage", "rate", "speed", "health", "projectile", "multishot"]
    for id in ids:
        if not main_text.contains("\"id\":\"" + id + "\""):
            _fail("Upgrade pool is missing id: %s" % id)
            return
        if not hud_text.contains("\"" + id + "\""):
            _fail("HUD presentation is missing id: %s" % id)
            return

    var required_main := ["\"family\":\"OFFENSE\"", "\"family\":\"SURVIVAL\"", "\"family\":\"BARRAGE\""]
    for token in required_main:
        if not main_text.contains(token):
            _fail("Upgrade presentation is missing family token: %s" % token)
            return

    var required_hud := [
        "func _upgrade_glyph",
        "func _upgrade_color",
        "func _style_upgrade_card",
        "StyleBoxFlat.new()",
        "upgrade_family_labels",
        "upgrade_detail_labels"
    ]
    for token in required_hud:
        if not hud_text.contains(token):
            _fail("HUD upgrade presentation is missing token: %s" % token)
            return

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
    assert(player.contains("PlayerMarkerRing"))
    assert(player.contains("PlayerAimTick"))
    assert(player.contains("MuzzleFlash"))
    assert(player.contains("_trigger_muzzle_flash()"))

    print("weapon_presentation_test: PASS")
    quit()
```

## File: tests/weapon_profile_data_test.gd
```
extends SceneTree

const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

func _initialize() -> void:
    var script := load("res://scripts/WeaponProfiles.gd")
    if script == null:
        push_error("Data-driven weapon profile catalog is missing")
        quit(1)
        return

    var rail: Dictionary = script.profile("rail")
    if rail.is_empty():
        push_error("Rail weapon profile data is missing")
        quit(1)
        return
    if float(rail.get("damage_multiplier", 1.0)) <= 1.0:
        push_error("Rail profile damage multiplier is not represented as data")
        quit(1)
        return
    if int(rail.get("multishot_set", 0)) != 1:
        push_error("Rail profile multishot rule is not represented as data")
        quit(1)
        return

    var cryo: Dictionary = script.profile("cryo")
    if float(cryo.get("projectile_speed_multiplier", 1.0)) <= 1.0:
        push_error("Cryo projectile speed rule is not represented as data")
        quit(1)
        return

    var root := Node3D.new()
    get_root().add_child(root)
    var player := PLAYER_SCRIPT.new()
    root.add_child(player)
    await process_frame
    if not player.has_method("_apply_weapon_profile_data"):
        push_error("Player generic weapon-profile applicator is missing")
        quit(1)
        return

    var base_damage: float = player.weapon_damage
    var base_speed: float = player.projectile_speed
    var base_interval: float = player.fire_interval
    var custom := {
        "tint": Color(0.9, 0.2, 0.7),
        "damage_multiplier": 2.0,
        "projectile_speed_multiplier": 1.5,
        "fire_interval_multiplier": 0.5,
        "fire_interval_floor": 0.10,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0
    }
    player._apply_weapon_profile_data("test_profile", custom)
    if player.weapon_profile != "test_profile":
        push_error("Generic profile applicator did not set weapon profile")
        quit(1)
        return
    if not is_equal_approx(player.weapon_damage, base_damage * 2.0):
        push_error("Generic profile applicator ignored damage data")
        quit(1)
        return
    if not is_equal_approx(player.projectile_speed, base_speed * 1.5):
        push_error("Generic profile applicator ignored projectile speed data")
        quit(1)
        return
    if not is_equal_approx(player.fire_interval, max(0.10, base_interval * 0.5)):
        push_error("Generic profile applicator ignored cadence data")
        quit(1)
        return

    print("Deadline Zero weapon profile data: OK")
    quit(0)
```

## File: tests/weapon_protocol_behavior_test.gd
```
extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")
const WEAPON_PROFILES := preload("res://scripts/WeaponProfiles.gd")

func _initialize() -> void:
    if PROJECTILE_SCRIPT.protocol_pierce_budget("rail") != 2:
        push_error("Rail pierce budget is incorrect")
        quit(1)
        return
    if not is_equal_approx(PROJECTILE_SCRIPT.protocol_splash_radius("inferno"), 1.85):
        push_error("Inferno splash radius is incorrect")
        quit(1)
        return
    if PROJECTILE_SCRIPT.protocol_chain_targets("arc") != 2:
        push_error("Arc chain target count is incorrect")
        quit(1)
        return
    var slow := PROJECTILE_SCRIPT.protocol_slow("cryo")
    if slow.x >= 1.0 or slow.y <= 0.0:
        push_error("Cryo slow rule is incorrect")
        quit(1)
        return

    var signatures := {}
    for id in ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]:
        var profile := WEAPON_PROFILES.profile(id)
        for key in ["projectile_scale", "trail_length", "impact_weight"]:
            if not profile.has(key):
                push_error("Weapon profile %s is missing feedback key %s" % [id, key])
                quit(1)
                return
        var signature := "%s|%s|%s" % [profile["projectile_scale"], profile["trail_length"], profile["impact_weight"]]
        if signatures.has(signature):
            push_error("Weapon feedback signature is not distinct: %s and %s" % [signatures[signature], id])
            quit(1)
            return
        signatures[signature] = id
    if float(WEAPON_PROFILES.profile("rail")["impact_weight"]) <= float(WEAPON_PROFILES.profile("vanguard")["impact_weight"]):
        push_error("Rail impact should read heavier than Vanguard")
        quit(1)
        return
    if float(WEAPON_PROFILES.profile("scatter")["trail_length"]) >= float(WEAPON_PROFILES.profile("rail")["trail_length"]):
        push_error("Scatter should read shorter-ranged than Rail")
        quit(1)
        return

    var target := Node3D.new()
    var enemy := ENEMY_SCRIPT.new()
    enemy.configure("shambler", 1.0, target)
    enemy.apply_slow(slow.x, slow.y)
    if enemy.slow_multiplier >= 1.0 or enemy.slow_left <= 0.0:
        push_error("Enemy slow state was not applied")
        quit(1)
        return

    var rail := PROJECTILE_SCRIPT.new()
    rail._apply_profile("rail")
    if rail.pierce_remaining != 2:
        push_error("Rail runtime profile did not consume deterministic rule")
        quit(1)
        return
    var inferno := PROJECTILE_SCRIPT.new()
    inferno._apply_profile("inferno")
    if not is_equal_approx(inferno.splash_radius, 1.85):
        push_error("Inferno runtime profile did not consume deterministic rule")
        quit(1)
        return
    var arc := PROJECTILE_SCRIPT.new()
    arc._apply_profile("arc")
    if arc.chain_targets != 2:
        push_error("Arc runtime profile did not consume deterministic rule")
        quit(1)
        return

    var parent := Node3D.new()
    parent.position = Vector3(9.0, 0.0, -4.0)
    get_root().add_child(parent)
    await process_frame
    var spawned := PROJECTILE_SCRIPT.new()
    spawned.process_mode = Node.PROCESS_MODE_DISABLED
    var spawn_origin := Vector3(2.0, 0.7, 3.0)
    spawned.setup(spawn_origin, Vector3.FORWARD, 10.0, 10.0, Color.WHITE, "vanguard")
    parent.add_child(spawned)
    await process_frame
    if spawned.global_position.distance_to(spawn_origin) > 0.001:
        push_error("Projectile setup did not preserve global spawn origin under a transformed parent")
        quit(1)
        return

    parent.queue_free()
    enemy.free()
    target.free()
    rail.free()
    inferno.free()
    arc.free()
    await process_frame
    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
```
