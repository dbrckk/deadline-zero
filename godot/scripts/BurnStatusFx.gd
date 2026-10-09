class_name DZBurnStatusFx
extends Node3D

# Burning enemies carry their actual Inferno damage-over-time state in world
# space. Seven shader-animated flame ribbons share one mesh, one MultiMesh,
# and one material across the horde; no particles, lights, or shadow maps.
const MAX_ACTIVE := 18
const FLAME_COUNT := 7
const THAW_SECONDS := 0.30

static var _shared_flame_mesh: QuadMesh
static var _shared_multimesh: MultiMesh
static var _shared_material: ShaderMaterial

var tracked_enemy: DZEnemy
var flames: MultiMeshInstance3D

static func attach_to(enemy: DZEnemy) -> DZBurnStatusFx:
    if enemy == null or not is_instance_valid(enemy) or not enemy.is_inside_tree():
        return null
    if enemy.dead or enemy.burn_left <= 0.0 or enemy.burn_dps <= 0.0:
        return null
    var existing := enemy.get_node_or_null("BurnStatusFlames") as DZBurnStatusFx
    if existing != null and not existing.is_queued_for_deletion():
        return existing
    var active := 0
    for node in enemy.get_tree().get_nodes_in_group("burn_status_flames"):
        if is_instance_valid(node) and not node.is_queued_for_deletion():
            active += 1
    if active >= MAX_ACTIVE:
        return null
    var effect := DZBurnStatusFx.new()
    effect.name = "BurnStatusFlames"
    effect.tracked_enemy = enemy
    enemy.add_child(effect)
    return effect

func _ready() -> void:
    add_to_group("burn_status_flames")
    flames = MultiMeshInstance3D.new()
    flames.name = "InfernoEmbers"
    flames.multimesh = _flame_multimesh()
    flames.material_override = _flame_material()
    flames.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var size := 1.62 if tracked_enemy.kind == "boss" else (
        1.22 if tracked_enemy.kind in ["elite", "brute", "charger"] else 0.93
    )
    flames.scale = Vector3.ONE * size
    add_child(flames)
    _update_opacity(1.0)

static func _flame_mesh() -> QuadMesh:
    if _shared_flame_mesh != null:
        return _shared_flame_mesh
    _shared_flame_mesh = QuadMesh.new()
    _shared_flame_mesh.size = Vector2(0.43, 0.88)
    return _shared_flame_mesh

static func _flame_multimesh() -> MultiMesh:
    if _shared_multimesh != null:
        return _shared_multimesh
    _shared_multimesh = MultiMesh.new()
    _shared_multimesh.transform_format = MultiMesh.TRANSFORM_3D
    _shared_multimesh.mesh = _flame_mesh()
    _shared_multimesh.instance_count = FLAME_COUNT
    for i in range(FLAME_COUNT):
        var angle := TAU * float(i) / float(FLAME_COUNT)
        var radius := 0.26 if i % 2 == 0 else 0.34
        var scale_y := 0.76 if i % 3 == 0 else (0.96 if i % 3 == 1 else 0.86)
        var transform := Transform3D(
            Basis(Vector3.UP, angle + PI * 0.5).scaled(Vector3(0.9, scale_y, 1.0)),
            Vector3(cos(angle) * radius, 0.64 * scale_y, sin(angle) * radius)
        )
        _shared_multimesh.set_instance_transform(i, transform)
    return _shared_multimesh

static func _flame_material() -> ShaderMaterial:
    if _shared_material != null:
        return _shared_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_add, cull_disabled, depth_draw_never;
instance uniform float burn_opacity = 1.0;

void vertex() {
    float tip = clamp(UV.y, 0.0, 1.0);
    VERTEX.x += 0.075 * tip * tip * sin(TIME * 7.2 + MODEL_MATRIX[3].x * 8.0 + MODEL_MATRIX[3].z * 5.0);
}

void fragment() {
    float height = clamp(UV.y, 0.0, 1.0);
    float sway = 0.06 * sin(height * 9.0 + TIME * 8.5);
    float lateral = abs(UV.x - 0.5 + sway) * 2.0;
    float width = mix(0.92, 0.03, pow(height, 1.3));
    float body = 1.0 - smoothstep(width * 0.55, max(width, 0.06), lateral);
    float foot = smoothstep(0.0, 0.13, height);
    float crown = 1.0 - smoothstep(0.84, 1.0, height);
    float flicker = 0.86 + 0.14 * sin(TIME * 11.5 + UV.x * 18.0 + UV.y * 7.0);
    vec3 ember = mix(vec3(1.0, 0.105, 0.018), vec3(1.0, 0.68, 0.17), smoothstep(0.15, 0.79, height));
    ALBEDO = ember * 0.12;
    EMISSION = ember * (3.2 + 1.5 * height);
    ALPHA = burn_opacity * body * foot * crown * flicker * 0.64;
}
"""
    _shared_material = ShaderMaterial.new()
    _shared_material.shader = shader
    return _shared_material

func _update_opacity(value: float) -> void:
    if flames != null:
        flames.set_instance_shader_parameter("burn_opacity", clampf(value, 0.0, 1.0))

func _process(_delta: float) -> void:
    if tracked_enemy == null or not is_instance_valid(tracked_enemy) or tracked_enemy.dead:
        queue_free()
        return
    if tracked_enemy.burn_left <= 0.0 or tracked_enemy.burn_dps <= 0.0:
        queue_free()
        return
    # Burn can be refreshed by another Inferno hit. Its owner, not the visual,
    # controls lifetime so the flames never disappear during a renewed DoT.
    _update_opacity(clampf(tracked_enemy.burn_left / THAW_SECONDS, 0.0, 1.0))
