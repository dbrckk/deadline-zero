extends RefCounted

const PROTOCOLS := {
    "scatter_protocol": {
        "weapon_profile": "scatter",
        "tint": Color(1.0, 0.56, 0.18),
        "damage_mult": 0.82,
        "multishot_add": 2,
        "spread_min": 11.0
    },
    "rail_protocol": {
        "weapon_profile": "rail",
        "tint": Color(0.72, 0.58, 1.0),
        "damage_mult": 1.50,
        "projectile_speed_mult": 1.40,
        "fire_interval_mult": 1.22,
        "fire_interval_max": 0.80,
        "multishot_set": 1,
        "spread_set": 3.0
    },
    "inferno_protocol": {
        "weapon_profile": "inferno",
        "tint": Color(1.0, 0.24, 0.035),
        "damage_mult": 1.20,
        "fire_interval_mult": 1.08,
        "fire_interval_max": 0.80
    },
    "cryo_protocol": {
        "weapon_profile": "cryo",
        "tint": Color(0.30, 0.90, 1.0),
        "damage_mult": 0.95,
        "projectile_speed_mult": 1.12,
        "fire_interval_mult": 0.90,
        "fire_interval_min": 0.09
    },
    "arc_protocol": {
        "weapon_profile": "arc",
        "tint": Color(0.64, 0.42, 1.0),
        "damage_mult": 0.90,
        "fire_interval_mult": 0.92,
        "fire_interval_min": 0.09,
        "multishot_add": 1,
        "spread_max": 4.0
    }
}

static func protocol(id: String) -> Dictionary:
    if not PROTOCOLS.has(id):
        return {}
    return (PROTOCOLS[id] as Dictionary).duplicate(true)
