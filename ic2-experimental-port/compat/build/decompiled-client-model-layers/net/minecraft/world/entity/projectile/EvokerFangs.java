/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EvokerFangs
extends Entity {
    public static final int f_150133_ = 20;
    public static final int f_150134_ = 2;
    public static final int f_150135_ = 14;
    private int f_36916_;
    private boolean f_36917_;
    private int f_36918_ = 22;
    private boolean f_36919_;
    @Nullable
    private LivingEntity f_36920_;
    @Nullable
    private UUID f_36921_;

    public EvokerFangs(EntityType<? extends EvokerFangs> p_36923_, Level p_36924_) {
        super(p_36923_, p_36924_);
    }

    public EvokerFangs(Level p_36926_, double p_36927_, double p_36928_, double p_36929_, float p_36930_, int p_36931_, LivingEntity p_36932_) {
        this((EntityType<? extends EvokerFangs>)EntityType.f_20569_, p_36926_);
        this.f_36916_ = p_36931_;
        this.m_36938_(p_36932_);
        this.m_146922_(p_36930_ * 57.295776f);
        this.m_6034_(p_36927_, p_36928_, p_36929_);
    }

    @Override
    protected void m_8097_() {
    }

    public void m_36938_(@Nullable LivingEntity p_36939_) {
        this.f_36920_ = p_36939_;
        this.f_36921_ = p_36939_ == null ? null : p_36939_.m_20148_();
    }

    @Nullable
    public LivingEntity m_36947_() {
        Entity $$0;
        if (this.f_36920_ == null && this.f_36921_ != null && this.f_19853_ instanceof ServerLevel && ($$0 = ((ServerLevel)this.f_19853_).m_8791_(this.f_36921_)) instanceof LivingEntity) {
            this.f_36920_ = (LivingEntity)$$0;
        }
        return this.f_36920_;
    }

    @Override
    protected void m_7378_(CompoundTag p_36941_) {
        this.f_36916_ = p_36941_.m_128451_("Warmup");
        if (p_36941_.m_128403_("Owner")) {
            this.f_36921_ = p_36941_.m_128342_("Owner");
        }
    }

    @Override
    protected void m_7380_(CompoundTag p_36943_) {
        p_36943_.m_128405_("Warmup", this.f_36916_);
        if (this.f_36921_ != null) {
            p_36943_.m_128362_("Owner", this.f_36921_);
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_) {
            if (this.f_36919_) {
                --this.f_36918_;
                if (this.f_36918_ == 14) {
                    for (int $$0 = 0; $$0 < 12; ++$$0) {
                        double $$1 = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
                        double $$2 = this.m_20186_() + 0.05 + this.f_19796_.m_188500_();
                        double $$3 = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
                        double $$4 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
                        double $$5 = 0.3 + this.f_19796_.m_188500_() * 0.3;
                        double $$6 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
                        this.f_19853_.m_7106_(ParticleTypes.f_123797_, $$1, $$2 + 1.0, $$3, $$4, $$5, $$6);
                    }
                }
            }
        } else if (--this.f_36916_ < 0) {
            if (this.f_36916_ == -8) {
                List<LivingEntity> $$7 = this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2));
                for (LivingEntity $$8 : $$7) {
                    this.m_36944_($$8);
                }
            }
            if (!this.f_36917_) {
                this.f_19853_.m_7605_(this, (byte)4);
                this.f_36917_ = true;
            }
            if (--this.f_36918_ < 0) {
                this.m_146870_();
            }
        }
    }

    private void m_36944_(LivingEntity p_36945_) {
        LivingEntity $$1 = this.m_36947_();
        if (!p_36945_.m_6084_() || p_36945_.m_20147_() || p_36945_ == $$1) {
            return;
        }
        if ($$1 == null) {
            p_36945_.m_6469_(DamageSource.f_19319_, 6.0f);
        } else {
            if ($$1.m_7307_(p_36945_)) {
                return;
            }
            p_36945_.m_6469_(DamageSource.m_19367_(this, $$1), 6.0f);
        }
    }

    @Override
    public void m_7822_(byte p_36935_) {
        super.m_7822_(p_36935_);
        if (p_36935_ == 4) {
            this.f_36919_ = true;
            if (!this.m_20067_()) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11865_, this.m_5720_(), 1.0f, this.f_19796_.m_188501_() * 0.2f + 0.85f, false);
            }
        }
    }

    public float m_36936_(float p_36937_) {
        if (!this.f_36919_) {
            return 0.0f;
        }
        int $$1 = this.f_36918_ - 2;
        if ($$1 <= 0) {
            return 1.0f;
        }
        return 1.0f - ((float)$$1 - p_36937_) / 20.0f;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }
}

