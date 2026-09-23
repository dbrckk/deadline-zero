extends SceneTree

func _init() -> void:
    var hud_source := FileAccess.get_file_as_string("res://scripts/Hud.gd")
    var enemy_source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var main_source := FileAccess.get_file_as_string("res://scripts/Main.gd")

    assert(hud_source.contains("func show_boss("))
    assert(hud_source.contains("func set_boss_health("))
    assert(hud_source.contains("PHASE II // ENRAGED"))
    assert(hud_source.contains("PHASE III // EXECUTE"))
    assert(hud_source.contains("boss_hp_bar"))
    assert(enemy_source.contains("signal health_changed"))
    assert(enemy_source.contains("health_changed.emit(max(0.0, health), max_health)"))
    assert(main_source.contains("enemy.health_changed.connect(_on_boss_health_changed)"))
    assert(main_source.contains("hud.show_boss("))

    print("Godot boss HUD identity validation passed")
    quit()
