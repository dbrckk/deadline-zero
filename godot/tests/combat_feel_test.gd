extends SceneTree

func _initialize() -> void:
    _assert(DZCombatFeel.DEFAULT_HIT_FREEZE > 0.0, "default hit freeze must be positive")
    _assert(DZCombatFeel.CRITICAL_HIT_FREEZE > DZCombatFeel.DEFAULT_HIT_FREEZE, "critical must read stronger")
    _assert(DZCombatFeel.BOSS_HIT_FREEZE <= 0.050, "boss hit freeze must stay mobile-safe")
    _assert(DZCombatFeel.camera_kick(false, false, false) < DZCombatFeel.camera_kick(true, true, true),
        "important impacts must produce stronger camera feedback")
    _assert(DZCombatFeel.camera_kick(true, true, true) <= 0.16, "camera kick comfort bound")
    _assert(DZCombatFeel.impact_fov_pulse(false, false, false) == 0.0,
        "normal impacts must not pulse FOV")
    _assert(DZCombatFeel.impact_fov_pulse(true, false, false) > 0.0,
        "critical impacts must add restrained FOV punch")
    _assert(DZCombatFeel.impact_fov_pulse(true, true, true) <= 1.30,
        "impact FOV pulse comfort bound")
    var light_damage_kick := DZCombatFeel.damage_received_camera_kick(5.0, 100.0)
    var heavy_damage_kick := DZCombatFeel.damage_received_camera_kick(30.0, 100.0)
    _assert(light_damage_kick > 0.0, "received damage must produce camera feedback")
    _assert(heavy_damage_kick > light_damage_kick, "heavier received damage must read stronger")
    _assert(heavy_damage_kick <= 0.115, "received damage kick must remain mobile-safe")
    _assert(DZCombatFeel.damage_received_camera_kick(0.0, 100.0) == 0.0, "zero damage must not kick camera")
    _assert(is_equal_approx(DZCombatFeel.unscaled_delta(0.012, 0.12), 0.10),
        "hit-freeze timing must recover real delta under time scaling")
    _assert(DZCombatFeel.unscaled_delta(0.0, 0.12) == 0.0,
        "zero scaled delta must remain zero")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    _assert(main_source.contains("if hit_stop_enabled:"),
        "combat impact path must respect hit-stop comfort setting")
    _assert(main_source.contains("if camera_shake_enabled:"),
        "combat impact path must respect camera-shake comfort setting")
    _assert(main_source.contains("camera_kick = 0.0"),
        "disabling camera shake must clear active camera kick")
    _assert(main_source.contains("_clear_hit_freeze()"),
        "disabling hit stop must clear active freeze")
    print("Deadline Zero Godot combat-feel profile: OK")
    quit(0)

func _assert(condition: bool, message: String) -> void:
    if condition:
        return
    push_error(message)
    quit(1)
