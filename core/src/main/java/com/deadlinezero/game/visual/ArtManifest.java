package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;

/** Central production-art contract and lightweight runtime atlas validator. */
public final class ArtManifest {
    private ArtManifest() {}

    public static final String[] REQUIRED_STATIC = {
        "environment/decal/crack_a",
        "environment/decal/blood_a",
        "environment/prop/barrier_a"
    };

    /** Atlas-driven effects already consumed by AuthoredVfxRenderer. */
    public static final String[] REQUIRED_FX = {
        "fx/muzzle_fire",
        "fx/dash",
        "fx/level_up",
        "fx/impact_energy",
        "fx/impact_fire",
        "fx/impact_frost",
        "fx/impact_shock",
        "fx/impact_kill",
        "fx/boss_explosion"
    };

    public static int validate(GameArt art) {
        if (!art.authoredAvailable()) return 0;
        int missing = 0;
        for (String key : REQUIRED_STATIC) missing += requireRegion(art, key);
        for (String key : REQUIRED_FX) missing += requireMotion(art, key);
        for (WeaponDefinition weapon : WeaponCatalog.all()) missing += requireRegion(art, "weapon/" + weapon.id);

        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String root = "survivor/" + survivor.name().toLowerCase();
            missing += requireMotion(art, root, "idle");
            missing += requireMotion(art, root, "run");
            missing += requireMotion(art, root, "attack");
            missing += requireMotion(art, root, "hit");
            missing += requireMotion(art, root, "death");
        }
        for (Enemy.Type type : Enemy.Type.values()) {
            String root = "enemy/" + type.name().toLowerCase();
            missing += requireMotion(art, root, "idle");
            missing += requireMotion(art, root, "run");
            missing += requireMotion(art, root, "attack");
            missing += requireMotion(art, root, "hit");
            missing += requireMotion(art, root, "death");
            missing += requireRegion(art, root + "/corpse");
        }
        if (missing == 0) Gdx.app.log("ArtManifest", "Production atlas validation passed.");
        else Gdx.app.log("ArtManifest", "Production atlas incomplete: " + missing + " required entries missing.");
        return missing;
    }

    private static int requireMotion(GameArt art, String root, String motion) {
        return requireMotion(art, root + "/" + motion);
    }

    private static int requireMotion(GameArt art, String key) {
        if (art.hasAnimation(key)) return 0;
        Gdx.app.log("ArtManifest", "Missing authored animation/region: " + key);
        return 1;
    }

    private static int requireRegion(GameArt art, String key) {
        if (art.hasRegion(key)) return 0;
        Gdx.app.log("ArtManifest", "Missing authored region: " + key);
        return 1;
    }
}
