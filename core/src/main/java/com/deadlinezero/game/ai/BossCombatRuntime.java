package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.RunStageContext;

/** Runtime timers and phase-gated decisions for advanced boss actions. */
public final class BossCombatRuntime {
    private static final float SUMMON_TELEGRAPH_SECONDS = .72f;

    private final BossIdentity identity;
    private final BossAffixRules.Affix affix;
    private float chargeTimer = 4.5f;
    private float summonTimer = 8.0f;
    private float enragePulseTimer = 5.0f;
    private float chargeDuration;

    public BossCombatRuntime() { this(BossIdentity.forStage(RunStageContext.stage())); }
    public BossCombatRuntime(boolean revenant) { this(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA); }

    public BossCombatRuntime(BossIdentity identity) {
        this.identity = identity == null ? BossIdentity.ALPHA : identity;
        this.affix = BossAffixRules.forRun(RunStageContext.stage(), RunStageContext.threatTier());
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
        float baseCooldown;
        switch (identity) {
            case REVENANT -> { baseCooldown = phase >= 3 ? RevenantBossProfile.PHASE3_CHARGE_COOLDOWN : RevenantBossProfile.PHASE2_CHARGE_COOLDOWN; chargeDuration = phase >= 3 ? .66f : .54f; }
            case WARDEN -> { baseCooldown = phase >= 3 ? WardenBossProfile.PHASE3_CHARGE_COOLDOWN : WardenBossProfile.PHASE2_CHARGE_COOLDOWN; chargeDuration = phase >= 3 ? WardenBossProfile.PHASE3_CHARGE_DURATION : WardenBossProfile.PHASE2_CHARGE_DURATION; }
            case HARVESTER -> { baseCooldown = phase >= 3 ? HarvesterBossProfile.PHASE3_CHARGE_COOLDOWN : HarvesterBossProfile.PHASE2_CHARGE_COOLDOWN; chargeDuration = phase >= 3 ? HarvesterBossProfile.PHASE3_CHARGE_DURATION : HarvesterBossProfile.PHASE2_CHARGE_DURATION; }
            default -> { baseCooldown = phase >= 3 ? 3.0f : 4.2f; chargeDuration = phase >= 3 ? .72f : .58f; }
        }
        chargeTimer = baseCooldown * affix.chargeCooldown;
        return true;
    }

    public boolean consumeSummon(int phase) {
        if (phase < 2 || summonTimer > 0f) return false;
        float base = switch (identity) {
            case REVENANT -> phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COOLDOWN : RevenantBossProfile.PHASE2_SUMMON_COOLDOWN;
            case WARDEN -> phase >= 3 ? WardenBossProfile.PHASE3_SUMMON_COOLDOWN : WardenBossProfile.PHASE2_SUMMON_COOLDOWN;
            case HARVESTER -> phase >= 3 ? HarvesterBossProfile.PHASE3_SUMMON_COOLDOWN : HarvesterBossProfile.PHASE2_SUMMON_COOLDOWN;
            default -> phase >= 3 ? 5.2f : 8.5f;
        };
        summonTimer = base * affix.summonCooldown;
        return true;
    }

    /** True during the final pre-summon window. HARVESTER uses this for visible portal telegraphs. */
    public boolean summonTelegraphing(int phase) {
        return phase >= 2 && summonTimer > 0f && summonTimer <= SUMMON_TELEGRAPH_SECONDS;
    }

    /** 0 at telegraph start and 1 immediately before the summon fires. */
    public float summonTelegraphProgress(int phase) {
        if (!summonTelegraphing(phase)) return 0f;
        return Math.max(0f, Math.min(1f, 1f - summonTimer / SUMMON_TELEGRAPH_SECONDS));
    }

    public static float summonTelegraphSeconds() { return SUMMON_TELEGRAPH_SECONDS; }

    public boolean consumeEnragePulse(int phase) {
        if (phase < 3 || enragePulseTimer > 0f) return false;
        float base = switch (identity) {
            case REVENANT -> RevenantBossProfile.PHASE3_PULSE_COOLDOWN;
            case WARDEN -> WardenBossProfile.PHASE3_PULSE_COOLDOWN;
            case HARVESTER -> HarvesterBossProfile.PHASE3_PULSE_COOLDOWN;
            default -> 3.8f;
        };
        enragePulseTimer = base * affix.pulseCooldown;
        return true;
    }

    public int summonCount(int phase) {
        int base = switch (identity) {
            case REVENANT -> phase >= 3 ? RevenantBossProfile.PHASE3_SUMMON_COUNT : RevenantBossProfile.PHASE2_SUMMON_COUNT;
            case WARDEN -> phase >= 3 ? WardenBossProfile.PHASE3_SUMMON_COUNT : WardenBossProfile.PHASE2_SUMMON_COUNT;
            case HARVESTER -> phase >= 3 ? HarvesterBossProfile.PHASE3_SUMMON_COUNT : HarvesterBossProfile.PHASE2_SUMMON_COUNT;
            default -> phase >= 3 ? 6 : 3;
        };
        return base + affix.summonBonus;
    }

    public int enrageShots() {
        int base = switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_SHOTS;
            case WARDEN -> WardenBossProfile.ENRAGE_SHOTS;
            case HARVESTER -> HarvesterBossProfile.ENRAGE_SHOTS;
            default -> 20;
        };
        return base + affix.enrageShotBonus;
    }

    public float enrageProjectileSpeed() {
        float base = switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_PROJECTILE_SPEED;
            case WARDEN -> WardenBossProfile.ENRAGE_PROJECTILE_SPEED;
            case HARVESTER -> HarvesterBossProfile.ENRAGE_PROJECTILE_SPEED;
            default -> 8.2f;
        };
        return base * (affix == BossAffixRules.Affix.ARTILLERY || affix == BossAffixRules.Affix.APOCALYPSE ? 1.12f : 1f);
    }

    public int enrageExplosiveEvery() {
        int base = switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_EXPLOSIVE_EVERY;
            case WARDEN -> WardenBossProfile.ENRAGE_EXPLOSIVE_EVERY;
            case HARVESTER -> HarvesterBossProfile.ENRAGE_EXPLOSIVE_EVERY;
            default -> 4;
        };
        return Math.max(1, base - affix.explosiveDensityBonus);
    }

    public float enrageExplosionRadius() {
        float base = switch (identity) {
            case REVENANT -> RevenantBossProfile.ENRAGE_EXPLOSION_RADIUS;
            case WARDEN -> WardenBossProfile.ENRAGE_EXPLOSION_RADIUS;
            case HARVESTER -> HarvesterBossProfile.ENRAGE_EXPLOSION_RADIUS;
            default -> 2.0f;
        };
        return base * (affix == BossAffixRules.Affix.ARTILLERY || affix == BossAffixRules.Affix.APOCALYPSE ? 1.18f : 1f);
    }

    public boolean charging() { return chargeDuration > 0f; }
    public float chargeDuration() { return chargeDuration; }
    public BossIdentity identity() { return identity; }
    public BossAffixRules.Affix affix() { return affix; }
    public boolean revenant() { return identity == BossIdentity.REVENANT; }
    public boolean warden() { return identity == BossIdentity.WARDEN; }
    public boolean harvester() { return identity == BossIdentity.HARVESTER; }
}
