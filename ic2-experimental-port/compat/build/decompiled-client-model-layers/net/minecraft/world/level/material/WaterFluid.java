/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.material;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class WaterFluid
extends FlowingFluid {
    @Override
    public Fluid m_5615_() {
        return Fluids.f_76192_;
    }

    @Override
    public Fluid m_5613_() {
        return Fluids.f_76193_;
    }

    @Override
    public Item m_6859_() {
        return Items.f_42447_;
    }

    @Override
    public void m_213811_(Level p_230606_, BlockPos p_230607_, FluidState p_230608_, RandomSource p_230609_) {
        if (!p_230608_.m_76170_() && !p_230608_.m_61143_(f_75947_).booleanValue()) {
            if (p_230609_.m_188503_(64) == 0) {
                p_230606_.m_7785_((double)p_230607_.m_123341_() + 0.5, (double)p_230607_.m_123342_() + 0.5, (double)p_230607_.m_123343_() + 0.5, SoundEvents.f_12540_, SoundSource.BLOCKS, p_230609_.m_188501_() * 0.25f + 0.75f, p_230609_.m_188501_() + 0.5f, false);
            }
        } else if (p_230609_.m_188503_(10) == 0) {
            p_230606_.m_7106_(ParticleTypes.f_123768_, (double)p_230607_.m_123341_() + p_230609_.m_188500_(), (double)p_230607_.m_123342_() + p_230609_.m_188500_(), (double)p_230607_.m_123343_() + p_230609_.m_188500_(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    @Nullable
    public ParticleOptions m_7792_() {
        return ParticleTypes.f_123803_;
    }

    @Override
    protected boolean m_6760_() {
        return true;
    }

    @Override
    protected void m_7456_(LevelAccessor p_76450_, BlockPos p_76451_, BlockState p_76452_) {
        BlockEntity $$3 = p_76452_.m_155947_() ? p_76450_.m_7702_(p_76451_) : null;
        Block.m_49892_(p_76452_, p_76450_, p_76451_, $$3);
    }

    @Override
    public int m_6719_(LevelReader p_76464_) {
        return 4;
    }

    @Override
    public BlockState m_5804_(FluidState p_76466_) {
        return (BlockState)Blocks.f_49990_.m_49966_().m_61124_(LiquidBlock.f_54688_, WaterFluid.m_76092_(p_76466_));
    }

    @Override
    public boolean m_6212_(Fluid p_76456_) {
        return p_76456_ == Fluids.f_76193_ || p_76456_ == Fluids.f_76192_;
    }

    @Override
    public int m_6713_(LevelReader p_76469_) {
        return 1;
    }

    @Override
    public int m_6718_(LevelReader p_76454_) {
        return 5;
    }

    @Override
    public boolean m_5486_(FluidState p_76458_, BlockGetter p_76459_, BlockPos p_76460_, Fluid p_76461_, Direction p_76462_) {
        return p_76462_ == Direction.DOWN && !p_76461_.m_205067_(FluidTags.f_13131_);
    }

    @Override
    protected float m_6752_() {
        return 100.0f;
    }

    @Override
    public Optional<SoundEvent> m_142520_() {
        return Optional.of(SoundEvents.f_11781_);
    }

    public static class Flowing
    extends WaterFluid {
        @Override
        protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76476_) {
            super.m_7180_(p_76476_);
            p_76476_.m_61104_(f_75948_);
        }

        @Override
        public int m_7430_(FluidState p_76480_) {
            return p_76480_.m_61143_(f_75948_);
        }

        @Override
        public boolean m_7444_(FluidState p_76478_) {
            return false;
        }
    }

    public static class Source
    extends WaterFluid {
        @Override
        public int m_7430_(FluidState p_76485_) {
            return 8;
        }

        @Override
        public boolean m_7444_(FluidState p_76483_) {
            return true;
        }
    }
}

