/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.serialization.Codec
 *  io.netty.buffer.ByteBuf
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
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
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer
 *  net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  net.minecraft.world.level.levelgen.placement.PlacementModifierType
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.proxy;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import ic2.core.fluid.EnvFluidHandler;
import ic2.core.item.EnvItemHandler;
import ic2.core.network.GrowingBuffer;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.phys.Vec3;

public interface EnvProxy {
    public boolean isClientEnv();

    public boolean isFabricEnv();

    public boolean isForgeEnv();

    public MinecraftServer getServer();

    public void registerBlock(ResourceLocation var1, Block var2);

    public <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(ResourceLocation var1, BiFunction<BlockPos, BlockState, T> var2, Block ... var3);

    public <T extends AbstractContainerMenu> MenuType<T> registerScreenHandler(ResourceLocation var1, BiFunction<Integer, Inventory, T> var2);

    public <T extends AbstractContainerMenu> MenuType<T> registerExtendedScreenHandler(ResourceLocation var1, ExtendedClientScreenHandlerFactory<T> var2);

    public void registerItem(ResourceLocation var1, Item var2);

    public void registerEntity(ResourceLocation var1, EntityType<?> var2);

    public WoodType registerSignType(String var1);

    public void registerStatusEffect(ResourceLocation var1, MobEffect var2);

    public void registerFlammableBlock(Block var1, int var2, int var3);

    public SoundEvent registerSoundEvent(String var1);

    public GameEvent registerGameEvent(String var1, int var2);

    public <FC extends FeatureConfiguration, F extends Feature<FC>> CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> registerConfiguredFeature(ResourceLocation var1, F var2, FC var3);

    public <FC extends FeatureConfiguration> void registerPlacedFeature(ResourceLocation var1, CompletableFuture<Holder<ConfiguredFeature<FC, ?>>> var2, List<PlacementModifier> var3);

    public void attachPlacedFeatureToBiome(ResourceLocation var1, BiomeSelector var2, GenerationStep.Decoration var3);

    public void registerPlacementModifierType(ResourceLocation var1, PlacementModifierType<?> var2);

    public <T extends FoliagePlacer> FoliagePlacerType<T> registerFoliagePlacer(ResourceLocation var1, Codec<T> var2);

    public <T extends Recipe<?>> RecipeType<T> registerRecipeType(ResourceLocation var1);

    public void registerRecipeSerializer(ResourceLocation var1, RecipeSerializer<?> var2);

    public void runAfterRegistryInit(Runnable var1);

    public CreativeModeTab createItemGroup(ResourceLocation var1, Supplier<ItemStack> var2);

    public EnvFluidHandler createFluidStackHandler();

    public EnvItemHandler createItemHandler();

    public float getBlastResistance(BlockState var1, BlockGetter var2, BlockPos var3, Explosion var4);

    public BlockState rotate(BlockState var1, LevelAccessor var2, BlockPos var3, Rotation var4);

    public boolean hasRecipeRemainder(ItemStack var1);

    public ItemStack getRecipeRemainder(ItemStack var1);

    public void registerBurnTime(ItemLike var1, int var2);

    public int getBurnTime(ItemStack var1);

    public boolean biomeHasType(Holder<Biome> var1, BiomeType var2);

    public Collection<BiomeType> getBiomeTypes(Holder<Biome> var1);

    public void announceProfileLoad(Set<String> var1, String var2);

    public void announceProfileSwitch(String var1, String var2);

    public boolean announceRetexture(Level var1, BlockPos var2, BlockState var3, Direction var4, Player var5, BlockState var6, String var7, Direction var8, int[] var9);

    public boolean openHandledScreen(Player var1, MenuProvider var2, GrowingBuffer var3);

    public boolean isFakePlayer(Player var1);

    public Player createFakePlayer(ServerLevel var1, GameProfile var2);

    public boolean announceExplosion(Level var1, Entity var2, Vec3 var3, double var4, LivingEntity var6, int var7, double var8);

    public static enum BiomeType {
        COLD,
        HOT,
        DRY,
        WET,
        DENSE,
        SPARSE,
        RARE,
        PLATEAU,
        MODIFIED,
        END,
        NETHER,
        OVERWORLD,
        BEACH,
        CONIFEROUS,
        DEAD,
        FOREST,
        HILLS,
        JUNGLE,
        LUSH,
        MAGICAL,
        MESA,
        MOUNTAIN,
        MUSHROOM,
        OCEAN,
        PLAINS,
        RIVER,
        SANDY,
        SAVANNA,
        SNOWY,
        SPOOKY,
        SWAMP,
        VOID,
        WASTELAND,
        WATER;

    }

    public static enum BiomeSelector {
        OVERWORLD,
        SWAMP,
        JUNGLE,
        FOREST;

    }

    public static interface ExtendedClientScreenHandlerFactory<T> {
        public T create(int var1, Inventory var2, ByteBuf var3);
    }
}

