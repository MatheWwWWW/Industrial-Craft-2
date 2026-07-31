/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.google.common.annotations.VisibleForTesting;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;

public class Slime
extends Mob
implements Enemy {
    private static final EntityDataAccessor<Integer> f_33582_ = SynchedEntityData.m_135353_(Slime.class, EntityDataSerializers.f_135028_);
    public static final int f_149844_ = 1;
    public static final int f_149845_ = 127;
    public float f_33581_;
    public float f_33584_;
    public float f_33585_;
    private boolean f_33583_;

    public Slime(EntityType<? extends Slime> p_33588_, Level p_33589_) {
        super((EntityType<? extends Mob>)p_33588_, p_33589_);
        this.f_21342_ = new SlimeMoveControl(this);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new SlimeFloatGoal(this));
        this.f_21345_.m_25352_(2, new SlimeAttackGoal(this));
        this.f_21345_.m_25352_(3, new SlimeRandomDirectionGoal(this));
        this.f_21345_.m_25352_(5, new SlimeKeepOnJumpingGoal(this));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, p_33641_ -> Math.abs(p_33641_.m_20186_() - this.m_20186_()) <= 4.0));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33582_, 1);
    }

    @VisibleForTesting
    public void m_7839_(int p_33594_, boolean p_33595_) {
        int $$2 = Mth.m_14045_(p_33594_, 1, 127);
        this.f_19804_.m_135381_(f_33582_, $$2);
        this.m_20090_();
        this.m_6210_();
        this.m_21051_(Attributes.f_22276_).m_22100_($$2 * $$2);
        this.m_21051_(Attributes.f_22279_).m_22100_(0.2f + 0.1f * (float)$$2);
        this.m_21051_(Attributes.f_22281_).m_22100_($$2);
        if (p_33595_) {
            this.m_21153_(this.m_21233_());
        }
        this.f_21364_ = $$2;
    }

    public int m_33632_() {
        return this.f_19804_.m_135370_(f_33582_);
    }

    @Override
    public void m_7380_(CompoundTag p_33619_) {
        super.m_7380_(p_33619_);
        p_33619_.m_128405_("Size", this.m_33632_() - 1);
        p_33619_.m_128379_("wasOnGround", this.f_33583_);
    }

    @Override
    public void m_7378_(CompoundTag p_33607_) {
        this.m_7839_(p_33607_.m_128451_("Size") + 1, false);
        super.m_7378_(p_33607_);
        this.f_33583_ = p_33607_.m_128471_("wasOnGround");
    }

    public boolean m_33633_() {
        return this.m_33632_() <= 1;
    }

    protected ParticleOptions m_6300_() {
        return ParticleTypes.f_123753_;
    }

    @Override
    protected boolean m_8028_() {
        return this.m_33632_() > 0;
    }

    @Override
    public void m_8119_() {
        this.f_33584_ += (this.f_33581_ - this.f_33584_) * 0.5f;
        this.f_33585_ = this.f_33584_;
        super.m_8119_();
        if (this.f_19861_ && !this.f_33583_) {
            int $$0 = this.m_33632_();
            for (int $$1 = 0; $$1 < $$0 * 8; ++$$1) {
                float $$2 = this.f_19796_.m_188501_() * ((float)Math.PI * 2);
                float $$3 = this.f_19796_.m_188501_() * 0.5f + 0.5f;
                float $$4 = Mth.m_14031_($$2) * (float)$$0 * 0.5f * $$3;
                float $$5 = Mth.m_14089_($$2) * (float)$$0 * 0.5f * $$3;
                this.f_19853_.m_7106_(this.m_6300_(), this.m_20185_() + (double)$$4, this.m_20186_(), this.m_20189_() + (double)$$5, 0.0, 0.0, 0.0);
            }
            this.m_5496_(this.m_7905_(), this.m_6121_(), ((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f) / 0.8f);
            this.f_33581_ = -0.5f;
        } else if (!this.f_19861_ && this.f_33583_) {
            this.f_33581_ = 1.0f;
        }
        this.f_33583_ = this.f_19861_;
        this.m_7480_();
    }

    protected void m_7480_() {
        this.f_33581_ *= 0.6f;
    }

    protected int m_7549_() {
        return this.f_19796_.m_188503_(20) + 10;
    }

    @Override
    public void m_6210_() {
        double $$0 = this.m_20185_();
        double $$1 = this.m_20186_();
        double $$2 = this.m_20189_();
        super.m_6210_();
        this.m_6034_($$0, $$1, $$2);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_33609_) {
        if (f_33582_.equals(p_33609_)) {
            this.m_6210_();
            this.m_146922_(this.f_20885_);
            this.f_20883_ = this.f_20885_;
            if (this.m_20069_() && this.f_19796_.m_188503_(20) == 0) {
                this.m_5841_();
            }
        }
        super.m_7350_(p_33609_);
    }

    public EntityType<? extends Slime> m_6095_() {
        return super.m_6095_();
    }

    @Override
    public void m_142687_(Entity.RemovalReason p_149847_) {
        int $$1 = this.m_33632_();
        if (!this.f_19853_.f_46443_ && $$1 > 1 && this.m_21224_()) {
            Component $$2 = this.m_7770_();
            boolean $$3 = this.m_21525_();
            float $$4 = (float)$$1 / 4.0f;
            int $$5 = $$1 / 2;
            int $$6 = 2 + this.f_19796_.m_188503_(3);
            for (int $$7 = 0; $$7 < $$6; ++$$7) {
                float $$8 = ((float)($$7 % 2) - 0.5f) * $$4;
                float $$9 = ((float)($$7 / 2) - 0.5f) * $$4;
                Slime $$10 = this.m_6095_().m_20615_(this.f_19853_);
                if (this.m_21532_()) {
                    $$10.m_21530_();
                }
                $$10.m_6593_($$2);
                $$10.m_21557_($$3);
                $$10.m_20331_(this.m_20147_());
                $$10.m_7839_($$5, true);
                $$10.m_7678_(this.m_20185_() + (double)$$8, this.m_20186_() + 0.5, this.m_20189_() + (double)$$9, this.f_19796_.m_188501_() * 360.0f, 0.0f);
                this.f_19853_.m_7967_($$10);
            }
        }
        super.m_142687_(p_149847_);
    }

    @Override
    public void m_7334_(Entity p_33636_) {
        super.m_7334_(p_33636_);
        if (p_33636_ instanceof IronGolem && this.m_7483_()) {
            this.m_33637_((LivingEntity)p_33636_);
        }
    }

    @Override
    public void m_6123_(Player p_33611_) {
        if (this.m_7483_()) {
            this.m_33637_(p_33611_);
        }
    }

    protected void m_33637_(LivingEntity p_33638_) {
        if (this.m_6084_()) {
            int $$1 = this.m_33632_();
            if (this.m_20280_(p_33638_) < 0.6 * (double)$$1 * (0.6 * (double)$$1) && this.m_142582_(p_33638_) && p_33638_.m_6469_(DamageSource.m_19370_(this), this.m_7566_())) {
                this.m_5496_(SoundEvents.f_12384_, 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                this.m_19970_(this, p_33638_);
            }
        }
    }

    @Override
    protected float m_6431_(Pose p_33614_, EntityDimensions p_33615_) {
        return 0.625f * p_33615_.f_20378_;
    }

    protected boolean m_7483_() {
        return !this.m_33633_() && this.m_6142_();
    }

    protected float m_7566_() {
        return (float)this.m_21133_(Attributes.f_22281_);
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33631_) {
        if (this.m_33633_()) {
            return SoundEvents.f_12468_;
        }
        return SoundEvents.f_12386_;
    }

    @Override
    protected SoundEvent m_5592_() {
        if (this.m_33633_()) {
            return SoundEvents.f_12467_;
        }
        return SoundEvents.f_12385_;
    }

    protected SoundEvent m_7905_() {
        if (this.m_33633_()) {
            return SoundEvents.f_12470_;
        }
        return SoundEvents.f_12388_;
    }

    public static boolean m_219112_(EntityType<Slime> p_219113_, LevelAccessor p_219114_, MobSpawnType p_219115_, BlockPos p_219116_, RandomSource p_219117_) {
        if (p_219114_.m_46791_() != Difficulty.PEACEFUL) {
            boolean $$6;
            if (p_219114_.m_204166_(p_219116_).m_203656_(BiomeTags.f_215815_) && p_219116_.m_123342_() > 50 && p_219116_.m_123342_() < 70 && p_219117_.m_188501_() < 0.5f && p_219117_.m_188501_() < p_219114_.m_46940_() && p_219114_.m_46803_(p_219116_) <= p_219117_.m_188503_(8)) {
                return Slime.m_217057_(p_219113_, p_219114_, p_219115_, p_219116_, p_219117_);
            }
            if (!(p_219114_ instanceof WorldGenLevel)) {
                return false;
            }
            ChunkPos $$5 = new ChunkPos(p_219116_);
            boolean bl = $$6 = WorldgenRandom.m_224681_($$5.f_45578_, $$5.f_45579_, ((WorldGenLevel)p_219114_).m_7328_(), 987234911L).m_188503_(10) == 0;
            if (p_219117_.m_188503_(10) == 0 && $$6 && p_219116_.m_123342_() < 40) {
                return Slime.m_217057_(p_219113_, p_219114_, p_219115_, p_219116_, p_219117_);
            }
        }
        return false;
    }

    @Override
    protected float m_6121_() {
        return 0.4f * (float)this.m_33632_();
    }

    @Override
    public int m_8132_() {
        return 0;
    }

    protected boolean m_33634_() {
        return this.m_33632_() > 0;
    }

    @Override
    protected void m_6135_() {
        Vec3 $$0 = this.m_20184_();
        this.m_20334_($$0.f_82479_, this.m_6118_(), $$0.f_82481_);
        this.f_19812_ = true;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
        RandomSource $$5 = p_33601_.m_213780_();
        int $$6 = $$5.m_188503_(3);
        if ($$6 < 2 && $$5.m_188501_() < 0.5f * p_33602_.m_19057_()) {
            ++$$6;
        }
        int $$7 = 1 << $$6;
        this.m_7839_($$7, true);
        return super.m_6518_(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
    }

    float m_33642_() {
        float $$0 = this.m_33633_() ? 1.4f : 0.8f;
        return ((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f) * $$0;
    }

    protected SoundEvent m_7903_() {
        return this.m_33633_() ? SoundEvents.f_12469_ : SoundEvents.f_12387_;
    }

    @Override
    public EntityDimensions m_6972_(Pose p_33597_) {
        return super.m_6972_(p_33597_).m_20388_(0.255f * (float)this.m_33632_());
    }

    static class SlimeMoveControl
    extends MoveControl {
        private float f_33663_;
        private int f_33664_;
        private final Slime f_33665_;
        private boolean f_33666_;

        public SlimeMoveControl(Slime p_33668_) {
            super(p_33668_);
            this.f_33665_ = p_33668_;
            this.f_33663_ = 180.0f * p_33668_.m_146908_() / (float)Math.PI;
        }

        public void m_33672_(float p_33673_, boolean p_33674_) {
            this.f_33663_ = p_33673_;
            this.f_33666_ = p_33674_;
        }

        public void m_33670_(double p_33671_) {
            this.f_24978_ = p_33671_;
            this.f_24981_ = MoveControl.Operation.MOVE_TO;
        }

        @Override
        public void m_8126_() {
            this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), this.f_33663_, 90.0f));
            this.f_24974_.f_20885_ = this.f_24974_.m_146908_();
            this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
            if (this.f_24981_ != MoveControl.Operation.MOVE_TO) {
                this.f_24974_.m_21564_(0.0f);
                return;
            }
            this.f_24981_ = MoveControl.Operation.WAIT;
            if (this.f_24974_.m_20096_()) {
                this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
                if (this.f_33664_-- <= 0) {
                    this.f_33664_ = this.f_33665_.m_7549_();
                    if (this.f_33666_) {
                        this.f_33664_ /= 3;
                    }
                    this.f_33665_.m_21569_().m_24901_();
                    if (this.f_33665_.m_33634_()) {
                        this.f_33665_.m_5496_(this.f_33665_.m_7903_(), this.f_33665_.m_6121_(), this.f_33665_.m_33642_());
                    }
                } else {
                    this.f_33665_.f_20900_ = 0.0f;
                    this.f_33665_.f_20902_ = 0.0f;
                    this.f_24974_.m_7910_(0.0f);
                }
            } else {
                this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
            }
        }
    }

    static class SlimeFloatGoal
    extends Goal {
        private final Slime f_33653_;

        public SlimeFloatGoal(Slime p_33655_) {
            this.f_33653_ = p_33655_;
            this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            p_33655_.m_21573_().m_7008_(true);
        }

        @Override
        public boolean m_8036_() {
            return (this.f_33653_.m_20069_() || this.f_33653_.m_20077_()) && this.f_33653_.m_21566_() instanceof SlimeMoveControl;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            if (this.f_33653_.m_217043_().m_188501_() < 0.8f) {
                this.f_33653_.m_21569_().m_24901_();
            }
            ((SlimeMoveControl)this.f_33653_.m_21566_()).m_33670_(1.2);
        }
    }

    static class SlimeAttackGoal
    extends Goal {
        private final Slime f_33645_;
        private int f_33646_;

        public SlimeAttackGoal(Slime p_33648_) {
            this.f_33645_ = p_33648_;
            this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            LivingEntity $$0 = this.f_33645_.m_5448_();
            if ($$0 == null) {
                return false;
            }
            if (!this.f_33645_.m_6779_($$0)) {
                return false;
            }
            return this.f_33645_.m_21566_() instanceof SlimeMoveControl;
        }

        @Override
        public void m_8056_() {
            this.f_33646_ = SlimeAttackGoal.m_186073_(300);
            super.m_8056_();
        }

        @Override
        public boolean m_8045_() {
            LivingEntity $$0 = this.f_33645_.m_5448_();
            if ($$0 == null) {
                return false;
            }
            if (!this.f_33645_.m_6779_($$0)) {
                return false;
            }
            return --this.f_33646_ > 0;
        }

        @Override
        public boolean m_183429_() {
            return true;
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = this.f_33645_.m_5448_();
            if ($$0 != null) {
                this.f_33645_.m_21391_($$0, 10.0f, 10.0f);
            }
            ((SlimeMoveControl)this.f_33645_.m_21566_()).m_33672_(this.f_33645_.m_146908_(), this.f_33645_.m_7483_());
        }
    }

    static class SlimeRandomDirectionGoal
    extends Goal {
        private final Slime f_33675_;
        private float f_33676_;
        private int f_33677_;

        public SlimeRandomDirectionGoal(Slime p_33679_) {
            this.f_33675_ = p_33679_;
            this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            return this.f_33675_.m_5448_() == null && (this.f_33675_.f_19861_ || this.f_33675_.m_20069_() || this.f_33675_.m_20077_() || this.f_33675_.m_21023_(MobEffects.f_19620_)) && this.f_33675_.m_21566_() instanceof SlimeMoveControl;
        }

        @Override
        public void m_8037_() {
            if (--this.f_33677_ <= 0) {
                this.f_33677_ = this.m_183277_(40 + this.f_33675_.m_217043_().m_188503_(60));
                this.f_33676_ = this.f_33675_.m_217043_().m_188503_(360);
            }
            ((SlimeMoveControl)this.f_33675_.m_21566_()).m_33672_(this.f_33676_, false);
        }
    }

    static class SlimeKeepOnJumpingGoal
    extends Goal {
        private final Slime f_33658_;

        public SlimeKeepOnJumpingGoal(Slime p_33660_) {
            this.f_33658_ = p_33660_;
            this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            return !this.f_33658_.m_20159_();
        }

        @Override
        public void m_8037_() {
            ((SlimeMoveControl)this.f_33658_.m_21566_()).m_33670_(1.0);
        }
    }
}

