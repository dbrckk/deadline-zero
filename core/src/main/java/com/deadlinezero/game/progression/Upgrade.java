package com.deadlinezero.game.progression;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Player;

public enum Upgrade {
    RAPID_FIRE("Overclock", "Fire rate +18%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.fireInterval *= 0.82f; } },
    DAMAGE("High Caliber", "Damage +25%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.damage *= 1.25f; } },
    SPEED("Adrenaline", "Move speed +14%", UpgradeRarity.COMMON) { public void apply(Player p) { p.moveSpeed *= 1.14f; } },
    VITALITY("Nano Repair", "+25 max HP and heal", UpgradeRarity.COMMON) { public void apply(Player p) { p.maxHp += 25; p.hp = Math.min(p.maxHp, p.hp + 35); } },
    MULTISHOT("Twin Protocol", "+1 projectile", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.projectileCount = Math.min(7, p.weapon.projectileCount + 1); } },
    CRIT("Hunter OS", "+8% crit chance", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .08f); } },
    BALLISTICS("Rail Accelerator", "Projectile speed +22%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.projectileSpeed *= 1.22f; } },
    PENETRATION("Tungsten Core", "+1 penetration", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); } },
    KNOCKBACK("Kinetic Driver", "Knockback +35%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.knockback *= 1.35f; } },
    INCENDIARY("Thermite Protocol", "Shots ignite enemies", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.FIRE; } },
    CRYO("Cryo Protocol", "Shots slow enemies", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.FROST; } },
    SHOCK("Arc Protocol", "Shots briefly stun enemies", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.SHOCK; } },
    TESLA_ORB("Tesla Orb", "Unlock/upgrade autonomous chain lightning", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.TESLA_ORB); } },
    MISSILE_SWARM("Missile Swarm", "Unlock/upgrade homing missile volleys", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.MISSILE_SWARM); } },
    CRYO_NOVA("Cryo Nova", "Unlock/upgrade periodic freezing pulse", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.CRYO_NOVA); } },
    DRONE("Sentinel Drone", "Unlock/upgrade autonomous combat drone", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.DRONE); } },
    ORBITAL("Orbital Blade", "Unlock/upgrade rotating close-range blade", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.ORBITAL_BLADE); } },
    DASH_CORE("Phase Dash", "Dash cooldown -18%", UpgradeRarity.RARE) { public void apply(Player p) { p.dashCooldown = Math.max(1.25f, p.dashCooldown * .82f); } };

    public final String title, description;
    public final UpgradeRarity rarity;

    Upgrade(String title, String description, UpgradeRarity rarity) {
        this.title = title;
        this.description = description;
        this.rarity = rarity;
    }

    public abstract void apply(Player player);
}
