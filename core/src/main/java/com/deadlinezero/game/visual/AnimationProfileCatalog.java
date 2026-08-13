package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;

/** Production animation timing contract kept independent from gameplay simulation. */
public final class AnimationProfileCatalog {
    public record Profile(float idle, float run, float attack, float hit, float death) {
        public float duration(GameArt.Motion motion) {
            return switch (motion) {
                case IDLE -> idle;
                case RUN -> run;
                case ATTACK -> attack;
                case HIT -> hit;
                case DEATH -> death;
            };
        }
    }

    private static final Profile REX = new Profile(.13f, .082f, .060f, .055f, .105f);
    private static final Profile NYX = new Profile(.13f, .074f, .052f, .050f, .098f);
    private static final Profile BASTION = new Profile(.15f, .105f, .078f, .065f, .120f);
    private static final Profile VOLT = new Profile(.12f, .080f, .060f, .052f, .102f);
    private static final Profile WRAITH = new Profile(.11f, .066f, .050f, .046f, .092f);

    private static final Profile SHAMBLER = new Profile(.15f, .110f, .095f, .060f, .125f);
    private static final Profile RUNNER = new Profile(.11f, .070f, .070f, .050f, .095f);
    private static final Profile BRUTE = new Profile(.17f, .125f, .115f, .072f, .145f);
    private static final Profile RANGED = new Profile(.14f, .095f, .085f, .058f, .115f);
    private static final Profile ELITE = new Profile(.13f, .088f, .080f, .055f, .110f);
    private static final Profile BOSS = new Profile(.18f, .120f, .105f, .070f, .155f);

    private AnimationProfileCatalog() {}

    public static Profile survivor(SurvivorCatalog.Survivor survivor) {
        return switch (survivor) {
            case REX -> REX;
            case NYX -> NYX;
            case BASTION -> BASTION;
            case VOLT -> VOLT;
            case WRAITH -> WRAITH;
        };
    }

    public static Profile enemy(Enemy.Type type) {
        return switch (type) {
            case RUNNER -> RUNNER;
            case BRUTE -> BRUTE;
            case RANGED -> RANGED;
            case ELITE -> ELITE;
            case BOSS -> BOSS;
            default -> SHAMBLER;
        };
    }

    public static boolean loops(GameArt.Motion motion) {
        return motion == GameArt.Motion.IDLE || motion == GameArt.Motion.RUN || motion == GameArt.Motion.ATTACK;
    }
}
