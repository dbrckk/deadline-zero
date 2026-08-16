package com.deadlinezero.game.meta;

/** Applies one completed run to the persistent account profile. */
public final class RunSettlement {
    private RunSettlement() {}

    public static RunRewardCalculator.Rewards apply(PlayerProfile profile, int kills, float secondsSurvived,
                                                     boolean bossKilled, int stage) {
        RunRewardCalculator.Rewards rewards = RunRewardCalculator.calculate(kills, secondsSurvived, bossKilled, stage);
        profile.addCurrency(PlayerProfile.Currency.CREDITS, rewards.credits());
        profile.addCurrency(PlayerProfile.Currency.GEMS, rewards.gems());
        profile.addAccountXp(rewards.accountXp());
        profile.recordRun(kills, stage);
        if (bossKilled) {
            MasteryProgress.Gain mastery = profile.mastery.recordVictory(RunLoadoutContext.weaponDefinition().id, stage);
            profile.addCurrency(PlayerProfile.Currency.CREDITS, mastery.creditsReward());
            profile.addCurrency(PlayerProfile.Currency.GEMS, mastery.gemsReward());
        }
        BalanceRunSample sample = BalanceTelemetryRuntime.settle(bossKilled, secondsSurvived, kills);
        BalanceTelemetryStore.append(sample);
        return rewards;
    }
}
