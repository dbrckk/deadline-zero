package com.deadlinezero.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deadlinezero.game.config.AccessibilitySettings;
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
        // ShapeRenderer does not enable blending automatically. Nearly every premium surface below
        // intentionally uses translucent overlays, so without this the alpha channel is ignored and
        // subtle 5-15% accents become opaque cyan/violet slabs on Android.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        set(shapes, VisualTheme.SURFACE_0, 1f);
        shapes.rect(0f, 0f, m.width(), m.height());

        set(shapes, VisualTheme.SURFACE_1, .52f);
        shapes.rect(0f, m.height() * .73f, m.width(), m.height() * .27f);
        set(shapes, VisualTheme.SURFACE_2, .34f);
        shapes.rect(0f, 0f, m.width(), m.height() * .13f);

        // Keep the sci-fi scan language, but let content dominate the frame.
        boolean reduceMotion = AccessibilitySettings.active().reducedMotion;
        float pulse = reduceMotion ? .022f : .022f + .010f * (float) Math.sin(time * .75f);
        set(shapes, VisualTheme.accent(), pulse);
        float step = 144f;
        for (float y = 42f; y < m.height(); y += step) shapes.rect(0f, y, m.width(), 1f);

        // Shader-inspired light shafts and horizon bloom, implemented with cheap geometry.
        // They create depth on every screen without shipping a static background bitmap.
        float drift = reduceMotion ? 0f : (float)Math.sin(time * .22f) * m.width() * .025f;
        float horizon = m.height() * .58f;
        set(shapes, VisualTheme.accent(), .018f);
        shapes.triangle(m.width() * .08f + drift, m.height(), m.width() * .22f + drift, m.height(),
            m.width() * .42f + drift, 0f);
        shapes.triangle(m.width() * .74f - drift, m.height(), m.width() * .86f - drift, m.height(),
            m.width() * .58f - drift, 0f);
        set(shapes, VisualTheme.CYAN_SOFT, .028f);
        shapes.rect(0f, horizon - 22f, m.width(), 44f);
        set(shapes, VisualTheme.SURFACE_0, .74f);
        shapes.rect(0f, horizon + 4f, m.width(), 2f);

        // Quiet edge rails create depth without flooding the screen with cyan.
        set(shapes, VisualTheme.BORDER, .28f);
        shapes.rect(m.safeLeft(), m.safeBottom(), 2f, m.safeTop() - m.safeBottom());
        shapes.rect(m.safeLeft() + m.contentWidth() - 2f, m.safeBottom(), 2f, m.safeTop() - m.safeBottom());
    }

    public static void panel(ShapeRenderer shapes, float x, float y, float w, float h) {
        set(shapes, VisualTheme.SURFACE_1, .985f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.BORDER, .64f);
        border(shapes, x, y, w, h, 2f);
        set(shapes, VisualTheme.SURFACE_2, .72f);
        shapes.rect(x + 3f, y + h - 4f, Math.max(0f, w - 6f), 1f);
    }

    public static void card(ShapeRenderer shapes, float x, float y, float w, float h, boolean focused, boolean selected) {
        set(shapes, selected ? VisualTheme.SURFACE_2 : VisualTheme.SURFACE_1, .97f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.SURFACE_0, .36f);
        shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.min(10f, Math.max(0f, h - 10f)));
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

    public static void iconBadge(ShapeRenderer shapes, float x, float y, float size, Color accent, boolean active) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        set(shapes, VisualTheme.SURFACE_0, .96f);
        shapes.circle(x + size * .5f, y + size * .5f, size * .50f, 24);
        set(shapes, active ? a : VisualTheme.BORDER, active ? .24f : .18f);
        shapes.circle(x + size * .5f, y + size * .5f, size * .40f, 24);
        set(shapes, active ? a : VisualTheme.BORDER, active ? .90f : .50f);
        float t = Math.max(1.5f, size * .055f);
        border(shapes, x, y, size, size, t);
        cornerMarks(shapes, x, y, size, size, active ? a : VisualTheme.DIVIDER);
    }

    public static void sectionBand(ShapeRenderer shapes, float x, float y, float w, float h, Color accent) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        set(shapes, VisualTheme.SURFACE_0, .72f);
        shapes.rect(x, y, w, h);
        set(shapes, a, .18f);
        shapes.rect(x, y, Math.min(w, Math.max(44f, w * .34f)), h);
        set(shapes, a, .88f);
        shapes.rect(x, y, 4f, h);
        shapes.rect(x + 8f, y + h - 3f, Math.max(0f, Math.min(w - 16f, w * .42f)), 2f);
    }

    public static void premiumCta(ShapeRenderer shapes, float x, float y, float w, float h, Color accent, float pulse) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        float p = Math.max(0f, Math.min(1f, pulse));
        set(shapes, VisualTheme.SURFACE_2, .98f);
        shapes.rect(x, y, w, h);
        set(shapes, a, .10f + .10f * p);
        shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.max(0f, h - 10f));
        set(shapes, a, .72f + .24f * p);
        border(shapes, x, y, w, h, 2f);
        shapes.rect(x + 10f, y + h - 6f, Math.max(0f, w - 20f), 4f);
        shapes.rect(x + 10f, y + 10f, 4f, Math.max(0f, h - 20f));
        cornerMarks(shapes, x, y, w, h, a);
    }

    /**
     * Production panel with layered depth, bevel notches and restrained emissive trim.
     * Draw while ShapeRenderer is already in Filled mode.
     */
    public static void premiumPanel(ShapeRenderer shapes, float x, float y, float w, float h,
                                    Color accent, boolean emphasized) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        float notch = Math.min(18f, Math.min(w, h) * .10f);

        // Shadow / separation from background.
        set(shapes, VisualTheme.BG, .92f);
        shapes.rect(x + 7f, y - 7f, Math.max(0f, w), Math.max(0f, h));

        // Main body + subtle inset.
        set(shapes, VisualTheme.SURFACE_1, .995f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.SURFACE_2, emphasized ? .72f : .46f);
        shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.max(0f, h - 10f));

        // Angular cut-corner overlays.
        set(shapes, VisualTheme.BG, 1f);
        shapes.triangle(x, y + h, x + notch, y + h, x, y + h - notch);
        shapes.triangle(x + w, y, x + w - notch, y, x + w, y + notch);

        // Double-frame and accent hierarchy.
        set(shapes, VisualTheme.BORDER, emphasized ? .94f : .72f);
        border(shapes, x, y, w, h, emphasized ? 2.5f : 2f);
        set(shapes, a, emphasized ? .88f : .52f);
        shapes.rect(x + notch + 5f, y + h - 4f, Math.max(0f, w - notch * 2f - 10f), 3f);
        shapes.rect(x + 4f, y + notch + 5f, 3f, Math.max(0f, h - notch * 2f - 10f));

        // Inner highlight gives the card material depth without a texture dependency.
        set(shapes, a, emphasized ? .10f : .045f);
        shapes.rect(x + 9f, y + 9f, Math.max(0f, w - 18f), Math.max(0f, h - 18f));
        set(shapes, VisualTheme.SURFACE_1, .96f);
        shapes.rect(x + 13f, y + 13f, Math.max(0f, w - 26f), Math.max(0f, h - 26f));

        cornerMarks(shapes, x + 3f, y + 3f, w - 6f, h - 6f, a);
    }

    public static void premiumCard(ShapeRenderer shapes, float x, float y, float w, float h,
                                   Color accent, boolean focused, boolean selected, boolean disabled) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        set(shapes, VisualTheme.SURFACE_1, disabled ? .72f : .98f);
        shapes.rect(x, y, w, h);
        set(shapes, VisualTheme.SURFACE_2, disabled ? .18f : focused || selected ? .66f : .38f);
        shapes.rect(x + 4f, y + 4f, Math.max(0f, w - 8f), Math.max(0f, h - 8f));

        Color frame = disabled ? VisualTheme.BORDER : (focused || selected ? a : VisualTheme.BORDER);
        set(shapes, frame, disabled ? .34f : focused || selected ? .95f : .64f);
        border(shapes, x, y, w, h, focused || selected ? 2.5f : 2f);

        set(shapes, a, disabled ? .14f : selected ? .92f : focused ? .72f : .34f);
        shapes.rect(x + 6f, y + h - 5f, Math.max(0f, w - 12f), 3f);
        shapes.rect(x + 6f, y + 7f, focused || selected ? 4f : 2f, Math.max(0f, h - 14f));

        if (selected) {
            set(shapes, a, .10f);
            shapes.rect(x + 10f, y + 10f, Math.max(0f, w - 20f), Math.max(0f, h - 20f));
        }
        cornerMarks(shapes, x, y, w, h, frame);
    }

    public static void premiumButton(ShapeRenderer shapes, float x, float y, float w, float h,
                                     Color accent, ButtonState state) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        boolean disabled = state == ButtonState.DISABLED;
        boolean active = state == ButtonState.SELECTED || state == ButtonState.PRESSED;
        set(shapes, disabled ? VisualTheme.SURFACE_1 : VisualTheme.SURFACE_2, disabled ? .60f : .99f);
        shapes.rect(x, y, w, h);
        set(shapes, a, disabled ? .10f : active ? .18f : .07f);
        shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.max(0f, h - 10f));
        set(shapes, disabled ? VisualTheme.BORDER : a, disabled ? .38f : active ? 1f : .70f);
        border(shapes, x, y, w, h, active ? 3f : 2f);
        shapes.rect(x + 9f, y + h - 5f, Math.max(0f, w - 18f), active ? 4f : 2f);
        if (active) {
            shapes.rect(x + 9f, y + 9f, 4f, Math.max(0f, h - 18f));
        }
        cornerMarks(shapes, x, y, w, h, disabled ? VisualTheme.BORDER : a);
    }

    public static void sectionPlate(ShapeRenderer shapes, float x, float y, float w, float h,
                                    Color accent, boolean strong) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        set(shapes, VisualTheme.SURFACE_0, .94f);
        shapes.rect(x, y, w, h);
        set(shapes, a, strong ? .18f : .09f);
        shapes.rect(x + 3f, y + 3f, Math.max(0f, w - 6f), Math.max(0f, h - 6f));
        set(shapes, a, strong ? .92f : .58f);
        shapes.rect(x, y, 4f, h);
        shapes.rect(x + 8f, y + h - 3f, Math.max(0f, Math.min(w - 16f, w * .48f)), 2f);
        set(shapes, VisualTheme.BORDER, .62f);
        border(shapes, x, y, w, h, 1.5f);
    }

    public static void segmentedTrack(ShapeRenderer shapes, float x, float y, float w, float h,
                                      float progress, int segments, Color accent) {
        Color a = accent == null ? VisualTheme.accent() : accent;
        float p = Math.max(0f, Math.min(1f, progress));
        int count = Math.max(1, segments);
        float gap = Math.min(3f, w / Math.max(12f, count * 8f));
        float segW = Math.max(1f, (w - gap * (count - 1)) / count);
        int filled = Math.round(p * count);
        for (int i = 0; i < count; i++) {
            float sx = x + i * (segW + gap);
            set(shapes, i < filled ? a : VisualTheme.SURFACE_0, i < filled ? .95f : .98f);
            shapes.rect(sx, y, segW, h);
            set(shapes, i < filled ? a : VisualTheme.BORDER, i < filled ? .82f : .55f);
            border(shapes, sx, y, segW, h, 1f);
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
