package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;

/** Draws compact non-color champion identifiers above authored enemy sprites. */
public final class ChampionBadgeRenderer {
    private final BitmapFont font = new BitmapFont();

    public void draw(SpriteBatch batch, Array<Enemy> enemies) {
        if (batch == null || enemies == null || enemies.size == 0) return;

        batch.begin();
        font.getData().setScale(.036f);
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive) continue;
            String badge = ChampionVariantPresentation.badge(enemy.variant);
            if (badge.isEmpty()) continue;

            float width = .82f;
            float x = enemy.position.x - width * .5f;
            float y = enemy.position.y + enemy.radius + .52f;

            font.setColor(0f, 0f, 0f, .96f);
            font.draw(batch, badge, x + .025f, y - .025f, width, Align.center, false);
            font.setColor(1f, .86f, .30f, 1f);
            font.draw(batch, badge, x, y, width, Align.center, false);
        }
        batch.end();
        font.setColor(1f, 1f, 1f, 1f);
    }

    public void dispose() {
        font.dispose();
    }
}
