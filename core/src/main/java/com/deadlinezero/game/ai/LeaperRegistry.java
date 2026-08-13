package com.deadlinezero.game.ai;

import java.util.WeakHashMap;

import com.deadlinezero.game.entities.Enemy;

/** Run-local identity registry for specialized LEAPER enemies. */
public final class LeaperRegistry {
    private final WeakHashMap<Enemy, Boolean> entries = new WeakHashMap<>();

    public void register(Enemy enemy) {
        if (enemy != null) entries.put(enemy, Boolean.TRUE);
    }

    public boolean contains(Enemy enemy) {
        return enemy != null && entries.containsKey(enemy);
    }
}
