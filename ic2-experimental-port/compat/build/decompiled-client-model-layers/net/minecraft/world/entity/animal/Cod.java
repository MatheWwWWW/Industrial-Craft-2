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

public class Cod
extends AbstractSchoolingFish {
    public Cod(EntityType<? extends Cod> p_28276_, Level p_28277_) {
        super((EntityType<? extends AbstractSchoolingFish>)p_28276_, p_28277_);
    }

    @Override
    public ItemStack m_28282_() {
        return new ItemStack(Items.f_42458_);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11758_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11759_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_28281_) {
        return SoundEvents.f_11761_;
    }

    @Override
    protected SoundEvent m_5699_() {
        return SoundEvents.f_11760_;
    }
}

