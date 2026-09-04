package com.deadlinezero.game.meta;

import com.badlogic.gdx.utils.Array;

/** Persistent equipment inventory with stable IDs for serialization and UI selection. */
public final class Inventory {
    public static final int NORMAL_CAPACITY = 120;
    public static final int EXCLUSIVE_RESERVE = 4;
    public static final int MAX_ITEMS = NORMAL_CAPACITY + EXCLUSIVE_RESERVE;
    private final Array<EquipmentItem> items = new Array<>(false, MAX_ITEMS);

    public Array<EquipmentItem> items() { return items; }
    public int size() { return items.size; }
    /** Normal drops stop at 120 so four milestone rewards can never be capacity-blocked. */
    public boolean full() { return normalItemCount() >= NORMAL_CAPACITY; }

    public boolean add(EquipmentItem item) {
        if (item == null || full() || find(item.id) != null) return false;
        items.add(item);
        return true;
    }

    /** Adds only catalogued Threat milestone gear into the reserved capacity. */
    public boolean addExclusive(EquipmentItem item) {
        if (item == null || !ThreatMilestoneRewardCatalog.isExclusiveId(item.id)
            || items.size >= MAX_ITEMS || find(item.id) != null) return false;
        items.add(item);
        return true;
    }

    /** Restores persisted items while preserving the reserved capacity contract for exclusive gear. */
    boolean restore(EquipmentItem item) {
        if (item == null) return false;
        return ThreatMilestoneRewardCatalog.isExclusiveId(item.id) ? addExclusive(item) : add(item);
    }

    private int normalItemCount() {
        int count = 0;
        for (EquipmentItem item : items) {
            if (!ThreatMilestoneRewardCatalog.isExclusiveId(item.id)) count++;
        }
        return count;
    }

    public EquipmentItem find(String id) {
        if (id == null) return null;
        for (EquipmentItem item : items) if (id.equals(item.id)) return item;
        return null;
    }

    public boolean replace(EquipmentItem replacement) {
        if (replacement == null) return false;
        for (int i = 0; i < items.size; i++) {
            if (items.get(i).id.equals(replacement.id)) {
                items.set(i, replacement);
                return true;
            }
        }
        return false;
    }

    public boolean remove(String id) {
        if (id == null) return false;
        for (int i = 0; i < items.size; i++) {
            if (items.get(i).id.equals(id)) {
                items.removeIndex(i);
                return true;
            }
        }
        return false;
    }
}
