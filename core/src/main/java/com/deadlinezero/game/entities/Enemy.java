package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.ai.AttackController;
import com.deadlinezero.game.ai.BossCombatRuntime;
import com.deadlinezero.game.ai.BossPhaseController;
import com.deadlinezero.game.ai.EnemyArchetype;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageRules;

public final class Enemy extends ActorState {
    public enum Type { SHAMBLER, RUNNER, BRUTE, RANGED, ELITE, BOSS }
    public enum Variant { NORMAL, SWIFT, ARMORED, FERAL }

    public Type type;
    public Variant variant = Variant.NORMAL;
    public float speed;
    public float contactDamage;
    public int xpValue;
    public float hitFlash;
    public float slowTimer;
    public float slowMultiplier = 1f;
    public float burnTimer;
    public float burnDps;
    public float shockTimer;
    public float variantTime;
    public final Vector2 impulse = new Vector2();
    public final AttackController attack;
    public final BossPhaseController bossPhases;
    public final BossCombatRuntime bossCombat;

    public Enemy(Type type, float x, float y, float hp, float speed, float radius, float damage, int xp) {
        super(x, y, radius, hp * StageRules.enemyHpMultiplier(RunStageContext.stage()));
        int stage = RunStageContext.stage();
        this.type = type;
        this.speed = speed * StageRules.enemySpeedMultiplier(stage);
        this.contactDamage = damage * StageRules.enemyDamageMultiplier(stage);
        this.xpValue = Math.max(1, Math.round(xp * (1f + (stage - 1) * .035f)));
        EnemyArchetype archetype = switch (type) {
            case RANGED -> EnemyArchetype.RANGED;
            case BOSS -> EnemyArchetype.BOSS;
            default -> EnemyArchetype.MELEE;
        };
        this.attack = new AttackController(archetype);
        this.bossPhases = type == Type.BOSS ? new BossPhaseController() : null;
        this.bossCombat = type == Type.BOSS ? new BossCombatRuntime() : null;
        configureAttackCadence();

        if (type != Type.BOSS) {
            float chance = MathUtils.clamp(.02f + (stage - 1) * .018f, .02f, .20f);
            if (MathUtils.random() < chance) {
                float roll = MathUtils.random();
                applyVariant(roll < .36f ? Variant.SWIFT : (roll < .69f ? Variant.ARMORED : Variant.FERAL));
            }
        }
    }

    /** Applies a champion variant once, preserving the base archetype while changing combat priorities. */
    public void applyVariant(Variant next) {
        if (next == null || next == Variant.NORMAL || type == Type.BOSS || variant != Variant.NORMAL) return;
        variant = next;
        switch (next) {
            case SWIFT -> {
                speed *= 1.34f;
                maxHp *= .84f;
                hp = maxHp;
                xpValue = Math.max(1, Math.round(xpValue * 1.20f));
            }
            case ARMORED -> {
                maxHp *= 1.72f;
                hp = maxHp;
                speed *= .84f;
                xpValue = Math.max(1, Math.round(xpValue * 1.55f));
            }
            case FERAL -> {
                contactDamage *= 1.48f;
                speed *= 1.10f;
                maxHp *= 1.12f;
                hp = maxHp;
                xpValue = Math.max(1, Math.round(xpValue * 1.45f));
            }
            default -> { }
        }
        configureAttackCadence();
    }

    private void configureAttackCadence() {
        float cooldown = 1f, telegraph = 1f, recovery = 1f;
        switch (type) {
            case RUNNER -> { cooldown = .84f; telegraph = .72f; recovery = .82f; }
            case BRUTE -> { cooldown = 1.12f; telegraph = 1.34f; recovery = 1.18f; }
            case RANGED -> { cooldown = 1f; telegraph = .92f; recovery = .95f; }
            case ELITE -> { cooldown = .82f; telegraph = .78f; recovery = .80f; }
            case BOSS -> { cooldown = 1f; telegraph = 1f; recovery = 1f; }
            default -> { }
        }
        switch (variant) {
            case SWIFT -> { cooldown *= .78f; telegraph *= .80f; recovery *= .82f; }
            case ARMORED -> { cooldown *= 1.12f; telegraph *= 1.12f; recovery *= 1.08f; }
            case FERAL -> { cooldown *= .72f; telegraph *= .74f; recovery *= .76f; }
            default -> { }
        }
        attack.setCadence(cooldown, telegraph, recovery);
    }

    @Override public void damage(float amount) {
        boolean wasAlive = alive;
        super.damage(amount);
        if (wasAlive && !alive && type == Type.BOSS) RunMissionRuntime.signalBossDefeated();
    }

    public void applyElement(DamageElement element, float power) {
        switch (element) {
            case FIRE -> { burnTimer = Math.max(burnTimer, 2.4f); burnDps = Math.max(burnDps, power * 0.22f); }
            case FROST -> { slowTimer = Math.max(slowTimer, 1.6f); slowMultiplier = Math.min(slowMultiplier, 0.62f); }
            case SHOCK -> {
                shockTimer = Math.max(shockTimer, 0.35f);
                attack.forceStunned(0.35f);
            }
            default -> { }
        }
    }

    public void addImpulse(float x, float y) {
        float resistance = variant == Variant.ARMORED ? .34f : 1f;
        impulse.add(x * resistance, y * resistance);
    }

    public void updateStatus(float dt) {
        if (!alive) {
            attack.markDead();
            return;
        }
        variantTime += Math.max(0f, dt);
        if (burnTimer > 0f) {
            burnTimer -= dt;
            damage(burnDps * dt);
        }
        if (slowTimer > 0f) slowTimer -= dt; else slowMultiplier = 1f;
        if (shockTimer > 0f) shockTimer -= dt;
        attack.updateStun(dt);
        hitFlash = Math.max(0f, hitFlash - dt * 6f);
        float damping = MathUtils.clamp(1f - dt * (variant == Variant.ARMORED ? 12f : 8f), 0f, 1f);
        impulse.scl(damping);
        if (bossPhases != null) {
            bossPhases.update(maxHp <= 0f ? 0f : hp / maxHp);
            bossCombat.update(dt, bossPhases.phase());
        }
    }

    public void updateAi(float dt, float distanceToPlayer) {
        if (!alive || attack.state() == EnemyState.STUNNED) return;
        attack.update(dt, distanceToPlayer);
    }

    public float effectiveSpeed() {
        if (attack.state() == EnemyState.STUNNED) return 0f;
        float phaseMultiplier = bossPhases == null ? 1f : bossPhases.speedMultiplier();
        float chargeMultiplier = bossCombat != null && bossCombat.charging() ? 3.4f : 1f;
        float variantMultiplier = 1f;
        if (variant == Variant.SWIFT) {
            float cycle = variantTime % 3.2f;
            if (cycle < .48f) variantMultiplier = 1.28f;
        } else if (variant == Variant.FERAL && maxHp > 0f && hp / maxHp < .42f) {
            variantMultiplier = 1.24f;
        }
        return speed * slowMultiplier * phaseMultiplier * chargeMultiplier * variantMultiplier;
    }
}
