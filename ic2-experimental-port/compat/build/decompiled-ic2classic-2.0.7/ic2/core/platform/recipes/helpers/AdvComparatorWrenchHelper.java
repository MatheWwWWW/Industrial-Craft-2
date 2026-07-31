/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.platform.recipes.helpers;

import ic2.api.blocks.wrench.BaseWrenchHandler;
import ic2.core.block.cables.AdvancedComparatorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class AdvComparatorWrenchHelper
extends BaseWrenchHandler {
    @Override
    public Direction getFacing(BlockState state, Level world, BlockPos pos) {
        return (Direction)state.m_61143_((Property)AdvancedComparatorBlock.ROTATION);
    }

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        int index = AdvancedComparatorBlock.getRotation((Direction)state.m_61143_((Property)AdvancedComparatorBlock.ROTATION), ((Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING)).m_122434_());
        int newIndex = AdvancedComparatorBlock.getRotation(side, ((Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING)).m_122434_());
        return newIndex != index && newIndex != -1;
    }

    @Override
    public boolean setFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        int newIndex;
        int index = AdvancedComparatorBlock.getRotation((Direction)state.m_61143_((Property)AdvancedComparatorBlock.ROTATION), ((Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING)).m_122434_());
        if (index == (newIndex = AdvancedComparatorBlock.getRotation(side, ((Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING)).m_122434_())) || newIndex == -1) {
            return false;
        }
        return world.m_46597_(pos, (BlockState)state.m_61124_((Property)AdvancedComparatorBlock.ROTATION, (Comparable)AdvancedComparatorBlock.getFacingForRotation(((Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING)).m_122434_(), newIndex)));
    }
}

