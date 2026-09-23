extends SceneTree

const PLAYER_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/player_matt.gltf"
const ZOMBIE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_basic.gltf"
const BRUTE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/zombie_chubby.gltf"
const RIFLE_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/rifle.gltf"
const BARRIER_PATH := "res://assets/third_party/quaternius/zombie_apocalypse/plastic_barrier.gltf"

func _initialize() -> void:
    _assert_scene(PLAYER_PATH, ["Idle_Gun", "Run_Gun", "HitReact", "Death"])
    _assert_scene(ZOMBIE_PATH, ["Walk", "Run_Arms", "HitReact", "Death"])
    _assert_scene(BRUTE_PATH, ["Walk", "Run_Arms", "Death"])
    _assert_scene(RIFLE_PATH, [])
    _assert_scene(BARRIER_PATH, [])
    print("Deadline Zero authored 3D asset validation: OK")
    quit(0)

func _assert_scene(path: String, required_animations: Array[String]) -> void:
    var resource := load(path) as PackedScene
    if resource == null:
        push_error("Failed to import authored scene: " + path)
        quit(1)
        return
    var instance := resource.instantiate()
    if instance == null:
        push_error("Failed to instantiate authored scene: " + path)
        quit(1)
        return
    if not required_animations.is_empty():
        var player := _find_animation_player(instance)
        if player == null:
            push_error("No AnimationPlayer found in: " + path)
            instance.free()
            quit(1)
            return
        for animation_name in required_animations:
            if not player.has_animation(animation_name):
                push_error("Missing animation %s in %s" % [animation_name, path])
                instance.free()
                quit(1)
                return
    instance.free()

func _find_animation_player(node: Node) -> AnimationPlayer:
    if node is AnimationPlayer:
        return node as AnimationPlayer
    for child in node.get_children():
        var found := _find_animation_player(child)
        if found != null:
            return found
    return null
