package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class FoundryHazardAudioContractTest {
    @Test void dedicatedFoundryCuesHaveSafeFallbacks() {
        assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_LAVA));
        assertEquals(AudioDirector.Cue.DASH, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_STEAM));
        assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_HEAT));
    }

    @Test void foundryCuesAreRateLimitedAsEnvironmentalEvents() {
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_LAVA) >= 400_000_000L);
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_STEAM) >= 250_000_000L);
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_HEAT) >= 350_000_000L);
    }
}
