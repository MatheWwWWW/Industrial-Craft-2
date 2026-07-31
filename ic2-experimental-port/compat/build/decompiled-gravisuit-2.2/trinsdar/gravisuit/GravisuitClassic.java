/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.platform.recipes.misc.AdvRecipeRegistry
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.client.event.RegisterGuiOverlaysEvent
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.gui.overlay.IGuiOverlay
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.config.IConfigSpec
 *  net.minecraftforge.fml.config.ModConfig$Type
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.network.NetworkRegistry$ChannelBuilder
 *  net.minecraftforge.network.simple.SimpleChannel
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package trinsdar.gravisuit;

import ic2.core.IC2;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trinsdar.gravisuit.network.PacketRelocator;
import trinsdar.gravisuit.proxy.CommonProxy;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitKeys;
import trinsdar.gravisuit.util.GravisuitRecipes;
import trinsdar.gravisuit.util.GravisuitWiki;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.render.GraviSuitOverlay;
import trinsdar.gravisuit.util.render.PlasmaBallRenderer;

@Mod(value="gravisuit")
public class GravisuitClassic {
    public static final String MODID = "gravisuit";
    public static final String networkChannelName = "gravisuit";
    private static final String MAIN_CHANNEL = "main_channel";
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static SimpleChannel NETWORK;
    public static CommonProxy proxy;
    public static final Logger LOGGER;
    private int currMessageId = 0;

    public GravisuitClassic() {
        FMLJavaModLoadingContext.get().getModEventBus().register((Object)this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, (IConfigSpec)GravisuitConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)GravisuitConfig.COMMON_SPEC);
        IC2.EVENT_BUS.register(GravisuitWiki.class);
        if (!FMLEnvironment.production) {
            System.setProperty("ic2workspace", "true");
        }
        NETWORK = NetworkRegistry.ChannelBuilder.named((ResourceLocation)new ResourceLocation("gravisuit", MAIN_CHANNEL)).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
        NETWORK.registerMessage(this.currMessageId++, PacketRelocator.class, PacketRelocator::encode, PacketRelocator::decode, PacketRelocator::handle);
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        AdvRecipeRegistry.INSTANCE.registerListener(GravisuitRecipes::loadRecipes);
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer((Block)Registry.PLASMA_PORTAL, (RenderType)RenderType.m_110457_());
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("gravisuit_overlay", (IGuiOverlay)new GraviSuitOverlay(Minecraft.m_91087_()));
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onEntityRenderersAdd(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registry.PLASMA_BALL_ENTITY_TYPE, PlasmaBallRenderer::new);
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void registerKeys(RegisterKeyMappingsEvent evt) {
        evt.register(GravisuitKeys.G_KEY);
    }

    @SubscribeEvent
    public void onRegisterItem(RegisterEvent event) {
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BLOCKS)) {
            Registry.init();
            event.register(ForgeRegistries.Keys.BLOCKS, new ResourceLocation("gravisuit", "plasma_portal"), () -> Registry.PLASMA_PORTAL);
        }
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.ITEMS)) {
            Registry.REGISTRY.forEach((r, i) -> event.register(ForgeRegistries.Keys.ITEMS, r, () -> i));
        }
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.ENTITY_TYPES)) {
            event.register(ForgeRegistries.Keys.ENTITY_TYPES, new ResourceLocation("gravisuit", "plasma_ball"), () -> Registry.PLASMA_BALL_ENTITY_TYPE);
        }
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BLOCK_ENTITY_TYPES)) {
            event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, new ResourceLocation("gravisuit", "plasma_portal"), () -> Registry.PLASMA_PORTAL_BLOCK_ENTITY);
        }
    }

    static {
        LOGGER = LogManager.getLogger((String)"gravisuit");
    }
}

