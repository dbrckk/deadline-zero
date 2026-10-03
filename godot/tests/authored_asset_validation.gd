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
    _assert_surface_preservation(DZAssetLibrary.player(), "player")
    _assert_surface_preservation(DZAssetLibrary.rifle(), "rifle")
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


func _assert_surface_preservation(instance: Node3D, label: String) -> void:
    if instance == null:
        push_error("Failed to instantiate graded authored asset: " + label)
        quit(1)
        return
    var mesh_nodes: Array[MeshInstance3D] = []
    if instance is MeshInstance3D:
        mesh_nodes.append(instance as MeshInstance3D)
    for node in instance.find_children("*", "MeshInstance3D", true, false):
        mesh_nodes.append(node as MeshInstance3D)
    if mesh_nodes.is_empty():
        push_error("Graded authored asset has no meshes: " + label)
        instance.free()
        quit(1)
        return

    for mesh_instance in mesh_nodes:
        if mesh_instance == null or mesh_instance.mesh == null:
            continue
        if mesh_instance.material_override != null:
            push_error("Graded authored asset flattened all surfaces: " + label)
            instance.free()
            quit(1)
            return
        for surface_index in range(mesh_instance.mesh.get_surface_count()):
            var source := mesh_instance.mesh.surface_get_material(surface_index)
            if source == null:
                continue
            var graded := mesh_instance.get_surface_override_material(surface_index)
            if graded == null:
                push_error("Missing per-surface graded material for %s surface %d" % [label, surface_index])
                instance.free()
                quit(1)
                return
            if source is BaseMaterial3D and graded is BaseMaterial3D:
                var source_material := source as BaseMaterial3D
                var graded_material := graded as BaseMaterial3D
                if source_material.albedo_texture != graded_material.albedo_texture:
                    push_error("Per-surface grading replaced authored texture for %s surface %d" % [label, surface_index])
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
