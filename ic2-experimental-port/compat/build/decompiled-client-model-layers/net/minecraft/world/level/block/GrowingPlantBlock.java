/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantBlock
extends Block {
    protected final Direction f_53859_;
    protected final boolean f_53860_;
    protected final VoxelShape f_53861_;

    protected GrowingPlantBlock(BlockBehaviour.Properties p_53863_, Direction p_53864_, VoxelShape p_53865_, boolean p_53866_) {
        super(p_53863_);
        this.f_53859_ = p_53864_;
        this.f_53861_ = p_53865_;
        this.f_53860_ = p_53866_;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_53868_) {
        BlockState $$1 = p_53868_.m_43725_().m_8055_(p_53868_.m_8083_().m_121945_(this.f_53859_));
        if ($$1.m_60713_(this.m_7272_()) || $$1.m_60713_(this.m_7777_())) {
            return this.m_7777_().m_49966_();
        }
        return this.m_7722_(p_53868_.m_43725_());
    }

    public BlockState m_7722_(LevelAccessor p_53869_) {
        return this.m_49966_();
    }

    @Override
    public boolean m_7898_(BlockState p_53876_, LevelReader p_53877_, BlockPos p_53878_) {
        BlockPos $$3 = p_53878_.m_121945_(this.f_53859_.m_122424_());
        BlockState $$4 = p_53877_.m_8055_($$3);
        if (!this.m_142209_($$4)) {
            return false;
        }
        return $$4.m_60713_(this.m_7272_()) || $$4.m_60713_(this.m_7777_()) || $$4.m_60783_(p_53877_, $$3, this.f_53859_);
    }

    @Override
    public void m_213897_(BlockState p_221280_, ServerLevel p_221281_, BlockPos p_221282_, RandomSource p_221283_) {
        if (!p_221280_.m_60710_(p_221281_, p_221282_)) {
            p_221281_.m_46961_(p_221282_, true);
        }
    }

    protected boolean m_142209_(BlockState p_153321_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53880_, BlockGetter p_53881_, BlockPos p_53882_, CollisionContext p_53883_) {
        return this.f_53861_;
    }

    protected abstract GrowingPlantHeadBlock m_7272_();

    protected abstract Block m_7777_();
}

