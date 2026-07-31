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
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;

public class FillLayerFeature
extends Feature<LayerConfiguration> {
    public FillLayerFeature(Codec<LayerConfiguration> p_65818_) {
        super(p_65818_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<LayerConfiguration> p_159780_) {
        BlockPos $$1 = p_159780_.m_159777_();
        LayerConfiguration $$2 = p_159780_.m_159778_();
        WorldGenLevel $$3 = p_159780_.m_159774_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = 0; $$5 < 16; ++$$5) {
            for (int $$6 = 0; $$6 < 16; ++$$6) {
                int $$7 = $$1.m_123341_() + $$5;
                int $$8 = $$1.m_123343_() + $$6;
                int $$9 = $$3.m_141937_() + $$2.f_67768_;
                $$4.m_122178_($$7, $$9, $$8);
                if (!$$3.m_8055_($$4).m_60795_()) continue;
                $$3.m_7731_($$4, $$2.f_67769_, 2);
            }
        }
        return true;
    }
}

