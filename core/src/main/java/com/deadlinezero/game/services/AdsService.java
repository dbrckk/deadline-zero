package com.deadlinezero.game.services;

public interface AdsService {
    enum Reward { REVIVE, DOUBLE_LOOT, REROLL_SKILLS, BONUS_CHEST }
    boolean isRewardedReady();
    void showRewarded(Reward reward, Runnable onEarned, Runnable onUnavailable);
    default void preload() {}
}
