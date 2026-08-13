package com.deadlinezero.game.ai;

import java.util.WeakHashMap;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;

/** External LEAPER behavior runtime so specialized runners can leap without modifying Enemy.Type. */
public final class LeaperRuntime {
    private static final class State {
        float cooldown = MathUtils.random(LeaperProfile.LEAP_COOLDOWN_MIN, LeaperProfile.LEAP_COOLDOWN_MAX);
        float windup;
        float impactWindow;
        boolean impactConsumed;
    }

    private final WeakHashMap<Enemy, State> states = new WeakHashMap<>();

    public void register(Enemy enemy) {
        if (enemy != null) states.put(enemy, new State());
    }

    public boolean contains(Enemy enemy) {
        return enemy != null && states.containsKey(enemy);
    }

    public void update(Enemy enemy, float dt, float distanceToPlayer, float dirX, float dirY) {
        State state = states.get(enemy);
        if (state == null || enemy == null || !enemy.alive) return;
        state.cooldown = Math.max(0f, state.cooldown - dt);
        state.impactWindow = Math.max(0f, state.impactWindow - dt);
        if (state.windup > 0f) {
            state.windup = Math.max(0f, state.windup - dt);
            if (state.windup <= 0f) {
                enemy.addImpulse(dirX * LeaperProfile.LEAP_IMPULSE, dirY * LeaperProfile.LEAP_IMPULSE);
                state.impactWindow = LeaperProfile.LEAP_IMPACT_WINDOW;
                state.impactConsumed = false;
                state.cooldown = MathUtils.random(LeaperProfile.LEAP_COOLDOWN_MIN, LeaperProfile.LEAP_COOLDOWN_MAX);
            }
            return;
        }
        if (state.cooldown <= 0f && LeaperProfile.inLeapRange(distanceToPlayer)) {
            state.windup = LeaperProfile.LEAP_WINDUP;
        }
    }

    public boolean telegraphing(Enemy enemy) {
        State state = states.get(enemy);
        return state != null && state.windup > 0f;
    }

    public boolean consumeImpact(Enemy enemy) {
        State state = states.get(enemy);
        if (state == null || state.impactWindow <= 0f || state.impactConsumed) return false;
        state.impactConsumed = true;
        return true;
    }
}
