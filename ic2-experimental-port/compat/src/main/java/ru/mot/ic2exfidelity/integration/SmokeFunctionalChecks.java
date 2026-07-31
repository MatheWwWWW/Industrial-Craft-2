package ru.mot.ic2exfidelity.integration;

import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.transport.BlockFluidPipe;
import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.item.block.ItemFluidPipe;
import ic2.core.item.logistics.ItemPumpCover;
import ic2.core.item.logistics.PumpCoverType;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.api.item.ElectricItem;
import ic2.api.crops.BaseSeed;
import ic2.api.crops.Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.event.TickHandler;
import ic2.core.recipe.AdvRecipe;
import ic2.core.recipe.AdvShapelessRecipe;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import net.minecraft.tags.TagKey;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ru.mot.ic2exfidelity.legacy.LegacyDynamiteBlock;
import ru.mot.ic2exfidelity.legacy.LegacyDynamiteEntity;
import ru.mot.ic2exfidelity.legacy.LegacyBarrelBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyBoozeItem;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnGrateBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnHatchBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyRemoteItem;
import ru.mot.ic2exfidelity.legacy.LegacyFluidCell;
import ru.mot.ic2exfidelity.legacy.LegacyLuminatorBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyCompactItemBufferBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyTradingTerminalBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyMfsuUpgradeKit;
import ru.mot.ic2exfidelity.legacy.LegacyNewWrench;
import ru.mot.ic2exfidelity.legacy.LegacyJetpackAttachmentRecipe;
import ru.mot.ic2exfidelity.legacy.LegacyJetpackHandler;
import ru.mot.ic2exfidelity.gravisuit.LegacyGravisuitJetpack;
import ru.mot.ic2exfidelity.gravisuit.LegacyGravisuitContent;
import ru.mot.ic2exfidelity.gravisuit.LegacyGravitool;
import ru.mot.ic2exfidelity.gravisuit.LegacyRelocatorData;
import ru.mot.ic2exfidelity.gravisuit.LegacyRelocatorItem;
import ru.mot.ic2exfidelity.gravisuit.LegacyVajra;
import ru.mot.ic2exfidelity.friends.LegacyFriendContent;
import ru.mot.ic2exfidelity.friends.LegacyFriendManager;
import ru.mot.ic2exfidelity.advancedsolars.LegacyAdvancedSolarPanelBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyAdvancedSolarHelmetItem;
import ru.mot.ic2exfidelity.advancedsolars.LegacyClassicAdvancedSolarHelmetItem;
import ru.mot.ic2exfidelity.advancedsolars.LegacyClassicSolarPanelBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyIVTransformerBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyIridiumStoneBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyPlasmafierBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyRareEarthExtractorBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyMolecularTransformerBlockEntity;
import ru.mot.ic2exfidelity.advancedsolars.LegacyMolecularTransformerRecipes;

/** Destructive checks that only run in the disposable copied smoke world. */
public final class SmokeFunctionalChecks {
    private SmokeFunctionalChecks() {
    }

    public static void run(ServerLevel level, RecipeManager recipes) {
        if (!(requireItem("ic2:wrench_new") instanceof LegacyNewWrench)) {
            throw new IllegalStateException("Legacy hit-region wrench is absent");
        }
        if (!ForgeRegistries.FLUIDS.containsKey(new ResourceLocation("forge", "milk"))) {
            throw new IllegalStateException("Forge milk fluid required by the legacy canner recipe is absent");
        }
        checkFluidCell(recipes);
        checkMatterAmplifier();
        checkDynamite(level);
        checkLuminator(level, recipes);
        checkCompactItemBuffer(level, recipes);
        checkTradingTerminal(level);
        checkCokeMaterials(recipes);
        checkCokeKiln(level);
        checkBarrel(level, recipes);
        checkFluidPipes(level, recipes);
        checkLegacyCrops(level, recipes);
        checkJetpackAttachment(level, recipes);
        checkGravisuitRegistry();
        checkAdvancedSolars(level);
        BlockPos position = new BlockPos(0, level.m_151558_() - 2, 0);
        BlockState originalState = level.m_8055_(position);
        BlockEntity originalBlockEntity = level.m_7702_(position);
        CompoundTag originalData = originalBlockEntity == null
                ? null : originalBlockEntity.m_187482_();

        try {
            Ic2TileEntityBlock mfeBlock = (Ic2TileEntityBlock) Ic2Blocks.MFE;
            BlockState mfeState = Ic2Blocks.MFE.m_49966_();
            if (mfeState.m_61138_(mfeBlock.facingProperty)
                    && mfeBlock.getSupportedFacings().contains(Direction.EAST)) {
                mfeState = mfeState.m_61124_(mfeBlock.facingProperty, Direction.EAST);
            }
            if (!level.m_7731_(position, mfeState, 3)) {
                throw new IllegalStateException("Unable to place the smoke-test MFE");
            }
            if (!(level.m_7702_(position) instanceof TileEntityElectricBlock mfe)) {
                throw new IllegalStateException("Smoke-test MFE has no electric block entity");
            }
            mfe.energy.forceAddEnergy(12_345.0);
            mfe.redstoneMode = 2;

            boolean upgraded = LegacyMfsuUpgradeKit.upgradeAt(level, position);
            BlockState resultState = level.m_8055_(position);
            if (!(level.m_7702_(position) instanceof TileEntityElectricBlock mfsu)) {
                throw new IllegalStateException("MFSU upgrade produced no electric block entity");
            }
            Ic2TileEntityBlock mfsuBlock = (Ic2TileEntityBlock) Ic2Blocks.MFSU;
            Direction facing = resultState.m_61143_(mfsuBlock.facingProperty);
            boolean passed = upgraded
                    && resultState.m_60734_() == Ic2Blocks.MFSU
                    && mfsu.getStored() == 12_345
                    && mfsu.redstoneMode == 2
                    && facing == Direction.EAST;
            System.out.println("[IC2-FIDELITY-UPGRADE-CHECK] passed=" + passed
                    + " energy=" + mfsu.getStored()
                    + " redstoneMode=" + mfsu.redstoneMode
                    + " facing=" + facing);
            if (!passed) {
                throw new IllegalStateException("MFSU upgrade-kit functional check failed");
            }
        } finally {
            level.m_7731_(position, originalState, 3);
            if (originalData != null) {
                BlockEntity restored = level.m_7702_(position);
                if (restored != null) {
                    restored.m_142466_(originalData);
                    restored.m_6596_();
                }
            }
        }
    }

    private static void checkAdvancedSolars(ServerLevel level) {
        String[] materials = {
                "sunnarium", "sunnarium_alloy", "irradiant_uranium",
                "enriched_sunnarium", "enriched_sunnarium_alloy",
                "irradiant_glass_pane", "iridium_iron_plate",
                "reinforced_iridium_iron_plate", "irradiant_reinforced_plate",
                "sunnarium_part", "iridium_ingot", "mt_core"
        };
        boolean passed = net.minecraftforge.fml.ModList.get().isLoaded("advanced_solars");
        for (String material : materials) {
            passed &= ForgeRegistries.ITEMS.containsKey(
                    new ResourceLocation("advanced_solars", material));
        }

        Map<String, int[]> panels = new LinkedHashMap<>();
        panels.put("advanced_solar_panel", new int[]{16, 2, 1, 32_000, 32});
        panels.put("hybrid_solar_panel", new int[]{128, 16, 2, 100_000, 128});
        panels.put("ultimate_hybrid_solar_panel", new int[]{1_024, 128, 4, 1_000_000, 2_048});
        Map<BlockPos, SavedBlock> saved = new LinkedHashMap<>();
        int index = 0;
        try {
            ResourceLocation ivTransformerKey = new ResourceLocation("ic2", "transformer_iv");
            Block ivTransformerBlock = requireBlock(ivTransformerKey.toString());
            passed &= ForgeRegistries.ITEMS.containsKey(ivTransformerKey)
                    && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(ivTransformerKey);
            BlockPos ivTransformerPosition = new BlockPos(
                    2 + index++, level.m_151558_() - 2, 12);
            saveBlock(level, ivTransformerPosition, saved);
            if (!level.m_7731_(
                    ivTransformerPosition, ivTransformerBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place IV transformer");
            }
            if (!(level.m_7702_(ivTransformerPosition)
                    instanceof LegacyIVTransformerBlockEntity ivTransformer)) {
                throw new IllegalStateException(
                        "IV transformer has no compatible block entity");
            }
            passed &= ivTransformer.getinputflow() == 32_768.0
                    && ivTransformer.getoutputflow() == 8_192.0;

            ResourceLocation iridiumStoneKey = new ResourceLocation("ic2", "iridium_stone");
            Block iridiumStoneBlock = requireBlock(iridiumStoneKey.toString());
            passed &= ForgeRegistries.ITEMS.containsKey(iridiumStoneKey)
                    && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(iridiumStoneKey);
            BlockPos iridiumStonePosition = new BlockPos(
                    2 + index++, level.m_151558_() - 2, 12);
            saveBlock(level, iridiumStonePosition, saved);
            if (!level.m_7731_(
                    iridiumStonePosition, iridiumStoneBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place iridium stone");
            }
            if (!(level.m_7702_(iridiumStonePosition)
                    instanceof LegacyIridiumStoneBlockEntity iridiumStone)) {
                throw new IllegalStateException(
                        "Iridium stone has no compatible block entity");
            }
            java.util.UUID owner = java.util.UUID.fromString(
                    "d95ad22b-081a-4e45-9b82-eca4fe088fd8");
            iridiumStone.setOwner(owner);
            java.util.UUID replacementOwner = java.util.UUID.fromString(
                    "fe0a1dce-2303-4187-bda4-98b1e8ae25bd");
            iridiumStone.setOwner(replacementOwner);
            passed &= iridiumStone.isOwner(owner)
                    && !iridiumStone.isOwner(replacementOwner)
                    && !iridiumStone.canBreak(new java.util.UUID(0L, 0L), false)
                    && iridiumStone.canBreak(new java.util.UUID(0L, 0L), true);

            ResourceLocation plasmafierKey = new ResourceLocation("ic2", "plasmafier");
            Block plasmafierBlock = requireBlock(plasmafierKey.toString());
            passed &= ForgeRegistries.ITEMS.containsKey(plasmafierKey)
                    && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(plasmafierKey)
                    && ForgeRegistries.ITEMS.containsKey(
                            new ResourceLocation("ic2", "cell_plasma"))
                    && ForgeRegistries.ITEMS.containsKey(
                            new ResourceLocation("ic2", "plasma_core"));
            ItemStack pesd = new ItemStack(requireItem("ic2:pesd"));
            passed &= pesd.m_41720_() instanceof ic2.api.item.IElectricItem pesdItem
                    && ElectricItem.manager.getMaxCharge(pesd) == 50_000_000.0
                    && pesdItem.getTransferLimit(pesd) == 25_000.0
                    && ElectricItem.manager.getTier(pesd) == 4;
            BlockPos plasmafierPosition = new BlockPos(
                    2 + index++, level.m_151558_() - 2, 12);
            saveBlock(level, plasmafierPosition, saved);
            if (!level.m_7731_(
                    plasmafierPosition, plasmafierBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place Plasmafier");
            }
            if (!(level.m_7702_(plasmafierPosition)
                    instanceof LegacyPlasmafierBlockEntity plasmafier)) {
                throw new IllegalStateException(
                        "Plasmafier has no compatible block entity");
            }
            plasmafier.matterInput.put(new ItemStack(requireItem("ic2:uu_matter")));
            plasmafier.forceSetTicksForTest(39L);
            plasmafier.processTick();
            passed &= plasmafier.getUuMatter() == 100
                    && plasmafier.matterInput.isEmpty();
            plasmafier.forceAddEnergyForTest(10_240.0);
            plasmafier.processTick();
            passed &= plasmafier.getUuMatter() == 99
                    && plasmafier.getPlasma() == 1
                    && plasmafier.getStoredEU() == 0;
            plasmafier.forceSetPlasmaForTest(1_000);
            plasmafier.emptyCellInput.put(
                    new ItemStack(requireItem("ic2:empty_cell")));
            plasmafier.forceSetTicksForTest(49L);
            plasmafier.processTick();
            passed &= plasmafier.getPlasma() == 0
                    && plasmafier.emptyCellInput.isEmpty()
                    && plasmafier.output.get().m_41720_()
                            == requireItem("ic2:cell_plasma");
            plasmafier.output.put(new ItemStack(requireItem("minecraft:gunpowder")));
            plasmafier.emptyCellInput.put(
                    new ItemStack(requireItem("ic2:empty_cell")));
            plasmafier.forceSetPlasmaForTest(1_000);
            plasmafier.forceSetTicksForTest(49L);
            plasmafier.processTick();
            passed &= plasmafier.getPlasma() == 0
                    && plasmafier.emptyCellInput.get().m_41613_() == 1
                    && plasmafier.output.get().m_41720_()
                            == requireItem("minecraft:gunpowder")
                    && ic2.core.gui.dynamic.GuiParser.parse(
                            plasmafierKey, LegacyPlasmafierBlockEntity.class) != null;

            String[] rareEarthItems = {
                    "rare_earth_dust", "dust_aluminium", "rare_earth_chunk",
                    "dead_magnet", "magnet"
            };
            for (String id : rareEarthItems) {
                passed &= ForgeRegistries.ITEMS.containsKey(
                        new ResourceLocation("ic2", id));
            }
            ResourceLocation rareExtractorKey = new ResourceLocation(
                    "ic2", "rare_earth_extractor");
            Block rareExtractorBlock = requireBlock(rareExtractorKey.toString());
            passed &= ForgeRegistries.ITEMS.containsKey(rareExtractorKey)
                    && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(rareExtractorKey);
            BlockPos rareExtractorPosition = new BlockPos(
                    2 + index++, level.m_151558_() - 2, 12);
            saveBlock(level, rareExtractorPosition, saved);
            if (!level.m_7731_(
                    rareExtractorPosition, rareExtractorBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place Rare Earth Extractor");
            }
            if (!(level.m_7702_(rareExtractorPosition)
                    instanceof LegacyRareEarthExtractorBlockEntity rareExtractor)) {
                throw new IllegalStateException(
                        "Rare Earth Extractor has no compatible block entity");
            }
            rareExtractor.forceAddEnergyForTest(1_120.0);
            rareExtractor.input.put(new ItemStack(requireItem("minecraft:stone")));
            for (int tick = 0; tick < 35; tick++) {
                rareExtractor.tick();
            }
            passed &= rareExtractor.getCurrentMaterial().equals("rare_earth")
                    && rareExtractor.getMaterialProgress() == 3.125F;
            rareExtractor.input.put(new ItemStack(requireItem("minecraft:clay_ball"), 2));
            for (int tick = 0; tick < 70; tick++) {
                rareExtractor.tick();
            }
            passed &= rareExtractor.getCurrentMaterial().equals("aluminium")
                    && rareExtractor.getMaterialProgress() == 0.0F
                    && rareExtractor.output.get().m_41720_()
                            == requireItem("ic2:dust_aluminium");
            rareExtractor.output.clear();
            rareExtractor.input.put(new ItemStack(requireItem("minecraft:obsidian"), 5));
            for (int tick = 0; tick < 175; tick++) {
                rareExtractor.tick();
            }
            passed &= rareExtractor.getCurrentMaterial().equals("rare_earth")
                    && rareExtractor.getMaterialProgress() == 0.0F
                    && rareExtractor.output.get().m_41720_()
                            == requireItem("ic2:rare_earth_dust")
                    && ic2.core.gui.dynamic.GuiParser.parse(
                            rareExtractorKey, LegacyRareEarthExtractorBlockEntity.class) != null;

            RecipeOutput deadMagnetOutput = Recipes.compressor.get(level).getOutputFor(
                    new ItemStack(requireItem("ic2:rare_earth_chunk")), true);
            passed &= deadMagnetOutput != null
                    && deadMagnetOutput.items.size() == 1
                    && deadMagnetOutput.items.get(0).m_41720_()
                            == requireItem("ic2:dead_magnet");

            Recipe<?> plasmaCoreRecipe = level.m_7654_().m_129894_().m_44043_(
                    new ResourceLocation("ic2:shaped/plasma_core")).orElseThrow();
            if (!(plasmaCoreRecipe instanceof AdvRecipe plasmaCoreAdvanced)) {
                throw new IllegalStateException("Plasma-core recipe is not an IC2 shaped recipe");
            }
            ItemStack oneMagnet = new ItemStack(requireItem("ic2:magnet"));
            ItemStack twoMagnets = new ItemStack(requireItem("ic2:magnet"), 2);
            ItemStack threeAlloy = new ItemStack(requireItem("ic2:alloy"), 3);
            ItemStack fourAlloy = new ItemStack(requireItem("ic2:alloy"), 4);
            passed &= java.util.Arrays.stream(plasmaCoreAdvanced.input)
                    .filter(input -> input.matches(twoMagnets) && !input.matches(oneMagnet))
                    .count() == 4
                    && java.util.Arrays.stream(plasmaCoreAdvanced.input)
                    .filter(input -> input.matches(fourAlloy) && !input.matches(threeAlloy))
                    .count() == 4;
            CraftingContainer plasmaCoreGrid = new CraftingContainer(
                    new AbstractContainerMenu(null, -1) {
                        @Override
                        public ItemStack m_7648_(Player player, int slot) {
                            return ItemStack.f_41583_;
                        }

                        @Override
                        public boolean m_6875_(Player player) {
                            return true;
                        }
                    }, 3, 3);
            int[] magnetSlots = {0, 2, 6, 8};
            int[] alloySlots = {1, 3, 5, 7};
            for (int slot : magnetSlots) {
                plasmaCoreGrid.m_6836_(slot, twoMagnets.m_41777_());
            }
            for (int slot : alloySlots) {
                plasmaCoreGrid.m_6836_(slot, fourAlloy.m_41777_());
            }
            plasmaCoreGrid.m_6836_(4, new ItemStack(requireItem("ic2:cell_plasma")));
            passed &= plasmaCoreAdvanced.craft(plasmaCoreGrid).m_41720_()
                    == requireItem("ic2:plasma_core");
            plasmaCoreAdvanced.getRemainder(plasmaCoreGrid);
            for (int slot = 0; slot < 9; slot++) {
                plasmaCoreGrid.m_8020_(slot).m_41774_(1);
            }
            for (int slot = 0; slot < 9; slot++) {
                passed &= plasmaCoreGrid.m_8020_(slot).m_41619_();
            }

            BlockPos electrolyzerPosition = new BlockPos(
                    2 + index++, level.m_151558_() - 2, 12);
            BlockPos magnetMfePosition = electrolyzerPosition.m_121945_(Direction.EAST);
            saveBlock(level, electrolyzerPosition, saved);
            saveBlock(level, magnetMfePosition, saved);
            level.m_7731_(electrolyzerPosition,
                    Ic2Blocks.CLASSIC_ELECTROLYZER.m_49966_(), 3);
            level.m_7731_(magnetMfePosition, Ic2Blocks.MFE.m_49966_(), 3);
            if (!(level.m_7702_(electrolyzerPosition)
                    instanceof ic2.core.block.machine.tileentity.TileEntityClassicElectrolyzer
                            magnetElectrolyzer)
                    || !(level.m_7702_(magnetMfePosition)
                    instanceof TileEntityElectricBlock magnetMfe)) {
                throw new IllegalStateException("Unable to place magnet electrolyzer test pair");
            }
            magnetMfe.energy.forceAddEnergy(magnetMfe.energy.getCapacity());
            magnetElectrolyzer.mfe = magnetMfe;
            magnetElectrolyzer.waterSlot.put(
                    new ItemStack(requireItem("ic2:dead_magnet")));
            for (int tick = 0; tick < 625; tick++) {
                magnetElectrolyzer.tick();
            }
            passed &= magnetElectrolyzer.waterSlot.isEmpty()
                    && magnetElectrolyzer.hydrogenSlot.get().m_41720_()
                            == requireItem("ic2:magnet")
                    && magnetMfe.energy.getEnergy()
                            == magnetMfe.energy.getCapacity() - 20_000.0;

            Map<String, double[]> bronzeCables = new LinkedHashMap<>();
            bronzeCables.put("bronze_cable", new double[]{0.7, 8.0, 2.0 / 16.0});
            bronzeCables.put("bronze_cable_insulated", new double[]{0.65, 32.0, 3.0 / 16.0});
            bronzeCables.put("bronze_cable_double_insulated",
                    new double[]{0.6, 128.0, 3.5 / 16.0});
            String[] bronzeCableItems = {
                    "bronze_cable_item",
                    "bronze_insulated_cable_item",
                    "bronze_double_insulated_cable_item"
            };
            for (String item : bronzeCableItems) {
                passed &= ForgeRegistries.ITEMS.containsKey(
                        new ResourceLocation("ic2", item));
            }
            int insulation = 0;
            for (Map.Entry<String, double[]> entry : bronzeCables.entrySet()) {
                ResourceLocation key = new ResourceLocation("ic2", entry.getKey());
                Block block = requireBlock(key.toString());
                passed &= block instanceof ic2.core.block.wiring.CableBlock cable
                        && cable.getFoamCableBlock() != null;
                BlockPos position = new BlockPos(4 + index++, level.m_151558_() - 2, 12);
                saveBlock(level, position, saved);
                if (!level.m_7731_(position, block.m_49966_(), 3)) {
                    throw new IllegalStateException("Unable to place bronze cable " + key);
                }
                ic2.api.energy.tile.IEnergyTile energyTile =
                        ic2.api.energy.EnergyNet.instance.getTile(level, position);
                if (!(energyTile instanceof ic2.api.energy.tile.IEnergyConductor conductor)) {
                    throw new IllegalStateException(
                            "Bronze cable is not registered in IC2 EnergyNet: " + key);
                }
                double[] expected = entry.getValue();
                passed &= conductor.getConductionLoss() == expected[0]
                        && conductor.getInsulationEnergyAbsorption() == expected[1]
                        && conductor.getInsulationBreakdownEnergy() == 9001.0
                        && conductor.getConductorBreakdownEnergy() == 129.0
                        && ic2.core.block.wiring.CableType.bronze.getThickness(insulation++)
                                == (float) expected[2];
            }

            ResourceLocation plasmaCableKey = new ResourceLocation("ic2", "plasma_cable");
            Block plasmaCableBlock = requireBlock(plasmaCableKey.toString());
            passed &= ForgeRegistries.ITEMS.containsKey(
                    new ResourceLocation("ic2", "plasma_cable_item"))
                    && plasmaCableBlock instanceof ic2.core.block.wiring.CableBlock plasmaCable
                    && plasmaCable.getFoamCableBlock() != null
                    && ic2.core.block.wiring.CableType.plasma.getThickness(0) == 0.375F;
            String[] foamCableBlocks = {
                    "bronze_foam_cable", "bronze_insulated_foam_cable",
                    "bronze_double_insulated_foam_cable", "plasma_foam_cable"
            };
            for (String id : foamCableBlocks) {
                passed &= ForgeRegistries.BLOCKS.containsKey(
                        new ResourceLocation("ic2", id));
            }
            BlockPos plasmaCablePosition = new BlockPos(
                    4 + index++, level.m_151558_() - 2, 12);
            saveBlock(level, plasmaCablePosition, saved);
            if (!level.m_7731_(plasmaCablePosition, plasmaCableBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place plasma cable");
            }
            ic2.api.energy.tile.IEnergyTile plasmaEnergyTile =
                    ic2.api.energy.EnergyNet.instance.getTile(level, plasmaCablePosition);
            if (!(plasmaEnergyTile
                    instanceof ic2.api.energy.tile.IEnergyConductor plasmaConductor)) {
                throw new IllegalStateException(
                        "Plasma cable is not registered in IC2 EnergyNet");
            }
            passed &= plasmaConductor.getConductionLoss() == 1.2
                    && plasmaConductor.getInsulationEnergyAbsorption() == 32_769.0
                    && plasmaConductor.getInsulationBreakdownEnergy() == 32_769.0
                    && plasmaConductor.getConductorBreakdownEnergy() == 32_769.0;

            Item classicAdvancedHelmet = requireItem("ic2:advanced_solar_helmet");
            passed &= classicAdvancedHelmet
                            instanceof LegacyClassicAdvancedSolarHelmetItem helmet
                    && LegacyClassicAdvancedSolarHelmetItem.PRODUCTION == 5
                    && ElectricItem.manager.getMaxCharge(new ItemStack(helmet)) == 0.0
                    && helmet.m_7167_(net.minecraft.world.entity.EquipmentSlot.HEAD)
                            .get(net.minecraft.world.entity.ai.attributes.Attributes.f_22284_)
                            .stream().anyMatch(modifier -> modifier.m_22218_() == 1.0);

            Map<String, int[]> classicPanels = new LinkedHashMap<>();
            classicPanels.put("solar_panel_mv", new int[]{64, 2});
            classicPanels.put("solar_panel_hv", new int[]{512, 3});
            for (Map.Entry<String, int[]> entry : classicPanels.entrySet()) {
                String id = entry.getKey();
                ResourceLocation key = new ResourceLocation("ic2", id);
                Block block = requireBlock(key.toString());
                passed &= ForgeRegistries.ITEMS.containsKey(key)
                        && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(key);
                BlockPos position = new BlockPos(8 + index++, level.m_151558_() - 2, 12);
                saveBlock(level, position, saved);
                if (!level.m_7731_(position, block.m_49966_(), 3)) {
                    throw new IllegalStateException("Unable to place IC2 Classic solar panel " + key);
                }
                if (!(level.m_7702_(position)
                        instanceof LegacyClassicSolarPanelBlockEntity panel)) {
                    throw new IllegalStateException(
                            "IC2 Classic solar panel has no compatible block entity: " + key);
                }
                int[] expected = entry.getValue();
                passed &= panel.getOutput() == expected[0]
                        && panel.getSourceTier() == expected[1]
                        && panel.getCapacity() == expected[0]
                        && panel.getOutputDirections().equals(
                                java.util.EnumSet.complementOf(
                                        java.util.EnumSet.of(Direction.UP)))
                        && ic2.core.gui.dynamic.GuiParser.parse(
                                key, panel.getClass()) != null;
            }

            for (Map.Entry<String, int[]> entry : panels.entrySet()) {
                String id = entry.getKey();
                ResourceLocation key = new ResourceLocation("advanced_solars", id);
                Block block = requireBlock(key.toString());
                passed &= ForgeRegistries.ITEMS.containsKey(key)
                        && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(key);
                BlockPos position = new BlockPos(12 + index++, level.m_151558_() - 2, 12);
                saveBlock(level, position, saved);
                if (!level.m_7731_(position, block.m_49966_(), 3)) {
                    throw new IllegalStateException("Unable to place Advanced Solars panel " + key);
                }
                if (!(level.m_7702_(position)
                        instanceof LegacyAdvancedSolarPanelBlockEntity panel)) {
                    throw new IllegalStateException("Advanced Solars panel has no compatible block entity: " + key);
                }
                int[] expected = entry.getValue();
                double before = panel.getStoredEnergy();
                boolean skyVisible = panel.skyBlockCheck();
                boolean sunVisible = panel.isSunVisible();
                boolean generated = panel.gainEnergy();
                int expectedGeneration = skyVisible
                        ? (sunVisible ? expected[0] : expected[1]) : 0;
                double gained = panel.getStoredEnergy() - before;
                passed &= panel.getDayProduction() == expected[0]
                        && panel.getLowerProduction() == expected[1]
                        && panel.getSourceTier() == expected[2]
                        && panel.getCapacity() == expected[3]
                        && panel.getMaxOutput() == expected[4]
                        && panel.getChargeSlotCount() == 4
                        && generated == (expectedGeneration > 0)
                        && gained == expectedGeneration;
            }

            ResourceLocation transformerKey = new ResourceLocation(
                    "advanced_solars", "molecular_transformer");
            Block transformerBlock = requireBlock(transformerKey.toString());
            int expectedTransformerRecipes = 15
                    + (net.minecraftforge.fml.ModList.get().isLoaded("ae2") ? 1 : 0)
                    + (net.minecraftforge.fml.ModList.get().isLoaded("antimatter_shared") ? 4 : 0);
            passed &= ForgeRegistries.ITEMS.containsKey(transformerKey)
                    && ForgeRegistries.BLOCK_ENTITY_TYPES.containsKey(transformerKey)
                    && LegacyMolecularTransformerRecipes.all().size() == expectedTransformerRecipes
                    && LegacyMolecularTransformerRecipes.all().stream().anyMatch(
                            recipe -> recipe.id().toString().equals("advanced_solars:certus_quartz")
                                    && recipe.energy() == 500_000);
            BlockPos transformerPosition = new BlockPos(
                    12 + index, level.m_151558_() - 2, 12);
            saveBlock(level, transformerPosition, saved);
            if (!level.m_7731_(transformerPosition, transformerBlock.m_49966_(), 3)) {
                throw new IllegalStateException("Unable to place Molecular Transformer");
            }
            if (!(level.m_7702_(transformerPosition)
                    instanceof LegacyMolecularTransformerBlockEntity transformer)) {
                throw new IllegalStateException("Molecular Transformer has no compatible block entity");
            }
            ItemStack netherrack = new ItemStack(requireItem("minecraft:netherrack"));
            transformer.inputSlot.put(netherrack);
            transformer.processTick();
            passed &= transformer.hasConsumedInputs()
                    && transformer.inputSlot.isEmpty()
                    && transformer.getTier() == 6
                    && transformer.getMaxEU() == 70_000
                    && transformer.getStoredEU() == 0
                    && transformer.getCurrentRecipe() != null
                    && transformer.getCurrentRecipe().id().toString()
                            .equals("advanced_solars:gunpowder");
            transformer.forceAddEnergyForTest(70_000);
            transformer.processTick();
            ItemStack transformed = transformer.outputSlot.get();
            passed &= !transformer.hasConsumedInputs()
                    && transformer.getMaxEU() == 0
                    && transformer.getStoredEU() == 0
                    && transformed.m_41720_() == requireItem("minecraft:gunpowder")
                    && transformed.m_41613_() == 2;

            transformer.outputSlot.put(new ItemStack(requireItem("minecraft:gunpowder"), 22));
            transformer.inputSlot.put(new ItemStack(requireItem("minecraft:netherrack")));
            transformer.processTick();
            passed &= !transformer.hasConsumedInputs()
                    && transformer.inputSlot.get().m_41613_() == 1
                    && transformer.outputSlot.get().m_41613_() == 22;
            passed &= ic2.core.gui.dynamic.GuiParser.parse(
                    transformerKey, LegacyMolecularTransformerBlockEntity.class) != null;

            Map<String, int[]> helmets = new LinkedHashMap<>();
            helmets.put("advanced_solar_helmet", new int[]{16, 2, 2});
            helmets.put("hybrid_solar_helmet", new int[]{128, 16, 3});
            helmets.put("ultimate_hybrid_solar_helmet", new int[]{1_024, 128, 4});
            for (Map.Entry<String, int[]> entry : helmets.entrySet()) {
                Item item = requireItem("advanced_solars:" + entry.getKey());
                int[] expected = entry.getValue();
                passed &= item instanceof LegacyAdvancedSolarHelmetItem helmet
                        && helmet.getProduction() == expected[0]
                        && helmet.getLowerProduction() == expected[1]
                        && helmet.getChargingTier() == expected[2]
                        && ElectricItem.manager.getMaxCharge(new ItemStack(helmet)) == 0.0
                        && helmet.m_7167_(net.minecraft.world.entity.EquipmentSlot.HEAD)
                                .get(net.minecraft.world.entity.ai.attributes.Attributes.f_22284_)
                                .stream().anyMatch(modifier -> modifier.m_22218_() == 1.0);
            }

            LegacyAdvancedSolarHelmetItem advancedHelmet =
                    (LegacyAdvancedSolarHelmetItem) requireItem(
                            "advanced_solars:advanced_solar_helmet");
            NonNullList<ItemStack> armorInventory = NonNullList.m_122780_(
                    4, ItemStack.f_41583_);
            NonNullList<ItemStack> offhandInventory = NonNullList.m_122780_(
                    1, ItemStack.f_41583_);
            NonNullList<ItemStack> mainInventory = NonNullList.m_122780_(
                    36, ItemStack.f_41583_);
            ItemStack armorBattery = new ItemStack(requireItem("ic2:re_battery"));
            ItemStack mainBattery = new ItemStack(requireItem("ic2:re_battery"));
            armorInventory.set(0, armorBattery);
            mainInventory.set(0, mainBattery);
            int remaining = advancedHelmet.chargeInventoryLists(
                    java.util.List.of(armorInventory, offhandInventory, mainInventory), 16);
            passed &= remaining == 0
                    && ElectricItem.manager.getCharge(armorBattery) == 16.0
                    && ElectricItem.manager.getCharge(mainBattery) == 0.0;
        } finally {
            restoreBlocks(level, saved);
        }

        System.out.println("[IC2-FIDELITY-ADVANCED-SOLARS] passed=" + passed
                + " materials=" + materials.length
                + " ivTransformer=1"
                + " iridiumStone=1"
                + " plasmafier=1"
                + " rareEarthExtractor=1"
                + " magnetElectrolyzer=1"
                + " bronzeCables=3"
                + " plasmaCable=1"
                + " classicHelmet=1"
                + " classicPanels=2"
                + " panels=" + panels.size()
                + " transformerRecipes=" + LegacyMolecularTransformerRecipes.all().size()
                + " helmets=3");
        if (!passed) {
            throw new IllegalStateException("Advanced Solars compatibility check failed");
        }
    }

    private static void checkGravisuitRegistry() {
        Map<String, double[]> electricItems = new LinkedHashMap<>();
        electricItems.put("advanced_electric_jetpack", new double[]{200_000, 500, 2});
        electricItems.put("advanced_nuclear_jetpack", new double[]{200_000, 0, 2});
        electricItems.put("advanced_lappack", new double[]{600_000, 500, 2});
        electricItems.put("ultimate_lappack", new double[]{10_000_000, 4_000, 3});
        electricItems.put("gravitation_jetpack", new double[]{500_000, 1_000, 3});
        electricItems.put("nuclear_gravitation_jetpack", new double[]{500_000, 0, 3});
        electricItems.put("gravitool", new double[]{50_000, 400, 2});
        electricItems.put("vajra", new double[]{3_000_000, 1_000, 3});
        electricItems.put("relocator", new double[]{50_000_000, 25_000, 5});

        boolean passed = net.minecraftforge.fml.ModList.get().isLoaded("gravisuit");
        for (Map.Entry<String, double[]> entry : electricItems.entrySet()) {
            Item item = requireItem("gravisuit:" + entry.getKey());
            ItemStack stack = new ItemStack(item);
            double[] expected = entry.getValue();
            passed &= ElectricItem.manager.getMaxCharge(stack) == expected[0]
                    && ElectricItem.manager.getTier(stack) == (int) expected[2];
            if (item instanceof ic2.api.item.IElectricItem electric) {
                passed &= electric.getTransferLimit(stack) == expected[1];
            } else {
                passed = false;
            }
            boolean expectedNuclear = entry.getKey().equals("advanced_nuclear_jetpack")
                    || entry.getKey().equals("nuclear_gravitation_jetpack");
            if (item instanceof LegacyGravisuitJetpack jetpack) {
                passed &= jetpack.isNuclear() == expectedNuclear;
            } else if (expectedNuclear) {
                passed = false;
            }
        }

        Map<String, double[]> prerequisiteJetpacks = new LinkedHashMap<>();
        prerequisiteJetpacks.put("nuclear_jetpack", new double[]{30_000, 0, 1, 0.95});
        prerequisiteJetpacks.put("compacted_electric_jetpack", new double[]{360_000, 500, 2, 1.4});
        prerequisiteJetpacks.put("compacted_nuclear_jetpack", new double[]{360_000, 0, 1, 1.8});
        for (Map.Entry<String, double[]> entry : prerequisiteJetpacks.entrySet()) {
            Item item = requireItem("ic2:" + entry.getKey());
            ItemStack stack = new ItemStack(item);
            double[] expected = entry.getValue();
            passed &= item instanceof LegacyGravisuitJetpack jetpack
                    && ElectricItem.manager.getMaxCharge(stack) == expected[0]
                    && ((ic2.api.item.IElectricItem) item).getTransferLimit(stack) == expected[1]
                    && ElectricItem.manager.getTier(stack) == (int) expected[2]
                    && jetpack.getPower(stack) == (float) expected[3]
                    && jetpack.isNuclear()
                            == !entry.getKey().equals("compacted_electric_jetpack");
        }

        Map<String, double[]> classicRecipePrerequisites = new LinkedHashMap<>();
        classicRecipePrerequisites.put("glowtronic_crystal", new double[]{7_500_000, 2_500, 3});
        classicRecipePrerequisites.put("quantum_pack", new double[]{1_200_000, 1_000, 3});
        classicRecipePrerequisites.put("portable_teleporter", new double[]{50_000_000, 25_000, 4});
        classicRecipePrerequisites.put("precision_wrench", new double[]{40_000, 350, 2});
        classicRecipePrerequisites.put("advanced_drill", new double[]{10_000, 100, 3});
        classicRecipePrerequisites.put("advanced_chainsaw", new double[]{10_000, 100, 3});
        for (Map.Entry<String, double[]> entry : classicRecipePrerequisites.entrySet()) {
            Item item = requireItem("ic2:" + entry.getKey());
            ItemStack stack = new ItemStack(item);
            double[] expected = entry.getValue();
            passed &= item instanceof ic2.api.item.IElectricItem electric
                    && ElectricItem.manager.getMaxCharge(stack) == expected[0]
                    && electric.getTransferLimit(stack) == expected[1]
                    && ElectricItem.manager.getTier(stack) == (int) expected[2];
        }
        String[] prerequisiteComponents = {
                "memory_stick", "upgrade_base", "pulsating_quartz",
                "complex_circuit", "advanced_field_expansion_pad_upgrade", "simple_import",
                "efficiency_upgrade", "basic_field_expansion_pad_upgrade",
                "field_expansion_pad_upgrade"
        };
        for (String component : prerequisiteComponents) {
            passed &= ForgeRegistries.ITEMS.containsKey(
                    new ResourceLocation("ic2", component));
        }

        String[] components = {
                "super_conductor_cover", "super_conductor", "cooling_core",
                "gravitation_engine", "magnetron", "vajra_core", "engine_boost"
        };
        for (String component : components) {
            passed &= ForgeRegistries.ITEMS.containsKey(
                    new ResourceLocation("gravisuit", component));
        }

        passed &= RestoredLegacyContent.nuclearJetpackMenu() != null
                && new ResourceLocation("ic2", "nuclear_jetpack").equals(
                        ForgeRegistries.MENU_TYPES.getKey(
                                RestoredLegacyContent.nuclearJetpackMenu()))
                && RestoredLegacyContent.relocatorMenu() != null
                && new ResourceLocation("ic2", "relocator").equals(
                        ForgeRegistries.MENU_TYPES.getKey(
                                RestoredLegacyContent.relocatorMenu()))
                && new ResourceLocation("gravisuit", "plasma_portal").equals(
                        ForgeRegistries.BLOCKS.getKey(
                                LegacyGravisuitContent.PLASMA_PORTAL.get()))
                && new ResourceLocation("gravisuit", "plasma_portal").equals(
                        ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(
                                LegacyGravisuitContent.PLASMA_PORTAL_BLOCK_ENTITY.get()))
                && new ResourceLocation("gravisuit", "plasma_ball").equals(
                        ForgeRegistries.ENTITY_TYPES.getKey(
                                LegacyGravisuitContent.PLASMA_BALL.get()))
                && new ResourceLocation("ic2", "item.relocator.teleport").equals(
                        ForgeRegistries.SOUND_EVENTS.getKey(
                                RestoredLegacyContent.RELOCATOR_TELEPORT.get()));

        Item relocatorItem = requireItem("gravisuit:relocator");
        LegacyRelocatorData relocatorData = new LegacyRelocatorData(
                new BlockPos(12, 34, -56).m_121878_(),
                "minecraft:overworld", "home");
        LegacyRelocatorData restoredRelocatorData = LegacyRelocatorData.read(
                relocatorData.write(), relocatorData.name());
        java.util.UUID owner = java.util.UUID.fromString(
                "00000000-0000-0000-0000-000000000001");
        java.util.UUID friend = java.util.UUID.fromString(
                "00000000-0000-0000-0000-000000000002");
        LegacyFriendManager friendManager = new LegacyFriendManager();
        friendManager.update(owner, friend, "friend", true, true);
        LegacyFriendManager restoredFriends = new LegacyFriendManager(
                friendManager.m_7176_(new CompoundTag()));
        passed &= relocatorItem instanceof LegacyRelocatorItem
                && LegacyRelocatorItem.TRANSLOCATOR_ACTIVATION_REQUIREMENT == 500_000
                && LegacyRelocatorItem.PORTAL_COST == 10_000_000
                && relocatorData.equals(restoredRelocatorData)
                && relocatorData.blockPosition().equals(new BlockPos(12, 34, -56))
                && new ResourceLocation(
                        "ic2_experimental_fidelity", "friends").equals(
                        ForgeRegistries.MENU_TYPES.getKey(
                                LegacyFriendContent.FRIENDS_MENU.get()))
                && restoredFriends.canApply(
                        owner, friend, LegacyFriendManager.BREAK_IRIDIUM);

        Item advanced = requireItem("gravisuit:advanced_electric_jetpack");
        Item gravity = requireItem("gravisuit:gravitation_jetpack");
        Item gravitoolItem = requireItem("gravisuit:gravitool");
        Item vajraItem = requireItem("gravisuit:vajra");
        ItemStack gravitoolStack = new ItemStack(gravitoolItem);
        ItemStack vajraStack = new ItemStack(vajraItem);
        boolean vajraUncharged = vajraItem.m_8102_(vajraStack, Ic2Blocks.REINFORCED_STONE.m_49966_()) == 1.0F;
        ElectricItem.manager.charge(vajraStack, 10_000.0, Integer.MAX_VALUE, true, false);
        passed &= advanced instanceof LegacyGravisuitJetpack advancedJetpack
                && advancedJetpack.getPower(new ItemStack(advanced)) == 1.0F
                && advancedJetpack.getDropPercentage(new ItemStack(advanced)) == 0.05F
                && advancedJetpack.getWorldHeightDivisor(new ItemStack(advanced)) == 1.15F
                && gravity instanceof LegacyGravisuitJetpack gravityJetpack
                && gravityJetpack.getPower(new ItemStack(gravity)) == 1.4F
                && gravityJetpack.getDropPercentage(new ItemStack(gravity)) == 0.0F
                && gravityJetpack.getWorldHeightDivisor(new ItemStack(gravity)) == 1.0F
                && gravityJetpack.isGravitation()
                && checkGravitationEngineState(gravityJetpack, new ItemStack(gravity))
                && gravitoolItem instanceof LegacyGravitool gravitool
                && gravitool.getMode(gravitoolStack) == 0
                && setAndCheckGravitoolMode(gravitool, gravitoolStack, 5, 1)
                && setAndCheckGravitoolMode(gravitool, gravitoolStack, -1, 3)
                && vajraItem instanceof LegacyVajra
                && vajraUncharged
                && vajraItem.m_8102_(vajraStack, Ic2Blocks.REINFORCED_STONE.m_49966_()) == 16_384.0F
                && !vajraStack.m_41784_().m_128471_("silkTouch");

        vajraStack.m_41784_().m_128379_("silkTouch", true);
        passed &= vajraStack.m_41784_().m_128471_("silkTouch");

        System.out.println("[IC2-FIDELITY-GRAVISUIT-REGISTRY] passed=" + passed
                + " electric=" + electricItems.size()
                + " components=" + components.length
                + " prerequisites=" + prerequisiteJetpacks.size()
                + " nuclearReactors=4"
                + " relocatorPortal=1"
                + " relocatorProjectile=1"
                + " friendManager=1");
        if (!passed) {
            throw new IllegalStateException("Gravisuit compatibility registry check failed");
        }
    }

    private static boolean setAndCheckGravitoolMode(
            LegacyGravitool gravitool, ItemStack stack, int storedMode, int expectedMode) {
        stack.m_41784_().m_128344_("mode", (byte) storedMode);
        return gravitool.getMode(stack) == expectedMode;
    }

    private static boolean checkGravitationEngineState(
            LegacyGravisuitJetpack jetpack, ItemStack stack) {
        boolean initiallyActive = jetpack.isJetpackActive(stack)
                && !jetpack.isGravitationEngineEnabled(stack);
        jetpack.setGravitationEngineEnabled(stack, true);
        boolean engineEnabled = jetpack.isGravitationEngineEnabled(stack)
                && !jetpack.isJetpackActive(stack);
        jetpack.setGravitationEngineEnabled(stack, false);
        return initiallyActive && engineEnabled && jetpack.isJetpackActive(stack);
    }

    private static void checkJetpackAttachment(ServerLevel level, RecipeManager recipes) {
        Recipe<?> loaded = recipes.m_44043_(
                new ResourceLocation("ic2:shapeless/jetpack_attachment")).orElseThrow();
        if (!(loaded instanceof LegacyJetpackAttachmentRecipe recipe)) {
            throw new IllegalStateException("Jetpack attachment recipe has the wrong serializer");
        }

        CraftingContainer grid = new CraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public ItemStack m_7648_(Player player, int slot) {
                return ItemStack.f_41583_;
            }

            @Override
            public boolean m_6875_(Player player) {
                return true;
            }
        }, 2, 2);
        ItemStack electricJetpack = new ItemStack(RestoredLegacyContent.ELECTRIC_JETPACK.get());
        ElectricItem.manager.charge(
                electricJetpack, 12_345.0, Integer.MAX_VALUE, true, false);
        ItemStack armor = new ItemStack(requireItem("minecraft:iron_chestplate"));
        armor.m_41784_().m_128379_("ic2FidelityMarker", true);
        grid.m_6836_(0, electricJetpack);
        grid.m_6836_(1, new ItemStack(Ic2Items.JETPACK_ATTACHMENT_PLATE));
        grid.m_6836_(2, armor);

        boolean matches = recipe.m_5818_(grid, level);
        ItemStack result = recipe.m_5874_(grid);
        double transferred = ElectricItem.manager.getCharge(result);
        double maxCharge = ElectricItem.manager.getMaxCharge(result);
        int tier = ElectricItem.manager.getTier(result);
        double discharged = ElectricItem.manager.discharge(
                result, 45.0, Integer.MAX_VALUE, true, false, false);
        double remaining = ElectricItem.manager.getCharge(result);

        CraftingContainer blacklistedGrid = new CraftingContainer(
                new AbstractContainerMenu(null, -1) {
                    @Override
                    public ItemStack m_7648_(Player player, int slot) {
                        return ItemStack.f_41583_;
                    }

                    @Override
                    public boolean m_6875_(Player player) {
                        return true;
                    }
                },
                2,
                2);
        blacklistedGrid.m_6836_(0, electricJetpack.m_41777_());
        blacklistedGrid.m_6836_(1, new ItemStack(Ic2Items.JETPACK_ATTACHMENT_PLATE));
        blacklistedGrid.m_6836_(2, new ItemStack(Ic2Items.JETPACK));
        boolean rejectsBuiltInJetpack = !recipe.m_5818_(blacklistedGrid, level);

        boolean passed = matches
                && result.m_41720_() == requireItem("minecraft:iron_chestplate")
                && result.m_41782_()
                && result.m_41783_().m_128471_("ic2FidelityMarker")
                && LegacyJetpackHandler.hasJetpackAttached(result)
                && transferred == 12_345.0
                && maxCharge == 30_000.0
                && tier == 1
                && discharged == 45.0
                && remaining == 12_300.0
                && rejectsBuiltInJetpack;
        System.out.println("[IC2-FIDELITY-JETPACK-CHECK] passed=" + passed
                + " matches=" + matches
                + " attached=" + LegacyJetpackHandler.hasJetpackAttached(result)
                + " transferred=" + transferred
                + " max=" + maxCharge
                + " tier=" + tier
                + " remaining=" + remaining
                + " blacklist=" + rejectsBuiltInJetpack);
        if (!passed) {
            throw new IllegalStateException("Jetpack attachment functional check failed");
        }
    }

    private static void checkLegacyCrops(ServerLevel level, RecipeManager recipes) {
        Map<String, String> normalDrops = new LinkedHashMap<>();
        normalDrops.put("blazereed", "minecraft:blaze_powder");
        normalDrops.put("bobs_yer_uncle_ranks_berries", "ic2:bobs_yer_uncle_ranks_berry");
        normalDrops.put("corium", "minecraft:leather");
        normalDrops.put("corpse_plant", "minecraft:rotten_flesh");
        normalDrops.put("creeper_weed", "minecraft:gunpowder");
        normalDrops.put("diareed", "ic2:small_diamond_dust");
        normalDrops.put("egg_plant", "minecraft:egg");
        normalDrops.put("ender_blossom", "ic2:ender_pearl_dust");
        normalDrops.put("meat_rose", "minecraft:pink_dye");
        normalDrops.put("milk_wart", "ic2:milk_wart");
        normalDrops.put("oil_berries", "ic2:oil_berry");
        normalDrops.put("slime_plant", "minecraft:slime_ball");
        normalDrops.put("spidernip", "minecraft:string");
        normalDrops.put("tearstalks", "minecraft:ghast_tear");
        normalDrops.put("withereed", "ic2:coal_dust");

        Map<String, LegacyCropContent.LegacyGenericCropCard> cards =
                LegacyCropContent.cropCards();
        Map<String, net.minecraftforge.registries.RegistryObject<Ic2TileEntityBlock>> blocks =
                LegacyCropContent.cropBlocks();
        boolean cardsPassed = cards.size() == 15 && blocks.size() == 15;
        for (Map.Entry<String, String> entry : normalDrops.entrySet()) {
            LegacyCropContent.LegacyGenericCropCard card = cards.get(entry.getKey());
            Ic2TileEntityBlock block = blocks.get(entry.getKey()).get();
            BlockEntityType<?> registeredType = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(
                    new ResourceLocation("ic2", entry.getKey() + "_crop"));
            BlockState matureState = block.m_49966_().m_61124_(
                    block.getAgeProperty(), card.getMaxAge());
            BlockEntity blockEntity = block.m_142194_(BlockPos.f_121853_, matureState);
            if (!(blockEntity instanceof TileEntityCrop crop)
                    || card == null
                    || card.getCropBlock() != block
                    || Crops.instance.getCropCard(block) != card
                    || Ic2BlockEntities.get(new ResourceLocation("ic2", entry.getKey() + "_crop"))
                            != registeredType
                    || crop.getCrop() != card
                    || block.getCropMaxAge() != card.legacyMaxSize() - 1
                    || card.getMaxAge() != card.legacyMaxSize() - 1
                    || !card.canBeHarvested(crop)
                    || card.canGrow(crop)
                    || !card.canCross(crop)
                    || card.getAgeAfterHarvest(crop) != card.legacyAfterHarvestSize() - 1
                    || card.getGrowthDuration(crop) != card.getProperties().getTier()
                            * (card.legacyGrowthSpeed() < 200 ? 200 : card.legacyGrowthSpeed())) {
                cardsPassed = false;
                break;
            }
            ItemStack[] gains = card.getGains(crop);
            if (gains.length == 0 || gains[0].m_41720_() != requireItem(entry.getValue())) {
                cardsPassed = false;
                break;
            }
        }

        BaseSeed baseSeed = Crops.instance.getBaseSeed(
                new ItemStack(LegacyCropContent.MILK_WART.get()));
        boolean baseSeedPassed = baseSeed != null
                && baseSeed.crop == cards.get("milk_wart")
                && baseSeed.size == 0
                && baseSeed.statGrowth == 1
                && baseSeed.statGain == 1
                && baseSeed.statResistance == 1;

        RecipeOutput enderPearlOutput = Recipes.macerator.get(level).getOutputFor(
                new ItemStack(requireItem("minecraft:ender_pearl")), true);
        RecipeOutput enderEyeOutput = Recipes.macerator.get(level).getOutputFor(
                new ItemStack(requireItem("minecraft:ender_eye")), true);
        RecipeOutput emeraldOutput = Recipes.macerator.get(level).getOutputFor(
                new ItemStack(requireItem("minecraft:emerald")), true);
        RecipeOutput berryOutput = Recipes.extractor.get(level).getOutputFor(
                new ItemStack(LegacyCropContent.BOBS_YER_UNCLE_RANKS_BERRY.get()), true);
        RecipeOutput blackWoolOutput = Recipes.extractor.get(level).getOutputFor(
                new ItemStack(requireItem("minecraft:black_wool")), true);
        RecipeOutput whiteWoolOutput = Recipes.extractor.get(level).getOutputFor(
                new ItemStack(requireItem("minecraft:white_wool")), true);
        boolean enderPearlPassed = machineOutput(
                enderPearlOutput, "ic2:ender_pearl_dust", null);
        boolean enderEyePassed = machineOutput(
                enderEyeOutput, "ic2:ender_eye_dust", null);
        boolean emeraldPassed = machineOutput(
                emeraldOutput, "ic2:emerald_dust", "forge:dusts/emerald");
        boolean berryPassed = machineOutput(
                berryOutput, "ic2:small_emerald_dust", null);
        boolean woolPassed = machineOutput(
                blackWoolOutput, "minecraft:white_wool", null)
                && whiteWoolOutput == null;
        boolean machineRecipesPassed = enderPearlPassed && enderEyePassed
                && emeraldPassed && berryPassed && woolPassed;
        System.out.println("[IC2-FIDELITY-CROP-MACHINE-RECIPES] passed="
                + machineRecipesPassed
                + " enderPearl=" + outputId(enderPearlOutput)
                + " enderEye=" + outputId(enderEyeOutput)
                + " emerald=" + outputId(emeraldOutput)
                + " berry=" + outputId(berryOutput)
                + " blackWool=" + outputId(blackWoolOutput)
                + " whiteWool=" + outputId(whiteWoolOutput));

        checkUniformLegacyShapedRecipe(
                recipes,
                "ic2:shaped/diamond_dust_from_small_dusts",
                "ic2:diamond_dust",
                "ic2:small_diamond_dust",
                "forge:dusts/diamond");
        checkUniformLegacyShapedRecipe(
                recipes,
                "ic2:shaped/emerald_dust_from_small_dusts",
                "ic2:emerald_dust",
                "ic2:small_emerald_dust",
                "forge:dusts/emerald");

        BlockPos cropPos = new BlockPos(24, level.m_151558_() - 2, 24);
        BlockPos soilPos = cropPos.m_7495_();
        Map<BlockPos, SavedBlock> saved = new LinkedHashMap<>();
        saveBlock(level, cropPos, saved);
        saveBlock(level, soilPos, saved);
        boolean worldTransformPassed = false;
        try {
            level.m_7731_(soilPos, requireBlock("minecraft:farmland").m_49966_(), 3);
            Ic2TileEntityBlock first = blocks.get("blazereed").get();
            level.m_7731_(cropPos, first.m_49966_(), 3);
            if (level.m_7702_(cropPos) instanceof TileEntityCrop crop) {
                TileEntityCrop transformed = crop.transformCropBlock(cards.get("milk_wart"), 0);
                worldTransformPassed = transformed != null
                        && transformed.getCrop() == cards.get("milk_wart")
                        && level.m_8055_(cropPos).m_60734_() == blocks.get("milk_wart").get()
                        && transformed.getCurrentAge() == 0;
            }
        } finally {
            restoreBlocks(level, saved);
        }

        boolean passed = cardsPassed && baseSeedPassed
                && machineRecipesPassed && worldTransformPassed;
        System.out.println("[IC2-FIDELITY-CROP-CHECK] passed=" + passed
                + " cards=" + cards.size()
                + " cardData=" + cardsPassed
                + " baseSeed=" + baseSeedPassed
                + " machineRecipes=" + machineRecipesPassed
                + " worldTransform=" + worldTransformPassed);
        if (!passed) {
            throw new IllegalStateException("Legacy crop integration differs from IC2 2.8.222");
        }
    }

    private static boolean machineOutput(
            RecipeOutput output, String expectedItem, String compatibleTag) {
        return output != null
                && output.items.size() == 1
                && (compatibleTag == null
                        ? output.items.get(0).m_41720_() == requireItem(expectedItem)
                        : isExactOrTagged(output.items.get(0), expectedItem, compatibleTag))
                && output.items.get(0).m_41613_() == 1;
    }

    private static String outputId(RecipeOutput output) {
        if (output == null || output.items.isEmpty()) {
            return "missing";
        }
        return String.valueOf(ForgeRegistries.ITEMS.getKey(output.items.get(0).m_41720_()));
    }

    private static void checkUniformLegacyShapedRecipe(
            RecipeManager recipes,
            String id,
            String expectedOutput,
            String expectedInput,
            String compatibleOutputTag) {
        Recipe<?> recipe = recipes.m_44043_(new ResourceLocation(id)).orElseThrow();
        ItemStack result = recipe.m_8043_();
        ItemStack input = new ItemStack(requireItem(expectedInput));
        var ingredients = recipe.m_7527_();
        AdvRecipe advanced = recipe instanceof AdvRecipe shaped ? shaped : null;
        CraftingContainer grid = new CraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public ItemStack m_7648_(Player player, int slot) {
                return ItemStack.f_41583_;
            }

            @Override
            public boolean m_6875_(Player player) {
                return true;
            }
        }, 3, 3);
        for (int slot = 0; slot < 9; slot++) {
            grid.m_6836_(slot, input.m_41777_());
        }
        ItemStack crafted = advanced == null ? ItemStack.f_41583_ : advanced.craft(grid);
        boolean passed = advanced != null
                && advanced.inputWidth == 3
                && advanced.inputHeight == 3
                && isExactOrTagged(result, expectedOutput, compatibleOutputTag)
                && result.m_41613_() == 1
                && ingredients.size() == 9
                && isExactOrTagged(crafted, expectedOutput, compatibleOutputTag)
                && crafted.m_41613_() == 1;
        System.out.println("[IC2-FIDELITY-UNIFORM-SHAPED-RECIPE] id=" + id
                + " passed=" + passed
                + " ingredients=" + ingredients.size()
                + " dimensions=" + (advanced == null
                        ? "not-adv-recipe" : advanced.inputWidth + "x" + advanced.inputHeight)
                + " crafted=" + ForgeRegistries.ITEMS.getKey(crafted.m_41720_())
                + " result=" + ForgeRegistries.ITEMS.getKey(result.m_41720_()));
        if (!passed) {
            throw new IllegalStateException(
                    "Uniform legacy shaped recipe differs from IC2 2.8.222: " + id);
        }
    }

    private static boolean isExactOrTagged(
            ItemStack stack, String exactItem, String compatibleTag) {
        if (stack.m_41720_() == requireItem(exactItem)) {
            return true;
        }
        TagKey<Item> tag = TagKey.m_203882_(
                ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(compatibleTag));
        return stack.m_204117_(tag);
    }

    private static void checkFluidCell(RecipeManager recipes) {
        LegacyFluidCell item = (LegacyFluidCell) RestoredLegacyContent.FLUID_CELL.get();
        ItemStack stack = new ItemStack(item);
        int filled = item.fillMb(
                stack, Ic2FluidStack.create(Fluids.f_76193_, 1_000), false, null);
        Ic2FluidStack stored = Ic2FluidStack.get(stack);
        Ic2FluidStack drained = item.drainMb(stack, 1_000, false, null);
        Ic2FluidStack remaining = Ic2FluidStack.get(stack);
        boolean passed = filled == 1_000
                && stored != null
                && stored.hasExactFluid(Fluids.f_76193_)
                && stored.getAmountMb() == 1_000
                && drained != null
                && drained.hasExactFluid(Fluids.f_76193_)
                && drained.getAmountMb() == 1_000
                && (remaining == null || remaining.isEmpty());
        System.out.println("[IC2-FIDELITY-FLUID-CELL-CHECK] passed=" + passed
                + " filled=" + filled
                + " drained=" + (drained == null ? 0 : drained.getAmountMb()));
        if (!passed) {
            throw new IllegalStateException("Universal fluid-cell functional check failed");
        }
        checkFilledCellRecipe(recipes, "ic2:shapeless/water_cell", Fluids.f_76193_);
        checkFilledCellRecipe(recipes, "ic2:shapeless/lava_cell", Fluids.f_76195_);
    }

    private static void checkFilledCellRecipe(RecipeManager recipes, String id, Fluid expected) {
        Recipe<?> recipe = recipes.m_44043_(new ResourceLocation(id)).orElseThrow();
        ItemStack result = recipe.m_8043_();
        Ic2FluidStack stored = Ic2FluidStack.get(result);
        boolean passed = result.m_41720_() == RestoredLegacyContent.FLUID_CELL.get()
                && stored != null
                && stored.hasExactFluid(expected)
                && stored.getAmountMb() == 1_000;
        System.out.println("[IC2-FIDELITY-FILLED-CELL-RECIPE] id=" + id
                + " passed=" + passed
                + " result=" + result
                + " fluid=" + stored);
        if (!passed) {
            throw new IllegalStateException("Filled universal-cell recipe failed: " + id);
        }
    }

    private static void checkMatterAmplifier() {
        MachineRecipeResult<?, ?, ?> scrap = Recipes.matterAmplifier.apply(
                new ItemStack(Ic2Items.SCRAP), true);
        MachineRecipeResult<?, ?, ?> scrapBox = Recipes.matterAmplifier.apply(
                new ItemStack(Ic2Items.SCRAP_BOX), true);
        int scrapValue = scrap == null ? 0 : (Integer) scrap.getOutput();
        int scrapBoxValue = scrapBox == null ? 0 : (Integer) scrapBox.getOutput();
        boolean passed = scrapValue == 5_000 && scrapBoxValue == 45_000;
        System.out.println("[IC2-FIDELITY-MATTER-AMPLIFIER] passed=" + passed
                + " scrap=" + scrapValue
                + " scrapBox=" + scrapBoxValue);
        if (!passed) {
            throw new IllegalStateException("Matter-amplifier values differ from IC2 2.8.222");
        }
    }

    private static void checkDynamite(ServerLevel level) {
        BlockPos supportPos = new BlockPos(2, level.m_151558_() - 3, 2);
        BlockPos dynamitePos = supportPos.m_7494_();
        BlockState originalSupport = level.m_8055_(supportPos);
        BlockState originalDynamite = level.m_8055_(dynamitePos);
        LegacyDynamiteEntity spawned = null;
        try {
            level.m_7731_(supportPos, Ic2Blocks.REINFORCED_STONE.m_49966_(), 3);
            BlockState dynamite = RestoredLegacyContent.DYNAMITE_BLOCK.get().m_49966_()
                    .m_61124_(LegacyDynamiteBlock.FACING, Direction.UP)
                    .m_61124_(LegacyDynamiteBlock.LINKED, Boolean.TRUE);
            level.m_7731_(dynamitePos, dynamite, 3);

            ItemStack remote = new ItemStack(RestoredLegacyContent.REMOTE.get());
            LegacyRemoteItem.addRemote(dynamitePos, remote);
            int linkedIndex = LegacyRemoteItem.hasRemote(dynamitePos, remote);
            LegacyRemoteItem.launchRemotes(level, remote, null);

            AABB search = new AABB(dynamitePos).m_82377_(2.0D, 2.0D, 2.0D);
            for (net.minecraft.world.entity.Entity entity : level.m_6249_(null, search, candidate -> true)) {
                if (entity instanceof LegacyDynamiteEntity dynamiteEntity) {
                    spawned = dynamiteEntity;
                    break;
                }
            }
            boolean passed = linkedIndex == 0
                    && level.m_8055_(dynamitePos).m_60795_()
                    && spawned != null
                    && spawned.isSticky()
                    && spawned.getFuse() == 40
                    && remote.m_41773_() == 0;
            System.out.println("[IC2-FIDELITY-DYNAMITE-CHECK] passed=" + passed
                    + " linkedIndex=" + linkedIndex
                    + " sticky=" + (spawned != null && spawned.isSticky())
                    + " fuse=" + (spawned == null ? -1 : spawned.getFuse())
                    + " remoteDamage=" + remote.m_41773_());
            if (!passed) {
                throw new IllegalStateException("Dynamite remote functional check failed");
            }
        } finally {
            if (spawned != null) {
                spawned.m_146870_();
            }
            level.m_7731_(dynamitePos, originalDynamite, 3);
            level.m_7731_(supportPos, originalSupport, 3);
        }
    }

    private static void checkLuminator(ServerLevel level, RecipeManager recipes) {
        checkLegacyShapedRecipe(
                recipes,
                "ic2:shaped/luminator_flat",
                "ic2:luminator_flat",
                8,
                "ic2:iron_casing", "ic2:insulated_copper_cable", "ic2:iron_casing",
                "minecraft:glass", "ic2:tin_cable", "minecraft:glass",
                "minecraft:glass", "minecraft:glass", "minecraft:glass");
        checkLegacyShapedRecipe(
                recipes,
                "ic2:shaped/scanner",
                "ic2:scanner",
                1,
                "ic2:iron_plate", "ic2:reinforced_glass", "ic2:iron_plate",
                "ic2:electric_motor", "ic2:luminator_flat", "ic2:electric_motor",
                "ic2:advanced_circuit", "ic2:advanced_machine", "ic2:advanced_circuit");
        checkLegacyShapedRecipe(
                recipes,
                "ic2:shaped/night_vision_goggles",
                "ic2:night_vision_goggles",
                1,
                "ic2:advanced_heat_exchanger", "ic2:advanced_re_battery", "ic2:advanced_heat_exchanger",
                "ic2:luminator_flat", "ic2:reinforced_glass", "ic2:luminator_flat",
                "ic2:rubber", "ic2:advanced_circuit", "ic2:rubber");

        BlockPos supportPos = new BlockPos(4, level.m_151558_() - 3, 4);
        BlockPos luminatorPos = supportPos.m_7494_();
        BlockState originalSupport = level.m_8055_(supportPos);
        BlockState originalLuminator = level.m_8055_(luminatorPos);
        BlockEntity originalSupportEntity = level.m_7702_(supportPos);
        BlockEntity originalLuminatorEntity = level.m_7702_(luminatorPos);
        CompoundTag originalSupportData = originalSupportEntity == null
                ? null : originalSupportEntity.m_187482_();
        CompoundTag originalLuminatorData = originalLuminatorEntity == null
                ? null : originalLuminatorEntity.m_187482_();
        boolean scheduled = false;

        try {
            BlockState redstoneSupport = requireBlock("minecraft:redstone_block").m_49966_();
            level.m_7731_(supportPos, redstoneSupport, 3);
            Ic2TileEntityBlock luminatorBlock = RestoredLegacyContent.LUMINATOR_BLOCK.get();
            BlockState luminatorState = luminatorBlock.m_49966_()
                    .m_61124_(luminatorBlock.facingProperty, Direction.UP);
            if (!level.m_7731_(luminatorPos, luminatorState, 3)) {
                throw new IllegalStateException("Unable to place the smoke-test luminator");
            }
            if (!(level.m_7702_(luminatorPos) instanceof LegacyLuminatorBlockEntity luminator)) {
                throw new IllegalStateException("Smoke-test luminator has no legacy block entity");
            }
            // IC2 intentionally completes block-entity loading through a
            // one-shot world-tick callback. Queue the assertion behind that
            // callback so this exercises the real placement lifecycle.
            TickHandler.requestSingleWorldTick(level, ignored -> verifyLoadedLuminator(
                    level,
                    luminatorPos,
                    luminator,
                    originalLuminator,
                    originalLuminatorData,
                    supportPos,
                    originalSupport,
                    originalSupportData));
            scheduled = true;
        } finally {
            if (!scheduled) {
                restoreBlock(level, luminatorPos, originalLuminator, originalLuminatorData);
                restoreBlock(level, supportPos, originalSupport, originalSupportData);
            }
        }
    }

    private static void verifyLoadedLuminator(
            ServerLevel level,
            BlockPos luminatorPos,
            LegacyLuminatorBlockEntity luminator,
            BlockState originalLuminator,
            CompoundTag originalLuminatorData,
            BlockPos supportPos,
            BlockState originalSupport,
            CompoundTag originalSupportData) {
        try {
            if (level.m_7702_(luminatorPos) != luminator) {
                throw new IllegalStateException("Smoke-test luminator changed before deferred load");
            }
            luminator.redstone.update();
            luminator.energy.forceAddEnergy(1.0D);
            luminator.tick();

            CompoundTag invertedData = luminator.m_187482_();
            invertedData.m_128379_("invert", true);
            luminator.m_142466_(invertedData);
            CompoundTag savedData = luminator.m_187482_();

            boolean passed = luminator.energy.getCapacity() == 5.0D
                    && luminator.energy.getSinkTier() == 1
                    && luminator.energy.getSinkDirs().equals(java.util.Set.of(Direction.DOWN))
                    && luminator.getActive()
                    && Math.abs(luminator.energy.getEnergy() - 0.75D) < 0.000_001D
                    && luminator.isInverted()
                    && savedData.m_128471_("invert");
            System.out.println("[IC2-FIDELITY-LUMINATOR-CHECK] passed=" + passed
                    + " capacity=" + luminator.energy.getCapacity()
                    + " sinkTier=" + luminator.energy.getSinkTier()
                    + " sinkDirs=" + luminator.energy.getSinkDirs()
                    + " active=" + luminator.getActive()
                    + " energy=" + luminator.energy.getEnergy()
                    + " invert=" + savedData.m_128471_("invert"));
            if (!passed) {
                throw new IllegalStateException("Flat luminator functional check failed");
            }
        } finally {
            restoreBlock(level, luminatorPos, originalLuminator, originalLuminatorData);
            restoreBlock(level, supportPos, originalSupport, originalSupportData);
        }
    }

    private static void checkLegacyShapedRecipe(
            RecipeManager recipes,
            String id,
            String expectedOutput,
            int expectedCount,
            String... expectedInputs) {
        Recipe<?> recipe = recipes.m_44043_(new ResourceLocation(id)).orElseThrow();
        ItemStack result = recipe.m_8043_();
        boolean passed = recipe instanceof AdvRecipe advanced
                && advanced.inputWidth == 3
                && advanced.inputHeight == 3
                && advanced.input.length == expectedInputs.length
                && result.m_41720_() == requireItem(expectedOutput)
                && result.m_41613_() == expectedCount;
        if (passed) {
            AdvRecipe advanced = (AdvRecipe) recipe;
            for (int index = 0; index < expectedInputs.length; index++) {
                if (!advanced.input[index].matches(new ItemStack(requireItem(expectedInputs[index])))) {
                    passed = false;
                    break;
                }
            }
        }
        System.out.println("[IC2-FIDELITY-SHAPED-RECIPE] id=" + id
                + " passed=" + passed
                + " result=" + result);
        if (!passed) {
            throw new IllegalStateException("Legacy shaped recipe differs from IC2 2.8.222: " + id);
        }
    }

    private static void checkCompactItemBuffer(ServerLevel level, RecipeManager recipes) {
        checkLegacyShapedRecipe(
                recipes, "ic2:shaped/item_buffer_2", "ic2:item_buffer_2", 1,
                "ic2:iron_casing", "minecraft:chest", "ic2:iron_casing",
                "ic2:iron_casing", "ic2:machine", "ic2:iron_casing",
                "ic2:iron_casing", "minecraft:chest", "ic2:iron_casing");
        checkLegacyShapedRecipe(
                recipes, "ic2:shaped/rci_lzh", "ic2:rci_lzh", 1,
                "ic2:ejector_upgrade", "ic2:lzh_condensator", "ic2:ejector_upgrade",
                "ic2:lzh_condensator", "ic2:advanced_machine", "ic2:lzh_condensator",
                "ic2:ejector_upgrade", "ic2:item_buffer_2", "ic2:ejector_upgrade");
        checkLegacyShapedRecipe(
                recipes, "ic2:shaped/rci_rsh", "ic2:rci_rsh", 1,
                "ic2:ejector_upgrade", "ic2:rsh_condensator", "ic2:ejector_upgrade",
                "ic2:rsh_condensator", "ic2:advanced_machine", "ic2:rsh_condensator",
                "ic2:ejector_upgrade", "ic2:item_buffer_2", "ic2:ejector_upgrade");

        BlockPos position = new BlockPos(20, level.m_151558_() - 2, 20);
        BlockState originalState = level.m_8055_(position);
        BlockEntity originalBlockEntity = level.m_7702_(position);
        CompoundTag originalData = originalBlockEntity == null
                ? null : originalBlockEntity.m_187482_();
        try {
            boolean placed = level.m_7731_(
                    position, RestoredLegacyContent.ITEM_BUFFER_2_BLOCK.get().m_49966_(), 3);
            BlockEntity entity = level.m_7702_(position);
            boolean passed = placed
                    && entity instanceof LegacyCompactItemBufferBlockEntity buffer
                    && buffer.bufferSlot.size() == 9
                    && buffer.upgradeSlot.size() == 4
                    && buffer.getUpgradableProperties().contains(
                            ic2.api.upgrade.UpgradableProperty.ItemProducing)
                    && buffer.getUpgradableProperties().contains(
                            ic2.api.upgrade.UpgradableProperty.ItemConsuming);
            System.out.println("[IC2-FIDELITY-COMPACT-BUFFER] passed=" + passed);
            if (!passed) {
                throw new IllegalStateException(
                        "Compact Item Buffer differs from IC2 2.8.222");
            }
        } finally {
            restoreBlock(level, position, originalState, originalData);
        }
    }

    private static void checkTradingTerminal(ServerLevel level) {
        BlockPos position = new BlockPos(21, level.m_151558_() - 2, 20);
        BlockState originalState = level.m_8055_(position);
        BlockEntity originalBlockEntity = level.m_7702_(position);
        CompoundTag originalData = originalBlockEntity == null
                ? null : originalBlockEntity.m_187482_();
        try {
            boolean placed = level.m_7731_(
                    position, RestoredLegacyContent.TRADING_TERMINAL_BLOCK.get().m_49966_(), 3);
            BlockEntity entity = level.m_7702_(position);
            int range = -1;
            if (entity instanceof LegacyTradingTerminalBlockEntity terminal) {
                try {
                    java.lang.reflect.Field field =
                            LegacyTradingTerminalBlockEntity.class.getDeclaredField("range");
                    field.setAccessible(true);
                    range = field.getInt(terminal);
                } catch (ReflectiveOperationException exception) {
                    throw new IllegalStateException("Unable to inspect Trading Terminal range", exception);
                }
            }
            boolean passed = placed
                    && entity instanceof LegacyTradingTerminalBlockEntity terminal
                    && terminal.rangeUpgrade.size() == 1
                    && terminal.rangeUpgrade.getStackSizeLimit() == 16
                    && terminal.getUpgradableProperties().equals(java.util.Collections.singleton(
                            ic2.api.upgrade.UpgradableProperty.RemotelyAccessible))
                    && terminal.getEnergy() == 0.0D
                    && !terminal.useEnergy(1.0D)
                    && range == 512;
            System.out.println("[IC2-FIDELITY-TRADING-TERMINAL] passed=" + passed
                    + " range=" + range);
            if (!passed) {
                throw new IllegalStateException(
                        "Trading Terminal differs from IC2 2.8.222");
            }
        } finally {
            restoreBlock(level, position, originalState, originalData);
        }
    }

    private static void checkCokeMaterials(RecipeManager recipes) {
        checkLegacyShapedRecipe(
                recipes,
                "ic2:shaped/refractory_bricks",
                "ic2:refractory_bricks",
                1,
                "minecraft:clay_ball", "minecraft:brick", "minecraft:clay_ball",
                "minecraft:brick", "minecraft:water_bucket", "minecraft:brick",
                "minecraft:clay_ball", "minecraft:brick", "minecraft:clay_ball");
        checkLegacyShapelessRecipe(
                recipes, "ic2:shapeless/coke_kiln", "ic2:coke_kiln",
                "ic2:refractory_bricks", "ic2:iron_plate");
        checkLegacyShapelessRecipe(
                recipes, "ic2:shapeless/coke_kiln_hatch", "ic2:coke_kiln_hatch",
                "ic2:refractory_bricks", "minecraft:iron_trapdoor");
        checkLegacyShapelessRecipe(
                recipes, "ic2:shapeless/coke_kiln_grate", "ic2:coke_kiln_grate",
                "ic2:refractory_bricks", "minecraft:iron_bars");
        FurnaceFuelBurnTimeEvent event = new FurnaceFuelBurnTimeEvent(
                new ItemStack(RestoredLegacyContent.COKE.get()), 0, null);
        RestoredLegacyContent.onFuelBurnTime(event);
        boolean passed = event.getBurnTime() == 3_200
                && RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get().m_7325_() == 10.0F;
        System.out.println("[IC2-FIDELITY-COKE-MATERIALS] passed=" + passed
                + " burnTime=" + event.getBurnTime()
                + " resistance=" + RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get().m_7325_());
        if (!passed) {
            throw new IllegalStateException("Coke/refractory material properties differ from IC2 2.8.222");
        }
    }

    private static void checkLegacyShapelessRecipe(
            RecipeManager recipes, String id, String expectedOutput, String... expectedInputs) {
        Recipe<?> recipe = recipes.m_44043_(new ResourceLocation(id)).orElseThrow();
        ItemStack result = recipe.m_8043_();
        boolean passed = recipe instanceof AdvShapelessRecipe advanced
                && advanced.input.length == expectedInputs.length
                && result.m_41720_() == requireItem(expectedOutput)
                && result.m_41613_() == 1;
        if (passed) {
            AdvShapelessRecipe advanced = (AdvShapelessRecipe) recipe;
            boolean[] used = new boolean[advanced.input.length];
            for (String expectedInput : expectedInputs) {
                boolean matched = false;
                ItemStack expectedStack = new ItemStack(requireItem(expectedInput));
                for (int index = 0; index < advanced.input.length; index++) {
                    if (!used[index] && advanced.input[index].matches(expectedStack)) {
                        used[index] = true;
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    passed = false;
                    break;
                }
            }
        }
        System.out.println("[IC2-FIDELITY-SHAPELESS-RECIPE] id=" + id
                + " passed=" + passed
                + " result=" + result);
        if (!passed) {
            throw new IllegalStateException("Legacy shapeless recipe differs from IC2 2.8.222: " + id);
        }
    }

    private static void checkCokeKiln(ServerLevel level) {
        BlockPos controllerPos = new BlockPos(8, level.m_151558_() - 3, 8);
        Direction facing = Direction.NORTH;
        BlockPos center = controllerPos.m_121945_(facing.m_122424_());
        Map<BlockPos, SavedBlock> saved = new LinkedHashMap<>();
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    saveBlock(level, center.m_7918_(dx, dy, dz), saved);
                }
            }
        }

        boolean scheduled = false;
        try {
            BlockState refractory = RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get().m_49966_();
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        level.m_7731_(center.m_7918_(dx, dy, dz), refractory, 3);
                    }
                }
            }
            level.m_7471_(center, false);

            Ic2TileEntityBlock controllerBlock = RestoredLegacyContent.COKE_KILN_BLOCK.get();
            Ic2TileEntityBlock hatchBlock = RestoredLegacyContent.COKE_KILN_HATCH_BLOCK.get();
            Ic2TileEntityBlock grateBlock = RestoredLegacyContent.COKE_KILN_GRATE_BLOCK.get();
            level.m_7731_(controllerPos, controllerBlock.m_49966_()
                    .m_61124_(controllerBlock.facingProperty, facing), 3);
            level.m_7731_(center.m_7494_(), hatchBlock.m_49966_()
                    .m_61124_(hatchBlock.facingProperty, facing), 3);
            level.m_7731_(center.m_7495_(), grateBlock.m_49966_()
                    .m_61124_(grateBlock.facingProperty, facing), 3);

            if (!(level.m_7702_(controllerPos) instanceof LegacyCokeKilnBlockEntity controller)
                    || !(level.m_7702_(center.m_7494_())
                            instanceof LegacyCokeKilnHatchBlockEntity hatch)
                    || !(level.m_7702_(center.m_7495_())
                            instanceof LegacyCokeKilnGrateBlockEntity grate)) {
                throw new IllegalStateException("Smoke-test coke kiln is missing block entities");
            }

            TickHandler.requestSingleWorldTick(level, ignored -> verifyLoadedCokeKiln(
                    level, center, controller, hatch, grate, saved));
            scheduled = true;
        } finally {
            if (!scheduled) {
                restoreBlocks(level, saved);
            }
        }
    }

    private static void checkBarrel(ServerLevel level, RecipeManager recipes) {
        Recipe<?> shaped = recipes.m_44043_(new ResourceLocation("ic2:shaped/barrel")).orElseThrow();
        ItemStack shapedResult = shaped.m_8043_();
        boolean shapedPassed = shaped instanceof AdvRecipe advanced
                && advanced.inputWidth == 1
                && advanced.inputHeight == 3
                && advanced.input.length == 3
                && advanced.input[0].matches(new ItemStack(requireItem("minecraft:oak_planks")))
                && advanced.input[1].matches(new ItemStack(requireItem("ic2:rubber_wood")))
                && advanced.input[2].matches(new ItemStack(requireItem("minecraft:oak_planks")))
                && shapedResult.m_41720_() == RestoredLegacyContent.BARREL.get();
        checkLegacyShapelessRecipe(
                recipes, "ic2:shapeless/barrel_reset", "ic2:barrel", "ic2:barrel");

        BlockPos position = new BlockPos(12, level.m_151558_() - 3, 12);
        BlockState originalState = level.m_8055_(position);
        BlockEntity originalBlockEntity = level.m_7702_(position);
        CompoundTag originalData = originalBlockEntity == null
                ? null : originalBlockEntity.m_187482_();
        try {
            Ic2TileEntityBlock block = RestoredLegacyContent.BARREL_BLOCK.get();
            BlockState state = block.m_49966_()
                    .m_61124_(block.facingProperty, Direction.NORTH)
                    .m_61124_(Ic2TileEntityBlock.ACTIVE, Boolean.FALSE);
            level.m_7731_(position, state, 3);
            if (!(level.m_7702_(position) instanceof LegacyBarrelBlockEntity barrel)) {
                throw new IllegalStateException("Smoke-test barrel is missing its block entity");
            }

            barrel.debugSetBeer(12, 6, 2, 2);
            int beerValue = barrel.calculateValue();
            int expectedBeerValue = (((((2 << 3) | 1) << 3) | 2) << 5 | 11) << 2 | 1;
            boolean beerPassed = beerValue == expectedBeerValue
                    && LegacyBoozeItem.getTypeOfValue(beerValue) == 1
                    && LegacyBoozeItem.getAmountOfValue(beerValue) == 12
                    && LegacyBoozeItem.getSolidRatioOfBeerValue(beerValue) == 2
                    && LegacyBoozeItem.getHopsRatioOfBeerValue(beerValue) == 1
                    && LegacyBoozeItem.getTimeRatioOfBeerValue(beerValue) == 2
                    && LegacyBoozeItem.getModelVariant(LegacyBoozeItem.create(beerValue)) == 3
                    && LegacyBoozeItem.create(beerValue).m_41786_().getString()
                            .equals("Lite Alcfree Beer");

            boolean drained = barrel.drainLiquid(1);
            int drainedValue = barrel.calculateValue();
            boolean drainPassed = drained && LegacyBoozeItem.getAmountOfValue(drainedValue) == 11;

            barrel.debugSetRum(8, 100);
            int rumValue = barrel.calculateValue();
            int expectedRumTime = (int) (9_600.0D * Math.pow(0.95D, 7.0D));
            boolean rumPassed = LegacyBarrelBlockEntity.timeNeededForRum(8) == expectedRumTime
                    && LegacyBoozeItem.getTypeOfValue(rumValue) == 2
                    && LegacyBoozeItem.getAmountOfValue(rumValue) == 8
                    && LegacyBoozeItem.getProgressOfRumValue(rumValue) == 100
                    && LegacyBoozeItem.getModelVariant(LegacyBoozeItem.create(rumValue)) == 7;

            CompoundTag saved = barrel.m_187482_();
            boolean nbtPassed = saved.m_128445_("type") == 2
                    && saved.m_128445_("waterCount") == 8
                    && saved.m_128451_("age") == expectedRumTime;
            boolean passed = shapedPassed && beerPassed && drainPassed && rumPassed && nbtPassed;
            System.out.println("[IC2-FIDELITY-BARREL-CHECK] passed=" + passed
                    + " shaped=" + shapedPassed
                    + " beer=" + beerPassed
                    + " drain=" + drainPassed
                    + " rum=" + rumPassed
                    + " nbt=" + nbtPassed
                    + " beerValue=" + beerValue
                    + " rumValue=" + rumValue);
            if (!passed) {
                throw new IllegalStateException("Barrel/booze logic differs from IC2 2.8.222");
            }
        } finally {
            level.m_7731_(position, originalState, 3);
            if (originalData != null) {
                BlockEntity restored = level.m_7702_(position);
                if (restored != null) {
                    restored.m_142466_(originalData);
                    restored.m_6596_();
                }
            }
        }
    }

    private static void checkFluidPipes(ServerLevel level, RecipeManager recipes) {
        String[] materials = {"bronze", "steel"};
        String[] sizes = {"tiny", "small", "medium", "large"};
        int[] counts = {6, 3, 2, 1};
        boolean variantsPassed = true;
        StringBuilder variantDetails = new StringBuilder();
        for (int material = 0; material < materials.length; material++) {
            for (int size = 0; size < sizes.length; size++) {
                String id = "ic2:shaped/" + materials[material] + "_pipe_" + sizes[size];
                Recipe<?> recipe = recipes.m_44043_(new ResourceLocation(id)).orElseThrow();
                ItemStack result = recipe.m_8043_();
                boolean variantPassed = result.m_41720_() == RestoredLegacyContent.PIPE.get()
                        && result.m_41613_() == counts[size]
                        && ItemFluidPipe.getPipeType(result).ordinal() == material
                        && ItemFluidPipe.getPipeSize(result).ordinal() == size;
                variantsPassed &= variantPassed;
                variantDetails.append(id).append('=')
                        .append(result.m_41613_()).append('@')
                        .append(result.m_41783_()).append(':')
                        .append(variantPassed).append(';');
            }
        }

        Recipe<?> lvRecipe = recipes.m_44043_(new ResourceLocation("ic2:shapeless/pump_lv"))
                .orElseThrow();
        Recipe<?> mvRecipe = recipes.m_44043_(new ResourceLocation("ic2:shapeless/pump_mv"))
                .orElseThrow();
        ItemStack bronzeMedium = ItemFluidPipe.getPipe(PipeType.bronze, PipeSize.medium);
        ItemStack bronzeSmall = ItemFluidPipe.getPipe(PipeType.bronze, PipeSize.small);
        ItemStack steelMedium = ItemFluidPipe.getPipe(PipeType.steel, PipeSize.medium);
        if (!(lvRecipe instanceof AdvShapelessRecipe lvAdvanced)
                || !(mvRecipe instanceof AdvShapelessRecipe mvAdvanced)) {
            throw new IllegalStateException("Pump-cover recipes are not IC2 shapeless recipes");
        }
        boolean strictLv = java.util.Arrays.stream(lvAdvanced.input)
                .anyMatch(ingredient -> ingredient.matches(bronzeMedium))
                && java.util.Arrays.stream(lvAdvanced.input)
                        .noneMatch(ingredient -> ingredient.matches(bronzeSmall))
                && java.util.Arrays.stream(lvAdvanced.input)
                        .noneMatch(ingredient -> ingredient.matches(steelMedium));
        boolean strictMv = java.util.Arrays.stream(mvAdvanced.input)
                .anyMatch(ingredient -> ingredient.matches(steelMedium))
                && java.util.Arrays.stream(mvAdvanced.input)
                        .noneMatch(ingredient -> ingredient.matches(bronzeMedium));
        ItemStack lvResult = lvRecipe.m_8043_();
        ItemStack mvResult = mvRecipe.m_8043_();
        boolean pumpsPassed = strictLv && strictMv
                && lvResult.m_41720_() == RestoredLegacyContent.COVER.get()
                && mvResult.m_41720_() == RestoredLegacyContent.COVER.get()
                && ItemPumpCover.getType(lvResult) == PumpCoverType.pump_lv
                && ItemPumpCover.getType(mvResult) == PumpCoverType.pump_mv;
        boolean recipesPassed = variantsPassed && pumpsPassed;

        BlockPos sourcePos = new BlockPos(16, level.m_151558_() - 3, 16);
        BlockPos targetPos = sourcePos.m_121945_(Direction.EAST);
        Map<BlockPos, SavedBlock> saved = new LinkedHashMap<>();
        saveBlock(level, sourcePos, saved);
        saveBlock(level, targetPos, saved);
        try {
            BlockState pipeState = RestoredLegacyContent.FLUID_PIPE_BLOCK.get().m_49966_()
                    .m_61124_(BlockFluidPipe.TYPE, PipeType.bronze)
                    .m_61124_(BlockFluidPipe.SIZE, PipeSize.medium);
            level.m_7731_(sourcePos, pipeState, 3);
            level.m_7731_(targetPos, pipeState, 3);
            if (!(level.m_7702_(sourcePos) instanceof TileEntityFluidPipe source)
                    || !(level.m_7702_(targetPos) instanceof TileEntityFluidPipe target)) {
                throw new IllegalStateException("Smoke-test fluid pipes have no block entities");
            }
            source.configure(PipeType.bronze, PipeSize.medium);
            target.configure(PipeType.bronze, PipeSize.medium);
            source.setConnection(Direction.EAST, true);
            target.setConnection(Direction.WEST, true);
            source.getTank().fill(
                    new FluidStack(Fluids.f_76193_, 1_200),
                    IFluidHandler.FluidAction.EXECUTE);
            source.tickPipe();
            int sourceEqualized = source.getTank().getFluidAmount();
            int targetEqualized = target.getTank().getFluidAmount();
            boolean equalized = sourceEqualized == 600 && targetEqualized == 600;

            source.getTank().drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
            target.getTank().drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
            source.getTank().fill(
                    new FluidStack(Fluids.f_76193_, 100),
                    IFluidHandler.FluidAction.EXECUTE);
            ItemStack lvCover = ItemPumpCover.getCover(PumpCoverType.pump_lv);
            target.placeCover(level, targetPos, Direction.WEST, lvCover);
            boolean pumped = RestoredLegacyContent.COVER.get().onTick(
                    target.getCover(Direction.WEST), target);
            int sourcePumped = source.getTank().getFluidAmount();
            int targetPumped = target.getTank().getFluidAmount();

            IFluidHandler coveredSide = target.getCapability(
                    ForgeCapabilities.FLUID_HANDLER, Direction.WEST).orElseThrow(
                            () -> new IllegalStateException("Covered pipe side lost its fluid capability"));
            FluidStack blockedDrain = coveredSide.drain(1, IFluidHandler.FluidAction.SIMULATE);
            int allowedFill = coveredSide.fill(
                    new FluidStack(Fluids.f_76193_, 1), IFluidHandler.FluidAction.SIMULATE);
            boolean coverGate = blockedDrain.isEmpty() && allowedFill == 1;

            CompoundTag pipeData = target.m_187482_();
            TileEntityFluidPipe loaded = new TileEntityFluidPipe(targetPos, pipeState);
            loaded.m_142466_(pipeData);
            boolean nbtPassed = loaded.getPipeType() == PipeType.bronze
                    && loaded.getPipeSize() == PipeSize.medium
                    && loaded.isConnected(Direction.WEST)
                    && loaded.getTank().getFluidAmount() == 32
                    && ItemPumpCover.getType(loaded.getCover(Direction.WEST)) == PumpCoverType.pump_lv;

            boolean passed = recipesPassed
                    && equalized
                    && pumped
                    && sourcePumped == 68
                    && targetPumped == 32
                    && coverGate
                    && nbtPassed;
            System.out.println("[IC2-FIDELITY-PIPE-CHECK] passed=" + passed
                    + " recipes=" + recipesPassed
                    + " variants=" + variantsPassed
                    + " strictLv=" + strictLv
                    + " strictMv=" + strictMv
                    + " pumpResults=" + lvResult.m_41783_() + "/" + mvResult.m_41783_()
                    + " equalized=" + sourceEqualized + "/" + targetEqualized
                    + " pump=" + sourcePumped + "/" + targetPumped
                    + " gate=" + coverGate
                    + " nbt=" + nbtPassed);
            if (!recipesPassed) {
                System.out.println("[IC2-FIDELITY-PIPE-RECIPE-DETAIL] " + variantDetails);
            }
            if (!passed) {
                throw new IllegalStateException("Fluid-pipe logic differs from IC2 2.8.222");
            }
        } finally {
            restoreBlocks(level, saved);
        }
    }

    private static void verifyLoadedCokeKiln(
            ServerLevel level,
            BlockPos center,
            LegacyCokeKilnBlockEntity controller,
            LegacyCokeKilnHatchBlockEntity hatch,
            LegacyCokeKilnGrateBlockEntity grate,
            Map<BlockPos, SavedBlock> saved) {
        try {
            boolean structure = controller.hasValidStructure();
            hatch.inventory.put(new ItemStack(requireItem("minecraft:coal")));
            for (int tick = 0; tick < 1_820; tick++) {
                controller.tick();
            }
            ItemStack coalOutput = controller.outputSlot.get();
            int coalCreosote = grate.fluidTank.getFluidAmount();
            boolean coalPassed = coalOutput.m_41720_() == RestoredLegacyContent.COKE.get()
                    && coalOutput.m_41613_() == 1
                    && hatch.inventory.get().m_41619_()
                    && grate.fluidTank.hasExactFluid(Ic2Fluids.CREOSOTE.still)
                    && coalCreosote == 500;

            controller.outputSlot.clear();
            grate.fluidTank.setFluidStack(null);
            hatch.inventory.put(new ItemStack(requireItem("minecraft:oak_log")));
            for (int tick = 0; tick < 1_820; tick++) {
                controller.tick();
            }
            ItemStack logOutput = controller.outputSlot.get();
            int logCreosote = grate.fluidTank.getFluidAmount();
            boolean logPassed = logOutput.m_41720_() == requireItem("minecraft:charcoal")
                    && logOutput.m_41613_() == 1
                    && hatch.inventory.get().m_41619_()
                    && grate.fluidTank.hasExactFluid(Ic2Fluids.CREOSOTE.still)
                    && grate.fluidTank.getFluidAmount() == 250;

            BlockPos shellProbe = center.m_7918_(1, 0, 0);
            level.m_7471_(shellProbe, false);
            boolean rejectsBrokenStructure = !controller.hasValidStructure();
            boolean outputSide = grate.fluidTank.canDrain(Direction.NORTH)
                    && !grate.fluidTank.canDrain(Direction.SOUTH);
            boolean passed = structure && coalPassed && logPassed
                    && rejectsBrokenStructure && outputSide;
            System.out.println("[IC2-FIDELITY-COKE-KILN-CHECK] passed=" + passed
                    + " structure=" + structure
                    + " coal=" + coalPassed
                    + " log=" + logPassed
                    + " brokenRejected=" + rejectsBrokenStructure
                    + " outputSide=" + outputSide
                    + " coalCreosote=" + coalCreosote
                    + " logCreosote=" + logCreosote);
            if (!passed) {
                throw new IllegalStateException("Coke-kiln functional check failed");
            }
        } finally {
            restoreBlocks(level, saved);
        }
    }

    private static void saveBlock(
            ServerLevel level, BlockPos position, Map<BlockPos, SavedBlock> saved) {
        if (saved.containsKey(position)) {
            return;
        }
        BlockEntity blockEntity = level.m_7702_(position);
        saved.put(position, new SavedBlock(
                level.m_8055_(position),
                blockEntity == null ? null : blockEntity.m_187482_()));
    }

    private static void restoreBlocks(ServerLevel level, Map<BlockPos, SavedBlock> saved) {
        for (Map.Entry<BlockPos, SavedBlock> entry : saved.entrySet()) {
            restoreBlock(level, entry.getKey(), entry.getValue().state(), entry.getValue().data());
        }
    }

    private record SavedBlock(BlockState state, CompoundTag data) {
    }

    private static Item requireItem(String id) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item == null) {
            throw new IllegalStateException("Missing smoke-test item: " + id);
        }
        return item;
    }

    private static Block requireBlock(String id) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id));
        if (block == null) {
            throw new IllegalStateException("Missing smoke-test block: " + id);
        }
        return block;
    }

    private static void restoreBlock(
            ServerLevel level, BlockPos position, BlockState state, CompoundTag blockEntityData) {
        level.m_7731_(position, state, 3);
        if (blockEntityData != null) {
            BlockEntity restored = level.m_7702_(position);
            if (restored != null) {
                restored.m_142466_(blockEntityData);
                restored.m_6596_();
            }
        }
    }
}
