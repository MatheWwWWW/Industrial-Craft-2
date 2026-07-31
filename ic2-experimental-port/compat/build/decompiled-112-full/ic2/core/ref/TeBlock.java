/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.common.registry.GameRegistry
 */
package ic2.core.ref;

import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.ITeBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBarrel;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityWall;
import ic2.core.block.generator.tileentity.TileEntityCreativeGenerator;
import ic2.core.block.generator.tileentity.TileEntityGenerator;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import ic2.core.block.generator.tileentity.TileEntityRTGenerator;
import ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntitySolidHeatGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityManualKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.block.machine.tileentity.ITnt;
import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
import ic2.core.block.machine.tileentity.TileEntityAssemblyBench;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityBetterItemBuffer;
import ic2.core.block.machine.tileentity.TileEntityBlastFurnace;
import ic2.core.block.machine.tileentity.TileEntityBlockCutter;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.block.machine.tileentity.TileEntityCentrifuge;
import ic2.core.block.machine.tileentity.TileEntityChunkloader;
import ic2.core.block.machine.tileentity.TileEntityCompressor;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityExtractor;
import ic2.core.block.machine.tileentity.TileEntityFermenter;
import ic2.core.block.machine.tileentity.TileEntityFluidBottler;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import ic2.core.block.machine.tileentity.TileEntityInduction;
import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.block.machine.tileentity.TileEntityItemBuffer;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.block.machine.tileentity.TileEntityMacerator;
import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.block.machine.tileentity.TileEntityNuke;
import ic2.core.block.machine.tileentity.TileEntityOreWashing;
import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import ic2.core.block.machine.tileentity.TileEntityScanner;
import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.block.machine.tileentity.TileEntitySteamRepressurizer;
import ic2.core.block.machine.tileentity.TileEntityTank;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.block.machine.tileentity.TileEntityTesla;
import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
import ic2.core.block.machine.tileentity.TileEntityWeightedItemDistributor;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.block.personal.TileEntityTradingTerminal;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.reactor.tileentity.TileEntityRCI_LZH;
import ic2.core.block.reactor.tileentity.TileEntityRCI_RSH;
import ic2.core.block.reactor.tileentity.TileEntityReactorAccessHatch;
import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorFluidPort;
import ic2.core.block.reactor.tileentity.TileEntityReactorRedstonePort;
import ic2.core.block.steam.TileEntityCokeKiln;
import ic2.core.block.steam.TileEntityCokeKilnGrate;
import ic2.core.block.steam.TileEntityCokeKilnHatch;
import ic2.core.block.storage.box.TileEntityBronzeStorageBox;
import ic2.core.block.storage.box.TileEntityIridiumStorageBox;
import ic2.core.block.storage.box.TileEntityIronStorageBox;
import ic2.core.block.storage.box.TileEntitySteelStorageBox;
import ic2.core.block.storage.box.TileEntityWoodenStorageBox;
import ic2.core.block.storage.tank.TileEntityBronzeTank;
import ic2.core.block.storage.tank.TileEntityIridiumTank;
import ic2.core.block.storage.tank.TileEntityIronTank;
import ic2.core.block.storage.tank.TileEntitySteelTank;
import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.block.wiring.TileEntityCableDetector;
import ic2.core.block.wiring.TileEntityCableSplitter;
import ic2.core.block.wiring.TileEntityChargepadBatBox;
import ic2.core.block.wiring.TileEntityChargepadCESU;
import ic2.core.block.wiring.TileEntityChargepadMFE;
import ic2.core.block.wiring.TileEntityChargepadMFSU;
import ic2.core.block.wiring.TileEntityElectricBatBox;
import ic2.core.block.wiring.TileEntityElectricCESU;
import ic2.core.block.wiring.TileEntityElectricMFE;
import ic2.core.block.wiring.TileEntityElectricMFSU;
import ic2.core.block.wiring.TileEntityLuminator;
import ic2.core.block.wiring.TileEntityTransformerEV;
import ic2.core.block.wiring.TileEntityTransformerHV;
import ic2.core.block.wiring.TileEntityTransformerLV;
import ic2.core.block.wiring.TileEntityTransformerMV;
import ic2.core.crop.TileEntityCrop;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.profile.Version;
import ic2.core.ref.IC2Material;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class TeBlock
extends Enum<TeBlock>
implements ITeBlock,
ITeBlock.ITeBlockCreativeRegisterer {
    public static final /* enum */ TeBlock invalid = new TeBlock(null, 0, false, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, 5.0f, 10.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock barrel = new TeBlock(TileEntityBarrel.class, -1, true, Util.horizontalFacings, false, HarvestTool.Axe, DefaultDrop.None, 2.0f, 6.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock wall = new TeBlock(TileEntityWall.class, -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 3.0f, 30.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock itnt = new TeBlock(ITnt.class, 1, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0f, 0.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock nuke = new TeBlock(TileEntityNuke.delegate(), 2, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0f, 0.0f, EnumRarity.UNCOMMON);
    public static final /* enum */ TeBlock generator = new TeBlock(TileEntityGenerator.class, 3, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock geo_generator = new TeBlock(TileEntityGeoGenerator.class, 4, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock kinetic_generator = new TeBlock(TileEntityKineticGenerator.class, 5, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock rt_generator = new TeBlock(TileEntityRTGenerator.class, 6, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock semifluid_generator = new TeBlock(TileEntitySemifluidGenerator.class, 7, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock solar_generator = new TeBlock(TileEntitySolarGenerator.class, 8, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock stirling_generator = new TeBlock(TileEntityStirlingGenerator.class, 9, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock water_generator = new TeBlock(TileEntityWaterGenerator.class, 10, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock wind_generator = new TeBlock(TileEntityWindGenerator.class, 11, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock electric_heat_generator = new TeBlock(TileEntityElectricHeatGenerator.class, 12, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fluid_heat_generator = new TeBlock(TileEntityFluidHeatGenerator.class, 13, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock rt_heat_generator = new TeBlock(TileEntityRTHeatGenerator.class, 14, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock solid_heat_generator = new TeBlock(TileEntitySolidHeatGenerator.class, 15, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock electric_kinetic_generator = new TeBlock(TileEntityElectricKineticGenerator.class, 16, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock manual_kinetic_generator = new TeBlock(TileEntityManualKineticGenerator.class, 17, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0f, 10.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock steam_kinetic_generator = new TeBlock(TileEntitySteamKineticGenerator.class, 18, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock stirling_kinetic_generator = new TeBlock(TileEntityStirlingKineticGenerator.class, 19, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock water_kinetic_generator = new TeBlock(TileEntityWaterKineticGenerator.class, 20, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock wind_kinetic_generator = new TeBlock(TileEntityWindKineticGenerator.class, 21, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock nuclear_reactor = new TeBlock(TileEntityNuclearReactorElectric.class, 22, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock reactor_access_hatch = new TeBlock(TileEntityReactorAccessHatch.class, 23, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0f, 90.0f, EnumRarity.UNCOMMON);
    public static final /* enum */ TeBlock reactor_chamber = new TeBlock(TileEntityReactorChamberElectric.class, 24, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock reactor_fluid_port = new TeBlock(TileEntityReactorFluidPort.class, 25, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0f, 90.0f, EnumRarity.UNCOMMON);
    public static final /* enum */ TeBlock reactor_redstone_port = new TeBlock(TileEntityReactorRedstonePort.class, 26, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0f, 90.0f, EnumRarity.UNCOMMON);
    public static final /* enum */ TeBlock condenser = new TeBlock(TileEntityCondenser.class, 27, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fluid_bottler = new TeBlock(TileEntityFluidBottler.class, 28, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fluid_distributor = new TeBlock(TileEntityFluidDistributor.class, 29, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fluid_regulator = new TeBlock(TileEntityFluidRegulator.class, 30, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock liquid_heat_exchanger = new TeBlock(TileEntityLiquidHeatExchanger.class, 31, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock pump = new TeBlock(TileEntityPump.class, 32, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock solar_distiller = new TeBlock(TileEntitySolarDestiller.class, 33, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock steam_generator = new TeBlock(TileEntitySteamGenerator.class, 34, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock item_buffer = new TeBlock(TileEntityItemBuffer.class, 35, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock luminator_flat = new TeBlock(TileEntityLuminator.class, 36, true, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0f, 10.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock magnetizer = new TeBlock(TileEntityMagnetizer.class, 37, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock sorting_machine = new TeBlock(TileEntitySortingMachine.class, 38, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock teleporter = new TeBlock(TileEntityTeleporter.class, 39, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.RARE, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock terraformer = new TeBlock(TileEntityTerra.class, 40, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock tesla_coil = new TeBlock(TileEntityTesla.class, 41, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock canner = new TeBlock(TileEntityCanner.delegate(), 42, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock compressor = new TeBlock(TileEntityCompressor.class, 43, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock electric_furnace = new TeBlock(TileEntityElectricFurnace.class, 44, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock extractor = new TeBlock(TileEntityExtractor.class, 45, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock iron_furnace = new TeBlock(TileEntityIronFurnace.class, 46, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0f, 10.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock macerator = new TeBlock(TileEntityMacerator.class, 47, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock recycler = new TeBlock(TileEntityRecycler.class, 48, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock solid_canner = new TeBlock(TileEntitySolidCanner.class, 49, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock blast_furnace = new TeBlock(TileEntityBlastFurnace.class, 50, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock block_cutter = new TeBlock(TileEntityBlockCutter.class, 51, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock centrifuge = new TeBlock(TileEntityCentrifuge.class, 52, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fermenter = new TeBlock(TileEntityFermenter.class, 53, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock induction_furnace = new TeBlock(TileEntityInduction.class, 54, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock metal_former = new TeBlock(TileEntityMetalFormer.class, 55, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock ore_washing_plant = new TeBlock(TileEntityOreWashing.class, 56, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock advanced_miner = new TeBlock(TileEntityAdvMiner.class, 57, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock crop_harvester = new TeBlock(TileEntityCropHarvester.class, 58, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock cropmatron = new TeBlock(TileEntityCropmatron.delegate(), 59, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock miner = new TeBlock(TileEntityMiner.class, 60, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock mass_fabricator = new TeBlock(TileEntityMassFabricator.delegate(), 92, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.RARE, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock uu_assembly_bench = new TeBlock(TileEntityAssemblyBench.class, 93, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock matter_generator = new TeBlock(TileEntityMatter.class, 61, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.RARE, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock pattern_storage = new TeBlock(TileEntityPatternStorage.class, 62, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock replicator = new TeBlock(TileEntityReplicator.class, 63, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock scanner = new TeBlock(TileEntityScanner.class, 64, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock energy_o_mat = new TeBlock(TileEntityEnergyOMat.class, 65, false, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0f, 3600000.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock personal_chest = new TeBlock(TileEntityPersonalChest.class, 66, false, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0f, 3600000.0f, EnumRarity.UNCOMMON);
    public static final /* enum */ TeBlock trade_o_mat = new TeBlock(TileEntityTradeOMat.class, 67, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0f, 3600000.0f, EnumRarity.COMMON);
    public static final /* enum */ TeBlock trading_terminal = new TeBlock(TileEntityTradingTerminal.class, 94, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock cable = new TeBlock(TileEntityCable.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5f, 5.0f, EnumRarity.COMMON, Material.field_151580_n, true);
    public static final /* enum */ TeBlock detector_cable = new TeBlock(TileEntityCableDetector.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5f, 5.0f, EnumRarity.COMMON, Material.field_151580_n, false);
    public static final /* enum */ TeBlock splitter_cable = new TeBlock(TileEntityCableSplitter.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5f, 5.0f, EnumRarity.COMMON, Material.field_151580_n, false);
    public static final /* enum */ TeBlock chargepad_batbox = new TeBlock(TileEntityChargepadBatBox.class, 68, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock chargepad_cesu = new TeBlock(TileEntityChargepadCESU.class, 69, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock chargepad_mfe = new TeBlock(TileEntityChargepadMFE.class, 70, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock chargepad_mfsu = new TeBlock(TileEntityChargepadMFSU.class, 71, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock batbox = new TeBlock(TileEntityElectricBatBox.class, 72, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock cesu = new TeBlock(TileEntityElectricCESU.class, 73, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock mfe = new TeBlock(TileEntityElectricMFE.delegate(), 74, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock mfsu = new TeBlock(TileEntityElectricMFSU.delegate(), 75, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock electrolyzer = new TeBlock(TileEntityElectrolyzer.delegate(), 76, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock lv_transformer = new TeBlock(TileEntityTransformerLV.class, 77, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock mv_transformer = new TeBlock(TileEntityTransformerMV.class, 78, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock hv_transformer = new TeBlock(TileEntityTransformerHV.class, 79, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock ev_transformer = new TeBlock(TileEntityTransformerEV.class, 80, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock tank = new TeBlock(TileEntityTank.class, 81, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock chunk_loader = new TeBlock(TileEntityChunkloader.class, 82, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock item_buffer_2 = new TeBlock(TileEntityBetterItemBuffer.class, 83, false, Util.noFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock creative_generator = new TeBlock(TileEntityCreativeGenerator.class, 86, true, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, EnumRarity.COMMON);
    public static final /* enum */ TeBlock steam_repressurizer = new TeBlock(TileEntitySteamRepressurizer.class, 87, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock weighted_fluid_distributor = new TeBlock(TileEntityWeightedFluidDistributor.class, 90, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock weighted_item_distributor = new TeBlock(TileEntityWeightedItemDistributor.class, 91, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock rci_rsh = new TeBlock(TileEntityRCI_RSH.class, 84, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock rci_lzh = new TeBlock(TileEntityRCI_LZH.class, 85, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock crop = new TeBlock(TileEntityCrop.class, -1, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 0.8f, 0.2f, EnumRarity.COMMON, Material.field_151585_k, true);
    public static final /* enum */ TeBlock industrial_workbench = new TeBlock(TileEntityIndustrialWorkbench.class, 88, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock batch_crafter = new TeBlock(TileEntityBatchCrafter.class, 89, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0f, 10.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock fluid_pipe = new TeBlock(TileEntityFluidPipe.class, -1, false, Util.allFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0f, 5.0f, EnumRarity.COMMON, IC2Material.PIPE, true);
    public static final /* enum */ TeBlock coke_kiln = new TeBlock(TileEntityCokeKiln.class, 100, true, Util.horizontalFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, Material.field_151576_e, false);
    public static final /* enum */ TeBlock coke_kiln_hatch = new TeBlock(TileEntityCokeKilnHatch.class, 101, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, Material.field_151576_e, false);
    public static final /* enum */ TeBlock coke_kiln_grate = new TeBlock(TileEntityCokeKilnGrate.class, 102, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0f, 10.0f, EnumRarity.COMMON, Material.field_151576_e, false);
    public static final /* enum */ TeBlock wooden_storage_box = new TeBlock(TileEntityWoodenStorageBox.class, 111, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 1.0f, 10.0f, EnumRarity.COMMON, Material.field_151575_d, false);
    public static final /* enum */ TeBlock iron_storage_box = new TeBlock(TileEntityIronStorageBox.class, 112, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0f, 15.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock bronze_storage_box = new TeBlock(TileEntityBronzeStorageBox.class, 113, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0f, 15.0f, EnumRarity.COMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock steel_storage_box = new TeBlock(TileEntitySteelStorageBox.class, 114, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 2.0f, 20.0f, EnumRarity.UNCOMMON, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock iridium_storage_box = new TeBlock(TileEntityIridiumStorageBox.class, 115, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0f, 100.0f, EnumRarity.EPIC, IC2Material.MACHINE, false);
    public static final /* enum */ TeBlock bronze_tank = new TeBlock(TileEntityBronzeTank.class, 131, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0f, 15.0f, EnumRarity.COMMON, IC2Material.MACHINE, true);
    public static final /* enum */ TeBlock iron_tank = new TeBlock(TileEntityIronTank.class, 132, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0f, 15.0f, EnumRarity.COMMON, IC2Material.MACHINE, true);
    public static final /* enum */ TeBlock steel_tank = new TeBlock(TileEntitySteelTank.class, 133, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 4.0f, 20.0f, EnumRarity.COMMON, IC2Material.MACHINE, true);
    public static final /* enum */ TeBlock iridium_tank = new TeBlock(TileEntityIridiumTank.class, 134, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 5.0f, 100.0f, EnumRarity.COMMON, IC2Material.MACHINE, true);
    private static final ResourceLocation loc;
    private final Class<? extends TileEntityBlock> teClass;
    private final int itemMeta;
    private final boolean hasActive;
    private final Set<EnumFacing> supportedFacings;
    private final boolean allowWrenchRotating;
    private final HarvestTool harvestTool;
    private final DefaultDrop defaultDrop;
    private final float hardness;
    private final float explosionResistance;
    private final EnumRarity rarity;
    private final Material material;
    private final boolean transparent;
    private TileEntityBlock dummyTe;
    private ITePlaceHandler placeHandler;
    public static final TeBlock[] values;
    private static final String teIdPrefix = "ic2:";
    private static final String teClassicPrefix = "Old";
    private static final /* synthetic */ TeBlock[] $VALUES;

    public static TeBlock[] values() {
        return (TeBlock[])$VALUES.clone();
    }

    public static TeBlock valueOf(String name) {
        return Enum.valueOf(TeBlock.class, name);
    }

    private TeBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity) {
        this(teClass, itemMeta, hasActive, supportedFacings, allowWrenchRotating, harvestTool, defaultDrop, hardness, explosionResistance, rarity, Material.field_151573_f, false);
    }

    private TeBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material, boolean transparent) {
        this.teClass = teClass;
        this.itemMeta = itemMeta;
        this.hasActive = hasActive;
        this.supportedFacings = supportedFacings;
        this.allowWrenchRotating = allowWrenchRotating;
        this.harvestTool = harvestTool;
        this.defaultDrop = defaultDrop;
        this.hardness = hardness;
        this.explosionResistance = explosionResistance;
        this.rarity = rarity;
        this.material = material;
        this.transparent = transparent;
    }

    @Override
    public boolean hasItem() {
        return this.teClass != null && this.itemMeta != -1;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return loc;
    }

    @Override
    public Class<? extends TileEntityBlock> getTeClass() {
        return this.teClass;
    }

    @Override
    public boolean hasActive() {
        return this.hasActive;
    }

    @Override
    public int getId() {
        return this.itemMeta;
    }

    @Override
    public float getHardness() {
        return this.hardness;
    }

    @Override
    public HarvestTool getHarvestTool() {
        return this.harvestTool;
    }

    @Override
    public DefaultDrop getDefaultDrop() {
        return this.defaultDrop;
    }

    @Override
    public float getExplosionResistance() {
        return this.explosionResistance;
    }

    @Override
    public boolean allowWrenchRotating() {
        return this.allowWrenchRotating;
    }

    @Override
    public Set<EnumFacing> getSupportedFacings() {
        return this.supportedFacings;
    }

    @Override
    public EnumRarity getRarity() {
        return this.rarity;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public boolean isTransparent() {
        return this.transparent;
    }

    @Override
    public void addSubBlocks(NonNullList<ItemStack> list, BlockTileEntity block, ItemBlockTileEntity item, CreativeTabs tab) {
        if (tab == IC2.tabIC2 || tab == CreativeTabs.field_78027_g) {
            for (TeBlock type : values) {
                if (!type.hasItem() || !Version.shouldEnable(type.teClass)) continue;
                list.add((Object)block.getItemStack(type));
                if (!(type.getDummyTe() instanceof IEnergyStorage)) continue;
                ItemStack filled = block.getItemStack(type);
                StackUtil.getOrCreateNbtData(filled).func_74780_a("energy", (double)((IEnergyStorage)((Object)type.getDummyTe())).getCapacity());
                list.add((Object)filled);
            }
        }
    }

    @Override
    public void setPlaceHandler(ITePlaceHandler handler) {
        if (this.placeHandler != null) {
            throw new RuntimeException("duplicate place handler");
        }
        this.placeHandler = handler;
    }

    @Override
    public ITePlaceHandler getPlaceHandler() {
        return this.placeHandler;
    }

    public static void buildDummies() {
        ModContainer mc = Loader.instance().activeModContainer();
        if (mc == null || !"ic2".equals(mc.getModId())) {
            throw new IllegalAccessError("Don't mess with this please.");
        }
        for (TeBlock block : values) {
            if (block.teClass == null) continue;
            try {
                block.dummyTe = block.teClass.newInstance();
            }
            catch (Exception e) {
                if (!Util.inDev()) continue;
                e.printStackTrace();
            }
        }
    }

    @Override
    @Deprecated
    public TileEntityBlock getDummyTe() {
        return this.dummyTe;
    }

    public static void registerTeMappings() {
        for (TeBlock block : values) {
            if (block.teClass == null) continue;
            if (block.teClass.isAnnotationPresent(Delegated.class)) {
                Delegated delegation = block.teClass.getAnnotation(Delegated.class);
                assert (delegation != null);
                GameRegistry.registerTileEntity(delegation.current(), (String)(teIdPrefix + block.getName()));
                GameRegistry.registerTileEntity(delegation.old(), (String)("ic2:Old" + block.getName()));
                TeBlockRegistry.ensureMapping(block, delegation.current());
                TeBlockRegistry.ensureMapping(block, delegation.old());
                continue;
            }
            GameRegistry.registerTileEntity(block.teClass, (String)(teIdPrefix + block.getName()));
        }
    }

    static {
        $VALUES = new TeBlock[]{invalid, barrel, wall, itnt, nuke, generator, geo_generator, kinetic_generator, rt_generator, semifluid_generator, solar_generator, stirling_generator, water_generator, wind_generator, electric_heat_generator, fluid_heat_generator, rt_heat_generator, solid_heat_generator, electric_kinetic_generator, manual_kinetic_generator, steam_kinetic_generator, stirling_kinetic_generator, water_kinetic_generator, wind_kinetic_generator, nuclear_reactor, reactor_access_hatch, reactor_chamber, reactor_fluid_port, reactor_redstone_port, condenser, fluid_bottler, fluid_distributor, fluid_regulator, liquid_heat_exchanger, pump, solar_distiller, steam_generator, item_buffer, luminator_flat, magnetizer, sorting_machine, teleporter, terraformer, tesla_coil, canner, compressor, electric_furnace, extractor, iron_furnace, macerator, recycler, solid_canner, blast_furnace, block_cutter, centrifuge, fermenter, induction_furnace, metal_former, ore_washing_plant, advanced_miner, crop_harvester, cropmatron, miner, mass_fabricator, uu_assembly_bench, matter_generator, pattern_storage, replicator, scanner, energy_o_mat, personal_chest, trade_o_mat, trading_terminal, cable, detector_cable, splitter_cable, chargepad_batbox, chargepad_cesu, chargepad_mfe, chargepad_mfsu, batbox, cesu, mfe, mfsu, electrolyzer, lv_transformer, mv_transformer, hv_transformer, ev_transformer, tank, chunk_loader, item_buffer_2, creative_generator, steam_repressurizer, weighted_fluid_distributor, weighted_item_distributor, rci_rsh, rci_lzh, crop, industrial_workbench, batch_crafter, fluid_pipe, coke_kiln, coke_kiln_hatch, coke_kiln_grate, wooden_storage_box, iron_storage_box, bronze_storage_box, steel_storage_box, iridium_storage_box, bronze_tank, iron_tank, steel_tank, iridium_tank};
        loc = new ResourceLocation("ic2", "te");
        values = TeBlock.values();
    }

    public static interface ITePlaceHandler {
        public boolean canReplace(World var1, BlockPos var2, EnumFacing var3, ItemStack var4);
    }

    public static enum DefaultDrop {
        Self,
        None,
        Generator,
        Machine,
        AdvMachine;

    }

    public static enum HarvestTool {
        None(null, -1),
        Pickaxe("pickaxe", 0),
        Shovel("shovel", 0),
        Axe("axe", 0),
        Wrench("wrench", 0);

        public final String toolClass;
        public final int level;

        private HarvestTool(String toolClass, int level) {
            this.toolClass = toolClass;
            this.level = level;
        }
    }

    @Inherited
    @Documented
    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Delegated {
        public Class<? extends TileEntityBlock> old();

        public Class<? extends TileEntityBlock> current();
    }
}

