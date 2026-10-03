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
        "_melee_standoff_distance": "Close melee pressure must preserve a player-readable standoff envelope",
        "_contact_attack_range": "Melee enemies must remain dangerous from the standoff envelope",
        "movement_speed_scale": "Close melee correction must settle rather than jitter at full chase speed"
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

    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var target := Node3D.new()
    root.add_child(target)
    var first := DZEnemy.new()
    first.configure("shambler", 1.0, target)
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    var second := DZEnemy.new()
    second.configure("shambler", 1.0, target)
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    await process_frame
    if first.hit_flash_visual == null or second.hit_flash_visual == null:
        push_error("Enemy hit-flash visual is missing")
        quit(1)
        return
    if first.hit_flash_visual.mesh != second.hit_flash_visual.mesh:
        push_error("Same-scale enemy hit flashes must reuse one mesh resource")
        quit(1)
        return
    if first.hit_flash_visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Enemy hit-flash geometry must not cast dynamic shadows")
        quit(1)
        return
    if first.hit_flash_material == second.hit_flash_material:
        push_error("Enemy hit-flash materials must stay instance-local for independent animation")
        quit(1)
        return
    print("enemy_hit_reaction_test: PASS")
    quit(0)
