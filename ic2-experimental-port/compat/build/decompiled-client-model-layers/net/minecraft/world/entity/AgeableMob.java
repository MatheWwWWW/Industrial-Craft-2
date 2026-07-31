/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AgeableMob
extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> f_146731_ = SynchedEntityData.m_135353_(AgeableMob.class, EntityDataSerializers.f_135035_);
    public static final int f_146730_ = -24000;
    private static final int f_146732_ = 40;
    protected int f_146733_;
    protected int f_146734_;
    protected int f_146735_;

    protected AgeableMob(EntityType<? extends AgeableMob> p_146738_, Level p_146739_) {
        super((EntityType<? extends PathfinderMob>)p_146738_, p_146739_);
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        AgeableMobGroupData $$5;
        if (p_146749_ == null) {
            p_146749_ = new AgeableMobGroupData(true);
        }
        if (($$5 = (AgeableMobGroupData)p_146749_).m_146779_() && $$5.m_146777_() > 0 && p_146746_.m_213780_().m_188501_() <= $$5.m_146780_()) {
            this.m_146762_(-24000);
        }
        $$5.m_146778_();
        return super.m_6518_(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Nullable
    public abstract AgeableMob m_142606_(ServerLevel var1, AgeableMob var2);

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_146731_, false);
    }

    public boolean m_35506_() {
        return false;
    }

    public int m_146764_() {
        if (this.f_19853_.f_46443_) {
            return this.f_19804_.m_135370_(f_146731_) != false ? -1 : 1;
        }
        return this.f_146733_;
    }

    public void m_146740_(int p_146741_, boolean p_146742_) {
        int $$2;
        int $$3 = $$2 = this.m_146764_();
        if (($$2 += p_146741_ * 20) > 0) {
            $$2 = 0;
        }
        int $$4 = $$2 - $$3;
        this.m_146762_($$2);
        if (p_146742_) {
            this.f_146734_ += $$4;
            if (this.f_146735_ == 0) {
                this.f_146735_ = 40;
            }
        }
        if (this.m_146764_() == 0) {
            this.m_146762_(this.f_146734_);
        }
    }

    public void m_146758_(int p_146759_) {
        this.m_146740_(p_146759_, false);
    }

    public void m_146762_(int p_146763_) {
        int $$1 = this.m_146764_();
        this.f_146733_ = p_146763_;
        if ($$1 < 0 && p_146763_ >= 0 || $$1 >= 0 && p_146763_ < 0) {
            this.f_19804_.m_135381_(f_146731_, p_146763_ < 0);
            this.m_30232_();
        }
    }

    @Override
    public void m_7380_(CompoundTag p_146761_) {
        super.m_7380_(p_146761_);
        p_146761_.m_128405_("Age", this.m_146764_());
        p_146761_.m_128405_("ForcedAge", this.f_146734_);
    }

    @Override
    public void m_7378_(CompoundTag p_146752_) {
        super.m_7378_(p_146752_);
        this.m_146762_(p_146752_.m_128451_("Age"));
        this.f_146734_ = p_146752_.m_128451_("ForcedAge");
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_146754_) {
        if (f_146731_.equals(p_146754_)) {
            this.m_6210_();
        }
        super.m_7350_(p_146754_);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (this.f_19853_.f_46443_) {
            if (this.f_146735_ > 0) {
                if (this.f_146735_ % 4 == 0) {
                    this.f_19853_.m_7106_(ParticleTypes.f_123748_, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), 0.0, 0.0, 0.0);
                }
                --this.f_146735_;
            }
        } else if (this.m_6084_()) {
            int $$0 = this.m_146764_();
            if ($$0 < 0) {
                this.m_146762_(++$$0);
            } else if ($$0 > 0) {
                this.m_146762_(--$$0);
            }
        }
    }

    protected void m_30232_() {
    }

    @Override
    public boolean m_6162_() {
        return this.m_146764_() < 0;
    }

    @Override
    public void m_6863_(boolean p_146756_) {
        this.m_146762_(p_146756_ ? -24000 : 0);
    }

    public static int m_216967_(int p_216968_) {
        return (int)((float)(p_216968_ / 20) * 0.1f);
    }

    public static class AgeableMobGroupData
    implements SpawnGroupData {
        private int f_146767_;
        private final boolean f_146768_;
        private final float f_146769_;

        private AgeableMobGroupData(boolean p_146775_, float p_146776_) {
            this.f_146768_ = p_146775_;
            this.f_146769_ = p_146776_;
        }

        public AgeableMobGroupData(boolean p_146773_) {
            this(p_146773_, 0.05f);
        }

        public AgeableMobGroupData(float p_146771_) {
            this(true, p_146771_);
        }

        public int m_146777_() {
            return this.f_146767_;
        }

        public void m_146778_() {
            ++this.f_146767_;
        }

        public boolean m_146779_() {
            return this.f_146768_;
        }

        public float m_146780_() {
            return this.f_146769_;
        }
    }
}

