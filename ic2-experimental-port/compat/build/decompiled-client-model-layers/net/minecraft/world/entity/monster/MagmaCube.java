/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

public class MagmaCube
extends Slime {
    public MagmaCube(EntityType<? extends MagmaCube> p_32968_, Level p_32969_) {
        super((EntityType<? extends Slime>)p_32968_, p_32969_);
    }

    public static AttributeSupplier.Builder m_33000_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.2f);
    }

    public static boolean m_219002_(EntityType<MagmaCube> p_219003_, LevelAccessor p_219004_, MobSpawnType p_219005_, BlockPos p_219006_, RandomSource p_219007_) {
        return p_219004_.m_46791_() != Difficulty.PEACEFUL;
    }

    @Override
    public boolean m_6914_(LevelReader p_32975_) {
        return p_32975_.m_45784_(this) && !p_32975_.m_46855_(this.m_20191_());
    }

    @Override
    public void m_7839_(int p_32972_, boolean p_32973_) {
        super.m_7839_(p_32972_, p_32973_);
        this.m_21051_(Attributes.f_22284_).m_22100_(p_32972_ * 3);
    }

    @Override
    public float m_213856_() {
        return 1.0f;
    }

    @Override
    protected ParticleOptions m_6300_() {
        return ParticleTypes.f_123744_;
    }

    @Override
    public boolean m_6060_() {
        return false;
    }

    @Override
    protected int m_7549_() {
        return super.m_7549_() * 4;
    }

    @Override
    protected void m_7480_() {
        this.f_33581_ *= 0.9f;
    }

    @Override
    protected void m_6135_() {
        Vec3 $$0 = this.m_20184_();
        this.m_20334_($$0.f_82479_, this.m_6118_() + (float)this.m_33632_() * 0.1f, $$0.f_82481_);
        this.f_19812_ = true;
    }

    @Override
    protected void m_203347_(TagKey<Fluid> p_204065_) {
        if (p_204065_ == FluidTags.f_13132_) {
            Vec3 $$1 = this.m_20184_();
            this.m_20334_($$1.f_82479_, 0.22f + (float)this.m_33632_() * 0.05f, $$1.f_82481_);
            this.f_19812_ = true;
        } else {
            super.m_203347_(p_204065_);
        }
    }

    @Override
    public boolean m_142535_(float p_149717_, float p_149718_, DamageSource p_149719_) {
        return false;
    }

    @Override
    protected boolean m_7483_() {
        return this.m_6142_();
    }

    @Override
    protected float m_7566_() {
        return super.m_7566_() + 2.0f;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32992_) {
        if (this.m_33633_()) {
            return SoundEvents.f_12110_;
        }
        return SoundEvents.f_12109_;
    }

    @Override
    protected SoundEvent m_5592_() {
        if (this.m_33633_()) {
            return SoundEvents.f_12101_;
        }
        return SoundEvents.f_12108_;
    }

    @Override
    protected SoundEvent m_7905_() {
        if (this.m_33633_()) {
            return SoundEvents.f_12061_;
        }
        return SoundEvents.f_12112_;
    }

    @Override
    protected SoundEvent m_7903_() {
        return SoundEvents.f_12111_;
    }
}

