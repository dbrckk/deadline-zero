# DEADLINE: ZERO

Production-oriented Android action-survival roguelite built with libGDX.

## Current milestone

v0.2 combat-engine foundation is underway: data-driven weapons, spatial hashing, modular projectile effects, Android monetization services, and a playable vertical slice.

## Modules

- `core` — gameplay, rendering, progression, combat simulation
- `android` — Android launcher, rewarded ads, Google Play Billing
- `desktop` — fast desktop iteration launcher

## Build targets

Android Studio / Gradle. Desktop launcher is included for rapid gameplay iteration.

## Development principles

- fixed-step gameplay simulation
- allocation-conscious hot paths
- pooled projectiles and transient FX
- data-driven weapon definitions
- scalable collision broad phase
- rewarded-first monetization
- platform services isolated from gameplay

See `docs/ROADMAP.md` for the production roadmap.
