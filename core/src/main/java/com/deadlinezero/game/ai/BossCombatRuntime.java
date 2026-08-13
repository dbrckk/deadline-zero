package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Runtime timers and phase-gated decisions for advanced boss actions. */
public final class BossCombatRuntime {
    private final boolean revenant;
    private float chargeTimer = 4.5f;
    private float summonTimer = 8.0f;
    private float enragePulseTimer = 5.0f;
    private float chargeDuration;

    public BossCombatRuntime() {
        this(RevenantBossProfile.useForStage(RunStageContext.stage()));
    }

    public BossCombatRuntime(boolean revenant) {
        this.revenant = revenant;
    }

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
        chargeTimer = revenant
            ? (phase >= 3 ? RevenantBossProfile.PHASE3_CHARGE_COOLDOWN : RevenantBossProfile.PHASE2_CHARGE_COOLDOWN)
            : (phase >= 3 ? 3.0f : 4.2f);
        chargeDuration = revenant ? (phase >= 3 ? .66f : .54f) : (phase >= 3 ? .72f : .58f);
        return true;
    }

    public boolean consumeSummon(int phase) {
        if (phase < 2 || summonTimer > 0f) return false;
        summonTimer = revenant
            ? (phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COOLDOWN : RevenantBossProfile.PHASE2_SUMMON_COOLDOWN)
            : (phase >= 3 ? 5.2f : 8.5f);
        return true;
    }

    public boolean consumeEnragePulse(int phase) {
        if (phase < 3 || enragePulseTimer > 0f) return false;
        enragePulseTimer = revenant ? RevenantBossProfile.PHASE3_PULSE_COOLDOWN : 3.8f;
        return true;
    }

    public int summonCount(int phase) {
        if (!revenant) return phase >= 3 ? 6 : 3;
        return phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COUNT : RevenantBossProfile.PHASE2_SUMMON_COUNT;
    }

    public int enrageShots() { return revenant ? RevenantBossProfile.ENRAGE_SHOTS : 20; }
    public float enrageProjectileSpeed() { return revenant ? RevenantBossProfile.ENRAGE_PROJECTILE_SPEED : 8.2f; }
    public int enrageExplosiveEvery() { return revenant ? RevenantBossProfile.ENRAGE_EXPLOSIVE_EVERY : 4; }
    public float enrageExplosionRadius() { return revenant ? RevenantBossProfile.ENRAGE_EXPLOSION_RADIUS : 2.0f; }

    public boolean charging() { return chargeDuration > 0f; }
    public float chargeDuration() { return chargeDuration; }
    public boolean revenant() { return revenant; }
}
