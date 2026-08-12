package com.deadlinezero.game.ai;

/** Health-threshold phase controller. Phase transitions are edge-triggered. */
public final class BossPhaseController {
    private int phase = 1;
    private boolean phaseChanged;

    public int phase() { return phase; }
    public boolean consumePhaseChanged() {
        boolean changed = phaseChanged;
        phaseChanged = false;
        return changed;
    }

    public void update(float hpRatio) {
        int next = hpRatio <= 0.33f ? 3 : hpRatio <= 0.66f ? 2 : 1;
        if (next != phase) {
            phase = next;
            phaseChanged = true;
        }
    }

    public float speedMultiplier() {
        return switch (phase) {
            case 2 -> 1.14f;
            case 3 -> 1.28f;
            default -> 1f;
        };
    }

    public float cooldownMultiplier() {
        return switch (phase) {
            case 2 -> 0.84f;
            case 3 -> 0.68f;
            default -> 1f;
        };
    }
}
