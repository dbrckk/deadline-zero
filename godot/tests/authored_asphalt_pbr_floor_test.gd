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
    if floor.mesh.get_surface_count() != 1 or float(material.get_shader_parameter("asphalt_repeat")) < 12.0:
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
