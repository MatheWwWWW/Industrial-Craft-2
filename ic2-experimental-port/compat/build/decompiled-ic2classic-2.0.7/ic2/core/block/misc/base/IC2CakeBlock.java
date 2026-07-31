/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.CakeBlock
 *  net.minecraft.world.level.block.CandleBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc.base;

import com.mojang.datafixers.util.Pair;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.misc.base.IC2CandleCake;
import ic2.core.item.block.CakeBlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2CakeBlock
extends CakeBlock
implements IBlockModel,
IBlockModifiers.IAutoUV,
IAutoCreator {
    ResourceLocation id;
    String textureFolder;
    String itemTextureFolder;
    String itemTexture;
    Function<Block, BlockState> candleCake;
    FoodProperties props;

    public IC2CakeBlock(String name, String textureFolder, String itemTextureFolder, String itemTexture, FoodProperties props) {
        this(name, textureFolder, itemTextureFolder, itemTexture, props, null);
    }

    public IC2CakeBlock(String name, String textureFolder, String itemTextureFolder, String itemTexture, FoodProperties props, Supplier<Block> provider) {
        super(BlockBehaviour.Properties.m_60939_((Material)Material.f_76287_).m_60978_(0.5f).m_60918_(SoundType.f_56745_));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureFolder = textureFolder;
        this.itemTextureFolder = itemTextureFolder;
        this.itemTexture = itemTexture;
        this.props = props;
        this.candleCake = provider == null ? null : IC2CakeBlock.cakeProvider(provider);
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        AABB box = f_51181_[(Integer)state.m_61143_((Property)f_51180_)].m_83215_();
        return new AABB(box.f_82288_ * 16.0, box.f_82289_ * 16.0, box.f_82290_ * 16.0, box.f_82291_ * 16.0, box.f_82292_ * 16.0, box.f_82293_ * 16.0);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)super.m_5573_(context).m_61124_((Property)f_51180_, (Comparable)Integer.valueOf(context.m_43722_().m_41773_()));
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41721_(((Integer)state.m_61143_((Property)f_51180_)).intValue());
        drops.add(stack);
        return drops;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Block block;
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_204117_(ItemTags.f_144319_) && (Integer)state.m_61143_((Property)f_51180_) == 0 && this.candleCake != null && (block = Block.m_49814_((Item)stack.m_41720_())) instanceof CandleBlock) {
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            level.m_5594_((Player)null, pos, SoundEvents.f_144090_, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.m_46597_(pos, this.candleCake.apply(block));
            level.m_142346_((Entity)player, GameEvent.f_157792_, pos);
            player.m_36246_(Stats.f_12982_.m_12902_((Object)stack.m_41720_()));
            return InteractionResult.SUCCESS;
        }
        if (level.f_46443_) {
            if (this.eatCake((LevelAccessor)level, pos, state, player).m_19077_()) {
                return InteractionResult.SUCCESS;
            }
            if (stack.m_41619_()) {
                return InteractionResult.CONSUME;
            }
        }
        return this.eatCake((LevelAccessor)level, pos, state, player);
    }

    private InteractionResult eatCake(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
        if (!player.m_36391_(this.props.m_38747_())) {
            return InteractionResult.PASS;
        }
        player.m_36220_(Stats.f_12942_);
        player.m_36324_().m_38707_(this.props.m_38744_(), this.props.m_38745_());
        for (Pair pair : this.props.m_38749_()) {
            if (level.m_5776_() || pair.getFirst() == null || !(player.m_217043_().m_188501_() < ((Float)pair.getSecond()).floatValue())) continue;
            player.m_7292_(new MobEffectInstance((MobEffectInstance)pair.getFirst()));
        }
        int i = (Integer)state.m_61143_((Property)f_51180_);
        level.m_142346_((Entity)player, GameEvent.f_157806_, pos);
        if (i < 6) {
            level.m_7731_(pos, (BlockState)state.m_61124_((Property)f_51180_, (Comparable)Integer.valueOf(i + 1)), 3);
        } else {
            level.m_7471_(pos, false);
            level.m_142346_((Entity)player, GameEvent.f_157794_, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult eat(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
        Block block = state.m_60734_();
        if (block instanceof IC2CakeBlock) {
            IC2CakeBlock cake = (IC2CakeBlock)block;
            return cake.eatCake(level, pos, state, player);
        }
        return CakeBlock.m_51185_((LevelAccessor)level, (BlockPos)pos, (BlockState)state, (Player)player);
    }

    @Override
    public BlockItem createItem() {
        return new CakeBlockItem((Block)this, this.itemTextureFolder, this.itemTexture, this.props);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(side == Direction.UP ? "up" : (side == Direction.DOWN ? "down" : ((Integer)state.m_61143_((Property)f_51180_) > 0 && side == Direction.WEST ? "inner" : "outer")));
    }

    public static Function<Block, BlockState> cakeProvider(Supplier<Block> provider) {
        return T -> (BlockState)((Block)provider.get()).m_49966_().m_61124_((Property)IC2CandleCake.COLOR, (Comparable)Integer.valueOf(IC2CandleCake.getIdFromBlock(T)));
    }
}

