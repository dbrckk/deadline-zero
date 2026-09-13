package com.deadlinezero.game.meta;

/** Weekly mission rules. Weeks are deterministic UTC Monday-based buckets. */
public final class WeeklyService {
    public static final int KILL_TARGET = 1000;
    public static final int RUN_TARGET = 15;
    public static final int BOSS_TARGET = 5;

    private WeeklyService() {}

    /** 1970-01-01 was Thursday; +3 aligns bucket boundaries to Monday. */
    public static long weekIndexForEpochDay(long epochDay) {
        return Math.floorDiv(epochDay + 3L, 7L);
    }

    public static void refresh(PlayerProfile profile, long epochDay) {
        if (profile == null) return;
        profile.weekly.resetForWeek(weekIndexForEpochDay(epochDay));
    }

    public static void recordRun(PlayerProfile profile, int kills, boolean bossKilled) {
        if (profile == null) return;
        profile.weekly.runs = DailyCounterMath.increment(profile.weekly.runs);
        profile.weekly.kills = DailyCounterMath.addKills(profile.weekly.kills, kills);
        if (bossKilled) profile.weekly.bosses = DailyCounterMath.increment(profile.weekly.bosses);
    }

    public static boolean claimKillMission(PlayerProfile profile) {
        if (profile == null || profile.weekly.killMissionClaimed || profile.weekly.kills < KILL_TARGET) return false;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 2500L);
        profile.weekly.killMissionClaimed = true;
        return true;
    }

    public static boolean claimRunMission(PlayerProfile profile) {
        if (profile == null || profile.weekly.runMissionClaimed || profile.weekly.runs < RUN_TARGET) return false;
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 3500L);
        profile.weekly.runMissionClaimed = true;
        return true;
    }

    public static boolean claimBossMission(PlayerProfile profile) {
        if (profile == null || profile.weekly.bossMissionClaimed || profile.weekly.bosses < BOSS_TARGET) return false;
        profile.addCurrency(PlayerProfile.Currency.GEMS, 12L);
        profile.weekly.bossMissionClaimed = true;
        return true;
    }
}
