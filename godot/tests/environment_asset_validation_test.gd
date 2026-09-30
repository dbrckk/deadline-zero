extends SceneTree

const ASSETS := preload("res://scripts/AssetLibrary.gd")

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var factories := {
        "barrel": Callable(ASSETS, "barrel"),
        "pallet": Callable(ASSETS, "pallet"),
        "street_lights": Callable(ASSETS, "street_lights"),
        "traffic_cone": Callable(ASSETS, "traffic_cone"),
        "trash_bag": Callable(ASSETS, "trash_bag"),
        "street_crack": Callable(ASSETS, "street_crack"),
    }

    for name in factories:
        var node := factories[name].call() as Node3D
        if node == null:
            push_error("Environment asset failed to instantiate: %s" % name)
            quit(1)
            return
        root.add_child(node)
        await process_frame
        if node.get_child_count() == 0:
            push_error("Environment asset has no imported scene content: %s" % name)
            quit(1)
            return
        node.queue_free()
        await process_frame

    print("Deadline Zero authored environment assets: OK")
    quit(0)
