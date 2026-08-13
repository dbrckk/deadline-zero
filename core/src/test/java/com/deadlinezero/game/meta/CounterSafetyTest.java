package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class CounterSafetyTest {
    @Test void longAdditionSaturatesInsteadOfWrapping() {
        assertEquals(Long.MAX_VALUE, SaturatingMath.addPositive(Long.MAX_VALUE - 3L, 10L));
        assertEquals(12L, SaturatingMath.addPositive(5L, 7L));
        assertEquals(5L, SaturatingMath.addPositive(5L, -7L));
    }

    @Test void dailyCountersSaturateAndIgnoreNegativeKills() {
        assertEquals(Integer.MAX_VALUE, DailyCounterMath.increment(Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DailyCounterMath.addKills(Integer.MAX_VALUE - 2, 10));
        assertEquals(25, DailyCounterMath.addKills(25, -100));
        assertEquals(31, DailyCounterMath.addKills(25, 6));
    }
}
