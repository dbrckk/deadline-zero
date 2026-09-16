package com.deadlinezero.game.ui;

import com.badlogic.gdx.math.Rectangle;

/** Shared pure layout contract for production meta screens. */
public final class MetaLayout {
    public record Layout(Rectangle header, Rectangle content, Rectangle footer, Rectangle back) {}

    private MetaLayout() {}

    public static Layout compute(UiLayout.Metrics m) {
        Rectangle header = new Rectangle(m.safeLeft(), m.headerBottom(), m.contentWidth(), m.safeTop() - m.headerBottom());
        Rectangle content = new Rectangle(m.safeLeft(), m.contentBottom(), m.contentWidth(), m.contentHeight());
        Rectangle footer = new Rectangle(m.safeLeft(), m.safeBottom(), m.contentWidth(), m.footerTop() - m.safeBottom());
        Rectangle back = new Rectangle(m.safeLeft(), m.headerBottom(), Math.max(112f, m.touchTarget() * 2f), header.height);
        return new Layout(header, content, footer, back);
    }

    public static Rectangle[] columns(Rectangle area, int count, float gap) {
        int safeCount = Math.max(1, count);
        float safeGap = Math.max(0f, gap);
        float width = (area.width - safeGap * Math.max(0, safeCount - 1)) / safeCount;
        Rectangle[] result = new Rectangle[safeCount];
        for (int i = 0; i < safeCount; i++) {
            result[i] = new Rectangle(area.x + i * (width + safeGap), area.y, width, area.height);
        }
        return result;
    }

    public static Rectangle[] rows(Rectangle area, int count, float gap) {
        int safeCount = Math.max(1, count);
        float safeGap = Math.max(0f, gap);
        float height = (area.height - safeGap * Math.max(0, safeCount - 1)) / safeCount;
        Rectangle[] result = new Rectangle[safeCount];
        for (int i = 0; i < safeCount; i++) {
            float y = area.y + area.height - (i + 1) * height - i * safeGap;
            result[i] = new Rectangle(area.x, y, area.width, height);
        }
        return result;
    }

    public static Rectangle[] actions(Rectangle footer, int count, float gap) {
        int safeCount = Math.max(1, count);
        float safeGap = Math.max(0f, gap);
        float height = Math.max(UiLayout.MIN_TOUCH_TARGET, footer.height - 12f);
        float width = (footer.width - safeGap * Math.max(0, safeCount - 1)) / safeCount;
        Rectangle[] result = new Rectangle[safeCount];
        float y = footer.y + (footer.height - height) * .5f;
        for (int i = 0; i < safeCount; i++) {
            result[i] = new Rectangle(footer.x + i * (width + safeGap), y, width, height);
        }
        return result;
    }
}
