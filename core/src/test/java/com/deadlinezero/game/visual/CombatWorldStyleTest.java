package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatWorldStyleTest {
    @Test
    void quarantineArenaIsDeterministicAndAvoidsHazardWallpaper() {
        CombatWorldStyle.Profile a = CombatWorldStyle.forStage(1, 42L);
        CombatWorldStyle.Profile b = CombatWorldStyle.forStage(1, 42L);

        assertEquals(a, b);
        assertTrue(a.hazardCoverage() <= .12f);
        assertTrue(a.largeFeatureCount() >= 4 && a.largeFeatureCount() <= 12);
        assertTrue(a.propCount() >= 6 && a.propCount() <= 24);
        assertTrue(a.decalCount() >= 8 && a.decalCount() <= 36);
        assertTrue(a.lightCount() >= 2 && a.lightCount() <= 8);
    }

    @Test
    void differentRunSeedsCreateDifferentButBoundedWorldBreakup() {
        CombatWorldStyle.Profile a = CombatWorldStyle.forStage(1, 42L);
        CombatWorldStyle.Profile b = CombatWorldStyle.forStage(1, 43L);

        assertNotEquals(a.seed(), b.seed());
        assertNotEquals(a.decalCount(), b.decalCount());
        assertTrue(b.hazardCoverage() <= .12f);
    }
}
