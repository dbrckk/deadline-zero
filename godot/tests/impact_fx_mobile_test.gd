extends SceneTree

const IMPACT_SCRIPT := preload("res://scripts/ImpactFx.gd")

func _initialize() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var fx := IMPACT_SCRIPT.new()
    root.add_child(fx)
    await process_frame

    var light_count := 0
    var mesh_count := 0
    var ring_found := false
    for child in fx.get_children():
        if child is OmniLight3D:
            light_count += 1
        if child is MeshInstance3D:
            mesh_count += 1
            if child.name == "ImpactRing":
                ring_found = true

    if light_count != 0:
        push_error("Impact FX should avoid per-hit dynamic lights on mobile")
        quit(1)
        return
    if mesh_count < 2 or not ring_found:
        push_error("Impact FX is missing layered emissive geometry")
        quit(1)
        return

    var sparks := fx.get_node_or_null("ImpactSparks") as GPUParticles3D
    if sparks == null:
        push_error("Impact FX is missing mobile-safe GPU sparks")
        quit(1)
        return
    if sparks.amount > 12 or sparks.amount < 4:
        push_error("Impact spark count must stay within mobile budget")
        quit(1)
        return
    if sparks.lifetime > 0.35:
        push_error("Impact sparks live too long for dense mobile combat")
        quit(1)
        return

    print("Deadline Zero mobile-safe impact FX: OK")
    quit(0)
