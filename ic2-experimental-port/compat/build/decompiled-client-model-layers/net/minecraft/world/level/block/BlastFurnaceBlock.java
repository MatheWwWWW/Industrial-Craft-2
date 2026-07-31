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
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlastFurnaceBlock
extends AbstractFurnaceBlock {
    protected BlastFurnaceBlock(BlockBehaviour.Properties p_49773_) {
        super(p_49773_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_152386_, BlockState p_152387_) {
        return new BlastFurnaceBlockEntity(p_152386_, p_152387_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152382_, BlockState p_152383_, BlockEntityType<T> p_152384_) {
        return BlastFurnaceBlock.m_151987_(p_152382_, p_152384_, BlockEntityType.f_58907_);
    }

    @Override
    protected void m_7137_(Level p_49777_, BlockPos p_49778_, Player p_49779_) {
        BlockEntity $$3 = p_49777_.m_7702_(p_49778_);
        if ($$3 instanceof BlastFurnaceBlockEntity) {
            p_49779_.m_5893_((MenuProvider)((Object)$$3));
            p_49779_.m_36220_(Stats.f_12972_);
        }
    }

    @Override
    public void m_214162_(BlockState p_220818_, Level p_220819_, BlockPos p_220820_, RandomSource p_220821_) {
        if (!p_220818_.m_61143_(f_48684_).booleanValue()) {
            return;
        }
        double $$4 = (double)p_220820_.m_123341_() + 0.5;
        double $$5 = p_220820_.m_123342_();
        double $$6 = (double)p_220820_.m_123343_() + 0.5;
        if (p_220821_.m_188500_() < 0.1) {
            p_220819_.m_7785_($$4, $$5, $$6, SoundEvents.f_11715_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }
        Direction $$7 = p_220818_.m_61143_(f_48683_);
        Direction.Axis $$8 = $$7.m_122434_();
        double $$9 = 0.52;
        double $$10 = p_220821_.m_188500_() * 0.6 - 0.3;
        double $$11 = $$8 == Direction.Axis.X ? (double)$$7.m_122429_() * 0.52 : $$10;
        double $$12 = p_220821_.m_188500_() * 9.0 / 16.0;
        double $$13 = $$8 == Direction.Axis.Z ? (double)$$7.m_122431_() * 0.52 : $$10;
        p_220819_.m_7106_(ParticleTypes.f_123762_, $$4 + $$11, $$5 + $$12, $$6 + $$13, 0.0, 0.0, 0.0);
    }
}

