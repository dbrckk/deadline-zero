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
        Rectangle hp,
        Rectangle xp,
        Rectangle timeline,
        Rectangle boss,
        Rectangle onboarding,
        float dashX,
        float dashY,
        float dashRadius
    ) {
        public float toLogicalX(float physicalX) { return physicalX * scaleX; }
        public float toLogicalY(float physicalY) { return physicalY * scaleY; }
    }

    private CombatHudLayout() {}

    public static Layout compute(int screenWidth, int screenHeight, float uiScale, boolean bossActive) {
        int physicalW = Math.max(1, screenWidth);
        int physicalH = Math.max(1, screenHeight);
        UiLayout.Metrics m = UiLayout.compute(screenWidth, screenHeight);
        float sx = m.width() / physicalW;
        float sy = m.height() / physicalH;
        float s = MathUtils.clamp(uiScale, .85f, 1.35f);

        float railH = 30f * s;
        float railW = Math.min(420f * s, m.contentWidth() * .30f);
        float gap = 16f * s;
        float top = m.safeTop() - 12f;
        Rectangle hp = new Rectangle(m.safeLeft() + 12f, top - railH, railW, railH);
        Rectangle xp = new Rectangle(hp.x + hp.width + gap, hp.y, railW, railH);

        float timelineW = Math.min(760f, m.contentWidth() * .54f);
        Rectangle timeline = new Rectangle(m.centerX() - timelineW * .5f,
            hp.y - 25f * s, timelineW, 9f * s);

        Rectangle boss = null;
        if (bossActive) {
            float bossW = Math.min(860f, m.contentWidth() * .62f);
            float bossH = 24f * s;
            boss = new Rectangle(m.centerX() - bossW * .5f,
                timeline.y - bossH - 13f * s, bossW, bossH);
        }

        float hintW = Math.min(460f, m.contentWidth() * .36f);
        float hintH = 44f * s;
        Rectangle onboarding = new Rectangle(m.centerX() - hintW * .5f,
            m.safeBottom() + 58f * s, hintW, hintH);

        float dashPhysicalX = physicalW - 58f * s;
        float dashPhysicalY = 62f * s;
        float dashX = dashPhysicalX * sx;
        float dashY = dashPhysicalY * sy;
        float dashRadius = Math.max(32f * s, 28f);

        return new Layout(m.width(), m.height(), sx, sy, hp, xp, timeline, boss, onboarding,
            dashX, dashY, dashRadius);
    }
}
