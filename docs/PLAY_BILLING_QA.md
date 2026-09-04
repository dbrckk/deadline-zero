# Google Play Billing QA

This checklist proves the platform behavior that core unit tests cannot simulate. Run it with a Play Console license-test account against the same product IDs configured for release.

## Automated invariants

Core CI must prove:

- the same consumable receipt grants currency at most once;
- distinct purchase receipts grant independently;
- the starter pack is idempotent across restore/restart;
- cached Remove Ads is retained while the Play entitlement snapshot is non-authoritative;
- an authoritative Play snapshot can revoke stale Remove Ads state;
- consumable delivery always executes `grant -> persist profile -> consume Play purchase`;
- replaying an already-delivered consumable persists the receipt state and finalizes Play without adding currency again;
- malformed/blank receipt IDs are rejected without persistence or consumption.

## Physical-device / Play license-test matrix

Record device, Android version, build versionCode/versionName, tester account and result for each row.

### Remove Ads

1. Start without entitlement; confirm ads remain eligible.
2. Purchase `remove_ads_lifetime`.
3. Confirm entitlement is granted only after Play reports a purchased/acknowledged durable purchase.
4. Force-stop and relaunch; confirm Remove Ads remains active.
5. Disable network and relaunch; cached entitlement may remain active while the store snapshot is non-authoritative.
6. Restore network and trigger restore; confirm authoritative ownership remains active.
7. Test a refunded/revoked license state where available; once Play returns an authoritative non-owned snapshot, confirm local Remove Ads is cleared.

### Starter Pack

1. Purchase `starter_pack_01` from an account that has never owned it.
2. Record credits, gems and inventory before/after; expected grant is exactly 5,000 credits + 250 gems + up to two gear drops.
3. Force-stop immediately after delivery and relaunch.
4. Trigger restore repeatedly.
5. Confirm no additional credits, gems or equipment are added.

### Gem consumables

For both `gems_250` and `gems_1200`:

1. Purchase once and record the resulting balance.
2. Confirm the receipt token is recorded before the Play purchase is consumed.
3. Force-stop at the earliest practical moment after grant; relaunch and open the shop so `restoreConsumables` runs.
4. Confirm the same token cannot add currency twice.
5. If finalization failed before process death, confirm the recovered purchase is consumed after the stored receipt is detected.
6. Purchase the same SKU again; its new token must grant normally.

### Pending purchase

1. Use a Play test payment method that enters PENDING when available.
2. Confirm UI enters `PURCHASE_PENDING` and blocks another purchase flight.
3. Force-stop/relaunch while pending.
4. Confirm the pending product is rediscovered without granting anything.
5. Approve the payment; confirm exactly one delivery.

### Connectivity / reconnection

1. Disconnect network before opening the shop; confirm no local entitlement is revoked from a non-authoritative snapshot.
2. Reconnect and wait for BillingClient reconnection/restore.
3. Confirm the purchase gate returns to a usable state.
4. Repeat a purchase after reconnection and confirm only one callback/delivery occurs.

### Unknown product safety

No unknown Play product ID may mutate profile currency, inventory, starter-pack state or Remove Ads state.

## Exit evidence

Issue #17 may be closed when automated CI is green and every physical-device row above has a recorded PASS, or a linked defect exists for any failure. Keep screenshots/logcat excerpts for Play failures when useful.