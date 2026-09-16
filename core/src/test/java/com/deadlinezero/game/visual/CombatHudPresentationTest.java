package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatHudPresentationTest {
    @Test
    void onboardingUsesContextualToastInsteadOfPermanentCenterPanel() {
        assertEquals(CombatHudRenderer.HintMode.TOAST, CombatHudRenderer.hintModeFor(false));
        assertEquals(CombatHudRenderer.HintMode.NONE, CombatHudRenderer.hintModeFor(true));
    }

    @Test
    void mobileControlsRecedeAtIdleAndStrengthenOnInteraction() {
        assertTrue(CombatHudRenderer.controlIdleAlpha() <= .18f);
        assertTrue(CombatHudRenderer.controlActiveAlpha() >= .30f);
        assertTrue(CombatHudRenderer.controlActiveAlpha() > CombatHudRenderer.controlIdleAlpha());
    }
}
