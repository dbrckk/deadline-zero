package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;

final class UpgradePoolTest {
    @Test void productionPoolMeetsFiftyUpgradeTargetWithUniquePresentation() {
        Upgrade[] upgrades = Upgrade.values();
        assertTrue(upgrades.length >= 50, "P5 requires 50+ standard upgrades");
        assertEquals(60, upgrades.length);

        Set<String> titles = new HashSet<>();
        int common = 0, rare = 0, epic = 0;
        for (Upgrade upgrade : upgrades) {
            assertTrue(titles.add(upgrade.title), "duplicate upgrade title: " + upgrade.title);
            assertFalse(upgrade.description.isBlank(), upgrade.name());
            switch (upgrade.rarity) {
                case COMMON -> common++;
                case RARE -> rare++;
                case EPIC -> epic++;
                case LEGENDARY -> { }
            }
        }
        assertTrue(common >= 10, "common pool too small");
        assertTrue(rare >= 20, "rare pool too small");
        assertTrue(epic >= 8, "epic pool too small");
    }

    @Test void everyUpgradeKeepsRuntimeStatsFiniteAndInsideSafetyCaps() {
        RunLoadoutContext.end();
        for (Upgrade upgrade : Upgrade.values()) {
            Player player = new Player(0f, 0f);
            upgrade.apply(player);
            assertRuntimeSafe(player, upgrade.name());
        }
    }

    @Test void repeatedStackingSaturatesInsteadOfEscapingMobileSafetyCaps() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);
        Upgrade[] stress = {
            Upgrade.RAPID_FIRE, Upgrade.DAMAGE, Upgrade.SPEED, Upgrade.VITALITY,
            Upgrade.MULTISHOT, Upgrade.CRIT, Upgrade.CRIT_POWER, Upgrade.BALLISTICS,
            Upgrade.PENETRATION, Upgrade.KNOCKBACK, Upgrade.DASH_CORE,
            Upgrade.CROSSFIRE, Upgrade.HYPER_VELOCITY, Upgrade.BREACH_MATRIX
        };
        for (int i = 0; i < 120; i++) for (Upgrade upgrade : stress) upgrade.apply(player);

        assertEquals(Upgrade.MIN_FIRE_INTERVAL, player.weapon.fireInterval, .0001f);
        assertEquals(Upgrade.MAX_DAMAGE, player.weapon.damage, .0001f);
        assertEquals(Upgrade.MAX_MOVE_SPEED, player.moveSpeed, .0001f);
        assertEquals(Upgrade.MAX_HP, player.maxHp, .0001f);
        assertEquals(Upgrade.MAX_PROJECTILES, player.weapon.projectileCount);
        assertEquals(Upgrade.MAX_CRIT_CHANCE, player.weapon.critChance, .0001f);
        assertEquals(Upgrade.MAX_CRIT_MULTIPLIER, player.weapon.critMultiplier, .0001f);
        assertEquals(Upgrade.MAX_PROJECTILE_SPEED, player.weapon.projectileSpeed, .0001f);
        assertEquals(Upgrade.MAX_PENETRATION, player.weapon.penetration);
        assertEquals(Upgrade.MAX_KNOCKBACK, player.weapon.knockback, .0001f);
        assertEquals(Upgrade.MIN_DASH_COOLDOWN, player.dashCooldown, .0001f);
    }

    @Test void selectorAlwaysReturnsThreeDistinctEligibleChoices() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);
        Upgrade[] choices = new Upgrade[3];
        for (int sample = 0; sample < 100; sample++) {
            UpgradeSelector.fillChoices(player, choices);
            Set<Upgrade> unique = new HashSet<>();
            for (Upgrade choice : choices) {
                assertNotNull(choice);
                assertTrue(unique.add(choice), "selector returned duplicate choice");
                assertTrue(UpgradeSelector.isAvailable(player, choice), choice.name());
            }
        }
    }

    @Test void everyUpgradeEventuallyStopsBeingUsefulWhenRepeatedAlone() {
        RunLoadoutContext.end();
        for (Upgrade upgrade : Upgrade.values()) {
            Player player = new Player(0f, 0f);
            int applications = 0;
            while (UpgradeSelector.isAvailable(player, upgrade) && applications < 240) {
                upgrade.apply(player);
                applications++;
            }
            assertTrue(applications < 240, "upgrade never saturated: " + upgrade.name());
            assertFalse(UpgradeSelector.isAvailable(player, upgrade), "upgrade still offered after saturation: " + upgrade.name());
            assertRuntimeSafe(player, upgrade.name());
        }
    }

    @Test void droneDoctrinesUnlockAtTierTwoAndBecomeMutuallyExclusive() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_HUNTER_DOCTRINE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_SENTINEL_DOCTRINE));

        for (int i = 0; i < 3; i++) Upgrade.DRONE.apply(player);
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.DRONE_HUNTER_DOCTRINE));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.DRONE_SENTINEL_DOCTRINE));

        Upgrade.DRONE_HUNTER_DOCTRINE.apply(player);
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_HUNTER_DOCTRINE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_SENTINEL_DOCTRINE));
    }

    @Test void protocolEvolutionsRequireTheirBaseAndThenSaturate() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);

        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_OVERCHARGE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CASCADE));

        Upgrade.RHYTHM_DRIVER.apply(player);
        Upgrade.KILLCHAIN_CAPACITOR.apply(player);
        Upgrade.REACTION_CORE.apply(player);

        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_OVERCHARGE));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CASCADE));

        Upgrade.RHYTHM_ACCELERATOR.apply(player);
        Upgrade.KILLCHAIN_OVERCHARGE.apply(player);
        Upgrade.REACTION_CASCADE.apply(player);

        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_OVERCHARGE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CASCADE));
    }

    @Test void eventProtocolsAreOneTimeRunChoices() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_DRIVER));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_CAPACITOR));
        assertTrue(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CORE));

        Upgrade.RHYTHM_DRIVER.apply(player);
        Upgrade.KILLCHAIN_CAPACITOR.apply(player);
        Upgrade.REACTION_CORE.apply(player);

        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_DRIVER));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_CAPACITOR));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CORE));
    }

    @Test void hardCappedChoicesDisappearFromEligibility() {
        RunLoadoutContext.end();
        Player player = new Player(0f, 0f);
        player.weapon.fireInterval = Upgrade.MIN_FIRE_INTERVAL;
        player.weapon.damage = Upgrade.MAX_DAMAGE;
        player.moveSpeed = Upgrade.MAX_MOVE_SPEED;
        player.weapon.projectileCount = Upgrade.MAX_PROJECTILES;
        player.weapon.critChance = Upgrade.MAX_CRIT_CHANCE;
        player.weapon.critMultiplier = Upgrade.MAX_CRIT_MULTIPLIER;
        player.weapon.projectileSpeed = Upgrade.MAX_PROJECTILE_SPEED;
        player.weapon.penetration = Upgrade.MAX_PENETRATION;
        player.weapon.knockback = Upgrade.MAX_KNOCKBACK;
        player.dashCooldown = Upgrade.MIN_DASH_COOLDOWN;

        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RAPID_FIRE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DAMAGE));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.SPEED));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.MULTISHOT));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.CRIT));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.CRIT_POWER));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.BALLISTICS));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.PENETRATION));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KNOCKBACK));
        assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DASH_CORE));
    }

    private static void assertRuntimeSafe(Player player, String context) {
        assertTrue(Float.isFinite(player.hp), context);
        assertTrue(Float.isFinite(player.maxHp), context);
        assertTrue(Float.isFinite(player.moveSpeed), context);
        assertTrue(Float.isFinite(player.dashCooldown), context);
        assertTrue(Float.isFinite(player.weapon.damage), context);
        assertTrue(Float.isFinite(player.weapon.fireInterval), context);
        assertTrue(Float.isFinite(player.weapon.projectileSpeed), context);
        assertTrue(Float.isFinite(player.weapon.spreadDegrees), context);
        assertTrue(Float.isFinite(player.weapon.critChance), context);
        assertTrue(Float.isFinite(player.weapon.critMultiplier), context);
        assertTrue(Float.isFinite(player.weapon.knockback), context);

        assertTrue(player.maxHp >= Upgrade.MIN_HP && player.maxHp <= Upgrade.MAX_HP, context);
        assertTrue(player.hp >= 0f && player.hp <= player.maxHp, context);
        assertTrue(player.moveSpeed >= 1f && player.moveSpeed <= Upgrade.MAX_MOVE_SPEED, context);
        assertTrue(player.dashCooldown >= Upgrade.MIN_DASH_COOLDOWN, context);
        assertTrue(player.weapon.damage > 0f && player.weapon.damage <= Upgrade.MAX_DAMAGE, context);
        assertTrue(player.weapon.fireInterval >= Upgrade.MIN_FIRE_INTERVAL, context);
        assertTrue(player.weapon.projectileSpeed >= 1f && player.weapon.projectileSpeed <= Upgrade.MAX_PROJECTILE_SPEED, context);
        assertTrue(player.weapon.projectileCount >= 1 && player.weapon.projectileCount <= Upgrade.MAX_PROJECTILES, context);
        assertTrue(player.weapon.spreadDegrees >= 0f && player.weapon.spreadDegrees <= Upgrade.MAX_SPREAD_DEGREES, context);
        assertTrue(player.weapon.critChance >= 0f && player.weapon.critChance <= Upgrade.MAX_CRIT_CHANCE, context);
        assertTrue(player.weapon.critMultiplier >= 1f && player.weapon.critMultiplier <= Upgrade.MAX_CRIT_MULTIPLIER, context);
        assertTrue(player.weapon.penetration >= 0 && player.weapon.penetration <= Upgrade.MAX_PENETRATION, context);
        assertTrue(player.weapon.knockback >= 0f && player.weapon.knockback <= Upgrade.MAX_KNOCKBACK, context);
    }
}
