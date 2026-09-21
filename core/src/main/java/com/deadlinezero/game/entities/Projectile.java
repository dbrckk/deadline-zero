package com.deadlinezero.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.deadlinezero.game.audio.AudioDirector;
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
    public WeaponSignatureRuntime.Kind weaponSignatureKind = WeaponSignatureRuntime.Kind.NONE;
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
        weaponSignatureKind = signature.kind();
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

        switch (weaponSignatureKind) {
            case ION_OVERCHARGE -> AudioDirector.playGlobal(AudioDirector.Cue.ION_OVERCHARGE, 1.08f, 0f);
            case CINDER_OVERHEAT -> AudioDirector.playGlobal(AudioDirector.Cue.CINDER_OVERHEAT, .82f, 0f);
            case TEMPEST_SURGE -> AudioDirector.playGlobal(AudioDirector.Cue.TEMPEST_SURGE, 1.18f, 0f);
            case WHITEOUT_SHATTER -> AudioDirector.playGlobal(AudioDirector.Cue.WHITEOUT_SHATTER, .92f, 0f);
            case PHOENIX_IGNITION -> AudioDirector.playGlobal(AudioDirector.Cue.PHOENIX_IGNITION, 1.02f, 0f);
            case NONE -> { }
        }
        return this;
    }
}
