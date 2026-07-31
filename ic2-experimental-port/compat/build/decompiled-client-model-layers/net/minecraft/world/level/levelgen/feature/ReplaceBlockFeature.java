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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;

public class ReplaceBlockFeature
extends Feature<ReplaceBlockConfiguration> {
    public ReplaceBlockFeature(Codec<ReplaceBlockConfiguration> p_66651_) {
        super(p_66651_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<ReplaceBlockConfiguration> p_160216_) {
        WorldGenLevel $$1 = p_160216_.m_159774_();
        BlockPos $$2 = p_160216_.m_159777_();
        ReplaceBlockConfiguration $$3 = p_160216_.m_159778_();
        for (OreConfiguration.TargetBlockState $$4 : $$3.f_161083_) {
            if (!$$4.f_161032_.m_213865_($$1.m_8055_($$2), p_160216_.m_225041_())) continue;
            $$1.m_7731_($$2, $$4.f_161033_, 2);
            break;
        }
        return true;
    }
}

