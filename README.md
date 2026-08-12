# DEADLINE: ZERO

Android-first top-down survival roguelite built with libGDX 1.14.2.

## Current playable vertical slice
- 60 Hz fixed-step simulation
- touch virtual stick + WASD/arrow controls
- automatic target acquisition and shooting
- 4 enemy archetypes with difficulty ramp
- pooling for 900 projectiles and 256 impact FX
- XP/level-up loop with 7 upgrades and 3-choice draft
- crits, multishot, HP, damage, fire-rate and speed builds
- procedural neon battlefield rendering
- kill counter, timer, HP and XP HUD
- death/revive flow
- rewarded AdMob integration using Google's official test ad unit
- Google Play Billing 9.1.0 abstraction and Android implementation
- desktop launcher for rapid iteration

## Open in Android Studio
Open this repository root as a Gradle project. Android module package: `com.deadlinezero.game`.

## Important before release
1. Replace Google test AdMob app/ad-unit IDs.
2. Create Play Console in-app products matching IDs in `BillingService`.
3. Add server-side purchase verification before selling consumable/value-bearing items.
4. Replace procedural placeholder visuals with production art/animation/audio.
5. Add consent/privacy flow and final analytics/crash reporting.

## Architecture direction
Core gameplay is platform-independent. Android-specific ads/billing live only in `android/` through service interfaces, keeping gameplay testable and portable.
