package com.deadlinezero.game.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

final class AudioDirectorFallbackTest {
    @Test void bossPhaseFallsBackToBossHitWhenDedicatedAssetIsMissing() {
        assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.BOSS_PHASE));
    }

    @Test void ordinaryCuesDoNotUnexpectedlyAlias() {
        assertNull(AudioDirector.fallbackCue(AudioDirector.Cue.SHOT));
        assertNull(AudioDirector.fallbackCue(AudioDirector.Cue.BOSS_KILL));
    }
}
