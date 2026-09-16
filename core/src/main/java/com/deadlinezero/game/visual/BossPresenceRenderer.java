package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;

/** Presentation-only emphasis for boss escalation and critical player health. */
public final class BossPresenceRenderer {
    private final ShapeRenderer shapes = new ShapeRenderer();

    public void draw(SpriteBatch batch, Player player, Array<Enemy> enemies, float time, GraphicsQuality quality) {
        if (batch == null || player == null || enemies == null) return;
        AccessibilitySettings a = AccessibilitySettings.active();
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        drawCriticalHealth(player, time, a);
        for (Enemy enemy : enemies) {
            if (enemy == null || !enemy.alive || enemy.type != Enemy.Type.BOSS) continue;
            drawBoss(enemy, time, quality, a);
        }
        shapes.end();
    }

    private void drawCriticalHealth(Player player, float time, AccessibilitySettings a) {
        float hpRatio = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
        BossPresenceProfile.LowHpProfile p = BossPresenceProfile.lowHp(hpRatio, !a.reducedMotion);
        if (p.edgeAlpha() <= 0f) return;

        float pulse = a.reducedMotion ? 1f : 1f + MathUtils.sin(time * 5.2f) * p.motionPulse();
        float r = player.radius * (1.55f + (1f - hpRatio) * .32f) * pulse;
        Color danger = a.highContrastTelegraphs ? Color.WHITE : VisualTheme.danger();
        shapes.setColor(danger.r, danger.g, danger.b, p.edgeAlpha());

        float marker = .30f + p.warningWidth() * .006f;
        for (int i = 0; i < 4; i++) {
            float angle = 45f + i * 90f;
            float cx = player.position.x + MathUtils.cosDeg(angle) * r;
            float cy = player.position.y + MathUtils.sinDeg(angle) * r;
            float tx = -MathUtils.sinDeg(angle);
            float ty = MathUtils.cosDeg(angle);
            shapes.rectLine(cx - tx * marker, cy - ty * marker, cx + tx * marker, cy + ty * marker, .045f);
        }
    }

    private void drawBoss(Enemy boss, float time, GraphicsQuality quality, AccessibilitySettings a) {
        int phase = boss.bossPhases == null ? 1 : boss.bossPhases.phase();
        BossIdentity identity = boss.bossCombat == null ? BossIdentity.ALPHA : boss.bossCombat.identity();
        BossPresenceProfile.PhaseProfile p = BossPresenceProfile.forPhase(identity, phase);
        boolean reduced = a.reducedMotion || quality == GraphicsQuality.LOW;
        float pulse = reduced ? 1f : 1f + MathUtils.sin(time * p.pulseRate()) * .032f * p.intensity();
        float r = boss.radius * p.ringScale() * pulse;
        Color c = a.highContrastTelegraphs ? Color.WHITE : identityColor(identity);
        int segments = Math.max(16, p.telegraphSegments() * 6);

        shapes.setColor(.005f, .006f, .008f, .28f);
        shapes.circle(boss.position.x, boss.position.y, r * 1.04f, segments);
        shapes.setColor(c.r, c.g, c.b, .06f + p.intensity() * .08f);
        shapes.circle(boss.position.x, boss.position.y, r, segments);
        shapes.setColor(.01f, .012f, .015f, .72f);
        shapes.circle(boss.position.x, boss.position.y, r * .91f, segments);

        shapes.setColor(c.r, c.g, c.b, .24f + p.intensity() * .13f);
        int markers = quality == GraphicsQuality.LOW ? Math.min(4, p.markerCount()) : p.markerCount();
        for (int i = 0; i < markers; i++) {
            float angle = i * (360f / markers) + (reduced ? 0f : time * 8f);
            float x1 = boss.position.x + MathUtils.cosDeg(angle) * r;
            float y1 = boss.position.y + MathUtils.sinDeg(angle) * r;
            float x2 = boss.position.x + MathUtils.cosDeg(angle) * (r + .22f * p.intensity());
            float y2 = boss.position.y + MathUtils.sinDeg(angle) * (r + .22f * p.intensity());
            shapes.rectLine(x1, y1, x2, y2, p.lineWeight());
        }
    }

    private Color identityColor(BossIdentity identity) {
        if (identity == null) return VisualTheme.danger();
        return switch (identity) {
            case REVENANT -> VisualTheme.VIOLET;
            case WARDEN -> VisualTheme.GOLD;
            case HARVESTER -> Color.ORANGE;
            case NULL_ARCHON -> new Color(.58f, .46f, 1f, 1f);
            case FROST_COLOSSUS -> VisualTheme.CYAN_SOFT;
            default -> VisualTheme.danger();
        };
    }

    public void dispose() {
        shapes.dispose();
    }
}
