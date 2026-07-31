/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Guardian
extends Monster {
    protected static final int f_149711_ = 80;
    private static final EntityDataAccessor<Boolean> f_32797_ = SynchedEntityData.m_135353_(Guardian.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_32807_ = SynchedEntityData.m_135353_(Guardian.class, EntityDataSerializers.f_135028_);
    private float f_32798_;
    private float f_32799_;
    private float f_32800_;
    private float f_32801_;
    private float f_32802_;
    @Nullable
    private LivingEntity f_32803_;
    private int f_32804_;
    private boolean f_32805_;
    @Nullable
    protected RandomStrollGoal f_32806_;

    public Guardian(EntityType<? extends Guardian> p_32810_, Level p_32811_) {
        super((EntityType<? extends Monster>)p_32810_, p_32811_);
        this.f_21364_ = 10;
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.f_21342_ = new GuardianMoveControl(this);
        this.f_32799_ = this.f_32798_ = this.f_19796_.m_188501_();
    }

    @Override
    protected void m_8099_() {
        MoveTowardsRestrictionGoal $$0 = new MoveTowardsRestrictionGoal(this, 1.0);
        this.f_32806_ = new RandomStrollGoal(this, 1.0, 80);
        this.f_21345_.m_25352_(4, new GuardianAttackGoal(this));
        this.f_21345_.m_25352_(5, $$0);
        this.f_21345_.m_25352_(7, this.f_32806_);
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Guardian.class, 12.0f, 0.01f));
        this.f_21345_.m_25352_(9, new RandomLookAroundGoal(this));
        this.f_32806_.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        $$0.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 10, true, false, new GuardianAttackSelector(this)));
    }

    public static AttributeSupplier.Builder m_32853_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22281_, 6.0).m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22277_, 16.0).m_22268_(Attributes.f_22276_, 30.0);
    }

    @Override
    protected PathNavigation m_6037_(Level p_32846_) {
        return new WaterBoundPathNavigation(this, p_32846_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_32797_, false);
        this.f_19804_.m_135372_(f_32807_, 0);
    }

    @Override
    public boolean m_6040_() {
        return true;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21644_;
    }

    public boolean m_32854_() {
        return this.f_19804_.m_135370_(f_32797_);
    }

    void m_32861_(boolean p_32862_) {
        this.f_19804_.m_135381_(f_32797_, p_32862_);
    }

    public int m_7552_() {
        return 80;
    }

    void m_32817_(int p_32818_) {
        this.f_19804_.m_135381_(f_32807_, p_32818_);
    }

    public boolean m_32855_() {
        return this.f_19804_.m_135370_(f_32807_) != 0;
    }

    @Nullable
    public LivingEntity m_32856_() {
        if (!this.m_32855_()) {
            return null;
        }
        if (this.f_19853_.f_46443_) {
            if (this.f_32803_ != null) {
                return this.f_32803_;
            }
            Entity $$0 = this.f_19853_.m_6815_(this.f_19804_.m_135370_(f_32807_));
            if ($$0 instanceof LivingEntity) {
                this.f_32803_ = (LivingEntity)$$0;
                return this.f_32803_;
            }
            return null;
        }
        return this.m_5448_();
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_32834_) {
        super.m_7350_(p_32834_);
        if (f_32807_.equals(p_32834_)) {
            this.f_32804_ = 0;
            this.f_32803_ = null;
        }
    }

    @Override
    public int m_8100_() {
        return 160;
    }

    @Override
    protected SoundEvent m_7515_() {
        return this.m_20072_() ? SoundEvents.f_11999_ : SoundEvents.f_12000_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32852_) {
        return this.m_20072_() ? SoundEvents.f_12005_ : SoundEvents.f_12006_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return this.m_20072_() ? SoundEvents.f_12002_ : SoundEvents.f_12003_;
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected float m_6431_(Pose p_32843_, EntityDimensions p_32844_) {
        return p_32844_.f_20378_ * 0.5f;
    }

    @Override
    public float m_5610_(BlockPos p_32831_, LevelReader p_32832_) {
        if (p_32832_.m_6425_(p_32831_).m_205070_(FluidTags.f_13131_)) {
            return 10.0f + p_32832_.m_220419_(p_32831_);
        }
        return super.m_5610_(p_32831_, p_32832_);
    }

    @Override
    public void m_8107_() {
        if (this.m_6084_()) {
            if (this.f_19853_.f_46443_) {
                this.f_32799_ = this.f_32798_;
                if (!this.m_20069_()) {
                    this.f_32800_ = 2.0f;
                    Vec3 $$0 = this.m_20184_();
                    if ($$0.f_82480_ > 0.0 && this.f_32805_ && !this.m_20067_()) {
                        this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7868_(), this.m_5720_(), 1.0f, 1.0f, false);
                    }
                    this.f_32805_ = $$0.f_82480_ < 0.0 && this.f_19853_.m_46575_(this.m_20183_().m_7495_(), this);
                } else {
                    this.f_32800_ = this.m_32854_() ? (this.f_32800_ < 0.5f ? 4.0f : (this.f_32800_ += (0.5f - this.f_32800_) * 0.1f)) : (this.f_32800_ += (0.125f - this.f_32800_) * 0.2f);
                }
                this.f_32798_ += this.f_32800_;
                this.f_32802_ = this.f_32801_;
                this.f_32801_ = !this.m_20072_() ? this.f_19796_.m_188501_() : (this.m_32854_() ? (this.f_32801_ += (0.0f - this.f_32801_) * 0.25f) : (this.f_32801_ += (1.0f - this.f_32801_) * 0.06f));
                if (this.m_32854_() && this.m_20069_()) {
                    Vec3 $$1 = this.m_20252_(0.0f);
                    for (int $$2 = 0; $$2 < 2; ++$$2) {
                        this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20208_(0.5) - $$1.f_82479_ * 1.5, this.m_20187_() - $$1.f_82480_ * 1.5, this.m_20262_(0.5) - $$1.f_82481_ * 1.5, 0.0, 0.0, 0.0);
                    }
                }
                if (this.m_32855_()) {
                    LivingEntity $$3;
                    if (this.f_32804_ < this.m_7552_()) {
                        ++this.f_32804_;
                    }
                    if (($$3 = this.m_32856_()) != null) {
                        this.m_21563_().m_24960_($$3, 90.0f, 90.0f);
                        this.m_21563_().m_8128_();
                        double $$4 = this.m_32812_(0.0f);
                        double $$5 = $$3.m_20185_() - this.m_20185_();
                        double $$6 = $$3.m_20227_(0.5) - this.m_20188_();
                        double $$7 = $$3.m_20189_() - this.m_20189_();
                        double $$8 = Math.sqrt($$5 * $$5 + $$6 * $$6 + $$7 * $$7);
                        $$5 /= $$8;
                        $$6 /= $$8;
                        $$7 /= $$8;
                        double $$9 = this.f_19796_.m_188500_();
                        while ($$9 < $$8) {
                            this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20185_() + $$5 * ($$9 += 1.8 - $$4 + this.f_19796_.m_188500_() * (1.7 - $$4)), this.m_20188_() + $$6 * $$9, this.m_20189_() + $$7 * $$9, 0.0, 0.0, 0.0);
                        }
                    }
                }
            }
            if (this.m_20072_()) {
                this.m_20301_(300);
            } else if (this.f_19861_) {
                this.m_20256_(this.m_20184_().m_82520_((this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.4f, 0.5, (this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.4f));
                this.m_146922_(this.f_19796_.m_188501_() * 360.0f);
                this.f_19861_ = false;
                this.f_19812_ = true;
            }
            if (this.m_32855_()) {
                this.m_146922_(this.f_20885_);
            }
        }
        super.m_8107_();
    }

    protected SoundEvent m_7868_() {
        return SoundEvents.f_12004_;
    }

    public float m_32863_(float p_32864_) {
        return Mth.m_14179_(p_32864_, this.f_32799_, this.f_32798_);
    }

    public float m_32865_(float p_32866_) {
        return Mth.m_14179_(p_32866_, this.f_32802_, this.f_32801_);
    }

    public float m_32812_(float p_32813_) {
        return ((float)this.f_32804_ + p_32813_) / (float)this.m_7552_();
    }

    @Override
    public boolean m_6914_(LevelReader p_32829_) {
        return p_32829_.m_45784_(this);
    }

    public static boolean m_218990_(EntityType<? extends Guardian> p_218991_, LevelAccessor p_218992_, MobSpawnType p_218993_, BlockPos p_218994_, RandomSource p_218995_) {
        return !(p_218995_.m_188503_(20) != 0 && p_218992_.m_46861_(p_218994_) || p_218992_.m_46791_() == Difficulty.PEACEFUL || p_218993_ != MobSpawnType.SPAWNER && !p_218992_.m_6425_(p_218994_).m_205070_(FluidTags.f_13131_) || !p_218992_.m_6425_(p_218994_.m_7495_()).m_205070_(FluidTags.f_13131_));
    }

    @Override
    public boolean m_6469_(DamageSource p_32820_, float p_32821_) {
        if (!this.m_32854_() && !p_32820_.m_19387_() && p_32820_.m_7640_() instanceof LivingEntity) {
            LivingEntity $$2 = (LivingEntity)p_32820_.m_7640_();
            if (!p_32820_.m_19372_()) {
                $$2.m_6469_(DamageSource.m_19335_(this), 2.0f);
            }
        }
        if (this.f_32806_ != null) {
            this.f_32806_.m_25751_();
        }
        return super.m_6469_(p_32820_, p_32821_);
    }

    @Override
    public int m_8132_() {
        return 180;
    }

    @Override
    public void m_7023_(Vec3 p_32858_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(0.1f, p_32858_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (!this.m_32854_() && this.m_5448_() == null) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
        } else {
            super.m_7023_(p_32858_);
        }
    }

    static class GuardianMoveControl
    extends MoveControl {
        private final Guardian f_32884_;

        public GuardianMoveControl(Guardian p_32886_) {
            super(p_32886_);
            this.f_32884_ = p_32886_;
        }

        @Override
        public void m_8126_() {
            if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_32884_.m_21573_().m_26571_()) {
                this.f_32884_.m_7910_(0.0f);
                this.f_32884_.m_32861_(false);
                return;
            }
            Vec3 $$0 = new Vec3(this.f_24975_ - this.f_32884_.m_20185_(), this.f_24976_ - this.f_32884_.m_20186_(), this.f_24977_ - this.f_32884_.m_20189_());
            double $$1 = $$0.m_82553_();
            double $$2 = $$0.f_82479_ / $$1;
            double $$3 = $$0.f_82480_ / $$1;
            double $$4 = $$0.f_82481_ / $$1;
            float $$5 = (float)(Mth.m_14136_($$0.f_82481_, $$0.f_82479_) * 57.2957763671875) - 90.0f;
            this.f_32884_.m_146922_(this.m_24991_(this.f_32884_.m_146908_(), $$5, 90.0f));
            this.f_32884_.f_20883_ = this.f_32884_.m_146908_();
            float $$6 = (float)(this.f_24978_ * this.f_32884_.m_21133_(Attributes.f_22279_));
            float $$7 = Mth.m_14179_(0.125f, this.f_32884_.m_6113_(), $$6);
            this.f_32884_.m_7910_($$7);
            double $$8 = Math.sin((double)(this.f_32884_.f_19797_ + this.f_32884_.m_19879_()) * 0.5) * 0.05;
            double $$9 = Math.cos(this.f_32884_.m_146908_() * ((float)Math.PI / 180));
            double $$10 = Math.sin(this.f_32884_.m_146908_() * ((float)Math.PI / 180));
            double $$11 = Math.sin((double)(this.f_32884_.f_19797_ + this.f_32884_.m_19879_()) * 0.75) * 0.05;
            this.f_32884_.m_20256_(this.f_32884_.m_20184_().m_82520_($$8 * $$9, $$11 * ($$10 + $$9) * 0.25 + (double)$$7 * $$3 * 0.1, $$8 * $$10));
            LookControl $$12 = this.f_32884_.m_21563_();
            double $$13 = this.f_32884_.m_20185_() + $$2 * 2.0;
            double $$14 = this.f_32884_.m_20188_() + $$3 / $$1;
            double $$15 = this.f_32884_.m_20189_() + $$4 * 2.0;
            double $$16 = $$12.m_24969_();
            double $$17 = $$12.m_24970_();
            double $$18 = $$12.m_24971_();
            if (!$$12.m_186069_()) {
                $$16 = $$13;
                $$17 = $$14;
                $$18 = $$15;
            }
            this.f_32884_.m_21563_().m_24950_(Mth.m_14139_(0.125, $$16, $$13), Mth.m_14139_(0.125, $$17, $$14), Mth.m_14139_(0.125, $$18, $$15), 10.0f, 40.0f);
            this.f_32884_.m_32861_(true);
        }
    }

    static class GuardianAttackGoal
    extends Goal {
        private final Guardian f_32867_;
        private int f_32868_;
        private final boolean f_32869_;

        public GuardianAttackGoal(Guardian p_32871_) {
            this.f_32867_ = p_32871_;
            this.f_32869_ = p_32871_ instanceof ElderGuardian;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = this.f_32867_.m_5448_();
            return $$0 != null && $$0.m_6084_();
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && (this.f_32869_ || this.f_32867_.m_5448_() != null && this.f_32867_.m_20280_(this.f_32867_.m_5448_()) > 9.0);
        }

        @Override
        public void m_8056_() {
            this.f_32868_ = -10;
            this.f_32867_.m_21573_().m_26573_();
            LivingEntity $$0 = this.f_32867_.m_5448_();
            if ($$0 != null) {
                this.f_32867_.m_21563_().m_24960_($$0, 90.0f, 90.0f);
            }
            this.f_32867_.f_19812_ = true;
        }

        @Override
        public void m_8041_() {
            this.f_32867_.m_32817_(0);
            this.f_32867_.m_6710_(null);
            this.f_32867_.f_32806_.m_25751_();
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = this.f_32867_.m_5448_();
            if ($$0 == null) {
                return;
            }
            this.f_32867_.m_21573_().m_26573_();
            this.f_32867_.m_21563_().m_24960_($$0, 90.0f, 90.0f);
            if (!this.f_32867_.m_142582_($$0)) {
                this.f_32867_.m_6710_(null);
                return;
            }
            ++this.f_32868_;
            if (this.f_32868_ == 0) {
                this.f_32867_.m_32817_($$0.m_19879_());
                if (!this.f_32867_.m_20067_()) {
                    this.f_32867_.f_19853_.m_7605_(this.f_32867_, (byte)21);
                }
            } else if (this.f_32868_ >= this.f_32867_.m_7552_()) {
                float $$1 = 1.0f;
                if (this.f_32867_.f_19853_.m_46791_() == Difficulty.HARD) {
                    $$1 += 2.0f;
                }
                if (this.f_32869_) {
                    $$1 += 2.0f;
                }
                $$0.m_6469_(DamageSource.m_19367_(this.f_32867_, this.f_32867_), $$1);
                $$0.m_6469_(DamageSource.m_19370_(this.f_32867_), (float)this.f_32867_.m_21133_(Attributes.f_22281_));
                this.f_32867_.m_6710_(null);
            }
            super.m_8037_();
        }
    }

    static class GuardianAttackSelector
    implements Predicate<LivingEntity> {
        private final Guardian f_32877_;

        public GuardianAttackSelector(Guardian p_32879_) {
            this.f_32877_ = p_32879_;
        }

        @Override
        public boolean test(@Nullable LivingEntity p_32881_) {
            return (p_32881_ instanceof Player || p_32881_ instanceof Squid || p_32881_ instanceof Axolotl) && p_32881_.m_20280_(this.f_32877_) > 9.0;
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((LivingEntity)object);
        }
    }
}

