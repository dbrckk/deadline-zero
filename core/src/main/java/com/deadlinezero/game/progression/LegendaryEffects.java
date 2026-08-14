package com.deadlinezero.game.progression;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;
import com.deadlinezero.game.entities.Player;

/** Applies one-shot legendary transformations while LegendaryState owns eligibility. */
public final class LegendaryEffects {
    private LegendaryEffects() { }

    public static boolean applyOverdrive(Player player) {
        if (!player.legendary.grantOverdrive()) return false;
        player.weapon.damage *= 1.38f;
        player.weapon.fireInterval = Math.max(.045f, player.weapon.fireInterval * .76f);
        player.weapon.projectileSpeed *= 1.12f;
        player.moveSpeed *= 1.18f;
        player.dashCooldown = Math.max(1.1f, player.dashCooldown * .72f);
        return true;
    }

    public static boolean applySingularity(Player player) {
        if (!player.legendary.grantSingularity()) return false;
        player.weapon.projectileCount += 2;
        player.weapon.penetration += 3;
        player.weapon.projectileSpeed *= 1.24f;
        player.weapon.damage *= .88f;
        if (player.weapon.projectileCount > 1) player.weapon.spreadDegrees += 3.5f;
        return true;
    }

    public static boolean applyApex(Player player) {
        if (!player.legendary.grantApex()) return false;
        for (AbilityType type : AbilityType.values()) {
            while (player.abilities.level(type) < 3) player.abilities.upgrade(type);
        }
        player.weapon.damage *= .90f;
        player.weapon.fireInterval *= 1.06f;
        return true;
    }

    public static boolean applyIonCascade(Player player) {
        if (!player.legendary.grantIonCascade()) return false;
        WeaponSignatureRuntime.enableIonCascade();
        player.weapon.critChance = Math.min(.75f, player.weapon.critChance + .04f);
        player.weapon.projectileSpeed *= 1.08f;
        return true;
    }

    public static boolean applyCinderFurnace(Player player) {
        if (!player.legendary.grantCinderFurnace()) return false;
        WeaponSignatureRuntime.enableCinderFurnace();
        player.weapon.fireInterval = Math.max(.30f, player.weapon.fireInterval * .94f);
        player.weapon.knockback *= 1.08f;
        return true;
    }

    public static boolean applyRailPhaseLance(Player player) {
        if (!player.legendary.grantRailPhaseLance()) return false;
        player.weapon.damage *= 1.22f;
        player.weapon.projectileSpeed *= 1.12f;
        player.weapon.penetration += 2;
        player.weapon.critChance = Math.min(.75f, player.weapon.critChance + .08f);
        return true;
    }

    public static boolean applyCryoPrism(Player player) {
        if (!player.legendary.grantCryoPrism()) return false;
        player.weapon.projectileCount += 2;
        player.weapon.damage *= .48f;
        player.weapon.spreadDegrees += 4.5f;
        player.weapon.penetration += 1;
        return true;
    }

    public static boolean applyArcOverload(Player player) {
        if (!player.legendary.grantArcOverload()) return false;
        player.weapon.projectileCount += 1;
        player.weapon.damage *= .68f;
        player.weapon.spreadDegrees += 2.5f;
        player.weapon.penetration += 1;
        player.weapon.critChance = Math.min(.75f, player.weapon.critChance + .04f);
        return true;
    }
}
