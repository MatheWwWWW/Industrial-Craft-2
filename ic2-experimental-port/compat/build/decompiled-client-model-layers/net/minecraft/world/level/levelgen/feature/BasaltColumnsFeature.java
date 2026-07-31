/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

public class BasaltColumnsFeature
extends Feature<ColumnFeatureConfiguration> {
    private static final ImmutableList<Block> f_65150_ = ImmutableList.of((Object)Blocks.f_49991_, (Object)Blocks.f_50752_, (Object)Blocks.f_50450_, (Object)Blocks.f_50135_, (Object)Blocks.f_50197_, (Object)Blocks.f_50198_, (Object)Blocks.f_50199_, (Object)Blocks.f_50200_, (Object)Blocks.f_50087_, (Object)Blocks.f_50085_);
    private static final int f_159439_ = 5;
    private static final int f_159440_ = 50;
    private static final int f_159441_ = 8;
    private static final int f_159442_ = 15;

    public BasaltColumnsFeature(Codec<ColumnFeatureConfiguration> p_65153_) {
        super(p_65153_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<ColumnFeatureConfiguration> p_159444_) {
        int $$1 = p_159444_.m_159775_().m_6337_();
        BlockPos $$2 = p_159444_.m_159777_();
        WorldGenLevel $$3 = p_159444_.m_159774_();
        RandomSource $$4 = p_159444_.m_225041_();
        ColumnFeatureConfiguration $$5 = p_159444_.m_159778_();
        if (!BasaltColumnsFeature.m_65154_($$3, $$1, $$2.m_122032_())) {
            return false;
        }
        int $$6 = $$5.m_160720_().m_214085_($$4);
        boolean $$7 = $$4.m_188501_() < 0.9f;
        int $$8 = Math.min($$6, $$7 ? 5 : 8);
        int $$9 = $$7 ? 50 : 15;
        boolean $$10 = false;
        for (BlockPos $$11 : BlockPos.m_235641_($$4, $$9, $$2.m_123341_() - $$8, $$2.m_123342_(), $$2.m_123343_() - $$8, $$2.m_123341_() + $$8, $$2.m_123342_(), $$2.m_123343_() + $$8)) {
            int $$12 = $$6 - $$11.m_123333_($$2);
            if ($$12 < 0) continue;
            $$10 |= this.m_65167_($$3, $$1, $$11, $$12, $$5.m_160717_().m_214085_($$4));
        }
        return $$10;
    }

    private boolean m_65167_(LevelAccessor p_65168_, int p_65169_, BlockPos p_65170_, int p_65171_, int p_65172_) {
        boolean $$5 = false;
        block0: for (BlockPos $$6 : BlockPos.m_121976_(p_65170_.m_123341_() - p_65172_, p_65170_.m_123342_(), p_65170_.m_123343_() - p_65172_, p_65170_.m_123341_() + p_65172_, p_65170_.m_123342_(), p_65170_.m_123343_() + p_65172_)) {
            BlockPos $$8;
            int $$7 = $$6.m_123333_(p_65170_);
            BlockPos blockPos = $$8 = BasaltColumnsFeature.m_65163_(p_65168_, p_65169_, $$6) ? BasaltColumnsFeature.m_65158_(p_65168_, p_65169_, $$6.m_122032_(), $$7) : BasaltColumnsFeature.m_65173_(p_65168_, $$6.m_122032_(), $$7);
            if ($$8 == null) continue;
            BlockPos.MutableBlockPos $$10 = $$8.m_122032_();
            for (int $$9 = p_65171_ - $$7 / 2; $$9 >= 0; --$$9) {
                if (BasaltColumnsFeature.m_65163_(p_65168_, p_65169_, $$10)) {
                    this.m_5974_(p_65168_, $$10, Blocks.f_50137_.m_49966_());
                    $$10.m_122173_(Direction.UP);
                    $$5 = true;
                    continue;
                }
                if (!p_65168_.m_8055_($$10).m_60713_(Blocks.f_50137_)) continue block0;
                $$10.m_122173_(Direction.UP);
            }
        }
        return $$5;
    }

    @Nullable
    private static BlockPos m_65158_(LevelAccessor p_65159_, int p_65160_, BlockPos.MutableBlockPos p_65161_, int p_65162_) {
        while (p_65161_.m_123342_() > p_65159_.m_141937_() + 1 && p_65162_ > 0) {
            --p_65162_;
            if (BasaltColumnsFeature.m_65154_(p_65159_, p_65160_, p_65161_)) {
                return p_65161_;
            }
            p_65161_.m_122173_(Direction.DOWN);
        }
        return null;
    }

    private static boolean m_65154_(LevelAccessor p_65155_, int p_65156_, BlockPos.MutableBlockPos p_65157_) {
        if (BasaltColumnsFeature.m_65163_(p_65155_, p_65156_, p_65157_)) {
            BlockState $$3 = p_65155_.m_8055_(p_65157_.m_122173_(Direction.DOWN));
            p_65157_.m_122173_(Direction.UP);
            return !$$3.m_60795_() && !f_65150_.contains((Object)$$3.m_60734_());
        }
        return false;
    }

    @Nullable
    private static BlockPos m_65173_(LevelAccessor p_65174_, BlockPos.MutableBlockPos p_65175_, int p_65176_) {
        while (p_65175_.m_123342_() < p_65174_.m_151558_() && p_65176_ > 0) {
            --p_65176_;
            BlockState $$3 = p_65174_.m_8055_(p_65175_);
            if (f_65150_.contains((Object)$$3.m_60734_())) {
                return null;
            }
            if ($$3.m_60795_()) {
                return p_65175_;
            }
            p_65175_.m_122173_(Direction.UP);
        }
        return null;
    }

    private static boolean m_65163_(LevelAccessor p_65164_, int p_65165_, BlockPos p_65166_) {
        BlockState $$3 = p_65164_.m_8055_(p_65166_);
        return $$3.m_60795_() || $$3.m_60713_(Blocks.f_49991_) && p_65166_.m_123342_() <= p_65165_;
    }
}

