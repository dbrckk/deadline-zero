package com.deadlinezero.game.meta;

/** Pure rules for the four-piece Ascension Mythic equipment set. */
public final class ThreatSetBonusRules {
    private ThreatSetBonusRules() {}

    public static int equippedPieces(PlayerProfile profile) {
        if (profile == null) return 0;
        int count = 0;
        for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
            EquipmentItem item = profile.equipped(slot);
            if (item != null && ThreatMilestoneRewardCatalog.isExclusiveId(item.id)) count++;
        }
        return Math.min(4, count);
    }

    public static float weaponMultiplier(int pieces) { return pieces >= 2 ? 1.08f : 1f; }
    public static float abilityMultiplier(int pieces) { return pieces >= 2 ? 1.08f : 1f; }
    public static float hpMultiplier(int pieces) { return pieces >= 3 ? 1.05f : 1f; }
    public static float moveSpeedMultiplier(int pieces) { return pieces >= 3 ? 1.06f : 1f; }
    public static float damageTakenMultiplier(int pieces) { return pieces >= 4 ? .90f : 1f; }
    public static float dashInvulnerabilityBonus(int pieces) { return pieces >= 4 ? .06f : 0f; }

    public static String summary(int pieces) {
        int safe = Math.max(0, Math.min(4, pieces));
        if (safe >= 4) return "ASCENSION 4/4 • +8% DAMAGE/ABILITY • +5% HP • +6% SPEED • -10% DAMAGE TAKEN";
        if (safe == 3) return "ASCENSION 3/4 • +8% DAMAGE/ABILITY • +5% HP • +6% SPEED";
        if (safe == 2) return "ASCENSION 2/4 • +8% DAMAGE/ABILITY";
        return "ASCENSION " + safe + "/4 • NEXT BONUS AT 2 PIECES";
    }
}
