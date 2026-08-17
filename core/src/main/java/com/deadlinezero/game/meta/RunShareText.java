package com.deadlinezero.game.meta;

/** Deterministic, truthful share copy built only from the settled run result. */
public final class RunShareText {
    public static final String PLAY_URL = "https://play.google.com/store/apps/details?id=com.deadlinezero.game";

    private RunShareText() { }

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
            .append("Contract: ").append(safe(result.contractTitle()))
            .append("\nCan you clear it?\n")
            .append(PLAY_URL);
        return text.toString();
    }

    private static String safe(String value) {
        if (value == null || value.isBlank()) return "STANDARD";
        String normalized = value.replace('\n', ' ').replace('\r', ' ').trim();
        return normalized.length() > 72 ? normalized.substring(0, 72) : normalized;
    }
}
