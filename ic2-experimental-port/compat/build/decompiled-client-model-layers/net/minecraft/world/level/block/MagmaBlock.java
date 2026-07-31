/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaBlock
extends Block {
    private static final int f_153775_ = 20;

    public MagmaBlock(BlockBehaviour.Properties p_54800_) {
        super(p_54800_);
    }

    @Override
    public void m_141947_(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
        if (!p_153780_.m_20161_() && p_153780_ instanceof LivingEntity && !EnchantmentHelper.m_44938_((LivingEntity)p_153780_)) {
            p_153780_.m_6469_(DamageSource.f_19309_, 1.0f);
        }
        super.m_141947_(p_153777_, p_153778_, p_153779_, p_153780_);
    }

    @Override
    public void m_213897_(BlockState p_221415_, ServerLevel p_221416_, BlockPos p_221417_, RandomSource p_221418_) {
        BubbleColumnBlock.m_152707_(p_221416_, p_221417_.m_7494_(), p_221415_);
    }

    @Override
    public BlockState m_7417_(BlockState p_54811_, Direction p_54812_, BlockState p_54813_, LevelAccessor p_54814_, BlockPos p_54815_, BlockPos p_54816_) {
        if (p_54812_ == Direction.UP && p_54813_.m_60713_(Blocks.f_49990_)) {
            p_54814_.m_186460_(p_54815_, this, 20);
        }
        return super.m_7417_(p_54811_, p_54812_, p_54813_, p_54814_, p_54815_, p_54816_);
    }

    @Override
    public void m_213898_(BlockState p_221420_, ServerLevel p_221421_, BlockPos p_221422_, RandomSource p_221423_) {
        BlockPos $$4 = p_221422_.m_7494_();
        if (p_221421_.m_6425_(p_221422_).m_205070_(FluidTags.f_13131_)) {
            p_221421_.m_5594_(null, p_221422_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + (p_221421_.f_46441_.m_188501_() - p_221421_.f_46441_.m_188501_()) * 0.8f);
            p_221421_.m_8767_(ParticleTypes.f_123755_, (double)$$4.m_123341_() + 0.5, (double)$$4.m_123342_() + 0.25, (double)$$4.m_123343_() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
        }
    }

    @Override
    public void m_6807_(BlockState p_54823_, Level p_54824_, BlockPos p_54825_, BlockState p_54826_, boolean p_54827_) {
        p_54824_.m_186460_(p_54825_, this, 20);
    }
}

