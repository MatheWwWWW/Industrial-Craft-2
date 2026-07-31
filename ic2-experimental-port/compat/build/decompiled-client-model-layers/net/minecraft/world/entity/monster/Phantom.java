/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Phantom
extends FlyingMob
implements Enemy {
    public static final float f_149721_ = 7.448451f;
    public static final int f_149722_ = Mth.m_14167_(24.166098f);
    private static final EntityDataAccessor<Integer> f_33095_ = SynchedEntityData.m_135353_(Phantom.class, EntityDataSerializers.f_135028_);
    Vec3 f_33097_ = Vec3.f_82478_;
    BlockPos f_33098_ = BlockPos.f_121853_;
    AttackPhase f_33096_ = AttackPhase.CIRCLE;

    public Phantom(EntityType<? extends Phantom> p_33101_, Level p_33102_) {
        super((EntityType<? extends FlyingMob>)p_33101_, p_33102_);
        this.f_21364_ = 5;
        this.f_21342_ = new PhantomMoveControl(this);
        this.f_21365_ = new PhantomLookControl(this);
    }

    @Override
    public boolean m_142039_() {
        return (this.m_149736_() + this.f_19797_) % f_149722_ == 0;
    }

    @Override
    protected BodyRotationControl m_7560_() {
        return new PhantomBodyRotationControl(this);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new PhantomAttackStrategyGoal());
        this.f_21345_.m_25352_(2, new PhantomSweepAttackGoal());
        this.f_21345_.m_25352_(3, new PhantomCircleAroundAnchorGoal());
        this.f_21346_.m_25352_(1, new PhantomAttackPlayerTargetGoal());
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33095_, 0);
    }

    public void m_33108_(int p_33109_) {
        this.f_19804_.m_135381_(f_33095_, Mth.m_14045_(p_33109_, 0, 64));
    }

    private void m_33155_() {
        this.m_6210_();
        this.m_21051_(Attributes.f_22281_).m_22100_(6 + this.m_33172_());
    }

    public int m_33172_() {
        return this.f_19804_.m_135370_(f_33095_);
    }

    @Override
    protected float m_6431_(Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.f_20378_ * 0.35f;
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_33134_) {
        if (f_33095_.equals(p_33134_)) {
            this.m_33155_();
        }
        super.m_7350_(p_33134_);
    }

    public int m_149736_() {
        return this.m_19879_() * 3;
    }

    @Override
    protected boolean m_8028_() {
        return true;
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_) {
            float $$0 = Mth.m_14089_((float)(this.m_149736_() + this.f_19797_) * 7.448451f * ((float)Math.PI / 180) + (float)Math.PI);
            float $$1 = Mth.m_14089_((float)(this.m_149736_() + this.f_19797_ + 1) * 7.448451f * ((float)Math.PI / 180) + (float)Math.PI);
            if ($$0 > 0.0f && $$1 <= 0.0f) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12230_, this.m_5720_(), 0.95f + this.f_19796_.m_188501_() * 0.05f, 0.95f + this.f_19796_.m_188501_() * 0.05f, false);
            }
            int $$2 = this.m_33172_();
            float $$3 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)) * (1.3f + 0.21f * (float)$$2);
            float $$4 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)) * (1.3f + 0.21f * (float)$$2);
            float $$5 = (0.3f + $$0 * 0.45f) * ((float)$$2 * 0.2f + 1.0f);
            this.f_19853_.m_7106_(ParticleTypes.f_123757_, this.m_20185_() + (double)$$3, this.m_20186_() + (double)$$5, this.m_20189_() + (double)$$4, 0.0, 0.0, 0.0);
            this.f_19853_.m_7106_(ParticleTypes.f_123757_, this.m_20185_() - (double)$$3, this.m_20186_() + (double)$$5, this.m_20189_() - (double)$$4, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void m_8107_() {
        if (this.m_6084_() && this.m_21527_()) {
            this.m_20254_(8);
        }
        super.m_8107_();
    }

    @Override
    protected void m_8024_() {
        super.m_8024_();
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
        this.f_33098_ = this.m_20183_().m_6630_(5);
        this.m_33108_(0);
        return super.m_6518_(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    @Override
    public void m_7378_(CompoundTag p_33132_) {
        super.m_7378_(p_33132_);
        if (p_33132_.m_128441_("AX")) {
            this.f_33098_ = new BlockPos(p_33132_.m_128451_("AX"), p_33132_.m_128451_("AY"), p_33132_.m_128451_("AZ"));
        }
        this.m_33108_(p_33132_.m_128451_("Size"));
    }

    @Override
    public void m_7380_(CompoundTag p_33141_) {
        super.m_7380_(p_33141_);
        p_33141_.m_128405_("AX", this.f_33098_.m_123341_());
        p_33141_.m_128405_("AY", this.f_33098_.m_123342_());
        p_33141_.m_128405_("AZ", this.f_33098_.m_123343_());
        p_33141_.m_128405_("Size", this.m_33172_());
    }

    @Override
    public boolean m_6783_(double p_33107_) {
        return true;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12227_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33152_) {
        return SoundEvents.f_12231_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12229_;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    protected float m_6121_() {
        return 1.0f;
    }

    @Override
    public boolean m_6549_(EntityType<?> p_33111_) {
        return true;
    }

    @Override
    public EntityDimensions m_6972_(Pose p_33113_) {
        int $$1 = this.m_33172_();
        EntityDimensions $$2 = super.m_6972_(p_33113_);
        float $$3 = ($$2.f_20377_ + 0.2f * (float)$$1) / $$2.f_20377_;
        return $$2.m_20388_($$3);
    }

    static final class AttackPhase
    extends Enum<AttackPhase> {
        public static final /* enum */ AttackPhase CIRCLE = new AttackPhase();
        public static final /* enum */ AttackPhase SWOOP = new AttackPhase();
        private static final /* synthetic */ AttackPhase[] $VALUES;

        public static AttackPhase[] values() {
            return (AttackPhase[])$VALUES.clone();
        }

        public static AttackPhase valueOf(String p_33189_) {
            return Enum.valueOf(AttackPhase.class, p_33189_);
        }

        private static /* synthetic */ AttackPhase[] m_149737_() {
            return new AttackPhase[]{CIRCLE, SWOOP};
        }

        static {
            $VALUES = AttackPhase.m_149737_();
        }
    }

    class PhantomMoveControl
    extends MoveControl {
        private float f_33238_;

        public PhantomMoveControl(Mob p_33241_) {
            super(p_33241_);
            this.f_33238_ = 0.1f;
        }

        @Override
        public void m_8126_() {
            if (Phantom.this.f_19862_) {
                Phantom.this.m_146922_(Phantom.this.m_146908_() + 180.0f);
                this.f_33238_ = 0.1f;
            }
            double $$0 = Phantom.this.f_33097_.f_82479_ - Phantom.this.m_20185_();
            double $$1 = Phantom.this.f_33097_.f_82480_ - Phantom.this.m_20186_();
            double $$2 = Phantom.this.f_33097_.f_82481_ - Phantom.this.m_20189_();
            double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
            if (Math.abs($$3) > (double)1.0E-5f) {
                double $$4 = 1.0 - Math.abs($$1 * (double)0.7f) / $$3;
                $$3 = Math.sqrt(($$0 *= $$4) * $$0 + ($$2 *= $$4) * $$2);
                double $$5 = Math.sqrt($$0 * $$0 + $$2 * $$2 + $$1 * $$1);
                float $$6 = Phantom.this.m_146908_();
                float $$7 = (float)Mth.m_14136_($$2, $$0);
                float $$8 = Mth.m_14177_(Phantom.this.m_146908_() + 90.0f);
                float $$9 = Mth.m_14177_($$7 * 57.295776f);
                Phantom.this.m_146922_(Mth.m_14148_($$8, $$9, 4.0f) - 90.0f);
                Phantom.this.f_20883_ = Phantom.this.m_146908_();
                this.f_33238_ = Mth.m_14145_($$6, Phantom.this.m_146908_()) < 3.0f ? Mth.m_14121_(this.f_33238_, 1.8f, 0.005f * (1.8f / this.f_33238_)) : Mth.m_14121_(this.f_33238_, 0.2f, 0.025f);
                float $$10 = (float)(-(Mth.m_14136_(-$$1, $$3) * 57.2957763671875));
                Phantom.this.m_146926_($$10);
                float $$11 = Phantom.this.m_146908_() + 90.0f;
                double $$12 = (double)(this.f_33238_ * Mth.m_14089_($$11 * ((float)Math.PI / 180))) * Math.abs($$0 / $$5);
                double $$13 = (double)(this.f_33238_ * Mth.m_14031_($$11 * ((float)Math.PI / 180))) * Math.abs($$2 / $$5);
                double $$14 = (double)(this.f_33238_ * Mth.m_14031_($$10 * ((float)Math.PI / 180))) * Math.abs($$1 / $$5);
                Vec3 $$15 = Phantom.this.m_20184_();
                Phantom.this.m_20256_($$15.m_82549_(new Vec3($$12, $$14, $$13).m_82546_($$15).m_82490_(0.2)));
            }
        }
    }

    class PhantomLookControl
    extends LookControl {
        public PhantomLookControl(Mob p_33235_) {
            super(p_33235_);
        }

        @Override
        public void m_8128_() {
        }
    }

    class PhantomBodyRotationControl
    extends BodyRotationControl {
        public PhantomBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        @Override
        public void m_8121_() {
            Phantom.this.f_20885_ = Phantom.this.f_20883_;
            Phantom.this.f_20883_ = Phantom.this.m_146908_();
        }
    }

    class PhantomAttackStrategyGoal
    extends Goal {
        private int f_33202_;

        PhantomAttackStrategyGoal() {
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = Phantom.this.m_5448_();
            if ($$0 != null) {
                return Phantom.this.m_21040_($$0, TargetingConditions.f_26872_);
            }
            return false;
        }

        @Override
        public void m_8056_() {
            this.f_33202_ = this.m_183277_(10);
            Phantom.this.f_33096_ = AttackPhase.CIRCLE;
            this.m_33212_();
        }

        @Override
        public void m_8041_() {
            Phantom.this.f_33098_ = Phantom.this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING, Phantom.this.f_33098_).m_6630_(10 + Phantom.this.f_19796_.m_188503_(20));
        }

        @Override
        public void m_8037_() {
            if (Phantom.this.f_33096_ == AttackPhase.CIRCLE) {
                --this.f_33202_;
                if (this.f_33202_ <= 0) {
                    Phantom.this.f_33096_ = AttackPhase.SWOOP;
                    this.m_33212_();
                    this.f_33202_ = this.m_183277_((8 + Phantom.this.f_19796_.m_188503_(4)) * 20);
                    Phantom.this.m_5496_(SoundEvents.f_12232_, 10.0f, 0.95f + Phantom.this.f_19796_.m_188501_() * 0.1f);
                }
            }
        }

        private void m_33212_() {
            Phantom.this.f_33098_ = Phantom.this.m_5448_().m_20183_().m_6630_(20 + Phantom.this.f_19796_.m_188503_(20));
            if (Phantom.this.f_33098_.m_123342_() < Phantom.this.f_19853_.m_5736_()) {
                Phantom.this.f_33098_ = new BlockPos(Phantom.this.f_33098_.m_123341_(), Phantom.this.f_19853_.m_5736_() + 1, Phantom.this.f_33098_.m_123343_());
            }
        }
    }

    class PhantomSweepAttackGoal
    extends PhantomMoveTargetGoal {
        private static final int f_199895_ = 20;
        private boolean f_199896_;
        private int f_199897_;

        PhantomSweepAttackGoal() {
        }

        @Override
        public boolean m_8036_() {
            return Phantom.this.m_5448_() != null && Phantom.this.f_33096_ == AttackPhase.SWOOP;
        }

        @Override
        public boolean m_8045_() {
            LivingEntity $$0 = Phantom.this.m_5448_();
            if ($$0 == null) {
                return false;
            }
            if (!$$0.m_6084_()) {
                return false;
            }
            if ($$0 instanceof Player) {
                Player $$1 = (Player)$$0;
                if ($$0.m_5833_() || $$1.m_7500_()) {
                    return false;
                }
            }
            if (!this.m_8036_()) {
                return false;
            }
            if (Phantom.this.f_19797_ > this.f_199897_) {
                this.f_199897_ = Phantom.this.f_19797_ + 20;
                List<Entity> $$2 = Phantom.this.f_19853_.m_6443_(Cat.class, Phantom.this.m_20191_().m_82400_(16.0), EntitySelector.f_20402_);
                for (Cat cat : $$2) {
                    cat.m_28167_();
                }
                this.f_199896_ = !$$2.isEmpty();
            }
            return !this.f_199896_;
        }

        @Override
        public void m_8056_() {
        }

        @Override
        public void m_8041_() {
            Phantom.this.m_6710_(null);
            Phantom.this.f_33096_ = AttackPhase.CIRCLE;
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = Phantom.this.m_5448_();
            if ($$0 == null) {
                return;
            }
            Phantom.this.f_33097_ = new Vec3($$0.m_20185_(), $$0.m_20227_(0.5), $$0.m_20189_());
            if (Phantom.this.m_20191_().m_82400_(0.2f).m_82381_($$0.m_20191_())) {
                Phantom.this.m_7327_($$0);
                Phantom.this.f_33096_ = AttackPhase.CIRCLE;
                if (!Phantom.this.m_20067_()) {
                    Phantom.this.f_19853_.m_46796_(1039, Phantom.this.m_20183_(), 0);
                }
            } else if (Phantom.this.f_19862_ || Phantom.this.f_20916_ > 0) {
                Phantom.this.f_33096_ = AttackPhase.CIRCLE;
            }
        }
    }

    class PhantomCircleAroundAnchorGoal
    extends PhantomMoveTargetGoal {
        private float f_33219_;
        private float f_33220_;
        private float f_33221_;
        private float f_33222_;

        PhantomCircleAroundAnchorGoal() {
        }

        @Override
        public boolean m_8036_() {
            return Phantom.this.m_5448_() == null || Phantom.this.f_33096_ == AttackPhase.CIRCLE;
        }

        @Override
        public void m_8056_() {
            this.f_33220_ = 5.0f + Phantom.this.f_19796_.m_188501_() * 10.0f;
            this.f_33221_ = -4.0f + Phantom.this.f_19796_.m_188501_() * 9.0f;
            this.f_33222_ = Phantom.this.f_19796_.m_188499_() ? 1.0f : -1.0f;
            this.m_33231_();
        }

        @Override
        public void m_8037_() {
            if (Phantom.this.f_19796_.m_188503_(this.m_183277_(350)) == 0) {
                this.f_33221_ = -4.0f + Phantom.this.f_19796_.m_188501_() * 9.0f;
            }
            if (Phantom.this.f_19796_.m_188503_(this.m_183277_(250)) == 0) {
                this.f_33220_ += 1.0f;
                if (this.f_33220_ > 15.0f) {
                    this.f_33220_ = 5.0f;
                    this.f_33222_ = -this.f_33222_;
                }
            }
            if (Phantom.this.f_19796_.m_188503_(this.m_183277_(450)) == 0) {
                this.f_33219_ = Phantom.this.f_19796_.m_188501_() * 2.0f * (float)Math.PI;
                this.m_33231_();
            }
            if (this.m_33246_()) {
                this.m_33231_();
            }
            if (Phantom.this.f_33097_.f_82480_ < Phantom.this.m_20186_() && !Phantom.this.f_19853_.m_46859_(Phantom.this.m_20183_().m_6625_(1))) {
                this.f_33221_ = Math.max(1.0f, this.f_33221_);
                this.m_33231_();
            }
            if (Phantom.this.f_33097_.f_82480_ > Phantom.this.m_20186_() && !Phantom.this.f_19853_.m_46859_(Phantom.this.m_20183_().m_6630_(1))) {
                this.f_33221_ = Math.min(-1.0f, this.f_33221_);
                this.m_33231_();
            }
        }

        private void m_33231_() {
            if (BlockPos.f_121853_.equals(Phantom.this.f_33098_)) {
                Phantom.this.f_33098_ = Phantom.this.m_20183_();
            }
            this.f_33219_ += this.f_33222_ * 15.0f * ((float)Math.PI / 180);
            Phantom.this.f_33097_ = Vec3.m_82528_(Phantom.this.f_33098_).m_82520_(this.f_33220_ * Mth.m_14089_(this.f_33219_), -4.0f + this.f_33221_, this.f_33220_ * Mth.m_14031_(this.f_33219_));
        }
    }

    class PhantomAttackPlayerTargetGoal
    extends Goal {
        private final TargetingConditions f_33192_ = TargetingConditions.m_148352_().m_26883_(64.0);
        private int f_33193_ = PhantomAttackPlayerTargetGoal.m_186073_(20);

        PhantomAttackPlayerTargetGoal() {
        }

        @Override
        public boolean m_8036_() {
            if (this.f_33193_ > 0) {
                --this.f_33193_;
                return false;
            }
            this.f_33193_ = PhantomAttackPlayerTargetGoal.m_186073_(60);
            List<Player> $$0 = Phantom.this.f_19853_.m_45955_(this.f_33192_, Phantom.this, Phantom.this.m_20191_().m_82377_(16.0, 64.0, 16.0));
            if (!$$0.isEmpty()) {
                $$0.sort(Comparator.comparing(Entity::m_20186_).reversed());
                for (Player $$1 : $$0) {
                    if (!Phantom.this.m_21040_($$1, TargetingConditions.f_26872_)) continue;
                    Phantom.this.m_6710_($$1);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean m_8045_() {
            LivingEntity $$0 = Phantom.this.m_5448_();
            if ($$0 != null) {
                return Phantom.this.m_21040_($$0, TargetingConditions.f_26872_);
            }
            return false;
        }
    }

    abstract class PhantomMoveTargetGoal
    extends Goal {
        public PhantomMoveTargetGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean m_33246_() {
            return Phantom.this.f_33097_.m_82531_(Phantom.this.m_20185_(), Phantom.this.m_20186_(), Phantom.this.m_20189_()) < 4.0;
        }
    }
}

