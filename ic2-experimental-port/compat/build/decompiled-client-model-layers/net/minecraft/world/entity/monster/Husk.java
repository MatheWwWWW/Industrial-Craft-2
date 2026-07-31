/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Husk
extends Zombie {
    public Husk(EntityType<? extends Husk> p_32889_, Level p_32890_) {
        super((EntityType<? extends Zombie>)p_32889_, p_32890_);
    }

    public static boolean m_218996_(EntityType<Husk> p_218997_, ServerLevelAccessor p_218998_, MobSpawnType p_218999_, BlockPos p_219000_, RandomSource p_219001_) {
        return Husk.m_219013_(p_218997_, p_218998_, p_218999_, p_219000_, p_219001_) && (p_218999_ == MobSpawnType.SPAWNER || p_218998_.m_45527_(p_219000_));
    }

    @Override
    protected boolean m_5884_() {
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12043_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32903_) {
        return SoundEvents.f_12046_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12045_;
    }

    @Override
    protected SoundEvent m_7660_() {
        return SoundEvents.f_12047_;
    }

    @Override
    public boolean m_7327_(Entity p_32892_) {
        boolean $$1 = super.m_7327_(p_32892_);
        if ($$1 && this.m_21205_().m_41619_() && p_32892_ instanceof LivingEntity) {
            float $$2 = this.f_19853_.m_6436_(this.m_20183_()).m_19056_();
            ((LivingEntity)p_32892_).m_147207_(new MobEffectInstance(MobEffects.f_19612_, 140 * (int)$$2), this);
        }
        return $$1;
    }

    @Override
    protected boolean m_7593_() {
        return true;
    }

    @Override
    protected void m_7595_() {
        this.m_34310_(EntityType.f_20501_);
        if (!this.m_20067_()) {
            this.f_19853_.m_5898_(null, 1041, this.m_20183_(), 0);
        }
    }

    @Override
    protected ItemStack m_5728_() {
        return ItemStack.f_41583_;
    }
}

