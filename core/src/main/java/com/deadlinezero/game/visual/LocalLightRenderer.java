package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.ArcFx;
import com.deadlinezero.game.util.Pools;

/** Cheap additive-looking local light halos implemented with translucent geometry for mobile scalability. */
public final class LocalLightRenderer {
    public void draw(ShapeRenderer shapes, Player player, Iterable<Enemy> enemies, Pools pools, float time) {
        halo(shapes, player.position.x, player.position.y, 1.85f,
            player.invulnerable() ? Color.WHITE : VisualTheme.CYAN, .055f);

        for (ArcFx arc : pools.arcs) {
            if (!arc.active) continue;
            float mx = (arc.x1 + arc.x2) * .5f;
            float my = (arc.y1 + arc.y2) * .5f;
            halo(shapes, mx, my, 1.25f, VisualTheme.CYAN, .075f);
        }

        for (HomingMissile missile : pools.homingMissiles) {
            if (!missile.active) continue;
            Color c = missile.element.name().equals("FROST") ? VisualTheme.CYAN : VisualTheme.GOLD;
            halo(shapes, missile.position.x, missile.position.y, 1.05f, c, .065f);
        }

        for (EnemyProjectile projectile : pools.hostileProjectiles) {
            if (!projectile.active || !projectile.explosive) continue;
            halo(shapes, projectile.position.x, projectile.position.y, 1.15f, VisualTheme.RED, .052f);
        }

        float pulse = .75f + .25f * MathUtils.sin(time * 4f);
        for (Enemy enemy : enemies) {
            if (!enemy.alive || enemy.type != Enemy.Type.BOSS) continue;
            halo(shapes, enemy.position.x, enemy.position.y,
                2.8f + pulse * .35f, VisualTheme.RED, .035f + pulse * .02f);
        }
    }

    private void halo(ShapeRenderer shapes, float x, float y, float radius, Color color, float alpha) {
        shapes.setColor(color.r, color.g, color.b, alpha * .30f);
        shapes.circle(x, y, radius * 1.55f, 28);
        shapes.setColor(color.r, color.g, color.b, alpha * .60f);
        shapes.circle(x, y, radius, 24);
        shapes.setColor(color.r, color.g, color.b, alpha);
        shapes.circle(x, y, radius * .52f, 20);
    }
}
