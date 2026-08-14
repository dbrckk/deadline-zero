package com.deadlinezero.game.meta;

/** Immutable, non-identifying local run telemetry used only for game balancing. */
public record BalanceRunSample(
    long sequence,
    int stage,
    int threatTier,
    int runOrdinal,
    boolean victory,
    float seconds,
    int kills,
    float damageDealt,
    float damageReceived,
    float maxHitDealt,
    float maxHitReceived,
    String contract,
    String survivor,
    String weaponId,
    int ascensionSetPieces,
    boolean zeroDayCore
) {
    public BalanceRunSample {
        sequence = Math.max(0L, sequence);
        stage = Math.max(1, stage);
        threatTier = ThreatTierRules.sanitizeTier(threatTier);
        runOrdinal = Math.max(0, runOrdinal);
        seconds = finiteNonNegative(seconds);
        kills = Math.max(0, kills);
        damageDealt = finiteNonNegative(damageDealt);
        damageReceived = finiteNonNegative(damageReceived);
        maxHitDealt = finiteNonNegative(maxHitDealt);
        maxHitReceived = finiteNonNegative(maxHitReceived);
        contract = safeText(contract, "STANDARD");
        survivor = safeText(survivor, "REX");
        weaponId = safeText(weaponId, "ar9");
        ascensionSetPieces = Math.max(0, Math.min(4, ascensionSetPieces));
    }

    public float dps() { return seconds <= .001f ? 0f : damageDealt / seconds; }
    public float damageTakenPerMinute() { return seconds <= .001f ? 0f : damageReceived * 60f / seconds; }
    public float killsPerMinute() { return seconds <= .001f ? 0f : kills * 60f / seconds; }

    private static float finiteNonNegative(float value) {
        return Float.isFinite(value) ? Math.max(0f, value) : 0f;
    }

    private static String safeText(String value, String fallback) {
        if (value == null || value.isBlank()) return fallback;
        String trimmed = value.trim();
        return trimmed.length() > 48 ? trimmed.substring(0, 48) : trimmed;
    }
}
