/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.model.ArmorStandArmorModel
 *  net.minecraft.client.model.DrownedModel
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.GiantZombieModel
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.SkeletonModel
 *  net.minecraft.client.model.ZombieModel
 *  net.minecraft.client.model.ZombieVillagerModel
 *  net.minecraft.client.model.geom.EntityModelSet
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderers
 *  net.minecraft.client.renderer.blockentity.SignRenderer
 *  net.minecraft.client.renderer.entity.EntityRenderers
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.MinecartRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.server.packs.resources.ResourceManagerReloadListener
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.EntityRenderersEvent$AddLayers
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent
 *  net.minecraftforge.client.event.RegisterColorHandlersEvent
 *  net.minecraftforge.client.event.RegisterColorHandlersEvent$Block
 *  net.minecraftforge.client.event.RegisterColorHandlersEvent$Item
 *  net.minecraftforge.client.event.RegisterGuiOverlaysEvent
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 *  net.minecraftforge.client.event.RenderLivingEvent$Pre
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.BufferBuilder;
import ic2.api.items.armor.IEnergyShieldArmor;
import ic2.api.tiles.tubes.IItemCache;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.block.base.misc.IAtlasProvider;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.block.rendering.tile.AdvancedComparatorRenderer;
import ic2.core.block.rendering.tile.ChunkloaderRenderer;
import ic2.core.block.rendering.tile.EnchanterRenderer;
import ic2.core.block.rendering.tile.ExpansionRenderer;
import ic2.core.block.rendering.tile.FusionReactorRenderer;
import ic2.core.block.rendering.tile.MonitorRenderer;
import ic2.core.block.rendering.tile.PersonalChestRenderer;
import ic2.core.block.rendering.tile.PlasmaRenderer;
import ic2.core.block.rendering.tile.TankRenderer;
import ic2.core.block.rendering.tile.TubeRenderer;
import ic2.core.block.rendering.tile.WindmillRenderer;
import ic2.core.block.rendering.world.WorldOverlays;
import ic2.core.block.rendering.world.WrenchOverlay;
import ic2.core.block.rendering.world.impl.MultiBlockOverlay;
import ic2.core.block.transport.item.cache.ClientItemCache;
import ic2.core.entity.renderer.CrossRenderer;
import ic2.core.entity.renderer.EnergyShieldLayer;
import ic2.core.entity.renderer.IC2BoatRenderer;
import ic2.core.entity.renderer.IC2ExplosiveRenderer;
import ic2.core.entity.renderer.RocketMinerRenderer;
import ic2.core.entity.renderer.ThrowItemRenderer;
import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.item.renders.IC2TileEntityStackRenderer;
import ic2.core.item.renders.features.EnergyHudRenderer;
import ic2.core.item.renders.features.ToolBoxRenderer;
import ic2.core.item.renders.tooltip.InventoryToolTipRenderer;
import ic2.core.item.renders.tooltip.InventoryTooltip;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.custom.PlayerConfigSetting;
import ic2.core.platform.corehacks.mixins.client.DebugMixin;
import ic2.core.platform.corehacks.mixins.client.rendering.RenderTypeBuffersMixin;
import ic2.core.platform.corehacks.mixins.client.rendering.WorldRenderMixin;
import ic2.core.platform.events.DebugHighlighter;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Colors;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.PackHack;
import ic2.core.platform.rendering.events.RegisterBatchEvent;
import ic2.core.platform.rendering.events.TilesRenderedEvent;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.misc.IC2RenderTypes;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.managers.RecipeProvider;
import ic2.core.wiki.base.managers.WikiBuilder;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.GiantZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value=Dist.CLIENT)
public class PlatformClient
extends Platform
implements ResourceManagerReloadListener {
    Minecraft minecraft = Minecraft.m_91087_();
    Set<Class<? extends LivingEntity>> validClasses = CollectionUtils.createSet();
    List<WikiBuilder> builders = CollectionUtils.createList();
    int cooldown;

    @Override
    public void preInit() {
        super.preInit();
        IItemCache.CACHE.set(new ClientItemCache(), false);
        PackHack.doResourcePackHack();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register((Object)IC2Textures.INSTANCE);
        bus.register((Object)IC2Models.INSTANCE);
        bus.addListener(this::addLayers);
        bus.addListener(this::registerColors);
        bus.addListener(this::registerTooltip);
        bus.addListener(this::registerOverlays);
        MinecraftForge.EVENT_BUS.register((Object)WorldOverlays.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)WrenchOverlay.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)MultiBlockOverlay.INSTANCE);
        WorldOverlays.INSTANCE.init();
    }

    @Override
    public void onClientLoad() {
        ((ReloadableResourceManager)this.minecraft.m_91098_()).m_7217_((PreparableReloadListener)this);
        for (Block block : ForgeRegistries.BLOCKS) {
            if (block instanceof IRenderType) {
                IRenderType type = (IRenderType)block;
                ItemBlockRenderTypes.setRenderLayer((Block)block, T -> type.canRenderInLayer(block.m_49966_(), (RenderType)T));
            }
            if (!(block instanceof IAtlasProvider)) continue;
            IC2Textures.INSTANCE.registerRenderMaterial(((IAtlasProvider)block).createMaterial());
        }
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IAtlasProvider)) continue;
            IC2Textures.INSTANCE.registerRenderMaterial(((IAtlasProvider)item).createMaterial());
        }
    }

    @Override
    public void sendPlayerSpecificConfig() {
        boolean sprint;
        if (this.minecraft.f_91074_ == null) {
            return;
        }
        PlayerHandler.getHandler((Player)this.minecraft.f_91074_).doSpringBoost = sprint = IC2.CONFIG.boostOnSprint.get();
        PacketManager.INSTANCE.sendToServer(new PlayerConfigSetting(sprint));
    }

    @SubscribeEvent
    public void onDebugKey(InputEvent.Key event) {
        long window = this.minecraft.m_91268_().m_85439_();
        if (this.cooldown <= 0 && InputConstants.m_84830_((long)window, (int)292) && InputConstants.m_84830_((long)window, (int)82) && this.minecraft.f_91080_ == null) {
            this.minecraft.f_91074_.m_5661_((Component)Component.m_237113_((String)"[Debug]: ").m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD}).m_7220_((Component)Component.m_237113_((String)"Reloading IC2C Wikis").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.WHITE).m_131136_(Boolean.valueOf(false)))), false);
            ((DebugMixin)this.minecraft.f_91068_).setDebugHandled(true);
            this.minecraft.m_91102_().m_6213_(this.minecraft.m_91098_());
            int i = 0;
            while (i < this.builders.size()) {
                this.builders.get(i++).build();
            }
            this.cooldown = 5;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && this.cooldown > 0) {
            --this.cooldown;
            if (this.cooldown <= 0) {
                ((DebugMixin)this.minecraft.f_91068_).setDebugHandled(true);
                this.minecraft.m_91102_().m_6213_(this.minecraft.m_91098_());
                this.m_6213_(null);
                this.minecraft.f_91074_.m_5661_((Component)Component.m_237113_((String)"[Debug]: ").m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD}).m_7220_((Component)Component.m_237113_((String)"Reloaded IC2C Wikis").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.WHITE).m_131136_(Boolean.valueOf(false)))), false);
            }
        }
    }

    private void registerColors(RegisterColorHandlersEvent event) {
        if (event instanceof RegisterColorHandlersEvent.Block) {
            RegisterColorHandlersEvent.Block block = (RegisterColorHandlersEvent.Block)event;
            block.register((BlockColor)IC2Colors.INSTANCE, (Block[])IC2Blocks.COLORABLE.toArray(Block[]::new));
        } else if (event instanceof RegisterColorHandlersEvent.Item) {
            RegisterColorHandlersEvent.Item item = (RegisterColorHandlersEvent.Item)event;
            item.register((ItemColor)IC2Colors.INSTANCE, (ItemLike[])IC2Items.COLORABLE.toArray(Item[]::new));
        }
    }

    private void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("ic2_overlays", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            MultiBlockOverlay.INSTANCE.onRenderUI(poseStack);
            EnergyHudRenderer.INSTANCE.renderOverlay(poseStack, screenWidth, screenHeight);
            ToolBoxRenderer.INSTANCE.renderOverlay(poseStack, screenWidth, screenHeight);
            DebugHighlighter.INSTANCE.onToolTip(poseStack);
        });
    }

    private void registerTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(InventoryTooltip.class, InventoryToolTipRenderer::new);
    }

    @Override
    public void init() {
        super.init();
        RecipeProvider.INSTANCE.init();
        EntityRenderers.m_174036_(IC2Entities.ITNT, IC2ExplosiveRenderer::new);
        EntityRenderers.m_174036_(IC2Entities.NUKE, IC2ExplosiveRenderer::new);
        EntityRenderers.m_174036_(IC2Entities.RUBBERWOOD_BOAT, T -> new IC2BoatRenderer(T, false));
        EntityRenderers.m_174036_(IC2Entities.RUBBER_BOAT, T -> new IC2BoatRenderer(T, false));
        EntityRenderers.m_174036_(IC2Entities.CARBON_BOAT, T -> new IC2BoatRenderer(T, false));
        EntityRenderers.m_174036_(IC2Entities.DYNAMITE, ThrowItemRenderer::new);
        EntityRenderers.m_174036_(IC2Entities.MINING_LASER, T -> new CrossRenderer(T, new ResourceLocation("ic2", "textures/models/misc/mining_laser_shot.png")));
        EntityRenderers.m_174036_(IC2Entities.TRACKING_MINING_LASER, T -> new CrossRenderer(T, new ResourceLocation("ic2", "textures/models/misc/mining_laser_shot.png")));
        EntityRenderers.m_174036_(IC2Entities.ROCKET_MINER, RocketMinerRenderer::new);
        EntityRenderers.m_174036_(IC2Entities.BATBOX, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(IC2Entities.MFE, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(IC2Entities.MFSU, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(IC2Entities.ESU, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(IC2Entities.ISU, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        EntityRenderers.m_174036_(IC2Entities.PESU, T -> new MinecartRenderer(T, ModelLayers.f_171198_));
        BlockEntityRenderers.m_173590_(IC2Tiles.SIMPLE_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.SPEED_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.TRANSPORT_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.VOID_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.HOVER_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.DIRECTIONAL_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.INSERTION_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.DIRECTIONAL_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.EXTRACTION_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.FILTERED_EXTRACTION_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.STACKING_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.PICKUP_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.REDSTONE_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.FILTER_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.SWITCH_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.ROUND_ROBIN_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.TELEPORT_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.DROPPING_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.DYEING_TUBE, TubeRenderer.ColorTubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.COLOR_FILTER_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.REQUEST_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.PROVIDER_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.FLUID_TUBE, TubeRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.SIGN, SignRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.CHUNKLOADER, ChunkloaderRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.ELECTRIC_ENCHANTER, EnchanterRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.PLASMAFIER, PlasmaRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.FLUID_EXPANSION, ExpansionRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.PERSONAL_CHEST, PersonalChestRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.ADVANCED_COMPARATOR, AdvancedComparatorRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.TANK, TankRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.GENERATOR_WINDMILL, WindmillRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.MONITOR, MonitorRenderer::new);
        BlockEntityRenderers.m_173590_(IC2Tiles.FUSION_REACTOR, FusionReactorRenderer::new);
        MinecraftForge.EVENT_BUS.register((Object)this);
        IC2.EVENT_BUS.addListener(this::onBatchesCreated);
        SortedMap<RenderType, BufferBuilder> buffer = ((RenderTypeBuffersMixin)((WorldRenderMixin)this.minecraft.f_91060_).getBuffers()).getFixedBuffers();
        IC2.EVENT_BUS.post((Event)new RegisterBatchEvent(T -> buffer.put((RenderType)T, new BufferBuilder(T.m_110507_()))));
    }

    public void addLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet sets = event.getEntityModels();
        for (String skin : event.getSkins()) {
            LivingEntityRenderer render = event.getSkin(skin);
            render.m_115326_(new EnergyShieldLayer(render, new PlayerModel(sets.m_171103_(ModelLayers.f_171162_), false)));
        }
        this.addToClass((EntityType)EntityType.f_20524_, (HumanoidModel)new SkeletonModel(sets.m_171103_(ModelLayers.f_171236_)));
        this.addToClass((EntityType)EntityType.f_20481_, (HumanoidModel)new SkeletonModel(sets.m_171103_(ModelLayers.f_171236_)));
        this.addToClass((EntityType)EntityType.f_20497_, (HumanoidModel)new SkeletonModel(sets.m_171103_(ModelLayers.f_171216_)));
        this.addToClass((EntityType)EntityType.f_20529_, (HumanoidModel)new ArmorStandArmorModel(sets.m_171103_(ModelLayers.f_171155_)));
        this.addToClass((EntityType)EntityType.f_20454_, (HumanoidModel)new GiantZombieModel(sets.m_171103_(ModelLayers.f_171151_)));
        this.addToClass((EntityType)EntityType.f_20531_, (HumanoidModel)new ZombieModel(sets.m_171103_(ModelLayers.f_171231_)));
        this.addToClass((EntityType)EntityType.f_20530_, (HumanoidModel)new ZombieVillagerModel(sets.m_171103_(ModelLayers.f_171228_)));
        this.addToClass((EntityType)EntityType.f_20562_, (HumanoidModel)new DrownedModel(sets.m_171103_(ModelLayers.f_171136_)));
        this.addToClass((EntityType)EntityType.f_20501_, (HumanoidModel)new ZombieModel(sets.m_171103_(ModelLayers.f_171223_)));
        this.addToClass((EntityType)EntityType.f_20458_, (HumanoidModel)new ZombieModel(sets.m_171103_(ModelLayers.f_171188_)));
    }

    private <T extends LivingEntity> void addToClass(EntityType<T> entity, HumanoidModel<T> model) {
        LivingEntityRenderer type = (LivingEntityRenderer)this.minecraft.m_91290_().f_114362_.get(entity);
        if (type != null) {
            type.m_115326_(new EnergyShieldLayer(type, model));
        }
    }

    public void onBatchesCreated(RegisterBatchEvent event) {
        event.register(IC2RenderTypes.FLUID_RENDER);
        event.register(IC2RenderTypes.TUBE_ITEM_HIGHLIGHT);
        event.register(IC2RenderTypes.WINDMILL_ROTORS);
    }

    @SubscribeEvent
    public void onTilesRendered(TilesRenderedEvent event) {
        if (event.isTranslucent()) {
            event.finishType(IC2RenderTypes.FLUID_RENDER);
            event.finishType(IC2RenderTypes.TUBE_ITEM_HIGHLIGHT);
        } else {
            event.finishType(IC2RenderTypes.WINDMILL_ROTORS);
        }
    }

    @Override
    public void registerWiki(WikiBuilder builder) {
        this.builders.add(builder);
    }

    public void m_6213_(ResourceManager p_10758_) {
        RecipeProvider.INSTANCE.clearReferences();
        int i = 0;
        while (i < this.builders.size()) {
            this.builders.get(i++).build();
        }
        if (this.minecraft.f_91073_ != null) {
            RecipeProvider.INSTANCE.reloadReferences();
        }
    }

    @SubscribeEvent
    public void onEntityDamageEvent(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player && IEnergyShieldArmor.addsEnergyShieldEffect(entity)) {
            entity.f_20916_ = -entity.f_20916_;
        }
    }

    @SubscribeEvent
    public void onEntityDamageEvent(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        LivingEntity entity = event.getEntity();
        if (entity.f_20916_ < 0) {
            entity.f_20916_ = -entity.f_20916_;
        }
    }

    @Override
    public boolean launchGuiClient(Player player, IHasCustomGui gui, int windowID) {
        try {
            IC2Container container = gui.createContainer(player, windowID);
            player.f_36096_ = container;
            Screen screen = gui.createGui(player, container);
            if (screen == null) {
                return false;
            }
            PlayerHandler.getClientHandler().customGui = gui;
            this.minecraft.m_91152_(screen);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean launchGuiClient(Player player, InteractionHand hand, Direction side, IHasGui gui, int windowID) {
        try {
            IC2Container container = gui.createContainer(player, hand, side, windowID);
            player.f_36096_ = container;
            Screen screen = gui.createGui(player, hand, side, container);
            if (screen == null) {
                return false;
            }
            this.minecraft.m_91152_(screen);
            return true;
        }
        catch (Exception e) {
            IC2.LOGGER.catching((Throwable)e);
            return false;
        }
    }

    @Override
    public Player getClientPlayerInstance() {
        return this.isSimulating() ? null : this.minecraft.f_91074_;
    }

    @Override
    public boolean isOp(UUID id) {
        return id.equals(this.minecraft.f_91074_.m_20148_()) && this.minecraft.f_91074_.m_20310_(1);
    }

    @Override
    public RecipeManager getRecipes() {
        return this.isSimulating() ? super.getRecipes() : this.minecraft.f_91074_.f_108617_.m_105141_();
    }

    @Override
    public Level getWorld(ResourceKey<Level> dimID) {
        if (this.isSimulating()) {
            return super.getWorld(dimID);
        }
        return dimID != this.minecraft.f_91073_.m_46472_() ? null : this.minecraft.f_91073_;
    }

    @Override
    public void markBlockForRenderUpdate(BlockPos pos) {
        this.minecraft.f_91060_.m_109494_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
    }

    @Override
    public ModelData createValveData(Block block) {
        return ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> new CamouflageStorage(CamouflageShape.FULL_CUBE).setTexture(DirectionList.ALL, block).getQuads(block.m_49966_(), (Level)this.minecraft.f_91073_, BlockPos.f_121853_, (RenderType)T)).build();
    }

    @Override
    public boolean isInWorld() {
        return this.minecraft.f_91073_ != null;
    }

    @Override
    public Registry<Biome> getBiomeRegistry(Level level) {
        Registry registry;
        if (level instanceof ServerLevel) {
            ServerLevel server = (ServerLevel)level;
            registry = server.m_5962_().m_175515_(Registry.f_122885_);
        } else if (level instanceof ClientLevel) {
            ClientLevel client = (ClientLevel)level;
            registry = client.m_5962_().m_175515_(Registry.f_122885_);
        } else {
            registry = null;
        }
        return registry;
    }

    @Override
    public IClientItemExtensions getTileRenderer() {
        return TileProperties.INSTANCE;
    }

    private static class TileProperties
    implements IClientItemExtensions {
        public static final IClientItemExtensions INSTANCE = new TileProperties();

        private TileProperties() {
        }

        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return IC2TileEntityStackRenderer.INSTANCE;
        }
    }
}

