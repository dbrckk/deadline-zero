package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;

/** Central contract for authored combat audio expected in a production build. */
public final class AudioManifest {
    private AudioManifest() { }

    public static final String[] REQUIRED_COMBAT = {
        "audio/combat/shot.ogg",
        "audio/combat/dash.ogg",
        "audio/combat/level_up.ogg",
        "audio/combat/legendary_overdrive.ogg",
        "audio/combat/legendary_singularity.ogg",
        "audio/combat/legendary_apex.ogg",
        "audio/combat/boss_alpha_intro.ogg",
        "audio/combat/boss_revenant_intro.ogg",
        "audio/combat/boss_phase.ogg",
        "audio/combat/forge_hound_attack.ogg",
        "audio/combat/cinder_gunner_attack.ogg",
        "audio/combat/slag_guard_attack.ogg",
        "audio/combat/phase_stalker_attack.ogg",
        "audio/combat/static_seer_attack.ogg",
        "audio/combat/null_ward_attack.ogg"
    };

    public static int validate() {
        int missing = 0;
        for (String path : REQUIRED_COMBAT) {
            if (Gdx.files.internal(path).exists()) continue;
            missing++;
            Gdx.app.log("AudioManifest", "Missing production audio: " + path);
        }
        if (missing == 0) Gdx.app.log("AudioManifest", "Production combat audio validation passed.");
        else Gdx.app.log("AudioManifest", "Production combat audio incomplete: " + missing + " required files missing.");
        return missing;
    }
}
