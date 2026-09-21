package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class OnboardingHintPolicyTest {
    @Test void stepsAdvanceInGameplayOrder() {
        assertEquals(0, OnboardingHintPolicy.step(false, false, false, false));
        assertEquals(1, OnboardingHintPolicy.step(true, false, false, false));
        assertEquals(2, OnboardingHintPolicy.step(true, true, false, false));
        assertEquals(3, OnboardingHintPolicy.step(true, true, true, false));
        assertEquals(OnboardingHintPolicy.NONE,
            OnboardingHintPolicy.step(true, true, true, true));
    }

    @Test void eachHintAutoHidesWithoutCompletingOnboarding() {
        assertTrue(OnboardingHintPolicy.visible(false, 1, 0f));
        assertTrue(OnboardingHintPolicy.visible(false, 1, OnboardingHintPolicy.MAX_VISIBLE_SECONDS - .01f));
        assertFalse(OnboardingHintPolicy.visible(false, 1, OnboardingHintPolicy.MAX_VISIBLE_SECONDS));
        assertFalse(OnboardingHintPolicy.visible(false, OnboardingHintPolicy.NONE, 0f));
        assertFalse(OnboardingHintPolicy.visible(true, 1, 0f));
    }
}
