package com.deadlinezero.game.meta;

/** Immutable payload passed from combat to the result screen. */
public record RunResult(
    int kills,
    float secondsSurvived,
    boolean bossKilled,
    int stage,
    RunRewardCalculator.Rewards rewards,
    EquipmentItem drop
) {}
