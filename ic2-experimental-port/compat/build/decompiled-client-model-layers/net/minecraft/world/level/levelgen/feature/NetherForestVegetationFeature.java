/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;

public class NetherForestVegetationFeature
extends Feature<NetherForestVegetationConfig> {
    public NetherForestVegetationFeature(Codec<NetherForestVegetationConfig> p_66361_) {
        super(p_66361_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NetherForestVegetationConfig> p_160068_) {
        WorldGenLevel $$1 = p_160068_.m_159774_();
        BlockPos $$2 = p_160068_.m_159777_();
        BlockState $$3 = $$1.m_8055_($$2.m_7495_());
        NetherForestVegetationConfig $$4 = p_160068_.m_159778_();
        RandomSource $$5 = p_160068_.m_225041_();
        if (!$$3.m_204336_(BlockTags.f_13077_)) {
            return false;
        }
        int $$6 = $$2.m_123342_();
        if ($$6 < $$1.m_141937_() + 1 || $$6 + 1 >= $$1.m_151558_()) {
            return false;
        }
        int $$7 = 0;
        for (int $$8 = 0; $$8 < $$4.f_191259_ * $$4.f_191259_; ++$$8) {
            BlockPos $$9 = $$2.m_7918_($$5.m_188503_($$4.f_191259_) - $$5.m_188503_($$4.f_191259_), $$5.m_188503_($$4.f_191260_) - $$5.m_188503_($$4.f_191260_), $$5.m_188503_($$4.f_191259_) - $$5.m_188503_($$4.f_191259_));
            BlockState $$10 = $$4.f_67540_.m_213972_($$5, $$9);
            if (!$$1.m_46859_($$9) || $$9.m_123342_() <= $$1.m_141937_() || !$$10.m_60710_($$1, $$9)) continue;
            $$1.m_7731_($$9, $$10, 2);
            ++$$7;
        }
        return $$7 > 0;
    }
}

