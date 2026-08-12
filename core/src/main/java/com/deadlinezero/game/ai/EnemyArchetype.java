package com.deadlinezero.game.ai;

/** Immutable behavior tuning shared by enemies of the same combat role. */
public final class EnemyArchetype {
    public enum Role { MELEE, RANGED, BOSS }

    public final Role role;
    public final float preferredRange;
    public final float attackRange;
    public final float attackCooldown;
    public final float telegraphDuration;
    public final float recoveryDuration;

    public EnemyArchetype(Role role, float preferredRange, float attackRange,
                          float attackCooldown, float telegraphDuration, float recoveryDuration) {
        this.role = role;
        this.preferredRange = preferredRange;
        this.attackRange = attackRange;
        this.attackCooldown = attackCooldown;
        this.telegraphDuration = telegraphDuration;
        this.recoveryDuration = recoveryDuration;
    }

    public static final EnemyArchetype MELEE = new EnemyArchetype(Role.MELEE, 0f, 0.9f, 0.8f, 0.18f, 0.22f);
    public static final EnemyArchetype RANGED = new EnemyArchetype(Role.RANGED, 7.5f, 9.5f, 1.8f, 0.65f, 0.45f);
    public static final EnemyArchetype BOSS = new EnemyArchetype(Role.BOSS, 5.5f, 11f, 2.6f, 0.9f, 0.7f);
}
