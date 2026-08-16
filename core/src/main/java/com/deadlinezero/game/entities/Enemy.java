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
import com.deadlinezero.game.world.BiomeEnemyBehaviorRules;
import com.deadlinezero.game.world.BiomeEnemyRoster;

public final class Enemy extends ActorState {
    public enum Type { SHAMBLER, RUNNER, BRUTE, RANGED, ELITE, SHIELDED, REGENERATOR, PHANTOM, BOSS }
    public enum Variant { NORMAL, SWIFT, ARMORED, FERAL }
    public enum Tactic { NONE, STRAFE, CHARGE }
    public enum ElementReaction { NONE, THERMAL_SHOCK, STEAM_BURST, OVERLOAD }

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
    public float reactionFlash;
    public ElementReaction lastReaction = ElementReaction.NONE;
    public float variantTime;
    public float tacticalCooldown;
    public float tacticalWindup;
    public float shieldHp;
    public float shieldMaxHp;
    public final Vector2 impulse = new Vector2();
    public final AttackController attack;
    public final BossPhaseController bossPhases;
    public final BossCombatRuntime bossCombat;
    private Tactic pendingTactic = Tactic.NONE;
    private float tacticSide = 1f;
    private float chargeImpactWindow;
    private boolean chargeImpactConsumed;
    private float specialRecoveryDelay;

    public Enemy(Type type, float x, float y, float hp, float speed, float radius, float damage, int xp) {
        super(x, y, radius, hp * StageRules.enemyHpMultiplier(RunStageContext.stage()));
        int stage = RunStageContext.stage();
        this.type = type;
        this.speed = speed * StageRules.enemySpeedMultiplier(stage);
        this.contactDamage = damage * StageRules.enemyDamageMultiplier(stage);
        this.xpValue = Math.max(1, Math.round(xp * (1f + (stage - 1) * .035f)));
        applyTypeProfile();
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
        configureSpecialTrait();
    }

    private void applyTypeProfile() {
        switch (type) {
            case SHIELDED -> {
                maxHp *= 1.72f;
                hp = maxHp;
                speed *= .78f;
                radius *= 1.12f;
                xpValue = Math.max(1, Math.round(xpValue * 1.65f));
            }
            case REGENERATOR -> {
                maxHp *= 1.34f;
                hp = maxHp;
                speed *= .92f;
                xpValue = Math.max(1, Math.round(xpValue * 1.48f));
            }
            case PHANTOM -> {
                maxHp *= .82f;
                hp = maxHp;
                speed *= 1.24f;
                radius *= .90f;
                contactDamage *= 1.12f;
                xpValue = Math.max(1, Math.round(xpValue * 1.58f));
            }
            default -> { }
        }
    }

    private void configureSpecialTrait() {
        if (type == Type.SHIELDED) {
            shieldMaxHp = maxHp * .55f;
            shieldHp = shieldMaxHp;
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
            case RUNNER, PHANTOM -> { cooldown = .84f; telegraph = .72f; recovery = .82f; }
            case BRUTE, SHIELDED -> { cooldown = 1.12f; telegraph = 1.34f; recovery = 1.18f; }
            case RANGED -> { cooldown = 1f; telegraph = .92f; recovery = .95f; }
            case ELITE -> { cooldown = .82f; telegraph = .78f; recovery = .80f; }
            case REGENERATOR -> { cooldown = .94f; telegraph = .95f; recovery = .94f; }
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
        if (!alive || amount <= 0f || !Float.isFinite(amount)) return;
        specialRecoveryDelay = type == Type.SHIELDED ? 3.5f : (type == Type.REGENERATOR ? 2.6f : specialRecoveryDelay);
        float remaining = amount;
        if (type == Type.SHIELDED && shieldHp > 0f) {
            float absorbed = Math.min(shieldHp, remaining);
            shieldHp -= absorbed;
            remaining -= absorbed;
            hitFlash = Math.max(hitFlash, .45f);
        }
        if (type == Type.PHANTOM && phased()) remaining *= .22f;
        if (remaining <= 0f) return;
        boolean wasAlive = alive;
        super.damage(remaining);
        if (wasAlive && !alive && type == Type.BOSS) RunMissionRuntime.signalBossDefeated();
    }

    /**
     * Applies elemental status and resolves deterministic cross-element reactions.
     * Biome signature enemies attenuate their resisted element here, once, before status/reaction math.
     */
    public void applyElement(DamageElement element, float power) {
        if (!alive || element == null) return;
        lastReaction = ElementReaction.NONE;
        float resistance = BiomeEnemyRoster.elementalDamageMultiplier(RunStageContext.stage(), type, element);
        float safePower = Math.max(0f, power) * resistance;

        switch (element) {
            case FIRE -> {
                if (slowTimer > .05f) {
                    damage(safePower * .34f);
                    slowTimer = 0f;
                    slowMultiplier = 1f;
                    triggerReaction(ElementReaction.THERMAL_SHOCK);
                }
                burnTimer = Math.max(burnTimer, 2.4f * resistance);
                burnDps = Math.max(burnDps, safePower * .22f);
            }
            case FROST -> {
                if (burnTimer > .05f) {
                    damage(safePower * .28f);
                    burnTimer = 0f;
                    burnDps = 0f;
                    triggerReaction(ElementReaction.STEAM_BURST);
                }
                slowTimer = Math.max(slowTimer, 1.6f * resistance);
                slowMultiplier = Math.min(slowMultiplier, MathUtils.lerp(1f, .62f, resistance));
            }
            case SHOCK -> {
                boolean burning = burnTimer > .05f;
                boolean frozen = slowTimer > .05f;
                float stun = .35f * resistance;
                if (burning || frozen) {
                    damage(safePower * (burning && frozen ? .34f : .22f));
                    stun = .55f * resistance;
                    triggerReaction(ElementReaction.OVERLOAD);
                }
                shockTimer = Math.max(shockTimer, stun);
                attack.forceStunned(stun);
            }
            default -> { }
        }
    }

    private void triggerReaction(ElementReaction reaction) {
        lastReaction = reaction;
        reactionFlash = .24f;
        hitFlash = Math.max(hitFlash, .72f);
    }

    public void addImpulse(float x, float y) {
        float resistance = variant == Variant.ARMORED || type == Type.SHIELDED ? .34f : 1f;
        impulse.add(x * resistance, y * resistance);
    }

    public void updateStatus(float dt) {
        if (!alive) {
            attack.markDead();
            return;
        }
        float safeDt = Math.max(0f, dt);
        variantTime += safeDt;
        specialRecoveryDelay = Math.max(0f, specialRecoveryDelay - safeDt);
        BiomeEnemyBehaviorRules.Profile behavior = biomeBehavior();
        if (type == Type.SHIELDED && specialRecoveryDelay <= 0f && shieldHp < shieldMaxHp) {
            shieldHp = Math.min(shieldMaxHp, shieldHp + shieldMaxHp * .18f * safeDt);
        } else if (type == Type.REGENERATOR && specialRecoveryDelay <= 0f && hp < maxHp) {
            hp = Math.min(maxHp, hp + maxHp * .035f * behavior.recoveryMultiplier() * safeDt);
        }
        tacticalCooldown = Math.max(0f, tacticalCooldown - dt);
        tacticalWindup = Math.max(0f, tacticalWindup - dt);
        chargeImpactWindow = Math.max(0f, chargeImpactWindow - dt);
        reactionFlash = Math.max(0f, reactionFlash - dt);
        if (reactionFlash <= 0f) lastReaction = ElementReaction.NONE;
        if (burnTimer > 0f) {
            burnTimer -= dt;
            damage(burnDps * dt);
        }
        if (slowTimer > 0f) slowTimer -= dt; else slowMultiplier = 1f;
        if (shockTimer > 0f) shockTimer -= dt;
        attack.updateStun(dt);
        hitFlash = Math.max(0f, hitFlash - dt * 6f);
        float damping = MathUtils.clamp(1f - dt * (variant == Variant.ARMORED || type == Type.SHIELDED ? 12f : 8f), 0f, 1f);
        impulse.scl(damping);
        if (bossPhases != null) {
            bossPhases.update(maxHp <= 0f ? 0f : hp / maxHp);
            bossCombat.update(dt, bossPhases.phase());
        }
    }

    public void updateAi(float dt, float distanceToPlayer) {
        if (!alive || attack.state() == EnemyState.STUNNED) return;
        attack.update(dt, distanceToPlayer);
        updateTactics(distanceToPlayer);
    }

    private void updateTactics(float distanceToPlayer) {
        if (pendingTactic != Tactic.NONE && tacticalWindup <= 0f) {
            executePendingTactic();
            pendingTactic = Tactic.NONE;
            return;
        }
        if (pendingTactic != Tactic.NONE || tacticalCooldown > 0f) return;

        BiomeEnemyBehaviorRules.Profile behavior = biomeBehavior();
        boolean canStrafe = type == Type.RANGED || behavior.evasiveStrafe();
        boolean tacticalState = attack.state() == EnemyState.CHASING
            || (canStrafe && attack.state() == EnemyState.HOLDING_RANGE);
        if (!tacticalState) return;

        float cadence = switch (variant) {
            case SWIFT -> .78f;
            case FERAL -> .82f;
            case ARMORED -> 1.12f;
            default -> 1f;
        };
        cadence *= behavior.tacticCooldownMultiplier();

        if (canStrafe && distanceToPlayer >= 3.2f && distanceToPlayer <= 10.5f) {
            pendingTactic = Tactic.STRAFE;
            tacticalWindup = type == Type.PHANTOM ? .10f : .16f;
            tacticalCooldown = (1.45f + MathUtils.random(.35f)) * cadence;
            tacticSide = MathUtils.randomBoolean() ? 1f : -1f;
        } else if ((type == Type.BRUTE || type == Type.ELITE || type == Type.SHIELDED || behavior.aggressiveCharge())
            && distanceToPlayer >= 2.2f && distanceToPlayer <= 8.4f) {
            pendingTactic = Tactic.CHARGE;
            tacticalWindup = type == Type.ELITE ? .24f : (type == Type.RUNNER ? .18f : .34f);
            tacticalCooldown = (type == Type.ELITE ? 2.35f : 3.05f) * cadence;
        }
    }

    private void executePendingTactic() {
        float len = velocity.len();
        if (len < .05f) return;
        float nx = velocity.x / len;
        float ny = velocity.y / len;
        BiomeEnemyBehaviorRules.Profile behavior = biomeBehavior();
        if (pendingTactic == Tactic.STRAFE) {
            float strength = (variant == Variant.SWIFT ? 2.15f : 1.65f) * behavior.strafeStrengthMultiplier();
            impulse.add(-ny * strength * tacticSide, nx * strength * tacticSide);
        } else if (pendingTactic == Tactic.CHARGE) {
            float strength = type == Type.ELITE ? 3.6f : (type == Type.SHIELDED ? 2.35f : 2.85f);
            if (variant == Variant.FERAL) strength *= 1.18f;
            strength *= behavior.chargeStrengthMultiplier();
            impulse.add(nx * strength, ny * strength);
            chargeImpactWindow = .26f;
            chargeImpactConsumed = false;
        }
    }

    public Tactic pendingTactic() { return pendingTactic; }
    public boolean tacticalTelegraph() { return pendingTactic != Tactic.NONE && tacticalWindup > 0f; }
    public boolean chargeImpactActive() { return chargeImpactWindow > 0f && !chargeImpactConsumed; }
    public boolean consumeChargeImpact() {
        if (!chargeImpactActive()) return false;
        chargeImpactConsumed = true;
        return true;
    }

    public boolean phased() {
        if (type != Type.PHANTOM) return false;
        float cycle = variantTime % 4.4f;
        return cycle >= 3.55f && cycle < 4.22f;
    }

    public float shieldFraction() {
        return shieldMaxHp <= 0f ? 0f : MathUtils.clamp(shieldHp / shieldMaxHp, 0f, 1f);
    }

    public BiomeEnemyRoster.Identity biomeIdentity() {
        return BiomeEnemyRoster.identityFor(RunStageContext.stage(), type);
    }

    public BiomeEnemyBehaviorRules.Profile biomeBehavior() {
        return BiomeEnemyBehaviorRules.forIdentity(biomeIdentity());
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
        if (phased()) variantMultiplier *= 1.36f;
        BiomeEnemyBehaviorRules.Profile behavior = biomeBehavior();
        float burst = 1f;
        if (behavior.burstMultiplier() > 1f && variantTime % 3.6f < .42f) burst = behavior.burstMultiplier();
        return speed * slowMultiplier * phaseMultiplier * chargeMultiplier * variantMultiplier
            * behavior.speedMultiplier() * burst;
    }
}
