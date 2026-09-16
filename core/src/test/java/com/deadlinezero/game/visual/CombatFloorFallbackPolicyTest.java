package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class CombatFloorFallbackPolicyTest {
    @Test
    void premiumFloorFailureNeverRestoresLegacyHazardWallpaper() {
        assertEquals(CombatFloorFallbackPolicy.Mode.PREMIUM_TEXTURED,
            CombatFloorFallbackPolicy.modeFor(true));
        assertEquals(CombatFloorFallbackPolicy.Mode.CLEAN_EMPTY,
            CombatFloorFallbackPolicy.modeFor(false));
    }
}
