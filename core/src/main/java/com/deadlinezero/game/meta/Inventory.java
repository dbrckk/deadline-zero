package com.deadlinezero.game.meta;

import com.badlogic.gdx.utils.Array;

/** Persistent-ready equipment inventory. Serialization will be added to ProfileStore after schema stabilization. */
public final class Inventory {
    public static final int MAX_ITEMS = 120;
    private final Array<EquipmentItem> items = new Array<>(false, MAX_ITEMS);

    public Array<EquipmentItem> items() { return items; }
    public int size() { return items.size; }
    public boolean full() { return items.size >= MAX_ITEMS; }

    public boolean add(EquipmentItem item) {
        if (item == null || full()) return false;
        items.add(item);
        return true;
    }

    public EquipmentItem find(String id) {
        if (id == null) return null;
        for (EquipmentItem item : items) if (id.equals(item.id)) return item;
        return null;
    }

    public boolean remove(String id) {
        for (int i = 0; i < items.size; i++) {
            if (items.get(i).id.equals(id)) {
                items.removeIndex(i);
                return true;
            }
        }
        return false;
    }
}
