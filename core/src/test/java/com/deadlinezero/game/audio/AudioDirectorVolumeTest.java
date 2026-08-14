package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class AudioDirectorVolumeTest {
    @Test void clampsFiniteVolumesToUnitRange() {
        assertEquals(0f, AudioDirector.normalizeVolume(-0.5f));
        assertEquals(0.35f, AudioDirector.normalizeVolume(0.35f));
        assertEquals(1f, AudioDirector.normalizeVolume(1.8f));
    }

    @Test void rejectsNonFiniteVolumesToSafeSilence() {
        assertEquals(0f, AudioDirector.normalizeVolume(Float.NaN));
        assertEquals(0f, AudioDirector.normalizeVolume(Float.POSITIVE_INFINITY));
        assertEquals(0f, AudioDirector.normalizeVolume(Float.NEGATIVE_INFINITY));
    }
}
