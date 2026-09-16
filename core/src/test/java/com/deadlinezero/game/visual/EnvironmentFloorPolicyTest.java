package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class EnvironmentFloorPolicyTest {
    @Test
    void legacyEnvironmentFloorCannotReintroduceHazardWallpaper() {
        assertEquals(0, EnvironmentRenderer.legacyHazardTileBudget(),
            "legacy floor pass must never draw hazard tiles; CombatWorldRenderer owns sparse accents");
    }
}
