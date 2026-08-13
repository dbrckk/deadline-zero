package com.deadlinezero.game.abilities;

import java.util.EnumMap;

/** Persistent-in-run ability levels plus deterministic evolution/synergy contracts. */
public final class AbilityLoadout {
    public static final int MAX_LEVEL = 5;

    private final EnumMap<AbilityType, Integer> levels = new EnumMap<>(AbilityType.class);

    public int level(AbilityType type) { return levels.getOrDefault(type, 0); }
    public boolean unlocked(AbilityType type) { return level(type) > 0; }

    /** Tier 0=locked, 1=levels 1-2, 2=levels 3-4, 3=level 5 evolution tier. */
    public int tier(AbilityType type) {
        int level = level(type);
        if (level <= 0) return 0;
        if (level >= 5) return 3;
        if (level >= 3) return 2;
        return 1;
    }

    public boolean evolved(AbilityType type) { return tier(type) >= 3; }

    public int upgrade(AbilityType type) {
        int next = Math.min(MAX_LEVEL, level(type) + 1);
        levels.put(type, next);
        return next;
    }

    /** Arc Reactor: evolved Tesla + established Drone turns both into a shock network. */
    public boolean hasTeslaEvolution() {
        return evolved(AbilityType.TESLA_ORB) && tier(AbilityType.DRONE) >= 2;
    }

    /** Cryo Barrage: mature Cryo + Missile trees convert missiles into enlarged frost payloads. */
    public boolean hasCryoMissileEvolution() {
        return tier(AbilityType.CRYO_NOVA) >= 2 && tier(AbilityType.MISSILE_SWARM) >= 2;
    }

    /** Superconductor: mature Tesla + Cryo primes targets for elemental overload reactions. */
    public boolean hasSuperconductorSynergy() {
        return tier(AbilityType.TESLA_ORB) >= 2 && tier(AbilityType.CRYO_NOVA) >= 2;
    }

    /** Target Network: mature Drone + Missile trees improve volley density and target pressure. */
    public boolean hasTargetNetworkSynergy() {
        return tier(AbilityType.DRONE) >= 2 && tier(AbilityType.MISSILE_SWARM) >= 2;
    }

    /** Permafrost Blades: mature Orbital + Cryo turns the blade into a close-range frost applicator. */
    public boolean hasPermafrostBladeSynergy() {
        return tier(AbilityType.ORBITAL_BLADE) >= 2 && tier(AbilityType.CRYO_NOVA) >= 2;
    }

    /** Storm Blade: evolved Orbital + evolved Tesla turns close-range hits into shock pressure. */
    public boolean hasStormBladeSynergy() {
        return evolved(AbilityType.ORBITAL_BLADE) && evolved(AbilityType.TESLA_ORB);
    }
}
