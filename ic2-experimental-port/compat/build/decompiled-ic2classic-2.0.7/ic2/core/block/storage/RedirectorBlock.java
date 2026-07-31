/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.storage;

import ic2.api.util.DirectionList;
import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.EnergyStorageBlock;
import ic2.core.block.storage.tiles.RedirectorMasterTileEntity;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class RedirectorBlock
extends BaseFacingBlock<BaseTileEntity> {
    boolean master;

    public RedirectorBlock(String blockName, boolean master, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, EnergyStorageBlock.BASE_ENERGY_STORAGE, ITextureProvider.noStateIC2("electric/cable/redirector/" + (master ? "master" : "slave")), tile);
        this.setDropProvider(IBlockDropProvider.SELF_OR_MACHINE);
        this.master = master;
    }

    @Override
    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!this.master && this.getFacing(worldIn, pos, (Direction)state.m_61143_((Property)FACING)) == null) {
            Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)this));
            worldIn.m_7471_(pos, false);
            return;
        }
        super.m_6861_(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext context) {
        if (!this.master) {
            Direction facing = this.getFacing(context.m_43725_(), context.m_8083_(), null);
            return facing == null ? null : (BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)facing);
        }
        return super.m_5573_(context);
    }

    public Direction getFacing(Level world, BlockPos pos, Direction presentDir) {
        if (presentDir != null) {
            BlockEntity tile = DirectionList.getNeighborTile(world, pos, presentDir);
            return tile instanceof RedirectorMasterTileEntity && ((RedirectorMasterTileEntity)tile).getFacing().m_122424_() != presentDir ? presentDir : null;
        }
        for (Direction dir : DirectionList.ALL) {
            BlockEntity tile = world.m_7702_(pos.m_121945_(dir));
            if (!(tile instanceof RedirectorMasterTileEntity) || dir == ((RedirectorMasterTileEntity)tile).getFacing().m_122424_()) continue;
            return dir;
        }
        return null;
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        if (context.m_43723_() == null) {
            return Direction.NORTH;
        }
        int pitch = Math.round(context.m_43723_().m_146909_());
        return pitch >= 65 ? Direction.UP : (pitch <= -65 ? Direction.DOWN : context.m_8125_().m_122424_());
    }
}

