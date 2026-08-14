package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class WeaponSignatureAudioContractTest {
    @Test void authoredSignatureCuesHaveSafeFallbacks() {
        assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.ION_OVERCHARGE));
        assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.CINDER_OVERHEAT));
    }

    @Test void signatureCuesAreRateLimitedIndependently() {
        long ion = AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.ION_OVERCHARGE);
        long cinder = AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.CINDER_OVERHEAT);
        assertTrue(ion >= 100_000_000L);
        assertTrue(cinder >= 220_000_000L);
        assertTrue(cinder > ion);
    }
}
