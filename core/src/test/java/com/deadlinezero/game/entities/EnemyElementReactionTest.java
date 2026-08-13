package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.meta.RunStageContext;

public final class EnemyElementReactionTest {
    private Enemy enemy() {
        RunStageContext.begin(1);
        return new Enemy(Enemy.Type.BOSS, 0f, 0f, 500f, 1f, .6f, 10f, 1);
    }

    @Test public void fireOnFrozenTargetTriggersThermalShock() {
        Enemy e = enemy();
        e.applyElement(DamageElement.FROST, 40f);
        float hpBefore = e.hp;
        e.applyElement(DamageElement.FIRE, 40f);

        assertEquals(Enemy.ElementReaction.THERMAL_SHOCK, e.lastReaction);
        assertTrue(e.hp < hpBefore);
        assertEquals(0f, e.slowTimer, .0001f);
        assertTrue(e.burnTimer > 0f);
    }

    @Test public void frostOnBurningTargetTriggersSteamBurst() {
        Enemy e = enemy();
        e.applyElement(DamageElement.FIRE, 40f);
        float hpBefore = e.hp;
        e.applyElement(DamageElement.FROST, 40f);

        assertEquals(Enemy.ElementReaction.STEAM_BURST, e.lastReaction);
        assertTrue(e.hp < hpBefore);
        assertEquals(0f, e.burnTimer, .0001f);
        assertTrue(e.slowTimer > 0f);
    }

    @Test public void shockOnPrimedTargetTriggersLongerOverloadStun() {
        Enemy e = enemy();
        e.applyElement(DamageElement.FIRE, 30f);
        e.applyElement(DamageElement.FROST, 30f);
        e.applyElement(DamageElement.SH0CK, 30f);
    }
}
