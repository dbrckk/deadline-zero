package com.deadlinezero.game.services;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Store-offer presentation boundary for optional remote configuration.
 *
 * Remote data may only control visibility and a featured known Play product. Product IDs, prices and
 * grant quantities remain owned by BillingService / Play Billing. Invalid or missing remote data
 * falls back to a complete safe catalog instead of silently hiding monetization.
 */
public interface OfferConfigService {
    record Snapshot(Set<String> enabledProducts, String featuredProductId) {
        public Snapshot {
            enabledProducts = Set.copyOf(enabledProducts == null ? Set.of() : enabledProducts);
            featuredProductId = featuredProductId == null ? "" : featuredProductId;
        }

        public boolean enabled(String productId) {
            return BillingService.isKnownProduct(productId) && enabledProducts.contains(productId);
        }

        public boolean featured(String productId) {
            return enabled(productId) && productId.equals(featuredProductId);
        }
    }

    Snapshot current();

    default void refresh() {}

    static Snapshot safeDefaults() {
        return new Snapshot(BillingService.PRODUCTS, BillingService.STARTER_PACK);
    }

    /**
     * Sanitizes an optional remote snapshot. Unknown product IDs are dropped. A missing/empty/fully
     * invalid remote catalog falls back to safe defaults. Featured products must also be enabled.
     */
    static Snapshot sanitize(Set<String> remoteEnabledProducts, String remoteFeaturedProductId) {
        if (remoteEnabledProducts == null || remoteEnabledProducts.isEmpty()) return safeDefaults();

        LinkedHashSet<String> known = new LinkedHashSet<>();
        for (String productId : remoteEnabledProducts) {
            if (BillingService.isKnownProduct(productId)) known.add(productId);
        }
        if (known.isEmpty()) return safeDefaults();

        String featured = remoteFeaturedProductId;
        if (!BillingService.isKnownProduct(featured) || !known.contains(featured)) featured = "";
        return new Snapshot(known, featured);
    }

    static OfferConfigService safeLocal() {
        Snapshot defaults = safeDefaults();
        return () -> defaults;
    }

    static OfferConfigService fixed(Snapshot snapshot) {
        Snapshot safe = snapshot == null ? safeDefaults() : sanitize(snapshot.enabledProducts(), snapshot.featuredProductId());
        return () -> safe;
    }
}
