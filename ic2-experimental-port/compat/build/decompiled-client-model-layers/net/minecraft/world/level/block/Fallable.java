/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Fallable {
    default public void m_48792_(Level p_153220_, BlockPos p_153221_, BlockState p_153222_, BlockState p_153223_, FallingBlockEntity p_153224_) {
    }

    default public void m_142525_(Level p_153217_, BlockPos p_153218_, FallingBlockEntity p_153219_) {
    }

    default public DamageSource m_142088_() {
        return DamageSource.f_19322_;
    }

    default public Predicate<Entity> m_142398_() {
        return EntitySelector.f_20408_;
    }
}

