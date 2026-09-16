package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.ai.BossIdentity;
import org.junit.jupiter.api.Test;

final class BossPresenceProfileTest {
    @Test
    void laterBossPhasesIncreasePresentationIntensityWithoutChangingGameplay() {
        BossPresenceProfile.PhaseProfile phase1 = BossPresenceProfile.forPhase(BossIdentity.ALPHA, 1);
        BossPresenceProfile.PhaseProfile phase3 = BossPresenceProfile.forPhase(BossIdentity.ALPHA, 3);
        BossPresenceProfile.PhaseProfile frost = BossPresenceProfile.forPhase(BossIdentity.FROST_COLOSSUS, 2);

        assertTrue(phase3.intensity() > phase1.intensity());
        assertTrue(frost.telegraphSegments() >= 3);
        assertTrue(phase3.ringScale() >= phase1.ringScale());
        assertTrue(phase3.markerCount() >= phase1.markerCount());
    }

    @Test
    void lowHealthTreatmentStaysRestrainedAndRespectsReducedMotion() {
        BossPresenceProfile.LowHpProfile animated = BossPresenceProfile.lowHp(.20f, true);
        BossPresenceProfile.LowHpProfile reduced = BossPresenceProfile.lowHp(.20f, false);
        BossPresenceProfile.LowHpProfile healthy = BossPresenceProfile.lowHp(.80f, true);

        assertTrue(animated.flashAlpha() <= .07f);
        assertEquals(0f, reduced.motionPulse(), .0001f);
        assertEquals(0f, healthy.edgeAlpha(), .0001f);
        assertTrue(animated.edgeAlpha() > healthy.edgeAlpha());
    }
}
