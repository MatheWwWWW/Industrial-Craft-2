/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class BlockPileFeature
extends Feature<BlockPileConfiguration> {
    public BlockPileFeature(Codec<BlockPileConfiguration> p_65262_) {
        super(p_65262_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<BlockPileConfiguration> p_159473_) {
        BlockPos $$1 = p_159473_.m_159777_();
        WorldGenLevel $$2 = p_159473_.m_159774_();
        RandomSource $$3 = p_159473_.m_225041_();
        BlockPileConfiguration $$4 = p_159473_.m_159778_();
        if ($$1.m_123342_() < $$2.m_141937_() + 5) {
            return false;
        }
        int $$5 = 2 + $$3.m_188503_(2);
        int $$6 = 2 + $$3.m_188503_(2);
        for (BlockPos $$7 : BlockPos.m_121940_($$1.m_7918_(-$$5, 0, -$$6), $$1.m_7918_($$5, 1, $$6))) {
            int $$9;
            int $$8 = $$1.m_123341_() - $$7.m_123341_();
            if ((float)($$8 * $$8 + ($$9 = $$1.m_123343_() - $$7.m_123343_()) * $$9) <= $$3.m_188501_() * 10.0f - $$3.m_188501_() * 6.0f) {
                this.m_224948_($$2, $$7, $$3, $$4);
                continue;
            }
            if (!((double)$$3.m_188501_() < 0.031)) continue;
            this.m_224948_($$2, $$7, $$3, $$4);
        }
        return true;
    }

    private boolean m_224944_(LevelAccessor p_224945_, BlockPos p_224946_, RandomSource p_224947_) {
        BlockPos $$3 = p_224946_.m_7495_();
        BlockState $$4 = p_224945_.m_8055_($$3);
        if ($$4.m_60713_(Blocks.f_152481_)) {
            return p_224947_.m_188499_();
        }
        return $$4.m_60783_(p_224945_, $$3, Direction.UP);
    }

    private void m_224948_(LevelAccessor p_224949_, BlockPos p_224950_, RandomSource p_224951_, BlockPileConfiguration p_224952_) {
        if (p_224949_.m_46859_(p_224950_) && this.m_224944_(p_224949_, p_224950_, p_224951_)) {
            p_224949_.m_7731_(p_224950_, p_224952_.f_67540_.m_213972_(p_224951_, p_224950_), 4);
        }
    }
}

