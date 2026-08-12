package com.deadlinezero.game.abilities;

public final class AbilityRuntime {
    public float teslaTimer;
    public float missileTimer;
    public float cryoTimer;
    public float droneTimer;
    public float orbitalTimer;
    public float orbitalAngle;

    public void update(float dt) {
        teslaTimer = Math.max(0f, teslaTimer - dt);
        missileTimer = Math.max(0f, missileTimer - dt);
        cryoTimer = Math.max(0f, cryoTimer - dt);
        droneTimer = Math.max(0f, droneTimer - dt);
        orbitalTimer = Math.max(0f, orbitalTimer - dt);
        orbitalAngle = (orbitalAngle + dt * 110f) % 360f;
    }

    public boolean readyTesla() { return teslaTimer <= 0f; }
    public boolean readyMissile() { return missileTimer <= 0f; }
    public boolean readyCryo() { return cryoTimer <= 0f; }
    public boolean readyDrone() { return droneTimer <= 0f; }
    public boolean readyOrbital() { return orbitalTimer <= 0f; }

    public void resetTesla(int level) { teslaTimer = Math.max(.75f, 2.7f - level * .28f); }
    public void resetMissile(int level) { missileTimer = Math.max(1.1f, 4.4f - level * .42f); }
    public void resetCryo(int level) { cryoTimer = Math.max(2.2f, 7.2f - level * .55f); }
    public void resetDrone(int level) { droneTimer = Math.max(.35f, 1.1f - level * .10f); }
    public void resetOrbital(int level) { orbitalTimer = Math.max(.18f, .46f - level * .045f); }
}
