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
    DASH_CORE("Phase Dash", "Dash cooldown -18%", UpgradeRarity.RARE) { public void apply(Player p) { p.dashCooldown = Math.max(1.25f, p.dashCooldown * .82f); } },

    // Offensive specialization
    CRIT_DAMAGE("Execution Kernel", "Crit damage +18%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.critMultiplier = Math.min(4.0f, p.weapon.critMultiplier + .18f); } },
    PRECISION_BARREL("Precision Barrel", "Spread -14%, projectile speed +8%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.spreadDegrees *= .86f; p.weapon.projectileSpeed *= 1.08f; } },
    HEAVY_PAYLOAD("Heavy Payload", "Damage +18%, fire rate -7%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.damage *= 1.18f; p.weapon.fireInterval *= 1.07f; } },
    LIGHT_BOLT("Light Bolt", "Fire rate +14%, damage -5%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.fireInterval *= .86f; p.weapon.damage *= .95f; } },
    HYPER_VELOCITY("Hyper Velocity", "Projectile speed +30%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.projectileSpeed *= 1.30f; } },
    IMPACT_CORE("Impact Core", "Damage +10%, knockback +22%", UpgradeRarity.COMMON) { public void apply(Player p) { p.weapon.damage *= 1.10f; p.weapon.knockback *= 1.22f; } },
    SHARPENED_FEED("Sharpened Feed", "Crit chance +5%, fire rate +6%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .05f); p.weapon.fireInterval *= .94f; } },
    DEEP_MAGAZINE("Deep Magazine", "+1 penetration, projectile speed -5%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); p.weapon.projectileSpeed *= .95f; } },
    FOCUSED_BURST("Focused Burst", "Spread -22%, crit damage +10%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.spreadDegrees *= .78f; p.weapon.critMultiplier = Math.min(4.0f, p.weapon.critMultiplier + .10f); } },
    SHATTER_ROUNDS("Shatter Rounds", "Damage +12%, penetration +1", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.damage *= 1.12f; p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); } },
    SUPPRESSIVE_PATTERN("Suppressive Pattern", "+1 projectile, damage -8%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.projectileCount = Math.min(7, p.weapon.projectileCount + 1); p.weapon.damage *= .92f; } },
    DEADLY_CADENCE("Deadly Cadence", "Crit chance +6%, crit damage +12%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .06f); p.weapon.critMultiplier = Math.min(4.0f, p.weapon.critMultiplier + .12f); } },

    // Survivability and mobility
    REINFORCED_FRAME("Reinforced Frame", "+40 max HP", UpgradeRarity.COMMON) { public void apply(Player p) { p.maxHp += 40f; p.hp += 40f; } },
    FIELD_MEDKIT("Field Medkit", "Heal 35% max HP", UpgradeRarity.COMMON) { public void apply(Player p) { p.hp = Math.min(p.maxHp, p.hp + p.maxHp * .35f); } },
    COMBAT_STIMS("Combat Stims", "Move speed +9%, heal 12 HP", UpgradeRarity.COMMON) { public void apply(Player p) { p.moveSpeed *= 1.09f; p.hp = Math.min(p.maxHp, p.hp + 12f); } },
    LIGHTWEIGHT_PLATING("Lightweight Plating", "Move speed +10%, max HP +12", UpgradeRarity.COMMON) { public void apply(Player p) { p.moveSpeed *= 1.10f; p.maxHp += 12f; p.hp += 12f; } },
    DASH_CAPACITOR("Dash Capacitor", "Dash cooldown -12%, move speed +5%", UpgradeRarity.RARE) { public void apply(Player p) { p.dashCooldown = Math.max(1.25f, p.dashCooldown * .88f); p.moveSpeed *= 1.05f; } },
    EMERGENCY_PLATING("Emergency Plating", "+55 max HP, move speed -4%", UpgradeRarity.RARE) { public void apply(Player p) { p.maxHp += 55f; p.hp += 55f; p.moveSpeed *= .96f; } },
    ADAPTIVE_SERVOS("Adaptive Servos", "Move speed +16%", UpgradeRarity.RARE) { public void apply(Player p) { p.moveSpeed *= 1.16f; } },
    PHASE_COOLANT("Phase Coolant", "Dash cooldown -10%, projectile speed +10%", UpgradeRarity.RARE) { public void apply(Player p) { p.dashCooldown = Math.max(1.25f, p.dashCooldown * .90f); p.weapon.projectileSpeed *= 1.10f; } },

    // Elemental specialization
    THERMAL_FEED("Thermal Feed", "FIRE damage +16%, projectile speed +8%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.FIRE; p.weapon.damage *= 1.16f; p.weapon.projectileSpeed *= 1.08f; } },
    FROST_MATRIX("Frost Matrix", "FROST knockback +30%, damage +8%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.FROST; p.weapon.knockback *= 1.30f; p.weapon.damage *= 1.08f; } },
    VOLTAIC_FEED("Voltaic Feed", "SHOCK fire rate +14%, crit chance +4%", UpgradeRarity.RARE) { public void apply(Player p) { p.weapon.element = DamageElement.SHOCK; p.weapon.fireInterval *= .86f; p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .04f); } },
    THERMAL_LANCE("Thermal Lance", "FIRE penetration +1, damage +10%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.element = DamageElement.FIRE; p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); p.weapon.damage *= 1.10f; } },
    CRYO_SHARDS("Cryo Shards", "FROST +1 projectile, spread +8%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.element = DamageElement.FROST; p.weapon.projectileCount = Math.min(7, p.weapon.projectileCount + 1); p.weapon.spreadDegrees *= 1.08f; } },
    ARC_PIERCE("Arc Pierce", "SHOCK penetration +1, projectile speed +15%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.element = DamageElement.SHOCK; p.weapon.penetration = Math.min(8, p.weapon.penetration + 1); p.weapon.projectileSpeed *= 1.15f; } },

    // Advanced hybrid protocols
    GLASS_CANNON("Glass Cannon", "Damage +32%, max HP -15%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.damage *= 1.32f; p.maxHp = Math.max(40f, p.maxHp * .85f); p.hp = Math.min(p.hp, p.maxHp); } },
    BULLET_STORM("Bullet Storm", "Fire rate +20%, +1 projectile, damage -14%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.fireInterval *= .80f; p.weapon.projectileCount = Math.min(7, p.weapon.projectileCount + 1); p.weapon.damage *= .86f; } },
    SNIPER_LOGIC("Sniper Logic", "Crit +10%, crit damage +24%, fire rate -10%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.critChance = Math.min(.60f, p.weapon.critChance + .10f); p.weapon.critMultiplier = Math.min(4.0f, p.weapon.critMultiplier + .24f); p.weapon.fireInterval *= 1.10f; } },
    BREACH_PROTOCOL("Breach Protocol", "Penetration +2, knockback +20%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.penetration = Math.min(8, p.weapon.penetration + 2); p.weapon.knockback *= 1.20f; } },
    VELOCITY_OVERDRIVE("Velocity Overdrive", "Projectile speed +45%, spread -10%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.projectileSpeed *= 1.45f; p.weapon.spreadDegrees *= .90f; } },
    REDLINE_OS("Redline OS", "Damage +15%, fire rate +15%, move speed +8%", UpgradeRarity.EPIC) { public void apply(Player p) { p.weapon.damage *= 1.15f; p.weapon.fireInterval *= .85f; p.moveSpeed *= 1.08f; } };

    public final String title, description;
    public final UpgradeRarity rarity;

    Upgrade(String title, String description, UpgradeRarity rarity) {
        this.title = title;
        this.description = description;
        this.rarity = rarity;
    }

    public abstract void apply(Player player);
}
