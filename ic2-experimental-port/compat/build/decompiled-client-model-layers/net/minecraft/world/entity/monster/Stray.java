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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Stray
extends AbstractSkeleton {
    public Stray(EntityType<? extends Stray> p_33836_, Level p_33837_) {
        super((EntityType<? extends AbstractSkeleton>)p_33836_, p_33837_);
    }

    public static boolean m_219120_(EntityType<Stray> p_219121_, ServerLevelAccessor p_219122_, MobSpawnType p_219123_, BlockPos p_219124_, RandomSource p_219125_) {
        BlockPos $$5 = p_219124_;
        while (p_219122_.m_8055_($$5 = $$5.m_7494_()).m_60713_(Blocks.f_152499_)) {
        }
        return Stray.m_219013_(p_219121_, p_219122_, p_219123_, p_219124_, p_219125_) && (p_219123_ == MobSpawnType.SPAWNER || p_219122_.m_45527_($$5.m_7495_()));
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12451_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33850_) {
        return SoundEvents.f_12453_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12452_;
    }

    @Override
    SoundEvent m_7878_() {
        return SoundEvents.f_12454_;
    }

    @Override
    protected AbstractArrow m_7932_(ItemStack p_33846_, float p_33847_) {
        AbstractArrow $$2 = super.m_7932_(p_33846_, p_33847_);
        if ($$2 instanceof Arrow) {
            ((Arrow)$$2).m_36870_(new MobEffectInstance(MobEffects.f_19597_, 600));
        }
        return $$2;
    }
}

