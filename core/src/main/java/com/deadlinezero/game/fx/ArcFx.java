package com.deadlinezero.game.fx;

/** Short-lived pooled electric arc used for Tesla/SHOCK presentation. */
public final class ArcFx {
    public float x1, y1, x2, y2;
    public float life, maxLife;
    public boolean active;

    public ArcFx spawn(float x1, float y1, float x2, float y2, float duration) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.maxLife = Math.max(.04f, duration);
        this.life = this.maxLife;
        this.active = true;
        return this;
    }

    public void update(float dt) {
        if (!active) return;
        life -= dt;
        if (life <= 0f) active = false;
    }
}
