package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;

/**
 * Geometry-first combat readability layer. Role information remains legible without relying on
 * enemy tint, and the pass owns no simulation state.
 */
public final class CombatReadabilityPass {
    private final ShapeRenderer shapes = new ShapeRenderer();

    public void drawUnderlay(SpriteBatch batch, Player player, Array<Enemy> enemies,
                             float time, GraphicsQuality quality) {
        if (batch == null || player == null || enemies == null) return;
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        drawPlayerGrounding(player);
        boolean reduced = quality == GraphicsQuality.LOW;
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive) continue;
            HordeRolePresentation.RoleStyle style = HordeRolePresentation.style(enemy.type);
            float pulse = AccessibilitySettings.active().reducedMotion
                ? 1f : 1f + MathUtils.sin(time * 4f + enemy.position.x * .31f) * .035f;
            float rx = enemy.radius * style.shadowScale() * 1.18f;
            float ry = enemy.radius * style.shadowScale() * .42f;
            shapes.setColor(.005f, .007f, .008f, enemy.type == Enemy.Type.PHANTOM ? .28f : .52f);
            shapes.ellipse(enemy.position.x - rx, enemy.position.y - ry * .80f, rx * 2f, ry * 2f);

            if (reduced) continue;
            switch (style.motion()) {
                case FAST -> drawSpeedTick(enemy, pulse);
                case HEAVY -> drawHeavyFootprint(enemy, style.markerScale());
                case SUPPORT -> drawSupportBase(enemy, style.markerScale());
                case PHASE -> drawPhaseFootprint(enemy, pulse);
                default -> { }
            }
        }
        shapes.end();
    }

    public void drawOverlay(SpriteBatch batch, Player player, Array<Enemy> enemies,
                            float time, GraphicsQuality quality) {
        if (batch == null || player == null || enemies == null) return;
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        boolean highContrast = AccessibilitySettings.active().highContrastTelegraphs;
        boolean reduced = quality == GraphicsQuality.LOW;

        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive) continue;
            HordeRolePresentation.RoleStyle style = HordeRolePresentation.style(enemy.type);
            drawSilhouetteMarker(enemy, style, highContrast);
            if (reduced && enemy.type != Enemy.Type.BOSS && enemy.type != Enemy.Type.RANGED) continue;

            switch (style.telegraph()) {
                case PREFIRE -> drawPrefire(enemy, player, highContrast, time);
                case SUPPORT -> drawSupportMarker(enemy, highContrast, time);
                case GUARDED -> drawGuardMarker(enemy, highContrast);
                case PHASED -> drawBossMarker(enemy, highContrast, time);
                default -> { }
            }
        }
        shapes.end();
    }

    private void drawPlayerGrounding(Player player) {
        float rx = player.radius * 1.20f;
        float ry = player.radius * .42f;
        shapes.setColor(.005f, .008f, .010f, .62f);
        shapes.ellipse(player.position.x - rx, player.position.y - ry * .78f, rx * 2f, ry * 2f);
        shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .18f);
        shapes.rect(player.position.x - .24f, player.position.y - player.radius - .18f, .48f, .035f);
    }

    private void drawSpeedTick(Enemy enemy, float pulse) {
        float vx = enemy.velocity.x;
        float vy = enemy.velocity.y;
        float len = (float) Math.sqrt(vx * vx + vy * vy);
        if (len < .05f) return;
        vx /= len;
        vy /= len;
        float backX = enemy.position.x - vx * enemy.radius * 1.15f;
        float backY = enemy.position.y - vy * enemy.radius * 1.15f;
        shapes.setColor(1f, 1f, 1f, .18f);
        shapes.rectLine(backX, backY, backX - vx * (.60f * pulse), backY - vy * (.60f * pulse), .045f);
    }

    private void drawHeavyFootprint(Enemy enemy, float scale) {
        float w = enemy.radius * 1.45f * scale;
        shapes.setColor(.78f, .82f, .84f, .15f);
        shapes.rect(enemy.position.x - w, enemy.position.y - enemy.radius * .88f, w * .52f, .055f);
        shapes.rect(enemy.position.x + w * .48f, enemy.position.y - enemy.radius * .88f, w * .52f, .055f);
    }

    private void drawSupportBase(Enemy enemy, float scale) {
        float r = enemy.radius * 1.35f * scale;
        shapes.setColor(.72f, .90f, .76f, .12f);
        shapes.circle(enemy.position.x, enemy.position.y, r, 24);
        shapes.setColor(.01f, .02f, .02f, .88f);
        shapes.circle(enemy.position.x, enemy.position.y, r * .86f, 24);
    }

    private void drawPhaseFootprint(Enemy enemy, float pulse) {
        float r = enemy.radius * 1.16f * pulse;
        shapes.setColor(.80f, .72f, 1f, .12f);
        for (int i = 0; i < 4; i++) {
            float a = i * 90f + 45f;
            float x = enemy.position.x + MathUtils.cosDeg(a) * r;
            float y = enemy.position.y + MathUtils.sinDeg(a) * r;
            shapes.circle(x, y, .075f, 8);
        }
    }

    private void drawSilhouetteMarker(Enemy enemy, HordeRolePresentation.RoleStyle style, boolean highContrast) {
        float r = enemy.radius * style.markerScale();
        Color c = highContrast ? Color.WHITE : VisualTheme.TEXT_DIM;
        shapes.setColor(c.r, c.g, c.b, .28f + style.rimStrength() * .24f);
        switch (style.silhouetteClass()) {
            case RUNNER -> {
                shapes.rect(enemy.position.x - r * .58f, enemy.position.y + enemy.radius + .10f, r * 1.16f, .035f);
            }
            case BRUTE -> {
                shapes.rect(enemy.position.x - r, enemy.position.y - enemy.radius * .15f, .055f, r * .82f);
                shapes.rect(enemy.position.x + r - .055f, enemy.position.y - enemy.radius * .15f, .055f, r * .82f);
            }
            case RANGED -> {
                float y = enemy.position.y + enemy.radius + .18f;
                shapes.rect(enemy.position.x - r * .52f, y, r * .36f, .045f);
                shapes.rect(enemy.position.x + r * .16f, y, r * .36f, .045f);
            }
            case ELITE -> drawDiamond(enemy.position.x, enemy.position.y + enemy.radius + .28f, .16f + r * .10f);
            case SHIELDED -> {
                float top = enemy.position.y + enemy.radius + .20f;
                shapes.rect(enemy.position.x - r * .55f, top, r * 1.10f, .05f);
                shapes.rect(enemy.position.x - r * .55f, top - .24f, .05f, .24f);
                shapes.rect(enemy.position.x + r * .50f, top - .24f, .05f, .24f);
            }
            case REGENERATOR -> {
                float y = enemy.position.y + enemy.radius + .25f;
                shapes.rect(enemy.position.x - .20f, y, .40f, .055f);
                shapes.rect(enemy.position.x - .027f, y - .17f, .055f, .40f);
            }
            case PHANTOM -> {
                float y = enemy.position.y + enemy.radius + .22f;
                shapes.rectLine(enemy.position.x - .18f, y - .10f, enemy.position.x + .18f, y + .10f, .035f);
                shapes.rectLine(enemy.position.x - .18f, y + .10f, enemy.position.x + .18f, y - .10f, .035f);
            }
            case BOSS -> {
                float y = enemy.position.y + enemy.radius + .30f;
                shapes.rect(enemy.position.x - r * .75f, y, r * 1.5f, .07f);
                shapes.rect(enemy.position.x - r * .75f, y - .28f, .07f, .28f);
                shapes.rect(enemy.position.x + r * .68f, y - .28f, .07f, .28f);
            }
            default -> { }
        }
    }

    private void drawPrefire(Enemy enemy, Player player, boolean highContrast, float time) {
        if (enemy.attack.state() != EnemyState.TELEGRAPHING) return;
        float alpha = AccessibilitySettings.active().reducedMotion ? .45f : .34f + .18f * (.5f + .5f * MathUtils.sin(time * 12f));
        Color c = highContrast ? Color.WHITE : VisualTheme.danger();
        shapes.setColor(c.r, c.g, c.b, alpha);
        shapes.rectLine(enemy.position.x, enemy.position.y, player.position.x, player.position.y, .035f);
        float dx = player.position.x - enemy.position.x;
        float dy = player.position.y - enemy.position.y;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > .001f) {
            dx /= len;
            dy /= len;
            float px = -dy;
            float py = dx;
            float mx = enemy.position.x + dx * Math.min(2.2f, len * .45f);
            float my = enemy.position.y + dy * Math.min(2.2f, len * .45f);
            shapes.rectLine(mx - px * .20f, my - py * .20f, mx + px * .20f, my + py * .20f, .055f);
        }
    }

    private void drawSupportMarker(Enemy enemy, boolean highContrast, float time) {
        float pulse = AccessibilitySettings.active().reducedMotion ? 1f : .88f + .12f * MathUtils.sin(time * 5f);
        Color c = highContrast ? Color.WHITE : VisualTheme.positive();
        float r = enemy.radius * 1.45f * pulse;
        shapes.setColor(c.r, c.g, c.b, .30f);
        for (int i = 0; i < 4; i++) {
            float a = i * 90f;
            float x = enemy.position.x + MathUtils.cosDeg(a) * r;
            float y = enemy.position.y + MathUtils.sinDeg(a) * r;
            shapes.rect(x - .09f, y - .025f, .18f, .05f);
        }
    }

    private void drawGuardMarker(Enemy enemy, boolean highContrast) {
        Color c = highContrast ? Color.WHITE : VisualTheme.GOLD;
        float r = enemy.radius * 1.28f;
        shapes.setColor(c.r, c.g, c.b, .34f);
        shapes.rect(enemy.position.x - r, enemy.position.y + r * .62f, r * .38f, .055f);
        shapes.rect(enemy.position.x + r * .62f, enemy.position.y + r * .62f, r * .38f, .055f);
        shapes.rect(enemy.position.x - r, enemy.position.y + r * .25f, .055f, r * .40f);
        shapes.rect(enemy.position.x + r - .055f, enemy.position.y + r * .25f, .055f, r * .40f);
    }

    private void drawBossMarker(Enemy enemy, boolean highContrast, float time) {
        if (enemy.attack.state() != EnemyState.TELEGRAPHING && (enemy.bossCombat == null || !enemy.bossCombat.charging())) return;
        float pulse = AccessibilitySettings.active().reducedMotion ? 1f : .92f + .08f * MathUtils.sin(time * 10f);
        Color c = highContrast ? Color.WHITE : VisualTheme.danger();
        float r = enemy.radius * 1.80f * pulse;
        shapes.setColor(c.r, c.g, c.b, .26f);
        for (int i = 0; i < 8; i++) {
            float a = i * 45f;
            float x1 = enemy.position.x + MathUtils.cosDeg(a) * r;
            float y1 = enemy.position.y + MathUtils.sinDeg(a) * r;
            float x2 = enemy.position.x + MathUtils.cosDeg(a) * (r + .38f);
            float y2 = enemy.position.y + MathUtils.sinDeg(a) * (r + .38f);
            shapes.rectLine(x1, y1, x2, y2, .055f);
        }
    }

    private void drawDiamond(float x, float y, float r) {
        shapes.rectLine(x, y + r, x + r, y, .04f);
        shapes.rectLine(x + r, y, x, y - r, .04f);
        shapes.rectLine(x, y - r, x - r, y, .04f);
        shapes.rectLine(x - r, y, x, y + r, .04f);
    }

    public void dispose() {
        shapes.dispose();
    }
}
