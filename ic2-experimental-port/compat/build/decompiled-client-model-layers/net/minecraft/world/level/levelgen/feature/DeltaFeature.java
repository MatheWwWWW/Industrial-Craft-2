/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
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
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;

public class DeltaFeature
extends Feature<DeltaFeatureConfiguration> {
    private static final ImmutableList<Block> f_65546_ = ImmutableList.of((Object)Blocks.f_50752_, (Object)Blocks.f_50197_, (Object)Blocks.f_50198_, (Object)Blocks.f_50199_, (Object)Blocks.f_50200_, (Object)Blocks.f_50087_, (Object)Blocks.f_50085_);
    private static final Direction[] f_65547_ = Direction.values();
    private static final double f_159546_ = 0.9;

    public DeltaFeature(Codec<DeltaFeatureConfiguration> p_65550_) {
        super(p_65550_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<DeltaFeatureConfiguration> p_159548_) {
        boolean $$1 = false;
        RandomSource $$2 = p_159548_.m_225041_();
        WorldGenLevel $$3 = p_159548_.m_159774_();
        DeltaFeatureConfiguration $$4 = p_159548_.m_159778_();
        BlockPos $$5 = p_159548_.m_159777_();
        boolean $$6 = $$2.m_188500_() < 0.9;
        int $$7 = $$6 ? $$4.m_160744_().m_214085_($$2) : 0;
        int $$8 = $$6 ? $$4.m_160744_().m_214085_($$2) : 0;
        boolean $$9 = $$6 && $$7 != 0 && $$8 != 0;
        int $$10 = $$4.m_160741_().m_214085_($$2);
        int $$11 = $$4.m_160741_().m_214085_($$2);
        int $$12 = Math.max($$10, $$11);
        for (BlockPos $$13 : BlockPos.m_121925_($$5, $$10, 0, $$11)) {
            BlockPos $$14;
            if ($$13.m_123333_($$5) > $$12) break;
            if (!DeltaFeature.m_65551_($$3, $$13, $$4)) continue;
            if ($$9) {
                $$1 = true;
                this.m_5974_($$3, $$13, $$4.m_67611_());
            }
            if (!DeltaFeature.m_65551_($$3, $$14 = $$13.m_7918_($$7, 0, $$8), $$4)) continue;
            $$1 = true;
            this.m_5974_($$3, $$14, $$4.m_67608_());
        }
        return $$1;
    }

    private static boolean m_65551_(LevelAccessor p_65552_, BlockPos p_65553_, DeltaFeatureConfiguration p_65554_) {
        BlockState $$3 = p_65552_.m_8055_(p_65553_);
        if ($$3.m_60713_(p_65554_.m_67608_().m_60734_())) {
            return false;
        }
        if (f_65546_.contains((Object)$$3.m_60734_())) {
            return false;
        }
        for (Direction $$4 : f_65547_) {
            boolean $$5 = p_65552_.m_8055_(p_65553_.m_121945_($$4)).m_60795_();
            if ((!$$5 || $$4 == Direction.UP) && ($$5 || $$4 != Direction.UP)) continue;
            return false;
        }
        return true;
    }
}

