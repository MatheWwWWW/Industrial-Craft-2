/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Spider
extends Monster {
    private static final EntityDataAccessor<Byte> f_33783_ = SynchedEntityData.m_135353_(Spider.class, EntityDataSerializers.f_135027_);
    private static final float f_149853_ = 0.1f;

    public Spider(EntityType<? extends Spider> p_33786_, Level p_33787_) {
        super((EntityType<? extends Monster>)p_33786_, p_33787_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(3, new LeapAtTargetGoal(this, 0.4f));
        this.f_21345_.m_25352_(4, new SpiderAttackGoal(this));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
        this.f_21346_.m_25352_(2, new SpiderTargetGoal<Player>(this, Player.class));
        this.f_21346_.m_25352_(3, new SpiderTargetGoal<IronGolem>(this, IronGolem.class));
    }

    @Override
    public double m_6048_() {
        return this.m_20206_() * 0.5f;
    }

    @Override
    protected PathNavigation m_6037_(Level p_33802_) {
        return new WallClimberNavigation(this, p_33802_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33783_, (byte)0);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.f_19853_.f_46443_) {
            this.m_33819_(this.f_19862_);
        }
    }

    public static AttributeSupplier.Builder m_33815_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 16.0).m_22268_(Attributes.f_22279_, 0.3f);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12432_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33814_) {
        return SoundEvents.f_12434_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12433_;
    }

    @Override
    protected void m_7355_(BlockPos p_33804_, BlockState p_33805_) {
        this.m_5496_(SoundEvents.f_12435_, 0.15f, 1.0f);
    }

    @Override
    public boolean m_6147_() {
        return this.m_33816_();
    }

    @Override
    public void m_7601_(BlockState p_33796_, Vec3 p_33797_) {
        if (!p_33796_.m_60713_(Blocks.f_50033_)) {
            super.m_7601_(p_33796_, p_33797_);
        }
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21642_;
    }

    @Override
    public boolean m_7301_(MobEffectInstance p_33809_) {
        if (p_33809_.m_19544_() == MobEffects.f_19614_) {
            return false;
        }
        return super.m_7301_(p_33809_);
    }

    public boolean m_33816_() {
        return (this.f_19804_.m_135370_(f_33783_) & 1) != 0;
    }

    public void m_33819_(boolean p_33820_) {
        byte $$1 = this.f_19804_.m_135370_(f_33783_);
        $$1 = p_33820_ ? (byte)($$1 | 1) : (byte)($$1 & 0xFFFFFFFE);
        this.f_19804_.m_135381_(f_33783_, $$1);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33790_, DifficultyInstance p_33791_, MobSpawnType p_33792_, @Nullable SpawnGroupData p_33793_, @Nullable CompoundTag p_33794_) {
        MobEffect $$7;
        p_33793_ = super.m_6518_(p_33790_, p_33791_, p_33792_, p_33793_, p_33794_);
        RandomSource $$5 = p_33790_.m_213780_();
        if ($$5.m_188503_(100) == 0) {
            Skeleton $$6 = EntityType.f_20524_.m_20615_(this.f_19853_);
            $$6.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0f);
            $$6.m_6518_(p_33790_, p_33791_, p_33792_, null, null);
            $$6.m_20329_(this);
        }
        if (p_33793_ == null) {
            p_33793_ = new SpiderEffectsGroupData();
            if (p_33790_.m_46791_() == Difficulty.HARD && $$5.m_188501_() < 0.1f * p_33791_.m_19057_()) {
                ((SpiderEffectsGroupData)p_33793_).m_219118_($$5);
            }
        }
        if (p_33793_ instanceof SpiderEffectsGroupData && ($$7 = ((SpiderEffectsGroupData)p_33793_).f_33827_) != null) {
            this.m_7292_(new MobEffectInstance($$7, Integer.MAX_VALUE));
        }
        return p_33793_;
    }

    @Override
    protected float m_6431_(Pose p_33799_, EntityDimensions p_33800_) {
        return 0.65f;
    }

    static class SpiderAttackGoal
    extends MeleeAttackGoal {
        public SpiderAttackGoal(Spider p_33822_) {
            super(p_33822_, 1.0, true);
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && !this.f_25540_.m_20160_();
        }

        @Override
        public boolean m_8045_() {
            float $$0 = this.f_25540_.m_213856_();
            if ($$0 >= 0.5f && this.f_25540_.m_217043_().m_188503_(100) == 0) {
                this.f_25540_.m_6710_(null);
                return false;
            }
            return super.m_8045_();
        }

        @Override
        protected double m_6639_(LivingEntity p_33825_) {
            return 4.0f + p_33825_.m_20205_();
        }
    }

    static class SpiderTargetGoal<T extends LivingEntity>
    extends NearestAttackableTargetGoal<T> {
        public SpiderTargetGoal(Spider p_33832_, Class<T> p_33833_) {
            super((Mob)p_33832_, p_33833_, true);
        }

        @Override
        public boolean m_8036_() {
            float $$0 = this.f_26135_.m_213856_();
            if ($$0 >= 0.5f) {
                return false;
            }
            return super.m_8036_();
        }
    }

    public static class SpiderEffectsGroupData
    implements SpawnGroupData {
        @Nullable
        public MobEffect f_33827_;

        public void m_219118_(RandomSource p_219119_) {
            int $$1 = p_219119_.m_188503_(5);
            if ($$1 <= 1) {
                this.f_33827_ = MobEffects.f_19596_;
            } else if ($$1 <= 2) {
                this.f_33827_ = MobEffects.f_19600_;
            } else if ($$1 <= 3) {
                this.f_33827_ = MobEffects.f_19605_;
            } else if ($$1 <= 4) {
                this.f_33827_ = MobEffects.f_19609_;
            }
        }
    }
}

