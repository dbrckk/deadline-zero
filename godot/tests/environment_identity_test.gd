extends SceneTree

const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    await process_frame

    var floor := scene.get_node_or_null("QuarantineFloor")
    var env := scene.get_node_or_null("QuarantineEnvironment")
    var fill := scene.get_node_or_null("ContainmentFill")
    if floor == null or env == null or fill == null:
        push_error("Authored quarantine environment anchors are missing")
        quit(1)
        return

    var barrier_count := 0
    var lane_count := 0
    var beacon_count := 0
    var floor_plate_count := 0
    var floor_seam_count := 0
    var floor_wear_count := 0
    var floor_chip_count := 0
    var containment_ring_count := 0
    var street_light_count := 0
    var street_light_pool_count := 0
    var graded_street_light_meshes := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    var service_grate_count := 0
    var service_grate_slat_count := 0
    var oversized_barrier_count := 0
    for child in scene.get_children():
        if child.name.begins_with("AuthoredBarrier_"):
            barrier_count += 1
            if child.scale.x > 0.56 or Vector2(child.position.x, child.position.z).length() < 22.0:
                oversized_barrier_count += 1
            var barrier_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                barrier_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                barrier_meshes.append(mesh_node as MeshInstance3D)
            for mesh_instance in barrier_meshes:
                if mesh_instance != null and mesh_instance.material_override is StandardMaterial3D:
                    var material := mesh_instance.material_override as StandardMaterial3D
                    if material.roughness >= 0.80 and material.metallic >= 0.30 and material.albedo_color.get_luminance() < 0.16 and material.albedo_texture == null:
                        graded_barrier_meshes += 1
            hazard_strip_count += int(child.find_child("BarrierHazardFront", true, false) != null)
            hazard_strip_count += int(child.find_child("BarrierHazardRear", true, false) != null)
        elif child.name.begins_with("ContainmentLane_"):
            lane_count += 1
        elif child.name.begins_with("PerimeterBeacon_"):
            beacon_count += 1
        elif child.name.begins_with("FloorPlate_"):
            floor_plate_count += 1
        elif child.name.begins_with("FloorSeam_"):
            floor_seam_count += 1
        elif child.name.begins_with("ServiceGrate_"):
            service_grate_count += 1
            for slat in child.find_children("Slat_*", "MeshInstance3D", true, false):
                if slat is MeshInstance3D:
                    service_grate_slat_count += 1
        elif child.name.begins_with("FloorWear_"):
            floor_wear_count += 1
        elif child.name.begins_with("FloorChip_"):
            floor_chip_count += 1
        elif child.name.begins_with("ContainmentRing_"):
            containment_ring_count += 1
        elif child.name.begins_with("AuthoredStreetLight_"):
            street_light_count += 1
            var light_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                light_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                var mesh_instance := mesh_node as MeshInstance3D
                if mesh_instance != null and mesh_instance.name != "StreetLightCore":
                    light_meshes.append(mesh_instance)
            for mesh_instance in light_meshes:
                if mesh_instance.material_override is BaseMaterial3D:
                    var material := mesh_instance.material_override as BaseMaterial3D
                    if material.roughness >= 0.86 and material.albedo_color.get_luminance() < 0.55:
                        graded_street_light_meshes += 1
            var pool := child.get_node_or_null("StreetLightPool") as OmniLight3D
            if pool != null:
                if pool.shadow_enabled:
                    push_error("Street-light pool must remain shadowless for mobile budget")
                    quit(1)
                    return
                if pool.omni_range > 8.5 or pool.light_energy > 1.0:
                    push_error("Street-light pool exceeded mobile-safe range/energy budget")
                    quit(1)
                    return
                street_light_pool_count += 1

    if barrier_count < 12:
        push_error("Expected authored barrier clusters, got %d" % barrier_count)
        quit(1)
        return
    if lane_count < 40:
        push_error("Expected structured containment lanes, got %d" % lane_count)
        quit(1)
        return
    if beacon_count != 12:
        push_error("Expected 12 perimeter beacons, got %d" % beacon_count)
        quit(1)
        return
    if floor_plate_count < 8:
        push_error("Expected midfield floor variation plates, got %d" % floor_plate_count)
        quit(1)
        return
    if floor_seam_count < 8:
        push_error("Expected industrial floor seam structure, got %d" % floor_seam_count)
        quit(1)
        return
    if service_grate_count != 4 or service_grate_slat_count < 36:
        push_error("Expected 4 detailed service grates with at least 36 slats, got %d grates / %d slats" % [service_grate_count, service_grate_slat_count])
        quit(1)
        return
    if floor_wear_count < 12 or floor_chip_count < 12:
        push_error("Expected deterministic floor wear/chip dressing, got %d/%d" % [floor_wear_count, floor_chip_count])
        quit(1)
        return
    if containment_ring_count != 2:
        push_error("Expected 2 thin containment rings, got %d" % containment_ring_count)
        quit(1)
        return
    if street_light_count != 4 or street_light_pool_count != 4:
        push_error("Expected 4 authored vertical light fixtures with safe pools, got %d/%d" % [street_light_count, street_light_pool_count])
        quit(1)
        return
    if graded_street_light_meshes < 4:
        push_error("Authored street lights must receive dark steel grading, got %d graded meshes" % graded_street_light_meshes)
        quit(1)
        return
    if graded_barrier_meshes < 12:
        push_error("Authored barriers must use the dedicated dark industrial material, got %d graded meshes" % graded_barrier_meshes)
        quit(1)
        return
    if hazard_strip_count < 24:
        push_error("Authored barriers must expose hazard signatures, got %d strips" % hazard_strip_count)
        quit(1)
        return
    if oversized_barrier_count != 0:
        push_error("Authored barriers must stay compact and perimeter-biased, got %d violations" % oversized_barrier_count)
        quit(1)
        return

    for child in scene.get_children():
        if child is MeshInstance3D and child.name.begins_with("PrototypeProp"):
            push_error("Prototype prop remained in production arena")
            quit(1)
            return

    print("Deadline Zero environment identity: OK")
    quit(0)
