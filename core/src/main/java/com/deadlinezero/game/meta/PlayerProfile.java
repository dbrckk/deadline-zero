package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Set;

/** Long-term account progression. Storage is intentionally separate from gameplay runtime. */
public final class PlayerProfile {
    private static final int MAX_PURCHASE_RECEIPTS = 128;

    public enum Currency { CREDITS, GEMS }
    public enum EquipmentSlot { WEAPON, ARMOR, HELMET, GLOVES, BOOTS, CORE }

    private final EnumMap<Currency, Long> currencies = new EnumMap<>(Currency.class);
    private final EnumMap<EquipmentSlot, EquipmentItem> equipped = new EnumMap<>(EquipmentSlot.class);
    private final LinkedHashSet<String> deliveredPurchaseReceipts = new LinkedHashSet<>();
    public final Inventory inventory = new Inventory();
    public final DailyProgress daily = new DailyProgress();
    public final SurvivorProgression survivors = new SurvivorProgression();
    public int accountLevel = 1;
    public long accountXp;
    public int highestStage = 1;
    public int selectedStage = 1;
    public int totalRuns;
    public long totalKills;
    public boolean removeAdsPurchased;
    public boolean starterPackGranted;
    public SurvivorCatalog.Survivor selectedSurvivor = SurvivorCatalog.Survivor.REX;
    public String selectedWeaponId = WeaponCatalog.AR9.id;

    public PlayerProfile() { for (Currency currency : Currency.values()) currencies.put(currency, 0L); }
    public long currency(Currency currency) { return Math.max(0L, currencies.getOrDefault(currency, 0L)); }
    public void addCurrency(Currency currency, long amount) {
        if (currency == null || amount <= 0) return;
        currencies.put(currency, ProfileCounterMath.addNonNegative(currency(currency), amount));
    }
    public boolean spend(Currency currency, long amount) {
        if (currency == null || amount <= 0 || currency(currency) < amount) return false;
        currencies.put(currency, currency(currency) - amount);
        return true;
    }
    public long xpForNextLevel() { return 250L + (long)(Math.max(1, accountLevel) - 1) * 110L; }
    public void addAccountXp(long amount) {
        if (amount <= 0) return;
        accountXp = ProfileCounterMath.addNonNegative(accountXp, amount);
        while (accountLevel < Integer.MAX_VALUE && accountXp >= xpForNextLevel()) {
            accountXp -= xpForNextLevel();
            accountLevel = ProfileCounterMath.incrementNonNegative(accountLevel);
        }
        if (accountLevel == Integer.MAX_VALUE) accountXp = Math.min(accountXp, xpForNextLevel() - 1L);
        survivors.refreshUnlocks(this);
        validateSelectedWeapon();
    }
    public void recordRun(int kills, int stage) {
        totalRuns = ProfileCounterMath.incrementNonNegative(totalRuns);
        totalKills = ProfileCounterMath.addKills(totalKills, kills);
        highestStage = Math.max(Math.max(1, highestStage), Math.max(1, stage));
        selectedStage = Math.min(Math.max(1, selectedStage), highestStage);
        survivors.refreshUnlocks(this);
        validateSelectedWeapon();
    }
    public EquipmentItem equipped(EquipmentSlot slot) { return equipped.get(slot); }
    public void equip(EquipmentItem item) { if (item != null) { equipped.put(item.slot, item); if (inventory.find(item.id) == null) inventory.add(item); } }
    public void unequip(EquipmentSlot slot) { if (slot != null) equipped.remove(slot); }
    public boolean selectStage(int stage) { if (stage < 1 || stage > highestStage) return false; selectedStage = stage; return true; }
    public boolean selectSurvivor(SurvivorCatalog.Survivor survivor) { if (survivor == null || !survivors.unlocked(survivor)) return false; selectedSurvivor = survivor; return true; }
    public WeaponDefinition selectedWeapon() { return WeaponCatalog.byId(selectedWeaponId); }
    public boolean selectWeapon(WeaponDefinition weapon) {
        if (weapon == null || !WeaponProgression.unlocked(this, weapon)) return false;
        selectedWeaponId = weapon.id;
        return true;
    }
    public void validateSelectedWeapon() {
        WeaponDefinition selected = WeaponCatalog.byId(selectedWeaponId);
        if (!WeaponProgression.unlocked(this, selected)) selectedWeaponId = WeaponCatalog.AR9.id;
    }
    public float aggregatePowerMultiplier() { float bonus = 0f; for (EquipmentItem item : equipped.values()) if (item != null) bonus += item.powerBonus; return 1f + bonus; }

    public boolean hasDeliveredPurchaseReceipt(String receiptId) {
        return receiptId != null && !receiptId.isBlank() && deliveredPurchaseReceipts.contains(receiptId);
    }

    /** Returns true only for the first delivery of this Play receipt. */
    public boolean recordDeliveredPurchaseReceipt(String receiptId) {
        if (receiptId == null || receiptId.isBlank() || deliveredPurchaseReceipts.contains(receiptId)) return false;
        deliveredPurchaseReceipts.add(receiptId);
        while (deliveredPurchaseReceipts.size() > MAX_PURCHASE_RECEIPTS) {
            String oldest = deliveredPurchaseReceipts.iterator().next();
            deliveredPurchaseReceipts.remove(oldest);
        }
        return true;
    }

    public Set<String> deliveredPurchaseReceipts() { return Set.copyOf(deliveredPurchaseReceipts); }
}
