package com.deadlinezero.game.entities;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponRuntime;
import com.deadlinezero.game.config.GameConfig;

public final class Player extends ActorState {
    public int level = 1;
    public int xp = 0;
    public int xpNext = 28;
    public float moveSpeed = GameConfig.PLAYER_SPEED;
    public final WeaponRuntime weapon = new WeaponRuntime(WeaponCatalog.AR9);

    public Player(float x, float y) { super(x, y, 0.42f, GameConfig.PLAYER_MAX_HP); }

    public boolean addXp(int amount) {
        xp += amount;
        if (xp >= xpNext) {
            xp -= xpNext;
            level++;
            xpNext = Math.round(xpNext * 1.32f + 8);
            return true;
        }
        return false;
    }
}
