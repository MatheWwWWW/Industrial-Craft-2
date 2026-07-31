/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.IC2Block;
import ic2.core.block.rendering.block.TreeTapAndBucketModel;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.base.IC2TexturedBlockItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TreeTapAndBucketBlock
extends IC2Block
implements ICustomBlockModel {
    public static final DirectionProperty FACING = IC2Properties.HORIZONTAL_FACINGS;
    public static final IntegerProperty FILL_STAGE = IntegerProperty.m_61631_((String)"fill_stage", (int)0, (int)5);
    public static final VoxelShape CENTER = Shapes.m_83110_((VoxelShape)Block.m_49796_((double)4.0, (double)0.0, (double)4.0, (double)12.0, (double)6.0, (double)12.0), (VoxelShape)Block.m_49796_((double)7.0, (double)7.0, (double)7.0, (double)9.0, (double)12.0, (double)9.0));
    public static final VoxelShape[] SHAPE = new VoxelShape[]{Shapes.m_83124_((VoxelShape)CENTER, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)2.0, (double)12.0, (double)9.0, (double)4.0, (double)16.0), Block.m_49796_((double)7.0, (double)10.0, (double)9.0, (double)9.0, (double)12.0, (double)16.0)}), Shapes.m_83124_((VoxelShape)CENTER, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)0.0, (double)2.0, (double)7.0, (double)10.0, (double)4.0, (double)9.0), Block.m_49796_((double)0.0, (double)10.0, (double)7.0, (double)7.0, (double)12.0, (double)9.0)}), Shapes.m_83124_((VoxelShape)CENTER, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)2.0, (double)0.0, (double)9.0, (double)4.0, (double)4.0), Block.m_49796_((double)7.0, (double)10.0, (double)0.0, (double)9.0, (double)12.0, (double)7.0)}), Shapes.m_83124_((VoxelShape)CENTER, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)12.0, (double)2.0, (double)7.0, (double)16.0, (double)4.0, (double)9.0), Block.m_49796_((double)9.0, (double)10.0, (double)7.0, (double)16.0, (double)12.0, (double)9.0)})};

    public TreeTapAndBucketBlock() {
        super("treetap_and_bucket", BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60978_(4.0f));
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)FILL_STAGE, (Comparable)Integer.valueOf(0)));
    }

    @Override
    public IC2BlockItem createItem() {
        return new IC2TexturedBlockItem(this, "misc", "treetap_and_bucket");
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING, FILL_STAGE});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new TreeTapAndBucketModel(state, IC2Textures.getMappedEntriesBlockIC2("misc").get("treetap_and_bucket"));
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE[((Direction)state.m_61143_((Property)FACING)).m_122416_()];
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return this.getFacing((Level)worldIn, pos) != null;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        Direction direction = this.getFacing(context.m_43725_(), context.m_8083_());
        return direction == null ? null : (BlockState)super.m_5573_(context).m_61124_((Property)FACING, (Comparable)direction);
    }

    protected Direction getFacing(Level world, BlockPos pos) {
        for (Direction dir : DirectionList.HORIZONTAL) {
            BlockState neighbor = DirectionList.getNeighborState(world, pos, dir);
            if (!(neighbor.m_60734_() instanceof RubberwoodLogBlock) || !((Boolean)neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() || neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) != dir.m_122424_()) continue;
            return dir;
        }
        return null;
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.m_6402_(worldIn, pos, state, placer, stack);
        worldIn.m_186460_(pos, (Block)this, 200);
    }

    public int tickRate(LevelReader worldIn) {
        return 100;
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockState neighbor = worldIn.m_8055_(pos.m_121945_((Direction)state.m_61143_((Property)FACING)));
        if (!(neighbor.m_60734_() instanceof RubberwoodLogBlock) || !((Boolean)neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() || neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) != ((Direction)state.m_61143_((Property)FACING)).m_122424_()) {
            worldIn.m_46961_(pos, true);
            return;
        }
        worldIn.m_186460_(pos, (Block)this, 200);
    }

    public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        BlockState neighbor = worldIn.m_8055_(pos.m_121945_((Direction)state.m_61143_((Property)FACING)));
        if (!(neighbor.m_60734_() instanceof RubberwoodLogBlock) || !((Boolean)neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() || neighbor.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) != ((Direction)state.m_61143_((Property)FACING)).m_122424_()) {
            worldIn.m_46961_(pos, true);
            return;
        }
        if ((Integer)state.m_61143_((Property)FILL_STAGE) >= 5) {
            return;
        }
        if (((Boolean)neighbor.m_61143_((Property)RubberwoodLogBlock.COLLECTABLE)).booleanValue()) {
            worldIn.m_46597_(pos.m_121945_((Direction)state.m_61143_((Property)FACING)), (BlockState)neighbor.m_61124_((Property)RubberwoodLogBlock.COLLECTABLE, (Comparable)Boolean.valueOf(false)));
            worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)FILL_STAGE, (Comparable)Integer.valueOf(Math.min(5, (Integer)state.m_61143_((Property)FILL_STAGE) + 1 + random.m_188503_(3)))));
        }
        worldIn.m_186460_(pos, (Block)this, 200);
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int stage = (Integer)state.m_61143_((Property)FILL_STAGE);
        if (stage > 0) {
            if (IC2.PLATFORM.isSimulating()) {
                worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)FILL_STAGE, (Comparable)Integer.valueOf(0)));
                Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)IC2Items.STICKY_RESIN, stage));
                worldIn.m_186460_(pos, (Block)this, 200);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void addExtraDrops(List<ItemStack> list, BlockState state, ItemStack tool, RandomSource rand) {
        int props = (Integer)state.m_61143_((Property)FILL_STAGE);
        if (props > 0) {
            list.add(new ItemStack((ItemLike)IC2Items.STICKY_RESIN, props));
        }
    }
}

