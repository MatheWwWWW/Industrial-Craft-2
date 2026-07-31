/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.piston.PistonBaseBlock
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
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class PistonWrenchHandler
extends BaseWrenchHandler {
    public static final IWrenchable INSTANCE = new PistonWrenchHandler();

    @Override
    public Direction getFacing(BlockState state, Level world, BlockPos pos) {
        return (Direction)state.m_61143_((Property)PistonBaseBlock.f_52588_);
    }

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        return state.m_61143_((Property)PistonBaseBlock.f_52588_) != side && (Boolean)state.m_61143_((Property)PistonBaseBlock.f_60153_) == false;
    }

    @Override
    public boolean setFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        if (((Boolean)state.m_61143_((Property)PistonBaseBlock.f_60153_)).booleanValue()) {
            return false;
        }
        return world.m_46597_(pos, (BlockState)state.m_61124_((Property)PistonBaseBlock.f_52588_, (Comparable)side));
    }

    @Override
    public boolean canRemoveBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public double getDropRate(BlockState state, Level world, BlockPos pos, Player player) {
        return 1.0;
    }
}

