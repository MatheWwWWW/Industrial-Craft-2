/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ComparatorBlockEntity
extends BlockEntity {
    private int f_59173_;

    public ComparatorBlockEntity(BlockPos p_155386_, BlockState p_155387_) {
        super(BlockEntityType.f_58934_, p_155386_, p_155387_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187493_) {
        super.m_183515_(p_187493_);
        p_187493_.m_128405_("OutputSignal", this.f_59173_);
    }

    @Override
    public void m_142466_(CompoundTag p_155389_) {
        super.m_142466_(p_155389_);
        this.f_59173_ = p_155389_.m_128451_("OutputSignal");
    }

    public int m_59182_() {
        return this.f_59173_;
    }

    public void m_59175_(int p_59176_) {
        this.f_59173_ = p_59176_;
    }
}

