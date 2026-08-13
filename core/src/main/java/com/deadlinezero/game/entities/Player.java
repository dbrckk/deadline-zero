package com.deadlinezero.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.abilities.AbilityLoadout;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.combat.WeaponRuntime;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.input.MobileCombatInput;
import com.deadlinezero.game.meta.RunLoadoutContext;
import com.deadlinezero.game.progression.LegendaryState;
import com.deadlinezero.game.visual.CombatVisualEvents;

public final class Player extends ActorState {
    public int level = 1;
    public int xp = 0;
    public int xpNext = 28;
    public float moveSpeed = GameConfig.PLAYER_SPEED * RunLoadoutContext.moveSpeedMultiplier();
    public float dashCooldown = 3.2f * RunLoadoutContext.dashCooldownMultiplier();
    public float dashTimer;
    public float invulnerabilityTimer;
    public float visualHitTimer;
    public final WeaponRuntime weapon = new WeaponRuntime(RunLoadoutContext.weaponDefinition());
    public final AbilityLoadout abilities = new AbilityLoadout();
    public final LegendaryState legendary = new LegendaryState();
    private final MobileCombatInput mobileCombatInput = new MobileCombatInput();
    private final Vector2 dashDirection = new Vector2();

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

        AccessibilitySettings settings = AccessibilitySettings.active();
        if (canDash() && velocity.len2() > .08f && mobileCombatInput.dashJustPressed(settings.uiScale)) {
            dashDirection.set(velocity).nor();
            position.mulAdd(dashDirection, 4.8f);
            triggerDash();
            CombatVisualEvents.markDash();
            AudioDirector.playGlobal(AudioDirector.Cue.DASH);
            if (settings.haptics) {
                try { Gdx.input.vibrate(18); } catch (Throwable ignored) { }
            }
        }
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
            CombatVisualEvents.markLevelUp();
            return true;
        }
        return false;
    }
}
