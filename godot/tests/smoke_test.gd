extends SceneTree

var frames := 0

func _initialize() -> void:
    var packed := load("res://scenes/Main.tscn") as PackedScene
    if packed == null:
        push_error("Unable to load Main.tscn")
        quit(1)
        return
    var game := packed.instantiate()
    root.add_child(game)
    current_scene = game
    if current_scene != game:
        push_error("Smoke test must install Main as current_scene")
        quit(1)
        return

func _process(_delta: float) -> bool:
    frames += 1
    if frames > 6:
        if root.get_child_count() <= 0:
            push_error("Godot smoke test has no instantiated game root")
            quit(1)
        else:
            print("Deadline Zero Godot 3D smoke test: OK")
            quit(0)
        return true
    return false
