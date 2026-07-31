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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

public class BlueIceFeature
extends Feature<NoneFeatureConfiguration> {
    public BlueIceFeature(Codec<NoneFeatureConfiguration> p_65285_) {
        super(p_65285_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159475_) {
        BlockPos $$1 = p_159475_.m_159777_();
        WorldGenLevel $$2 = p_159475_.m_159774_();
        RandomSource $$3 = p_159475_.m_225041_();
        if ($$1.m_123342_() > $$2.m_5736_() - 1) {
            return false;
        }
        if (!$$2.m_8055_($$1).m_60713_(Blocks.f_49990_) && !$$2.m_8055_($$1.m_7495_()).m_60713_(Blocks.f_49990_)) {
            return false;
        }
        boolean $$4 = false;
        for (Direction $$5 : Direction.values()) {
            if ($$5 == Direction.DOWN || !$$2.m_8055_($$1.m_121945_($$5)).m_60713_(Blocks.f_50354_)) continue;
            $$4 = true;
            break;
        }
        if (!$$4) {
            return false;
        }
        $$2.m_7731_($$1, Blocks.f_50568_.m_49966_(), 2);
        block1: for (int $$6 = 0; $$6 < 200; ++$$6) {
            BlockPos $$9;
            BlockState $$10;
            int $$7 = $$3.m_188503_(5) - $$3.m_188503_(6);
            int $$8 = 3;
            if ($$7 < 2) {
                $$8 += $$7 / 2;
            }
            if ($$8 < 1 || ($$10 = $$2.m_8055_($$9 = $$1.m_7918_($$3.m_188503_($$8) - $$3.m_188503_($$8), $$7, $$3.m_188503_($$8) - $$3.m_188503_($$8)))).m_60767_() != Material.f_76296_ && !$$10.m_60713_(Blocks.f_49990_) && !$$10.m_60713_(Blocks.f_50354_) && !$$10.m_60713_(Blocks.f_50126_)) continue;
            for (Direction $$11 : Direction.values()) {
                BlockState $$12 = $$2.m_8055_($$9.m_121945_($$11));
                if (!$$12.m_60713_(Blocks.f_50568_)) continue;
                $$2.m_7731_($$9, Blocks.f_50568_.m_49966_(), 2);
                continue block1;
            }
        }
        return true;
    }
}

