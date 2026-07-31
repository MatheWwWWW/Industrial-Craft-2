/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SnowLayerBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class Chilling
extends TerraformerBase {
    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        if ((blockPos = TileEntityTerra.getFirstBlockFrom(level, blockPos, 10)) == null) {
            return false;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        if (block == Blocks.f_49990_) {
            level.m_46597_(blockPos, Blocks.f_50126_.m_49966_());
            return true;
        }
        if (block == Blocks.f_50126_) {
            BlockPos blockPos2 = blockPos.m_7495_();
            Block block2 = level.m_8055_(blockPos2).m_60734_();
            if (block2 == Blocks.f_49990_) {
                level.m_46597_(blockPos2, Blocks.f_50126_.m_49966_());
                return true;
            }
        } else if (block == Blocks.f_50125_) {
            if (Chilling.isSurroundedBySnow(level, blockPos)) {
                level.m_46597_(blockPos, Blocks.f_50127_.m_49966_());
                return true;
            }
            int n = (Integer)blockState.m_61143_((Property)SnowLayerBlock.f_56581_);
            if (SnowLayerBlock.f_56581_.m_6908_().contains(n + 1)) {
                level.m_46597_(blockPos, (BlockState)blockState.m_61124_((Property)SnowLayerBlock.f_56581_, (Comparable)Integer.valueOf(n + 1)));
                return true;
            }
        }
        blockPos = blockPos.m_7494_();
        if (Blocks.f_50125_.m_49966_().m_60710_((LevelReader)level, blockPos) || block == Blocks.f_50126_) {
            level.m_46597_(blockPos, Blocks.f_50125_.m_49966_());
            return true;
        }
        return false;
    }

    private static boolean isSurroundedBySnow(Level level, BlockPos blockPos) {
        for (Direction direction : Util.HORIZONTAL_DIRS) {
            if (Chilling.isSnowHere(level, blockPos.m_121945_(direction))) continue;
            return false;
        }
        return true;
    }

    private static boolean isSnowHere(Level level, BlockPos blockPos) {
        int n = blockPos.m_123342_();
        if ((blockPos = TileEntityTerra.getFirstBlockFrom(level, blockPos, 16)) == null || n > blockPos.m_123342_()) {
            return false;
        }
        Block block = level.m_8055_(blockPos).m_60734_();
        if (block == Blocks.f_50127_ || block == Blocks.f_50125_) {
            return true;
        }
        blockPos = blockPos.m_7494_();
        if (Blocks.f_50125_.m_49966_().m_60710_((LevelReader)level, blockPos) || block == Blocks.f_50126_) {
            level.m_46597_(blockPos, Blocks.f_50125_.m_49966_());
        }
        return false;
    }
}

