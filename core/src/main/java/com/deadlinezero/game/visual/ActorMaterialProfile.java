package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;

/**
 * Pure presentation budget for actor silhouette reinforcement.
 *
 * Standard crowd enemies remain single-draw. Only player/high-priority enemies receive one
 * enlarged dark underlay so busy authored floors cannot swallow important silhouettes.
 */
public final class ActorMaterialProfile {
    public record Profile(boolean outline, float scale, float alpha) { }

    private static final Profile NONE = new Profile(false, 1f, 0f);
    private static final Profile PLAYER = new Profile(true, 1.078f, .80f);
    private static final Profile CHAMPION = new Profile(true, 1.068f, .74f);
    private static final Profile SPECIALIST = new Profile(true, 1.072f, .78f);
    private static final Profile ELITE = new Profile(true, 1.076f, .80f);
    private static final Profile BOSS = new Profile(true, 1.080f, .80f);

    private ActorMaterialProfile() { }

    public static Profile player() { return PLAYER; }

    public static Profile enemy(Enemy.Type type, Enemy.Variant variant) {
        if (type == null) return NONE;
        if (type == Enemy.Type.BOSS) return BOSS;
        if (type == Enemy.Type.ELITE) return ELITE;
        if (type == Enemy.Type.SHIELDED || type == Enemy.Type.REGENERATOR || type == Enemy.Type.PHANTOM) {
            return SPECIALIST;
        }
        if (variant != null && variant != Enemy.Variant.NORMAL) return CHAMPION;
        return NONE;
    }
}
