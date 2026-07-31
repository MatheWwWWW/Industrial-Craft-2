/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Salmon
extends AbstractSchoolingFish {
    public Salmon(EntityType<? extends Salmon> p_29790_, Level p_29791_) {
        super((EntityType<? extends AbstractSchoolingFish>)p_29790_, p_29791_);
    }

    @Override
    public int m_6031_() {
        return 5;
    }

    @Override
    public ItemStack m_28282_() {
        return new ItemStack(Items.f_42457_);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12327_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12328_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29795_) {
        return SoundEvents.f_12330_;
    }

    @Override
    protected SoundEvent m_5699_() {
        return SoundEvents.f_12329_;
    }
}

