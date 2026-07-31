/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.ref;

import ic2.api.network.IGrowingBuffer;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.heatgenerator.container.ContainerElectricHeatGenerator;
import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
import ic2.core.block.kineticgenerator.container.ContainerElectricKineticGenerator;
import ic2.core.block.kineticgenerator.container.ContainerSteamKineticGenerator;
import ic2.core.block.kineticgenerator.container.ContainerStirlingKineticGenerator;
import ic2.core.block.kineticgenerator.container.ContainerWaterKineticGenerator;
import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
import ic2.core.block.machine.container.ContainerAdvMiner;
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.container.ContainerCanner;
import ic2.core.block.machine.container.ContainerChunkLoader;
import ic2.core.block.machine.container.ContainerClassicCanner;
import ic2.core.block.machine.container.ContainerClassicCropmatron;
import ic2.core.block.machine.container.ContainerCondenser;
import ic2.core.block.machine.container.ContainerCropHarvester;
import ic2.core.block.machine.container.ContainerCropmatron;
import ic2.core.block.machine.container.ContainerElectrolyzer;
import ic2.core.block.machine.container.ContainerFermenter;
import ic2.core.block.machine.container.ContainerFluidBottler;
import ic2.core.block.machine.container.ContainerFluidDistributor;
import ic2.core.block.machine.container.ContainerFluidRegulator;
import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
import ic2.core.block.machine.container.ContainerItemBuffer;
import ic2.core.block.machine.container.ContainerLiquidHeatExchanger;
import ic2.core.block.machine.container.ContainerMagnetizer;
import ic2.core.block.machine.container.ContainerMatter;
import ic2.core.block.machine.container.ContainerMetalFormer;
import ic2.core.block.machine.container.ContainerMiner;
import ic2.core.block.machine.container.ContainerPatternStorage;
import ic2.core.block.machine.container.ContainerReplicator;
import ic2.core.block.machine.container.ContainerScanner;
import ic2.core.block.machine.container.ContainerSolarDestiller;
import ic2.core.block.machine.container.ContainerSortingMachine;
import ic2.core.block.machine.container.ContainerSteamGenerator;
import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
import ic2.core.block.personal.ContainerEnergyOMatClosed;
import ic2.core.block.personal.ContainerEnergyOMatOpen;
import ic2.core.block.personal.ContainerTradeOMatClosed;
import ic2.core.block.personal.ContainerTradeOMatOpen;
import ic2.core.block.reactor.container.ContainerNuclearReactor;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.block.wiring.ContainerChargepadBlock;
import ic2.core.block.wiring.ContainerElectricBlock;
import ic2.core.block.wiring.ContainerTransformer;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.IHandHeldSubInventory;
import ic2.core.item.tool.ContainerToolScanner;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.HandHeldOre;
import ic2.core.item.upgrade.HandHeldValueConfig;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.proxy.EnvProxy;
import ic2.core.util.Util;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.function.BiFunction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class Ic2ScreenHandlers {
    private static final EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>> MANAGED_CLIENT_BE_HANDLER = Ic2ScreenHandlers.createManagedBeClientHandler();
    private static final EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>> MANAGED_CLIENT_ITEM_HANDLER = Ic2ScreenHandlers.createManagedItemClientHandler();
    public static final MenuType<DynamicContainer<TileEntityInventory>> DYNAMIC_BE = Ic2ScreenHandlers.registerManagedBe("dynamic_be");
    public static final MenuType<DynamicContainer<HandHeldInventory>> DYNAMIC_ITEM = Ic2ScreenHandlers.registerManagedItem("dynamic_item");
    public static final MenuType<ContainerElectricHeatGenerator> ELECTRIC_HEAT_GENERATOR = Ic2ScreenHandlers.registerManagedBe("electric_heat_generator");
    public static final MenuType<ContainerFluidHeatGenerator> FLUID_HEAT_GENERATOR = Ic2ScreenHandlers.registerManagedBe("fluid_heat_generator");
    public static final MenuType<ContainerRTHeatGenerator> RT_HEAT_GENERATOR = Ic2ScreenHandlers.registerManagedBe("rt_heat_generator");
    public static final MenuType<ContainerElectricKineticGenerator> ELECTRIC_KINETIC_GENERATOR = Ic2ScreenHandlers.registerManagedBe("electric_kinetic_generator");
    public static final MenuType<ContainerSteamKineticGenerator> STEAM_KINETIC_GENERATOR = Ic2ScreenHandlers.registerManagedBe("steam_kinetic_generator");
    public static final MenuType<ContainerStirlingKineticGenerator> STIRLING_KINETIC_GENERATOR = Ic2ScreenHandlers.registerManagedBe("stirling_kinetic_generator");
    public static final MenuType<ContainerWaterKineticGenerator> WATER_KINETIC_GENERATOR = Ic2ScreenHandlers.registerManagedBe("water_kinetic_generator");
    public static final MenuType<ContainerWindKineticGenerator> WIND_KINETIC_GENERATOR = Ic2ScreenHandlers.registerManagedBe("wind_kinetic_generator");
    public static final MenuType<ContainerNuclearReactor> NUCLEAR_REACTOR = Ic2ScreenHandlers.registerManagedBe("nuclear_reactor");
    public static final MenuType<ContainerCondenser> CONDENSER = Ic2ScreenHandlers.registerManagedBe("condenser");
    public static final MenuType<ContainerFluidBottler> FLUID_BOTTLER = Ic2ScreenHandlers.registerManagedBe("fluid_bottler");
    public static final MenuType<ContainerFluidDistributor> FLUID_DISTRIBUTOR = Ic2ScreenHandlers.registerManagedBe("fluid_distributor");
    public static final MenuType<ContainerFluidRegulator> FLUID_REGULATOR = Ic2ScreenHandlers.registerManagedBe("fluid_regulator");
    public static final MenuType<ContainerLiquidHeatExchanger> LIQUID_HEAT_EXCHANGER = Ic2ScreenHandlers.registerManagedBe("liquid_heat_exchanger");
    public static final MenuType<ContainerSolarDestiller> SOLAR_DISTILLER = Ic2ScreenHandlers.registerManagedBe("solar_distiller");
    public static final MenuType<ContainerSteamGenerator> STEAM_GENERATOR = Ic2ScreenHandlers.registerManagedBe("steam_generator");
    public static final MenuType<ContainerItemBuffer> ITEM_BUFFER = Ic2ScreenHandlers.registerManagedBe("item_buffer");
    public static final MenuType<ContainerMagnetizer> MAGNETIZER = Ic2ScreenHandlers.registerManagedBe("magnetizer");
    public static final MenuType<ContainerSortingMachine> SORTING_MACHINE = Ic2ScreenHandlers.registerManagedBe("sorting_machine");
    public static final MenuType<ContainerCanner> CANNER = Ic2ScreenHandlers.registerManagedBe("canner");
    public static final MenuType<ContainerClassicCanner> CLASSIC_CANNER = Ic2ScreenHandlers.registerManagedBe("classic_canner");
    public static final MenuType<ContainerFermenter> FERMENTER = Ic2ScreenHandlers.registerManagedBe("fermenter");
    public static final MenuType<ContainerMetalFormer> METAL_FORMER = Ic2ScreenHandlers.registerManagedBe("metal_former");
    public static final MenuType<ContainerAdvMiner> ADVANCED_MINER = Ic2ScreenHandlers.registerManagedBe("advanced_miner");
    public static final MenuType<ContainerCropHarvester> CROP_HARVESTER = Ic2ScreenHandlers.registerManagedBe("crop_harvester");
    public static final MenuType<ContainerCropmatron> CROPMATRON = Ic2ScreenHandlers.registerManagedBe("cropmatron");
    public static final MenuType<ContainerClassicCropmatron> CLASSIC_CROPMATRON = Ic2ScreenHandlers.registerManagedBe("classic_cropmatron");
    public static final MenuType<ContainerMiner> MINER = Ic2ScreenHandlers.registerManagedBe("miner");
    public static final MenuType<ContainerMatter> MATTER_GENERATOR = Ic2ScreenHandlers.registerManagedBe("matter_generator");
    public static final MenuType<ContainerPatternStorage> PATTERN_STORAGE = Ic2ScreenHandlers.registerManagedBe("pattern_storage");
    public static final MenuType<ContainerReplicator> REPLICATOR = Ic2ScreenHandlers.registerManagedBe("replicator");
    public static final MenuType<ContainerScanner> UU_SCANNER = Ic2ScreenHandlers.registerManagedBe("uu_scanner");
    public static final MenuType<ContainerEnergyOMatClosed> ENERGY_O_MAT_CLOSED = Ic2ScreenHandlers.registerManagedBe("energy_o_mat_closed");
    public static final MenuType<ContainerEnergyOMatOpen> ENERGY_O_MAT_OPEN = Ic2ScreenHandlers.registerManagedBe("energy_o_mat_open");
    public static final MenuType<ContainerTradeOMatClosed> TRADE_O_MAT_CLOSED = Ic2ScreenHandlers.registerManagedBe("trade_o_mat_closed");
    public static final MenuType<ContainerTradeOMatOpen> TRADE_O_MAT_OPEN = Ic2ScreenHandlers.registerManagedBe("trade_o_mat_open");
    public static final MenuType<ContainerChargepadBlock> CHARGEPAD = Ic2ScreenHandlers.registerManagedBe("chargepad");
    public static final MenuType<ContainerElectricBlock> ENERGY_STORAGE = Ic2ScreenHandlers.registerManagedBe("energy_storage");
    public static final MenuType<ContainerElectrolyzer> ELECTROLYZER = Ic2ScreenHandlers.registerManagedBe("electrolyzer");
    public static final MenuType<ContainerTransformer> TRANSFORMER = Ic2ScreenHandlers.registerManagedBe("transformer");
    public static final MenuType<ContainerChunkLoader> CHUNK_LOADER = Ic2ScreenHandlers.registerManagedBe("chunk_loader");
    public static final MenuType<ContainerWeightedFluidDistributor> WEIGHTED_FLUID_DISTRIBUTOR = Ic2ScreenHandlers.registerManagedBe("weighted_fluid_distributor");
    public static final MenuType<ContainerWeightedItemDistributor> WEIGHTED_ITEM_DISTRIBUTOR = Ic2ScreenHandlers.registerManagedBe("weighted_item_distributor");
    public static final MenuType<ContainerIndustrialWorkbench> INDUSTRIAL_WORKBENCH = Ic2ScreenHandlers.registerManagedBe("industrial_workbench");
    public static final MenuType<ContainerBatchCrafter> BATCH_CRAFTER = Ic2ScreenHandlers.registerManagedBe("batch_crafter");
    public static final MenuType<DynamicContainer<HandHeldAdvancedUpgrade>> ADVANCED_UPGRADE = Ic2ScreenHandlers.registerManagedItem("advanced_upgrade");
    public static final MenuType<HandHeldOre.ContainerEditOre> ADVANCED_UPGRADE_EDIT_ORE = Ic2ScreenHandlers.registerManagedItem("advanced_upgrade/edit_ore");
    public static final MenuType<HandHeldValueConfig.ContainerValueConfig> ADVANCED_UPGRADE_VALUE_CONFIG = Ic2ScreenHandlers.registerManagedItem("advanced_upgrade/value_config");
    public static final MenuType<ContainerToolScanner> SCANNER = Ic2ScreenHandlers.registerManagedItem("scanner");

    public static void init() {
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String string, BiFunction<Integer, Inventory, T> biFunction) {
        return IC2.envProxy.registerScreenHandler(IC2.getIdentifier(string), biFunction);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> registerExtended(String string, EnvProxy.ExtendedClientScreenHandlerFactory<T> extendedClientScreenHandlerFactory) {
        return IC2.envProxy.registerExtendedScreenHandler(IC2.getIdentifier(string), extendedClientScreenHandlerFactory);
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerManagedBe(String string) {
        return Ic2ScreenHandlers.registerExtended(string, MANAGED_CLIENT_BE_HANDLER);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> registerManagedItem(String string) {
        return Ic2ScreenHandlers.registerExtended(string, MANAGED_CLIENT_ITEM_HANDLER);
    }

    private static EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>> createManagedBeClientHandler() {
        return new EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>>(){

            @Override
            public ContainerBase<?> create(int n, Inventory inventory, ByteBuf byteBuf) {
                GrowingBuffer growingBuffer = GrowingBuffer.wrap(byteBuf);
                try {
                    BlockEntity blockEntity = (BlockEntity)DataEncoder.getValue(DataEncoder.decode((IGrowingBuffer)growingBuffer, DataEncoder.EncodedType.TileEntity), null);
                    if (!(blockEntity instanceof IHasGui)) {
                        return null;
                    }
                    IHasGui iHasGui = (IHasGui)blockEntity;
                    return iHasGui.createClientScreenHandler(n, inventory, growingBuffer);
                }
                catch (IOException iOException) {
                    throw new RuntimeException(iOException);
                }
            }
        };
    }

    public static void writeManagedBeData(BlockEntity blockEntity, GrowingBuffer growingBuffer) throws IOException {
        DataEncoder.encode(growingBuffer, blockEntity, false);
    }

    private static EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>> createManagedItemClientHandler() {
        return new EnvProxy.ExtendedClientScreenHandlerFactory<ContainerBase<?>>(){

            @Override
            public ContainerBase<?> create(int n, Inventory inventory, ByteBuf byteBuf) {
                ItemStack itemStack;
                InteractionHand interactionHand;
                GrowingBuffer growingBuffer = GrowingBuffer.wrap(byteBuf);
                int n2 = growingBuffer.readInt();
                boolean bl = growingBuffer.readBoolean();
                int n3 = bl ? growingBuffer.readVarInt() : 0;
                Player player = IC2.sideProxy.getPlayerInstance();
                if (n2 < 0) {
                    int n4 = -n2;
                    if (n4 >= Util.HANDS.length) {
                        return null;
                    }
                    interactionHand = Util.HANDS[n4];
                    itemStack = player.m_21120_(interactionHand);
                } else {
                    if (n2 != player.m_150109_().f_35977_) {
                        return null;
                    }
                    interactionHand = InteractionHand.MAIN_HAND;
                    itemStack = player.m_150109_().m_36056_();
                }
                if (itemStack == null) {
                    return null;
                }
                Item item = itemStack.m_41720_();
                if (!(item instanceof IHandHeldInventory)) {
                    return null;
                }
                IHandHeldInventory iHandHeldInventory = (IHandHeldInventory)item;
                IHasGui iHasGui = bl && iHandHeldInventory instanceof IHandHeldSubInventory ? ((IHandHeldSubInventory)iHandHeldInventory).getSubInventory(player, interactionHand, itemStack, n3) : iHandHeldInventory.getInventory(player, interactionHand, itemStack);
                return iHasGui.createClientScreenHandler(n, inventory, growingBuffer);
            }
        };
    }

    public static void writeManagedItemData(Player player, InteractionHand interactionHand, Integer n, GrowingBuffer growingBuffer) {
        Item item = player.m_21120_(interactionHand).m_41720_();
        assert (item instanceof IHandHeldInventory);
        int n2 = interactionHand == InteractionHand.MAIN_HAND ? player.m_150109_().f_35977_ : -interactionHand.ordinal();
        growingBuffer.writeInt(n2);
        boolean bl = n != null && item instanceof IHandHeldSubInventory;
        growingBuffer.writeBoolean(bl);
        if (bl) {
            growingBuffer.writeVarInt(n);
        }
    }
}

