package com.deadlinezero.game.progression;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Player;

/**
 * Run-local upgrade catalog.
 *
 * Keep effects inside explicit mobile-safe caps so long runs cannot create pathological projectile,
 * movement or fire-rate values. Legendary transformations remain separate in LegendaryChoice.
 */
public enum Upgrade {
    RAPID_FIRE("Overclock", "Fire rate +18%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .82f); }
    },
    DAMAGE("High Caliber", "Damage +25%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.damage = damage(p.weapon.damage * 1.25f); }
    },
    SPEED("Adrenaline", "Move speed +14%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.moveSpeed = moveSpeed(p.moveSpeed * 1.14f); }
    },
    VITALITY("Nano Repair", "+25 max HP and heal", UpgradeRarity.COMMON) {
        public void apply(Player p) { increaseMaxHp(p, 25f, 35f); }
    },
    MULTISHOT("Twin Protocol", "+1 projectile", UpgradeRarity.RARE) {
        public void apply(Player p) { p.weapon.projectileCount = Math.min(MAX_PROJECTILES, p.weapon.projectileCount + 1); }
    },
    CRIT("Hunter OS", "+8% crit chance", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.critChance = critChance(p.weapon.critChance + .08f); }
    },
    BALLISTICS("Rail Accelerator", "Projectile speed +22%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.22f); }
    },
    PENETRATION("Tungsten Core", "+1 penetration", UpgradeRarity.RARE) {
        public void apply(Player p) { p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1); }
    },
    KNOCKBACK("Kinetic Driver", "Knockback +35%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.knockback = knockback(p.weapon.knockback * 1.35f); }
    },
    INCENDIARY("Thermite Protocol", "FIRE rounds: +14% damage, -6% fire rate", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FIRE;
            p.weapon.damage = damage(p.weapon.damage * 1.14f);
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.06f);
        }
    },
    CRYO("Cryo Protocol", "FROST rounds: +18% knockback, +10% projectile speed", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FROST;
            p.weapon.knockback = knockback(p.weapon.knockback * 1.18f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.10f);
        }
    },
    SHOCK("Arc Protocol", "SHOCK rounds: +10% fire rate, -6% damage", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.SHOCK;
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .90f);
            p.weapon.damage = damage(p.weapon.damage * .94f);
        }
    },

    CRIT_POWER("Deadeye Kernel", "Crit damage +25%", UpgradeRarity.COMMON) {
        public void apply(Player p) { p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .25f); }
    },
    HEAVY_BARREL("Heavy Barrel", "Damage +18%, velocity +10%, fire rate -8%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.18f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.10f);
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.08f);
        }
    },
    LIGHTWEIGHT_BOLT("Lightweight Bolt", "Velocity +35%, damage -5%", UpgradeRarity.COMMON) {
        public void apply(Player p) {
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.35f);
            p.weapon.damage = damage(p.weapon.damage * .95f);
        }
    },
    SUPPRESSIVE_CYCLE("Suppressive Cycle", "Fire rate +12%, knockback +15%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .88f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.15f);
        }
    },
    FOCUSED_PAYLOAD("Focused Payload", "Damage +20%, spread -12%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.20f);
            p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .88f);
        }
    },
    GLASS_CANNON("Glass Cannon", "Damage +32%, max HP -12%", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.32f);
            reduceMaxHp(p, .88f);
        }
    },
    EXECUTIONER("Executioner OS", "Crit +5%, crit damage +30%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.critChance = critChance(p.weapon.critChance + .05f);
            p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .30f);
        }
    },
    SIEGE_ROUNDS("Siege Rounds", "+1 penetration, damage +12%, fire rate -10%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1);
            p.weapon.damage = damage(p.weapon.damage * 1.12f);
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.10f);
        }
    },
    CROSSFIRE("Crossfire Array", "+1 projectile, spread +2.5°", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.projectileCount = Math.min(MAX_PROJECTILES, p.weapon.projectileCount + 1);
            p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees + 2.5f);
        }
    },
    TIGHT_CHOKE("Tight Choke", "Spread -28%, velocity +8%", UpgradeRarity.COMMON) {
        public void apply(Player p) {
            p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .72f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.08f);
        }
    },
    HYPER_VELOCITY("Hyper Velocity", "Velocity +45%, knockback +10%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.45f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.10f);
        }
    },
    IMPACT_CORE("Impact Core", "Knockback +55%, damage +8%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.knockback = knockback(p.weapon.knockback * 1.55f);
            p.weapon.damage = damage(p.weapon.damage * 1.08f);
        }
    },
    ADAPTIVE_TRIGGER("Adaptive Trigger", "Fire rate +9%, crit +4%", UpgradeRarity.COMMON) {
        public void apply(Player p) {
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .91f);
            p.weapon.critChance = critChance(p.weapon.critChance + .04f);
        }
    },
    BERSERKER_CALIBER("Berserker Caliber", "Damage +24%, spread +10%, velocity -6%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.24f);
            p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * 1.10f + .4f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * .94f);
        }
    },

    SCOUT_FRAME("Scout Frame", "Move +10%, dash cooldown -8%", UpgradeRarity.COMMON) {
        public void apply(Player p) {
            p.moveSpeed = moveSpeed(p.moveSpeed * 1.10f);
            p.dashCooldown = dashCooldown(p.dashCooldown * .92f);
        }
    },
    BULWARK_FRAME("Bulwark Frame", "+35 max HP, move -4%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            increaseMaxHp(p, 35f, 20f);
            p.moveSpeed = moveSpeed(p.moveSpeed * .96f);
        }
    },
    COMBAT_STIMS("Combat Stims", "+18 max HP, move +7%", UpgradeRarity.COMMON) {
        public void apply(Player p) {
            increaseMaxHp(p, 18f, 18f);
            p.moveSpeed = moveSpeed(p.moveSpeed * 1.07f);
        }
    },
    PHASE_CAPACITOR("Phase Capacitor", "Dash cooldown -14%, move +4%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.dashCooldown = dashCooldown(p.dashCooldown * .86f);
            p.moveSpeed = moveSpeed(p.moveSpeed * 1.04f);
        }
    },
    AFTERBURNER("Afterburner", "Move +16%, max HP -6%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.moveSpeed = moveSpeed(p.moveSpeed * 1.16f);
            reduceMaxHp(p, .94f);
        }
    },
    REACTIVE_PLATING("Reactive Plating", "+30 max HP, knockback +12%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            increaseMaxHp(p, 30f, 12f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.12f);
        }
    },
    LAST_STAND("Last Stand", "Damage +18%, crit +6%, max HP -8%", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.18f);
            p.weapon.critChance = critChance(p.weapon.critChance + .06f);
            reduceMaxHp(p, .92f);
        }
    },
    FIELD_REPAIR("Field Repair", "+20 max HP and restore 45 HP", UpgradeRarity.COMMON) {
        public void apply(Player p) { increaseMaxHp(p, 20f, 45f); }
    },

    FIRE_CONTROL("Fire Control", "FIRE: damage +12%, crit +3%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FIRE;
            p.weapon.damage = damage(p.weapon.damage * 1.12f);
            p.weapon.critChance = critChance(p.weapon.critChance + .03f);
        }
    },
    FROST_CONTROL("Frost Control", "FROST: knockback +22%, fire rate +5%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FROST;
            p.weapon.knockback = knockback(p.weapon.knockback * 1.22f);
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .95f);
        }
    },
    SHOCK_CONTROL("Shock Control", "SHOCK: fire rate +8%, penetration +1", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.SHOCK;
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .92f);
            p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1);
        }
    },
    THERMAL_LANCE("Thermal Lance", "FIRE: velocity +20%, penetration +1", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FIRE;
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.20f);
            p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1);
        }
    },
    CRYO_HAMMER("Cryo Hammer", "FROST: damage +14%, knockback +30%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.FROST;
            p.weapon.damage = damage(p.weapon.damage * 1.14f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.30f);
        }
    },
    ARC_LANCER("Arc Lancer", "SHOCK: velocity +18%, crit +4%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.element = DamageElement.SHOCK;
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.18f);
            p.weapon.critChance = critChance(p.weapon.critChance + .04f);
        }
    },
    ELEMENTAL_HARMONIZER("Elemental Harmonizer", "Damage +10%, velocity +10%, knockback +10%", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.10f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.10f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.10f);
        }
    },

    PRECISION_MATRIX("Precision Matrix", "Crit +6%, crit damage +20%, spread -15%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.weapon.critChance = critChance(p.weapon.critChance + .06f);
            p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .20f);
            p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .85f);
        }
    },
    BARRAGE_MATRIX("Barrage Matrix", "+1 projectile, fire rate +6%, damage -8%", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.projectileCount = Math.min(MAX_PROJECTILES, p.weapon.projectileCount + 1);
            p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .94f);
            p.weapon.damage = damage(p.weapon.damage * .92f);
        }
    },
    BREACH_MATRIX("Breach Matrix", "Penetration +1, damage +16%, knockback +16%", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1);
            p.weapon.damage = damage(p.weapon.damage * 1.16f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.16f);
        }
    },
    MOMENTUM_CORE("Momentum Core", "Move +9%, knockback +20%, velocity +8%", UpgradeRarity.RARE) {
        public void apply(Player p) {
            p.moveSpeed = moveSpeed(p.moveSpeed * 1.09f);
            p.weapon.knockback = knockback(p.weapon.knockback * 1.20f);
            p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.08f);
        }
    },
    VETERAN_CORE("Veteran Core", "Damage +10%, crit +3%, +12 max HP", UpgradeRarity.EPIC) {
        public void apply(Player p) {
            p.weapon.damage = damage(p.weapon.damage * 1.10f);
            p.weapon.critChance = critChance(p.weapon.critChance + .03f);
            increaseMaxHp(p, 12f, 12f);
        }
    },

    TESLA_ORB("Tesla Orb", "Chain lightning • Tier II at Lv3 • evolves at Lv5 • synergizes with Cryo/Drone", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.abilities.upgrade(AbilityType.TESLA_ORB); }
    },
    MISSILE_SWARM("Missile Swarm", "Homing volleys • Tier II at Lv3 • evolved warheads at Lv5 • synergizes with Cryo/Drone", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.abilities.upgrade(AbilityType.MISSILE_SWARM); }
    },
    CRYO_NOVA("Cryo Nova", "Freeze pulse • larger Tier II nova at Lv3 • evolved damage at Lv5 • enables frost synergies", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.abilities.upgrade(AbilityType.CRYO_NOVA); }
    },
    DRONE("Sentinel Drone", "Autonomous fire • improved range at Lv3 • evolved damage at Lv5 • network synergies", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.abilities.upgrade(AbilityType.DRONE); }
    },
    ORBITAL("Orbital Blade", "Close-range blade • larger Tier II hitbox at Lv3 • evolves at Lv5 • Frost/Storm forms", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.abilities.upgrade(AbilityType.ORBITAL_BLADE); }
    },
    RHYTHM_DRIVER("Rhythm Driver", "Every 6th volley is empowered and guaranteed critical", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.protocols.enableRhythm(); }
    },
    KILLCHAIN_CAPACITOR("Killchain Capacitor", "Every 8 kills empowers the next volley with damage and penetration", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.protocols.enableKillchain(); }
    },
    REACTION_CORE("Reaction Core", "Elemental reactions deal +35% bonus trigger damage", UpgradeRarity.EPIC) {
        public void apply(Player p) { p.protocols.enableReactionCore(); }
    },
    DASH_CORE("Phase Dash", "Dash cooldown -18%", UpgradeRarity.RARE) {
        public void apply(Player p) { p.dashCooldown = dashCooldown(p.dashCooldown * .82f); }
    };

    public static final float MIN_FIRE_INTERVAL = .05f;
    public static final float MAX_DAMAGE = 500f;
    public static final float MAX_MOVE_SPEED = 15f;
    public static final float MAX_HP = 500f;
    public static final float MIN_HP = 45f;
    public static final float MAX_PROJECTILE_SPEED = 60f;
    public static final int MAX_PROJECTILES = 7;
    public static final float MAX_SPREAD_DEGREES = 24f;
    public static final float MAX_CRIT_CHANCE = .75f;
    public static final float MAX_CRIT_MULTIPLIER = 4f;
    public static final int MAX_PENETRATION = 8;
    public static final float MAX_KNOCKBACK = 12f;
    public static final float MIN_DASH_COOLDOWN = 1.25f;

    public final String title;
    public final String description;
    public final UpgradeRarity rarity;

    Upgrade(String title, String description, UpgradeRarity rarity) {
        this.title = title;
        this.description = description;
        this.rarity = rarity;
    }

    public String titleKey() { return "upgrade." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
    public String descriptionKey() { return "upgrade." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }

    public abstract void apply(Player player);

    static float damage(float value) { return Math.max(.1f, Math.min(MAX_DAMAGE, value)); }
    static float fireInterval(float value) { return Math.max(MIN_FIRE_INTERVAL, value); }
    static float moveSpeed(float value) { return Math.max(1f, Math.min(MAX_MOVE_SPEED, value)); }
    static float projectileSpeed(float value) { return Math.max(1f, Math.min(MAX_PROJECTILE_SPEED, value)); }
    static float spread(float value) { return Math.max(0f, Math.min(MAX_SPREAD_DEGREES, value)); }
    static float critChance(float value) { return Math.max(0f, Math.min(MAX_CRIT_CHANCE, value)); }
    static float critMultiplier(float value) { return Math.max(1f, Math.min(MAX_CRIT_MULTIPLIER, value)); }
    static float knockback(float value) { return Math.max(0f, Math.min(MAX_KNOCKBACK, value)); }
    static float dashCooldown(float value) { return Math.max(MIN_DASH_COOLDOWN, value); }

    static void increaseMaxHp(Player p, float amount, float heal) {
        p.maxHp = Math.min(MAX_HP, Math.max(MIN_HP, p.maxHp + Math.max(0f, amount)));
        p.hp = Math.min(p.maxHp, p.hp + Math.max(0f, heal));
    }

    static void reduceMaxHp(Player p, float multiplier) {
        p.maxHp = Math.max(MIN_HP, Math.min(MAX_HP, p.maxHp * multiplier));
        p.hp = Math.min(p.hp, p.maxHp);
    }
}
