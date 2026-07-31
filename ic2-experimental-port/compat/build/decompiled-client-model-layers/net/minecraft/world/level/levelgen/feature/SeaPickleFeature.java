/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class SeaPickleFeature
extends Feature<CountConfiguration> {
    public SeaPickleFeature(Codec<CountConfiguration> p_66754_) {
        super(p_66754_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<CountConfiguration> p_160316_) {
        int $$1 = 0;
        RandomSource $$2 = p_160316_.m_225041_();
        WorldGenLevel $$3 = p_160316_.m_159774_();
        BlockPos $$4 = p_160316_.m_159777_();
        int $$5 = p_160316_.m_159778_().m_160725_().m_214085_($$2);
        for (int $$6 = 0; $$6 < $$5; ++$$6) {
            int $$7 = $$2.m_188503_(8) - $$2.m_188503_(8);
            int $$8 = $$2.m_188503_(8) - $$2.m_188503_(8);
            int $$9 = $$3.m_6924_(Heightmap.Types.OCEAN_FLOOR, $$4.m_123341_() + $$7, $$4.m_123343_() + $$8);
            BlockPos $$10 = new BlockPos($$4.m_123341_() + $$7, $$9, $$4.m_123343_() + $$8);
            BlockState $$11 = (BlockState)Blocks.f_50567_.m_49966_().m_61124_(SeaPickleBlock.f_56074_, $$2.m_188503_(4) + 1);
            if (!$$3.m_8055_($$10).m_60713_(Blocks.f_49990_) || !$$11.m_60710_($$3, $$10)) continue;
            $$3.m_7731_($$10, $$11, 2);
            ++$$1;
        }
        return $$1 > 0;
    }
}

