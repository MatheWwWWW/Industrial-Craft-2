/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

public class BuddingAmethystBlock
extends AmethystBlock {
    public static final int f_152722_ = 5;
    private static final Direction[] f_152723_ = Direction.values();

    public BuddingAmethystBlock(BlockBehaviour.Properties p_152726_) {
        super(p_152726_);
    }

    @Override
    public PushReaction m_5537_(BlockState p_152733_) {
        return PushReaction.DESTROY;
    }

    @Override
    public void m_213898_(BlockState p_220898_, ServerLevel p_220899_, BlockPos p_220900_, RandomSource p_220901_) {
        if (p_220901_.m_188503_(5) != 0) {
            return;
        }
        Direction $$4 = f_152723_[p_220901_.m_188503_(f_152723_.length)];
        BlockPos $$5 = p_220900_.m_121945_($$4);
        BlockState $$6 = p_220899_.m_8055_($$5);
        Block $$7 = null;
        if (BuddingAmethystBlock.m_152734_($$6)) {
            $$7 = Blocks.f_152495_;
        } else if ($$6.m_60713_(Blocks.f_152495_) && $$6.m_61143_(AmethystClusterBlock.f_152006_) == $$4) {
            $$7 = Blocks.f_152494_;
        } else if ($$6.m_60713_(Blocks.f_152494_) && $$6.m_61143_(AmethystClusterBlock.f_152006_) == $$4) {
            $$7 = Blocks.f_152493_;
        } else if ($$6.m_60713_(Blocks.f_152493_) && $$6.m_61143_(AmethystClusterBlock.f_152006_) == $$4) {
            $$7 = Blocks.f_152492_;
        }
        if ($$7 != null) {
            BlockState $$8 = (BlockState)((BlockState)$$7.m_49966_().m_61124_(AmethystClusterBlock.f_152006_, $$4)).m_61124_(AmethystClusterBlock.f_152005_, $$6.m_60819_().m_76152_() == Fluids.f_76193_);
            p_220899_.m_46597_($$5, $$8);
        }
    }

    public static boolean m_152734_(BlockState p_152735_) {
        return p_152735_.m_60795_() || p_152735_.m_60713_(Blocks.f_49990_) && p_152735_.m_60819_().m_76186_() == 8;
    }
}

