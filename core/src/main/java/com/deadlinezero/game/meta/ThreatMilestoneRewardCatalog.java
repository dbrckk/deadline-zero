package com.deadlinezero.game.meta;

/** Unique Mythic equipment earned only from Threat 5/10/15/20 clears. */
public final class ThreatMilestoneRewardCatalog {
    private ThreatMilestoneRewardCatalog() {}

    public static EquipmentItem forTier(int tier) {
        return switch (ThreatTierRules.sanitizeTier(tier)) {
            case 5 -> new EquipmentItem("threat_05_helmet", "Aegis Protocol Helm",
                PlayerProfile.EquipmentSlot.HELMET, EquipmentItem.Rarity.MYTHIC, 10, .12f);
            case 10 -> new EquipmentItem("threat_10_gloves", "Warden Breaker Gauntlets",
                PlayerProfile.EquipmentSlot.GLOVES, EquipmentItem.Rarity.MYTHIC, 15, .16f);
            case 15 -> new EquipmentItem("threat_15_armor", "Revenant Null Carapace",
                PlayerProfile.EquipmentSlot.ARMOR, EquipmentItem.Rarity.MYTHIC, 20, .21f);
            case 20 -> new EquipmentItem("threat_20_core", "Zero-Day Singularity Core",
                PlayerProfile.EquipmentSlot.CORE, EquipmentItem.Rarity.MYTHIC, 25, .28f);
            default -> null;
        };
    }

    public static boolean isExclusiveId(String id) {
        if (id == null) return false;
        return id.equals("threat_05_helmet") || id.equals("threat_10_gloves")
            || id.equals("threat_15_armor") || id.equals("threat_20_core");
    }
}
