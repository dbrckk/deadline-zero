package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deadlinezero.game.abilities.DroneDoctrine;
import org.junit.jupiter.api.Test;

final class CompanionRendererTest {
    @Test void hunterUsesAggressiveWideOrbit() {
        assertEquals(2.25f, CompanionRenderer.orbitRadius(DroneDoctrine.HUNTER), .0001f);
        assertEquals(145f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.HUNTER), .0001f);
        assertEquals(.76f, CompanionRenderer.baseSize(DroneDoctrine.HUNTER), .0001f);
    }

    @Test void sentinelUsesDefensiveCompactOrbit() {
        assertEquals(1.55f, CompanionRenderer.orbitRadius(DroneDoctrine.SENTINEL), .0001f);
        assertEquals(95f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.SENTINEL), .0001f);
        assertEquals(.86f, CompanionRenderer.baseSize(DroneDoctrine.SENTINEL), .0001f);
    }

    @Test void baseDroneKeepsNeutralPresentation() {
        assertEquals(1.80f, CompanionRenderer.orbitRadius(DroneDoctrine.NONE), .0001f);
        assertEquals(110f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.NONE), .0001f);
        assertEquals(.80f, CompanionRenderer.baseSize(DroneDoctrine.NONE), .0001f);
    }
}
