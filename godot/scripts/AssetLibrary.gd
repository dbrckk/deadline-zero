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
    _grade_mesh_tree(root, tint, 0.82, 0.02)
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
    return instantiate_scene(BARREL)

static func pallet() -> Node3D:
    return instantiate_scene(PALLET)

static func street_lights() -> Node3D:
    var root := instantiate_scene(STREET_LIGHTS)
    _apply_street_light_industrial_material(root)
    return root

static func traffic_cone() -> Node3D:
    return instantiate_scene(TRAFFIC_CONE)

static func trash_bag() -> Node3D:
    return instantiate_scene(TRASH_BAG)

static func street_crack() -> Node3D:
    return instantiate_scene(STREET_CRACK)

static func _apply_street_light_industrial_material(root: Node3D) -> void:
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
        material.albedo_color = Color(0.040, 0.058, 0.068)
        material.metallic = 0.46
        material.roughness = 0.78
        mesh_instance.material_override = material
        mesh_instance.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_ON

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
