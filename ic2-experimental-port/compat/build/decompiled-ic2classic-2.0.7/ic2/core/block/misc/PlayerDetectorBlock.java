/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.misc;

import ic2.api.blocks.PainterHelper;
import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.cables.CableBlock;
import ic2.core.block.misc.CFoamBlock;
import ic2.core.block.misc.tiles.PlayerDetectorTileEntity;
import ic2.core.block.rendering.block.CamouflageModel;
import ic2.core.block.rendering.block.PlayerDetectorModel;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.features.providers.EmptyProvider;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.NonTranslucentBlockModel;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class PlayerDetectorBlock
extends BaseActivityBlock<PlayerDetectorTileEntity>
implements ICustomBlockModel,
IDualLogged,
ICamouflageBlock,
PainterHelper.IPaintable {
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final IntegerProperty FOAMED = CableBlock.FOAMED;
    public static final VoxelShape[] SHAPES = new VoxelShape[]{Block.m_49796_((double)5.0, (double)14.0, (double)5.0, (double)11.0, (double)16.0, (double)11.0), Block.m_49796_((double)5.0, (double)0.0, (double)5.0, (double)11.0, (double)2.0, (double)11.0), Block.m_49796_((double)5.0, (double)5.0, (double)14.0, (double)11.0, (double)11.0, (double)16.0), Block.m_49796_((double)5.0, (double)5.0, (double)0.0, (double)11.0, (double)11.0, (double)2.0), Block.m_49796_((double)14.0, (double)5.0, (double)5.0, (double)16.0, (double)11.0, (double)11.0), Block.m_49796_((double)0.0, (double)5.0, (double)5.0, (double)2.0, (double)11.0, (double)11.0)};

    public PlayerDetectorBlock() {
        super("player_detector", BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60955_(), EmptyProvider.INSTANCE, IC2Tiles.PLAYER_DETECTOR);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(0)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA}).m_61104_(new Property[]{FOAMED});
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    @Override
    public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (((Boolean)state.m_61143_((Property)WATER)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        } else if (((Boolean)state.m_61143_((Property)LAVA)).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)world));
        }
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, PlayerDetectorTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed)));
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)((BlockState)((BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76195_))).m_61124_((Property)FACING, (Comparable)context.m_43719_());
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (Integer)state.m_61143_((Property)FOAMED) > 0 ? Shapes.m_83144_() : SHAPES[((Direction)state.m_61143_((Property)FACING)).m_122411_()];
    }

    public BlockState m_7417_(BlockState state, Direction direction, BlockState neighbor, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if ((Integer)state.m_61143_((Property)FOAMED) > 0) {
            return state;
        }
        return direction == ((Direction)state.m_61143_((Property)FACING)).m_122424_() && !PlayerDetectorBlock.m_49863_((LevelReader)world, (BlockPos)neighborPos, (Direction)direction.m_122424_()) ? Blocks.f_50016_.m_49966_() : state;
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof PlayerDetectorTileEntity) {
            return ((PlayerDetectorTileEntity)tile).setColor(dir, color);
        }
        return false;
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return null;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        switch ((Integer)state.m_61143_((Property)FOAMED)) {
            case 1: {
                return new NonTranslucentBlockModel((BlockState)IC2Blocks.CFOAM_WET.m_49966_().m_61124_(CFoamBlock.FOAM_TYPE, (Comparable)((Object)CFoamBlock.FoamType.CABLE)), (IBlockModel)IC2Blocks.CFOAM_WET);
            }
            case 2: {
                return CamouflageModel.getInstance();
            }
        }
        return new PlayerDetectorModel(state, "misc", "player_detector_active", "player_detector_inactive");
    }
}

