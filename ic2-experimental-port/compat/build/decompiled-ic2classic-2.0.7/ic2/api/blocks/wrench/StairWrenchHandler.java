/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Half
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.api.blocks.wrench;

import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.wrench.BaseWrenchHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;

public class StairWrenchHandler
extends BaseWrenchHandler {
    public static final IWrenchable INSTANCE = new StairWrenchHandler();

    @Override
    public Direction getFacing(BlockState state, Level world, BlockPos pos) {
        return ((Direction)state.m_61143_((Property)StairBlock.f_56841_)).m_122424_();
    }

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        if (side.m_122434_().m_122478_()) {
            return side == Direction.DOWN ? state.m_61143_((Property)StairBlock.f_56842_) == Half.TOP : state.m_61143_((Property)StairBlock.f_56842_) == Half.BOTTOM;
        }
        return ((Direction)state.m_61143_((Property)StairBlock.f_56841_)).m_122424_() != side;
    }

    @Override
    public boolean setFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        if (side.m_122434_().m_122478_()) {
            return world.m_46597_(pos, (BlockState)state.m_61124_((Property)StairBlock.f_56842_, (Comparable)(side == Direction.UP ? Half.TOP : Half.BOTTOM)));
        }
        return world.m_46597_(pos, (BlockState)state.m_61124_((Property)StairBlock.f_56841_, (Comparable)side.m_122424_()));
    }
}

