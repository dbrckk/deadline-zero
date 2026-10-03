extends SceneTree

func _initialize() -> void:
    var source := FileAccess.get_file_as_string("res://scripts/Enemy.gd")
    var required := {
        "hit_reaction_profile": "Enemy must expose a hit reaction profile",
        "_play_hit_reaction": "Enemy damage must trigger a hit reaction",
        "normal_hit": "Normal enemies need a readable hit reaction",
        "elite_hit": "Elites need a heavier hit reaction",
        "boss_hit": "Bosses need a restrained but weighty hit reaction",
        "hit_flash_material": "Hit reaction must include a material flash without dynamic lights",
        "separation_radius := 2.05": "Heavy melee bodies need a wider anti-stack separation radius",
        "separation_radius := 2.05 if kind in": "Enemy movement must retain archetype-aware separation",
        "tangent_weight := crowd_pressure * 0.72": "Close melee pressure must fan bodies around the survivor"
    }
    for token in required:
        if not source.contains(token):
            push_error(required[token])
            quit(1)
            return
    if source.contains("hit_reaction_light"):
        push_error("Hit reactions must not allocate per-hit dynamic lights")
        quit(1)
        return
    print("enemy_hit_reaction_test: PASS")
    quit(0)
