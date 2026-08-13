package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** Centralized micro-feedback controller: hit-stop, recoil impulse, and transient camera kick. */
public final class CombatFeel {
    private float hitStop;
    private float recoil;
    private final Vector2 recoilDir = new Vector2();

    public void triggerHitStop(float seconds) {
        hitStop = Math.max(hitStop, MathUtils.clamp(seconds, 0f, .08f));
    }

    public void triggerRecoil(float angleDeg, float amount) {
        recoil = Math.max(recoil, MathUtils.clamp(amount, 0f, .45f));
        recoilDir.set(1f, 0f).setAngleDeg(angleDeg + 180f);
    }

    public float consumeSimulationScale(float dt) {
        if (hitStop <= 0f) return 1f;
        hitStop = Math.max(0f, hitStop - Math.max(0f, dt));
        return 0f;
    }

    public void update(float dt) {
        recoil = Math.max(0f, recoil - Math.max(0f, dt) * 3.8f);
    }

    public float recoilX() { return recoilDir.x * recoil; }
    public float recoilY() { return recoilDir.y * recoil; }
    public float recoilAmount() { return recoil; }
}
