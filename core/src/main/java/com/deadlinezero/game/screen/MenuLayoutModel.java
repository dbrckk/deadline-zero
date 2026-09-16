package com.deadlinezero.game.screen;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;

/** Pure layout model for the responsive home/deployment screen. */
public final class MenuLayoutModel {
    public record Layout(
        Rectangle topRail,
        Rectangle survivorCard,
        Rectangle loadoutCard,
        Rectangle threatCard,
        Rectangle deploy,
        Rectangle bottomNav,
        Rectangle[] bottomTabs
    ) {}

    private MenuLayoutModel() {}

    public static Layout layout(UiLayout.Metrics m) {
        float gap = 20f;
        float contentX = m.safeLeft();
        float contentY = m.contentBottom();
        float contentW = m.contentWidth();
        float contentH = m.contentHeight();

        float leftW = m.wide() ? Math.min(contentW * .54f, 820f) : contentW * .52f;
        float rightX = contentX + leftW + gap;
        float rightW = Math.max(m.touchTarget(), contentX + contentW - rightX);

        Rectangle survivor = new Rectangle(contentX, contentY, leftW, contentH);
        float deployH = 84f;
        Rectangle deploy = new Rectangle(rightX, contentY, rightW, deployH);
        float threatH = 68f;
        Rectangle threat = new Rectangle(rightX, deploy.y + deploy.height + gap, rightW, threatH);
        float loadoutY = threat.y + threat.height + gap;
        Rectangle loadout = new Rectangle(rightX, loadoutY, rightW, Math.max(m.touchTarget(), m.contentTop() - loadoutY));

        Rectangle topRail = new Rectangle(
            m.safeLeft(),
            m.headerBottom(),
            m.contentWidth(),
            m.safeTop() - m.headerBottom()
        );
        Rectangle bottomNav = new Rectangle(
            m.safeLeft(),
            m.safeBottom(),
            m.contentWidth(),
            m.footerTop() - m.safeBottom()
        );

        Rectangle[] tabs = new Rectangle[6];
        float tabW = bottomNav.width / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = new Rectangle(bottomNav.x + i * tabW, bottomNav.y, tabW, bottomNav.height);
        }

        return new Layout(topRail, survivor, loadout, threat, deploy, bottomNav, tabs);
    }
}
