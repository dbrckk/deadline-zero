class_name DZInfernoBlastFx
extends Node3D

# Two lightweight world-space rings read as a radial blast rather than an
# unrelated flash on each enemy. The ring radius matches gameplay splash.
const DURATION := 0.30
const SCORCH_DURATION := 0.90
const MAX_ACTIVE := 8
const MIN_RADIUS := 0.50
const MAX_RADIUS := 3.50

static var _shared_ring_mesh: TorusMesh
static var _shared_material: ShaderMaterial
static var _shared_scorch_mesh: QuadMesh
static var _shared_scorch_material: ShaderMaterial

var origin := Vector3.ZERO
var blast_radius := 1.85
var age := 0.0
var opacity := 1.0
var wave: MeshInstance3D
var echo: MeshInstance3D
var scorch: MeshInstance3D

static func spawn_blast(parent: Node3D, at: Vector3, effect_radius: float) -> DZInfernoBlastFx:
    if parent == null or not is_instance_valid(parent) or not parent.is_inside_tree():
        return null
    if effect_radius < MIN_RADIUS or effect_radius > MAX_RADIUS:
        return null
    var active := 0
    for node in parent.get_tree().get_nodes_in_group("inferno_blast_waves"):
        if is_instance_valid(node) and not node.is_queued_for_deletion():
            active += 1
    if active >= MAX_ACTIVE:
        return null
    var effect := DZInfernoBlastFx.new()
    effect.origin = at
    effect.blast_radius = effect_radius
    parent.add_child(effect)
    return effect

func _ready() -> void:
    name = "InfernoWorldBlast"
    add_to_group("inferno_blast_waves")
    top_level = true
    global_position = Vector3(origin.x, 0.0, origin.z)

    wave = MeshInstance3D.new()
    wave.name = "BlastFront"
    wave.mesh = _ring_mesh()
    wave.material_override = _heat_material()
    wave.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    wave.position.y = 0.095
    wave.scale = Vector3.ONE * blast_radius * 0.23
    add_child(wave)
    wave.set_instance_shader_parameter("heat_opacity", 1.0)

    echo = MeshInstance3D.new()
    echo.name = "BlastAfterglow"
    echo.mesh = _ring_mesh()
    echo.material_override = _heat_material()
    echo.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    echo.position.y = 0.090
    echo.scale = Vector3.ONE * blast_radius * 0.14
    add_child(echo)
    echo.set_instance_shader_parameter("heat_opacity", 0.45)

    # A shared procedural ground-scorch decal keeps the impact readable after
    # the fast expanding rings vanish. No textures, emitters or point lights.
    scorch = MeshInstance3D.new()
    scorch.name = "ScorchResidue"
    scorch.mesh = _scorch_mesh()
    scorch.material_override = _scorch_material()
    scorch.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
    scorch.rotation_degrees.x = -90.0
    scorch.position.y = 0.064
    scorch.scale = Vector3(blast_radius, blast_radius, 1.0)
    add_child(scorch)
    scorch.set_instance_shader_parameter("scorch_opacity", 0.0)

static func _ring_mesh() -> TorusMesh:
    if _shared_ring_mesh != null:
        return _shared_ring_mesh
    _shared_ring_mesh = TorusMesh.new()
    _shared_ring_mesh.inner_radius = 0.875
    _shared_ring_mesh.outer_radius = 1.0
    _shared_ring_mesh.rings = 32
    _shared_ring_mesh.ring_segments = 4
    return _shared_ring_mesh

static func _heat_material() -> ShaderMaterial:
    if _shared_material != null:
        return _shared_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_add, cull_disabled, depth_draw_never;
instance uniform float heat_opacity = 1.0;

void fragment() {
    ALBEDO = vec3(1.0, 0.42, 0.11);
    EMISSION = vec3(1.0, 0.30, 0.055) * 6.0;
    ALPHA = heat_opacity * 0.95;
}
"""
    _shared_material = ShaderMaterial.new()
    _shared_material.shader = shader
    return _shared_material

static func _scorch_mesh() -> QuadMesh:
    if _shared_scorch_mesh != null:
        return _shared_scorch_mesh
    _shared_scorch_mesh = QuadMesh.new()
    _shared_scorch_mesh.size = Vector2(2.0, 2.0)
    return _shared_scorch_mesh

static func _scorch_material() -> ShaderMaterial:
    if _shared_scorch_material != null:
        return _shared_scorch_material
    var shader := Shader.new()
    shader.code = """
shader_type spatial;
render_mode unshaded, blend_mix, cull_disabled, depth_draw_never;
instance uniform float scorch_opacity = 1.0;

float hash21(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453);
}

void fragment() {
    vec2 p = UV * 2.0 - 1.0;
    float r = length(p);
    float angle = atan(p.y, p.x);
    float grain = hash21(floor(p * 31.0));
    float crackle = sin(angle * 19.0 + r * 23.0 + sin(angle * 7.0) * 1.4);
    float ragged_edge = 0.035 * sin(angle * 13.0 + sin(angle * 5.0) * 2.0);
    float footprint = 1.0 - smoothstep(0.70, 0.96 + ragged_edge, r);
    float embers = pow(max(crackle, 0.0), 17.0)
                 * smoothstep(0.12, 0.38, r) * (1.0 - smoothstep(0.67, 0.91, r));
    float hot_rim = (smoothstep(0.48, 0.71, r) - smoothstep(0.71, 0.89, r))
                  * (0.38 + 0.62 * grain);
    float heat = clamp(embers * 0.85 + hot_rim * 0.55, 0.0, 1.0);
    ALBEDO = mix(vec3(0.015, 0.011, 0.016), vec3(0.24, 0.052, 0.019), heat);
    EMISSION = vec3(1.0, 0.16, 0.018) * heat * 1.75;
    ALPHA = scorch_opacity * footprint * clamp(
        0.36 + grain * 0.24 + heat * 0.30, 0.0, 0.92);
}
"""
    _shared_scorch_material = ShaderMaterial.new()
    _shared_scorch_material.shader = shader
    return _shared_scorch_material

func _process(delta: float) -> void:
    age += maxf(delta, 0.0)
    var fraction := clampf(age / DURATION, 0.0, 1.0)
    var ease_out := 1.0 - pow(1.0 - fraction, 2.3)
    opacity = pow(1.0 - fraction, 1.6)
    if wave != null:
        wave.scale = Vector3.ONE * blast_radius * lerpf(0.23, 1.0, ease_out)
        wave.set_instance_shader_parameter("heat_opacity", opacity)
    if echo != null:
        echo.scale = Vector3.ONE * blast_radius * lerpf(0.14, 0.88, ease_out)
        echo.set_instance_shader_parameter("heat_opacity", opacity * 0.42)
    if scorch != null:
        var burn_fraction := clampf(age / SCORCH_DURATION, 0.0, 1.0)
        var ignition := minf(1.0, age / 0.055)
        var fade := pow(1.0 - burn_fraction, 1.15)
        scorch.set_instance_shader_parameter("scorch_opacity", ignition * fade)
    if age >= SCORCH_DURATION:
        queue_free()
