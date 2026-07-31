/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package ic2.core.block.storage;

import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.EnergyStorageBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TransformerBlock
extends BaseActivityBlock<BaseTileEntity>
implements IBlockModel,
IRotatableBlock {
    public TransformerBlock(String blockName, IBlockDropProvider drop, ITextureProvider provider, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, EnergyStorageBlock.BASE_ENERGY_STORAGE, provider, type);
        this.setDropProvider(drop);
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

