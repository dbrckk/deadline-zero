class_name DZSpatialHash
extends RefCounted

var cell_size := 4.0
var buckets := {}

func _init(size := 4.0) -> void:
    cell_size = maxf(0.5, float(size))

func rebuild(nodes: Array) -> void:
    buckets.clear()
    for node in nodes:
        if node == null or not is_instance_valid(node) or not node is Node3D:
            continue
        var key := _cell((node as Node3D).global_position)
        if not buckets.has(key):
            buckets[key] = []
        buckets[key].append(node)

func query(position: Vector3, radius: float) -> Array:
    var result: Array = []
    var safe_radius := maxf(0.0, radius)
    var min_key := _cell(position - Vector3(safe_radius, 0.0, safe_radius))
    var max_key := _cell(position + Vector3(safe_radius, 0.0, safe_radius))
    var radius_sq := safe_radius * safe_radius
    for x in range(min_key.x, max_key.x + 1):
        for z in range(min_key.y, max_key.y + 1):
            var key := Vector2i(x, z)
            if not buckets.has(key):
                continue
            for node in buckets[key]:
                if node == null or not is_instance_valid(node) or not node is Node3D:
                    continue
                var delta := (node as Node3D).global_position - position
                delta.y = 0.0
                if delta.length_squared() <= radius_sq:
                    result.append(node)
    return result

func _cell(position: Vector3) -> Vector2i:
    return Vector2i(
        int(floor(position.x / cell_size)),
        int(floor(position.z / cell_size))
    )
