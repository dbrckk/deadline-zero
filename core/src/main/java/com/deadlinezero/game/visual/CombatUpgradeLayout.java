package com.deadlinezero.game.visual;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;

/** Pure responsive geometry for the in-run three-choice upgrade decision. */
public final class CombatUpgradeLayout {
    public record Layout(
        float logicalWidth,
        float logicalHeight,
        Rectangle panel,
        Rectangle[] cards,
        Rectangle footer
    ) { }

    private CombatUpgradeLayout() { }

    public static Layout compute(int screenWidth, int screenHeight) {
        UiLayout.Metrics m = UiLayout.compute(screenWidth, screenHeight);
        float panelW = Math.min(1040f, m.contentWidth() * .80f);
        float panelH = Math.min(400f, m.height() * .54f);
        float panelX = m.centerX() - panelW * .5f;
        float panelY = m.centerY() - panelH * .5f;
        Rectangle panel = new Rectangle(panelX, panelY, panelW, panelH);

        float pad = 16f;
        float gap = 18f;
        float footerH = 48f;
        float headerH = 66f;
        Rectangle footer = new Rectangle(panelX + pad, panelY + pad, panelW - pad * 2f, footerH);

        float cardsY = footer.y + footer.height + 12f;
        float cardsTop = panelY + panelH - headerH;
        float cardH = Math.max(210f, cardsTop - cardsY);
        float cardW = (panelW - pad * 2f - gap * 2f) / 3f;
        Rectangle[] cards = new Rectangle[3];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Rectangle(panelX + pad + i * (cardW + gap), cardsY, cardW, cardH);
        }

        return new Layout(m.width(), m.height(), panel, cards, footer);
    }
}
