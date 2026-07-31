/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Silverfish
extends Monster {
    @Nullable
    private SilverfishWakeUpFriendsGoal f_33521_;

    public Silverfish(EntityType<? extends Silverfish> p_33523_, Level p_33524_) {
        super((EntityType<? extends Monster>)p_33523_, p_33524_);
    }

    @Override
    protected void m_8099_() {
        this.f_33521_ = new SilverfishWakeUpFriendsGoal(this);
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new ClimbOnTopOfPowderSnowGoal(this, this.f_19853_));
        this.f_21345_.m_25352_(3, this.f_33521_);
        this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(5, new SilverfishMergeWithStoneGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
    }

    @Override
    public double m_6049_() {
        return 0.1;
    }

    @Override
    protected float m_6431_(Pose p_33540_, EntityDimensions p_33541_) {
        return 0.13f;
    }

    public static AttributeSupplier.Builder m_33551_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 8.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22281_, 1.0);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12419_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33549_) {
        return SoundEvents.f_12421_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12420_;
    }

    @Override
    protected void m_7355_(BlockPos p_33543_, BlockState p_33544_) {
        this.m_5496_(SoundEvents.f_12422_, 0.15f, 1.0f);
    }

    @Override
    public boolean m_6469_(DamageSource p_33527_, float p_33528_) {
        if (this.m_6673_(p_33527_)) {
            return false;
        }
        if ((p_33527_ instanceof EntityDamageSource || p_33527_ == DamageSource.f_19319_) && this.f_33521_ != null) {
            this.f_33521_.m_33568_();
        }
        return super.m_6469_(p_33527_, p_33528_);
    }

    @Override
    public void m_8119_() {
        this.f_20883_ = this.m_146908_();
        super.m_8119_();
    }

    @Override
    public void m_5618_(float p_33553_) {
        this.m_146922_(p_33553_);
        super.m_5618_(p_33553_);
    }

    @Override
    public float m_5610_(BlockPos p_33530_, LevelReader p_33531_) {
        if (InfestedBlock.m_54195_(p_33531_.m_8055_(p_33530_.m_7495_()))) {
            return 10.0f;
        }
        return super.m_5610_(p_33530_, p_33531_);
    }

    public static boolean m_219076_(EntityType<Silverfish> p_219077_, LevelAccessor p_219078_, MobSpawnType p_219079_, BlockPos p_219080_, RandomSource p_219081_) {
        if (Silverfish.m_219019_(p_219077_, p_219078_, p_219079_, p_219080_, p_219081_)) {
            Player $$5 = p_219078_.m_45924_((double)p_219080_.m_123341_() + 0.5, (double)p_219080_.m_123342_() + 0.5, (double)p_219080_.m_123343_() + 0.5, 5.0, true);
            return $$5 == null;
        }
        return false;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21642_;
    }

    static class SilverfishWakeUpFriendsGoal
    extends Goal {
        private final Silverfish f_33562_;
        private int f_33563_;

        public SilverfishWakeUpFriendsGoal(Silverfish p_33565_) {
            this.f_33562_ = p_33565_;
        }

        public void m_33568_() {
            if (this.f_33563_ == 0) {
                this.f_33563_ = this.m_183277_(20);
            }
        }

        @Override
        public boolean m_8036_() {
            return this.f_33563_ > 0;
        }

        @Override
        public void m_8037_() {
            --this.f_33563_;
            if (this.f_33563_ <= 0) {
                Level $$0 = this.f_33562_.f_19853_;
                RandomSource $$1 = this.f_33562_.m_217043_();
                BlockPos $$2 = this.f_33562_.m_20183_();
                int $$3 = 0;
                block0: while ($$3 <= 5 && $$3 >= -5) {
                    int $$4 = 0;
                    while ($$4 <= 10 && $$4 >= -10) {
                        int $$5 = 0;
                        while ($$5 <= 10 && $$5 >= -10) {
                            BlockPos $$6 = $$2.m_7918_($$4, $$3, $$5);
                            BlockState $$7 = $$0.m_8055_($$6);
                            Block $$8 = $$7.m_60734_();
                            if ($$8 instanceof InfestedBlock) {
                                if ($$0.m_46469_().m_46207_(GameRules.f_46132_)) {
                                    $$0.m_46953_($$6, true, this.f_33562_);
                                } else {
                                    $$0.m_7731_($$6, ((InfestedBlock)$$8).m_153432_($$0.m_8055_($$6)), 3);
                                }
                                if ($$1.m_188499_()) break block0;
                            }
                            $$5 = ($$5 <= 0 ? 1 : 0) - $$5;
                        }
                        $$4 = ($$4 <= 0 ? 1 : 0) - $$4;
                    }
                    $$3 = ($$3 <= 0 ? 1 : 0) - $$3;
                }
            }
        }
    }

    static class SilverfishMergeWithStoneGoal
    extends RandomStrollGoal {
        @Nullable
        private Direction f_33555_;
        private boolean f_33556_;

        public SilverfishMergeWithStoneGoal(Silverfish p_33558_) {
            super(p_33558_, 1.0, 10);
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            if (this.f_25725_.m_5448_() != null) {
                return false;
            }
            if (!this.f_25725_.m_21573_().m_26571_()) {
                return false;
            }
            RandomSource $$0 = this.f_25725_.m_217043_();
            if (this.f_25725_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) && $$0.m_188503_(SilverfishMergeWithStoneGoal.m_186073_(10)) == 0) {
                this.f_33555_ = Direction.m_235672_($$0);
                BlockPos $$1 = new BlockPos(this.f_25725_.m_20185_(), this.f_25725_.m_20186_() + 0.5, this.f_25725_.m_20189_()).m_121945_(this.f_33555_);
                BlockState $$2 = this.f_25725_.f_19853_.m_8055_($$1);
                if (InfestedBlock.m_54195_($$2)) {
                    this.f_33556_ = true;
                    return true;
                }
            }
            this.f_33556_ = false;
            return super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            if (this.f_33556_) {
                return false;
            }
            return super.m_8045_();
        }

        @Override
        public void m_8056_() {
            if (!this.f_33556_) {
                super.m_8056_();
                return;
            }
            Level $$0 = this.f_25725_.f_19853_;
            BlockPos $$1 = new BlockPos(this.f_25725_.m_20185_(), this.f_25725_.m_20186_() + 0.5, this.f_25725_.m_20189_()).m_121945_(this.f_33555_);
            BlockState $$2 = $$0.m_8055_($$1);
            if (InfestedBlock.m_54195_($$2)) {
                $$0.m_7731_($$1, InfestedBlock.m_153430_($$2), 3);
                this.f_25725_.m_21373_();
                this.f_25725_.m_146870_();
            }
        }
    }
}

