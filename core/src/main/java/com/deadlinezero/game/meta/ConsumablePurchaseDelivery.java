package com.deadlinezero.game.meta;

import com.deadlinezero.game.services.BillingService;
import java.util.function.Consumer;

/** Crash-safe consumable delivery: grant and persist before asking the platform store to consume. */
public final class ConsumablePurchaseDelivery {
    private ConsumablePurchaseDelivery() {}

    public interface Persistence {
        void save();
    }

    public static boolean deliver(PlayerProfile profile, BillingService billing, BillingService.PurchaseReceipt receipt,
                                  Persistence persistence, Consumer<Boolean> onFinalized, Runnable onFinalizeFailure) {
        if (profile == null || billing == null || receipt == null || persistence == null) return false;
        if (!BillingService.isConsumable(receipt.productId())) return false;
        if (receipt.receiptId() == null || receipt.receiptId().isBlank()) return false;

        boolean granted = PurchaseGrantService.grant(profile, receipt.productId(), receipt.receiptId());
        persistence.save();
        billing.finishConsumable(receipt.receiptId(),
            () -> { if (onFinalized != null) onFinalized.accept(granted); },
            onFinalizeFailure == null ? () -> { } : onFinalizeFailure);
        return granted;
    }
}
