/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.Cultivation;
import ic2.core.item.tfbp.TerraformerBase;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Desertification
extends TerraformerBase {
    @Override
    boolean terraform(Level level, BlockPos blockPos) {
        if ((blockPos = TileEntityTerra.getFirstBlockFrom(level, blockPos, 10)) == null) {
            return false;
        }
        BlockState blockState = Blocks.f_49992_.m_49966_();
        if (TileEntityTerra.switchGround(level, blockPos, Blocks.f_50493_, blockState, false) || TileEntityTerra.switchGround(level, blockPos, Blocks.f_50034_, blockState, false) || TileEntityTerra.switchGround(level, blockPos, Blocks.f_50093_, blockState, false)) {
            TileEntityTerra.switchGround(level, blockPos, Blocks.f_50493_, blockState, false);
            return true;
        }
        BlockState blockState2 = level.m_8055_(blockPos);
        Block block = blockState2.m_60734_();
        if (block == Blocks.f_49990_ || block == Blocks.f_50125_ || blockState2.m_204336_(BlockTags.f_13035_) || Desertification.isPlant(block)) {
            level.m_7471_(blockPos, false);
            if (Desertification.isPlant(level.m_8055_(blockPos.m_7494_()).m_60734_())) {
                level.m_7471_(blockPos.m_7494_(), false);
            }
            return true;
        }
        if (block == Blocks.f_50126_ || block == Blocks.f_50125_) {
            level.m_46597_(blockPos, Blocks.f_49990_.m_49966_());
            return true;
        }
        if ((blockState2.m_204336_(BlockTags.f_13090_) || blockState2.m_204336_(BlockTags.f_13105_)) && level.f_46441_.m_188503_(15) == 0) {
            level.m_46597_(blockPos, Blocks.f_50083_.m_49966_());
            return true;
        }
        return false;
    }

    private static boolean isPlant(Block block) {
        for (BlockState blockState : Cultivation.plants) {
            if (blockState.m_60734_() != block) continue;
            return true;
        }
        return block.m_204297_().m_203656_(BlockTags.f_13104_) || block.m_204297_().m_203656_(BlockTags.f_13073_) || block.m_204297_().m_203656_(BlockTags.f_13041_);
    }
}

