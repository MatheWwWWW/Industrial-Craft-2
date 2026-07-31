/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BrewingStandBlockEntity
 *  net.minecraft.world.level.block.entity.SpawnerBlockEntity
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.fml.loading.FMLPaths
 *  net.minecraftforge.fml.util.thread.EffectiveSide
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core;

import com.google.common.base.Function;
import ic2.api.blocks.ExplosionWhitelist;
import ic2.api.items.ItemRegistries;
import ic2.api.tiles.readers.IActivityProvider;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.readers.ReaderProvider;
import ic2.api.tiles.tubes.IItemCache;
import ic2.core.IC2;
import ic2.core.block.transport.item.cache.ServerItemCache;
import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.inventory.base.IHasGui;
import ic2.core.platform.corehacks.mixins.server.BlockMixin;
import ic2.core.platform.corehacks.mixins.server.BlockPropertiesMixin;
import ic2.core.platform.corehacks.mixins.server.PlayerNetMixin;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.recipes.misc.MonitorRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.compat.BrewingStandHelper;
import ic2.core.utils.compat.FurnaceHelper;
import ic2.core.utils.compat.MobSpawnerHelper;
import ic2.core.wiki.base.managers.WikiBuilder;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Platform {
    List<ResourceLocation> customLang = CollectionUtils.createList();

    public void preInit() {
        MonitorRegistry.INSTANCE.init();
        IItemCache.CACHE.set(new ServerItemCache(), true);
    }

    public void init() {
        this.setResistance(Blocks.f_50080_, 60.0f);
        this.setResistance(Blocks.f_50201_, 60.0f);
        this.setResistance(Blocks.f_50265_, 60.0f);
        this.setResistance(Blocks.f_50322_, 60.0f);
        this.setResistance(Blocks.f_50323_, 60.0f);
        this.setResistance(Blocks.f_50324_, 60.0f);
        this.setResistance(Blocks.f_49990_, 30.0f);
        this.setResistance(Blocks.f_49991_, 30.0f);
        ExplosionWhitelist.addWhitelist(IC2Blocks.IRIDIUM_STONE, Blocks.f_50752_);
        ItemRegistries.registerMetalArmor((S, P, E) -> true, Items.f_42483_, Items.f_42471_, Items.f_42479_);
        ReaderProvider.registerActiveProvider(AbstractFurnaceBlockEntity.class, (Function<BlockEntity, IActivityProvider>)((Function)FurnaceHelper::new));
        ReaderProvider.registerActiveProvider(SpawnerBlockEntity.class, (Function<BlockEntity, IActivityProvider>)((Function)MobSpawnerHelper::new));
        ReaderProvider.registerActiveProvider(BrewingStandBlockEntity.class, (Function<BlockEntity, IActivityProvider>)((Function)BrewingStandHelper::new));
        ReaderProvider.registerFuelProvider(AbstractFurnaceBlockEntity.class, (Function<BlockEntity, IFuelStorage>)((Function)FurnaceHelper::new));
        ReaderProvider.registerFuelProvider(BrewingStandBlockEntity.class, (Function<BlockEntity, IFuelStorage>)((Function)BrewingStandHelper::new));
        ReaderProvider.registerProgressProvider(AbstractFurnaceBlockEntity.class, (Function<BlockEntity, IProgressMachine>)((Function)FurnaceHelper::new));
        ReaderProvider.registerProgressProvider(SpawnerBlockEntity.class, (Function<BlockEntity, IProgressMachine>)((Function)MobSpawnerHelper::new));
        ReaderProvider.registerProgressProvider(BrewingStandBlockEntity.class, (Function<BlockEntity, IProgressMachine>)((Function)BrewingStandHelper::new));
    }

    public void onClientLoad() {
    }

    public void sendPlayerSpecificConfig() {
    }

    public void displayError(String string) {
        IC2.LOGGER.error(string);
        ServerLifecycleHooks.handleExit((int)0);
    }

    public Player getClientPlayerInstance() {
        return null;
    }

    public RecipeManager getRecipes() {
        return ServerLifecycleHooks.getCurrentServer().m_129894_();
    }

    public Registry<Biome> getBiomeRegistry(Level level) {
        Registry registry;
        if (level instanceof ServerLevel) {
            ServerLevel server = (ServerLevel)level;
            registry = server.m_5962_().m_175515_(Registry.f_122885_);
        } else {
            registry = null;
        }
        return registry;
    }

    public final BlockEntity getTileEntity(ResourceKey<Level> dim, BlockPos pos, boolean optional) {
        Level world = this.getWorld(dim);
        return world != null && (!optional || world.m_7232_(pos.m_123341_() >> 4, pos.m_123343_() >> 4)) ? world.m_7702_(pos) : null;
    }

    public final Entity getEntity(ResourceKey<Level> dim, int id) {
        Level world = this.getWorld(dim);
        return world == null ? null : world.m_6815_(id);
    }

    public boolean isOp(UUID owner) {
        ServerPlayer player = this.getServer().m_6846_().m_11259_(owner);
        if (player == null) {
            return false;
        }
        return this.getServer().m_129944_(player.m_36316_()) >= this.getServer().m_7022_();
    }

    public boolean isSimulating() {
        return EffectiveSide.get().isServer();
    }

    public boolean isRendering() {
        return EffectiveSide.get().isClient();
    }

    public Level getWorld(ResourceKey<Level> dim) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server == null || dim == null ? null : server.m_129880_(dim);
    }

    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public IClientItemExtensions getTileRenderer() {
        return IClientItemExtensions.DEFAULT;
    }

    public boolean launchGui(Player player, IHasCustomGui gui) {
        if (player instanceof ServerPlayer && gui != null && gui.hasGui(player)) {
            ServerPlayer server = (ServerPlayer)player;
            PlayerHandler.getHandler((Player)player).customGui = gui;
            server.m_9217_();
            server.m_9230_();
            IC2.NETWORKING.get(true).sendCustomUIPacket(player, gui, server.f_8940_);
            player.f_36096_ = gui.createContainer(player, server.f_8940_);
            server.m_143399_(player.f_36096_);
            return true;
        }
        return false;
    }

    public boolean launchGuiClient(Player player, IHasCustomGui gui, int windowID) {
        return false;
    }

    public boolean launchGui(Player player, InteractionHand hand, Direction side, IHasGui gui) {
        if (player instanceof ServerPlayer && gui != null && gui.hasGui(player, hand, side)) {
            ServerPlayer server = (ServerPlayer)player;
            server.m_9217_();
            server.m_9230_();
            IC2.NETWORKING.get(true).sendUIPacket(player, hand, side, gui, server.f_8940_);
            player.f_36096_ = gui.createContainer(player, hand, side, server.f_8940_);
            server.m_143399_(player.f_36096_);
            return true;
        }
        return false;
    }

    public boolean launchGuiClient(Player player, InteractionHand hand, Direction side, IHasGui gui, int windowID) {
        return false;
    }

    public void markBlockForRenderUpdate(BlockPos pos) {
    }

    public Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }

    public void registerWiki(WikiBuilder builder) {
    }

    public ModelData createValveData(Block block) {
        return ModelData.EMPTY;
    }

    public void resetSleep(Player player) {
        player.m_7166_(Stats.f_12988_.m_12902_((Object)Stats.f_12992_));
    }

    public void addLangFile(ResourceLocation key) {
        this.customLang.add(key);
    }

    public void registerCustomLang(Consumer<ResourceLocation> filePrefix) {
        this.customLang.forEach(filePrefix);
    }

    public void setResistance(Block block, float newResistance) {
        ((BlockPropertiesMixin)((BlockMixin)block).getProperties()).setResistance(newResistance);
    }

    public boolean isPVP() {
        return this.getServer().m_129799_();
    }

    public void resetPlayerInAirTime(Entity player) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        PlayerNetMixin mixin = (PlayerNetMixin)((ServerPlayer)player).f_8906_;
        mixin.setAboveGroundTickCount(0);
        mixin.setAboveGroundVehicleTickCount(0);
        player.m_20201_().m_20199_().forEach(Entity::m_183634_);
    }

    public boolean isInWorld() {
        return false;
    }
}

