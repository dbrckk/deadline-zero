extends SceneTree

func _init() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    assert(source.contains("hit_reaction_profile"), "Enemy must expose a hit reaction profile")
    assert(source.contains("_play_hit_reaction"), "Enemy damage must trigger a hit reaction")
    assert(source.contains("normal_hit"), "Normal enemies need a readable hit reaction")
    assert(source.contains("elite_hit"), "Elites need a heavier hit reaction")
    assert(source.contains("boss_hit"), "Bosses need a restrained but weighty hit reaction")
    assert(source.contains("hit_flash_material"), "Hit reaction must include a material flash without dynamic lights")
    assert(not source.contains("hit_reaction_light"), "Hit reactions must not allocate per-hit dynamic lights")
    print("enemy_hit_reaction_test: PASS")
    quit()
