/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSkeleton
extends Monster
implements RangedAttackMob {
    private final RangedBowAttackGoal<AbstractSkeleton> f_32130_ = new RangedBowAttackGoal<AbstractSkeleton>(this, 1.0, 20, 15.0f);
    private final MeleeAttackGoal f_32131_ = new MeleeAttackGoal(this, 1.2, false){

        @Override
        public void m_8041_() {
            super.m_8041_();
            AbstractSkeleton.this.m_21561_(false);
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            AbstractSkeleton.this.m_21561_(true);
        }
    };

    protected AbstractSkeleton(EntityType<? extends AbstractSkeleton> p_32133_, Level p_32134_) {
        super((EntityType<? extends Monster>)p_32133_, p_32134_);
        this.m_32164_();
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(2, new RestrictSunGoal(this));
        this.f_21345_.m_25352_(3, new FleeSunGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new AvoidEntityGoal<Wolf>(this, Wolf.class, 6.0f, 1.0, 1.2));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<Turtle>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
    }

    public static AttributeSupplier.Builder m_32166_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    protected void m_7355_(BlockPos p_32159_, BlockState p_32160_) {
        this.m_5496_(this.m_7878_(), 0.15f, 1.0f);
    }

    abstract SoundEvent m_7878_();

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    public void m_8107_() {
        boolean $$0 = this.m_21527_();
        if ($$0) {
            ItemStack $$1 = this.m_6844_(EquipmentSlot.HEAD);
            if (!$$1.m_41619_()) {
                if ($$1.m_41763_()) {
                    $$1.m_41721_($$1.m_41773_() + this.f_19796_.m_188503_(2));
                    if ($$1.m_41773_() >= $$1.m_41776_()) {
                        this.m_21166_(EquipmentSlot.HEAD);
                        this.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
                    }
                }
                $$0 = false;
            }
            if ($$0) {
                this.m_20254_(8);
            }
        }
        super.m_8107_();
    }

    @Override
    public void m_6083_() {
        super.m_6083_();
        if (this.m_20202_() instanceof PathfinderMob) {
            PathfinderMob $$0 = (PathfinderMob)this.m_20202_();
            this.f_20883_ = $$0.f_20883_;
        }
    }

    @Override
    protected void m_213945_(RandomSource p_218949_, DifficultyInstance p_218950_) {
        super.m_213945_(p_218949_, p_218950_);
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42411_));
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ = super.m_6518_(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        RandomSource $$5 = p_32146_.m_213780_();
        this.m_213945_($$5, p_32147_);
        this.m_213946_($$5, p_32147_);
        this.m_32164_();
        this.m_21553_($$5.m_188501_() < 0.55f * p_32147_.m_19057_());
        if (this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            LocalDate $$6 = LocalDate.now();
            int $$7 = $$6.get(ChronoField.DAY_OF_MONTH);
            int $$8 = $$6.get(ChronoField.MONTH_OF_YEAR);
            if ($$8 == 10 && $$7 == 31 && $$5.m_188501_() < 0.25f) {
                this.m_8061_(EquipmentSlot.HEAD, new ItemStack($$5.m_188501_() < 0.1f ? Blocks.f_50144_ : Blocks.f_50143_));
                this.f_21348_[EquipmentSlot.HEAD.m_20749_()] = 0.0f;
            }
        }
        return p_32149_;
    }

    public void m_32164_() {
        if (this.f_19853_ == null || this.f_19853_.f_46443_) {
            return;
        }
        this.f_21345_.m_25363_(this.f_32131_);
        this.f_21345_.m_25363_(this.f_32130_);
        ItemStack $$0 = this.m_21120_(ProjectileUtil.m_37297_(this, Items.f_42411_));
        if ($$0.m_150930_(Items.f_42411_)) {
            int $$1 = 20;
            if (this.f_19853_.m_46791_() != Difficulty.HARD) {
                $$1 = 40;
            }
            this.f_32130_.m_25797_($$1);
            this.f_21345_.m_25352_(4, this.f_32130_);
        } else {
            this.f_21345_.m_25352_(4, this.f_32131_);
        }
    }

    @Override
    public void m_6504_(LivingEntity p_32141_, float p_32142_) {
        ItemStack $$2 = this.m_6298_(this.m_21120_(ProjectileUtil.m_37297_(this, Items.f_42411_)));
        AbstractArrow $$3 = this.m_7932_($$2, p_32142_);
        double $$4 = p_32141_.m_20185_() - this.m_20185_();
        double $$5 = p_32141_.m_20227_(0.3333333333333333) - $$3.m_20186_();
        double $$6 = p_32141_.m_20189_() - this.m_20189_();
        double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6);
        $$3.m_6686_($$4, $$5 + $$7 * (double)0.2f, $$6, 1.6f, 14 - this.f_19853_.m_46791_().m_19028_() * 4);
        this.m_5496_(SoundEvents.f_12382_, 1.0f, 1.0f / (this.m_217043_().m_188501_() * 0.4f + 0.8f));
        this.f_19853_.m_7967_($$3);
    }

    protected AbstractArrow m_7932_(ItemStack p_32156_, float p_32157_) {
        return ProjectileUtil.m_37300_(this, p_32156_, p_32157_);
    }

    @Override
    public boolean m_5886_(ProjectileWeaponItem p_32144_) {
        return p_32144_ == Items.f_42411_;
    }

    @Override
    public void m_7378_(CompoundTag p_32152_) {
        super.m_7378_(p_32152_);
        this.m_32164_();
    }

    @Override
    public void m_8061_(EquipmentSlot p_32138_, ItemStack p_32139_) {
        super.m_8061_(p_32138_, p_32139_);
        if (!this.f_19853_.f_46443_) {
            this.m_32164_();
        }
    }

    @Override
    protected float m_6431_(Pose p_32154_, EntityDimensions p_32155_) {
        return 1.74f;
    }

    @Override
    public double m_6049_() {
        return -0.6;
    }

    public boolean m_142548_() {
        return this.m_146890_();
    }
}

