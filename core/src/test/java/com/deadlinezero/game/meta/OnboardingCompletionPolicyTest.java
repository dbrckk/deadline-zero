package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class OnboardingCompletionPolicyTest {
    @Test void requiresEveryTutorialMilestone() {
        assertFalse(OnboardingCompletionPolicy.completed(false, false, false, false));
        assertFalse(OnboardingCompletionPolicy.completed(true, false, true, true));
        assertFalse(OnboardingCompletionPolicy.completed(true, true, false, true));
        assertFalse(OnboardingCompletionPolicy.completed(true, true, true, false));
        assertTrue(OnboardingCompletionPolicy.completed(true, true, true, true));
    }
}
