/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.ChestBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.ChestType
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.api.blocks.wrench;

import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.wrench.HorizontalWrenchHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;

public class ChestWrenchHandler
extends HorizontalWrenchHandler {
    public static final IWrenchable INSTANCE = new ChestWrenchHandler();

    @Override
    public boolean canSetFacing(BlockState state, Level world, BlockPos pos, Player player, Direction side) {
        if (state.m_61138_((Property)ChestBlock.f_51479_) && state.m_61143_((Property)ChestBlock.f_51479_) != ChestType.SINGLE) {
            return false;
        }
        return side.m_122434_().m_122479_() && state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_) != side;
    }
}

