/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.Tags$Blocks
 */
package ic2.core.block.cables.luminator;

import ic2.core.block.base.IStateController;
import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.cables.CableBlock;
import ic2.core.block.cables.luminator.LuminatorTileEntity;
import ic2.core.block.rendering.block.LuminatorModel;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.ICamouflageRenderType;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.features.providers.EmptyProvider;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class LuminatorBlock
extends BaseActivityBlock<LuminatorTileEntity>
implements IStateController<LuminatorTileEntity>,
ICustomBlockModel,
ICamouflageRenderType,
SimpleWaterloggedBlock {
    public static final VoxelShape[] FULL_SHAPES = new VoxelShape[]{Block.m_49796_((double)0.16, (double)15.0, (double)0.16, (double)15.84, (double)16.0, (double)15.84), Block.m_49796_((double)0.16, (double)0.0, (double)0.16, (double)15.84, (double)1.0, (double)15.84), Block.m_49796_((double)0.16, (double)0.16, (double)15.0, (double)15.84, (double)15.84, (double)16.0), Block.m_49796_((double)0.16, (double)0.16, (double)0.0, (double)15.84, (double)15.84, (double)1.0), Block.m_49796_((double)15.0, (double)0.16, (double)0.16, (double)16.0, (double)15.84, (double)15.84), Block.m_49796_((double)0.0, (double)0.16, (double)0.16, (double)1.0, (double)15.84, (double)15.84)};
    public static final VoxelShape[] EMPTY_SHAPE = new VoxelShape[]{Block.m_49796_((double)4.0, (double)13.0, (double)4.0, (double)12.0, (double)16.0, (double)12.0), Block.m_49796_((double)0.16, (double)0.0, (double)0.16, (double)15.84, (double)1.0, (double)15.84), Block.m_49796_((double)6.0, (double)3.0, (double)14.0, (double)10.0, (double)13.0, (double)16.0), Block.m_49796_((double)6.0, (double)3.0, (double)0.0, (double)10.0, (double)13.0, (double)2.0), Block.m_49796_((double)14.0, (double)3.0, (double)6.0, (double)16.0, (double)13.0, (double)10.0), Block.m_49796_((double)0.0, (double)3.0, (double)6.0, (double)2.0, (double)13.0, (double)10.0)};
    public static final IntegerProperty LIGHT = IC2Properties.LIGHT_0_15;
    public static final BooleanProperty BIG = BooleanProperty.m_61465_((String)"big");
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BlockBehaviour.Properties BASE_LUMINATOR = BlockBehaviour.Properties.m_60939_((Material)Material.f_76275_).m_60918_(SoundType.f_56744_).m_60953_(T -> (Integer)T.m_61143_((Property)LIGHT)).m_60924_((x, y, z) -> false).m_60913_(0.3f, 0.5f).m_60988_().m_60955_();

    public LuminatorBlock(String blockName, BlockEntityType<?> type) {
        super(blockName, BASE_LUMINATOR, EmptyProvider.INSTANCE, type);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(true))).m_61124_((Property)LIGHT, (Comparable)Integer.valueOf(0))).m_61124_((Property)BIG, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)context.m_43719_())).m_61124_((Property)BIG, (Comparable)Boolean.valueOf(LuminatorBlock.isSpecialSupporter((LevelReader)context.m_43725_(), context.m_8083_().m_121945_(context.m_43719_().m_122424_()))))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_));
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return LuminatorBlock.isGlass(worldIn, pos) ? Shapes.m_83144_() : ((Boolean)state.m_61143_((Property)BIG) != false ? FULL_SHAPES : EMPTY_SHAPE)[((Direction)state.m_61143_((Property)FACING)).m_122411_()];
    }

    @Override
    public List<BlockState> getModelStates() {
        return ObjectLists.emptyList();
    }

    public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        BlockState glass;
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof LuminatorTileEntity && (glass = ((LuminatorTileEntity)tile).getGlassState()) != null) {
            try {
                return glass.getExplosionResistance(world, pos, explosion);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return super.getExplosionResistance(state, world, pos, explosion);
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, LuminatorTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)BIG, (Comparable)Boolean.valueOf(LuminatorBlock.isSpecialSupporter((LevelReader)world, pos.m_121945_(tile.getFacing().m_122424_())) || tile.getGlassState() != null))).m_61124_((Property)LIGHT, (Comparable)Integer.valueOf(tile.getLightLevel()))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.getGlassState() == null)));
        world.m_7726_().m_7827_().m_7174_(pos);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ACTIVE}).m_61104_(new Property[]{BIG}).m_61104_(new Property[]{FACING}).m_61104_(new Property[]{LIGHT}).m_61104_(new Property[]{WATER});
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new LuminatorModel(state, IC2Textures.getMappedEntriesBlockIC2("electric/luminator").get((Boolean)state.m_61143_((Property)ACTIVE) != false ? "luminator_on" : "luminator_off"));
    }

    @Override
    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!this.m_7898_(state, (LevelReader)worldIn, pos) && !LuminatorBlock.isGlass((BlockGetter)worldIn, pos)) {
            worldIn.m_7471_(pos, isMoving);
            Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)this));
        }
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return LuminatorBlock.isSupporterBlock(worldIn, pos.m_121945_(((Direction)state.m_61143_((Property)FACING)).m_122424_()));
    }

    public static boolean isGlass(BlockGetter world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        return tile instanceof LuminatorTileEntity && ((LuminatorTileEntity)tile).getGlassState() != null;
    }

    public static boolean isSupporterBlock(LevelReader world, BlockPos pos) {
        return !world.m_46859_(pos) && (world.m_8055_(pos).m_60796_((BlockGetter)world, pos) || LuminatorBlock.isSpecialSupporter(world, pos));
    }

    public static boolean isSpecialSupporter(LevelReader world, BlockPos pos) {
        if (world.m_46859_(pos)) {
            return false;
        }
        BlockState state = world.m_8055_(pos);
        Block block = state.m_60734_();
        return state.m_204336_(Tags.Blocks.GLASS) || state.m_204336_(BlockTags.f_13039_) || block instanceof CableBlock || block == IC2Blocks.REINFORCED_CLEAR_GLASS || block == IC2Blocks.REINFORCED_GLASS || block instanceof LuminatorBlock && LuminatorBlock.isGlass((BlockGetter)world, pos);
    }
}

