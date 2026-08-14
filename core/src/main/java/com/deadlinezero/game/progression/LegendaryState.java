package com.deadlinezero.game.progression;

/** Run-local ownership flags for one-shot legendary upgrades. */
public final class LegendaryState {
    private boolean overdrive;
    private boolean singularity;
    private boolean apex;
    private boolean ionCascade;
    private boolean cinderFurnace;
    private boolean railPhaseLance;
    private boolean cryoPrism;
    private boolean arcOverload;
    private boolean vanguardProtocol;
    private boolean scatterMaelstrom;
    private boolean infernoPyroclasm;
    private boolean breacherRupture;

    public boolean hasOverdrive() { return overdrive; }
    public boolean hasSingularity() { return singularity; }
    public boolean hasApex() { return apex; }
    public boolean hasIonCascade() { return ionCascade; }
    public boolean hasCinderFurnace() { return cinderFurnace; }
    public boolean hasRailPhaseLance() { return railPhaseLance; }
    public boolean hasCryoPrism() { return cryoPrism; }
    public boolean hasArcOverload() { return arcOverload; }
    public boolean hasVanguardProtocol() { return vanguardProtocol; }
    public boolean hasScatterMaelstrom() { return scatterMaelstrom; }
    public boolean hasInfernoPyroclasm() { return infernoPyroclasm; }
    public boolean hasBreacherRupture() { return breacherRupture; }
    public boolean hasAny() {
        return overdrive || singularity || apex || ionCascade || cinderFurnace
            || railPhaseLance || cryoPrism || arcOverload || vanguardProtocol
            || scatterMaelstrom || infernoPyroclasm || breacherRupture;
    }

    public boolean grantOverdrive() { if (overdrive) return false; overdrive = true; return true; }
    public boolean grantSingularity() { if (singularity) return false; singularity = true; return true; }
    public boolean grantApex() { if (apex) return false; apex = true; return true; }
    public boolean grantIonCascade() { if (ionCascade) return false; ionCascade = true; return true; }
    public boolean grantCinderFurnace() { if (cinderFurnace) return false; cinderFurnace = true; return true; }
    public boolean grantRailPhaseLance() { if (railPhaseLance) return false; railPhaseLance = true; return true; }
    public boolean grantCryoPrism() { if (cryoPrism) return false; cryoPrism = true; return true; }
    public boolean grantArcOverload() { if (arcOverload) return false; arcOverload = true; return true; }
    public boolean grantVanguardProtocol() { if (vanguardProtocol) return false; vanguardProtocol = true; return true; }
    public boolean grantScatterMaelstrom() { if (scatterMaelstrom) return false; scatterMaelstrom = true; return true; }
    public boolean grantInfernoPyroclasm() { if (infernoPyroclasm) return false; infernoPyroclasm = true; return true; }
    public boolean grantBreacherRupture() { if (breacherRupture) return false; breacherRupture = true; return true; }
}
