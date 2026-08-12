package com.deadlinezero.game.meta;

/** Lightweight active-run telemetry and deferred victory signal. */
public final class RunMissionRuntime {
    private static int kills;
    private static float elapsed;
    private static boolean victorySignaled;
    private static Runnable victoryCallback;

    private RunMissionRuntime() {}

    public static void begin(Runnable callback) {
        kills = 0;
        elapsed = 0f;
        victorySignaled = false;
        victoryCallback = callback;
    }

    public static void update(float seconds, int killCount) {
        elapsed = Math.max(0f, seconds);
        kills = Math.max(0, killCount);
    }

    public static int kills() { return kills; }
    public static float elapsed() { return elapsed; }

    public static void signalBossDefeated() {
        if (victorySignaled) return;
        victorySignaled = true;
        if (victoryCallback != null) victoryCallback.run();
    }

    public static void end() {
        victoryCallback = null;
    }
}
