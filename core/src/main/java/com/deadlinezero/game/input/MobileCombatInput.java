package com.deadlinezero.game.input;

import com.badlogic.gdx.Gdx;

/** Multi-touch combat actions kept separate from movement stick ownership. */
public final class MobileCombatInput {
    private static boolean dashDownGlobal;
    private boolean dashWasDown;

    public boolean dashJustPressed(float uiScale) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float s = Math.max(.85f, Math.min(1.35f, uiScale));
        float cx = w - 58f * s;
        float cy = 62f * s;
        float radius = 48f * s;
        boolean down = false;
        for (int pointer = 0; pointer < 5; pointer++) {
            if (!Gdx.input.isTouched(pointer)) continue;
            float x = Gdx.input.getX(pointer);
            float y = h - Gdx.input.getY(pointer);
            float dx = x - cx;
            float dy = y - cy;
            if (dx * dx + dy * dy <= radius * radius) {
                down = true;
                break;
            }
        }
        dashDownGlobal = down;
        boolean justPressed = down && !dashWasDown;
        dashWasDown = down;
        return justPressed;
    }

    public static boolean dashDown() { return dashDownGlobal; }
}
