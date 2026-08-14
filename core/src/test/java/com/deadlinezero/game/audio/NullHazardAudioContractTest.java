package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class NullHazardAudioContractTest {
    @Test void nullHazardCuesHaveResilientFallbackChains() {
        assertEquals(AudioDirector.Cue.SINGULARITY, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_RIFT));
        assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_STATIC));
        assertEquals(AudioDirector.Cue.BOSS_PHASE, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_BEAM));
    }

    @Test void nullHazardCuesAreRateLimitedAsEnvironmentalEvents() {
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_RIFT) >= 400_000_000L);
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_STATIC) >= 250_000_000L);
        assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_BEAM) >= 350_000_000L);
    }
}
