class_name DZWeaponProfiles
extends RefCounted

const PROFILES := {
    "vanguard": {
        "tint": Color(0.18, 0.90, 1.0),
        "damage_multiplier": 1.0,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0
    },
    "scatter": {
        "tint": Color(1.0, 0.56, 0.18),
        "damage_multiplier": 0.82,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "multishot_add": 2,
        "multishot_cap": 5,
        "spread_min": 11.0
    },
    "rail": {
        "tint": Color(0.72, 0.58, 1.0),
        "damage_multiplier": 1.50,
        "projectile_speed_multiplier": 1.40,
        "fire_interval_multiplier": 1.22,
        "fire_interval_cap": 0.80,
        "multishot_set": 1,
        "spread_set": 3.0
    },
    "inferno": {
        "tint": Color(1.0, 0.24, 0.035),
        "damage_multiplier": 1.20,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.08,
        "fire_interval_cap": 0.80
    },
    "cryo": {
        "tint": Color(0.30, 0.90, 1.0),
        "damage_multiplier": 0.95,
        "projectile_speed_multiplier": 1.12,
        "fire_interval_multiplier": 0.90,
        "fire_interval_floor": 0.09
    },
    "arc": {
        "tint": Color(0.64, 0.42, 1.0),
        "damage_multiplier": 0.90,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 0.92,
        "fire_interval_floor": 0.09,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0
    }
}

static func profile(id: String) -> Dictionary:
    var key := id if PROFILES.has(id) else "vanguard"
    return (PROFILES[key] as Dictionary).duplicate(true)
