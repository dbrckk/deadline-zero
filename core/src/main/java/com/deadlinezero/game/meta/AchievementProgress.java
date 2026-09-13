package com.deadlinezero.game.meta;

import java.util.EnumSet;
import java.util.Set;

/** Persistent one-time achievement state. */
public final class AchievementProgress {
    private final EnumSet<AchievementService.Achievement> claimed = EnumSet.noneOf(AchievementService.Achievement.class);

    public boolean claimed(AchievementService.Achievement achievement) {
        return achievement != null && claimed.contains(achievement);
    }

    public boolean markClaimed(AchievementService.Achievement achievement) {
        return achievement != null && claimed.add(achievement);
    }

    public Set<AchievementService.Achievement> claimed() { return Set.copyOf(claimed); }
}
