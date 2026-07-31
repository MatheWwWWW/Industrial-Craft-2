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
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;

public class TwistingVinesFeature
extends Feature<TwistingVinesConfig> {
    public TwistingVinesFeature(Codec<TwistingVinesConfig> p_67292_) {
        super(p_67292_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<TwistingVinesConfig> p_160558_) {
        BlockPos $$2;
        WorldGenLevel $$1 = p_160558_.m_159774_();
        if (TwistingVinesFeature.m_67296_($$1, $$2 = p_160558_.m_159777_())) {
            return false;
        }
        RandomSource $$3 = p_160558_.m_225041_();
        TwistingVinesConfig $$4 = p_160558_.m_159778_();
        int $$5 = $$4.f_191365_();
        int $$6 = $$4.f_191366_();
        int $$7 = $$4.f_191367_();
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        for (int $$9 = 0; $$9 < $$5 * $$5; ++$$9) {
            $$8.m_122190_($$2).m_122184_(Mth.m_216271_($$3, -$$5, $$5), Mth.m_216271_($$3, -$$6, $$6), Mth.m_216271_($$3, -$$5, $$5));
            if (!TwistingVinesFeature.m_67293_($$1, $$8) || TwistingVinesFeature.m_67296_($$1, $$8)) continue;
            int $$10 = Mth.m_216271_($$3, 1, $$7);
            if ($$3.m_188503_(6) == 0) {
                $$10 *= 2;
            }
            if ($$3.m_188503_(5) == 0) {
                $$10 = 1;
            }
            int $$11 = 17;
            int $$12 = 25;
            TwistingVinesFeature.m_225300_($$1, $$3, $$8, $$10, 17, 25);
        }
        return true;
    }

    private static boolean m_67293_(LevelAccessor p_67294_, BlockPos.MutableBlockPos p_67295_) {
        do {
            p_67295_.m_122184_(0, -1, 0);
            if (!p_67294_.m_151570_(p_67295_)) continue;
            return false;
        } while (p_67294_.m_8055_(p_67295_).m_60795_());
        p_67295_.m_122184_(0, 1, 0);
        return true;
    }

    public static void m_225300_(LevelAccessor p_225301_, RandomSource p_225302_, BlockPos.MutableBlockPos p_225303_, int p_225304_, int p_225305_, int p_225306_) {
        for (int $$6 = 1; $$6 <= p_225304_; ++$$6) {
            if (p_225301_.m_46859_(p_225303_)) {
                if ($$6 == p_225304_ || !p_225301_.m_46859_((BlockPos)p_225303_.m_7494_())) {
                    p_225301_.m_7731_(p_225303_, (BlockState)Blocks.f_50704_.m_49966_().m_61124_(GrowingPlantHeadBlock.f_53924_, Mth.m_216271_(p_225302_, p_225305_, p_225306_)), 2);
                    break;
                }
                p_225301_.m_7731_(p_225303_, Blocks.f_50653_.m_49966_(), 2);
            }
            p_225303_.m_122173_(Direction.UP);
        }
    }

    private static boolean m_67296_(LevelAccessor p_67297_, BlockPos p_67298_) {
        if (!p_67297_.m_46859_(p_67298_)) {
            return true;
        }
        BlockState $$2 = p_67297_.m_8055_(p_67298_.m_7495_());
        return !$$2.m_60713_(Blocks.f_50134_) && !$$2.m_60713_(Blocks.f_50690_) && !$$2.m_60713_(Blocks.f_50692_);
    }
}

