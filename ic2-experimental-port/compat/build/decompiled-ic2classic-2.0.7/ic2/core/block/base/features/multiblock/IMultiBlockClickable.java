/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.base.features.multiblock;

import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface IMultiBlockClickable
extends IClickable {
    default public boolean shouldAbsorbClick(BlockPos blockClicked, BlockState state) {
        return ((BaseMultiBlockTileEntity)((Object)this)).isValid;
    }

    default public boolean shouldLinkToBlock(BlockPos blockClicked, BlockState state) {
        return ((BaseMultiBlockTileEntity)((Object)this)).isValid;
    }

    default public BlockPos getOrigin() {
        return ((BlockEntity)this).m_58899_();
    }

    @Override
    default public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return false;
    }
}

