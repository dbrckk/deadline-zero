package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.ai.BossIdentity;
import org.junit.jupiter.api.Test;

final class BossPhaseTransitionProfileTest {
    @Test void phaseThreeEscalatesPresentation() {
        var phase2 = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 2);
        var phase3 = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 3);
        assertTrue(phase3.duration() > phase2.duration());
        assertTrue(phase3.radiusMultiplier() > phase2.radiusMultiplier());
        assertTrue(phase3.vibrationMs() > phase2.vibrationMs());
    }

    @Test void identitiesHaveDistinctAudioWeight() {
        var revenant = BossPhaseTransitionProfile.forPhase(BossIdentity.REVENANT, 3);
        var alpha = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 3);
        var warden = BossPhaseTransitionProfile.forPhase(BossIdentity.WARDEN, 3);
        assertTrue(revenant.audioPitch() > alpha.audioPitch());
        assertTrue(alpha.audioPitch() > warden.audioPitch());
    }
}
