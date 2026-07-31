/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WitherSkull
extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Boolean> f_37595_ = SynchedEntityData.m_135353_(WitherSkull.class, EntityDataSerializers.f_135035_);

    public WitherSkull(EntityType<? extends WitherSkull> p_37598_, Level p_37599_) {
        super((EntityType<? extends AbstractHurtingProjectile>)p_37598_, p_37599_);
    }

    public WitherSkull(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
        super(EntityType.f_20498_, p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
    }

    @Override
    protected float m_6884_() {
        return this.m_37635_() ? 0.73f : super.m_6884_();
    }

    @Override
    public boolean m_6060_() {
        return false;
    }

    @Override
    public float m_7077_(Explosion p_37619_, BlockGetter p_37620_, BlockPos p_37621_, BlockState p_37622_, FluidState p_37623_, float p_37624_) {
        if (this.m_37635_() && WitherBoss.m_31491_(p_37622_)) {
            return Math.min(0.8f, p_37624_);
        }
        return p_37624_;
    }

    @Override
    protected void m_5790_(EntityHitResult p_37626_) {
        boolean $$5;
        super.m_5790_(p_37626_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        Entity $$1 = p_37626_.m_82443_();
        Entity $$2 = this.m_37282_();
        if ($$2 instanceof LivingEntity) {
            LivingEntity $$3 = (LivingEntity)$$2;
            boolean $$4 = $$1.m_6469_(DamageSource.m_19355_(this, $$3), 8.0f);
            if ($$4) {
                if ($$1.m_6084_()) {
                    this.m_19970_($$3, $$1);
                } else {
                    $$3.m_5634_(5.0f);
                }
            }
        } else {
            $$5 = $$1.m_6469_(DamageSource.f_19319_, 5.0f);
        }
        if ($$5 && $$1 instanceof LivingEntity) {
            int $$6 = 0;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
                $$6 = 10;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
                $$6 = 40;
            }
            if ($$6 > 0) {
                ((LivingEntity)$$1).m_147207_(new MobEffectInstance(MobEffects.f_19615_, 20 * $$6, 1), this.m_150173_());
            }
        }
    }

    @Override
    protected void m_6532_(HitResult p_37628_) {
        super.m_6532_(p_37628_);
        if (!this.f_19853_.f_46443_) {
            Explosion.BlockInteraction $$1 = this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0f, false, $$1);
            this.m_146870_();
        }
    }

    @Override
    public boolean m_6087_() {
        return false;
    }

    @Override
    public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
        return false;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_37595_, false);
    }

    public boolean m_37635_() {
        return this.f_19804_.m_135370_(f_37595_);
    }

    public void m_37629_(boolean p_37630_) {
        this.f_19804_.m_135381_(f_37595_, p_37630_);
    }

    @Override
    protected boolean m_5931_() {
        return false;
    }
}

