/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class Squid
extends WaterAnimal {
    public float f_29938_;
    public float f_29950_;
    public float f_29951_;
    public float f_29939_;
    public float f_29940_;
    public float f_29941_;
    public float f_29942_;
    public float f_29943_;
    private float f_29944_;
    private float f_29945_;
    private float f_29946_;
    private float f_29947_;
    private float f_29948_;
    private float f_29949_;

    public Squid(EntityType<? extends Squid> p_29953_, Level p_29954_) {
        super((EntityType<? extends WaterAnimal>)p_29953_, p_29954_);
        this.f_19796_.m_188584_(this.m_19879_());
        this.f_29945_ = 1.0f / (this.f_19796_.m_188501_() + 1.0f) * 0.2f;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new SquidRandomMovementGoal(this));
        this.f_21345_.m_25352_(1, new SquidFleeGoal());
    }

    public static AttributeSupplier.Builder m_29988_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0);
    }

    @Override
    protected float m_6431_(Pose p_29975_, EntityDimensions p_29976_) {
        return p_29976_.f_20378_ * 0.5f;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12438_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29980_) {
        return SoundEvents.f_12440_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12439_;
    }

    protected SoundEvent m_142555_() {
        return SoundEvents.f_12441_;
    }

    @Override
    public boolean m_6573_(Player p_149052_) {
        return !this.m_21523_();
    }

    @Override
    protected float m_6121_() {
        return 0.4f;
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        this.f_29950_ = this.f_29938_;
        this.f_29939_ = this.f_29951_;
        this.f_29941_ = this.f_29940_;
        this.f_29943_ = this.f_29942_;
        this.f_29940_ += this.f_29945_;
        if ((double)this.f_29940_ > Math.PI * 2) {
            if (this.f_19853_.f_46443_) {
                this.f_29940_ = (float)Math.PI * 2;
            } else {
                this.f_29940_ -= (float)Math.PI * 2;
                if (this.f_19796_.m_188503_(10) == 0) {
                    this.f_29945_ = 1.0f / (this.f_19796_.m_188501_() + 1.0f) * 0.2f;
                }
                this.f_19853_.m_7605_(this, (byte)19);
            }
        }
        if (this.m_20072_()) {
            if (this.f_29940_ < (float)Math.PI) {
                float $$0 = this.f_29940_ / (float)Math.PI;
                this.f_29942_ = Mth.m_14031_($$0 * $$0 * (float)Math.PI) * (float)Math.PI * 0.25f;
                if ((double)$$0 > 0.75) {
                    this.f_29944_ = 1.0f;
                    this.f_29946_ = 1.0f;
                } else {
                    this.f_29946_ *= 0.8f;
                }
            } else {
                this.f_29942_ = 0.0f;
                this.f_29944_ *= 0.9f;
                this.f_29946_ *= 0.99f;
            }
            if (!this.f_19853_.f_46443_) {
                this.m_20334_(this.f_29947_ * this.f_29944_, this.f_29948_ * this.f_29944_, this.f_29949_ * this.f_29944_);
            }
            Vec3 $$1 = this.m_20184_();
            double $$2 = $$1.m_165924_();
            this.f_20883_ += (-((float)Mth.m_14136_($$1.f_82479_, $$1.f_82481_)) * 57.295776f - this.f_20883_) * 0.1f;
            this.m_146922_(this.f_20883_);
            this.f_29951_ += (float)Math.PI * this.f_29946_ * 1.5f;
            this.f_29938_ += (-((float)Mth.m_14136_($$2, $$1.f_82480_)) * 57.295776f - this.f_29938_) * 0.1f;
        } else {
            this.f_29942_ = Mth.m_14154_(Mth.m_14031_(this.f_29940_)) * (float)Math.PI * 0.25f;
            if (!this.f_19853_.f_46443_) {
                double $$3 = this.m_20184_().f_82480_;
                if (this.m_21023_(MobEffects.f_19620_)) {
                    $$3 = 0.05 * (double)(this.m_21124_(MobEffects.f_19620_).m_19564_() + 1);
                } else if (!this.m_20068_()) {
                    $$3 -= 0.08;
                }
                this.m_20334_(0.0, $$3 * (double)0.98f, 0.0);
            }
            this.f_29938_ += (-90.0f - this.f_29938_) * 0.02f;
        }
    }

    @Override
    public boolean m_6469_(DamageSource p_29963_, float p_29964_) {
        if (super.m_6469_(p_29963_, p_29964_) && this.m_21188_() != null) {
            if (!this.f_19853_.f_46443_) {
                this.m_29982_();
            }
            return true;
        }
        return false;
    }

    private Vec3 m_29985_(Vec3 p_29986_) {
        Vec3 $$1 = p_29986_.m_82496_(this.f_29950_ * ((float)Math.PI / 180));
        $$1 = $$1.m_82524_(-this.f_20884_ * ((float)Math.PI / 180));
        return $$1;
    }

    private void m_29982_() {
        this.m_5496_(this.m_142555_(), this.m_6121_(), this.m_6100_());
        Vec3 $$0 = this.m_29985_(new Vec3(0.0, -1.0, 0.0)).m_82520_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        for (int $$1 = 0; $$1 < 30; ++$$1) {
            Vec3 $$2 = this.m_29985_(new Vec3((double)this.f_19796_.m_188501_() * 0.6 - 0.3, -1.0, (double)this.f_19796_.m_188501_() * 0.6 - 0.3));
            Vec3 $$3 = $$2.m_82490_(0.3 + (double)(this.f_19796_.m_188501_() * 2.0f));
            ((ServerLevel)this.f_19853_).m_8767_(this.m_142033_(), $$0.f_82479_, $$0.f_82480_ + 0.5, $$0.f_82481_, 0, $$3.f_82479_, $$3.f_82480_, $$3.f_82481_, 0.1f);
        }
    }

    protected ParticleOptions m_142033_() {
        return ParticleTypes.f_123765_;
    }

    @Override
    public void m_7023_(Vec3 p_29984_) {
        this.m_6478_(MoverType.SELF, this.m_20184_());
    }

    @Override
    public void m_7822_(byte p_29957_) {
        if (p_29957_ == 19) {
            this.f_29940_ = 0.0f;
        } else {
            super.m_7822_(p_29957_);
        }
    }

    public void m_29958_(float p_29959_, float p_29960_, float p_29961_) {
        this.f_29947_ = p_29959_;
        this.f_29948_ = p_29960_;
        this.f_29949_ = p_29961_;
    }

    public boolean m_29981_() {
        return this.f_29947_ != 0.0f || this.f_29948_ != 0.0f || this.f_29949_ != 0.0f;
    }

    class SquidRandomMovementGoal
    extends Goal {
        private final Squid f_30001_;

        public SquidRandomMovementGoal(Squid p_30004_) {
            this.f_30001_ = p_30004_;
        }

        @Override
        public boolean m_8036_() {
            return true;
        }

        @Override
        public void m_8037_() {
            int $$0 = this.f_30001_.m_21216_();
            if ($$0 > 100) {
                this.f_30001_.m_29958_(0.0f, 0.0f, 0.0f);
            } else if (this.f_30001_.m_217043_().m_188503_(SquidRandomMovementGoal.m_186073_(50)) == 0 || !this.f_30001_.f_19798_ || !this.f_30001_.m_29981_()) {
                float $$1 = this.f_30001_.m_217043_().m_188501_() * ((float)Math.PI * 2);
                float $$2 = Mth.m_14089_($$1) * 0.2f;
                float $$3 = -0.1f + this.f_30001_.m_217043_().m_188501_() * 0.2f;
                float $$4 = Mth.m_14031_($$1) * 0.2f;
                this.f_30001_.m_29958_($$2, $$3, $$4);
            }
        }
    }

    class SquidFleeGoal
    extends Goal {
        private static final float f_149054_ = 3.0f;
        private static final float f_149055_ = 5.0f;
        private static final float f_149056_ = 10.0f;
        private int f_29991_;

        SquidFleeGoal() {
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = Squid.this.m_21188_();
            if (Squid.this.m_20069_() && $$0 != null) {
                return Squid.this.m_20280_($$0) < 100.0;
            }
            return false;
        }

        @Override
        public void m_8056_() {
            this.f_29991_ = 0;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            ++this.f_29991_;
            LivingEntity $$0 = Squid.this.m_21188_();
            if ($$0 == null) {
                return;
            }
            Vec3 $$1 = new Vec3(Squid.this.m_20185_() - $$0.m_20185_(), Squid.this.m_20186_() - $$0.m_20186_(), Squid.this.m_20189_() - $$0.m_20189_());
            BlockState $$2 = Squid.this.f_19853_.m_8055_(new BlockPos(Squid.this.m_20185_() + $$1.f_82479_, Squid.this.m_20186_() + $$1.f_82480_, Squid.this.m_20189_() + $$1.f_82481_));
            FluidState $$3 = Squid.this.f_19853_.m_6425_(new BlockPos(Squid.this.m_20185_() + $$1.f_82479_, Squid.this.m_20186_() + $$1.f_82480_, Squid.this.m_20189_() + $$1.f_82481_));
            if ($$3.m_205070_(FluidTags.f_13131_) || $$2.m_60795_()) {
                double $$4 = $$1.m_82553_();
                if ($$4 > 0.0) {
                    $$1.m_82541_();
                    double $$5 = 3.0;
                    if ($$4 > 5.0) {
                        $$5 -= ($$4 - 5.0) / 5.0;
                    }
                    if ($$5 > 0.0) {
                        $$1 = $$1.m_82490_($$5);
                    }
                }
                if ($$2.m_60795_()) {
                    $$1 = $$1.m_82492_(0.0, $$1.f_82480_, 0.0);
                }
                Squid.this.m_29958_((float)$$1.f_82479_ / 20.0f, (float)$$1.f_82480_ / 20.0f, (float)$$1.f_82481_ / 20.0f);
            }
            if (this.f_29991_ % 10 == 5) {
                Squid.this.f_19853_.m_7106_(ParticleTypes.f_123795_, Squid.this.m_20185_(), Squid.this.m_20186_(), Squid.this.m_20189_(), 0.0, 0.0, 0.0);
            }
        }
    }
}

