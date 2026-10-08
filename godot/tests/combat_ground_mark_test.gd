extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    var first := DZCombatGroundMark.spawn_mark(root, Vector3(2.0, 0.8, -3.0))
    if first == null or not first is MeshInstance3D:
        push_error("Kill ground mark did not instantiate as real 3D mesh")
        quit(1)
        return
    var mat := first.material_override as StandardMaterial3D
    if mat == null or mat.albedo_texture != load(DZCombatGroundMark.BLOOD_TEXTURE):
        push_error("Kill ground mark lost project-owned authored blood decal texture")
        quit(1)
        return
    if mat.transparency != BaseMaterial3D.TRANSPARENCY_ALPHA or mat.roughness < 0.85:
        push_error("Kill ground mark must use a restrained alpha-blended rough material")
        quit(1)
        return
    if first.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Ground marks must not cast directional shadows")
        quit(1)
        return
    if not first.mesh is QuadMesh or first.mesh.size != Vector2.ONE:
        push_error("Ground marks must use one lightweight quad")
        quit(1)
        return
    if absf(first.global_position.y - DZCombatGroundMark.GROUND_Y) > 0.0001:
        push_error("Kill marks must be placed on the ground, not at enemy's hit height")
        quit(1)
        return
    if first.fade_tween == null or not first.fade_tween.is_running():
        push_error("Kill ground mark must fade and release its node automatically")
        quit(1)
        return

    var boss_mark := DZCombatGroundMark.spawn_mark(root, Vector3(-2.0, 2.0, 3.0), true)
    var boss_mat := boss_mark.material_override as StandardMaterial3D
    if boss_mat == null or boss_mat.albedo_texture != load(DZCombatGroundMark.SCORCH_TEXTURE):
        push_error("Boss kill must use the authored scorch decal instead of blood")
        quit(1)
        return
    if boss_mark.scale.x < first.scale.x or boss_mark.mesh != first.mesh:
        push_error("Boss scorch must be larger and reuse the same quad mesh")
        quit(1)
        return

    for index in range(DZCombatGroundMark.MAX_ACTIVE + 9):
        var mark := DZCombatGroundMark.spawn_mark(root, Vector3(float(index), 0.0, 0.0))
        if mark == null:
            push_error("Dense-run ground mark emitter failed")
            quit(1)
            return
        if mark.mesh != first.mesh:
            push_error("Ground marks must reuse the single shared quad resource")
            quit(1)
            return
        if mark.get_child_count() != 0:
            push_error("Ground mark has unexpected mesh/light/particle children")
            quit(1)
            return
    await process_frame

    var marks := get_nodes_in_group("combat_ground_marks")
    if marks.size() != DZCombatGroundMark.MAX_ACTIVE:
        push_error("Kill ground mark cap regressed: %d / %d" % [marks.size(), DZCombatGroundMark.MAX_ACTIVE])
        quit(1)
        return

    if DZCombatGroundMark.spawn_mark(null, Vector3.ZERO) != null:
        push_error("Ground marks must reject missing parent")
        quit(1)
        return

    var source := FileAccess.get_file_as_string("res://scripts/Main.gd")
    if not source.contains("DZCombatGroundMark.spawn_mark(self, at, xp_value >= 30)"):
        push_error("Enemy death callback lost world-space kill mark integration")
        quit(1)
        return

    print("Deadline Zero authored mobile ground marks: OK (%d max visible)" % marks.size())
    quit(0)
