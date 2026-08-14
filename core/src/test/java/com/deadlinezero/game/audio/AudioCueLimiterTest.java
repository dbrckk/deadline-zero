package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class AudioCueLimiterTest {
    @Test void firstCueAlwaysPasses() {
        AudioCueLimiter limiter = new AudioCueLimiter();
        assertTrue(limiter.allow(AudioDirector.Cue.SHOT, 1_000_000L));
    }

    @Test void repeatedCueInsideWindowIsRejected() {
        AudioCueLimiter limiter = new AudioCueLimiter();
        long now = 10_000_000L;
        assertTrue(limiter.allow(AudioDirector.Cue.HIT, now));
        assertFalse(limiter.allow(AudioDirector.Cue.HIT, now + 20_000_000L));
    }

    @Test void cuePassesOnceItsWindowExpires() {
        AudioCueLimiter limiter = new AudioCueLimiter();
        long now = 10_000_000L;
        assertTrue(limiter.allow(AudioDirector.Cue.CRIT, now));
        assertTrue(limiter.allow(AudioDirector.Cue.CRIT,
            now + AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.CRIT)));
    }

    @Test void cuesAreRateLimitedIndependently() {
        AudioCueLimiter limiter = new AudioCueLimiter();
        long now = 10_000_000L;
        assertTrue(limiter.allow(AudioDirector.Cue.SHOT, now));
        assertTrue(limiter.allow(AudioDirector.Cue.KILL, now));
        assertFalse(limiter.allow(AudioDirector.Cue.SHOT, now + 1_000_000L));
    }

    @Test void resetRestoresImmediatePlayback() {
        AudioCueLimiter limiter = new AudioCueLimiter();
        long now = 10_000_000L;
        assertTrue(limiter.allow(AudioDirector.Cue.UI_SELECT, now));
        assertFalse(limiter.allow(AudioDirector.Cue.UI_SELECT, now + 1_000_000L));
        limiter.reset();
        assertTrue(limiter.allow(AudioDirector.Cue.UI_SELECT, now + 1_000_000L));
    }
}
