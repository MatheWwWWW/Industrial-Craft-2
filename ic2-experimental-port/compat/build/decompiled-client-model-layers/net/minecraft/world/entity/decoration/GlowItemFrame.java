/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GlowItemFrame
extends ItemFrame {
    public GlowItemFrame(EntityType<? extends ItemFrame> p_149607_, Level p_149608_) {
        super(p_149607_, p_149608_);
    }

    public GlowItemFrame(Level p_149610_, BlockPos p_149611_, Direction p_149612_) {
        super(EntityType.f_147033_, p_149610_, p_149611_, p_149612_);
    }

    @Override
    public SoundEvent m_142544_() {
        return SoundEvents.f_144157_;
    }

    @Override
    public SoundEvent m_142543_() {
        return SoundEvents.f_144155_;
    }

    @Override
    public SoundEvent m_142541_() {
        return SoundEvents.f_144156_;
    }

    @Override
    public SoundEvent m_142546_() {
        return SoundEvents.f_144154_;
    }

    @Override
    public SoundEvent m_142545_() {
        return SoundEvents.f_144158_;
    }

    @Override
    protected ItemStack m_142590_() {
        return new ItemStack(Items.f_151063_);
    }
}

