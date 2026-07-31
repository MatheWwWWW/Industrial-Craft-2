/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DragonFireball
extends AbstractHurtingProjectile {
    public static final float f_150132_ = 4.0f;

    public DragonFireball(EntityType<? extends DragonFireball> p_36892_, Level p_36893_) {
        super((EntityType<? extends AbstractHurtingProjectile>)p_36892_, p_36893_);
    }

    public DragonFireball(Level p_36903_, LivingEntity p_36904_, double p_36905_, double p_36906_, double p_36907_) {
        super(EntityType.f_20561_, p_36904_, p_36905_, p_36906_, p_36907_, p_36903_);
    }

    @Override
    protected void m_6532_(HitResult p_36913_) {
        super.m_6532_(p_36913_);
        if (p_36913_.m_6662_() == HitResult.Type.ENTITY && this.m_150171_(((EntityHitResult)p_36913_).m_82443_())) {
            return;
        }
        if (!this.f_19853_.f_46443_) {
            List<LivingEntity> $$1 = this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(4.0, 2.0, 4.0));
            AreaEffectCloud $$2 = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
            Entity $$3 = this.m_37282_();
            if ($$3 instanceof LivingEntity) {
                $$2.m_19718_((LivingEntity)$$3);
            }
            $$2.m_19724_(ParticleTypes.f_123799_);
            $$2.m_19712_(3.0f);
            $$2.m_19734_(600);
            $$2.m_19738_((7.0f - $$2.m_19743_()) / (float)$$2.m_19748_());
            $$2.m_19716_(new MobEffectInstance(MobEffects.f_19602_, 1, 1));
            if (!$$1.isEmpty()) {
                for (LivingEntity $$4 : $$1) {
                    double $$5 = this.m_20280_($$4);
                    if (!($$5 < 16.0)) continue;
                    $$2.m_6034_($$4.m_20185_(), $$4.m_20186_(), $$4.m_20189_());
                    break;
                }
            }
            this.f_19853_.m_46796_(2006, this.m_20183_(), this.m_20067_() ? -1 : 1);
            this.f_19853_.m_7967_($$2);
            this.m_146870_();
        }
    }

    @Override
    public boolean m_6087_() {
        return false;
    }

    @Override
    public boolean m_6469_(DamageSource p_36910_, float p_36911_) {
        return false;
    }

    @Override
    protected ParticleOptions m_5967_() {
        return ParticleTypes.f_123799_;
    }

    @Override
    protected boolean m_5931_() {
        return false;
    }
}

