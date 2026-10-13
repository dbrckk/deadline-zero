extends SceneTree

const ENEMY_SCRIPT := preload("res://scripts/Enemy.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)

    var palette_samples := {}
    var rim_samples := {}
    var shared_grade_shader: Shader
    for palette_kind in ["runner", "charger", "harrier", "regenerator", "brute", "elite", "boss"]:
        var visual := DZAssetLibrary.enemy(palette_kind)
        if visual == null:
            push_error("Missing authored visual for palette kind %s" % palette_kind)
            quit(1)
            return
        var mesh_nodes: Array[MeshInstance3D] = []
        if visual is MeshInstance3D:
            mesh_nodes.append(visual as MeshInstance3D)
        for node in visual.find_children("*", "MeshInstance3D", true, false):
            mesh_nodes.append(node as MeshInstance3D)
        if mesh_nodes.is_empty():
            push_error("Enemy authored visual has no meshes for %s" % palette_kind)
            quit(1)
            return

        var palette_material: ShaderMaterial
        var textured_surfaces := 0
        for mesh_instance in mesh_nodes:
            if mesh_instance == null or mesh_instance.mesh == null:
                continue
            if mesh_instance.material_override != null:
                push_error("Enemy grading must not flatten authored multi-surface materials for %s" % palette_kind)
                quit(1)
                return
            for surface_index in range(mesh_instance.mesh.get_surface_count()):
                var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                if source == null or source.albedo_texture == null:
                    continue
                textured_surfaces += 1
                var graded := mesh_instance.get_surface_override_material(surface_index)
                if not graded is ShaderMaterial:
                    push_error("Enemy surface grading shader missing for %s surface %d" % [palette_kind, surface_index])
                    quit(1)
                    return
                var material := graded as ShaderMaterial
                if material.get_shader_parameter("albedo_tex") != source.albedo_texture:
                    push_error("Enemy grading replaced an authored atlas for %s surface %d" % [palette_kind, surface_index])
                    quit(1)
                    return
                if shared_grade_shader == null:
                    shared_grade_shader = material.shader
                elif material.shader != shared_grade_shader:
                    push_error("Enemy grading must reuse one compiled shader across all authored surfaces")
                    quit(1)
                    return
                var highlight_floor := float(material.get_shader_parameter("highlight_floor"))
                if highlight_floor > 0.50:
                    push_error("Enemy authored highlights are not compressed enough for %s" % palette_kind)
                    quit(1)
                    return
                if palette_material == null:
                    palette_material = material
        if textured_surfaces == 0 or palette_material == null:
            push_error("Enemy palette grading found no authored textured surfaces for %s" % palette_kind)
            quit(1)
            return
        palette_samples[palette_kind] = palette_material.get_shader_parameter("body_tint") as Color
        rim_samples[palette_kind] = float(palette_material.get_shader_parameter("rim_strength"))
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

    if float(rim_samples.get("boss", 0.0)) <= float(rim_samples.get("elite", 0.0)):
        push_error("Boss shader rim must dominate elite silhouette")
        quit(1)
        return
    if float(rim_samples.get("elite", 0.0)) <= float(rim_samples.get("runner", 0.0)):
        push_error("Elite shader rim must remain stronger than runner")
        quit(1)
        return

    # Integration budget: independent horde spawns must share their immutable
    # imported surface grade. Different enemy archetypes must keep unique
    # color/roughness/rim parameters and never share the same material.
    # Two real imported bodies: shared atlas/shader, separate PBR wear grades.
    var runner_copy_a := DZAssetLibrary.enemy("runner", 0)
    var runner_copy_b := DZAssetLibrary.enemy("runner", 6)
    var brute_copy := DZAssetLibrary.enemy("brute")
    if runner_copy_a == null or runner_copy_b == null or brute_copy == null:
        push_error("Cannot instantiate enemy materials for horde sharing check")
        quit(1)
        return
    var runner_grade_a := _first_graded_enemy_material(runner_copy_a)
    var runner_grade_b := _first_graded_enemy_material(runner_copy_b)
    var brute_grade := _first_graded_enemy_material(brute_copy)
    if runner_grade_a == null or runner_grade_b == null or brute_grade == null:
        push_error("Cannot find textured PBR grades in the imported enemy horde")
        quit(1)
        return
    if runner_grade_a != runner_grade_b:
        push_error("Identical enemy archetypes must share their immutable graded atlas material")
        quit(1)
        return
    if runner_grade_a == brute_grade:
        push_error("Different enemy archetypes must preserve distinct palette materials")
        quit(1)
        return
    # Both source zombie GLTF materials use implicit alphaMode=OPAQUE. Writing
    # ALPHA forces the expensive transparent pass and breaks reliable depth.
    if runner_grade_a.shader.code.contains("ALPHA ="):
        push_error("Authored opaque zombie atlases incorrectly render in the transparent pipeline")
        quit(1)
        return
    if not runner_grade_a.shader.code.contains("instance uniform float wear_grade"):
        push_error("Zombie shader lost material-sharing per-instance weathering")
        quit(1)
        return
    var runner_mesh_a := runner_copy_a.find_children("*", "MeshInstance3D", true, false)[0] as MeshInstance3D
    var runner_mesh_b := runner_copy_b.find_children("*", "MeshInstance3D", true, false)[0] as MeshInstance3D
    if runner_mesh_a == null or runner_mesh_b == null:
        push_error("No real imported mesh for per-body wear grading")
        quit(1)
        return
    var wear_a := float(runner_mesh_a.get_instance_shader_parameter("wear_grade"))
    var wear_b := float(runner_mesh_b.get_instance_shader_parameter("wear_grade"))
    if absf(wear_a) > 0.001 or absf(wear_b - 1.0) > 0.001:
        push_error("Independent zombie meshes cannot retain their own wear grades")
        quit(1)
        return
    if runner_grade_a != runner_grade_b or runner_mesh_a.mesh != runner_mesh_b.mesh:
        push_error("Per-instance wear grade must not duplicate a shader material or authored mesh")
        quit(1)
        return
    runner_copy_a.free()
    runner_copy_b.free()
    brute_copy.free()

    var expected := {
        "shambler": ["SignatureBeacon"],
        "runner": ["RunnerBladeL", "RunnerBladeR", "SignatureBeacon"],
        "charger": ["SignatureBeacon"],
        "harrier": ["SignatureBeacon"],
        "regenerator": ["SignatureBeacon"],
        "brute": ["BrutePlateL", "BrutePlateR", "BruteEdgeL", "BruteEdgeR", "SignatureBeacon"],
        "elite": ["EliteFinL", "EliteFinR", "SignatureBeacon"],
        "boss": ["BossWingL", "BossWingR", "BossHornL", "BossHornR", "BossCore", "SignatureBeacon"]
    }

    var shared_shadow_material: Material
    var shadow_radii := {}
    for kind in expected.keys():
        var enemy := ENEMY_SCRIPT.new()
        enemy.kind = kind
        root.add_child(enemy)
        await process_frame
        for node_name in expected[kind]:
            if enemy.get_node_or_null(node_name) == null:
                push_error("Missing %s signature node %s" % [kind, node_name])
                quit(1)
                return
        var shadow := enemy.get_node_or_null("EnemyContactShadow") as MeshInstance3D
        var shadow_mesh := shadow.mesh as QuadMesh if shadow != null else null
        if enemy.spawn_reveal_tween == null:
            push_error("Enemy spawn did not initialize premium reveal motion for %s" % kind)
            quit(1)
            return
        if shadow == null or shadow_mesh == null:
            push_error("Missing mobile-safe contact shadow for %s" % kind)
            quit(1)
            return
        if shadow.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Contact shadow must not cast dynamic shadows for %s" % kind)
            quit(1)
            return
        if shadow_mesh.size.x < 0.69 or shadow_mesh.size.y != shadow_mesh.size.x:
            push_error("Contact shadow lost its low-poly radial ground footprint for %s" % kind)
            quit(1)
            return
        if absf(shadow.rotation_degrees.x + 90.0) > 0.01:
            push_error("Contact shadow quad no longer faces the arena floor for %s" % kind)
            quit(1)
            return
        var shadow_material := shadow.material_override
        if shadow_material == null or not shadow_material is ShaderMaterial:
            push_error("Contact shadow must use shared soft radial shader for %s" % kind)
            quit(1)
            return
        var soft_material := shadow_material as ShaderMaterial
        if soft_material.shader == null or not soft_material.shader.code.contains("smoothstep") or not soft_material.shader.code.contains("ALPHA = feather * feather"):
            push_error("Contact shadow lost smooth radial falloff for %s" % kind)
            quit(1)
            return
        var shadow_opacity := float(soft_material.get_shader_parameter("shadow_opacity"))
        if shadow_opacity > 0.40 or shadow_opacity < 0.20:
            push_error("Contact shadow opacity escaped subtle mobile visibility budget for %s" % kind)
            quit(1)
            return
        if shared_shadow_material == null:
            shared_shadow_material = shadow_material
        elif shadow_material != shared_shadow_material:
            push_error("Enemy contact shadows must share one material instance")
            quit(1)
            return
        shadow_radii[kind] = shadow_mesh.size.x * 0.5
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
            var boss_visual := enemy.get_node_or_null("Visual") as Node3D
            if wing_mesh == null or wing_mesh.size.z < 0.68 or absf(wing.position.x) < 0.80:
                push_error("Boss signature must remain broad and dominant in top-down projection")
                quit(1)
                return
            if boss_visual == null or boss_visual.scale.x < 1.78:
                push_error("Boss authored body lost premium screen-space mass")
                quit(1)
                return
            var aura := enemy.get_node_or_null("BossThreatAura") as Node3D
            var aura_inner := aura.get_node_or_null("BossAuraInner") as MeshInstance3D if aura != null else null
            var aura_outer := aura.get_node_or_null("BossAuraOuter") as MeshInstance3D if aura != null else null
            if aura == null or aura_inner == null or aura_outer == null:
                push_error("Boss threat aura lost layered ground presence")
                quit(1)
                return
            if aura_inner.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or aura_outer.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Boss threat aura must remain shadow-free")
                quit(1)
                return
            if aura.get_child_count() < 6:
                push_error("Boss threat aura lost directional ticks")
                quit(1)
                return
        enemy.queue_free()

    if float(shadow_radii.get("boss", 0.0)) < 0.86:
        push_error("Boss contact shadow is too small for its premium ground mass")
        quit(1)
        return
    if float(shadow_radii.get("boss", 0.0)) <= float(shadow_radii.get("brute", 0.0)):
        push_error("Boss contact shadow must preserve larger ground mass than brute")
        quit(1)
        return
    if float(shadow_radii.get("brute", 0.0)) <= float(shadow_radii.get("runner", 0.0)):
        push_error("Heavy enemy contact shadow must read broader than runner")
        quit(1)
        return

    var runner_a := ENEMY_SCRIPT.new()
    runner_a.kind = "runner"
    root.add_child(runner_a)
    var runner_b := ENEMY_SCRIPT.new()
    runner_b.kind = "runner"
    root.add_child(runner_b)
    await process_frame

    var runner_shadow_a := runner_a.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    var runner_shadow_b := runner_b.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    if runner_shadow_a == null or runner_shadow_b == null:
        push_error("Runner contact shadow reuse fixture is incomplete")
        quit(1)
        return
    if runner_shadow_a.mesh != runner_shadow_b.mesh:
        push_error("Same-kind enemy contact shadows must reuse one mesh resource")
        quit(1)
        return
    if runner_shadow_a.material_override != runner_shadow_b.material_override:
        push_error("Same-kind enemy contact shadows must reuse one material resource")
        quit(1)
        return

    var runner_blade_a := runner_a.get_node_or_null("RunnerBladeL") as MeshInstance3D
    var runner_blade_b := runner_b.get_node_or_null("RunnerBladeL") as MeshInstance3D
    if runner_blade_a == null or runner_blade_b == null or runner_blade_a.material_override != runner_blade_b.material_override:
        push_error("Same-kind runner signatures must reuse emissive material resources")
        quit(1)
        return

    if runner_blade_a.mesh != runner_blade_b.mesh:
        push_error("Same-kind runner signatures must reuse mesh resources")
        quit(1)
        return

    var brute_a := ENEMY_SCRIPT.new()
    brute_a.kind = "brute"
    root.add_child(brute_a)
    var brute_b := ENEMY_SCRIPT.new()
    brute_b.kind = "brute"
    root.add_child(brute_b)
    await process_frame
    var brute_plate_a := brute_a.get_node_or_null("BrutePlateL") as MeshInstance3D
    var brute_plate_b := brute_b.get_node_or_null("BrutePlateL") as MeshInstance3D
    if brute_plate_a == null or brute_plate_b == null or brute_plate_a.material_override != brute_plate_b.material_override:
        push_error("Brute armor plates must reuse one dark armor material")
        quit(1)
        return

    if brute_plate_a.mesh != brute_plate_b.mesh:
        push_error("Brute armor plates must reuse one mesh resource")
        quit(1)
        return

    # An actual imported runner must dissolve through its own opaque shader
    # instance after death, without whitening/fading the live runner's shared
    # material. This checks runtime GLTF meshes, not textual source alone.
    var dying_visual := runner_a.authored_visual
    var living_visual := runner_b.authored_visual
    if dying_visual == null or living_visual == null:
        push_error("Missing real imported GLTF runner for death dissolve integration")
        quit(1)
        return
    var dying_meshes := dying_visual.find_children("*", "MeshInstance3D", true, false)
    var living_meshes := living_visual.find_children("*", "MeshInstance3D", true, false)
    if dying_meshes.is_empty() or living_meshes.is_empty():
        push_error("Dissolve test requires real authored skinned zombie meshes")
        quit(1)
        return
    var dying_mesh := dying_meshes[0] as MeshInstance3D
    var living_mesh := living_meshes[0] as MeshInstance3D
    # Hit light must visibly pulse the REAL skinned, atlas-textured body per
    # instance. The neighboring 110+ enemies must keep their own dark skin.
    if runner_a.authored_skin_meshes.size() != dying_meshes.size() or (
        runner_b.authored_skin_meshes.size() != living_meshes.size()
    ):
        push_error("Per-enemy GLTF mesh cache does not cover actual skinned geometry")
        quit(1)
        return
    if not _first_graded_enemy_material(dying_visual).shader.code.contains("instance uniform float hit_skin_flash"):
        push_error("Shared opaque zombie shader lost per-instance ballistic skin feedback")
        quit(1)
        return
    runner_a._play_hit_reaction(true, false)
    var impact_skin_flash := float(dying_mesh.get_instance_shader_parameter("hit_skin_flash"))
    var untouched_skin_flash := float(living_mesh.get_instance_shader_parameter("hit_skin_flash"))
    if impact_skin_flash < 1.0 or absf(untouched_skin_flash) > 0.001:
        push_error("Critical impact did not light only the struck imported zombie body")
        quit(1)
        return
    if runner_a.hit_reaction_tween == null:
        push_error("Actual skinned zombie impact lacked a bounded fade tween")
        quit(1)
        return
    runner_a._set_hit_skin_flash(0.0)
    if absf(float(dying_mesh.get_instance_shader_parameter("hit_skin_flash"))) > 0.001:
        push_error("Zombie impact skin glow did not return to neutral without changing shared materials")
        quit(1)
        return
    var dying_shadow := runner_a.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    var living_shadow := runner_b.get_node_or_null("EnemyContactShadow") as MeshInstance3D
    if dying_shadow == null or living_shadow == null:
        push_error("Rigged death dissolve must preserve independent contact shadows")
        quit(1)
        return
    var shadow_shader := (dying_shadow.material_override as ShaderMaterial).shader
    if shadow_shader == null or not shadow_shader.code.contains("instance uniform float death_shadow_opacity"):
        push_error("Death contact shadows lost their shared-shader per-instance fade")
        quit(1)
        return
    var shared_dying_grade := _first_graded_enemy_material(dying_visual)
    if shared_dying_grade == null or shared_dying_grade != _first_graded_enemy_material(living_visual):
        push_error("Lethal damage FX must preserve one shared imported PBR grade")
        quit(1)
        return
    if not shared_dying_grade.shader.code.contains("instance uniform float death_dissolve") or not shared_dying_grade.shader.code.contains("discard;"):
        push_error("Zombie death transition must use opaque dither, never transparent alpha")
        quit(1)
        return
    runner_b._set_death_dissolve(0.0)
    var living_count_before_kill := get_node_count_in_group("enemies")
    runner_a.take_damage(runner_a.health + 1.0, true)
    # The body remains in the scene for the authored death clip/dissolve, but
    # must immediately stop consuming horde spatial/movement/spawn capacity.
    if runner_a.is_in_group("enemies") or not runner_b.is_in_group("enemies"):
        push_error("Dead corpse stayed indexed as a living enemy, or a living enemy was detached")
        quit(1)
        return
    if get_node_count_in_group("enemies") != living_count_before_kill - 1:
        push_error("Godot live enemy count did not drop when the zombie died")
        quit(1)
        return
    if not is_instance_valid(runner_a) or not runner_a.is_inside_tree() or runner_a.is_physics_processing():
        push_error("Dying GLTF body must remain visible but not run zombie physics")
        quit(1)
        return
    if not runner_a.dead or runner_a.authored_anim.current_animation != "Death":
        push_error("Imported GLTF death clip must begin before opaque fade")
        quit(1)
        return
    await create_timer(0.43).timeout
    var dying_amount := float(dying_mesh.get_instance_shader_parameter("death_dissolve"))
    var living_amount := float(living_mesh.get_instance_shader_parameter("death_dissolve"))
    var dying_shadow_opacity := float(dying_shadow.get_instance_shader_parameter("death_shadow_opacity"))
    var living_shadow_opacity := float(living_shadow.get_instance_shader_parameter("death_shadow_opacity"))
    if dying_shadow_opacity < 0.10 or dying_shadow_opacity > 0.90:
        push_error("Dying rigged zombie did not fade its ground contact on the death timeline")
        quit(1)
        return
    if absf(living_shadow_opacity - 1.0) > 0.001 or dying_shadow.material_override != living_shadow.material_override:
        push_error("Death contact fade changed a living zombie or duplicated horde shadow materials")
        quit(1)
        return
    if dying_amount < 0.24 or dying_amount > 0.95:
        push_error("Dying GLTF enemy failed to advance per-instance opaque dissolve")
        quit(1)
        return
    if absf(living_amount) > 0.001 or _first_graded_enemy_material(living_visual) != shared_dying_grade:
        push_error("Dying zombie changed the living horde's PBR shader or material")
        quit(1)
        return

    var source := FileAccess.get_file_as_string("res://scripts/AssetLibrary.gd")
    if not source.contains("uniform float rim_strength = 0.11") or not source.contains("EMISSION = body_tint.rgb * rim"):
        push_error("Enemy shared grading shader lost subtle silhouette rim lighting")
        quit(1)
        return

    print("Deadline Zero enemy silhouette identity: OK")
    quit(0)

func _first_graded_enemy_material(visual: Node3D) -> ShaderMaterial:
    var mesh_nodes: Array[MeshInstance3D] = []
    if visual is MeshInstance3D:
        mesh_nodes.append(visual as MeshInstance3D)
    for node in visual.find_children("*", "MeshInstance3D", true, false):
        mesh_nodes.append(node as MeshInstance3D)
    for mesh_instance in mesh_nodes:
        if mesh_instance.mesh == null:
            continue
        for surface_index in range(mesh_instance.mesh.get_surface_count()):
            var grade := mesh_instance.get_surface_override_material(surface_index) as ShaderMaterial
            if grade != null:
                return grade
    return null
