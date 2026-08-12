package com.deadlinezero.game.progression;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.entities.Player;

/** Weighted, allocation-light upgrade selection with max-level filtering. */
public final class UpgradeSelector {
    private static final Upgrade[] ALL = Upgrade.values();
    private static final Upgrade[] ELIGIBLE = new Upgrade[ALL.length];
    private static final float[] WEIGHTS = new float[ALL.length];

    private UpgradeSelector() {}

    public static void fillChoices(Player player, Upgrade[] out) {
        for (int slot = 0; slot < out.length; slot++) {
            int count = collectEligible(player, out, slot);
            if (count == 0) {
                out[slot] = Upgrade.DAMAGE;
                continue;
            }
            float total = 0f;
            for (int i = 0; i < count; i++) total += WEIGHTS[i];
            float roll = MathUtils.random(total);
            int selected = count - 1;
            for (int i = 0; i < count; i++) {
                roll -= WEIGHTS[i];
                if (roll <= 0f) { selected = i; break; }
            }
            out[slot] = ELIGIBLE[selected];
        }
    }

    private static int collectEligible(Player player, Upgrade[] chosen, int chosenCount) {
        int count = 0;
        for (Upgrade upgrade : ALL) {
            if (!isAvailable(player, upgrade)) continue;
            boolean duplicate = false;
            for (int i = 0; i < chosenCount; i++) {
                if (chosen[i] == upgrade) { duplicate = true; break; }
            }
            if (duplicate) continue;
            ELIGIBLE[count] = upgrade;
            WEIGHTS[count] = rarityWeight(upgrade.rarity);
            count++;
        }
        return count;
    }

    private static boolean isAvailable(Player p, Upgrade u) {
        return switch (u) {
            case TESLA_ORB -> p.abilities.level(AbilityType.TESLA_ORB) < 5;
            case MISSILE_SWARM -> p.abilities.level(AbilityType.MISSILE_SWARM) < 5;
            case CRYO_NOVA -> p.abilities.level(AbilityType.CRYO_NOVA) < 5;
            case DRONE -> p.abilities.level(AbilityType.DRONE) < 5;
            case ORBITAL -> p.abilities.level(AbilityType.ORBITAL_BLADE) < 5;
            case MULTISHOT -> p.weapon.projectileCount < 7;
            case PENETRATION -> p.weapon.penetration < 8;
            case CRIT -> p.weapon.critChance < .60f;
            case DASH_CORE -> p.dashCooldown > 1.26f;
            default -> true;
        };
    }

    private static float rarityWeight(UpgradeRarity rarity) {
        return switch (rarity) {
            case COMMON -> 60f;
            case RARE -> 27f;
            case EPIC -> 12f;
            case LEGENDARY -> 1f;
        };
    }
}
