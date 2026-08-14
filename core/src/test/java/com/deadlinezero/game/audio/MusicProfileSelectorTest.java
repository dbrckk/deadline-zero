package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class MusicProfileSelectorTest {
    @Test void stagesMapToStableIntensityBands() {
        assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(-3));
        assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(1));
        assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(3));
        assertEquals(MusicProfileSelector.Profile.PRESSURE, MusicProfileSelector.forStage(4));
        assertEquals(MusicProfileSelector.Profile.PRESSURE, MusicProfileSelector.forStage(6));
        assertEquals(MusicProfileSelector.Profile.APEX, MusicProfileSelector.forStage(7));
        assertEquals(MusicProfileSelector.Profile.APEX, MusicProfileSelector.forStage(99));
    }

    @Test void profilesExposeDeterministicAssetPaths() {
        assertEquals("audio/music/combat.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.SURVIVAL));
        assertEquals("audio/music/combat_pressure.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.PRESSURE));
        assertEquals("audio/music/combat_apex.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.APEX));
        assertEquals("audio/music/combat.ogg", MusicProfileSelector.assetPath(null));
    }
}