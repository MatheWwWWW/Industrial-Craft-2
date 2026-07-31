/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TheEndPortalBlockEntity
extends BlockEntity {
    protected TheEndPortalBlockEntity(BlockEntityType<?> p_155855_, BlockPos p_155856_, BlockState p_155857_) {
        super(p_155855_, p_155856_, p_155857_);
    }

    public TheEndPortalBlockEntity(BlockPos p_155859_, BlockState p_155860_) {
        this(BlockEntityType.f_58929_, p_155859_, p_155860_);
    }

    public boolean m_6665_(Direction p_59980_) {
        return p_59980_.m_122434_() == Direction.Axis.Y;
    }
}

