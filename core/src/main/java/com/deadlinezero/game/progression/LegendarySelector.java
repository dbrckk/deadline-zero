package com.deadlinezero.game.progression;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Player;

/** Allocation-light selector for exceptional legendary offers. */
public final class LegendarySelector {
    private static final LegendaryChoice[] ALL = LegendaryChoice.values();
    private static final LegendaryChoice[] ELIGIBLE = new LegendaryChoice[ALL.length];

    private LegendarySelector() { }

    public static int fillChoices(Player player, LegendaryChoice[] out) {
        int count = 0;
        for (LegendaryChoice choice : ALL) {
            if (choice.eligible(player)) ELIGIBLE[count++] = choice;
        }
        int written = Math.min(out.length, count);
        for (int i = 0; i < written; i++) {
            int pick = MathUtils.random(i, count - 1);
            LegendaryChoice tmp = ELIGIBLE[i];
            ELIGIBLE[i] = ELIGIBLE[pick];
            ELIGIBLE[pick] = tmp;
            out[i] = ELIGIBLE[i];
        }
        for (int i = written; i < out.length; i++) out[i] = null;
        return written;
    }

    /** Rare enough to feel exceptional while guaranteeing exposure in longer runs. */
    public static boolean shouldOffer(Player player) {
        if (player == null || player.level < 8) return false;
        boolean scheduled = player.level == 8 || player.level == 12 || player.level == 16
            || (player.level > 16 && player.level % 5 == 0);
        if (!scheduled) return false;
        for (LegendaryChoice choice : ALL) if (choice.eligible(player)) return true;
        return false;
    }
}
