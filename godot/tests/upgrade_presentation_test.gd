extends SceneTree

func _fail(message: String) -> void:
    push_error(message)
    quit(1)

func _init() -> void:
    var main_text := FileAccess.get_file_as_string("res://scripts/Main.gd")
    var hud_text := FileAccess.get_file_as_string("res://scripts/Hud.gd")

    var ids := ["damage", "rate", "speed", "health", "projectile", "multishot"]
    for id in ids:
        if not main_text.contains("\"id\":\"" + id + "\""):
            _fail("Upgrade pool is missing id: %s" % id)
            return
        if not hud_text.contains("\"" + id + "\""):
            _fail("HUD presentation is missing id: %s" % id)
            return

    var required_main := ["\"family\":\"OFFENSE\"", "\"family\":\"SURVIVAL\"", "\"family\":\"BARRAGE\""]
    for token in required_main:
        if not main_text.contains(token):
            _fail("Upgrade presentation is missing family token: %s" % token)
            return

    var required_hud := [
        "func _upgrade_glyph",
        "func _upgrade_color",
        "func _style_upgrade_card",
        "StyleBoxFlat.new()",
        "upgrade_family_labels",
        "upgrade_detail_labels"
    ]
    for token in required_hud:
        if not hud_text.contains(token):
            _fail("HUD upgrade presentation is missing token: %s" % token)
            return

    print("godot upgrade presentation test passed")
    quit(0)
