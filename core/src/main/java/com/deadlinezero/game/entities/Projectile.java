package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.combat.DamageElement;

public final class Projectile {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float damage;
    public float life;
    public float radius = 0.11f;
    public float knockback;
    public int penetrationRemaining;
    public boolean active;
    public boolean critical;
    public DamageElement element = DamageElement.KINETIC;
    public Enemy lastHit;

    public Projectile spawn(float x, float y, float vx, float vy, float damage, boolean critical,
                            int penetration, float knockback, DamageElement element) {
        position.set(x, y);
        velocity.set(vx, vy);
        this.damage = damage;
        this.life = 1.5f;
        this.active = true;
        this.critical = critical;
        this.penetrationRemaining = penetration;
        this.knockback = knockback;
        this.element = element;
        this.lastHit = null;
        return this;
    }
}
