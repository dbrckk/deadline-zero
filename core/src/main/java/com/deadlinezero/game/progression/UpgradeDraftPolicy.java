package com.deadlinezero.game.progression;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Player;

/**
 * Build-affinity policy for level-up drafts.
 *
 * Once a run has established an elemental or ability identity, one draft slot stays relevant to
 * that identity while the remaining slots preserve broad roguelite discovery.
 */
final class UpgradeDraftPolicy {
    private UpgradeDraftPolicy() {}

    static boolean hasEstablishedBuild(Player player) {
        if (player == null) return false;
        if (player.weapon.element != DamageElement.KINETIC) return true;
        for (AbilityType type : AbilityType.values()) {
            if (player.abilities.level(type) >= 2) return true;
        }
        return false;
    }

    static boolean isFocusedCandidate(Player player, Upgrade upgrade) {
        return affinityMultiplier(player, upgrade) >= 1.50f;
    }

    static float affinityMultiplier(Player player, Upgrade upgrade) {
        if (player == null || upgrade == null) return 1f;

        float multiplier = elementalAffinity(player, upgrade);
        multiplier *= abilityAffinity(player, upgrade);
        return Math.max(.45f, Math.min(3.25f, multiplier));
    }

    private static float elementalAffinity(Player player, Upgrade upgrade) {
        DamageElement current = player.weapon.element;
        DamageElement family = elementFamily(upgrade);

        if (family == null) {
            if (upgrade == Upgrade.ELEMENTAL_HARMONIZER && current != DamageElement.KINETIC) return 1.45f;
            return 1f;
        }
        if (current == DamageElement.KINETIC) return 1f;
        return current == family ? 2.25f : .58f;
    }

    private static DamageElement elementFamily(Upgrade upgrade) {
        return switch (upgrade) {
            case INCENDIARY, FIRE_CONTROL, THERMAL_LANCE -> DamageElement.FIRE;
            case CRYO, FROST_CONTROL, CRYO_HAMMER -> DamageElement.FROST;
            case SHOCK, SHOCK_CONTROL, ARC_LANCER -> DamageElement.SHOCK;
            default -> null;
        };
    }

    private static float abilityAffinity(Player player, Upgrade upgrade) {
        AbilityType offered = abilityType(upgrade);
        if (offered == null) return 1f;

        int ownLevel = player.abilities.level(offered);
        if (ownLevel > 0) return ownLevel >= 3 ? 2.75f : 2.35f;

        return switch (offered) {
            case TESLA_ORB -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.DRONE, AbilityType.ORBITAL_BLADE);
            case MISSILE_SWARM -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.DRONE);
            case CRYO_NOVA -> partnerBoost(player, AbilityType.TESLA_ORB, AbilityType.MISSILE_SWARM, AbilityType.ORBITAL_BLADE);
            case DRONE -> partnerBoost(player, AbilityType.TESLA_ORB, AbilityType.MISSILE_SWARM);
            case ORBITAL_BLADE -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.TESLA_ORB);
        };
    }

    private static float partnerBoost(Player player, AbilityType... partners) {
        int strongest = 0;
        for (AbilityType partner : partners) strongest = Math.max(strongest, player.abilities.level(partner));
        if (strongest >= 3) return 2.05f;
        if (strongest >= 2) return 1.75f;
        return 1f;
    }

    private static AbilityType abilityType(Upgrade upgrade) {
        return switch (upgrade) {
            case TESLA_ORB -> AbilityType.TESLA_ORB;
            case MISSILE_SWARM -> AbilityType.MISSILE_SWARM;
            case CRYO_NOVA -> AbilityType.CRYO_NOVA;
            case DRONE, DRONE_HUNTER_DOCTRINE, DRONE_SENTINEL_DOCTRINE -> AbilityType.DRONE;
            case ORBITAL -> AbilityType.ORBITAL_BLADE;
            default -> null;
        };
    }
}
