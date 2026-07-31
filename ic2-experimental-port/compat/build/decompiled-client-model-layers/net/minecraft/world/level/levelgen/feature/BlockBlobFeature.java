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
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BlockBlobFeature
extends Feature<BlockStateConfiguration> {
    public BlockBlobFeature(Codec<BlockStateConfiguration> p_65248_) {
        super(p_65248_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<BlockStateConfiguration> p_159471_) {
        BlockState $$5;
        BlockPos $$1 = p_159471_.m_159777_();
        WorldGenLevel $$2 = p_159471_.m_159774_();
        RandomSource $$3 = p_159471_.m_225041_();
        BlockStateConfiguration $$4 = p_159471_.m_159778_();
        while ($$1.m_123342_() > $$2.m_141937_() + 3 && ($$2.m_46859_($$1.m_7495_()) || !BlockBlobFeature.m_159759_($$5 = $$2.m_8055_($$1.m_7495_())) && !BlockBlobFeature.m_159747_($$5))) {
            $$1 = $$1.m_7495_();
        }
        if ($$1.m_123342_() <= $$2.m_141937_() + 3) {
            return false;
        }
        for (int $$6 = 0; $$6 < 3; ++$$6) {
            int $$7 = $$3.m_188503_(2);
            int $$8 = $$3.m_188503_(2);
            int $$9 = $$3.m_188503_(2);
            float $$10 = (float)($$7 + $$8 + $$9) * 0.333f + 0.5f;
            for (BlockPos $$11 : BlockPos.m_121940_($$1.m_7918_(-$$7, -$$8, -$$9), $$1.m_7918_($$7, $$8, $$9))) {
                if (!($$11.m_123331_($$1) <= (double)($$10 * $$10))) continue;
                $$2.m_7731_($$11, $$4.f_67547_, 4);
            }
            $$1 = $$1.m_7918_(-1 + $$3.m_188503_(2), -$$3.m_188503_(2), -1 + $$3.m_188503_(2));
        }
        return true;
    }
}

