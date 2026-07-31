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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BasaltPillarFeature
extends Feature<NoneFeatureConfiguration> {
    public BasaltPillarFeature(Codec<NoneFeatureConfiguration> p_65190_) {
        super(p_65190_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159446_) {
        BlockPos $$1 = p_159446_.m_159777_();
        WorldGenLevel $$2 = p_159446_.m_159774_();
        RandomSource $$3 = p_159446_.m_225041_();
        if (!$$2.m_46859_($$1) || $$2.m_46859_($$1.m_7494_())) {
            return false;
        }
        BlockPos.MutableBlockPos $$4 = $$1.m_122032_();
        BlockPos.MutableBlockPos $$5 = $$1.m_122032_();
        boolean $$6 = true;
        boolean $$7 = true;
        boolean $$8 = true;
        boolean $$9 = true;
        while ($$2.m_46859_($$4)) {
            if ($$2.m_151570_($$4)) {
                return true;
            }
            $$2.m_7731_($$4, Blocks.f_50137_.m_49966_(), 2);
            $$6 = $$6 && this.m_224940_($$2, $$3, $$5.m_122159_($$4, Direction.NORTH));
            $$7 = $$7 && this.m_224940_($$2, $$3, $$5.m_122159_($$4, Direction.SOUTH));
            $$8 = $$8 && this.m_224940_($$2, $$3, $$5.m_122159_($$4, Direction.WEST));
            $$9 = $$9 && this.m_224940_($$2, $$3, $$5.m_122159_($$4, Direction.EAST));
            $$4.m_122173_(Direction.DOWN);
        }
        $$4.m_122173_(Direction.UP);
        this.m_224936_($$2, $$3, $$5.m_122159_($$4, Direction.NORTH));
        this.m_224936_($$2, $$3, $$5.m_122159_($$4, Direction.SOUTH));
        this.m_224936_($$2, $$3, $$5.m_122159_($$4, Direction.WEST));
        this.m_224936_($$2, $$3, $$5.m_122159_($$4, Direction.EAST));
        $$4.m_122173_(Direction.DOWN);
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        for (int $$11 = -3; $$11 < 4; ++$$11) {
            for (int $$12 = -3; $$12 < 4; ++$$12) {
                int $$13 = Mth.m_14040_($$11) * Mth.m_14040_($$12);
                if ($$3.m_188503_(10) >= 10 - $$13) continue;
                $$10.m_122190_($$4.m_7918_($$11, 0, $$12));
                int $$14 = 3;
                while ($$2.m_46859_($$5.m_122159_($$10, Direction.DOWN))) {
                    $$10.m_122173_(Direction.DOWN);
                    if (--$$14 > 0) continue;
                }
                if ($$2.m_46859_($$5.m_122159_($$10, Direction.DOWN))) continue;
                $$2.m_7731_($$10, Blocks.f_50137_.m_49966_(), 2);
            }
        }
        return true;
    }

    private void m_224936_(LevelAccessor p_224937_, RandomSource p_224938_, BlockPos p_224939_) {
        if (p_224938_.m_188499_()) {
            p_224937_.m_7731_(p_224939_, Blocks.f_50137_.m_49966_(), 2);
        }
    }

    private boolean m_224940_(LevelAccessor p_224941_, RandomSource p_224942_, BlockPos p_224943_) {
        if (p_224942_.m_188503_(10) != 0) {
            p_224941_.m_7731_(p_224943_, Blocks.f_50137_.m_49966_(), 2);
            return true;
        }
        return false;
    }
}

