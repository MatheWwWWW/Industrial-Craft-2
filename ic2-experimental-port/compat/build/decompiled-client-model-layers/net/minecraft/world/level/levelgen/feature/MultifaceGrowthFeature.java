/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;

public class MultifaceGrowthFeature
extends Feature<MultifaceGrowthConfiguration> {
    public MultifaceGrowthFeature(Codec<MultifaceGrowthConfiguration> p_225156_) {
        super(p_225156_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<MultifaceGrowthConfiguration> p_225165_) {
        WorldGenLevel $$1 = p_225165_.m_159774_();
        BlockPos $$2 = p_225165_.m_159777_();
        RandomSource $$3 = p_225165_.m_225041_();
        MultifaceGrowthConfiguration $$4 = p_225165_.m_159778_();
        if (!MultifaceGrowthFeature.m_225166_($$1.m_8055_($$2))) {
            return false;
        }
        List<Direction> $$5 = $$4.m_225399_($$3);
        if (MultifaceGrowthFeature.m_225157_($$1, $$2, $$1.m_8055_($$2), $$4, $$3, $$5)) {
            return true;
        }
        BlockPos.MutableBlockPos $$6 = $$2.m_122032_();
        block0: for (Direction $$7 : $$5) {
            $$6.m_122190_($$2);
            List<Direction> $$8 = $$4.m_225401_($$3, $$7.m_122424_());
            for (int $$9 = 0; $$9 < $$4.f_225383_; ++$$9) {
                $$6.m_122159_($$2, $$7);
                BlockState $$10 = $$1.m_8055_($$6);
                if (!MultifaceGrowthFeature.m_225166_($$10) && !$$10.m_60713_($$4.f_225382_)) continue block0;
                if (!MultifaceGrowthFeature.m_225157_($$1, $$6, $$10, $$4, $$3, $$8)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean m_225157_(WorldGenLevel p_225158_, BlockPos p_225159_, BlockState p_225160_, MultifaceGrowthConfiguration p_225161_, RandomSource p_225162_, List<Direction> p_225163_) {
        BlockPos.MutableBlockPos $$6 = p_225159_.m_122032_();
        for (Direction $$7 : p_225163_) {
            BlockState $$8 = p_225158_.m_8055_($$6.m_122159_(p_225159_, $$7));
            if (!$$8.m_204341_(p_225161_.f_225388_)) continue;
            BlockState $$9 = p_225161_.f_225382_.m_153940_(p_225160_, p_225158_, p_225159_, $$7);
            if ($$9 == null) {
                return false;
            }
            p_225158_.m_7731_(p_225159_, $$9, 3);
            p_225158_.m_46865_(p_225159_).m_8113_(p_225159_);
            if (p_225162_.m_188501_() < p_225161_.f_225387_) {
                p_225161_.f_225382_.m_213612_().m_221630_($$9, p_225158_, p_225159_, $$7, p_225162_, true);
            }
            return true;
        }
        return false;
    }

    private static boolean m_225166_(BlockState p_225167_) {
        return p_225167_.m_60795_() || p_225167_.m_60713_(Blocks.f_49990_);
    }
}

