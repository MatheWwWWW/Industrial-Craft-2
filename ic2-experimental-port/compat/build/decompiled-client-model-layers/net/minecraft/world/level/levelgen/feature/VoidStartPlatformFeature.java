/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidStartPlatformFeature
extends Feature<NoneFeatureConfiguration> {
    private static final BlockPos f_160629_ = new BlockPos(8, 3, 8);
    private static final ChunkPos f_67351_ = new ChunkPos(f_160629_);
    private static final int f_160630_ = 16;
    private static final int f_160631_ = 1;

    public VoidStartPlatformFeature(Codec<NoneFeatureConfiguration> p_67354_) {
        super(p_67354_);
    }

    private static int m_67355_(int p_67356_, int p_67357_, int p_67358_, int p_67359_) {
        return Math.max(Math.abs(p_67356_ - p_67358_), Math.abs(p_67357_ - p_67359_));
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160633_) {
        WorldGenLevel $$1 = p_160633_.m_159774_();
        ChunkPos $$2 = new ChunkPos(p_160633_.m_159777_());
        if (VoidStartPlatformFeature.m_67355_($$2.f_45578_, $$2.f_45579_, VoidStartPlatformFeature.f_67351_.f_45578_, VoidStartPlatformFeature.f_67351_.f_45579_) > 1) {
            return true;
        }
        BlockPos $$3 = f_160629_.m_175288_(p_160633_.m_159777_().m_123342_() + f_160629_.m_123342_());
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = $$2.m_45605_(); $$5 <= $$2.m_45609_(); ++$$5) {
            for (int $$6 = $$2.m_45604_(); $$6 <= $$2.m_45608_(); ++$$6) {
                if (VoidStartPlatformFeature.m_67355_($$3.m_123341_(), $$3.m_123343_(), $$6, $$5) > 16) continue;
                $$4.m_122178_($$6, $$3.m_123342_(), $$5);
                if ($$4.equals($$3)) {
                    $$1.m_7731_($$4, Blocks.f_50652_.m_49966_(), 2);
                    continue;
                }
                $$1.m_7731_($$4, Blocks.f_50069_.m_49966_(), 2);
            }
        }
        return true;
    }
}

