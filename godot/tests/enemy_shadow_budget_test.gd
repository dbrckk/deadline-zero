extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var normal_meshes := 0
    var hero_body_casters := 0
    for enemy_kind in ["shambler", "runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var enemy := DZEnemy.new()
        enemy.kind = enemy_kind
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        root.add_child(enemy)
        await process_frame

        var body := enemy.get_node_or_null("Visual") as Node3D
        var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
        var hit_flash := enemy.get_node_or_null("HitFlash") as MeshInstance3D
        if body == null or shadow == null or hit_flash == null:
            push_error("Shadow budget fixture missing authored body/contact shadow/hit flash: %s" % enemy_kind)
            quit(1)
            return
        if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Contact shadow must never cast into the directional shadow map")
            quit(1)
            return
        if hit_flash.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Hit flash must never cast a dynamic shadow")
            quit(1)
            return

        var body_mesh_count := 0
        var body_casters := 0
        for node in enemy.find_children("*", "MeshInstance3D", true, false):
            var mesh := node as MeshInstance3D
            if mesh == null:
                continue
            var is_body := mesh == body or body.is_ancestor_of(mesh)
            if is_body:
                body_mesh_count += 1
                if mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    body_casters += 1
            elif mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("FX/signature mesh casts dynamic shadows: %s/%s" % [enemy_kind, mesh.name])
                quit(1)
                return

        if body_mesh_count == 0:
            push_error("Enemy must retain real 3D authored body geometry: %s" % enemy_kind)
            quit(1)
            return
        if enemy_kind in ["boss", "elite"]:
            if body_casters == 0:
                push_error("Hero threat has no dynamic body shadow: %s" % enemy_kind)
                quit(1)
                return
            hero_body_casters += body_casters
        else:
            if body_casters != 0:
                push_error("Swarm body is still a dynamic shadow caster: %s" % enemy_kind)
                quit(1)
                return
            normal_meshes += body_mesh_count

        enemy.queue_free()
        await process_frame

    if normal_meshes < 6 or hero_body_casters < 2:
        push_error("Shadow-budget regression fixture did not exercise body meshes")
        quit(1)
        return
    print("Deadline Zero swarm shadow budget: OK, shadow-free regular body meshes=%d, elite/boss body casters=%d" % [normal_meshes, hero_body_casters])
    quit(0)
