/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class FallingBlock
extends Block
implements Fallable {
    public FallingBlock(BlockBehaviour.Properties p_53205_) {
        super(p_53205_);
    }

    @Override
    public void m_6807_(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
        p_53234_.m_186460_(p_53235_, this, this.m_7198_());
    }

    @Override
    public BlockState m_7417_(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor p_53229_, BlockPos p_53230_, BlockPos p_53231_) {
        p_53229_.m_186460_(p_53230_, this, this.m_7198_());
        return super.m_7417_(p_53226_, p_53227_, p_53228_, p_53229_, p_53230_, p_53231_);
    }

    @Override
    public void m_213897_(BlockState p_221124_, ServerLevel p_221125_, BlockPos p_221126_, RandomSource p_221127_) {
        if (!FallingBlock.m_53241_(p_221125_.m_8055_(p_221126_.m_7495_())) || p_221126_.m_123342_() < p_221125_.m_141937_()) {
            return;
        }
        FallingBlockEntity $$4 = FallingBlockEntity.m_201971_(p_221125_, p_221126_, p_221124_);
        this.m_6788_($$4);
    }

    protected void m_6788_(FallingBlockEntity p_53206_) {
    }

    protected int m_7198_() {
        return 2;
    }

    public static boolean m_53241_(BlockState p_53242_) {
        Material $$1 = p_53242_.m_60767_();
        return p_53242_.m_60795_() || p_53242_.m_204336_(BlockTags.f_13076_) || $$1.m_76332_() || $$1.m_76336_();
    }

    @Override
    public void m_214162_(BlockState p_221129_, Level p_221130_, BlockPos p_221131_, RandomSource p_221132_) {
        BlockPos $$4;
        if (p_221132_.m_188503_(16) == 0 && FallingBlock.m_53241_(p_221130_.m_8055_($$4 = p_221131_.m_7495_()))) {
            double $$5 = (double)p_221131_.m_123341_() + p_221132_.m_188500_();
            double $$6 = (double)p_221131_.m_123342_() - 0.05;
            double $$7 = (double)p_221131_.m_123343_() + p_221132_.m_188500_();
            p_221130_.m_7106_(new BlockParticleOption(ParticleTypes.f_123814_, p_221129_), $$5, $$6, $$7, 0.0, 0.0, 0.0);
        }
    }

    public int m_6248_(BlockState p_53238_, BlockGetter p_53239_, BlockPos p_53240_) {
        return -16777216;
    }
}

