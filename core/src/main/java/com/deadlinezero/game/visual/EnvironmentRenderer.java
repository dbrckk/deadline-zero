package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

/**
 * Deterministic combat environment presentation.
 * Procedural geometry is always available; final atlas art takes priority over generated bootstrap art.
 */
public final class EnvironmentRenderer implements Disposable {
    private static final float HALF_W = 40f;
    private static final float HALF_H = 24f;
    private static final float TILE_WORLD = 4f;
    private static final float TRANSITION_FLOOR_ALPHA = .62f;
    private final GameArt art;
    private BootstrapEnvironmentArt bootstrap;

    public EnvironmentRenderer(GameArt art) {
        this.art = art;
        try {
            bootstrap = BootstrapEnvironmentArt.create();
        } catch (RuntimeException ignored) {
            bootstrap = null;
        }
    }

    public void drawGround(ShapeRenderer shapes, float time) {
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
        batch.setColor(1f, 1f, 1f, alpha);
        for (int gy = -6; gy < 6; gy++) {
            for (int gx = -10; gx < 10; gx++) {
                int variant = floorVariant(gx, gy);
                TextureRegion region = region("environment/floor/concrete_" + (char)('a' + variant));
                if (region != null) batch.draw(region, gx * TILE_WORLD, gy * TILE_WORLD, TILE_WORLD, TILE_WORLD);
            }
        }
        TextureRegion hazard = region("environment/floor/hazard_a");
        if (hazard != null) {
            batch.setColor(1f, 1f, 1f, Math.min(1f, alpha * 1.13f));
            for (int x = -8; x <= 8; x += 4) batch.draw(hazard, x, -2f, TILE_WORLD, TILE_WORLD);
        }
    }

    static int floorVariant(int gridX, int gridY) {
        return Math.floorMod(gridX * 31 + gridY * 17, 3);
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

        batch.setColor(1f, 1f, 1f, .76f);
        draw(batch, crack, -10.5f, -7.2f, 3.4f);
        draw(batch, crack, 13.4f, 6.1f, 2.8f);
        draw(batch, crack, -18f, 9f, 2.35f);
        batch.setColor(1f, 1f, 1f, .60f);
        draw(batch, blood, 5.8f, -3.2f, 2.2f);
        draw(batch, blood, -4.2f, 10.1f, 1.7f);
        draw(batch, scorch, 9.3f, 8.4f, 3.6f);

        batch.setColor(1f, 1f, 1f, 1f);
        draw(batch, barrier, -21f, 12f, 2.9f);
        draw(batch, barrier, 22f, -13f, 2.9f);
        draw(batch, debrisA, -24f, -11.5f, 3.4f);
        draw(batch, debrisB, 20f, 10.4f, 3.1f);
        draw(batch, crate, -16f, 14.2f, 2.7f);
        draw(batch, crate, 17.5f, -12.8f, 2.5f);

        drawArenaEdge(batch, wallA, wallB, beacon);
    }

    private void drawArenaEdge(SpriteBatch batch, TextureRegion wallA, TextureRegion wallB, TextureRegion beacon) {
        for (int x = -30; x <= 30; x += 10) {
            draw(batch, ((x / 10) & 1) == 0 ? wallA : wallB, x, 17.9f, 4.2f);
            draw(batch, ((x / 10) & 1) == 0 ? wallB : wallA, x, -17.9f, 4.2f);
        }
        for (int y = -12; y <= 12; y += 8) {
            draw(batch, wallA, -31.4f, y, 3.7f);
            draw(batch, wallB, 31.4f, y, 3.7f);
        }
        draw(batch, beacon, -29.6f, -15.6f, 2.0f);
        draw(batch, beacon, 29.6f, -15.6f, 2.0f);
        draw(batch, beacon, -29.6f, 15.6f, 2.0f);
        draw(batch, beacon, 29.6f, 15.6f, 2.0f);
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
