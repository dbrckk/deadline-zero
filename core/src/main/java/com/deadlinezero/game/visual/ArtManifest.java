package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;

/** Central production-art contract and lightweight runtime atlas validator. */
public final class ArtManifest {
    private ArtManifest() {}

    public static final String[] REQUIRED_STATIC = {
        "weapon/ar9",
        "environment/decal/crack_a",
        "environment/decal/blood_a",
        "environment/prop/barrier_a"
    };

    public static int validate(GameArt art) {
        if (!art.authoredAvailable()) return 0;
        int missing = 0;
        for (String key : REQUIRED_STATIC) {
            if (!art.hasRegion(key)) {
                missing++;
                Gdx.app.log("ArtManifest", "Missing authored region: " + key);
            }
        }
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String root = "survivor/" + survivor.name().toLowerCase();
            missing += requireMotion(art, root, "idle");
            missing += requireMotion(art, root, "run");
            missing += requireMotion(art, root, "attack");
            missing += requireMotion(art, root, "hit");
        }
        for (Enemy.Type type : Enemy.Type.values()) {
            String root = "enemy/" + type.name().toLowerCase();
            missing += requireMotion(art, root, "idle");
            missing += requireMotion(art, root, "run");
            missing += requireMotion(art, root, "attack");
            missing += requireMotion(art, root, "hit");
        }
        if (missing == 0) Gdx.app.log("ArtManifest", "Production atlas validation passed.");
        else Gdx.app.log("ArtManifest", "Production atlas incomplete: " + missing + " required entries missing.");
        return missing;
    }

    private static int requireMotion(GameArt art, String root, String motion) {
        String key = root + "/" + motion;
        if (art.hasRegion(key)) return 0;
        Gdx.app.log("ArtManifest", "Missing authored animation/region: " + key);
        return 1;
    }
}
