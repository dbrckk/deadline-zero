package com.deadlinezero.game.visual;

import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.world.BiomeEnemyRoster;

/** Pure mapping from combat source identity to hostile projectile presentation. */
public final class HostileProjectilePresentation {
    private HostileProjectilePresentation() { }

    public static EnemyProjectile.Style styleFor(Enemy source) {
        if (source == null) return EnemyProjectile.Style.DEFAULT;
        if (source.type == Enemy.Type.BOSS) {
            BossIdentity identity = source.bossCombat == null ? BossIdentity.ALPHA : source.bossCombat.identity();
            return identity == BossIdentity.NULL_ARCHON ? EnemyProjectile.Style.NULL : EnemyProjectile.Style.DEFAULT;
        }
        return switch (source.biomeIdentity()) {
            case CINDER_GUNNER -> EnemyProjectile.Style.CINDER;
            case STATIC_SEER -> EnemyProjectile.Style.STATIC;
            case NULL_WARD, PHASE_STALKER -> EnemyProjectile.Style.NULL;
            default -> EnemyProjectile.Style.DEFAULT;
        };
    }

    public static float coreRadiusMultiplier(EnemyProjectile.Style style) {
        if (style == null) return 1f;
        return switch (style) {
            case CINDER -> 1.18f;
            case STATIC -> .86f;
            case NULL -> 1.06f;
            default -> 1f;
        };
    }
}
