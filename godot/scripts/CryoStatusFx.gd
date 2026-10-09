class_name DZCryoStatusFx
extends Node3D

# Communicates actual movement slow on its victim, not only projectile color.
# All affected enemies reuse the same meshes and additive shader; each frost
# crown uses one torus and one low-poly MultiMesh without lights or particles.
const MAX_ACTIVE := 24
const CRYSTAL_COUNT := 8

static var _shared_ring_mesh: TorusMesh
static var _shared_crystal_mesh: BoxMesh
static var _shared_material: ShaderMaterial

var tracked_enemy: DZEnemy
var ring: MeshInstance3D
var crystals: MultiMeshInstance3D

static func attach_to(enemy: DZEnemy) -> DZCryoStatusFx:
    if enemy == null or not is_instance_valid(enemy) or not enemy.is_inside_tree():
        return null
    if enemy.dead or enemy.slow_left <= 0.0:
        return null
    var current := enemy.get_node_or_null("CryoStatusCrown") as DZCryoStatusFx
    if current != null and not current.is_queued_for_deletion():
        return current
    var active := 0
    for effect in enemy.get_tree().get_nodes_in_group("cryo_status_crowns"):
        if is_instance_valid(effect) and not effect.is_queued_for_deletion():
            active += 1
    if active >= MAX_ACTIVE:
        return null
    var effect := DZCryoStatusFx.new()
    effect.name = "CryoStatusCrown"
    effect.tracked_enemy = enemy
    enemy.add_child(effect)
    return effect

func _ready() -> void:
    add_to_group("cryo_status_crowns")
    var radius := 1.40 if tracked_enemy.kind == "boss" else (
        1.12 if tracked_enemy.kind in ["elite", "brute", "charger"] else 0.90
    )

    ring = MeshInstance3D.new()
    ring.name = "FrostFootprint"
    ring.mesh = _ring_mesh()
    ring.material_override = _frost_material()
    ring.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    ring.position.y = 0.075
    ring.scale = Vector3(radius, 1.0, radius)
    add_child(ring)

    crystals = MultiMeshInstance3D.new()
    crystals.name = "FrostCrystals"
    crystals.multimesh = _crystal_multimesh()
    crystals.material_override = _frost_material()
    crystals.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    crystals.scale = Vector3(radius, 1.0, radius)
    add_child(crystals)
    _update_opacity(1.0)

static func _ring_mesh() -> TorusMesh:
    if _shared_ring_mesh != null:
        return _shared_ring_mesh
    _shared_ring_mesh = TorusMesh.new()
    _shared_ring_mesh.inner_radius = 0.77
    _shared_ring_mesh.outer_radius = 0.88
    _shared_ring_mesh.rings = 24
    _shared_ring_mesh.ring_segments = 4
    return _shared_ring_mesh

static func _crystal_mesh() -> BoxMesh:
    if _shared_crystal_mesh != null:
        return _shared_crystal_mesh
    _shared_crystal_mesh = BoxMesh.new()
    _shared_crystal_mesh.size = Vector3(0.075, 0.30, 0.095)
    return _shared_crystal_mesh

static func _crystal_multimesh() -> MultiMesh:
    var multi := MultiMesh.new()
    multi.transform_format = MultiMesh.TRANSFORM_3D
    multi.mesh = _crystal_mesh()
    multi.instance_count = CRYSTAL_COUNT
    for i in range(CRYSTAL_COUNT):
        var angle := TAU * float(i) / float(CRYSTAL_COUNT)
        var yaw := Basis(Vector3.UP, angle)
        var tilt := Basis(Vector3.FORWARD, 0.20 if i % 2 == 0 else -0.20)
        var pos := Vector3(cos(angle) * 0.69, 0.19, sin(angle) * 0.69)
        multi.set_instance_transform(i, Transform3D(yaw * tilt, pos))
    return multi

static func _frost_material() -> ShaderMaterial:
    if _shared_material != null:
        return _shared_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_add, cull_disabled, depth_draw_never;
instance uniform float frost_opacity = 1.0;

void fragment() {
    vec3 ice = mix(vec3(0.04, 0.58, 0.95), vec3(0.58, 0.95, 1.0), UV.y);
    ALBEDO = ice * 0.16;
    EMISSION = ice * 2.8;
    ALPHA = frost_opacity * (0.56 + 0.12 * sin(TIME * 6.0));
}
"""
    _shared_material = ShaderMaterial.new()
    _shared_material.shader = shader
    return _shared_material

func _update_opacity(value: float) -> void:
    if ring != null:
        ring.set_instance_shader_parameter("frost_opacity", value)
    if crystals != null:
        crystals.set_instance_shader_parameter("frost_opacity", value)

func _process(delta: float) -> void:
    if tracked_enemy == null or not is_instance_valid(tracked_enemy) or tracked_enemy.dead or tracked_enemy.slow_left <= 0.0:
        queue_free()
        return
    # Victim-controlled lifetime also handles refreshed Cryo hits without
    # duplicating geometry or letting stale marks survive after thawing.
    var thaw := clampf(tracked_enemy.slow_left / 0.25, 0.0, 1.0)
    _update_opacity(thaw)
    if crystals != null:
        crystals.rotation.y += maxf(delta, 0.0) * 0.55
