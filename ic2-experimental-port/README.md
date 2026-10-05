# IC2 Experimental 1.19.2 fidelity port

This workspace ports the installed IC2 Experimental `2.8.222-ex112` setup and
its IC2-family addons to Forge 1.19.2. The active implementation is based on the
last numbered official Experimental build,
`industrialcraft-2-2.9.162+ex119-1.19.2-forge.jar`, plus a reproducible fidelity
patch and companion mod.

The checksum-verified upstream jar remains unchanged under `upstream`. Its
SHA-256 is
`3246b9bc6f6b9063f666221b4bf802be7a94b58884c7ca6dd73399088d5a2519`.
The active files are:

- `mods/industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar`;
- `mods/IC2-Experimental-Fidelity-0.1.0.jar`.

Russian localization covers all English keys in IC2, GraviSuite, Advanced
Solar Panels and C.U.U. Matter, including restored screens, tooltips, crop
traits and drink names. Local language files are applied after addon asset
extraction and merged into the patched IC2 base so resource load order cannot
discard the translations. `validate-translations.py` checks coverage, format
arguments and the translations actually packaged in both built jars; use
`--jar-dir` to check another installation.

The build uses PowerShell 7. When building from a standalone checkout, pass
`-DependencyRoot <Minecraft directory> -SkipInstall` to `compat/build.ps1` to
read Java and libraries from that installation and leave the results under
`compat/build`. Missing compile-time addon jars are read from `smoke-instance`.

## Restored Experimental behavior

The port keeps the old recipes, quantities, energy values and machine rules.
It does not replace missing ingredients with unrelated 1.19.2 items.

- 149 recipes are direct translations of the 2.8.222 configuration, guarded by
  an exact build manifest. Another 185 explicitly restored addon/core recipes
  make the runtime completeness list 334 distinct recipe IDs.
- Legacy fluid cells, charging batteries, packs, tools, Cropnalyzer, sprayer,
  electric jetpack, solar helmet, static boots, containment box, dynamite and
  remote, flat luminator, coke kiln, booze barrel, fluid pipes and pump covers
  retain their old item, NBT, energy and interaction rules.
- The Compact Item Buffer (`ic2:item_buffer_2`) is restored with its exact 9
  buffer slots, 4 upgrade slots and both item input/output upgrade properties.
  The two Reactor Coolant Injector recipes again require this block.
- The creative-only Trading Terminal (`ic2:trading_terminal`) is restored with
  its Remote Interface Upgrade slot (limit 16), base range 512, networked range
  field, old drop/rarity/facing rules and the intentionally unfinished 2.8.222
  list GUI.
- The distinct 2.8.222 hit-region wrench (`ic2:wrench_new`) is no longer merged
  into the machine-removal wrench. Its pipe-link/facing behavior and its exact
  MFSU kit, Batch Crafter and Electric Wrench recipe inputs are restored.
- All fifteen omitted generic crop cards, their base seeds, transformations,
  special drops, crop resources, processing recipes, textures and block states
  are restored.
- The complete 2.8 jetpack event system is restored, including gas, electric
  and quantum flight, hover mode, armor attachment, backup EU, break recovery,
  tooltip and player render layer.
- All fifteen reactor-component implementations were compared against 2.8.222.
  The removed lithium-cell completion behavior is restored exactly: a full
  lithium cell clears its reactor slot because the old tritium rod was never
  registered.
- Wool extraction keeps every real colored-wool bleaching recipe while
  excluding only the invalid white-to-white no-op rejected by the newer recipe
  manager.
- The omitted milk-powder canner enrichment is restored against Forge's shared
  `forge:milk` fluid, avoiding a duplicate IC2 milk registration while keeping
  the original 1000 mB water + milk-powder transformation.

The old `TeBlock` registry audit has no unexplained content gaps. The remaining
literal name differences are 1.19.2 refactors: cables and walls are split into
typed blocks, chargepad names are reordered, `crop` is `crop_stick`, and
`scanner` is `uu_scanner`. The `invalid` enum member was only a sentinel.

## Addons

The installed 1.19.2 addon jars targeted IC2 Classic internals and could not
link against Experimental. Their original jars are retained in recoverable
quarantine; the companion supplies Experimental-native implementations under
the original mod and registry IDs.

- GraviSuite 2.2: all original items and recipes, flight engines, Gravitool,
  Vajra, both 5x5 nuclear jetpacks, compacted jetpacks, Relocator modes and GUI,
  plasma projectile, temporary paired plasma portals and original costs/NBT.
  This includes the 500,000-EU Translocator launch gate, 10,000,000-EU Portal
  charge, original thrown-stack weight rounding, 3x3 Advanced Drill behavior
  and the leaf-qualified, log-only Advanced Chainsaw tree search. Direct
  Relocator and Portable Teleporter transfers also use the original Classic
  teleport sound.
  Previously substituted inputs are restored under their original IDs: IV
  Transformer, Glowtronic Crystal, Quantum Pack, Precision Wrench, Advanced
  Drill/Chainsaw, Memory Stick and Portable Teleporter. The omitted Ultimate
  Lappack, Relocator and NBT-bearing Gravitool upgrade recipes are present. The
  Classic-only upgrade-base, complex-circuit, field/efficiency and simple-import
  component chain stays craftable instead of using unrelated Experimental parts.
- Advanced Solar Panels 2.0.2: all materials, three panel tiers, all three solar
  helmets, MV/HV panels, IV transformer, bronze/plasma cables, Iridium Stone and
  FriendManager permissions, Plasmafier, rare-earth processing chain,
  Molecular Transformer and unchanged prerequisite/assembly recipes.
- IC2 UU-Matter 1.2.2: its original JEI-only matter-value integration.
- Additional Enchanted Miner: its Classic bridge is replaced with an
  Experimental energy bridge while retaining the configured EU/FE ratio.

`industrialupgrade` has a separate mod ID and API and remains installed without
being coupled to `ic2`.

## Conflict and verification status

`audit-mods.ps1` currently reports 71 active jars, 74 declared mod IDs and zero
duplicate mod IDs. A duplicate GeckoLib jar was moved to recoverable
quarantine. The patched IC2 and companion build cleanly; the eleven compiler
warnings are pre-existing missing Fabric annotations/deprecation warnings.
Static checks confirm the restored classes, resources, patched compatibility
fields, 334-recipe manifests and zero registry-ID conflicts. Both active jars
contain 880 unique recipe IDs in total. `validate-artifacts.py` confirms that
all recipe/data/resource JSON parses, all 334 expected legacy recipe IDs are
present, all 107 cross-JAR recipe overrides are intentional, and every internal
model, blockstate, texture and sound reference resolves. `audit-linkage.ps1`
also reports no missing binary class dependencies against the installed
Minecraft, Forge, IC2, JEI and Additional Enchanted Miner classpath.

The last permitted disposable-world run passed its then-current 281/281 recipe
list and core/addon functional checks. The smoke suite has since been expanded
for the final tranche (nuclear jetpacks, Relocator, FriendManager, lithium
cell, Compact Item Buffer and Trading Terminal) and now expects all 334 IDs.
A fresh Forge launch is still required before this port can be declared fully
verified; the current Codex environment temporarily refuses that external
launch, so no newer runtime result is claimed here.
