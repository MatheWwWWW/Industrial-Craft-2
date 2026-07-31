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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DesertWellFeature
extends Feature<NoneFeatureConfiguration> {
    private static final BlockStatePredicate f_65593_ = BlockStatePredicate.m_61287_(Blocks.f_49992_);
    private final BlockState f_65594_ = Blocks.f_50406_.m_49966_();
    private final BlockState f_65595_ = Blocks.f_50062_.m_49966_();
    private final BlockState f_65596_ = Blocks.f_49990_.m_49966_();

    public DesertWellFeature(Codec<NoneFeatureConfiguration> p_65599_) {
        super(p_65599_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159571_) {
        WorldGenLevel $$1 = p_159571_.m_159774_();
        BlockPos $$2 = p_159571_.m_159777_();
        $$2 = $$2.m_7494_();
        while ($$1.m_46859_($$2) && $$2.m_123342_() > $$1.m_141937_() + 2) {
            $$2 = $$2.m_7495_();
        }
        if (!f_65593_.test($$1.m_8055_($$2))) {
            return false;
        }
        for (int $$3 = -2; $$3 <= 2; ++$$3) {
            for (int $$4 = -2; $$4 <= 2; ++$$4) {
                if (!$$1.m_46859_($$2.m_7918_($$3, -1, $$4)) || !$$1.m_46859_($$2.m_7918_($$3, -2, $$4))) continue;
                return false;
            }
        }
        for (int $$5 = -1; $$5 <= 0; ++$$5) {
            for (int $$6 = -2; $$6 <= 2; ++$$6) {
                for (int $$7 = -2; $$7 <= 2; ++$$7) {
                    $$1.m_7731_($$2.m_7918_($$6, $$5, $$7), this.f_65595_, 2);
                }
            }
        }
        $$1.m_7731_($$2, this.f_65596_, 2);
        for (Direction $$8 : Direction.Plane.HORIZONTAL) {
            $$1.m_7731_($$2.m_121945_($$8), this.f_65596_, 2);
        }
        for (int $$9 = -2; $$9 <= 2; ++$$9) {
            for (int $$10 = -2; $$10 <= 2; ++$$10) {
                if ($$9 != -2 && $$9 != 2 && $$10 != -2 && $$10 != 2) continue;
                $$1.m_7731_($$2.m_7918_($$9, 1, $$10), this.f_65595_, 2);
            }
        }
        $$1.m_7731_($$2.m_7918_(2, 1, 0), this.f_65594_, 2);
        $$1.m_7731_($$2.m_7918_(-2, 1, 0), this.f_65594_, 2);
        $$1.m_7731_($$2.m_7918_(0, 1, 2), this.f_65594_, 2);
        $$1.m_7731_($$2.m_7918_(0, 1, -2), this.f_65594_, 2);
        for (int $$11 = -1; $$11 <= 1; ++$$11) {
            for (int $$12 = -1; $$12 <= 1; ++$$12) {
                if ($$11 == 0 && $$12 == 0) {
                    $$1.m_7731_($$2.m_7918_($$11, 4, $$12), this.f_65595_, 2);
                    continue;
                }
                $$1.m_7731_($$2.m_7918_($$11, 4, $$12), this.f_65594_, 2);
            }
        }
        for (int $$13 = 1; $$13 <= 3; ++$$13) {
            $$1.m_7731_($$2.m_7918_(-1, $$13, -1), this.f_65595_, 2);
            $$1.m_7731_($$2.m_7918_(-1, $$13, 1), this.f_65595_, 2);
            $$1.m_7731_($$2.m_7918_(1, $$13, -1), this.f_65595_, 2);
            $$1.m_7731_($$2.m_7918_(1, $$13, 1), this.f_65595_, 2);
        }
        return true;
    }
}

