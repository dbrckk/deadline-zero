package com.deadlinezero.game.progression;

import com.deadlinezero.game.abilities.AbilityType;
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
}
