package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatFeedbackProfileTest {
    @Test
    void lowerQualityNeverSpendsMoreThanHighQuality() {
        CombatFeedbackProfile.Profile low = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.HIT, GraphicsQuality.LOW, false, false);
        CombatFeedbackProfile.Profile high = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.HIT, GraphicsQuality.HIGH, false, false);

        assertTrue(low.particleBudget() <= high.particleBudget());
        assertTrue(low.geometryBudget() <= high.geometryBudget());
        assertTrue(low.glowAlpha() <= high.glowAlpha());
    }

    @Test
    void comfortSettingsClampMotionAndFlash() {
        CombatFeedbackProfile.Profile reducedDash = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.DASH, GraphicsQuality.HIGH, true, false);
        CombatFeedbackProfile.Profile minimizedCrit = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.CRIT, GraphicsQuality.HIGH, false, true);

        assertEquals(0f, reducedDash.afterimageStrength(), .0001f);
        assertTrue(minimizedCrit.flashAlpha() <= .08f);
    }

    @Test
    void persistentResidueAndShakeStayBounded() {
        CombatFeedbackProfile.Profile kill = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.KILL, GraphicsQuality.HIGH, false, false);
        CombatFeedbackProfile.Profile boss = CombatFeedbackProfile.forEvent(
            CombatFeedbackProfile.Event.BOSS_RELEASE, GraphicsQuality.HIGH, false, false);

        assertTrue(kill.decalLifetime() <= 8f);
        assertTrue(kill.decalLifetime() >= 0f);
        assertTrue(boss.shakeScale() <= 1f);
        assertTrue(boss.shakeScale() >= 0f);
    }
}
