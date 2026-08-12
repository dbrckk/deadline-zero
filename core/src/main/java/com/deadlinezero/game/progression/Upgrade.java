package com.deadlinezero.game.progression;

import com.deadlinezero.game.entities.Player;

public enum Upgrade {
    RAPID_FIRE("Overclock", "Fire rate +18%") { public void apply(Player p) { p.fireInterval *= 0.82f; } },
    DAMAGE("High Caliber", "Damage +25%") { public void apply(Player p) { p.damage *= 1.25f; } },
    SPEED("Adrenaline", "Move speed +14%") { public void apply(Player p) { p.moveSpeed *= 1.14f; } },
    VITALITY("Nano Repair", "+25 max HP and heal") { public void apply(Player p) { p.maxHp += 25; p.hp = Math.min(p.maxHp, p.hp + 35); } },
    MULTISHOT("Twin Protocol", "+1 projectile") { public void apply(Player p) { p.projectileCount = Math.min(5, p.projectileCount + 1); } },
    CRIT("Hunter OS", "+8% crit chance") { public void apply(Player p) { p.critChance = Math.min(.55f, p.critChance + .08f); } },
    BALLISTICS("Rail Accelerator", "Projectile speed +22%") { public void apply(Player p) { p.projectileSpeed *= 1.22f; } };

    public final String title, description;
    Upgrade(String title, String description) { this.title = title; this.description = description; }
    public abstract void apply(Player player);
}
