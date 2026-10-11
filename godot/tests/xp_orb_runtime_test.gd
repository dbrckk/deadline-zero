extends SceneTree

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var target := Node3D.new()
    target.global_position = Vector3.ZERO
    root.add_child(target)

    var first := DZXpOrb.new()
    first.target = target
    first.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(first)
    first.global_position = Vector3(12.0, 0.18, 0.0)

    var second := DZXpOrb.new()
    second.target = target
    second.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(second)
    second.global_position = Vector3(14.0, 0.18, 0.0)
    await process_frame

    var first_visual := first.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var second_visual := second.get_node_or_null("XpOrbVisual") as MeshInstance3D
    var first_halo := first.get_node_or_null("XpOrbHalo") as MeshInstance3D
    var second_halo := second.get_node_or_null("XpOrbHalo") as MeshInstance3D
    if first_visual == null or second_visual == null or first_halo == null or second_halo == null:
        push_error("XP orb premium core/halo production visual is missing")
        quit(1)
        return
    if first_visual.mesh != second_visual.mesh:
        push_error("XP orbs must reuse one shared mesh resource")
        quit(1)
        return
    if first_visual.material_override != second_visual.material_override:
        push_error("XP orbs must reuse one shared core material resource")
        quit(1)
        return
    if first_halo.mesh != second_halo.mesh or first_halo.material_override != second_halo.material_override:
        push_error("XP orbs must reuse shared halo mesh/material resources")
        quit(1)
        return
    if first_halo.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb halo must remain shadow-free")
        quit(1)
        return
    var halo_mesh := first_halo.mesh as TorusMesh
    if halo_mesh == null or halo_mesh.rings > 16 or halo_mesh.ring_segments > 6:
        push_error("XP orb halo geometry budget regressed")
        quit(1)
        return
    if first_visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
        push_error("XP orb visual must not cast mobile-costly dynamic shadows")
        quit(1)
        return

    var mesh := first_visual.mesh as SphereMesh
    if mesh == null or mesh.radial_segments > 12 or mesh.rings > 6:
        push_error("XP orb geometry budget regressed")
        quit(1)
        return

    if DZXpOrb.tier_for_amount(2) != "standard" or DZXpOrb.tier_for_amount(8) != "elite" or DZXpOrb.tier_for_amount(35) != "boss":
        push_error("XP orb value tier thresholds regressed")
        quit(1)
        return

    var elite := DZXpOrb.new()
    elite.amount = 8
    elite.target = target
    elite.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(elite)
    var boss := DZXpOrb.new()
    boss.amount = 35
    boss.target = target
    boss.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss)
    var boss_duplicate := DZXpOrb.new()
    boss_duplicate.amount = 35
    boss_duplicate.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(boss_duplicate)
    await process_frame

    var elite_visual := elite.get_node("XpOrbVisual") as MeshInstance3D
    var boss_visual := boss.get_node("XpOrbVisual") as MeshInstance3D
    var boss_duplicate_visual := boss_duplicate.get_node("XpOrbVisual") as MeshInstance3D
    var elite_halo := elite.get_node("XpOrbHalo") as MeshInstance3D
    var boss_halo := boss.get_node("XpOrbHalo") as MeshInstance3D
    var boss_duplicate_halo := boss_duplicate.get_node("XpOrbHalo") as MeshInstance3D
    if elite.visual_tier_scale <= first.visual_tier_scale or boss.visual_tier_scale <= elite.visual_tier_scale:
        push_error("Higher-value XP rewards need visibly larger 3D silhouettes")
        quit(1)
        return
    if elite_visual.mesh != first_visual.mesh or boss_visual.mesh != first_visual.mesh:
        push_error("XP value tiers must share one low-poly core mesh")
        quit(1)
        return
    if elite_halo.mesh != first_halo.mesh or boss_halo.mesh != first_halo.mesh:
        push_error("XP value tiers must share one low-poly ring mesh")
        quit(1)
        return
    if boss_visual.material_override != boss_duplicate_visual.material_override or boss_halo.material_override != boss_duplicate_halo.material_override:
        push_error("Boss XP rewards must share cached tier materials")
        quit(1)
        return
    if boss_visual.material_override == first_visual.material_override or elite_visual.material_override == first_visual.material_override:
        push_error("Premium XP tiers lost their independent color identity")
        quit(1)
        return
    if (boss_visual.material_override as StandardMaterial3D).emission_energy_multiplier <= (first_visual.material_override as StandardMaterial3D).emission_energy_multiplier:
        push_error("Boss XP reward must be more emissive than ordinary drops")
        quit(1)
        return
    for drop in [elite, boss, boss_duplicate]:
        for visual in [drop.get_node("XpOrbVisual"), drop.get_node("XpOrbHalo")]:
            if visual.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                push_error("Premium XP rewards must remain shadow-free")
                quit(1)
                return
        for child in drop.get_children():
            if child is Light3D or child is GPUParticles3D:
                push_error("Premium XP tiers may not add lights or emitters per pickup")
                quit(1)
                return

    var initial_distance := first.global_position.distance_to(target.global_position)
    first.age = DZXpOrb.FORCED_MAGNET_AGE
    var halo_scale_before := first_halo.scale.x
    first._process(0.20)
    var forced_distance := first.global_position.distance_to(target.global_position)
    if forced_distance >= initial_distance:
        push_error("Old XP orb did not force-magnet toward the player")
        quit(1)
        return
    if first_halo.scale.x <= halo_scale_before:
        push_error("XP orb halo did not expand when entering magnetized pickup state")
        quit(1)
        return

    second.age = 0.0
    var second_initial := second.global_position
    second._process(0.20)
    if second.global_position.distance_to(second_initial) > 0.05:
        push_error("Fresh distant XP orb should remain parked until magnet range/age threshold")
        quit(1)
        return

    print("Deadline Zero XP orb runtime: OK")
    quit(0)
