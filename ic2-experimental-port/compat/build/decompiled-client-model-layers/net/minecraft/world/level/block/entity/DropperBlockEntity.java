/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DropperBlockEntity
extends DispenserBlockEntity {
    public DropperBlockEntity(BlockPos p_155498_, BlockState p_155499_) {
        super(BlockEntityType.f_58923_, p_155498_, p_155499_);
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.dropper");
    }
}

