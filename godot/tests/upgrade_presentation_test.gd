extends SceneTree

func _init() -> void:
    var main_text := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var hud_text := FileAccess.get_file_as_string("res://scripts/Hud.gd")

    var ids := ["damage", "rate", "speed", "health", "projectile", "multishot"]
    for id in ids:
        assert(main_text.contains("\"id\":\"" + id + "\""))
        assert(hud_text.contains("\"" + id + "\""))

    assert(main_text.contains("\"family\":\"OFFENSE\""))
    assert(main_text.contains("\"family\":\"SURVIVAL\""))
    assert(main_text.contains("\"family\":\"BARRAGE\""))
    assert(hud_text.contains("func _upgrade_glyph"))
    assert(hud_text.contains("func _upgrade_color"))
    assert(hud_text.contains("func _style_upgrade_card"))
    assert(hud_text.contains("StyleBoxFlat.new()"))
    assert(hud_text.contains("upgrade_family_labels"))
    assert(hud_text.contains("upgrade_detail_labels"))
    print("godot upgrade presentation test passed")
    quit(0)
