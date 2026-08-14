package com.deadlinezero.game.services;

/** Small, deterministic policy that prevents consumable restore from being marked complete before Play Billing is ready. */
public final class BillingRestorePolicy {
    private BillingRestorePolicy() {}

    public static boolean shouldRequestConsumableRestore(boolean alreadyRequested, BillingService.State state) {
        return !alreadyRequested && state == BillingService.State.READY;
    }
}
