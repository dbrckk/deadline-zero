package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.world.BiomeEnemyRoster;

/**
 * Strict release-art audit. Unlike GameArt.hasAnimation(), this class inspects only art/game.atlas
 * and deliberately ignores every generated/bootstrap fallback.
 */
public final class ProductionAtlasAudit {
    private static final String ATLAS_PATH = "art/game.atlas";

    private ProductionAtlasAudit() {}

    public static int validate() {
        if (!Gdx.files.internal(ATLAS_PATH).exists()) {
            Gdx.app.log("ProductionAtlasAudit", "Final production atlas is not installed: " + ATLAS_PATH);
            return 1;
        }

        TextureAtlas atlas = null;
        try {
            atlas = new TextureAtlas(Gdx.files.internal(ATLAS_PATH));
            int issues = 0;

            for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
                issues += auditDirectionalActor(atlas, "survivor/" + survivor.name().toLowerCase(), false);
            }
            for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
                if (identity == BiomeEnemyRoster.Identity.NONE) continue;
                issues += auditDirectionalActor(atlas, "enemy/biome/" + identity.name().toLowerCase(), false);
            }
            for (BossIdentity identity : BossIdentity.values()) {
                issues += auditDirectionalActor(atlas, "boss/" + identity.name().toLowerCase(), true);
            }

            if (issues == 0) Gdx.app.log("ProductionAtlasAudit", "Final directional animation audit passed.");
            else Gdx.app.log("ProductionAtlasAudit", "Final atlas has " + issues + " missing/under-framed animation groups.");
            return issues;
        } catch (RuntimeException exception) {
            Gdx.app.error("ProductionAtlasAudit", "Unable to audit final atlas.", exception);
            return 1;
        } finally {
            if (atlas != null) atlas.dispose();
        }
    }

    static int auditDirectionalActor(TextureAtlas atlas, String root, boolean boss) {
        int issues = 0;
        for (Direction8 direction : Direction8.values()) {
            for (GameArt.Motion motion : GameArt.Motion.values()) {
                String key = root + "/" + direction.atlasToken() + "/" + motion.name().toLowerCase();
                int actual = frameCount(atlas, key);
                int required = FinalArtContract.minimumFrames(motion, boss);
                if (actual < required) {
                    issues++;
                    Gdx.app.log("ProductionAtlasAudit", "Under-framed " + key + ": " + actual + "/" + required);
                }
            }
        }
        return issues;
    }

    static int frameCount(TextureAtlas atlas, String key) {
        if (atlas == null || key == null || key.isBlank()) return 0;
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(key);
        if (frames != null && frames.size > 0) return frames.size;
        return atlas.findRegion(key) == null ? 0 : 1;
    }
}
