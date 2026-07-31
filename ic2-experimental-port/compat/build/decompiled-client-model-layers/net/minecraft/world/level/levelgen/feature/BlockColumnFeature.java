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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;

public class BlockColumnFeature
extends Feature<BlockColumnConfiguration> {
    public BlockColumnFeature(Codec<BlockColumnConfiguration> p_190789_) {
        super(p_190789_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<BlockColumnConfiguration> p_190791_) {
        WorldGenLevel $$1 = p_190791_.m_159774_();
        BlockColumnConfiguration $$2 = p_190791_.m_159778_();
        RandomSource $$3 = p_190791_.m_225041_();
        int $$4 = $$2.f_191207_().size();
        int[] $$5 = new int[$$4];
        int $$6 = 0;
        for (int $$7 = 0; $$7 < $$4; ++$$7) {
            $$5[$$7] = $$2.f_191207_().get($$7).f_191234_().m_214085_($$3);
            $$6 += $$5[$$7];
        }
        if ($$6 == 0) {
            return false;
        }
        BlockPos.MutableBlockPos $$8 = p_190791_.m_159777_().m_122032_();
        BlockPos.MutableBlockPos $$9 = $$8.m_122032_().m_122173_($$2.f_191208_());
        for (int $$10 = 0; $$10 < $$6; ++$$10) {
            if (!$$2.f_191209_().test($$1, $$9)) {
                BlockColumnFeature.m_190792_($$5, $$6, $$10, $$2.f_191210_());
                break;
            }
            $$9.m_122173_($$2.f_191208_());
        }
        for (int $$11 = 0; $$11 < $$4; ++$$11) {
            int $$12 = $$5[$$11];
            if ($$12 == 0) continue;
            BlockColumnConfiguration.Layer $$13 = $$2.f_191207_().get($$11);
            for (int $$14 = 0; $$14 < $$12; ++$$14) {
                $$1.m_7731_($$8, $$13.f_191235_().m_213972_($$3, $$8), 2);
                $$8.m_122173_($$2.f_191208_());
            }
        }
        return true;
    }

    private static void m_190792_(int[] p_190793_, int p_190794_, int p_190795_, boolean p_190796_) {
        int $$10;
        int $$4 = p_190794_ - p_190795_;
        int $$5 = p_190796_ ? 1 : -1;
        int $$6 = p_190796_ ? 0 : p_190793_.length - 1;
        int $$7 = p_190796_ ? p_190793_.length : -1;
        for (int $$8 = $$6; $$8 != $$7 && $$4 > 0; $$4 -= $$10, $$8 += $$5) {
            int $$9 = p_190793_[$$8];
            $$10 = Math.min($$9, $$4);
            int n = $$8;
            p_190793_[n] = p_190793_[n] - $$10;
        }
    }
}

