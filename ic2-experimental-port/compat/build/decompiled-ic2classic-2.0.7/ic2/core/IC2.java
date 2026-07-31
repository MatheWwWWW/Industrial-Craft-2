/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands$CommandSelection
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.dedicated.DedicatedServer
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RegisterParticleProvidersEvent
 *  net.minecraftforge.common.ForgeMod
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.AddReloadListenerEvent
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.server.ServerAboutToStartEvent
 *  net.minecraftforge.eventbus.api.BusBuilder
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.fml.loading.FMLPaths
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package ic2.core;

import com.mojang.brigadier.CommandDispatcher;
import ic2.api.addons.IModule;
import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.PainterHelper;
import ic2.api.core.APIHelper;
import ic2.api.core.IC2Classic;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.INetworkManager;
import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.ticks.ITickScheduler;
import ic2.core.Platform;
import ic2.core.PlatformClient;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioManagerClient;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.machines.logic.planner.PlannerRegistry;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.inventory.base.ICurioGetter;
import ic2.core.item.food_and_drink.IC2FoodsAndDrinks;
import ic2.core.item.manager.GatewayElectricItemManager;
import ic2.core.item.misc.tfbp.bp.IrrigationBluePrint;
import ic2.core.networking.NetworkManager;
import ic2.core.platform.events.ExportCommand;
import ic2.core.platform.events.IC2EventHandler;
import ic2.core.platform.events.InternalEventHandler;
import ic2.core.platform.events.MultiBlockManager;
import ic2.core.platform.events.MultiMineToolHandler;
import ic2.core.platform.events.StructureManager;
import ic2.core.platform.events.WorldGenerator;
import ic2.core.platform.player.Keyboard;
import ic2.core.platform.player.KeyboardClient;
import ic2.core.platform.player.ReactorSubmissions;
import ic2.core.platform.recipes.crafting.IC2RecipeSerializer;
import ic2.core.platform.recipes.helpers.Boxables;
import ic2.core.platform.recipes.helpers.IC2RecipeLoader;
import ic2.core.platform.recipes.helpers.ItemStackCache;
import ic2.core.platform.recipes.helpers.LootOverride;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Capabilities;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Fuels;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Paintings;
import ic2.core.platform.registries.IC2People;
import ic2.core.platform.registries.IC2Potions;
import ic2.core.platform.registries.IC2Recipes;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.registries.IC2Villagers;
import ic2.core.platform.registries.IC2Wiki;
import ic2.core.platform.registries.TickScheduler;
import ic2.core.utils.IC2ItemGroup;
import ic2.core.utils.SidedGateway;
import ic2.core.utils.compat.NamedThreadFactory;
import ic2.core.utils.config.config.FileSystemWatcher;
import ic2.core.utils.config.ic2.IC2Config;
import ic2.core.utils.config.impl.internal.ConfigLogger;
import ic2.core.utils.config.impl.internal.EventHandler;
import ic2.core.utils.helpers.ModCache;
import ic2.core.utils.plugins.IJEIHelper;
import ic2.core.utils.plugins.PluginLoader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="ic2")
public class IC2
implements APIHelper {
    public static final int API_VERSION = 2;
    public static final ExecutorService OFF_THREAD_WORKER = new ThreadPoolExecutor(0, Math.max(2, Runtime.getRuntime().availableProcessors() / 2), 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory("IC2 Offthread Tasks "));
    public static Logger LOGGER = LogManager.getLogger((String)"IC2Classic");
    public static IC2 INSTANCE;
    public static String MODID;
    public static CreativeModeTab IC2_MAIN_GROUP;
    public static CreativeModeTab FOOD_AND_DRINK_GROUP;
    public static CreativeModeTab CFOAM_GROUP;
    public static final Platform PLATFORM;
    public static final SidedGateway<NetworkManager> NETWORKING;
    public static final SidedGateway<GlobalRecipes> RECIPES;
    public static final AudioManager AUDIO;
    public static final Keyboard KEYBOARD;
    public static final TickScheduler TICK_HANDLER;
    public static IC2Config CONFIG;
    public static final FileSystemWatcher FILE_WATCHER;
    public static final PluginLoader PLUGINS;
    public static final ModCache MOD_CACHE;
    public static ICurioGetter CURIO_PLUGIN;
    public static IJEIHelper JEI_PLUGIN;
    public static final IEventBus EVENT_BUS;

    public IC2() {
        ForgeMod.enableMilkFluid();
        IC2Classic.setHelper(this);
        IC2People.init();
        CONFIG = new IC2Config();
        INSTANCE = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::postInit);
        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(this::onClientInit);
            bus.addListener(this::onClientLoaded);
        }
        bus.addListener(EventPriority.LOWEST, this::complete);
        bus.addListener(this::registerContent);
        bus.addListener(IC2Capabilities::registerCapabilities);
        MinecraftForge.EVENT_BUS.register((Object)EventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)IC2EventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.addListener(this::onCommandLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStartingEvent);
        MinecraftForge.EVENT_BUS.register((Object)InternalEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)FILE_WATCHER);
        MinecraftForge.EVENT_BUS.register((Object)IC2Fuels.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)StructureManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)MultiBlockManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)MultiMineToolHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.addListener(IC2Villagers::onVillagerEvent);
        MinecraftForge.EVENT_BUS.addListener(IC2Villagers::onWanderingEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onResourceReload);
        IC2RecipeSerializer.register();
        GatewayElectricItemManager.init();
        PainterHelper.init();
        PLATFORM.preInit();
        KEYBOARD.init();
        NETWORKING.get(FMLEnvironment.dist.isDedicatedServer()).init();
        RECIPES.get(true).init();
        if (FMLEnvironment.dist.isClient()) {
            RECIPES.get(false).setRecipes(false);
        }
        AUDIO.init();
        CropRegistry.REGISTRY.preInit();
        PLUGINS.init(2, CONFIG.getConfig().add("plugins"));
        CONFIG.save();
        PLUGINS.fireEvent(t -> t.preInit(bus));
    }

    @Override
    public List<ItemStack> getFluidContainers(Fluid fluid) {
        return ItemStackCache.INSTANCE.getFluidContainers(fluid);
    }

    @Override
    public FluidStack createSteam(int amount) {
        return new FluidStack(IC2Fluids.STEAM, amount);
    }

    @Override
    public ITickScheduler getTickHelper() {
        return TICK_HANDLER;
    }

    @Override
    public INetworkManager getNetworkManager() {
        return NETWORKING.get();
    }

    @Override
    public INetworkManager getNetworkManager(Dist side) {
        return NETWORKING.get(side.isDedicatedServer());
    }

    public void registerContent(RegisterEvent event) {
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.ENTITY_TYPES)) {
            LOGGER.info("Loading Entities");
            IC2Entities.init();
            LOGGER.info("Loaded Entities");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BLOCKS)) {
            LOGGER.info("Loading TileEntities");
            IC2Tiles.loadTiles();
            LOGGER.info("Loading Blocks");
            IC2Blocks.addBlocks();
            LOGGER.info("Loaded Blocks & Tiles");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.ITEMS)) {
            LOGGER.info("Loading Items");
            IC2Items.addItems();
            IC2FoodsAndDrinks.fillContainerList();
            LOGGER.info("Loaded Items");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.FLUIDS)) {
            LOGGER.info("Loading Fluids");
            IC2Fluids.loadFluids();
            LOGGER.info("Loaded Fluids");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.MOB_EFFECTS)) {
            LOGGER.info("Loading Potions");
            IC2Potions.init();
            LOGGER.info("Loaded Potions");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.POI_TYPES)) {
            LOGGER.info("Loading Villager Workstations");
            IC2Villagers.loadPois();
            LOGGER.info("Loaded Villager Workstations");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.VILLAGER_PROFESSIONS)) {
            LOGGER.info("Loading Villager Nosises");
            IC2Villagers.loadVillagers();
            LOGGER.info("Loaded Villager Nosises");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.PAINTING_VARIANTS)) {
            LOGGER.info("Loading Child Drawings");
            IC2Paintings.init();
            LOGGER.info("Loaded Child Drawings");
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS)) {
            event.getForgeRegistry().register(new ResourceLocation("ic2", "ic2_loot"), LootOverride.CODEC);
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.FEATURES)) {
            WorldGenerator.generateOres();
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS)) {
            event.getForgeRegistry().register(new ResourceLocation("ic2", "modifier"), WorldGenerator.IC2Modifier.CODEC);
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BIOME_MODIFIERS)) {
            event.getForgeRegistry().register(new ResourceLocation("ic2", "modifier"), (Object)new WorldGenerator.IC2Modifier());
        } else if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.STAT_TYPES)) {
            IC2Stats.init();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onClientInit(RegisterParticleProvidersEvent setup) {
        PLATFORM.onClientLoad();
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onClientLoaded(FMLClientSetupEvent event) {
        EventHandler.INSTANCE.onConfigsLoaded();
        CONFIG.loadSuggestions();
    }

    public void postInit(FMLCommonSetupEvent setup) {
        LOGGER.info("CommonSetup");
        IrrigationBluePrint.INSTANCE.init();
        IWrenchable.WrenchRegistry.INSTANCE.init();
        CropRegistry.REGISTRY.register();
        EnergyNetGlobal.INSTANCE.init();
        PLATFORM.init();
        PainterHelper.INSTANCE.registerPaintHelper(IC2Blocks.RUBBERWOOD_SIGN, (PainterHelper.IPaintable)new PainterHelper.SignPainter());
        PainterHelper.INSTANCE.registerPaintHelper(IC2Blocks.RUBBERWOOD_WALL_SIGN, (PainterHelper.IPaintable)new PainterHelper.SignPainter());
        IC2Tags.initTags();
        Boxables.loadBoxableItems();
        PlannerRegistry.init();
        ReactorSubmissions.INSTANCE.init();
        IC2Wiki.init();
        PLATFORM.addLangFile(new ResourceLocation("ic2", "blocks"));
        PLATFORM.addLangFile(new ResourceLocation("ic2", "items"));
        PLATFORM.addLangFile(new ResourceLocation("ic2", "crops"));
        PLATFORM.addLangFile(new ResourceLocation("ic2", "advancements"));
        PLATFORM.addLangFile(new ResourceLocation("ic2", "stats"));
        PLATFORM.addLangFile(new ResourceLocation("ic2", "probe"));
        if (!(CURIO_PLUGIN instanceof ICurioGetter.DummyGetter)) {
            ElectricItem.setCurioSupport(CURIO_PLUGIN::getCurioHandler);
        }
        PLUGINS.fireEvent(IModule::postInit);
    }

    public void onServerStartingEvent(ServerAboutToStartEvent event) {
        if (event.getServer() instanceof DedicatedServer) {
            ItemStackCache.INSTANCE.init();
        }
    }

    public void complete(FMLLoadCompleteEvent event) {
        if (FMLEnvironment.dist.isClient()) {
            ItemStackCache.INSTANCE.init();
        }
    }

    public void onCommandLoad(RegisterCommandsEvent starting) {
        if (starting.getCommandSelection() == Commands.CommandSelection.INTEGRATED) {
            ExportCommand.export((CommandDispatcher<CommandSourceStack>)starting.getDispatcher());
        }
    }

    public void onResourceReload(AddReloadListenerEvent listener) {
        listener.addListener((PreparableReloadListener)IC2RecipeLoader.INSTANCE);
    }

    public void loadRecipes(IAdvancedCraftingManager registry) {
        LOGGER.info("Loading Recipes");
        IC2Recipes.addRecipes(registry);
    }

    static {
        MODID = "ic2";
        IC2_MAIN_GROUP = new IC2ItemGroup();
        FOOD_AND_DRINK_GROUP = new IC2ItemGroup.IC2FoodAndDinkItemGroup();
        CFOAM_GROUP = new IC2ItemGroup.IC2CFoamItemGroup();
        PLATFORM = (Platform)DistExecutor.unsafeRunForDist(() -> PlatformClient::new, () -> Platform::new);
        NETWORKING = new SidedGateway("ic2.core.networking.NetworkManager", "ic2.core.networking.NetworkManagerClient");
        RECIPES = new SidedGateway("ic2.core.platform.recipes.misc.GlobalRecipes", "ic2.core.platform.recipes.misc.GlobalRecipesClient");
        AUDIO = (AudioManager)DistExecutor.unsafeRunForDist(() -> AudioManagerClient::new, () -> AudioManager::new);
        KEYBOARD = (Keyboard)DistExecutor.unsafeRunForDist(() -> KeyboardClient::new, () -> Keyboard::new);
        TICK_HANDLER = new TickScheduler();
        FILE_WATCHER = new FileSystemWatcher(new ConfigLogger(LOGGER), FMLPaths.CONFIGDIR.get(), EventHandler.INSTANCE);
        PLUGINS = new PluginLoader();
        MOD_CACHE = new ModCache();
        CURIO_PLUGIN = new ICurioGetter.DummyGetter();
        JEI_PLUGIN = new IJEIHelper.Dummy();
        EVENT_BUS = BusBuilder.builder().build();
    }
}

