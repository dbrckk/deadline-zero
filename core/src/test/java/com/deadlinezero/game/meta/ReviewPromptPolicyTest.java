package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ReviewPromptPolicyTest {
    @Test void onlyMeaningfulFirstClearIsEligible() {
        assertFalse(ReviewPromptPolicy.eligible(false, 5, false));
        assertFalse(ReviewPromptPolicy.eligible(true, 1, false));
        assertFalse(ReviewPromptPolicy.eligible(true, 2, false));
        assertTrue(ReviewPromptPolicy.eligible(true, 3, false));
        assertTrue(ReviewPromptPolicy.eligible(true, 20, false));
        assertFalse(ReviewPromptPolicy.eligible(true, 20, true));
    }
}
