package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.meta.RunStageContext;

public final class EnemyBiomeElementResistanceTest {
    @AfterEach void resetStage() {
        RunStageContext.begin(1);
    }

    @Test public void forgeHoundAttenuatesFireStatusPower() {
        RunStageContext.begin(10);
        Enemy e = enemy(Enemy.Type.RUNNER);
        e.applyElement(DamageElement.FIRE, 100f);

        assertEquals(13.64f, e.burnDps, .001f);
        assertTrue(e.burnTimer > 0f);
    }

    @Test public void cinderGunnerUsesItsOwnFireResistance() {
        RunStageContext.begin(10);
        Enemy e = enemy(Enemy.Type.RANGED);
        e.applyElement(DamageElement.FIRE, 100f);

        assertEquals(15.84f, e.burnDps, .001f);
    }

    @Test public void nonResistedElementKeepsFullStatusPower() {
        RunStageContext.begin(10);
        Enemy e = enemy(Enemy.Type.RUNNER);
        e.applyElement(DamageElement.FIRE, 100f);
        float resisted = e.burnDps;

        RunStageContext.begin(1);
        Enemy baseline = enemy(Enemy.Type.RUNNER);
        baseline.applyElement(DamageElement.FIRE, 100f);

        assertEquals(22f, baseline.burnDps, .001f);
        assertTrue(resisted < baseline.burnDps);
    }

    @Test public void phaseStalkerAttenuatesShockReactionDamage() {
        RunStageContext.begin(20);
        Enemy e = enemy(Enemy.Type.PHANTOM);
        e.applyElement(DamageElement.FIRE, 100f);
        float before = e.hp;
        e.applyElement(DamageElement.SHOCK, 100f);

        assertEquals(Enemy.ElementReaction.OVERLOAD, e.lastReaction);
        assertEquals(13.64f, before - e.hp, .01f);
    }

    @Test public void nullWardAttenuatesFrostReactionDamage() {
        RunStageContext.begin(20);
        Enemy e = enemy(Enemy.Type.REGENERATOR);
        e.applyElement(DamageElement.FIRE, 100f);
        float before = e.hp;
        e.applyElement(DamageElement.FROST, 100f);

        assertEquals(Enemy.ElementReaction.STEAM_BURST, e.lastReaction);
        assertEquals(18.48f, before - e.hp, .01f);
    }

    private Enemy enemy(Enemy.Type type) {
        return new Enemy(type, 0f, 0f, 1000f, 1f, .5f, 10f, 1);
    }
}
