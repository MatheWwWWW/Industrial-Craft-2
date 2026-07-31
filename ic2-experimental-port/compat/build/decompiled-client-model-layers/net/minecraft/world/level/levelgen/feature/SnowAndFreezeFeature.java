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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowAndFreezeFeature
extends Feature<NoneFeatureConfiguration> {
    public SnowAndFreezeFeature(Codec<NoneFeatureConfiguration> p_66836_) {
        super(p_66836_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160368_) {
        WorldGenLevel $$1 = p_160368_.m_159774_();
        BlockPos $$2 = p_160368_.m_159777_();
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = 0; $$5 < 16; ++$$5) {
            for (int $$6 = 0; $$6 < 16; ++$$6) {
                int $$7 = $$2.m_123341_() + $$5;
                int $$8 = $$2.m_123343_() + $$6;
                int $$9 = $$1.m_6924_(Heightmap.Types.MOTION_BLOCKING, $$7, $$8);
                $$3.m_122178_($$7, $$9, $$8);
                $$4.m_122190_($$3).m_122175_(Direction.DOWN, 1);
                Biome $$10 = $$1.m_204166_($$3).m_203334_();
                if ($$10.m_47480_($$1, $$4, false)) {
                    $$1.m_7731_($$4, Blocks.f_50126_.m_49966_(), 2);
                }
                if (!$$10.m_47519_($$1, $$3)) continue;
                $$1.m_7731_($$3, Blocks.f_50125_.m_49966_(), 2);
                BlockState $$11 = $$1.m_8055_($$4);
                if (!$$11.m_61138_(SnowyDirtBlock.f_56637_)) continue;
                $$1.m_7731_($$4, (BlockState)$$11.m_61124_(SnowyDirtBlock.f_56637_, true), 2);
            }
        }
        return true;
    }
}

