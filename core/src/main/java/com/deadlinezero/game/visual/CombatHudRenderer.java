package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.world.WaveDirector;

/** Dedicated mobile HUD renderer. Keeps combat presentation separate from simulation. */
public final class CombatHudRenderer {
    private final Matrix4 projection = new Matrix4();
    private float damageFlash;

    public void triggerDamageFlash() { damageFlash = 1f; }
    public void update(float dt) { damageFlash = Math.max(0f, damageFlash - dt * 2.8f); }

    public void render(ShapeRenderer shapes, SpriteBatch batch, BitmapFont font,
                       Player player, WaveDirector director, Array<Enemy> enemies,
                       float width, float height) {
        projection.setToOrtho2D(0, 0, width, height);
        drawBars(shapes, player, director, enemies, width, height);
        drawText(batch, font, player, director, width, height);
        drawDamageVignette(shapes, width, height);
    }

    private void drawBars(ShapeRenderer shapes, Player player, WaveDirector director,
                          Array<Enemy> enemies, float w, float h) {
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Top HUD rails.
        shapes.setColor(VisualTheme.PANEL);
        shapes.rect(18f, h - 62f, w * .31f, 27f);
        shapes.rect(w * .345f, h - 62f, w * .31f, 27f);

        float hp = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
        float xp = MathUtils.clamp(player.xp / (float)Math.max(1, player.xpNext), 0f, 1f);
        shapes.setColor(hp < .28f ? VisualTheme.RED : VisualTheme.CYAN);
        shapes.rect(22f, h - 57f, (w * .31f - 8f) * hp, 17f);
        shapes.setColor(VisualTheme.VIOLET);
        shapes.rect(w * .345f + 4f, h - 57f, (w * .31f - 8f) * xp, 17f);

        // Mission timeline.
        float timelineX = w * .22f;
        float timelineW = w * .56f;
        float timelineY = h - 96f;
        shapes.setColor(VisualTheme.PANEL_ALT);
        shapes.rect(timelineX, timelineY, timelineW, 9f);
        shapes.setColor(director.bossWarning() ? VisualTheme.RED : VisualTheme.CYAN_SOFT);
        shapes.rect(timelineX, timelineY, timelineW * director.bossProgress(), 9f);

        Enemy boss = findBoss(enemies);
        if (boss != null) {
            float bossW = w * .58f;
            float bossX = (w - bossW) * .5f;
            float bossY = h - 130f;
            float ratio = MathUtils.clamp(boss.hp / Math.max(1f, boss.maxHp), 0f, 1f);
            shapes.setColor(VisualTheme.PANEL);
            shapes.rect(bossX, bossY, bossW, 19f);
            shapes.setColor(VisualTheme.RED);
            shapes.rect(bossX + 3f, bossY + 3f, (bossW - 6f) * ratio, 13f);
        }

        // Dash button/cooldown indicator area (visual only; input remains in GameScreen).
        float dashRadius = 34f;
        float dashX = w - 58f;
        float dashY = 62f;
        shapes.setColor(player.canDash() ? new Color(VisualTheme.CYAN).mul(1f, 1f, 1f, .22f)
                                         : new Color(VisualTheme.PANEL_ALT));
        shapes.circle(dashX, dashY, dashRadius, 32);
        shapes.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        shapes.circle(dashX, dashY, 4f, 16);

        shapes.end();
    }

    private void drawText(SpriteBatch batch, BitmapFont font, Player player,
                          WaveDirector director, float w, float h) {
        batch.setProjectionMatrix(projection);
        batch.begin();
        font.getData().setScale(.68f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "HP  " + (int)player.hp + " / " + (int)player.maxHp, 28f, h - 67f);
        font.draw(batch, "LV " + player.level, w * .355f, h - 67f);
        font.draw(batch, "KILLS  " + director.kills(), w - 176f, h - 42f);
        font.draw(batch, "STAGE " + RunStageContext.stage(), 28f, h - 100f);

        if (!director.bossSpawned()) {
            int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
            font.setColor(director.bossWarning() ? VisualTheme.RED : VisualTheme.MUTED);
            font.draw(batch, director.bossWarning() ? "BOSS SIGNAL  " + remaining + "s" : "BOSS ETA  " + remaining + "s",
                0f, h - 100f, w, Align.center, false);
        } else {
            font.setColor(VisualTheme.RED);
            font.draw(batch, "ELIMINATE THE ALPHA", 0f, h - 100f, w, Align.center, false);
        }

        font.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        font.draw(batch, player.canDash() ? "DASH" : String.format("%.1f", player.dashTimer),
            w - 93f, 67f, 70f, Align.center, false);
        batch.end();
    }

    private void drawDamageVignette(ShapeRenderer shapes, float w, float h) {
        if (damageFlash <= 0f) return;
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        float alpha = .16f * damageFlash;
        shapes.setColor(1f, .03f, .02f, alpha);
        float edge = Math.min(46f, Math.min(w, h) * .06f);
        shapes.rect(0f, 0f, w, edge);
        shapes.rect(0f, h - edge, w, edge);
        shapes.rect(0f, edge, edge, h - edge * 2f);
        shapes.rect(w - edge, edge, edge, h - edge * 2f);
        shapes.end();
    }

    private Enemy findBoss(Array<Enemy> enemies) {
        for (Enemy e : enemies) if (e.alive && e.type == Enemy.Type.BOSS) return e;
        return null;
    }
}
