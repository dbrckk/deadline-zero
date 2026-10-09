extends SceneTree

# Real firing QA: bullets must visibly originate at the authored rifle muzzle,
# never at the character's torso, across far/close bearings and weapon protocols.
const PROFILES := ["vanguard", "scatter", "rail", "inferno", "cryo", "arc"]

func _initialize() -> void:
    call_deferred("_run_test")

func _run_test() -> void:
    var root := Node3D.new()
    get_root().add_child(root)
    current_scene = root

    var survivor := DZPlayer.new()
    survivor.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(survivor)

    var enemy := DZEnemy.new()
    enemy.configure("shambler", 1.0, survivor)
    enemy.process_mode = Node.PROCESS_MODE_DISABLED
    root.add_child(enemy)
    await process_frame

    if survivor.muzzle_flash == null or survivor.muzzle_flash.get_parent() != survivor:
        push_error("Real rifle muzzle locator is missing from the production survivor")
        quit(1)
        return

    survivor.multishot = 3
    survivor.spread_degrees = 8.0
    var bearings := [Vector3(8.0, 0.0, -5.0), Vector3(-7.0, 0.0, 4.0), Vector3(0.0, 0.0, -1.45)]
    for bearing in bearings:
        enemy.global_position = bearing
        survivor.look_at(bearing, Vector3.UP)
        for profile in PROFILES:
            survivor.weapon_profile = profile
            survivor._fire_at(enemy)
            var shots := get_nodes_in_group("projectiles")
            if shots.size() != survivor.multishot:
                push_error("%s barrel origin test produced %d projectiles, expected %d" % [profile, shots.size(), survivor.multishot])
                quit(1)
                return

            var base_direction := survivor.global_position.direction_to(enemy.global_position)
            base_direction.y = 0.0
            base_direction = base_direction.normalized()
            var muzzle_to_target := enemy.global_position - survivor._projectile_muzzle_origin(Vector3.ZERO)
            muzzle_to_target.y = 0.0
            if muzzle_to_target.length_squared() > 0.0001:
                base_direction = muzzle_to_target.normalized()
            for index in range(shots.size()):
                var projectile := shots[index] as DZProjectile
                if projectile == null or projectile.visual_profile != profile:
                    push_error("%s shot routing lost its authored projectile profile" % profile)
                    quit(1)
                    return
                projectile.process_mode = Node.PROCESS_MODE_DISABLED
                var spread_offset := float(index) - float(survivor.multishot - 1) * 0.5
                var direction := base_direction.rotated(Vector3.UP, deg_to_rad(spread_offset * survivor.spread_degrees))
                var expected := survivor.muzzle_flash.global_position + direction * 0.12
                if projectile.global_position.distance_to(expected) > 0.005:
                    push_error("%s projectile %d detached from the visible 3D rifle muzzle" % [profile, index])
                    quit(1)
                    return
                if projectile.velocity.normalized().distance_to(direction) > 0.001:
                    push_error("%s multishot lost the intended directional spread" % profile)
                    quit(1)
                    return
                if projectile.global_position.y < 0.90:
                    push_error("%s projectile spawned inside the player's torso" % profile)
                    quit(1)
                    return
                if index == 1:
                    var target_offset := enemy.global_position - projectile.global_position
                    var lateral_error := absf(Vector2(target_offset.x, target_offset.z).cross(Vector2(direction.x, direction.z)))
                    if lateral_error > 0.04:
                        push_error("%s centered shot misses the close target because of the rifle barrel offset" % profile)
                        quit(1)
                        return
                projectile.queue_free()
            await process_frame

    print("Deadline Zero rifle muzzle ballistics: OK (six protocols, three bearings, 54 shots)")
    quit(0)
