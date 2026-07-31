/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MushroomBlock
extends BushBlock
implements BonemealableBlock {
    protected static final float f_153980_ = 3.0f;
    protected static final VoxelShape f_54855_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final Supplier<Holder<? extends ConfiguredFeature<?, ?>>> f_153981_;

    public MushroomBlock(BlockBehaviour.Properties p_153983_, Supplier<Holder<? extends ConfiguredFeature<?, ?>>> p_153984_) {
        super(p_153983_);
        this.f_153981_ = p_153984_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54889_, BlockGetter p_54890_, BlockPos p_54891_, CollisionContext p_54892_) {
        return f_54855_;
    }

    @Override
    public void m_213898_(BlockState p_221784_, ServerLevel p_221785_, BlockPos p_221786_, RandomSource p_221787_) {
        if (p_221787_.m_188503_(25) == 0) {
            int $$4 = 5;
            int $$5 = 4;
            for (BlockPos $$6 : BlockPos.m_121940_(p_221786_.m_7918_(-4, -1, -4), p_221786_.m_7918_(4, 1, 4))) {
                if (!p_221785_.m_8055_($$6).m_60713_(this) || --$$4 > 0) continue;
                return;
            }
            BlockPos $$7 = p_221786_.m_7918_(p_221787_.m_188503_(3) - 1, p_221787_.m_188503_(2) - p_221787_.m_188503_(2), p_221787_.m_188503_(3) - 1);
            for (int $$8 = 0; $$8 < 4; ++$$8) {
                if (p_221785_.m_46859_($$7) && p_221784_.m_60710_(p_221785_, $$7)) {
                    p_221786_ = $$7;
                }
                $$7 = p_221786_.m_7918_(p_221787_.m_188503_(3) - 1, p_221787_.m_188503_(2) - p_221787_.m_188503_(2), p_221787_.m_188503_(3) - 1);
            }
            if (p_221785_.m_46859_($$7) && p_221784_.m_60710_(p_221785_, $$7)) {
                p_221785_.m_7731_($$7, p_221784_, 2);
            }
        }
    }

    @Override
    protected boolean m_6266_(BlockState p_54894_, BlockGetter p_54895_, BlockPos p_54896_) {
        return p_54894_.m_60804_(p_54895_, p_54896_);
    }

    @Override
    public boolean m_7898_(BlockState p_54880_, LevelReader p_54881_, BlockPos p_54882_) {
        BlockPos $$3 = p_54882_.m_7495_();
        BlockState $$4 = p_54881_.m_8055_($$3);
        if ($$4.m_204336_(BlockTags.f_13057_)) {
            return true;
        }
        return p_54881_.m_45524_(p_54882_, 0) < 13 && this.m_6266_($$4, p_54881_, $$3);
    }

    public boolean m_221773_(ServerLevel p_221774_, BlockPos p_221775_, BlockState p_221776_, RandomSource p_221777_) {
        p_221774_.m_7471_(p_221775_, false);
        if (this.f_153981_.get().m_203334_().m_224953_(p_221774_, p_221774_.m_7726_().m_8481_(), p_221777_, p_221775_)) {
            return true;
        }
        p_221774_.m_7731_(p_221775_, p_221776_, 3);
        return false;
    }

    @Override
    public boolean m_7370_(BlockGetter p_54870_, BlockPos p_54871_, BlockState p_54872_, boolean p_54873_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_221779_, RandomSource p_221780_, BlockPos p_221781_, BlockState p_221782_) {
        return (double)p_221780_.m_188501_() < 0.4;
    }

    @Override
    public void m_214148_(ServerLevel p_221769_, RandomSource p_221770_, BlockPos p_221771_, BlockState p_221772_) {
        this.m_221773_(p_221769_, p_221771_, p_221772_, p_221770_);
    }
}

