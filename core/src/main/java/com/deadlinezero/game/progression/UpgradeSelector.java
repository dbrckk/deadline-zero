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

    static boolean isAvailable(Player p, Upgrade u) {
        if (p == null || u == null) return false;
        return switch (u) {
            case TESLA_ORB -> p.abilities.level(AbilityType.TESLA_ORB) < 5;
            case MISSILE_SWARM -> p.abilities.level(AbilityType.MISSILE_SWARM) < 5;
            case CRYO_NOVA -> p.abilities.level(AbilityType.CRYO_NOVA) < 5;
            case DRONE -> p.abilities.level(AbilityType.DRONE) < 5;
            case ORBITAL -> p.abilities.level(AbilityType.ORBITAL_BLADE) < 5;

            case RAPID_FIRE -> p.weapon.fireInterval > Upgrade.MIN_FIRE_INTERVAL + .001f;
            case DAMAGE, FOCUSED_PAYLOAD -> p.weapon.damage < Upgrade.MAX_DAMAGE - .01f;
            case SPEED, AFTERBURNER -> p.moveSpeed < Upgrade.MAX_MOVE_SPEED - .01f;
            case VITALITY, BULWARK_FRAME, COMBAT_STIMS, REACTIVE_PLATING, FIELD_REPAIR ->
                p.maxHp < Upgrade.MAX_HP - .01f || p.hp < p.maxHp - .01f;
            case MULTISHOT, CROSSFIRE, BARRAGE_MATRIX -> p.weapon.projectileCount < Upgrade.MAX_PROJECTILES;
            case CRIT -> p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f;
            case CRIT_POWER -> p.weapon.critMultiplier < Upgrade.MAX_CRIT_MULTIPLIER - .01f;
            case BALLISTICS, LIGHTWEIGHT_BOLT, HYPER_VELOCITY ->
                p.weapon.projectileSpeed < Upgrade.MAX_PROJECTILE_SPEED - .01f;
            case PENETRATION -> p.weapon.penetration < Upgrade.MAX_PENETRATION;
            case KNOCKBACK, IMPACT_CORE -> p.weapon.knockback < Upgrade.MAX_KNOCKBACK - .01f;
            case DASH_CORE, PHASE_CAPACITOR -> p.dashCooldown > Upgrade.MIN_DASH_COOLDOWN + .01f;
            case TIGHT_CHOKE -> p.weapon.spreadDegrees > .05f;

            case HEAVY_BARREL, ELEMENTAL_HARMONIZER, MOMENTUM_CORE, VETERAN_CORE ->
                p.weapon.damage < Upgrade.MAX_DAMAGE - .01f
                    || p.weapon.projectileSpeed < Upgrade.MAX_PROJECTILE_SPEED - .01f
                    || p.weapon.knockback < Upgrade.MAX_KNOCKBACK - .01f;
            case SUPPRESSIVE_CYCLE ->
                p.weapon.fireInterval > Upgrade.MIN_FIRE_INTERVAL + .001f
                    || p.weapon.knockback < Upgrade.MAX_KNOCKBACK - .01f;
            case GLASS_CANNON, BERSERKER_CALIBER ->
                p.weapon.damage < Upgrade.MAX_DAMAGE - .01f && p.maxHp > Upgrade.MIN_HP + .01f;
            case EXECUTIONER, PRECISION_MATRIX ->
                p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f
                    || p.weapon.critMultiplier < Upgrade.MAX_CRIT_MULTIPLIER - .01f;
            case SIEGE_ROUNDS, BREACH_MATRIX ->
                p.weapon.penetration < Upgrade.MAX_PENETRATION
                    || p.weapon.damage < Upgrade.MAX_DAMAGE - .01f;
            case ADAPTIVE_TRIGGER ->
                p.weapon.fireInterval > Upgrade.MIN_FIRE_INTERVAL + .001f
                    || p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f;
            case SCOUT_FRAME ->
                p.moveSpeed < Upgrade.MAX_MOVE_SPEED - .01f
                    || p.dashCooldown > Upgrade.MIN_DASH_COOLDOWN + .01f;
            case LAST_STAND ->
                p.weapon.damage < Upgrade.MAX_DAMAGE - .01f
                    || p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f;

            case INCENDIARY, FIRE_CONTROL ->
                p.weapon.element != com.deadlinezero.game.combat.DamageElement.FIRE
                    || p.weapon.damage < Upgrade.MAX_DAMAGE - .01f
                    || p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f;
            case CRYO, FROST_CONTROL, CRYO_HAMMER ->
                p.weapon.element != com.deadlinezero.game.combat.DamageElement.FROST
                    || p.weapon.knockback < Upgrade.MAX_KNOCKBACK - .01f;
            case SHOCK, SHOCK_CONTROL ->
                p.weapon.element != com.deadlinezero.game.combat.DamageElement.SHOCK
                    || p.weapon.fireInterval > Upgrade.MIN_FIRE_INTERVAL + .001f
                    || p.weapon.penetration < Upgrade.MAX_PENETRATION;
            case THERMAL_LANCE ->
                p.weapon.element != com.deadlinezero.game.combat.DamageElement.FIRE
                    || p.weapon.projectileSpeed < Upgrade.MAX_PROJECTILE_SPEED - .01f
                    || p.weapon.penetration < Upgrade.MAX_PENETRATION;
            case ARC_LANCER ->
                p.weapon.element != com.deadlinezero.game.combat.DamageElement.SHOCK
                    || p.weapon.projectileSpeed < Upgrade.MAX_PROJECTILE_SPEED - .01f
                    || p.weapon.critChance < Upgrade.MAX_CRIT_CHANCE - .001f;
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
