package com.deadlinezero.game.entities;

import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponRuntime;
import com.deadlinezero.game.config.GameConfig;

public final class Player extends ActorState {
    public int level = 1;
    public int xp = 0;
    public int xpNext = 28;
    public float moveSpeed = GameConfig.PLAYER_SPEED;
    public float dashCooldown = 3.2f;
    public float dashTimer;
    public final WeaponRuntime weapon = new WeaponRuntime(WeaponCatalog.AR9);
    public final AbilityLoadout abilities = new AbilityLoadout();

    public Player(float x, float y) { super(x, y, 0.42f, GameConfig.PLAYER_MAX_HP); }

    public boolean canDash() { return dashTimer <= 0f; }
    public void triggerDash() { dashTimer = dashCooldown; }
    public void updateRuntime(float dt) { dashTimer = Math.max(0f, dashTimer - dt); }

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
