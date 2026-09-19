package com.deadlinezero.game.abilities;

import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.progression.Upgrade;

/** Pure presentation policy describing what an ability upgrade choice would unlock next. */
public final class AbilityUpgradeGuidance {
    private AbilityUpgradeGuidance() {}

    public static String key(Player player, Upgrade upgrade) {
        AbilityType type = abilityType(upgrade);
        if (player == null || type == null) return null;

        AbilityLoadout loadout = player.abilities;
        int current = loadout.level(type);
        if (current >= AbilityLoadout.MAX_LEVEL) return null;
        int next = current + 1;

        String synergy = activatingSynergy(loadout, type, next);
        if (synergy != null) return synergy;
        if (current == 0) return "combat.abilityGuidance.unlock";
        if (next == 3) return "combat.abilityGuidance.tier2";
        if (next == 5) return "combat.abilityGuidance.evolution";
        return "combat.abilityGuidance.level";
    }

    private static String activatingSynergy(AbilityLoadout a, AbilityType type, int next) {
        return switch (type) {
            case TESLA_ORB -> {
                if (next >= 5 && a.tier(AbilityType.DRONE) >= 2 && !a.hasTeslaEvolution())
                    yield "combat.synergy.arcReactor";
                if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasSuperconductorSynergy())
                    yield "combat.synergy.superconductor";
                if (next >= 5 && a.evolved(AbilityType.ORBITAL_BLADE) && !a.hasStormBladeSynergy())
                    yield "combat.synergy.stormBlade";
                yield null;
            }
            case MISSILE_SWARM -> {
                if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasCryoMissileEvolution())
                    yield "combat.synergy.cryoBarrage";
                if (next >= 3 && a.tier(AbilityType.DRONE) >= 2 && !a.hasTargetNetworkSynergy())
                    yield "combat.synergy.targetNetwork";
                yield null;
            }
            case CRYO_NOVA -> {
                if (next >= 3 && a.tier(AbilityType.MISSILE_SWARM) >= 2 && !a.hasCryoMissileEvolution())
                    yield "combat.synergy.cryoBarrage";
                if (next >= 3 && a.tier(AbilityType.TESLA_ORB) >= 2 && !a.hasSuperconductorSynergy())
                    yield "combat.synergy.superconductor";
                if (next >= 3 && a.tier(AbilityType.ORBITAL_BLADE) >= 2 && !a.hasPermafrostBladeSynergy())
                    yield "combat.synergy.permafrostBlades";
                yield null;
            }
            case DRONE -> {
                if (next >= 3 && a.evolved(AbilityType.TESLA_ORB) && !a.hasTeslaEvolution())
                    yield "combat.synergy.arcReactor";
                if (next >= 3 && a.tier(AbilityType.MISSILE_SWARM) >= 2 && !a.hasTargetNetworkSynergy())
                    yield "combat.synergy.targetNetwork";
                yield null;
            }
            case ORBITAL_BLADE -> {
                if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasPermafrostBladeSynergy())
                    yield "combat.synergy.permafrostBlades";
                if (next >= 5 && a.evolved(AbilityType.TESLA_ORB) && !a.hasStormBladeSynergy())
                    yield "combat.synergy.stormBlade";
                yield null;
            }
        };
    }

    public static AbilityType abilityType(Upgrade upgrade) {
        if (upgrade == null) return null;
        return switch (upgrade) {
            case TESLA_ORB -> AbilityType.TESLA_ORB;
            case MISSILE_SWARM -> AbilityType.MISSILE_SWARM;
            case CRYO_NOVA -> AbilityType.CRYO_NOVA;
            case DRONE -> AbilityType.DRONE;
            case ORBITAL -> AbilityType.ORBITAL_BLADE;
            default -> null;
        };
    }
}
