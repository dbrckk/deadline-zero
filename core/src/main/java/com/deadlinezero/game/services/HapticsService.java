package com.deadlinezero.game.services;

/** Platform haptic feedback bridge. Implementations should keep pulses short and non-blocking. */
public interface HapticsService {
    void dash();
    void damage();
    void bossKill();

    static HapticsService noOp() {
        return new HapticsService() {
            @Override public void dash() {}
            @Override public void damage() {}
            @Override public void bossKill() {}
        };
    }
}
