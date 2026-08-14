package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class FoundryHazardActivationCueTest {
    @Test void activationCueFiresExactlyOnceAfterWarningCompletes() {
        ArenaHazardRuntime runtime = new ArenaHazardRuntime(10, 3, 0);
        float spawnDelay = runtime.foundryHazardInterval() * .72f + .01f;
        runtime.update(spawnDelay, 0f, 0f);
        ArenaHazardRuntime.Hazard hazard = runtime.hazards().get(0);

        assertFalse(hazard.consumeActivationCue());
        runtime.update(2f, 0f, 0f);
        assertTrue(hazard.consumeActivationCue());
        assertFalse(hazard.consumeActivationCue());
    }
}
