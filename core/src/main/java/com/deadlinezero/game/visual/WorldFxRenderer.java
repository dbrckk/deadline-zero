package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.entities.Projectile;

/** Procedural combat presentation layer used until authored sprite/VFX assets replace primitives. */
public final class WorldFxRenderer {
    public void drawGroundShadows(ShapeRenderer shapes, Player player, Array<Enemy> enemies) {
        shapes.setColor(0f, 0f, 0f, .24f);
        shapes.ellipse(player.position.x - player.radius * 1.05f, player.position.y - player.radius * .82f,
            player.radius * 2.1f, player.radius * .72f);
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float width = e.radius * 2.1f;
            shapes.ellipse(e.position.x - width * .5f, e.position.y - e.radius * .72f,
                width, e.radius * .66f);
        }
    }

    public void drawProjectileTrails(ShapeRenderer shapes, Array<Projectile> projectiles,
                                     Array<EnemyProjectile> hostileProjectiles,
                                     Array<HomingMissile> missiles) {
        for (Projectile p : projectiles) {
            if (!p.active) continue;
            float speed = p.velocity.len();
            if (speed < .001f) continue;
            float nx = p.velocity.x / speed;
            float ny = p.velocity.y / speed;
            Color c = switch (p.element) {
                case FIRE -> Color.ORANGE;
                case FROST -> Color.CYAN;
                case SHOCK -> VisualTheme.VIOLET;
                default -> p.critical ? VisualTheme.GOLD : VisualTheme.CYAN;
            };
            shapes.setColor(c.r, c.g, c.b, p.critical ? .72f : .46f);
            shapes.rectLine(p.position.x, p.position.y,
                p.position.x - nx * (p.critical ? .85f : .58f),
                p.position.y - ny * (p.critical ? .85f : .58f),
                p.critical ? .09f : .055f);
        }

        for (EnemyProjectile p : hostileProjectiles) {
            if (!p.active) continue;
            float speed = p.velocity.len();
            if (speed < .001f) continue;
            float nx = p.velocity.x / speed;
            float ny = p.velocity.y / speed;
            Color c = p.explosive ? VisualTheme.GOLD : VisualTheme.RED;
            shapes.setColor(c.r, c.g, c.b, .40f);
            shapes.rectLine(p.position.x, p.position.y,
                p.position.x - nx * .52f, p.position.y - ny * .52f, .065f);
        }

        for (HomingMissile m : missiles) {
            if (!m.active) continue;
            float speed = m.velocity.len();
            if (speed < .001f) continue;
            float nx = m.velocity.x / speed;
            float ny = m.velocity.y / speed;
            Color c = m.element == DamageElement.FROST ? VisualTheme.CYAN : VisualTheme.GOLD;
            for (int i = 1; i <= 3; i++) {
                float t = i / 3f;
                shapes.setColor(c.r, c.g, c.b, .42f * (1f - t * .55f));
                float jitter = MathUtils.sin((m.life + i) * 19f) * .035f;
                shapes.circle(m.position.x - nx * (.22f + i * .22f) + ny * jitter,
                    m.position.y - ny * (.22f + i * .22f) - nx * jitter,
                    .10f * (1f - t * .45f), 10);
            }
        }
    }
}
