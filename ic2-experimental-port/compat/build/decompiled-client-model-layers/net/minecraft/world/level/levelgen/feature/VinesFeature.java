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
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VinesFeature
extends Feature<NoneFeatureConfiguration> {
    public VinesFeature(Codec<NoneFeatureConfiguration> p_67337_) {
        super(p_67337_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160628_) {
        WorldGenLevel $$1 = p_160628_.m_159774_();
        BlockPos $$2 = p_160628_.m_159777_();
        p_160628_.m_159778_();
        if (!$$1.m_46859_($$2)) {
            return false;
        }
        for (Direction $$3 : Direction.values()) {
            if ($$3 == Direction.DOWN || !VineBlock.m_57853_($$1, $$2.m_121945_($$3), $$3)) continue;
            $$1.m_7731_($$2, (BlockState)Blocks.f_50191_.m_49966_().m_61124_(VineBlock.m_57883_($$3), true), 2);
            return true;
        }
        return false;
    }
}

