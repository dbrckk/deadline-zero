package com.deadlinezero.game.meta;

import com.deadlinezero.game.config.Localization;

/** Deterministic, truthful share copy built only from the settled run result. */
public final class RunShareText {
    public static final String PLAY_URL = "https://play.google.com/store/apps/details?id=com.deadlinezero.game";

    private RunShareText() { }

    /** Legacy deterministic English formatter retained for headless tests and non-UI callers. */
    public static String format(RunResult result) {
        if (result == null) return "Deadline: Zero\n" + PLAY_URL;
        int seconds = Math.max(0, (int)result.secondsSurvived());
        StringBuilder text = new StringBuilder(192);
        text.append("DEADLINE: ZERO\n")
            .append("Protocol cleared — Stage ").append(Math.max(1, result.stage()));
        if (result.threatTier() > 0) text.append(" • Threat ").append(result.threatTier());
        text.append(" • ").append(Math.max(0, result.kills())).append(" kills")
            .append(" • ").append(String.format(java.util.Locale.ROOT, "%02d:%02d", seconds / 60, seconds % 60))
            .append('\n')
            .append("Contract: ").append(safeEnglish(result.contractTitle()))
            .append("\nCan you clear it?\n")
            .append(PLAY_URL);
        return text.toString();
    }

    public static String format(RunResult result, Localization i18n) {
        if (i18n == null) throw new IllegalArgumentException("i18n");
        if (result == null) return i18n.format("share.fallback", PLAY_URL);
        int seconds = Math.max(0, (int)result.secondsSurvived());
        StringBuilder text = new StringBuilder(192);
        text.append(i18n.text("share.header")).append('\n')
            .append(i18n.format("share.cleared", Math.max(1, result.stage())));
        if (result.threatTier() > 0) text.append(i18n.format("share.threat", result.threatTier()));
        text.append(i18n.format("share.kills", Math.max(0, result.kills())))
            .append(" • ").append(String.format(java.util.Locale.ROOT, "%02d:%02d", seconds / 60, seconds % 60))
            .append('\n')
            .append(i18n.format("share.contract", safe(result.contractTitle(), i18n)))
            .append('\n').append(i18n.text("share.challenge")).append('\n')
            .append(PLAY_URL);
        return text.toString();
    }

    private static String safeEnglish(String value) {
        if (value == null || value.isBlank()) return "STANDARD";
        String normalized = value.replace('\n', ' ').replace('\r', ' ').trim();
        return normalized.length() > 72 ? normalized.substring(0, 72) : normalized;
    }

    private static String safe(String value, Localization i18n) {
        if (value == null || value.isBlank()) return i18n.text("share.standard");
        String normalized = value.replace('\n', ' ').replace('\r', ' ').trim();
        return normalized.length() > 72 ? normalized.substring(0, 72) : normalized;
    }
}
