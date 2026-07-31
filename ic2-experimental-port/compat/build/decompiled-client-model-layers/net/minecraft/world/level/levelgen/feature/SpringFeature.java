/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;

public class SpringFeature
extends Feature<SpringConfiguration> {
    public SpringFeature(Codec<SpringConfiguration> p_66914_) {
        super(p_66914_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<SpringConfiguration> p_160404_) {
        BlockPos $$3;
        SpringConfiguration $$1 = p_160404_.m_159778_();
        WorldGenLevel $$2 = p_160404_.m_159774_();
        if (!$$2.m_8055_(($$3 = p_160404_.m_159777_()).m_7494_()).m_204341_($$1.f_68128_)) {
            return false;
        }
        if ($$1.f_68125_ && !$$2.m_8055_($$3.m_7495_()).m_204341_($$1.f_68128_)) {
            return false;
        }
        BlockState $$4 = $$2.m_8055_($$3);
        if (!$$4.m_60795_() && !$$4.m_204341_($$1.f_68128_)) {
            return false;
        }
        int $$5 = 0;
        int $$6 = 0;
        if ($$2.m_8055_($$3.m_122024_()).m_204341_($$1.f_68128_)) {
            ++$$6;
        }
        if ($$2.m_8055_($$3.m_122029_()).m_204341_($$1.f_68128_)) {
            ++$$6;
        }
        if ($$2.m_8055_($$3.m_122012_()).m_204341_($$1.f_68128_)) {
            ++$$6;
        }
        if ($$2.m_8055_($$3.m_122019_()).m_204341_($$1.f_68128_)) {
            ++$$6;
        }
        if ($$2.m_8055_($$3.m_7495_()).m_204341_($$1.f_68128_)) {
            ++$$6;
        }
        int $$7 = 0;
        if ($$2.m_46859_($$3.m_122024_())) {
            ++$$7;
        }
        if ($$2.m_46859_($$3.m_122029_())) {
            ++$$7;
        }
        if ($$2.m_46859_($$3.m_122012_())) {
            ++$$7;
        }
        if ($$2.m_46859_($$3.m_122019_())) {
            ++$$7;
        }
        if ($$2.m_46859_($$3.m_7495_())) {
            ++$$7;
        }
        if ($$6 == $$1.f_68126_ && $$7 == $$1.f_68127_) {
            $$2.m_7731_($$3, $$1.f_68124_.m_76188_(), 2);
            $$2.m_186469_($$3, $$1.f_68124_.m_76152_(), 0);
            ++$$5;
        }
        return $$5 > 0;
    }
}

