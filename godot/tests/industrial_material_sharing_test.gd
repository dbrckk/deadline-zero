extends SceneTree

# Stress actual imported materials: every repeat prop must share immutable
# PBR surface resources without flattening its UV atlas.
func _initialize() -> void:
    var factories := [
        {"name": "barrier", "make": Callable(DZAssetLibrary, "barrier")},
        {"name": "barrel", "make": Callable(DZAssetLibrary, "barrel")},
        {"name": "pallet", "make": Callable(DZAssetLibrary, "pallet")},
        {"name": "street_lights", "make": Callable(DZAssetLibrary, "street_lights")},
        {"name": "traffic_cone", "make": Callable(DZAssetLibrary, "traffic_cone")},
        {"name": "trash_bag", "make": Callable(DZAssetLibrary, "trash_bag")},
    ]

    var first_grades := {}
    var total_textured_instances := 0
    var barrier_stripe_material: Material
    for spec in factories:
        var name := String(spec["name"])
        var make: Callable = spec["make"]
        for sample in range(5):
            var root := make.call() as Node3D
            if root == null:
                push_error("Industrial PBR instance missing: %s" % name)
                quit(1)
                return

            var surface_count := 0
            var nodes: Array[MeshInstance3D] = []
            if root is MeshInstance3D:
                nodes.append(root as MeshInstance3D)
            for node in root.find_children("*", "MeshInstance3D", true, false):
                nodes.append(node as MeshInstance3D)
            for instance in nodes:
                if instance.mesh == null or instance.material_override != null:
                    continue
                for surface_index in range(instance.mesh.get_surface_count()):
                    var source := instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    if source == null or source.albedo_texture == null:
                        continue
                    var material := instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if material == null or material.albedo_texture != source.albedo_texture:
                        push_error("Authored atlas flattened or lost: %s" % name)
                        quit(1)
                        return
                    if not first_grades.has(name):
                        first_grades[name] = material
                    elif first_grades[name] != material:
                        push_error("Identical imported prop copies allocated distinct PBR materials: %s" % name)
                        quit(1)
                        return
                    surface_count += 1

            if surface_count == 0:
                push_error("No original textured surface in %s" % name)
                quit(1)
                return
            total_textured_instances += surface_count

            if name == "barrier":
                var front := root.find_child("BarrierHazardFront", true, false) as MeshInstance3D
                var rear := root.find_child("BarrierHazardRear", true, false) as MeshInstance3D
                if front == null or rear == null or front.material_override == null:
                    push_error("Hazard material sharing fixture missing")
                    quit(1)
                    return
                if rear.material_override != front.material_override:
                    push_error("Barrier stripes did not share one emissive material")
                    quit(1)
                    return
                if barrier_stripe_material == null:
                    barrier_stripe_material = front.material_override
                elif barrier_stripe_material != front.material_override:
                    push_error("Different barriers allocated duplicate hazard stripe materials")
                    quit(1)
                    return
            root.free()

    if first_grades.size() != factories.size() or total_textured_instances < 30:
        push_error("PBR sharing stress test did not cover all 30 imported props")
        quit(1)
        return
    if first_grades["barrel"] == first_grades["pallet"]:
        push_error("Material sharing leaked barrel metal grade into wood pallet")
        quit(1)
        return

    print("Deadline Zero shared authored PBR prop materials: OK (%d textured surfaces, %d families)" % [total_textured_instances, first_grades.size()])
    quit(0)
