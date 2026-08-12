package com.deadlinezero.game.progression;

public enum UpgradeRarity {
    COMMON(1f), RARE(1.18f), EPIC(1.42f), LEGENDARY(1.8f);

    public final float power;
    UpgradeRarity(float power) { this.power = power; }
}
