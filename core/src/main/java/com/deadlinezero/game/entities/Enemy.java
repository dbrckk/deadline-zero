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

    public void addImpulse(float x, float y) { impulse.add(x, y); }

    public void updateStatus(float dt) {
        if (!alive) {
            attack.markDead();
            return;
        }
        if (burnTimer > 0f) {
            burnTimer -= dt;
            damage(burnDps * dt);
        }
        if (slowTimer > 0f) slowTimer -= dt; else slowMultiplier = 1f;
        if (shockTimer > 0f) shockTimer -= dt;
        attack.updateStun(dt);
        hitFlash = Math.max(0f, hitFlash - dt * 6f);
        float damping = MathUtils.clamp(1f - dt * 8f, 0f, 1f);
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
        return speed * slowMultiplier * phaseMultiplier * chargeMultiplier;
    }
}
