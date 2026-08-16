package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;

/** Pooled hostile projectile used by ranged enemies and boss patterns. */
public final class EnemyProjectile {
    public enum Style { DEFAULT, CINDER, STATIC, NULL }

    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float damage;
    public float radius;
    public float life;
    public boolean active;
    public boolean explosive;
    public float explosionRadius;
    public Style style = Style.DEFAULT;

    public EnemyProjectile spawn(float x, float y, float vx, float vy, float damage,
                                 float radius, float life, boolean explosive, float explosionRadius) {
        return spawn(x, y, vx, vy, damage, radius, life, explosive, explosionRadius, Style.DEFAULT);
    }

    public EnemyProjectile spawn(float x, float y, float vx, float vy, float damage,
                                 float radius, float life, boolean explosive, float explosionRadius,
                                 Style style) {
        position.set(x, y);
        velocity.set(vx, vy);
        this.damage = damage;
        this.radius = radius;
        this.life = life;
        this.explosive = explosive;
        this.explosionRadius = explosionRadius;
        this.style = style == null ? Style.DEFAULT : style;
        this.active = true;
        return this;
    }
}
