package com.deadlinezero.game.progression;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Player;

public enum Upgrade {
    RAPID_FIRE("Overclock", "Fire rate +18%") { public void apply(Player p) { p.weapon.fireInterval *= 0.82f; } },
    DAMAGE("High Caliber", "Damage +25%") { public void apply(Player p) { p.weapon.damage *= 1.25f; } },
    SPEED("Adrenaline", "Move speed +14%") { public void apply(Player p) { p.moveSpeed *= 1.14f; } },
    VITALITY("Nano Repair", "+25 max HP and heal") { public void apply(Player p) { p.maxHp += 25; p.hp = Math.min(p.maxHp, p.hp + 35); } },
    MULTISHOT("Twin Protocol", "+1 projectile") { public void apply(Player p) { p.weapon.projectileCount = Math.min(7, p.weapon.projectileCount + 1); } },
    CRIT("Hunter OS", "+8% crit chance") { public void apply(Player p) { p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .08f); } },
    BALLISTICS("Rail Accelerator", "Projectile speed +22%") { public void apply(Player p) { p.weapon.projectileSpeed *= 1.22f; } },
    PENETRATION("Tungsten Core", "+1 penetration") { public void apply(Player p) { p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); } },
    KNOCKBACK("Kinetic Driver", "Knockback +35%") { public void apply(Player p) { p.weapon.knockback *= 1.35f; } },
    INCENDIARY("Thermite Protocol", "Shots ignite enemies") { public void apply(Player p) { p.weapon.element = DamageElement.FIRE; } },
    CRYO("Cryo Protocol", "Shots slow enemies") { public void apply(Player p) { p.weapon.element = DamageElement.FROST; } },
    SHOCK("Arc Protocol", "Shots briefly stun enemies") { public void apply(Player p) { p.weapon.element = DamageElement.SHOCK; } };

    public final String title, description;
    Upgrade(String title, String description) { this.title = title; this.description = description; }
    public abstract void apply(Player player);
}
