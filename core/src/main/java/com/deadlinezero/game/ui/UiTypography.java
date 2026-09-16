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
            case DISPLAY -> 2.60f;
            case TITLE -> 1.80f;
            case SECTION -> 1.30f;
            case BODY -> 1.00f;
            case LABEL -> .86f;
            case CAPTION -> .72f;
            case METRIC -> 1.15f;
        };
    }
}
