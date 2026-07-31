/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage;

import ic2.api.util.DirectionList;
import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.storage.EnergyStorageBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseLoaderBlock
extends BaseFacingBlock<BaseInventoryTileEntity> {
    public BaseLoaderBlock(String blockName, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, EnergyStorageBlock.BASE_ENERGY_STORAGE, provider, tile);
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        DirectionList list = this.getValidFacings(context.m_43725_(), context.m_8083_());
        if (list.size() == 1) {
            return list.getDefaultFacing();
        }
        Direction player = this.getPlayerFacing(context);
        return list.contains(player) ? player : list.getRandomFacing();
    }

    public Direction getPlayerFacing(BlockPlaceContext context) {
        if (context.m_43723_() == null) {
            return Direction.NORTH;
        }
        int pitch = Math.round(context.m_43723_().m_146909_());
        return pitch >= 65 ? Direction.UP : (pitch <= -65 ? Direction.DOWN : context.m_8125_().m_122424_());
    }

    public DirectionList getValidFacings(Level level, BlockPos pos) {
        DirectionList result = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            BlockState state = level.m_8055_(pos.m_121945_(dir));
            if (!state.m_204336_(BlockTags.f_13034_)) continue;
            result = result.add(dir);
        }
        return result.isEmpty() ? DirectionList.ALL : result;
    }
}

