package com.deadlinezero.game.abilities;

import java.util.EnumMap;

public final class AbilityLoadout {
    private final EnumMap<AbilityType, Integer> levels = new EnumMap<>(AbilityType.class);

    public int level(AbilityType type) { return levels.getOrDefault(type, 0); }
    public boolean unlocked(AbilityType type) { return level(type) > 0; }

    public int upgrade(AbilityType type) {
        int next = Math.min(5, level(type) + 1);
        levels.put(type, next);
        return next;
    }

    public boolean hasTeslaEvolution() {
        return level(AbilityType.TESLA_ORB) >= 5 && level(AbilityType.DRONE) >= 3;
    }

    public boolean hasCryoMissileEvolution() {
        return level(AbilityType.CRYO_NOVA) >= 4 && level(AbilityType.MISSILE_SWARM) >= 4;
    }
}
