package com.deadlinezero.game.audio;

/**
 * Allocation-free per-cue rate limiter for combat audio. Prevents dense combat events from
 * spawning excessive overlapping voices while preserving high-priority feedback.
 */
public final class AudioCueLimiter {
    private final long[] lastPlayedNanos = new long[AudioDirector.Cue.values().length];
    private final boolean[] initialized = new boolean[lastPlayedNanos.length];

    public boolean allow(AudioDirector.Cue cue, long nowNanos) {
        if (cue == null) return false;
        int index = cue.ordinal();
        long minInterval = minIntervalNanos(cue);
        if (!initialized[index]) {
            initialized[index] = true;
            lastPlayedNanos[index] = nowNanos;
            return true;
        }
        long elapsed = nowNanos - lastPlayedNanos[index];
        if (elapsed < 0L || elapsed < minInterval) return false;
        lastPlayedNanos[index] = nowNanos;
        return true;
    }

    static long minIntervalNanos(AudioDirector.Cue cue) {
        long millis = switch (cue) {
            case SHOT -> 28L;
            case HIT -> 34L;
            case CRIT -> 52L;
            case KILL -> 44L;
            case BOSS_HIT -> 60L;
            case BOSS_PHASE -> 700L;
            case BOSS_KILL -> 140L;
            case DASH -> 90L;
            case LEVEL_UP -> 180L;
            case UI_SELECT -> 75L;
            case UI_BACK -> 90L;
            case SINGULARITY -> 220L;
            case ION_OVERCHARGE -> 120L;
            case CINDER_OVERHEAT -> 260L;
        };
        return millis * 1_000_000L;
    }

    public void reset() {
        for (int i = 0; i < initialized.length; i++) {
            initialized[i] = false;
            lastPlayedNanos[i] = 0L;
        }
    }
}
