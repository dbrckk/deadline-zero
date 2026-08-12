package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;

/** Pooled hostile projectile used by ranged enemies and boss patterns. */
public final class EnemyProjectile {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float damage;
    public float radius;
    public float life;
    public boolean active;
    public boolean explosive;
    public float explosionRadius;

    public EnemyProjectile spawn(float x, float y, float vx, float vy, float damage,
                                 float radius, float life, boolean explosive, float explosionRadius) {
        position.set(x, y);
        velocity.set(vx, vy);
        this.damage = damage;
        this.radius = radius;
        this.life = life;
        this.explosive = explosive;
        this.explosionRadius = explosionRadius;
        this.active = true;
        return this;
    }
}
