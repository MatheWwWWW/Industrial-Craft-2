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
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class SeagrassFeature
extends Feature<ProbabilityFeatureConfiguration> {
    public SeagrassFeature(Codec<ProbabilityFeatureConfiguration> p_66768_) {
        super(p_66768_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> p_160318_) {
        boolean $$1 = false;
        RandomSource $$2 = p_160318_.m_225041_();
        WorldGenLevel $$3 = p_160318_.m_159774_();
        BlockPos $$4 = p_160318_.m_159777_();
        ProbabilityFeatureConfiguration $$5 = p_160318_.m_159778_();
        int $$6 = $$2.m_188503_(8) - $$2.m_188503_(8);
        int $$7 = $$2.m_188503_(8) - $$2.m_188503_(8);
        int $$8 = $$3.m_6924_(Heightmap.Types.OCEAN_FLOOR, $$4.m_123341_() + $$6, $$4.m_123343_() + $$7);
        BlockPos $$9 = new BlockPos($$4.m_123341_() + $$6, $$8, $$4.m_123343_() + $$7);
        if ($$3.m_8055_($$9).m_60713_(Blocks.f_49990_)) {
            BlockState $$11;
            boolean $$10 = $$2.m_188500_() < (double)$$5.f_67859_;
            BlockState blockState = $$11 = $$10 ? Blocks.f_50038_.m_49966_() : Blocks.f_50037_.m_49966_();
            if ($$11.m_60710_($$3, $$9)) {
                if ($$10) {
                    BlockState $$12 = (BlockState)$$11.m_61124_(TallSeagrassBlock.f_154740_, DoubleBlockHalf.UPPER);
                    BlockPos $$13 = $$9.m_7494_();
                    if ($$3.m_8055_($$13).m_60713_(Blocks.f_49990_)) {
                        $$3.m_7731_($$9, $$11, 2);
                        $$3.m_7731_($$13, $$12, 2);
                    }
                } else {
                    $$3.m_7731_($$9, $$11, 2);
                }
                $$1 = true;
            }
        }
        return $$1;
    }
}

