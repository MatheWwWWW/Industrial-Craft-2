/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WeightedPressurePlateBlock
extends BasePressurePlateBlock {
    public static final IntegerProperty f_58198_ = BlockStateProperties.f_61426_;
    private final int f_58199_;

    protected WeightedPressurePlateBlock(int p_58202_, BlockBehaviour.Properties p_58203_) {
        super(p_58203_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_58198_, 0));
        this.f_58199_ = p_58202_;
    }

    @Override
    protected int m_6693_(Level p_58213_, BlockPos p_58214_) {
        int $$2 = Math.min(p_58213_.m_45976_(Entity.class, f_49287_.m_82338_(p_58214_)).size(), this.f_58199_);
        if ($$2 > 0) {
            float $$3 = (float)Math.min(this.f_58199_, $$2) / (float)this.f_58199_;
            return Mth.m_14167_($$3 * 15.0f);
        }
        return 0;
    }

    @Override
    protected void m_5494_(LevelAccessor p_58205_, BlockPos p_58206_) {
        p_58205_.m_5594_(null, p_58206_, SoundEvents.f_12067_, SoundSource.BLOCKS, 0.3f, 0.90000004f);
    }

    @Override
    protected void m_5493_(LevelAccessor p_58216_, BlockPos p_58217_) {
        p_58216_.m_5594_(null, p_58217_, SoundEvents.f_12066_, SoundSource.BLOCKS, 0.3f, 0.75f);
    }

    @Override
    protected int m_6016_(BlockState p_58220_) {
        return p_58220_.m_61143_(f_58198_);
    }

    @Override
    protected BlockState m_7422_(BlockState p_58208_, int p_58209_) {
        return (BlockState)p_58208_.m_61124_(f_58198_, p_58209_);
    }

    @Override
    protected int m_7342_() {
        return 10;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58211_) {
        p_58211_.m_61104_(f_58198_);
    }
}

