/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class ThrowableProjectile
extends Projectile {
    protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super((EntityType<? extends Projectile>)p_37466_, p_37467_);
    }

    protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> p_37456_, double p_37457_, double p_37458_, double p_37459_, Level p_37460_) {
        this(p_37456_, p_37460_);
        this.m_6034_(p_37457_, p_37458_, p_37459_);
    }

    protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> p_37462_, LivingEntity p_37463_, Level p_37464_) {
        this(p_37462_, p_37463_.m_20185_(), p_37463_.m_20188_() - (double)0.1f, p_37463_.m_20189_(), p_37464_);
        this.m_5602_(p_37463_);
    }

    @Override
    public boolean m_6783_(double p_37470_) {
        double $$1 = this.m_20191_().m_82309_() * 4.0;
        if (Double.isNaN($$1)) {
            $$1 = 4.0;
        }
        return p_37470_ < ($$1 *= 64.0) * $$1;
    }

    @Override
    public void m_8119_() {
        float $$12;
        super.m_8119_();
        HitResult $$0 = ProjectileUtil.m_37294_(this, this::m_5603_);
        boolean $$1 = false;
        if ($$0.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos $$2 = ((BlockHitResult)$$0).m_82425_();
            BlockState $$3 = this.f_19853_.m_8055_($$2);
            if ($$3.m_60713_(Blocks.f_50142_)) {
                this.m_20221_($$2);
                $$1 = true;
            } else if ($$3.m_60713_(Blocks.f_50446_)) {
                BlockEntity $$4 = this.f_19853_.m_7702_($$2);
                if ($$4 instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.m_59940_(this)) {
                    TheEndGatewayBlockEntity.m_155828_(this.f_19853_, $$2, $$3, this, (TheEndGatewayBlockEntity)$$4);
                }
                $$1 = true;
            }
        }
        if ($$0.m_6662_() != HitResult.Type.MISS && !$$1) {
            this.m_6532_($$0);
        }
        this.m_20101_();
        Vec3 $$5 = this.m_20184_();
        double $$6 = this.m_20185_() + $$5.f_82479_;
        double $$7 = this.m_20186_() + $$5.f_82480_;
        double $$8 = this.m_20189_() + $$5.f_82481_;
        this.m_37283_();
        if (this.m_20069_()) {
            for (int $$9 = 0; $$9 < 4; ++$$9) {
                float $$10 = 0.25f;
                this.f_19853_.m_7106_(ParticleTypes.f_123795_, $$6 - $$5.f_82479_ * 0.25, $$7 - $$5.f_82480_ * 0.25, $$8 - $$5.f_82481_ * 0.25, $$5.f_82479_, $$5.f_82480_, $$5.f_82481_);
            }
            float $$11 = 0.8f;
        } else {
            $$12 = 0.99f;
        }
        this.m_20256_($$5.m_82490_($$12));
        if (!this.m_20068_()) {
            Vec3 $$13 = this.m_20184_();
            this.m_20334_($$13.f_82479_, $$13.f_82480_ - (double)this.m_7139_(), $$13.f_82481_);
        }
        this.m_6034_($$6, $$7, $$8);
    }

    protected float m_7139_() {
        return 0.03f;
    }
}

