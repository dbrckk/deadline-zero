extends SceneTree

# Rendering-budget QA for directional shot feedback on the actual survivor.
func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    var survivor := DZPlayer.new()
    root.add_child(survivor)
    survivor.set_combat_enabled(false)
    await process_frame

    var core := survivor.muzzle_flash
    if core == null or not core is MeshInstance3D:
        push_error("Authored survivor is missing its original 3D muzzle core")
        quit(1)
        return
    if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("Muzzle core must not enter mobile directional shadow map")
        quit(1)
        return

    var spikes := [
        core.get_node_or_null("MuzzleFlashWingL") as MeshInstance3D,
        core.get_node_or_null("MuzzleFlashSpear") as MeshInstance3D,
        core.get_node_or_null("MuzzleFlashWingR") as MeshInstance3D,
    ]
    var shared_mesh: Mesh
    for index in range(spikes.size()):
        var spike: MeshInstance3D = spikes[index]
        if spike == null:
            push_error("Production shot flare missing directional 3D spike: %d" % index)
            quit(1)
            return
        if spike.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("Muzzle spike creates expensive mobile shadows")
            quit(1)
            return
        if spike.material_override != core.material_override or spike.material_override != survivor.muzzle_flash_material:
            push_error("Layered muzzle flare lost shared accessibility-aware emissive material")
            quit(1)
            return
        var cone := spike.mesh as CylinderMesh
        if cone == null or cone.radial_segments > 6 or cone.rings > 1:
            push_error("Muzzle flare spike exceeded constrained six-sided geometry budget")
            quit(1)
            return
        if shared_mesh == null:
            shared_mesh = cone
        elif spike.mesh != shared_mesh:
            push_error("Muzzle flare creates duplicate tapered mesh resources")
            quit(1)
            return
    if spikes[1].position.z >= -0.10 or spikes[0].position.x >= 0.0 or spikes[2].position.x <= 0.0:
        push_error("Muzzle flare lost forward-pointing left/center/right silhouette")
        quit(1)
        return

    survivor.set_reduced_flashes(false)
    survivor._trigger_muzzle_flash()
    if not core.visible or survivor.muzzle_flash_material.emission_energy_multiplier < 6.0:
        push_error("Ordinary muzzle flare no longer creates readable shot feedback")
        quit(1)
        return

    survivor.set_reduced_flashes(true)
    survivor._trigger_muzzle_flash()
    if not core.visible or survivor.muzzle_flash_material.emission_energy_multiplier > 2.21:
        push_error("Reduced-flashes setting did not dim all shared 3D muzzle flare geometry")
        quit(1)
        return
    if survivor.muzzle_flash_tween == null or not survivor.muzzle_flash_tween.is_running():
        push_error("Short muzzle flash envelope was not triggered")
        quit(1)
        return

    await create_timer(0.18).timeout
    if core.visible:
        push_error("Mobile muzzle flare persisted beyond its brief shot window")
        quit(1)
        return

    print("Deadline Zero authored directional 3D muzzle flare: OK (3 shared tapered spikes)")
    quit(0)
