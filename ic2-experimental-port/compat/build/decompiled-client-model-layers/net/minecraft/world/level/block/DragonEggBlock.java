/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonEggBlock
extends FallingBlock {
    protected static final VoxelShape f_52908_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public DragonEggBlock(BlockBehaviour.Properties p_52911_) {
        super(p_52911_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52930_, BlockGetter p_52931_, BlockPos p_52932_, CollisionContext p_52933_) {
        return f_52908_;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52923_, Level p_52924_, BlockPos p_52925_, Player p_52926_, InteractionHand p_52927_, BlockHitResult p_52928_) {
        this.m_52935_(p_52923_, p_52924_, p_52925_);
        return InteractionResult.m_19078_(p_52924_.f_46443_);
    }

    @Override
    public void m_6256_(BlockState p_52918_, Level p_52919_, BlockPos p_52920_, Player p_52921_) {
        this.m_52935_(p_52918_, p_52919_, p_52920_);
    }

    private void m_52935_(BlockState p_52936_, Level p_52937_, BlockPos p_52938_) {
        WorldBorder $$3 = p_52937_.m_6857_();
        for (int $$4 = 0; $$4 < 1000; ++$$4) {
            BlockPos $$5 = p_52938_.m_7918_(p_52937_.f_46441_.m_188503_(16) - p_52937_.f_46441_.m_188503_(16), p_52937_.f_46441_.m_188503_(8) - p_52937_.f_46441_.m_188503_(8), p_52937_.f_46441_.m_188503_(16) - p_52937_.f_46441_.m_188503_(16));
            if (!p_52937_.m_8055_($$5).m_60795_() || !$$3.m_61937_($$5)) continue;
            if (p_52937_.f_46443_) {
                for (int $$6 = 0; $$6 < 128; ++$$6) {
                    double $$7 = p_52937_.f_46441_.m_188500_();
                    float $$8 = (p_52937_.f_46441_.m_188501_() - 0.5f) * 0.2f;
                    float $$9 = (p_52937_.f_46441_.m_188501_() - 0.5f) * 0.2f;
                    float $$10 = (p_52937_.f_46441_.m_188501_() - 0.5f) * 0.2f;
                    double $$11 = Mth.m_14139_($$7, $$5.m_123341_(), p_52938_.m_123341_()) + (p_52937_.f_46441_.m_188500_() - 0.5) + 0.5;
                    double $$12 = Mth.m_14139_($$7, $$5.m_123342_(), p_52938_.m_123342_()) + p_52937_.f_46441_.m_188500_() - 0.5;
                    double $$13 = Mth.m_14139_($$7, $$5.m_123343_(), p_52938_.m_123343_()) + (p_52937_.f_46441_.m_188500_() - 0.5) + 0.5;
                    p_52937_.m_7106_(ParticleTypes.f_123760_, $$11, $$12, $$13, $$8, $$9, $$10);
                }
            } else {
                p_52937_.m_7731_($$5, p_52936_, 2);
                p_52937_.m_7471_(p_52938_, false);
            }
            return;
        }
    }

    @Override
    protected int m_7198_() {
        return 5;
    }

    @Override
    public boolean m_7357_(BlockState p_52913_, BlockGetter p_52914_, BlockPos p_52915_, PathComputationType p_52916_) {
        return false;
    }
}

