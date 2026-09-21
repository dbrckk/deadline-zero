package com.deadlinezero.game.visual;

import com.deadlinezero.game.ui.UiLayout;

/**
 * Normalizes combat modal overlays to the same logical UI space as the HUD.
 *
 * Android may report a render-surface size that is larger than the captured/display backbuffer.
 * Drawing modal cards directly in raw Gdx pixel coordinates can therefore push choices off-screen.
 */
public final class CombatOverlayViewport {
    public record Viewport(float width, float height, float scaleX, float scaleY) {
        public float toLogicalX(float physicalX) { return physicalX * scaleX; }
        public float toLogicalY(float physicalY) { return physicalY * scaleY; }
    }

    private CombatOverlayViewport() { }

    public static Viewport compute(int physicalWidth, int physicalHeight) {
        int w = Math.max(1, physicalWidth);
        int h = Math.max(1, physicalHeight);
        UiLayout.Metrics m = UiLayout.compute(w, h);
        return new Viewport(m.width(), m.height(), m.width() / w, m.height() / h);
    }
}
