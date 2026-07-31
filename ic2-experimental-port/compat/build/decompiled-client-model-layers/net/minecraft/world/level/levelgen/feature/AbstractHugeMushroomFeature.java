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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public abstract class AbstractHugeMushroomFeature
extends Feature<HugeMushroomFeatureConfiguration> {
    public AbstractHugeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65093_) {
        super(p_65093_);
    }

    protected void m_224929_(LevelAccessor p_224930_, RandomSource p_224931_, BlockPos p_224932_, HugeMushroomFeatureConfiguration p_224933_, int p_224934_, BlockPos.MutableBlockPos p_224935_) {
        for (int $$6 = 0; $$6 < p_224934_; ++$$6) {
            p_224935_.m_122190_(p_224932_).m_122175_(Direction.UP, $$6);
            if (p_224930_.m_8055_(p_224935_).m_60804_(p_224930_, p_224935_)) continue;
            this.m_5974_(p_224930_, p_224935_, p_224933_.f_67741_.m_213972_(p_224931_, p_224932_));
        }
    }

    protected int m_224921_(RandomSource p_224922_) {
        int $$1 = p_224922_.m_188503_(3) + 4;
        if (p_224922_.m_188503_(12) == 0) {
            $$1 *= 2;
        }
        return $$1;
    }

    protected boolean m_65098_(LevelAccessor p_65099_, BlockPos p_65100_, int p_65101_, BlockPos.MutableBlockPos p_65102_, HugeMushroomFeatureConfiguration p_65103_) {
        int $$5 = p_65100_.m_123342_();
        if ($$5 < p_65099_.m_141937_() + 1 || $$5 + p_65101_ + 1 >= p_65099_.m_151558_()) {
            return false;
        }
        BlockState $$6 = p_65099_.m_8055_(p_65100_.m_7495_());
        if (!AbstractHugeMushroomFeature.m_159759_($$6) && !$$6.m_204336_(BlockTags.f_13057_)) {
            return false;
        }
        for (int $$7 = 0; $$7 <= p_65101_; ++$$7) {
            int $$8 = this.m_6794_(-1, -1, p_65103_.f_67742_, $$7);
            for (int $$9 = -$$8; $$9 <= $$8; ++$$9) {
                for (int $$10 = -$$8; $$10 <= $$8; ++$$10) {
                    BlockState $$11 = p_65099_.m_8055_(p_65102_.m_122154_(p_65100_, $$9, $$7, $$10));
                    if ($$11.m_60795_() || $$11.m_204336_(BlockTags.f_13035_)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<HugeMushroomFeatureConfiguration> p_159436_) {
        BlockPos.MutableBlockPos $$6;
        WorldGenLevel $$1 = p_159436_.m_159774_();
        BlockPos $$2 = p_159436_.m_159777_();
        RandomSource $$3 = p_159436_.m_225041_();
        HugeMushroomFeatureConfiguration $$4 = p_159436_.m_159778_();
        int $$5 = this.m_224921_($$3);
        if (!this.m_65098_($$1, $$2, $$5, $$6 = new BlockPos.MutableBlockPos(), $$4)) {
            return false;
        }
        this.m_213950_($$1, $$3, $$2, $$5, $$6, $$4);
        this.m_224929_($$1, $$3, $$2, $$4, $$5, $$6);
        return true;
    }

    protected abstract int m_6794_(int var1, int var2, int var3, int var4);

    protected abstract void m_213950_(LevelAccessor var1, RandomSource var2, BlockPos var3, int var4, BlockPos.MutableBlockPos var5, HugeMushroomFeatureConfiguration var6);
}

