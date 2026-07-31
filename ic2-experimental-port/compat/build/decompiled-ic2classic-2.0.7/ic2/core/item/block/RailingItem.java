/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.StairsShape
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.item.block;

import ic2.core.block.misc.base.IC2PanelBlock;
import ic2.core.block.misc.tiles.PanelCornerTileEntity;
import ic2.core.item.base.IC2BlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.Vec3;

public class RailingItem
extends IC2BlockItem {
    public RailingItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return true;
    }

    protected BlockState m_5965_(BlockPlaceContext context) {
        BlockState original = context.m_43725_().m_8055_(context.m_8083_());
        if (original.m_60734_() == ((IC2PanelBlock)this.m_40614_()).getCorner()) {
            return original;
        }
        return super.m_5965_(context);
    }

    protected boolean m_7429_(BlockPlaceContext context, BlockState state) {
        if (state.m_60734_() == ((IC2PanelBlock)this.m_40614_()).getCorner()) {
            BlockState original = context.m_43725_().m_8055_(context.m_8083_());
            if (original.m_60734_() instanceof IC2PanelBlock) {
                int index;
                Direction facing = (Direction)original.m_61143_((Property)IC2PanelBlock.FACING);
                int oIndex = this.calcIndex(facing, original.m_61143_(IC2PanelBlock.SHAPE) == StairsShape.INNER_RIGHT);
                BlockPos pos = context.m_8083_();
                Vec3 place = context.m_43720_().m_82492_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                int n = place.m_7096_() < 0.5 ? (place.m_7094_() < 0.5 ? 0 : 3) : (index = place.m_7094_() < 0.5 ? 1 : 2);
                if (oIndex == index) {
                    return false;
                }
                if (super.m_7429_(context, state)) {
                    BlockEntity tile = context.m_43725_().m_7702_(pos);
                    if (!(tile instanceof PanelCornerTileEntity)) {
                        return true;
                    }
                    PanelCornerTileEntity railing = (PanelCornerTileEntity)tile;
                    railing.setColor(this.calcIndex(facing, original.m_61143_(IC2PanelBlock.SHAPE) == StairsShape.INNER_RIGHT), ((IC2PanelBlock)original.m_60734_()).getColor());
                    railing.setColor(index, ((IC2PanelBlock)this.m_40614_()).getColor());
                    return true;
                }
                return false;
            }
            if (original.m_60734_() == ((IC2PanelBlock)this.m_40614_()).getCorner()) {
                BlockPos pos = context.m_8083_();
                BlockEntity tile = context.m_43725_().m_7702_(pos);
                if (tile instanceof PanelCornerTileEntity) {
                    int index;
                    PanelCornerTileEntity other = (PanelCornerTileEntity)tile;
                    Vec3 place = context.m_43720_().m_82492_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                    int n = place.m_7096_() < 0.5 ? (place.m_7094_() < 0.5 ? 0 : 3) : (index = place.m_7094_() < 0.5 ? 1 : 2);
                    if (other.isSet(index)) {
                        return false;
                    }
                    other.setColor(index, ((IC2PanelBlock)this.m_40614_()).getColor());
                    return true;
                }
                return false;
            }
        }
        return super.m_7429_(context, state);
    }

    public int calcIndex(Direction dir, boolean right) {
        return (dir.m_122416_() + (right ? 1 : 0)) % 4;
    }
}

