package com.deadlinezero.game.meta;

/** Persistent daily progression snapshot. Date is stored as UTC epoch day. */
public final class DailyProgress {
    public long epochDay = -1L;
    public int loginStreak;
    public boolean loginClaimed;
    public boolean rewardedChestClaimed;
    public int killsToday;
    public int runsToday;
    public int bossesToday;
    public boolean killMissionClaimed;
    public boolean runMissionClaimed;
    public boolean bossMissionClaimed;

    public void resetForDay(long day) {
        if (epochDay == day) return;
        if (epochDay == day - 1L) loginStreak = Math.min(30, loginStreak + 1);
        else loginStreak = 1;
        epochDay = day;
        loginClaimed = false;
        rewardedChestClaimed = false;
        killsToday = 0;
        runsToday = 0;
        bossesToday = 0;
        killMissionClaimed = false;
        runMissionClaimed = false;
        bossMissionClaimed = false;
    }
}
