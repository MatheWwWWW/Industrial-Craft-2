/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.cables.luminator;

import ic2.core.IC2;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.cables.luminator.ConstructionLightTileEntity;
import ic2.core.block.rendering.block.ConstructionLightModel;
import ic2.core.item.block.ConstructionLightItemBlock;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ConstructionLightBlock
extends IC2ContainerBlock
implements ICustomBlockModel,
IStateController<ConstructionLightTileEntity>,
IDualLogged {
    public static final DirectionProperty FACING = IC2Properties.HORIZONTAL_FACINGS;
    public static final BooleanProperty TOP = BooleanProperty.m_61465_((String)"top");
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final VoxelShape BOTTOM_MAIN = Shapes.m_83124_((VoxelShape)Block.m_49796_((double)7.0, (double)0.0, (double)0.0, (double)9.0, (double)1.0, (double)16.0), (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)0.0, (double)0.0, (double)7.0, (double)16.0, (double)1.0, (double)9.0), Block.m_49796_((double)7.0, (double)0.0, (double)7.0, (double)9.0, (double)27.0, (double)9.0)}).m_83296_();
    public static final VoxelShape[] BOTTOM_SHAPES = new VoxelShape[]{Shapes.m_83124_((VoxelShape)BOTTOM_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)25.0, (double)5.0, (double)9.0, (double)27.0, (double)7.0), Block.m_49796_((double)5.0, (double)24.0, (double)2.0, (double)11.0, (double)28.0, (double)5.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)BOTTOM_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)8.0, (double)25.0, (double)7.0, (double)12.0, (double)27.0, (double)9.0), Block.m_49796_((double)11.0, (double)24.0, (double)5.0, (double)14.0, (double)28.0, (double)11.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)BOTTOM_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)25.0, (double)8.0, (double)9.0, (double)27.0, (double)12.0), Block.m_49796_((double)5.0, (double)24.0, (double)11.0, (double)11.0, (double)28.0, (double)14.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)BOTTOM_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)5.0, (double)25.0, (double)7.0, (double)7.0, (double)27.0, (double)9.0), Block.m_49796_((double)2.0, (double)24.0, (double)5.0, (double)5.0, (double)28.0, (double)11.0)}).m_83296_()};
    public static final VoxelShape TOP_MAIN = Shapes.m_83124_((VoxelShape)Block.m_49796_((double)7.0, (double)-16.0, (double)0.0, (double)9.0, (double)-15.0, (double)16.0), (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)0.0, (double)-16.0, (double)7.0, (double)0.0, (double)-15.0, (double)9.0), Block.m_49796_((double)7.0, (double)-16.0, (double)7.0, (double)9.0, (double)11.0, (double)9.0)}).m_83296_();
    public static final VoxelShape[] TOP_SHAPES = new VoxelShape[]{Shapes.m_83124_((VoxelShape)TOP_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)9.0, (double)5.0, (double)9.0, (double)11.0, (double)7.0), Block.m_49796_((double)5.0, (double)8.0, (double)2.0, (double)11.0, (double)12.0, (double)5.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)TOP_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)8.0, (double)9.0, (double)7.0, (double)12.0, (double)11.0, (double)9.0), Block.m_49796_((double)11.0, (double)8.0, (double)5.0, (double)14.0, (double)12.0, (double)11.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)TOP_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)7.0, (double)9.0, (double)8.0, (double)9.0, (double)11.0, (double)12.0), Block.m_49796_((double)5.0, (double)8.0, (double)11.0, (double)11.0, (double)12.0, (double)14.0)}).m_83296_(), Shapes.m_83124_((VoxelShape)TOP_MAIN, (VoxelShape[])new VoxelShape[]{Block.m_49796_((double)5.0, (double)9.0, (double)7.0, (double)7.0, (double)11.0, (double)9.0), Block.m_49796_((double)2.0, (double)8.0, (double)5.0, (double)5.0, (double)12.0, (double)11.0)}).m_83296_()};

    public ConstructionLightBlock() {
        super("construction_light", BlockBehaviour.Properties.m_60939_((Material)Material.f_76275_).m_60953_(T -> (Boolean)T.m_61143_((Property)ACTIVE) != false ? 15 : 0));
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)TOP, (Comparable)Boolean.valueOf(false))).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public BlockItem createItem() {
        return new ConstructionLightItemBlock((Block)this);
    }

    public void m_6810_(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.m_60734_() != newState.m_60734_()) {
            worldIn.m_7471_((Boolean)state.m_61143_((Property)TOP) != false ? pos.m_7495_() : pos.m_7494_(), isMoving);
            if (((Boolean)state.m_61143_((Property)TOP)).booleanValue()) {
                worldIn.m_46747_(pos);
            }
        }
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING}).m_61104_(new Property[]{TOP}).m_61104_(new Property[]{ACTIVE}).m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA});
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return ((Boolean)state.m_61143_((Property)TOP) != false ? TOP_SHAPES : BOTTOM_SHAPES)[((Direction)state.m_61143_((Property)FACING)).m_122416_()];
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, ConstructionLightTileEntity tile) {
        if (tile.setState((BlockState)((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FACING, (Comparable)tile.getFacing()))) {
            world.m_46597_(pos.m_7495_(), (BlockState)world.m_8055_(pos.m_7495_()).m_61124_((Property)FACING, (Comparable)tile.getFacing()));
        }
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos.m_7494_());
        if (!(tile instanceof BaseTileEntity)) {
            return;
        }
        BaseTileEntity base = (BaseTileEntity)tile;
        base.lock();
        base.setFacing((Direction)state.m_61143_((Property)FACING));
        if (stack.m_41788_()) {
            base.setCustomName(stack.m_41786_());
        }
        base.unlock();
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)super.m_5573_(context).m_61124_((Property)FACING, (Comparable)context.m_8125_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new ConstructionLightModel(state);
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return (Boolean)state.m_61143_((Property)TOP) != false ? IC2Tiles.CONSTRUCTION_LIGHT.m_155264_(pos, state) : null;
    }
}

