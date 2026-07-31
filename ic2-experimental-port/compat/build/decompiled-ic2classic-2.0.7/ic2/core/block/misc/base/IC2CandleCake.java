/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  javax.annotation.Nullable
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.AbstractCandleBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CakeBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc.base;

import com.google.common.collect.ImmutableList;
import ic2.core.block.misc.base.IC2CakeBlock;
import ic2.core.block.rendering.block.base.CandleCakeModel;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2CandleCake
extends AbstractCandleBlock
implements IBlockModel,
ICustomBlockModel,
IBlockModifiers.IAutoUV {
    private static final Object2IntMap<Block> BLOCK_TO_ID = (Object2IntMap)Util.m_137469_((Object)new Object2IntOpenHashMap(), T -> {
        T.put((Object)Blocks.f_152482_, 0);
        T.put((Object)Blocks.f_152483_, 1);
        T.put((Object)Blocks.f_152484_, 2);
        T.put((Object)Blocks.f_152511_, 3);
        T.put((Object)Blocks.f_152512_, 4);
        T.put((Object)Blocks.f_152513_, 5);
        T.put((Object)Blocks.f_152514_, 6);
        T.put((Object)Blocks.f_152515_, 7);
        T.put((Object)Blocks.f_152516_, 8);
        T.put((Object)Blocks.f_152517_, 9);
        T.put((Object)Blocks.f_152518_, 10);
        T.put((Object)Blocks.f_152519_, 11);
        T.put((Object)Blocks.f_152520_, 12);
        T.put((Object)Blocks.f_152521_, 13);
        T.put((Object)Blocks.f_152522_, 14);
        T.put((Object)Blocks.f_152523_, 15);
        T.put((Object)Blocks.f_152524_, 16);
    });
    public static final BooleanProperty LIT = AbstractCandleBlock.f_151895_;
    public static final IntegerProperty COLOR = IntegerProperty.m_61631_((String)"color", (int)0, (int)16);
    protected static final VoxelShape CAKE_SHAPE = Block.m_49796_((double)1.0, (double)0.0, (double)1.0, (double)15.0, (double)8.0, (double)15.0);
    protected static final VoxelShape CANDLE_SHAPE = Block.m_49796_((double)7.0, (double)8.0, (double)7.0, (double)9.0, (double)14.0, (double)9.0);
    protected static final VoxelShape SHAPE = Shapes.m_83110_((VoxelShape)CAKE_SHAPE, (VoxelShape)CANDLE_SHAPE);
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of((Object)new Vec3(0.5, 1.0, 0.5));
    private Block cake;
    ResourceLocation id;
    String textureLocation;

    public IC2CandleCake(String name, String textureLocation, Block cake) {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)cake).m_60953_(IC2CandleCake::getCakeLight));
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.textureLocation = textureLocation;
        this.cake = cake;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)LIT, (Comparable)Boolean.valueOf(false))).m_61124_((Property)COLOR, (Comparable)Integer.valueOf(0)));
    }

    private static int getCakeLight(BlockState state) {
        return (Boolean)state.m_61143_((Property)BlockStateProperties.f_61443_) != false ? 3 : 0;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152905_) {
        p_152905_.m_61104_(new Property[]{LIT}).m_61104_(new Property[]{COLOR});
    }

    protected Iterable<Vec3> m_142199_(BlockState state) {
        return PARTICLE_OFFSETS;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.m_21120_(hand);
        if (!stack.m_150930_(Items.f_42409_) && !stack.m_150930_(Items.f_42613_)) {
            if (IC2CandleCake.candleHit(result) && player.m_21120_(hand).m_41619_() && ((Boolean)state.m_61143_((Property)LIT)).booleanValue()) {
                IC2CandleCake.m_151899_((Player)player, (BlockState)state, (LevelAccessor)level, (BlockPos)pos);
                return InteractionResult.m_19078_((boolean)level.f_46443_);
            }
            InteractionResult interactionresult = IC2CakeBlock.eat((LevelAccessor)level, pos, this.cake.m_49966_(), player);
            if (interactionresult.m_19077_()) {
                Block.m_49840_((Level)level, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)IC2CandleCake.getBlockById((Integer)state.m_61143_((Property)COLOR))));
            }
            return interactionresult;
        }
        return InteractionResult.PASS;
    }

    private static boolean candleHit(BlockHitResult hit) {
        return hit.m_82450_().f_82480_ - (double)hit.m_82425_().m_123342_() > 0.5;
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(this.createDrop(state, (ItemStack)builder.m_78982_(LootContextParams.f_81463_), builder.m_78962_().f_46441_, (BlockEntity)builder.m_78982_(LootContextParams.f_81462_)));
        drops.add(new ItemStack((ItemLike)this.cake));
        return drops;
    }

    public ItemStack createDrop(BlockState state, ItemStack tool, RandomSource rand, @Nullable BlockEntity tile) {
        return new ItemStack((ItemLike)IC2CandleCake.getBlockById((Integer)state.m_61143_((Property)COLOR)));
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockHitResult result;
        if (target instanceof BlockHitResult && IC2CandleCake.candleHit(result = (BlockHitResult)target)) {
            return new ItemStack((ItemLike)IC2CandleCake.getBlockById((Integer)state.m_61143_((Property)COLOR)));
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    public ItemStack m_7397_(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack((ItemLike)this.cake);
    }

    public BlockState m_7417_(BlockState state, Direction side, BlockState sourceState, LevelAccessor level, BlockPos pos, BlockPos sourcePos) {
        return side == Direction.DOWN && !state.m_60710_((LevelReader)level, pos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(state, side, sourceState, level, pos, sourcePos);
    }

    public boolean m_7898_(BlockState state, LevelReader level, BlockPos pos) {
        return level.m_8055_(pos.m_7495_()).m_60767_().m_76333_();
    }

    public int m_6782_(BlockState state, Level level, BlockPos pos) {
        return CakeBlock.f_152743_;
    }

    public boolean m_7278_(BlockState state) {
        return true;
    }

    public boolean m_7357_(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new CandleCakeModel(state, this, IC2CandleCake.getTextureById((Integer)state.m_61143_((Property)COLOR)));
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        AABB box = CAKE_SHAPE.m_83215_();
        return new AABB(box.f_82288_ * 16.0, box.f_82289_ * 16.0, box.f_82290_ * 16.0, box.f_82291_ * 16.0, box.f_82292_ * 16.0, box.f_82293_ * 16.0);
    }

    @Override
    public List<BlockState> getModelStates() {
        return ObjectLists.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.textureLocation).get(side == Direction.UP ? "up" : (side == Direction.DOWN ? "down" : "outer"));
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    public static int getIdFromBlock(Block block) {
        return BLOCK_TO_ID.getInt((Object)block);
    }

    public static Block getBlockById(int id) {
        switch (id) {
            case 0: {
                return Blocks.f_152482_;
            }
            case 1: {
                return Blocks.f_152483_;
            }
            case 2: {
                return Blocks.f_152484_;
            }
            case 3: {
                return Blocks.f_152511_;
            }
            case 4: {
                return Blocks.f_152512_;
            }
            case 5: {
                return Blocks.f_152513_;
            }
            case 6: {
                return Blocks.f_152514_;
            }
            case 7: {
                return Blocks.f_152515_;
            }
            case 8: {
                return Blocks.f_152516_;
            }
            case 9: {
                return Blocks.f_152517_;
            }
            case 10: {
                return Blocks.f_152518_;
            }
            case 11: {
                return Blocks.f_152519_;
            }
            case 12: {
                return Blocks.f_152520_;
            }
            case 13: {
                return Blocks.f_152521_;
            }
            case 14: {
                return Blocks.f_152522_;
            }
            case 15: {
                return Blocks.f_152523_;
            }
            case 16: {
                return Blocks.f_152524_;
            }
        }
        return Blocks.f_50016_;
    }

    public static ResourceLocation getTextureById(int id) {
        return new ResourceLocation((String)(id == 0 ? "block/candle" : "block/" + DyeColor.m_41053_((int)(id - 1)).m_41065_() + "_candle"));
    }
}

