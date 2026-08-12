package com.deadlinezero.game.combat;

public final class WeaponDefinition {
    public final String id;
    public final String displayName;
    public final float damage;
    public final float fireInterval;
    public final float projectileSpeed;
    public final int projectileCount;
    public final float spreadDegrees;
    public final float critChance;
    public final float critMultiplier;
    public final int penetration;
    public final float knockback;
    public final DamageElement element;

    public WeaponDefinition(String id, String displayName, float damage, float fireInterval,
                            float projectileSpeed, int projectileCount, float spreadDegrees,
                            float critChance, float critMultiplier, int penetration,
                            float knockback, DamageElement element) {
        this.id = id;
        this.displayName = displayName;
        this.damage = damage;
        this.fireInterval = fireInterval;
        this.projectileSpeed = projectileSpeed;
        this.projectileCount = projectileCount;
        this.spreadDegrees = spreadDegrees;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.penetration = penetration;
        this.knockback = knockback;
        this.element = element;
    }
}
