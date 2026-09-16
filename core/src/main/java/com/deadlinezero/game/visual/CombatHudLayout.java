package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;

/** Pure combat-HUD geometry, independent from simulation and GL state. */
public final class CombatHudLayout {
    public record Layout(
        float logicalWidth,
        float logicalHeight,
        float scaleX,
        float scaleY,
        Rectangle survival,
        Rectangle levelBadge,
        Rectangle xpRail,
        Rectangle hordeStatus,
        Rectangle boss,
        Rectangle toast,
        float dashX,
        float dashY,
        float dashRadius
    ) {
        public float toLogicalX(float physicalX) { return physicalX * scaleX; }
        public float toLogicalY(float physicalY) { return physicalY * scaleY; }

        /** Transitional aliases kept while CombatHudRenderer migrates to the v2 regions. */
        public Rectangle hp() { return survival; }
        public Rectangle xp() { return levelBadge; }
        public Rectangle timeline() { return xpRail; }
        public Rectangle onboarding() { return toast; }
    }

    private CombatHudLayout() {}

    public static Layout compute(int screenWidth, int screenHeight, float uiScale, boolean bossActive) {
        int physicalW = Math.max(1, screenWidth);
        int physicalH = Math.max(1, screenHeight);
        UiLayout.Metrics m = UiLayout.compute(screenWidth, screenHeight);
        float sx = m.width() / physicalW;
        float sy = m.height() / physicalH;
        float s = MathUtils.clamp(uiScale, .85f, 1.35f);

        float left = m.safeLeft() + 14f;
        float top = m.safeTop() - 14f;
        float survivalH = 42f * s;
        float survivalW = Math.min(330f * s, m.contentWidth() * .25f);
        Rectangle survival = new Rectangle(left, top - survivalH, survivalW, survivalH);

        float badgeW = Math.max(72f, 78f * s);
        Rectangle levelBadge = new Rectangle(
            survival.x + survival.width + 10f * s,
            survival.y,
            badgeW,
            survival.height
        );

        Rectangle xpRail = new Rectangle(
            survival.x,
            survival.y - 13f * s,
            survival.width + levelBadge.width + 10f * s,
            Math.min(10f, 8f * s)
        );

        float hordeW = Math.max(220f, Math.min(320f * s, m.contentWidth() * .25f));
        Rectangle hordeStatus = new Rectangle(
            m.safeRight() - 14f - hordeW,
            survival.y,
            hordeW,
            survival.height
        );

        Rectangle boss = null;
        if (bossActive) {
            float bossW = Math.min(660f * s, m.contentWidth() * .48f);
            float bossH = 16f * s;
            boss = new Rectangle(
                m.centerX() - bossW * .5f,
                xpRail.y - bossH - 12f * s,
                bossW,
                bossH
            );
        }

        float toastW = Math.min(330f * s, m.contentWidth() * .28f);
        float toastH = 40f * s;
        Rectangle toast = new Rectangle(
            m.safeLeft() + 18f,
            m.safeBottom() + 20f,
            toastW,
            toastH
        );

        float dashPhysicalX = physicalW - 58f * s;
        float dashPhysicalY = 62f * s;
        float dashX = dashPhysicalX * sx;
        float dashY = dashPhysicalY * sy;
        float dashRadius = Math.max(32f, 32f * s);

        return new Layout(
            m.width(), m.height(), sx, sy,
            survival, levelBadge, xpRail, hordeStatus, boss, toast,
            dashX, dashY, dashRadius
        );
    }
}
