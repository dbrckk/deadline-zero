extends SceneTree

const MAIN_SCRIPT := preload("res://scripts/Main.gd")
const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var main := MAIN_SCRIPT.new()
    root.add_child(main)
    if not main.has_method("_select_priority_threat"):
        push_error("Off-screen threat priority selector is missing")
        quit(1)
        return

    var near_elite := ENEMY_SCRIPT.new()
    root.add_child(near_elite)
    near_elite.kind = "elite"
    near_elite.global_position = Vector3(2.0, 0.0, 0.0)

    var far_boss := ENEMY_SCRIPT.new()
    root.add_child(far_boss)
    far_boss.kind = "boss"
    far_boss.global_position = Vector3(20.0, 0.0, 0.0)

    var chosen = main._select_priority_threat([near_elite, far_boss], Vector3.ZERO)
    if chosen != far_boss:
        push_error("Boss must outrank a nearer elite in off-screen threat selection")
        quit(1)
        return

    var near_elite_two := ENEMY_SCRIPT.new()
    root.add_child(near_elite_two)
    near_elite_two.kind = "elite"
    near_elite_two.global_position = Vector3(3.0, 0.0, 0.0)
    var far_elite := ENEMY_SCRIPT.new()
    root.add_child(far_elite)
    far_elite.kind = "elite"
    far_elite.global_position = Vector3(11.0, 0.0, 0.0)

    chosen = main._select_priority_threat([far_elite, near_elite_two], Vector3.ZERO)
    if chosen != near_elite_two:
        push_error("Equal-priority threats must use nearest distance as tie-breaker")
        quit(1)
        return

    print("offscreen_threat_priority_test: PASS")
    quit(0)
