package com.deadlinezero.game.services;

public interface AdsService {
    enum Reward { REVIVE, DOUBLE_LOOT, REROLL_SKILLS, BONUS_CHEST }

    interface FullscreenListener {
        void onOpening();
        void onClosed();
    }

    boolean isRewardedReady();
    void showRewarded(Reward reward, Runnable onEarned, Runnable onUnavailable);
    default void preload() {}
    default void setFullscreenListener(FullscreenListener listener) {}
}
