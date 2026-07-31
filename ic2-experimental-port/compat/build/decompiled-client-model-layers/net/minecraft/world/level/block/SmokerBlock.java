/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SmokerBlock
extends AbstractFurnaceBlock {
    protected SmokerBlock(BlockBehaviour.Properties p_56439_) {
        super(p_56439_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_154644_, BlockState p_154645_) {
        return new SmokerBlockEntity(p_154644_, p_154645_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154640_, BlockState p_154641_, BlockEntityType<T> p_154642_) {
        return SmokerBlock.m_151987_(p_154640_, p_154642_, BlockEntityType.f_58906_);
    }

    @Override
    protected void m_7137_(Level p_56443_, BlockPos p_56444_, Player p_56445_) {
        BlockEntity $$3 = p_56443_.m_7702_(p_56444_);
        if ($$3 instanceof SmokerBlockEntity) {
            p_56445_.m_5893_((MenuProvider)((Object)$$3));
            p_56445_.m_36220_(Stats.f_12973_);
        }
    }

    @Override
    public void m_214162_(BlockState p_222443_, Level p_222444_, BlockPos p_222445_, RandomSource p_222446_) {
        if (!p_222443_.m_61143_(f_48684_).booleanValue()) {
            return;
        }
        double $$4 = (double)p_222445_.m_123341_() + 0.5;
        double $$5 = p_222445_.m_123342_();
        double $$6 = (double)p_222445_.m_123343_() + 0.5;
        if (p_222446_.m_188500_() < 0.1) {
            p_222444_.m_7785_($$4, $$5, $$6, SoundEvents.f_12472_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }
        p_222444_.m_7106_(ParticleTypes.f_123762_, $$4, $$5 + 1.1, $$6, 0.0, 0.0, 0.0);
    }
}

