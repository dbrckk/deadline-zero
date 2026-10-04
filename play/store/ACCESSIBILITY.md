# Deadline: Zero — Accessibility release contract

Repository-side release contract for accessibility and comfort controls in the current Godot shipping runtime.

## Implemented controls

The in-game pause/settings panel currently exposes persistent controls for:

- master volume;
- SFX volume;
- haptics on/off;
- reduced flashes on/off;
- camera shake on/off;
- hit stop on/off.

Reduced flashes currently lowers the intensity/duration of screen-space impact flashes and the player damage vignette. Haptics can be disabled completely for combat impact events.

The runtime also provides non-configurable readability aids including boss telegraphs, a close-pressure player locator, low-health warning, off-screen elite/boss direction guidance, and distinct enemy silhouettes.

## Planned / not yet implemented in the Godot candidate

The following comfort controls existed in historical release planning but are **not yet user-configurable in the current Godot runtime**:

- camera shake strength slider;
- high-contrast telegraphs toggle;
- reduced-motion comfort preset;
- UI scale;
- music volume;
- graphics quality;
- frame-rate target.

These must not be advertised as user settings until implemented and covered by runtime tests.

## Release QA

Before production rollout, verify on representative physical Android hardware:

- [ ] Every implemented setting is reachable with touch input.
- [ ] Every implemented setting persists after app restart.
- [ ] Reduced flashes materially suppresses avoidable full-screen flashing effects.
- [ ] Haptics can be disabled completely.
- [ ] Camera shake can be disabled completely and clears any active kick.
- [ ] Hit stop can be disabled completely and clears any active freeze.
- [ ] Master and SFX audio can be reduced or muted without blocking gameplay.
- [ ] Boss/attack telegraphs remain readable in dense combat.
- [ ] Close-pressure and off-screen threat indicators remain readable without relying only on color.
- [ ] Settings remain usable on the smallest supported representative display.
- [ ] Any newly added screen shake, high-contrast telegraphs, reduced-motion, UI scale, graphics, or frame-rate controls are validated before being advertised.

## Change-control rule

A new visual effect, camera effect, vibration pattern, critical combat telegraph, or persistent settings control must be reviewed against this contract before release. CI can verify automated settings behavior, but physical readability and comfort remain manual release gates.

## Legacy libGDX contract compatibility

The legacy libGDX runtime still has an automated accessibility contract. These exact statements are retained for its historical CI while the shipping Godot surface is documented separately above:

- Every setting persists after app restart.
- Reduced motion materially reduces non-essential motion.
- High-contrast telegraphs remain readable in dense combat.
- Critical gameplay information is not communicated by color alone.

These statements describe the legacy runtime's tested contract; they do not advertise unavailable Godot settings.
