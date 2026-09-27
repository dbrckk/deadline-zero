class_name DZWeaponProfiles
extends RefCounted

const PROFILES := {
    "vanguard": {
        "tint": Color(0.18, 0.90, 1.0),
        "damage_multiplier": 1.0,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "projectile_scale": 1.0,
        "trail_length": 0.55,
        "impact_weight": 1.0
    },
    "scatter": {
        "tint": Color(1.0, 0.56, 0.18),
        "damage_multiplier": 0.82,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "multishot_add": 2,
        "multishot_cap": 5,
        "spread_min": 11.0,
        "projectile_scale": 1.16,
        "trail_length": 0.32,
        "impact_weight": 1.18
    },
    "rail": {
        "tint": Color(0.72, 0.58, 1.0),
        "damage_multiplier": 1.50,
        "projectile_speed_multiplier": 1.40,
        "fire_interval_multiplier": 1.22,
        "fire_interval_cap": 0.80,
        "multishot_set": 1,
        "spread_set": 3.0,
        "projectile_scale": 0.78,
        "trail_length": 1.25,
        "impact_weight": 1.34
    },
    "inferno": {
        "tint": Color(1.0, 0.24, 0.035),
        "damage_multiplier": 1.20,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.08,
        "fire_interval_cap": 0.80,
        "projectile_scale": 1.10,
        "trail_length": 0.72,
        "impact_weight": 1.22
    },
    "cryo": {
        "tint": Color(0.30, 0.90, 1.0),
        "damage_multiplier": 0.95,
        "projectile_speed_multiplier": 1.12,
        "fire_interval_multiplier": 0.90,
        "fire_interval_floor": 0.09,
        "projectile_scale": 1.08,
        "trail_length": 0.82,
        "impact_weight": 1.24
    },
    "arc": {
        "tint": Color(0.64, 0.42, 1.0),
        "damage_multiplier": 0.90,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 0.92,
        "fire_interval_floor": 0.09,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0,
        "projectile_scale": 0.92,
        "trail_length": 0.94,
        "impact_weight": 1.20
    }
}

static func profile(id: String) -> Dictionary:
    var key := id if PROFILES.has(id) else "vanguard"
    return (PROFILES[key] as Dictionary).duplicate(true)
