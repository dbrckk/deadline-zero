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
        var trail_mesh := trail.mesh as BoxMesh
        var feedback := DZWeaponProfiles.profile(profile)
        if trail_mesh == null or trail_mesh.size.z + 0.001 < float(feedback.get("trail_length", 0.0)):
            push_error("%s projectile trail lost configured premium streak length" % profile)
            quit(1)
            return
        if trail_mesh.size.x < 0.030:
            push_error("%s projectile trail became too thin for phone-scale readability" % profile)
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

        var duplicate := PROJECTILE_SCRIPT.new()
        duplicate.name = "Projectile_%s_duplicate" % profile
        duplicate.process_mode = Node.PROCESS_MODE_DISABLED
        duplicate.setup(
            Vector3(float(index) * 1.5, 0.7, 1.2),
            Vector3.FORWARD,
            10.0,
            10.0,
            Color(0.18, 0.90, 1.0),
            profile
        )
        root.add_child(duplicate)
        await process_frame
        var duplicate_core := duplicate.get_node_or_null("ProjectileCore") as MeshInstance3D
        var duplicate_trail := duplicate.get_node_or_null("ProjectileTrail") as MeshInstance3D
        if duplicate_core == null or duplicate_trail == null:
            push_error("%s duplicate projectile did not build cached visuals" % profile)
            quit(1)
            return
        if duplicate_core.mesh != core.mesh or duplicate_trail.mesh != trail.mesh:
            push_error("%s projectile visuals must reuse cached mesh resources" % profile)
            quit(1)
            return
        if duplicate_core.material_override != core.material_override or duplicate_trail.material_override != trail.material_override:
            push_error("%s projectile visuals must reuse cached material resources" % profile)
            quit(1)
            return
        match profile:
            "scatter":
                var sparks: Array[MeshInstance3D] = []
                var duplicate_sparks: Array[MeshInstance3D] = []
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is BoxMesh:
                            sparks.append(mesh_instance)
                for child in duplicate.get_children():
                    if child is MeshInstance3D and child != duplicate_core and child != duplicate_trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is BoxMesh:
                            duplicate_sparks.append(mesh_instance)
                if sparks.size() < 2 or duplicate_sparks.size() < 2:
                    push_error("Scatter projectile must build two side-spark accents")
                    quit(1)
                    return
                if sparks[0].mesh != duplicate_sparks[0].mesh:
                    push_error("Scatter projectile side sparks must reuse cached mesh resources")
                    quit(1)
                    return
            "cryo":
                if core.scale.y <= core.scale.x:
                    push_error("Cryo projectile core must retain elongated shard identity")
                    quit(1)
                    return
            "arc":
                var arc_accent: MeshInstance3D
                var duplicate_arc_accent: MeshInstance3D
                for child in projectile.get_children():
                    if child is MeshInstance3D and (child as MeshInstance3D).mesh is TorusMesh:
                        arc_accent = child as MeshInstance3D
                for child in duplicate.get_children():
                    if child is MeshInstance3D and (child as MeshInstance3D).mesh is TorusMesh:
                        duplicate_arc_accent = child as MeshInstance3D
                if arc_accent == null or duplicate_arc_accent == null:
                    push_error("Arc projectile runtime accent is missing")
                    quit(1)
                    return
                if arc_accent.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Arc accent must not cast shadows")
                    quit(1)
                    return
                if arc_accent.mesh != duplicate_arc_accent.mesh or arc_accent.material_override != duplicate_arc_accent.material_override:
                    push_error("Arc accents must reuse cached mesh/material resources")
                    quit(1)
                    return
            "inferno":
                var flame: MeshInstance3D
                var duplicate_flame: MeshInstance3D
                for child in projectile.get_children():
                    if child is MeshInstance3D and child != core and child != trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is SphereMesh and mesh_instance.scale.z > mesh_instance.scale.x:
                            flame = mesh_instance
                for child in duplicate.get_children():
                    if child is MeshInstance3D and child != duplicate_core and child != duplicate_trail:
                        var mesh_instance := child as MeshInstance3D
                        if mesh_instance.mesh is SphereMesh and mesh_instance.scale.z > mesh_instance.scale.x:
                            duplicate_flame = mesh_instance
                if flame == null or duplicate_flame == null:
                    push_error("Inferno projectile runtime flame identity is missing")
                    quit(1)
                    return
                if flame.cast_shadow != GeometryInstance3D.SHADOW_CASTING_SETTING_OFF:
                    push_error("Inferno flame core must not cast shadows")
                    quit(1)
                    return
                if flame.mesh != duplicate_flame.mesh:
                    push_error("Inferno flame cores must reuse cached mesh resources")
                    quit(1)
                    return

        duplicate.queue_free()
        await process_frame
        projectile.queue_free()
        await process_frame

    print("Deadline Zero projectile profile runtime visuals: OK")
    quit(0)
