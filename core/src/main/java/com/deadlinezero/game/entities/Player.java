package com.deadlinezero.game.entities;

import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponRuntime;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.meta.RunLoadoutContext;

public final class Player extends ActorState {
    public int level = 1;
    public int xp = 0;
    public int xpNext = 28;
    public float moveSpeed = GameConfig.PLAYER_SPEED * RunLoadoutContext.moveSpeedMultiplier();
    public float dashCooldown = 3.2f * RunLoadoutContext.dashCooldownMultiplier();
    public float dashTimer;
    public float invulnerabilityTimer;
    public float visualHitTimer;
    public final WeaponRuntime weapon = new WeaponRuntime(WeaponCatalog.AR9);
    public final AbilityLoadout abilities = new AbilityLoadout();

    public Player(float x, float y) {
        super(x, y, 0.42f, GameConfig.PLAYER_MAX_HP * RunLoadoutContext.maxHpMultiplier());
        weapon.damage *= RunLoadoutContext.weaponDamageMultiplier();
        weapon.critChance = Math.min(.75f, weapon.critChance + RunLoadoutContext.critChanceBonus());
        weapon.critMultiplier += RunLoadoutContext.critDamageBonus();
        for (int i = 0; i < RunLoadoutContext.startingTeslaLevel(); i++) abilities.upgrade(AbilityType.TESLA_ORB);
    }

    public boolean canDash() { return dashTimer <= 0f; }

    public void triggerDash() {
        dashTimer = dashCooldown;
        invulnerabilityTimer = Math.max(invulnerabilityTimer, RunLoadoutContext.dashInvulnerabilitySeconds());
    }

    public boolean invulnerable() { return invulnerabilityTimer > 0f; }

    public void updateRuntime(float dt) {
        dashTimer = Math.max(0f, dashTimer - dt);
        invulnerabilityTimer = Math.max(0f, invulnerabilityTimer - dt);
        visualHitTimer = Math.max(0f, visualHitTimer - dt);
    }

    @Override public void damage(float amount) {
        if (invulnerable()) return;
        float before = hp;
        super.damage(amount * RunLoadoutContext.damageTakenMultiplier());
        if (hp < before) visualHitTimer = Math.max(visualHitTimer, .16f);
    }

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
