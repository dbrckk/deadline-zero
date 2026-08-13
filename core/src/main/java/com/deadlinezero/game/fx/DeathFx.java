package com.deadlinezero.game.fx;

import com.deadlinezero.game.entities.Enemy;

/** Long-lived pooled death presentation generated when enemies die. */
public final class DeathFx {
    public boolean active;
    public Enemy.Type type = Enemy.Type.SHAMBLER;
    public float x, y;
    public float angleDeg;
    public float life;
    public float maxLife;
    public float radius;
    public float age;

    public void spawn(Enemy.Type type, float x, float y, float angleDeg, float radius, float duration) {
        this.type = type == null ? Enemy.Type.SHAMBLER : type;
        this.x = x;
        this.y = y;
        this.angleDeg = angleDeg;
        this.radius = radius;
        this.life = duration;
        this.maxLife = duration;
        this.age = 0f;
        this.active = true;
    }

    public void update(float dt) {
        if (!active) return;
        float step = Math.max(0f, dt);
        age += step;
        life -= step;
        if (life <= 0f) active = false;
    }
}
