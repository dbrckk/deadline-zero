package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.input.MobileCombatInput;
import com.deadlinezero.game.input.VirtualStick;
import com.deadlinezero.game.meta.OnboardingState;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.world.RunEncounterDirector;
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
        drawText(batch, font, player, director, enemies, width, height);
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
            Color identityColor = contrast ? Color.WHITE : bossColor(boss);
            shapes.setColor(identityColor);
            shapes.rect(bossX + 3f * s, bossY + 3f * s, (bossW - 6f * s) * ratio, 13f * s);

            shapes.setColor(VisualTheme.PANEL_ALT);
            float innerW = bossW - 6f * s;
            shapes.rect(bossX + 3f * s + innerW * .33f, bossY + 2f * s, 2f * s, 15f * s);
            shapes.rect(bossX + 3f * s + innerW * .66f, bossY + 2f * s, 2f * s, 15f * s);
        }

        drawMobileControls(shapes, player, w, h, s);
        shapes.end();
    }

    private void drawMobileControls(ShapeRenderer shapes, Player player, float w, float h, float s) {
        if (VirtualStick.hudActive()) {
            float max = Math.max(64f, h * .12f);
            float ox = VirtualStick.hudOriginX();
            float oy = VirtualStick.hudOriginY();
            float vx = VirtualStick.hudValueX();
            float vy = VirtualStick.hudValueY();
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .10f);
            shapes.circle(ox, oy, max, 40);
            shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .24f);
            shapes.circle(ox, oy, max * .62f, 32);
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .42f);
            shapes.circle(ox + vx * max * .68f, oy + vy * max * .68f, max * .24f, 28);
        }

        float dashRadius = (MobileCombatInput.dashDown() ? 40f : 34f) * s;
        float dashX = w - 58f * s;
        float dashY = 62f * s;
        float alpha = MobileCombatInput.dashDown() ? .38f : .22f;
        shapes.setColor(player.canDash() ? new Color(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, alpha)
                                         : new Color(VisualTheme.PANEL_ALT));
        shapes.circle(dashX, dashY, dashRadius, 32);
        shapes.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        shapes.circle(dashX, dashY, MobileCombatInput.dashDown() ? 7f * s : 4f * s, 16);
    }

    private void drawText(SpriteBatch batch, BitmapFont font, Player player,
                          WaveDirector director, Array<Enemy> enemies, float w, float h) {
        float s = ui();
        batch.setProjectionMatrix(projection);
        batch.begin();
        font.getData().setScale(.68f * s);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "HP  " + (int)player.hp + " / " + (int)player.maxHp, 28f * s, h - 67f * s);
        font.draw(batch, "LV " + player.level, w * .355f, h - 67f * s);
        font.draw(batch, "KILLS  " + director.kills(), w - 176f * s, h - 42f * s);
        font.draw(batch, "STAGE " + RunStageContext.stage(), 28f * s, h - 100f * s);
        if (RunModifierContext.active()) {
            font.getData().setScale(.48f * s);
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, "CONTRACT  " + RunModifierContext.title() + "  //  +" + RunModifierContext.rewardBonusPercent() + "%",
                28f * s, h - 122f * s, w * .30f, Align.left, false);
            font.getData().setScale(.68f * s);
        }

        WeaponLegendaryPresentation.Style legendaryStyle = WeaponLegendaryPresentation.style(player);
        if (legendaryStyle != WeaponLegendaryPresentation.Style.NONE) {
            font.getData().setScale(.50f * s);
            font.setColor(legendaryStyle.r, legendaryStyle.g, legendaryStyle.b, 1f);
            font.draw(batch, "WEAPON LEGENDARY  •  " + legendaryStyle.label,
                w * .61f, h - 118f * s, w * .36f, Align.right, false);
            font.getData().setScale(.68f * s);
        }

        boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
        Enemy boss = findBoss(enemies);
        if (!director.bossSpawned()) {
            int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
            font.setColor(director.bossWarning() ? (contrast ? Color.WHITE : VisualTheme.RED) : VisualTheme.MUTED);
            font.draw(batch, director.bossWarning() ? "BOSS SIGNAL  " + remaining + "s" : "BOSS ETA  " + remaining + "s",
                0f, h - 100f * s, w, Align.center, false);
        } else if (boss != null) {
            int phase = boss.bossPhases == null ? 1 : boss.bossPhases.phase();
            font.setColor(contrast ? Color.WHITE : bossColor(boss));
            font.draw(batch, bossName(boss) + "  //  PHASE " + phase, 0f, h - 100f * s, w, Align.center, false);
        } else {
            font.setColor(contrast ? Color.WHITE : VisualTheme.RED);
            font.draw(batch, "BOSS SIGNAL LOST", 0f, h - 100f * s, w, Align.center, false);
        }

        RunEncounterDirector.Type encounter = director.activeEncounter();
        if (encounter != RunEncounterDirector.Type.NONE && !director.bossSpawned()) {
            String name = switch (encounter) {
                case SWARM_SURGE -> "SWARM SURGE";
                case HUNTER_PACK -> "HUNTER PACK";
                case JUGGERNAUT_PUSH -> "JUGGERNAUT PUSH";
                case PHANTOM_BREACH -> "PHANTOM BREACH";
                case REGEN_BLOOM -> "REGEN BLOOM";
                case BULWARK_LINE -> "BULWARK LINE";
                default -> "";
            };
            int seconds = Math.max(1, Math.round(director.encounterSecondsRemaining()));
            font.getData().setScale(.58f * s);
            font.setColor(contrast ? Color.WHITE : VisualTheme.GOLD);
            font.draw(batch, name + "  •  HOLD THE LINE  " + seconds + "s", 0f, h - 126f * s, w, Align.center, false);
            font.getData().setScale(.68f * s);
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

    private BossIdentity bossIdentity(Enemy boss) {
        return boss != null && boss.bossCombat != null ? boss.bossCombat.identity() : BossIdentity.ALPHA;
    }

    private String bossName(Enemy boss) {
        return switch (bossIdentity(boss)) {
            case REVENANT -> "REVENANT";
            case WARDEN -> "WARDEN";
            case HARVESTER -> "HARVESTER";
            case NULL_ARCHON -> "NULL ARCHON";
            default -> "ALPHA";
        };
    }

    private Color bossColor(Enemy boss) {
        return switch (bossIdentity(boss)) {
            case REVENANT -> VisualTheme.VIOLET;
            case WARDEN -> VisualTheme.GOLD;
            case HARVESTER -> new Color(.96f, .42f, .10f, 1f);
            case NULL_ARCHON -> new Color(.52f, .42f, 1f, 1f);
            default -> VisualTheme.RED;
        };
    }
}
