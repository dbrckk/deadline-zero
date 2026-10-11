class_name DZWeaponProfiles
extends RefCounted

const PROFILES := {
    "vanguard": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(1.0, 0.74, 0.18),
        "muzzle_size": 1.00,
        "recoil_kick": 0.085,
        "recoil_recover": 0.075,
        "flash_duration": 0.055,
        "tint": Color(0.18, 0.90, 1.0),
        "damage_multiplier": 1.0,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "projectile_scale": 1.0,
        "trail_length": 0.72,
        "impact_weight": 1.06
    },
    "scatter": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(1.0, 0.50, 0.12),
        "muzzle_size": 1.38,
        "recoil_kick": 0.145,
        "recoil_recover": 0.110,
        "flash_duration": 0.070,
        "tint": Color(1.0, 0.56, 0.18),
        "damage_multiplier": 0.82,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.0,
        "multishot_add": 2,
        "multishot_cap": 5,
        "spread_min": 11.0,
        "projectile_scale": 1.16,
        "trail_length": 0.42,
        "impact_weight": 1.24
    },
    "rail": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(0.76, 0.57, 1.0),
        "muzzle_size": 0.86,
        "recoil_kick": 0.160,
        "recoil_recover": 0.145,
        "flash_duration": 0.050,
        "tint": Color(0.72, 0.58, 1.0),
        "damage_multiplier": 1.50,
        "projectile_speed_multiplier": 1.40,
        "fire_interval_multiplier": 1.22,
        "fire_interval_cap": 0.80,
        "multishot_set": 1,
        "spread_set": 3.0,
        "projectile_scale": 0.78,
        "trail_length": 1.55,
        "impact_weight": 1.42
    },
    "inferno": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(1.0, 0.27, 0.055),
        "muzzle_size": 1.22,
        "recoil_kick": 0.110,
        "recoil_recover": 0.095,
        "flash_duration": 0.065,
        "tint": Color(1.0, 0.24, 0.035),
        "damage_multiplier": 1.20,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 1.08,
        "fire_interval_cap": 0.80,
        "projectile_scale": 1.10,
        "trail_length": 0.92,
        "impact_weight": 1.30
    },
    "cryo": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(0.38, 0.89, 1.0),
        "muzzle_size": 1.02,
        "recoil_kick": 0.072,
        "recoil_recover": 0.070,
        "flash_duration": 0.052,
        "tint": Color(0.30, 0.90, 1.0),
        "damage_multiplier": 0.95,
        "projectile_speed_multiplier": 1.12,
        "fire_interval_multiplier": 0.90,
        "fire_interval_floor": 0.09,
        "projectile_scale": 1.08,
        "trail_length": 1.00,
        "impact_weight": 1.30
    },
    "arc": {
        # Live barrel flash and recoil signature; no additional particles/lights.
        "muzzle_color": Color(0.69, 0.44, 1.0),
        "muzzle_size": 0.96,
        "recoil_kick": 0.087,
        "recoil_recover": 0.072,
        "flash_duration": 0.047,
        "tint": Color(0.64, 0.42, 1.0),
        "damage_multiplier": 0.90,
        "projectile_speed_multiplier": 1.0,
        "fire_interval_multiplier": 0.92,
        "fire_interval_floor": 0.09,
        "multishot_add": 1,
        "multishot_cap": 5,
        "spread_max": 4.0,
        "projectile_scale": 0.92,
        "trail_length": 1.10,
        "impact_weight": 1.28
    }
}

static func profile(id: String) -> Dictionary:
    var key := id if PROFILES.has(id) else "vanguard"
    return (PROFILES[key] as Dictionary).duplicate(true)
