/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.SignRenderer
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.proxy;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioManagerClient;
import ic2.core.audio.PositionSpec;
import ic2.core.block.heatgenerator.gui.GuiElectricHeatGenerator;
import ic2.core.block.heatgenerator.gui.GuiFluidHeatGenerator;
import ic2.core.block.heatgenerator.gui.GuiRTHeatGenerator;
import ic2.core.block.kineticgenerator.gui.GuiElectricKineticGenertor;
import ic2.core.block.kineticgenerator.gui.GuiSteamKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiStirlingKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiWaterKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator;
import ic2.core.block.machine.gui.GuiAdvMiner;
import ic2.core.block.machine.gui.GuiBatchCrafter;
import ic2.core.block.machine.gui.GuiCanner;
import ic2.core.block.machine.gui.GuiChunkLoader;
import ic2.core.block.machine.gui.GuiClassicCanner;
import ic2.core.block.machine.gui.GuiClassicCropmatron;
import ic2.core.block.machine.gui.GuiCondenser;
import ic2.core.block.machine.gui.GuiCropHarvester;
import ic2.core.block.machine.gui.GuiCropmatron;
import ic2.core.block.machine.gui.GuiElectrolyzer;
import ic2.core.block.machine.gui.GuiFermenter;
import ic2.core.block.machine.gui.GuiFluidBottler;
import ic2.core.block.machine.gui.GuiFluidDistributor;
import ic2.core.block.machine.gui.GuiFluidRegulator;
import ic2.core.block.machine.gui.GuiIndustrialWorkbench;
import ic2.core.block.machine.gui.GuiItemBuffer;
import ic2.core.block.machine.gui.GuiLiquidHeatExchanger;
import ic2.core.block.machine.gui.GuiMagnetizer;
import ic2.core.block.machine.gui.GuiMatter;
import ic2.core.block.machine.gui.GuiMetalFormer;
import ic2.core.block.machine.gui.GuiMiner;
import ic2.core.block.machine.gui.GuiPatternStorage;
import ic2.core.block.machine.gui.GuiReplicator;
import ic2.core.block.machine.gui.GuiScanner;
import ic2.core.block.machine.gui.GuiSolarDestiller;
import ic2.core.block.machine.gui.GuiSortingMachine;
import ic2.core.block.machine.gui.GuiSteamGenerator;
import ic2.core.block.machine.gui.GuiWeightedFluidDistributor;
import ic2.core.block.machine.gui.GuiWeightedItemDistributor;
import ic2.core.block.personal.GuiEnergyOMatClosed;
import ic2.core.block.personal.GuiEnergyOMatOpen;
import ic2.core.block.personal.GuiTradeOMatClosed;
import ic2.core.block.personal.GuiTradeOMatOpen;
import ic2.core.block.reactor.gui.GuiNuclearReactor;
import ic2.core.block.renderer.KineticGeneratorRenderer;
import ic2.core.block.wiring.GuiChargepadBlock;
import ic2.core.block.wiring.GuiElectricBlock;
import ic2.core.block.wiring.GuiTransformer;
import ic2.core.entity.render.BoatEntityRenderer;
import ic2.core.entity.render.ExplosiveBlockRenderer;
import ic2.core.entity.render.LaserBulletEntityRenderer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.item.tool.GuiToolScanner;
import ic2.core.item.upgrade.AdvancedUpgradeScreenFactory;
import ic2.core.item.upgrade.HandHeldOre;
import ic2.core.item.upgrade.HandHeldValueConfig;
import ic2.core.proxy.ClientEnvProxy;
import ic2.core.proxy.SideProxy;
import ic2.core.proxy.SideProxyServer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Entities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.sound.SoundManager;
import ic2.core.sound.SoundManagerClient;
import ic2.core.util.Keyboard;
import ic2.core.util.KeyboardClient;
import ic2.core.util.Util;
import java.io.File;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public final class SideProxyClient
implements SideProxy {
    public static final ClientEnvProxy envProxy = SideProxyClient.getEnvProxy();
    public static final Minecraft mc = Minecraft.m_91087_();
    private static final AudioManager audioManager = new AudioManagerClient();
    private static final SoundManager soundManager = new SoundManagerClient();
    private static final Keyboard keyboard = new KeyboardClient();

    @Override
    public void preInit() {
        envProxy.registerScreen(Ic2ScreenHandlers.DYNAMIC_BE, DynamicGui::create);
        envProxy.registerScreen(Ic2ScreenHandlers.DYNAMIC_ITEM, DynamicGui::create);
        envProxy.registerScreen(Ic2ScreenHandlers.ELECTRIC_HEAT_GENERATOR, GuiElectricHeatGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.FLUID_HEAT_GENERATOR, GuiFluidHeatGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.RT_HEAT_GENERATOR, GuiRTHeatGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ELECTRIC_KINETIC_GENERATOR, GuiElectricKineticGenertor::new);
        envProxy.registerScreen(Ic2ScreenHandlers.STEAM_KINETIC_GENERATOR, GuiSteamKineticGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.STIRLING_KINETIC_GENERATOR, GuiStirlingKineticGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.WATER_KINETIC_GENERATOR, GuiWaterKineticGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.WIND_KINETIC_GENERATOR, GuiWindKineticGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.NUCLEAR_REACTOR, GuiNuclearReactor::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CONDENSER, GuiCondenser::new);
        envProxy.registerScreen(Ic2ScreenHandlers.FLUID_BOTTLER, GuiFluidBottler::new);
        envProxy.registerScreen(Ic2ScreenHandlers.FLUID_DISTRIBUTOR, GuiFluidDistributor::new);
        envProxy.registerScreen(Ic2ScreenHandlers.FLUID_REGULATOR, GuiFluidRegulator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.LIQUID_HEAT_EXCHANGER, GuiLiquidHeatExchanger::new);
        envProxy.registerScreen(Ic2ScreenHandlers.SOLAR_DISTILLER, GuiSolarDestiller::new);
        envProxy.registerScreen(Ic2ScreenHandlers.STEAM_GENERATOR, GuiSteamGenerator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ITEM_BUFFER, GuiItemBuffer::new);
        envProxy.registerScreen(Ic2ScreenHandlers.MAGNETIZER, GuiMagnetizer::new);
        envProxy.registerScreen(Ic2ScreenHandlers.SORTING_MACHINE, GuiSortingMachine::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CANNER, GuiCanner::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CLASSIC_CANNER, GuiClassicCanner::new);
        envProxy.registerScreen(Ic2ScreenHandlers.FERMENTER, GuiFermenter::new);
        envProxy.registerScreen(Ic2ScreenHandlers.METAL_FORMER, GuiMetalFormer::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ADVANCED_MINER, GuiAdvMiner::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CROP_HARVESTER, GuiCropHarvester::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CROPMATRON, GuiCropmatron::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CLASSIC_CROPMATRON, GuiClassicCropmatron::new);
        envProxy.registerScreen(Ic2ScreenHandlers.MINER, GuiMiner::new);
        envProxy.registerScreen(Ic2ScreenHandlers.MATTER_GENERATOR, GuiMatter::new);
        envProxy.registerScreen(Ic2ScreenHandlers.PATTERN_STORAGE, GuiPatternStorage::new);
        envProxy.registerScreen(Ic2ScreenHandlers.REPLICATOR, GuiReplicator::new);
        envProxy.registerScreen(Ic2ScreenHandlers.UU_SCANNER, GuiScanner::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ENERGY_O_MAT_CLOSED, GuiEnergyOMatClosed::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ENERGY_O_MAT_OPEN, GuiEnergyOMatOpen::new);
        envProxy.registerScreen(Ic2ScreenHandlers.TRADE_O_MAT_CLOSED, GuiTradeOMatClosed::new);
        envProxy.registerScreen(Ic2ScreenHandlers.TRADE_O_MAT_OPEN, GuiTradeOMatOpen::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CHARGEPAD, GuiChargepadBlock::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ENERGY_STORAGE, GuiElectricBlock::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ELECTROLYZER, GuiElectrolyzer::new);
        envProxy.registerScreen(Ic2ScreenHandlers.TRANSFORMER, GuiTransformer::new);
        envProxy.registerScreen(Ic2ScreenHandlers.CHUNK_LOADER, GuiChunkLoader::new);
        envProxy.registerScreen(Ic2ScreenHandlers.WEIGHTED_FLUID_DISTRIBUTOR, GuiWeightedFluidDistributor::new);
        envProxy.registerScreen(Ic2ScreenHandlers.WEIGHTED_ITEM_DISTRIBUTOR, GuiWeightedItemDistributor::new);
        envProxy.registerScreen(Ic2ScreenHandlers.INDUSTRIAL_WORKBENCH, GuiIndustrialWorkbench::new);
        envProxy.registerScreen(Ic2ScreenHandlers.BATCH_CRAFTER, GuiBatchCrafter::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ADVANCED_UPGRADE, new AdvancedUpgradeScreenFactory());
        envProxy.registerScreen(Ic2ScreenHandlers.ADVANCED_UPGRADE_EDIT_ORE, HandHeldOre.GuiEditOre::new);
        envProxy.registerScreen(Ic2ScreenHandlers.ADVANCED_UPGRADE_VALUE_CONFIG, HandHeldValueConfig.GuiValueConfig::new);
        envProxy.registerScreen(Ic2ScreenHandlers.SCANNER, GuiToolScanner::new);
        envProxy.registerColorProvider(new BlockColor(){

            public int m_92566_(BlockState blockState, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, int n) {
                return 0x669944;
            }
        }, new Block[]{Ic2Blocks.RUBBER_LEAVES});
        envProxy.registerColorProvider(new ItemColor(){

            public int m_92671_(ItemStack itemStack, int n) {
                return 0x669944;
            }
        }, new ItemLike[]{Ic2Items.RUBBER_LEAVES});
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.RUBBER_SAPLING);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.WOODEN_SCAFFOLD, Ic2Blocks.IRON_SCAFFOLD);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.FOAM);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.REINFORCED_GLASS);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.REINFORCED_DOOR);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.COPPER_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.INSULATED_COPPER_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.TIN_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.INSULATED_TIN_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.IRON_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.INSULATED_IRON_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.DOUBLE_INSULATED_IRON_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.TRIPLE_INSULATED_IRON_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.GOLD_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.INSULATED_GOLD_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.DOUBLE_INSULATED_GOLD_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.GLASS_FIBRE_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.DETECTOR_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.SPLITTER_CABLE);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.CROP_STICK);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.WEED_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.WHEAT_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.CARROTS_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.POTATO_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.BEETROOTS_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.PUMPKIN_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.MELON_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.DANDELION_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.POPPY_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.BLACKTHORN_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.TULIP_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.CYAZINT_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.VENOMILIA_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.REED_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.STICKY_REED_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.COCOA_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.FLAX_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.RED_MUSHROOM_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.BROWN_MUSHROOM_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.NETHER_WART_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.TERRA_WART_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.OAK_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.SPRUCE_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.BIRCH_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.JUNGLE_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.ACACIA_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.DARK_OAK_SAPLING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.FERRU_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.CYPRIUM_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.STAGNIUM_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.PLUMBISCUS_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.AURELIA_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.SHINING_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.RED_WHEAT_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.COFFEE_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.HOPS_CROP);
        envProxy.registerBlockLayer(RenderType.m_110457_(), Ic2Blocks.EATING_PLANT_CROP);
        this.registerRotorProvider(Ic2BlockEntities.WIND_KINETIC_GENERATOR);
        this.registerRotorProvider(Ic2BlockEntities.WATER_KINETIC_GENERATOR);
        this.registerRotorProvider(Ic2BlockEntities.WIND_GENERATOR);
        this.registerRotorProvider(Ic2BlockEntities.WATER_GENERATOR);
        envProxy.registerEntityRenderer(Ic2Entities.ITNT, ExplosiveBlockRenderer::new);
        envProxy.registerEntityRenderer(Ic2Entities.NUKE, ExplosiveBlockRenderer::new);
        envProxy.registerEntityRenderer(Ic2Entities.LASER_BULLET, LaserBulletEntityRenderer::new);
        envProxy.registerEntityRenderer(Ic2Entities.RUBBER_BOAT, context -> new BoatEntityRenderer(context, false, "ic2"));
        envProxy.registerEntityRenderer(Ic2Entities.ELECTRIC_BOAT, context -> new BoatEntityRenderer(context, false, "ic2"));
        envProxy.registerEntityRenderer(Ic2Entities.CARBON_BOAT, context -> new BoatEntityRenderer(context, false, "ic2"));
        envProxy.registerBlockEntityRenderer(Ic2BlockEntities.SIGN, SignRenderer::new);
    }

    @Override
    public void onPostInit() {
    }

    @Override
    public boolean isSimulating() {
        return !this.isRendering();
    }

    @Override
    @Deprecated
    public AudioManager getAudioManager() {
        return audioManager;
    }

    @Override
    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public Keyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public boolean isRendering() {
        return Minecraft.m_91087_().m_18695_();
    }

    @Override
    public void requestTick(boolean bl, Runnable runnable) {
        if (bl) {
            IntegratedServer integratedServer = mc.m_91092_();
            if (integratedServer == null) {
                throw new IllegalStateException("server unavailable");
            }
            integratedServer.execute(runnable);
        } else {
            mc.execute(runnable);
        }
    }

    @Override
    public void onServerAvailable(MinecraftServer minecraftServer) {
    }

    @Override
    public void displayError(String string, Object ... objectArray) {
        SideProxyServer.displayError0(string, objectArray);
    }

    @Override
    public void displayError(Exception exception, String string, Object ... objectArray) {
        SideProxyServer.displayError(this, exception, string, objectArray);
    }

    @Override
    public Player getPlayerInstance() {
        return SideProxyClient.mc.f_91074_;
    }

    @Override
    public Level getWorld(MinecraftServer minecraftServer, ResourceLocation resourceLocation) {
        if (minecraftServer == null) {
            ClientLevel clientLevel = SideProxyClient.mc.f_91073_;
            if (clientLevel != null && resourceLocation.equals((Object)Util.getDimId((Level)clientLevel))) {
                return clientLevel;
            }
        } else {
            for (Level level : minecraftServer.m_129785_()) {
                if (!resourceLocation.equals((Object)Util.getDimId(level))) continue;
                return level;
            }
        }
        return null;
    }

    @Override
    public Level getPlayerWorld() {
        return SideProxyClient.mc.f_91073_;
    }

    @Override
    public RecipeManager getRecipeManager() {
        IntegratedServer integratedServer = mc.m_91092_();
        if (integratedServer != null) {
            return integratedServer.m_129894_();
        }
        return Objects.requireNonNull(mc.m_91403_()).m_105141_();
    }

    @Override
    public File getMinecraftDir() {
        return envProxy.getMinecraftDir();
    }

    @Override
    @Deprecated
    public void playSoundSp(String string, float f, float f2) {
        IC2.audioManager.playOnce(this.getPlayerInstance(), PositionSpec.Hand, string, true, IC2.audioManager.getDefaultVolume());
    }

    @Override
    public void playSoundOnce(Entity entity, SoundEvent soundEvent, float f, float f2) {
        entity.m_5496_(soundEvent, f, f2);
    }

    @Override
    public void messagePlayer(Player player, String string, Object ... objectArray) {
        if (player == null) {
            player = SideProxyClient.mc.f_91074_;
        }
        if (objectArray.length > 0) {
            player.m_5661_((Component)Component.m_237110_((String)string, (Object[])SideProxyServer.getMessageComponents(objectArray)), false);
        } else {
            player.m_5661_((Component)Component.m_237113_((String)string), false);
        }
    }

    @Override
    public <T extends BlockEntity> void registerRotorProvider(BlockEntityType<T> blockEntityType) {
        envProxy.registerBer(blockEntityType, KineticGeneratorRenderer::new);
    }

    private static ClientEnvProxy getEnvProxy() {
        try {
            if (IC2.envProxy.isFabricEnv()) {
                return (ClientEnvProxy)Class.forName("ic2.fabric.ClientEnvProxyFabric").getConstructors()[0].newInstance(new Object[0]);
            }
            if (IC2.envProxy.isForgeEnv()) {
                return (ClientEnvProxy)Class.forName("ic2.forge.ClientEnvProxyForge").getConstructors()[0].newInstance(new Object[0]);
            }
            throw new IllegalStateException("unknown env");
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

