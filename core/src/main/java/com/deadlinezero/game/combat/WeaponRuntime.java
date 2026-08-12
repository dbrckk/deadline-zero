package com.deadlinezero.game.combat;

public final class WeaponRuntime {
    public final WeaponDefinition definition;
    public float damage;
    public float fireInterval;
    public float projectileSpeed;
    public int projectileCount;
    public float spreadDegrees;
    public float critChance;
    public float critMultiplier;
    public int penetration;
    public float knockback;
    public DamageElement element;

    public WeaponRuntime(WeaponDefinition definition) {
        this.definition = definition;
        reset();
    }

    public void reset() {
        damage = definition.damage;
        fireInterval = definition.fireInterval;
        projectileSpeed = definition.projectileSpeed;
        projectileCount = definition.projectileCount;
        spreadDegrees = definition.spreadDegrees;
        critChance = definition.critChance;
        critMultiplier = definition.critMultiplier;
        penetration = definition.penetration;
        knockback = definition.knockback;
        element = definition.element;
    }
}
