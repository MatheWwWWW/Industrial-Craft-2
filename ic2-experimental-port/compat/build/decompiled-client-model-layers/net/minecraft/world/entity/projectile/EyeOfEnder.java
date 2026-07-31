/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EyeOfEnder
extends Entity
implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> f_36949_ = SynchedEntityData.m_135353_(EyeOfEnder.class, EntityDataSerializers.f_135033_);
    private double f_36950_;
    private double f_36951_;
    private double f_36952_;
    private int f_36953_;
    private boolean f_36954_;

    public EyeOfEnder(EntityType<? extends EyeOfEnder> p_36957_, Level p_36958_) {
        super(p_36957_, p_36958_);
    }

    public EyeOfEnder(Level p_36960_, double p_36961_, double p_36962_, double p_36963_) {
        this((EntityType<? extends EyeOfEnder>)EntityType.f_20571_, p_36960_);
        this.m_6034_(p_36961_, p_36962_, p_36963_);
    }

    public void m_36972_(ItemStack p_36973_) {
        if (!p_36973_.m_150930_(Items.f_42545_) || p_36973_.m_41782_()) {
            this.m_20088_().m_135381_(f_36949_, Util.m_137469_(p_36973_.m_41777_(), p_36978_ -> p_36978_.m_41764_(1)));
        }
    }

    private ItemStack m_36981_() {
        return this.m_20088_().m_135370_(f_36949_);
    }

    @Override
    public ItemStack m_7846_() {
        ItemStack $$0 = this.m_36981_();
        return $$0.m_41619_() ? new ItemStack(Items.f_42545_) : $$0;
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_36949_, ItemStack.f_41583_);
    }

    @Override
    public boolean m_6783_(double p_36966_) {
        double $$1 = this.m_20191_().m_82309_() * 4.0;
        if (Double.isNaN($$1)) {
            $$1 = 4.0;
        }
        return p_36966_ < ($$1 *= 64.0) * $$1;
    }

    public void m_36967_(BlockPos p_36968_) {
        double $$5;
        double $$1 = p_36968_.m_123341_();
        int $$2 = p_36968_.m_123342_();
        double $$3 = p_36968_.m_123343_();
        double $$4 = $$1 - this.m_20185_();
        double $$6 = Math.sqrt($$4 * $$4 + ($$5 = $$3 - this.m_20189_()) * $$5);
        if ($$6 > 12.0) {
            this.f_36950_ = this.m_20185_() + $$4 / $$6 * 12.0;
            this.f_36952_ = this.m_20189_() + $$5 / $$6 * 12.0;
            this.f_36951_ = this.m_20186_() + 8.0;
        } else {
            this.f_36950_ = $$1;
            this.f_36951_ = $$2;
            this.f_36952_ = $$3;
        }
        this.f_36953_ = 0;
        this.f_36954_ = this.f_19796_.m_188503_(5) > 0;
    }

    @Override
    public void m_6001_(double p_36984_, double p_36985_, double p_36986_) {
        this.m_20334_(p_36984_, p_36985_, p_36986_);
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            double $$3 = Math.sqrt(p_36984_ * p_36984_ + p_36986_ * p_36986_);
            this.m_146922_((float)(Mth.m_14136_(p_36984_, p_36986_) * 57.2957763671875));
            this.m_146926_((float)(Mth.m_14136_(p_36985_, $$3) * 57.2957763671875));
            this.f_19859_ = this.m_146908_();
            this.f_19860_ = this.m_146909_();
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        Vec3 $$0 = this.m_20184_();
        double $$1 = this.m_20185_() + $$0.f_82479_;
        double $$2 = this.m_20186_() + $$0.f_82480_;
        double $$3 = this.m_20189_() + $$0.f_82481_;
        double $$4 = $$0.m_165924_();
        this.m_146926_(Projectile.m_37273_(this.f_19860_, (float)(Mth.m_14136_($$0.f_82480_, $$4) * 57.2957763671875)));
        this.m_146922_(Projectile.m_37273_(this.f_19859_, (float)(Mth.m_14136_($$0.f_82479_, $$0.f_82481_) * 57.2957763671875)));
        if (!this.f_19853_.f_46443_) {
            double $$5 = this.f_36950_ - $$1;
            double $$6 = this.f_36952_ - $$3;
            float $$7 = (float)Math.sqrt($$5 * $$5 + $$6 * $$6);
            float $$8 = (float)Mth.m_14136_($$6, $$5);
            double $$9 = Mth.m_14139_(0.0025, $$4, $$7);
            double $$10 = $$0.f_82480_;
            if ($$7 < 1.0f) {
                $$9 *= 0.8;
                $$10 *= 0.8;
            }
            int $$11 = this.m_20186_() < this.f_36951_ ? 1 : -1;
            $$0 = new Vec3(Math.cos($$8) * $$9, $$10 + ((double)$$11 - $$10) * (double)0.015f, Math.sin($$8) * $$9);
            this.m_20256_($$0);
        }
        float $$12 = 0.25f;
        if (this.m_20069_()) {
            for (int $$13 = 0; $$13 < 4; ++$$13) {
                this.f_19853_.m_7106_(ParticleTypes.f_123795_, $$1 - $$0.f_82479_ * 0.25, $$2 - $$0.f_82480_ * 0.25, $$3 - $$0.f_82481_ * 0.25, $$0.f_82479_, $$0.f_82480_, $$0.f_82481_);
            }
        } else {
            this.f_19853_.m_7106_(ParticleTypes.f_123760_, $$1 - $$0.f_82479_ * 0.25 + this.f_19796_.m_188500_() * 0.6 - 0.3, $$2 - $$0.f_82480_ * 0.25 - 0.5, $$3 - $$0.f_82481_ * 0.25 + this.f_19796_.m_188500_() * 0.6 - 0.3, $$0.f_82479_, $$0.f_82480_, $$0.f_82481_);
        }
        if (!this.f_19853_.f_46443_) {
            this.m_6034_($$1, $$2, $$3);
            ++this.f_36953_;
            if (this.f_36953_ > 80 && !this.f_19853_.f_46443_) {
                this.m_5496_(SoundEvents.f_11897_, 1.0f, 1.0f);
                this.m_146870_();
                if (this.f_36954_) {
                    this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7846_()));
                } else {
                    this.f_19853_.m_46796_(2003, this.m_20183_(), 0);
                }
            }
        } else {
            this.m_20343_($$1, $$2, $$3);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_36975_) {
        ItemStack $$1 = this.m_36981_();
        if (!$$1.m_41619_()) {
            p_36975_.m_128365_("Item", $$1.m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_36970_) {
        ItemStack $$1 = ItemStack.m_41712_(p_36970_.m_128469_("Item"));
        this.m_36972_($$1);
    }

    @Override
    public float m_213856_() {
        return 1.0f;
    }

    @Override
    public boolean m_6097_() {
        return false;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }
}

