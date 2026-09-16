package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;

/** Non-color semantic presentation contract for zombie roles. */
public final class HordeRolePresentation {
    public enum Motion { STEADY, FAST, HEAVY, SUPPORT, PHASE }
    public enum Telegraph { NONE, PREFIRE, SUPPORT, GUARDED, PHASED }
    public enum Silhouette { MASS, RUNNER, BRUTE, RANGED, ELITE, SHIELDED, REGENERATOR, PHANTOM, BOSS }

    public record RoleStyle(
        float shadowScale,
        float rimStrength,
        float markerScale,
        Motion motion,
        Telegraph telegraph,
        Silhouette silhouetteClass
    ) {}

    private static final RoleStyle SHAMBLER = new RoleStyle(1.00f, .10f, .70f, Motion.STEADY, Telegraph.NONE, Silhouette.MASS);
    private static final RoleStyle RUNNER = new RoleStyle(.82f, .18f, .62f, Motion.FAST, Telegraph.NONE, Silhouette.RUNNER);
    private static final RoleStyle BRUTE = new RoleStyle(1.52f, .30f, 1.10f, Motion.HEAVY, Telegraph.NONE, Silhouette.BRUTE);
    private static final RoleStyle RANGED = new RoleStyle(.92f, .24f, .82f, Motion.STEADY, Telegraph.PREFIRE, Silhouette.RANGED);
    private static final RoleStyle ELITE = new RoleStyle(1.30f, .42f, 1.05f, Motion.HEAVY, Telegraph.GUARDED, Silhouette.ELITE);
    private static final RoleStyle SHIELDED = new RoleStyle(1.42f, .38f, 1.08f, Motion.HEAVY, Telegraph.GUARDED, Silhouette.SHIELDED);
    private static final RoleStyle REGENERATOR = new RoleStyle(1.06f, .30f, .92f, Motion.SUPPORT, Telegraph.SUPPORT, Silhouette.REGENERATOR);
    private static final RoleStyle PHANTOM = new RoleStyle(.78f, .32f, .80f, Motion.PHASE, Telegraph.NONE, Silhouette.PHANTOM);
    private static final RoleStyle BOSS = new RoleStyle(1.76f, .58f, 1.42f, Motion.HEAVY, Telegraph.PHASED, Silhouette.BOSS);

    private HordeRolePresentation() {}

    public static RoleStyle style(Enemy.Type type) {
        if (type == null) return SHAMBLER;
        return switch (type) {
            case RUNNER -> RUNNER;
            case BRUTE -> BRUTE;
            case RANGED -> RANGED;
            case ELITE -> ELITE;
            case SHIELDED -> SHIELDED;
            case REGENERATOR -> REGENERATOR;
            case PHANTOM -> PHANTOM;
            case BOSS -> BOSS;
            default -> SHAMBLER;
        };
    }
}
