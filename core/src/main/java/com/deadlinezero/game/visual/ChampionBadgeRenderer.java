package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deadlinezero.game.entities.Enemy;

/**
 * Draws compact, shape-based champion markers under authored enemy sprites.
 *
 * Champion identity must not depend on color alone, but the previous two-letter plates were almost
 * as large as small enemies and read like debug labels. These markers keep the accessibility signal
 * while staying subordinate to the actual actor silhouette.
 */
public final class ChampionBadgeRenderer {
    private final ShapeRenderer shapes = new ShapeRenderer();

    public void draw(SpriteBatch batch, Array<Enemy> enemies) {
        if (batch == null || enemies == null || enemies.size == 0) return;

        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive || enemy.type == Enemy.Type.BOSS) continue;
            ChampionVariantPresentation.Marker marker = ChampionVariantPresentation.marker(enemy.variant);
            if (marker == ChampionVariantPresentation.Marker.NONE) continue;

            float size = MathUtils.clamp(enemy.radius * .72f, .22f, .46f);
            float cx = enemy.position.x;
            float cy = enemy.position.y - enemy.radius * .55f;
            drawMarker(marker, cx, cy, size);
        }
        shapes.end();
    }

    private void drawMarker(ChampionVariantPresentation.Marker marker, float cx, float cy, float size) {
        float t = Math.max(.026f, size * .12f);
        float r = size * .50f;

        shapes.setColor(.015f, .022f, .028f, .44f);
        shapes.circle(cx, cy, r * 1.28f, 20);
        shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .72f);

        switch (marker) {
            case CHEVRON -> {
                shapes.triangle(cx + r * .82f, cy,
                    cx - r * .16f, cy + r * .58f,
                    cx + r * .05f, cy);
                shapes.triangle(cx + r * .82f, cy,
                    cx - r * .16f, cy - r * .58f,
                    cx + r * .05f, cy);
            }
            case ARMOR -> {
                shapes.rect(cx - r * .62f, cy - r * .44f, t, r * .88f);
                shapes.rect(cx + r * .62f - t, cy - r * .44f, t, r * .88f);
                shapes.rect(cx - r * .42f, cy + r * .52f - t, r * .84f, t);
                shapes.rect(cx - r * .42f, cy - r * .52f, r * .84f, t);
            }
            case CLAW -> {
                for (int i = -1; i <= 1; i++) {
                    float off = i * r * .28f;
                    shapes.rectLine(cx - r * .48f + off, cy - r * .48f,
                        cx + r * .24f + off, cy + r * .48f, t * .72f);
                }
            }
            case VOLATILE_CORE -> {
                shapes.circle(cx, cy, r * .24f, 12);
                for (int i = 0; i < 4; i++) {
                    float a = 45f + i * 90f;
                    float dx = MathUtils.cosDeg(a) * r * .62f;
                    float dy = MathUtils.sinDeg(a) * r * .62f;
                    shapes.circle(cx + dx, cy + dy, r * .13f, 10);
                }
            }
            case JUGGERNAUT -> {
                shapes.rect(cx - t * .6f, cy - r * .60f, t * 1.2f, r * 1.20f);
                shapes.rect(cx - r * .60f, cy - t * .6f, r * 1.20f, t * 1.2f);
            }
            case RAVAGER -> {
                shapes.rectLine(cx - r * .52f, cy - r * .52f, cx + r * .52f, cy + r * .52f, t);
                shapes.rectLine(cx - r * .52f, cy + r * .52f, cx + r * .52f, cy - r * .52f, t);
            }
            case AEGIS -> {
                shapes.triangle(cx, cy + r * .70f,
                    cx - r * .64f, cy,
                    cx, cy - r * .70f);
                shapes.triangle(cx, cy + r * .70f,
                    cx + r * .64f, cy,
                    cx, cy - r * .70f);
                shapes.setColor(.015f, .022f, .028f, .88f);
                shapes.circle(cx, cy, r * .22f, 12);
            }
            case HUNTER -> {
                shapes.circle(cx, cy, r * .40f, 18);
                shapes.setColor(.015f, .022f, .028f, .88f);
                shapes.circle(cx, cy, r * .22f, 14);
                shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .72f);
                shapes.rect(cx - t * .45f, cy + r * .38f, t * .9f, r * .28f);
                shapes.rect(cx - t * .45f, cy - r * .66f, t * .9f, r * .28f);
                shapes.rect(cx + r * .38f, cy - t * .45f, r * .28f, t * .9f);
                shapes.rect(cx - r * .66f, cy - t * .45f, r * .28f, t * .9f);
            }
            case NONE -> { }
        }
    }

    public void dispose() {
        shapes.dispose();
    }
}
