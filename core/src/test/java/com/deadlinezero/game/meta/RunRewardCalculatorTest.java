package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class RunRewardCalculatorTest {
    @Test void ordinaryRewardsRemainStable() {
        var rewards = RunRewardCalculator.calculate(60, 120f, true, 1);
        assertEquals(345L, rewards.credits());
        assertEquals(195L, rewards.accountXp());
        assertEquals(1, rewards.gems());
    }

    @Test void largeRunsKeepLongPrecisionInsteadOfNarrowingToInt() {
        var rewards = RunRewardCalculator.calculate(Integer.MAX_VALUE, 0f, false, 100);
        assertTrue(rewards.credits() > Integer.MAX_VALUE);
        assertTrue(rewards.accountXp() > Integer.MAX_VALUE);
    }

    @Test void scalingSaturatesSafelyAtLongLimit() {
        assertEquals(Long.MAX_VALUE, ProfileCounterMath.scaleNonNegative(Long.MAX_VALUE, 2f));
        assertEquals(0L, ProfileCounterMath.scaleNonNegative(100L, Float.NaN));
        assertEquals(0L, ProfileCounterMath.scaleNonNegative(-100L, 2f));
    }
}
