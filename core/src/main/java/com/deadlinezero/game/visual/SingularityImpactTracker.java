package com.deadlinezero.game.visual;

import java.util.IdentityHashMap;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Projectile;

/** Tracks pooled Singularity Shot lifecycles and emits short-lived visual collapse impacts. */
public final class SingularityImpactTracker {
    public static final float IMPACT_LIFETIME = .42f;
    public static final int MAX_IMPACTS = 12;

    public static final class Impact {
        public float x;
        public float y;
        public float life;
        public float maxLife;

        public float progress() {
            return 1f - Math.max(0f, Math.min(1f, life / Math.max(.001f, maxLife)));
        }
    }

    private static final class State {
        long generation = -1L;
        boolean singularity;
        boolean active;
        float x;
        float y;
    }

    private final IdentityHashMap<Projectile, State> states = new IdentityHashMap<>();
    private final Array<Impact> impacts = new Array<>(false, MAX_IMPACTS);
    private int triggeredSinceConsume;

    public void update(Array<Projectile> projectiles, float dt) {
        float safeDt = Math.max(0f, dt);
        for (int i = impacts.size - 1; i >= 0; i--) {
            Impact impact = impacts.get(i);
            impact.life -= safeDt;
            if (impact.life <= 0f) impacts.removeIndex(i);
        }
        if (projectiles == null) return;

        for (Projectile projectile : projectiles) {
            State state = states.get(projectile);
            if (state == null) {
                state = new State();
                states.put(projectile, state);
            }

            if (state.generation != projectile.generation) {
                if (state.active && state.singularity) trigger(state.x, state.y);
                state.generation = projectile.generation;
                state.singularity = projectile.singularity;
                state.active = projectile.active;
                state.x = projectile.position.x;
                state.y = projectile.position.y;
                if (projectile.singularity && !projectile.active && projectile.generation > 0L) {
                    trigger(projectile.position.x, projectile.position.y);
                    state.singularity = false;
                }
                continue;
            }

            if (projectile.active && projectile.singularity) {
                state.singularity = true;
                state.active = true;
                state.x = projectile.position.x;
                state.y = projectile.position.y;
            } else if (state.active && state.singularity) {
                trigger(state.x, state.y);
                state.active = false;
                state.singularity = false;
            } else {
                state.active = projectile.active;
            }
        }
    }

    private void trigger(float x, float y) {
        if (impacts.size >= MAX_IMPACTS) impacts.removeIndex(0);
        Impact impact = new Impact();
        impact.x = x;
        impact.y = y;
        impact.life = IMPACT_LIFETIME;
        impact.maxLife = IMPACT_LIFETIME;
        impacts.add(impact);
        triggeredSinceConsume++;
    }

    public Array<Impact> impacts() { return impacts; }

    public int consumeTriggeredCount() {
        int count = triggeredSinceConsume;
        triggeredSinceConsume = 0;
        return count;
    }
}
