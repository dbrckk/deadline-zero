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

    # Inspect the actual scene's graded PBR light rig, not an offline concept:
    # the 720p rendered frame needs visible imported zombie/survivor texture
    # detail while maintaining low-cost shadow budget and dark arena identity.
    var key_light := scene.get_node_or_null("ColdKeyLight") as DirectionalLight3D
    var staging_camera := scene.camera as Camera3D
    var graded_environment := world_environment.environment
    if key_light == null or not key_light.shadow_enabled:
        push_error("Quarantine lost its single authored directional character shadow key")
        quit(1)
        return
    if graded_environment.ambient_light_energy < 0.84 or graded_environment.ambient_light_energy > 1.00:
        push_error("3D actors must receive bounded diffuse bounce for mobile readability")
        quit(1)
        return
    if key_light.light_energy < 1.35 or key_light.light_energy > 1.60:
        push_error("Authored actor key intensity escaped bounded night-scene contrast")
        quit(1)
        return
    if staging_camera == null or staging_camera.fov < 44.0 or staging_camera.fov > 46.0:
        push_error("Phone-scale camera framing is not keeping imported 3D actors readable")
        quit(1)
        return

    if floor_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Broad quarantine floor must not waste shadow-caster budget")
        quit(1)
        return

    var ambient_motes := scene.get_node_or_null("AmbientMotes") as GPUParticles3D
    if ambient_motes == null:
        push_error("Quarantine arena lost subtle atmospheric depth motes")
        quit(1)
        return
    if ambient_motes.amount > 32 or ambient_motes.lifetime > 6.0:
        push_error("Ambient motes exceeded mobile-safe particle budget")
        quit(1)
        return
    if ambient_motes.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Ambient motes must never cast dynamic shadows")
        quit(1)
        return
    if not ambient_motes.draw_pass_1 is QuadMesh:
        push_error("Ambient motes must remain one lightweight billboard draw pass")
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
    var graded_street_light_optics := 0
    var offscreen_authored_street_light_count := 0
    var quarantine_mast_count := 0
    var quarantine_mast_lamp_count := 0
    var graded_barrier_meshes := 0
    var hazard_strip_count := 0
    var service_pylon_count := 0
    var service_grate_count := 0
    var inspection_panel_count := 0
    var inspection_service_stripe_count := 0
    var service_grate_mesh_count := 0
    var oversized_barrier_count := 0
    var perimeter_bulkhead_count := 0
    var bulkhead_hazard_stripe_count := 0
    var bulkhead_signal_count := 0
    var arena_light_pool_count := 0
    var arena_light_pool_violations := 0
    var authored_floor_decal_count := 0
    var authored_floor_decal_violations := 0
    var authored_floor_decal_materials := {}
    var utility_counts := {}
    var utility_shadow_casters := 0
    var utility_shadow_violations := 0
    var utility_mesh_count := 0
    var utility_materials := {}
    var utility_light_lenses := 0
    var utility_textured_parts := 0
    var utility_normal_parts := 0
    var utility_arm_parts := 0
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
                if mesh_instance == null or mesh_instance.mesh == null or mesh_instance.material_override != null:
                    continue
                for surface_index in range(mesh_instance.mesh.get_surface_count()):
                    var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    var material := mesh_instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if source != null and source.albedo_texture != null and material != null:
                        if material.albedo_texture == source.albedo_texture and material.roughness >= 0.84 and material.metallic >= 0.34:
                            graded_barrier_meshes += 1
            hazard_strip_count += int(child.find_child("BarrierHazardFront", true, false) != null)
            hazard_strip_count += int(child.find_child("BarrierHazardRear", true, false) != null)
        elif child.name.begins_with("AuthoredUtility_"):
            # Inspect the actual imported Blender GLB parts and shared PBR
            # material instances, not only scene-construction source text.
            var kind := String(child.name).split("_")[1]
            utility_counts[kind] = int(utility_counts.get(kind, 0)) + 1
            if not child.is_in_group("environment_utility_props"):
                push_error("Authored industrial fixture escaped environment group")
                quit(1)
                return
            var parts := child.find_children("*", "MeshInstance3D", true, false)
            if parts.size() < 4:
                push_error("Imported industrial fixture lacks authored bevels/fasteners: %s" % kind)
                quit(1)
                return
            for part in parts:
                var mesh := part as MeshInstance3D
                if mesh == null or mesh.mesh == null or mesh.mesh.get_surface_count() == 0:
                    utility_shadow_violations += 1
                    continue
                utility_mesh_count += 1
                if mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    utility_shadow_casters += 1
                    if kind != "cabinet" or mesh.name != "CabinetBody":
                        utility_shadow_violations += 1
                for surf in range(mesh.mesh.get_surface_count()):
                    var material := mesh.get_surface_override_material(surf) as StandardMaterial3D
                    if material == null or mesh.material_override != null:
                        push_error("Blender GLB utility mesh lost its shared authored PBR surface: %s" % kind)
                        quit(1)
                        return
                    utility_materials[material.get_instance_id()] = true
                    if material.albedo_texture != null:
                        utility_textured_parts += 1
                        if material.normal_enabled and material.normal_texture != null:
                            utility_normal_parts += 1
                        if material.ao_enabled and material.ao_texture != null and (
                            material.roughness_texture == material.ao_texture
                            and material.metallic_texture == material.ao_texture
                            and material.ao_texture_channel == BaseMaterial3D.TEXTURE_CHANNEL_RED
                            and material.roughness_texture_channel == BaseMaterial3D.TEXTURE_CHANNEL_GREEN
                            and material.metallic_texture_channel == BaseMaterial3D.TEXTURE_CHANNEL_BLUE
                        ):
                            utility_arm_parts += 1
                    if kind == "light" and mesh.name == "LightBarLens" and material.emission_enabled:
                        utility_light_lenses += 1
        elif child.name.begins_with("PerimeterBulkhead_"):
            perimeter_bulkhead_count += 1
            bulkhead_hazard_stripe_count += child.find_children("HazardMarker_*", "MeshInstance3D", true, false).size()
            bulkhead_signal_count += int(child.find_children("*", "MeshInstance3D", true, false).size() >= 10)
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
        elif child.name.begins_with("AuthoredFloorDecal_"):
            authored_floor_decal_count += 1
            if not child is MeshInstance3D:
                authored_floor_decal_violations += 1
            else:
                var decal_mesh := child as MeshInstance3D
                if not decal_mesh.mesh is QuadMesh:
                    authored_floor_decal_violations += 1
                if decal_mesh.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    authored_floor_decal_violations += 1
                if not decal_mesh.material_override is StandardMaterial3D:
                    authored_floor_decal_violations += 1
                else:
                    var decal_material := decal_mesh.material_override as StandardMaterial3D
                    if decal_material.albedo_texture == null or decal_material.transparency == BaseMaterial3D.TRANSPARENCY_DISABLED:
                        authored_floor_decal_violations += 1
                    authored_floor_decal_materials[decal_material.get_instance_id()] = true
                if decal_mesh.position.y < 0.025 or decal_mesh.position.y > 0.045:
                    authored_floor_decal_violations += 1
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
            service_grate_mesh_count += child.find_children("*", "MeshInstance3D", true, false).size()
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
                if mesh_instance.mesh == null or mesh_instance.material_override != null:
                    continue
                for surface_index in range(mesh_instance.mesh.get_surface_count()):
                    var source := mesh_instance.mesh.surface_get_material(surface_index) as BaseMaterial3D
                    var material := mesh_instance.get_surface_override_material(surface_index) as BaseMaterial3D
                    if source == null or material == null:
                        continue
                    if source.albedo_texture != null and source.albedo_texture == material.albedo_texture:
                        if material.roughness >= 0.78 and material.metallic >= 0.46:
                            graded_street_light_meshes += 1
                    elif source.albedo_texture == null and material.emission_enabled and material.emission_energy_multiplier > 0.8:
                        graded_street_light_optics += 1
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

    var expected_utilities := {
        "cabinet": 2, "vent": 2, "hatch": 4,
        "bollard": 4, "junction": 2, "light": 2
    }
    if utility_counts != expected_utilities:
        push_error("Production scene is missing project-authored industrial 3D kit: %s" % str(utility_counts))
        quit(1)
        return
    if utility_shadow_violations != 0 or utility_shadow_casters != 2:
        push_error("Imported industrial fixtures exceeded two-cabinet shadow budget")
        quit(1)
        return
    if utility_mesh_count < 75 or utility_materials.size() > 7 or utility_materials.size() < 4:
        push_error("Industrial kit lost dense 3D geometry or shared PBR palette: meshes=%d materials=%d" % [utility_mesh_count, utility_materials.size()])
        quit(1)
        return
    if utility_textured_parts < 40 or utility_normal_parts != utility_textured_parts or utility_arm_parts != utility_textured_parts:
        push_error("Project-owned Blender GLBs must retain shared CC0 albedo, normal and packed PBR wear textures: %d / %d / %d" % [
            utility_textured_parts, utility_normal_parts, utility_arm_parts
        ])
        quit(1)
        return
    if utility_light_lenses != 2:
        push_error("Industrial quarantine cabinets lost their two real emissive GLB light lenses")
        quit(1)
        return

    if barrier_count < 12:
        push_error("Expected authored barrier clusters, got %d" % barrier_count)
        quit(1)
        return
    if perimeter_bulkhead_count != 10 or bulkhead_hazard_stripe_count < 40 or bulkhead_signal_count != 10:
        push_error("Expected 10 authored GLB bulkheads with readable hazard/details, got %d bulkheads / %d hazard markers / %d detailed shells" % [perimeter_bulkhead_count, bulkhead_hazard_stripe_count, bulkhead_signal_count])
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
    if authored_floor_decal_count != 12 or authored_floor_decal_violations != 0:
        push_error("Expected 12 valid project-owned floor decals, got %d with %d violations" % [authored_floor_decal_count, authored_floor_decal_violations])
        quit(1)
        return
    if authored_floor_decal_materials.size() != 3:
        push_error("Authored floor decals must reuse exactly 3 shared materials, got %d" % authored_floor_decal_materials.size())
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
    if service_grate_count != 4 or service_grate_mesh_count < 40:
        push_error("Expected 4 authored GLB service grates with detailed mesh hierarchy, got %d grates / %d mesh nodes" % [service_grate_count, service_grate_mesh_count])
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
    if graded_street_light_meshes < 4 or graded_street_light_optics < 4:
        push_error("Authored street lights must retain textured steel plus emissive optics, got %d steel / %d optics" % [graded_street_light_meshes, graded_street_light_optics])
        quit(1)
        return
    if graded_barrier_meshes < 12:
        push_error("Authored barriers must preserve their textured industrial surfaces, got %d graded meshes" % graded_barrier_meshes)
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

    # Live threat lighting must act on the real Blender-exported PBR lamp
    # lenses and existing additive ground pools, not allocate new lights.
    var authored_light_a := scene.get_node_or_null("AuthoredUtility_light_02") as Node3D
    var authored_light_b := scene.get_node_or_null("AuthoredUtility_light_03") as Node3D
    if authored_light_a == null or authored_light_b == null:
        push_error("Dynamic alarm lost its two authored industrial light bars")
        quit(1)
        return
    var lens_a := authored_light_a.find_child("LightBarLens", true, false) as MeshInstance3D
    var lens_b := authored_light_b.find_child("LightBarLens", true, false) as MeshInstance3D
    if lens_a == null or lens_b == null:
        push_error("Dynamic alarm must use actual Blender GLB lens geometry")
        quit(1)
        return
    var lens_material := lens_a.get_surface_override_material(0) as StandardMaterial3D
    if lens_material == null or lens_b.get_surface_override_material(0) != lens_material:
        push_error("Dynamic alarm must reuse a shared PBR lens material on both fixtures")
        quit(1)
        return
    if scene.alert_lens_material != lens_material or scene.alert_warm_pool_material == null or scene.alert_beacon_material == null:
        push_error("Dynamic alarm is not bound to its production light fixtures")
        quit(1)
        return
    if scene.emergency_rim_lights.size() != 2:
        push_error("Dynamic alarm must reuse exactly the two existing emergency rim lights")
        quit(1)
        return

    scene.elapsed = 0.0
    scene.boss_defeat_relief_left = 0.0
    scene._update_world_alert_lighting()
    var calm_lens_color := lens_material.emission
    var calm_energy := lens_material.emission_energy_multiplier
    scene.elapsed = 225.0
    scene._update_world_alert_lighting()
    var hazard_color := lens_material.emission
    if hazard_color.r <= calm_lens_color.r + 0.35 or hazard_color.g >= calm_lens_color.g:
        push_error("Actual industrial PBR lamp failed to signal escalated threat with copper alert tint")
        quit(1)
        return
    if lens_material.emission_energy_multiplier <= calm_energy or lens_material.emission_energy_multiplier > 2.56:
        push_error("Industrial alert brightness did not escalate within its mobile light budget")
        quit(1)
        return

    scene.boss_defeat_relief_left = 1.0
    scene._update_world_alert_lighting()
    if lens_material.emission.r >= hazard_color.r - 0.30:
        push_error("Boss victory failed to cool the actual world-space emergency fixtures")
        quit(1)
        return

    scene.boss_defeat_relief_left = 0.0
    scene.reduced_flashes = true
    scene.elapsed = 150.0
    scene._update_world_alert_lighting()
    var steady_energy := lens_material.emission_energy_multiplier
    scene.elapsed = 150.4
    scene._update_world_alert_lighting()
    if absf(lens_material.emission_energy_multiplier - steady_energy) > 0.008:
        push_error("Reduced-flashes setting must remove the animated emergency brightness pulse")
        quit(1)
        return
    if scene.alert_beacon_material.emission_energy_multiplier > 4.01:
        push_error("Threat alert exceeded existing perimeter emissive beacon budget")
        quit(1)
        return
    scene.elapsed = 0.0
    scene.reduced_flashes = false
    scene._update_world_alert_lighting()

    print("Deadline Zero environment identity: OK")
    quit(0)
