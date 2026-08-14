package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Runtime timers and phase-gated decisions for advanced boss actions. */
public final class BossCombatRuntime {
    private final BossIdentity identity;
    private float chargeTimer = 4.5f;
    private float summonTimer = 8.0f;
    private float enragePulseTimer = 5.0f;
    private float chargeDuration;

    public BossCombatRuntime() {
        this(BossIdentity.forStage(RunStageContext.stage()));
    }

    /** Compatibility constructor retained for existing tests/callers. */
    public BossCombatRuntime(boolean revenant) {
        this(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA);
    }

    public BossCombatRuntime(BossIdentity identity) {
        this.identity = identity == null ? BossIdentity.ALPHA : identity;
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
        switch (identity) {
            case REVENANT -> {
                chargeTimer = phase >= 3 ? RevenantBossProfile.PHASE3_CHARGE_COOLDOWN : RevenantBossProfile.PHASE2_CHARGE_COOLDOWN;
                chargeDuration = phase >= 3 ? .66f : .54f;
            }
            case WARDEN -> {
                chargeTimer = phase >= 3 ? WardenBossProfile.PHASE3_CHARGE_COOLDOWN : WardenBossProfile.PHASE2_CHARGE_COOLDOWN;
                chargeDuration = phase >= 3 ? WardenBossProfile.PHASE3_CHARGE_DURATION : WardenBossProfile.PHASE2_CHARGE_DURATION;
            }
            default -> {
                chargeTimer = phase >= 3 ? 3.0f : 4.2f;
                chargeDuration = phase >= 3 ? .72f : .58f;
            }
        }
        return true;
    }

    public boolean consumeSummon(int phase) {
        if (phase < 2 || summonTimer > 0f) return false;
        summonTimer = switch (identity) {
            case REVENANT -> phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COOLDOWN : RevenantBossProfile.PHASE2_SUMMON_COOLDOWN;
            case WARDEN -> phase >= 3 ? WardenBossProfile.PHASE3_SUMMON_COOLDOWN : WardenBossProfile.PHASE2_SUMMON_COOLDOWN;
            default -> phase >= 3 ? 5.2f : 8.5f;
        };
        return true;
    }

    public boolean consumeEnragePulse(int phase) {
        if (phase < 3 || enragePulseTimer > 0f) return false;
        enragePulseTimer = switch (identity) {
            case REVENANT -> RevenantBossProfile.PHASE3_PULSE_COOLDOWN;
            case WARDEN -> WardenBossProfile.PHASE3_PULSE_COOLDOWN;
            default -> 3.8f;
        };
        return true;
    }

    public int summonCount(int phase) {
        return switch (identity) {
            case REVENANT -> phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COUNT : RevenantBossProfile.PHASE2_SUMMON_COUNT;
            case WARDEN -> phase >= 3 ? WardenBossProfile.PHASE3_SUMMON_COUNT : WardenBossProfile.PHASE2_SUMMON_COUNT;
            default -> phase >= 3 ? 6 : 3;
        };
    }

    public int enrageShots() {
        return switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_SHOTS;
            case WARDEN -> WardenBossProfile.ENRAGE_SHOTS;
            default -> 20;
        };
    }

    public float enrageProjectileSpeed() {
        return switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_PROJECTILE_SPEED;
            case WARDEN -> WardenBossProfile.ENRAGE_PROJECTILE_SPEED;
            default -> 8.2f;
        };
    }

    public int enrageExplosiveEvery() {
        return switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_EXPLOSIVE_EVERY;
            case WARDEN -> WardenBossProfile.ENRAGE_EXPLOSIVE_EVERY;
            default -> 4;
        };
    }

    public float enrageExplosionRadius() {
        return switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_EXPLOSION_RADIUS;
            case WARDEN -> WardenBossProfile.ENRAGE_EXPLOSION_RADIUS;
            default -> 2.0f;
        };
    }

    public boolean charging() { return chargeDuration > 0f; }
    public float chargeDuration() { return chargeDuration; }
    public BossIdentity identity() { return identity; }
    public boolean revenant() { return identity == BossIdentity.REVENANT; }
    public boolean warden() { return identity == BossIdentity.WARDEN; }
}
