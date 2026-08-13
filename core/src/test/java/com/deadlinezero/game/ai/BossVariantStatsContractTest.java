package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BossVariantStatsContractTest {
    @Test void alphaKeepsBaseStats() {
        var s = BossVariantStats.forStage(3, 2200f, 1.35f, 24f);
        assertEquals(2200f, s.hp(), .001f);
        assertEquals(1.35f, s.speed(), .001f);
        assertEquals(24f, s.damage(), .001f);
    }

    @Test void revenantTradesHpForPressure() {
        var s = BossVariantStats.forStage(4, 2200f, 1.35f, 24f);
        assertTrue(s.hp() < 2200f);
        assertTrue(s.speed() > 1.35f);
        assertTrue(s.damage() > 24f);
    }
}
