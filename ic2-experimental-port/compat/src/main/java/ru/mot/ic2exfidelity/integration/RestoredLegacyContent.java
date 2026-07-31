package ru.mot.ic2exfidelity.integration;

import ic2.core.IC2;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.item.crafting.BlockCuttingBlade;
import ic2.core.block.transport.BlockFluidPipe;
import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.item.block.ItemFluidPipe;
import ic2.core.item.logistics.ItemPumpCover;
import ic2.core.item.tool.ContainerMeter;
import ic2.core.item.tool.ContainerToolbox;
import ic2.core.item.tool.ItemFrequencyTransmitter;
import ic2.core.item.tool.ItemToolCrowbar;
import ic2.core.item.tool.ItemToolMeter;
import ic2.core.item.tool.ItemToolbox;
import ic2.core.item.type.BlockCuttingBladeType;
import ic2.core.recipe.MatterAmplifierRecipeManager;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Entities;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.legacy.LegacyChargingBattery;
import ru.mot.ic2exfidelity.legacy.LegacyBarrelBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyBarrelItem;
import ru.mot.ic2exfidelity.legacy.LegacyBoozeItem;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnGrateBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyCokeKilnHatchBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyContainerContainmentBox;
import ru.mot.ic2exfidelity.legacy.LegacyContainmentBox;
import ru.mot.ic2exfidelity.legacy.LegacyCompactItemBufferBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyContainerTradingTerminal;
import ru.mot.ic2exfidelity.legacy.LegacyTradingTerminalBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyContainerCropAnalyzer;
import ru.mot.ic2exfidelity.gravisuit.LegacyContainerNuclearJetpack;
import ru.mot.ic2exfidelity.gravisuit.LegacyContainerRelocator;
import ru.mot.ic2exfidelity.legacy.LegacyCropAnalyzer;
import ru.mot.ic2exfidelity.legacy.LegacyElectricHoe;
import ru.mot.ic2exfidelity.legacy.LegacyElectricJetpack;
import ru.mot.ic2exfidelity.legacy.LegacyEnergyPack;
import ru.mot.ic2exfidelity.legacy.LegacyDynamiteBlock;
import ru.mot.ic2exfidelity.legacy.LegacyDynamiteEntity;
import ru.mot.ic2exfidelity.legacy.LegacyDynamiteItem;
import ru.mot.ic2exfidelity.legacy.LegacyFoamSprayer;
import ru.mot.ic2exfidelity.legacy.LegacyFluidCell;
import ru.mot.ic2exfidelity.legacy.LegacyIodineTablet;
import ru.mot.ic2exfidelity.legacy.LegacyLuminatorBlockEntity;
import ru.mot.ic2exfidelity.legacy.LegacyMfsuUpgradeKit;
import ru.mot.ic2exfidelity.legacy.LegacyNewWrench;
import ru.mot.ic2exfidelity.legacy.LegacyRemoteItem;
import ru.mot.ic2exfidelity.legacy.LegacySolarHelmet;
import ru.mot.ic2exfidelity.legacy.LegacyStaticBoots;
import ru.mot.ic2exfidelity.legacy.LegacyWeedingTrowel;

public final class RestoredLegacyContent {
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "ic2");
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "ic2");
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "ic2");
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "ic2");
    private static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "ic2");
    public static final RegistryObject<SoundEvent> IODINE_EAT = SOUNDS.register(
            "item.iodine_tablet.eat",
            () -> new SoundEvent(new ResourceLocation("ic2", "item.iodine_tablet.eat")));
    public static final RegistryObject<SoundEvent> REMOTE_USE = SOUNDS.register(
            "item.remote.use",
            () -> new SoundEvent(new ResourceLocation("ic2", "item.remote.use")));
    public static final RegistryObject<SoundEvent> RELOCATOR_TELEPORT = SOUNDS.register(
            "item.relocator.teleport",
            () -> new SoundEvent(new ResourceLocation("ic2", "item.relocator.teleport")));

    public static final RegistryObject<LegacyDynamiteBlock> DYNAMITE_BLOCK = BLOCKS.register(
            "dynamite", LegacyDynamiteBlock::new);
    public static final RegistryObject<Block> REFRACTORY_BRICKS_BLOCK = BLOCKS.register(
            "refractory_bricks",
            () -> new Block(BlockBehaviour.Properties.m_60939_(Material.f_76278_)
                    .m_60913_(2.0F, 10.0F)
                    .m_60999_()));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> COKE_KILN_BLOCK =
            BLOCKS.register("coke_kiln", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    cokeMachineProperties(), LegacyCokeKilnBlockEntity.class, true,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    horizontalFacings(), true));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> COKE_KILN_HATCH_BLOCK =
            BLOCKS.register("coke_kiln_hatch", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    cokeMachineProperties(), LegacyCokeKilnHatchBlockEntity.class, false,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    horizontalFacings(), true));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> COKE_KILN_GRATE_BLOCK =
            BLOCKS.register("coke_kiln_grate", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    cokeMachineProperties(), LegacyCokeKilnGrateBlockEntity.class, false,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    horizontalFacings(), true));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> LUMINATOR_BLOCK =
            BLOCKS.register("luminator_flat", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(Material.f_76310_)
                            .m_60913_(0.3F, 0.3F)
                            .m_60953_(state -> state.m_61143_(
                                    ic2.core.block.tileentity.Ic2TileEntityBlock.ACTIVE) ? 15 : 0),
                    LegacyLuminatorBlockEntity.class,
                    true,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    EnumSet.allOf(net.minecraft.core.Direction.class),
                    true));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> BARREL_BLOCK =
            BLOCKS.register("barrel", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    BlockBehaviour.Properties.m_60939_(Material.f_76274_)
                            .m_60913_(2.0F, 3.0F),
                    LegacyBarrelBlockEntity.class,
                    true,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    horizontalFacings(),
                    false));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> ITEM_BUFFER_2_BLOCK =
            BLOCKS.register("item_buffer_2", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    cokeMachineProperties(), LegacyCompactItemBufferBlockEntity.class, false,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Self,
                    EnumSet.noneOf(net.minecraft.core.Direction.class), true));
    public static final RegistryObject<ic2.core.block.tileentity.Ic2TileEntityBlock> TRADING_TERMINAL_BLOCK =
            BLOCKS.register("trading_terminal", () -> ic2.core.block.tileentity.Ic2TileEntityBlock.create(
                    cokeMachineProperties(), LegacyTradingTerminalBlockEntity.class, false,
                    ic2.core.block.tileentity.Ic2TileEntityBlock.DefaultDrop.Machine,
                    horizontalFacings(), false));
    public static final RegistryObject<BlockFluidPipe> FLUID_PIPE_BLOCK = BLOCKS.register(
            "fluid_pipe", BlockFluidPipe::new);
    public static final RegistryObject<EntityType<LegacyDynamiteEntity>> DYNAMITE_ENTITY = ENTITIES.register(
            "dynamite",
            () -> EntityType.Builder
                    .<LegacyDynamiteEntity>m_20704_(LegacyDynamiteEntity::new, MobCategory.MISC)
                    .m_20699_(0.5F, 0.5F)
                    .m_20702_(4)
                    .m_20717_(10)
                    .m_20712_("ic2:dynamite"));
    public static final RegistryObject<BlockEntityType<LegacyLuminatorBlockEntity>> LUMINATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(
                    "luminator_flat",
                    RestoredLegacyContent::createLuminatorBlockEntityType);
    public static final RegistryObject<BlockEntityType<LegacyCokeKilnBlockEntity>> COKE_KILN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("coke_kiln", () -> createBlockEntityType(
                    COKE_KILN_BLOCK, LegacyCokeKilnBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyCokeKilnHatchBlockEntity>>
            COKE_KILN_HATCH_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "coke_kiln_hatch", () -> createBlockEntityType(
                            COKE_KILN_HATCH_BLOCK, LegacyCokeKilnHatchBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyCokeKilnGrateBlockEntity>>
            COKE_KILN_GRATE_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "coke_kiln_grate", () -> createBlockEntityType(
                            COKE_KILN_GRATE_BLOCK, LegacyCokeKilnGrateBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyBarrelBlockEntity>> BARREL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("barrel", () -> createBlockEntityType(
                    BARREL_BLOCK, LegacyBarrelBlockEntity::new));
    public static final RegistryObject<BlockEntityType<TileEntityFluidPipe>> FLUID_PIPE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fluid_pipe", () -> createBlockEntityType(
                    FLUID_PIPE_BLOCK, TileEntityFluidPipe::new));
    public static final RegistryObject<BlockEntityType<LegacyCompactItemBufferBlockEntity>>
            ITEM_BUFFER_2_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "item_buffer_2", () -> createBlockEntityType(
                            ITEM_BUFFER_2_BLOCK, LegacyCompactItemBufferBlockEntity::new));
    public static final RegistryObject<BlockEntityType<LegacyTradingTerminalBlockEntity>>
            TRADING_TERMINAL_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                    "trading_terminal", () -> createBlockEntityType(
                            TRADING_TERMINAL_BLOCK, LegacyTradingTerminalBlockEntity::new));

    public static final RegistryObject<Item> IRON_CUTTING_BLADE = ITEMS.register(
            "iron_cutting_blade",
            () -> new BlockCuttingBlade(materialProperties(), BlockCuttingBladeType.iron));
    public static final RegistryObject<Item> DIAMOND_CUTTING_BLADE = ITEMS.register(
            "diamond_cutting_blade",
            () -> new BlockCuttingBlade(materialProperties(), BlockCuttingBladeType.diamond));
    public static final RegistryObject<Item> STEEL_CUTTING_BLADE = ITEMS.register(
            "steel_cutting_blade",
            () -> new BlockCuttingBlade(materialProperties(), BlockCuttingBladeType.steel));
    public static final RegistryObject<Item> TOOL_BOX = ITEMS.register(
            "tool_box", () -> new ItemToolbox(toolProperties()));
    public static final RegistryObject<Item> METER = ITEMS.register(
            "meter", () -> new ItemToolMeter(toolProperties()));
    public static final RegistryObject<Item> FREQUENCY_TRANSMITTER = ITEMS.register(
            "frequency_transmitter", () -> new ItemFrequencyTransmitter(toolProperties()));
    public static final RegistryObject<Item> CROWBAR = ITEMS.register(
            "crowbar", () -> new ItemToolCrowbar(Tiers.IRON, toolProperties().m_41503_(250)));
    public static final RegistryObject<Item> WRENCH_NEW = ITEMS.register(
            "wrench_new", () -> new LegacyNewWrench(toolProperties().m_41503_(120)));
    public static final RegistryObject<Item> CHARGING_RE_BATTERY = ITEMS.register(
            "charging_re_battery",
            () -> new LegacyChargingBattery(toolProperties(), 40_000.0, 128.0, 1));
    public static final RegistryObject<Item> ADVANCED_CHARGING_RE_BATTERY = ITEMS.register(
            "advanced_charging_re_battery",
            () -> new LegacyChargingBattery(toolProperties(), 400_000.0, 1_024.0, 2));
    public static final RegistryObject<Item> CHARGING_ENERGY_CRYSTAL = ITEMS.register(
            "charging_energy_crystal",
            () -> new LegacyChargingBattery(toolProperties(), 4_000_000.0, 8_192.0, 3));
    public static final RegistryObject<Item> CHARGING_LAPOTRON_CRYSTAL = ITEMS.register(
            "charging_lapotron_crystal",
            () -> new LegacyChargingBattery(toolProperties(), 40_000_000.0, 32_768.0, 4));
    public static final RegistryObject<Item> BATPACK = ITEMS.register(
            "batpack",
            () -> new LegacyEnergyPack(toolProperties(), 60_000.0, 100.0, 1, "batpack"));
    public static final RegistryObject<Item> ADVANCED_BATPACK = ITEMS.register(
            "advanced_batpack",
            () -> new LegacyEnergyPack(toolProperties(), 600_000.0, 1_000.0, 2, "advbatpack"));
    public static final RegistryObject<Item> ENERGY_PACK = ITEMS.register(
            "energy_pack",
            () -> new LegacyEnergyPack(toolProperties(), 2_000_000.0, 1_000.0, 3, "energypack"));
    public static final RegistryObject<Item> LAPPACK = ITEMS.register(
            "lappack",
            () -> new LegacyEnergyPack(toolProperties().m_41497_(net.minecraft.world.item.Rarity.UNCOMMON),
                    20_000_000.0, 2_500.0, 4, "lappack"));
    public static final RegistryObject<Item> ELECTRIC_HOE = ITEMS.register(
            "electric_hoe", () -> new LegacyElectricHoe(toolProperties()));
    public static final RegistryObject<Item> CROP_ANALYZER = ITEMS.register(
            "cropnalyzer", () -> new LegacyCropAnalyzer(
                    toolProperties().m_41497_(net.minecraft.world.item.Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FOAM_SPRAYER = ITEMS.register(
            "foam_sprayer", () -> new LegacyFoamSprayer(toolProperties()));
    public static final RegistryObject<Item> ELECTRIC_JETPACK = ITEMS.register(
            "jetpack_electric", () -> new LegacyElectricJetpack(toolProperties()));
    public static final RegistryObject<Item> WEEDING_TROWEL = ITEMS.register(
            "weeding_trowel", () -> new LegacyWeedingTrowel(toolProperties()));
    public static final RegistryObject<Item> SOLAR_HELMET = ITEMS.register(
            "solar_helmet", () -> new LegacySolarHelmet(toolProperties()));
    public static final RegistryObject<Item> STATIC_BOOTS = ITEMS.register(
            "static_boots", () -> new LegacyStaticBoots(toolProperties()));
    public static final RegistryObject<Item> IODINE_TABLET = ITEMS.register(
            "iodine_tablet", () -> new LegacyIodineTablet(materialProperties(), IODINE_EAT));
    public static final RegistryObject<Item> UPGRADE_KIT = ITEMS.register(
            "upgrade_kit", () -> new LegacyMfsuUpgradeKit(toolProperties()));
    public static final RegistryObject<Item> CONTAINMENT_BOX = ITEMS.register(
            "containment_box", () -> new LegacyContainmentBox(
                    toolProperties().m_41497_(net.minecraft.world.item.Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FLUID_CELL = ITEMS.register(
            "fluid_cell", () -> new LegacyFluidCell(materialProperties()));
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register(
            "dynamite", () -> new LegacyDynamiteItem(materialProperties(), false));
    public static final RegistryObject<Item> DYNAMITE_STICKY = ITEMS.register(
            "dynamite_sticky", () -> new LegacyDynamiteItem(materialProperties(), true));
    public static final RegistryObject<Item> REMOTE = ITEMS.register(
            "remote", () -> new LegacyRemoteItem(toolProperties()));
    public static final RegistryObject<Item> LUMINATOR_FLAT = ITEMS.register(
            "luminator_flat", () -> new BlockItem(LUMINATOR_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> REFRACTORY_BRICKS = ITEMS.register(
            "refractory_bricks", () -> new BlockItem(REFRACTORY_BRICKS_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> COKE = ITEMS.register(
            "coke", () -> new Item(materialProperties()));
    public static final RegistryObject<Item> COKE_KILN = ITEMS.register(
            "coke_kiln", () -> new BlockItem(COKE_KILN_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> COKE_KILN_HATCH = ITEMS.register(
            "coke_kiln_hatch", () -> new BlockItem(COKE_KILN_HATCH_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> COKE_KILN_GRATE = ITEMS.register(
            "coke_kiln_grate", () -> new BlockItem(COKE_KILN_GRATE_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> BARREL = ITEMS.register(
            "barrel", () -> new LegacyBarrelItem(materialProperties()));
    public static final RegistryObject<Item> BOOZE_MUG = ITEMS.register(
            "booze_mug", () -> new LegacyBoozeItem(new Item.Properties()));
    public static final RegistryObject<Item> ITEM_BUFFER_2 = ITEMS.register(
            "item_buffer_2", () -> new BlockItem(ITEM_BUFFER_2_BLOCK.get(), materialProperties()));
    public static final RegistryObject<Item> TRADING_TERMINAL = ITEMS.register(
            "trading_terminal", () -> new BlockItem(
                    TRADING_TERMINAL_BLOCK.get(),
                    materialProperties().m_41497_(net.minecraft.world.item.Rarity.UNCOMMON)));
    public static final RegistryObject<ItemFluidPipe> PIPE = ITEMS.register(
            "pipe", () -> new ItemFluidPipe(materialProperties()));
    public static final RegistryObject<ItemPumpCover> COVER = ITEMS.register(
            "cover", () -> new ItemPumpCover(materialProperties()));

    public static final RegistryObject<SoundEvent> CROWBAR_USE = SOUNDS.register(
            "item.crowbar.use",
            () -> new SoundEvent(new ResourceLocation("ic2", "item.crowbar.use")));

    private static MenuType<ContainerMeter> meterMenu;
    private static MenuType<ContainerToolbox> toolboxMenu;
    private static MenuType<LegacyContainerCropAnalyzer> cropAnalyzerMenu;
    private static MenuType<LegacyContainerContainmentBox> containmentBoxMenu;
    private static MenuType<LegacyContainerNuclearJetpack> nuclearJetpackMenu;
    private static MenuType<LegacyContainerRelocator> relocatorMenu;
    private static MenuType<LegacyContainerTradingTerminal> tradingTerminalMenu;

    private RestoredLegacyContent() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        BLOCK_ENTITIES.register(modBus);
        SOUNDS.register(modBus);
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            patch(Ic2Items.class, "IRON_CUTTING_BLADE", IRON_CUTTING_BLADE.get());
            patch(Ic2Items.class, "DIAMOND_CUTTING_BLADE", DIAMOND_CUTTING_BLADE.get());
            patch(Ic2Items.class, "STEEL_CUTTING_BLADE", STEEL_CUTTING_BLADE.get());
            patch(Ic2Items.class, "TOOL_BOX", TOOL_BOX.get());
            patch(Ic2Items.class, "METER", METER.get());
            patch(Ic2Items.class, "FREQUENCY_TRANSMITTER", FREQUENCY_TRANSMITTER.get());
            patch(Ic2Items.class, "CROWBAR", CROWBAR.get());
            patch(Ic2Items.class, "CHARGING_RE_BATTERY", CHARGING_RE_BATTERY.get());
            patch(Ic2Items.class, "ADVANCED_CHARGING_RE_BATTERY", ADVANCED_CHARGING_RE_BATTERY.get());
            patch(Ic2Items.class, "CHARGING_ENERGY_CRYSTAL", CHARGING_ENERGY_CRYSTAL.get());
            patch(Ic2Items.class, "CHARGING_LAPOTRON_CRYSTAL", CHARGING_LAPOTRON_CRYSTAL.get());
            patch(Ic2Items.class, "BATPACK", BATPACK.get());
            patch(Ic2Items.class, "ADVANCED_BATPACK", ADVANCED_BATPACK.get());
            patch(Ic2Items.class, "ENERGY_PACK", ENERGY_PACK.get());
            patch(Ic2Items.class, "LAPPACK", LAPPACK.get());
            patch(Ic2Items.class, "ELECTRIC_HOE", ELECTRIC_HOE.get());
            patch(Ic2Items.class, "CROP_ANALYZER", CROP_ANALYZER.get());
            patch(Ic2Items.class, "FOAM_SPRAYER", FOAM_SPRAYER.get());
            patch(Ic2Items.class, "ELECTRIC_JETPACK", ELECTRIC_JETPACK.get());
            patch(Ic2Items.class, "WEEDING_TROWEL", WEEDING_TROWEL.get());
            patch(Ic2Items.class, "SOLAR_HELMET", SOLAR_HELMET.get());
            patch(Ic2Items.class, "STATIC_BOOTS", STATIC_BOOTS.get());
            patch(Ic2Items.class, "IODINE_TABLET", IODINE_TABLET.get());
            patch(Ic2Items.class, "UPGRADE_KIT", UPGRADE_KIT.get());
            patch(Ic2Items.class, "CONTAINMENT_BOX", CONTAINMENT_BOX.get());
            patch(Ic2Items.class, "FLUID_CELL", FLUID_CELL.get());
            patch(Ic2Items.class, "DYNAMITE", DYNAMITE.get());
            patch(Ic2Items.class, "DYNAMITE_STICKY", DYNAMITE_STICKY.get());
            patch(Ic2Items.class, "REMOTE", REMOTE.get());
            patch(Ic2Items.class, "LUMINATOR_FLAT", LUMINATOR_FLAT.get());
            patch(Ic2Items.class, "REFRACTORY_BRICKS", REFRACTORY_BRICKS.get());
            patch(Ic2Items.class, "COKE", COKE.get());
            patch(Ic2Items.class, "COKE_KILN", COKE_KILN.get());
            patch(Ic2Items.class, "COKE_KILN_HATCH", COKE_KILN_HATCH.get());
            patch(Ic2Items.class, "COKE_KILN_GRATE", COKE_KILN_GRATE.get());
            patch(Ic2Items.class, "BARREL", BARREL.get());
            patch(Ic2Items.class, "BOOZE_MUG", BOOZE_MUG.get());
            patch(Ic2Items.class, "PIPE", PIPE.get());
            patch(Ic2Items.class, "COVER", COVER.get());
            patch(Ic2Blocks.class, "DYNAMITE", DYNAMITE_BLOCK.get());
            patch(Ic2Blocks.class, "LUMINATOR_FLAT", LUMINATOR_BLOCK.get());
            patch(Ic2Blocks.class, "REFRACTORY_BRICKS", REFRACTORY_BRICKS_BLOCK.get());
            patch(Ic2Blocks.class, "COKE_KILN", COKE_KILN_BLOCK.get());
            patch(Ic2Blocks.class, "COKE_KILN_HATCH", COKE_KILN_HATCH_BLOCK.get());
            patch(Ic2Blocks.class, "COKE_KILN_GRATE", COKE_KILN_GRATE_BLOCK.get());
            patch(Ic2Blocks.class, "BARREL", BARREL_BLOCK.get());
            patch(Ic2Blocks.class, "FLUID_PIPE", FLUID_PIPE_BLOCK.get());
            patch(Ic2Entities.class, "DYNAMITE", DYNAMITE_ENTITY.get());
            patch(Ic2BlockEntities.class, "LUMINATOR_FLAT", LUMINATOR_BLOCK_ENTITY.get());
            patch(Ic2BlockEntities.class, "COKE_KILN", COKE_KILN_BLOCK_ENTITY.get());
            patch(Ic2BlockEntities.class, "COKE_KILN_HATCH", COKE_KILN_HATCH_BLOCK_ENTITY.get());
            patch(Ic2BlockEntities.class, "COKE_KILN_GRATE", COKE_KILN_GRATE_BLOCK_ENTITY.get());
            patch(Ic2BlockEntities.class, "BARREL", BARREL_BLOCK_ENTITY.get());
            patch(Ic2BlockEntities.class, "FLUID_PIPE", FLUID_PIPE_BLOCK_ENTITY.get());
            patch(Ic2Items.class, "ITEM_BUFFER_2", ITEM_BUFFER_2.get());
            patch(Ic2Blocks.class, "ITEM_BUFFER_2", ITEM_BUFFER_2_BLOCK.get());
            patch(Ic2BlockEntities.class, "ITEM_BUFFER_2", ITEM_BUFFER_2_BLOCK_ENTITY.get());
            patch(Ic2Items.class, "TRADING_TERMINAL", TRADING_TERMINAL.get());
            patch(Ic2Blocks.class, "TRADING_TERMINAL", TRADING_TERMINAL_BLOCK.get());
            patch(Ic2BlockEntities.class, "TRADING_TERMINAL", TRADING_TERMINAL_BLOCK_ENTITY.get());
            patch(Ic2SoundEvents.class, "ITEM_CROWBAR_USE", CROWBAR_USE.get());
            patch(Ic2SoundEvents.class, "ITEM_REMOTE_USE", REMOTE_USE.get());
            DispenserBlock.m_52672_(DYNAMITE.get(), dynamiteDispenseBehavior(false));
            DispenserBlock.m_52672_(DYNAMITE_STICKY.get(), dynamiteDispenseBehavior(true));
        });
    }

    private static AbstractProjectileDispenseBehavior dynamiteDispenseBehavior(boolean sticky) {
        return new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile m_6895_(Level level, Position position, ItemStack stack) {
                return new LegacyDynamiteEntity(
                        level,
                        position.m_7096_(),
                        position.m_7098_(),
                        position.m_7094_(),
                        sticky);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static BlockEntityType<LegacyLuminatorBlockEntity> createLuminatorBlockEntityType() {
        return createBlockEntityType(LUMINATOR_BLOCK, LegacyLuminatorBlockEntity::new);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(
            RegistryObject<? extends Block> block,
            LegacyBlockEntityFactory<T> creator) {
        try {
            // In Mojang's 1.19.2 SRG compile jar the otherwise public factory
            // interface is package-private. Construct through its runtime
            // signature so the companion remains buildable without replacing
            // the launcher's mapped Minecraft jar.
            Class<?> factoryType = Class.forName(
                    "net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier");
            Object factory = Proxy.newProxyInstance(
                    RestoredLegacyContent.class.getClassLoader(),
                    new Class<?>[] {factoryType},
                    (proxy, method, args) -> {
                        if (method.getName().equals("m_155267_") && args != null && args.length == 2) {
                            return creator.create(
                                    (net.minecraft.core.BlockPos) args[0],
                                    (net.minecraft.world.level.block.state.BlockState) args[1]);
                        }
                        if (method.getName().equals("toString")) {
                            return "IC2 restored block-entity factory for " + block.getId();
                        }
                        return null;
                    });
            Constructor<BlockEntityType> constructor = BlockEntityType.class.getConstructor(
                    factoryType, Set.class, com.mojang.datafixers.types.Type.class);
            return (BlockEntityType<T>) constructor.newInstance(factory, Set.of(block.get()), null);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(
                    "Unable to create restored block-entity type for " + block.getId(), exception);
        }
    }

    private static BlockBehaviour.Properties cokeMachineProperties() {
        return BlockBehaviour.Properties.m_60939_(ic2.core.ref.IC2Material.MACHINE)
                .m_60999_()
                .m_60913_(2.0F, 10.0F)
                .m_60918_(net.minecraft.world.level.block.SoundType.f_56743_);
    }

    private static EnumSet<net.minecraft.core.Direction> horizontalFacings() {
        return EnumSet.of(
                net.minecraft.core.Direction.NORTH,
                net.minecraft.core.Direction.SOUTH,
                net.minecraft.core.Direction.WEST,
                net.minecraft.core.Direction.EAST);
    }

    @FunctionalInterface
    public interface LegacyBlockEntityFactory<T extends BlockEntity> {
        T create(net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state);
    }

    public static void restoreMatterAmplifiers() {
        if (!(Recipes.matterAmplifier instanceof MatterAmplifierRecipeManager manager)) {
            throw new IllegalStateException("IC2 matter-amplifier recipe API is not initialized");
        }
        manager.addRecipe(
                new LegacyExactRecipeInput(new ItemStack(Ic2Items.SCRAP)),
                5_000, null, true);
        manager.addRecipe(
                new LegacyExactRecipeInput(new ItemStack(Ic2Items.SCRAP_BOX)),
                45_000, null, true);
    }

    public static void onFuelBurnTime(net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().m_41720_() == COKE.get()) {
            event.setBurnTime(3_200);
        }
    }

    /**
     * IC2 2.9.162's RecipeInputItemStack returns List.of() from listStacks(),
     * while RecipeInputBase.getInputs() calls replaceAll() on that list.  The
     * resulting UnsupportedOperationException made the otherwise present
     * matter-amplifier bootstrap unusable.  This small API-native input keeps
     * the original strict stack match without entering that broken code path.
     */
    private static final class LegacyExactRecipeInput implements IRecipeInput {
        private final ItemStack input;

        private LegacyExactRecipeInput(ItemStack input) {
            this.input = input.m_41777_();
        }

        @Override
        public boolean matches(ItemStack candidate) {
            return StackUtil.checkItemEqualityStrict(input, candidate);
        }

        @Override
        public int getAmount() {
            return input.m_41613_();
        }

        @Override
        public List<ItemStack> getInputs() {
            return List.of(input.m_41777_());
        }
    }

    public static void setMenus(
            MenuType<ContainerMeter> restoredMeterMenu,
            MenuType<ContainerToolbox> restoredToolboxMenu,
            MenuType<LegacyContainerCropAnalyzer> restoredCropAnalyzerMenu,
            MenuType<LegacyContainerContainmentBox> restoredContainmentBoxMenu,
            MenuType<LegacyContainerNuclearJetpack> restoredNuclearJetpackMenu,
            MenuType<LegacyContainerRelocator> restoredRelocatorMenu,
            MenuType<LegacyContainerTradingTerminal> restoredTradingTerminalMenu) {
        meterMenu = restoredMeterMenu;
        toolboxMenu = restoredToolboxMenu;
        cropAnalyzerMenu = restoredCropAnalyzerMenu;
        containmentBoxMenu = restoredContainmentBoxMenu;
        nuclearJetpackMenu = restoredNuclearJetpackMenu;
        relocatorMenu = restoredRelocatorMenu;
        tradingTerminalMenu = restoredTradingTerminalMenu;
        patch(Ic2ScreenHandlers.class, "METER", restoredMeterMenu);
        patch(Ic2ScreenHandlers.class, "TOOL_BOX", restoredToolboxMenu);
        patch(Ic2ScreenHandlers.class, "CROP_ANALYZER", restoredCropAnalyzerMenu);
        patch(Ic2ScreenHandlers.class, "CONTAINMENT_BOX", restoredContainmentBoxMenu);
        patch(Ic2ScreenHandlers.class, "TRADING_TERMINAL", restoredTradingTerminalMenu);
    }

    public static MenuType<ContainerMeter> meterMenu() {
        return meterMenu;
    }

    public static MenuType<ContainerToolbox> toolboxMenu() {
        return toolboxMenu;
    }

    public static MenuType<LegacyContainerCropAnalyzer> cropAnalyzerMenu() {
        return cropAnalyzerMenu;
    }

    public static MenuType<LegacyContainerContainmentBox> containmentBoxMenu() {
        return containmentBoxMenu;
    }

    public static MenuType<LegacyContainerNuclearJetpack> nuclearJetpackMenu() {
        return nuclearJetpackMenu;
    }

    public static MenuType<LegacyContainerRelocator> relocatorMenu() {
        return relocatorMenu;
    }

    public static MenuType<LegacyContainerTradingTerminal> tradingTerminalMenu() {
        return tradingTerminalMenu;
    }

    private static Item.Properties materialProperties() {
        return new Item.Properties().m_41491_(IC2.tabIC2);
    }

    private static Item.Properties toolProperties() {
        return new Item.Properties().m_41487_(1).m_41491_(IC2.tabIC2);
    }

    private static void patch(Class<?> owner, String name, Object value) {
        try {
            Field field = owner.getDeclaredField(name);
            field.setAccessible(true);
            field.set(null, value);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to restore IC2 field " + owner.getName() + "." + name, exception);
        }
    }
}
