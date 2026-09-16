package com.deadlinezero.game.ui;

import com.badlogic.gdx.math.Rectangle;

/** Pure responsive card-grid calculations for inventory/loadout screens. */
public final class ResponsiveGrid {
    public record Spec(int columns, float cardWidth, float gap) {}

    private ResponsiveGrid() {}

    public static Spec compute(float contentWidth, float minCardWidth, int maxColumns, float gap) {
        float safeGap = Math.max(0f, gap);
        float safeMin = Math.max(1f, minCardWidth);
        int safeMax = Math.max(1, maxColumns);
        int columns = (int) Math.floor((Math.max(1f, contentWidth) + safeGap) / (safeMin + safeGap));
        columns = Math.max(1, Math.min(safeMax, columns));
        float cardWidth = (Math.max(1f, contentWidth) - safeGap * Math.max(0, columns - 1)) / columns;
        return new Spec(columns, cardWidth, safeGap);
    }

    public static Rectangle cardBounds(int index, float originX, float topY, float cardHeight, Spec spec) {
        if (spec == null) throw new IllegalArgumentException("spec");
        int safeIndex = Math.max(0, index);
        int col = safeIndex % spec.columns();
        int row = safeIndex / spec.columns();
        float x = originX + col * (spec.cardWidth() + spec.gap());
        float y = topY - (row + 1) * cardHeight - row * spec.gap();
        return new Rectangle(x, y, spec.cardWidth(), cardHeight);
    }
}
