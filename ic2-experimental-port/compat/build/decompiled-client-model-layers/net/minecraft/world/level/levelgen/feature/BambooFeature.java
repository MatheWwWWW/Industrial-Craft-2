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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class BambooFeature
extends Feature<ProbabilityFeatureConfiguration> {
    private static final BlockState f_65131_ = (BlockState)((BlockState)((BlockState)Blocks.f_50571_.m_49966_().m_61124_(BambooBlock.f_48869_, 1)).m_61124_(BambooBlock.f_48870_, BambooLeaves.NONE)).m_61124_(BambooBlock.f_48871_, 0);
    private static final BlockState f_65132_ = (BlockState)((BlockState)f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.LARGE)).m_61124_(BambooBlock.f_48871_, 1);
    private static final BlockState f_65133_ = (BlockState)f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.LARGE);
    private static final BlockState f_65134_ = (BlockState)f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.SMALL);

    public BambooFeature(Codec<ProbabilityFeatureConfiguration> p_65137_) {
        super(p_65137_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> p_159438_) {
        int $$1 = 0;
        BlockPos $$2 = p_159438_.m_159777_();
        WorldGenLevel $$3 = p_159438_.m_159774_();
        RandomSource $$4 = p_159438_.m_225041_();
        ProbabilityFeatureConfiguration $$5 = p_159438_.m_159778_();
        BlockPos.MutableBlockPos $$6 = $$2.m_122032_();
        BlockPos.MutableBlockPos $$7 = $$2.m_122032_();
        if ($$3.m_46859_($$6)) {
            if (Blocks.f_50571_.m_49966_().m_60710_($$3, $$6)) {
                int $$8 = $$4.m_188503_(12) + 5;
                if ($$4.m_188501_() < $$5.f_67859_) {
                    int $$9 = $$4.m_188503_(4) + 1;
                    for (int $$10 = $$2.m_123341_() - $$9; $$10 <= $$2.m_123341_() + $$9; ++$$10) {
                        for (int $$11 = $$2.m_123343_() - $$9; $$11 <= $$2.m_123343_() + $$9; ++$$11) {
                            int $$13;
                            int $$12 = $$10 - $$2.m_123341_();
                            if ($$12 * $$12 + ($$13 = $$11 - $$2.m_123343_()) * $$13 > $$9 * $$9) continue;
                            $$7.m_122178_($$10, $$3.m_6924_(Heightmap.Types.WORLD_SURFACE, $$10, $$11) - 1, $$11);
                            if (!BambooFeature.m_159759_($$3.m_8055_($$7))) continue;
                            $$3.m_7731_($$7, Blocks.f_50599_.m_49966_(), 2);
                        }
                    }
                }
                for (int $$14 = 0; $$14 < $$8 && $$3.m_46859_($$6); ++$$14) {
                    $$3.m_7731_($$6, f_65131_, 2);
                    $$6.m_122175_(Direction.UP, 1);
                }
                if ($$6.m_123342_() - $$2.m_123342_() >= 3) {
                    $$3.m_7731_($$6, f_65132_, 2);
                    $$3.m_7731_($$6.m_122175_(Direction.DOWN, 1), f_65133_, 2);
                    $$3.m_7731_($$6.m_122175_(Direction.DOWN, 1), f_65134_, 2);
                }
            }
            ++$$1;
        }
        return $$1 > 0;
    }
}

