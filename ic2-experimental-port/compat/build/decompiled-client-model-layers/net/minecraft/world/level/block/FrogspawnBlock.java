/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrogspawnBlock
extends Block {
    private static final int f_221169_ = 2;
    private static final int f_221170_ = 5;
    private static final int f_221171_ = 3600;
    private static final int f_221172_ = 12000;
    protected static final VoxelShape f_221168_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
    private static int f_221173_ = 3600;
    private static int f_221174_ = 12000;

    public FrogspawnBlock(BlockBehaviour.Properties p_221177_) {
        super(p_221177_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_221199_, BlockGetter p_221200_, BlockPos p_221201_, CollisionContext p_221202_) {
        return f_221168_;
    }

    @Override
    public boolean m_7898_(BlockState p_221209_, LevelReader p_221210_, BlockPos p_221211_) {
        return FrogspawnBlock.m_221187_(p_221210_, p_221211_.m_7495_());
    }

    @Override
    public void m_6807_(BlockState p_221227_, Level p_221228_, BlockPos p_221229_, BlockState p_221230_, boolean p_221231_) {
        p_221228_.m_186460_(p_221229_, this, FrogspawnBlock.m_221185_(p_221228_.m_213780_()));
    }

    private static int m_221185_(RandomSource p_221186_) {
        return p_221186_.m_216339_(f_221173_, f_221174_);
    }

    @Override
    public BlockState m_7417_(BlockState p_221213_, Direction p_221214_, BlockState p_221215_, LevelAccessor p_221216_, BlockPos p_221217_, BlockPos p_221218_) {
        if (!this.m_7898_(p_221213_, p_221216_, p_221217_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_221213_, p_221214_, p_221215_, p_221216_, p_221217_, p_221218_);
    }

    @Override
    public void m_213897_(BlockState p_221194_, ServerLevel p_221195_, BlockPos p_221196_, RandomSource p_221197_) {
        if (!this.m_7898_(p_221194_, p_221195_, p_221196_)) {
            this.m_221190_(p_221195_, p_221196_);
            return;
        }
        this.m_221181_(p_221195_, p_221196_, p_221197_);
    }

    @Override
    public void m_7892_(BlockState p_221204_, Level p_221205_, BlockPos p_221206_, Entity p_221207_) {
        if (p_221207_.m_6095_().equals(EntityType.f_20450_)) {
            this.m_221190_(p_221205_, p_221206_);
        }
    }

    private static boolean m_221187_(BlockGetter p_221188_, BlockPos p_221189_) {
        FluidState $$2 = p_221188_.m_6425_(p_221189_);
        FluidState $$3 = p_221188_.m_6425_(p_221189_.m_7494_());
        return $$2.m_76152_() == Fluids.f_76193_ && $$3.m_76152_() == Fluids.f_76191_;
    }

    private void m_221181_(ServerLevel p_221182_, BlockPos p_221183_, RandomSource p_221184_) {
        this.m_221190_(p_221182_, p_221183_);
        p_221182_.m_5594_(null, p_221183_, SoundEvents.f_215687_, SoundSource.BLOCKS, 1.0f, 1.0f);
        this.m_221220_(p_221182_, p_221183_, p_221184_);
    }

    private void m_221190_(Level p_221191_, BlockPos p_221192_) {
        p_221191_.m_46961_(p_221192_, false);
    }

    private void m_221220_(ServerLevel p_221221_, BlockPos p_221222_, RandomSource p_221223_) {
        int $$3 = p_221223_.m_216339_(2, 6);
        for (int $$4 = 1; $$4 <= $$3; ++$$4) {
            Tadpole $$5 = EntityType.f_217013_.m_20615_(p_221221_);
            double $$6 = (double)p_221222_.m_123341_() + this.m_221224_(p_221223_);
            double $$7 = (double)p_221222_.m_123343_() + this.m_221224_(p_221223_);
            int $$8 = p_221223_.m_216339_(1, 361);
            $$5.m_7678_($$6, (double)p_221222_.m_123342_() - 0.5, $$7, $$8, 0.0f);
            $$5.m_21530_();
            p_221221_.m_7967_($$5);
        }
    }

    private double m_221224_(RandomSource p_221225_) {
        double $$1 = Tadpole.f_218681_ / 2.0f;
        return Mth.m_14008_(p_221225_.m_188500_(), $$1, 1.0 - $$1);
    }

    @VisibleForTesting
    public static void m_221178_(int p_221179_, int p_221180_) {
        f_221173_ = p_221179_;
        f_221174_ = p_221180_;
    }

    @VisibleForTesting
    public static void m_221219_() {
        f_221173_ = 3600;
        f_221174_ = 12000;
    }
}

