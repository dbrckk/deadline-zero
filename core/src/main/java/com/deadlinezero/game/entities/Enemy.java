package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.combat.DamageElement;

public final class Enemy extends ActorState {
    public enum Type { SHAMBLER, RUNNER, BRUTE, ELITE, BOSS }
    public Type type;
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

    public Enemy(Type type, float x, float y, float hp, float speed, float radius, float damage, int xp) {
        super(x, y, radius, hp);
        this.type = type;
        this.speed = speed;
        this.contactDamage = damage;
        this.xpValue = xp;
    }

    public void applyElement(DamageElement element, float power) {
        switch (element) {
            case FIRE -> { burnTimer = Math.max(burnTimer, 2.4f); burnDps = Math.max(burnDps, power * 0.22f); }
            case FROST -> { slowTimer = Math.max(slowTimer, 1.6f); slowMultiplier = Math.min(slowMultiplier, 0.62f); }
            case SHOCK -> shockTimer = Math.max(shockTimer, 0.35f);
            default -> { }
        }
    }

    public void addImpulse(float x, float y) { impulse.add(x, y); }

    public void updateStatus(float dt) {
        if (!alive) return;
        if (burnTimer > 0f) {
            burnTimer -= dt;
            damage(burnDps * dt);
        }
        if (slowTimer > 0f) slowTimer -= dt; else slowMultiplier = 1f;
        if (shockTimer > 0f) shockTimer -= dt;
        hitFlash = Math.max(0f, hitFlash - dt * 6f);
        float damping = MathUtils.clamp(1f - dt * 8f, 0f, 1f);
        impulse.scl(damping);
    }

    public float effectiveSpeed() { return shockTimer > 0f ? speed * 0.18f : speed * slowMultiplier; }
}
