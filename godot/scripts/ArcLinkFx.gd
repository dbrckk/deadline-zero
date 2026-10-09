class_name DZArcLinkFx
extends Node3D

# Actual connected secondary-target feedback for the Arc weapon protocol.
# Each bolt is one unlit ArrayMesh, with three layered ribbons and no
# particles, physics bodies, dynamic lights, or per-instance materials.
const DURATION := 0.16
const MAX_ACTIVE := 12
const MIN_LENGTH := 0.18
const MAX_LENGTH := 7.5
const SEGMENTS := 6
const BAND_WIDTHS := [0.15, 0.075, 0.025]
const BAND_COLORS := [
    Color(0.25, 0.12, 0.92, 0.18),
    Color(0.54, 0.35, 1.0, 0.70),
    Color(0.90, 0.89, 1.0, 1.0)
]

static var _shared_material: ShaderMaterial

var start_at := Vector3.ZERO
var end_at := Vector3.ZERO
var branch_index := 0
var age := 0.0
var opacity := 1.0
var ribbon: MeshInstance3D

static func spawn_link(parent: Node3D, start: Vector3, finish: Vector3, branch: int = 0) -> DZArcLinkFx:
    if parent == null or not is_instance_valid(parent) or not parent.is_inside_tree():
        return null
    var distance := start.distance_to(finish)
    if distance < MIN_LENGTH or distance > MAX_LENGTH:
        return null
    var active := 0
    for link in parent.get_tree().get_nodes_in_group("arc_chain_links"):
        if is_instance_valid(link) and not link.is_queued_for_deletion():
            active += 1
    if active >= MAX_ACTIVE:
        return null
    var effect := DZArcLinkFx.new()
    effect.start_at = start
    effect.end_at = finish
    effect.branch_index = branch
    parent.add_child(effect)
    return effect

func _ready() -> void:
    name = "ArcChainLink"
    add_to_group("arc_chain_links")
    top_level = true
    global_position = start_at

    ribbon = MeshInstance3D.new()
    ribbon.name = "ArcRibbon"
    ribbon.mesh = _create_bolt_mesh(end_at - start_at, branch_index)
    ribbon.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    ribbon.material_override = _arc_material()
    add_child(ribbon)
    ribbon.set_instance_shader_parameter("chain_opacity", 1.0)

static func _arc_material() -> ShaderMaterial:
    if _shared_material != null:
        return _shared_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_add, cull_disabled, depth_draw_never;
instance uniform float chain_opacity = 1.0;

void fragment() {
    ALBEDO = COLOR.rgb * 0.10;
    EMISSION = COLOR.rgb * 3.8;
    ALPHA = COLOR.a * chain_opacity;
}
"""
    _shared_material = ShaderMaterial.new()
    _shared_material.shader = shader
    return _shared_material

static func _create_bolt_mesh(displacement: Vector3, branch: int) -> ArrayMesh:
    var side := displacement.cross(Vector3.UP).normalized()
    if side.length_squared() < 0.001:
        side = Vector3.RIGHT
    var control := PackedVector3Array()
    for step in range(SEGMENTS + 1):
        var t := float(step) / float(SEGMENTS)
        var wobble := 0.0
        if step > 0 and step < SEGMENTS:
            var direction_sign := 1.0 if (step + branch) % 2 == 0 else -1.0
            wobble = direction_sign * (0.055 + 0.045 * float((step + branch) % 3))
        var lift := 0.07 * sin(t * PI)
        control.append(displacement * t + side * wobble + Vector3.UP * lift)

    var vertices := PackedVector3Array()
    var vertex_colors := PackedColorArray()
    for band in range(BAND_WIDTHS.size()):
        var width: float = BAND_WIDTHS[band]
        var tint: Color = BAND_COLORS[band]
        for segment in range(SEGMENTS):
            var p0 := control[segment]
            var p1 := control[segment + 1]
            # Each polygon spans a local side vector perpendicular to the bolt.
            # Endpoints taper without changing the contact anchors.
            var w0 := width * (0.72 if segment == 0 else 1.0)
            var w1 := width * (0.72 if segment == SEGMENTS - 1 else 1.0)
            var a := p0 - side * w0 * 0.5
            var b := p0 + side * w0 * 0.5
            var c := p1 - side * w1 * 0.5
            var d := p1 + side * w1 * 0.5
            for point in [a, c, b, b, c, d]:
                vertices.append(point)
                vertex_colors.append(tint)
    var arrays := []
    arrays.resize(Mesh.ARRAY_MAX)
    arrays[Mesh.ARRAY_VERTEX] = vertices
    arrays[Mesh.ARRAY_COLOR] = vertex_colors
    var result := ArrayMesh.new()
    result.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)
    return result

func _process(delta: float) -> void:
    age += maxf(0.0, delta)
    var fraction := clampf(age / DURATION, 0.0, 1.0)
    opacity = pow(1.0 - fraction, 1.5)
    if ribbon != null:
        ribbon.set_instance_shader_parameter("chain_opacity", opacity)
    if age >= DURATION:
        queue_free()
