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
    var containment_ring_count := 0
    var street_light_count := 0
    var street_light_pool_count := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    for child in scene.get_children():
        if child.name.begins_with("AuthoredBarrier_"):
            barrier_count += 1
            var barrier_meshes: Array[MeshInstance3D] = []
            if child is MeshInstance3D:
                barrier_meshes.append(child as MeshInstance3D)
            for mesh_node in child.find_children("*", "MeshInstance3D", true, false):
                barrier_meshes.append(mesh_node as MeshInstance3D)
            for mesh_instance in barrier_meshes:
                if mesh_instance != null and mesh_instance.material_override is StandardMaterial3D:
                    var material := mesh_instance.material_override as StandardMaterial3D
                    if material.roughness >= 0.88 and material.albedo_color.get_luminance() < 0.62:
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
        elif child.name.begins_with("ContainmentRing_"):
            containment_ring_count += 1
        elif child.name.begins_with("AuthoredStreetLight_"):
            street_light_count += 1
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
    if containment_ring_count != 2:
        push_error("Expected 2 thin containment rings, got %d" % containment_ring_count)
        quit(1)
        return
    if street_light_count != 4 or street_light_pool_count != 4:
        push_error("Expected 4 authored vertical light fixtures with safe pools, got %d/%d" % [street_light_count, street_light_pool_count])
        quit(1)
        return
    if graded_barrier_meshes < 12:
        push_error("Authored barriers must receive dark industrial material grading, got %d graded meshes" % graded_barrier_meshes)
        quit(1)
        return
    if hazard_strip_count < 24:
        push_error("Authored barriers must expose hazard signatures, got %d strips" % hazard_strip_count)
        quit(1)
        return

    for child in scene.get_children():
        if child is MeshInstance3D and child.name.begins_with("PrototypeProp"):
            push_error("Prototype prop remained in production arena")
            quit(1)
            return

    print("Deadline Zero environment identity: OK")
    quit(0)
