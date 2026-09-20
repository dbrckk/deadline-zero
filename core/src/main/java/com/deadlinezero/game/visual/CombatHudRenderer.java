package com.deadlinezero.game.visual;

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
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.world.RunEncounterDirector;
import com.deadlinezero.game.world.WaveDirector;

/** Dedicated responsive mobile HUD renderer. Keeps combat presentation separate from simulation/input. */
public final class CombatHudRenderer {
    private static final Color HARVESTER_COLOR = new Color(.96f, .42f, .10f, 1f);
    private static final Color NULL_ARCHON_COLOR = new Color(.52f, .42f, 1f, 1f);
    private final Matrix4 projection = new Matrix4();
    private final String[] activeBuildKeys = new String[2];
    private float damageFlash;
    private final Localization i18n;

    public CombatHudRenderer(Localization i18n) {
        if (i18n == null) throw new IllegalArgumentException("i18n");
        this.i18n = i18n;
    }

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
        drawBars(shapes, player, director, boss, layout, width, height);
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

    private void drawBars(ShapeRenderer shapes, Player player, WaveDirector director, Enemy boss,
                          CombatHudLayout.Layout layout, float physicalW, float physicalH) {
        shapes.setProjectionMatrix(projection);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        Rectangle hpRect = layout.hp();
        Rectangle xpRect = layout.xp();

        UiRenderer.card(shapes, hpRect.x, hpRect.y, hpRect.width, hpRect.height, false, false);
        UiRenderer.card(shapes, xpRect.x, xpRect.y, xpRect.width, xpRect.height, false, false);
        float hp = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
        float xp = MathUtils.clamp(player.xp / (float) Math.max(1, player.xpNext), 0f, 1f);
        UiRenderer.progress(shapes, hpRect.x + 5f, hpRect.y + 5f, hpRect.width - 10f, hpRect.height - 10f,
            hp, hp < .28f ? VisualTheme.danger() : VisualTheme.accent());
        UiRenderer.progress(shapes, xpRect.x + 5f, xpRect.y + 5f, xpRect.width - 10f, xpRect.height - 10f,
            xp, VisualTheme.VIOLET);
        drawRailChrome(shapes, hpRect, xpRect, hp, xp);

        Rectangle timeline = layout.timeline();
        UiRenderer.progress(shapes, timeline.x, timeline.y, timeline.width, timeline.height,
            director.bossProgress(), director.bossWarning()
                ? (AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : VisualTheme.danger())
                : (AccessibilitySettings.active().highContrastTelegraphs ? VisualTheme.CYAN : VisualTheme.CYAN_SOFT));

        if (boss != null && layout.boss() != null) {
            Rectangle b = layout.boss();
            float ratio = MathUtils.clamp(boss.hp / Math.max(1f, boss.maxHp), 0f, 1f);
            UiRenderer.card(shapes, b.x, b.y, b.width, b.height, true, false);
            Color identity = AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : bossColor(boss);
            UiRenderer.progress(shapes, b.x + 4f, b.y + 4f, b.width - 8f, b.height - 8f, ratio, identity);
            shapes.setColor(VisualTheme.SURFACE_0);
            shapes.rect(b.x + b.width * .33f, b.y + 3f, 2f, b.height - 6f);
            shapes.rect(b.x + b.width * .66f, b.y + 3f, 2f, b.height - 6f);
        }

        OnboardingState onboarding = OnboardingState.active();
        if (!onboarding.completed()) {
            Rectangle hint = layout.onboarding();
            UiRenderer.card(shapes, hint.x, hint.y, hint.width, hint.height, false, false);
        }

        drawMobileControls(shapes, player, layout, physicalW, physicalH);
        shapes.end();
    }

    private void drawRailChrome(ShapeRenderer shapes, Rectangle hpRect, Rectangle xpRect,
                                float hpRatio, float xpRatio) {
        float s = MathUtils.clamp(ui(), .85f, 1.35f);
        float accentH = Math.max(2f, 2.5f * s);

        shapes.setColor(VisualTheme.accent());
        shapes.rect(hpRect.x + 5f, hpRect.y + hpRect.height - 5f - accentH,
            Math.max(18f, (hpRect.width - 10f) * MathUtils.clamp(hpRatio, 0f, 1f)), accentH);
        shapes.setColor(VisualTheme.VIOLET);
        shapes.rect(xpRect.x + 5f, xpRect.y + xpRect.height - 5f - accentH,
            Math.max(18f, (xpRect.width - 10f) * MathUtils.clamp(xpRatio, 0f, 1f)), accentH);

        drawProgressTicks(shapes, hpRect.x + 5f, hpRect.y + 5f, hpRect.width - 10f, hpRect.height - 10f, 4);
        drawProgressTicks(shapes, xpRect.x + 5f, xpRect.y + 5f, xpRect.width - 10f, xpRect.height - 10f, 4);

        if (hpRatio < .28f) {
            float urgency = 1f - MathUtils.clamp(hpRatio / .28f, 0f, 1f);
            Color danger = VisualTheme.danger();
            shapes.setColor(danger.r, danger.g, danger.b, .18f + urgency * .22f);
            float t = Math.max(2f, 2.5f * s);
            shapes.rect(hpRect.x - t, hpRect.y - t, hpRect.width + t * 2f, t);
            shapes.rect(hpRect.x - t, hpRect.y + hpRect.height, hpRect.width + t * 2f, t);
            shapes.rect(hpRect.x - t, hpRect.y, t, hpRect.height);
            shapes.rect(hpRect.x + hpRect.width, hpRect.y, t, hpRect.height);
        }
    }

    private void drawProgressTicks(ShapeRenderer shapes, float x, float y, float w, float h, int segments) {
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .72f);
        float tickW = Math.max(1f, 1.5f * ui());
        for (int i = 1; i < segments; i++) {
            float px = x + w * i / segments;
            shapes.rect(px - tickW * .5f, y + 2f, tickW, Math.max(0f, h - 4f));
        }
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
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .09f);
            shapes.circle(ox, oy, max, 40);
            shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .20f);
            shapes.circle(ox, oy, max * .62f, 32);
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .50f);
            shapes.circle(ox + vx * max * .68f, oy + vy * max * .68f, max * .24f, 28);
        }

        float radius = layout.dashRadius() * (MobileCombatInput.dashDown() ? 1.12f : 1f);
        float alpha = MobileCombatInput.dashDown() ? .46f : .26f;
        Color dashColor = player.canDash() ? VisualTheme.accent() : VisualTheme.MUTED;
        shapes.setColor(dashColor.r, dashColor.g, dashColor.b, alpha);
        shapes.circle(layout.dashX(), layout.dashY(), radius, 40);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .82f);
        shapes.circle(layout.dashX(), layout.dashY(), radius * .78f, 40);
        shapes.setColor(dashColor.r, dashColor.g, dashColor.b, player.canDash() ? .32f : .16f);
        shapes.circle(layout.dashX(), layout.dashY(), radius * .60f, 36);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .72f);
        shapes.circle(layout.dashX(), layout.dashY(), radius * .43f, 32);
        shapes.setColor(dashColor);
        shapes.circle(layout.dashX(), layout.dashY(),
            MobileCombatInput.dashDown() ? radius * .22f : radius * .12f, 18);
    }

    private void drawText(SpriteBatch batch, BitmapFont font, Player player, WaveDirector director,
                          Enemy boss, CombatHudLayout.Layout layout) {
        float s = MathUtils.clamp(ui(), .85f, 1.35f);
        float w = layout.logicalWidth();
        batch.setProjectionMatrix(projection);
        batch.begin();

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * s);
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("hud.hp", (int) player.hp, (int) player.maxHp), layout.hp().x + 12f,
            layout.hp().y + layout.hp().height * .70f, layout.hp().width - 24f, Align.left, false);
        font.draw(batch, f("hud.level", player.level), layout.xp().x + 12f,
            layout.xp().y + layout.xp().height * .70f, layout.xp().width - 24f, Align.left, false);

        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("hud.stage", RunStageContext.stage()), layout.hp().x,
            layout.timeline().y + 29f * s, 160f * s, Align.left, false);
        font.draw(batch, f("hud.kills", director.kills()), w - layout.hp().x - 180f * s,
            layout.timeline().y + 29f * s, 180f * s, Align.right, false);

        boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
        if (!director.bossSpawned()) {
            int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
            font.setColor(director.bossWarning() ? (contrast ? Color.WHITE : VisualTheme.danger()) : VisualTheme.TEXT_DIM);
            font.draw(batch, director.bossWarning() ? f("hud.bossSignal", remaining) : f("hud.bossEta", remaining),
                layout.timeline().x, layout.timeline().y + 29f * s, layout.timeline().width, Align.center, false);
        } else if (boss != null && layout.boss() != null) {
            int phase = boss.bossPhases == null ? 1 : boss.bossPhases.phase();
            font.setColor(contrast ? Color.WHITE : bossColor(boss));
            font.draw(batch, f("hud.bossPhase", bossName(boss), phase), layout.boss().x,
                layout.boss().y + layout.boss().height + 21f * s, layout.boss().width, Align.center, false);
        } else {
            font.setColor(contrast ? Color.WHITE : VisualTheme.danger());
            font.draw(batch, t("hud.bossLost"), layout.timeline().x, layout.timeline().y + 29f * s,
                layout.timeline().width, Align.center, false);
        }

        if (RunModifierContext.active()) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .86f * s);
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, f("hud.contract", RunModifierContext.title(), RunModifierContext.rewardBonusPercent()),
                layout.hp().x, layout.timeline().y - 14f * s, Math.min(430f, w * .34f), Align.left, false);
        }

        WeaponLegendaryPresentation.Style legendaryStyle = WeaponLegendaryPresentation.style(player);
        if (legendaryStyle != WeaponLegendaryPresentation.Style.NONE) {
            font.setColor(legendaryStyle.r, legendaryStyle.g, legendaryStyle.b, 1f);
            font.draw(batch, f("hud.weaponLegendary", legendaryStyle.label), w - layout.hp().x - 430f,
                layout.timeline().y - 14f * s, 430f, Align.right, false);
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
            font.draw(batch, f("hud.encounter", name, seconds), layout.timeline().x,
                layout.timeline().y - 14f * s, layout.timeline().width, Align.center, false);
        }

        if (encounter == RunEncounterDirector.Type.NONE && !director.bossSpawned()) {
            drawBuildStatus(batch, font, player, layout, s);
        }

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * s);
        font.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
        font.draw(batch, player.canDash() ? t("hud.dash") : String.format(java.util.Locale.ROOT, "%.1f", player.dashTimer),
            layout.dashX() - layout.dashRadius(), layout.dashY() + 4f * s, layout.dashRadius() * 2f, Align.center, false);

        drawSynergyUnlock(batch, font, layout, s);
        drawSentinelIntercept(batch, font, layout, s);
        drawProtocolCue(batch, font, layout, s);
        drawOnboardingHint(batch, font, layout, s);
        batch.end();
    }

    private void drawBuildStatus(SpriteBatch batch, BitmapFont font, Player player,
                                 CombatHudLayout.Layout layout, float s) {
        ActiveBuildStatus.fill(player, activeBuildKeys);
        if (activeBuildKeys[0] == null) return;
        String text = activeBuildKeys[1] == null
            ? f("hud.build.summaryOne", t(activeBuildKeys[0]))
            : f("hud.build.summaryTwo", t(activeBuildKeys[0]), t(activeBuildKeys[1]));
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .82f * s);
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, text, layout.timeline().x, layout.timeline().y - 14f * s,
            layout.timeline().width, Align.center, false);
    }

    private void drawSynergyUnlock(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
        float age = CombatVisualEvents.synergyAgeSeconds();
        if (age > 1.65f) return;
        String key = CombatVisualEvents.synergyKey();
        if (key == null) return;
        float alpha = MathUtils.clamp(1f - age / 1.65f, 0f, 1f);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY) * 1.12f * s);
        font.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, alpha);
        font.draw(batch, t(key), layout.timeline().x, layout.timeline().y + 88f * s,
            layout.timeline().width, Align.center, false);
    }

    private void drawSentinelIntercept(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
        float age = CombatVisualEvents.sentinelInterceptAgeSeconds();
        if (age > .72f) return;
        float alpha = MathUtils.clamp(1f - age / .72f, 0f, 1f);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .94f * s);
        font.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, alpha);
        font.draw(batch, t("hud.sentinelBlock"), layout.timeline().x, layout.timeline().y + 44f * s,
            layout.timeline().width, Align.center, false);
    }

    private void drawProtocolCue(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
        float age = CombatVisualEvents.protocolAgeSeconds();
        if (age > .90f) return;
        String key = switch (CombatVisualEvents.protocolCue()) {
            case RHYTHM -> "hud.protocol.rhythm";
            case KILLCHAIN_ARMED -> "hud.protocol.killchainArmed";
            case KILLCHAIN -> "hud.protocol.killchain";
            case COMBINED -> "hud.protocol.combined";
            case REACTION -> "hud.protocol.reaction";
            default -> null;
        };
        if (key == null) return;
        float alpha = MathUtils.clamp(1f - age / .90f, 0f, 1f);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY) * 1.08f * s);
        font.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, alpha);
        font.draw(batch, t(key), layout.timeline().x, layout.timeline().y + 62f * s,
            layout.timeline().width, Align.center, false);
    }

    private void drawOnboardingHint(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
        OnboardingState o = OnboardingState.active();
        if (o.completed()) return;
        String hint;
        if (!o.movementSeen()) hint = t("hud.onboardingMove");
        else if (!o.dashSeen()) hint = t("hud.onboardingDash");
        else if (!o.upgradeSeen()) hint = t("hud.onboardingUpgrade");
        else if (!o.bossSeen()) hint = t("hud.onboardingBoss");
        else return;
        Rectangle r = layout.onboarding();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * s);
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, hint, r.x + 14f, r.y + r.height * .62f, r.width - 28f, Align.center, true);
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
    private BossIdentity bossIdentity(Enemy boss) { return boss != null && boss.bossCombat != null ? boss.bossCombat.identity() : BossIdentity.ALPHA; }
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
