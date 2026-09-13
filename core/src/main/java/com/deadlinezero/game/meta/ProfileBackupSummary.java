package com.deadlinezero.game.meta;

import java.util.Map;
import java.util.Set;

/** Monotone progression vector used only when every non-monotone persisted field is identical. */
public record ProfileBackupSummary(
    int highestStage,
    int accountLevel,
    int totalRuns,
    long totalKills,
    int highestThreatTier
) {
    public static final Set<String> MONOTONE_KEYS = Set.of(
        "highestStage", "accountLevel", "totalRuns", "totalKills", "threat.highest"
    );

    public static ProfileBackupSummary from(Map<String, ?> values) {
        if (values == null) return new ProfileBackupSummary(1, 1, 0, 0L, 0);
        return new ProfileBackupSummary(
            Math.max(1, intValue(values.get("highestStage"), 1)),
            Math.max(1, intValue(values.get("accountLevel"), 1)),
            Math.max(0, intValue(values.get("totalRuns"), 0)),
            Math.max(0L, longValue(values.get("totalKills"), 0L)),
            Math.max(0, intValue(values.get("threat.highest"), 0))
        );
    }

    public boolean dominates(ProfileBackupSummary other) {
        if (other == null) return true;
        boolean noWorse = highestStage >= other.highestStage
            && accountLevel >= other.accountLevel
            && totalRuns >= other.totalRuns
            && totalKills >= other.totalKills
            && highestThreatTier >= other.highestThreatTier;
        boolean strictlyBetter = highestStage > other.highestStage
            || accountLevel > other.accountLevel
            || totalRuns > other.totalRuns
            || totalKills > other.totalKills
            || highestThreatTier > other.highestThreatTier;
        return noWorse && strictlyBetter;
    }

    private static int intValue(Object value, int fallback) {
        return value instanceof Number n ? n.intValue() : fallback;
    }

    private static long longValue(Object value, long fallback) {
        return value instanceof Number n ? n.longValue() : fallback;
    }
}
