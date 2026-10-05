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

    if not floor is MeshInstance3D:
        push_error("Quarantine floor is not a mesh")
        quit(1)
        return
    var floor_mesh := floor as MeshInstance3D
    if not floor_mesh.material_override is ShaderMaterial:
        push_error("Quarantine floor lost its procedural industrial material")
        quit(1)
        return
    var floor_shader := (floor_mesh.material_override as ShaderMaterial).shader
    if floor_shader == null or not floor_shader.code.contains("panel_variation") or not floor_shader.code.contains("micro_variation") or not floor_shader.code.contains("macro_variation") or not floor_shader.code.contains("perimeter_heat"):
        push_error("Quarantine floor procedural surface hierarchy regressed")
        quit(1)
        return
    var world_environment := env as WorldEnvironment
    if world_environment.environment == null or not world_environment.environment.adjustment_enabled:
        push_error("Quarantine environment lost lightweight cinematic color grading")
        quit(1)
        return
    if world_environment.environment.adjustment_contrast < 1.06 or world_environment.environment.adjustment_saturation < 1.04:
        push_error("Quarantine environment color grading is too flat for premium combat readability")
        quit(1)
        return

    if floor_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Broad quarantine floor must not waste shadow-caster budget")
        quit(1)
        return

    var barrier_count := 0
    var lane_count := 0
    var arena_boundary_count := 0
    var arena_boundary_shadow_violations := 0
    var beacon_count := 0
    var floor_plate_count := 0
    var floor_plate_accent_count := 0
    var dark_floor_plate_count := 0
    var floor_seam_count := 0
    var floor_wear_count := 0
    var floor_chip_count := 0
    var containment_ring_count := 0
    var street_light_count := 0
    var street_light_pool_count := 0
    var graded_street_light_meshes := 0
    var offscreen_authored_street_light_count := 0
    var quarantine_mast_count := 0
    var quarantine_mast_lamp_count := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    var service_pylon_count := 0
    var service_grate_count := 0
    var inspection_panel_count := 0
    var inspection_service_stripe_count := 0
    var service_grate_slat_count := 0
    var oversized_barrier_count := 0
    var perimeter_bulkhead_count := 0
    var bulkhead_hazard_stripe_count := 0
    var bulkhead_signal_count := 0
    var arena_light_pool_count := 0
    var arena_light_pool_violations := 0
    for child in scene.get_children():
        if child.name.begins_with("AuthoredBarrier_"):
            barrier_count += 1
            if child.scale.x > 0.40 or Vector2(child.position.x, child.position.z).length() < 24.0:
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
        elif child.name.begins_with("PerimeterBulkhead_"):
            perimeter_bulkhead_count += 1
            bulkhead_hazard_stripe_count += child.find_children("HazardStripe_*", "MeshInstance3D", true, false).size()
            bulkhead_signal_count += child.find_children("Signal", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("ContainmentLane_"):
            lane_count += 1
        elif child.name.begins_with("ArenaBoundary_"):
            arena_boundary_count += 1
            if child is MeshInstance3D and (child as MeshInstance3D).cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                arena_boundary_shadow_violations += 1
        elif child.name.begins_with("ArenaLightPool_"):
            arena_light_pool_count += 1
            if not child is MeshInstance3D:
                arena_light_pool_violations += 1
            else:
                var pool_mesh := child as MeshInstance3D
                if pool_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF or not pool_mesh.material_override is ShaderMaterial:
                    arena_light_pool_violations += 1
                elif not (pool_mesh.material_override as ShaderMaterial).shader.code.contains("blend_add"):
                    arena_light_pool_violations += 1
        elif child.name.begins_with("PerimeterBeacon_"):
            beacon_count += 1
        elif child.name.begins_with("FloorPlate_"):
            floor_plate_count += 1
            if child is MeshInstance3D and (child as MeshInstance3D).material_override is StandardMaterial3D:
                var plate_material := (child as MeshInstance3D).material_override as StandardMaterial3D
                if plate_material.albedo_color.get_luminance() < 0.08 and plate_material.roughness >= 0.88:
                    dark_floor_plate_count += 1
            floor_plate_accent_count += child.find_children("FloorPlateAccent_*", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("FloorSeam_"):
            floor_seam_count += 1
        elif child.name.begins_with("ServiceGrate_"):
            service_grate_count += 1
            for slat in child.find_children("Slat_*", "MeshInstance3D", true, false):
                if slat is MeshInstance3D:
                    service_grate_slat_count += 1
        elif child.name.begins_with("InspectionPanel_"):
            inspection_panel_count += 1
            inspection_service_stripe_count += child.find_children("ServiceStripe_*", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("FloorWear_"):
            floor_wear_count += 1
        elif child.name.begins_with("FloorChip_"):
            floor_chip_count += 1
        elif child.name.begins_with("ContainmentRing_"):
            containment_ring_count += 1
        elif child.name.begins_with("ServicePylon_"):
            service_pylon_count += 1
        elif child.name.begins_with("QuarantineMast_"):
            quarantine_mast_count += 1
            quarantine_mast_lamp_count += child.find_children("Lamp", "MeshInstance3D", true, false).size()
        elif child.name.begins_with("AuthoredStreetLight_"):
            street_light_count += 1
            if Vector2(child.position.x, child.position.z).length() > 32.0 and child.scale.x <= 0.40:
                offscreen_authored_street_light_count += 1
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
                    if material.roughness >= 0.76 and material.metallic >= 0.40 and material.albedo_color.get_luminance() < 0.09 and material.albedo_texture == null:
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
    if perimeter_bulkhead_count != 10 or bulkhead_hazard_stripe_count != 30 or bulkhead_signal_count != 10:
        push_error("Expected 10 layered perimeter bulkheads with 30 hazard stripes / 10 signals, got %d/%d/%d" % [perimeter_bulkhead_count, bulkhead_hazard_stripe_count, bulkhead_signal_count])
        quit(1)
        return
    if lane_count < 40:
        push_error("Expected structured containment lanes, got %d" % lane_count)
        quit(1)
        return
    if arena_boundary_count != 44 or arena_boundary_shadow_violations != 0:
        push_error("Expected 44 shadow-free arena boundary markers, got %d with %d shadow violations" % [arena_boundary_count, arena_boundary_shadow_violations])
        quit(1)
        return
    if arena_light_pool_count != 6 or arena_light_pool_violations != 0:
        push_error("Expected 6 mobile-safe additive arena light pools, got %d with %d violations" % [arena_light_pool_count, arena_light_pool_violations])
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
    if dark_floor_plate_count != floor_plate_count:
        push_error("All floor plates must remain dark under combat lighting, got %d/%d" % [dark_floor_plate_count, floor_plate_count])
        quit(1)
        return
    if floor_plate_accent_count != 3:
        push_error("Expected exactly 3 restrained floor-plate accents, got %d" % floor_plate_accent_count)
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
    if inspection_panel_count != 6 or inspection_service_stripe_count != 12:
        push_error("Expected 6 midfield inspection panels / 12 service stripes, got %d/%d" % [inspection_panel_count, inspection_service_stripe_count])
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
    if service_pylon_count != 6:
        push_error("Expected 6 low service pylons, got %d" % service_pylon_count)
        quit(1)
        return
    if street_light_count != 4 or street_light_pool_count != 4:
        push_error("Expected 4 authored vertical light fixtures with safe pools, got %d/%d" % [street_light_count, street_light_pool_count])
        quit(1)
        return
    if offscreen_authored_street_light_count != 4:
        push_error("Authored street lights must stay outside normal combat framing, got %d/4 compliant" % offscreen_authored_street_light_count)
        quit(1)
        return
    if quarantine_mast_count != 4 or quarantine_mast_lamp_count != 4:
        push_error("Expected 4 compact visible quarantine masts with emissive lamps, got %d/%d" % [quarantine_mast_count, quarantine_mast_lamp_count])
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
        push_error("Authored barriers must stay compact beyond the active combat frame, got %d violations" % oversized_barrier_count)
        quit(1)
        return

    for child in scene.get_children():
        if child is MeshInstance3D and child.name.begins_with("PrototypeProp"):
            push_error("Prototype prop remained in production arena")
            quit(1)
            return

    print("Deadline Zero environment identity: OK")
    quit(0)
