package com.deadlinezero.game.visual;

/** Pure onboarding-hint routing. Every returned key exists in the shared localization catalog. */
public final class CombatOnboardingHintPresentation {
    private CombatOnboardingHintPresentation() { }

    public static String keyFor(boolean movementSeen, boolean dashSeen, boolean upgradeSeen, boolean bossSeen) {
        if (!movementSeen) return "hud.onboardingMove";
        if (!dashSeen) return "hud.onboardingDash";
        if (!upgradeSeen) return "hud.onboardingUpgrade";
        if (!bossSeen) return "hud.onboardingBoss";
        return null;
    }
}
