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
