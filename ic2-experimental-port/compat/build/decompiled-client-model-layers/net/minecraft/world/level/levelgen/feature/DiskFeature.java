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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class DiskFeature
extends Feature<DiskConfiguration> {
    public DiskFeature(Codec<DiskConfiguration> p_224992_) {
        super(p_224992_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<DiskConfiguration> p_224994_) {
        DiskConfiguration $$1 = p_224994_.m_159778_();
        BlockPos $$2 = p_224994_.m_159777_();
        WorldGenLevel $$3 = p_224994_.m_159774_();
        RandomSource $$4 = p_224994_.m_225041_();
        boolean $$5 = false;
        int $$6 = $$2.m_123342_();
        int $$7 = $$6 + $$1.f_67621_();
        int $$8 = $$6 - $$1.f_67621_() - 1;
        int $$9 = $$1.f_67620_().m_214085_($$4);
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        for (BlockPos $$11 : BlockPos.m_121940_($$2.m_7918_(-$$9, 0, -$$9), $$2.m_7918_($$9, 0, $$9))) {
            int $$13;
            int $$12 = $$11.m_123341_() - $$2.m_123341_();
            if ($$12 * $$12 + ($$13 = $$11.m_123343_() - $$2.m_123343_()) * $$13 > $$9 * $$9) continue;
            $$5 |= this.m_224995_($$1, $$3, $$4, $$7, $$8, $$10.m_122190_($$11));
        }
        return $$5;
    }

    protected boolean m_224995_(DiskConfiguration p_224996_, WorldGenLevel p_224997_, RandomSource p_224998_, int p_224999_, int p_225000_, BlockPos.MutableBlockPos p_225001_) {
        boolean $$6 = false;
        Object $$7 = null;
        for (int $$8 = p_224999_; $$8 > p_225000_; --$$8) {
            p_225001_.m_142448_($$8);
            if (!p_224996_.f_225373_().test(p_224997_, p_225001_)) continue;
            BlockState $$9 = p_224996_.f_225372_().m_225932_(p_224997_, p_224998_, p_225001_);
            p_224997_.m_7731_(p_225001_, $$9, 2);
            this.m_159739_(p_224997_, p_225001_);
            $$6 = true;
        }
        return $$6;
    }
}

