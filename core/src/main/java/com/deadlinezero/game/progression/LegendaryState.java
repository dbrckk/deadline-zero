package com.deadlinezero.game.progression;

/** Run-local ownership flags for one-shot legendary upgrades. */
public final class LegendaryState {
    private boolean overdrive;
    private boolean singularity;
    private boolean apex;

    public boolean hasOverdrive() { return overdrive; }
    public boolean hasSingularity() { return singularity; }
    public boolean hasApex() { return apex; }
    public boolean hasAny() { return overdrive || singularity || apex; }

    public boolean grantOverdrive() {
        if (overdrive) return false;
        overdrive = true;
        return true;
    }

    public boolean grantSingularity() {
        if (singularity) return false;
        singularity = true;
        return true;
    }

    public boolean grantApex() {
        if (apex) return false;
        apex = true;
        return true;
    }
}
