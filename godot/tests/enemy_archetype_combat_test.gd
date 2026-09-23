extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    assert(source.contains("attack_windup"))
    assert(source.contains("_begin_telegraphed_attack"))
    assert(source.contains("_resolve_telegraphed_attack"))
    assert(source.contains("kind == \"elite\""))
    assert(source.contains("kind == \"boss\""))
    assert(source.contains("target.take_damage(damage)"))
    assert(source.contains("_show_telegraph"))
    assert(source.contains("_spawn_attack_impact"))
    print("enemy_archetype_combat_test: PASS")
    quit()
