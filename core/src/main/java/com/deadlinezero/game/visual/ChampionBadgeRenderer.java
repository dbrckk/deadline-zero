package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;

/** Draws compact non-color champion identifiers above authored enemy sprites. */
public final class ChampionBadgeRenderer {
    private static final float BADGE_WIDTH = .82f;
    private static final float BADGE_HEIGHT = .58f;
    private static final float PIXEL = .075f;
    private final ShapeRenderer shapes = new ShapeRenderer();

    public void draw(SpriteBatch batch, Array<Enemy> enemies) {
        if (batch == null || enemies == null || enemies.size == 0) return;

        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive) continue;
            String badge = ChampionVariantPresentation.badge(enemy.variant);
            if (badge.length() != 2) continue;

            float left = enemy.position.x - BADGE_WIDTH * .5f;
            float bottom = enemy.position.y + enemy.radius + .36f;

            // The dark plate and pixel glyphs remain readable regardless of champion tint/color-vision mode.
            shapes.setColor(VisualTheme.GOLD);
            shapes.rect(left, bottom, BADGE_WIDTH, BADGE_HEIGHT);
            shapes.setColor(.025f, .030f, .035f, 1f);
            shapes.rect(left + .045f, bottom + .045f, BADGE_WIDTH - .09f, BADGE_HEIGHT - .09f);

            float glyphWidth = PIXEL * 3f;
            float totalWidth = glyphWidth * 2f + PIXEL;
            float glyphX = left + (BADGE_WIDTH - totalWidth) * .5f;
            float glyphY = bottom + (BADGE_HEIGHT - PIXEL * 5f) * .5f;
            shapes.setColor(.96f, .98f, 1f, 1f);
            drawGlyph(badge.charAt(0), glyphX, glyphY);
            drawGlyph(badge.charAt(1), glyphX + glyphWidth + PIXEL, glyphY);
        }
        shapes.end();
    }

    private void drawGlyph(char glyph, float x, float y) {
        String bits = switch (glyph) {
            case 'A' -> "010101111101101";
            case 'E' -> "111100110100111";
            case 'F' -> "111100110100100";
            case 'H' -> "101101111101101";
            case 'J' -> "001001001101010";
            case 'O' -> "010101101101010";
            case 'R' -> "110101110101101";
            case 'S' -> "111100111001111";
            case 'U' -> "101101101101111";
            case 'V' -> "101101101101010";
            case 'W' -> "101101101111101";
            default -> "000000000000000";
        };
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                if (bits.charAt(row * 3 + col) != '1') continue;
                shapes.rect(x + col * PIXEL, y + (4 - row) * PIXEL, PIXEL, PIXEL);
            }
        }
    }

    public void dispose() {
        shapes.dispose();
    }
}
