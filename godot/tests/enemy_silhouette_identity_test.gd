extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var palette_samples := {}
    for palette_kind in ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var visual := DZAssetLibrary.enemy(palette_kind)
        if visual == null:
            push_error("Missing authored visual for palette kind %s" % palette_kind)
            quit(1)
            return
        var mesh_instance := visual as MeshInstance3D
        if mesh_instance == null:
            mesh_instance = visual.find_child("*", true, false) as MeshInstance3D
        if mesh_instance == null or not mesh_instance.material_override is BaseMaterial3D:
            push_error("Enemy palette grading missing for %s" % palette_kind)
            quit(1)
            return
        var material := mesh_instance.material_override as BaseMaterial3D
        if material.albedo_texture == null:
            push_error("Enemy palette grading must preserve authored atlas for %s" % palette_kind)
            quit(1)
            return
        palette_samples[palette_kind] = material.albedo_color
        visual.queue_free()

    if (palette_samples["runner"] as Color).is_equal_approx(palette_samples["charger"] as Color):
        push_error("Runner and charger must not collapse to the same authored palette")
        quit(1)
        return
    if (palette_samples["harrier"] as Color).is_equal_approx(palette_samples["brute"] as Color):
        push_error("Harrier and brute must retain distinct authored palettes")
        quit(1)
        return

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
    }

    for kind in expected.keys():
        var enemy := ENEMY_SCRIPT.new()
        root.add_child(enemy)
        enemy.kind = kind
        enemy.call_deferred("_add_archetype_signature")
        await process_frame
        for node_name in expected[kind]:
            if enemy.get_node_or_null(node_name) == null:
                push_error("Missing %s signature node %s" % [kind, node_name])
                quit(1)
                return
        if kind == "runner":
            var blade := enemy.get_node_or_null("RunnerBladeL") as MeshInstance3D
            var blade_mesh := blade.mesh as BoxMesh if blade != null else null
            if blade_mesh == null or blade_mesh.size.z < 0.35 or absf(blade.position.x) < 0.40:
                push_error("Runner signature must remain wide and readable in top-down projection")
                quit(1)
                return
        enemy.queue_free()

    print("Deadline Zero enemy silhouette identity: OK")
    quit(0)
