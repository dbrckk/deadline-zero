package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Deterministic combat environment presentation.
 * Procedural geometry is always available; authored decals/props are layered in when the atlas contains them.
 */
public final class EnvironmentRenderer {
    private static final float HALF_W = 40f;
    private static final float HALF_H = 24f;
    private final GameArt art;

    public EnvironmentRenderer(GameArt art) { this.art = art; }

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
        if (!art.authoredAvailable()) return;
        TextureRegion decalA = art.region("environment/decal/crack_a");
        TextureRegion decalB = art.region("environment/decal/blood_a");
        TextureRegion prop = art.region("environment/prop/barrier_a");

        batch.begin();
        batch.setColor(1f, 1f, 1f, .72f);
        draw(batch, decalA, -10.5f, -7.2f, 3.4f);
        draw(batch, decalA, 13.4f, 6.1f, 2.8f);
        batch.setColor(1f, 1f, 1f, .58f);
        draw(batch, decalB, 5.8f, -3.2f, 2.2f);
        batch.setColor(1f, 1f, 1f, 1f);
        draw(batch, prop, -21f, 12f, 2.6f);
        draw(batch, prop, 22f, -13f, 2.6f);
        batch.end();
        batch.setColor(Color.WHITE);
    }

    private void draw(SpriteBatch batch, TextureRegion region, float x, float y, float width) {
        if (region == null) return;
        float aspect = region.getRegionHeight() <= 0 ? 1f : region.getRegionWidth() / (float)region.getRegionHeight();
        float h = width / Math.max(.2f, aspect);
        batch.draw(region, x - width * .5f, y - h * .5f, width, h);
    }
}
