package com.deadlinezero.game.meta;

/** In-memory accumulator for one run. No network access and no player-identifying data. */
public final class BalanceTelemetryRuntime {
    private static boolean active;
    private static long sequence;
    private static int stage = 1;
    private static int threatTier;
    private static int runOrdinal;
    private static String contract = "STANDARD";
    private static float damageDealt;
    private static float damageReceived;
    private static float maxHitDealt;
    private static float maxHitReceived;

    private BalanceTelemetryRuntime() {}

    public static void begin(int nextStage, int nextRunOrdinal, int nextThreatTier) {
        active = true;
        sequence++;
        stage = Math.max(1, nextStage);
        runOrdinal = Math.max(0, nextRunOrdinal);
        threatTier = ThreatTierRules.sanitizeTier(nextThreatTier);
        contract = "STANDARD";
        damageDealt = 0f;
        damageReceived = 0f;
        maxHitDealt = 0f;
        maxHitReceived = 0f;
    }

    public static void setContract(String title) {
        if (active && title != null && !title.isBlank()) contract = title;
    }

    public static void recordDamageDealt(float amount) {
        float safe = safeDamage(amount);
        if (!active || safe <= 0f) return;
        damageDealt = safeAdd(damageDealt, safe);
        maxHitDealt = Math.max(maxHitDealt, safe);
    }

    public static void recordDamageReceived(float amount) {
        float safe = safeDamage(amount);
        if (!active || safe <= 0f) return;
        damageReceived = safeAdd(damageReceived, safe);
        maxHitReceived = Math.max(maxHitReceived, safe);
    }

    public static BalanceRunSample settle(boolean victory, float seconds, int kills) {
        if (!active) begin(RunStageContext.stage(), RunStageContext.runOrdinal(), RunStageContext.threatTier());
        BalanceRunSample sample = new BalanceRunSample(
            sequence,
            stage,
            threatTier,
            runOrdinal,
            victory,
            seconds,
            kills,
            damageDealt,
            damageReceived,
            maxHitDealt,
            maxHitReceived,
            contract,
            RunLoadoutContext.survivor().name(),
            RunLoadoutContext.weaponDefinition().id,
            RunLoadoutContext.ascensionSetPieces(),
            RunLoadoutContext.zeroDayCoreEquipped()
        );
        active = false;
        return sample;
    }

    public static boolean active() { return active; }

    private static float safeDamage(float amount) {
        return Float.isFinite(amount) ? Math.max(0f, amount) : 0f;
    }

    private static float safeAdd(float a, float b) {
        float result = a + b;
        return Float.isFinite(result) ? Math.max(0f, result) : Float.MAX_VALUE;
    }
}
