class_name DZBurnStatusFx
extends Node3D

# True Inferno DoT is visible on its victim in world space. The seven curved
# flame ribbons are baked into *one* 14-triangle mesh surface: one shared
# geometry resource and one shared additive shader for every burning enemy.
# No particle emitters, point lights, transparent overdraw stacks or shadows.
const MAX_ACTIVE := 18
const FLAME_COUNT := 7
const THAW_SECONDS := 0.30

static var _shared_flame_mesh: ArrayMesh
static var _shared_material: ShaderMaterial

var tracked_enemy: DZEnemy
var flames: MeshInstance3D

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
    flames = MeshInstance3D.new()
    flames.name = "InfernoEmbers"
    flames.mesh = _flame_mesh()
    flames.material_override = _flame_material()
    flames.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    var size := 1.62 if tracked_enemy.kind == "boss" else (
        1.22 if tracked_enemy.kind in ["elite", "brute", "charger"] else 0.93
    )
    flames.scale = Vector3.ONE * size
    add_child(flames)
    _update_opacity(1.0)

static func _flame_mesh() -> ArrayMesh:
    if _shared_flame_mesh != null:
        return _shared_flame_mesh
    var vertices := PackedVector3Array()
    var uv := PackedVector2Array()
    for i in range(FLAME_COUNT):
        var angle := TAU * float(i) / float(FLAME_COUNT)
        var outward := Vector3(cos(angle), 0.0, sin(angle))
        var sideways := Vector3(-outward.z, 0.0, outward.x)
        # Move flames outside imported body volumes. The flame tips remain
        # visible from the real top-down gameplay camera, not hidden in torsos.
        var radius := 0.79 if i % 2 == 0 else 0.92
        var height_scale := 0.76 if i % 3 == 0 else (0.96 if i % 3 == 1 else 0.86)
        var width := 0.50 * height_scale
        var height := 1.40 * height_scale
        var center := outward * radius + Vector3.UP * (0.90 * height_scale)
        var bottom_l := center - sideways * width * 0.5 - Vector3.UP * height * 0.5
        var bottom_r := center + sideways * width * 0.5 - Vector3.UP * height * 0.5
        var top_l := center - sideways * width * 0.5 + Vector3.UP * height * 0.5
        var top_r := center + sideways * width * 0.5 + Vector3.UP * height * 0.5
        for point in [bottom_l, top_l, bottom_r, bottom_r, top_l, top_r]:
            vertices.append(point)
        for texel in [
            Vector2(0.0, 0.0), Vector2(0.0, 1.0), Vector2(1.0, 0.0),
            Vector2(1.0, 0.0), Vector2(0.0, 1.0), Vector2(1.0, 1.0)
        ]:
            uv.append(texel)
    var arrays := []
    arrays.resize(Mesh.ARRAY_MAX)
    arrays[Mesh.ARRAY_VERTEX] = vertices
    arrays[Mesh.ARRAY_TEX_UV] = uv
    _shared_flame_mesh = ArrayMesh.new()
    _shared_flame_mesh.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)
    return _shared_flame_mesh

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
    ALPHA = burn_opacity * body * foot * crown * flicker * 0.84;
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
    # Refreshed hits renew the source debuff; the visual follows its owner
    # rather than replacing geometry or running an unrelated fixed timer.
    _update_opacity(clampf(tracked_enemy.burn_left / THAW_SECONDS, 0.0, 1.0))
