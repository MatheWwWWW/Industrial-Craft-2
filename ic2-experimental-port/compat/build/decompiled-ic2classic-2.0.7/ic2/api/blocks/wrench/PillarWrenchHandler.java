/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.api.blocks.wrench;

import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.wrench.BaseWrenchHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class PillarWrenchHandler
extends BaseWrenchHandler {
    public static final IWrenchable INSTANCE = new PillarWrenchHandler();

    @Override
    public Direction getFacing(BlockState state, Level world, BlockPos pos) {
        switch ((Direction.Axis)state.m_61143_((Property)RotatedPillarBlock.f_55923_)) {
            case X: {
                return Direction.EAST;
            }
            case Y: {
                return Direction.UP;
            }
            case Z: {
                return Direction.SOUTH;
            }
        }
        return null;
    }

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        return state.m_61143_((Property)RotatedPillarBlock.f_55923_) != side.m_122434_();
    }

    @Override
    public boolean setFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        return world.m_46597_(pos, (BlockState)state.m_61124_((Property)RotatedPillarBlock.f_55923_, (Comparable)side.m_122434_()));
    }
}

