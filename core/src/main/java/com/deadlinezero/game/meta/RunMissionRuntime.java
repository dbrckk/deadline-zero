package com.deadlinezero.game.meta;

/** Lightweight active-run telemetry and deferred victory signal. */
public final class RunMissionRuntime {
    private static int kills;
    private static float elapsed;
    private static boolean victorySignaled;
    private static Runnable victoryCallback;
    private static int requiredBossKills = 1;
    private static int bossKills;

    private RunMissionRuntime() {}

    public static void begin(Runnable callback) { begin(callback, 1); }

    public static void begin(Runnable callback, int requiredBossDefeats) {
        kills = 0;
        elapsed = 0f;
        victorySignaled = false;
        victoryCallback = callback;
        requiredBossKills = Math.max(1, requiredBossDefeats);
        bossKills = 0;
    }

    public static void update(float seconds, int killCount) {
        elapsed = Math.max(0f, seconds);
        kills = Math.max(0, killCount);
    }

    public static int kills() { return kills; }
    public static float elapsed() { return elapsed; }
    public static int bossKills() { return bossKills; }
    public static int requiredBossKills() { return requiredBossKills; }

    public static void signalBossDefeated() {
        if (victorySignaled) return;
        bossKills = Math.min(requiredBossKills, bossKills + 1);
        if (bossKills < requiredBossKills) return;
        victorySignaled = true;
        if (victoryCallback != null) victoryCallback.run();
    }

    public static void end() {
        victoryCallback = null;
        requiredBossKills = 1;
        bossKills = 0;
    }
}
