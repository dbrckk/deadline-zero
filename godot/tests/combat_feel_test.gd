extends SceneTree

func _initialize() -> void:
    _assert(DZCombatFeel.DEFAULT_HIT_FREEZE > 0.0, "default hit freeze must be positive")
    _assert(DZCombatFeel.CRITICAL_HIT_FREEZE > DZCombatFeel.DEFAULT_HIT_FREEZE, "critical must read stronger")
    _assert(DZCombatFeel.BOSS_HIT_FREEZE <= 0.050, "boss hit freeze must stay mobile-safe")
    _assert(DZCombatFeel.camera_kick(false, false, false) < DZCombatFeel.camera_kick(true, true, true),
        "important impacts must produce stronger camera feedback")
    _assert(DZCombatFeel.camera_kick(true, true, true) <= 0.16, "camera kick comfort bound")
    print("Deadline Zero Godot combat-feel profile: OK")
    quit(0)

func _assert(condition: bool, message: String) -> void:
    if condition:
        return
    push_error(message)
    quit(1)
