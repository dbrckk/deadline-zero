# Deadline: Zero — Accessibility release contract

Repository-side release contract for accessibility and comfort controls already present in the shipping UI.

## Implemented controls

The Settings screen exposes persistent controls for:

- screen shake on/off and shake strength;
- hit stop;
- damage flash;
- high-contrast telegraphs;
- reduced flashes;
- haptics;
- reduced-motion comfort preset;
- UI scale;
- master, SFX and music volume;
- graphics quality;
- frame-rate target.

## Release QA

Before production rollout, verify on representative physical Android hardware:

- [ ] Every setting is reachable with touch input.
- [ ] Every setting persists after app restart.
- [ ] Reduced motion materially reduces non-essential motion.
- [ ] Reduce flashes materially suppresses avoidable flashing effects.
- [ ] High-contrast telegraphs remain readable in dense combat.
- [ ] UI scale at minimum and maximum does not clip critical controls or text.
- [ ] Haptics can be disabled completely.
- [ ] Audio can be independently reduced or muted without blocking gameplay.
- [ ] Settings remain usable on the smallest supported representative display.
- [ ] Critical gameplay information is not communicated by color alone where practical.

## Change-control rule

A new visual effect, camera effect, vibration pattern, critical combat telegraph or persistent settings control must be reviewed against this contract before release. CI can verify the contract and automated settings behavior, but physical readability and comfort remain manual release gates.
