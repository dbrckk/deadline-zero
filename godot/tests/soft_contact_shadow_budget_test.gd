extends SceneTree

# The dense horde should have softened authored 3D grounding, not hundreds
# of hard edged multi-triangle discs or individual light/shader allocations.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var shared_material: ShaderMaterial
    var total_shadows := 0
    var meshes_by_kind := {}
    var kinds := ["shambler", "runner", "charger", "harrier",
        "regenerator", "brute", "elite", "boss"]
    for kind in kinds:
        for sample_index in range(2):
            var enemy := DZEnemy.new()
            enemy.kind = kind
            enemy.process_mode = Node.PROCESS_MODE_DISABLED
            root.add_child(enemy)
            await process_frame

            var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
            if shadow == null or not shadow.mesh is QuadMesh:
                push_error("Enemy is missing one lightweight radial quad shadow: %s" % kind)
                quit(1)
                return
            if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Contact shadow became an actual shadow caster: %s" % kind)
                quit(1)
                return
            if shadow.rotation_degrees.x > -89.0 or shadow.rotation_degrees.x < -91.0:
                push_error("Contact shadow quad no longer lies horizontally on the ground: %s" % kind)
                quit(1)
                return
            var quad := shadow.mesh as QuadMesh
            if quad.size.x > 1.83 or quad.size.x < 0.69 or not is_equal_approx(quad.size.x, quad.size.y):
                push_error("Contact shadow radius escaped silhouette coverage budget: %s" % kind)
                quit(1)
                return

            var material := shadow.material_override as ShaderMaterial
            if material == null or material.shader == null:
                push_error("Shadow lost feathered shader material: %s" % kind)
                quit(1)
                return
            if shared_material == null:
                shared_material = material
                var shader_code := material.shader.code
                if not shader_code.contains("smoothstep(0.18, 1.0, radius)") or not shader_code.contains("ALPHA = feather * feather * shadow_opacity"):
                    push_error("Contact shadow changed to opaque or hard-edged rendering")
                    quit(1)
                    return
            elif shared_material != material:
                push_error("Enemy copies created individual contact shadow shader instances: %s" % kind)
                quit(1)
                return

            if meshes_by_kind.has(kind) and meshes_by_kind[kind] != quad:
                push_error("Same archetype reallocated identical shadow quad geometry: %s" % kind)
                quit(1)
                return
            meshes_by_kind[kind] = quad
            total_shadows += 1

            enemy.queue_free()
            await process_frame

    if total_shadows != 16 or meshes_by_kind.size() != kinds.size():
        push_error("Soft shadow budget did not exercise all enemy model classes")
        quit(1)
        return
    print("Deadline Zero soft 3D contact shadows: OK (16 fixtures, 8 archetypes, 1 shader)")
    quit(0)
