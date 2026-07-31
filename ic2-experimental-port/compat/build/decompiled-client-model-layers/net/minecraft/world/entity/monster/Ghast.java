/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Ghast
extends FlyingMob
implements Enemy {
    private static final EntityDataAccessor<Boolean> f_32721_ = SynchedEntityData.m_135353_(Ghast.class, EntityDataSerializers.f_135035_);
    private int f_32722_ = 1;

    public Ghast(EntityType<? extends Ghast> p_32725_, Level p_32726_) {
        super((EntityType<? extends FlyingMob>)p_32725_, p_32726_);
        this.f_21364_ = 5;
        this.f_21342_ = new GhastMoveControl(this);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(5, new RandomFloatAroundGoal(this));
        this.f_21345_.m_25352_(7, new GhastLookGoal(this));
        this.f_21345_.m_25352_(7, new GhastShootFireballGoal(this));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, p_32755_ -> Math.abs(p_32755_.m_20186_() - this.m_20186_()) <= 4.0));
    }

    public boolean m_32756_() {
        return this.f_19804_.m_135370_(f_32721_);
    }

    public void m_32758_(boolean p_32759_) {
        this.f_19804_.m_135381_(f_32721_, p_32759_);
    }

    public int m_32751_() {
        return this.f_32722_;
    }

    @Override
    protected boolean m_8028_() {
        return true;
    }

    private static boolean m_238407_(DamageSource p_238408_) {
        return p_238408_.m_7640_() instanceof LargeFireball && p_238408_.m_7639_() instanceof Player;
    }

    @Override
    public boolean m_6673_(DamageSource p_238289_) {
        return !Ghast.m_238407_(p_238289_) && super.m_6673_(p_238289_);
    }

    @Override
    public boolean m_6469_(DamageSource p_32730_, float p_32731_) {
        if (Ghast.m_238407_(p_32730_)) {
            super.m_6469_(p_32730_, 1000.0f);
            return true;
        }
        if (this.m_6673_(p_32730_)) {
            return false;
        }
        return super.m_6469_(p_32730_, p_32731_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_32721_, false);
    }

    public static AttributeSupplier.Builder m_32752_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22277_, 100.0);
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11919_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32750_) {
        return SoundEvents.f_11921_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11920_;
    }

    @Override
    protected float m_6121_() {
        return 5.0f;
    }

    public static boolean m_218984_(EntityType<Ghast> p_218985_, LevelAccessor p_218986_, MobSpawnType p_218987_, BlockPos p_218988_, RandomSource p_218989_) {
        return p_218986_.m_46791_() != Difficulty.PEACEFUL && p_218989_.m_188503_(20) == 0 && Ghast.m_217057_(p_218985_, p_218986_, p_218987_, p_218988_, p_218989_);
    }

    @Override
    public int m_5792_() {
        return 1;
    }

    @Override
    public void m_7380_(CompoundTag p_32744_) {
        super.m_7380_(p_32744_);
        p_32744_.m_128344_("ExplosionPower", (byte)this.f_32722_);
    }

    @Override
    public void m_7378_(CompoundTag p_32733_) {
        super.m_7378_(p_32733_);
        if (p_32733_.m_128425_("ExplosionPower", 99)) {
            this.f_32722_ = p_32733_.m_128445_("ExplosionPower");
        }
    }

    @Override
    protected float m_6431_(Pose p_32741_, EntityDimensions p_32742_) {
        return 2.6f;
    }

    static class GhastMoveControl
    extends MoveControl {
        private final Ghast f_32765_;
        private int f_32766_;

        public GhastMoveControl(Ghast p_32768_) {
            super(p_32768_);
            this.f_32765_ = p_32768_;
        }

        @Override
        public void m_8126_() {
            if (this.f_24981_ != MoveControl.Operation.MOVE_TO) {
                return;
            }
            if (this.f_32766_-- <= 0) {
                this.f_32766_ += this.f_32765_.m_217043_().m_188503_(5) + 2;
                Vec3 $$0 = new Vec3(this.f_24975_ - this.f_32765_.m_20185_(), this.f_24976_ - this.f_32765_.m_20186_(), this.f_24977_ - this.f_32765_.m_20189_());
                double $$1 = $$0.m_82553_();
                if (this.m_32770_($$0 = $$0.m_82541_(), Mth.m_14165_($$1))) {
                    this.f_32765_.m_20256_(this.f_32765_.m_20184_().m_82549_($$0.m_82490_(0.1)));
                } else {
                    this.f_24981_ = MoveControl.Operation.WAIT;
                }
            }
        }

        private boolean m_32770_(Vec3 p_32771_, int p_32772_) {
            AABB $$2 = this.f_32765_.m_20191_();
            for (int $$3 = 1; $$3 < p_32772_; ++$$3) {
                if (this.f_32765_.f_19853_.m_45756_(this.f_32765_, $$2 = $$2.m_82383_(p_32771_))) continue;
                return false;
            }
            return true;
        }
    }

    static class RandomFloatAroundGoal
    extends Goal {
        private final Ghast f_32781_;

        public RandomFloatAroundGoal(Ghast p_32783_) {
            this.f_32781_ = p_32783_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            double $$3;
            double $$2;
            MoveControl $$0 = this.f_32781_.m_21566_();
            if (!$$0.m_24995_()) {
                return true;
            }
            double $$1 = $$0.m_25000_() - this.f_32781_.m_20185_();
            double $$4 = $$1 * $$1 + ($$2 = $$0.m_25001_() - this.f_32781_.m_20186_()) * $$2 + ($$3 = $$0.m_25002_() - this.f_32781_.m_20189_()) * $$3;
            return $$4 < 1.0 || $$4 > 3600.0;
        }

        @Override
        public boolean m_8045_() {
            return false;
        }

        @Override
        public void m_8056_() {
            RandomSource $$0 = this.f_32781_.m_217043_();
            double $$1 = this.f_32781_.m_20185_() + (double)(($$0.m_188501_() * 2.0f - 1.0f) * 16.0f);
            double $$2 = this.f_32781_.m_20186_() + (double)(($$0.m_188501_() * 2.0f - 1.0f) * 16.0f);
            double $$3 = this.f_32781_.m_20189_() + (double)(($$0.m_188501_() * 2.0f - 1.0f) * 16.0f);
            this.f_32781_.m_21566_().m_6849_($$1, $$2, $$3, 1.0);
        }
    }

    static class GhastLookGoal
    extends Goal {
        private final Ghast f_32760_;

        public GhastLookGoal(Ghast p_32762_) {
            this.f_32760_ = p_32762_;
            this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            return true;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            if (this.f_32760_.m_5448_() == null) {
                Vec3 $$0 = this.f_32760_.m_20184_();
                this.f_32760_.m_146922_(-((float)Mth.m_14136_($$0.f_82479_, $$0.f_82481_)) * 57.295776f);
                this.f_32760_.f_20883_ = this.f_32760_.m_146908_();
            } else {
                LivingEntity $$1 = this.f_32760_.m_5448_();
                double $$2 = 64.0;
                if ($$1.m_20280_(this.f_32760_) < 4096.0) {
                    double $$3 = $$1.m_20185_() - this.f_32760_.m_20185_();
                    double $$4 = $$1.m_20189_() - this.f_32760_.m_20189_();
                    this.f_32760_.m_146922_(-((float)Mth.m_14136_($$3, $$4)) * 57.295776f);
                    this.f_32760_.f_20883_ = this.f_32760_.m_146908_();
                }
            }
        }
    }

    static class GhastShootFireballGoal
    extends Goal {
        private final Ghast f_32774_;
        public int f_32773_;

        public GhastShootFireballGoal(Ghast p_32776_) {
            this.f_32774_ = p_32776_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_32774_.m_5448_() != null;
        }

        @Override
        public void m_8056_() {
            this.f_32773_ = 0;
        }

        @Override
        public void m_8041_() {
            this.f_32774_.m_32758_(false);
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = this.f_32774_.m_5448_();
            if ($$0 == null) {
                return;
            }
            double $$1 = 64.0;
            if ($$0.m_20280_(this.f_32774_) < 4096.0 && this.f_32774_.m_142582_($$0)) {
                Level $$2 = this.f_32774_.f_19853_;
                ++this.f_32773_;
                if (this.f_32773_ == 10 && !this.f_32774_.m_20067_()) {
                    $$2.m_5898_(null, 1015, this.f_32774_.m_20183_(), 0);
                }
                if (this.f_32773_ == 20) {
                    double $$3 = 4.0;
                    Vec3 $$4 = this.f_32774_.m_20252_(1.0f);
                    double $$5 = $$0.m_20185_() - (this.f_32774_.m_20185_() + $$4.f_82479_ * 4.0);
                    double $$6 = $$0.m_20227_(0.5) - (0.5 + this.f_32774_.m_20227_(0.5));
                    double $$7 = $$0.m_20189_() - (this.f_32774_.m_20189_() + $$4.f_82481_ * 4.0);
                    if (!this.f_32774_.m_20067_()) {
                        $$2.m_5898_(null, 1016, this.f_32774_.m_20183_(), 0);
                    }
                    LargeFireball $$8 = new LargeFireball($$2, (LivingEntity)this.f_32774_, $$5, $$6, $$7, this.f_32774_.m_32751_());
                    $$8.m_6034_(this.f_32774_.m_20185_() + $$4.f_82479_ * 4.0, this.f_32774_.m_20227_(0.5) + 0.5, $$8.m_20189_() + $$4.f_82481_ * 4.0);
                    $$2.m_7967_($$8);
                    this.f_32773_ = -40;
                }
            } else if (this.f_32773_ > 0) {
                --this.f_32773_;
            }
            this.f_32774_.m_32758_(this.f_32773_ > 10);
        }
    }
}

