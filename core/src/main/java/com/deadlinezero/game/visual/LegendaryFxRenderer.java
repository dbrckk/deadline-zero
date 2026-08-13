package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Player;

/** Lightweight procedural signatures for active legendary transformations. */
public final class LegendaryFxRenderer {
    public void render(ShapeRenderer shapes, Player player, float time, float quality) {
        if (player == null || !player.legendary.hasAny()) return;
        float q = MathUtils.clamp(quality, .35f, 1f);

        if (player.legendary.hasOverdrive()) {
            float pulse = 1f + MathUtils.sin(time * 9f) * .08f;
            shapes.setColor(1f, .32f, .08f, .13f * q);
            shapes.circle(player.position.x, player.position.y, 1.28f * pulse, 24);
            shapes.setColor(1f, .78f, .18f, .26f * q);
            shapes.circle(player.position.x, player.position.y, .78f * pulse, 20);
        }

        if (player.legendary.hasSingularity()) {
            float pulse = 1f + MathUtils.sin(time * 6.5f + 1.7f) * .10f;
            shapes.setColor(.55f, .16f, 1f, .14f * q);
            shapes.circle(player.position.x, player.position.y, 1.55f * pulse, 28);
            shapes.setColor(.18f, .72f, 1f, .22f * q);
            shapes.circle(player.position.x, player.position.y, 1.02f / pulse, 24);
        }

        if (player.legendary.hasApex()) {
            float pulse = 1f + MathUtils.sin(time * 4.2f + .8f) * .06f;
            shapes.setColor(1f, .86f, .28f, .13f * q);
            shapes.circle(player.position.x, player.position.y, 1.88f * pulse, 30);
            for (int i = 0; i < 5; i++) {
                float angle = time * 52f + i * 72f;
                float x = player.position.x + MathUtils.cosDeg(angle) * 1.48f;
                float y = player.position.y + MathUtils.sinDeg(angle) * 1.48f;
                shapes.setColor(.90f, .96f, 1f, .42f * q);
                shapes.circle(x, y, .10f, 10);
            }
        }
    }
}
