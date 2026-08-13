package com.deadlinezero.game.visual;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;

/**
 * Production-art proportions and anchors. Values are world-space presentation data only;
 * gameplay collision radii remain authoritative and independent from sprite dimensions.
 */
public final class ArtProfileCatalog {
    public record CharacterProfile(float height, float footOffset, float weaponAnchorX, float weaponAnchorY) {}

    private static final CharacterProfile REX = new CharacterProfile(1.72f, .60f, .29f, .23f);
    private static final CharacterProfile NYX = new CharacterProfile(1.70f, .59f, .31f, .25f);
    private static final CharacterProfile BASTION = new CharacterProfile(1.86f, .65f, .34f, .27f);
    private static final CharacterProfile VOLT = new CharacterProfile(1.73f, .60f, .30f, .24f);
    private static final CharacterProfile WRAITH = new CharacterProfile(1.68f, .58f, .30f, .23f);

    private static final CharacterProfile SHAMBLER = new CharacterProfile(1.34f, .38f, 0f, 0f);
    private static final CharacterProfile RUNNER = new CharacterProfile(1.26f, .36f, 0f, 0f);
    private static final CharacterProfile BRUTE = new CharacterProfile(2.08f, .62f, 0f, 0f);
    private static final CharacterProfile RANGED = new CharacterProfile(1.46f, .43f, 0f, 0f);
    private static final CharacterProfile ELITE = new CharacterProfile(2.42f, .72f, 0f, 0f);
    private static final CharacterProfile BOSS = new CharacterProfile(5.15f, 1.35f, 0f, 0f);

    private ArtProfileCatalog() {}

    public static CharacterProfile survivor(SurvivorCatalog.Survivor survivor) {
        return switch (survivor) {
            case NYX -> NYX;
            case BASTION -> BASTION;
            case VOLT -> VOLT;
            case WRAITH -> WRAITH;
            default -> REX;
        };
    }

    public static CharacterProfile enemy(Enemy.Type type) {
        return switch (type) {
            case RUNNER -> RUNNER;
            case BRUTE -> BRUTE;
            case RANGED -> RANGED;
            case ELITE -> ELITE;
            case BOSS -> BOSS;
            default -> SHAMBLER;
        };
    }
}
