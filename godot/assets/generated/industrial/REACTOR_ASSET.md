# DZ Filtration Reactor — production geometry

`dz_filtration_reactor.gltf` is **actual project-owned glTF 2.0 triangle geometry**, not a concept image, screenshot, sprite, 2D billboard, or download from a proprietary game.

- **Design:** 2.55 m tall industrial air-filtration / contamination-control machine. Beveled octagonal steel casing, double air intake rings with machined rotor and bolts, bent multi-segment coolant pipes, side heat exchangers, ventilation slots, armored feet, segmented hazard bands, inset telemetry and twin emergency indicator lenses.
- **Geometry:** 5,712 triangles, 7 separate baked indexed-free triangle-soup draw surfaces (each surface collects many machined parts). Embedded binary buffer is self-contained in the glTF data URI; no runtime mesh construction.
- **Shading:** exact 7 finite PBR finishes `HullSteel`, `SlatExchanger`, `Pipe_Coolant`, `HazardBands`, `SignalStatus`, `LightBarLens`, `RecessInserts`. `DZAssetLibrary._grade_generated_industrial_kit` assigns the same globally shared authored Poly Haven CC0 1k color, normal and packed AO/roughness/metallic maps used by other industrial props. No unique texture atlas or shader variant.
- **Production placement:** 4 static instances around the Quarantine yard's perimeter, rotated toward the central combat zone; no collision meshes or extra OmniLights. Their mesh shadows are disabled to protect the single directional character-shadow budget.
- **Ownership:** geometry authored from original parametric mechanical modeling instructions for Deadline Zéro. No third-party mesh, reference model, trademark or restrictive external asset. The only external textures are the already-documented CC0 Poly Haven surfaces in `godot/assets/third_party/polyhaven/`.
- **QA:** `godot/tests/environment_identity_test.gd` inspects all four imported scenes, 28 actual render meshes, 7 shared material identities, 12 PBR textured surfaces, 4 emissive lenses, arena boundary and shadow budget.

Note: this is an environment hard-surface asset. It does **not** replace the lower-fidelity rigged zombie/survivor characters. The full scene still needs real phone Mobile/Vulkan screenshot and thermal sign-off.
