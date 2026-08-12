package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.combat.DamageElement;

/** Pooled player missile with allocation-free steering. */
public final class HomingMissile {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public Enemy target;
    public float speed;
    public float turnRateDeg;
    public float damage;
    public float explosionRadius;
    public float life;
    public float radius;
    public boolean active;
    public DamageElement element = DamageElement.KINETIC;

    public HomingMissile spawn(float x, float y, Enemy target, float speed, float turnRateDeg,
                               float damage, float explosionRadius, float life, DamageElement element) {
        position.set(x, y);
        this.target = target;
        this.speed = speed;
        this.turnRateDeg = turnRateDeg;
        this.damage = damage;
        this.explosionRadius = explosionRadius;
        this.life = life;
        this.element = element;
        this.radius = .16f;
        this.active = true;
        if (target != null) velocity.set(target.position).sub(position).nor().scl(speed);
        else velocity.set(speed, 0f);
        return this;
    }

    public void update(float dt) {
        if (!active) return;
        life -= dt;
        if (life <= 0f) { active = false; return; }
        if (target != null && target.alive) {
            float desired = MathUtils.atan2(target.position.y - position.y, target.position.x - position.x) * MathUtils.radiansToDegrees;
            float current = velocity.angleDeg();
            float delta = ((desired - current + 540f) % 360f) - 180f;
            float maxTurn = turnRateDeg * dt;
            current += MathUtils.clamp(delta, -maxTurn, maxTurn);
            velocity.set(speed, 0f).setAngleDeg(current);
        }
        position.mulAdd(velocity, dt);
    }
}
