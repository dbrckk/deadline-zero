package com.deadlinezero.game.abilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class DroneDoctrineRulesTest {
    @Test void hunterIsTheOffensiveDoctrine() {
        assertEquals(1.30f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.HUNTER), .0001f);
        assertEquals(.72f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.HUNTER, false), .0001f);
        assertEquals(.82f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.HUNTER, true), .0001f);
        assertEquals(0f, DroneDoctrineRules.interceptionRange(DroneDoctrine.HUNTER), .0001f);
    }

    @Test void sentinelTradesDamageForProjectileInterception() {
        assertEquals(.88f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.SENTINEL), .0001f);
        assertEquals(0f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.SENTINEL, false), .0001f);
        assertEquals(3.4f, DroneDoctrineRules.interceptionRange(DroneDoctrine.SENTINEL), .0001f);
    }

    @Test void noDoctrinePreservesExistingDroneBehavior() {
        assertEquals(1f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.NONE), .0001f);
        assertEquals(0f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.NONE, false), .0001f);
        assertEquals(.62f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.NONE, true), .0001f);
        assertEquals(0f, DroneDoctrineRules.interceptionRange(DroneDoctrine.NONE), .0001f);
    }
}
