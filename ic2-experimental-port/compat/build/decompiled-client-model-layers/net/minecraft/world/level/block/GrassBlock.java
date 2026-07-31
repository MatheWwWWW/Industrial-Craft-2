/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GrassBlock
extends SpreadingSnowyDirtBlock
implements BonemealableBlock {
    public GrassBlock(BlockBehaviour.Properties p_53685_) {
        super(p_53685_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_53692_, BlockPos p_53693_, BlockState p_53694_, boolean p_53695_) {
        return p_53692_.m_8055_(p_53693_.m_7494_()).m_60795_();
    }

    @Override
    public boolean m_214167_(Level p_221275_, RandomSource p_221276_, BlockPos p_221277_, BlockState p_221278_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221270_, RandomSource p_221271_, BlockPos p_221272_, BlockState p_221273_) {
        BlockPos $$4 = p_221272_.m_7494_();
        BlockState $$5 = Blocks.f_50034_.m_49966_();
        block0: for (int $$6 = 0; $$6 < 128; ++$$6) {
            Holder<PlacedFeature> $$12;
            BlockPos $$7 = $$4;
            for (int $$8 = 0; $$8 < $$6 / 16; ++$$8) {
                if (!p_221270_.m_8055_(($$7 = $$7.m_7918_(p_221271_.m_188503_(3) - 1, (p_221271_.m_188503_(3) - 1) * p_221271_.m_188503_(3) / 2, p_221271_.m_188503_(3) - 1)).m_7495_()).m_60713_(this) || p_221270_.m_8055_($$7).m_60838_(p_221270_, $$7)) continue block0;
            }
            BlockState $$9 = p_221270_.m_8055_($$7);
            if ($$9.m_60713_($$5.m_60734_()) && p_221271_.m_188503_(10) == 0) {
                ((BonemealableBlock)((Object)$$5.m_60734_())).m_214148_(p_221270_, p_221271_, $$7, $$9);
            }
            if (!$$9.m_60795_()) continue;
            if (p_221271_.m_188503_(8) == 0) {
                List<ConfiguredFeature<?, ?>> $$10 = p_221270_.m_204166_($$7).m_203334_().m_47536_().m_47815_();
                if ($$10.isEmpty()) continue;
                Holder<PlacedFeature> $$11 = ((RandomPatchConfiguration)$$10.get(0).f_65378_()).f_191304_();
            } else {
                $$12 = VegetationPlacements.f_195459_;
            }
            $$12.m_203334_().m_226357_(p_221270_, p_221270_.m_7726_().m_8481_(), p_221271_, $$7);
        }
    }
}

