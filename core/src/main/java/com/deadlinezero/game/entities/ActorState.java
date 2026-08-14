package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.meta.BalanceTelemetryRuntime;

public abstract class ActorState {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float radius;
    public float hp;
    public float maxHp;
    public boolean alive = true;
    protected ActorState(float x, float y, float radius, float hp) {
        position.set(x, y); this.radius = radius; this.hp = hp; this.maxHp = hp;
    }
    public void damage(float amount) {
        if (!alive || amount <= 0f || !Float.isFinite(amount)) return;
        float before = hp;
        hp -= amount;
        if (hp <= 0f) { hp = 0f; alive = false; }
        float effective = Math.max(0f, before - hp);
        if (this instanceof Enemy) BalanceTelemetryRuntime.recordDamageDealt(effective);
        else if (this instanceof Player) BalanceTelemetryRuntime.recordDamageReceived(effective);
    }
}
