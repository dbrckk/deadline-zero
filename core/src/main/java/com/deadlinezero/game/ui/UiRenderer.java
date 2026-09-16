package com.deadlinezero.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deadlinezero.game.visual.VisualTheme;

/** Allocation-free shape primitives for the shared production UI language. */
public final class UiRenderer {
    public enum ButtonState { NORMAL, PRESSED, SELECTED, DISABLED, DANGER }
    public enum Tone { NEUTRAL, ACCENT, SELECTED, DISABLED, DANGER }

    public record ButtonStyle(Tone tone, float fillAlpha, float borderAlpha, float labelAlpha) {}

    private static final ButtonStyle NORMAL = new ButtonStyle(Tone.NEUTRAL, .88f, .78f, 1f);
    private static final ButtonStyle PRESSED = new ButtonStyle(Tone.ACCENT, .98f, 1f, 1f);
    private static final ButtonStyle SELECTED = new ButtonStyle(Tone.SELECTED, .94f, 1f, 1f);
    private static final ButtonStyle DISABLED = new ButtonStyle(Tone.DISABLED, .58f, .55f, .62f);
    private static final ButtonStyle DANGER = new ButtonStyle(Tone.DANGER, .90f, 1f, 1f);

    private UiRenderer() {}

    public static ButtonStyle buttonStyle(ButtonState state) {
        if (state == null) return NORMAL;
        return switch (state) {
            case PRESSED -> PRESSED;
            case SELECTED -> SELECTED;
            case DISABLED -> DISABLED;
            case DANGER -> DANGER;
            default -> NORMAL;
        };
    }

    /** Draw while ShapeRenderer is already in Filled mode. */
    public static void background(ShapeRenderer shapes, UiLayout.Metrics m, float time) {
        set(shapes, VisualTheme.SURFACE_0, 1f);
        shapes.rect(0f, 0f, m.width(), m.height());

        set(shapes, VisualTheme.SURFACE_1, .40f);
        shapes.rect(0f, m.height() * .68f, m.width(), m.height() * .32f);
        set(shapes, VisualTheme.SURFACE_2, .25f);
        shapes.rect(0f, 0f, m.width(), m.height() * .16f);

        float pulse = .055f + .025f * (float) Math.sin(time * .9f);
        set(shapes, VisualTheme.accent(), pulse);
        float step = 72f;
        for (float y = 28f; y < m.height(); y += step) shapes.rect(0f, y, m.width(), 1f);
    }

    public static void panel(ShapeRenderer shapes, float x, float y, float w, float h) {
        set(shapes, VisualTheme.SURFACE_1, .985f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.BORDER, .76f);
        border(shapes, x, y, w, h, 2f);
    }

    public static void card(ShapeRenderer shapes, float x, float y, float w, float h, boolean focused, boolean selected) {
        set(shapes, selected ? VisualTheme.SURFACE_2 : VisualTheme.SURFACE_1, .97f);
        shapes.rect(x, y, w, h);
        Color border = focused || selected ? VisualTheme.BORDER_FOCUS : VisualTheme.BORDER;
        set(shapes, border, focused || selected ? 1f : .68f);
        border(shapes, x, y, w, h, focused || selected ? 3f : 2f);
        if (selected) {
            set(shapes, VisualTheme.accent(), .95f);
            shapes.rect(x, y, 5f, h);
        }
        cornerMarks(shapes, x, y, w, h, focused || selected ? VisualTheme.accent() : VisualTheme.DIVIDER);
    }

    public static void button(ShapeRenderer shapes, float x, float y, float w, float h, ButtonState state) {
        ButtonStyle style = buttonStyle(state);
        Color fill = switch (style.tone()) {
            case ACCENT, SELECTED -> VisualTheme.accent();
            case DANGER -> VisualTheme.danger();
            case DISABLED -> VisualTheme.SURFACE_1;
            default -> VisualTheme.SURFACE_2;
        };
        Color borderColor = switch (style.tone()) {
            case ACCENT, SELECTED -> VisualTheme.BORDER_FOCUS;
            case DANGER -> VisualTheme.danger();
            case DISABLED -> VisualTheme.BORDER;
            default -> VisualTheme.BORDER;
        };
        set(shapes, fill, style.fillAlpha());
        shapes.rect(x, y, w, h);
        set(shapes, borderColor, style.borderAlpha());
        border(shapes, x, y, w, h, state == ButtonState.PRESSED ? 4f : 2f);
        if (state == ButtonState.SELECTED || state == ButtonState.PRESSED) {
            set(shapes, VisualTheme.TEXT_STRONG, .82f);
            shapes.rect(x + 7f, y + 7f, 4f, Math.max(0f, h - 14f));
        }
    }

    public static void progress(ShapeRenderer shapes, float x, float y, float w, float h, float progress, Color color) {
        float p = Math.max(0f, Math.min(1f, progress));
        set(shapes, VisualTheme.SURFACE_0, .98f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.BORDER, .75f);
        border(shapes, x, y, w, h, 1.5f);
        if (p > 0f) {
            set(shapes, color == null ? VisualTheme.accent() : color, .95f);
            shapes.rect(x + 2f, y + 2f, Math.max(0f, (w - 4f) * p), Math.max(0f, h - 4f));
        }
    }

    public static void chip(ShapeRenderer shapes, float x, float y, float w, float h, Color accent) {
        set(shapes, VisualTheme.SURFACE_2, .94f);
        shapes.rect(x, y, w, h);
        set(shapes, accent == null ? VisualTheme.BORDER : accent, .88f);
        shapes.rect(x, y, 3f, h);
        border(shapes, x, y, w, h, 1f);
    }

    public static void topRail(ShapeRenderer shapes, UiLayout.Metrics m) {
        float h = m.safeTop() - m.headerBottom();
        set(shapes, VisualTheme.SURFACE_1, .96f);
        shapes.rect(m.safeLeft(), m.headerBottom(), m.contentWidth(), h);
        set(shapes, VisualTheme.BORDER, .78f);
        shapes.rect(m.safeLeft(), m.headerBottom(), m.contentWidth(), 2f);
    }

    public static void bottomNav(ShapeRenderer shapes, UiLayout.Metrics m) {
        float h = m.footerTop() - m.safeBottom();
        set(shapes, VisualTheme.SURFACE_1, .96f);
        shapes.rect(m.safeLeft(), m.safeBottom(), m.contentWidth(), h);
        set(shapes, VisualTheme.BORDER, .78f);
        shapes.rect(m.safeLeft(), m.footerTop() - 2f, m.contentWidth(), 2f);
    }

    private static void cornerMarks(ShapeRenderer shapes, float x, float y, float w, float h, Color color) {
        float l = Math.min(14f, Math.min(w, h) * .15f);
        set(shapes, color, .9f);
        shapes.rect(x, y + h - 2f, l, 2f);
        shapes.rect(x, y + h - l, 2f, l);
        shapes.rect(x + w - l, y + h - 2f, l, 2f);
        shapes.rect(x + w - 2f, y + h - l, 2f, l);
    }

    private static void border(ShapeRenderer shapes, float x, float y, float w, float h, float t) {
        shapes.rect(x, y, w, t);
        shapes.rect(x, y + h - t, w, t);
        shapes.rect(x, y + t, t, Math.max(0f, h - t * 2f));
        shapes.rect(x + w - t, y + t, t, Math.max(0f, h - t * 2f));
    }

    private static void set(ShapeRenderer shapes, Color c, float alpha) {
        shapes.setColor(c.r, c.g, c.b, Math.max(0f, Math.min(1f, alpha)));
    }
}
