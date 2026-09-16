package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

final class CombatOnboardingHintPresentationTest {
    @Test
    void mobileHintsOnlyUseKeysThatExistInTheSharedCatalog() {
        assertEquals("hud.onboardingMove", CombatOnboardingHintPresentation.keyFor(false, false, false, false));
        assertEquals("hud.onboardingDash", CombatOnboardingHintPresentation.keyFor(true, false, false, false));
        assertEquals("hud.onboardingUpgrade", CombatOnboardingHintPresentation.keyFor(true, true, false, false));
        assertEquals("hud.onboardingBoss", CombatOnboardingHintPresentation.keyFor(true, true, true, false));
        assertNull(CombatOnboardingHintPresentation.keyFor(true, true, true, true));
    }
}
