package com.deadlinezero.game.perf;

/**
 * Runtime-only FPS governor. The user's selected frame rate is a ceiling; sustained instability
 * can temporarily reduce the effective cap. Recovery is deliberately slower than degradation.
 */
public final class AdaptiveFrameRateGovernor {
    private static final int UNSTABLE_WINDOWS_TO_DROP = 3;
    private static final int STABLE_WINDOWS_TO_RAISE = 8;

    private int effectiveTarget = 60;
    private int unstableWindows;
    private int stableWindows;

    public int effectiveTarget() { return effectiveTarget; }

    public void reset(int selectedTarget) {
        effectiveTarget = normalize(selectedTarget);
        unstableWindows = 0;
        stableWindows = 0;
    }

    public int update(int selectedTarget, PerformanceTelemetry.Snapshot snapshot) {
        int selected = normalize(selectedTarget);
        if (effectiveTarget > selected) {
            effectiveTarget = selected;
            unstableWindows = 0;
            stableWindows = 0;
            return effectiveTarget;
        }

        if (snapshot == null || snapshot.targetFps() != effectiveTarget || !snapshot.stable()) {
            unstableWindows++;
            stableWindows = 0;
            if (unstableWindows >= UNSTABLE_WINDOWS_TO_DROP) {
                int lower = lowerTarget(effectiveTarget);
                if (lower < effectiveTarget) effectiveTarget = lower;
                unstableWindows = 0;
            }
            return effectiveTarget;
        }

        stableWindows++;
        unstableWindows = 0;
        if (stableWindows >= STABLE_WINDOWS_TO_RAISE && effectiveTarget < selected) {
            effectiveTarget = Math.min(selected, higherTarget(effectiveTarget));
            stableWindows = 0;
        }
        return effectiveTarget;
    }

    static int normalize(int target) {
        if (target >= 120) return 120;
        if (target >= 90) return 90;
        return 60;
    }

    private static int lowerTarget(int target) {
        return target >= 120 ? 90 : 60;
    }

    private static int higherTarget(int target) {
        return target <= 60 ? 90 : 120;
    }
}
