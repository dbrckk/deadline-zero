class_name DZCombatFeel
extends RefCounted

const DEFAULT_HIT_FREEZE := 0.022
const CRITICAL_HIT_FREEZE := 0.038
const KILL_HIT_FREEZE := 0.030
const BOSS_HIT_FREEZE := 0.044

static func hit_freeze_seconds(critical: bool, killed: bool, boss: bool) -> float:
    if boss:
        return BOSS_HIT_FREEZE
    if critical:
        return CRITICAL_HIT_FREEZE
    if killed:
        return KILL_HIT_FREEZE
    return DEFAULT_HIT_FREEZE

static func camera_kick(critical: bool, killed: bool, boss: bool) -> float:
    var kick := 0.055
    if killed:
        kick += 0.025
    if critical:
        kick += 0.035
    if boss:
        kick += 0.050
    return min(kick, 0.16)
