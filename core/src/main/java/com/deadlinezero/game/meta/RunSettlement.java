package com.deadlinezero.game.meta;

import com.deadlinezero.game.visual.EnvironmentBiomeRules;

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
        MasteryRunNotice.clear();
        if (bossKilled) {
            var weapon = RunLoadoutContext.weaponDefinition();
            MasteryProgress.Gain mastery = profile.mastery.recordVictory(weapon.id, stage);
            profile.addCurrency(PlayerProfile.Currency.CREDITS, mastery.creditsReward());
            profile.addCurrency(PlayerProfile.Currency.GEMS, mastery.gemsReward());
            MasteryRunNotice.capture(mastery, weapon.displayName, EnvironmentBiomeRules.forStage(stage));
        }
        BalanceRunSample sample = BalanceTelemetryRuntime.settle(bossKilled, secondsSurvived, kills);
        BalanceTelemetryStore.append(sample);
        return rewards;
    }
}
