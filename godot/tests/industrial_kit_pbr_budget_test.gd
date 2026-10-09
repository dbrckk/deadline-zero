extends SceneTree

# Real GLB material QA: four floor grates and the authored industrial kit
# should never render as ungraded white surfaces on low-end GLES/mobile.
func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var families := [
        {"id": "grate", "factory": Callable(DZAssetLibrary, "industrial_floor_grate"), "min_meshes": 18},
        {"id": "bulkhead", "factory": Callable(DZAssetLibrary, "industrial_bulkhead"), "min_meshes": 10},
        {"id": "crate", "factory": Callable(DZAssetLibrary, "industrial_crate"), "min_meshes": 10},
        {"id": "pipe", "factory": Callable(DZAssetLibrary, "industrial_pipe_rack"), "min_meshes": 10},
        {"id": "pillar", "factory": Callable(DZAssetLibrary, "industrial_service_pillar"), "min_meshes": 8},
    ]
    var tested_surfaces := 0
    for family in families:
        var factory: Callable = family["factory"]
        var first := factory.call() as Node3D
        var second := factory.call() as Node3D
        if first == null or second == null:
            push_error("%s production GLB is unavailable" % family["id"])
            quit(1)
            return
        var meshes := _meshes(first)
        var duplicate_meshes := _meshes(second)
        if meshes.size() < int(family["min_meshes"]) or duplicate_meshes.size() != meshes.size():
            push_error("%s authored hard-surface geometry was flattened or lost" % family["id"])
            quit(1)
            return

        var grate_slats := 0
        var grate_recesses := 0
        var hazard_parts := 0
        var signal_parts := 0
        for index in range(meshes.size()):
            var mesh := meshes[index]
            var duplicate := duplicate_meshes[index]
            if mesh.mesh == null or mesh.material_override != null:
                push_error("%s lost per-surface industrial PBR grading" % family["id"])
                quit(1)
                return
            if family["id"] == "grate" and mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Floor grate slats must not enter the costly mobile shadow map")
                quit(1)
                return
            for surface in range(mesh.mesh.get_surface_count()):
                var finish := mesh.get_surface_override_material(surface) as StandardMaterial3D
                var duplicate_finish := duplicate.get_surface_override_material(surface) as StandardMaterial3D
                if finish == null or duplicate_finish != finish:
                    push_error("%s does not reuse shared graded GLB materials" % family["id"])
                    quit(1)
                    return
                if maxf(finish.albedo_color.r, maxf(finish.albedo_color.g, finish.albedo_color.b)) > 0.75:
                    push_error("%s imported a near-white overexposed surface" % family["id"])
                    quit(1)
                    return
                if finish.roughness < 0.45 or finish.metallic < 0.15:
                    push_error("%s has implausible industrial PBR surface response" % family["id"])
                    quit(1)
                    return
                tested_surfaces += 1

            var identity := mesh.name.to_lower()
            if family["id"] == "grate" and "slat" in identity:
                grate_slats += 1
                var slat_material := mesh.get_surface_override_material(0) as StandardMaterial3D
                if slat_material.albedo_color.max_component() > 0.22 or slat_material.emission_enabled:
                    push_error("Grate slat is too bright for the dark combat floor")
                    quit(1)
                    return
            if family["id"] == "grate" and "recess" in identity:
                grate_recesses += 1
            if "hazard" in identity:
                hazard_parts += 1
            if "signal" in identity:
                signal_parts += 1

        if family["id"] == "grate" and (grate_slats < 12 or grate_recesses < 1):
            push_error("Original grate slat/recess geometry no longer exists")
            quit(1)
            return
        if family["id"] == "bulkhead" and hazard_parts < 4:
            push_error("Original bulkhead warning markings were lost")
            quit(1)
            return
        if family["id"] == "pillar" and signal_parts < 1:
            push_error("Original industrial pillar signal was lost")
            quit(1)
            return
        first.free()
        second.free()

    if tested_surfaces < 55:
        push_error("Hard-surface QA exercised too few authored GLB surfaces")
        quit(1)
        return
    print("Deadline Zero industrial GLB PBR grading: OK (%d shared surfaces)" % tested_surfaces)
    quit(0)

func _meshes(node: Node3D) -> Array[MeshInstance3D]:
    var result: Array[MeshInstance3D] = []
    if node is MeshInstance3D:
        result.append(node as MeshInstance3D)
    for child in node.find_children("*", "MeshInstance3D", true, false):
        result.append(child as MeshInstance3D)
    return result
