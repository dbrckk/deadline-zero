package com.deadlinezero.game.ui;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class UiRendererStateTest {
    @Test
    void interactiveStatesResolveToDistinctSemanticStyles() {
        UiRenderer.ButtonStyle normal = UiRenderer.buttonStyle(UiRenderer.ButtonState.NORMAL);
        UiRenderer.ButtonStyle pressed = UiRenderer.buttonStyle(UiRenderer.ButtonState.PRESSED);
        UiRenderer.ButtonStyle selected = UiRenderer.buttonStyle(UiRenderer.ButtonState.SELECTED);
        UiRenderer.ButtonStyle disabled = UiRenderer.buttonStyle(UiRenderer.ButtonState.DISABLED);
        UiRenderer.ButtonStyle danger = UiRenderer.buttonStyle(UiRenderer.ButtonState.DANGER);

        assertNotEquals(normal.tone(), selected.tone());
        assertNotEquals(normal.tone(), disabled.tone());
        assertNotEquals(selected.tone(), danger.tone());
        assertTrue(pressed.fillAlpha() > normal.fillAlpha());
        assertTrue(disabled.labelAlpha() > 0f);
        assertTrue(disabled.borderAlpha() > 0f);
        assertTrue(danger.borderAlpha() >= normal.borderAlpha());
    }

    @Test
    void nullStateFallsBackToNormalPresentation() {
        UiRenderer.ButtonStyle fallback = UiRenderer.buttonStyle(null);
        UiRenderer.ButtonStyle normal = UiRenderer.buttonStyle(UiRenderer.ButtonState.NORMAL);
        assertTrue(fallback == normal);
    }
}
