package com.deadlinezero.game.fx;

import com.badlogic.gdx.graphics.Color;

/** Allocation-free floating combat text entry. */
public final class DamageNumber {
    public float x, y, value, life, maxLife;
    public boolean critical, active;
    public final Color color = new Color(Color.WHITE);

    public DamageNumber spawn(float x, float y, float value, boolean critical, Color color) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.critical = critical;
        this.maxLife = critical ? 0.72f : 0.52f;
        this.life = maxLife;
        this.color.set(color);
        this.active = true;
        return this;
    }

    public void update(float dt) {
        if (!active) return;
        life -= dt;
        y += dt * (critical ? 1.8f : 1.25f);
        if (life <= 0f) active = false;
    }
}
