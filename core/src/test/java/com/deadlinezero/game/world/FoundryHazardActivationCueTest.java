package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class FoundryHazardActivationCueTest {
    @Test void activationCueFiresExactlyOnceAfterWarningCompletes() {
        ArenaHazardRuntime runtime = new ArenaHazardRuntime(10, 3, 0);
        float spawnDelay = runtime.foundryHazardInterval() * .72f;
        for (float elapsed = 0f; elapsed < spawnDelay + .05f; elapsed += .05f) {
            runtime.update(.05f, 0f, 0f);
        }
        ArenaHazardRuntime.Hazard hazard = runtime.hazards().get(0);

        assertFalse(hazard.consumeActivationCue());
        for (int i = 0; i < 30 && hazard.phase() == ArenaHazardRuntime.Phase.WARNING; i++) {
            runtime.update(.05f, 0f, 0f);
        }
        assertTrue(hazard.consumeActivationCue());
        assertFalse(hazard.consumeActivationCue());
    }
}
