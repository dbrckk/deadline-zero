extends SceneTree

# Imported Quaternius assets ship UV-painted atlases. The runtime must keep
# those textures and individually grade PBR surface materials, never flatten
# the whole asset with a single untextured material_override.
func _initialize() -> void:
    var fixtures := [
        {"id": "barrel", "factory": Callable(DZAssetLibrary, "barrel"), "roughness": 0.83, "metallic": 0.28},
        {"id": "pallet", "factory": Callable(DZAssetLibrary, "pallet"), "roughness": 0.91, "metallic": 0.02},
        {"id": "traffic_cone", "factory": Callable(DZAssetLibrary, "traffic_cone"), "roughness": 0.86, "metallic": 0.01},
        {"id": "trash_bag", "factory": Callable(DZAssetLibrary, "trash_bag"), "roughness": 0.94, "metallic": 0.01},
        {"id": "barrier", "factory": Callable(DZAssetLibrary, "barrier"), "roughness": 0.84, "metallic": 0.34},
        {"id": "street_lights", "factory": Callable(DZAssetLibrary, "street_lights"), "roughness": 0.78, "metallic": 0.46},
    ]
    var total_textured_surfaces := 0
    for spec in fixtures:
        var factory: Callable = spec["factory"]
        var root := factory.call() as Node3D
        if root == null:
            push_error("Missing imported industrial 3D scene: %s" % spec["id"])
            quit(1)
            return
        var meshes: Array[MeshInstance3D] = []
        if root is MeshInstance3D:
            meshes.append(root as MeshInstance3D)
        for node in root.find_children("*", "MeshInstance3D", true, false):
            meshes.append(node as MeshInstance3D)

        var textured_surfaces := 0
        var optic_surfaces := 0
        for instance in meshes:
            if instance == null or instance.mesh == null:
                continue
            for surface_index in range(instance.mesh.get_surface_count()):
                var source := instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                if source == null:
                    continue
                # Authored hazard strips are separate geometry and should not
                # affect imported mesh/atlas checks.
                if instance.material_override != null:
                    push_error("Flattened industrial material on %s / %s" % [spec["id"], instance.name])
                    quit(1)
                    return
                var graded := instance.get_surface_override_material(surface_index) as BaseMaterial3D
                if graded == null:
                    push_error("Missing per-surface PBR material: %s / %s" % [spec["id"], instance.name])
                    quit(1)
                    return
                if source.albedo_texture != null:
                    if graded.albedo_texture != source.albedo_texture:
                        push_error("Authoring atlas was replaced for %s" % spec["id"])
                        quit(1)
                        return
                    if graded.roughness < float(spec["roughness"]) - 0.001 or graded.metallic < float(spec["metallic"]) - 0.001:
                        push_error("Industrial PBR roughness/metalness was lost on %s" % spec["id"])
                        quit(1)
                        return
                    textured_surfaces += 1
                elif spec["id"] == "street_lights":
                    if not graded.emission_enabled or graded.emission_energy_multiplier < 1.0 or graded.emission_energy_multiplier > 1.5:
                        push_error("Authored streetlight lens lost budgeted emissive shading")
                        quit(1)
                        return
                    optic_surfaces += 1

        if textured_surfaces == 0:
            push_error("No preserved authored texture atlas: %s" % spec["id"])
            quit(1)
            return
        if spec["id"] == "street_lights" and optic_surfaces == 0:
            push_error("Streetlight GLTF lens surface is missing")
            quit(1)
            return
        if spec["id"] == "barrier":
            for marker_name in ["BarrierHazardFront", "BarrierHazardRear"]:
                var marker := root.find_child(marker_name, true, false) as MeshInstance3D
                if marker == null or marker.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Hazard stripe must remain visible but shadowless: %s" % marker_name)
                    quit(1)
                    return
        total_textured_surfaces += textured_surfaces
        root.free()

    if total_textured_surfaces < 6:
        push_error("Too few authored PBR surfaces were exercised")
        quit(1)
        return
    print("Deadline Zero authored industrial PBR atlases: OK (%d textured surfaces)" % total_textured_surfaces)
    quit(0)
