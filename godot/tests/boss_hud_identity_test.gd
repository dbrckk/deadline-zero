extends SceneTree

func _init() -> void:
    var hud_source := FileAccess.get_file_as_string("res://scripts/Hud.gd")
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")

    var hud_contract := [
        ["func show_boss(", "Boss HUD show contract is missing"],
        ["func set_boss_health(", "Boss HUD health update contract is missing"],
        ["PHASE II // ENRAGED", "Boss phase-II label is missing"],
        ["PHASE III // EXECUTE", "Boss phase-III label is missing"],
        ["boss_hp_bar", "Boss health bar is missing"],
        ["boss_hp_fill_style", "Boss health fill style is missing"],
        ["func pulse_boss_phase(", "Boss phase transition pulse is missing"],
        ["boss_phase_tween", "Boss phase transition tween is missing"],
        ["BossHealthBar", "Boss health bar node identity is missing"],
        ["add_theme_stylebox_override(\"fill\"", "Boss health bar fill theme is missing"],
        ["ratio <= 0.30", "Boss phase color threshold is missing"]
    ]
    for requirement in hud_contract:
        if not _require(hud_source.contains(String(requirement[0])), String(requirement[1])):
            return

    if not _require(enemy_source.contains("signal health_changed"), "Boss enemy health signal is missing"):
        return
    if not _require(enemy_source.contains("health_changed.emit(max(0.0, health), max_health)"), "Boss enemy health emission contract is missing"):
        return
    if not _require(main_source.contains("enemy.health_changed.connect(_on_boss_health_changed)"), "Boss health is not connected to HUD"):
        return
    if not _require(main_source.contains("hud.show_boss("), "Boss spawn does not reveal HUD"):
        return

    print("Godot boss HUD identity validation passed")
    quit(0)

func _require(condition: bool, message: String) -> bool:
    if condition:
        return true
    push_error(message)
    quit(1)
    return false
