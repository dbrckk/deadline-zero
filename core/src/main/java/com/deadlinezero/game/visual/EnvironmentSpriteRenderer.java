package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** Draws deterministic authored environment tiles and set dressing behind combat. */
public final class EnvironmentSpriteRenderer {
    private static final float TILE_WORLD = 4f;
    private final GameArt art;

    public EnvironmentSpriteRenderer(GameArt art) { this.art = art; }

    public void render(SpriteBatch batch, OrthographicCamera cam) {
        if (!art.authoredAvailable()) return;
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        drawFloor(batch);
        drawSetDressing(batch);
        batch.end();
    }

    private void drawFloor(SpriteBatch batch) {
        for (int gy = -6; gy < 6; gy++) {
            for (int gx = -10; gx < 10; gx++) {
                int variant = Math.floorMod(gx * 31 + gy * 17, 3);
                String key = "environment/floor/concrete_" + (char)('a' + variant);
                TextureRegion region = art.regionOrNull(key);
                if (region == null) continue;
                batch.draw(region, gx * TILE_WORLD, gy * TILE_WORLD, TILE_WORLD, TILE_WORLD);
            }
        }
        TextureRegion hazard = art.regionOrNull("environment/floor/hazard_a");
        if (hazard != null) {
            for (int x = -8; x <= 8; x += 4) batch.draw(hazard, x, -2f, 4f, 4f);
        }
    }

    private void drawSetDressing(SpriteBatch batch) {
        draw(batch, "environment/decal/crack_a", -18f, 8f, 4.2f, 4.2f);
        draw(batch, "environment/decal/crack_a", 13f, -10f, 3.8f, 3.8f);
        draw(batch, "environment/decal/blood_a", -5f, -7f, 3.2f, 3.2f);
        draw(batch, "environment/decal/scorch_a", 9f, 7f, 4.6f, 4.6f);
        draw(batch, "environment/prop/barrier_a", -27f, 11f, 5.4f, 5.4f);
        draw(batch, "environment/prop/barrier_a", 22f, -14f, 5.4f, 5.4f);
        draw(batch, "environment/prop/debris_a", -23f, -12f, 4.4f, 4.4f);
        draw(batch, "environment/prop/debris_b", 20f, 10f, 4.4f, 4.4f);
    }

    private void draw(SpriteBatch batch, String key, float x, float y, float w, float h) {
        TextureRegion region = art.regionOrNull(key);
        if (region != null) batch.draw(region, x, y, w, h);
    }
}
