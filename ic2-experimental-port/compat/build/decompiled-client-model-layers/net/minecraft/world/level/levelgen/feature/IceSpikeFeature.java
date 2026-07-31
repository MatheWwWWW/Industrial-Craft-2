/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IceSpikeFeature
extends Feature<NoneFeatureConfiguration> {
    public IceSpikeFeature(Codec<NoneFeatureConfiguration> p_66003_) {
        super(p_66003_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
        BlockPos $$1 = p_159882_.m_159777_();
        RandomSource $$2 = p_159882_.m_225041_();
        WorldGenLevel $$3 = p_159882_.m_159774_();
        while ($$3.m_46859_($$1) && $$1.m_123342_() > $$3.m_141937_() + 2) {
            $$1 = $$1.m_7495_();
        }
        if (!$$3.m_8055_($$1).m_60713_(Blocks.f_50127_)) {
            return false;
        }
        $$1 = $$1.m_6630_($$2.m_188503_(4));
        int $$4 = $$2.m_188503_(4) + 7;
        int $$5 = $$4 / 4 + $$2.m_188503_(2);
        if ($$5 > 1 && $$2.m_188503_(60) == 0) {
            $$1 = $$1.m_6630_(10 + $$2.m_188503_(30));
        }
        for (int $$6 = 0; $$6 < $$4; ++$$6) {
            float $$7 = (1.0f - (float)$$6 / (float)$$4) * (float)$$5;
            int $$8 = Mth.m_14167_($$7);
            for (int $$9 = -$$8; $$9 <= $$8; ++$$9) {
                float $$10 = (float)Mth.m_14040_($$9) - 0.25f;
                for (int $$11 = -$$8; $$11 <= $$8; ++$$11) {
                    float $$12 = (float)Mth.m_14040_($$11) - 0.25f;
                    if (($$9 != 0 || $$11 != 0) && $$10 * $$10 + $$12 * $$12 > $$7 * $$7 || ($$9 == -$$8 || $$9 == $$8 || $$11 == -$$8 || $$11 == $$8) && $$2.m_188501_() > 0.75f) continue;
                    BlockState $$13 = $$3.m_8055_($$1.m_7918_($$9, $$6, $$11));
                    if ($$13.m_60795_() || IceSpikeFeature.m_159759_($$13) || $$13.m_60713_(Blocks.f_50127_) || $$13.m_60713_(Blocks.f_50126_)) {
                        this.m_5974_($$3, $$1.m_7918_($$9, $$6, $$11), Blocks.f_50354_.m_49966_());
                    }
                    if ($$6 == 0 || $$8 <= 1 || !($$13 = $$3.m_8055_($$1.m_7918_($$9, -$$6, $$11))).m_60795_() && !IceSpikeFeature.m_159759_($$13) && !$$13.m_60713_(Blocks.f_50127_) && !$$13.m_60713_(Blocks.f_50126_)) continue;
                    this.m_5974_($$3, $$1.m_7918_($$9, -$$6, $$11), Blocks.f_50354_.m_49966_());
                }
            }
        }
        int $$14 = $$5 - 1;
        if ($$14 < 0) {
            $$14 = 0;
        } else if ($$14 > 1) {
            $$14 = 1;
        }
        for (int $$15 = -$$14; $$15 <= $$14; ++$$15) {
            for (int $$16 = -$$14; $$16 <= $$14; ++$$16) {
                BlockState $$19;
                BlockPos $$17 = $$1.m_7918_($$15, -1, $$16);
                int $$18 = 50;
                if (Math.abs($$15) == 1 && Math.abs($$16) == 1) {
                    $$18 = $$2.m_188503_(5);
                }
                while ($$17.m_123342_() > 50 && (($$19 = $$3.m_8055_($$17)).m_60795_() || IceSpikeFeature.m_159759_($$19) || $$19.m_60713_(Blocks.f_50127_) || $$19.m_60713_(Blocks.f_50126_) || $$19.m_60713_(Blocks.f_50354_))) {
                    this.m_5974_($$3, $$17, Blocks.f_50354_.m_49966_());
                    $$17 = $$17.m_7495_();
                    if (--$$18 > 0) continue;
                    $$17 = $$17.m_6625_($$2.m_188503_(5) + 1);
                    $$18 = $$2.m_188503_(5);
                }
            }
        }
        return true;
    }
}

