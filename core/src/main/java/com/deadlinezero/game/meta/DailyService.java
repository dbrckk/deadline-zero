package com.deadlinezero.game.meta;

/** Daily login and mission reward rules. */
public final class DailyService {
    private DailyService() {}

    public static void refresh(PlayerProfile profile, long epochDay) {
        if (profile == null) return;
        profile.daily.resetForDay(epochDay);
    }

    public static boolean claimLogin(PlayerProfile profile) {
        if (profile == null || profile.daily.loginClaimed) return false;
        int streak = Math.max(1, profile.daily.loginStreak);
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 120L + streak * 35L);
        if (streak % 3 == 0) profile.addCurrency(PlayerProfile.Currency.GEMS, 2L);
        profile.daily.loginClaimed = true;
        return true;
    }

    public static void recordRun(PlayerProfile profile, int kills, boolean bossKilled) {
        if (profile == null) return;
        profile.daily.runsToday++;
        profile.daily.killsToday += Math.max(0, kills);
        if (bossKilled) profile.daily.bossesToday++;
    }

    public static boolean claimKillMission(PlayerProfile profile) {
        if (profile == null || profile.daily.killMissionClaimed || profile.daily.killsToday < 100) return false;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 350L);
        profile.daily.killMissionClaimed = true;
        return true;
    }

    public static boolean claimRunMission(PlayerProfile profile) {
        if (profile == null || profile.daily.runMissionClaimed || profile.daily.runsToday < 3) return false;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 450L);
        profile.daily.runMissionClaimed = true;
        return true;
    }

    public static boolean claimBossMission(PlayerProfile profile) {
        if (profile == null || profile.daily.bossMissionClaimed || profile.daily.bossesToday < 1) return false;
        profile.addCurrency(PlayerProfile.Currency.GEMS, 3L);
        profile.daily.bossMissionClaimed = true;
        return true;
    }
}
