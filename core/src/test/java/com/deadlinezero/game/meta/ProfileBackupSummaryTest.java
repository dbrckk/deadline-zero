package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class ProfileBackupSummaryTest {
    @Test void dominanceRequiresNoRegressionAcrossMonotoneProgress() {
        ProfileBackupSummary local = ProfileBackupSummary.from(Map.of(
            "highestStage", 8, "accountLevel", 12, "totalRuns", 40,
            "totalKills", 9000L, "threat.highest", 2));
        ProfileBackupSummary remote = ProfileBackupSummary.from(Map.of(
            "highestStage", 7, "accountLevel", 12, "totalRuns", 39,
            "totalKills", 8500L, "threat.highest", 2));
        assertTrue(local.dominates(remote));
        assertFalse(remote.dominates(local));
    }

    @Test void conflictingProgressIsDivergentRatherThanSilentlyRanked() {
        ProfileBackupSummary moreStage = ProfileBackupSummary.from(Map.of(
            "highestStage", 10, "accountLevel", 8, "totalRuns", 20, "totalKills", 3000L));
        ProfileBackupSummary moreHistory = ProfileBackupSummary.from(Map.of(
            "highestStage", 8, "accountLevel", 12, "totalRuns", 50, "totalKills", 9000L));
        assertFalse(moreStage.dominates(moreHistory));
        assertFalse(moreHistory.dominates(moreStage));
    }
}
