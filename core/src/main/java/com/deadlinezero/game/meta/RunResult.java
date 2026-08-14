package com.deadlinezero.game.meta;

/** Immutable payload passed from combat to result screens, including the settled run contract. */
public record RunResult(
    int kills,
    float secondsSurvived,
    boolean bossKilled,
    int stage,
    RunRewardCalculator.Rewards rewards,
    EquipmentItem drop,
    String contractTitle,
    int contractBonusPercent
) {
    /** Compatibility constructor for tests and non-contracted result creation. */
    public RunResult(int kills, float secondsSurvived, boolean bossKilled, int stage,
                     RunRewardCalculator.Rewards rewards, EquipmentItem drop) {
        this(kills, secondsSurvived, bossKilled, stage, rewards, drop, "STANDARD", 0);
    }

    public RunResult {
        if (contractTitle == null || contractTitle.isBlank()) contractTitle = "STANDARD";
        contractBonusPercent = Math.max(0, contractBonusPercent);
    }
}
