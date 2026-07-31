/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 */
package ic2.core.block.transport.fluid;

import ic2.api.util.DirectionList;
import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.utils.helpers.Tool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class PumpBlock
extends BaseFacingBlock<BaseTileEntity> {
    public PumpBlock(String blockName, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, BaseMachineBlock.BASE_MACHINE, provider, tile);
        this.setDropProvider(IBlockDropProvider.SELF);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.SOUTH));
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        return this.createList(context.m_43725_(), context.m_8083_(), context.m_8125_());
    }

    protected Direction createList(Level world, BlockPos pos, Direction defaultFacing) {
        DirectionList list = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            if (!DirectionList.getNeighborCapability(world, pos, dir, ForgeCapabilities.FLUID_HANDLER).isPresent()) continue;
            list = list.add(dir);
        }
        return list.size() != 1 ? defaultFacing : list.getDefaultFacing();
    }
}

