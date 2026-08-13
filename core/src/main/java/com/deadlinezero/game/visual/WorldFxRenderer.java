package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
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
import com.deadlinezero.game.fx.ArcFx;

/** Procedural combat presentation layer used until authored sprite/VFX assets replace primitives. */
public final class WorldFxRenderer {
    private final AdaptiveFxBudget budget = new AdaptiveFxBudget();

    public float fxQuality() { return budget.quality(); }
    public float smoothedFps() { return budget.smoothedFps(); }

    public void drawGroundShadows(ShapeRenderer shapes, Player player, Array<Enemy> enemies) {
        budget.update(Gdx.graphics.getDeltaTime());
        shapes.setColor(0f, 0f, 0f, .24f * MathUtils.lerp(.65f, 1f, budget.quality()));
        shapes.ellipse(player.position.x - player.radius * 1.05f, player.position.y - player.radius * .82f,
            player.radius * 2.1f, player.radius * .72f);
        int stride = budget.quality() < .55f ? 2 : 1;
        for (int i = 0; i < enemies.size; i += stride) {
            Enemy e = enemies.get(i);
            if (!e.alive) continue;
            float width = e.radius * 2.1f;
            shapes.ellipse(e.position.x - width * .5f, e.position.y - e.radius * .72f,
                width, e.radius * .66f);
        }
    }

    public void drawProjectileTrails(ShapeRenderer shapes, Array<Projectile> projectiles,
                                     Array<EnemyProjectile> hostileProjectiles,
                                     Array<HomingMissile> missiles) {
        float q = budget.quality();
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
            shapes.setColor(c.r, c.g, c.b, (p.critical ? .72f : .46f) * MathUtils.lerp(.65f, 1f, q));
            shapes.rectLine(p.position.x, p.position.y,
                p.position.x - nx * (p.critical ? .85f : .58f),
                p.position.y - ny * (p.critical ? .85f : .58f),
                p.critical ? .09f : .055f);

            if (p.life > 1.41f && budget.allowHeavyFx()) {
                float bx = p.position.x - nx * .20f;
                float by = p.position.y - ny * .20f;
                float sideX = -ny;
                float sideY = nx;
                shapes.setColor(1f, .86f, .30f, .70f);
                shapes.triangle(bx - sideX * .12f, by - sideY * .12f,
                    bx + sideX * .12f, by + sideY * .12f,
                    bx + nx * .55f, by + ny * .55f);
                shapes.setColor(1f, 1f, .82f, .88f);
                shapes.circle(bx, by, .105f, budget.geometrySegments(10, 6));
            }
        }

        for (EnemyProjectile p : hostileProjectiles) {
            if (!p.active) continue;
            float speed = p.velocity.len();
            if (speed < .001f) continue;
            float nx = p.velocity.x / speed;
            float ny = p.velocity.y / speed;
            Color c = p.explosive ? VisualTheme.GOLD : VisualTheme.RED;
            shapes.setColor(c.r, c.g, c.b, .40f * MathUtils.lerp(.7f, 1f, q));
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
            int trailNodes = budget.allowExtraFx() ? 3 : (budget.allowHeavyFx() ? 2 : 1);
            for (int i = 1; i <= trailNodes; i++) {
                float t = i / (float)trailNodes;
                shapes.setColor(c.r, c.g, c.b, .42f * (1f - t * .55f));
                float jitter = MathUtils.sin((m.life + i) * 19f) * .035f;
                shapes.circle(m.position.x - nx * (.22f + i * .22f) + ny * jitter,
                    m.position.y - ny * (.22f + i * .22f) - nx * jitter,
                    .10f * (1f - t * .45f), budget.geometrySegments(10, 6));
            }
        }
    }

    public void drawElectricArcs(ShapeRenderer shapes, Array<ArcFx> arcs, float time) {
        int segments = budget.geometrySegments(7, 4);
        int stride = budget.quality() < .50f ? 2 : 1;
        for (int ai = 0; ai < arcs.size; ai += stride) {
            ArcFx arc = arcs.get(ai);
            if (!arc.refreshFromClock()) continue;
            float dx = arc.x2 - arc.x1;
            float dy = arc.y2 - arc.y1;
            float length = (float)Math.sqrt(dx * dx + dy * dy);
            if (length < .001f) continue;
            float nx = -dy / length;
            float ny = dx / length;
            float alpha = MathUtils.clamp(arc.life / Math.max(.001f, arc.maxLife), 0f, 1f);
            float prevX = arc.x1;
            float prevY = arc.y1;
            for (int i = 1; i <= segments; i++) {
                float t = i / (float)segments;
                float envelope = MathUtils.sin(t * MathUtils.PI);
                float jitter = MathUtils.sin(time * 43f + i * 5.71f + arc.x1 * 2.3f) * .15f * envelope;
                float x = arc.x1 + dx * t + nx * jitter;
                float y = arc.y1 + dy * t + ny * jitter;
                shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, 1f, .72f * alpha);
                shapes.rectLine(prevX, prevY, x, y, .055f);
                if (budget.allowHeavyFx()) {
                    shapes.setColor(1f, 1f, 1f, .52f * alpha);
                    shapes.rectLine(prevX, prevY, x, y, .018f);
                }
                prevX = x;
                prevY = y;
            }
        }
    }
}
