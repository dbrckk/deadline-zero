package com.deadlinezero.game.ui;

/** Named bitmap-font roles so screens do not invent arbitrary scales. */
public final class UiTypography {
    public enum Role {
        DISPLAY,
        TITLE,
        SECTION,
        BODY,
        LABEL,
        CAPTION,
        METRIC
    }

    private UiTypography() {}

    public static float scale(Role role) {
        if (role == null) return 1f;
        return switch (role) {
            case DISPLAY -> 2.20f;
            case TITLE -> 1.55f;
            case SECTION -> 1.12f;
            case BODY -> .84f;
            case LABEL -> .68f;
            case CAPTION -> .54f;
            case METRIC -> 1.00f;
        };
    }
}
