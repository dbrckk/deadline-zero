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
    INCENDIARY("Thermite Protocol", "FIRE rounds: +14% damage, -6% fire rate", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FIRE;
            p.weapon.damage *= 1.14f;
            p.weapon.fireInterval *= 1.06f;
        }
    },
    CRYO("Cryo Protocol", "FROST rounds: +18% knockback, +10% projectile speed", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FROST;
            p.weapon.knockback *= 1.18f;
            p.weapon.projectileSpeed *= 1.10f;
        }
    },
    SHOCK("Arc Protocol", "SHOCK rounds: +10% fire rate, -6% damage", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.SHOCK;
            p.weapon.fireInterval *= .90f;
            p.weapon.damage *= .94f;
        }
    },
    TESLA_ORB("Tesla Orb", "Chain lightning • Tier II at Lv3 • evolves at Lv5 • synergizes with Cryo/Drone", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.TESLA_ORB); } },
    MISSILE_SWARM("Missile Swarm", "Homing volleys • Tier II at Lv3 • evolved warheads at Lv5 • synergizes with Cryo/Drone", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.MISSILE_SWARM); } },
    CRYO_NOVA("Cryo Nova", "Freeze pulse • larger Tier II nova at Lv3 • evolved damage at Lv5 • enables frost synergies", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.CRYO_NOVA); } },
    DRONE("Sentinel Drone", "Autonomous fire • improved range at Lv3 • evolved damage at Lv5 • network synergies", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.DRONE); } },
    ORBITAL("Orbital Blade", "Close-range blade • larger Tier II hitbox at Lv3 • evolves at Lv5 • Frost/Storm forms", UpgradeRarity.EPIC) { public void apply(Player p) { p.abilities.upgrade(AbilityType.ORBITAL_BLADE); } },
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
