package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class EnvironmentFloorVariationTest {
    @Test void floorRotationIsDeterministicAndQuarterTurnBounded() {
        for (int y = -8; y <= 8; y++) {
            for (int x = -12; x <= 12; x++) {
                int rotation = EnvironmentRenderer.floorRotationQuarterTurns(x, y);
                assertTrue(rotation >= 0 && rotation <= 3);
                assertEquals(rotation, EnvironmentRenderer.floorRotationQuarterTurns(x, y));
            }
        }
    }

    @Test void visibleArenaUsesAllFourOrientations() {
        Set<Integer> rotations = new HashSet<>();
        for (int y = -6; y < 6; y++) {
            for (int x = -10; x < 10; x++) {
                rotations.add(EnvironmentRenderer.floorRotationQuarterTurns(x, y));
            }
        }
        assertEquals(Set.of(0, 1, 2, 3), rotations);
    }
}
