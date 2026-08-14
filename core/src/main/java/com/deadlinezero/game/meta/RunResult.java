package com.deadlinezero.game.meta;

/** Immutable payload passed from combat to result screens, including settled contract and ascension state. */
public record RunResult(
    int kills,
    float secondsSurvived,
    boolean bossKilled,
    int stage,
    RunRewardCalculator.Rewards rewards,
    EquipmentItem drop,
    String contractTitle,
    int contractBonusPercent,
    int threatTier,
    int threatBonusPercent,
    int unlockedThreatTier,
    int threatMilestoneGems
) {
    /** Compatibility constructor for tests and non-contracted result creation. */
    public RunResult(int kills, float secondsSurvived, boolean bossKilled, int stage,
                     RunRewardCalculator.Rewards rewards, EquipmentItem drop) {
        this(kills, secondsSurvived, bossKilled, stage, rewards, drop, "STANDARD", 0, 0, 0, 0, 0);
    }

    /** Compatibility constructor for contracted results created before ascension was added. */
    public RunResult(int kills, float secondsSurvived, boolean bossKilled, int stage,
                     RunRewardCalculator.Rewards rewards, EquipmentItem drop,
                     String contractTitle, int contractBonusPercent) {
        this(kills, secondsSurvived, bossKilled, stage, rewards, drop,
            contractTitle, contractBonusPercent, 0, 0, 0, 0);
    }

    public RunResult {
        if (contractTitle == null || contractTitle.isBlank()) contractTitle = "STANDARD";
        contractBonusPercent = Math.max(0, contractBonusPercent);
        threatTier = ThreatTierRules.sanitizeTier(threatTier);
        threatBonusPercent = Math.max(0, threatBonusPercent);
        unlockedThreatTier = ThreatTierRules.sanitizeTier(unlockedThreatTier);
        threatMilestoneGems = Math.max(0, threatMilestoneGems);
    }
}
