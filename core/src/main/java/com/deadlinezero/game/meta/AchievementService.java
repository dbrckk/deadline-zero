package com.deadlinezero.game.meta;

/** Permanent account achievements derived from canonical lifetime progression. */
public final class AchievementService {
    public enum Achievement {
        FIRST_DEPLOYMENT("First Deployment", "Complete 1 run", 500, 0),
        FIELD_VETERAN("Field Veteran", "Complete 25 runs", 2500, 0),
        EXTERMINATOR("Exterminator", "Eliminate 5,000 hostiles", 4000, 0),
        FIRST_CLEAR("Breakthrough", "Clear the first operation", 0, 5),
        DEEP_STRIKE("Deep Strike", "Reach operation 5", 5000, 10),
        ACCOUNT_TEN("Established", "Reach account level 10", 3000, 8);

        public final String title;
        public final String description;
        public final long credits;
        public final int gems;

        public String titleKey() { return "achievement." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
        public String descriptionKey() { return "achievement." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }

        Achievement(String title, String description, long credits, int gems) {
            this.title = title;
            this.description = description;
            this.credits = credits;
            this.gems = gems;
        }
    }

    private AchievementService() {}

    public static boolean unlocked(PlayerProfile profile, Achievement achievement) {
        if (profile == null || achievement == null) return false;
        return switch (achievement) {
            case FIRST_DEPLOYMENT -> profile.totalRuns >= 1;
            case FIELD_VETERAN -> profile.totalRuns >= 25;
            case EXTERMINATOR -> profile.totalKills >= 5_000L;
            case FIRST_CLEAR -> profile.highestStage >= 2;
            case DEEP_STRIKE -> profile.highestStage >= 5;
            case ACCOUNT_TEN -> profile.accountLevel >= 10;
        };
    }

    public static boolean claim(PlayerProfile profile, Achievement achievement) {
        if (!unlocked(profile, achievement) || profile.achievements.claimed(achievement)) return false;
        if (!profile.achievements.markClaimed(achievement)) return false;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, achievement.credits);
        profile.addCurrency(PlayerProfile.Currency.GEMS, achievement.gems);
        return true;
    }
}
