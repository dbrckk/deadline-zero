package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Deterministic combat-floor renderer. It replaces the prototype hazard-wallpaper floor while
 * leaving authored set dressing and combatants in their existing passes.
 */
public final class CombatWorldRenderer implements Disposable {
    @FunctionalInterface
    interface RegionSource {
        TextureRegion region(String key);
    }

    private static final float TILE = 4f;
    private static final int MIN_GX = -10;
    private static final int MAX_GX = 9;
    private static final int MIN_GY = -6;
    private static final int MAX_GY = 5;

    private final RegionSource regions;
    private BootstrapEnvironmentArt bootstrap;

    public CombatWorldRenderer(GameArt art) {
        if (art == null) throw new IllegalArgumentException("art");
        regions = art::regionOrNull;
        try {
            bootstrap = BootstrapEnvironmentArt.create();
        } catch (RuntimeException ignored) {
            bootstrap = null;
        }
    }

    CombatWorldRenderer(RegionSource regions) {
        if (regions == null) throw new IllegalArgumentException("regions");
        this.regions = regions;
    }

    boolean hasUsableFloorSource() {
        return region("environment/floor/concrete_a") != null
            || region("environment/floor/concrete_b") != null
            || region("environment/floor/concrete_c") != null;
    }

    /** Returns false only when no usable authored/bootstrap floor texture exists. */
    public boolean drawFloor(SpriteBatch batch, int stage, long seed, float time) {
        TextureRegion concreteA = region("environment/floor/concrete_a");
        TextureRegion concreteB = region("environment/floor/concrete_b");
        TextureRegion concreteC = region("environment/floor/concrete_c");
        if (concreteA == null && concreteB == null && concreteC == null) return false;

        CombatWorldStyle.Profile profile = CombatWorldStyle.forStage(stage, seed);
        TextureRegion fallback = concreteA != null ? concreteA : concreteB != null ? concreteB : concreteC;
        TextureRegion hazard = region("environment/floor/hazard_a");
        TextureRegion crack = region("environment/decal/crack_a");
        TextureRegion blood = region("environment/decal/blood_a");
        TextureRegion scorch = region("environment/decal/scorch_a");

        batch.begin();
        drawBase(batch, profile, concreteA, concreteB, concreteC, fallback);
        drawLargeBreakup(batch, profile, fallback);
        drawSparseHazards(batch, profile, hazard);
        drawDecals(batch, profile, crack, blood, scorch);
        batch.setColor(Color.WHITE);
        batch.end();
        return true;
    }

    private TextureRegion region(String key) {
        TextureRegion region = regions.region(key);
        if (region != null) return region;
        return bootstrap == null ? null : bootstrap.region(key);
    }

    private void drawBase(SpriteBatch batch, CombatWorldStyle.Profile profile,
                          TextureRegion a, TextureRegion b, TextureRegion c, TextureRegion fallback) {
        for (int gy = MIN_GY; gy <= MAX_GY; gy++) {
            for (int gx = MIN_GX; gx <= MAX_GX; gx++) {
                int variant = Math.floorMod(gx * 31 + gy * 17 + (int) profile.seed(), 3);
                TextureRegion region = variant == 0 ? a : variant == 1 ? b : c;
                if (region == null) region = fallback;

                float shade = .82f + .035f * Math.floorMod(gx * 7 + gy * 11, 4);
                setBiomeTint(batch, profile.biome(), shade, 1f);
                batch.draw(region, gx * TILE, gy * TILE, TILE, TILE);
            }
        }
    }

    private void drawLargeBreakup(SpriteBatch batch, CombatWorldStyle.Profile profile, TextureRegion concrete) {
        for (int i = 0; i < profile.largeFeatureCount(); i++) {
            long h = hash(profile.seed(), i, 0x51A3L);
            float x = -31f + CombatWorldStyle.unit(h) * 62f;
            float y = -17f + CombatWorldStyle.unit(h >>> 7) * 34f;
            float w = 4f + CombatWorldStyle.unit(h >>> 13) * 7f;
            float hgt = 2.5f + CombatWorldStyle.unit(h >>> 21) * 4.5f;
            if (Math.abs(x) < 5f && Math.abs(y) < 4f) x += x < 0f ? -7f : 7f;

            batch.setColor(.055f, .065f, .068f, .26f);
            batch.draw(concrete, x - w * .5f, y - hgt * .5f, w, hgt);
        }
    }

    private void drawSparseHazards(SpriteBatch batch, CombatWorldStyle.Profile profile, TextureRegion hazard) {
        if (hazard == null) return;
        int tileBudget = Math.max(4, Math.min(12, Math.round(240f * profile.hazardCoverage())));
        for (int i = 0; i < tileBudget; i++) {
            long h = hash(profile.seed(), i, 0x77B5L);
            boolean horizontalEdge = (i & 1) == 0;
            float x;
            float y;
            if (horizontalEdge) {
                x = -28f + CombatWorldStyle.unit(h) * 56f;
                y = ((i & 2) == 0 ? -1f : 1f) * (14f + CombatWorldStyle.unit(h >>> 11) * 3f);
            } else {
                x = ((i & 2) == 0 ? -1f : 1f) * (26f + CombatWorldStyle.unit(h) * 4f);
                y = -12f + CombatWorldStyle.unit(h >>> 11) * 24f;
            }
            setHazardTint(batch, profile.biome());
            batch.draw(hazard, x - TILE * .5f, y - TILE * .5f, TILE, TILE);
        }
    }

    private void drawDecals(SpriteBatch batch, CombatWorldStyle.Profile profile,
                            TextureRegion crack, TextureRegion blood, TextureRegion scorch) {
        for (int i = 0; i < profile.decalCount(); i++) {
            long h = hash(profile.seed(), i, 0xC19DL);
            float x = -30f + CombatWorldStyle.unit(h) * 60f;
            float y = -16f + CombatWorldStyle.unit(h >>> 8) * 32f;
            if (Math.abs(x) < 5f && Math.abs(y) < 4f) continue;

            int kind = CombatWorldStyle.bounded(h >>> 18, 3);
            TextureRegion region = kind == 0 ? crack : kind == 1 ? blood : scorch;
            if (region == null) continue;
            float size = 1.05f + CombatWorldStyle.unit(h >>> 25) * 1.8f;
            setDecalTint(batch, profile.biome(), kind);
            batch.draw(region, x - size * .5f, y - size * .5f, size, size);
        }
    }

    private static long hash(long seed, int index, long salt) {
        return CombatWorldStyle.mix(seed ^ salt ^ ((long) index * 0x9E3779B97F4A7C15L));
    }

    private static void setBiomeTint(SpriteBatch batch, EnvironmentBiomeRules.Biome biome, float shade, float alpha) {
        switch (biome) {
            case CINDER_FOUNDRY -> batch.setColor(.34f * shade, .25f * shade, .22f * shade, alpha);
            case NULL_SECTOR -> batch.setColor(.24f * shade, .25f * shade, .36f * shade, alpha);
            case CRYO_VAULT -> batch.setColor(.25f * shade, .34f * shade, .40f * shade, alpha);
            case CRYOGENIC_DEPTHS -> batch.setColor(.19f * shade, .29f * shade, .34f * shade, alpha);
            default -> batch.setColor(.29f * shade, .31f * shade, .32f * shade, alpha);
        }
    }

    private static void setHazardTint(SpriteBatch batch, EnvironmentBiomeRules.Biome biome) {
        switch (biome) {
            case CINDER_FOUNDRY -> batch.setColor(1f, .42f, .10f, .62f);
            case NULL_SECTOR -> batch.setColor(.54f, .32f, 1f, .52f);
            case CRYO_VAULT -> batch.setColor(.36f, .82f, 1f, .50f);
            case CRYOGENIC_DEPTHS -> batch.setColor(.18f, .64f, .76f, .48f);
            default -> batch.setColor(.92f, .66f, .16f, .54f);
        }
    }

    private static void setDecalTint(SpriteBatch batch, EnvironmentBiomeRules.Biome biome, int kind) {
        if (kind == 1 && biome == EnvironmentBiomeRules.Biome.QUARANTINE_YARD) {
            batch.setColor(.52f, .09f, .08f, .50f);
        } else if (biome == EnvironmentBiomeRules.Biome.CINDER_FOUNDRY) {
            batch.setColor(.50f, .23f, .10f, .42f);
        } else if (biome == EnvironmentBiomeRules.Biome.NULL_SECTOR) {
            batch.setColor(.35f, .20f, .58f, .38f);
        } else if (biome == EnvironmentBiomeRules.Biome.CRYO_VAULT
            || biome == EnvironmentBiomeRules.Biome.CRYOGENIC_DEPTHS) {
            batch.setColor(.28f, .55f, .66f, .36f);
        } else {
            batch.setColor(.34f, .36f, .36f, .42f);
        }
    }

    @Override
    public void dispose() {
        if (bootstrap != null) bootstrap.dispose();
        bootstrap = null;
    }
}
