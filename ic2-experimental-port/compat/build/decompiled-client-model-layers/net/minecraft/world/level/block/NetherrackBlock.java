/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class NetherrackBlock
extends Block
implements BonemealableBlock {
    public NetherrackBlock(BlockBehaviour.Properties p_54995_) {
        super(p_54995_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_55002_, BlockPos p_55003_, BlockState p_55004_, boolean p_55005_) {
        if (!p_55002_.m_8055_(p_55003_.m_7494_()).m_60631_(p_55002_, p_55003_)) {
            return false;
        }
        for (BlockPos $$4 : BlockPos.m_121940_(p_55003_.m_7918_(-1, -1, -1), p_55003_.m_7918_(1, 1, 1))) {
            if (!p_55002_.m_8055_($$4).m_204336_(BlockTags.f_13077_)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean m_214167_(Level p_221816_, RandomSource p_221817_, BlockPos p_221818_, BlockState p_221819_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221811_, RandomSource p_221812_, BlockPos p_221813_, BlockState p_221814_) {
        boolean $$4 = false;
        boolean $$5 = false;
        for (BlockPos $$6 : BlockPos.m_121940_(p_221813_.m_7918_(-1, -1, -1), p_221813_.m_7918_(1, 1, 1))) {
            BlockState $$7 = p_221811_.m_8055_($$6);
            if ($$7.m_60713_(Blocks.f_50690_)) {
                $$5 = true;
            }
            if ($$7.m_60713_(Blocks.f_50699_)) {
                $$4 = true;
            }
            if (!$$5 || !$$4) continue;
            break;
        }
        if ($$5 && $$4) {
            p_221811_.m_7731_(p_221813_, p_221812_.m_188499_() ? Blocks.f_50690_.m_49966_() : Blocks.f_50699_.m_49966_(), 3);
        } else if ($$5) {
            p_221811_.m_7731_(p_221813_, Blocks.f_50690_.m_49966_(), 3);
        } else if ($$4) {
            p_221811_.m_7731_(p_221813_, Blocks.f_50699_.m_49966_(), 3);
        }
    }
}

