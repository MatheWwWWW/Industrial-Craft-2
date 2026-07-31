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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;

public class SculkPatchFeature
extends Feature<SculkPatchConfiguration> {
    public SculkPatchFeature(Codec<SculkPatchConfiguration> p_225237_) {
        super(p_225237_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<SculkPatchConfiguration> p_225242_) {
        BlockPos $$2;
        WorldGenLevel $$1 = p_225242_.m_159774_();
        if (!this.m_225238_($$1, $$2 = p_225242_.m_159777_())) {
            return false;
        }
        SculkPatchConfiguration $$3 = p_225242_.m_159778_();
        RandomSource $$4 = p_225242_.m_225041_();
        SculkSpreader $$5 = SculkSpreader.m_222274_();
        int $$6 = $$3.f_225430_() + $$3.f_225429_();
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            for (int $$8 = 0; $$8 < $$3.f_225426_(); ++$$8) {
                $$5.m_222266_($$2, $$3.f_225427_());
            }
            boolean $$9 = $$7 < $$3.f_225430_();
            for (int $$10 = 0; $$10 < $$3.f_225428_(); ++$$10) {
                $$5.m_222255_($$1, $$2, $$4, $$9);
            }
            $$5.m_222284_();
        }
        BlockPos $$11 = $$2.m_7495_();
        if ($$4.m_188501_() <= $$3.f_225432_() && $$1.m_8055_($$11).m_60838_($$1, $$11)) {
            $$1.m_7731_($$2, Blocks.f_220857_.m_49966_(), 3);
        }
        int $$12 = $$3.f_225431_().m_214085_($$4);
        for (int $$13 = 0; $$13 < $$12; ++$$13) {
            BlockPos $$14 = $$2.m_7918_($$4.m_188503_(5) - 2, 0, $$4.m_188503_(5) - 2);
            if (!$$1.m_8055_($$14).m_60795_() || !$$1.m_8055_($$14.m_7495_()).m_60783_($$1, $$14.m_7495_(), Direction.UP)) continue;
            $$1.m_7731_($$14, (BlockState)Blocks.f_220858_.m_49966_().m_61124_(SculkShriekerBlock.f_222154_, true), 3);
        }
        return true;
    }

    private boolean m_225238_(LevelAccessor p_225239_, BlockPos p_225240_) {
        block5: {
            block4: {
                BlockState $$2 = p_225239_.m_8055_(p_225240_);
                if ($$2.m_60734_() instanceof SculkBehaviour) {
                    return true;
                }
                if ($$2.m_60795_()) break block4;
                if (!$$2.m_60713_(Blocks.f_49990_) || !$$2.m_60819_().m_76170_()) break block5;
            }
            return Direction.m_235666_().map(p_225240_::m_121945_).anyMatch(p_225245_ -> p_225239_.m_8055_((BlockPos)p_225245_).m_60838_(p_225239_, (BlockPos)p_225245_));
        }
        return false;
    }
}

