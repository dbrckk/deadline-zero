package com.deadlinezero.game.ai;

/** Runtime timers and phase-gated decisions for advanced boss actions. */
public final class BossCombatRuntime {
    private float chargeTimer = 4.5f;
    private float summonTimer = 8.0f;
    private float enragePulseTimer = 5.0f;
    private float chargeDuration;

    public void update(float dt, int phase) {
        chargeTimer -= dt;
        summonTimer -= dt;
        enragePulseTimer -= dt;
        chargeDuration = Math.max(0f, chargeDuration - dt);

        if (phase >= 2 && chargeTimer < -2f) chargeTimer = -2f;
        if (phase >= 3 && summonTimer < -2f) summonTimer = -2f;
    }

    public boolean consumeCharge(int phase) {
        if (phase < 2 || chargeDuration > 0f || chargeTimer > 0f) return false;
        chargeTimer = phase >= 3 ? 3.0f : 4.2f;
        chargeDuration = phase >= 3 ? .72f : .58f;
        return true;
    }

    public boolean consumeSummon(int phase) {
        if (phase < 2 || summonTimer > 0f) return false;
        summonTimer = phase >= 3 ? 5.2f : 8.5f;
        return true;
    }

    public boolean consumeEnragePulse(int phase) {
        if (phase < 3 || enragePulseTimer > 0f) return false;
        enragePulseTimer = 3.8f;
        return true;
    }

    public boolean charging() { return chargeDuration > 0f; }
    public float chargeDuration() { return chargeDuration; }
}
