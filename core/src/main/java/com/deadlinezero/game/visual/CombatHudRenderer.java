package com.deadlinezero.game.visual;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.config.Localization;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.input.MobileCombatInput;
import com.deadlinezero.game.input.VirtualStick;
import com.deadlinezero.game.meta.OnboardingState;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.world.RunEncounterDirector;
import com.deadlinezero.game.world.WaveDirector;

/** Dedicated responsive mobile HUD renderer. Keeps combat presentation separate from simulation/input. */
public final class CombatHudRenderer {
    public enum HintMode { NONE, TOAST }

    private static final Color HARVESTER_COLOR = new Color(.96f, .42f, .10f, 1f);
    private static final Color NULL_ARCHON_COLOR = new Color(.52f, .42f, 1f, 1f);
    private static final float CONTROL_IDLE_ALPHA = .15f;
    private static final float CONTROL_ACTIVE_ALPHA = .36f;

    private final Matrix4 projection = new Matrix4();
    private float damageFlash;
    private final Localization i18n;

    public CombatHudRenderer(Localization i18n) {
        if (i18n == null) throw new IllegalArgumentException("i18n");
        this.i18n = i18n;
    }

    public static HintMode hintModeFor(boolean onboardingComplete) {
        return onboardingComplete ? HintMode.NONE : HintMode.TOAST;
    }

    public static float controlIdleAlpha() { return CONTROL_IDLE_ALPHA; }
    public static float controlActiveAlpha() { return CONTROL_ACTIVE_ALPHA; }

    public void triggerDamageFlash() {
        if (AccessibilitySettings.active().damageFlash) damageFlash = 1f;
    }

    public void update(float dt) { damageFlash = Math.max(0f, damageFlash - dt * 2.8f); }

    public void render(ShapeRenderer shapes, SpriteBatch batch, BitmapFont font,
                       Player player, WaveDirector director, Array<Enemy> enemies,
                       float width, float height) {
        Enemy boss = findBoss(enemies);
        CombatHudLayout.Layout layout = CombatHudLayout.compute((int) width, (int) height, ui(), boss != null);
        projection.setToOrtho2D(0, 0, layout.logicalWidth(), layout.logicalHeight());
        updateOnboarding(player, director);
        drawHudShapes(shapes, player, director, boss, layout, width, height);
        drawText(batch, font, player, director, boss, layout);
        drawDamageVignette(shapes, layout.logicalWidth(), layout.logicalHeight());
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

    private void drawHudShapes(ShapeRenderer shapes, Player player, WaveDirector director, Enemy boss,
                               CombatHudLayout.Layout layout, float physicalW, float physicalH) {
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        drawSurvivalCluster(shapes, player, layout);
        drawHordeStatus(shapes, layout);

        if (boss != null && layout.boss() != null) drawBossRail(shapes, boss, layout.boss());

        if (hintModeFor(OnboardingState.active().completed()) == HintMode.TOAST) {
            Rectangle toast = layout.toast();
            shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .78f);
            shapes.rect(toast.x, toast.y, toast.width, toast.height);
            shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .78f);
            shapes.rect(toast.x, toast.y, 3f, toast.height);
        }

        drawMobileControls(shapes, player, layout, physicalW, physicalH);
        shapes.end();
    }

    private void drawSurvivalCluster(ShapeRenderer shapes, Player player, CombatHudLayout.Layout layout) {
        Rectangle survival = layout.survival();
        Rectangle badge = layout.levelBadge();
        Rectangle xp = layout.xpRail();

        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .82f);
        shapes.rect(survival.x, survival.y, survival.width, survival.height);
        shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .62f);
        shapes.rect(survival.x, survival.y, 2f, survival.height);
        shapes.rect(survival.x, survival.y, survival.width, 1f);

        float hp = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
        Color hpColor = hp < .28f ? VisualTheme.danger() : VisualTheme.accent();
        shapes.setColor(VisualTheme.SURFACE_2.r, VisualTheme.SURFACE_2.g, VisualTheme.SURFACE_2.b, .85f);
        shapes.rect(survival.x + 10f, survival.y + 8f, survival.width - 20f, 7f);
        shapes.setColor(hpColor.r, hpColor.g, hpColor.b, .95f);
        shapes.rect(survival.x + 10f, survival.y + 8f, Math.max(0f, (survival.width - 20f) * hp), 7f);

        shapes.setColor(VisualTheme.SURFACE_1.r, VisualTheme.SURFACE_1.g, VisualTheme.SURFACE_1.b, .78f);
        shapes.rect(badge.x, badge.y, badge.width, badge.height);
        shapes.setColor(VisualTheme.VIOLET.r, VisualTheme.VIOLET.g, VisualTheme.VIOLET.b, .68f);
        shapes.rect(badge.x, badge.y, 3f, badge.height);

        float xpRatio = MathUtils.clamp(player.xp / (float) Math.max(1, player.xpNext), 0f, 1f);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .88f);
        shapes.rect(xp.x, xp.y, xp.width, xp.height);
        shapes.setColor(VisualTheme.VIOLET.r, VisualTheme.VIOLET.g, VisualTheme.VIOLET.b, .92f);
        shapes.rect(xp.x, xp.y, Math.max(0f, xp.width * xpRatio), xp.height);
    }

    private void drawHordeStatus(ShapeRenderer shapes, CombatHudLayout.Layout layout) {
        Rectangle status = layout.hordeStatus();
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .58f);
        shapes.rect(status.x, status.y, status.width, status.height);
        shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .72f);
        shapes.rect(status.x + status.width - 3f, status.y, 3f, status.height);
    }

    private void drawBossRail(ShapeRenderer shapes, Enemy boss, Rectangle rail) {
        float ratio = MathUtils.clamp(boss.hp / Math.max(1f, boss.maxHp), 0f, 1f);
        Color identity = AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : bossColor(boss);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .88f);
        shapes.rect(rail.x, rail.y, rail.width, rail.height);
        shapes.setColor(identity.r, identity.g, identity.b, .94f);
        shapes.rect(rail.x + 2f, rail.y + 2f, Math.max(0f, (rail.width - 4f) * ratio), rail.height - 4f);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .92f);
        shapes.rect(rail.x + rail.width * .33f, rail.y + 2f, 2f, rail.height - 4f);
        shapes.rect(rail.x + rail.width * .66f, rail.y + 2f, 2f, rail.height - 4f);
    }

    private void drawMobileControls(ShapeRenderer shapes, Player player, CombatHudLayout.Layout layout,
                                    float physicalW, float physicalH) {
        if (VirtualStick.hudActive()) {
            float physicalMax = Math.max(64f, physicalH * .12f);
            float max = physicalMax * layout.scaleY();
            float ox = layout.toLogicalX(VirtualStick.hudOriginX());
            float oy = layout.toLogicalY(VirtualStick.hudOriginY());
            float vx = VirtualStick.hudValueX();
            float vy = VirtualStick.hudValueY();

            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, CONTROL_IDLE_ALPHA);
            shapes.circle(ox, oy, max, 40);
            shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .78f);
            shapes.circle(ox, oy, max * .78f, 40);
            shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, CONTROL_ACTIVE_ALPHA);
            shapes.circle(ox + vx * max * .68f, oy + vy * max * .68f, max * .22f, 28);
        }

        boolean down = MobileCombatInput.dashDown();
        float radius = layout.dashRadius() * (down ? 1.06f : 1f);
        float alpha = down ? CONTROL_ACTIVE_ALPHA : CONTROL_IDLE_ALPHA;
        Color ring = player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED;
        shapes.setColor(ring.r, ring.g, ring.b, alpha);
        shapes.circle(layout.dashX(), layout.dashY(), radius, 36);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .76f);
        shapes.circle(layout.dashX(), layout.dashY(), radius * .78f, 36);
        if (player.canDash()) {
            shapes.setColor(ring.r, ring.g, ring.b, down ? .76f : .38f);
            shapes.circle(layout.dashX(), layout.dashY(), radius * .12f, 18);
        }
    }

    private void drawText(SpriteBatch batch, BitmapFont font, Player player, WaveDirector director,
                          Enemy boss, CombatHudLayout.Layout layout) {
        float s = MathUtils.clamp(ui(), .85f, 1.35f);
        batch.setProjectionMatrix(projection);
        batch.begin();

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * s);
        font.setColor(VisualTheme.TEXT_STRONG);
        Rectangle survival = layout.survival();
        font.draw(batch, f("hud.hp", (int) player.hp, (int) player.maxHp),
            survival.x + 10f, survival.y + survival.height - 9f * s,
            survival.width - 20f, Align.left, false);

        Rectangle badge = layout.levelBadge();
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("hud.level", player.level), badge.x + 6f,
            badge.y + badge.height * .62f, badge.width - 12f, Align.center, false);

        Rectangle status = layout.hordeStatus();
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("hud.kills", director.kills()), status.x + 10f,
            status.y + status.height * .68f, status.width * .52f, Align.left, false);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("hud.stage", RunStageContext.stage()), status.x + status.width * .48f,
            status.y + status.height * .68f, status.width * .46f, Align.right, false);

        boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
        if (!director.bossSpawned()) {
            int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
            font.setColor(director.bossWarning() ? (contrast ? Color.WHITE : VisualTheme.danger()) : VisualTheme.TEXT_DIM);
            font.draw(batch, director.bossWarning() ? f("hud.bossSignal", remaining) : f("hud.bossEta", remaining),
                layout.logicalWidth() * .36f, survival.y + survival.height * .66f,
                layout.logicalWidth() * .28f, Align.center, false);
        } else if (boss != null && layout.boss() != null) {
            int phase = boss.bossPhases == null ? 1 : boss.bossPhases.phase();
            font.setColor(contrast ? Color.WHITE : bossColor(boss));
            font.draw(batch, f("hud.bossPhase", bossName(boss), phase),
                layout.boss().x, layout.boss().y + layout.boss().height + 18f * s,
                layout.boss().width, Align.center, false);
        } else {
            font.setColor(contrast ? Color.WHITE : VisualTheme.danger());
            font.draw(batch, t("hud.bossLost"), layout.logicalWidth() * .36f,
                survival.y + survival.height * .66f, layout.logicalWidth() * .28f, Align.center, false);
        }

        float chipY = layout.xpRail().y - 12f * s;
        if (RunModifierContext.active()) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .82f * s);
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, f("hud.contract", RunModifierContext.title(), RunModifierContext.rewardBonusPercent()),
                survival.x, chipY, Math.min(410f, layout.logicalWidth() * .32f), Align.left, false);
        }

        WeaponLegendaryPresentation.Style legendaryStyle = WeaponLegendaryPresentation.style(player);
        if (legendaryStyle != WeaponLegendaryPresentation.Style.NONE) {
            font.setColor(legendaryStyle.r, legendaryStyle.g, legendaryStyle.b, 1f);
            font.draw(batch, f("hud.weaponLegendary", legendaryStyle.label),
                status.x - 360f, chipY, 350f, Align.right, false);
        }

        RunEncounterDirector.Type encounter = director.activeEncounter();
        if (encounter != RunEncounterDirector.Type.NONE && !director.bossSpawned()) {
            String name = switch (encounter) {
                case SWARM_SURGE -> t("encounter.swarm_surge");
                case HUNTER_PACK -> t("encounter.hunter_pack");
                case JUGGERNAUT_PUSH -> t("encounter.juggernaut_push");
                case PHANTOM_BREACH -> t("encounter.phantom_breach");
                case REGEN_BLOOM -> t("encounter.regen_bloom");
                case BULWARK_LINE -> t("encounter.bulwark_line");
                default -> "";
            };
            int seconds = Math.max(1, Math.round(director.encounterSecondsRemaining()));
            font.setColor(contrast ? Color.WHITE : VisualTheme.GOLD);
            font.draw(batch, f("hud.encounter", name, seconds),
                layout.logicalWidth() * .34f, chipY, layout.logicalWidth() * .32f, Align.center, false);
        }

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .90f * s);
        font.setColor(player.canDash() ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
        font.draw(batch, player.canDash() ? t("hud.dash") : String.format(java.util.Locale.ROOT, "%.1f", player.dashTimer),
            layout.dashX() - layout.dashRadius(), layout.dashY() + 4f * s,
            layout.dashRadius() * 2f, Align.center, false);

        drawOnboardingHint(batch, font, layout, s);
        batch.end();
    }

    private void drawOnboardingHint(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
        OnboardingState o = OnboardingState.active();
        if (hintModeFor(o.completed()) == HintMode.NONE) return;

        String key = CombatOnboardingHintPresentation.keyFor(
            o.movementSeen(), o.dashSeen(), o.upgradeSeen(), o.bossSeen());
        if (key == null) return;
        String hint = t(key);

        Rectangle r = layout.toast();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .88f * s);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, hint, r.x + 14f, r.y + r.height * .62f, r.width - 28f, Align.left, true);
    }

    private boolean isAndroid() {
        return Gdx.app != null && Gdx.app.getType() == Application.ApplicationType.Android;
    }

    private void drawDamageVignette(ShapeRenderer shapes, float w, float h) {
        AccessibilitySettings settings = AccessibilitySettings.active();
        if (damageFlash <= 0f || !settings.damageFlash) return;
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        float alpha = (settings.minimizesFlashes() ? .07f : .16f) * damageFlash;
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
            case REVENANT -> t("boss.revenant");
            case WARDEN -> t("boss.warden");
            case HARVESTER -> t("boss.harvester");
            case NULL_ARCHON -> t("boss.null_archon");
            default -> t("boss.alpha");
        };
    }

    private String t(String key) { return i18n.text(key); }
    private String f(String key, Object... args) { return i18n.format(key, args); }

    private Color bossColor(Enemy boss) {
        return switch (bossIdentity(boss)) {
            case REVENANT -> VisualTheme.VIOLET;
            case WARDEN -> VisualTheme.GOLD;
            case HARVESTER -> HARVESTER_COLOR;
            case NULL_ARCHON -> NULL_ARCHON_COLOR;
            default -> VisualTheme.danger();
        };
    }
}
