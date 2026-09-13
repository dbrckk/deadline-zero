package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Player;

final class UpgradePoolScaleTest {
    @Test void productionPoolContainsAtLeastFiftyStandardUpgrades() {
        assertTrue(Upgrade.values().length >= 50, "standard upgrade pool regressed below P5 target");
        assertEquals(50, Upgrade.values().length);
    }

    @Test void everyUpgradeKeepsCoreCombatStatsFiniteAndPositive() {
        for (Upgrade upgrade : Upgrade.values()) {
            Player player = new Player(0f, 0f);
            upgrade.apply(player);

            assertTrue(Float.isFinite(player.hp) && player.hp > 0f, upgrade.name() + " hp");
            assertTrue(Float.isFinite(player.maxHp) && player.maxHp >= player.hp, upgrade.name() + " maxHp");
            assertTrue(Float.isFinite(player.moveSpeed) && player.moveSpeed > 0f, upgrade.name() + " moveSpeed");
            assertTrue(Float.isFinite(player.dashCooldown) && player.dashCooldown >= 1.25f, upgrade.name() + " dashCooldown");
            assertTrue(Float.isFinite(player.weapon.damage) && player.weapon.damage > 0f, upgrade.name() + " damage");
            assertTrue(Float.isFinite(player.weapon.fireInterval) && player.weapon.fireInterval > .03f, upgrade.name() + " fireInterval");
            assertTrue(Float.isFinite(player.weapon.projectileSpeed) && player.weapon.projectileSpeed > 0f, upgrade.name() + " projectileSpeed");
            assertTrue(player.weapon.projectileCount >= 1 && player.weapon.projectileCount <= 7, upgrade.name() + " projectileCount");
            assertTrue(player.weapon.penetration >= 0 && player.weapon.penetration <= 8, upgrade.name() + " penetration");
            assertTrue(player.weapon.critChance >= 0f && player.weapon.critChance <= .60f, upgrade.name() + " critChance");
            assertTrue(player.weapon.critMultiplier > 0f && player.weapon.critMultiplier <= 4.0f, upgrade.name() + " critMultiplier");
            assertTrue(Float.isFinite(player.weapon.knockback) && player.weapon.knockback >= 0f, upgrade.name() + " knockback");
        }
    }

    @Test void repeatedBoundedUpgradesRespectHardCaps() {
        Player player = new Player(0f, 0f);
        for (int i = 0; i < 20; i++) {
            Upgrade.MULTISHOT.apply(player);
            Upgrade.PENETRATION.apply(player);
            Upgrade.CRIT.apply(player);
            Upgrade.CRIT_DAMAGE.apply(player);
            Upgrade.DASH_CORE.apply(player);
        }
        assertEquals(7, player.weapon.projectileCount);
        assertEquals(8, player.weapon.penetration);
        assertEquals(.60f, player.weapon.critChance, .0001f);
        assertEquals(4.0f, player.weapon.critMultiplier, .0001f);
        assertEquals(1.25f, player.dashCooldown, .0001f);
    }
}
