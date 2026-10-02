extends SceneTree

const PROJECTILE_SCRIPT := preload("res://scripts/Projectile.gd")
const PROFILES := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root
    await process_frame

    for index in range(PROFILES.size()):
        var profile: String = PROFILES[index]
        var projectile := PROJECTILE_SCRIPT.new()
        projectile.name = "Projectile_%s" % profile
        projectile.process_mode = Node.PROCESS_MODE_DISABLED
        projectile.setup(
            Vector3(float(index) * 1.5, 0.7, 0.0),
            Vector3.FORWARD,
            10.0,
            10.0,
            Color(0.18, 0.90, 1.0),
            profile
        )
        root.add_child(projectile)
        await process_frame

        var core := projectile.get_node_or_null("ProjectileCore") as MeshInstance3D
        var trail := projectile.get_node_or_null("ProjectileTrail") as MeshInstance3D
        if core == null or trail == null:
            push_error("%s projectile did not build core/trail runtime visuals" % profile)
            quit(1)
            return
        if core.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("%s projectile core must not cast mobile-unfriendly shadows" % profile)
            quit(1)
            return
        if trail.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
            push_error("%s projectile trail must not cast mobile-unfriendly shadows" % profile)
            quit(1)
            return

        match profile:
            "scatter":
                var sparks := 0
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is BoxMesh:
                            sparks += 1
                if sparks < 2:
                    push_error("Scatter projectile must build two side-spark accents")
                    quit(1)
                    return
            "cryo":
                if core.scale.y <= core.scale.x:
                    push_error("Cryo projectile core must retain elongated shard identity")
                    quit(1)
                    return
            "arc":
                var torus_found := false
                for child in projectile.get_children():
                    if child is MeshInstance3D and (child as MeshInstance3D).mesh is TorusMesh:
                        torus_found = true
                        if (child as MeshInstance3D).cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                            push_error("Arc accent must not cast shadows")
                            quit(1)
                            return
                if not torus_found:
                    push_error("Arc projectile runtime accent is missing")
                    quit(1)
                    return
            "inferno":
                var flame_found := false
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is SphereMesh and mesh_instance.scale.z > mesh_instance.scale.x:
                            flame_found = true
                            if mesh_instance.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                                push_error("Inferno flame core must not cast shadows")
                                quit(1)
                                return
                if not flame_found:
                    push_error("Inferno projectile runtime flame identity is missing")
                    quit(1)
                    return

        projectile.queue_free()
        await process_frame

    print("Deadline Zero projectile profile runtime visuals: OK")
    quit(0)
