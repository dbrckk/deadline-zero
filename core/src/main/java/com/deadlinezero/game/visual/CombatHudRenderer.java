package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.OnboardingState;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.world.WaveDirector;

/** Dedicated mobile HUD renderer. Keeps combat presentation separate from simulation. */
public final class CombatHudRenderer {
    private final Matrix4 projection = new Matrix4();
    private float damageFlash;

    public void triggerDamageFlash() {
        if (AccessibilitySettings.active().damageFlash) damageFlash = 1f;
    }

    public void update(float dt) { damageFlash = Math.max(0f, damageFlash - dt * 2.8f); }

    public void render(ShapeRenderer shapes, SpriteBatch batch, BitmapFont font,
                       Player player, WaveDirector director, Array<Enemy> enemies,
                       float width, float height) {
        projection.setToOrtho2D(0, 0, width, height);
        updateOnboarding(player, director);
        drawBars(shapes, player, director, enemies, width, height);
        drawText(batch, font, player, director, width, height);
        drawDamageVignette(shapes, width, height);
    }

    private float ui() { return AccessibilitySettings.active().uiScale; }

    private void updateOnboarding(Player player, WaveDirector director) {
        OnboardingState onboarding = OnboardingState.active();
        if (onboarding.completed()) return;
        if (player.velocity.len2() > .12f) onboarding.markMovementSeen();
        if (player.dashTimer > .05f) onboarding.markDashSeen();
        if (player.level > 1) onboarding.markUpgradeSeen();
        if (director.bossWarning() || director.bossSpawned()) onboarding.markBossSeen();
        onboarding.refreshCompletion();
    }

    private void drawBars(ShapeRenderer shapes, Player player, WaveDirector director,
                          Array<Enemy> enemies, float w, float h) {
        float s = ui();
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        float railY = h - 62f * s;
        shapes.setColor(VisualTheme.PANEL);
        shapes.rect(18f * s, railY, w * .31f, 27f * s);
        shapes.rect(w * .345f, railY, w * .31f, 27f * s);

        float hp = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
        float xp = MathUtils.clamp(player.xp / (float)Math.max(1, player.xpNext), 0f, 1f);
        shapes.setColor(hp < .28f ? VisualTheme.RED : VisualTheme.CYAN);
        shapes.rect(22f * s, h - 57f * s, (w * .31f - 8f * s) * hp, 17f * s);
        shapes.setColor(VisualTheme.VIOLET);
        shapes.rect(w * .345f + 4f * s, h - 57f * s, (w * .31f - 8f * s) * xp, 17f * s);

        float timelineX = w * .22f;
        float timelineW = w * .56f;
        float timelineY = h - 96f * s;
        shapes.setColor(VisualTheme.PANEL_ALT);
        shapes.rect(timelineX, timelineY, timelineW, 9f * s);
        boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
        if (director.bossWarning()) shapes.setColor(contrast ? Color.WHITE : VisualTheme.RED);
        else shapes.setColor(contrast ? VisualTheme.CYAN : VisualTheme.CYAN_SOFT);
        shapes.rect(timelineX, timelineY, timelineW * director.bossProgress(), 9f * s);

        Enemy boss = findBoss(enemies);
        if (boss != null) {
            float bossW = w * .58f;
            float bossX = (w - bossW) * .5f;
            float bossY = h - 130f * s;
            float ratio = MathUtils.clamp(boss.hp / Math.max(1f, boss.maxHp), 0f, 1f);
            shapes.setColor(VisualTheme.PANEL);
            shapes.rect(bossX, bossY, bossW, 19f * s);
            shapes.setColor(contrast ? Color.WHITE : VisualTheme.RED);
            shapes.rect(bossX + 3f * s, bossY + 3f * s, (bossW - 6f * s) * ratio, 13f * s);
        }

        float dashRadius = 34f * s;
        float dashX = w - 58f * s;
        float dashY = 62f * s;
        shapes.setColor(player.canDash() ? new Color(VisualTheme.CYAN).mul(1f, 1f, 1f, .22f)
                                         : new Color(VisualTheme.PANEL_ALT));
        shapes.circle(dashX, dashY, dashRadius, 32);
        shapes.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        shapes.circle(dashX, dashY, 4f * s, 16);
        shapes.end();
    }

    private void drawText(SpriteBatch batch, BitmapFont font, Player player,
                          WaveDirector director, float w, float h) {
        float s = ui();
        batch.setProjectionMatrix(projection);
        batch.begin();
        font.getData().setScale(.68f * s);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "HP  " + (int)player.hp + " / " + (int)player.maxHp, 28f * s, h - 67f * s);
        font.draw(batch, "LV " + player.level, w * .355f, h - 67f * s);
        font.draw(batch, "KILLS  " + director.kills(), w - 176f * s, h - 42f * s);
        font.draw(batch, "STAGE " + RunStageContext.stage(), 28f * s, h - 100f * s);

        boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
        if (!director.bossSpawned()) {
            int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
            font.setColor(director.bossWarning() ? (contrast ? Color.WHITE : VisualTheme.RED) : VisualTheme.MUTED);
            font.draw(batch, director.bossWarning() ? "BOSS SIGNAL  " + remaining + "s" : "BOSS ETA  " + remaining + "s",
                0f, h - 100f * s, w, Align.center, false);
        } else {
            font.setColor(contrast ? Color.WHITE : VisualTheme.RED);
            font.draw(batch, "ELIMINATE THE ALPHA", 0f, h - 100f * s, w, Align.center, false);
        }

        font.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        font.draw(batch, player.canDash() ? "DASH" : String.format("%.1f", player.dashTimer),
            w - 93f * s, 67f * s, 70f * s, Align.center, false);

        drawOnboardingHint(batch, font, w, h, s);
        batch.end();
    }

    private void drawOnboardingHint(SpriteBatch batch, BitmapFont font, float w, float h, float s) {
        OnboardingState o = OnboardingState.active();
        if (o.completed()) return;
        String hint;
        if (!o.movementSeen()) hint = "MOVE  •  DRAG LEFT SIDE / WASD";
        else if (!o.dashSeen()) hint = "DASH  •  BUTTON / SPACE";
        else if (!o.upgradeSeen()) hint = "ELIMINATE HOSTILES  •  LEVEL UP TO CHOOSE AN UPGRADE";
        else if (!o.bossSeen()) hint = "SURVIVE UNTIL THE ALPHA SIGNAL";
        else return;
        font.getData().setScale(.52f * s);
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, hint, 0f, 118f * s, w, Align.center, false);
    }

    private void drawDamageVignette(ShapeRenderer shapes, float w, float h) {
        if (damageFlash <= 0f || !AccessibilitySettings.active().damageFlash) return;
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        float alpha = (AccessibilitySettings.active().reduceFlashes ? .07f : .16f) * damageFlash;
        shapes.setColor(1f, .03f, .02f, alpha);
        float edge = Math.min(46f * ui(), Math.min(w, h) * .06f);
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
