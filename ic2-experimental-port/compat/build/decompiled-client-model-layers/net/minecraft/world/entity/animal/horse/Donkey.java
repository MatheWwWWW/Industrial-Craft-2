/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;

public class Donkey
extends AbstractChestedHorse {
    public Donkey(EntityType<? extends Donkey> p_30672_, Level p_30673_) {
        super((EntityType<? extends AbstractChestedHorse>)p_30672_, p_30673_);
    }

    @Override
    protected SoundEvent m_7515_() {
        super.m_7515_();
        return SoundEvents.f_11809_;
    }

    @Override
    protected SoundEvent m_7871_() {
        super.m_7871_();
        return SoundEvents.f_11810_;
    }

    @Override
    protected SoundEvent m_5592_() {
        super.m_5592_();
        return SoundEvents.f_11812_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7872_() {
        return SoundEvents.f_11813_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30682_) {
        super.m_7975_(p_30682_);
        return SoundEvents.f_11814_;
    }

    @Override
    public boolean m_7848_(Animal p_30679_) {
        if (p_30679_ == this) {
            return false;
        }
        if (p_30679_ instanceof Donkey || p_30679_ instanceof Horse) {
            return this.m_30628_() && ((AbstractHorse)p_30679_).m_30628_();
        }
        return false;
    }

    @Override
    public AgeableMob m_142606_(ServerLevel p_149530_, AgeableMob p_149531_) {
        EntityType<AbstractChestedHorse> $$2 = p_149531_ instanceof Horse ? EntityType.f_20503_ : EntityType.f_20560_;
        AbstractHorse $$3 = $$2.m_20615_(p_149530_);
        this.m_149508_(p_149531_, $$3);
        return $$3;
    }
}

