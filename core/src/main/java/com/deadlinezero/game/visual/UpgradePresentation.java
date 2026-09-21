package com.deadlinezero.game.visual;

import com.deadlinezero.game.progression.Upgrade;

/** Pure routing from run upgrades to a stable visual archetype. */
public final class UpgradePresentation {
    public enum Archetype {
        RATE,
        DAMAGE,
        MOBILITY,
        VITALITY,
        MULTISHOT,
        CRITICAL,
        BALLISTIC,
        BREACH,
        FIRE,
        FROST,
        SHOCK,
        ELEMENTAL,
        MISSILE,
        DRONE,
        ORBITAL,
        PROTOCOL
    }

    private UpgradePresentation() { }

    public static Archetype archetype(Upgrade upgrade) {
        if (upgrade == null) return Archetype.DAMAGE;
        return switch (upgrade) {
            case RAPID_FIRE, SUPPRESSIVE_CYCLE, ADAPTIVE_TRIGGER -> Archetype.RATE;
            case DAMAGE, HEAVY_BARREL, FOCUSED_PAYLOAD, GLASS_CANNON, BERSERKER_CALIBER,
                 LAST_STAND, VETERAN_CORE -> Archetype.DAMAGE;
            case SPEED, SCOUT_FRAME, PHASE_CAPACITOR, AFTERBURNER, MOMENTUM_CORE, DASH_CORE -> Archetype.MOBILITY;
            case VITALITY, BULWARK_FRAME, COMBAT_STIMS, REACTIVE_PLATING, FIELD_REPAIR -> Archetype.VITALITY;
            case MULTISHOT, CROSSFIRE, BARRAGE_MATRIX -> Archetype.MULTISHOT;
            case CRIT, CRIT_POWER, EXECUTIONER, PRECISION_MATRIX -> Archetype.CRITICAL;
            case BALLISTICS, LIGHTWEIGHT_BOLT, TIGHT_CHOKE, HYPER_VELOCITY -> Archetype.BALLISTIC;
            case PENETRATION, KNOCKBACK, SIEGE_ROUNDS, IMPACT_CORE, BREACH_MATRIX -> Archetype.BREACH;
            case INCENDIARY, FIRE_CONTROL, THERMAL_LANCE -> Archetype.FIRE;
            case CRYO, FROST_CONTROL, CRYO_HAMMER, CRYO_NOVA -> Archetype.FROST;
            case SHOCK, SHOCK_CONTROL, ARC_LANCER, TESLA_ORB -> Archetype.SHOCK;
            case ELEMENTAL_HARMONIZER -> Archetype.ELEMENTAL;
            case MISSILE_SWARM -> Archetype.MISSILE;
            case DRONE, DRONE_HUNTER_DOCTRINE, DRONE_SENTINEL_DOCTRINE -> Archetype.DRONE;
            case ORBITAL -> Archetype.ORBITAL;
            case RHYTHM_DRIVER, RHYTHM_ACCELERATOR, KILLCHAIN_CAPACITOR, KILLCHAIN_OVERCHARGE,
                 REACTION_CORE, REACTION_CASCADE -> Archetype.PROTOCOL;
        };
    }
}
