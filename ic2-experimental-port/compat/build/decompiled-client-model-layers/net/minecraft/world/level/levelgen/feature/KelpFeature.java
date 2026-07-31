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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class KelpFeature
extends Feature<NoneFeatureConfiguration> {
    public KelpFeature(Codec<NoneFeatureConfiguration> p_66219_) {
        super(p_66219_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159956_) {
        int $$1 = 0;
        WorldGenLevel $$2 = p_159956_.m_159774_();
        BlockPos $$3 = p_159956_.m_159777_();
        RandomSource $$4 = p_159956_.m_225041_();
        int $$5 = $$2.m_6924_(Heightmap.Types.OCEAN_FLOOR, $$3.m_123341_(), $$3.m_123343_());
        BlockPos $$6 = new BlockPos($$3.m_123341_(), $$5, $$3.m_123343_());
        if ($$2.m_8055_($$6).m_60713_(Blocks.f_49990_)) {
            BlockState $$7 = Blocks.f_50575_.m_49966_();
            BlockState $$8 = Blocks.f_50576_.m_49966_();
            int $$9 = 1 + $$4.m_188503_(10);
            for (int $$10 = 0; $$10 <= $$9; ++$$10) {
                if ($$2.m_8055_($$6).m_60713_(Blocks.f_49990_) && $$2.m_8055_($$6.m_7494_()).m_60713_(Blocks.f_49990_) && $$8.m_60710_($$2, $$6)) {
                    if ($$10 == $$9) {
                        $$2.m_7731_($$6, (BlockState)$$7.m_61124_(KelpBlock.f_53924_, $$4.m_188503_(4) + 20), 2);
                        ++$$1;
                    } else {
                        $$2.m_7731_($$6, $$8, 2);
                    }
                } else if ($$10 > 0) {
                    BlockPos $$11 = $$6.m_7495_();
                    if (!$$7.m_60710_($$2, $$11) || $$2.m_8055_($$11.m_7495_()).m_60713_(Blocks.f_50575_)) break;
                    $$2.m_7731_($$11, (BlockState)$$7.m_61124_(KelpBlock.f_53924_, $$4.m_188503_(4) + 20), 2);
                    ++$$1;
                    break;
                }
                $$6 = $$6.m_7494_();
            }
        }
        return $$1 > 0;
    }
}

