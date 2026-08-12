package com.deadlinezero.game.meta;

/** First chest economy. Purchases are explicit and always return an equipment item or null. */
public final class ChestService {
    public static final long CREDIT_CHEST_COST = 850L;
    public static final long GEM_CHEST_COST = 45L;

    private ChestService() {}

    public static EquipmentItem openCreditChest(PlayerProfile profile) {
        if (profile == null || profile.inventory.full()) return null;
        if (!profile.spend(PlayerProfile.Currency.CREDITS, CREDIT_CHEST_COST)) return null;
        EquipmentItem item = EquipmentDropTable.roll(Math.max(1, profile.highestStage), false);
        profile.inventory.add(item);
        return item;
    }

    public static EquipmentItem openGemChest(PlayerProfile profile) {
        if (profile == null || profile.inventory.full()) return null;
        if (!profile.spend(PlayerProfile.Currency.GEMS, GEM_CHEST_COST)) return null;
        EquipmentItem best = null;
        for (int i = 0; i < 3; i++) {
            EquipmentItem candidate = EquipmentDropTable.roll(Math.max(3, profile.highestStage + 2), true);
            if (best == null || candidate.rarity.ordinal() > best.rarity.ordinal() ||
                (candidate.rarity == best.rarity && candidate.powerBonus > best.powerBonus)) {
                best = candidate;
            }
        }
        profile.inventory.add(best);
        return best;
    }
}
