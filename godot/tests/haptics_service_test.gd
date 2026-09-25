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

    if HAPTICS.event_for_impact(false, false, false) != "hit":
        push_error("Regular impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(true, false, false) != "critical":
        push_error("Critical impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(false, true, false) != "critical":
        push_error("Kill impact haptic mapping is incorrect")
        quit(1)
        return
    if HAPTICS.event_for_impact(false, false, true) != "boss":
        push_error("Boss impact haptic mapping is incorrect")
        quit(1)
        return

    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if not main_source.contains("HAPTICS.pulse(HAPTICS.event_for_impact"):
        push_error("Main impact path is not wired to combat haptics")
        quit(1)
        return

    print("Deadline Zero haptics service: OK")
    quit(0)
