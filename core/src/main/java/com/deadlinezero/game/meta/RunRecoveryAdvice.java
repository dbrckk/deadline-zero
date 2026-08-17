package com.deadlinezero.game.meta;

/** Pure post-run coaching. It never changes difficulty or profile state. */
public final class RunRecoveryAdvice {
    public enum Focus { SURVIVABILITY, OFFENSE, ENDGAME_DEFENSE, BALANCED }
    public record Advice(Focus focus, String headline, String detail) { }

    private RunRecoveryAdvice() { }

    public static Advice forResult(RunResult result) {
        if (result == null) return balanced();
        float seconds = Math.max(0f, result.secondsSurvived());
        float target = Math.max(1f, StageMissionRules.bossArrivalSeconds(Math.max(1, result.stage())));
        float progress = seconds / target;
        float killsPerMinute = seconds <= .001f ? 0f : Math.max(0, result.kills()) * 60f / seconds;

        if (progress < .52f) {
            String detail = result.threatTier() >= 3
                ? "Early pressure broke the run. Add HP/mobility or drop Threat by one tier while refining the build."
                : "Early pressure broke the run. Prioritize max HP, movement speed and a reliable dash window.";
            return new Advice(Focus.SURVIVABILITY, "SURVIVE THE OPENING", detail);
        }
        if (killsPerMinute < 14f) {
            return new Advice(Focus.OFFENSE, "RAISE CLEAR SPEED",
                "Hostiles are staying alive too long. Prioritize damage, fire rate, penetration or a stronger weapon synergy.");
        }
        if (progress >= .84f) {
            return new Advice(Focus.ENDGAME_DEFENSE, "HOLD THE FINAL PRESSURE",
                "Your clear speed is viable. Preserve dash charges and add mitigation/HP for the last pressure band and boss signal.");
        }
        return balanced();
    }

    private static Advice balanced() {
        return new Advice(Focus.BALANCED, "REFINE THE BUILD",
            "The run is close to target pace. Keep offense and survivability balanced, then adapt to the active contract and biome hazards.");
    }
}
