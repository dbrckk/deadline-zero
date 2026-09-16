package com.deadlinezero.game.screen;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;

/** Pure responsive layout for survivor browsing and explicit selection actions. */
public final class SurvivorLayoutModel {
    public record Layout(
        Rectangle card,
        Rectangle portrait,
        Rectangle stats,
        Rectangle xpBar,
        Rectangle previous,
        Rectangle next,
        Rectangle cta
    ) {}

    private SurvivorLayoutModel() {}

    public static Layout layout(UiLayout.Metrics m) {
        Rectangle card = new Rectangle(m.safeLeft(), m.contentBottom(), m.contentWidth(), m.contentHeight());
        float navSize = Math.max(m.touchTarget(), 64f);
        Rectangle previous = new Rectangle(card.x + 12f, card.y + (card.height - navSize) * .5f, navSize, navSize);
        Rectangle next = new Rectangle(card.x + card.width - navSize - 12f, previous.y, navSize, navSize);

        float leftW = card.width * (m.wide() ? .48f : .46f);
        Rectangle portrait = new Rectangle(
            card.x + navSize + 24f,
            card.y + 54f,
            Math.max(260f, leftW - navSize - 36f),
            Math.max(240f, card.height - 108f)
        );

        float rightX = card.x + leftW + 32f;
        float rightEdge = next.x - 20f;
        float rightW = Math.max(320f, rightEdge - rightX);
        Rectangle cta = new Rectangle(rightX, card.y + 24f, rightW, 68f);
        Rectangle xpBar = new Rectangle(rightX, cta.y + cta.height + 22f, rightW, 16f);
        float statsY = xpBar.y + xpBar.height + 28f;
        Rectangle stats = new Rectangle(rightX, statsY, rightW, Math.max(150f, card.y + card.height - 42f - statsY));

        return new Layout(card, portrait, stats, xpBar, previous, next, cta);
    }
}
