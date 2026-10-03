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
            var meshes := visual.find_children("*", "MeshInstance3D", true, false)
            mesh_instance = meshes[0] as MeshInstance3D if not meshes.is_empty() else null
        if mesh_instance == null or not mesh_instance.material_override is ShaderMaterial:
            push_error("Enemy palette grading shader missing for %s" % palette_kind)
            quit(1)
            return
        var material := mesh_instance.material_override as ShaderMaterial
        if material.get_shader_parameter("albedo_tex") == null:
            push_error("Enemy palette grading must preserve authored atlas for %s" % palette_kind)
            quit(1)
            return
        var highlight_floor := float(material.get_shader_parameter("highlight_floor"))
        if highlight_floor > 0.50:
            push_error("Enemy authored highlights are not compressed enough for %s" % palette_kind)
            quit(1)
            return
        palette_samples[palette_kind] = material.get_shader_parameter("body_tint") as Color
        visual.free()

    var runner_color: Color = palette_samples["runner"]
    var charger_color: Color = palette_samples["charger"]
    var harrier_color: Color = palette_samples["harrier"]
    var brute_color: Color = palette_samples["brute"]
    if runner_color.is_equal_approx(charger_color):
        push_error("Runner and charger must not collapse to the same authored palette")
        quit(1)
        return
    if harrier_color.is_equal_approx(brute_color):
        push_error("Harrier and brute must retain distinct authored palettes")
        quit(1)
        return

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "BruteEdgeL", "BruteEdgeR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossWingL", "BossWingR", "BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
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
        if kind == "brute":
            var plate := enemy.get_node_or_null("BrutePlateL") as MeshInstance3D
            var edge := enemy.get_node_or_null("BruteEdgeL") as MeshInstance3D
            var plate_material := plate.material_override as BaseMaterial3D if plate != null else null
            var edge_material := edge.material_override as BaseMaterial3D if edge != null else null
            if plate_material == null or plate_material.emission_enabled:
                push_error("Brute armor plate must remain dark/non-emissive")
                quit(1)
                return
            if edge_material == null or not edge_material.emission_enabled:
                push_error("Brute identity must move to a narrow emissive edge")
                quit(1)
                return
        if kind == "boss":
            var wing := enemy.get_node_or_null("BossWingL") as MeshInstance3D
            var wing_mesh := wing.mesh as BoxMesh if wing != null else null
            if wing_mesh == null or wing_mesh.size.z < 0.50 or absf(wing.position.x) < 0.65:
                push_error("Boss signature must remain broad and dominant in top-down projection")
                quit(1)
                return
        enemy.queue_free()

    print("Deadline Zero enemy silhouette identity: OK")
    quit(0)
