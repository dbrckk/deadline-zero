package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import java.util.EnumMap;

/** Long-term account progression. Storage is intentionally separate from gameplay runtime. */
public final class PlayerProfile {
    public enum Currency { CREDITS, GEMS }
    public enum EquipmentSlot { WEAPON, ARMOR, HELMET, GLOVES, BOOTS, CORE }

    private final EnumMap<Currency, Long> currencies = new EnumMap<>(Currency.class);
    private final EnumMap<EquipmentSlot, EquipmentItem> equipped = new EnumMap<>(EquipmentSlot.class);
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
    public boolean onboardingCompleted;
    public SurvivorCatalog.Survivor selectedSurvivor = SurvivorCatalog.Survivor.REX;
    public String selectedWeaponId = WeaponCatalog.AR9.id;

    public PlayerProfile() { for (Currency currency : Currency.values()) currencies.put(currency, 0L); }
    public long currency(Currency currency) { return currencies.getOrDefault(currency, 0L); }
    public void addCurrency(Currency currency, long amount) { if (amount > 0) currencies.put(currency, currency(currency) + amount); }
    public boolean spend(Currency currency, long amount) { if (amount <= 0 || currency(currency) < amount) return false; currencies.put(currency, currency(currency) - amount); return true; }
    public long xpForNextLevel() { return 250L + (long)(accountLevel - 1) * 110L; }
    public void addAccountXp(long amount) { if (amount <= 0) return; accountXp += amount; while (accountXp >= xpForNextLevel()) { accountXp -= xpForNextLevel(); accountLevel++; } survivors.refreshUnlocks(this); validateSelectedWeapon(); }
    public void recordRun(int kills, int stage) { totalRuns++; totalKills += Math.max(0, kills); highestStage = Math.max(highestStage, Math.max(1, stage)); selectedStage = Math.min(Math.max(1, selectedStage), highestStage); survivors.refreshUnlocks(this); validateSelectedWeapon(); }
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
}
