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
    return instantiate_scene(ZOMBIE_CHUBBY if kind in ["brute", "elite"] else ZOMBIE_BASIC)

static func rifle() -> Node3D:
    return instantiate_scene(RIFLE)

static func barrier() -> Node3D:
    return instantiate_scene(BARRIER)

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

static func animation_player(root: Node) -> AnimationPlayer:
    if root == null:
        return null
    var direct := root.find_child("AnimationPlayer", true, false)
    return direct as AnimationPlayer
