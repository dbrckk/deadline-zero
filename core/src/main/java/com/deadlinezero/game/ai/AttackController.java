package com.deadlinezero.game.ai;

/** Allocation-free attack timing state machine. Rendering/gameplay consume the state transitions. */
public final class AttackController {
    private final EnemyArchetype archetype;
    private EnemyState state = EnemyState.CHASING;
    private float timer;

    public AttackController(EnemyArchetype archetype) {
        this.archetype = archetype;
        this.timer = archetype.attackCooldown;
    }

    public EnemyState state() { return state; }
    public EnemyArchetype archetype() { return archetype; }
    public float timer() { return timer; }

    public void update(float dt, float distance) {
        timer -= dt;
        switch (state) {
            case CHASING, HOLDING_RANGE -> {
                boolean inRange = distance <= archetype.attackRange;
                state = archetype.role == EnemyArchetype.Role.RANGED && distance <= archetype.preferredRange
                    ? EnemyState.HOLDING_RANGE : EnemyState.CHASING;
                if (inRange && timer <= 0f) {
                    state = EnemyState.TELEGRAPHING;
                    timer = archetype.telegraphDuration;
                }
            }
            case TELEGRAPHING -> {
                if (timer <= 0f) {
                    state = EnemyState.ATTACKING;
                    timer = 0f;
                }
            }
            case ATTACKING -> {
                state = EnemyState.RECOVERING;
                timer = archetype.recoveryDuration;
            }
            case RECOVERING -> {
                if (timer <= 0f) {
                    state = EnemyState.CHASING;
                    timer = archetype.attackCooldown;
                }
            }
            case STUNNED, SPAWNING, DEAD -> { }
        }
    }

    public boolean consumeAttack() {
        if (state != EnemyState.ATTACKING) return false;
        state = EnemyState.RECOVERING;
        timer = archetype.recoveryDuration;
        return true;
    }

    public void forceStunned(float duration) {
        state = EnemyState.STUNNED;
        timer = Math.max(timer, duration);
    }

    public void updateStun(float dt) {
        if (state != EnemyState.STUNNED) return;
        timer -= dt;
        if (timer <= 0f) {
            state = EnemyState.CHASING;
            timer = archetype.attackCooldown * 0.5f;
        }
    }

    public void markDead() { state = EnemyState.DEAD; }
}
