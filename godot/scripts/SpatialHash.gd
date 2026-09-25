extends RefCounted

var cell_size := 4.0
var buckets := {}

func _init(size := 4.0) -> void:
    cell_size = maxf(0.5, float(size))

func clear() -> void:
    buckets.clear()

func insert(value: Variant, position: Vector3) -> void:
    var key := _cell_for(position)
    if not buckets.has(key):
        buckets[key] = []
    var bucket: Array = buckets[key]
    if not bucket.has(value):
        bucket.append(value)

func query(position: Vector3, radius: float) -> Array:
    var result: Array = []
    var safe_radius := maxf(0.0, radius)
    var min_cell := _cell_for(position - Vector3(safe_radius, 0.0, safe_radius))
    var max_cell := _cell_for(position + Vector3(safe_radius, 0.0, safe_radius))
    for x in range(min_cell.x, max_cell.x + 1):
        for z in range(min_cell.y, max_cell.y + 1):
            var key := Vector2i(x, z)
            if not buckets.has(key):
                continue
            for value in buckets[key]:
                if not result.has(value):
                    result.append(value)
    return result

func _cell_for(position: Vector3) -> Vector2i:
    return Vector2i(
        int(floor(position.x / cell_size)),
        int(floor(position.z / cell_size))
    )
