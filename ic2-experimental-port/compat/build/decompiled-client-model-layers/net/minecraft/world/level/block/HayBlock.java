/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class HayBlock
extends RotatedPillarBlock {
    public HayBlock(BlockBehaviour.Properties p_53976_) {
        super(p_53976_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55923_, Direction.Axis.Y));
    }

    @Override
    public void m_142072_(Level p_153362_, BlockState p_153363_, BlockPos p_153364_, Entity p_153365_, float p_153366_) {
        p_153365_.m_142535_(p_153366_, 0.2f, DamageSource.f_19315_);
    }
}

