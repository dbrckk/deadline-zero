package com.deadlinezero.game.progression;

import com.deadlinezero.game.entities.Enemy;

/** Allocation-free run-local event protocol state. */
public final class CombatProtocolState {
    public static final int RHYTHM_VOLLEYS = 6;
    public static final int KILLCHAIN_KILLS = 8;

    public record VolleyModifier(float damageMultiplier, boolean forcedCrit, int bonusPenetration) {
        static final VolleyModifier NONE = new VolleyModifier(1f, false, 0);
    }

    private boolean rhythmEnabled;
    private boolean killchainEnabled;
    private boolean reactionEnabled;
    private int volleyCounter;
    private int killCounter;
    private boolean killchainArmed;

    public void enableRhythm() { rhythmEnabled = true; }
    public void enableKillchain() { killchainEnabled = true; }
    public void enableReactionCore() { reactionEnabled = true; }

    public boolean rhythmEnabled() { return rhythmEnabled; }
    public boolean killchainEnabled() { return killchainEnabled; }
    public boolean reactionEnabled() { return reactionEnabled; }
    public boolean killchainArmed() { return killchainArmed; }

    public VolleyModifier onVolley() {
        boolean rhythmProc = false;
        if (rhythmEnabled) {
            volleyCounter++;
            if (volleyCounter >= RHYTHM_VOLLEYS) {
                volleyCounter = 0;
                rhythmProc = true;
            }
        }
        boolean killProc = killchainArmed;
        killchainArmed = false;
        if (!rhythmProc && !killProc) return VolleyModifier.NONE;
        float damage = rhythmProc && killProc ? 1.75f : (killProc ? 1.45f : 1.30f);
        return new VolleyModifier(damage, rhythmProc, killProc ? 1 : 0);
    }

    public void onKill() {
        if (!killchainEnabled) return;
        killCounter++;
        if (killCounter >= KILLCHAIN_KILLS) {
            killCounter = 0;
            killchainArmed = true;
        }
    }

    public float reactionBonus(float triggeringDamage, Enemy.ElementReaction reaction) {
        if (!reactionEnabled || reaction == null || reaction == Enemy.ElementReaction.NONE) return 0f;
        return Math.max(0f, triggeringDamage) * .35f;
    }
}
