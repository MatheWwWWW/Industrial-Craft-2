/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;

public class ReplaceBlobsFeature
extends Feature<ReplaceSphereConfiguration> {
    public ReplaceBlobsFeature(Codec<ReplaceSphereConfiguration> p_66633_) {
        super(p_66633_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<ReplaceSphereConfiguration> p_160214_) {
        ReplaceSphereConfiguration $$1 = p_160214_.m_159778_();
        WorldGenLevel $$2 = p_160214_.m_159774_();
        RandomSource $$3 = p_160214_.m_225041_();
        Block $$4 = $$1.f_68037_.m_60734_();
        BlockPos $$5 = ReplaceBlobsFeature.m_66634_($$2, p_160214_.m_159777_().m_122032_().m_122147_(Direction.Axis.Y, $$2.m_141937_() + 1, $$2.m_151558_() - 1), $$4);
        if ($$5 == null) {
            return false;
        }
        int $$6 = $$1.m_161096_().m_214085_($$3);
        int $$7 = $$1.m_161096_().m_214085_($$3);
        int $$8 = $$1.m_161096_().m_214085_($$3);
        int $$9 = Math.max($$6, Math.max($$7, $$8));
        boolean $$10 = false;
        for (BlockPos $$11 : BlockPos.m_121925_($$5, $$6, $$7, $$8)) {
            if ($$11.m_123333_($$5) > $$9) break;
            BlockState $$12 = $$2.m_8055_($$11);
            if (!$$12.m_60713_($$4)) continue;
            this.m_5974_($$2, $$11, $$1.f_68038_);
            $$10 = true;
        }
        return $$10;
    }

    @Nullable
    private static BlockPos m_66634_(LevelAccessor p_66635_, BlockPos.MutableBlockPos p_66636_, Block p_66637_) {
        while (p_66636_.m_123342_() > p_66635_.m_141937_() + 1) {
            BlockState $$3 = p_66635_.m_8055_(p_66636_);
            if ($$3.m_60713_(p_66637_)) {
                return p_66636_;
            }
            p_66636_.m_122173_(Direction.DOWN);
        }
        return null;
    }
}

