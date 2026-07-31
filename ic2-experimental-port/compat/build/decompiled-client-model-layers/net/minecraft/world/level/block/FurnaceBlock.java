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
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FurnaceBlock
extends AbstractFurnaceBlock {
    protected FurnaceBlock(BlockBehaviour.Properties p_53627_) {
        super(p_53627_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153277_, BlockState p_153278_) {
        return new FurnaceBlockEntity(p_153277_, p_153278_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_) {
        return FurnaceBlock.m_151987_(p_153273_, p_153275_, BlockEntityType.f_58917_);
    }

    @Override
    protected void m_7137_(Level p_53631_, BlockPos p_53632_, Player p_53633_) {
        BlockEntity $$3 = p_53631_.m_7702_(p_53632_);
        if ($$3 instanceof FurnaceBlockEntity) {
            p_53633_.m_5893_((MenuProvider)((Object)$$3));
            p_53633_.m_36220_(Stats.f_12966_);
        }
    }

    @Override
    public void m_214162_(BlockState p_221253_, Level p_221254_, BlockPos p_221255_, RandomSource p_221256_) {
        if (!p_221253_.m_61143_(f_48684_).booleanValue()) {
            return;
        }
        double $$4 = (double)p_221255_.m_123341_() + 0.5;
        double $$5 = p_221255_.m_123342_();
        double $$6 = (double)p_221255_.m_123343_() + 0.5;
        if (p_221256_.m_188500_() < 0.1) {
            p_221254_.m_7785_($$4, $$5, $$6, SoundEvents.f_11907_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }
        Direction $$7 = p_221253_.m_61143_(f_48683_);
        Direction.Axis $$8 = $$7.m_122434_();
        double $$9 = 0.52;
        double $$10 = p_221256_.m_188500_() * 0.6 - 0.3;
        double $$11 = $$8 == Direction.Axis.X ? (double)$$7.m_122429_() * 0.52 : $$10;
        double $$12 = p_221256_.m_188500_() * 6.0 / 16.0;
        double $$13 = $$8 == Direction.Axis.Z ? (double)$$7.m_122431_() * 0.52 : $$10;
        p_221254_.m_7106_(ParticleTypes.f_123762_, $$4 + $$11, $$5 + $$12, $$6 + $$13, 0.0, 0.0, 0.0);
        p_221254_.m_7106_(ParticleTypes.f_123744_, $$4 + $$11, $$5 + $$12, $$6 + $$13, 0.0, 0.0, 0.0);
    }
}

