/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

public abstract class SpreadingSnowyDirtBlock
extends SnowyDirtBlock {
    protected SpreadingSnowyDirtBlock(BlockBehaviour.Properties p_56817_) {
        super(p_56817_);
    }

    private static boolean m_56823_(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
        BlockPos $$3 = p_56826_.m_7494_();
        BlockState $$4 = p_56825_.m_8055_($$3);
        if ($$4.m_60713_(Blocks.f_50125_) && $$4.m_61143_(SnowLayerBlock.f_56581_) == 1) {
            return true;
        }
        if ($$4.m_60819_().m_76186_() == 8) {
            return false;
        }
        int $$5 = LayerLightEngine.m_75667_(p_56825_, p_56824_, p_56826_, $$4, $$3, Direction.UP, $$4.m_60739_(p_56825_, $$3));
        return $$5 < p_56825_.m_7469_();
    }

    private static boolean m_56827_(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
        BlockPos $$3 = p_56830_.m_7494_();
        return SpreadingSnowyDirtBlock.m_56823_(p_56828_, p_56829_, p_56830_) && !p_56829_.m_6425_($$3).m_205070_(FluidTags.f_13131_);
    }

    @Override
    public void m_213898_(BlockState p_222508_, ServerLevel p_222509_, BlockPos p_222510_, RandomSource p_222511_) {
        if (!SpreadingSnowyDirtBlock.m_56823_(p_222508_, p_222509_, p_222510_)) {
            p_222509_.m_46597_(p_222510_, Blocks.f_50493_.m_49966_());
            return;
        }
        if (p_222509_.m_46803_(p_222510_.m_7494_()) >= 9) {
            BlockState $$4 = this.m_49966_();
            for (int $$5 = 0; $$5 < 4; ++$$5) {
                BlockPos $$6 = p_222510_.m_7918_(p_222511_.m_188503_(3) - 1, p_222511_.m_188503_(5) - 3, p_222511_.m_188503_(3) - 1);
                if (!p_222509_.m_8055_($$6).m_60713_(Blocks.f_50493_) || !SpreadingSnowyDirtBlock.m_56827_($$4, p_222509_, $$6)) continue;
                p_222509_.m_46597_($$6, (BlockState)$$4.m_61124_(f_56637_, p_222509_.m_8055_($$6.m_7494_()).m_60713_(Blocks.f_50125_)));
            }
        }
    }
}

