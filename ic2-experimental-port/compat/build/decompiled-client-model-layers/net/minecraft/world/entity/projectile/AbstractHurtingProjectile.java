/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHurtingProjectile
extends Projectile {
    public double f_36813_;
    public double f_36814_;
    public double f_36815_;

    protected AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super((EntityType<? extends Projectile>)p_36833_, p_36834_);
    }

    public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        this(p_36817_, p_36824_);
        this.m_7678_(p_36818_, p_36819_, p_36820_, this.m_146908_(), this.m_146909_());
        this.m_20090_();
        double $$8 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
        if ($$8 != 0.0) {
            this.f_36813_ = p_36821_ / $$8 * 0.1;
            this.f_36814_ = p_36822_ / $$8 * 0.1;
            this.f_36815_ = p_36823_ / $$8 * 0.1;
        }
    }

    public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        this(p_36826_, p_36827_.m_20185_(), p_36827_.m_20186_(), p_36827_.m_20189_(), p_36828_, p_36829_, p_36830_, p_36831_);
        this.m_5602_(p_36827_);
        this.m_19915_(p_36827_.m_146908_(), p_36827_.m_146909_());
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    public boolean m_6783_(double p_36837_) {
        double $$1 = this.m_20191_().m_82309_() * 4.0;
        if (Double.isNaN($$1)) {
            $$1 = 4.0;
        }
        return p_36837_ < ($$1 *= 64.0) * $$1;
    }

    @Override
    public void m_8119_() {
        HitResult $$1;
        Entity $$0 = this.m_37282_();
        if (!this.f_19853_.f_46443_ && ($$0 != null && $$0.m_213877_() || !this.f_19853_.m_46805_(this.m_20183_()))) {
            this.m_146870_();
            return;
        }
        super.m_8119_();
        if (this.m_5931_()) {
            this.m_20254_(1);
        }
        if (($$1 = ProjectileUtil.m_37294_(this, this::m_5603_)).m_6662_() != HitResult.Type.MISS) {
            this.m_6532_($$1);
        }
        this.m_20101_();
        Vec3 $$2 = this.m_20184_();
        double $$3 = this.m_20185_() + $$2.f_82479_;
        double $$4 = this.m_20186_() + $$2.f_82480_;
        double $$5 = this.m_20189_() + $$2.f_82481_;
        ProjectileUtil.m_37284_(this, 0.2f);
        float $$6 = this.m_6884_();
        if (this.m_20069_()) {
            for (int $$7 = 0; $$7 < 4; ++$$7) {
                float $$8 = 0.25f;
                this.f_19853_.m_7106_(ParticleTypes.f_123795_, $$3 - $$2.f_82479_ * 0.25, $$4 - $$2.f_82480_ * 0.25, $$5 - $$2.f_82481_ * 0.25, $$2.f_82479_, $$2.f_82480_, $$2.f_82481_);
            }
            $$6 = 0.8f;
        }
        this.m_20256_($$2.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_($$6));
        this.f_19853_.m_7106_(this.m_5967_(), $$3, $$4 + 0.5, $$5, 0.0, 0.0, 0.0);
        this.m_6034_($$3, $$4, $$5);
    }

    @Override
    protected boolean m_5603_(Entity p_36842_) {
        return super.m_5603_(p_36842_) && !p_36842_.f_19794_;
    }

    protected boolean m_5931_() {
        return true;
    }

    protected ParticleOptions m_5967_() {
        return ParticleTypes.f_123762_;
    }

    protected float m_6884_() {
        return 0.95f;
    }

    @Override
    public void m_7380_(CompoundTag p_36848_) {
        super.m_7380_(p_36848_);
        p_36848_.m_128365_("power", this.m_20063_(this.f_36813_, this.f_36814_, this.f_36815_));
    }

    @Override
    public void m_7378_(CompoundTag p_36844_) {
        ListTag $$1;
        super.m_7378_(p_36844_);
        if (p_36844_.m_128425_("power", 9) && ($$1 = p_36844_.m_128437_("power", 6)).size() == 3) {
            this.f_36813_ = $$1.m_128772_(0);
            this.f_36814_ = $$1.m_128772_(1);
            this.f_36815_ = $$1.m_128772_(2);
        }
    }

    @Override
    public boolean m_6087_() {
        return true;
    }

    @Override
    public float m_6143_() {
        return 1.0f;
    }

    @Override
    public boolean m_6469_(DamageSource p_36839_, float p_36840_) {
        if (this.m_6673_(p_36839_)) {
            return false;
        }
        this.m_5834_();
        Entity $$2 = p_36839_.m_7639_();
        if ($$2 != null) {
            if (!this.f_19853_.f_46443_) {
                Vec3 $$3 = $$2.m_20154_();
                this.m_20256_($$3);
                this.f_36813_ = $$3.f_82479_ * 0.1;
                this.f_36814_ = $$3.f_82480_ * 0.1;
                this.f_36815_ = $$3.f_82481_ * 0.1;
                this.m_5602_($$2);
            }
            return true;
        }
        return false;
    }

    @Override
    public float m_213856_() {
        return 1.0f;
    }

    @Override
    public Packet<?> m_5654_() {
        Entity $$0 = this.m_37282_();
        int $$1 = $$0 == null ? 0 : $$0.m_19879_();
        return new ClientboundAddEntityPacket(this.m_19879_(), this.m_20148_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146909_(), this.m_146908_(), this.m_6095_(), $$1, new Vec3(this.f_36813_, this.f_36814_, this.f_36815_), 0.0);
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_150128_) {
        super.m_141965_(p_150128_);
        double $$1 = p_150128_.m_131503_();
        double $$2 = p_150128_.m_131504_();
        double $$3 = p_150128_.m_131505_();
        double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
        if ($$4 != 0.0) {
            this.f_36813_ = $$1 / $$4 * 0.1;
            this.f_36814_ = $$2 / $$4 * 0.1;
            this.f_36815_ = $$3 / $$4 * 0.1;
        }
    }
}

