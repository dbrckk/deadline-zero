package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.combat.WeaponSignatureRuntime;
import com.deadlinezero.game.meta.SingularityCoreRuntime;

public final class Projectile {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public float damage;
    public float life;
    public float radius = 0.11f;
    public float knockback;
    public int penetrationRemaining;
    public boolean active;
    public boolean critical;
    public boolean singularity;
    public boolean weaponSignature;
    public long generation;
    public DamageElement element = DamageElement.KINETIC;
    public Enemy lastHit;

    public Projectile spawn(float x, float y, float vx, float vy, float damage, boolean critical,
                            int penetration, float knockback, DamageElement element) {
        generation++;
        position.set(x, y);
        velocity.set(vx, vy);
        WeaponSignatureRuntime.ShotModifier signature = WeaponSignatureRuntime.consumeShot(critical);
        singularity = SingularityCoreRuntime.consumeShotMark();
        weaponSignature = signature.active();
        float signatureDamage = damage * signature.damageMultiplier();
        this.damage = singularity ? signatureDamage * 1.35f : signatureDamage;
        this.life = 1.5f;
        this.active = true;
        this.critical = critical || signature.forceCritical();
        this.penetrationRemaining = penetration + signature.penetrationBonus() + (singularity ? 2 : 0);
        float signatureKnockback = knockback * signature.knockbackMultiplier();
        this.knockback = singularity ? signatureKnockback * 1.8f : signatureKnockback;
        this.element = singularity ? DamageElement.SHOCK : element;
        this.radius = singularity ? Math.max(.16f, signature.radius()) : signature.radius();
        this.lastHit = null;
        return this;
    }
}
