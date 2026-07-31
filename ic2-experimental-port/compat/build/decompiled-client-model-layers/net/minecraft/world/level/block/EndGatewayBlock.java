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
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class EndGatewayBlock
extends BaseEntityBlock {
    protected EndGatewayBlock(BlockBehaviour.Properties p_52999_) {
        super(p_52999_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153193_, BlockState p_153194_) {
        return new TheEndGatewayBlockEntity(p_153193_, p_153194_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153189_, BlockState p_153190_, BlockEntityType<T> p_153191_) {
        return EndGatewayBlock.m_152132_(p_153191_, BlockEntityType.f_58937_, p_153189_.f_46443_ ? TheEndGatewayBlockEntity::m_155834_ : TheEndGatewayBlockEntity::m_155844_);
    }

    @Override
    public void m_214162_(BlockState p_221097_, Level p_221098_, BlockPos p_221099_, RandomSource p_221100_) {
        BlockEntity $$4 = p_221098_.m_7702_(p_221099_);
        if (!($$4 instanceof TheEndGatewayBlockEntity)) {
            return;
        }
        int $$5 = ((TheEndGatewayBlockEntity)$$4).m_59975_();
        for (int $$6 = 0; $$6 < $$5; ++$$6) {
            double $$7 = (double)p_221099_.m_123341_() + p_221100_.m_188500_();
            double $$8 = (double)p_221099_.m_123342_() + p_221100_.m_188500_();
            double $$9 = (double)p_221099_.m_123343_() + p_221100_.m_188500_();
            double $$10 = (p_221100_.m_188500_() - 0.5) * 0.5;
            double $$11 = (p_221100_.m_188500_() - 0.5) * 0.5;
            double $$12 = (p_221100_.m_188500_() - 0.5) * 0.5;
            int $$13 = p_221100_.m_188503_(2) * 2 - 1;
            if (p_221100_.m_188499_()) {
                $$9 = (double)p_221099_.m_123343_() + 0.5 + 0.25 * (double)$$13;
                $$12 = p_221100_.m_188501_() * 2.0f * (float)$$13;
            } else {
                $$7 = (double)p_221099_.m_123341_() + 0.5 + 0.25 * (double)$$13;
                $$10 = p_221100_.m_188501_() * 2.0f * (float)$$13;
            }
            p_221098_.m_7106_(ParticleTypes.f_123760_, $$7, $$8, $$9, $$10, $$11, $$12);
        }
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_53003_, BlockPos p_53004_, BlockState p_53005_) {
        return ItemStack.f_41583_;
    }

    @Override
    public boolean m_5946_(BlockState p_53012_, Fluid p_53013_) {
        return false;
    }
}

