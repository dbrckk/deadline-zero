package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.entities.Player;
import org.junit.jupiter.api.Test;

public final class LegendaryStateTest {
    @Test public void grantsAreOneShot() {
        LegendaryState state = new LegendaryState();
        assertFalse(state.hasAny());
        assertTrue(state.grantOverdrive());
        assertFalse(state.grantOverdrive());
        assertTrue(state.hasOverdrive());
        assertTrue(state.hasAny());
    }

    @Test public void flagsRemainIndependent() {
        LegendaryState state = new LegendaryState();
        assertTrue(state.grantSingularity());
        assertTrue(state.grantApex());
        assertFalse(state.hasOverdrive());
        assertTrue(state.hasSingularity());
        assertTrue(state.hasApex());
    }

    @Test public void overdriveTransformsWeaponAndMobilityOnce() {
        Player player = new Player(0f, 0f);
        float damage = player.weapon.damage;
        float interval = player.weapon.fireInterval;
        float speed = player.moveSpeed;
        assertTrue(LegendaryEffects.applyOverdrive(player));
        assertFalse(LegendaryEffects.applyOverdrive(player));
        assertTrue(player.weapon.damage > damage);
        assertTrue(player.weapon.fireInterval < interval);
        assertTrue(player.moveSpeed > speed);
    }

    @Test public void singularityTransformsBallisticsOnce() {
        Player player = new Player(0f, 0f);
        int projectiles = player.weapon.projectileCount;
        int penetration = player.weapon.penetration;
        assertTrue(LegendaryEffects.applySingularity(player));
        assertFalse(LegendaryEffects.applySingularity(player));
        assertEquals(projectiles + 2, player.weapon.projectileCount);
        assertEquals(penetration + 3, player.weapon.penetration);
    }

    @Test public void apexEstablishesEveryAbilityAtTierTwo() {
        Player player = new Player(0f, 0f);
        assertTrue(LegendaryEffects.applyApex(player));
        assertFalse(LegendaryEffects.applyApex(player));
        for (AbilityType type : AbilityType.values()) assertTrue(player.abilities.tier(type) >= 2);
    }
}
