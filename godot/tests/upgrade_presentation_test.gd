extends SceneTree

func _initialize() -> void:
    var main_text := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var hud_text := FileAccess.get_file_as_string("res://scripts/Hud.gd")

    var ids := ["damage", "rate", "speed", "health", "projectile", "multishot"]
    for id in ids:
        if not main_text.contains("\"id\":\"" + id + "\""):
            push_error("Missing upgrade id in Main.gd: %s" % id)
            quit(1)
            return
        if not hud_text.contains("\"" + id + "\""):
            push_error("Missing upgrade presentation in Hud.gd: %s" % id)
            quit(1)
            return

    var required_main := ["\"family\":\"OFFENSE\"", "\"family\":\"SURVIVAL\"", "\"family\":\"BARRAGE\""]
    for token in required_main:
        if not main_text.contains(token):
            push_error("Missing upgrade family token: %s" % token)
            quit(1)
            return

    var required_hud := ["func _upgrade_glyph", "func _style_upgrade_card", "StyleBoxFlat.new()", "upgrade_family_labels", "upgrade_detail_labels"]
    for token in required_hud:
        if not hud_text.contains(token):
            push_error("Missing upgrade presentation token: %s" % token)
            quit(1)
            return

    print("godot upgrade presentation test passed")
    quit(0)
