package com.deadlinezero.game.meta;

/** Persistent weekly progression snapshot keyed by deterministic UTC week index. */
public final class WeeklyProgress {
    public long weekIndex = Long.MIN_VALUE;
    public int kills;
    public int runs;
    public int bosses;
    public boolean killMissionClaimed;
    public boolean runMissionClaimed;
    public boolean bossMissionClaimed;

    public void resetForWeek(long week) {
        if (weekIndex == week) return;
        weekIndex = week;
        kills = 0;
        runs = 0;
        bosses = 0;
        killMissionClaimed = false;
        runMissionClaimed = false;
        bossMissionClaimed = false;
    }
}
