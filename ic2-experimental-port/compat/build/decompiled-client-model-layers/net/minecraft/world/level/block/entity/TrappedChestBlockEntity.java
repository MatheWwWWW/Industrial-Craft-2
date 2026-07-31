/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrappedChestBlockEntity
extends ChestBlockEntity {
    public TrappedChestBlockEntity(BlockPos p_155862_, BlockState p_155863_) {
        super(BlockEntityType.f_58919_, p_155862_, p_155863_);
    }

    @Override
    protected void m_142151_(Level p_155865_, BlockPos p_155866_, BlockState p_155867_, int p_155868_, int p_155869_) {
        super.m_142151_(p_155865_, p_155866_, p_155867_, p_155868_, p_155869_);
        if (p_155868_ != p_155869_) {
            Block $$5 = p_155867_.m_60734_();
            p_155865_.m_46672_(p_155866_, $$5);
            p_155865_.m_46672_(p_155866_.m_7495_(), $$5);
        }
    }
}

