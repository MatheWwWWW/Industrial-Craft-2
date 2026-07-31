/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.serialization.Codec
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.data.worldgen.features.FeatureUtils
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.PlacementModifierType
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.extensions.IForgeMenuType
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.common.util.FakePlayerFactory
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.network.NetworkHooks
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.forge;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import ic2.api.energy.ProfileEvent;
import ic2.api.event.ExplosionEvent;
import ic2.api.event.RetextureEvent;
import ic2.core.IC2;
import ic2.core.fluid.EnvFluidHandler;
import ic2.core.item.EnvItemHandler;
import ic2.core.network.GrowingBuffer;
import ic2.core.proxy.EnvProxy;
import ic2.forge.EnvFluidHandlerForge;
import ic2.forge.EnvItemHandlerForge;
import ic2.forge.FmlMod;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class EnvProxyForge
implements EnvProxy {
    static final DeferredRegister<BlockEntityType<?>> blockEntityRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.BLOCK_ENTITY_TYPES, (String)"ic2");
    static final DeferredRegister<MenuType<?>> screenHandlerRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.MENU_TYPES, (String)"ic2");
    static final DeferredRegister<EntityType<?>> entityRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"ic2");
    static final DeferredRegister<MobEffect> statusEffectRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.MOB_EFFECTS, (String)"ic2");
    static List<Runnable> configuredFeatureRegistrations = new ArrayList<Runnable>();
    static List<PlacedFeatureRegistration<?>> placedFeatureRegistrations = new ArrayList();
    static List<PlacementModifierTypeRegistration> placementModifierTypeRegistrations = new ArrayList<PlacementModifierTypeRegistration>();
    static final DeferredRegister<FoliagePlacerType<?>> foliagePlacerRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.FOLIAGE_PLACER_TYPES, (String)"ic2");
    static final DeferredRegister<RecipeType<?>> recipeTypeRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.RECIPE_TYPES, (String)"ic2");
    static final DeferredRegister<RecipeSerializer<?>> recipeSerializerRegistry = DeferredRegister.create((IForgeRegistry)ForgeRegistries.RECIPE_SERIALIZERS, (String)"ic2");
    static HashMap<Item, Integer> burnTimeRecord = new HashMap();
    private static final boolean isClient = FMLEnvironment.dist.isClient();

    @Override
    public boolean isClientEnv() {
        return isClient;
    }

    @Override
    public boolean isFabricEnv() {
        return false;
    }

    @Override
    public boolean isForgeEnv() {
        return true;
    }

    @Override
    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public void registerBlock(ResourceLocation resourceLocation, Block block) {
        ForgeRegistries.BLOCKS.register(resourceLocation, (Object)block);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(ResourceLocation resourceLocation, BiFunction<BlockPos, BlockState, T> biFunction, Block ... blockArray) {
        BlockEntityType blockEntityType = BlockEntityType.Builder.m_155273_(biFunction::apply, (Block[])blockArray).m_58966_(null);
        blockEntityRegistry.register(resourceLocation.m_135815_(), () -> blockEntityType);
        return blockEntityType;
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> registerScreenHandler(ResourceLocation resourceLocation, BiFunction<Integer, Inventory, T> biFunction) {
        MenuType menuType = new MenuType(biFunction::apply);
        screenHandlerRegistry.register(resourceLocation.m_135815_(), () -> menuType);
        return menuType;
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> registerExtendedScreenHandler(ResourceLocation resourceLocation, EnvProxy.ExtendedClientScreenHandlerFactory<T> extendedClientScreenHandlerFactory) {
        MenuType menuType = IForgeMenuType.create(extendedClientScreenHandlerFactory::create);
        screenHandlerRegistry.register(resourceLocation.m_135815_(), () -> menuType);
        return menuType;
    }

    @Override
    public void registerItem(ResourceLocation resourceLocation, Item item) {
        ForgeRegistries.ITEMS.register(resourceLocation, (Object)item);
    }

    @Override
    public void registerEntity(ResourceLocation resourceLocation, EntityType<?> entityType) {
        entityRegistry.register(resourceLocation.m_135815_(), () -> entityType);
    }

    @Override
    public WoodType registerSignType(String string) {
        return WoodType.m_61844_((WoodType)WoodType.create((String)string));
    }

    @Override
    public void registerStatusEffect(ResourceLocation resourceLocation, MobEffect mobEffect) {
        statusEffectRegistry.register(resourceLocation.m_135815_(), () -> mobEffect);
    }

    @Override
    public void registerFlammableBlock(Block block, int n, int n2) {
    }

    @Override
    public SoundEvent registerSoundEvent(String string) {
        ResourceLocation resourceLocation = IC2.getIdentifier(string);
        SoundEvent soundEvent = new SoundEvent(resourceLocation);
        ForgeRegistries.SOUND_EVENTS.register(resourceLocation, (Object)soundEvent);
        return soundEvent;
    }

    @Override
    public GameEvent registerGameEvent(String string, int n) {
        ResourceLocation resourceLocation = IC2.getIdentifier(string);
        return (GameEvent)Registry.m_122965_((Registry)Registry.f_175412_, (ResourceLocation)resourceLocation, (Object)new GameEvent(resourceLocation.toString(), n));
    }

    @Override
    public <FC extends FeatureConfiguration, F extends Feature<FC>> CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> registerConfiguredFeature(ResourceLocation resourceLocation, F f, FC FC) {
        CompletableFuture completableFuture = new CompletableFuture();
        configuredFeatureRegistrations.add(() -> completableFuture.complete(FeatureUtils.m_206488_((String)resourceLocation.toString(), (Feature)f, (FeatureConfiguration)FC)));
        return completableFuture;
    }

    @Override
    public <FC extends FeatureConfiguration> void registerPlacedFeature(ResourceLocation resourceLocation, CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> completableFuture, List<PlacementModifier> list) {
        placedFeatureRegistrations.add(new PlacedFeatureRegistration<FC>(resourceLocation, completableFuture, list, new CompletableFuture<Holder<PlacedFeature>>()));
    }

    @Override
    public void attachPlacedFeatureToBiome(ResourceLocation resourceLocation, EnvProxy.BiomeSelector biomeSelector, GenerationStep.Decoration decoration) {
    }

    @Override
    public void registerPlacementModifierType(ResourceLocation resourceLocation, PlacementModifierType<?> placementModifierType) {
        placementModifierTypeRegistrations.add(new PlacementModifierTypeRegistration(resourceLocation, placementModifierType));
    }

    @Override
    public <T extends FoliagePlacer> FoliagePlacerType<T> registerFoliagePlacer(ResourceLocation resourceLocation, Codec<T> codec) {
        FoliagePlacerType foliagePlacerType = new FoliagePlacerType(codec);
        foliagePlacerRegistry.register(resourceLocation.m_135815_(), () -> foliagePlacerType);
        return foliagePlacerType;
    }

    @Override
    public <T extends Recipe<?>> RecipeType<T> registerRecipeType(ResourceLocation resourceLocation) {
        RecipeType recipeType = RecipeType.simple((ResourceLocation)resourceLocation);
        recipeTypeRegistry.register(resourceLocation.m_135815_(), () -> recipeType);
        return recipeType;
    }

    @Override
    public void registerRecipeSerializer(ResourceLocation resourceLocation, RecipeSerializer<?> recipeSerializer) {
        recipeSerializerRegistry.register(resourceLocation.m_135815_(), () -> recipeSerializer);
    }

    @Override
    public void runAfterRegistryInit(Runnable runnable) {
        FmlMod.instance.runAfterRegistryInit(runnable);
    }

    @Override
    public CreativeModeTab createItemGroup(ResourceLocation resourceLocation, final Supplier<ItemStack> supplier) {
        return new CreativeModeTab(String.format("%s.%s", resourceLocation.m_135827_(), resourceLocation.m_135815_())){

            public ItemStack m_40787_() {
                return (ItemStack)supplier.get();
            }

            public ItemStack m_6976_() {
                return null;
            }
        };
    }

    @Override
    public EnvFluidHandler createFluidStackHandler() {
        if (this.isClientEnv()) {
            try {
                return (EnvFluidHandler)Class.forName("ic2.forge.ClientEnvFluidHandlerForge").getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new RuntimeException(reflectiveOperationException);
            }
        }
        return new EnvFluidHandlerForge();
    }

    @Override
    public EnvItemHandler createItemHandler() {
        return new EnvItemHandlerForge();
    }

    @Override
    public float getBlastResistance(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Explosion explosion) {
        return blockState.m_60734_().getExplosionResistance(blockState, blockGetter, blockPos, explosion);
    }

    @Override
    public BlockState rotate(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, Rotation rotation) {
        return blockState.rotate(levelAccessor, blockPos, rotation);
    }

    @Override
    public boolean hasRecipeRemainder(ItemStack itemStack) {
        return itemStack.m_41720_().hasCraftingRemainingItem(itemStack);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack itemStack) {
        return itemStack.m_41720_().getCraftingRemainingItem(itemStack);
    }

    @Override
    public void registerBurnTime(ItemLike itemLike, int n) {
        burnTimeRecord.put(itemLike.m_5456_(), n);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return ForgeHooks.getBurnTime((ItemStack)itemStack, null);
    }

    @Override
    public boolean biomeHasType(Holder<Biome> holder, EnvProxy.BiomeType biomeType) {
        return false;
    }

    @Override
    public Collection<EnvProxy.BiomeType> getBiomeTypes(Holder<Biome> holder) {
        return Collections.emptySet();
    }

    @Override
    public boolean openHandledScreen(Player player, MenuProvider menuProvider, GrowingBuffer growingBuffer) {
        NetworkHooks.openScreen((ServerPlayer)((ServerPlayer)player), (MenuProvider)menuProvider, growingBuffer::writeTo);
        return true;
    }

    @Override
    public boolean isFakePlayer(Player player) {
        return player instanceof FakePlayer;
    }

    @Override
    public Player createFakePlayer(ServerLevel serverLevel, GameProfile gameProfile) {
        return FakePlayerFactory.get((ServerLevel)serverLevel, (GameProfile)gameProfile);
    }

    @Override
    public void announceProfileLoad(Set<String> set, String string) {
        MinecraftForge.EVENT_BUS.post((Event)new ProfileEvent.Load(set, string));
    }

    @Override
    public void announceProfileSwitch(String string, String string2) {
        MinecraftForge.EVENT_BUS.post((Event)new ProfileEvent.Switch(string, string2));
    }

    @Override
    public boolean announceRetexture(Level level, BlockPos blockPos, BlockState blockState, Direction direction, Player player, BlockState blockState2, String string, Direction direction2, int[] nArray) {
        RetextureEvent retextureEvent = new RetextureEvent(level, blockPos, blockState, direction, player, blockState2, string, direction2, nArray);
        MinecraftForge.EVENT_BUS.post((Event)retextureEvent);
        return retextureEvent.applied;
    }

    @Override
    public boolean announceExplosion(Level level, Entity entity, Vec3 vec3, double d, LivingEntity livingEntity, int n, double d2) {
        ExplosionEvent explosionEvent = new ExplosionEvent(level, entity, vec3, d, livingEntity, n, d2);
        return !MinecraftForge.EVENT_BUS.post((Event)explosionEvent);
    }

    record PlacedFeatureRegistration<FC extends FeatureConfiguration>(ResourceLocation id, CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> feature, List<PlacementModifier> modifiers, CompletableFuture<Holder<PlacedFeature>> placedFeature) {
    }

    record PlacementModifierTypeRegistration(ResourceLocation id, PlacementModifierType<?> type) {
    }
}

