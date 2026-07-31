/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.data.worldgen.placement.PlacementUtils
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.data.event.GatherDataEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.IExtensionPoint$DisplayTest
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 */
package ic2.forge;

import ic2.core.event.EventHandler;
import ic2.core.network.NetworkManager;
import ic2.data.Ic2DataGenerators;
import ic2.forge.ClientEventHandlerForge;
import ic2.forge.ClientModEventHandlerForge;
import ic2.forge.EnvFluidHandlerForge;
import ic2.forge.EnvProxyForge;
import ic2.forge.EventHandlerForge;
import ic2.forge.ForgeNetworkHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(value="ic2")
public final class FmlMod {
    private List<Runnable> toRunAfterRegistryInit = new ArrayList<Runnable>();
    public static FmlMod instance;
    public static ExistingFileHelper existingFileHelper;
    private static final AtomicInteger loadState;

    public FmlMod() {
        instance = this;
        IEventBus iEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        iEventBus.register((Object)this);
        EnvProxyForge.blockEntityRegistry.register(iEventBus);
        EnvProxyForge.entityRegistry.register(iEventBus);
        EnvProxyForge.screenHandlerRegistry.register(iEventBus);
        EnvProxyForge.statusEffectRegistry.register(iEventBus);
        EnvProxyForge.foliagePlacerRegistry.register(iEventBus);
        EnvProxyForge.recipeTypeRegistry.register(iEventBus);
        EnvProxyForge.recipeSerializerRegistry.register(iEventBus);
        EnvFluidHandlerForge.fluidRegistry.register(iEventBus);
        EnvFluidHandlerForge.fluidTypeRegistry.register(iEventBus);
        if (FMLEnvironment.dist.isClient()) {
            iEventBus.register((Object)new ClientModEventHandlerForge());
        }
    }

    @SubscribeEvent
    public void load(FMLCommonSetupEvent fMLCommonSetupEvent) {
        MinecraftForge.EVENT_BUS.register((Object)new EventHandlerForge());
        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.register((Object)new ClientEventHandlerForge());
        }
        NetworkRegistry.newEventChannel((ResourceLocation)NetworkManager.channelId, () -> "0", string -> true, string -> true).registerObject((Object)new ForgeNetworkHandler());
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "OHNOES\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31\ud83d\ude31", (string, bl) -> true));
        if (!loadState.compareAndSet(1, 2)) {
            throw new IllegalStateException();
        }
        EventHandler.onInit();
    }

    @SubscribeEvent
    public void init(FMLLoadCompleteEvent fMLLoadCompleteEvent) {
        if (!loadState.compareAndSet(2, 3)) {
            throw new IllegalStateException();
        }
        EventHandler.onInitLate();
    }

    @SubscribeEvent
    public void initData(GatherDataEvent gatherDataEvent) {
        existingFileHelper = gatherDataEvent.getExistingFileHelper();
        Ic2DataGenerators.setup(gatherDataEvent.getGenerator());
    }

    @SubscribeEvent
    public void registerBlocks(RegisterEvent registerEvent) {
        if (registerEvent.getRegistryKey() != Registry.f_122901_) {
            return;
        }
        if (!loadState.compareAndSet(0, 1)) {
            throw new IllegalStateException();
        }
        EventHandler.onInitEarly();
    }

    @SubscribeEvent
    public void registerGameEvents(RegisterEvent registerEvent) {
        if (registerEvent.getRegistryKey() != Registry.f_122898_) {
            return;
        }
        EventHandler.onInitGameEvents();
    }

    @SubscribeEvent
    public void registerLate(RegisterEvent registerEvent) {
        if (registerEvent.getRegistryKey() != ForgeRegistries.Keys.HOLDER_SET_TYPES) {
            return;
        }
        for (Runnable runnable : this.toRunAfterRegistryInit) {
            runnable.run();
        }
        this.toRunAfterRegistryInit = null;
    }

    void runAfterRegistryInit(Runnable runnable) {
        if (loadState.get() > 1) {
            runnable.run();
        } else {
            this.toRunAfterRegistryInit.add(runnable);
        }
    }

    @SubscribeEvent
    public void registerFeatures(RegisterEvent registerEvent) {
        if (registerEvent.getRegistryKey() != Registry.f_122838_) {
            return;
        }
        for (Runnable object : EnvProxyForge.configuredFeatureRegistrations) {
            object.run();
        }
        for (EnvProxyForge.PlacedFeatureRegistration placedFeatureRegistration : EnvProxyForge.placedFeatureRegistrations) {
            placedFeatureRegistration.placedFeature().complete((Holder<PlacedFeature>)PlacementUtils.m_206509_((String)placedFeatureRegistration.id().toString(), placedFeatureRegistration.feature().join(), placedFeatureRegistration.modifiers()));
        }
        for (EnvProxyForge.PlacementModifierTypeRegistration placementModifierTypeRegistration : EnvProxyForge.placementModifierTypeRegistrations) {
            Registry.m_122965_((Registry)Registry.f_194570_, (ResourceLocation)placementModifierTypeRegistration.id(), placementModifierTypeRegistration.type());
        }
    }

    static {
        loadState = new AtomicInteger();
    }
}

