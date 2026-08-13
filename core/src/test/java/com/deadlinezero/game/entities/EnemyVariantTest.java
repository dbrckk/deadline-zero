package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

final class EnemyVariantTest {
    @Test void armoredResistsKnockback() {
        Enemy normal = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
        Enemy armored = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
        normal.variant = Enemy.Variant.NORMAL;
        armored.variant = Enemy.Variant.NORMAL;
        armored.applyVariant(Enemy.Variant.ARMORED);
        normal.addImpulse(10f, 0f);
        armored.addImpulse(10f, 0f);
        assertTrue(armored.impulse.x < normal.impulse.x * .5f);
    }

    @Test void feralEnragesAtLowHealth() {
        Enemy feral = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
        feral.variant = Enemy.Variant.NORMAL;
        feral.applyVariant(Enemy.Variant.FERAL);
        float healthySpeed = feral.effectiveSpeed();
        feral.hp = feral.maxHp * .30f;
        float enragedSpeed = feral.effectiveSpeed();
        assertTrue(enragedSpeed > healthySpeed * 1.15f);
    }

    @Test void swiftHasBurstWindow() {
        Enemy swift = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
        swift.variant = Enemy.Variant.NORMAL;
        swift.applyVariant(Enemy.Variant.SWIFT);
        swift.variantTime = .10f;
        float burst = swift.effectiveSpeed();
        swift.variantTime = 1.0f;
        float cruise = swift.effectiveSpeed();
        assertTrue(burst > cruise * 1.20f);
    }

    @Test void bossNeverAcceptsChampionVariant() {
        Enemy boss = new Enemy(Enemy.Type.BOSS, 0f, 0f, 1000f, 1f, 1f, 20f, 100);
        boss.applyVariant(Enemy.Variant.FERAL);
        assertEquals(Enemy.Variant.NORMAL, boss.variant);
    }
}
