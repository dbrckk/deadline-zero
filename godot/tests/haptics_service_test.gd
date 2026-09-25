extends SceneTree

const HAPTICS := preload("res://scripts/Haptics.gd")

func _initialize() -> void:
    if HAPTICS.pattern_for("hit") <= 0:
        push_error("Hit haptic pattern is missing")
        quit(1)
        return
    if HAPTICS.pattern_for("critical") <= HAPTICS.pattern_for("hit"):
        push_error("Critical haptic should be stronger than regular hit")
        quit(1)
        return
    if HAPTICS.pattern_for("boss") <= HAPTICS.pattern_for("critical"):
        push_error("Boss haptic should be strongest combat pulse")
        quit(1)
        return
    if HAPTICS.pattern_for("none") != 0:
        push_error("Unknown haptic pattern should be silent")
        quit(1)
        return

    print("Deadline Zero haptics service: OK")
    quit(0)
