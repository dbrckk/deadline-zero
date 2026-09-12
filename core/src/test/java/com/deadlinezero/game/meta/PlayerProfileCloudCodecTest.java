package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.*;
import com.deadlinezero.game.services.CloudSaveSnapshot;
import org.junit.jupiter.api.Test;

final class PlayerProfileCloudCodecTest {
    @Test void roundTripCoreState() {
        PlayerProfile p = new PlayerProfile();
        p.accountLevel = 4;
        p.highestStage = 5;
        p.selectedStage = 3;
        p.totalKills = 345L;
        p.addCurrency(PlayerProfile.Currency.CREDITS, 1234L);
        p.recordDeliveredPurchaseReceipt("receipt-a");
        EquipmentItem item = new EquipmentItem("cloud-armor", "Cloud Armor",
            PlayerProfile.EquipmentSlot.ARMOR, EquipmentItem.Rarity.EPIC, 3, .12f);
        assertTrue(p.inventory.add(item));
        p.equip(item);

        CloudSaveSnapshot s = PlayerProfileCloudCodec.snapshot(p, 9L, 5000L, "device-a");
        PlayerProfile r = PlayerProfileCloudCodec.restore(s);
        assertEquals(4, r.accountLevel);
        assertEquals(5, r.highestStage);
        assertEquals(3, r.selectedStage);
        assertEquals(345L, r.totalKills);
        assertEquals(1234L, r.currency(PlayerProfile.Currency.CREDITS));
        assertTrue(r.hasDeliveredPurchaseReceipt("receipt-a"));
        assertEquals("cloud-armor", r.equipped(PlayerProfile.EquipmentSlot.ARMOR).id);
    }

    @Test void corruptSnapshotIsRejected() {
        CloudSaveSnapshot s = PlayerProfileCloudCodec.snapshot(new PlayerProfile(), 1L, 1000L, "device-a");
        CloudSaveSnapshot bad = CloudSaveSnapshot.restore(s.schemaVersion, 2L, s.updatedAtEpochMillis,
            s.deviceId, s.payload, s.sha256);
        assertThrows(IllegalArgumentException.class, () -> PlayerProfileCloudCodec.restore(bad));
    }
}
