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
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.base.blocks;

import ic2.core.IC2;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.blocks.BaseTexturedBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class BaseFacingBlock<T extends BaseTileEntity>
extends BaseTexturedBlock
implements IStateController<T>,
IRotatableBlock,
IBlockModel {
    public static final DirectionProperty FACING = IC2Properties.ALL_FACINGS;

    public BaseFacingBlock(String blockName, BlockBehaviour.Properties properties, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, properties, provider, tile);
        this.setDefaultState();
    }

    protected void setDefaultState() {
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING});
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)this.getFacing(context));
    }

    protected Direction getFacing(BlockPlaceContext context) {
        return context.m_8125_().m_122424_();
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (!(tile instanceof BaseTileEntity)) {
            return;
        }
        BaseTileEntity base = (BaseTileEntity)tile;
        base.lock();
        base.setFacing((Direction)state.m_61143_((Property)FACING));
        if (stack.m_41788_()) {
            base.setCustomName(stack.m_41786_());
        }
        this.setPlaceData(base, state, placer, stack);
        base.unlock();
    }

    protected void setPlaceData(BaseTileEntity tile, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    @Override
    public boolean hasRotation(BlockState state) {
        return true;
    }

    public BlockState m_6843_(BlockState state, Rotation rotation) {
        return state.m_61138_((Property)FACING) ? (BlockState)state.m_61124_((Property)FACING, (Comparable)rotation.m_55954_((Direction)state.m_61143_((Property)FACING))) : state;
    }

    public BlockState m_6943_(BlockState state, Mirror mirror) {
        return state.m_61138_((Property)FACING) ? (BlockState)state.m_61124_((Property)FACING, (Comparable)mirror.m_54848_((Direction)state.m_61143_((Property)FACING))) : state;
    }

    @Override
    public Direction getRotation(BlockState state) {
        return (Direction)state.m_61143_((Property)FACING);
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, T tile) {
        tile.withState((Property<Direction>)FACING, (Direction)tile.getFacing());
    }
}

