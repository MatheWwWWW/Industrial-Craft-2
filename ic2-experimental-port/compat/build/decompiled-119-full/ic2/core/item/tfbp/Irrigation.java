/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Irrigation
extends TerraformerBase {
    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        if (level.f_46441_.m_188503_(48000) == 0) {
            level.m_6106_().m_5565_(true);
            return true;
        }
        if ((blockPos = TileEntityTerra.getFirstBlockFrom(level, blockPos, 10)) == null) {
            return false;
        }
        if (TileEntityTerra.switchGround(level, blockPos, Blocks.f_49992_, Blocks.f_50493_.m_49966_(), true)) {
            TileEntityTerra.switchGround(level, blockPos, Blocks.f_49992_, Blocks.f_50493_.m_49966_(), true);
            return true;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        if (block instanceof BonemealableBlock && ((BonemealableBlock)block).m_7370_((BlockGetter)level, blockPos, blockState, false)) {
            ((BonemealableBlock)block).m_214148_((ServerLevel)level, level.f_46441_, blockPos, blockState);
            return true;
        }
        if (block == Blocks.f_50359_) {
            return Irrigation.spreadGrass(level, blockPos.m_122012_()) || Irrigation.spreadGrass(level, blockPos.m_122029_()) || Irrigation.spreadGrass(level, blockPos.m_122019_()) || Irrigation.spreadGrass(level, blockPos.m_122024_());
        }
        if (blockState.m_204336_(BlockTags.f_13106_)) {
            BlockPos blockPos2 = blockPos.m_7494_();
            level.m_46597_(blockPos2, blockState);
            BlockState blockState2 = Irrigation.getLeaves(level, blockPos);
            if (blockState2 != null) {
                Irrigation.createLeaves(level, blockPos2, blockState2);
            }
            return true;
        }
        if (block == Blocks.f_50083_) {
            level.m_7471_(blockPos, false);
            return true;
        }
        return false;
    }

    private static BlockState getLeaves(Level level, BlockPos blockPos) {
        for (Direction direction : Util.HORIZONTAL_DIRS) {
            BlockPos blockPos2 = blockPos.m_121945_(direction);
            BlockState blockState = level.m_8055_(blockPos2);
            if (!blockState.m_204336_(BlockTags.f_13035_)) continue;
            return blockState;
        }
        return null;
    }

    private static void createLeaves(Level level, BlockPos blockPos, BlockState blockState) {
        BlockPos blockPos2 = blockPos.m_7494_();
        if (level.m_46859_(blockPos2)) {
            level.m_46597_(blockPos2, blockState);
        }
        for (Direction direction : Util.HORIZONTAL_DIRS) {
            BlockPos blockPos3 = blockPos.m_121945_(direction);
            if (!level.m_46859_(blockPos3)) continue;
            level.m_46597_(blockPos3, blockState);
        }
    }

    private static boolean spreadGrass(Level level, BlockPos blockPos) {
        if (level.f_46441_.m_188499_()) {
            return false;
        }
        if ((blockPos = TileEntityTerra.getFirstBlockFrom(level, blockPos, 0)) == null) {
            return false;
        }
        Block block = level.m_8055_(blockPos).m_60734_();
        if (block == Blocks.f_50493_) {
            level.m_46597_(blockPos, Blocks.f_50034_.m_49966_());
            return true;
        }
        if (block == Blocks.f_50034_) {
            level.m_46597_(blockPos.m_7494_(), Blocks.f_50359_.m_49966_());
            return true;
        }
        return false;
    }
}

