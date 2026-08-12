package com.deadlinezero.game.meta;

import com.badlogic.gdx.utils.Array;

/** Persistent equipment inventory with stable IDs for serialization and UI selection. */
public final class Inventory {
    public static final int MAX_ITEMS = 120;
    private final Array<EquipmentItem> items = new Array<>(false, MAX_ITEMS);

    public Array<EquipmentItem> items() { return items; }
    public int size() { return items.size; }
    public boolean full() { return items.size >= MAX_ITEMS; }

    public boolean add(EquipmentItem item) {
        if (item == null || full() || find(item.id) != null) return false;
        items.add(item);
        return true;
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
