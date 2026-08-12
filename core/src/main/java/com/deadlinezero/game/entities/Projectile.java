package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;

public final class Projectile {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float damage;
    public float life;
    public float radius = 0.11f;
    public boolean active;
    public boolean critical;
    public Projectile spawn(float x, float y, float vx, float vy, float damage, boolean critical) {
        position.set(x, y); velocity.set(vx, vy); this.damage = damage; this.life = 1.5f; this.active = true; this.critical = critical; return this;
    }
}
