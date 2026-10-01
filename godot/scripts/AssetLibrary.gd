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
    var root := instantiate_scene(ZOMBIE_CHUBBY if kind in ["brute", "elite"] else ZOMBIE_BASIC)
    var tint := Color(0.82, 0.92, 0.80)
    match kind:
        "runner": tint = Color(0.72, 1.00, 0.74)
        "charger": tint = Color(1.00, 0.68, 0.48)
        "harrier": tint = Color(0.58, 0.88, 1.00)
        "regenerator": tint = Color(0.58, 1.00, 0.68)
        "brute": tint = Color(0.92, 0.56, 0.46)
        "elite": tint = Color(0.78, 0.58, 1.00)
        "boss": tint = Color(1.00, 0.72, 0.44)
    _grade_mesh_tree(root, tint, 0.74, 0.0)
    return root

static func rifle() -> Node3D:
    return instantiate_scene(RIFLE)

static func barrier() -> Node3D:
    var root := instantiate_scene(BARRIER)
    _grade_mesh_tree(root, Color(0.22, 0.28, 0.32), 0.92, 0.06)
    _add_barrier_hazard_signature(root)
    return root

static func barrel() -> Node3D:
    return instantiate_scene(BARREL)

static func pallet() -> Node3D:
    return instantiate_scene(PALLET)

static func street_lights() -> Node3D:
    return instantiate_scene(STREET_LIGHTS)

static func traffic_cone() -> Node3D:
    return instantiate_scene(TRAFFIC_CONE)

static func trash_bag() -> Node3D:
    return instantiate_scene(TRASH_BAG)

static func street_crack() -> Node3D:
    return instantiate_scene(STREET_CRACK)

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
