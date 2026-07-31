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
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class SimpleBlockFeature
extends Feature<SimpleBlockConfiguration> {
    public SimpleBlockFeature(Codec<SimpleBlockConfiguration> p_66808_) {
        super(p_66808_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean m_142674_(FeaturePlaceContext<SimpleBlockConfiguration> p_160341_) {
        SimpleBlockConfiguration $$1 = p_160341_.m_159778_();
        WorldGenLevel $$2 = p_160341_.m_159774_();
        BlockPos $$3 = p_160341_.m_159777_();
        BlockState $$4 = $$1.f_68069_().m_213972_(p_160341_.m_225041_(), $$3);
        if (!$$4.m_60710_($$2, $$3)) return false;
        if ($$4.m_60734_() instanceof DoublePlantBlock) {
            if (!$$2.m_46859_($$3.m_7494_())) return false;
            DoublePlantBlock.m_153173_($$2, $$4, $$3, 2);
            return true;
        } else {
            $$2.m_7731_($$3, $$4, 2);
        }
        return true;
    }
}

