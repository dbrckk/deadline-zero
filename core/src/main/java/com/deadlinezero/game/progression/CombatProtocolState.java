package com.deadlinezero.game.progression;

import com.deadlinezero.game.entities.Enemy;

/** Allocation-free run-local event protocol state. */
public final class CombatProtocolState {
    public static final int RHYTHM_VOLLEYS = 6;
    public static final int RHYTHM_EVOLVED_VOLLEYS = 4;
    public static final int KILLCHAIN_KILLS = 8;
    public static final int KILLCHAIN_EVOLVED_KILLS = 5;

    public record VolleyModifier(float damageMultiplier, boolean forcedCrit, int bonusPenetration) {
        static final VolleyModifier NONE = new VolleyModifier(1f, false, 0);
    }

    private boolean rhythmEnabled;
    private boolean killchainEnabled;
    private boolean reactionEnabled;
    private boolean rhythmEvolved;
    private boolean killchainEvolved;
    private boolean reactionEvolved;
    private int volleyCounter;
    private int killCounter;
    private boolean killchainArmed;

    public void enableRhythm() { rhythmEnabled = true; }
    public void enableKillchain() { killchainEnabled = true; }
    public void enableReactionCore() { reactionEnabled = true; }
    public void evolveRhythm() { if (rhythmEnabled) rhythmEvolved = true; }
    public void evolveKillchain() { if (killchainEnabled) killchainEvolved = true; }
    public void evolveReactionCore() { if (reactionEnabled) reactionEvolved = true; }

    public boolean rhythmEnabled() { return rhythmEnabled; }
    public boolean killchainEnabled() { return killchainEnabled; }
    public boolean reactionEnabled() { return reactionEnabled; }
    public boolean rhythmEvolved() { return rhythmEvolved; }
    public boolean killchainEvolved() { return killchainEvolved; }
    public boolean reactionEvolved() { return reactionEvolved; }
    public boolean killchainArmed() { return killchainArmed; }

    public VolleyModifier onVolley() {
        boolean rhythmProc = false;
        if (rhythmEnabled) {
            volleyCounter++;
            int cadence = rhythmEvolved ? RHYTHM_EVOLVED_VOLLEYS : RHYTHM_VOLLEYS;
            if (volleyCounter >= cadence) {
                volleyCounter = 0;
                rhythmProc = true;
            }
        }
        boolean killProc = killchainArmed;
        killchainArmed = false;
        if (!rhythmProc && !killProc) return VolleyModifier.NONE;
        float rhythmDamage = rhythmEvolved ? 1.45f : 1.30f;
        float killDamage = killchainEvolved ? 1.60f : 1.45f;
        float damage = rhythmProc && killProc
            ? Math.min(2.05f, rhythmDamage + killDamage - 1f)
            : (killProc ? killDamage : rhythmDamage);
        int penetration = killProc ? (killchainEvolved ? 2 : 1) : 0;
        return new VolleyModifier(damage, rhythmProc, penetration);
    }

    public boolean onKill() {
        if (!killchainEnabled) return false;
        killCounter++;
        int threshold = killchainEvolved ? KILLCHAIN_EVOLVED_KILLS : KILLCHAIN_KILLS;
        if (killCounter < threshold) return false;
        killCounter = 0;
        boolean newlyArmed = !killchainArmed;
        killchainArmed = true;
        return newlyArmed;
    }

    public float reactionBonus(float triggeringDamage, Enemy.ElementReaction reaction) {
        if (!reactionEnabled || reaction == null || reaction == Enemy.ElementReaction.NONE) return 0f;
        return Math.max(0f, triggeringDamage) * (reactionEvolved ? .55f : .35f);
    }
}
