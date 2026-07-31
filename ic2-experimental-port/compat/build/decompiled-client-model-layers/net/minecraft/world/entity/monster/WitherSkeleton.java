/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class WitherSkeleton
extends AbstractSkeleton {
    public WitherSkeleton(EntityType<? extends WitherSkeleton> p_34166_, Level p_34167_) {
        super((EntityType<? extends AbstractSkeleton>)p_34166_, p_34167_);
        this.m_21441_(BlockPathTypes.LAVA, 8.0f);
    }

    @Override
    protected void m_8099_() {
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractPiglin>((Mob)this, AbstractPiglin.class, true));
        super.m_8099_();
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12559_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34195_) {
        return SoundEvents.f_12561_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12560_;
    }

    @Override
    SoundEvent m_7878_() {
        return SoundEvents.f_12562_;
    }

    @Override
    protected void m_7472_(DamageSource p_34174_, int p_34175_, boolean p_34176_) {
        Creeper $$4;
        super.m_7472_(p_34174_, p_34175_, p_34176_);
        Entity $$3 = p_34174_.m_7639_();
        if ($$3 instanceof Creeper && ($$4 = (Creeper)$$3).m_32313_()) {
            $$4.m_32314_();
            this.m_19998_(Items.f_42679_);
        }
    }

    @Override
    protected void m_213945_(RandomSource p_219154_, DifficultyInstance p_219155_) {
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42425_));
    }

    @Override
    protected void m_213946_(RandomSource p_219157_, DifficultyInstance p_219158_) {
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_34178_, DifficultyInstance p_34179_, MobSpawnType p_34180_, @Nullable SpawnGroupData p_34181_, @Nullable CompoundTag p_34182_) {
        SpawnGroupData $$5 = super.m_6518_(p_34178_, p_34179_, p_34180_, p_34181_, p_34182_);
        this.m_21051_(Attributes.f_22281_).m_22100_(4.0);
        this.m_32164_();
        return $$5;
    }

    @Override
    protected float m_6431_(Pose p_34186_, EntityDimensions p_34187_) {
        return 2.1f;
    }

    @Override
    public boolean m_7327_(Entity p_34169_) {
        if (!super.m_7327_(p_34169_)) {
            return false;
        }
        if (p_34169_ instanceof LivingEntity) {
            ((LivingEntity)p_34169_).m_147207_(new MobEffectInstance(MobEffects.f_19615_, 200), this);
        }
        return true;
    }

    @Override
    protected AbstractArrow m_7932_(ItemStack p_34189_, float p_34190_) {
        AbstractArrow $$2 = super.m_7932_(p_34189_, p_34190_);
        $$2.m_20254_(100);
        return $$2;
    }

    @Override
    public boolean m_7301_(MobEffectInstance p_34192_) {
        if (p_34192_.m_19544_() == MobEffects.f_19615_) {
            return false;
        }
        return super.m_7301_(p_34192_);
    }
}

