package com.deadlinezero.game.ai;

/** Runtime state used by enemy AI and boss phase controllers. */
public enum EnemyState {
    SPAWNING,
    CHASING,
    HOLDING_RANGE,
    TELEGRAPHING,
    ATTACKING,
    RECOVERING,
    STUNNED,
    DEAD
}
