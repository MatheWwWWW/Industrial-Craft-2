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
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WeepingVinesFeature
extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] f_67372_ = Direction.values();

    public WeepingVinesFeature(Codec<NoneFeatureConfiguration> p_67375_) {
        super(p_67375_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160661_) {
        WorldGenLevel $$1 = p_160661_.m_159774_();
        BlockPos $$2 = p_160661_.m_159777_();
        RandomSource $$3 = p_160661_.m_225041_();
        if (!$$1.m_46859_($$2)) {
            return false;
        }
        BlockState $$4 = $$1.m_8055_($$2.m_7494_());
        if (!$$4.m_60713_(Blocks.f_50134_) && !$$4.m_60713_(Blocks.f_50451_)) {
            return false;
        }
        this.m_225359_($$1, $$3, $$2);
        this.m_225363_($$1, $$3, $$2);
        return true;
    }

    private void m_225359_(LevelAccessor p_225360_, RandomSource p_225361_, BlockPos p_225362_) {
        p_225360_.m_7731_(p_225362_, Blocks.f_50451_.m_49966_(), 2);
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = 0; $$5 < 200; ++$$5) {
            $$3.m_122154_(p_225362_, p_225361_.m_188503_(6) - p_225361_.m_188503_(6), p_225361_.m_188503_(2) - p_225361_.m_188503_(5), p_225361_.m_188503_(6) - p_225361_.m_188503_(6));
            if (!p_225360_.m_46859_($$3)) continue;
            int $$6 = 0;
            for (Direction $$7 : f_67372_) {
                BlockState $$8 = p_225360_.m_8055_($$4.m_122159_($$3, $$7));
                if ($$8.m_60713_(Blocks.f_50134_) || $$8.m_60713_(Blocks.f_50451_)) {
                    ++$$6;
                }
                if ($$6 > 1) break;
            }
            if ($$6 != true) continue;
            p_225360_.m_7731_($$3, Blocks.f_50451_.m_49966_(), 2);
        }
    }

    private void m_225363_(LevelAccessor p_225364_, RandomSource p_225365_, BlockPos p_225366_) {
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        for (int $$4 = 0; $$4 < 100; ++$$4) {
            BlockState $$5;
            $$3.m_122154_(p_225366_, p_225365_.m_188503_(8) - p_225365_.m_188503_(8), p_225365_.m_188503_(2) - p_225365_.m_188503_(7), p_225365_.m_188503_(8) - p_225365_.m_188503_(8));
            if (!p_225364_.m_46859_($$3) || !($$5 = p_225364_.m_8055_((BlockPos)$$3.m_7494_())).m_60713_(Blocks.f_50134_) && !$$5.m_60713_(Blocks.f_50451_)) continue;
            int $$6 = Mth.m_216271_(p_225365_, 1, 8);
            if (p_225365_.m_188503_(6) == 0) {
                $$6 *= 2;
            }
            if (p_225365_.m_188503_(5) == 0) {
                $$6 = 1;
            }
            int $$7 = 17;
            int $$8 = 25;
            WeepingVinesFeature.m_225352_(p_225364_, p_225365_, $$3, $$6, 17, 25);
        }
    }

    public static void m_225352_(LevelAccessor p_225353_, RandomSource p_225354_, BlockPos.MutableBlockPos p_225355_, int p_225356_, int p_225357_, int p_225358_) {
        for (int $$6 = 0; $$6 <= p_225356_; ++$$6) {
            if (p_225353_.m_46859_(p_225355_)) {
                if ($$6 == p_225356_ || !p_225353_.m_46859_((BlockPos)p_225355_.m_7495_())) {
                    p_225353_.m_7731_(p_225355_, (BlockState)Blocks.f_50702_.m_49966_().m_61124_(GrowingPlantHeadBlock.f_53924_, Mth.m_216271_(p_225354_, p_225357_, p_225358_)), 2);
                    break;
                }
                p_225353_.m_7731_(p_225355_, Blocks.f_50703_.m_49966_(), 2);
            }
            p_225355_.m_122173_(Direction.DOWN);
        }
    }
}

