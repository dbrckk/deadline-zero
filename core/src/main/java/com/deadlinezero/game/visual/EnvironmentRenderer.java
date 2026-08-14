package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.meta.RunStageContext;

/**
 * Deterministic combat environment presentation.
 * Procedural geometry is always available; final atlas art takes priority over generated bootstrap art.
 */
public final class EnvironmentRenderer implements Disposable {
    private static final float HALF_W = 40f;
    private static final float HALF_H = 24f;
    private static final float TILE_WORLD = 4f;
    private static final float TRANSITION_FLOOR_ALPHA = .62f;
    private static final float SHADOW_OFFSET_X = .22f;
    private static final float SHADOW_OFFSET_Y = -.28f;
    private static final Color FOUNDRY_WALL_TINT = new Color(1f, .58f, .32f, 1f);
    private static final Color NULL_WALL_TINT = new Color(.64f, .56f, 1f, 1f);
    private final GameArt art;
    private BootstrapEnvironmentArt bootstrap;
    private float visualTime;

    public EnvironmentRenderer(GameArt art) {
        this.art = art;
        try {
            bootstrap = BootstrapEnvironmentArt.create();
        } catch (RuntimeException ignored) {
            bootstrap = null;
        }
    }

    public void update(float dt) {
        visualTime += MathUtils.clamp(dt, 0f, .1f);
        if (visualTime > 4096f) visualTime -= 4096f;
    }

    private boolean foundry() {
        return EnvironmentBiomeRules.isFoundry(RunStageContext.stage());
    }

    private boolean nullSector() {
        return EnvironmentBiomeRules.isNullSector(RunStageContext.stage());
    }

    public void drawGround(ShapeRenderer shapes, float time) {
        if (nullSector()) drawNullSectorGround(shapes, time);
        else if (foundry()) drawFoundryGround(shapes, time);
        else drawQuarantineGround(shapes, time);
    }

    private void drawQuarantineGround(ShapeRenderer shapes, float time) {
        shapes.setColor(.016f, .025f, .033f, 1f);
        shapes.rect(-HALF_W, -HALF_H, HALF_W * 2f, HALF_H * 2f);
        shapes.setColor(.030f, .055f, .064f, .84f);
        for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .035f, 48f);
        for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .035f);
        shapes.setColor(.055f, .10f, .105f, .46f);
        for (int x = -38; x < 40; x += 8) {
            for (int y = -22; y < 24; y += 8) {
                float ox = MathUtils.sin(x * 1.7f + y * .4f) * .35f;
                float oy = MathUtils.cos(y * 1.3f + x * .2f) * .28f;
                shapes.rect(x + ox, y + oy, 1.1f, .07f);
                shapes.rect(x + ox + .25f, y + oy - .34f, .62f, .045f);
            }
        }
        float pulse = .035f + .018f * (MathUtils.sin(time * 1.4f) * .5f + .5f);
        shapes.setColor(VisualTheme.RED.r, VisualTheme.RED.g, VisualTheme.RED.b, pulse);
        shapes.circle(-17f, 8f, 5.2f, 36);
        shapes.circle(19f, -10f, 4.1f, 32);
    }

    private void drawFoundryGround(ShapeRenderer shapes, float time) {
        shapes.setColor(.032f, .019f, .017f, 1f);
        shapes.rect(-HALF_W, -HALF_H, HALF_W * 2f, HALF_H * 2f);
        shapes.setColor(.12f, .050f, .025f, .78f);
        for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .045f, 48f);
        for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .045f);
        shapes.setColor(.20f, .075f, .025f, .35f);
        for (int x = -36; x < 40; x += 8) {
            for (int y = -20; y < 24; y += 8) {
                float skew = MathUtils.sin(x * .37f + y * .61f) * .44f;
                shapes.rect(x + skew, y, 1.7f, .08f);
                shapes.rect(x + skew + .55f, y - .40f, .85f, .05f);
            }
        }
        float furnace = .5f + .5f * MathUtils.sin(time * 2.15f);
        shapes.setColor(1f, .19f, .025f, .035f + furnace * .028f);
        shapes.circle(-20f, -9f, 6.4f + furnace * .7f, 40);
        shapes.circle(18f, 9f, 5.2f + furnace * .55f, 36);
        shapes.setColor(1f, .58f, .08f, .055f + furnace * .03f);
        shapes.rect(-4.5f, -24f, 9f, 48f);
    }

    private void drawNullSectorGround(ShapeRenderer shapes, float time) {
        shapes.setColor(.012f, .012f, .032f, 1f);
        shapes.rect(-HALF_W, -HALF_H, HALF_W * 2f, HALF_H * 2f);
        float pulse = .5f + .5f * MathUtils.sin(time * 1.65f);
        shapes.setColor(.12f, .10f, .30f, .72f);
        for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .030f + pulse * .012f, 48f);
        for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .030f + pulse * .012f);
        shapes.setColor(.35f, .18f, .82f, .12f + pulse * .07f);
        for (int i = -4; i <= 4; i++) {
            float x = i * 8.2f + MathUtils.sin(time * .75f + i) * .55f;
            shapes.rectLine(x - 5.5f, -20f, x + 5.5f, 20f, .045f);
        }
        shapes.setColor(.12f, .72f, 1f, .055f + pulse * .045f);
        shapes.circle(-18f, 10f, 4.4f + pulse * .55f, 36);
        shapes.circle(20f, -8f, 5.6f + pulse * .70f, 40);
        shapes.setColor(.72f, .28f, 1f, .04f + pulse * .035f);
        shapes.rect(-3.2f, -24f, 6.4f, 48f);
    }

    public void drawAuthored(SpriteBatch batch) {
        if (!hasAnyEnvironmentArt()) return;
        batch.begin();
        drawFloorInternal(batch, TRANSITION_FLOOR_ALPHA);
        drawSetDressingInternal(batch);
        batch.setColor(Color.WHITE);
        batch.end();
    }

    public void drawFloor(SpriteBatch batch, float alpha) {
        if (!hasFloorArt()) return;
        batch.begin();
        drawFloorInternal(batch, MathUtils.clamp(alpha, 0f, 1f));
        batch.setColor(Color.WHITE);
        batch.end();
    }

    public void drawSetDressing(SpriteBatch batch) {
        if (!hasSetDressingArt()) return;
        batch.begin();
        drawSetDressingInternal(batch);
        batch.setColor(Color.WHITE);
        batch.end();
    }

    private void drawFloorInternal(SpriteBatch batch, float alpha) {
        if (nullSector()) batch.setColor(.66f, .62f, 1f, alpha * .88f);
        else if (foundry()) batch.setColor(1f, .63f, .43f, alpha * .92f);
        else batch.setColor(1f, 1f, 1f, alpha);
        for (int gy = -6; gy < 6; gy++) {
            for (int gx = -10; gx < 10; gx++) {
                int variant = floorVariant(gx, gy);
                TextureRegion region = region("environment/floor/concrete_" + (char)('a' + variant));
                if (region != null) batch.draw(region, gx * TILE_WORLD, gy * TILE_WORLD, TILE_WORLD, TILE_WORLD);
            }
        }
        TextureRegion hazard = region("environment/floor/hazard_a");
        if (hazard != null) {
            if (nullSector()) batch.setColor(.48f, .28f, 1f, Math.min(1f, alpha * 1.15f));
            else if (foundry()) batch.setColor(1f, .38f, .08f, Math.min(1f, alpha * 1.20f));
            else batch.setColor(1f, 1f, 1f, Math.min(1f, alpha * 1.13f));
            if (nullSector()) {
                for (int y = -12; y <= 12; y += 8) {
                    int offset = ((y / 4) & 1) == 0 ? -14 : -10;
                    for (int x = offset; x <= 14; x += 8) batch.draw(hazard, x, y, TILE_WORLD, TILE_WORLD);
                }
            } else if (foundry()) {
                for (int y = -10; y <= 10; y += 10) {
                    for (int x = -12; x <= 12; x += 8) batch.draw(hazard, x, y, TILE_WORLD, TILE_WORLD);
                }
            } else {
                for (int x = -8; x <= 8; x += 4) batch.draw(hazard, x, -2f, TILE_WORLD, TILE_WORLD);
            }
        }
    }

    static int floorVariant(int gridX, int gridY) {
        return Math.floorMod(gridX * 31 + gridY * 17, 3);
    }

    static int detailVariant(int gridX, int gridY) {
        int h = gridX * 0x45d9f3b ^ gridY * 0x119de1f3;
        h ^= h >>> 16;
        return Math.floorMod(h, 8);
    }

    static float beaconPulse(float time) {
        return .5f + .5f * MathUtils.sin(time * 2.8f);
    }

    private void drawSetDressingInternal(SpriteBatch batch) {
        TextureRegion crack = region("environment/decal/crack_a");
        TextureRegion blood = region("environment/decal/blood_a");
        TextureRegion scorch = region("environment/decal/scorch_a");
        TextureRegion barrier = region("environment/prop/barrier_a");
        TextureRegion debrisA = region("environment/prop/debris_a");
        TextureRegion debrisB = region("environment/prop/debris_b");
        TextureRegion wallA = region("environment/prop/wall_a");
        TextureRegion wallB = region("environment/prop/wall_b");
        TextureRegion crate = region("environment/prop/crate_a");
        TextureRegion beacon = region("environment/prop/beacon_a");

        drawAmbientDetails(batch, crack, blood, scorch, debrisA, debrisB);

        if (nullSector()) {
            drawNullSectorDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
            return;
        }
        if (foundry()) {
            drawFoundryDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
            return;
        }

        batch.setColor(1f, 1f, 1f, .76f);
        draw(batch, crack, -10.5f, -7.2f, 3.4f);
        draw(batch, crack, 13.4f, 6.1f, 2.8f);
        draw(batch, crack, -18f, 9f, 2.35f);
        batch.setColor(1f, 1f, 1f, .60f);
        draw(batch, blood, 5.8f, -3.2f, 2.2f);
        draw(batch, blood, -4.2f, 10.1f, 1.7f);
        draw(batch, scorch, 9.3f, 8.4f, 3.6f);

        drawPropShadow(batch, barrier, -21f, 12f, 2.9f);
        drawPropShadow(batch, barrier, 22f, -13f, 2.9f);
        drawPropShadow(batch, crate, -16f, 14.2f, 2.7f);
        drawPropShadow(batch, crate, 17.5f, -12.8f, 2.5f);
        drawArenaEdgeShadows(batch, wallA, wallB);

        batch.setColor(1f, 1f, 1f, 1f);
        draw(batch, barrier, -21f, 12f, 2.9f);
        draw(batch, barrier, 22f, -13f, 2.9f);
        draw(batch, debrisA, -24f, -11.5f, 3.4f);
        draw(batch, debrisB, 20f, 10.4f, 3.1f);
        draw(batch, crate, -16f, 14.2f, 2.7f);
        draw(batch, crate, 17.5f, -12.8f, 2.5f);
        drawArenaEdge(batch, wallA, wallB, beacon);
    }

    private void drawAmbientDetails(SpriteBatch batch, TextureRegion crack, TextureRegion blood,
                                    TextureRegion scorch, TextureRegion debrisA, TextureRegion debrisB) {
        boolean nullBiome = nullSector();
        boolean hotBiome = foundry();
        for (int gy = -2; gy <= 2; gy++) {
            for (int gx = -4; gx <= 4; gx++) {
                int variant = detailVariant(gx, gy);
                if (variant > 2) continue;
                float x = gx * 6.1f + ((variant & 1) == 0 ? -.55f : .48f);
                float y = gy * 6.0f + ((gx & 1) == 0 ? .42f : -.38f);
                if (Math.abs(x) < 5f && Math.abs(y) < 4f) continue;
                if (variant == 0) {
                    if (nullBiome) batch.setColor(.72f, .62f, 1f, .38f);
                    else batch.setColor(1f, 1f, 1f, hotBiome ? .34f : .40f);
                    draw(batch, crack, x, y, 1.45f);
                } else if (variant == 1) {
                    TextureRegion stain = hotBiome || nullBiome ? scorch : blood;
                    if (nullBiome) batch.setColor(.52f, .32f, 1f, .30f);
                    else batch.setColor(1f, hotBiome ? .45f : 1f, hotBiome ? .20f : 1f, .28f);
                    draw(batch, stain, x, y, 1.35f);
                } else {
                    TextureRegion debris = ((gx + gy) & 1) == 0 ? debrisA : debrisB;
                    drawPropShadow(batch, debris, x, y, 1.25f);
                    if (nullBiome) batch.setColor(.72f, .68f, 1f, .74f);
                    else batch.setColor(hotBiome ? 1f : .78f, hotBiome ? .64f : .82f, hotBiome ? .38f : .86f, .72f);
                    draw(batch, debris, x, y, 1.20f);
                }
            }
        }
        batch.setColor(Color.WHITE);
    }

    private void drawFoundryDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
                                     TextureRegion barrier, TextureRegion debrisA, TextureRegion debrisB,
                                     TextureRegion wallA, TextureRegion wallB, TextureRegion crate,
                                     TextureRegion beacon) {
        batch.setColor(1f, .48f, .24f, .70f);
        draw(batch, scorch, -12f, -7.5f, 4.8f);
        draw(batch, scorch, 13f, 7f, 4.2f);
        draw(batch, crack, -3f, 11f, 3.1f);
        draw(batch, crack, 5f, -12f, 2.7f);

        drawPropShadow(batch, barrier, -18f, -12f, 3.2f);
        drawPropShadow(batch, barrier, 19f, 12f, 3.2f);
        drawPropShadow(batch, crate, -22f, 10f, 3.0f);
        drawPropShadow(batch, crate, 23f, -9f, 3.0f);
        drawPropShadow(batch, debrisA, -8f, 14f, 3.2f);
        drawPropShadow(batch, debrisB, 9f, -14f, 3.2f);
        drawArenaEdgeShadows(batch, wallB, wallA);

        batch.setColor(1f, .66f, .42f, 1f);
        draw(batch, barrier, -18f, -12f, 3.2f);
        draw(batch, barrier, 19f, 12f, 3.2f);
        draw(batch, crate, -22f, 10f, 3.0f);
        draw(batch, crate, 23f, -9f, 3.0f);
        draw(batch, debrisA, -8f, 14f, 3.2f);
        draw(batch, debrisB, 9f, -14f, 3.2f);
        drawArenaEdge(batch, wallB, wallA, beacon);
    }

    private void drawNullSectorDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
                                        TextureRegion barrier, TextureRegion debrisA, TextureRegion debrisB,
                                        TextureRegion wallA, TextureRegion wallB, TextureRegion crate,
                                        TextureRegion beacon) {
        batch.setColor(.64f, .48f, 1f, .66f);
        draw(batch, crack, -14f, 8f, 4.0f);
        draw(batch, crack, 12f, -9f, 3.6f);
        draw(batch, scorch, -4f, -12f, 3.8f);
        draw(batch, scorch, 6f, 12f, 3.4f);

        drawPropShadow(batch, barrier, -20f, 11f, 3.0f);
        drawPropShadow(batch, barrier, 20f, -11f, 3.0f);
        drawPropShadow(batch, crate, -13f, -14f, 2.8f);
        drawPropShadow(batch, crate, 14f, 14f, 2.8f);
        drawPropShadow(batch, debrisA, -24f, -7f, 3.1f);
        drawPropShadow(batch, debrisB, 24f, 7f, 3.1f);
        drawArenaEdgeShadows(batch, wallA, wallB);

        batch.setColor(.72f, .68f, 1f, 1f);
        draw(batch, barrier, -20f, 11f, 3.0f);
        draw(batch, barrier, 20f, -11f, 3.0f);
        draw(batch, crate, -13f, -14f, 2.8f);
        draw(batch, crate, 14f, 14f, 2.8f);
        draw(batch, debrisA, -24f, -7f, 3.1f);
        draw(batch, debrisB, 24f, 7f, 3.1f);
        drawArenaEdge(batch, wallA, wallB, beacon);
    }

    private void drawArenaEdgeShadows(SpriteBatch batch, TextureRegion wallA, TextureRegion wallB) {
        for (int x = -30; x <= 30; x += 10) {
            drawPropShadow(batch, ((x / 10) & 1) == 0 ? wallA : wallB, x, 17.9f, 4.2f);
            drawPropShadow(batch, ((x / 10) & 1) == 0 ? wallB : wallA, x, -17.9f, 4.2f);
        }
        for (int y = -12; y <= 12; y += 8) {
            drawPropShadow(batch, wallA, -31.4f, y, 3.7f);
            drawPropShadow(batch, wallB, 31.4f, y, 3.7f);
        }
    }

    private void drawArenaEdge(SpriteBatch batch, TextureRegion wallA, TextureRegion wallB, TextureRegion beacon) {
        batch.setColor(nullSector() ? NULL_WALL_TINT : foundry() ? FOUNDRY_WALL_TINT : Color.WHITE);
        for (int x = -30; x <= 30; x += 10) {
            draw(batch, ((x / 10) & 1) == 0 ? wallA : wallB, x, 17.9f, 4.2f);
            draw(batch, ((x / 10) & 1) == 0 ? wallB : wallA, x, -17.9f, 4.2f);
        }
        for (int y = -12; y <= 12; y += 8) {
            draw(batch, wallA, -31.4f, y, 3.7f);
            draw(batch, wallB, 31.4f, y, 3.7f);
        }
        drawBeacon(batch, beacon, -29.6f, -15.6f);
        drawBeacon(batch, beacon, 29.6f, -15.6f);
        drawBeacon(batch, beacon, -29.6f, 15.6f);
        drawBeacon(batch, beacon, 29.6f, 15.6f);
    }

    private void drawBeacon(SpriteBatch batch, TextureRegion beacon, float x, float y) {
        if (beacon == null) return;
        float pulse = beaconPulse(visualTime);
        float glowSize = 2.7f + pulse * .65f;
        if (nullSector()) {
            batch.setColor(.36f, .16f, 1f, .11f + pulse * .12f);
            draw(batch, beacon, x, y, glowSize);
            batch.setColor(.42f, .86f, 1f, .88f + pulse * .12f);
        } else if (foundry()) {
            batch.setColor(1f, .18f, .03f, .10f + pulse * .12f);
            draw(batch, beacon, x, y, glowSize);
            batch.setColor(1f, .58f, .12f, .88f + pulse * .12f);
        } else {
            batch.setColor(.18f, .88f, 1f, .10f + pulse * .10f);
            draw(batch, beacon, x, y, glowSize);
            batch.setColor(.62f, .96f, 1f, .88f + pulse * .12f);
        }
        draw(batch, beacon, x, y, 2.0f);
        batch.setColor(Color.WHITE);
    }

    private void drawPropShadow(SpriteBatch batch, TextureRegion region, float x, float y, float width) {
        if (region == null) return;
        batch.setColor(.01f, .015f, .02f, .36f);
        draw(batch, region, x + SHADOW_OFFSET_X, y + SHADOW_OFFSET_Y, width * 1.03f);
    }

    private boolean hasAnyEnvironmentArt() { return hasFloorArt() || hasSetDressingArt(); }

    private boolean hasFloorArt() {
        return region("environment/floor/concrete_a") != null
            || region("environment/floor/concrete_b") != null
            || region("environment/floor/concrete_c") != null
            || region("environment/floor/hazard_a") != null;
    }

    private boolean hasSetDressingArt() {
        return region("environment/decal/crack_a") != null
            || region("environment/decal/blood_a") != null
            || region("environment/decal/scorch_a") != null
            || region("environment/prop/barrier_a") != null
            || region("environment/prop/debris_a") != null
            || region("environment/prop/debris_b") != null
            || region("environment/prop/wall_a") != null
            || region("environment/prop/wall_b") != null
            || region("environment/prop/crate_a") != null
            || region("environment/prop/beacon_a") != null;
    }

    private TextureRegion region(String key) {
        TextureRegion finalOrLegacy = art.regionOrNull(key);
        if (finalOrLegacy != null) return finalOrLegacy;
        return bootstrap == null ? null : bootstrap.region(key);
    }

    private void draw(SpriteBatch batch, TextureRegion region, float x, float y, float width) {
        if (region == null) return;
        float aspect = region.getRegionHeight() <= 0 ? 1f : region.getRegionWidth() / (float)region.getRegionHeight();
        float h = width / Math.max(.2f, aspect);
        batch.draw(region, x - width * .5f, y - h * .5f, width, h);
    }

    @Override public void dispose() {
        if (bootstrap != null) bootstrap.dispose();
        bootstrap = null;
    }
}
