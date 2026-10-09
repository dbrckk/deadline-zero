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
  CombatGroundMark.gd
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
  android_play_export_contract_test.gd
  archetype_roster_render_test.gd
  attack_facing_lock_test.gd
  attack_telegraph_escalation_test.gd
  authored_asphalt_pbr_floor_test.gd
  authored_asset_validation.gd
  authored_audio_asset_test.gd
  authored_gait_sync_test.gd
  authored_world_dressing_test.gd
  boss_encounter_render_test.gd
  boss_hud_identity_test.gd
  boss_phase_runtime_test.gd
  boss_reveal_camera_test.gd
  combat_audio_feedback_test.gd
  combat_danger_hud_test.gd
  combat_feel_test.gd
  combat_ground_mark_test.gd
  damage_number_budget_test.gd
  enemy_archetype_combat_test.gd
  enemy_hit_reaction_test.gd
  enemy_projectile_visual_test.gd
  enemy_shadow_budget_test.gd
  enemy_silhouette_identity_test.gd
  environment_asset_validation_test.gd
  environment_identity_test.gd
  first_playable_run_path_test.gd
  game_over_render_test.gd
  haptics_service_test.gd
  hud_readability_hierarchy_test.gd
  impact_fx_mobile_test.gd
  industrial_kit_pbr_budget_test.gd
  industrial_material_sharing_test.gd
  industrial_prop_material_test.gd
  mobile_orientation_test.gd
  multihit_silhouette_stability_test.gd
  muzzle_flare_3d_test.gd
  native_enemy_behavior_test.gd
  native_upgrade_depth_test.gd
  offscreen_threat_priority_test.gd
  pause_settings_layout_test.gd
  play_feature_graphic_render_test.gd
  play_icon_render_test.gd
  play_store_claims_test.gd
  player_damage_feedback_test.gd
  player_pressure_marker_test.gd
  player_targeting_stability_test.gd
  pressure_frame_render_test.gd
  projectile_profile_runtime_visual_test.gd
  rendered_frame_smoke_test.gd
  rifle_muzzle_ballistics_test.gd
  run_director_escalation_test.gd
  run_director_runtime_integration_test.gd
  run_end_combat_freeze_test.gd
  run_end_ux_test.gd
  screen_space_fx_test.gd
  settings_persistence_test.gd
  shock_expiry_recovery_test.gd
  smoke_test.gd
  soft_contact_shadow_budget_test.gd
  spatial_hash_test.gd
  status_effects_test.gd
  upgrade_choice_render_test.gd
  upgrade_presentation_test.gd
  weapon_presentation_test.gd
  weapon_profile_data_test.gd
  weapon_protocol_behavior_test.gd
  world_target_lock_test.gd
  xp_orb_runtime_test.gd
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
const INDUSTRIAL_FLOOR_GRATE := "res://assets/generated/industrial/dz_floor_grate.glb"
const INDUSTRIAL_BULKHEAD := "res://assets/generated/industrial/dz_bulkhead_panel.glb"
const INDUSTRIAL_CRATE := "res://assets/generated/industrial/dz_cargo_crate.glb"
const INDUSTRIAL_PIPE_RACK := "res://assets/generated/industrial/dz_pipe_rack.glb"
const INDUSTRIAL_SERVICE_PILLAR := "res://assets/generated/industrial/dz_service_pillar.glb"

static var _enemy_grade_shader: Shader
# Immutable PBR grades are shared between copies of the same authored GLTF
# surface, avoiding one material allocation per arena prop.
static var _graded_pbr_material_cache := {}
static var _shared_barrier_hazard_material: StandardMaterial3D
# GLBs generated by Blender may import with a white default base color if
# the exporter ignores diffuse_color on non-node materials. Supply a small
# authored, shared PBR palette at runtime so steel is never blown out.
static var _industrial_kit_material_cache := {}


static func instantiate_scene(path: String) -> Node3D:
    if not ResourceLoader.exists(path):
        return null
    var packed := load(path) as PackedScene
    if packed == null:
        return null
    return packed.instantiate() as Node3D

static func player() -> Node3D:
    var root := instantiate_scene(PLAYER)
    _grade_mesh_tree(root, Color(0.74, 0.82, 0.86), 0.78, 0.02)
    return root

static func enemy(kind: String) -> Node3D:
    var root := instantiate_scene(ZOMBIE_CHUBBY if kind in ["brute", "elite", "boss"] else ZOMBIE_BASIC)
    var tint := Color(0.46, 0.58, 0.48)
    match kind:
        "runner": tint = Color(0.46, 0.72, 0.48)
        "charger": tint = Color(0.68, 0.42, 0.28)
        "harrier": tint = Color(0.36, 0.62, 0.74)
        "regenerator": tint = Color(0.40, 0.72, 0.48)
        "brute": tint = Color(0.62, 0.34, 0.30)
        "elite": tint = Color(0.54, 0.42, 0.70)
        "boss": tint = Color(0.68, 0.42, 0.26)
    _grade_enemy_mesh_tree(root, tint, kind)
    return root

static func rifle() -> Node3D:
    var root := instantiate_scene(RIFLE)
    _grade_mesh_tree(root, Color(0.34, 0.40, 0.44), 0.60, 0.34)
    return root

static func barrier() -> Node3D:
    var root := instantiate_scene(BARRIER)
    _apply_barrier_industrial_material(root)
    _add_barrier_hazard_signature(root)
    return root

static func barrel() -> Node3D:
    var root := instantiate_scene(BARREL)
    _grade_mesh_tree(root, Color(0.64, 0.69, 0.72), 0.83, 0.28, "barrel")
    return root

static func pallet() -> Node3D:
    var root := instantiate_scene(PALLET)
    _grade_mesh_tree(root, Color(0.70, 0.64, 0.54), 0.91, 0.02, "pallet")
    return root

static func street_lights() -> Node3D:
    var root := instantiate_scene(STREET_LIGHTS)
    _apply_street_light_industrial_material(root)
    return root

static func traffic_cone() -> Node3D:
    var root := instantiate_scene(TRAFFIC_CONE)
    _grade_mesh_tree(root, Color(0.91, 0.78, 0.68), 0.86, 0.01, "traffic_cone")
    return root

static func trash_bag() -> Node3D:
    var root := instantiate_scene(TRASH_BAG)
    _grade_mesh_tree(root, Color(0.55, 0.63, 0.68), 0.94, 0.01, "trash_bag")
    return root

static func street_crack() -> Node3D:
    return instantiate_scene(STREET_CRACK)

static func industrial_floor_grate() -> Node3D:
    var root := instantiate_scene(INDUSTRIAL_FLOOR_GRATE)
    _grade_generated_industrial_kit(root, true)
    return root

static func industrial_bulkhead() -> Node3D:
    var root := instantiate_scene(INDUSTRIAL_BULKHEAD)
    _grade_generated_industrial_kit(root)
    return root

static func industrial_crate() -> Node3D:
    var root := instantiate_scene(INDUSTRIAL_CRATE)
    _grade_generated_industrial_kit(root)
    return root

static func industrial_pipe_rack() -> Node3D:
    var root := instantiate_scene(INDUSTRIAL_PIPE_RACK)
    _grade_generated_industrial_kit(root)
    return root

static func industrial_service_pillar() -> Node3D:
    var root := instantiate_scene(INDUSTRIAL_SERVICE_PILLAR)
    _grade_generated_industrial_kit(root)
    return root

static func _grade_generated_industrial_kit(root: Node3D, ground_detail := false) -> void:
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
        var identity := mesh_instance.name.to_lower()
        var finish := "steel"
        if "recess" in identity or "inset" in identity:
            finish = "recess"
        elif "hazard" in identity or "warning" in identity:
            finish = "hazard"
        elif "signal" in identity or "status" in identity or "lens" in identity or "capbolt" in identity:
            finish = "signal"
        elif "pipe_" in identity:
            finish = "rust"
        elif "slat" in identity:
            finish = "slat"
        var material := _industrial_kit_material(finish)
        mesh_instance.material_override = null
        for surface_index in range(mesh_instance.mesh.get_surface_count()):
            mesh_instance.set_surface_override_material(surface_index, material)
        if ground_detail:
            # The complete grate is inlaid into the arena floor; high-frequency
            # slats should not consume the sole directional shadow map.
            mesh_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF

static func _industrial_kit_material(finish: String) -> StandardMaterial3D:
    if _industrial_kit_material_cache.has(finish):
        return _industrial_kit_material_cache[finish] as StandardMaterial3D
    var mat := StandardMaterial3D.new()
    match finish:
        "recess":
            mat.albedo_color = Color(0.012, 0.019, 0.024)
            mat.metallic = 0.24
            mat.roughness = 0.90
        "slat":
            mat.albedo_color = Color(0.13, 0.17, 0.19)
            mat.metallic = 0.60
            mat.roughness = 0.68
        "hazard":
            mat.albedo_color = Color(0.56, 0.115, 0.025)
            mat.metallic = 0.28
            mat.roughness = 0.63
        "signal":
            mat.albedo_color = Color(0.035, 0.27, 0.36)
            mat.emission_enabled = true
            mat.emission = Color(0.015, 0.16, 0.23)
            mat.emission_energy_multiplier = 0.75
            mat.metallic = 0.22
            mat.roughness = 0.50
        "rust":
            mat.albedo_color = Color(0.24, 0.085, 0.04)
            mat.metallic = 0.37
            mat.roughness = 0.78
        _:
            mat.albedo_color = Color(0.070, 0.10, 0.115)
            mat.metallic = 0.66
            mat.roughness = 0.58
    _industrial_kit_material_cache[finish] = mat
    return mat

static func _apply_street_light_industrial_material(root: Node3D) -> void:
    if root == null:
        return
    # Preserve the authored two-surface GLTF: a painted atlas plus its
    # separate glass/lamp material. Material overrides flattened both into
    # featureless gray geometry in the combat arena.
    _grade_mesh_tree(root, Color(0.54, 0.67, 0.75), 0.78, 0.46, "street_lights")
    var meshes: Array[MeshInstance3D] = []
    if root is MeshInstance3D:
        meshes.append(root as MeshInstance3D)
    for node in root.find_children("*", "MeshInstance3D", true, false):
        meshes.append(node as MeshInstance3D)

    for mesh_instance in meshes:
        if mesh_instance == null or mesh_instance.mesh == null:
            continue
        for surface_index in range(mesh_instance.mesh.get_surface_count()):
            var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
            var graded := mesh_instance.get_surface_override_material(surface_index) as BaseMaterial3D
            if source == null or graded == null:
                continue
            if source.albedo_texture == null:
                # The authored lamp lens is a separate untextured surface.
                # Give it a low-power emissive material, not a second light.
                graded.albedo_color = Color(0.14, 0.48, 0.60, source.albedo_color.a)
                graded.emission_enabled = true
                graded.emission = Color(0.08, 0.40, 0.62)
                graded.emission_energy_multiplier = 1.15
                graded.metallic = 0.04
                graded.roughness = 0.34
        mesh_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_ON

static func _apply_barrier_industrial_material(root: Node3D) -> void:
    if root == null:
        return
    # Retain UVs and each Quaternius atlas rather than overwriting the
    # entire imported mesh with a single dark StandardMaterial3D.
    _grade_mesh_tree(root, Color(0.58, 0.70, 0.78), 0.84, 0.34, "barrier")

static func _add_barrier_hazard_signature(root: Node3D) -> void:
    if root == null:
        return
    if _shared_barrier_hazard_material == null:
        _shared_barrier_hazard_material = StandardMaterial3D.new()
        _shared_barrier_hazard_material.albedo_color = Color(0.92, 0.26, 0.035)
        _shared_barrier_hazard_material.emission_enabled = true
        _shared_barrier_hazard_material.emission = Color(0.68, 0.10, 0.01)
        _shared_barrier_hazard_material.emission_energy_multiplier = 0.72
        _shared_barrier_hazard_material.roughness = 0.54
    var material := _shared_barrier_hazard_material

    for side in [-1.0, 1.0]:
        var strip := MeshInstance3D.new()
        strip.name = "BarrierHazardFront" if side < 0.0 else "BarrierHazardRear"
        var mesh := BoxMesh.new()
        mesh.size = Vector3(0.74, 0.075, 0.018)
        strip.mesh = mesh
        strip.position = Vector3(0.0, 0.42, side * 0.176)
        strip.material_override = material
        strip.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        root.add_child(strip)

static func _grade_enemy_mesh_tree(root: Node3D, tint: Color, kind := "shambler") -> void:
    if root == null:
        return
    if root is MeshInstance3D:
        _grade_enemy_mesh_instance(root as MeshInstance3D, tint, kind)
    for node in root.find_children("*", "MeshInstance3D", true, false):
        _grade_enemy_mesh_instance(node as MeshInstance3D, tint, kind)

static func _grade_enemy_mesh_instance(mesh_instance: MeshInstance3D, tint: Color, kind := "shambler") -> void:
    if mesh_instance == null or mesh_instance.mesh == null or mesh_instance.mesh.get_surface_count() == 0:
        return

    # Imported characters may contain several authored surfaces. A single material_override
    # would repaint all of them with surface 0's atlas, so grade each surface independently.
    mesh_instance.material_override = null
    for surface_index in range(mesh_instance.mesh.get_surface_count()):
        var source := mesh_instance.get_active_material(surface_index)
        if not source is BaseMaterial3D:
            continue
        var source_material := source as BaseMaterial3D
        if source_material.albedo_texture == null:
            var fallback := source_material.duplicate(true) as BaseMaterial3D
            fallback.albedo_color = Color(
                fallback.albedo_color.r * tint.r,
                fallback.albedo_color.g * tint.g,
                fallback.albedo_color.b * tint.b,
                fallback.albedo_color.a
            )
            fallback.roughness = maxf(fallback.roughness, 0.82)
            fallback.metallic = maxf(fallback.metallic, 0.02)
            mesh_instance.set_surface_override_material(surface_index, fallback)
            continue

        mesh_instance.set_surface_override_material(
            surface_index,
            _enemy_surface_material(source_material, tint, kind)
        )

static func _enemy_surface_material(source_material: BaseMaterial3D, tint: Color, kind := "shambler") -> ShaderMaterial:
    var material := ShaderMaterial.new()
    material.shader = _get_enemy_grade_shader()
    material.set_shader_parameter("albedo_tex", source_material.albedo_texture)
    material.set_shader_parameter("body_tint", tint)
    material.set_shader_parameter("highlight_start", 0.30)
    material.set_shader_parameter("highlight_end", 0.72)
    material.set_shader_parameter("highlight_floor", 0.42)
    material.set_shader_parameter("authored_roughness", source_material.roughness)
    material.set_shader_parameter("authored_metallic", source_material.metallic)
    var rim_strength := 0.13
    var rim_power := 3.25
    match kind:
        "runner", "harrier":
            rim_strength = 0.16
            rim_power = 3.10
        "charger", "brute":
            rim_strength = 0.17
            rim_power = 3.05
        "regenerator", "elite":
            rim_strength = 0.21
            rim_power = 2.95
        "boss":
            rim_strength = 0.30
            rim_power = 2.65
    material.set_shader_parameter("rim_strength", rim_strength)
    material.set_shader_parameter("rim_power", rim_power)
    if source_material.normal_enabled and source_material.normal_texture != null:
        material.set_shader_parameter("use_normal_map", true)
        material.set_shader_parameter("normal_tex", source_material.normal_texture)
        material.set_shader_parameter("normal_scale", source_material.normal_scale)
    return material

static func _get_enemy_grade_shader() -> Shader:
    if _enemy_grade_shader != null:
        return _enemy_grade_shader

    _enemy_grade_shader = Shader.new()
    _enemy_grade_shader.code = """
shader_type spatial;
render_mode diffuse_burley, specular_schlick_ggx;

uniform sampler2D albedo_tex : source_color, filter_linear_mipmap_anisotropic;
uniform sampler2D normal_tex : hint_normal, filter_linear_mipmap_anisotropic;
uniform bool use_normal_map = false;
uniform float normal_scale = 1.0;
uniform vec4 body_tint : source_color = vec4(0.5, 0.6, 0.5, 1.0);
uniform float highlight_start = 0.34;
uniform float highlight_end = 0.82;
uniform float highlight_floor = 0.46;
uniform float authored_roughness = 0.84;
uniform float authored_metallic = 0.02;
uniform float rim_strength = 0.11;
uniform float rim_power = 3.4;

void fragment() {
    vec4 authored = texture(albedo_tex, UV);
    vec3 base = authored.rgb * body_tint.rgb;
    float luma = dot(base, vec3(0.2126, 0.7152, 0.0722));
    float compression = mix(1.0, highlight_floor, smoothstep(highlight_start, highlight_end, luma));
    ALBEDO = base * compression;
    ROUGHNESS = max(authored_roughness, 0.82);
    METALLIC = max(authored_metallic, 0.02);
    float fresnel = 1.0 - max(dot(normalize(NORMAL), normalize(VIEW)), 0.0);
    float rim = pow(fresnel, rim_power) * rim_strength;
    EMISSION = body_tint.rgb * rim;
    ALPHA = authored.a * body_tint.a;
    if (use_normal_map) {
        NORMAL_MAP = texture(normal_tex, UV).rgb;
        NORMAL_MAP_DEPTH = normal_scale;
    }
}
"""
    return _enemy_grade_shader

static func _grade_mesh_tree(root: Node3D, tint: Color, roughness: float, metallic: float, cache_family: String = "") -> void:
    if root == null:
        return
    if root is MeshInstance3D:
        _grade_mesh_instance(root as MeshInstance3D, tint, roughness, metallic, cache_family)
    for node in root.find_children("*", "MeshInstance3D", true, false):
        _grade_mesh_instance(node as MeshInstance3D, tint, roughness, metallic, cache_family)

static func _grade_mesh_instance(mesh_instance: MeshInstance3D, tint: Color, roughness: float, metallic: float, cache_family: String = "") -> void:
    if mesh_instance == null or mesh_instance.mesh == null or mesh_instance.mesh.get_surface_count() == 0:
        return

    mesh_instance.material_override = null
    for surface_index in range(mesh_instance.mesh.get_surface_count()):
        var source := mesh_instance.get_active_material(surface_index) as BaseMaterial3D
        if source == null:
            continue
        # Imported GLTF scenes sometimes localize their material resources on
        # every instantiate(), so object IDs are not a stable deduplication key.
        # Only cache static props with a known authored asset family: scene mesh
        # name + surface index + grade identify their immutable source surface.
        # Animated survivor and weapon surfaces remain instance-local.
        var grade_key := ""
        if not cache_family.is_empty():
            grade_key = "%s|%s|%s|%d|%s|%.3f|%.3f" % [
                cache_family, mesh_instance.name, source.resource_name,
                surface_index, tint.to_html(true), roughness, metallic
            ]
        var graded: BaseMaterial3D
        if not grade_key.is_empty():
            graded = _graded_pbr_material_cache.get(grade_key) as BaseMaterial3D
        if graded == null:
            graded = source.duplicate(true) as BaseMaterial3D
            graded.albedo_color = Color(
                graded.albedo_color.r * tint.r,
                graded.albedo_color.g * tint.g,
                graded.albedo_color.b * tint.b,
                graded.albedo_color.a
            )
            graded.roughness = maxf(graded.roughness, roughness)
            graded.metallic = maxf(graded.metallic, metallic)
            if not grade_key.is_empty():
                _graded_pbr_material_cache[grade_key] = graded
        mesh_instance.set_surface_override_material(surface_index, graded)

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

# Production audio is generated deterministically from original project synthesis.
# The in-engine chirp remains a resilience fallback when local generated assets are absent.

const AUTHORED_AUDIO_ROOT := "res://assets/audio/authored/"
const RUN_MUSIC_PATH := AUTHORED_AUDIO_ROOT + "music_run_loop.wav"
const PRESSURE_MUSIC_PATH := AUTHORED_AUDIO_ROOT + "music_pressure_layer.wav"
const BOSS_STINGER_PATH := AUTHORED_AUDIO_ROOT + "boss_stinger.wav"

const UI_PATHS := {
    "level_up": AUTHORED_AUDIO_ROOT + "ui_level_up.wav",
    "upgrade_confirm": AUTHORED_AUDIO_ROOT + "ui_upgrade_confirm.wav",
    "pause_toggle": AUTHORED_AUDIO_ROOT + "ui_pause_toggle.wav",
    "game_over": AUTHORED_AUDIO_ROOT + "ui_game_over.wav"
}

const SHOT_PATHS := {
    "vanguard": AUTHORED_AUDIO_ROOT + "weapon_vanguard.wav",
    "scatter": AUTHORED_AUDIO_ROOT + "weapon_scatter.wav",
    "rail": AUTHORED_AUDIO_ROOT + "weapon_rail.wav",
    "inferno": AUTHORED_AUDIO_ROOT + "weapon_inferno.wav",
    "cryo": AUTHORED_AUDIO_ROOT + "weapon_cryo.wav",
    "arc": AUTHORED_AUDIO_ROOT + "weapon_arc.wav"
}

const IMPACT_PATHS := {
    "hit": AUTHORED_AUDIO_ROOT + "impact_hit.wav",
    "critical": AUTHORED_AUDIO_ROOT + "impact_critical.wav",
    "kill": AUTHORED_AUDIO_ROOT + "impact_kill.wav",
    "boss": AUTHORED_AUDIO_ROOT + "impact_boss.wav"
}

static var _authored_cache := {}

static func _authored_stream(path: String) -> AudioStream:
    if _authored_cache.has(path):
        return _authored_cache[path] as AudioStream
    if not ResourceLoader.exists(path):
        return null
    var stream := load(path) as AudioStream
    if stream != null:
        _authored_cache[path] = stream
    return stream

static func authored_shot_stream(profile: String) -> AudioStream:
    var path := String(SHOT_PATHS.get(profile, SHOT_PATHS["vanguard"]))
    return _authored_stream(path)

static func authored_impact_stream(critical: bool, killed: bool, boss: bool) -> AudioStream:
    var key := "boss" if boss else ("kill" if killed else ("critical" if critical else "hit"))
    return _authored_stream(String(IMPACT_PATHS[key]))

static func ui_stream(key: String) -> AudioStream:
    var path := String(UI_PATHS.get(key, ""))
    if not path.is_empty():
        var authored := _authored_stream(path)
        if authored != null:
            return authored
    match key:
        "level_up":
            return _chirp(520.0, 1540.0, 0.28, 0.08, 0.72)
        "upgrade_confirm":
            return _chirp(980.0, 1260.0, 0.16, 0.06, 0.62)
        "game_over":
            return _chirp(260.0, 52.0, 0.62, 0.24, 0.78)
        _:
            return _chirp(420.0, 260.0, 0.10, 0.05, 0.52)

static func shot_stream(profile: String) -> AudioStream:
    var authored := authored_shot_stream(profile)
    if authored != null:
        return authored
    var spec: Array = {
        "vanguard": [1180.0, 720.0, 0.055, 0.20],
        "scatter": [520.0, 220.0, 0.085, 0.34],
        "rail": [1960.0, 980.0, 0.070, 0.18],
        "inferno": [760.0, 330.0, 0.080, 0.28],
        "cryo": [1540.0, 1080.0, 0.072, 0.16],
        "arc": [1320.0, 460.0, 0.075, 0.22]
    }.get(profile, [1180.0, 720.0, 0.055, 0.20]) as Array
    return _chirp(float(spec[0]), float(spec[1]), float(spec[2]), float(spec[3]), 0.82)

static func impact_stream(critical: bool, killed: bool, boss: bool) -> AudioStream:
    var authored := authored_impact_stream(critical, killed, boss)
    if authored != null:
        return authored
    if boss:
        return _chirp(210.0, 92.0, 0.120, 0.42, 0.92)
    if killed:
        return _chirp(390.0, 145.0, 0.095, 0.34, 0.88)
    if critical:
        return _chirp(980.0, 420.0, 0.082, 0.24, 0.88)
    return _chirp(640.0, 260.0, 0.052, 0.18, 0.72)

static func boss_stinger() -> AudioStream:
    var authored := _authored_stream(BOSS_STINGER_PATH)
    if authored != null:
        return authored
    return _chirp(170.0, 52.0, 1.45, 0.50, 0.94)

static func run_music_stream() -> AudioStream:
    var authored := _authored_stream(RUN_MUSIC_PATH)
    if authored == null:
        return null
    if authored is AudioStreamWAV:
        var looped := authored.duplicate(true) as AudioStreamWAV
        looped.loop_mode = AudioStreamWAV.LOOP_FORWARD
        looped.loop_begin = 0
        looped.loop_end = maxi(1, int(round(looped.get_length() * float(looped.mix_rate))))
        return looped
    return authored

static func pressure_music_stream() -> AudioStream:
    var authored := _authored_stream(PRESSURE_MUSIC_PATH)
    if authored == null:
        return null
    if authored is AudioStreamWAV:
        var looped := authored.duplicate(true) as AudioStreamWAV
        looped.loop_mode = AudioStreamWAV.LOOP_FORWARD
        looped.loop_begin = 0
        looped.loop_end = maxi(1, int(round(looped.get_length() * float(looped.mix_rate))))
        return looped
    return authored

static func _chirp(start_hz: float, end_hz: float, seconds: float, noise_mix: float,
        gain: float) -> AudioStreamWAV:
    var rate: int = 44100
    var frames: int = maxi(128, int(seconds * rate))
    var bytes := PackedByteArray()
    bytes.resize(frames * 2)
    var phase: float = 0.0
    var sub_phase: float = 0.0
    var noise_state: int = int(absf(start_hz * 131.0 + end_hz * 47.0 + seconds * 100000.0)) | 1

    for i in range(frames):
        var t: float = float(i) / float(maxi(1, frames - 1))
        var hz_curve := t * t * (3.0 - 2.0 * t)
        var hz: float = lerpf(start_hz, end_hz, hz_curve)
        phase += TAU * hz / float(rate)
        sub_phase += TAU * maxf(48.0, hz * 0.47) / float(rate)

        noise_state = int((1103515245 * noise_state + 12345) & 0x7fffffff)
        var noise := (float(noise_state) / 1073741823.5 - 1.0)

        var attack := clampf(t / 0.018, 0.0, 1.0)
        var decay := pow(maxf(0.0, 1.0 - t), 2.05)
        var envelope := attack * decay

        var fundamental := sin(phase)
        var harmonic := sin(phase * 2.03 + 0.35) * 0.24
        var body := sin(sub_phase) * 0.32
        var transient_window := pow(maxf(0.0, 1.0 - t / 0.16), 4.0)
        var transient := noise * transient_window * minf(0.62, noise_mix + 0.18)
        var texture := noise * noise_mix * 0.34

        var tonal_mix := fundamental * 0.72 + harmonic + body
        var raw := (tonal_mix * (1.0 - noise_mix * 0.46) + texture + transient) * envelope * gain
        var sample := tanh(raw * 1.28) / tanh(1.28)
        sample = clampf(sample, -0.985, 0.985)

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

static func impact_fov_pulse(critical: bool, killed: bool, boss: bool) -> float:
    var pulse := 0.0
    if critical:
        pulse += 0.48
    if killed:
        pulse += 0.62
    if boss:
        pulse += 0.46
    return minf(pulse, 1.30)

static func damage_received_camera_kick(damage: float, max_health: float) -> float:
    if damage <= 0.0 or max_health <= 0.0:
        return 0.0
    var severity := clampf(damage / max_health, 0.0, 0.35)
    return clampf(0.045 + severity * 0.22, 0.045, 0.115)

static func unscaled_delta(scaled_delta: float, time_scale: float) -> float:
    if scaled_delta <= 0.0:
        return 0.0
    return scaled_delta / maxf(time_scale, 0.01)
```

## File: scripts/CombatGroundMark.gd
```
class_name DZCombatGroundMark
extends MeshInstance3D

# One textured, shadowless ground quad per kill. Dense runs stay bounded to
# sixteen short-lived marks; no particles, physics bodies, or dynamic lights.
const MAX_ACTIVE := 16
const VISIBLE_SECONDS := 1.5
const FADE_SECONDS := 6.5
const GROUND_Y := 0.048
const SCORCH_TEXTURE := "res://assets/decals/quarantine_yard/scorch_a.png"
const BLOOD_TEXTURE := "res://assets/decals/quarantine_yard/blood_a.png"

static var _shared_quad: QuadMesh

var boss_mark := false
var fade_tween: Tween

static func spawn_mark(parent: Node3D, at: Vector3, is_boss: bool = false) -> DZCombatGroundMark:
    if parent == null or not is_instance_valid(parent) or not parent.is_inside_tree():
        return null
    var active: Array[Node] = []
    for node in parent.get_tree().get_nodes_in_group("combat_ground_marks"):
        if is_instance_valid(node) and not node.is_queued_for_deletion():
            active.append(node)
    if active.size() >= MAX_ACTIVE:
        # Avoid immediate free() while an enemy death callback is running
        # inside the physics frame.
        active[0].queue_free()
    var mark := DZCombatGroundMark.new()
    mark.boss_mark = is_boss
    parent.add_child(mark)
    mark.global_position = Vector3(at.x, GROUND_Y, at.z)
    mark.rotation_degrees = Vector3(-90.0, randf_range(-180.0, 180.0), 0.0)
    return mark

func _ready() -> void:
    name = "BossGroundScorch" if boss_mark else "EnemyGroundStain"
    add_to_group("combat_ground_marks")
    cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    mesh = _ground_quad()

    var material := StandardMaterial3D.new()
    material.albedo_texture = load(SCORCH_TEXTURE if boss_mark else BLOOD_TEXTURE) as Texture2D
    material.albedo_color = Color(0.42, 0.35, 0.28, 0.40) if boss_mark else Color(0.52, 0.19, 0.17, 0.30)
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.roughness = 0.94 if boss_mark else 0.88
    material.metallic = 0.0
    material_override = material

    var diameter := 1.95 if boss_mark else randf_range(0.70, 1.12)
    scale = Vector3(diameter, diameter * 0.84, 1.0)

    fade_tween = create_tween()
    fade_tween.tween_interval(VISIBLE_SECONDS)
    fade_tween.tween_property(material, "albedo_color:a", 0.0, FADE_SECONDS).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_IN)
    fade_tween.tween_callback(queue_free)

static func _ground_quad() -> QuadMesh:
    if _shared_quad == null:
        _shared_quad = QuadMesh.new()
        _shared_quad.size = Vector2.ONE
    return _shared_quad
```

## File: scripts/Enemy.gd
```
class_name DZEnemy
extends CharacterBody3D

signal died(xp_value: int, at: Vector3)
signal impact(at: Vector3, critical: bool, killed: bool, boss: bool)
signal health_changed(current: float, maximum: float)
signal boss_phase_changed(phase: int, at: Vector3)

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
var boss_presence_clock := 0.0
var boss_aura_root: Node3D
var boss_aura_inner: MeshInstance3D
var boss_aura_outer: MeshInstance3D
var boss_aura_inner_material: StandardMaterial3D
var boss_aura_outer_material: StandardMaterial3D
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
var spawn_reveal_tween: Tween
var visual_rest_scale := Vector3.ONE
var visual_rest_position := Vector3.ZERO
static var _shared_contact_shadow_material: ShaderMaterial
static var _contact_shadow_mesh_cache := {}
static var _signature_material_cache := {}
static var _signature_mesh_cache := {}
static var _shared_brute_armor_material: StandardMaterial3D
static var _telegraph_ring_mesh_cache := {}
static var _telegraph_tick_mesh_cache := {}
static var _hit_flash_mesh_cache := {}
static var _shared_regeneration_pulse_mesh: CylinderMesh

const MAX_DAMAGE_NUMBERS := 18
const ARENA_HALF_EXTENT := 34.0

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
    # Capture each imported archetype's immutable silhouette transform before
    # spawn reveals and hit reactions begin moving its visual root.
    if authored_visual != null:
        visual_rest_scale = authored_visual.scale
        visual_rest_position = authored_visual.position
    else:
        var fallback_visual := get_node_or_null("Visual") as Node3D
        if fallback_visual != null:
            visual_rest_scale = fallback_visual.scale
            visual_rest_position = fallback_visual.position
    _build_contact_shadow()
    _build_hit_flash()
    _apply_mobile_shadow_budget()
    _play_spawn_reveal()

func _apply_mobile_shadow_budget() -> void:
    # The arena has one real directional shadow map; a swarm of 100+ animated
    # characters should not render all of its geometry into that map.
    # Every enemy retains its already-authored planar contact shadow, while the
    # boss and elite bodies keep their imported dynamic shadow settings.
    # Emissive silhouettes and transient hit geometry never cast shadows.
    var keep_body_shadows := kind == "boss" or kind == "elite"
    for node in find_children("*", "MeshInstance3D", true, false):
        var mesh := node as MeshInstance3D
        if mesh == null:
            continue
        var is_body := authored_visual != null and (
            mesh == authored_visual or authored_visual.is_ancestor_of(mesh)
        )
        if not (keep_body_shadows and is_body):
            mesh.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF

func _physics_process(delta: float) -> void:
    if not combat_enabled:
        velocity = Vector3.ZERO
        return
    _process_status_effects(delta)
    var shock_was_active := shock_left > 0.0
    shock_left = maxf(0.0, shock_left - maxf(delta, 0.0))
    if dead or target == null or not is_instance_valid(target):
        return
    # Freeze the entire final shocked frame, even if the timer crosses zero
    # within this physics step. The next frame resumes from the correct pose.
    if shock_was_active:
        velocity = Vector3.ZERO
        if authored_anim != null:
            authored_anim.speed_scale = 0.0
        return
    if kind == "boss":
        _update_boss_phase()
        _update_boss_presence(delta)
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
        var movement_speed_scale := 1.0
        if kind == "harrier":
            if distance < 4.4:
                movement_direction = -movement_direction
            elif distance <= 6.6:
                movement_direction = Vector3(-movement_direction.z, 0.0, movement_direction.x)

        # Local separation keeps the swarm readable and prevents every body from collapsing
        # onto the same target point. Main.gd serves this from its spatial hash in production.
        var separation_radius := 2.05 if kind in ["boss", "brute", "charger"] else 1.62
        var separation := separation_vector(_nearby_enemies_for_separation(separation_radius), separation_radius)
        if separation.length_squared() > 0.001:
            var separation_weight := 0.88 if kind == "boss" else (1.62 if kind == "harrier" else 1.56)
            movement_direction = (movement_direction + separation * separation_weight).normalized()

        # Maintain a visible melee envelope around the survivor instead of letting bodies
        # occupy the same screen-space footprint. Enemies can still attack from this envelope.
        if kind != "harrier":
            var standoff := _melee_standoff_distance()
            var outward := global_position - target.global_position
            outward.y = 0.0
            if outward.length_squared() > 0.001:
                var radial := outward.normalized()
                var tangent := Vector3(-radial.z, 0.0, radial.x)
                if int(get_instance_id()) % 2 == 0:
                    tangent = -tangent

                if distance < standoff:
                    var penetration := clampf((standoff - distance) / maxf(standoff, 0.01), 0.0, 1.0)
                    movement_direction = (
                        radial * (1.42 + penetration * 1.05)
                        + tangent * 0.58
                        + separation * 0.72
                    ).normalized()
                    movement_speed_scale = lerpf(0.44, 0.90, penetration)
                elif distance < standoff + 0.62:
                    var settle := 1.0 - clampf((distance - standoff) / 0.62, 0.0, 1.0)
                    movement_direction = (movement_direction + tangent * settle * 0.24 + separation * settle * 0.32).normalized()
                    movement_speed_scale = lerpf(0.70, 1.0, 1.0 - settle)

        velocity = movement_direction * move_speed * slow_multiplier * movement_speed_scale
        move_and_slide()
        _constrain_to_arena()
        if velocity.length_squared() > 0.01:
            look_at(global_position + velocity, Vector3.UP)
    _update_authored_animation(distance)
    if distance < _contact_attack_range() and attack_cooldown <= 0.0 and target.has_method("take_damage"):
        target.take_damage(contact_damage)
        attack_cooldown = 0.72

func _constrain_to_arena() -> void:
    var clamped_x := clampf(global_position.x, -ARENA_HALF_EXTENT, ARENA_HALF_EXTENT)
    var clamped_z := clampf(global_position.z, -ARENA_HALF_EXTENT, ARENA_HALF_EXTENT)
    if not is_equal_approx(clamped_x, global_position.x):
        velocity.x = 0.0
    if not is_equal_approx(clamped_z, global_position.z):
        velocity.z = 0.0
    global_position.x = clamped_x
    global_position.z = clamped_z

func _melee_standoff_distance() -> float:
    match kind:
        "boss":
            return 1.82
        "brute":
            return 1.42
        "charger":
            return 1.28
        "elite":
            return 1.22
        "regenerator":
            return 1.16
        _:
            return 1.08

func _contact_attack_range() -> float:
    return _melee_standoff_distance() + (0.24 if kind in ["boss", "brute"] else 0.20)

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
    var next_phase := 3 if ratio <= 0.30 else (2 if ratio <= 0.65 else 1)
    if next_phase != boss_phase:
        boss_phase = next_phase
        _refresh_boss_presence_style()
        boss_phase_changed.emit(boss_phase, global_position + Vector3(0.0, 0.78, 0.0))
    else:
        boss_phase = next_phase
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

func _boss_aftershock_count() -> int:
    return 2 if boss_phase >= 3 else (1 if boss_phase >= 2 else 0)

func _schedule_boss_aftershocks(at: Vector3) -> void:
    var count := _boss_aftershock_count()
    for index in range(count):
        var delay := (0.30 + float(index) * 0.28) if boss_phase >= 3 else 0.36
        var radius := 2.50 + float(index) * 0.66
        var damage_scale := 0.42 if index == 0 else 0.32
        _show_boss_aftershock(at, radius, delay, damage_scale)

func _show_boss_aftershock(at: Vector3, radius: float, delay: float, damage_scale: float) -> void:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene == null:
        return
    var warning := MeshInstance3D.new()
    warning.name = "BossAftershockWarning"
    warning.mesh = _telegraph_ring_mesh(radius, true)
    warning.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    scene.add_child(warning)
    warning.global_position = at + Vector3(0.0, 0.040, 0.0)

    var material := StandardMaterial3D.new()
    material.albedo_color = Color(1.0, 0.10, 0.025, 0.30)
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.emission_enabled = true
    material.emission = Color(1.0, 0.055, 0.008)
    material.emission_energy_multiplier = 2.0
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    warning.material_override = material
    warning.scale = Vector3(0.58, 1.0, 0.58)

    var tween := warning.create_tween()
    tween.set_parallel(true)
    tween.tween_property(warning, "scale", Vector3.ONE, delay).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(material, "emission_energy_multiplier", 5.2, delay).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.tween_property(material, "albedo_color", Color(1.0, 0.045, 0.006, 0.82), delay).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.chain().tween_callback(_resolve_boss_aftershock.bind(at, radius, contact_damage * damage_scale, warning))

func _resolve_boss_aftershock(at: Vector3, radius: float, damage: float, warning: MeshInstance3D) -> void:
    if warning != null and is_instance_valid(warning):
        warning.queue_free()
    if target != null and is_instance_valid(target) and target.has_method("take_damage"):
        var flat_delta := target.global_position - at
        flat_delta.y = 0.0
        if flat_delta.length() <= radius:
            target.take_damage(damage)
    _spawn_attack_impact(at, radius * 0.72)

func _process_charge(delta: float) -> void:
    charge_left = max(0.0, charge_left - delta)
    velocity = charge_direction * 9.4
    move_and_slide()
    _constrain_to_arena()
    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity, Vector3.UP)
    _update_authored_animation(8.0)
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
    if authored_anim != null:
        authored_anim.speed_scale = 1.0
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
    # Aim the imported body at the *locked* attack point before playing its
    # windup. Harrier strafing and swarm separation can otherwise leave the
    # windup clip pointing sideways relative to the danger corridor. Never
    # track the survivor after the tell begins: dodging must still matter.
    var aim_delta := attack_target_position - global_position
    if aim_delta.length_squared() > 0.0025:
        look_at(attack_target_position, Vector3.UP)
    velocity = Vector3.ZERO
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
    if kind == "boss" and boss_phase >= 2:
        _schedule_boss_aftershocks(impact_point)
    attack_cooldown = 0.88 if kind == "boss" else 0.64

func _show_telegraph(radius: float, duration: float) -> void:
    if telegraph_visual != null and is_instance_valid(telegraph_visual):
        telegraph_visual.queue_free()
    telegraph_visual = MeshInstance3D.new()
    telegraph_visual.name = "AttackTelegraphRing"
    telegraph_visual.mesh = _telegraph_ring_mesh(radius, kind == "boss")
    telegraph_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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

    if pending_special == "charge" or pending_special == "harrier_shot":
        _add_directional_telegraph_lane()

    # Threat class changes the ring language without filling the danger area:
    # normal = 4 ticks, elite = 6 ticks, boss = 8 ticks plus a second inner ring.
    var tick_count := 8 if kind == "boss" else (6 if kind == "elite" else 4)
    for tick_index in range(tick_count):
        var angle := TAU * float(tick_index) / float(tick_count)
        var tick := MeshInstance3D.new()
        tick.name = "TelegraphTick_%d" % tick_index
        tick.mesh = _telegraph_tick_mesh(radius)
        tick.position = Vector3(cos(angle) * radius * 0.72, 0.0, sin(angle) * radius * 0.72)
        tick.rotation.y = -angle
        tick.material_override = telegraph_material
        tick.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        telegraph_visual.add_child(tick)

    if kind == "boss":
        var inner_ring := MeshInstance3D.new()
        inner_ring.name = "TelegraphInnerRing"
        inner_ring.mesh = _telegraph_ring_mesh(radius * 0.48, true)
        inner_ring.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        inner_ring.material_override = telegraph_material
        telegraph_visual.add_child(inner_ring)

    var tween := telegraph_visual.create_tween()
    tween.set_parallel(true)
    telegraph_visual.scale = Vector3(0.42, 1.0, 0.42)
    tween.tween_property(telegraph_visual, "scale", Vector3.ONE, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    if kind == "boss":
        tween.tween_property(telegraph_visual, "rotation:y", PI * 0.25, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    elif kind == "elite":
        tween.tween_property(telegraph_visual, "rotation:y", PI / 6.0, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    tween.tween_property(telegraph_material, "emission_energy_multiplier", 5.8 if kind == "boss" else 4.6, duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.tween_property(telegraph_material, "albedo_color", Color(1.0, 0.07, 0.008, 0.92 if kind == "boss" else 0.78), duration).set_trans(Tween.TRANS_EXPO).set_ease(Tween.EASE_IN)
    tween.chain().tween_callback(telegraph_visual.queue_free)

func _add_directional_telegraph_lane() -> void:
    if telegraph_visual == null or telegraph_material == null:
        return
    var delta := attack_target_position - global_position
    delta.y = 0.0
    var distance := delta.length()
    if distance < 0.25:
        return

    var lane := MeshInstance3D.new()
    lane.name = "ChargeLane" if pending_special == "charge" else "HarrierAimLane"
    var lane_mesh := BoxMesh.new()
    var width := 0.30 if pending_special == "charge" else 0.11
    lane_mesh.size = Vector3(width, 0.014, distance)
    lane.mesh = lane_mesh
    lane.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    lane.material_override = telegraph_material
    telegraph_visual.add_child(lane)

    var midpoint := global_position.lerp(attack_target_position, 0.5) + Vector3(0.0, 0.006, 0.0)
    lane.global_position = midpoint
    lane.rotation.y = atan2(delta.x, delta.z)

static func _telegraph_ring_mesh(radius: float, boss: bool) -> TorusMesh:
    var key := "%.3f|%s" % [radius, "boss" if boss else "normal"]
    if _telegraph_ring_mesh_cache.has(key):
        return _telegraph_ring_mesh_cache[key] as TorusMesh
    var mesh := TorusMesh.new()
    mesh.inner_radius = radius * (0.82 if boss else 0.86)
    mesh.outer_radius = radius
    mesh.rings = 40 if boss else 32
    mesh.ring_segments = 8
    _telegraph_ring_mesh_cache[key] = mesh
    return mesh

static func _telegraph_tick_mesh(radius: float) -> BoxMesh:
    var key := "%.3f" % radius
    if _telegraph_tick_mesh_cache.has(key):
        return _telegraph_tick_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = Vector3(radius * 0.24, 0.012, maxf(0.035, radius * 0.045))
    _telegraph_tick_mesh_cache[key] = mesh
    return mesh

func _spawn_attack_impact(at: Vector3, radius: float) -> void:
    if not spawn_secondary_fx:
        return
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.22, 0.05) if kind == "boss" else Color(0.72, 0.28, 1.0)
    fx.scale_boost = radius * 1.35
    get_tree().current_scene.add_child(fx)
    fx.global_position = at + Vector3(0.0, 0.10, 0.0)

static func _regeneration_pulse_mesh() -> CylinderMesh:
    if _shared_regeneration_pulse_mesh != null:
        return _shared_regeneration_pulse_mesh
    _shared_regeneration_pulse_mesh = CylinderMesh.new()
    _shared_regeneration_pulse_mesh.top_radius = 0.88
    _shared_regeneration_pulse_mesh.bottom_radius = 0.88
    _shared_regeneration_pulse_mesh.height = 0.022
    _shared_regeneration_pulse_mesh.radial_segments = 16
    return _shared_regeneration_pulse_mesh

func _begin_regeneration() -> void:
    if dead or not combat_enabled or health <= 0.0 or health >= max_health:
        return
    regeneration_windup = 0.42
    if regeneration_visual != null and is_instance_valid(regeneration_visual):
        regeneration_visual.queue_free()
    var pulse := MeshInstance3D.new()
    pulse.name = "RegenerationPulse"
    pulse.mesh = _regeneration_pulse_mesh()
    pulse.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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
    _spawn_damage_number(amount, critical, killed)
    _play_hit_reaction(critical, killed)
    if killed:
        dead = true
        velocity = Vector3.ZERO
        died.emit(xp_value, global_position)
        if authored_anim != null and authored_anim.has_animation("Death"):
            authored_anim.speed_scale = 1.0
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
    # Rapid multishot hits may interrupt the previous recoil at its peak.
    # Restart from the authored pose rather than compounding current scale
    # and displacement. Cancel spawn reveal as well so both tweens never
    # fight over the same imported GLTF visual transform.
    if hit_reaction_tween != null and hit_reaction_tween.is_valid():
        hit_reaction_tween.kill()
    if spawn_reveal_tween != null and spawn_reveal_tween.is_valid():
        spawn_reveal_tween.kill()
    visual.scale = visual_rest_scale
    visual.position = visual_rest_position
    var base_scale := visual_rest_scale
    var punch := float(profile["punch"]) * (1.035 if critical else 1.0)
    var duration := float(profile["duration"])
    var recoil := float(profile["recoil"])
    var base_position := visual_rest_position
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

func _build_contact_shadow() -> void:
    var shadow := MeshInstance3D.new()
    shadow.name = "EnemyContactShadow"
    var radius := 0.42
    match kind:
        "runner":
            radius = 0.36
        "charger":
            radius = 0.50
        "harrier":
            radius = 0.40
        "regenerator":
            radius = 0.47
        "brute":
            radius = 0.58
        "elite":
            radius = 0.54
        "boss":
            radius = 0.90
    shadow.mesh = _contact_shadow_mesh(radius)
    shadow.rotation_degrees.x = -90.0
    shadow.position.y = 0.012
    shadow.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    shadow.material_override = _enemy_contact_shadow_material()
    add_child(shadow)

func _play_spawn_reveal() -> void:
    var visual := get_node_or_null("Visual") as Node3D
    var shadow := get_node_or_null("EnemyContactShadow") as MeshInstance3D
    if visual == null or shadow == null:
        return
    if spawn_reveal_tween != null and spawn_reveal_tween.is_valid():
        spawn_reveal_tween.kill()

    var final_visual_position := visual.position
    var final_shadow_scale := shadow.scale
    visual.position = final_visual_position + Vector3(0.0, -0.12 if kind == "boss" else -0.075, 0.0)
    shadow.scale = final_shadow_scale * (0.46 if kind == "boss" else 0.62)

    var duration := 0.30 if kind == "boss" else 0.18
    spawn_reveal_tween = create_tween()
    spawn_reveal_tween.set_parallel(true)
    spawn_reveal_tween.tween_property(visual, "position", final_visual_position, duration).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    spawn_reveal_tween.tween_property(shadow, "scale", final_shadow_scale, duration).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

static func _contact_shadow_mesh(radius: float) -> QuadMesh:
    # One 2-triangle plane per enemy instead of a 16-segment opaque disk.
    # UV-driven radial falloff preserves depth grounding without hard edges.
    var key := "%.3f" % radius
    if _contact_shadow_mesh_cache.has(key):
        return _contact_shadow_mesh_cache[key] as QuadMesh
    var mesh := QuadMesh.new()
    mesh.size = Vector2.ONE * radius * 2.0
    _contact_shadow_mesh_cache[key] = mesh
    return mesh

static func _enemy_contact_shadow_material() -> ShaderMaterial:
    if _shared_contact_shadow_material != null:
        return _shared_contact_shadow_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_mix, cull_disabled, depth_draw_never;

uniform vec4 shadow_tint : source_color = vec4(0.006, 0.008, 0.010, 1.0);
uniform float shadow_opacity : hint_range(0.0, 0.6) = 0.35;

void fragment() {
    vec2 disk_uv = (UV - vec2(0.5)) * 2.0;
    float radius = length(disk_uv);
    float feather = 1.0 - smoothstep(0.18, 1.0, radius);
    ALBEDO = shadow_tint.rgb;
    ALPHA = feather * feather * shadow_opacity;
}
"""
    _shared_contact_shadow_material = ShaderMaterial.new()
    _shared_contact_shadow_material.shader = shader
    _shared_contact_shadow_material.set_shader_parameter("shadow_opacity", 0.35)
    return _shared_contact_shadow_material

static func _hit_flash_mesh(scale_factor: float) -> CylinderMesh:
    var key := "%.3f" % scale_factor
    if _hit_flash_mesh_cache.has(key):
        return _hit_flash_mesh_cache[key] as CylinderMesh
    var mesh := CylinderMesh.new()
    mesh.top_radius = 0.46 * scale_factor
    mesh.bottom_radius = 0.52 * scale_factor
    mesh.height = 1.28 * scale_factor
    mesh.radial_segments = 12
    _hit_flash_mesh_cache[key] = mesh
    return mesh

func _build_hit_flash() -> void:
    hit_flash_visual = MeshInstance3D.new()
    hit_flash_visual.name = "HitFlash"
    var scale_factor := 1.0
    if kind == "boss": scale_factor = 1.84
    elif kind in ["elite", "brute", "charger"]: scale_factor = 1.18
    hit_flash_visual.mesh = _hit_flash_mesh(scale_factor)
    hit_flash_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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

func _spawn_damage_number(amount: float, critical: bool, killed: bool) -> void:
    if get_tree() == null or get_tree().current_scene == null:
        return
    var active_numbers := get_tree().get_nodes_in_group("damage_numbers").size()
    if active_numbers >= MAX_DAMAGE_NUMBERS and not critical and not killed:
        return
    var number := Label3D.new()
    number.name = "DamageNumber_%d" % Time.get_ticks_usec()
    number.add_to_group("damage_numbers")
    number.text = "%d" % int(round(amount))
    number.font_size = 52 if critical else (44 if killed else 34)
    number.outline_size = 9 if critical or killed else 7
    number.modulate = Color(1.0, 0.72, 0.12) if critical else (Color(1.0, 0.42, 0.16) if killed else Color(0.92, 0.97, 1.0))
    number.outline_modulate = Color(0.02, 0.03, 0.05, 0.96)
    number.billboard = BaseMaterial3D.BILLBOARD_ENABLED
    number.no_depth_test = true
    number.pixel_size = 0.0074 if critical else (0.0069 if killed else 0.0062)
    get_tree().current_scene.add_child(number)
    number.global_position = global_position + Vector3(0.0, 1.28, 0.0)

    var rise := 0.96 if critical else (0.78 if killed else 0.66)
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
            "boss": scale_factor = 1.82
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

static func _signature_box_mesh(key: String, size: Vector3) -> BoxMesh:
    if _signature_mesh_cache.has(key):
        return _signature_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = size
    _signature_mesh_cache[key] = mesh
    return mesh

static func _signature_sphere_mesh(key: String, radius: float, height: float) -> SphereMesh:
    if _signature_mesh_cache.has(key):
        return _signature_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = radius
    mesh.height = height
    mesh.radial_segments = 10
    mesh.rings = 5
    _signature_mesh_cache[key] = mesh
    return mesh

func _signature_material(color: Color, energy := 2.2) -> StandardMaterial3D:
    var key := "%s|%.3f" % [color.to_html(true), energy]
    if _signature_material_cache.has(key):
        return _signature_material_cache[key] as StandardMaterial3D
    var mat := StandardMaterial3D.new()
    mat.albedo_color = color
    mat.metallic = 0.24
    mat.roughness = 0.34
    mat.emission_enabled = true
    mat.emission = color
    mat.emission_energy_multiplier = energy
    _signature_material_cache[key] = mat
    return mat

func _add_eye_beacon(color: Color, at: Vector3, size: float) -> void:
    var beacon := MeshInstance3D.new()
    beacon.mesh = _signature_sphere_mesh("beacon_%.3f" % size, size, size * 2.0)
    beacon.name = "SignatureBeacon"
    beacon.position = at
    beacon.material_override = _signature_material(color, 3.2)
    add_child(beacon)

func _add_runner_blades(color: Color) -> void:
    var mat := _signature_material(color, 1.85)
    for side in [-1.0, 1.0]:
        var blade := MeshInstance3D.new()
        # Extend the signature in the ground plane so it reads from the gameplay camera,
        # rather than relying on vertical geometry that collapses in top-down projection.
        blade.mesh = _signature_box_mesh("runner_blade", Vector3(0.060, 0.22, 0.42))
        blade.name = "RunnerBladeL" if side < 0.0 else "RunnerBladeR"
        blade.position = Vector3(side * 0.43, 0.82, 0.02)
        blade.rotation_degrees = Vector3(0.0, side * 18.0, side * -20.0)
        blade.material_override = mat
        add_child(blade)
    _add_eye_beacon(color, Vector3(0.0, 1.54, -0.30), 0.060)

static func _brute_armor_material() -> StandardMaterial3D:
    if _shared_brute_armor_material != null:
        return _shared_brute_armor_material
    _shared_brute_armor_material = StandardMaterial3D.new()
    _shared_brute_armor_material.albedo_color = Color(0.055, 0.072, 0.080)
    _shared_brute_armor_material.metallic = 0.54
    _shared_brute_armor_material.roughness = 0.56
    return _shared_brute_armor_material

func _add_brute_shoulders(color: Color) -> void:
    var armor := _brute_armor_material()
    var accent := _signature_material(color, 1.55)
    for side in [-1.0, 1.0]:
        var plate := MeshInstance3D.new()
        plate.mesh = _signature_box_mesh("brute_plate", Vector3(0.28, 0.12, 0.34))
        plate.name = "BrutePlateL" if side < 0.0 else "BrutePlateR"
        plate.position = Vector3(side * 0.47, 1.12, 0.03)
        plate.rotation_degrees = Vector3(-4.0, side * 7.0, side * -12.0)
        plate.material_override = armor
        add_child(plate)

        var edge := MeshInstance3D.new()
        edge.mesh = _signature_box_mesh("brute_edge", Vector3(0.045, 0.045, 0.26))
        edge.name = "BruteEdgeL" if side < 0.0 else "BruteEdgeR"
        edge.position = Vector3(side * 0.57, 1.14, -0.02)
        edge.rotation_degrees = plate.rotation_degrees
        edge.material_override = accent
        add_child(edge)
    _add_eye_beacon(color, Vector3(0.0, 1.72, -0.34), 0.060)

func _add_elite_crown(color: Color) -> void:
    var mat := _signature_material(color, 1.95)
    for side in [-1.0, 1.0]:
        var fin := MeshInstance3D.new()
        fin.mesh = _signature_box_mesh("elite_fin", Vector3(0.055, 0.38, 0.10))
        fin.name = "EliteFinL" if side < 0.0 else "EliteFinR"
        fin.position = Vector3(side * 0.31, 1.62, 0.06)
        fin.rotation_degrees.z = side * -28.0
        fin.material_override = mat
        add_child(fin)
    _add_eye_beacon(color, Vector3(0.0, 1.70, -0.34), 0.075)

func _add_boss_frame(color: Color) -> void:
    var mat := _signature_material(color, 3.7)
    for side in [-1.0, 1.0]:
        var wing := MeshInstance3D.new()
        wing.mesh = _signature_box_mesh("boss_wing_premium", Vector3(0.20, 0.34, 0.72))
        wing.name = "BossWingL" if side < 0.0 else "BossWingR"
        wing.position = Vector3(side * 0.84, 1.22, 0.04)
        wing.rotation_degrees = Vector3(0.0, side * 20.0, side * -17.0)
        wing.material_override = mat
        add_child(wing)

        var horn := MeshInstance3D.new()
        horn.mesh = _signature_box_mesh("boss_horn_premium", Vector3(0.14, 0.72, 0.25))
        horn.name = "BossHornL" if side < 0.0 else "BossHornR"
        horn.position = Vector3(side * 0.60, 1.92, 0.05)
        horn.rotation_degrees.z = side * -34.0
        horn.material_override = mat
        add_child(horn)

    var core := MeshInstance3D.new()
    core.mesh = _signature_sphere_mesh("boss_core_premium", 0.18, 0.36)
    core.name = "BossCore"
    core.position = Vector3(0.0, 1.40, -0.52)
    core.material_override = _signature_material(Color(1.0, 0.30, 0.04), 5.0)
    add_child(core)
    _add_eye_beacon(color, Vector3(0.0, 1.96, -0.50), 0.125)
    _build_boss_presence()

func _build_boss_presence() -> void:
    boss_aura_root = Node3D.new()
    boss_aura_root.name = "BossThreatAura"
    boss_aura_root.position.y = 0.032
    add_child(boss_aura_root)

    boss_aura_inner_material = StandardMaterial3D.new()
    boss_aura_inner_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    boss_aura_inner_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    boss_aura_inner_material.emission_enabled = true

    boss_aura_outer_material = StandardMaterial3D.new()
    boss_aura_outer_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    boss_aura_outer_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    boss_aura_outer_material.emission_enabled = true

    boss_aura_inner = MeshInstance3D.new()
    boss_aura_inner.name = "BossAuraInner"
    boss_aura_inner.mesh = _telegraph_ring_mesh(1.20, true)
    boss_aura_inner.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    boss_aura_inner.material_override = boss_aura_inner_material
    boss_aura_root.add_child(boss_aura_inner)

    boss_aura_outer = MeshInstance3D.new()
    boss_aura_outer.name = "BossAuraOuter"
    boss_aura_outer.mesh = _telegraph_ring_mesh(1.68, true)
    boss_aura_outer.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    boss_aura_outer.material_override = boss_aura_outer_material
    boss_aura_root.add_child(boss_aura_outer)

    var tick_mesh := _signature_box_mesh("boss_aura_tick", Vector3(0.080, 0.018, 0.36))
    for index in range(4):
        var tick := MeshInstance3D.new()
        tick.name = "BossAuraTick_%d" % index
        tick.mesh = tick_mesh
        var angle := float(index) * PI * 0.5
        tick.position = Vector3(sin(angle) * 1.68, 0.006, cos(angle) * 1.68)
        tick.rotation.y = angle
        tick.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        tick.material_override = boss_aura_inner_material
        boss_aura_root.add_child(tick)

    _refresh_boss_presence_style()

func _refresh_boss_presence_style() -> void:
    if boss_aura_inner_material == null or boss_aura_outer_material == null:
        return
    var phase_color := Color(1.0, 0.52, 0.06)
    if boss_phase == 2:
        phase_color = Color(1.0, 0.24, 0.035)
    elif boss_phase >= 3:
        phase_color = Color(1.0, 0.055, 0.12)

    var inner_color := phase_color
    inner_color.a = 0.78
    boss_aura_inner_material.albedo_color = inner_color
    boss_aura_inner_material.emission = phase_color
    boss_aura_inner_material.emission_energy_multiplier = 2.8 + float(boss_phase) * 0.55

    var outer_color := phase_color.lightened(0.12)
    outer_color.a = 0.32
    boss_aura_outer_material.albedo_color = outer_color
    boss_aura_outer_material.emission = phase_color
    boss_aura_outer_material.emission_energy_multiplier = 1.25 + float(boss_phase) * 0.38

func _update_boss_presence(delta: float) -> void:
    if boss_aura_root == null:
        return
    boss_presence_clock += maxf(delta, 0.0)
    boss_aura_root.rotation.y += delta * (0.42 + float(boss_phase) * 0.10)
    var pulse := 0.5 + 0.5 * sin(boss_presence_clock * (2.8 + float(boss_phase) * 0.35))
    if boss_aura_inner != null:
        var inner_scale := 0.96 + pulse * 0.075
        boss_aura_inner.scale = Vector3.ONE * inner_scale
    if boss_aura_outer != null:
        var outer_scale := 1.035 - pulse * 0.035
        boss_aura_outer.scale = Vector3.ONE * outer_scale
    if boss_aura_inner_material != null:
        boss_aura_inner_material.emission_energy_multiplier = 2.8 + float(boss_phase) * 0.55 + pulse * 0.55
    if boss_aura_outer_material != null:
        boss_aura_outer_material.emission_energy_multiplier = 1.25 + float(boss_phase) * 0.38 + pulse * 0.22

func _melee_attack_animation_range() -> float:
    return _contact_attack_range() + 0.08

func _update_authored_animation(distance: float) -> void:
    if authored_anim == null or dead:
        return
    if kind != "harrier" and distance <= _melee_attack_animation_range() and authored_anim.has_animation("Idle_Attack"):
        _play_authored("Idle_Attack")
        return
    if kind in ["runner", "elite", "boss"] and authored_anim.has_animation("Run_Arms"):
        _play_authored("Run_Arms")
    else:
        _play_authored("Walk")

    # Slow / standoff movement must slow the imported legs too. Keep the boss'
    # heavyweight gait deliberately slower without changing its navigation.
    var planar_speed := Vector2(velocity.x, velocity.z).length()
    var pace := clampf(planar_speed / maxf(move_speed, 0.01), 0.40, 1.16)
    if kind == "boss":
        pace *= 0.82
    elif kind == "brute":
        pace *= 0.90
    authored_anim.speed_scale = move_toward(authored_anim.speed_scale, pace, 0.22)

func _play_authored(name: String) -> void:
    if authored_anim == null or not authored_anim.has_animation(name):
        return
    if name != "Run_Arms" and name != "Walk":
        authored_anim.speed_scale = 1.0
    if current_anim == name:
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

static var _core_mesh: SphereMesh
static var _halo_mesh: SphereMesh
static var _trail_mesh: BoxMesh
static var _core_material: StandardMaterial3D
static var _halo_material: StandardMaterial3D
static var _trail_material: StandardMaterial3D

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

static func _shared_core_mesh() -> SphereMesh:
    if _core_mesh != null:
        return _core_mesh
    _core_mesh = SphereMesh.new()
    _core_mesh.radius = 0.13
    _core_mesh.height = 0.26
    _core_mesh.radial_segments = 10
    _core_mesh.rings = 5
    return _core_mesh

static func _shared_halo_mesh() -> SphereMesh:
    if _halo_mesh != null:
        return _halo_mesh
    _halo_mesh = SphereMesh.new()
    _halo_mesh.radius = 0.24
    _halo_mesh.height = 0.48
    _halo_mesh.radial_segments = 10
    _halo_mesh.rings = 5
    return _halo_mesh

static func _shared_trail_mesh() -> BoxMesh:
    if _trail_mesh != null:
        return _trail_mesh
    _trail_mesh = BoxMesh.new()
    _trail_mesh.size = Vector3(0.07, 0.07, 0.78)
    return _trail_mesh

static func _shared_core_material() -> StandardMaterial3D:
    if _core_material != null:
        return _core_material
    _core_material = StandardMaterial3D.new()
    _core_material.albedo_color = Color(0.08, 0.78, 1.0)
    _core_material.emission_enabled = true
    _core_material.emission = Color(0.04, 0.66, 1.0)
    _core_material.emission_energy_multiplier = 5.2
    _core_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _core_material

static func _shared_halo_material() -> StandardMaterial3D:
    if _halo_material != null:
        return _halo_material
    _halo_material = StandardMaterial3D.new()
    _halo_material.albedo_color = Color(0.08, 0.72, 1.0, 0.18)
    _halo_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _halo_material.emission_enabled = true
    _halo_material.emission = Color(0.04, 0.55, 1.0)
    _halo_material.emission_energy_multiplier = 2.6
    _halo_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _halo_material

static func _shared_trail_material() -> StandardMaterial3D:
    if _trail_material != null:
        return _trail_material
    _trail_material = StandardMaterial3D.new()
    _trail_material.albedo_color = Color(0.05, 0.64, 1.0, 0.42)
    _trail_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _trail_material.emission_enabled = true
    _trail_material.emission = Color(0.04, 0.58, 1.0)
    _trail_material.emission_energy_multiplier = 3.8
    _trail_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _trail_material

func _build_visual() -> void:
    var core := MeshInstance3D.new()
    core.name = "HarrierBoltCore"
    core.mesh = _shared_core_mesh()
    core.material_override = _shared_core_material()
    core.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(core)

    var halo := MeshInstance3D.new()
    halo.name = "HarrierBoltHalo"
    halo.mesh = _shared_halo_mesh()
    halo.material_override = _shared_halo_material()
    halo.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(halo)

    var trail := MeshInstance3D.new()
    trail.name = "HarrierBoltTrail"
    trail.mesh = _shared_trail_mesh()
    trail.position = Vector3(0.0, 0.0, 0.42)
    trail.material_override = _shared_trail_material()
    trail.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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
    "sfx_volume": 0.90,
    "music_volume": 0.62,
    "haptics_enabled": true,
    "reduced_flashes": false,
    "camera_shake_enabled": true,
    "hit_stop_enabled": true
}

static func save(path: String, settings: Dictionary) -> Error:
    var config := ConfigFile.new()
    config.set_value("audio", "master_volume", clampf(float(settings.get("master_volume", DEFAULTS["master_volume"])), 0.0, 1.0))
    config.set_value("audio", "sfx_volume", clampf(float(settings.get("sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0))
    config.set_value("audio", "music_volume", clampf(float(settings.get("music_volume", DEFAULTS["music_volume"])), 0.0, 1.0))
    config.set_value("comfort", "haptics_enabled", bool(settings.get("haptics_enabled", DEFAULTS["haptics_enabled"])))
    config.set_value("comfort", "reduced_flashes", bool(settings.get("reduced_flashes", DEFAULTS["reduced_flashes"])))
    config.set_value("comfort", "camera_shake_enabled", bool(settings.get("camera_shake_enabled", DEFAULTS["camera_shake_enabled"])))
    config.set_value("comfort", "hit_stop_enabled", bool(settings.get("hit_stop_enabled", DEFAULTS["hit_stop_enabled"])))
    return config.save(path)

static func load_settings(path: String) -> Dictionary:
    var result := DEFAULTS.duplicate(true)
    var config := ConfigFile.new()
    if config.load(path) != OK:
        return result
    result["master_volume"] = clampf(float(config.get_value("audio", "master_volume", DEFAULTS["master_volume"])), 0.0, 1.0)
    result["sfx_volume"] = clampf(float(config.get_value("audio", "sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0)
    result["music_volume"] = clampf(float(config.get_value("audio", "music_volume", DEFAULTS["music_volume"])), 0.0, 1.0)
    result["haptics_enabled"] = bool(config.get_value("comfort", "haptics_enabled", DEFAULTS["haptics_enabled"]))
    result["reduced_flashes"] = bool(config.get_value("comfort", "reduced_flashes", DEFAULTS["reduced_flashes"]))
    result["camera_shake_enabled"] = bool(config.get_value("comfort", "camera_shake_enabled", DEFAULTS["camera_shake_enabled"]))
    result["hit_stop_enabled"] = bool(config.get_value("comfort", "hit_stop_enabled", DEFAULTS["hit_stop_enabled"]))
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
signal music_volume_changed(value: float)
signal haptics_changed(enabled: bool)
signal reduced_flashes_changed(enabled: bool)
signal camera_shake_changed(enabled: bool)
signal hit_stop_changed(enabled: bool)

var hp_bar: ProgressBar
var health_bar: ProgressBar
var xp_bar: ProgressBar
var xp_pulse_tween: Tween
var status_label: Label
var health_value_label: Label
var wave_label: Label
var wave_panel: PanelContainer
var wave_transition_tween: Tween
var current_wave_text := ""
var upgrade_panel: PanelContainer
var upgrade_buttons: Array[Button] = []
var upgrade_cards: Array[VBoxContainer] = []
var upgrade_card_panels: Array[PanelContainer] = []
var upgrade_family_labels: Array[Label] = []
var upgrade_title_labels: Array[Label] = []
var upgrade_detail_labels: Array[Label] = []
var upgrade_entry_tween: Tween
var boss_panel: PanelContainer
var boss_name_label: Label
var boss_hp_bar: ProgressBar
var boss_phase_label: Label
var boss_hp_fill_style: StyleBoxFlat
var boss_hp_max := 1.0
var boss_phase_tween: Tween
var game_over_panel: PanelContainer
var game_over_scrim: ColorRect
var game_over_summary: Label
var low_health_panel: PanelContainer
var low_health_label: Label
var threat_panel: PanelContainer
var threat_label: Label
var pause_panel: PanelContainer
var pause_button: Button
var master_volume: HSlider
var sfx_volume: HSlider
var music_volume: HSlider
var haptics_toggle: CheckButton
var reduced_flashes_toggle: CheckButton
var camera_shake_toggle: CheckButton
var hit_stop_toggle: CheckButton
var reduced_flashes := false
var impact_flash: ColorRect
var impact_flash_tween: Tween
var damage_vignette: ColorRect
var damage_vignette_tween: Tween
var touch_stick_root: Control
var touch_stick_knob: Control
var onboarding_panel: PanelContainer
var onboarding_label: Label

func _ready() -> void:
    process_mode = Node.PROCESS_MODE_ALWAYS
    _build()

func pulse_damage_screen() -> void:
    if damage_vignette == null:
        return
    if damage_vignette_tween != null and damage_vignette_tween.is_valid():
        damage_vignette_tween.kill()
    damage_vignette.visible = true
    damage_vignette.modulate.a = 0.32 if reduced_flashes else 1.0
    damage_vignette_tween = damage_vignette.create_tween()
    damage_vignette_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    damage_vignette_tween.tween_property(damage_vignette, "modulate:a", 0.0, 0.12 if reduced_flashes else 0.26).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    damage_vignette_tween.tween_callback(func() -> void:
        if damage_vignette != null:
            damage_vignette.visible = false
            damage_vignette.modulate.a = 1.0
    )

func set_health(value: float, maximum: float) -> void:
    hp_bar.max_value = max(1.0, maximum)
    hp_bar.value = value
    if health_value_label != null:
        health_value_label.text = "HP %d / %d" % [int(round(value)), int(round(maximum))]
    var ratio: float = clampf(value / max(1.0, maximum), 0.0, 1.0)
    low_health_panel.visible = value > 0.0 and ratio <= 0.30
    if low_health_panel.visible:
        low_health_label.text = "CRITICAL INTEGRITY  •  %d%%" % int(round(ratio * 100.0))

func set_progress(xp: int, next_xp: int, level: int, kills: int, elapsed: float, threats := 0) -> void:
    xp_bar.max_value = max(1, next_xp)
    xp_bar.value = xp
    status_label.text = "LV %d   KILLS %d   THREATS %d   %02d:%02d" % [level, kills, threats, int(elapsed) / 60, int(elapsed) % 60]

func pulse_xp_collection(amount: int) -> void:
    if xp_bar == null:
        return
    if xp_pulse_tween != null and xp_pulse_tween.is_valid():
        xp_pulse_tween.kill()
    var strength := clampf(float(amount) / 8.0, 0.0, 1.0)
    xp_bar.modulate = Color(
        lerpf(0.82, 1.0, strength),
        1.0,
        lerpf(0.94, 0.72, strength),
        1.0
    )
    xp_bar.scale = Vector2(1.0, 1.35 + strength * 0.18)
    xp_pulse_tween = xp_bar.create_tween()
    xp_pulse_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    xp_pulse_tween.set_parallel(true)
    xp_pulse_tween.tween_property(xp_bar, "scale", Vector2.ONE, 0.16).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    xp_pulse_tween.tween_property(xp_bar, "modulate", Color.WHITE, 0.18).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

func set_wave(text: String) -> void:
    if wave_label == null:
        return
    if text == current_wave_text:
        return
    current_wave_text = text
    wave_label.text = text
    _pulse_wave_transition(text)

func _pulse_wave_transition(text: String) -> void:
    if wave_panel == null or wave_label == null:
        return
    if wave_transition_tween != null and wave_transition_tween.is_valid():
        wave_transition_tween.kill()
    var urgent := text.contains("BOSS") or text.contains("OVERRUN")
    var accent := Color(1.0, 0.34, 0.12) if urgent else Color(0.22, 0.76, 0.94)
    wave_panel.pivot_offset = wave_panel.size * 0.5
    wave_panel.scale = Vector2(0.94, 0.94)
    wave_label.modulate = accent.lightened(0.16)
    wave_transition_tween = wave_panel.create_tween()
    wave_transition_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    wave_transition_tween.set_parallel(true)
    wave_transition_tween.tween_property(wave_panel, "scale", Vector2.ONE, 0.24).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    wave_transition_tween.tween_property(wave_label, "modulate", Color(0.90, 0.95, 0.98), 0.36).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

func show_boss(name: String, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_name_label.text = name
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = boss_hp_max
    boss_phase_label.text = "THREAT LOCK"
    boss_phase_label.modulate = Color(1.0, 0.62, 0.22)
    boss_phase_label.scale = Vector2.ONE
    boss_panel.visible = true

func set_boss_health(value: float, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = clamp(value, 0.0, boss_hp_max)
    var ratio := boss_hp_bar.value / boss_hp_max
    boss_phase_label.text = "PHASE III // EXECUTE" if ratio <= 0.30 else ("PHASE II // ENRAGED" if ratio <= 0.65 else "PHASE I // HUNT")
    boss_phase_label.modulate = Color(1.0, 0.18, 0.18) if ratio <= 0.30 else (Color(1.0, 0.42, 0.18) if ratio <= 0.65 else Color(1.0, 0.68, 0.28))
    if boss_hp_fill_style != null:
        if ratio <= 0.30:
            boss_hp_fill_style.bg_color = Color(0.96, 0.16, 0.055, 0.98)
            boss_hp_fill_style.border_color = Color(1.0, 0.44, 0.16, 0.90)
        elif ratio <= 0.65:
            boss_hp_fill_style.bg_color = Color(0.98, 0.34, 0.075, 0.98)
            boss_hp_fill_style.border_color = Color(1.0, 0.62, 0.18, 0.88)
        else:
            boss_hp_fill_style.bg_color = Color(0.96, 0.58, 0.12, 0.98)
            boss_hp_fill_style.border_color = Color(1.0, 0.80, 0.30, 0.86)
    if boss_hp_bar.value <= 0.0:
        boss_panel.visible = false

func pulse_boss_phase(phase: int) -> void:
    if boss_phase_label == null:
        return
    if boss_phase_tween != null and boss_phase_tween.is_valid():
        boss_phase_tween.kill()
    var phase_color := Color(1.0, 0.44, 0.18) if phase == 2 else Color(1.0, 0.12, 0.15)
    boss_phase_label.modulate = phase_color.lightened(0.18)
    boss_phase_label.scale = Vector2(1.10, 1.10)
    boss_phase_tween = boss_phase_label.create_tween()
    boss_phase_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    boss_phase_tween.set_parallel(true)
    boss_phase_tween.tween_property(boss_phase_label, "scale", Vector2.ONE, 0.34).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    boss_phase_tween.tween_property(boss_phase_label, "modulate", phase_color, 0.34).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

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

func show_touch_stick(origin: Vector2) -> void:
    if touch_stick_root == null or touch_stick_knob == null:
        return
    var viewport_size := get_viewport().get_visible_rect().size
    var half_size := touch_stick_root.size * 0.5
    var safe_center := Vector2(
        clampf(origin.x, half_size.x + 8.0, maxf(half_size.x + 8.0, viewport_size.x - half_size.x - 8.0)),
        clampf(origin.y, half_size.y + 8.0, maxf(half_size.y + 8.0, viewport_size.y - half_size.y - 8.0))
    )
    touch_stick_root.position = safe_center - half_size
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5
    touch_stick_root.visible = true

func update_touch_stick(origin: Vector2, input_vector: Vector2) -> void:
    if touch_stick_root == null or touch_stick_knob == null:
        return
    if not touch_stick_root.visible:
        show_touch_stick(origin)
    var travel_radius := (touch_stick_root.size.x - touch_stick_knob.size.x) * 0.5 - 4.0
    var displacement := input_vector.limit_length(1.0) * maxf(travel_radius, 0.0)
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5 + displacement

func hide_touch_stick() -> void:
    if touch_stick_root != null:
        touch_stick_root.visible = false

func show_onboarding_hint() -> void:
    if onboarding_panel != null:
        onboarding_panel.visible = true

func hide_onboarding_hint() -> void:
    if onboarding_panel != null:
        onboarding_panel.visible = false

func set_reduced_flashes(enabled: bool) -> void:
    reduced_flashes = enabled
    if reduced_flashes_toggle != null:
        reduced_flashes_toggle.set_pressed_no_signal(enabled)

func show_upgrade(items: Array) -> void:
    if upgrade_entry_tween != null and upgrade_entry_tween.is_valid():
        upgrade_entry_tween.kill()
    for i in range(upgrade_buttons.size()):
        var item: Dictionary = items[i] if i < items.size() else {}
        var id := str(item.get("id", "damage"))
        upgrade_family_labels[i].text = str(item.get("family", "UPGRADE"))
        upgrade_title_labels[i].text = str(item.get("title", "UPGRADE"))
        upgrade_detail_labels[i].text = str(item.get("detail", ""))
        upgrade_buttons[i].text = _upgrade_glyph(id)
        _style_upgrade_card(i, id)

    upgrade_panel.visible = true
    upgrade_panel.pivot_offset = upgrade_panel.size * 0.5
    upgrade_panel.scale = Vector2(0.965, 0.965)
    upgrade_panel.modulate = Color(1.0, 1.0, 1.0, 0.0)

    for card in upgrade_card_panels:
        card.pivot_offset = card.size * 0.5
        card.scale = Vector2(0.94, 0.94)
        card.modulate = Color(1.0, 1.0, 1.0, 0.0)

    upgrade_entry_tween = upgrade_panel.create_tween()
    upgrade_entry_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    upgrade_entry_tween.set_parallel(true)
    upgrade_entry_tween.tween_property(upgrade_panel, "scale", Vector2.ONE, 0.16).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
    upgrade_entry_tween.tween_property(upgrade_panel, "modulate:a", 1.0, 0.10).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    for i in range(upgrade_card_panels.size()):
        var card := upgrade_card_panels[i]
        var delay := 0.035 + float(i) * 0.040
        upgrade_entry_tween.tween_property(card, "scale", Vector2.ONE, 0.16).set_delay(delay).set_trans(Tween.TRANS_BACK).set_ease(Tween.EASE_OUT)
        upgrade_entry_tween.tween_property(card, "modulate:a", 1.0, 0.11).set_delay(delay).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)

func hide_upgrade() -> void:
    if upgrade_entry_tween != null and upgrade_entry_tween.is_valid():
        upgrade_entry_tween.kill()
    upgrade_panel.visible = false
    upgrade_panel.scale = Vector2.ONE
    upgrade_panel.modulate = Color.WHITE

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
    if reduced_flashes:
        tint.a *= 0.32
    impact_flash.color = tint
    impact_flash.visible = true
    impact_flash_tween = create_tween()
    impact_flash_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    var flash_duration := (0.07 if boss else 0.055) if reduced_flashes else (0.16 if boss else 0.11)
    impact_flash_tween.tween_property(impact_flash, "color:a", 0.0, flash_duration)
    impact_flash_tween.tween_callback(func() -> void:
        if impact_flash != null:
            impact_flash.visible = false
    )

func show_game_over(kills: int, level: int, elapsed: float) -> void:
    wave_label.text = "RUN TERMINATED"
    upgrade_panel.visible = false
    boss_panel.visible = false
    pause_panel.visible = false
    impact_flash.visible = false
    damage_vignette.visible = false
    hide_onboarding_hint()
    var minutes := int(elapsed) / 60
    var seconds := int(elapsed) % 60
    game_over_summary.text = "LEVEL %d   •   KILLS %d   •   %02d:%02d" % [level, kills, minutes, seconds]
    low_health_panel.visible = false
    threat_panel.visible = false
    if game_over_scrim != null:
        game_over_scrim.visible = true
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

    touch_stick_root = Control.new()
    touch_stick_root.name = "TouchStick"
    touch_stick_root.size = Vector2(112.0, 112.0)
    touch_stick_root.mouse_filter = Control.MOUSE_FILTER_IGNORE
    touch_stick_root.visible = false
    root.add_child(touch_stick_root)

    var stick_base := Panel.new()
    stick_base.name = "TouchStickBase"
    stick_base.position = Vector2.ZERO
    stick_base.size = touch_stick_root.size
    stick_base.mouse_filter = Control.MOUSE_FILTER_IGNORE
    var stick_base_style := StyleBoxFlat.new()
    stick_base_style.bg_color = Color(0.015, 0.035, 0.045, 0.34)
    stick_base_style.border_color = Color(0.18, 0.78, 1.0, 0.52)
    stick_base_style.set_border_width_all(2)
    stick_base_style.corner_radius_top_left = 56
    stick_base_style.corner_radius_top_right = 56
    stick_base_style.corner_radius_bottom_left = 56
    stick_base_style.corner_radius_bottom_right = 56
    stick_base.add_theme_stylebox_override("panel", stick_base_style)
    touch_stick_root.add_child(stick_base)

    touch_stick_knob = Panel.new()
    touch_stick_knob.name = "TouchStickKnob"
    touch_stick_knob.size = Vector2(42.0, 42.0)
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5
    touch_stick_knob.mouse_filter = Control.MOUSE_FILTER_IGNORE
    var stick_knob_style := StyleBoxFlat.new()
    stick_knob_style.bg_color = Color(0.10, 0.62, 0.88, 0.74)
    stick_knob_style.border_color = Color(0.54, 0.92, 1.0, 0.88)
    stick_knob_style.set_border_width_all(2)
    stick_knob_style.corner_radius_top_left = 21
    stick_knob_style.corner_radius_top_right = 21
    stick_knob_style.corner_radius_bottom_left = 21
    stick_knob_style.corner_radius_bottom_right = 21
    touch_stick_knob.add_theme_stylebox_override("panel", stick_knob_style)
    touch_stick_root.add_child(touch_stick_knob)

    onboarding_panel = PanelContainer.new()
    onboarding_panel.name = "OnboardingHint"
    onboarding_panel.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
    onboarding_panel.position = Vector2(-285.0, -166.0)
    onboarding_panel.size = Vector2(570.0, 54.0)
    onboarding_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    onboarding_panel.visible = false
    root.add_child(onboarding_panel)

    var onboarding_style := StyleBoxFlat.new()
    onboarding_style.bg_color = Color(0.008, 0.022, 0.030, 0.92)
    onboarding_style.border_color = Color(0.12, 0.70, 0.90, 0.74)
    onboarding_style.set_border_width_all(1)
    onboarding_style.border_width_left = 4
    onboarding_style.corner_radius_top_left = 7
    onboarding_style.corner_radius_top_right = 7
    onboarding_style.corner_radius_bottom_left = 7
    onboarding_style.corner_radius_bottom_right = 7
    onboarding_style.content_margin_left = 16.0
    onboarding_style.content_margin_right = 16.0
    onboarding_panel.add_theme_stylebox_override("panel", onboarding_style)

    onboarding_label = Label.new()
    onboarding_label.name = "OnboardingLabel"
    onboarding_label.text = "DRAG LEFT SIDE TO MOVE   •   AUTO-FIRE ONLINE"
    onboarding_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    onboarding_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    onboarding_label.add_theme_font_size_override("font_size", 16)
    onboarding_label.add_theme_color_override("font_color", Color(0.78, 0.93, 0.98))
    onboarding_label.mouse_filter = Control.MOUSE_FILTER_IGNORE
    onboarding_panel.add_child(onboarding_label)

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

    health_value_label = Label.new()
    health_value_label.name = "HealthValueLabel"
    health_value_label.text = "HP 100 / 100"
    health_value_label.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    health_value_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    health_value_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    health_value_label.add_theme_font_size_override("font_size", 10)
    health_value_label.add_theme_color_override("font_color", Color(0.94, 0.98, 1.0))
    health_value_label.add_theme_color_override("font_shadow_color", Color(0.0, 0.0, 0.0, 0.82))
    health_value_label.add_theme_constant_override("shadow_offset_x", 1)
    health_value_label.add_theme_constant_override("shadow_offset_y", 1)
    health_value_label.mouse_filter = Control.MOUSE_FILTER_IGNORE
    hp_bar.add_child(health_value_label)

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

    wave_panel = PanelContainer.new()
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
    pause_panel.position = Vector2(-380, -235)
    pause_panel.size = Vector2(760, 470)
    pause_panel.visible = false
    add_child(pause_panel)

    var pause_box := VBoxContainer.new()
    pause_box.alignment = BoxContainer.ALIGNMENT_CENTER
    pause_box.add_theme_constant_override("separation", 14)
    pause_panel.add_child(pause_box)

    var pause_title := Label.new()
    pause_title.text = "SYSTEM PAUSED"
    pause_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    pause_title.add_theme_font_size_override("font_size", 30)
    pause_title.modulate = Color(0.72, 0.92, 1.0)
    pause_box.add_child(pause_title)

    var settings_columns := HBoxContainer.new()
    settings_columns.name = "SettingsColumns"
    settings_columns.alignment = BoxContainer.ALIGNMENT_CENTER
    settings_columns.add_theme_constant_override("separation", 34)
    pause_box.add_child(settings_columns)

    var audio_column := VBoxContainer.new()
    audio_column.name = "AudioColumn"
    audio_column.custom_minimum_size = Vector2(310, 0)
    audio_column.add_theme_constant_override("separation", 8)
    settings_columns.add_child(audio_column)

    var master_label := Label.new()
    master_label.text = "MASTER VOLUME"
    master_label.add_theme_font_size_override("font_size", 16)
    audio_column.add_child(master_label)
    master_volume = HSlider.new()
    master_volume.name = "MasterVolume"
    master_volume.min_value = 0.0
    master_volume.max_value = 1.0
    master_volume.step = 0.05
    master_volume.value = 0.85
    master_volume.custom_minimum_size = Vector2(300, 48)
    master_volume.value_changed.connect(func(value: float) -> void: master_volume_changed.emit(value))
    audio_column.add_child(master_volume)

    var sfx_label := Label.new()
    sfx_label.text = "SFX VOLUME"
    sfx_label.add_theme_font_size_override("font_size", 16)
    audio_column.add_child(sfx_label)
    sfx_volume = HSlider.new()
    sfx_volume.name = "SfxVolume"
    sfx_volume.min_value = 0.0
    sfx_volume.max_value = 1.0
    sfx_volume.step = 0.05
    sfx_volume.value = 0.90
    sfx_volume.custom_minimum_size = Vector2(300, 48)
    sfx_volume.value_changed.connect(func(value: float) -> void: sfx_volume_changed.emit(value))
    audio_column.add_child(sfx_volume)

    var music_label := Label.new()
    music_label.text = "MUSIC VOLUME"
    music_label.add_theme_font_size_override("font_size", 16)
    audio_column.add_child(music_label)
    music_volume = HSlider.new()
    music_volume.name = "MusicVolume"
    music_volume.min_value = 0.0
    music_volume.max_value = 1.0
    music_volume.step = 0.05
    music_volume.value = 0.62
    music_volume.custom_minimum_size = Vector2(300, 48)
    music_volume.value_changed.connect(func(value: float) -> void: music_volume_changed.emit(value))
    audio_column.add_child(music_volume)

    var comfort_column := VBoxContainer.new()
    comfort_column.name = "ComfortColumn"
    comfort_column.custom_minimum_size = Vector2(310, 0)
    comfort_column.add_theme_constant_override("separation", 12)
    settings_columns.add_child(comfort_column)

    var comfort_label := Label.new()
    comfort_label.text = "COMFORT"
    comfort_label.add_theme_font_size_override("font_size", 16)
    comfort_label.modulate = Color(0.68, 0.86, 0.94)
    comfort_column.add_child(comfort_label)

    haptics_toggle = CheckButton.new()
    haptics_toggle.name = "HapticsToggle"
    haptics_toggle.text = "HAPTICS"
    haptics_toggle.button_pressed = true
    haptics_toggle.custom_minimum_size = Vector2(300, 48)
    haptics_toggle.add_theme_font_size_override("font_size", 16)
    haptics_toggle.toggled.connect(func(enabled: bool) -> void: haptics_changed.emit(enabled))
    comfort_column.add_child(haptics_toggle)

    reduced_flashes_toggle = CheckButton.new()
    reduced_flashes_toggle.name = "ReducedFlashesToggle"
    reduced_flashes_toggle.text = "REDUCED FLASHES"
    reduced_flashes_toggle.button_pressed = false
    reduced_flashes_toggle.custom_minimum_size = Vector2(300, 48)
    reduced_flashes_toggle.add_theme_font_size_override("font_size", 16)
    reduced_flashes_toggle.toggled.connect(func(enabled: bool) -> void:
        reduced_flashes = enabled
        reduced_flashes_changed.emit(enabled)
    )
    comfort_column.add_child(reduced_flashes_toggle)

    camera_shake_toggle = CheckButton.new()
    camera_shake_toggle.name = "CameraShakeToggle"
    camera_shake_toggle.text = "CAMERA SHAKE"
    camera_shake_toggle.button_pressed = true
    camera_shake_toggle.custom_minimum_size = Vector2(300, 48)
    camera_shake_toggle.add_theme_font_size_override("font_size", 16)
    camera_shake_toggle.toggled.connect(func(enabled: bool) -> void: camera_shake_changed.emit(enabled))
    comfort_column.add_child(camera_shake_toggle)

    hit_stop_toggle = CheckButton.new()
    hit_stop_toggle.name = "HitStopToggle"
    hit_stop_toggle.text = "HIT STOP"
    hit_stop_toggle.button_pressed = true
    hit_stop_toggle.custom_minimum_size = Vector2(300, 48)
    hit_stop_toggle.add_theme_font_size_override("font_size", 16)
    hit_stop_toggle.toggled.connect(func(enabled: bool) -> void: hit_stop_changed.emit(enabled))
    comfort_column.add_child(hit_stop_toggle)

    var resume_button := Button.new()
    resume_button.name = "ResumeButton"
    resume_button.text = "RESUME"
    resume_button.custom_minimum_size = Vector2(300, 58)
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

    game_over_scrim = ColorRect.new()
    game_over_scrim.name = "GameOverScrim"
    game_over_scrim.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    game_over_scrim.color = Color(0.002, 0.006, 0.010, 0.62)
    game_over_scrim.mouse_filter = Control.MOUSE_FILTER_IGNORE
    game_over_scrim.visible = false
    root.add_child(game_over_scrim)

    game_over_panel = PanelContainer.new()
    game_over_panel.name = "GameOverPanel"
    game_over_panel.set_anchors_preset(Control.PRESET_CENTER)
    game_over_panel.position = Vector2(-310, -148)
    game_over_panel.size = Vector2(620, 296)
    game_over_panel.visible = false
    root.add_child(game_over_panel)

    var game_over_box := VBoxContainer.new()
    game_over_box.alignment = BoxContainer.ALIGNMENT_CENTER
    game_over_box.add_theme_constant_override("separation", 12)
    game_over_panel.add_child(game_over_box)

    var game_over_kicker := Label.new()
    game_over_kicker.name = "GameOverKicker"
    game_over_kicker.text = "QUARANTINE LINK // OFFLINE"
    game_over_kicker.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_kicker.add_theme_font_size_override("font_size", 13)
    game_over_kicker.modulate = Color(0.42, 0.78, 0.90)
    game_over_box.add_child(game_over_kicker)

    var game_over_title := Label.new()
    game_over_title.name = "GameOverTitle"
    game_over_title.text = "SIGNAL LOST"
    game_over_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_title.add_theme_font_size_override("font_size", 38)
    game_over_title.add_theme_color_override("font_color", Color(1.0, 0.31, 0.16))
    game_over_title.add_theme_color_override("font_outline_color", Color(0.10, 0.015, 0.008, 0.95))
    game_over_title.add_theme_constant_override("outline_size", 3)
    game_over_box.add_child(game_over_title)

    var game_over_break := ColorRect.new()
    game_over_break.name = "GameOverBreak"
    game_over_break.custom_minimum_size = Vector2(420, 3)
    game_over_break.color = Color(1.0, 0.22, 0.075, 0.92)
    game_over_break.mouse_filter = Control.MOUSE_FILTER_IGNORE
    game_over_box.add_child(game_over_break)

    game_over_summary = Label.new()
    game_over_summary.name = "GameOverSummary"
    game_over_summary.text = "LEVEL 1   •   KILLS 0   •   00:00"
    game_over_summary.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_summary.add_theme_font_size_override("font_size", 18)
    game_over_summary.modulate = Color(0.82, 0.90, 0.94)
    game_over_box.add_child(game_over_summary)

    var game_over_note := Label.new()
    game_over_note.name = "GameOverNote"
    game_over_note.text = "COMBAT LOG SEALED // REDEPLOY WHEN READY"
    game_over_note.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_note.add_theme_font_size_override("font_size", 12)
    game_over_note.modulate = Color(0.44, 0.55, 0.62)
    game_over_box.add_child(game_over_note)

    var restart_button := Button.new()
    restart_button.name = "RestartButton"
    restart_button.text = "REDEPLOY"
    restart_button.custom_minimum_size = Vector2(300, 60)
    restart_button.add_theme_font_size_override("font_size", 21)
    var restart_normal := StyleBoxFlat.new()
    restart_normal.bg_color = Color(0.020, 0.085, 0.115, 0.98)
    restart_normal.border_color = Color(0.10, 0.72, 0.92, 0.90)
    restart_normal.set_border_width_all(2)
    restart_normal.corner_radius_top_left = 6
    restart_normal.corner_radius_top_right = 6
    restart_normal.corner_radius_bottom_left = 6
    restart_normal.corner_radius_bottom_right = 6
    var restart_hover := restart_normal.duplicate()
    restart_hover.bg_color = Color(0.035, 0.16, 0.20, 1.0)
    restart_hover.border_color = Color(0.28, 0.92, 1.0, 1.0)
    restart_button.add_theme_stylebox_override("normal", restart_normal)
    restart_button.add_theme_stylebox_override("hover", restart_hover)
    restart_button.add_theme_stylebox_override("pressed", restart_hover)
    restart_button.add_theme_color_override("font_color", Color(0.82, 0.96, 1.0))
    restart_button.pressed.connect(func() -> void: restart_requested.emit())
    game_over_box.add_child(restart_button)

    var game_over_style := StyleBoxFlat.new()
    game_over_style.bg_color = Color(0.024, 0.038, 0.050, 0.985)
    game_over_style.border_color = Color(1.0, 0.28, 0.10, 0.90)
    game_over_style.shadow_color = Color(0.0, 0.0, 0.0, 0.68)
    game_over_style.shadow_size = 12
    game_over_style.set_border_width_all(2)
    game_over_style.corner_radius_top_left = 12
    game_over_style.corner_radius_top_right = 12
    game_over_style.corner_radius_bottom_left = 12
    game_over_style.corner_radius_bottom_right = 12
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
    boss_hp_bar.name = "BossHealthBar"
    boss_hp_bar.custom_minimum_size = Vector2(620, 20)
    boss_hp_bar.show_percentage = false
    var boss_hp_background := StyleBoxFlat.new()
    boss_hp_background.bg_color = Color(0.012, 0.017, 0.022, 0.98)
    boss_hp_background.border_color = Color(0.24, 0.10, 0.06, 0.88)
    boss_hp_background.set_border_width_all(2)
    boss_hp_background.corner_radius_top_left = 4
    boss_hp_background.corner_radius_top_right = 4
    boss_hp_background.corner_radius_bottom_left = 4
    boss_hp_background.corner_radius_bottom_right = 4
    boss_hp_fill_style = StyleBoxFlat.new()
    boss_hp_fill_style.bg_color = Color(0.96, 0.58, 0.12, 0.98)
    boss_hp_fill_style.border_color = Color(1.0, 0.80, 0.30, 0.86)
    boss_hp_fill_style.set_border_width_all(1)
    boss_hp_fill_style.corner_radius_top_left = 3
    boss_hp_fill_style.corner_radius_top_right = 3
    boss_hp_fill_style.corner_radius_bottom_left = 3
    boss_hp_fill_style.corner_radius_bottom_right = 3
    boss_hp_bar.add_theme_stylebox_override("background", boss_hp_background)
    boss_hp_bar.add_theme_stylebox_override("fill", boss_hp_fill_style)
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
    upgrade_panel.position = Vector2(-535, -190)
    upgrade_panel.size = Vector2(1070, 380)
    upgrade_panel.visible = false
    root.add_child(upgrade_panel)

    var upgrade_panel_style := StyleBoxFlat.new()
    upgrade_panel_style.bg_color = Color(0.008, 0.016, 0.024, 0.955)
    upgrade_panel_style.border_color = Color(0.12, 0.52, 0.72, 0.62)
    upgrade_panel_style.set_border_width_all(2)
    upgrade_panel_style.corner_radius_top_left = 12
    upgrade_panel_style.corner_radius_top_right = 12
    upgrade_panel_style.corner_radius_bottom_left = 12
    upgrade_panel_style.corner_radius_bottom_right = 12
    upgrade_panel_style.shadow_color = Color(0.0, 0.0, 0.0, 0.72)
    upgrade_panel_style.shadow_size = 14
    upgrade_panel_style.content_margin_left = 24.0
    upgrade_panel_style.content_margin_right = 24.0
    upgrade_panel_style.content_margin_top = 18.0
    upgrade_panel_style.content_margin_bottom = 20.0
    upgrade_panel.add_theme_stylebox_override("panel", upgrade_panel_style)

    var box := VBoxContainer.new()
    box.add_theme_constant_override("separation", 12)
    upgrade_panel.add_child(box)

    var title := Label.new()
    title.text = "SELECT COMBAT UPGRADE"
    title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    title.add_theme_font_size_override("font_size", 30)
    title.add_theme_color_override("font_color", Color(0.91, 0.96, 0.99))
    box.add_child(title)

    var subtitle := Label.new()
    subtitle.name = "UpgradeSubtitle"
    subtitle.text = "CHOOSE ONE // ADAPT THE BUILD"
    subtitle.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    subtitle.add_theme_font_size_override("font_size", 12)
    subtitle.add_theme_color_override("font_color", Color(0.36, 0.72, 0.86))
    box.add_child(subtitle)

    var row := HBoxContainer.new()
    row.name = "UpgradeCardRow"
    row.alignment = BoxContainer.ALIGNMENT_CENTER
    row.add_theme_constant_override("separation", 16)
    box.add_child(row)

    for i in range(3):
        var card_panel := PanelContainer.new()
        card_panel.name = "UpgradeCard_%d" % i
        card_panel.custom_minimum_size = Vector2(316, 245)
        row.add_child(card_panel)
        upgrade_card_panels.append(card_panel)

        var card := VBoxContainer.new()
        card.custom_minimum_size = Vector2(284, 216)
        card.add_theme_constant_override("separation", 6)
        card_panel.add_child(card)
        upgrade_cards.append(card)

        var family := Label.new()
        family.text = "UPGRADE"
        family.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        family.add_theme_font_size_override("font_size", 12)
        card.add_child(family)
        upgrade_family_labels.append(family)

        var button := Button.new()
        button.name = "UpgradeGlyphButton_%d" % i
        button.custom_minimum_size = Vector2(284, 90)
        button.text = "◆"
        button.add_theme_font_size_override("font_size", 42)
        button.focus_mode = Control.FOCUS_ALL
        button.pressed.connect(_on_upgrade_pressed.bind(i))
        card.add_child(button)
        upgrade_buttons.append(button)

        var upgrade_title := Label.new()
        upgrade_title.text = "UPGRADE"
        upgrade_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        upgrade_title.add_theme_font_size_override("font_size", 21)
        upgrade_title.add_theme_color_override("font_color", Color(0.95, 0.97, 1.0))
        card.add_child(upgrade_title)
        upgrade_title_labels.append(upgrade_title)

        var detail := Label.new()
        detail.text = ""
        detail.custom_minimum_size = Vector2(280, 42)
        detail.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        detail.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
        detail.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
        detail.add_theme_font_size_override("font_size", 15)
        detail.add_theme_color_override("font_color", Color(0.72, 0.82, 0.89))
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

    var card_style := StyleBoxFlat.new()
    card_style.bg_color = Color(
        0.016 + accent.r * 0.018,
        0.024 + accent.g * 0.018,
        0.032 + accent.b * 0.018,
        0.985
    )
    card_style.border_color = Color(accent.r, accent.g, accent.b, 0.70)
    card_style.set_border_width_all(2)
    card_style.border_width_top = 4
    card_style.corner_radius_top_left = 10
    card_style.corner_radius_top_right = 10
    card_style.corner_radius_bottom_left = 10
    card_style.corner_radius_bottom_right = 10
    card_style.content_margin_left = 14.0
    card_style.content_margin_right = 14.0
    card_style.content_margin_top = 12.0
    card_style.content_margin_bottom = 12.0
    upgrade_card_panels[index].add_theme_stylebox_override("panel", card_style)

    var normal := StyleBoxFlat.new()
    normal.bg_color = Color(
        0.026 + accent.r * 0.026,
        0.036 + accent.g * 0.026,
        0.046 + accent.b * 0.026,
        0.98
    )
    normal.border_color = Color(accent.r, accent.g, accent.b, 0.46)
    normal.set_border_width_all(1)
    normal.border_width_top = 2
    normal.corner_radius_top_left = 8
    normal.corner_radius_top_right = 8
    normal.corner_radius_bottom_left = 8
    normal.corner_radius_bottom_right = 8

    var hover := normal.duplicate() as StyleBoxFlat
    hover.bg_color = Color(
        0.040 + accent.r * 0.18,
        0.048 + accent.g * 0.18,
        0.058 + accent.b * 0.18,
        0.99
    )
    hover.border_color = Color(accent.r, accent.g, accent.b, 0.96)
    hover.set_border_width_all(2)

    var pressed := hover.duplicate() as StyleBoxFlat
    pressed.bg_color = Color(accent.r * 0.22, accent.g * 0.22, accent.b * 0.22, 1.0)

    upgrade_buttons[index].add_theme_stylebox_override("normal", normal)
    upgrade_buttons[index].add_theme_stylebox_override("hover", hover)
    upgrade_buttons[index].add_theme_stylebox_override("focus", hover)
    upgrade_buttons[index].add_theme_stylebox_override("pressed", pressed)
    upgrade_buttons[index].add_theme_color_override("font_color", accent)
    upgrade_buttons[index].add_theme_color_override("font_hover_color", accent.lightened(0.14))
    upgrade_buttons[index].add_theme_color_override("font_pressed_color", Color.WHITE)

func _on_upgrade_pressed(index: int) -> void:
    upgrade_chosen.emit(index)
```

## File: scripts/ImpactFx.gd
```
class_name ImpactFx
extends Node3D

var life := 0.20
var age := 0.0
var color := Color(0.25, 0.9, 1.0, 1.0)
var scale_boost := 1.0
var mesh_instance: MeshInstance3D
var ring_instance: MeshInstance3D
var flare_instance: MeshInstance3D
var core_material: StandardMaterial3D
var ring_material: StandardMaterial3D
var flare_material: StandardMaterial3D

static var _shared_core_mesh: SphereMesh
static var _shared_ring_mesh: TorusMesh
static var _shared_flare_mesh: QuadMesh
static var _spark_process_cache := {}
static var _spark_mesh_cache := {}

func _ready() -> void:
    mesh_instance = MeshInstance3D.new()
    mesh_instance.name = "ImpactCore"
    mesh_instance.mesh = _core_mesh()
    mesh_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    core_material = _make_material(color, 4.2)
    mesh_instance.material_override = core_material
    add_child(mesh_instance)

    ring_instance = MeshInstance3D.new()
    ring_instance.name = "ImpactRing"
    ring_instance.mesh = _ring_mesh()
    ring_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    ring_instance.rotation_degrees.x = 90.0
    ring_material = _make_material(color.lightened(0.18), 3.4)
    ring_instance.material_override = ring_material
    add_child(ring_instance)

    flare_instance = MeshInstance3D.new()
    flare_instance.name = "ImpactFlare"
    flare_instance.mesh = _flare_mesh()
    flare_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    flare_material = _make_material(color.lightened(0.24), 5.6)
    flare_material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    flare_instance.material_override = flare_material
    add_child(flare_instance)

    var sparks := GPUParticles3D.new()
    sparks.name = "ImpactSparks"
    sparks.amount = 10
    sparks.lifetime = 0.20
    sparks.one_shot = true
    sparks.explosiveness = 1.0
    sparks.randomness = 0.35
    sparks.local_coords = false

    sparks.process_material = _spark_process_material(color)
    sparks.draw_pass_1 = _spark_mesh(color)
    add_child(sparks)
    sparks.emitting = true

static func _core_mesh() -> SphereMesh:
    if _shared_core_mesh != null:
        return _shared_core_mesh
    _shared_core_mesh = SphereMesh.new()
    _shared_core_mesh.radius = 0.20
    _shared_core_mesh.height = 0.40
    _shared_core_mesh.radial_segments = 12
    _shared_core_mesh.rings = 6
    return _shared_core_mesh

static func _ring_mesh() -> TorusMesh:
    if _shared_ring_mesh != null:
        return _shared_ring_mesh
    _shared_ring_mesh = TorusMesh.new()
    _shared_ring_mesh.inner_radius = 0.27
    _shared_ring_mesh.outer_radius = 0.39
    _shared_ring_mesh.rings = 16
    _shared_ring_mesh.ring_segments = 6
    return _shared_ring_mesh

static func _flare_mesh() -> QuadMesh:
    if _shared_flare_mesh != null:
        return _shared_flare_mesh
    _shared_flare_mesh = QuadMesh.new()
    _shared_flare_mesh.size = Vector2(0.62, 0.22)
    return _shared_flare_mesh

static func _spark_cache_key(tint: Color) -> String:
    return tint.to_html(true)

static func _spark_process_material(tint: Color) -> ParticleProcessMaterial:
    var key := _spark_cache_key(tint)
    if _spark_process_cache.has(key):
        return _spark_process_cache[key] as ParticleProcessMaterial
    var material := ParticleProcessMaterial.new()
    material.direction = Vector3(0.0, 1.0, 0.0)
    material.spread = 70.0
    material.initial_velocity_min = 2.8
    material.initial_velocity_max = 5.0
    material.gravity = Vector3(0.0, -7.0, 0.0)
    material.scale_min = 0.45
    material.scale_max = 1.0
    material.color = tint
    _spark_process_cache[key] = material
    return material

static func _spark_mesh(tint: Color) -> QuadMesh:
    var key := _spark_cache_key(tint)
    if _spark_mesh_cache.has(key):
        return _spark_mesh_cache[key] as QuadMesh
    var mesh := QuadMesh.new()
    mesh.size = Vector2(0.055, 0.16)
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = 4.5
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    mesh.material = material
    _spark_mesh_cache[key] = mesh
    return mesh

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
    scale = Vector3.ONE * lerp(0.50, 2.45 * scale_boost, t)
    _fade_material(core_material, t)
    _fade_material(ring_material, t)
    _fade_material(flare_material, minf(1.0, t * 1.55))
    if ring_instance != null:
        ring_instance.scale = Vector3.ONE * lerp(0.70, 1.62, t)
    if flare_instance != null:
        var flare_t := minf(1.0, t * 1.70)
        flare_instance.scale = Vector3(
            lerp(0.58, 2.05 * scale_boost, flare_t),
            lerp(0.74, 1.28 * scale_boost, flare_t),
            1.0
        )
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
var boss_defeat_relief_left := 0.0
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
const MUSIC_PRESSURE_RELIEF_DB := -34.0
const BOSS_DEFEAT_RELIEF_DURATION := 2.2
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
        "projectiles": get_tree().get_node_count_in_group("projectiles"),
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
    boss_defeat_relief_left = maxf(0.0, boss_defeat_relief_left - delta)
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
    if boss_defeat_relief_left > 0.0:
        return MUSIC_PRESSURE_RELIEF_DB
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
        if boss:
            boss_defeat_relief_left = BOSS_DEFEAT_RELIEF_DURATION
            boss_banner_timer = 0.0
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
    # A quiet world-space stain gives kills lasting weight without stacking
    # additional combat particles or lights. Bosses leave larger scorch marks.
    DZCombatGroundMark.spawn_mark(self, at, xp_value >= 30)
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
    if boss_defeat_relief_left > 0.0:
        return "THREAT NEUTRALIZED // PRESSURE DROPPING"
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
    _build_authored_floor_decals()
    _build_floor_seams()
    _build_light_pool_decals()
    _build_ambient_motes()
    _build_containment_lanes()
    _build_perimeter_bulkheads()
    _build_authored_barrier_clusters()
    _build_authored_world_dressing()
    _build_perimeter_street_lights()
    _build_perimeter_beacons()

func _build_ambient_motes() -> void:
    var particles := GPUParticles3D.new()
    particles.name = "AmbientMotes"
    particles.amount = 32
    particles.lifetime = 6.0
    particles.preprocess = 6.0
    particles.randomness = 0.34
    particles.local_coords = false
    particles.position = Vector3(0.0, 0.35, 0.0)
    particles.visibility_aabb = AABB(Vector3(-20.0, -0.5, -20.0), Vector3(40.0, 5.0, 40.0))
    particles.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF

    var process := ParticleProcessMaterial.new()
    process.emission_shape = ParticleProcessMaterial.EMISSION_SHAPE_BOX
    process.emission_box_extents = Vector3(18.0, 0.75, 18.0)
    process.direction = Vector3(0.0, 1.0, 0.0)
    process.spread = 82.0
    process.initial_velocity_min = 0.06
    process.initial_velocity_max = 0.24
    process.gravity = Vector3(0.0, 0.035, 0.0)
    process.scale_min = 0.55
    process.scale_max = 1.45
    process.angle_min = -180.0
    process.angle_max = 180.0
    process.color = Color(0.24, 0.66, 0.78, 0.16)
    particles.process_material = process

    var mote_mesh := QuadMesh.new()
    mote_mesh.size = Vector2(0.028, 0.090)
    var mote_material := StandardMaterial3D.new()
    mote_material.albedo_color = Color(0.30, 0.72, 0.82, 0.16)
    mote_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    mote_material.emission_enabled = true
    mote_material.emission = Color(0.10, 0.32, 0.38)
    mote_material.emission_energy_multiplier = 0.55
    mote_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    mote_material.billboard_mode = BaseMaterial3D.BILLBOARD_ENABLED
    mote_mesh.material = mote_material
    particles.draw_pass_1 = mote_mesh
    add_child(particles)

func _build_quarantine_floor_material() -> ShaderMaterial:
    # The existing Poly Haven CC0 asphalt maps give the arena physical
    # roughness/normal and color variation under its authored containment
    # graphics. This remains ONE draw call and ONE material on the broad floor.
    # Procedural seams, grime, directional wear and scene decals are retained.
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode diffuse_burley, specular_schlick_ggx;

uniform vec3 base_tone = vec3(0.040, 0.052, 0.060);
uniform sampler2D asphalt_albedo : source_color, filter_linear_mipmap_anisotropic, repeat_enable;
uniform sampler2D asphalt_normal : hint_normal, filter_linear_mipmap_anisotropic, repeat_enable;
uniform sampler2D asphalt_arm : filter_linear_mipmap_anisotropic, repeat_enable;
uniform float asphalt_repeat = 16.0;

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

    // Physically authored asphalt grain, not an arbitrary noise-only
    // checkerboard. Keep the photograph subordinate to gameplay silhouettes
    // using its luminance rather than reproducing any bright source hue.
    vec2 asphalt_uv = UV * asphalt_repeat;
    vec3 authored_surface = texture(asphalt_albedo, asphalt_uv).rgb;
    vec3 authored_arm = texture(asphalt_arm, asphalt_uv).rgb;
    float authored_luma = dot(authored_surface, vec3(0.2126, 0.7152, 0.0722));
    float porous_stone = clamp(0.77 + authored_luma * 1.05, 0.76, 1.23);

    vec3 tone = base_tone;
    tone *= mix(1.0, porous_stone, 0.62);
    tone *= 0.94 + panel_variation * 0.075;
    tone *= 0.965 + micro_variation * 0.055;
    tone *= 1.0 - panel_edge * 0.055;
    tone *= 1.0 - grime * 0.055;
    tone += vec3(0.005, 0.011, 0.015) * center_lift;
    tone += vec3(0.010, 0.0025, 0.0010) * perimeter_heat;

    ALBEDO = tone;
    float authored_roughness = clamp(authored_arm.g, 0.65, 1.0);
    float procedural_roughness = clamp(0.83 + (micro_variation - 0.5) * 0.10 + panel_edge * 0.05 + grime * 0.04, 0.75, 0.97);
    ROUGHNESS = clamp(mix(procedural_roughness, authored_roughness, 0.27), 0.72, 0.98);
    METALLIC = 0.055 + panel_variation * 0.045 - grime * 0.012;
    AO = clamp(mix(1.0, authored_arm.r, 0.24), 0.76, 1.0);
    NORMAL_MAP = texture(asphalt_normal, asphalt_uv).rgb;
    NORMAL_MAP_DEPTH = 0.27;
}
"""
    var material := ShaderMaterial.new()
    material.shader = shader
    material.set_shader_parameter("asphalt_repeat", 16.0)
    material.set_shader_parameter("asphalt_albedo", load("res://assets/third_party/polyhaven/asphalt_04/asphalt_04_diff_2k.jpg") as Texture2D)
    material.set_shader_parameter("asphalt_normal", load("res://assets/third_party/polyhaven/asphalt_04/asphalt_04_nor_gl_2k.jpg") as Texture2D)
    material.set_shader_parameter("asphalt_arm", load("res://assets/third_party/polyhaven/asphalt_04/asphalt_04_arm_2k.jpg") as Texture2D)
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
    var placements := [
        {"position": Vector3(-10.8, 0.012, -8.1), "rotation": 0.18},
        {"position": Vector3(10.7, 0.012, -8.0), "rotation": -0.16},
        {"position": Vector3(-10.6, 0.012, 8.2), "rotation": -0.20},
        {"position": Vector3(10.9, 0.012, 8.0), "rotation": 0.15},
    ]
    for index in range(placements.size()):
        var grate := DZAssetLibrary.industrial_floor_grate()
        if grate == null:
            continue
        var placement: Dictionary = placements[index]
        grate.name = "ServiceGrate_%02d" % index
        grate.position = placement["position"]
        grate.rotation.y = float(placement["rotation"])
        grate.scale = Vector3(1.04, 1.0, 0.82)
        add_child(grate)

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

func _build_authored_floor_decals() -> void:
    # Project-owned texture decals break up the procedural floor with authored marks.
    # They are lightweight textured quads because the mobile renderer does not rely on
    # Forward+-only decal projection. All instances share one mesh and three materials.
    var decal_mesh := QuadMesh.new()
    decal_mesh.size = Vector2.ONE

    var materials := {
        "crack": _floor_decal_material(
            "res://assets/decals/quarantine_yard/crack_a.png", 0.64, 0.96
        ),
        "scorch": _floor_decal_material(
            "res://assets/decals/quarantine_yard/scorch_a.png", 0.52, 0.92
        ),
        "blood": _floor_decal_material(
            "res://assets/decals/quarantine_yard/blood_a.png", 0.46, 0.76
        ),
    }

    var placements := [
        {"kind": "crack", "position": Vector3(-4.6, 0.028, -2.8), "size": Vector2(2.45, 2.05), "yaw": 17.0},
        {"kind": "crack", "position": Vector3(5.7, 0.029, 4.9), "size": Vector2(2.25, 1.90), "yaw": -28.0},
        {"kind": "crack", "position": Vector3(-10.4, 0.030, 6.0), "size": Vector2(2.70, 2.20), "yaw": 63.0},
        {"kind": "crack", "position": Vector3(11.2, 0.031, -5.8), "size": Vector2(2.35, 1.95), "yaw": 101.0},
        {"kind": "crack", "position": Vector3(1.8, 0.032, 10.8), "size": Vector2(2.10, 1.75), "yaw": -73.0},
        {"kind": "scorch", "position": Vector3(-6.7, 0.033, 4.4), "size": Vector2(2.10, 1.75), "yaw": 34.0},
        {"kind": "scorch", "position": Vector3(7.2, 0.034, -3.8), "size": Vector2(1.85, 1.55), "yaw": -12.0},
        {"kind": "scorch", "position": Vector3(-12.0, 0.035, -9.0), "size": Vector2(2.25, 1.90), "yaw": 82.0},
        {"kind": "scorch", "position": Vector3(10.0, 0.036, 10.0), "size": Vector2(2.00, 1.70), "yaw": -52.0},
        {"kind": "blood", "position": Vector3(-3.4, 0.037, 5.8), "size": Vector2(1.65, 1.35), "yaw": 22.0},
        {"kind": "blood", "position": Vector3(4.6, 0.038, -8.0), "size": Vector2(1.45, 1.20), "yaw": -41.0},
        {"kind": "blood", "position": Vector3(8.6, 0.039, 6.5), "size": Vector2(1.75, 1.42), "yaw": 116.0},
    ]

    for index in range(placements.size()):
        var placement: Dictionary = placements[index]
        var kind := String(placement["kind"])
        var decal := MeshInstance3D.new()
        decal.name = "AuthoredFloorDecal_%02d_%s" % [index, kind]
        decal.add_to_group("authored_floor_decals")
        decal.mesh = decal_mesh
        decal.position = placement["position"]
        decal.rotation_degrees = Vector3(-90.0, float(placement["yaw"]), 0.0)
        var size: Vector2 = placement["size"]
        decal.scale = Vector3(size.x, size.y, 1.0)
        decal.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        decal.material_override = materials[kind]
        add_child(decal)

func _floor_decal_material(path: String, opacity: float, roughness: float) -> StandardMaterial3D:
    var texture := load(path) as Texture2D
    var material := StandardMaterial3D.new()
    material.albedo_texture = texture
    material.albedo_color = Color(1.0, 1.0, 1.0, opacity)
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.roughness = roughness
    material.metallic = 0.0
    return material

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
        var bulkhead := DZAssetLibrary.industrial_bulkhead()
        if bulkhead == null:
            continue
        var placement: Dictionary = placements[index]
        bulkhead.name = "PerimeterBulkhead_%02d" % index
        bulkhead.position = placement["position"]
        bulkhead.rotation.y = float(placement["rotation"])
        bulkhead.scale = Vector3(1.0, 0.46, 1.0)
        add_child(bulkhead)

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

func _select_offscreen_threat(candidates: Array, origin: Vector3, view_camera: Camera3D, viewport_size: Vector2) -> DZEnemy:
    # Only off-screen elite/boss threats need a marker. A visible boss must not
    # suppress the direction cue for a different elite outside the camera.
    if view_camera == null or not is_instance_valid(view_camera) or viewport_size.x <= 0.0 or viewport_size.y <= 0.0:
        return null
    var margin := Vector2(84.0, 72.0)
    var best: DZEnemy
    var best_priority := -1
    var best_distance_sq := INF
    for node in candidates:
        if node == null or not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or (enemy.kind != "elite" and enemy.kind != "boss"):
            continue
        var at := enemy.global_position
        var projected := view_camera.unproject_position(at + Vector3(0.0, 0.9, 0.0))
        var visible := not view_camera.is_position_behind(at) and (
            projected.x >= margin.x and projected.y >= margin.y
            and projected.x <= viewport_size.x - margin.x and projected.y <= viewport_size.y - margin.y
        )
        if visible:
            continue
        var priority := 2 if enemy.kind == "boss" else 1
        var distance_sq := origin.distance_squared_to(at)
        if priority > best_priority or (priority == best_priority and distance_sq < best_distance_sq):
            best = enemy
            best_priority = priority
            best_distance_sq = distance_sq
    return best

func _update_offscreen_threat_indicator() -> void:
    if hud == null or camera == null or player == null or game_over:
        if hud:
            hud.hide_offscreen_threat()
        return

    var viewport_size := get_viewport().get_visible_rect().size
    var best := _select_offscreen_threat(
        get_tree().get_nodes_in_group("enemies"), player.global_position, camera, viewport_size
    )
    if best == null:
        hud.hide_offscreen_threat()
        return

    var best_distance := player.global_position.distance_to(best.global_position)
    var screen_pos := camera.unproject_position(best.global_position + Vector3(0.0, 0.9, 0.0))
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
var target_lock_marker: MeshInstance3D
var target_lock_enemy: DZEnemy
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
const MOVE_LEAN_ROLL_DEGREES := 4.2
const MOVE_LEAN_PITCH_DEGREES := 2.4

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
    _update_movement_lean(delta)
    if hit_reaction_left <= 0.0:
        _update_authored_animation()

    var target := _combat_target()
    _update_player_marker_pressure(_pressure_target(target))
    _update_target_lock_marker(target, delta)
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

func _movement_lean_target() -> Vector2:
    if move_speed <= 0.001 or velocity.length_squared() <= 0.0025:
        return Vector2.ZERO
    var local_velocity := global_transform.basis.inverse() * Vector3(velocity.x, 0.0, velocity.z)
    var strafe := clampf(local_velocity.x / move_speed, -1.0, 1.0)
    var forward := clampf(-local_velocity.z / move_speed, -1.0, 1.0)
    return Vector2(
        deg_to_rad(forward * MOVE_LEAN_PITCH_DEGREES),
        deg_to_rad(-strafe * MOVE_LEAN_ROLL_DEGREES)
    )

func _update_movement_lean(delta: float) -> void:
    var visual := get_node_or_null("Visual") as Node3D
    if visual == null:
        return
    var lean := _movement_lean_target()
    var blend := 1.0 - exp(-maxf(delta, 0.0) * 12.0)
    visual.rotation.x = lerp_angle(visual.rotation.x, lean.x, blend)
    visual.rotation.z = lerp_angle(visual.rotation.z, lean.y, blend)

func _reset_movement_lean() -> void:
    var visual := get_node_or_null("Visual") as Node3D
    if visual != null:
        visual.rotation.x = 0.0
        visual.rotation.z = 0.0

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
    if authored_anim != null:
        authored_anim.speed_scale = 1.0
    _reset_movement_lean()
    _clear_player_marker_pressure()
    _update_target_lock_marker(null, 0.0)

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
    var base_dir := global_position.direction_to(enemy.global_position)
    base_dir.y = 0.0
    base_dir = base_dir.normalized()
    if base_dir.length_squared() < 0.01:
        return
    # Compensate for the rifle being offset to the player's right. Otherwise
    # close enemies can fall beside a perfectly aimed-looking muzzle tracer.
    var barrel_to_target := enemy.global_position - _projectile_muzzle_origin(Vector3.ZERO)
    barrel_to_target.y = 0.0
    if barrel_to_target.length_squared() > 0.0001:
        base_dir = barrel_to_target.normalized()
    _play_shot_audio()
    _trigger_muzzle_flash()
    _trigger_rifle_recoil()
    for i in range(multishot):
        var offset := float(i) - float(multishot - 1) * 0.5
        var dir := base_dir.rotated(Vector3.UP, deg_to_rad(offset * spread_degrees))
        var projectile := DZProjectile.new()
        # Use the actual visible muzzle rather than a point inside the torso.
        # Every pellet starts on the same barrel, independent of spread angle.
        projectile.setup(_projectile_muzzle_origin(dir),
            dir, projectile_speed, weapon_damage, weapon_tint, weapon_profile)

        var projectile_parent: Node = get_tree().current_scene
        if projectile_parent == null:
            projectile_parent = get_parent()
        if projectile_parent == null:
            projectile.queue_free()
            return
        projectile_parent.add_child(projectile)

func _projectile_muzzle_origin(direction: Vector3) -> Vector3:
    # Shared barrel origin for all weapon protocols; the small forward offset
    # places the projectile just outside the transient 3D flash silhouette.
    var muzzle_position := global_transform * Vector3(0.33, 0.98, -0.90)
    if muzzle_flash != null and is_instance_valid(muzzle_flash):
        muzzle_position = muzzle_flash.global_position
    return muzzle_position + direction * 0.12

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
        _build_target_lock_marker()
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
    _build_target_lock_marker()
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

func _build_target_lock_marker() -> void:
    # Four independent, carefully spaced corner arcs create a calm world-space
    # aim confirmation. One indexed-looking surface, one small shader-free
    # material, no light, no particles and no directional shadow contribution.
    var vertices := PackedVector3Array()
    const ARC_STEPS := 8
    var ring_radius := 0.54
    var ring_width := 0.035
    for quadrant in range(4):
        var center_angle := float(quadrant) * PI * 0.5 + PI * 0.25
        for step in range(ARC_STEPS):
            var start_angle := center_angle - deg_to_rad(24.0) + deg_to_rad(48.0) * float(step) / float(ARC_STEPS)
            var end_angle := center_angle - deg_to_rad(24.0) + deg_to_rad(48.0) * float(step + 1) / float(ARC_STEPS)
            var inner_start := Vector3(cos(start_angle), sin(start_angle), 0.0) * (ring_radius - ring_width)
            var outer_start := Vector3(cos(start_angle), sin(start_angle), 0.0) * ring_radius
            var inner_end := Vector3(cos(end_angle), sin(end_angle), 0.0) * (ring_radius - ring_width)
            var outer_end := Vector3(cos(end_angle), sin(end_angle), 0.0) * ring_radius
            vertices.append_array(PackedVector3Array([
                inner_start, outer_start, outer_end,
                inner_start, outer_end, inner_end,
            ]))
    var arrays := []
    arrays.resize(Mesh.ARRAY_MAX)
    arrays[Mesh.ARRAY_VERTEX] = vertices
    var reticle_mesh := ArrayMesh.new()
    reticle_mesh.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)

    var material := StandardMaterial3D.new()
    material.albedo_color = Color(0.12, 0.72, 0.94, 0.52)
    material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    material.cull_mode = BaseMaterial3D.CULL_DISABLED
    material.emission_enabled = true
    material.emission = Color(0.06, 0.34, 0.46)
    material.emission_energy_multiplier = 0.82

    target_lock_marker = MeshInstance3D.new()
    target_lock_marker.name = "TargetLockReticle"
    target_lock_marker.mesh = reticle_mesh
    target_lock_marker.material_override = material
    target_lock_marker.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    target_lock_marker.top_level = true
    target_lock_marker.rotation_degrees.x = -90.0
    target_lock_marker.visible = false
    add_child(target_lock_marker)

func _update_target_lock_marker(target: DZEnemy, delta: float) -> void:
    if target_lock_marker == null:
        return
    if target == null or not is_instance_valid(target) or target.dead:
        target_lock_marker.visible = false
        target_lock_enemy = null
        return
    var at := target.global_position
    at.y = 0.070
    # Switching to a new enemy must be instantaneous. Smoothing is only used
    # for motion of the same target, avoiding an inaccurate aim cue.
    if target_lock_enemy != target or not target_lock_marker.visible:
        target_lock_marker.global_position = at
    else:
        var follow_alpha := 1.0 - exp(-maxf(delta, 0.0) * 24.0)
        target_lock_marker.global_position = target_lock_marker.global_position.lerp(at, follow_alpha)
    target_lock_enemy = target
    target_lock_marker.visible = true
    var radius_scale := 1.85 if target.kind == "boss" else (1.32 if target.kind in ["elite", "brute"] else 1.0)
    target_lock_marker.scale = Vector3.ONE * radius_scale

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
    muzzle_flash.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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
    # One low-poly tapered mesh, instanced three times, turns the original
    # glowing orb into a directional weapon flash visible from the combat
    # camera. All three spikes share the core material and accessibility pulse.
    var spike_mesh := CylinderMesh.new()
    spike_mesh.top_radius = 0.004
    spike_mesh.bottom_radius = 0.070
    spike_mesh.height = 0.44
    spike_mesh.radial_segments = 6
    spike_mesh.rings = 1
    for wing_index in [-1, 0, 1]:
        var spike := MeshInstance3D.new()
        spike.name = "MuzzleFlashSpear" if wing_index == 0 else (
            "MuzzleFlashWingL" if wing_index < 0 else "MuzzleFlashWingR"
        )
        spike.mesh = spike_mesh
        spike.position = Vector3(float(wing_index) * 0.075, 0.0, -0.24)
        spike.rotation_degrees = Vector3(-90.0, float(wing_index) * 24.0, 0.0)
        if wing_index != 0:
            spike.scale = Vector3(0.72, 0.72, 0.72)
        spike.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        spike.material_override = muzzle_flash_material
        muzzle_flash.add_child(spike)
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
    var planar_speed := Vector2(velocity.x, velocity.z).length()
    if planar_speed <= 0.28:
        _play_authored("Idle_Gun")
        authored_anim.speed_scale = 1.0
        return

    # Match the authored run cycle to the actual acceleration, slowdown and
    # upgraded movement speed. Retiming is isolated from recoil/hit/death clips.
    _play_authored("Run_Gun")
    var fraction := clampf(planar_speed / maxf(move_speed, 0.01), 0.0, 1.0)
    var desired_rate := lerpf(0.70, 1.12, fraction)
    authored_anim.speed_scale = move_toward(authored_anim.speed_scale, desired_rate, 0.18)

func _play_authored(name: String) -> void:
    if authored_anim == null or not authored_anim.has_animation(name):
        return
    if name != "Run_Gun":
        authored_anim.speed_scale = 1.0
    if current_anim == name:
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

static var _core_mesh_cache := {}
static var _trail_mesh_cache := {}
static var _core_material_cache := {}
static var _trail_material_cache := {}
static var _accent_mesh_cache := {}
static var _shared_arc_accent_material: StandardMaterial3D

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
    trail_width = 0.062 * projectile_scale
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
    glow.name = "ProjectileCore"
    glow.mesh = _cached_core_mesh()
    glow.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var core_mat := _cached_core_material()
    glow.material_override = core_mat
    add_child(glow)

    var trail := MeshInstance3D.new()
    trail.name = "ProjectileTrail"
    trail.mesh = _cached_trail_mesh()
    trail.position.z = trail_length * 0.52
    trail.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var trail_mat := _cached_trail_material()
    trail.material_override = trail_mat
    add_child(trail)

    if visual_profile == "cryo":
        glow.scale = Vector3(0.62, 1.55, 0.62)
        glow.rotation_degrees.z = 45.0
    elif visual_profile == "scatter":
        _add_side_spark(core_mat, -1.0)
        _add_side_spark(core_mat, 1.0)
    elif visual_profile == "arc":
        _add_arc_accent()
    elif visual_profile == "inferno":
        _add_flame_core(core_mat)

    if velocity.length_squared() > 0.01:
        look_at(global_position + velocity.normalized(), Vector3.UP)

func _visual_cache_key() -> String:
    return "%s|%s|%.4f|%.4f|%.4f" % [
        visual_profile,
        tint.to_html(true),
        core_radius,
        trail_width,
        trail_length
    ]

func _cached_core_mesh() -> SphereMesh:
    var key := _visual_cache_key()
    if _core_mesh_cache.has(key):
        return _core_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = core_radius * 0.82
    mesh.height = core_radius * 1.64
    mesh.radial_segments = 10
    mesh.rings = 5
    _core_mesh_cache[key] = mesh
    return mesh

func _cached_trail_mesh() -> BoxMesh:
    var key := _visual_cache_key()
    if _trail_mesh_cache.has(key):
        return _trail_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = Vector3(trail_width, trail_width * 0.72, trail_length)
    _trail_mesh_cache[key] = mesh
    return mesh

func _cached_core_material() -> StandardMaterial3D:
    var key := _visual_cache_key()
    if _core_material_cache.has(key):
        return _core_material_cache[key] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    material.albedo_color = tint
    material.emission_enabled = true
    material.emission = tint
    material.emission_energy_multiplier = 3.8
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _core_material_cache[key] = material
    return material

func _cached_trail_material() -> StandardMaterial3D:
    var key := _visual_cache_key()
    if _trail_material_cache.has(key):
        return _trail_material_cache[key] as StandardMaterial3D
    var material := StandardMaterial3D.new()
    material.albedo_color = Color(tint.r * 0.76, tint.g * 0.76, tint.b * 0.76)
    material.emission_enabled = true
    material.emission = Color(tint.r * 0.86, tint.g * 0.86, tint.b * 0.86)
    material.emission_energy_multiplier = 2.15
    material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    _trail_material_cache[key] = material
    return material

func _cached_accent_box_mesh(key: String, size: Vector3) -> BoxMesh:
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as BoxMesh
    var mesh := BoxMesh.new()
    mesh.size = size
    _accent_mesh_cache[key] = mesh
    return mesh

func _cached_accent_sphere_mesh(key: String, radius: float, height: float) -> SphereMesh:
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as SphereMesh
    var mesh := SphereMesh.new()
    mesh.radius = radius
    mesh.height = height
    mesh.radial_segments = 10
    mesh.rings = 5
    _accent_mesh_cache[key] = mesh
    return mesh

func _cached_arc_accent_mesh() -> TorusMesh:
    var key := "arc|%.4f" % core_radius
    if _accent_mesh_cache.has(key):
        return _accent_mesh_cache[key] as TorusMesh
    var mesh := TorusMesh.new()
    mesh.inner_radius = core_radius * 0.85
    mesh.outer_radius = core_radius * 1.45
    mesh.rings = 12
    mesh.ring_segments = 6
    _accent_mesh_cache[key] = mesh
    return mesh

static func _arc_accent_material() -> StandardMaterial3D:
    if _shared_arc_accent_material != null:
        return _shared_arc_accent_material
    _shared_arc_accent_material = StandardMaterial3D.new()
    _shared_arc_accent_material.albedo_color = Color(0.58, 0.36, 1.0)
    _shared_arc_accent_material.emission_enabled = true
    _shared_arc_accent_material.emission = _shared_arc_accent_material.albedo_color
    _shared_arc_accent_material.emission_energy_multiplier = 2.8
    _shared_arc_accent_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_arc_accent_material

func _add_side_spark(mat: StandardMaterial3D, side: float) -> void:
    var spark := MeshInstance3D.new()
    var key := "scatter_spark|%.4f" % trail_length
    spark.mesh = _cached_accent_box_mesh(key, Vector3(0.025, 0.025, trail_length * 0.62))
    spark.position = Vector3(side * 0.10, 0.0, trail_length * 0.30)
    spark.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    spark.material_override = mat
    add_child(spark)

func _add_arc_accent() -> void:
    var accent := MeshInstance3D.new()
    accent.mesh = _cached_arc_accent_mesh()
    accent.rotation_degrees.x = 90.0
    accent.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    accent.material_override = _arc_accent_material()
    add_child(accent)

func _add_flame_core(base_material: StandardMaterial3D) -> void:
    var flame := MeshInstance3D.new()
    var key := "inferno_flame|%.4f" % core_radius
    flame.mesh = _cached_accent_sphere_mesh(key, core_radius * 0.58, core_radius * 1.55)
    flame.scale = Vector3(0.72, 0.72, 1.42)
    flame.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
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
    var previous_position := global_position
    global_position += velocity * delta
    var hit_candidates := _swept_hit_candidates(previous_position, global_position)
    if hit_candidates.size() > 1:
        hit_candidates.sort_custom(func(a: DZEnemy, b: DZEnemy) -> bool:
            return previous_position.distance_squared_to(a.global_position) < previous_position.distance_squared_to(b.global_position)
        )
    for enemy in hit_candidates:
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
        var critical := randf() < critical_chance
        var dealt_damage := damage * (1.75 if critical else 1.0)
        hit_enemy_ids[enemy.get_instance_id()] = true
        enemy.take_damage(dealt_damage, critical)
        _apply_protocol_hit(enemy, dealt_damage)
        _impact(critical, enemy.global_position + Vector3(0.0, 0.55, 0.0))
        if visual_profile == "rail" and pierce_remaining > 0:
            pierce_remaining -= 1
            continue
        queue_free()
        return
    if age >= lifetime:
        queue_free()

func _swept_hit_candidates(from: Vector3, to: Vector3) -> Array[DZEnemy]:
    var segment := to - from
    segment.y = 0.0
    var travel := segment.length()
    var midpoint := from.lerp(to, 0.5)
    var query_radius := radius + travel * 0.5
    var result: Array[DZEnemy] = []
    for node in _enemies_near(midpoint, query_radius):
        if not is_instance_valid(node):
            continue
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or hit_enemy_ids.has(enemy.get_instance_id()):
            continue
        var offset := enemy.global_position - from
        offset.y = 0.0
        var t := 0.0
        if segment.length_squared() > 0.000001:
            t = clampf(offset.dot(segment) / segment.length_squared(), 0.0, 1.0)
        var closest := from + segment * t
        var miss := enemy.global_position - closest
        miss.y = 0.0
        if miss.length_squared() <= radius * radius:
            result.append(enemy)
    return result

func _candidate_enemies() -> Array:
    return _enemies_near(global_position, radius)

func _enemies_near(position: Vector3, range_radius: float) -> Array:
    var scene := get_tree().current_scene if get_tree() != null else null
    if scene != null and scene.has_method("query_enemies_near"):
        return scene.query_enemies_near(position, range_radius)
    return get_tree().get_nodes_in_group("enemies") if get_tree() != null else []

func _impact(critical := false, at := Vector3.INF) -> void:
    var fx := ImpactFx.new()
    fx.color = Color(1.0, 0.76, 0.18) if critical else tint
    fx.scale_boost = (1.70 if critical else 1.0) * impact_scale
    var fx_parent: Node = get_tree().current_scene if get_tree() != null else null
    if fx_parent == null:
        fx_parent = get_parent()
    if fx_parent == null:
        fx.queue_free()
        return
    fx_parent.add_child(fx)
    fx.global_position = global_position if at == Vector3.INF else at

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
    for node in _enemies_near(primary.global_position, range_radius):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
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
    for node in _enemies_near(primary.global_position, 3.8):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or enemy == primary:
            continue
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
        "trail_length": 0.72,
        "impact_weight": 1.06
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
        "trail_length": 0.42,
        "impact_weight": 1.24
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
        "trail_length": 1.55,
        "impact_weight": 1.42
    },
    "inferno": {
        "tint": Color(1.0, 0.24, 0.035),
        "damage_multiplier": 1.20,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.08,
        "fire_interval_cap": 0.80,
        "projectile_scale": 1.10,
        "trail_length": 0.92,
        "impact_weight": 1.30
    },
    "cryo": {
        "tint": Color(0.30, 0.90, 1.0),
        "damage_multiplier": 0.95,
        "projectile_speed_multiplier": 1.12,
        "fire_interval_multiplier": 0.90,
        "fire_interval_floor": 0.09,
        "projectile_scale": 1.08,
        "trail_length": 1.00,
        "impact_weight": 1.30
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
        "trail_length": 1.10,
        "impact_weight": 1.28
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
var combat_enabled := true

const MAGNET_RADIUS := 5.0
const FORCED_MAGNET_AGE := 5.0
const MAGNET_SPEED := 10.0
const FORCED_MAGNET_SPEED := 14.0

static var _shared_mesh: SphereMesh
static var _shared_halo_mesh: TorusMesh
static var _shared_material: StandardMaterial3D
static var _shared_halo_material: StandardMaterial3D

var orb_visual: MeshInstance3D
var halo_visual: MeshInstance3D

func _ready() -> void:
    add_to_group("xp_orbs")
    orb_visual = MeshInstance3D.new()
    orb_visual.name = "XpOrbVisual"
    orb_visual.mesh = _orb_mesh()
    orb_visual.material_override = _orb_material()
    orb_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    add_child(orb_visual)

    halo_visual = MeshInstance3D.new()
    halo_visual.name = "XpOrbHalo"
    halo_visual.mesh = _halo_mesh()
    halo_visual.material_override = _halo_material()
    halo_visual.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    halo_visual.position.y = -0.11
    halo_visual.scale = Vector3.ONE * 0.82
    add_child(halo_visual)

static func _orb_mesh() -> SphereMesh:
    if _shared_mesh != null:
        return _shared_mesh
    _shared_mesh = SphereMesh.new()
    _shared_mesh.radius = 0.12
    _shared_mesh.height = 0.24
    _shared_mesh.radial_segments = 12
    _shared_mesh.rings = 6
    return _shared_mesh

static func _halo_mesh() -> TorusMesh:
    if _shared_halo_mesh != null:
        return _shared_halo_mesh
    _shared_halo_mesh = TorusMesh.new()
    _shared_halo_mesh.inner_radius = 0.16
    _shared_halo_mesh.outer_radius = 0.205
    _shared_halo_mesh.rings = 16
    _shared_halo_mesh.ring_segments = 6
    return _shared_halo_mesh

static func _halo_material() -> StandardMaterial3D:
    if _shared_halo_material != null:
        return _shared_halo_material
    _shared_halo_material = StandardMaterial3D.new()
    _shared_halo_material.albedo_color = Color(0.12, 0.92, 0.74, 0.54)
    _shared_halo_material.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
    _shared_halo_material.emission_enabled = true
    _shared_halo_material.emission = Color(0.06, 1.0, 0.68)
    _shared_halo_material.emission_energy_multiplier = 1.75
    _shared_halo_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_halo_material

static func _orb_material() -> StandardMaterial3D:
    if _shared_material != null:
        return _shared_material
    _shared_material = StandardMaterial3D.new()
    _shared_material.albedo_color = Color(0.18, 0.95, 0.75)
    _shared_material.emission_enabled = true
    _shared_material.emission = Color(0.1, 1.0, 0.7)
    _shared_material.emission_energy_multiplier = 3.0
    _shared_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
    return _shared_material

func set_combat_enabled(enabled: bool) -> void:
    combat_enabled = enabled
    if not enabled:
        velocity = Vector3.ZERO

func _process(delta: float) -> void:
    if not combat_enabled:
        return
    age += delta
    rotation.y += delta * 4.0
    position.y = 0.18 + sin(age * 5.0) * 0.05
    var idle_pulse := 0.93 + (0.5 + 0.5 * sin(age * 6.4)) * 0.10
    if orb_visual != null:
        orb_visual.scale = Vector3.ONE * idle_pulse
    if target == null or not is_instance_valid(target):
        return
    var flat_target := target.global_position
    flat_target.y = global_position.y
    var distance := global_position.distance_to(flat_target)
    var forced_magnet := age >= FORCED_MAGNET_AGE
    var magnetized := distance < MAGNET_RADIUS or forced_magnet
    if halo_visual != null:
        var halo_pulse := 0.86 + (0.5 + 0.5 * sin(age * 7.2)) * 0.14
        var magnet_boost := 1.34 if magnetized else 1.0
        halo_visual.scale = Vector3.ONE * halo_pulse * magnet_boost
    if magnetized:
        var dir := global_position.direction_to(flat_target)
        var target_speed := FORCED_MAGNET_SPEED if forced_magnet else MAGNET_SPEED
        velocity = velocity.lerp(dir * target_speed, 1.0 - exp(-delta * 7.0))
        global_position += velocity * delta
    if distance < 0.55:
        collected.emit(amount)
        queue_free()
```

## File: tests/android_play_export_contract_test.gd
```
extends SceneTree

func _initialize() -> void:
    var project_source := FileAccess.get_file_as_string("res://project.godot")
    var preset_source := FileAccess.get_file_as_string("res://export_presets.cfg")

    var required_project := [
        'config/name="Deadline: Zero"',
        'window/handheld/orientation=4',
        'renderer/rendering_method="mobile"'
    ]
    for token in required_project:
        if not project_source.contains(token):
            push_error("Android Play project contract missing: %s" % token)
            quit(1)
            return

    var required_release := [
        'name="Android Play Release"',
        'gradle_build/use_gradle_build=true',
        'gradle_build/export_format=1',
        'gradle_build/min_sdk="26"',
        'gradle_build/target_sdk="36"',
        'architectures/armeabi-v7a=false',
        'architectures/arm64-v8a=true',
        'architectures/x86=false',
        'architectures/x86_64=false',
        'version/code=1',
        'version/name="0.1.0"',
        'package/unique_name="com.deadlinezero.game"',
        'package/name="Deadline: Zero"',
        'package/signed=true',
        'package/show_as_launcher_app=true',
        'launcher_icons/main_192x192="res://assets/ui/deadline_zero_icon.svg"',
        'launcher_icons/adaptive_foreground_432x432="res://assets/ui/deadline_zero_adaptive_foreground.svg"',
        'launcher_icons/adaptive_background_432x432="res://assets/ui/deadline_zero_adaptive_background.svg"',
        'launcher_icons/adaptive_monochrome_432x432="res://assets/ui/deadline_zero_adaptive_monochrome.svg"',
        'user_data_backup/allow=false'
    ]
    for token in required_release:
        if not preset_source.contains(token):
            push_error("Android Play export contract missing: %s" % token)
            quit(1)
            return

    for icon_path in [
        "res://assets/ui/deadline_zero_icon.svg",
        "res://assets/ui/deadline_zero_adaptive_foreground.svg",
        "res://assets/ui/deadline_zero_adaptive_background.svg",
        "res://assets/ui/deadline_zero_adaptive_monochrome.svg"
    ]:
        if not ResourceLoader.exists(icon_path):
            push_error("Android launcher icon asset missing: %s" % icon_path)
            quit(1)
            return

    if not preset_source.contains('name="Android Debug"') or not preset_source.contains('package/unique_name="com.deadlinezero.godot"'):
        push_error("Debug package must remain isolated from final Play application ID")
        quit(1)
        return

    print("Deadline Zero Android Play export contract: OK")
    quit(0)
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

## File: tests/attack_facing_lock_test.gd
```
extends SceneTree

# An authored attack clip should face its world-space warning, never the
# sideways steering direction inherited from swarm avoidance / strafing.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var player := Node3D.new()
    player.position = Vector3(4.0, 0.0, 1.6)
    root.add_child(player)
    await process_frame

    for kind in ["charger", "harrier", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, player)
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        root.add_child(enemy)
        await process_frame
        enemy.global_position = Vector3.ZERO
        enemy.look_at(Vector3(-3.0, 0.0, 0.0), Vector3.UP)
        enemy.velocity = Vector3(2.0, 0.0, 1.0)

        enemy.pending_special = "charge" if kind == "charger" else (
            "harrier_shot" if kind == "harrier" else ""
        )
        var locked := player.global_position
        enemy._begin_telegraphed_attack(0.52, locked)
        var expected := (locked - enemy.global_position).normalized()
        var facing := (-enemy.global_transform.basis.z).normalized()
        if facing.dot(expected) < 0.995:
            push_error("Attack anticipation body did not face its danger corridor: %s" % kind)
            quit(1)
            return
        if enemy.velocity.length_squared() > 0.0001 or enemy.attack_windup < 0.5:
            push_error("Attack anticipation did not freeze momentum before warning: %s" % kind)
            quit(1)
            return
        if enemy.telegraph_visual == null or enemy.authored_anim == null:
            push_error("Attack no longer drives real GLTF body and danger telegraph: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim.has_animation("Idle_Attack") and enemy.current_anim != "Idle_Attack":
            push_error("Enemy windup did not enter imported attack animation: %s" % kind)
            quit(1)
            return

        # Moving the target during the warning must *not* retarget the tell or
        # rotate the attacker. A dodge remains meaningful.
        player.global_position = Vector3(-1.0, 0.0, 6.0)
        enemy._physics_process(0.10)
        if enemy.attack_target_position.distance_to(locked) > 0.001:
            push_error("Enemy telegraph unfairly followed survivor dodge: %s" % kind)
            quit(1)
            return
        if (-enemy.global_transform.basis.z).normalized().dot(expected) < 0.995:
            push_error("Enemy windup turned toward dodging player: %s" % kind)
            quit(1)
            return
        enemy.set_combat_enabled(false)
        enemy.queue_free()
        player.global_position = locked
        await process_frame

    var overlap := DZEnemy.new()
    overlap.configure("elite", 1.0, player)
    overlap.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(overlap)
    await process_frame
    overlap.global_position = player.global_position
    overlap._begin_telegraphed_attack(0.32, overlap.global_position)
    if overlap.attack_windup <= 0.0 or overlap.telegraph_visual == null:
        push_error("Zero-range telegraph cannot safely begin")
        quit(1)
        return

    print("Deadline Zero locked 3D attack anticipation alignment: OK (4 archetypes)")
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

    var telegraph_mesh := enemy.telegraph_visual as MeshInstance3D
    if telegraph_mesh == null or telegraph_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Attack telegraph ring must not cast dynamic shadows")
        quit(1)
        return
    var tick_mesh_resource: Mesh
    var tick_count := 0
    for child in enemy.telegraph_visual.get_children():
        if not child is MeshInstance3D:
            continue
        var tick := child as MeshInstance3D
        if not tick.name.begins_with("TelegraphTick_"):
            continue
        tick_count += 1
        if tick.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Telegraph ticks must not cast dynamic shadows")
            quit(1)
            return
        if tick_mesh_resource == null:
            tick_mesh_resource = tick.mesh
        elif tick.mesh != tick_mesh_resource:
            push_error("Telegraph ticks must reuse shared geometry for one attack radius")
            quit(1)
            return
    if tick_count != 8:
        push_error("Boss telegraph must expose eight premium directional ticks")
        quit(1)
        return
    var inner_ring := enemy.telegraph_visual.get_node_or_null("TelegraphInnerRing") as MeshInstance3D
    if inner_ring == null or inner_ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Boss telegraph must include a shadow-free inner danger ring")
        quit(1)
        return

    var first_ring_mesh := telegraph_mesh.mesh
    enemy._show_telegraph(1.75, 0.40)
    await process_frame
    var replacement_telegraph := enemy.telegraph_visual as MeshInstance3D
    if replacement_telegraph == null or replacement_telegraph.mesh != first_ring_mesh:
        push_error("Same-radius attack telegraphs must reuse ring mesh resources")
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
    if absf(enemy.telegraph_visual.rotation.y) <= 0.01:
        push_error("Boss telegraph ticks did not gain rotational escalation")
        quit(1)
        return

    enemy.kind = "charger"
    enemy.pending_special = "charge"
    enemy.global_position = Vector3.ZERO
    enemy.attack_target_position = Vector3(4.4, 0.0, 0.0)
    enemy._show_telegraph(1.05, 0.40)
    await process_frame
    var charge_lane := enemy.telegraph_visual.get_node_or_null("ChargeLane") as MeshInstance3D
    var charge_mesh := charge_lane.mesh as BoxMesh if charge_lane != null else null
    if charge_lane == null or charge_mesh == null or charge_mesh.size.z < 4.2:
        push_error("Charger telegraph is missing readable directional corridor")
        quit(1)
        return
    if charge_lane.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or charge_mesh.size.x < 0.26:
        push_error("Charger telegraph corridor lost mobile-safe width/shadow contract")
        quit(1)
        return

    enemy.kind = "harrier"
    enemy.pending_special = "harrier_shot"
    enemy.attack_target_position = Vector3(-5.2, 0.0, 1.2)
    enemy._show_telegraph(1.05, 0.40)
    await process_frame
    var aim_lane := enemy.telegraph_visual.get_node_or_null("HarrierAimLane") as MeshInstance3D
    var aim_mesh := aim_lane.mesh as BoxMesh if aim_lane != null else null
    if aim_lane == null or aim_mesh == null or aim_mesh.size.z < 5.0:
        push_error("Harrier telegraph is missing long-range aim lane")
        quit(1)
        return
    if aim_mesh.size.x > 0.14 or aim_lane.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Harrier aim lane lost precision/mobile-safe contract")
        quit(1)
        return

    print("Deadline Zero attack telegraph escalation: OK")
    quit(0)
```

## File: tests/authored_asphalt_pbr_floor_test.gd
```
extends SceneTree

# Confirm that the production arena really uses the downloaded CC0 asphalt
# PBR textures, not merely an untextured procedural stand-in.
const MAPS := {
    "asphalt_albedo": "res://assets/third_party/polyhaven/asphalt_04/asphalt_04_diff_2k.jpg",
    "asphalt_normal": "res://assets/third_party/polyhaven/asphalt_04/asphalt_04_nor_gl_2k.jpg",
    "asphalt_arm": "res://assets/third_party/polyhaven/asphalt_04/asphalt_04_arm_2k.jpg",
}

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var packed := load("res://scenes/Main.tscn") as PackedScene
    if packed == null:
        push_error("Production arena is unavailable for PBR terrain validation")
        quit(1)
        return
    var main := packed.instantiate()
    get_root().add_child(main)
    current_scene = main
    await process_frame

    var floor := main.get_node_or_null("QuarantineFloor") as MeshInstance3D
    if floor == null or not floor.mesh is PlaneMesh or not floor.material_override is ShaderMaterial:
        push_error("Main arena floor lost its one-surface industrial shader")
        quit(1)
        return
    var material := floor.material_override as ShaderMaterial
    var shader := material.shader
    if shader == null:
        push_error("Quarantine floor PBR shader is missing")
        quit(1)
        return
    var code := shader.code
    for required in ["panel_variation", "micro_variation", "macro_variation", "perimeter_heat", "authored_surface", "NORMAL_MAP", "NORMAL_MAP_DEPTH", "ROUGHNESS", "AO ="]:
        if not code.contains(required):
            push_error("Floor lost authored terrain detail or grading: %s" % required)
            quit(1)
            return
    if code.contains("ALPHA =") or floor.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Large arena ground must stay opaque and shadow-free for mobile")
        quit(1)
        return
    var tile_repeat = material.get_shader_parameter("asphalt_repeat")
    if floor.mesh.get_surface_count() != 1 or typeof(tile_repeat) != TYPE_FLOAT or float(tile_repeat) < 12.0:
        push_error("PBR ground lost single-pass tiled geometry")
        quit(1)
        return

    for uniform in MAPS:
        var path: String = MAPS[uniform]
        var texture := material.get_shader_parameter(uniform) as Texture2D
        if texture == null or texture.resource_path != path:
            push_error("Main scene does not bind the actual authored PBR map %s" % uniform)
            quit(1)
            return
        if texture.get_width() < 1024 or texture.get_height() < 1024:
            push_error("Authored terrain PBR map has insufficient source resolution: %s" % uniform)
            quit(1)
            return

    var license_text := FileAccess.get_file_as_string("res://assets/third_party/polyhaven/asphalt_04/SOURCE.json")
    if not license_text.contains("CC0-1.0"):
        push_error("Third-party floor source attribution/license is missing")
        quit(1)
        return

    print("Deadline Zero authored asphalt PBR floor: OK (diffuse, normal, ARM; 1 draw surface)")
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
    _assert_surface_preservation(DZAssetLibrary.player(), "player")
    _assert_surface_preservation(DZAssetLibrary.rifle(), "rifle")
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


func _assert_surface_preservation(instance: Node3D, label: String) -> void:
    if instance == null:
        push_error("Failed to instantiate graded authored asset: " + label)
        quit(1)
        return
    var mesh_nodes: Array[MeshInstance3D] = []
    if instance is MeshInstance3D:
        mesh_nodes.append(instance as MeshInstance3D)
    for node in instance.find_children("*", "MeshInstance3D", true, false):
        mesh_nodes.append(node as MeshInstance3D)
    if mesh_nodes.is_empty():
        push_error("Graded authored asset has no meshes: " + label)
        instance.free()
        quit(1)
        return

    for mesh_instance in mesh_nodes:
        if mesh_instance == null or mesh_instance.mesh == null:
            continue
        if mesh_instance.material_override != null:
            push_error("Graded authored asset flattened all surfaces: " + label)
            instance.free()
            quit(1)
            return
        for surface_index in range(mesh_instance.mesh.get_surface_count()):
            var source := mesh_instance.mesh.surface_get_material(surface_index)
            if source == null:
                continue
            var graded := mesh_instance.get_surface_override_material(surface_index)
            if graded == null:
                push_error("Missing per-surface graded material for %s surface %d" % [label, surface_index])
                instance.free()
                quit(1)
                return
            if source is BaseMaterial3D and graded is BaseMaterial3D:
                var source_material := source as BaseMaterial3D
                var graded_material := graded as BaseMaterial3D
                if source_material.albedo_texture != graded_material.albedo_texture:
                    push_error("Per-surface grading replaced authored texture for %s surface %d" % [label, surface_index])
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

## File: tests/authored_audio_asset_test.gd
```
extends SceneTree

const EXPECTED := [
    "res://assets/audio/authored/weapon_vanguard.wav",
    "res://assets/audio/authored/weapon_scatter.wav",
    "res://assets/audio/authored/weapon_rail.wav",
    "res://assets/audio/authored/weapon_inferno.wav",
    "res://assets/audio/authored/weapon_cryo.wav",
    "res://assets/audio/authored/weapon_arc.wav",
    "res://assets/audio/authored/impact_hit.wav",
    "res://assets/audio/authored/impact_critical.wav",
    "res://assets/audio/authored/impact_kill.wav",
    "res://assets/audio/authored/impact_boss.wav",
    "res://assets/audio/authored/boss_stinger.wav",
    "res://assets/audio/authored/music_run_loop.wav",
    "res://assets/audio/authored/music_pressure_layer.wav",
    "res://assets/audio/authored/ui_level_up.wav",
    "res://assets/audio/authored/ui_upgrade_confirm.wav",
    "res://assets/audio/authored/ui_pause_toggle.wav",
    "res://assets/audio/authored/ui_game_over.wav"
]

func _init() -> void:
    for path in EXPECTED:
        if not ResourceLoader.exists(path):
            push_error("Missing generated authored audio asset: %s" % path)
            quit(1)
            return
        var stream := load(path) as AudioStream
        if stream == null or stream.get_length() <= 0.10:
            push_error("Invalid generated authored audio stream: %s" % path)
            quit(1)
            return

    var music := DZCombatAudio.run_music_stream()
    if music == null or music.get_length() < 11.5:
        push_error("Authored run music is missing or too short")
        quit(1)
        return
    if music is AudioStreamWAV and (music as AudioStreamWAV).loop_mode != AudioStreamWAV.LOOP_FORWARD:
        push_error("Authored run music is not configured for forward looping")
        quit(1)
        return

    var pressure := DZCombatAudio.pressure_music_stream()
    if pressure == null or pressure.get_length() < 11.5:
        push_error("Authored pressure music is missing or too short")
        quit(1)
        return

    for key in ["level_up", "upgrade_confirm", "pause_toggle", "game_over"]:
        var cue := DZCombatAudio.ui_stream(key)
        if cue == null or cue.get_length() < 0.14:
            push_error("Authored UI/progression cue is missing or too short: %s" % key)
            quit(1)
            return

    var boss := DZCombatAudio.boss_stinger()
    if boss == null or boss.get_length() < 2.0:
        push_error("Authored boss stinger is missing or too short")
        quit(1)
        return

    var readme := FileAccess.get_file_as_string("res://assets/audio/authored/README.md")
    if not readme.contains("No third-party samples") or not readme.contains("20261004"):
        push_error("Authored audio provenance contract is incomplete")
        quit(1)
        return

    print("Deadline Zero generated authored audio assets: OK")
    quit(0)
```

## File: tests/authored_gait_sync_test.gd
```
extends SceneTree

# Real imported GLTF AnimationPlayer nodes, not string-only source assertions.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var survivor := DZPlayer.new()
    survivor.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(survivor)
    await process_frame
    if survivor.authored_anim == null:
        push_error("Authored survivor AnimationPlayer missing")
        quit(1)
        return

    survivor.velocity = Vector3(0.65, 0.0, 0.0)
    survivor._update_authored_animation()
    var slow_rate := survivor.authored_anim.speed_scale
    if survivor.current_anim != "Run_Gun" or slow_rate >= 1.0 or slow_rate < 0.68:
        push_error("Survivor walk acceleration does not reduce imported run clip speed")
        quit(1)
        return

    survivor.velocity = Vector3(survivor.move_speed, 0.0, 0.0)
    for _step in range(4):
        survivor._update_authored_animation()
    var full_rate := survivor.authored_anim.speed_scale
    if full_rate <= slow_rate or full_rate > 1.121:
        push_error("Survivor full-speed gait is not faster than acceleration gait")
        quit(1)
        return

    survivor.velocity = Vector3.ZERO
    survivor._update_authored_animation()
    if survivor.current_anim != "Idle_Gun" or not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor idle animation did not restore normal playback")
        quit(1)
        return

    survivor.velocity = Vector3(0.80, 0.0, 0.0)
    survivor._update_authored_animation()
    survivor.take_damage(4.0)
    if survivor.current_anim != "HitReact" or not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor hit reaction inherited slowed locomotion playback")
        quit(1)
        return
    survivor.set_combat_enabled(false)
    if not is_equal_approx(survivor.authored_anim.speed_scale, 1.0):
        push_error("Survivor combat shutdown retained altered animation speed")
        quit(1)
        return

    for kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.kind = kind
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        enemy.configure(kind, 1.0, survivor)
        root.add_child(enemy)
        await process_frame
        if enemy.authored_anim == null:
            push_error("Imported enemy AnimationPlayer missing: %s" % kind)
            quit(1)
            return

        enemy.velocity = Vector3(enemy.move_speed * 0.42, 0.0, 0.0)
        enemy._update_authored_animation(10.0)
        var reduced_rate := enemy.authored_anim.speed_scale
        if reduced_rate < 0.38 or reduced_rate >= 1.0:
            push_error("Slow/standoff enemy gait did not retime: %s (%.3f)" % [kind, reduced_rate])
            quit(1)
            return

        enemy.velocity = Vector3(enemy.move_speed, 0.0, 0.0)
        for _step in range(4):
            enemy._update_authored_animation(10.0)
        if enemy.authored_anim.speed_scale <= reduced_rate:
            push_error("Full enemy pursuit kept a slower gait than standoff: %s" % kind)
            quit(1)
            return
        if enemy.kind == "boss" and enemy.authored_anim.speed_scale > 0.84:
            push_error("Boss must retain a weightier imported 3D gait")
            quit(1)
            return

        enemy.apply_shock(0.24)
        enemy._physics_process(0.02)
        if enemy.authored_anim.speed_scale != 0.0:
            push_error("Stunned enemy kept cycling its legs in place: %s" % kind)
            quit(1)
            return
        enemy.shock_left = 0.0
        enemy.velocity = Vector3(enemy.move_speed, 0.0, 0.0)
        enemy._update_authored_animation(10.0)
        if enemy.authored_anim.speed_scale <= 0.0:
            push_error("Enemy gait remained frozen after shock: %s" % kind)
            quit(1)
            return
        enemy.set_combat_enabled(false)
        if not is_equal_approx(enemy.authored_anim.speed_scale, 1.0):
            push_error("Enemy combat shutdown did not reset clip rate: %s" % kind)
            quit(1)
            return

        enemy.queue_free()
        await process_frame

    print("Deadline Zero authored 3D gait retiming and shock recovery: OK")
    quit(0)
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

    for node in ground_details:
        if not node is Node3D or not str(node.name).begins_with("StreetDamage_"):
            push_error("Unexpected ground-detail node: %s" % node.name)
            quit(1)
            return
        var segments := node.find_children("CrackSegment_*", "MeshInstance3D", true, false)
        if segments.size() != 4:
            push_error("Street damage must use 4 procedural crack segments, got %d on %s" % [segments.size(), node.name])
            quit(1)
            return
        for segment in segments:
            var mesh_instance := segment as MeshInstance3D
            if mesh_instance == null or not mesh_instance.mesh is BoxMesh:
                push_error("Street damage segment is not procedural BoxMesh")
                quit(1)
                return
            var size := (mesh_instance.mesh as BoxMesh).size
            if size.x > 1.0 or size.z > 0.05:
                push_error("Street damage segment exceeded decal-scale geometry: %s" % size)
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

## File: tests/boss_encounter_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-boss-encounter.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

    for node in get_nodes_in_group("enemies"):
        node.queue_free()
    await process_frame

    scene.elapsed = 150.0
    scene.spawn_clock = 999.0
    scene.next_boss_time = 9999.0
    scene.director_profile = scene.run_director.profile(scene.elapsed, 8)
    scene.max_enemies = 24

    var player := scene.player as DZPlayer
    if player == null:
        push_error("Boss capture has no player")
        quit(1)
        return
    player.max_health = 5000.0
    player.health = 5000.0
    player.invulnerability = 20.0
    player.weapon_damage = 8.0
    player.fire_interval = 0.22

    scene._spawn_enemy("boss")
    scene._spawn_enemy("runner")
    scene._spawn_enemy("elite")
    await process_frame

    var boss: DZEnemy
    var runner: DZEnemy
    var elite: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null:
            continue
        match enemy.kind:
            "boss":
                boss = enemy
            "runner":
                runner = enemy
            "elite":
                elite = enemy

    if boss == null or runner == null or elite == null:
        push_error("Boss capture failed to stage the encounter roster")
        quit(1)
        return

    # Keep the encounter hero readable below the boss HUD instead of hiding its face/body
    # directly behind the health panel in Store captures.
    boss.global_position = Vector3(3.2, 0.0, 2.6)
    runner.global_position = Vector3(-4.4, 0.0, 2.2)
    elite.global_position = Vector3(-3.3, 0.0, -2.0)
    boss.health = boss.max_health * 0.72
    boss.health_changed.emit(boss.health, boss.max_health)

    scene.current_boss = boss
    scene.boss_reveal_target = boss
    scene.boss_reveal_left = scene.BOSS_REVEAL_DURATION
    scene.hud.show_boss("REVENANT PRIME", boss.max_health)
    scene.hud.set_boss_health(boss.health, boss.max_health)

    for _tick in range(24):
        await physics_frame
    for _frame in range(8):
        await process_frame

    if boss.dead or not scene.hud.boss_panel.visible:
        push_error("Boss capture lost active boss presentation before capture")
        quit(1)
        return

    var boss_screen: Vector2 = scene.camera.unproject_position(boss.global_position + Vector3(0.0, 1.0, 0.0))
    var viewport_size: Vector2 = get_root().get_visible_rect().size
    if scene.camera.is_position_behind(boss.global_position):
        push_error("Boss capture camera placed the boss behind the camera")
        quit(1)
        return
    if boss_screen.x < viewport_size.x * 0.20 or boss_screen.x > viewport_size.x * 0.80:
        push_error("Boss capture framing pushed the boss outside the central readable zone")
        quit(1)
        return
    if boss_screen.y < viewport_size.y * 0.32 or boss_screen.y > viewport_size.y * 0.82:
        push_error("Boss capture framing must keep the boss below its HUD and inside the readable zone")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Boss capture has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Boss capture produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 1280 or height < 720:
        push_error("Boss capture must be landscape and at least 1280x720, got %dx%d" % [width, height])
        quit(1)
        return

    var bright := 0
    var total := 0
    var min_luma := 1.0
    var max_luma := 0.0
    for y in range(0, height, 10):
        for x in range(0, width, 10):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.045:
                bright += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    if bright_fraction < 0.008 or max_luma - min_luma < 0.05:
        push_error("Boss capture lacks readable tonal separation")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save boss encounter capture: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_BOSS_CAPTURE_OK %dx%d boss_screen=(%.1f,%.1f)" % [width, height, boss_screen.x, boss_screen.y])
    quit(0)
```

## File: tests/boss_hud_identity_test.gd
```
extends SceneTree

func _init() -> void:
    var hud_source := FileAccess.get_file_as_string("res://scripts/Hud.gd")
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")

    var hud_contract := [
        ["func show_boss(", "Boss HUD show contract is missing"],
        ["func set_boss_health(", "Boss HUD health update contract is missing"],
        ["PHASE II // ENRAGED", "Boss phase-II label is missing"],
        ["PHASE III // EXECUTE", "Boss phase-III label is missing"],
        ["boss_hp_bar", "Boss health bar is missing"],
        ["boss_hp_fill_style", "Boss health fill style is missing"],
        ["func pulse_boss_phase(", "Boss phase transition pulse is missing"],
        ["boss_phase_tween", "Boss phase transition tween is missing"],
        ["BossHealthBar", "Boss health bar node identity is missing"],
        ["add_theme_stylebox_override(\"fill\"", "Boss health bar fill theme is missing"],
        ["ratio <= 0.30", "Boss phase color threshold is missing"]
    ]
    for requirement in hud_contract:
        if not _require(hud_source.contains(String(requirement[0])), String(requirement[1])):
            return

    if not _require(enemy_source.contains("signal health_changed"), "Boss enemy health signal is missing"):
        return
    if not _require(enemy_source.contains("health_changed.emit(max(0.0, health), max_health)"), "Boss enemy health emission contract is missing"):
        return
    if not _require(main_source.contains("enemy.health_changed.connect(_on_boss_health_changed)"), "Boss health is not connected to HUD"):
        return
    if not _require(main_source.contains("hud.show_boss("), "Boss spawn does not reveal HUD"):
        return

    print("Godot boss HUD identity validation passed")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
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

    if boss.boss_aura_root == null or boss.boss_aura_inner_material == null or boss.boss_aura_outer_material == null:
        push_error("Boss premium threat aura did not initialize")
        quit(1)
        return
    if boss.boss_aura_inner == null or boss.boss_aura_outer == null:
        push_error("Boss threat aura ring layers are missing")
        quit(1)
        return
    if absf(boss.boss_aura_inner.rotation_degrees.x) > 0.01 or absf(boss.boss_aura_outer.rotation_degrees.x) > 0.01:
        push_error("Boss threat aura rings must remain aligned with the arena floor")
        quit(1)
        return
    var phase1_aura_color: Color = boss.boss_aura_inner_material.emission

    var phase_events: Array[int] = []
    boss.boss_phase_changed.connect(func(phase: int, _at: Vector3) -> void: phase_events.append(phase))

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
    if phase_events != [2]:
        push_error("Boss phase II transition event was not emitted exactly once")
        quit(1)
        return
    if boss._boss_aftershock_count() != 1:
        push_error("Boss phase II did not unlock one telegraphed aftershock")
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
    if phase_events != [2, 3]:
        push_error("Boss phase III transition event was not emitted exactly once")
        quit(1)
        return
    if boss._boss_aftershock_count() != 2:
        push_error("Boss phase III did not escalate to two telegraphed aftershocks")
        quit(1)
        return

    var phase3_aura_color: Color = boss.boss_aura_inner_material.emission
    if phase3_aura_color.is_equal_approx(phase1_aura_color):
        push_error("Boss phase escalation did not recolor threat aura")
        quit(1)
        return
    boss._update_boss_presence(0.16)
    if boss.boss_aura_root.rotation.y <= 0.0:
        push_error("Boss threat aura is not rotating")
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
    var required := [
        ["BOSS_REVEAL_DURATION := 1.15", "Boss reveal duration contract regressed"],
        ["BOSS_REVEAL_FOCUS := 0.58", "Boss reveal focus contract regressed"],
        ["BOSS_REVEAL_FOV_DELTA := 5.5", "Boss reveal FOV delta contract regressed"],
        ["CAMERA_BASE_HEIGHT := 12.8", "Base camera height contract regressed"],
        ["CAMERA_BASE_TRAIL := 9.15", "Base camera trail contract regressed"],
        ["CAMERA_BASE_FOV := 46.0", "Base camera FOV contract regressed"],
        ["CAMERA_REVEAL_HEIGHT := 14.0", "Boss reveal camera height contract regressed"],
        ["CAMERA_REVEAL_TRAIL := 10.4", "Boss reveal camera trail contract regressed"],
        ["boss_reveal_target = enemy", "Boss spawn no longer arms reveal target"],
        ["target_fov = CAMERA_BASE_FOV + BOSS_REVEAL_FOV_DELTA * envelope", "Boss reveal no longer widens field of view"],
        ["camera.look_at(focus_point, Vector3.UP)", "Camera focus contract is missing"]
    ]
    for item in required:
        if not _require(source.contains(String(item[0])), String(item[1])):
            return

    if not _require(1.15 <= 1.25, "Boss reveal duration exceeds mobile comfort bound"):
        return
    if not _require(5.5 <= 7.0, "Boss reveal FOV delta exceeds mobile comfort bound"):
        return
    if not _require(0.58 >= 0.45 and 0.58 <= 0.68, "Boss reveal focus leaves validated framing range"):
        return
    if not _require(46.0 >= 44.0 and 46.0 <= 49.0, "Base camera FOV leaves readability range"):
        return

    print("godot boss reveal camera validation passed")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
```

## File: tests/combat_audio_feedback_test.gd
```
extends SceneTree

func _init() -> void:
    var profiles := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]
    for profile in profiles:
        var authored := DZCombatAudio.authored_shot_stream(profile)
        var routed := DZCombatAudio.shot_stream(profile)
        assert(authored != null)
        assert(routed == authored)
        assert(routed.get_length() > 0.15)
        assert(routed.get_length() < 0.60)

    var impact_cases := [
        [false, false, false],
        [true, false, false],
        [false, true, false],
        [false, false, true]
    ]
    var impact_lengths := []
    for case in impact_cases:
        var authored := DZCombatAudio.authored_impact_stream(bool(case[0]), bool(case[1]), bool(case[2]))
        var routed := DZCombatAudio.impact_stream(bool(case[0]), bool(case[1]), bool(case[2]))
        assert(authored != null)
        assert(routed == authored)
        assert(routed.get_length() > 0.12)
        assert(routed.get_length() < 0.75)
        impact_lengths.append(routed.get_length())

    assert(impact_lengths[0] < impact_lengths[1])
    assert(impact_lengths[1] < impact_lengths[2])
    assert(impact_lengths[2] < impact_lengths[3])

    var boss := DZCombatAudio.boss_stinger()
    assert(boss != null)
    assert(boss.get_length() >= 2.0)

    var music := DZCombatAudio.run_music_stream()
    assert(music != null)
    assert(music.get_length() >= 11.5)
    if music is AudioStreamWAV:
        assert((music as AudioStreamWAV).loop_mode == AudioStreamWAV.LOOP_FORWARD)

    var pressure_music := DZCombatAudio.pressure_music_stream()
    assert(pressure_music != null)
    assert(pressure_music.get_length() >= 11.5)

    for key in ["level_up", "upgrade_confirm", "pause_toggle", "game_over"]:
        var cue := DZCombatAudio.ui_stream(key)
        assert(cue != null)
        assert(cue.get_length() >= 0.14)

    var player_source := FileAccess.get_file_as_string("res://scripts/Player.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    assert(player_source.contains("for voice_index in range(3)"))
    assert(player_source.contains("shot_audio_voices"))
    assert(main_source.contains("for voice_index in range(4)"))
    assert(main_source.contains("impact_audio_voices"))
    assert(main_source.contains("ui_audio_voices"))
    assert(main_source.contains("PROCESS_MODE_ALWAYS"))
    assert(main_source.contains("RunMusic"))
    assert(main_source.contains("PressureMusic"))
    assert(main_source.contains("_music_pressure_target_db"))
    print("combat audio authored routing test passed")
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
    _assert(DZCombatFeel.impact_fov_pulse(false, false, false) == 0.0,
        "normal impacts must not pulse FOV")
    _assert(DZCombatFeel.impact_fov_pulse(true, false, false) > 0.0,
        "critical impacts must add restrained FOV punch")
    _assert(DZCombatFeel.impact_fov_pulse(true, true, true) <= 1.30,
        "impact FOV pulse comfort bound")
    var light_damage_kick := DZCombatFeel.damage_received_camera_kick(5.0, 100.0)
    var heavy_damage_kick := DZCombatFeel.damage_received_camera_kick(30.0, 100.0)
    _assert(light_damage_kick > 0.0, "received damage must produce camera feedback")
    _assert(heavy_damage_kick > light_damage_kick, "heavier received damage must read stronger")
    _assert(heavy_damage_kick <= 0.115, "received damage kick must remain mobile-safe")
    _assert(DZCombatFeel.damage_received_camera_kick(0.0, 100.0) == 0.0, "zero damage must not kick camera")
    _assert(is_equal_approx(DZCombatFeel.unscaled_delta(0.012, 0.12), 0.10),
        "hit-freeze timing must recover real delta under time scaling")
    _assert(DZCombatFeel.unscaled_delta(0.0, 0.12) == 0.0,
        "zero scaled delta must remain zero")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    _assert(main_source.contains("if hit_stop_enabled:"),
        "combat impact path must respect hit-stop comfort setting")
    _assert(main_source.contains("if camera_shake_enabled:"),
        "combat impact path must respect camera-shake comfort setting")
    _assert(main_source.contains("_spawn_kill_confirmation_fx"),
        "kill impacts must have a distinct world-space confirmation burst")
    _assert(main_source.contains("fx.scale_boost = 2.15 if boss else 1.42"),
        "boss and normal kill bursts must preserve different visual mass")
    _assert(main_source.contains("camera_kick = 0.0"),
        "disabling camera shake must clear active camera kick")
    _assert(main_source.contains("_clear_hit_freeze()"),
        "disabling hit stop must clear active freeze")
    print("Deadline Zero Godot combat-feel profile: OK")
    quit(0)

func _assert(condition: bool, message: String) -> void:
    if condition:
        return
    push_error(message)
    quit(1)
```

## File: tests/combat_ground_mark_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var first := DZCombatGroundMark.spawn_mark(root, Vector3(2.0, 0.8, -3.0))
    if first == null or not first is MeshInstance3D:
        push_error("Kill ground mark did not instantiate as real 3D mesh")
        quit(1)
        return
    var mat := first.material_override as StandardMaterial3D
    if mat == null or mat.albedo_texture != load(DZCombatGroundMark.BLOOD_TEXTURE):
        push_error("Kill ground mark lost project-owned authored blood decal texture")
        quit(1)
        return
    if mat.transparency != BaseMaterial3D.TRANSPARENCY_ALPHA or mat.roughness < 0.85:
        push_error("Kill ground mark must use a restrained alpha-blended rough material")
        quit(1)
        return
    if first.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Ground marks must not cast directional shadows")
        quit(1)
        return
    var first_quad := first.mesh as QuadMesh
    if first_quad == null or first_quad.size != Vector2.ONE:
        push_error("Ground marks must use one lightweight quad")
        quit(1)
        return
    if absf(first.global_position.y - DZCombatGroundMark.GROUND_Y) > 0.0001:
        push_error("Kill marks must be placed on the ground, not at enemy's hit height")
        quit(1)
        return
    if first.fade_tween == null or not first.fade_tween.is_running():
        push_error("Kill ground mark must fade and release its node automatically")
        quit(1)
        return

    var boss_mark := DZCombatGroundMark.spawn_mark(root, Vector3(-2.0, 2.0, 3.0), true)
    var boss_mat := boss_mark.material_override as StandardMaterial3D
    if boss_mat == null or boss_mat.albedo_texture != load(DZCombatGroundMark.SCORCH_TEXTURE):
        push_error("Boss kill must use the authored scorch decal instead of blood")
        quit(1)
        return
    if boss_mark.scale.x < first.scale.x or boss_mark.mesh != first.mesh:
        push_error("Boss scorch must be larger and reuse the same quad mesh")
        quit(1)
        return

    for index in range(DZCombatGroundMark.MAX_ACTIVE + 9):
        var mark := DZCombatGroundMark.spawn_mark(root, Vector3(float(index), 0.0, 0.0))
        if mark == null:
            push_error("Dense-run ground mark emitter failed")
            quit(1)
            return
        if mark.mesh != first.mesh:
            push_error("Ground marks must reuse the single shared quad resource")
            quit(1)
            return
        if mark.get_child_count() != 0:
            push_error("Ground mark has unexpected mesh/light/particle children")
            quit(1)
            return
    await process_frame

    var marks := get_nodes_in_group("combat_ground_marks")
    if marks.size() != DZCombatGroundMark.MAX_ACTIVE:
        push_error("Kill ground mark cap regressed: %d / %d" % [marks.size(), DZCombatGroundMark.MAX_ACTIVE])
        quit(1)
        return

    if DZCombatGroundMark.spawn_mark(null, Vector3.ZERO) != null:
        push_error("Ground marks must reject missing parent")
        quit(1)
        return

    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if not source.contains("DZCombatGroundMark.spawn_mark(self, at, xp_value >= 30)"):
        push_error("Enemy death callback lost world-space kill mark integration")
        quit(1)
        return

    print("Deadline Zero authored mobile ground marks: OK (%d max visible)" % marks.size())
    quit(0)
```

## File: tests/damage_number_budget_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var enemy := DZEnemy.new()
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    for index in range(DZEnemy.MAX_DAMAGE_NUMBERS):
        var filler := Label3D.new()
        filler.name = "DamageNumberBudgetFiller_%d" % index
        filler.add_to_group("damage_numbers")
        root.add_child(filler)

    var baseline := get_nodes_in_group("damage_numbers").size()
    if baseline != DZEnemy.MAX_DAMAGE_NUMBERS:
        push_error("Damage number budget fixture did not reach configured cap")
        quit(1)
        return

    enemy._spawn_damage_number(12.0, false, false)
    if get_nodes_in_group("damage_numbers").size() != baseline:
        push_error("Ordinary damage number bypassed mobile clutter budget")
        quit(1)
        return

    enemy._spawn_damage_number(24.0, true, false)
    if get_nodes_in_group("damage_numbers").size() != baseline + 1:
        push_error("Critical damage number was incorrectly dropped at budget cap")
        quit(1)
        return

    var readable_critical: Label3D
    for node in get_nodes_in_group("damage_numbers"):
        if node is Label3D and str(node.name).begins_with("DamageNumber_"):
            readable_critical = node as Label3D
            break
    if readable_critical == null or readable_critical.pixel_size < 0.0067 or readable_critical.font_size < 44:
        push_error("Critical damage number is below phone-scale readability contract")
        quit(1)
        return

    enemy._spawn_damage_number(60.0, false, true)
    if get_nodes_in_group("damage_numbers").size() != baseline + 2:
        push_error("Kill damage number was incorrectly dropped at budget cap")
        quit(1)
        return

    print("Deadline Zero damage-number budget: OK")
    quit(0)
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
    assert(source.contains("_schedule_boss_aftershocks"))
    assert(source.contains("BossAftershockWarning"))
    assert(source.contains("_resolve_boss_aftershock"))
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
        "hit_flash_material": "Hit reaction must include a material flash without dynamic lights",
        "separation_radius := 2.05": "Heavy melee bodies need a wider anti-stack separation radius",
        "separation_radius := 2.05 if kind in": "Enemy movement must retain archetype-aware separation",
        "_melee_standoff_distance": "Close melee pressure must preserve a player-readable standoff envelope",
        "_contact_attack_range": "Melee enemies must remain dangerous from the standoff envelope",
        "movement_speed_scale": "Close melee correction must settle rather than jitter at full chase speed"
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

    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := Node3D.new()
    root.add_child(target)
    var first := DZEnemy.new()
    first.configure("shambler", 1.0, target)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    var second := DZEnemy.new()
    second.configure("shambler", 1.0, target)
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    await process_frame
    if first.hit_flash_visual == null or second.hit_flash_visual == null:
        push_error("Enemy hit-flash visual is missing")
        quit(1)
        return
    if first.hit_flash_visual.mesh != second.hit_flash_visual.mesh:
        push_error("Same-scale enemy hit flashes must reuse one mesh resource")
        quit(1)
        return
    if first.hit_flash_visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Enemy hit-flash geometry must not cast dynamic shadows")
        quit(1)
        return
    if first.hit_flash_material == second.hit_flash_material:
        push_error("Enemy hit-flash materials must stay instance-local for independent animation")
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
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
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

    var core := projectile.get_node_or_null("HarrierBoltCore") as MeshInstance3D
    var halo := projectile.get_node_or_null("HarrierBoltHalo") as MeshInstance3D
    if core == null or halo == null:
        push_error("Harrier bolt core/halo visual is missing")
        quit(1)
        return
    for mesh_instance in [core, halo, trail]:
        if (mesh_instance as MeshInstance3D).cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Harrier bolt visuals must not cast mobile-costly shadows")
            quit(1)
            return
    var core_mesh := core.mesh as SphereMesh
    var halo_mesh := halo.mesh as SphereMesh
    if core_mesh == null or core_mesh.radial_segments > 10 or core_mesh.rings > 5:
        push_error("Harrier bolt core geometry budget regressed")
        quit(1)
        return
    if halo_mesh == null or halo_mesh.radial_segments > 10 or halo_mesh.rings > 5:
        push_error("Harrier bolt halo geometry budget regressed")
        quit(1)
        return

    var duplicate := PROJECTILE_SCRIPT.new()
    duplicate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(duplicate)
    await process_frame
    var duplicate_core := duplicate.get_node_or_null("HarrierBoltCore") as MeshInstance3D
    var duplicate_halo := duplicate.get_node_or_null("HarrierBoltHalo") as MeshInstance3D
    var duplicate_trail := duplicate.get_node_or_null("HarrierBoltTrail") as MeshInstance3D
    if duplicate_core == null or duplicate_halo == null or duplicate_trail == null:
        push_error("Duplicate harrier bolt visual is incomplete")
        quit(1)
        return
    if duplicate_core.mesh != core.mesh or duplicate_halo.mesh != halo.mesh or duplicate_trail.mesh != trail.mesh:
        push_error("Harrier bolt instances must reuse shared mesh resources")
        quit(1)
        return
    if duplicate_core.material_override != core.material_override or duplicate_halo.material_override != halo.material_override or duplicate_trail.material_override != trail.material_override:
        push_error("Harrier bolt instances must reuse shared materials")
        quit(1)
        return

    print("Deadline Zero enemy projectile visual: OK")
    quit(0)
```

## File: tests/enemy_shadow_budget_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var normal_meshes := 0
    var hero_body_casters := 0
    for enemy_kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.kind = enemy_kind
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        root.add_child(enemy)
        await process_frame

        var body := enemy.get_node_or_null("Visual") as Node3D
        var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
        var hit_flash := enemy.get_node_or_null("HitFlash") as MeshInstance3D
        if body == null or shadow == null or hit_flash == null:
            push_error("Shadow budget fixture missing authored body/contact shadow/hit flash: %s" % enemy_kind)
            quit(1)
            return
        if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Contact shadow must never cast into the directional shadow map")
            quit(1)
            return
        if hit_flash.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Hit flash must never cast a dynamic shadow")
            quit(1)
            return

        var body_mesh_count := 0
        var body_casters := 0
        for node in enemy.find_children("*", "MeshInstance3D", true, false):
            var mesh := node as MeshInstance3D
            if mesh == null:
                continue
            var is_body := mesh == body or body.is_ancestor_of(mesh)
            if is_body:
                body_mesh_count += 1
                if mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    body_casters += 1
            elif mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("FX/signature mesh casts dynamic shadows: %s/%s" % [enemy_kind, mesh.name])
                quit(1)
                return

        if body_mesh_count == 0:
            push_error("Enemy must retain real 3D authored body geometry: %s" % enemy_kind)
            quit(1)
            return
        if enemy_kind in ["boss", "elite"]:
            if body_casters == 0:
                push_error("Hero threat has no dynamic body shadow: %s" % enemy_kind)
                quit(1)
                return
            hero_body_casters += body_casters
        else:
            if body_casters != 0:
                push_error("Swarm body is still a dynamic shadow caster: %s" % enemy_kind)
                quit(1)
                return
            normal_meshes += body_mesh_count

        enemy.queue_free()
        await process_frame

    if normal_meshes < 6 or hero_body_casters < 2:
        push_error("Shadow-budget regression fixture did not exercise body meshes")
        quit(1)
        return
    print("Deadline Zero swarm shadow budget: OK, shadow-free regular body meshes=%d, elite/boss body casters=%d" % [normal_meshes, hero_body_casters])
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
    var rim_samples := {}
    var shared_grade_shader: Shader
    for palette_kind in ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var visual := DZAssetLibrary.enemy(palette_kind)
        if visual == null:
            push_error("Missing authored visual for palette kind %s" % palette_kind)
            quit(1)
            return
        var mesh_nodes: Array[MeshInstance3D] = []
        if visual is MeshInstance3D:
            mesh_nodes.append(visual as MeshInstance3D)
        for node in visual.find_children("*", "MeshInstance3D", true, false):
            mesh_nodes.append(node as MeshInstance3D)
        if mesh_nodes.is_empty():
            push_error("Enemy authored visual has no meshes for %s" % palette_kind)
            quit(1)
            return

        var palette_material: ShaderMaterial
        var textured_surfaces := 0
        for mesh_instance in mesh_nodes:
            if mesh_instance == null or mesh_instance.mesh == null:
                continue
            if mesh_instance.material_override != null:
                push_error("Enemy grading must not flatten authored multi-surface materials for %s" % palette_kind)
                quit(1)
                return
            for surface_index in range(mesh_instance.mesh.get_surface_count()):
                var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                if source == null or source.albedo_texture == null:
                    continue
                textured_surfaces += 1
                var graded := mesh_instance.get_surface_override_material(surface_index)
                if not graded is ShaderMaterial:
                    push_error("Enemy surface grading shader missing for %s surface %d" % [palette_kind, surface_index])
                    quit(1)
                    return
                var material := graded as ShaderMaterial
                if material.get_shader_parameter("albedo_tex") != source.albedo_texture:
                    push_error("Enemy grading replaced an authored atlas for %s surface %d" % [palette_kind, surface_index])
                    quit(1)
                    return
                if shared_grade_shader == null:
                    shared_grade_shader = material.shader
                elif material.shader != shared_grade_shader:
                    push_error("Enemy grading must reuse one compiled shader across all authored surfaces")
                    quit(1)
                    return
                var highlight_floor := float(material.get_shader_parameter("highlight_floor"))
                if highlight_floor > 0.50:
                    push_error("Enemy authored highlights are not compressed enough for %s" % palette_kind)
                    quit(1)
                    return
                if palette_material == null:
                    palette_material = material
        if textured_surfaces == 0 or palette_material == null:
            push_error("Enemy palette grading found no authored textured surfaces for %s" % palette_kind)
            quit(1)
            return
        palette_samples[palette_kind] = palette_material.get_shader_parameter("body_tint") as Color
        rim_samples[palette_kind] = float(palette_material.get_shader_parameter("rim_strength"))
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

    if float(rim_samples.get("boss", 0.0)) <= float(rim_samples.get("elite", 0.0)):
        push_error("Boss shader rim must dominate elite silhouette")
        quit(1)
        return
    if float(rim_samples.get("elite", 0.0)) <= float(rim_samples.get("runner", 0.0)):
        push_error("Elite shader rim must remain stronger than runner")
        quit(1)
        return

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "charger": ["SignatureBeacon"],
        "harrier": ["SignatureBeacon"],
        "regenerator": ["SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "BruteEdgeL", "BruteEdgeR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossWingL", "BossWingR", "BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
    }

    var shared_shadow_material: Material
    var shadow_radii := {}
    for kind in expected.keys():
        var enemy := ENEMY_SCRIPT.new()
        enemy.kind = kind
        root.add_child(enemy)
        await process_frame
        for node_name in expected[kind]:
            if enemy.get_node_or_null(node_name) == null:
                push_error("Missing %s signature node %s" % [kind, node_name])
                quit(1)
                return
        var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
        var shadow_mesh := shadow.mesh as QuadMesh if shadow != null else null
        if enemy.spawn_reveal_tween == null:
            push_error("Enemy spawn did not initialize premium reveal motion for %s" % kind)
            quit(1)
            return
        if shadow == null or shadow_mesh == null:
            push_error("Missing mobile-safe contact shadow for %s" % kind)
            quit(1)
            return
        if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Contact shadow must not cast dynamic shadows for %s" % kind)
            quit(1)
            return
        if shadow_mesh.size.x < 0.69 or shadow_mesh.size.y != shadow_mesh.size.x:
            push_error("Contact shadow lost its low-poly radial ground footprint for %s" % kind)
            quit(1)
            return
        if absf(shadow.rotation_degrees.x + 90.0) > 0.01:
            push_error("Contact shadow quad no longer faces the arena floor for %s" % kind)
            quit(1)
            return
        var shadow_material := shadow.material_override
        if shadow_material == null or not shadow_material is ShaderMaterial:
            push_error("Contact shadow must use shared soft radial shader for %s" % kind)
            quit(1)
            return
        var soft_material := shadow_material as ShaderMaterial
        if soft_material.shader == null or not soft_material.shader.code.contains("smoothstep") or not soft_material.shader.code.contains("ALPHA = feather * feather"):
            push_error("Contact shadow lost smooth radial falloff for %s" % kind)
            quit(1)
            return
        var shadow_opacity := float(soft_material.get_shader_parameter("shadow_opacity"))
        if shadow_opacity > 0.40 or shadow_opacity < 0.20:
            push_error("Contact shadow opacity escaped subtle mobile visibility budget for %s" % kind)
            quit(1)
            return
        if shared_shadow_material == null:
            shared_shadow_material = shadow_material
        elif shadow_material != shared_shadow_material:
            push_error("Enemy contact shadows must share one material instance")
            quit(1)
            return
        shadow_radii[kind] = shadow_mesh.size.x * 0.5
        if kind == "runner":
            var blade := enemy.get_node_or_null("RunnerBladeL") as MeshInstance3D
            var blade_mesh := blade.mesh as BoxMesh if blade != null else null
            if blade_mesh == null or blade_mesh.size.z < 0.35 or absf(blade.position.x) < 0.40:
                push_error("Runner signature must remain wide and readable in top-down projection")
                quit(1)
                return
        if kind == "brute":
            var plate := enemy.get_node_or_null("BrutePlateL") as MeshInstance3D
            var edge := enemy.get_node_or_null("BruteEdgeL") as MeshInstance3D
            var plate_material := plate.material_override as BaseMaterial3D if plate != null else null
            var edge_material := edge.material_override as BaseMaterial3D if edge != null else null
            if plate_material == null or plate_material.emission_enabled:
                push_error("Brute armor plate must remain dark/non-emissive")
                quit(1)
                return
            if edge_material == null or not edge_material.emission_enabled:
                push_error("Brute identity must move to a narrow emissive edge")
                quit(1)
                return
        if kind == "boss":
            var wing := enemy.get_node_or_null("BossWingL") as MeshInstance3D
            var wing_mesh := wing.mesh as BoxMesh if wing != null else null
            var boss_visual := enemy.get_node_or_null("Visual") as Node3D
            if wing_mesh == null or wing_mesh.size.z < 0.68 or absf(wing.position.x) < 0.80:
                push_error("Boss signature must remain broad and dominant in top-down projection")
                quit(1)
                return
            if boss_visual == null or boss_visual.scale.x < 1.78:
                push_error("Boss authored body lost premium screen-space mass")
                quit(1)
                return
            var aura := enemy.get_node_or_null("BossThreatAura") as Node3D
            var aura_inner := aura.get_node_or_null("BossAuraInner") as MeshInstance3D if aura != null else null
            var aura_outer := aura.get_node_or_null("BossAuraOuter") as MeshInstance3D if aura != null else null
            if aura == null or aura_inner == null or aura_outer == null:
                push_error("Boss threat aura lost layered ground presence")
                quit(1)
                return
            if aura_inner.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or aura_outer.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Boss threat aura must remain shadow-free")
                quit(1)
                return
            if aura.get_child_count() < 6:
                push_error("Boss threat aura lost directional ticks")
                quit(1)
                return
        enemy.queue_free()

    if float(shadow_radii.get("boss", 0.0)) < 0.86:
        push_error("Boss contact shadow is too small for its premium ground mass")
        quit(1)
        return
    if float(shadow_radii.get("boss", 0.0)) <= float(shadow_radii.get("brute", 0.0)):
        push_error("Boss contact shadow must preserve larger ground mass than brute")
        quit(1)
        return
    if float(shadow_radii.get("brute", 0.0)) <= float(shadow_radii.get("runner", 0.0)):
        push_error("Heavy enemy contact shadow must read broader than runner")
        quit(1)
        return

    var runner_a := ENEMY_SCRIPT.new()
    runner_a.kind = "runner"
    root.add_child(runner_a)
    var runner_b := ENEMY_SCRIPT.new()
    runner_b.kind = "runner"
    root.add_child(runner_b)
    await process_frame

    var runner_shadow_a := runner_a.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    var runner_shadow_b := runner_b.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    if runner_shadow_a == null or runner_shadow_b == null:
        push_error("Runner contact shadow reuse fixture is incomplete")
        quit(1)
        return
    if runner_shadow_a.mesh != runner_shadow_b.mesh:
        push_error("Same-kind enemy contact shadows must reuse one mesh resource")
        quit(1)
        return
    if runner_shadow_a.material_override != runner_shadow_b.material_override:
        push_error("Same-kind enemy contact shadows must reuse one material resource")
        quit(1)
        return

    var runner_blade_a := runner_a.get_node_or_null("RunnerBladeL") as MeshInstance3D
    var runner_blade_b := runner_b.get_node_or_null("RunnerBladeL") as MeshInstance3D
    if runner_blade_a == null or runner_blade_b == null or runner_blade_a.material_override != runner_blade_b.material_override:
        push_error("Same-kind runner signatures must reuse emissive material resources")
        quit(1)
        return

    if runner_blade_a.mesh != runner_blade_b.mesh:
        push_error("Same-kind runner signatures must reuse mesh resources")
        quit(1)
        return

    var brute_a := ENEMY_SCRIPT.new()
    brute_a.kind = "brute"
    root.add_child(brute_a)
    var brute_b := ENEMY_SCRIPT.new()
    brute_b.kind = "brute"
    root.add_child(brute_b)
    await process_frame
    var brute_plate_a := brute_a.get_node_or_null("BrutePlateL") as MeshInstance3D
    var brute_plate_b := brute_b.get_node_or_null("BrutePlateL") as MeshInstance3D
    if brute_plate_a == null or brute_plate_b == null or brute_plate_a.material_override != brute_plate_b.material_override:
        push_error("Brute armor plates must reuse one dark armor material")
        quit(1)
        return

    if brute_plate_a.mesh != brute_plate_b.mesh:
        push_error("Brute armor plates must reuse one mesh resource")
        quit(1)
        return

    var source := FileAccess.get_file_as_string("res://scripts/AssetLibrary.gd")
    if not source.contains("uniform float rim_strength = 0.11") or not source.contains("EMISSION = body_tint.rgb * rim"):
        push_error("Enemy shared grading shader lost subtle silhouette rim lighting")
        quit(1)
        return

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

    if not floor is MeshInstance3D:
        push_error("Quarantine floor is not a mesh")
        quit(1)
        return
    var floor_mesh := floor as MeshInstance3D
    if not floor_mesh.material_override is ShaderMaterial:
        push_error("Quarantine floor lost its procedural industrial material")
        quit(1)
        return
    var floor_shader := (floor_mesh.material_override as ShaderMaterial).shader
    if floor_shader == null or not floor_shader.code.contains("panel_variation") or not floor_shader.code.contains("micro_variation") or not floor_shader.code.contains("macro_variation") or not floor_shader.code.contains("perimeter_heat"):
        push_error("Quarantine floor procedural surface hierarchy regressed")
        quit(1)
        return
    var world_environment := env as WorldEnvironment
    if world_environment.environment == null or not world_environment.environment.adjustment_enabled:
        push_error("Quarantine environment lost lightweight cinematic color grading")
        quit(1)
        return
    if world_environment.environment.adjustment_contrast < 1.06 or world_environment.environment.adjustment_saturation < 1.04:
        push_error("Quarantine environment color grading is too flat for premium combat readability")
        quit(1)
        return

    if floor_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Broad quarantine floor must not waste shadow-caster budget")
        quit(1)
        return

    var ambient_motes := scene.get_node_or_null("AmbientMotes") as GPUParticles3D
    if ambient_motes == null:
        push_error("Quarantine arena lost subtle atmospheric depth motes")
        quit(1)
        return
    if ambient_motes.amount > 32 or ambient_motes.lifetime > 6.0:
        push_error("Ambient motes exceeded mobile-safe particle budget")
        quit(1)
        return
    if ambient_motes.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Ambient motes must never cast dynamic shadows")
        quit(1)
        return
    if not ambient_motes.draw_pass_1 is QuadMesh:
        push_error("Ambient motes must remain one lightweight billboard draw pass")
        quit(1)
        return

    var barrier_count := 0
    var lane_count := 0
    var arena_boundary_count := 0
    var arena_boundary_shadow_violations := 0
    var beacon_count := 0
    var floor_plate_count := 0
    var floor_plate_accent_count := 0
    var dark_floor_plate_count := 0
    var floor_seam_count := 0
    var floor_wear_count := 0
    var floor_chip_count := 0
    var containment_ring_count := 0
    var street_light_count := 0
    var street_light_pool_count := 0
    var graded_street_light_meshes := 0
    var graded_street_light_optics := 0
    var offscreen_authored_street_light_count := 0
    var quarantine_mast_count := 0
    var quarantine_mast_lamp_count := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    var service_pylon_count := 0
    var service_grate_count := 0
    var inspection_panel_count := 0
    var inspection_service_stripe_count := 0
    var service_grate_mesh_count := 0
    var oversized_barrier_count := 0
    var perimeter_bulkhead_count := 0
    var bulkhead_hazard_stripe_count := 0
    var bulkhead_signal_count := 0
    var arena_light_pool_count := 0
    var arena_light_pool_violations := 0
    var authored_floor_decal_count := 0
    var authored_floor_decal_violations := 0
    var authored_floor_decal_materials := {}
    for child in scene.get_children():
        if child.name.begins_with("AuthoredBarrier_"):
            barrier_count += 1
            if child.scale.x > 0.40 or Vector2(child.position.x, child.position.z).length() < 24.0:
                oversized_barrier_count += 1
            var barrier_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                barrier_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                barrier_meshes.append(mesh_node as MeshInstance3D)
            for mesh_instance in barrier_meshes:
                if mesh_instance == null or mesh_instance.mesh == null or mesh_instance.material_override != null:
                    continue
                for surface_index in range(mesh_instance.mesh.get_surface_count()):
                    var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    var material := mesh_instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if source != null and source.albedo_texture != null and material != null:
                        if material.albedo_texture == source.albedo_texture and material.roughness >= 0.84 and material.metallic >= 0.34:
                            graded_barrier_meshes += 1
            hazard_strip_count += int(child.find_child("BarrierHazardFront", true, false) != null)
            hazard_strip_count += int(child.find_child("BarrierHazardRear", true, false) != null)
        elif child.name.begins_with("PerimeterBulkhead_"):
            perimeter_bulkhead_count += 1
            bulkhead_hazard_stripe_count += child.find_children("HazardMarker_*", "MeshInstance3D", true, false).size()
            bulkhead_signal_count += int(child.find_children("*", "MeshInstance3D", true, false).size() >= 10)
        elif child.name.begins_with("ContainmentLane_"):
            lane_count += 1
        elif child.name.begins_with("ArenaBoundary_"):
            arena_boundary_count += 1
            if child is MeshInstance3D and (child as MeshInstance3D).cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                arena_boundary_shadow_violations += 1
        elif child.name.begins_with("ArenaLightPool_"):
            arena_light_pool_count += 1
            if not child is MeshInstance3D:
                arena_light_pool_violations += 1
            else:
                var pool_mesh := child as MeshInstance3D
                if pool_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or not pool_mesh.material_override is ShaderMaterial:
                    arena_light_pool_violations += 1
                elif not (pool_mesh.material_override as ShaderMaterial).shader.code.contains("blend_add"):
                    arena_light_pool_violations += 1
        elif child.name.begins_with("AuthoredFloorDecal_"):
            authored_floor_decal_count += 1
            if not child is MeshInstance3D:
                authored_floor_decal_violations += 1
            else:
                var decal_mesh := child as MeshInstance3D
                if not decal_mesh.mesh is QuadMesh:
                    authored_floor_decal_violations += 1
                if decal_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    authored_floor_decal_violations += 1
                if not decal_mesh.material_override is StandardMaterial3D:
                    authored_floor_decal_violations += 1
                else:
                    var decal_material := decal_mesh.material_override as StandardMaterial3D
                    if decal_material.albedo_texture == null or decal_material.transparency == BaseMaterial3D.TRANSPARENCY_DISABLED:
                        authored_floor_decal_violations += 1
                    authored_floor_decal_materials[decal_material.get_instance_id()] = true
                if decal_mesh.position.y < 0.025 or decal_mesh.position.y > 0.045:
                    authored_floor_decal_violations += 1
        elif child.name.begins_with("PerimeterBeacon_"):
            beacon_count += 1
        elif child.name.begins_with("FloorPlate_"):
            floor_plate_count += 1
            if child is MeshInstance3D and (child as MeshInstance3D).material_override is StandardMaterial3D:
                var plate_material := (child as MeshInstance3D).material_override as StandardMaterial3D
                if plate_material.albedo_color.get_luminance() < 0.08 and plate_material.roughness >= 0.88:
                    dark_floor_plate_count += 1
            floor_plate_accent_count += child.find_children("FloorPlateAccent_*", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("FloorSeam_"):
            floor_seam_count += 1
        elif child.name.begins_with("ServiceGrate_"):
            service_grate_count += 1
            service_grate_mesh_count += child.find_children("*", "MeshInstance3D", true, false).size()
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
        elif child.name.begins_with("QuarantineMast_"):
            quarantine_mast_count += 1
            quarantine_mast_lamp_count += child.find_children("Lamp", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("AuthoredStreetLight_"):
            street_light_count += 1
            if Vector2(child.position.x, child.position.z).length() > 32.0 and child.scale.x <= 0.40:
                offscreen_authored_street_light_count += 1
            var light_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                light_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                var mesh_instance := mesh_node as MeshInstance3D
                if mesh_instance != null and mesh_instance.name != "StreetLightCore":
                    light_meshes.append(mesh_instance)
            for mesh_instance in light_meshes:
                if mesh_instance.mesh == null or mesh_instance.material_override != null:
                    continue
                for surface_index in range(mesh_instance.mesh.get_surface_count()):
                    var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    var material := mesh_instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if source == null or material == null:
                        continue
                    if source.albedo_texture != null and source.albedo_texture == material.albedo_texture:
                        if material.roughness >= 0.78 and material.metallic >= 0.46:
                            graded_street_light_meshes += 1
                    elif source.albedo_texture == null and material.emission_enabled and material.emission_energy_multiplier > 0.8:
                        graded_street_light_optics += 1
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
    if perimeter_bulkhead_count != 10 or bulkhead_hazard_stripe_count < 40 or bulkhead_signal_count != 10:
        push_error("Expected 10 authored GLB bulkheads with readable hazard/details, got %d bulkheads / %d hazard markers / %d detailed shells" % [perimeter_bulkhead_count, bulkhead_hazard_stripe_count, bulkhead_signal_count])
        quit(1)
        return
    if lane_count < 40:
        push_error("Expected structured containment lanes, got %d" % lane_count)
        quit(1)
        return
    if arena_boundary_count != 44 or arena_boundary_shadow_violations != 0:
        push_error("Expected 44 shadow-free arena boundary markers, got %d with %d shadow violations" % [arena_boundary_count, arena_boundary_shadow_violations])
        quit(1)
        return
    if arena_light_pool_count != 6 or arena_light_pool_violations != 0:
        push_error("Expected 6 mobile-safe additive arena light pools, got %d with %d violations" % [arena_light_pool_count, arena_light_pool_violations])
        quit(1)
        return
    if authored_floor_decal_count != 12 or authored_floor_decal_violations != 0:
        push_error("Expected 12 valid project-owned floor decals, got %d with %d violations" % [authored_floor_decal_count, authored_floor_decal_violations])
        quit(1)
        return
    if authored_floor_decal_materials.size() != 3:
        push_error("Authored floor decals must reuse exactly 3 shared materials, got %d" % authored_floor_decal_materials.size())
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
    if dark_floor_plate_count != floor_plate_count:
        push_error("All floor plates must remain dark under combat lighting, got %d/%d" % [dark_floor_plate_count, floor_plate_count])
        quit(1)
        return
    if floor_plate_accent_count != 3:
        push_error("Expected exactly 3 restrained floor-plate accents, got %d" % floor_plate_accent_count)
        quit(1)
        return
    if floor_seam_count < 8:
        push_error("Expected industrial floor seam structure, got %d" % floor_seam_count)
        quit(1)
        return
    if service_grate_count != 4 or service_grate_mesh_count < 40:
        push_error("Expected 4 authored GLB service grates with detailed mesh hierarchy, got %d grates / %d mesh nodes" % [service_grate_count, service_grate_mesh_count])
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
    if offscreen_authored_street_light_count != 4:
        push_error("Authored street lights must stay outside normal combat framing, got %d/4 compliant" % offscreen_authored_street_light_count)
        quit(1)
        return
    if quarantine_mast_count != 4 or quarantine_mast_lamp_count != 4:
        push_error("Expected 4 compact visible quarantine masts with emissive lamps, got %d/%d" % [quarantine_mast_count, quarantine_mast_lamp_count])
        quit(1)
        return
    if graded_street_light_meshes < 4 or graded_street_light_optics < 4:
        push_error("Authored street lights must retain textured steel plus emissive optics, got %d steel / %d optics" % [graded_street_light_meshes, graded_street_light_optics])
        quit(1)
        return
    if graded_barrier_meshes < 12:
        push_error("Authored barriers must preserve their textured industrial surfaces, got %d graded meshes" % graded_barrier_meshes)
        quit(1)
        return
    if hazard_strip_count < 24:
        push_error("Authored barriers must expose hazard signatures, got %d strips" % hazard_strip_count)
        quit(1)
        return
    if oversized_barrier_count != 0:
        push_error("Authored barriers must stay compact beyond the active combat frame, got %d violations" % oversized_barrier_count)
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
    var camera_look: Vector3 = main._camera_motion_look_ahead(Vector3(8.0, 0.0, 0.0))
    if camera_look.length() <= 0.2 or camera_look.length() > main.CAMERA_LOOK_AHEAD_MAX + 0.001:
        push_error("Camera movement look-ahead is outside premium framing budget")
        quit(1)
        return
    if camera_look.x <= 0.0 or absf(camera_look.z) > 0.001:
        push_error("Camera movement look-ahead lost travel direction")
        quit(1)
        return

    var perf_snapshot: Dictionary = main._performance_snapshot()
    for key in ["fps", "memory_bytes", "enemies", "projectiles", "hostile_projectiles", "xp_orbs", "elapsed"]:
        if not perf_snapshot.has(key):
            push_error("Debug performance snapshot is missing key: %s" % key)
            quit(1)
            return
    if int(perf_snapshot["memory_bytes"]) < 0 or int(perf_snapshot["enemies"]) < 0:
        push_error("Debug performance snapshot returned invalid counters")
        quit(1)
        return
    # Auto-aim may rotate the survivor before the test starts. Probe velocity in
    # the survivor's local frame rather than assuming world +X is always strafe.
    main.player.velocity = main.player.global_transform.basis * Vector3(main.player.move_speed, 0.0, 0.0)
    var strafe_lean: Vector2 = main.player._movement_lean_target()
    if strafe_lean.y >= -0.01 or absf(strafe_lean.y) > deg_to_rad(main.player.MOVE_LEAN_ROLL_DEGREES + 0.1):
        push_error("Survivor strafe lean lost bounded directional response")
        quit(1)
        return
    main.player.velocity = main.player.global_transform.basis * Vector3(0.0, 0.0, -main.player.move_speed)
    var forward_lean: Vector2 = main.player._movement_lean_target()
    if forward_lean.x <= 0.005 or absf(forward_lean.x) > deg_to_rad(main.player.MOVE_LEAN_PITCH_DEGREES + 0.1):
        push_error("Survivor forward lean lost bounded movement response")
        quit(1)
        return

    main.player.velocity = Vector3.ZERO
    var acceleration_step: Vector3 = main.player._smoothed_movement_velocity(Vector2.RIGHT, 1.0 / 60.0)
    if acceleration_step.x <= 0.0 or acceleration_step.x >= main.player.move_speed:
        push_error("Player movement lost short premium acceleration ramp")
        quit(1)
        return
    main.player.velocity = Vector3(main.player.move_speed, 0.0, 0.0)
    var braking_step: Vector3 = main.player._smoothed_movement_velocity(Vector2.ZERO, 1.0 / 60.0)
    if braking_step.x <= 0.0 or braking_step.x >= main.player.move_speed:
        push_error("Player movement braking no longer decelerates smoothly")
        quit(1)
        return
    main.player.velocity = Vector3(main.player.move_speed, 0.0, 0.0)
    var turn_step: Vector3 = main.player._smoothed_movement_velocity(Vector2.LEFT, 1.0 / 60.0)
    if turn_step.x >= braking_step.x:
        push_error("Player direction reversal is not more responsive than passive braking")
        quit(1)
        return
    main.player.velocity = Vector3.ZERO
    if main.hud.onboarding_panel == null or not main.hud.onboarding_panel.visible:
        push_error("First-playable did not expose the non-blocking movement hint")
        quit(1)
        return
    if main.hud.onboarding_label == null or not main.hud.onboarding_label.text.contains("AUTO-FIRE"):
        push_error("First-playable movement hint did not explain auto-fire")
        quit(1)
        return
    if main.player.shot_audio_voices.size() != 3:
        push_error("Player weapon audio did not initialize bounded 3-voice polyphony")
        quit(1)
        return
    for voice in main.player.shot_audio_voices:
        if voice.bus != "SFX":
            push_error("Player weapon audio voice escaped the SFX bus")
            quit(1)
            return
    if main.impact_audio_voices.size() != 4:
        push_error("Combat impacts did not initialize bounded 4-voice polyphony")
        quit(1)
        return
    if main.ui_audio_voices.size() != 2:
        push_error("UI/progression audio did not initialize bounded 2-voice polyphony")
        quit(1)
        return
    for voice in main.ui_audio_voices:
        if voice.bus != "SFX" or voice.process_mode != Node.PROCESS_MODE_ALWAYS:
            push_error("UI audio voice must stay on SFX and continue through paused overlays")
            quit(1)
            return
    if main.music_audio == null or main.music_audio.stream == null:
        push_error("Run path did not initialize authored background music")
        quit(1)
        return
    if main.music_audio.stream.get_length() < 11.5 or not main.music_audio.playing:
        push_error("Authored background music did not enter looping playback")
        quit(1)
        return
    if main.music_pressure_audio == null or main.music_pressure_audio.stream == null:
        push_error("Run path did not initialize adaptive pressure music")
        quit(1)
        return
    if main.music_pressure_audio.stream.get_length() < 11.5 or not main.music_pressure_audio.playing:
        push_error("Adaptive pressure music did not enter synchronized playback")
        quit(1)
        return
    main.director_profile["phase"] = "EXTINCTION"
    main._update_music_pressure(1.0)
    if main.music_pressure_audio.volume_db <= main.MUSIC_PRESSURE_BREACH_DB:
        push_error("Adaptive music layer did not rise with run pressure")
        quit(1)
        return
    if main.boss_audio == null or main.boss_audio.stream == null or main.boss_audio.stream.get_length() < 2.0:
        push_error("Run path did not initialize authored boss stinger")
        quit(1)
        return
    if get_nodes_in_group("enemies").size() < 8:
        push_error("Run path did not create initial enemy population")
        quit(1)
        return

    main.camera_kick = 0.0
    main.player.invulnerability = 0.0
    var health_before_camera_feedback: float = main.player.health
    main.player.take_damage(5.0)
    if not is_equal_approx(main.player.health, health_before_camera_feedback - 5.0):
        push_error("First-playable damage probe did not reduce player health")
        quit(1)
        return
    if main.camera_kick <= 0.0 or main.camera_kick > 0.115:
        push_error("Received player damage did not produce bounded camera feedback")
        quit(1)
        return

    main._on_camera_shake_changed(false)
    main.camera_kick = 0.0
    main.player.invulnerability = 0.0
    var no_shake_health: float = main.player.health
    main.player.take_damage(5.0)
    if not is_equal_approx(main.player.health, no_shake_health - 5.0):
        push_error("Camera-shake-off damage probe did not reduce player health")
        quit(1)
        return
    if main.camera_kick > 0.0001:
        push_error("Received damage bypassed disabled camera-shake setting")
        quit(1)
        return
    main._on_camera_shake_changed(true)

    Engine.time_scale = 0.12
    main.hit_freeze_left = 0.020
    main._process(0.020 * 0.12)
    if main.hit_freeze_left > 0.0001 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Hit-freeze duration remained coupled to slowed Engine.time_scale")
        quit(1)
        return

    main._on_hit_stop_changed(false)
    main._on_camera_shake_changed(false)
    main._on_haptics_changed(false)
    main.hit_freeze_left = 0.0
    main.camera_kick = 0.0
    main._on_enemy_impact(Vector3.ZERO, true, true, false)
    if main.hit_freeze_left > 0.0001:
        push_error("Enemy impact bypassed disabled hit-stop setting")
        quit(1)
        return
    if main.camera_kick > 0.0001:
        push_error("Enemy impact bypassed disabled camera-shake setting")
        quit(1)
        return
    main._on_hit_stop_changed(true)
    main._on_camera_shake_changed(true)
    main._on_haptics_changed(true)

    main.player.global_position = Vector3(42.0, 0.0, -44.0)
    main.player.velocity = Vector3(3.0, 0.0, -2.0)
    main.player._constrain_to_arena()
    if absf(main.player.global_position.x) > DZPlayer.ARENA_HALF_EXTENT + 0.001 or absf(main.player.global_position.z) > DZPlayer.ARENA_HALF_EXTENT + 0.001:
        push_error("Player escaped the authored arena floor bounds")
        quit(1)
        return
    if absf(main.player.velocity.x) > 0.001 or absf(main.player.velocity.z) > 0.001:
        push_error("Arena clamp did not cancel outward player velocity")
        quit(1)
        return
    main.player.global_position = Vector3.ZERO

    var clamped_spawn: Vector3 = main._clamp_spawn_position(Vector3(60.0, 0.0, -60.0))
    if absf(clamped_spawn.x) > 34.001 or absf(clamped_spawn.z) > 34.001:
        push_error("Enemy spawn position can escape the authored arena floor")
        quit(1)
        return

    main.player.global_position = Vector3(30.0, 0.0, 30.0)
    main.spawn_rng.seed = 777
    var edge_spawn: Vector3 = main._spawn_position_around_player(18.0)
    var edge_spawn_distance: float = main.player.global_position.distance_to(edge_spawn)
    if absf(edge_spawn.x) > 34.001 or absf(edge_spawn.z) > 34.001:
        push_error("Edge-player spawn escaped arena-safe bounds")
        quit(1)
        return
    if edge_spawn_distance < 11.99 or edge_spawn_distance > 18.01:
        push_error("Arena-safe spawn collapsed the intended enemy spawn distance")
        quit(1)
        return
    main.player.global_position = Vector3.ZERO

    main.player._trigger_weapon_accent_pulse()
    if main.player.weapon_accent_material == null or main.player.weapon_accent_material.emission_energy_multiplier < 4.0:
        push_error("Weapon accent did not pulse with shot feedback")
        quit(1)
        return

    var tactical_rig := main.player.get_node_or_null("TacticalRig") as Node3D
    var tactical_backplate := main.player.get_node_or_null("TacticalRig/TacticalBackplate") as MeshInstance3D
    var tactical_edge_l := main.player.get_node_or_null("TacticalRig/TacticalShoulderEdgeL") as MeshInstance3D
    var tactical_edge_r := main.player.get_node_or_null("TacticalRig/TacticalShoulderEdgeR") as MeshInstance3D
    var weapon_accent := main.player.get_node_or_null("WeaponAccent") as MeshInstance3D
    var authored_rifle := main.player.get_node_or_null("Rifle") as Node3D
    if tactical_rig == null or tactical_backplate == null or tactical_edge_l == null or tactical_edge_r == null or weapon_accent == null or authored_rifle == null:
        push_error("Player production presentation is missing tactical rig/rifle silhouette identity")
        quit(1)
        return
    if tactical_edge_l.material_override != tactical_edge_r.material_override:
        push_error("Player shoulder silhouette accents must share the same emissive material")
        quit(1)
        return
    if tactical_edge_l.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or tactical_edge_r.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Player emissive silhouette accents must not cast mobile-costly shadows")
        quit(1)
        return

    main.hud.show_touch_stick(Vector2(4.0, 716.0))
    var edge_pos: Vector2 = main.hud.touch_stick_root.position
    var edge_size: Vector2 = main.hud.touch_stick_root.size
    var viewport_size: Vector2 = main.get_viewport().get_visible_rect().size
    if edge_pos.x < 7.5 or edge_pos.y < 7.5:
        push_error("Touch stick can clip beyond top/left phone bounds")
        quit(1)
        return
    if edge_pos.x + edge_size.x > viewport_size.x - 7.5 or edge_pos.y + edge_size.y > viewport_size.y - 7.5:
        push_error("Touch stick can clip beyond bottom/right phone bounds")
        quit(1)
        return
    main.hud.hide_touch_stick()

    var touch_press := InputEventScreenTouch.new()
    touch_press.index = 7
    touch_press.position = Vector2(180.0, 520.0)
    touch_press.pressed = true
    main._unhandled_input(touch_press)
    if main.touch_id != 7 or main.hud.touch_stick_root == null or not main.hud.touch_stick_root.visible:
        push_error("Touch press did not activate floating movement stick")
        quit(1)
        return

    var micro_drag := InputEventScreenDrag.new()
    micro_drag.index = 7
    micro_drag.position = Vector2(186.0, 524.0)
    main._unhandled_input(micro_drag)
    var initial_knob_center: Vector2 = (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
    if main.player.touch_move.length_squared() > 0.0001:
        push_error("Touch-stick deadzone allowed unintended player drift")
        quit(1)
        return
    if main.hud.touch_stick_knob.position.distance_to(initial_knob_center) > 0.5:
        push_error("Touch-stick visual moved inside control deadzone")
        quit(1)
        return
    if not main.hud.onboarding_panel.visible:
        push_error("Movement hint disappeared before meaningful touch movement")
        quit(1)
        return

    var touch_drag := InputEventScreenDrag.new()
    touch_drag.index = 7
    touch_drag.position = Vector2(250.0, 455.0)
    main._unhandled_input(touch_drag)
    if main.player.touch_move.length() < 0.50:
        push_error("Touch drag did not drive player movement vector")
        quit(1)
        return
    if main.onboarding_hint_active or main.hud.onboarding_panel.visible:
        push_error("Movement hint did not dismiss after meaningful touch drag")
        quit(1)
        return
    var knob_center: Vector2 = (main.hud.touch_stick_root.size - main.hud.touch_stick_knob.size) * 0.5
    var knob_distance: float = main.hud.touch_stick_knob.position.distance_to(knob_center)
    var max_knob_travel: float = (main.hud.touch_stick_root.size.x - main.hud.touch_stick_knob.size.x) * 0.5 - 4.0
    if knob_distance < 8.0:
        push_error("Touch drag did not move floating stick knob")
        quit(1)
        return
    if knob_distance > max_knob_travel + 0.5:
        push_error("Touch stick knob escaped its visual base")
        quit(1)
        return

    var touch_release := InputEventScreenTouch.new()
    touch_release.index = 7
    touch_release.position = touch_drag.position
    touch_release.pressed = false
    main._unhandled_input(touch_release)
    if main.touch_id != -1 or main.player.touch_move.length_squared() > 0.0001 or main.hud.touch_stick_root.visible:
        push_error("Touch release did not reset movement stick state")
        quit(1)
        return

    var pause_button := main.hud.get_node_or_null("PauseButton") as Button
    var pause_panel := main.hud.get_node_or_null("PausePanel") as PanelContainer
    if pause_button == null or pause_panel == null:
        push_error("Pause controls are unavailable in first-playable path")
        quit(1)
        return

    var pressure_enemy: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var candidate := node as DZEnemy
        if candidate != null:
            pressure_enemy = candidate
            break
    if pressure_enemy == null:
        push_error("Pause pressure-cleanup test has no enemy")
        quit(1)
        return
    pressure_enemy.global_position = main.player.global_position + Vector3(1.6, 0.0, 0.0)
    main.player._update_player_marker_pressure(pressure_enemy)
    var pressure_locator := main.player.get_node_or_null("PlayerPressureLocator") as Node3D
    if pressure_locator == null or not pressure_locator.visible:
        push_error("First-playable pressure locator did not activate before pause")
        quit(1)
        return

    main.hit_freeze_left = 0.030
    Engine.time_scale = 0.12
    pause_button.pressed.emit()
    await process_frame
    if not paused or not pause_panel.visible:
        push_error("Pause action did not pause gameplay and show settings")
        quit(1)
        return
    if main.hit_freeze_left > 0.0 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Pause transition retained global hit-freeze state")
        quit(1)
        return
    if main.player.player_marker_pressure or pressure_locator.visible:
        push_error("Pause overlay must clear stale combat pressure feedback")
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
    var music_slider := pause_panel.find_child("MusicVolume", true, false) as HSlider
    master_slider.value = 0.35
    sfx_slider.value = 0.45
    music_slider.value = 0.40
    await process_frame
    var master_bus := AudioServer.get_bus_index("Master")
    var sfx_bus := AudioServer.get_bus_index("SFX")
    var music_bus := AudioServer.get_bus_index("Music")
    if sfx_bus < 0 or music_bus < 0:
        push_error("Pause settings did not create dedicated SFX/Music audio buses")
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
    if abs(AudioServer.get_bus_volume_db(music_bus) - linear_to_db(0.40)) > 0.25:
        push_error("Music volume slider did not update Music bus")
        quit(1)
        return

    var upgrade_touch_press := InputEventScreenTouch.new()
    upgrade_touch_press.index = 9
    upgrade_touch_press.position = Vector2(190.0, 525.0)
    upgrade_touch_press.pressed = true
    main._unhandled_input(upgrade_touch_press)

    var upgrade_touch_drag := InputEventScreenDrag.new()
    upgrade_touch_drag.index = 9
    upgrade_touch_drag.position = Vector2(260.0, 460.0)
    main._unhandled_input(upgrade_touch_drag)
    if main.player.touch_move.length_squared() <= 0.01 or not main.hud.touch_stick_root.visible:
        push_error("Upgrade transition test could not establish active touch movement")
        quit(1)
        return

    main.hit_freeze_left = 0.030
    Engine.time_scale = 0.12
    var previous_level: int = main.level
    var threshold: int = main.xp_next
    main._on_xp_collected(threshold)
    if main.level != previous_level + 1 or main.pending_upgrades.size() != 3:
        push_error("XP progression did not open a three-choice upgrade")
        quit(1)
        return
    if main.director_refresh_clock > 0.0:
        push_error("Level-up must force the run director to refresh on the next physics tick")
        quit(1)
        return
    if main.touch_id != -1 or main.player.touch_move.length_squared() > 0.0001 or main.hud.touch_stick_root.visible:
        push_error("Upgrade overlay retained stale mobile movement state")
        quit(1)
        return
    if main.hit_freeze_left > 0.0 or not is_equal_approx(Engine.time_scale, 1.0):
        push_error("Upgrade overlay retained global hit-freeze state")
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

    var chain_start_level: int = int(main.level)
    var chain_first_threshold: int = int(main.xp_next)
    var chain_second_threshold := int(round(float(chain_first_threshold) * 1.24 + 4.0))
    main._on_xp_collected(chain_first_threshold + chain_second_threshold + 3)
    if main.level != chain_start_level + 1 or main.pending_upgrades.size() != 3 or not paused:
        push_error("Banked multi-level XP did not open the first upgrade choice")
        quit(1)
        return
    main._on_upgrade_chosen(0)
    if main.level != chain_start_level + 2 or main.pending_upgrades.size() != 3 or not main.hud.upgrade_panel.visible or not paused:
        push_error("Banked XP did not immediately chain the next level-up choice")
        quit(1)
        return
    if main.xp != 3:
        push_error("Multi-level XP chain did not preserve overflow XP")
        quit(1)
        return
    main._on_upgrade_chosen(0)
    if not main.pending_upgrades.is_empty() or main.hud.upgrade_panel.visible or paused:
        push_error("Final chained upgrade did not resume gameplay cleanly")
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

    if main.current_boss == null or main.current_boss.dead:
        push_error("Boss spawn did not register the active boss singleton")
        quit(1)
        return
    var boss_count_before_deferred_spawn := _count_kind("boss")
    main.next_boss_time = main.elapsed
    main._physics_process(0.016)
    if _count_kind("boss") != boss_count_before_deferred_spawn:
        push_error("Boss scheduler spawned a second boss while one was still active")
        quit(1)
        return
    if main.next_boss_time < main.elapsed + main.BOSS_RETRY_DELAY - 0.05:
        push_error("Active boss did not create a recovery window before the next boss check")
        quit(1)
        return

    main.next_boss_time = main.elapsed + 0.5
    main._on_boss_health_changed(0.0, 100.0)
    if main.next_boss_time < main.elapsed + main.BOSS_RETRY_DELAY - 0.05:
        push_error("Boss death did not guarantee a full post-kill recovery window")
        quit(1)
        return

    main._on_enemy_impact(Vector3.ZERO, false, true, true)
    if main.boss_defeat_relief_left < main.BOSS_DEFEAT_RELIEF_DURATION - 0.01:
        push_error("Boss kill did not arm reward relief beat")
        quit(1)
        return
    if main._wave_name() != "THREAT NEUTRALIZED // PRESSURE DROPPING":
        push_error("Boss kill did not expose threat-neutralized run banner")
        quit(1)
        return
    if not is_equal_approx(main._music_pressure_target_db(), main.MUSIC_PRESSURE_RELIEF_DB):
        push_error("Boss kill did not temporarily drop pressure music layer")
        quit(1)
        return
    main.boss_defeat_relief_left = 0.0

    var projectile := PROJECTILE_SCRIPT.new()
    main.add_child(projectile)
    projectile.velocity = Vector3(8.0, 0.0, 0.0)
    await process_frame

    var active_boss: DZEnemy
    for node in get_nodes_in_group("enemies"):
        var candidate_boss := node as DZEnemy
        if candidate_boss != null and candidate_boss.kind == "boss" and not candidate_boss.dead:
            active_boss = candidate_boss
            break
    main.camera_kick = 0.12
    main.boss_reveal_left = 0.8
    main.boss_reveal_target = active_boss
    main.hud.impact_flash.visible = true
    main.hud.damage_vignette.visible = true
    if main.boss_audio != null and main.boss_audio.stream != null:
        main.boss_audio.play()
    if main.music_pressure_audio != null:
        main.music_pressure_audio.volume_db = main.MUSIC_PRESSURE_BOSS_DB
    main._on_player_died()
    if not main.game_over or not main.hud.game_over_panel.visible:
        push_error("Player death did not enter visible game-over state")
        quit(1)
        return
    if main.camera_kick > 0.0 or main.boss_reveal_left > 0.0 or main.boss_reveal_target != null:
        push_error("Run end retained transient camera combat state")
        quit(1)
        return
    if main.boss_audio != null and main.boss_audio.playing:
        push_error("Run end retained active boss stinger audio")
        quit(1)
        return
    if main.music_end_tween == null or not main.music_end_tween.is_valid():
        push_error("Run end did not start adaptive music de-escalation")
        quit(1)
        return
    if main.hud.impact_flash.visible or main.hud.damage_vignette.visible or main.hud.boss_panel.visible:
        push_error("Run end retained transient combat HUD overlays")
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

    var game_over_touch := InputEventScreenTouch.new()
    game_over_touch.index = 12
    game_over_touch.position = Vector2(180.0, 520.0)
    game_over_touch.pressed = true
    main._unhandled_input(game_over_touch)
    if main.touch_id != -1 or main.hud.touch_stick_root.visible:
        push_error("Game-over input leaked into the mobile movement stick")
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

## File: tests/game_over_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-game-over-frame.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    scene.kills = 37
    scene.level = 8
    scene.elapsed = 154.0
    scene._on_player_died()
    for _frame in range(6):
        await process_frame

    if not scene.game_over or scene.hud == null or not scene.hud.game_over_panel.visible:
        push_error("Game-over render QA did not enter final run state")
        quit(1)
        return
    if scene.hud.game_over_scrim == null or not scene.hud.game_over_scrim.visible:
        push_error("Game-over render QA is missing dimmed backdrop")
        quit(1)
        return
    if scene.hud.game_over_summary.text != "LEVEL 8   •   KILLS 37   •   02:34":
        push_error("Game-over render QA summary is incorrect")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Game-over render QA has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Game-over render QA produced no image")
        quit(1)
        return
    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 640 or height < 360:
        push_error("Game-over frame must remain landscape, got %dx%d" % [width, height])
        quit(1)
        return

    var center := image.get_pixel(width / 2, height / 2)
    if center.get_luminance() < 0.02:
        push_error("Game-over panel center rendered effectively black")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save game-over render artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_GAME_OVER_FRAME_OK %dx%d" % [width, height])
    quit(0)
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
    if not main_source.contains("if haptics_enabled:") or not main_source.contains("HAPTICS.pulse(HAPTICS.event_for_impact"):
        push_error("Main impact path is not wired to user-controllable combat haptics")
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

    for node_name in ["VitalPanel", "VitalAccent", "CombatLinkLabel", "SignalLabel", "HealthValueLabel", "WavePanel", "ThreatPanel", "BossPanel", "UpgradePanel"]:
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

    hud.set_health(73.0, 100.0)
    if hud.health_value_label == null or hud.health_value_label.text != "HP 73 / 100":
        push_error("Health value label must expose exact current/max integrity")
        quit(1)
        return
    hud.set_progress(5, 10, 3, 12, 65.0, 7)
    if not hud.status_label.text.contains("THREATS 7"):
        push_error("Combat status must expose active threat count")
        quit(1)
        return

    if hud.wave_label == null or hud.wave_label.get_theme_font_size("font_size") > 22:
        push_error("Wave label must not dominate the active combat frame")
        quit(1)
        return

    hud.set_wave("CINDER SURGE")
    if hud.current_wave_text != "CINDER SURGE" or hud.wave_transition_tween == null:
        push_error("Run phase transition did not trigger restrained HUD presentation")
        quit(1)
        return
    var phase_tween := hud.wave_transition_tween
    hud.set_wave("CINDER SURGE")
    if hud.wave_transition_tween != phase_tween:
        push_error("Unchanged wave label retriggered transition animation")
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
    fx.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(fx)
    await process_frame

    var light_count := 0
    var mesh_count := 0
    var ring_found := false
    var flare_found := false
    for child in fx.get_children():
        if child is OmniLight3D:
            light_count += 1
        if child is MeshInstance3D:
            mesh_count += 1
            if child.name == "ImpactRing":
                ring_found = true
            elif child.name == "ImpactFlare":
                flare_found = true

    if light_count != 0:
        push_error("Impact FX should avoid per-hit dynamic lights on mobile")
        quit(1)
        return
    if mesh_count < 3 or not ring_found or not flare_found:
        push_error("Impact FX is missing premium layered core/ring/flare geometry")
        quit(1)
        return

    var core := fx.get_node_or_null("ImpactCore") as MeshInstance3D
    var ring := fx.get_node_or_null("ImpactRing") as MeshInstance3D
    var flare := fx.get_node_or_null("ImpactFlare") as MeshInstance3D
    if core == null or ring == null or flare == null:
        push_error("Impact FX core/ring/flare nodes are missing")
        quit(1)
        return
    if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or ring.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or flare.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Impact FX emissive geometry must not cast dynamic shadows")
        quit(1)
        return
    var core_mesh := core.mesh as SphereMesh
    var ring_mesh := ring.mesh as TorusMesh
    if core_mesh == null or core_mesh.radial_segments > 12 or core_mesh.rings > 6:
        push_error("Impact core geometry budget regressed")
        quit(1)
        return
    if ring_mesh == null or ring_mesh.rings > 16 or ring_mesh.ring_segments > 6:
        push_error("Impact ring geometry budget regressed")
        quit(1)
        return

    var duplicate := IMPACT_SCRIPT.new()
    duplicate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(duplicate)
    await process_frame
    var duplicate_core := duplicate.get_node_or_null("ImpactCore") as MeshInstance3D
    var duplicate_ring := duplicate.get_node_or_null("ImpactRing") as MeshInstance3D
    var duplicate_flare := duplicate.get_node_or_null("ImpactFlare") as MeshInstance3D
    if duplicate_core == null or duplicate_ring == null or duplicate_flare == null:
        push_error("Duplicate impact FX did not build geometry")
        quit(1)
        return
    if duplicate_core.mesh != core.mesh or duplicate_ring.mesh != ring.mesh or duplicate_flare.mesh != flare.mesh:
        push_error("Impact FX instances must reuse shared core/ring/flare mesh resources")
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

    var duplicate_sparks := duplicate.get_node_or_null("ImpactSparks") as GPUParticles3D
    if duplicate_sparks == null:
        push_error("Duplicate impact FX is missing GPU sparks")
        quit(1)
        return
    if duplicate_sparks.process_material != sparks.process_material:
        push_error("Impact FX instances must reuse spark process materials for identical colors")
        quit(1)
        return
    if duplicate_sparks.draw_pass_1 != sparks.draw_pass_1:
        push_error("Impact FX instances must reuse spark draw meshes for identical colors")
        quit(1)
        return

    var projectile := DZProjectile.new()
    projectile.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(projectile)
    current_scene = null
    var children_before := root.get_child_count()
    projectile._impact(false, projectile.global_position)
    await process_frame
    if root.get_child_count() <= children_before:
        push_error("Projectile impact FX did not fall back to projectile parent without current_scene")
        quit(1)
        return
    current_scene = root

    print("Deadline Zero mobile-safe impact FX: OK")
    quit(0)
```

## File: tests/industrial_kit_pbr_budget_test.gd
```
extends SceneTree

# Real GLB material QA: four floor grates and the authored industrial kit
# should never render as ungraded white surfaces on low-end GLES/mobile.
func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var families := [
        {"id": "grate", "factory": Callable(DZAssetLibrary, "industrial_floor_grate"), "min_meshes": 18},
        {"id": "bulkhead", "factory": Callable(DZAssetLibrary, "industrial_bulkhead"), "min_meshes": 10},
        {"id": "crate", "factory": Callable(DZAssetLibrary, "industrial_crate"), "min_meshes": 10},
        {"id": "pipe", "factory": Callable(DZAssetLibrary, "industrial_pipe_rack"), "min_meshes": 10},
        {"id": "pillar", "factory": Callable(DZAssetLibrary, "industrial_service_pillar"), "min_meshes": 8},
    ]
    var tested_surfaces := 0
    for family in families:
        var factory: Callable = family["factory"]
        var first := factory.call() as Node3D
        var second := factory.call() as Node3D
        if first == null or second == null:
            push_error("%s production GLB is unavailable" % family["id"])
            quit(1)
            return
        var meshes := _meshes(first)
        var duplicate_meshes := _meshes(second)
        if meshes.size() < int(family["min_meshes"]) or duplicate_meshes.size() != meshes.size():
            push_error("%s authored hard-surface geometry was flattened or lost" % family["id"])
            quit(1)
            return

        var grate_slats := 0
        var grate_recesses := 0
        var hazard_parts := 0
        var signal_parts := 0
        for index in range(meshes.size()):
            var mesh := meshes[index]
            var duplicate := duplicate_meshes[index]
            if mesh.mesh == null or mesh.material_override != null:
                push_error("%s lost per-surface industrial PBR grading" % family["id"])
                quit(1)
                return
            if family["id"] == "grate" and mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Floor grate slats must not enter the costly mobile shadow map")
                quit(1)
                return
            for surface in range(mesh.mesh.get_surface_count()):
                var finish := mesh.get_surface_override_material(surface) as StandardMaterial3D
                var duplicate_finish := duplicate.get_surface_override_material(surface) as StandardMaterial3D
                if finish == null or duplicate_finish != finish:
                    push_error("%s does not reuse shared graded GLB materials" % family["id"])
                    quit(1)
                    return
                if maxf(finish.albedo_color.r, maxf(finish.albedo_color.g, finish.albedo_color.b)) > 0.75:
                    push_error("%s imported a near-white overexposed surface" % family["id"])
                    quit(1)
                    return
                if finish.roughness < 0.45 or finish.metallic < 0.15:
                    push_error("%s has implausible industrial PBR surface response" % family["id"])
                    quit(1)
                    return
                tested_surfaces += 1

            var identity := mesh.name.to_lower()
            if family["id"] == "grate" and "slat" in identity:
                grate_slats += 1
                var slat_material := mesh.get_surface_override_material(0) as StandardMaterial3D
                if maxf(slat_material.albedo_color.r, maxf(slat_material.albedo_color.g, slat_material.albedo_color.b)) > 0.22 or slat_material.emission_enabled:
                    push_error("Grate slat is too bright for the dark combat floor")
                    quit(1)
                    return
            if family["id"] == "grate" and "recess" in identity:
                grate_recesses += 1
            if "hazard" in identity:
                hazard_parts += 1
            if "signal" in identity:
                signal_parts += 1

        if family["id"] == "grate" and (grate_slats < 12 or grate_recesses < 1):
            push_error("Original grate slat/recess geometry no longer exists")
            quit(1)
            return
        if family["id"] == "bulkhead" and hazard_parts < 4:
            push_error("Original bulkhead warning markings were lost")
            quit(1)
            return
        if family["id"] == "pillar" and signal_parts < 1:
            push_error("Original industrial pillar signal was lost")
            quit(1)
            return
        first.free()
        second.free()

    if tested_surfaces < 55:
        push_error("Hard-surface QA exercised too few authored GLB surfaces")
        quit(1)
        return
    print("Deadline Zero industrial GLB PBR grading: OK (%d shared surfaces)" % tested_surfaces)
    quit(0)

func _meshes(node: Node3D) -> Array[MeshInstance3D]:
    var result: Array[MeshInstance3D] = []
    if node is MeshInstance3D:
        result.append(node as MeshInstance3D)
    for child in node.find_children("*", "MeshInstance3D", true, false):
        result.append(child as MeshInstance3D)
    return result
```

## File: tests/industrial_material_sharing_test.gd
```
extends SceneTree

# Stress actual imported materials: every repeat prop must share immutable
# PBR surface resources without flattening its UV atlas.
func _initialize() -> void:
    var factories := [
        {"name": "barrier", "make": Callable(DZAssetLibrary, "barrier")},
        {"name": "barrel", "make": Callable(DZAssetLibrary, "barrel")},
        {"name": "pallet", "make": Callable(DZAssetLibrary, "pallet")},
        {"name": "street_lights", "make": Callable(DZAssetLibrary, "street_lights")},
        {"name": "traffic_cone", "make": Callable(DZAssetLibrary, "traffic_cone")},
        {"name": "trash_bag", "make": Callable(DZAssetLibrary, "trash_bag")},
    ]

    var first_grades := {}
    var total_textured_instances := 0
    var barrier_stripe_material: Material
    for spec in factories:
        var name := String(spec["name"])
        var make: Callable = spec["make"]
        for sample in range(5):
            var root := make.call() as Node3D
            if root == null:
                push_error("Industrial PBR instance missing: %s" % name)
                quit(1)
                return

            var surface_count := 0
            var nodes: Array[MeshInstance3D] = []
            if root is MeshInstance3D:
                nodes.append(root as MeshInstance3D)
            for node in root.find_children("*", "MeshInstance3D", true, false):
                nodes.append(node as MeshInstance3D)
            for instance in nodes:
                if instance.mesh == null or instance.material_override != null:
                    continue
                for surface_index in range(instance.mesh.get_surface_count()):
                    var source := instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    if source == null or source.albedo_texture == null:
                        continue
                    var material := instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if material == null or material.albedo_texture != source.albedo_texture:
                        push_error("Authored atlas flattened or lost: %s" % name)
                        quit(1)
                        return
                    if not first_grades.has(name):
                        first_grades[name] = material
                    elif first_grades[name] != material:
                        push_error("Identical imported prop copies allocated distinct PBR materials: %s" % name)
                        quit(1)
                        return
                    surface_count += 1

            if surface_count == 0:
                push_error("No original textured surface in %s" % name)
                quit(1)
                return
            total_textured_instances += surface_count

            if name == "barrier":
                var front := root.find_child("BarrierHazardFront", true, false) as MeshInstance3D
                var rear := root.find_child("BarrierHazardRear", true, false) as MeshInstance3D
                if front == null or rear == null or front.material_override == null:
                    push_error("Hazard material sharing fixture missing")
                    quit(1)
                    return
                if rear.material_override != front.material_override:
                    push_error("Barrier stripes did not share one emissive material")
                    quit(1)
                    return
                if barrier_stripe_material == null:
                    barrier_stripe_material = front.material_override
                elif barrier_stripe_material != front.material_override:
                    push_error("Different barriers allocated duplicate hazard stripe materials")
                    quit(1)
                    return
            root.free()

    if first_grades.size() != factories.size() or total_textured_instances < 30:
        push_error("PBR sharing stress test did not cover all 30 imported props")
        quit(1)
        return
    if first_grades["barrel"] == first_grades["pallet"]:
        push_error("Material sharing leaked barrel metal grade into wood pallet")
        quit(1)
        return

    print("Deadline Zero shared authored PBR prop materials: OK (%d textured surfaces, %d families)" % [total_textured_instances, first_grades.size()])
    quit(0)
```

## File: tests/industrial_prop_material_test.gd
```
extends SceneTree

# Imported Quaternius assets ship UV-painted atlases. The runtime must keep
# those textures and individually grade PBR surface materials, never flatten
# the whole asset with a single untextured material_override.
func _initialize() -> void:
    var fixtures := [
        {"id": "barrel", "factory": Callable(DZAssetLibrary, "barrel"), "roughness": 0.83, "metallic": 0.28},
        {"id": "pallet", "factory": Callable(DZAssetLibrary, "pallet"), "roughness": 0.91, "metallic": 0.02},
        {"id": "traffic_cone", "factory": Callable(DZAssetLibrary, "traffic_cone"), "roughness": 0.86, "metallic": 0.01},
        {"id": "trash_bag", "factory": Callable(DZAssetLibrary, "trash_bag"), "roughness": 0.94, "metallic": 0.01},
        {"id": "barrier", "factory": Callable(DZAssetLibrary, "barrier"), "roughness": 0.84, "metallic": 0.34},
        {"id": "street_lights", "factory": Callable(DZAssetLibrary, "street_lights"), "roughness": 0.78, "metallic": 0.46},
    ]
    var total_textured_surfaces := 0
    for spec in fixtures:
        var factory: Callable = spec["factory"]
        var root := factory.call() as Node3D
        if root == null:
            push_error("Missing imported industrial 3D scene: %s" % spec["id"])
            quit(1)
            return
        var meshes: Array[MeshInstance3D] = []
        if root is MeshInstance3D:
            meshes.append(root as MeshInstance3D)
        for node in root.find_children("*", "MeshInstance3D", true, false):
            meshes.append(node as MeshInstance3D)

        var textured_surfaces := 0
        var optic_surfaces := 0
        for instance in meshes:
            if instance == null or instance.mesh == null:
                continue
            for surface_index in range(instance.mesh.get_surface_count()):
                var source := instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                if source == null:
                    continue
                # Authored hazard strips are separate geometry and should not
                # affect imported mesh/atlas checks.
                if instance.material_override != null:
                    push_error("Flattened industrial material on %s / %s" % [spec["id"], instance.name])
                    quit(1)
                    return
                var graded := instance.get_surface_override_material(surface_index) as BaseMaterial3D
                if graded == null:
                    push_error("Missing per-surface PBR material: %s / %s" % [spec["id"], instance.name])
                    quit(1)
                    return
                if source.albedo_texture != null:
                    if graded.albedo_texture != source.albedo_texture:
                        push_error("Authoring atlas was replaced for %s" % spec["id"])
                        quit(1)
                        return
                    if graded.roughness < float(spec["roughness"]) - 0.001 or graded.metallic < float(spec["metallic"]) - 0.001:
                        push_error("Industrial PBR roughness/metalness was lost on %s" % spec["id"])
                        quit(1)
                        return
                    textured_surfaces += 1
                elif spec["id"] == "street_lights":
                    if not graded.emission_enabled or graded.emission_energy_multiplier < 1.0 or graded.emission_energy_multiplier > 1.5:
                        push_error("Authored streetlight lens lost budgeted emissive shading")
                        quit(1)
                        return
                    optic_surfaces += 1

        if textured_surfaces == 0:
            push_error("No preserved authored texture atlas: %s" % spec["id"])
            quit(1)
            return
        if spec["id"] == "street_lights" and optic_surfaces == 0:
            push_error("Streetlight GLTF lens surface is missing")
            quit(1)
            return
        if spec["id"] == "barrier":
            for marker_name in ["BarrierHazardFront", "BarrierHazardRear"]:
                var marker := root.find_child(marker_name, true, false) as MeshInstance3D
                if marker == null or marker.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Hazard stripe must remain visible but shadowless: %s" % marker_name)
                    quit(1)
                    return
        total_textured_surfaces += textured_surfaces
        root.free()

    if total_textured_surfaces < 6:
        push_error("Too few authored PBR surfaces were exercised")
        quit(1)
        return
    print("Deadline Zero authored industrial PBR atlases: OK (%d textured surfaces)" % total_textured_surfaces)
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

## File: tests/multihit_silhouette_stability_test.gd
```
extends SceneTree

# Combat-grade GLTF regression: simultaneous scatter/arc hits must not enlarge
# or displace an enemy silhouette by multiplying interrupted recoil tweens.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var survivor := Node3D.new()
    survivor.name = "TestSurvivor"
    survivor.position = Vector3(-2.0, 0.0, 0.0)
    root.add_child(survivor)
    await process_frame

    var archetypes := ["shambler", "runner", "charger", "harrier",
        "regenerator", "brute", "elite", "boss"]
    for kind in archetypes:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, survivor)
        root.add_child(enemy)
        enemy.set_combat_enabled(false)
        await process_frame

        var visual := enemy.get_node_or_null("Visual") as Node3D
        if visual == null or enemy.authored_visual == null:
            push_error("Imported enemy GLTF visual is absent: %s" % kind)
            quit(1)
            return
        var rest_scale := enemy.visual_rest_scale
        var rest_position := enemy.visual_rest_position
        if rest_scale.length_squared() < 0.001:
            push_error("Enemy authored silhouette baseline was not captured: %s" % kind)
            quit(1)
            return

        # An early hit must cancel the unfinished spawn reveal before retiming
        # the exact same visual root.
        enemy._play_hit_reaction(false, false)
        if enemy.spawn_reveal_tween != null and enemy.spawn_reveal_tween.is_running():
            push_error("Early shot left competing spawn/recoil tweens active: %s" % kind)
            quit(1)
            return

        for index in range(9):
            # Model the worst-case previous frame's recoil peak. The new hit
            # must discard this temporary visual displacement immediately.
            visual.scale = rest_scale * 1.35
            visual.position = rest_position + Vector3(0.5, 0.1, -0.4)
            enemy._play_hit_reaction(index % 2 == 0, false)
            if visual.scale.distance_to(rest_scale) > 0.0001 or visual.position.distance_to(rest_position) > 0.0001:
                push_error("Stacked recoil inflated or displaced silhouette: %s hit %d" % [kind, index])
                quit(1)
                return
            if enemy.hit_flash_visual == null or not enemy.hit_flash_visual.visible:
                push_error("Repeated hit lost visual confirmation: %s" % kind)
                quit(1)
                return

        await create_timer(0.28).timeout
        if visual.scale.distance_to(rest_scale) > 0.002 or visual.position.distance_to(rest_position) > 0.002:
            push_error("Interrupted recoil never settled back to imported rest pose: %s" % kind)
            quit(1)
            return
        if enemy.hit_flash_visual.visible:
            push_error("Repeated hit flash did not finish: %s" % kind)
            quit(1)
            return

        enemy._play_hit_reaction(true, true)
        await create_timer(0.28).timeout
        var kill_pose := rest_scale * 1.04
        if visual.scale.distance_to(kill_pose) > 0.004:
            push_error("Death punch did not respect bounded authored silhouette: %s" % kind)
            quit(1)
            return
        enemy.free()

    print("Deadline Zero rapid multihit 3D silhouette stability: OK (8 archetypes x 10 hits)")
    quit(0)
```

## File: tests/muzzle_flare_3d_test.gd
```
extends SceneTree

# Rendering-budget QA for directional shot feedback on the actual survivor.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var survivor := DZPlayer.new()
    root.add_child(survivor)
    survivor.set_combat_enabled(false)
    await process_frame

    var core := survivor.muzzle_flash
    if core == null or not core is MeshInstance3D:
        push_error("Authored survivor is missing its original 3D muzzle core")
        quit(1)
        return
    if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Muzzle core must not enter mobile directional shadow map")
        quit(1)
        return

    var spikes := [
        core.get_node_or_null("MuzzleFlashWingL") as MeshInstance3D,
        core.get_node_or_null("MuzzleFlashSpear") as MeshInstance3D,
        core.get_node_or_null("MuzzleFlashWingR") as MeshInstance3D,
    ]
    var shared_mesh: Mesh
    for index in range(spikes.size()):
        var spike: MeshInstance3D = spikes[index]
        if spike == null:
            push_error("Production shot flare missing directional 3D spike: %d" % index)
            quit(1)
            return
        if spike.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Muzzle spike creates expensive mobile shadows")
            quit(1)
            return
        if spike.material_override != core.material_override or spike.material_override != survivor.muzzle_flash_material:
            push_error("Layered muzzle flare lost shared accessibility-aware emissive material")
            quit(1)
            return
        var cone := spike.mesh as CylinderMesh
        if cone == null or cone.radial_segments > 6 or cone.rings > 1:
            push_error("Muzzle flare spike exceeded constrained six-sided geometry budget")
            quit(1)
            return
        if shared_mesh == null:
            shared_mesh = cone
        elif spike.mesh != shared_mesh:
            push_error("Muzzle flare creates duplicate tapered mesh resources")
            quit(1)
            return
    if spikes[1].position.z >= -0.10 or spikes[0].position.x >= 0.0 or spikes[2].position.x <= 0.0:
        push_error("Muzzle flare lost forward-pointing left/center/right silhouette")
        quit(1)
        return

    survivor.set_reduced_flashes(false)
    survivor._trigger_muzzle_flash()
    if not core.visible or survivor.muzzle_flash_material.emission_energy_multiplier < 6.0:
        push_error("Ordinary muzzle flare no longer creates readable shot feedback")
        quit(1)
        return

    survivor.set_reduced_flashes(true)
    survivor._trigger_muzzle_flash()
    if not core.visible or survivor.muzzle_flash_material.emission_energy_multiplier > 2.21:
        push_error("Reduced-flashes setting did not dim all shared 3D muzzle flare geometry")
        quit(1)
        return
    if survivor.muzzle_flash_tween == null or not survivor.muzzle_flash_tween.is_running():
        push_error("Short muzzle flash envelope was not triggered")
        quit(1)
        return

    await create_timer(0.18).timeout
    if core.visible:
        push_error("Mobile muzzle flare persisted beyond its brief shot window")
        quit(1)
        return

    print("Deadline Zero authored directional 3D muzzle flare: OK (3 shared tapered spikes)")
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

    var melee_brute := ENEMY_SCRIPT.new()
    melee_brute.configure("brute", 1.0, target)
    melee_brute.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(melee_brute)
    if separation_a._melee_standoff_distance() < 1.05:
        push_error("Shambler melee standoff is too small to preserve player readability")
        quit(1)
        return
    if melee_brute._melee_standoff_distance() <= separation_a._melee_standoff_distance():
        push_error("Large melee archetypes must keep a wider visual standoff")
        quit(1)
        return
    if separation_a._contact_attack_range() <= separation_a._melee_standoff_distance():
        push_error("Enemies must remain able to attack from the visual standoff envelope")
        quit(1)
        return

    if separation_a._melee_attack_animation_range() < separation_a._contact_attack_range():
        push_error("Shambler attack animation range must cover contact damage range")
        quit(1)
        return
    if melee_brute._melee_attack_animation_range() < melee_brute._contact_attack_range():
        push_error("Brute attack animation range must cover heavy contact damage range")
        quit(1)
        return
    if melee_brute._melee_attack_animation_range() <= separation_a._melee_attack_animation_range():
        push_error("Heavy melee animation range must scale with the larger standoff envelope")
        quit(1)
        return

    separation_a.global_position = Vector3(40.0, 0.0, -41.0)
    separation_a.velocity = Vector3(3.0, 0.0, -2.0)
    separation_a._constrain_to_arena()
    if absf(separation_a.global_position.x) > DZEnemy.ARENA_HALF_EXTENT + 0.001 or absf(separation_a.global_position.z) > DZEnemy.ARENA_HALF_EXTENT + 0.001:
        push_error("Enemy escaped authored arena bounds")
        quit(1)
        return
    if absf(separation_a.velocity.x) > 0.001 or absf(separation_a.velocity.z) > 0.001:
        push_error("Enemy arena clamp did not cancel outward velocity")
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

## File: tests/offscreen_threat_priority_test.gd
```
extends SceneTree

const MAIN_SCRIPT := preload("res://scripts/Main.gd")

func _initialize() -> void:
    var arena := Node3D.new()
    get_root().add_child(arena)
    current_scene = arena

    var view_camera := Camera3D.new()
    arena.add_child(view_camera)
    # SceneTree._initialize() runs before initial nodes finish entering the tree.
    # Project/look-at calls require a live viewport and a valid world transform.
    await process_frame
    view_camera.global_position = Vector3(0.0, 12.8, 9.15)
    view_camera.look_at(Vector3.ZERO, Vector3.UP)
    view_camera.current = true
    await process_frame

    var viewport_size := get_root().get_visible_rect().size
    var main := MAIN_SCRIPT.new()

    var near_elite := _make_threat(arena, "elite", Vector3(20.0, 0.0, 0.0))
    var far_elite := _make_threat(arena, "elite", Vector3(27.0, 0.0, 0.0))
    var far_boss := _make_threat(arena, "boss", Vector3(-29.0, 0.0, 0.0))
    var visible_boss := _make_threat(arena, "boss", Vector3.ZERO)
    var ordinary := _make_threat(arena, "shambler", Vector3(-24.0, 0.0, 0.0))

    if main._select_offscreen_threat([near_elite, far_boss], Vector3.ZERO, view_camera, viewport_size) != far_boss:
        push_error("Offscreen boss should outrank nearer elite")
        quit(1)
        return

    if main._select_offscreen_threat([visible_boss, near_elite], Vector3.ZERO, view_camera, viewport_size) != near_elite:
        push_error("Visible boss must not hide offscreen elite warning")
        quit(1)
        return

    if main._select_offscreen_threat([far_elite, near_elite], Vector3.ZERO, view_camera, viewport_size) != near_elite:
        push_error("Nearest elite must win same-priority tie")
        quit(1)
        return

    far_boss.dead = true
    if main._select_offscreen_threat([far_boss, far_elite], Vector3.ZERO, view_camera, viewport_size) != far_elite:
        push_error("Dead boss must not suppress live offscreen warning")
        quit(1)
        return

    if main._select_offscreen_threat([visible_boss, ordinary], Vector3.ZERO, view_camera, viewport_size) != null:
        push_error("Visible boss or ordinary zombie triggered an unnecessary warning")
        quit(1)
        return

    if main._select_offscreen_threat([], Vector3.ZERO, view_camera, viewport_size) != null:
        push_error("Empty threat list should clear offscreen indicator")
        quit(1)
        return

    main.free()
    print("Deadline Zero offscreen threat priority and visibility: OK")
    quit(0)

func _make_threat(arena: Node3D, threat_kind: String, at: Vector3) -> DZEnemy:
    var enemy := DZEnemy.new()
    enemy.kind = threat_kind
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    arena.add_child(enemy)
    enemy.global_position = at
    return enemy
```

## File: tests/pause_settings_layout_test.gd
```
extends SceneTree

const HUD_SCRIPT := preload("res://scripts/Hud.gd")

func _initialize() -> void:
    var hud := HUD_SCRIPT.new()
    get_root().add_child(hud)
    await process_frame
    await process_frame

    var panel := hud.pause_panel
    if panel == null:
        push_error("Pause/settings panel is missing")
        quit(1)
        return

    var viewport_size := get_root().get_visible_rect().size
    var panel_rect := panel.get_global_rect()
    var safe_margin := 40.0
    if panel_rect.position.x < safe_margin or panel_rect.position.y < safe_margin:
        push_error("Pause/settings panel violates top/left mobile safe margin")
        quit(1)
        return
    if panel_rect.end.x > viewport_size.x - safe_margin or panel_rect.end.y > viewport_size.y - safe_margin:
        push_error("Pause/settings panel clips beyond mobile safe viewport margin")
        quit(1)
        return

    var required_controls := [
        hud.master_volume,
        hud.sfx_volume,
        hud.music_volume,
        hud.haptics_toggle,
        hud.reduced_flashes_toggle,
        hud.camera_shake_toggle,
        hud.hit_stop_toggle,
        panel.find_child("ResumeButton", true, false)
    ]
    for control in required_controls:
        if control == null or not control is Control:
            push_error("Pause/settings control is missing")
            quit(1)
            return
        var ui := control as Control
        if ui.size.y < 47.5:
            push_error("Pause/settings touch target is below 48px: %s %.1fpx" % [ui.name, ui.size.y])
            quit(1)
            return
        var rect := ui.get_global_rect()
        if rect.position.x < panel_rect.position.x - 1.0 or rect.end.x > panel_rect.end.x + 1.0:
            push_error("Pause/settings control escapes panel horizontally: %s" % ui.name)
            quit(1)
            return
        if rect.position.y < panel_rect.position.y - 1.0 or rect.end.y > panel_rect.end.y + 1.0:
            push_error("Pause/settings control escapes panel vertically: %s" % ui.name)
            quit(1)
            return

    print("Deadline Zero Godot pause/settings layout: OK")
    quit(0)
```

## File: tests/play_feature_graphic_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-feature-graphic-candidate.png"

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var viewport := SubViewport.new()
    viewport.size = Vector2i(1024, 500)
    viewport.render_target_update_mode = SubViewport.UPDATE_ALWAYS
    viewport.msaa_3d = Viewport.MSAA_4X
    get_root().add_child(viewport)

    var world := Node3D.new()
    viewport.add_child(world)

    var environment_node := WorldEnvironment.new()
    var environment := Environment.new()
    environment.background_mode = Environment.BG_COLOR
    environment.background_color = Color(0.006, 0.012, 0.018)
    environment.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    environment.ambient_light_color = Color(0.09, 0.15, 0.19)
    environment.ambient_light_energy = 0.72
    environment.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    environment_node.environment = environment
    world.add_child(environment_node)

    var key := DirectionalLight3D.new()
    key.rotation_degrees = Vector3(-48.0, -28.0, 0.0)
    key.light_color = Color(0.62, 0.90, 1.0)
    key.light_energy = 2.0
    key.shadow_enabled = true
    world.add_child(key)

    var warm := OmniLight3D.new()
    warm.position = Vector3(4.8, 2.4, 0.0)
    warm.light_color = Color(1.0, 0.16, 0.025)
    warm.light_energy = 8.5
    warm.omni_range = 10.0
    warm.shadow_enabled = false
    world.add_child(warm)

    var cool := OmniLight3D.new()
    cool.position = Vector3(-4.8, 2.3, 1.0)
    cool.light_color = Color(0.08, 0.72, 1.0)
    cool.light_energy = 6.5
    cool.omni_range = 10.0
    cool.shadow_enabled = false
    world.add_child(cool)

    var floor := MeshInstance3D.new()
    var floor_mesh := PlaneMesh.new()
    floor_mesh.size = Vector2(18.0, 9.0)
    floor.mesh = floor_mesh
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.016, 0.025, 0.032)
    floor_mat.metallic = 0.30
    floor_mat.roughness = 0.84
    floor.material_override = floor_mat
    world.add_child(floor)

    var wall_material := StandardMaterial3D.new()
    wall_material.albedo_color = Color(0.020, 0.032, 0.040)
    wall_material.metallic = 0.58
    wall_material.roughness = 0.68

    var recess_material := StandardMaterial3D.new()
    recess_material.albedo_color = Color(0.006, 0.010, 0.014)
    recess_material.metallic = 0.38
    recess_material.roughness = 0.82

    var cyan_material := StandardMaterial3D.new()
    cyan_material.albedo_color = Color(0.06, 0.70, 0.88)
    cyan_material.emission_enabled = true
    cyan_material.emission = Color(0.03, 0.52, 0.78)
    cyan_material.emission_energy_multiplier = 1.65
    cyan_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var orange_material := StandardMaterial3D.new()
    orange_material.albedo_color = Color(0.98, 0.20, 0.025)
    orange_material.emission_enabled = true
    orange_material.emission = Color(0.82, 0.08, 0.008)
    orange_material.emission_energy_multiplier = 1.85
    orange_material.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED

    var panel_mesh := BoxMesh.new()
    panel_mesh.size = Vector3(3.60, 3.30, 0.18)
    var recess_mesh := BoxMesh.new()
    recess_mesh.size = Vector3(2.66, 2.20, 0.08)
    var light_bar_mesh := BoxMesh.new()
    light_bar_mesh.size = Vector3(2.35, 0.055, 0.055)

    for panel_index in range(4):
        var wall_panel := MeshInstance3D.new()
        wall_panel.name = "FeatureBulkhead_%d" % panel_index
        wall_panel.mesh = panel_mesh
        wall_panel.position = Vector3(-5.35 + float(panel_index) * 3.55, 1.62, -3.78)
        wall_panel.material_override = wall_material
        world.add_child(wall_panel)

        var recess := MeshInstance3D.new()
        recess.name = "FeatureBulkheadRecess_%d" % panel_index
        recess.mesh = recess_mesh
        recess.position = wall_panel.position + Vector3(0.0, 0.02, 0.135)
        recess.material_override = recess_material
        world.add_child(recess)

        var upper_bar := MeshInstance3D.new()
        upper_bar.name = "FeatureBulkheadLight_%d" % panel_index
        upper_bar.mesh = light_bar_mesh
        upper_bar.position = wall_panel.position + Vector3(0.0, 1.18, 0.205)
        upper_bar.material_override = cyan_material if panel_index < 2 else orange_material
        upper_bar.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        world.add_child(upper_bar)

    var containment_ring := MeshInstance3D.new()
    var containment_mesh := TorusMesh.new()
    containment_mesh.inner_radius = 3.65
    containment_mesh.outer_radius = 3.73
    containment_mesh.rings = 44
    containment_mesh.ring_segments = 8
    containment_ring.mesh = containment_mesh
    containment_ring.position = Vector3(0.55, 0.022, -0.35)
    containment_ring.material_override = orange_material
    containment_ring.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    world.add_child(containment_ring)

    for side in [-1.0, 1.0]:
        var runway := MeshInstance3D.new()
        var runway_mesh := BoxMesh.new()
        runway_mesh.size = Vector3(0.075, 0.018, 5.8)
        runway.mesh = runway_mesh
        runway.position = Vector3(side * 4.55, 0.018, -0.15)
        runway.material_override = cyan_material if side < 0.0 else orange_material
        runway.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
        world.add_child(runway)

    for index in range(5):
        var barrier := DZAssetLibrary.barrier()
        if barrier == null:
            continue
        barrier.position = Vector3(-6.25 + float(index) * 3.1, 0.0, -2.85 + absf(float(index) - 2.0) * 0.10)
        barrier.rotation.y = 0.08 * float(index - 2)
        barrier.scale = Vector3.ONE * 0.28
        world.add_child(barrier)

    var target := Node3D.new()
    world.add_child(target)

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, target)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    boss.spawn_secondary_fx = false
    world.add_child(boss)
    boss.position = Vector3(3.20, 0.0, -0.92)
    boss.rotation.y = deg_to_rad(-24.0)
    boss.scale = Vector3.ONE * 1.46

    var elite := DZEnemy.new()
    elite.configure("elite", 1.0, target)
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    elite.spawn_secondary_fx = false
    world.add_child(elite)
    elite.position = Vector3(1.22, 0.0, -1.32)
    elite.rotation.y = deg_to_rad(12.0)
    elite.scale = Vector3.ONE * 1.08

    var player := DZAssetLibrary.player()
    if player == null:
        push_error("Feature graphic capture could not load authored player")
        quit(1)
        return
    player.position = Vector3(-3.20, 0.0, 0.22)
    player.rotation.y = deg_to_rad(18.0)
    player.scale = Vector3.ONE * 1.82
    world.add_child(player)

    var rifle := DZAssetLibrary.rifle()
    if rifle != null:
        rifle.position = Vector3(0.42, 0.83, -0.30)
        rifle.rotation_degrees = Vector3(-11.0, 166.0, -9.0)
        rifle.scale = Vector3.ONE * 0.84
        player.add_child(rifle)

    var camera := Camera3D.new()
    camera.position = Vector3(0.15, 2.62, 8.15)
    camera.fov = 34.5
    world.add_child(camera)
    camera.current = true
    camera.look_at(Vector3(0.05, 1.04, -0.62), Vector3.UP)

    var brand_layer := CanvasLayer.new()
    brand_layer.layer = 4
    viewport.add_child(brand_layer)

    var cinematic_overlay := ColorRect.new()
    cinematic_overlay.position = Vector2.ZERO
    cinematic_overlay.size = Vector2(1024.0, 500.0)
    var overlay_material := ShaderMaterial.new()
    var overlay_shader := Shader.new()
    overlay_shader.code = """
shader_type canvas_item;

void fragment() {
    vec2 uv = UV;
    float edge = smoothstep(0.72, 0.98, length((uv - vec2(0.5)) * vec2(1.0, 1.35)));
    float top = smoothstep(0.72, 0.04, uv.y);
    float diagonal = smoothstep(0.0, 0.85, uv.x + (1.0 - uv.y) * 0.45);
    vec3 cyan = vec3(0.02, 0.18, 0.24) * (1.0 - diagonal) * 0.36;
    vec3 orange = vec3(0.28, 0.055, 0.005) * diagonal * 0.28;
    vec3 tint = cyan + orange;
    float alpha = clamp(edge * 0.52 + top * 0.08, 0.0, 0.62);
    COLOR = vec4(tint, alpha);
}
"""
    overlay_material.shader = overlay_shader
    cinematic_overlay.material = overlay_material
    cinematic_overlay.mouse_filter = Control.MOUSE_FILTER_IGNORE
    brand_layer.add_child(cinematic_overlay)

    var title_backdrop := ColorRect.new()
    title_backdrop.position = Vector2(38.0, 20.0)
    title_backdrop.size = Vector2(478.0, 122.0)
    title_backdrop.color = Color(0.004, 0.009, 0.013, 0.90)
    brand_layer.add_child(title_backdrop)

    var cyan_rule := ColorRect.new()
    cyan_rule.position = Vector2(38.0, 20.0)
    cyan_rule.size = Vector2(7.0, 122.0)
    cyan_rule.color = Color(0.08, 0.82, 1.0, 0.95)
    brand_layer.add_child(cyan_rule)

    var orange_rule := ColorRect.new()
    orange_rule.position = Vector2(45.0, 136.0)
    orange_rule.size = Vector2(238.0, 7.0)
    orange_rule.color = Color(1.0, 0.26, 0.035, 0.92)
    brand_layer.add_child(orange_rule)

    var title := Label.new()
    title.position = Vector2(68.0, 35.0)
    title.size = Vector2(440.0, 58.0)
    title.text = "DEADLINE: ZERO"
    title.add_theme_font_size_override("font_size", 45)
    title.add_theme_color_override("font_color", Color(0.92, 0.97, 1.0))
    title.add_theme_color_override("font_outline_color", Color(0.0, 0.0, 0.0, 0.86))
    title.add_theme_constant_override("outline_size", 4)
    brand_layer.add_child(title)

    var subtitle := Label.new()
    subtitle.position = Vector2(70.0, 93.0)
    subtitle.size = Vector2(430.0, 34.0)
    subtitle.text = "SURVIVE THE QUARANTINE"
    subtitle.add_theme_font_size_override("font_size", 17)
    subtitle.add_theme_color_override("font_color", Color(0.35, 0.86, 1.0))
    brand_layer.add_child(subtitle)

    var emblem := TextureRect.new()
    emblem.position = Vector2(924.0, 22.0)
    emblem.size = Vector2(72.0, 72.0)
    emblem.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
    emblem.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
    emblem.texture = load("res://assets/ui/deadline_zero_icon.svg") as Texture2D
    brand_layer.add_child(emblem)

    for _frame in range(8):
        await process_frame

    var image := viewport.get_texture().get_image()
    if image == null or image.is_empty() or image.get_width() != 1024 or image.get_height() != 500:
        push_error("Feature graphic capture did not render exact 1024x500")
        quit(1)
        return

    var min_luma := 1.0
    var max_luma := 0.0
    var bright_samples := 0
    var samples := 0
    for y in range(0, image.get_height(), 16):
        for x in range(0, image.get_width(), 16):
            var luma := image.get_pixel(x, y).get_luminance()
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.18:
                bright_samples += 1
            samples += 1
    if max_luma - min_luma < 0.18 or float(bright_samples) / float(maxi(samples, 1)) < 0.015:
        push_error("Feature graphic lacks premium tonal separation")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save feature graphic candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_FEATURE_GRAPHIC_CANDIDATE_OK 1024x500")
    quit(0)
```

## File: tests/play_icon_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-play-icon-candidate.png"
const ICON_TEXTURE := preload("res://assets/ui/deadline_zero_icon.svg")

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var viewport := SubViewport.new()
    viewport.size = Vector2i(512, 512)
    viewport.render_target_update_mode = SubViewport.UPDATE_ALWAYS
    viewport.transparent_bg = false
    get_root().add_child(viewport)

    var background := ColorRect.new()
    background.position = Vector2.ZERO
    background.size = Vector2(512.0, 512.0)
    background.color = Color(0.005, 0.008, 0.010)
    viewport.add_child(background)

    var emblem := TextureRect.new()
    emblem.position = Vector2.ZERO
    emblem.size = Vector2(512.0, 512.0)
    emblem.texture = ICON_TEXTURE
    emblem.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
    emblem.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
    emblem.texture_filter = CanvasItem.TEXTURE_FILTER_LINEAR_WITH_MIPMAPS
    viewport.add_child(emblem)

    for _frame in range(4):
        await process_frame

    var image := viewport.get_texture().get_image()
    if image == null or image.is_empty() or image.get_width() != 512 or image.get_height() != 512:
        push_error("Brand icon capture did not render exact 512x512")
        quit(1)
        return

    var sample_points := [
        Vector2i(256, 256),
        Vector2i(256, 92),
        Vector2i(150, 256),
        Vector2i(362, 256)
    ]
    var luminance_range := 0.0
    var min_luma := 1.0
    var max_luma := 0.0
    for point in sample_points:
        var color := image.get_pixelv(point)
        var luma := color.get_luminance()
        min_luma = minf(min_luma, luma)
        max_luma = maxf(max_luma, luma)
    luminance_range = max_luma - min_luma
    if luminance_range < 0.08:
        push_error("Play icon emblem rendered without enough visual contrast")
        quit(1)
        return

    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save brand icon candidate: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PLAY_ICON_CANDIDATE_OK 512x512 contrast=%.3f" % luminance_range)
    quit(0)
```

## File: tests/play_store_claims_test.gd
```
extends SceneTree

func _initialize() -> void:
    var listing_path := ProjectSettings.globalize_path("res://../play/store/LISTING.md")
    var listing := FileAccess.get_file_as_string(listing_path)
    if listing.is_empty():
        push_error("Godot Play listing contract is missing")
        quit(1)
        return

    var required := [
        "8 enemy archetypes",
        "three-phase boss",
        "6 weapon profiles and protocols",
        "14 run upgrades",
        "Vanguard, Scatter, Rail, Inferno, Cryo, and Arc"
    ]
    for token in required:
        if not listing.contains(token):
            push_error("Godot Play listing is missing verified runtime claim: %s" % token)
            quit(1)
            return

    var forbidden := [
        "Multiple survivors",
        "12+ weapons",
        "50+ run upgrades",
        "5 biomes",
        "20+ enemy gameplay profiles",
        "6 multi-phase bosses",
        "Persistent progression"
    ]
    for token in forbidden:
        if listing.contains(token):
            push_error("Godot Play listing still contains unsupported legacy claim: %s" % token)
            quit(1)
            return

    var data_safety_path := ProjectSettings.globalize_path("res://../play/store/DATA_SAFETY.md")
    var data_safety := FileAccess.get_file_as_string(data_safety_path)
    if not data_safety.contains("not present in the current Godot release candidate"):
        push_error("Data Safety contract does not distinguish legacy SDKs from Godot release")
        quit(1)
        return
    if not data_safety.contains("no Google Mobile Ads") and not data_safety.contains("No Google Mobile Ads"):
        push_error("Data Safety contract does not state the current no-ads SDK state")
        quit(1)
        return

    print("Deadline Zero Godot Play Store claims: OK")
    quit(0)
```

## File: tests/player_damage_feedback_test.gd
```
extends SceneTree

var died_emitted := false

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

    if player.authored_anim != null and player.authored_anim.has_animation("HitReact"):
        if player.current_anim != "HitReact" or player.hit_reaction_left <= 0.0:
            push_error("Non-lethal player damage did not enter authored HitReact presentation")
            quit(1)
            return

    var health_after_first_hit: float = player.health
    player.take_damage(12.0)
    if not is_equal_approx(player.health, health_after_first_hit):
        push_error("Invulnerability window failed to reject immediate repeated damage")
        quit(1)
        return

    for _tick in range(14):
        await physics_frame
    await process_frame
    if pulse.visible:
        push_error("Damage pulse did not clear after its presentation window")
        quit(1)
        return

    if player.hit_reaction_left > 0.0:
        push_error("Player HitReact presentation did not release after its short lock window")
        quit(1)
        return
    if player.authored_anim != null and player.authored_anim.has_animation("Idle_Gun") and player.current_anim == "HitReact":
        push_error("Player remained stuck in HitReact after the presentation window")
        quit(1)
        return

    player.invulnerability = 0.0
    player.died.connect(_on_player_died)
    player.take_damage(player.health + 1000.0)
    if player.health > 0.0 or not died_emitted:
        push_error("Lethal player damage did not enter death state")
        quit(1)
        return
    if player.authored_anim != null and player.authored_anim.has_animation("Death") and player.current_anim != "Death":
        push_error("Lethal player damage did not play authored Death animation")
        quit(1)
        return

    print("Deadline Zero player damage feedback: OK")
    quit(0)

func _on_player_died() -> void:
    died_emitted = true
```

## File: tests/player_pressure_marker_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    var enemy := DZEnemy.new()
    enemy.configure("shambler", 1.0, player)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    player.global_position = Vector3.ZERO
    enemy.global_position = Vector3(1.6, 0.0, 0.0)
    player._update_player_marker_pressure(enemy)

    var locator := player.get_node_or_null("PlayerPressureLocator") as Node3D
    if locator == null or not locator.visible:
        push_error("Close melee pressure must show the elevated player locator")
        quit(1)
        return
    if not player.player_marker_pressure:
        push_error("Player pressure state was not activated")
        quit(1)
        return
    if player.player_marker_ring == null or player.player_marker_ring.scale.x < 1.09:
        push_error("Player pressure ring did not expand")
        quit(1)
        return

    player.set_combat_enabled(false)
    if player.player_marker_pressure:
        push_error("Combat shutdown must clear stale player pressure state")
        quit(1)
        return
    if locator.visible:
        push_error("Combat shutdown must hide the player pressure locator")
        quit(1)
        return
    if player.player_marker_ring == null or not player.player_marker_ring.scale.is_equal_approx(Vector3.ONE):
        push_error("Combat shutdown must restore the player marker ring scale")
        quit(1)
        return

    player.set_combat_enabled(true)
    player._update_player_marker_pressure(enemy)
    if not player.player_marker_pressure or not locator.visible:
        push_error("Player pressure feedback must recover when combat resumes")
        quit(1)
        return

    enemy.global_position = Vector3(4.0, 0.0, 0.0)
    player._update_player_marker_pressure(enemy)
    if player.player_marker_pressure or locator.visible:
        push_error("Distant enemies must not keep the pressure locator active")
        quit(1)
        return

    print("Deadline Zero player pressure marker: OK")
    quit(0)
```

## File: tests/player_targeting_stability_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    player.target_refresh_clock = 0.0
    if player._combat_target() != null or player.target_refresh_clock <= 0.0:
        push_error("Auto-aim did not arm a no-target refresh cooldown")
        quit(1)
        return

    var first := DZEnemy.new()
    first.configure("shambler", 1.0, player)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(DZPlayer.TARGET_ACQUIRE_RADIUS + 4.0, 0.0, 0.0)

    player.target_refresh_clock = 0.0
    if player._combat_target() != null:
        push_error("Auto-aim acquired an enemy beyond the gameplay camera engagement radius")
        quit(1)
        return

    first.global_position = Vector3(4.0, 0.0, 0.0)

    var second := DZEnemy.new()
    second.configure("runner", 1.0, player)
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    second.global_position = Vector3(4.5, 0.0, 0.0)
    await process_frame

    player.global_position = Vector3.ZERO
    if player._combat_target() != null:
        push_error("Auto-aim bypassed its no-target refresh window")
        quit(1)
        return
    player.target_refresh_clock = 0.0
    var selected := player._combat_target()
    if selected != first:
        push_error("Auto-aim did not select the nearest initial target")
        quit(1)
        return

    second.global_position = Vector3(3.5, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim switched targets for an insignificant distance gain")
        quit(1)
        return

    if player.nearest_threat != second:
        push_error("Close-threat tracking must follow the actual nearest enemy independently of aim lock")
        quit(1)
        return

    second.global_position = Vector3(2.8, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim did not switch to a materially closer threat")
        quit(1)
        return

    first.global_position = Vector3(1.5, 0.0, 0.0)
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim ignored its target refresh window")
        quit(1)
        return

    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim did not reconsider targets after refresh interval")
        quit(1)
        return

    first.global_position = Vector3(DZPlayer.TARGET_ACQUIRE_RADIUS + 0.5, 0.0, 0.0)
    player.target_refresh_clock = DZPlayer.TARGET_REFRESH_INTERVAL
    selected = player._combat_target()
    if selected == first:
        push_error("Cached auto-aim target remained locked after leaving engagement radius")
        quit(1)
        return

    first.global_position = Vector3(1.5, 0.0, 0.0)
    player.target_refresh_clock = 0.0
    selected = player._combat_target()
    if selected != first:
        push_error("Auto-aim failed to reacquire target after returning inside engagement radius")
        quit(1)
        return

    first.dead = true
    selected = player._combat_target()
    if selected != second:
        push_error("Auto-aim did not immediately abandon a dead target")
        quit(1)
        return

    first.dead = false
    first.global_position = Vector3(1.5, 0.0, 0.0)
    player.nearest_threat = second
    second.free()
    var pressure_target := player._pressure_target(first)
    if pressure_target != first or player.nearest_threat != null:
        push_error("Pressure targeting retained a freed nearest-threat reference")
        quit(1)
        return

    player.set_combat_enabled(false)
    if player.current_target != null or player.nearest_threat != null or player.target_refresh_clock > 0.0:
        push_error("Combat shutdown did not clear auto-aim/threat target state")
        quit(1)
        return

    print("Deadline Zero player targeting stability: OK")
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

    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

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

    # Drive a deterministic number of real physics ticks so movement, targeting,
    # projectile cadence and melee standoff settle identically on fast and slow CI runners.
    for _tick in range(72):
        await physics_frame
    # Give the renderer a few frames to present the settled simulation state.
    for _frame in range(4):
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

    var pressure_locator := player.get_node_or_null("PlayerPressureLocator") as Node3D
    if not player.player_marker_pressure or pressure_locator == null or not pressure_locator.visible:
        push_error("Pressure-frame QA did not capture active close-pressure player feedback")
        quit(1)
        return

    # Validate phone-scale readability in screen space, not just world-space spacing.
    # A visually black/non-black image gate cannot catch actors collapsing into one blob.
    var viewport_size: Vector2 = get_root().get_visible_rect().size
    var player_screen: Vector2 = scene.camera.unproject_position(player.global_position + Vector3(0.0, 0.9, 0.0))
    var projected_enemies: Array[Dictionary] = []
    for node in get_nodes_in_group("enemies"):
        var enemy := node as DZEnemy
        if enemy == null or enemy.dead or not active_kinds.has(enemy.kind):
            continue
        if scene.camera.is_position_behind(enemy.global_position):
            continue
        var screen_pos: Vector2 = scene.camera.unproject_position(enemy.global_position + Vector3(0.0, 0.9, 0.0))
        if screen_pos.x < 28.0 or screen_pos.y < 28.0 or screen_pos.x > viewport_size.x - 28.0 or screen_pos.y > viewport_size.y - 28.0:
            continue
        projected_enemies.append({"kind": enemy.kind, "screen": screen_pos})

    if projected_enemies.size() < 4:
        push_error("Pressure-frame QA has too few readable on-screen archetypes: %d" % projected_enemies.size())
        quit(1)
        return

    var min_player_distance := INF
    var min_enemy_distance := INF
    for index in range(projected_enemies.size()):
        var sample: Dictionary = projected_enemies[index]
        var sample_screen: Vector2 = sample["screen"]
        min_player_distance = minf(min_player_distance, sample_screen.distance_to(player_screen))
        for other_index in range(index + 1, projected_enemies.size()):
            var other: Dictionary = projected_enemies[other_index]
            var other_screen: Vector2 = other["screen"]
            min_enemy_distance = minf(min_enemy_distance, sample_screen.distance_to(other_screen))

    if min_player_distance < 36.0:
        push_error("Pressure-frame actor overlap hides the player silhouette: min_player_px=%.2f" % min_player_distance)
        quit(1)
        return
    if min_enemy_distance < 28.0:
        push_error("Pressure-frame enemy silhouettes collapse together: min_enemy_px=%.2f" % min_enemy_distance)
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
    var clipped := 0
    var total := 0
    var sum_luma := 0.0
    var min_luma := 1.0
    var max_luma := 0.0
    for y in range(0, height, 8):
        for x in range(0, width, 8):
            var color := image.get_pixel(x, y)
            var luma := color.r * 0.2126 + color.g * 0.7152 + color.b * 0.0722
            total += 1
            sum_luma += luma
            min_luma = minf(min_luma, luma)
            max_luma = maxf(max_luma, luma)
            if luma > 0.045:
                bright += 1
            if luma > 0.96:
                clipped += 1

    var bright_fraction := float(bright) / float(maxi(total, 1))
    var clipped_fraction := float(clipped) / float(maxi(total, 1))
    var average_luma := sum_luma / float(maxi(total, 1))
    var luma_range := max_luma - min_luma
    if bright_fraction < 0.008 or average_luma < 0.006:
        push_error("Pressure frame is effectively black: bright=%.5f avg=%.5f" % [bright_fraction, average_luma])
        quit(1)
        return
    if luma_range < 0.05:
        push_error("Pressure frame lacks enough tonal separation: range=%.5f" % luma_range)
        quit(1)
        return
    if clipped_fraction > 0.18:
        push_error("Pressure frame is excessively clipped: clipped=%.5f" % clipped_fraction)
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save pressure-frame artifact: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_PRESSURE_FRAME_OK %dx%d kinds=%d onscreen=%d player_px=%.2f enemy_px=%.2f bright=%.5f avg=%.5f range=%.5f clipped=%.5f" % [
        width, height, active_kinds.size(), projected_enemies.size(), min_player_distance, min_enemy_distance, bright_fraction, average_luma, luma_range, clipped_fraction
    ])
    quit(0)
```

## File: tests/projectile_profile_runtime_visual_test.gd
```
extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const PROFILES := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    for index in range(PROFILES.size()):
        var profile: String = PROFILES[index]
        var projectile := PROJECTILE_SCRIPT.new()
        projectile.name = "Projectile_%s" % profile
        projectile.process_mode = Node.PROCESS_MODE_DISABLED
        projectile.setup(
            Vector3(float(index) * 1.5, 0.7, 0.0),
            Vector3.FORWARD,
            10.0,
            10.0,
            Color(0.18, 0.90, 1.0),
            profile
        )
        root.add_child(projectile)
        await process_frame

        var core := projectile.get_node_or_null("ProjectileCore") as MeshInstance3D
        var trail := projectile.get_node_or_null("ProjectileTrail") as MeshInstance3D
        if core == null or trail == null:
            push_error("%s projectile did not build core/trail runtime visuals" % profile)
            quit(1)
            return
        var trail_mesh := trail.mesh as BoxMesh
        var feedback := DZWeaponProfiles.profile(profile)
        if trail_mesh == null or trail_mesh.size.z + 0.001 < float(feedback.get("trail_length", 0.0)):
            push_error("%s projectile trail lost configured premium streak length" % profile)
            quit(1)
            return
        if trail_mesh.size.x < 0.030:
            push_error("%s projectile trail became too thin for phone-scale readability" % profile)
            quit(1)
            return
        if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("%s projectile core must not cast mobile-unfriendly shadows" % profile)
            quit(1)
            return
        if trail.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("%s projectile trail must not cast mobile-unfriendly shadows" % profile)
            quit(1)
            return

        var duplicate := PROJECTILE_SCRIPT.new()
        duplicate.name = "Projectile_%s_duplicate" % profile
        duplicate.process_mode = Node.PROCESS_MODE_DISABLED
        duplicate.setup(
            Vector3(float(index) * 1.5, 0.7, 1.2),
            Vector3.FORWARD,
            10.0,
            10.0,
            Color(0.18, 0.90, 1.0),
            profile
        )
        root.add_child(duplicate)
        await process_frame
        var duplicate_core := duplicate.get_node_or_null("ProjectileCore") as MeshInstance3D
        var duplicate_trail := duplicate.get_node_or_null("ProjectileTrail") as MeshInstance3D
        if duplicate_core == null or duplicate_trail == null:
            push_error("%s duplicate projectile did not build cached visuals" % profile)
            quit(1)
            return
        if duplicate_core.mesh != core.mesh or duplicate_trail.mesh != trail.mesh:
            push_error("%s projectile visuals must reuse cached mesh resources" % profile)
            quit(1)
            return
        if duplicate_core.material_override != core.material_override or duplicate_trail.material_override != trail.material_override:
            push_error("%s projectile visuals must reuse cached material resources" % profile)
            quit(1)
            return
        match profile:
            "scatter":
                var sparks: Array[MeshInstance3D] = []
                var duplicate_sparks: Array[MeshInstance3D] = []
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is BoxMesh:
                            sparks.append(mesh_instance)
                for child in duplicate.get_children():
                    if child is MeshInstance3D and child != duplicate_core and child != duplicate_trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is BoxMesh:
                            duplicate_sparks.append(mesh_instance)
                if sparks.size() < 2 or duplicate_sparks.size() < 2:
                    push_error("Scatter projectile must build two side-spark accents")
                    quit(1)
                    return
                if sparks[0].mesh != duplicate_sparks[0].mesh:
                    push_error("Scatter projectile side sparks must reuse cached mesh resources")
                    quit(1)
                    return
            "cryo":
                if core.scale.y <= core.scale.x:
                    push_error("Cryo projectile core must retain elongated shard identity")
                    quit(1)
                    return
            "arc":
                var arc_accent: MeshInstance3D
                var duplicate_arc_accent: MeshInstance3D
                for child in projectile.get_children():
                    if child is MeshInstance3D and (child as MeshInstance3D).mesh is TorusMesh:
                        arc_accent = child as MeshInstance3D
                for child in duplicate.get_children():
                    if child is MeshInstance3D and (child as MeshInstance3D).mesh is TorusMesh:
                        duplicate_arc_accent = child as MeshInstance3D
                if arc_accent == null or duplicate_arc_accent == null:
                    push_error("Arc projectile runtime accent is missing")
                    quit(1)
                    return
                if arc_accent.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Arc accent must not cast shadows")
                    quit(1)
                    return
                if arc_accent.mesh != duplicate_arc_accent.mesh or arc_accent.material_override != duplicate_arc_accent.material_override:
                    push_error("Arc accents must reuse cached mesh/material resources")
                    quit(1)
                    return
            "inferno":
                var flame: MeshInstance3D
                var duplicate_flame: MeshInstance3D
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is SphereMesh and mesh_instance.scale.z > mesh_instance.scale.x:
                            flame = mesh_instance
                for child in duplicate.get_children():
                    if child is MeshInstance3D and child != duplicate_core and child != duplicate_trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is SphereMesh and mesh_instance.scale.z > mesh_instance.scale.x:
                            duplicate_flame = mesh_instance
                if flame == null or duplicate_flame == null:
                    push_error("Inferno projectile runtime flame identity is missing")
                    quit(1)
                    return
                if flame.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Inferno flame core must not cast shadows")
                    quit(1)
                    return
                if flame.mesh != duplicate_flame.mesh:
                    push_error("Inferno flame cores must reuse cached mesh resources")
                    quit(1)
                    return

        duplicate.queue_free()
        await process_frame
        projectile.queue_free()
        await process_frame

    print("Deadline Zero projectile profile runtime visuals: OK")
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

    await process_frame
    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

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

    image.convert(Image.FORMAT_RGB8)
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

## File: tests/rifle_muzzle_ballistics_test.gd
```
extends SceneTree

# Real firing QA: bullets must visibly originate at the authored rifle muzzle,
# never at the character's torso, across far/close bearings and weapon protocols.
const PROFILES := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var survivor := DZPlayer.new()
    survivor.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(survivor)

    var enemy := DZEnemy.new()
    enemy.configure("shambler", 1.0, survivor)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    if survivor.muzzle_flash == null or survivor.muzzle_flash.get_parent() != survivor:
        push_error("Real rifle muzzle locator is missing from the production survivor")
        quit(1)
        return

    survivor.multishot = 3
    survivor.spread_degrees = 8.0
    var bearings := [Vector3(8.0, 0.0, -5.0), Vector3(-7.0, 0.0, 4.0), Vector3(0.0, 0.0, -1.45)]
    for bearing in bearings:
        enemy.global_position = bearing
        survivor.look_at(bearing, Vector3.UP)
        for profile in PROFILES:
            survivor.weapon_profile = profile
            survivor._fire_at(enemy)
            var shots := get_nodes_in_group("projectiles")
            if shots.size() != survivor.multishot:
                push_error("%s barrel origin test produced %d projectiles, expected %d" % [profile, shots.size(), survivor.multishot])
                quit(1)
                return

            var base_direction := survivor.global_position.direction_to(enemy.global_position)
            base_direction.y = 0.0
            base_direction = base_direction.normalized()
            var muzzle_to_target := enemy.global_position - survivor._projectile_muzzle_origin(Vector3.ZERO)
            muzzle_to_target.y = 0.0
            if muzzle_to_target.length_squared() > 0.0001:
                base_direction = muzzle_to_target.normalized()
            for index in range(shots.size()):
                var projectile := shots[index] as DZProjectile
                if projectile == null or projectile.visual_profile != profile:
                    push_error("%s shot routing lost its authored projectile profile" % profile)
                    quit(1)
                    return
                projectile.process_mode = Node.PROCESS_MODE_DISABLED
                var spread_offset := float(index) - float(survivor.multishot - 1) * 0.5
                var direction := base_direction.rotated(Vector3.UP, deg_to_rad(spread_offset * survivor.spread_degrees))
                var expected := survivor.muzzle_flash.global_position + direction * 0.12
                if projectile.global_position.distance_to(expected) > 0.005:
                    push_error("%s projectile %d detached from the visible 3D rifle muzzle" % [profile, index])
                    quit(1)
                    return
                if projectile.velocity.normalized().distance_to(direction) > 0.001:
                    push_error("%s multishot lost the intended directional spread" % profile)
                    quit(1)
                    return
                if projectile.global_position.y < 0.90:
                    push_error("%s projectile spawned inside the player's torso" % profile)
                    quit(1)
                    return
                if index == 1:
                    var target_offset := enemy.global_position - projectile.global_position
                    var lateral_error := absf(Vector2(target_offset.x, target_offset.z).cross(Vector2(direction.x, direction.z)))
                    if lateral_error > 0.04:
                        push_error("%s centered shot misses the close target because of the rifle barrel offset" % profile)
                        quit(1)
                        return
                projectile.queue_free()
            await process_frame

    print("Deadline Zero rifle muzzle ballistics: OK (six protocols, three bearings, 54 shots)")
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
        "director_profile[\"difficulty\"]",
        "BOSS_DEFEAT_RELIEF_DURATION",
        "MUSIC_PRESSURE_RELIEF_DB",
        "THREAT NEUTRALIZED // PRESSURE DROPPING"
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
const XP_ORB_SCRIPT := preload("res://scripts/XpOrb.gd")

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

    var orb := XP_ORB_SCRIPT.new()
    orb.target = target
    root.add_child(orb)
    orb.position = Vector3(1.0, 0.18, 0.0)
    orb.velocity = Vector3(-6.0, 0.0, 0.0)

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

    var orb_before := orb.global_position
    orb.set_combat_enabled(false)
    orb._process(0.5)
    if orb.combat_enabled:
        push_error("XP orb combat freeze flag was not disabled")
        quit(1)
        return
    if orb.velocity.length_squared() > 0.0:
        push_error("XP orb velocity was not cleared")
        quit(1)
        return
    if orb.global_position.distance_to(orb_before) > 0.0001:
        push_error("XP orb moved after run-end combat freeze")
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
    if hud.game_over_scrim == null or hud.game_over_scrim.visible:
        push_error("Game-over scrim should exist and start hidden")
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
    if pause_panel.find_child("MasterVolume", true, false) == null or pause_panel.find_child("SfxVolume", true, false) == null or pause_panel.find_child("MusicVolume", true, false) == null:
        push_error("Pause/settings panel is missing audio sliders")
        quit(1)
        return

    hud.show_game_over(37, 8, 154.0)

    if not hud.game_over_panel.visible:
        push_error("Game-over panel did not become visible")
        quit(1)
        return
    if hud.game_over_scrim == null or not hud.game_over_scrim.visible:
        push_error("Game-over transition did not dim the combat scene")
        quit(1)
        return
    if hud.game_over_panel.find_child("GameOverKicker", true, false) == null or hud.game_over_panel.find_child("GameOverBreak", true, false) == null:
        push_error("Game-over presentation hierarchy is incomplete")
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
const PLAYER_SCRIPT := preload("res://scripts/Player.gd")

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

    var normal_alpha := hud.impact_flash.color.a
    hud.set_reduced_flashes(true)
    hud.show_impact_flash(true, false, false)
    if hud.impact_flash.color.a <= 0.0 or hud.impact_flash.color.a >= normal_alpha * 0.5:
        push_error("Reduced-flashes mode did not substantially lower impact flash intensity")
        quit(1)
        return
    hud.pulse_damage_screen()
    if hud.damage_vignette.modulate.a > 0.40:
        push_error("Reduced-flashes mode did not lower damage vignette intensity")
        quit(1)
        return

    var player := PLAYER_SCRIPT.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    get_root().add_child(player)
    await process_frame
    player.set_reduced_flashes(true)
    player._trigger_muzzle_flash()
    if player.muzzle_flash_material == null or player.muzzle_flash_material.emission_energy_multiplier > 2.3:
        push_error("Reduced-flashes mode did not lower player muzzle-flash emission")
        quit(1)
        return
    if player.muzzle_flash.scale.x > 0.50:
        push_error("Reduced-flashes mode did not reduce player muzzle-flash footprint")
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
        "sfx_volume": 0.33,
        "music_volume": 0.27,
        "haptics_enabled": false,
        "reduced_flashes": true,
        "camera_shake_enabled": false,
        "hit_stop_enabled": false
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
    if not is_equal_approx(float(loaded.get("music_volume", -1.0)), 0.27):
        push_error("Music volume setting did not persist")
        quit(1)
        return

    if bool(loaded.get("haptics_enabled", true)):
        push_error("Haptics setting did not persist")
        quit(1)
        return
    if not bool(loaded.get("reduced_flashes", false)):
        push_error("Reduced-flashes setting did not persist")
        quit(1)
        return

    if bool(loaded.get("camera_shake_enabled", true)) or bool(loaded.get("hit_stop_enabled", true)):
        push_error("Camera-shake/hit-stop settings did not persist")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(path))

    var integration_path := "user://deadline-zero-settings-integration-test.cfg"
    script.save(integration_path, {
        "master_volume": 0.35,
        "sfx_volume": 0.60,
        "music_volume": 0.40,
        "haptics_enabled": false,
        "reduced_flashes": true,
        "camera_shake_enabled": false,
        "hit_stop_enabled": false
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
    if not is_equal_approx(main.hud.music_volume.value, 0.40):
        push_error("Persisted music volume was not restored into pause settings")
        quit(1)
        return

    if main.hud.haptics_toggle.button_pressed or not main.hud.reduced_flashes_toggle.button_pressed:
        push_error("Persisted comfort toggles were not restored into pause settings")
        quit(1)
        return
    if main.haptics_enabled or not main.reduced_flashes:
        push_error("Persisted comfort state was not restored into Main")
        quit(1)
        return

    if main.camera_shake_enabled or main.hit_stop_enabled:
        push_error("Persisted camera-shake/hit-stop state was not restored into Main")
        quit(1)
        return
    if main.hud.camera_shake_toggle.button_pressed or main.hud.hit_stop_toggle.button_pressed:
        push_error("Persisted camera-shake/hit-stop toggles were not restored into pause settings")
        quit(1)
        return

    main.hud.master_volume.value = 0.60
    main.hud.sfx_volume.value = 0.45
    main.hud.music_volume.value = 0.50
    main._on_haptics_changed(true)
    main._on_reduced_flashes_changed(false)
    main._on_camera_shake_changed(true)
    main._on_hit_stop_changed(true)
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
    if not is_equal_approx(float(round_trip.get("music_volume", -1.0)), 0.50):
        push_error("Updated music volume was not saved from pause settings")
        quit(1)
        return

    if not bool(round_trip.get("haptics_enabled", false)) or bool(round_trip.get("reduced_flashes", true)):
        push_error("Updated comfort settings were not saved from pause settings")
        quit(1)
        return

    if not bool(round_trip.get("camera_shake_enabled", false)) or not bool(round_trip.get("hit_stop_enabled", false)):
        push_error("Updated camera-shake/hit-stop settings were not saved")
        quit(1)
        return

    DirAccess.remove_absolute(ProjectSettings.globalize_path(integration_path))
    print("Deadline Zero settings persistence: OK")
    quit(0)
```

## File: tests/shock_expiry_recovery_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := Node3D.new()
    target.position = Vector3(9.0, 0.0, 0.0)
    root.add_child(target)
    await process_frame

    for kind in ["shambler", "runner", "charger", "harrier", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.configure(kind, 1.0, target)
        # Keep the collision body registered in PhysicsServer3D; disable only
        # automatic callbacks so the two frames can be stepped deterministically.
        # PROCESS_MODE_DISABLED removes the body from its physics space, causing
        # hidden move_and_slide errors when the test calls _physics_process().
        enemy.set_physics_process(false)
        root.add_child(enemy)
        await process_frame
        if not PhysicsServer3D.body_get_space(enemy.get_rid()).is_valid():
            push_error("Shock QA attempted to simulate an unregistered physics body: %s" % kind)
            quit(1)
            return
        enemy.shock_left = 0.005
        enemy.velocity = Vector3(2.0, 0.0, 0.0)
        enemy._physics_process(0.016)
        if enemy.shock_left > 0.0 or enemy.velocity.length_squared() > 0.0001:
            push_error("Final stunned physics frame leaked movement: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim != null and enemy.authored_anim.speed_scale != 0.0:
            push_error("Final stunned frame did not freeze imported animation: %s" % kind)
            quit(1)
            return

        enemy._physics_process(0.016)
        if enemy.velocity.length_squared() < 0.01:
            push_error("Enemy failed to recover pursuit on next physics frame: %s" % kind)
            quit(1)
            return
        if enemy.authored_anim != null and enemy.authored_anim.speed_scale <= 0.0:
            push_error("Enemy remained animation-frozen after shock recovery: %s" % kind)
            quit(1)
            return
        enemy.queue_free()
        await process_frame

    print("Deadline Zero shock expiry recovery: OK (7 imported enemy archetypes)")
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

## File: tests/soft_contact_shadow_budget_test.gd
```
extends SceneTree

# The dense horde should have softened authored 3D grounding, not hundreds
# of hard edged multi-triangle discs or individual light/shader allocations.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var shared_material: ShaderMaterial
    var total_shadows := 0
    var meshes_by_kind := {}
    var kinds := ["shambler", "runner", "charger", "harrier",
        "regenerator", "brute", "elite", "boss"]
    for kind in kinds:
        for sample_index in range(2):
            var enemy := DZEnemy.new()
            enemy.kind = kind
            enemy.process_mode = Node.PROCESS_MODE_DISABLED
            root.add_child(enemy)
            await process_frame

            var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
            if shadow == null or not shadow.mesh is QuadMesh:
                push_error("Enemy is missing one lightweight radial quad shadow: %s" % kind)
                quit(1)
                return
            if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Contact shadow became an actual shadow caster: %s" % kind)
                quit(1)
                return
            if shadow.rotation_degrees.x > -89.0 or shadow.rotation_degrees.x < -91.0:
                push_error("Contact shadow quad no longer lies horizontally on the ground: %s" % kind)
                quit(1)
                return
            var quad := shadow.mesh as QuadMesh
            if quad.size.x > 1.83 or quad.size.x < 0.69 or not is_equal_approx(quad.size.x, quad.size.y):
                push_error("Contact shadow radius escaped silhouette coverage budget: %s" % kind)
                quit(1)
                return

            var material := shadow.material_override as ShaderMaterial
            if material == null or material.shader == null:
                push_error("Shadow lost feathered shader material: %s" % kind)
                quit(1)
                return
            if shared_material == null:
                shared_material = material
                var shader_code := material.shader.code
                if not shader_code.contains("smoothstep(0.18, 1.0, radius)") or not shader_code.contains("ALPHA = feather * feather * shadow_opacity"):
                    push_error("Contact shadow changed to opaque or hard-edged rendering")
                    quit(1)
                    return
            elif shared_material != material:
                push_error("Enemy copies created individual contact shadow shader instances: %s" % kind)
                quit(1)
                return

            if meshes_by_kind.has(kind) and meshes_by_kind[kind] != quad:
                push_error("Same archetype reallocated identical shadow quad geometry: %s" % kind)
                quit(1)
                return
            meshes_by_kind[kind] = quad
            total_shadows += 1

            enemy.queue_free()
            await process_frame

    if total_shadows != 16 or meshes_by_kind.size() != kinds.size():
        push_error("Soft shadow budget did not exercise all enemy model classes")
        quit(1)
        return
    print("Deadline Zero soft 3D contact shadows: OK (16 fixtures, 8 archetypes, 1 shader)")
    quit(0)
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

    var regenerator_a := ENEMY_SCRIPT.new()
    regenerator_a.configure("regenerator", 1.0, target)
    regenerator_a.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(regenerator_a)
    var regenerator_b := ENEMY_SCRIPT.new()
    regenerator_b.configure("regenerator", 1.0, target)
    regenerator_b.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(regenerator_b)
    await process_frame
    regenerator_a.health = regenerator_a.max_health * 0.5
    regenerator_b.health = regenerator_b.max_health * 0.5
    regenerator_a._begin_regeneration()
    regenerator_b._begin_regeneration()
    var pulse_a := regenerator_a.regeneration_visual as MeshInstance3D
    var pulse_b := regenerator_b.regeneration_visual as MeshInstance3D
    if pulse_a == null or pulse_b == null:
        push_error("Regenerator pulse visual is missing")
        quit(1)
        return
    if pulse_a.mesh != pulse_b.mesh:
        push_error("Regenerator pulses must reuse one mesh resource")
        quit(1)
        return
    if pulse_a.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Regenerator pulse must not cast dynamic shadows")
        quit(1)
        return
    if regenerator_a.regeneration_material == regenerator_b.regeneration_material:
        push_error("Regenerator pulse materials must stay instance-local for independent animation")
        quit(1)
        return

    print("Deadline Zero enemy status effects: OK")
    quit(0)
```

## File: tests/upgrade_choice_render_test.gd
```
extends SceneTree

const OUTPUT_PATH := "/tmp/deadline-zero-upgrade-choice.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_run_capture")

func _run_capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    if scene.hud != null:
        scene.hud.hide_onboarding_hint()

    scene.elapsed = 82.0
    scene.kills = 46
    scene.level = 4
    scene.xp = 0
    scene.xp_next = 92
    scene.director_profile = scene.run_director.profile(scene.elapsed, scene.level)
    scene.hud.set_progress(scene.xp, scene.xp_next, scene.level, scene.kills, scene.elapsed, get_nodes_in_group("enemies").size())

    seed(20261005)
    scene._on_xp_collected(scene.xp_next)
    for _frame in range(18):
        await process_frame

    if scene.pending_upgrades.size() != 3:
        push_error("Upgrade Store capture did not produce three choices")
        quit(1)
        return
    if not scene.hud.upgrade_panel.visible or not paused:
        push_error("Upgrade Store capture did not enter paused upgrade presentation")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Upgrade Store capture has no viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty():
        push_error("Upgrade Store capture produced no image")
        quit(1)
        return

    var width := image.get_width()
    var height := image.get_height()
    if width <= height or width < 1280 or height < 720:
        push_error("Upgrade Store capture must remain landscape and at least 1280x720, got %dx%d" % [width, height])
        quit(1)
        return

    var center := image.get_pixel(width / 2, height / 2)
    if center.get_luminance() < 0.02:
        push_error("Upgrade Store capture center rendered effectively black")
        quit(1)
        return

    var panel_min_luma := 1.0
    var panel_max_luma := 0.0
    var accent_pixels := 0
    var sampled_pixels := 0
    var x_start := int(width * 0.23)
    var x_end := int(width * 0.77)
    var y_start := int(height * 0.30)
    var y_end := int(height * 0.72)
    for y in range(y_start, y_end, 6):
        for x in range(x_start, x_end, 6):
            var pixel := image.get_pixel(x, y)
            var luma := pixel.get_luminance()
            panel_min_luma = minf(panel_min_luma, luma)
            panel_max_luma = maxf(panel_max_luma, luma)
            if pixel.b > pixel.r * 1.10 or pixel.r > pixel.b * 1.18:
                accent_pixels += 1
            sampled_pixels += 1
    if panel_max_luma - panel_min_luma < 0.08:
        push_error("Upgrade Store lacks premium tonal separation")
        quit(1)
        return
    if accent_pixels < maxi(18, int(sampled_pixels * 0.006)):
        push_error("Upgrade Store lacks readable accent-color hierarchy")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var save_error := image.save_png(OUTPUT_PATH)
    if save_error != OK:
        push_error("Unable to save upgrade Store capture: %s" % error_string(save_error))
        quit(1)
        return

    print("GODOT_UPGRADE_STORE_FRAME_OK %dx%d choices=%d" % [width, height, scene.pending_upgrades.size()])
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
        "upgrade_detail_labels",
        "upgrade_card_panels",
        "UpgradeSubtitle",
        "shadow_size = 14",
        "card_style.border_width_top = 4",
        "upgrade_entry_tween",
        "Tween.TWEEN_PAUSE_PROCESS",
        "float(i) * 0.040"
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
        if not _require(projectile.contains("\"" + profile + "\""), "Projectile profile missing presentation identity: %s" % profile):
            return

    var projectile_contract := [
        ["trail_length", "Projectile trail length contract is missing"],
        ["trail_width", "Projectile trail width contract is missing"],
        ["core_radius", "Projectile core radius contract is missing"],
        ["impact_scale", "Projectile impact scale contract is missing"],
        ["_add_side_spark", "Scatter projectile side-spark presentation is missing"],
        ["_add_arc_accent", "Arc projectile accent presentation is missing"],
        ["_add_flame_core", "Inferno projectile core presentation is missing"],
        ["ProjectileCore", "Projectile core node identity is missing"],
        ["ProjectileTrail", "Projectile trail node identity is missing"],
        ["SHADOW_CASTING_SETTING_OFF", "Projectile visual geometry must remain shadow-free"],
        ["material.emission_energy_multiplier = 2.15", "Premium projectile trail emission contract regressed"],
        ["_add_side_spark(core_mat, -1.0)", "Left scatter spark identity is missing"],
        ["_add_side_spark(core_mat, 1.0)", "Right scatter spark identity is missing"]
    ]
    for requirement in projectile_contract:
        if not _require(projectile.contains(String(requirement[0])), String(requirement[1])):
            return
    if not _require(not projectile.contains("_add_side_spark(mat,"), "Legacy scatter spark material call returned"):
        return

    var player_contract := [
        ["weapon_profile", "Player weapon profile routing is missing"],
        ["weapon_tint", "Player weapon tint routing is missing"],
        ["weapon_profile)", "Projectile setup no longer forwards weapon profile"],
        ["PlayerMarkerRing", "Player marker ring presentation is missing"],
        ["PlayerAimTick", "Player aim tick presentation is missing"],
        ["MuzzleFlash", "Player muzzle flash presentation is missing"],
        ["_trigger_muzzle_flash()", "Muzzle flash is not triggered during fire"],
        ["PlayerPressureLocator", "Player pressure locator is missing"],
        ["PlayerPressureChevron_", "Player pressure chevrons are missing"],
        ["_update_player_marker_pressure(_pressure_target(target))", "Pressure marker is not routed through safe threat validation"],
        ["distance_squared_to(target.global_position) <= 8.41", "Close-pressure threshold contract regressed"],
        ["TacticalRig", "Player tactical rig presentation is missing"],
        ["TacticalBackplate", "Player backplate presentation is missing"],
        ["TacticalShoulderL", "Player left shoulder presentation is missing"],
        ["TacticalShoulderR", "Player right shoulder presentation is missing"],
        ["TacticalCore", "Player tactical core presentation is missing"],
        ["WeaponAccent", "Weapon accent presentation is missing"],
        ["_trigger_rifle_recoil()", "Rifle recoil presentation is not triggered"]
    ]
    for requirement in player_contract:
        if not _require(player.contains(String(requirement[0])), String(requirement[1])):
            return

    print("weapon_presentation_test: PASS")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
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

class SpatialProbe:
    extends Node3D
    var query_calls := 0
    var last_radius := 0.0

    func query_enemies_near(_position: Vector3, radius: float) -> Array:
        query_calls += 1
        last_radius = radius
        return []

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
    var sweep_enemy := ENEMY_SCRIPT.new()
    sweep_enemy.configure("shambler", 1.0, target)
    sweep_enemy.process_mode = Node.PROCESS_MODE_DISABLED
    parent.add_child(sweep_enemy)
    sweep_enemy.global_position = Vector3(0.0, 0.0, -0.55)
    await process_frame

    var swept_projectile := PROJECTILE_SCRIPT.new()
    swept_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    swept_projectile.setup(Vector3.ZERO, Vector3.FORWARD, 40.0, 10.0, Color.WHITE, "rail")
    parent.add_child(swept_projectile)
    await process_frame
    var swept_hits := swept_projectile._swept_hit_candidates(Vector3.ZERO, Vector3(0.0, 0.0, -0.80))
    if not swept_hits.has(sweep_enemy):
        push_error("Fast projectile swept collision missed an enemy crossed between physics samples")
        quit(1)
        return

    var impact_anchor := sweep_enemy.global_position + Vector3(0.0, 0.55, 0.0)
    if impact_anchor.distance_to(sweep_enemy.global_position) < 0.50:
        push_error("Swept hit impact anchor lost its readable vertical offset")
        quit(1)
        return

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

    var spatial_probe := SpatialProbe.new()
    get_root().add_child(spatial_probe)
    current_scene = spatial_probe
    var spatial_projectile := PROJECTILE_SCRIPT.new()
    spatial_projectile.process_mode = Node.PROCESS_MODE_DISABLED
    spatial_probe.add_child(spatial_projectile)
    await process_frame
    spatial_projectile._enemies_near(Vector3.ZERO, 3.8)
    if spatial_probe.query_calls != 1 or not is_equal_approx(spatial_probe.last_radius, 3.8):
        push_error("Weapon protocol neighborhood queries did not route through scene spatial hash")
        quit(1)
        return
    print("Deadline Zero weapon protocol behavior: OK")
    quit(0)
```

## File: tests/world_target_lock_test.gd
```
extends SceneTree

# Production combat QA: only one aim marker, unlit/shadow-free, follows
# the actual selected enemy and disappears on death/combat shutdown.
func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var player := DZPlayer.new()
    player.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(player)
    await process_frame

    var marker := player.target_lock_marker
    if marker == null or marker.name != "TargetLockReticle" or marker.visible:
        push_error("World target marker must be built and hidden until an enemy is selected")
        quit(1)
        return
    if not marker.top_level or marker.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Target lock must stay in world space and out of mobile shadow maps")
        quit(1)
        return
    var mesh := marker.mesh as ArrayMesh
    if mesh == null or mesh.get_surface_count() != 1:
        push_error("Target reticle must be a single curved 3D surface")
        quit(1)
        return
    var mesh_arrays := mesh.surface_get_arrays(0)
    var vertices := mesh_arrays[Mesh.ARRAY_VERTEX] as PackedVector3Array
    if vertices.size() != 192:
        push_error("Target reticle lost its four segmented low-cost aiming arcs")
        quit(1)
        return
    var mat := marker.material_override as StandardMaterial3D
    if mat == null or not mat.emission_enabled or mat.transparency != BaseMaterial3D.TRANSPARENCY_ALPHA:
        push_error("World aim confirmation lost restrained unlit transparent shading")
        quit(1)
        return
    if mat.emission_energy_multiplier > 1.0:
        push_error("Target lock must not become a distracting neon visual effect")
        quit(1)
        return

    var first := DZEnemy.new()
    first.configure("runner", 1.0, player)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(3.0, 0.0, 2.0)
    await process_frame

    player._update_target_lock_marker(first, 0.016)
    if not marker.visible or marker.global_position.distance_to(Vector3(3.0, 0.070, 2.0)) > 0.005:
        push_error("Target lock failed to snap to the live autoaim enemy")
        quit(1)
        return

    first.global_position.x = 4.0
    player._update_target_lock_marker(first, 0.016)
    if marker.global_position.x <= 3.0 or marker.global_position.x >= 4.0:
        push_error("Target lock lost stable motion interpolation")
        quit(1)
        return

    var boss := DZEnemy.new()
    boss.configure("boss", 1.0, player)
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss)
    boss.global_position = Vector3(-5.0, 0.0, -2.0)
    await process_frame
    player._update_target_lock_marker(boss, 0.016)
    if marker.global_position.distance_to(Vector3(-5.0, 0.070, -2.0)) > 0.005 or marker.scale.x < 1.75:
        push_error("Switching target did not snap and scale the readable boss lock")
        quit(1)
        return

    boss.dead = true
    player._update_target_lock_marker(boss, 0.016)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Target lock retained a defeated boss")
        quit(1)
        return

    player._update_target_lock_marker(first, 0.016)
    player.set_combat_enabled(false)
    if marker.visible or player.target_lock_enemy != null:
        push_error("Combat shutdown left auto-aim confirmation visible")
        quit(1)
        return

    print("Deadline Zero world-space aim lock: OK (4 arcs, target motion, boss switch and death)")
    quit(0)
```

## File: tests/xp_orb_runtime_test.gd
```
extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    target.global_position = Vector3.ZERO
    root.add_child(target)

    var first := DZXpOrb.new()
    first.target = target
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(12.0, 0.18, 0.0)

    var second := DZXpOrb.new()
    second.target = target
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    second.global_position = Vector3(14.0, 0.18, 0.0)
    await process_frame

    var first_visual := first.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var second_visual := second.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var first_halo := first.get_node_or_null("XpOrbHalo") as MeshInstance3D
    var second_halo := second.get_node_or_null("XpOrbHalo") as MeshInstance3D
    if first_visual == null or second_visual == null or first_halo == null or second_halo == null:
        push_error("XP orb premium core/halo production visual is missing")
        quit(1)
        return
    if first_visual.mesh != second_visual.mesh:
        push_error("XP orbs must reuse one shared mesh resource")
        quit(1)
        return
    if first_visual.material_override != second_visual.material_override:
        push_error("XP orbs must reuse one shared core material resource")
        quit(1)
        return
    if first_halo.mesh != second_halo.mesh or first_halo.material_override != second_halo.material_override:
        push_error("XP orbs must reuse shared halo mesh/material resources")
        quit(1)
        return
    if first_halo.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb halo must remain shadow-free")
        quit(1)
        return
    var halo_mesh := first_halo.mesh as TorusMesh
    if halo_mesh == null or halo_mesh.rings > 16 or halo_mesh.ring_segments > 6:
        push_error("XP orb halo geometry budget regressed")
        quit(1)
        return
    if first_visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb visual must not cast mobile-costly dynamic shadows")
        quit(1)
        return

    var mesh := first_visual.mesh as SphereMesh
    if mesh == null or mesh.radial_segments > 12 or mesh.rings > 6:
        push_error("XP orb geometry budget regressed")
        quit(1)
        return

    var initial_distance := first.global_position.distance_to(target.global_position)
    first.age = DZXpOrb.FORCED_MAGNET_AGE
    var halo_scale_before := first_halo.scale.x
    first._process(0.20)
    var forced_distance := first.global_position.distance_to(target.global_position)
    if forced_distance >= initial_distance:
        push_error("Old XP orb did not force-magnet toward the player")
        quit(1)
        return
    if first_halo.scale.x <= halo_scale_before:
        push_error("XP orb halo did not expand when entering magnetized pickup state")
        quit(1)
        return

    second.age = 0.0
    var second_initial := second.global_position
    second._process(0.20)
    if second.global_position.distance_to(second_initial) > 0.05:
        push_error("Fresh distant XP orb should remain parked until magnet range/age threshold")
        quit(1)
        return

    print("Deadline Zero XP orb runtime: OK")
    quit(0)
```
