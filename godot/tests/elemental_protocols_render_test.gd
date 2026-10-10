extends SceneTree

# Real renderer evidence for Arc links, Inferno shockwave/scorch, actual burn and Cryo slow.
const OUTPUT_PATH := "/tmp/deadline-zero-elemental-protocols.png"
const RESIDUE_PATH := "/tmp/deadline-zero-inferno-residue.png"
const MAIN_SCENE := preload("res://scenes/Main.tscn")

func _initialize() -> void:
    call_deferred("_capture")

func _capture() -> void:
    var scene := MAIN_SCENE.instantiate()
    get_root().add_child(scene)
    current_scene = scene
    await process_frame
    if scene.player == null or scene.camera == null:
        push_error("Elemental visual QA failed to instantiate player and camera")
        quit(1)
        return

    scene.set_physics_process(false)
    scene.player.set_combat_enabled(false)
    if scene.hud != null:
        scene.hud.hide_onboarding_hint()
    for existing in get_nodes_in_group("enemies"):
        existing.queue_free()
    await process_frame

    # Freeze authored zombie poses so 3D protocol readability, rather than
    # random director spawns, is compared from render to render.
    var placements := [
        {"kind":"elite", "position":Vector3(-2.6, 0.0, -0.3)},
        {"kind":"runner", "position":Vector3(-4.9, 0.0, 1.4)},
        {"kind":"regenerator", "position":Vector3(-4.4, 0.0, -2.2)},
        {"kind":"brute", "position":Vector3(3.0, 0.0, 0.0)},
        {"kind":"shambler", "position":Vector3(4.7, 0.0, -0.8)}
    ]
    var cryo_victim: DZEnemy
    var burning_victim: DZEnemy
    for entry in placements:
        var enemy := DZEnemy.new()
        enemy.configure(String(entry["kind"]), 1.0, scene.player)
        enemy.process_mode = Node.PROCESS_MODE_DISABLED
        scene.add_child(enemy)
        enemy.global_position = entry["position"]
        if String(entry["kind"]) == "shambler":
            cryo_victim = enemy
        if String(entry["kind"]) == "brute":
            burning_victim = enemy
    await process_frame

    if cryo_victim == null or burning_victim == null:
        push_error("Elemental visual QA missing Cryo or burning subjects")
        quit(1)
        return
    cryo_victim.apply_slow(0.62, 1.6)
    var ice := DZCryoStatusFx.attach_to(cryo_victim)
    if ice == null or ice.get_node_or_null("FrostCrystals") == null:
        push_error("Cryo status crown failed to stage in real 3D gameplay rendering")
        quit(1)
        return

    burning_victim.apply_burn(4.2, 2.0)
    var flames := DZBurnStatusFx.attach_to(burning_victim)
    if flames == null or flames.get_node_or_null("InfernoEmbers") == null:
        push_error("Actual Inferno burn status failed to stage in the real 3D renderer")
        quit(1)
        return

    var anchor := Vector3(-2.6, 0.72, -0.3)
    var link_a := DZArcLinkFx.spawn_link(scene, anchor, Vector3(-4.9, 0.72, 1.4), 0)
    var link_b := DZArcLinkFx.spawn_link(scene, anchor, Vector3(-4.4, 0.72, -2.2), 1)
    var blast := DZInfernoBlastFx.spawn_blast(scene, Vector3(3.0, 0.0, 0.0), 1.85)
    if link_a == null or link_b == null or blast == null:
        push_error("Elemental visual QA did not stage all three real 3D combat effects")
        quit(1)
        return

    # Simulate one time slice, then hold the GPU mesh effects for the screenshot.
    for effect in [link_a, link_b, blast]:
        effect.set_process(false)
        effect._process(0.065)

    for _frame in range(7):
        await process_frame

    if link_a.get_node_or_null("ArcRibbon") == null or blast.get_node_or_null("BlastFront") == null or flames.get_node_or_null("InfernoEmbers") == null:
        push_error("Elemental protocol meshes vanished before capture")
        quit(1)
        return

    var texture := get_root().get_texture()
    if texture == null:
        push_error("Elemental render capture lacks a viewport texture")
        quit(1)
        return
    var image := texture.get_image()
    if image == null or image.is_empty() or image.get_width() < 1280 or image.get_height() < 720:
        push_error("Elemental visual QA did not produce a valid 1280x720 capture")
        quit(1)
        return

    image.convert(Image.FORMAT_RGB8)
    var error := image.save_png(OUTPUT_PATH)
    if error != OK:
        push_error("Failed to save elemental VFX render: %s" % error_string(error))
        quit(1)
        return
    # A second real frame proves the blast ring disappears while the charred
    # footprint is still visible, without copying the first render.
    blast._process(0.36)
    if blast.is_queued_for_deletion():
        push_error("Inferno afterglow disappeared before the second rendered frame")
        quit(1)
        return
    for _frame in range(4):
        await process_frame
    var residue_image := get_root().get_texture().get_image()
    if residue_image == null or residue_image.is_empty():
        push_error("Inferno heat residue did not render")
        quit(1)
        return
    residue_image.convert(Image.FORMAT_RGB8)
    if residue_image.save_png(RESIDUE_PATH) != OK:
        push_error("Failed to save rendered Inferno scorch residue")
        quit(1)
        return

    # A material/mesh contract alone cannot prove that a bright flame actually
    # survives GLTF body occlusion and the Android-compatible renderer. Compare
    # two identically staged GPU frames, with only the burn mesh removed. This
    # specifically protects against the earlier invisible-fire regression.
    var burn_screen: Vector2 = scene.camera.unproject_position(
        burning_victim.global_position + Vector3(0.0, 0.85, 0.0)
    )
    flames.queue_free()
    for _frame in range(3):
        await process_frame
    var without_burn := get_root().get_texture().get_image()
    if without_burn == null or without_burn.is_empty():
        push_error("Burn visibility comparison could not read the second GPU frame")
        quit(1)
        return
    without_burn.convert(Image.FORMAT_RGB8)
    var visible_flame_pixels := 0
    var center_x := int(round(burn_screen.x))
    var center_y := int(round(burn_screen.y))
    for y in range(maxi(0, center_y - 100), mini(residue_image.get_height(), center_y + 100)):
        for x in range(maxi(0, center_x - 100), mini(residue_image.get_width(), center_x + 100)):
            var burning_color := residue_image.get_pixel(x, y)
            var extinguished_color := without_burn.get_pixel(x, y)
            var difference := maxf(
                absf(burning_color.r - extinguished_color.r),
                absf(burning_color.g - extinguished_color.g)
            )
            if difference > 0.12 and burning_color.r > burning_color.b * 1.5:
                visible_flame_pixels += 1
    if visible_flame_pixels < 100:
        push_error("Inferno burning is hidden or too dim in a real rendered frame: only %d distinct pixels" % visible_flame_pixels)
        quit(1)
        return
    print("GODOT_BURN_VISIBLE_PIXELS_OK %d" % visible_flame_pixels)

    print("GODOT_ELEMENTAL_PROTOCOL_VISUAL_QA_OK %dx%d with real burn, Cryo, Arc and Inferno residue" % [image.get_width(), image.get_height()])
    quit(0)
