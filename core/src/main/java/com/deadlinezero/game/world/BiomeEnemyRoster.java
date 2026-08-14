package com.deadlinezero.game.world;

import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

/**
 * Deterministic biome enemy identities layered on top of stable low-level enemy archetypes.
 * This keeps collision/AI contracts intact while giving each biome a distinct combat population.
 */
public final class BiomeEnemyRoster {
    public enum Identity {
        NONE("STANDARD HOSTILE", 1f, 1f, 1f, 1f, null, 1f),
        FORGE_HOUND("FORGE HOUND", 1.00f, .55f, .30f, .94f, DamageElement.FIRE, .62f),
        CINDER_GUNNER("CINDER GUNNER", 1.00f, .70f, .34f, 1.03f, DamageElement.FIRE, .72f),
        SLAG_GUARD("SLAG GUARD", .92f, .48f, .26f, 1.12f, DamageElement.FIRE, .55f),
        PHASE_STALKER("PHASE STALKER", .64f, .56f, 1.00f, .95f, DamageElement.SHOCK, .62f),
        STATIC_SEER("STATIC SEER", .38f, .88f, 1.00f, 1.02f, DamageElement.SHOCK, .58f),
        NULL_WARD("NULL WARD", .58f, .42f, .92f, 1.10f, DamageElement.FROST, .66f);

        public final String title;
        public final float tintR;
        public final float tintG;
        public final float tintB;
        public final float visualScale;
        public final DamageElement resistedElement;
        public final float resistanceMultiplier;

        Identity(String title, float tintR, float tintG, float tintB, float visualScale,
                 DamageElement resistedElement, float resistanceMultiplier) {
            this.title = title;
            this.tintR = tintR;
            this.tintG = tintG;
            this.tintB = tintB;
            this.visualScale = visualScale;
            this.resistedElement = resistedElement;
            this.resistanceMultiplier = resistanceMultiplier;
        }

        public boolean resists(DamageElement element) {
            return resistedElement != null && resistedElement == element;
        }
    }

    private BiomeEnemyRoster() { }

    public static Identity identityFor(int stage, Enemy.Type type) {
        if (type == null || type == Enemy.Type.BOSS) return Identity.NONE;
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(stage);
        if (biome == EnvironmentBiomeRules.Biome.CINDER_FOUNDRY) {
            return switch (type) {
                case RUNNER -> Identity.FORGE_HOUND;
                case RANGED -> Identity.CINDER_GUNNER;
                case SHIELDED -> Identity.SLAG_GUARD;
                default -> Identity.NONE;
            };
        }
        if (biome == EnvironmentBiomeRules.Biome.NULL_SECTOR) {
            return switch (type) {
                case PHANTOM -> Identity.PHASE_STALKER;
                case RANGED -> Identity.STATIC_SEER;
                case REGENERATOR -> Identity.NULL_WARD;
                default -> Identity.NONE;
            };
        }
        return Identity.NONE;
    }

    /**
     * Converts part of the generic population into biome-signature archetypes while preserving
     * boss spawns and already-specialized encounter choices.
     */
    public static Enemy.Type remap(int stage, float rawRoll, Enemy.Type fallback) {
        if (fallback == null || fallback == Enemy.Type.BOSS) return fallback;
        float r = MathUtils.clamp(rawRoll, 0f, 1f);
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(stage);
        if (biome == EnvironmentBiomeRules.Biome.CINDER_FOUNDRY) {
            if (r < .18f) return Enemy.Type.RUNNER;
            if (r < .34f) return Enemy.Type.RANGED;
            if (r < .47f) return Enemy.Type.SHIELDED;
        } else if (biome == EnvironmentBiomeRules.Biome.NULL_SECTOR) {
            if (r < .20f) return Enemy.Type.PHANTOM;
            if (r < .38f) return Enemy.Type.RANGED;
            if (r < .54f) return Enemy.Type.REGENERATOR;
        }
        return fallback;
    }

    public static float elementalDamageMultiplier(int stage, Enemy.Type type, DamageElement element) {
        Identity identity = identityFor(stage, type);
        return identity.resists(element) ? identity.resistanceMultiplier : 1f;
    }
}
