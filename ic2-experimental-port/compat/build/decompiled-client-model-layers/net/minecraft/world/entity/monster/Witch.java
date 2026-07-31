/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableWitchTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestHealableRaiderTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Witch
extends Raider
implements RangedAttackMob {
    private static final UUID f_34126_ = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier f_34127_ = new AttributeModifier(f_34126_, "Drinking speed penalty", -0.25, AttributeModifier.Operation.ADDITION);
    private static final EntityDataAccessor<Boolean> f_34128_ = SynchedEntityData.m_135353_(Witch.class, EntityDataSerializers.f_135035_);
    private int f_34129_;
    private NearestHealableRaiderTargetGoal<Raider> f_34130_;
    private NearestAttackableWitchTargetGoal<Player> f_34131_;

    public Witch(EntityType<? extends Witch> p_34134_, Level p_34135_) {
        super((EntityType<? extends Raider>)p_34134_, p_34135_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_34130_ = new NearestHealableRaiderTargetGoal<Raider>(this, Raider.class, true, p_34159_ -> p_34159_ != null && this.m_37886_() && p_34159_.m_6095_() != EntityType.f_20495_);
        this.f_34131_ = new NearestAttackableWitchTargetGoal<Player>(this, Player.class, 10, true, false, null);
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 1.0, 60, 10.0f));
        this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class));
        this.f_21346_.m_25352_(2, this.f_34130_);
        this.f_21346_.m_25352_(3, this.f_34131_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.m_20088_().m_135372_(f_34128_, false);
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12548_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34154_) {
        return SoundEvents.f_12552_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12550_;
    }

    public void m_34163_(boolean p_34164_) {
        this.m_20088_().m_135381_(f_34128_, p_34164_);
    }

    public boolean m_34161_() {
        return this.m_20088_().m_135370_(f_34128_);
    }

    public static AttributeSupplier.Builder m_34155_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 26.0).m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    public void m_8107_() {
        if (!this.f_19853_.f_46443_ && this.m_6084_()) {
            this.f_34130_.m_26094_();
            if (this.f_34130_.m_26093_() <= 0) {
                this.f_34131_.m_26083_(true);
            } else {
                this.f_34131_.m_26083_(false);
            }
            if (this.m_34161_()) {
                if (this.f_34129_-- <= 0) {
                    List<MobEffectInstance> $$1;
                    this.m_34163_(false);
                    ItemStack $$0 = this.m_21205_();
                    this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                    if ($$0.m_150930_(Items.f_42589_) && ($$1 = PotionUtils.m_43547_($$0)) != null) {
                        for (MobEffectInstance $$2 : $$1) {
                            this.m_7292_(new MobEffectInstance($$2));
                        }
                    }
                    this.m_21051_(Attributes.f_22279_).m_22130_(f_34127_);
                }
            } else {
                Potion $$3 = null;
                if (this.f_19796_.m_188501_() < 0.15f && this.m_204029_(FluidTags.f_13131_) && !this.m_21023_(MobEffects.f_19608_)) {
                    $$3 = Potions.f_43621_;
                } else if (this.f_19796_.m_188501_() < 0.15f && (this.m_6060_() || this.m_21225_() != null && this.m_21225_().m_19384_()) && !this.m_21023_(MobEffects.f_19607_)) {
                    $$3 = Potions.f_43610_;
                } else if (this.f_19796_.m_188501_() < 0.05f && this.m_21223_() < this.m_21233_()) {
                    $$3 = Potions.f_43623_;
                } else if (this.f_19796_.m_188501_() < 0.5f && this.m_5448_() != null && !this.m_21023_(MobEffects.f_19596_) && this.m_5448_().m_20280_(this) > 121.0) {
                    $$3 = Potions.f_43612_;
                }
                if ($$3 != null) {
                    this.m_8061_(EquipmentSlot.MAINHAND, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), $$3));
                    this.f_34129_ = this.m_21205_().m_41779_();
                    this.m_34163_(true);
                    if (!this.m_20067_()) {
                        this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12551_, this.m_5720_(), 1.0f, 0.8f + this.f_19796_.m_188501_() * 0.4f);
                    }
                    AttributeInstance $$4 = this.m_21051_(Attributes.f_22279_);
                    $$4.m_22130_(f_34127_);
                    $$4.m_22118_(f_34127_);
                }
            }
            if (this.f_19796_.m_188501_() < 7.5E-4f) {
                this.f_19853_.m_7605_(this, (byte)15);
            }
        }
        super.m_8107_();
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_12549_;
    }

    @Override
    public void m_7822_(byte p_34138_) {
        if (p_34138_ == 15) {
            for (int $$1 = 0; $$1 < this.f_19796_.m_188503_(35) + 10; ++$$1) {
                this.f_19853_.m_7106_(ParticleTypes.f_123771_, this.m_20185_() + this.f_19796_.m_188583_() * (double)0.13f, this.m_20191_().f_82292_ + 0.5 + this.f_19796_.m_188583_() * (double)0.13f, this.m_20189_() + this.f_19796_.m_188583_() * (double)0.13f, 0.0, 0.0, 0.0);
            }
        } else {
            super.m_7822_(p_34138_);
        }
    }

    @Override
    protected float m_6515_(DamageSource p_34149_, float p_34150_) {
        p_34150_ = super.m_6515_(p_34149_, p_34150_);
        if (p_34149_.m_7639_() == this) {
            p_34150_ = 0.0f;
        }
        if (p_34149_.m_19387_()) {
            p_34150_ *= 0.15f;
        }
        return p_34150_;
    }

    @Override
    public void m_6504_(LivingEntity p_34143_, float p_34144_) {
        if (this.m_34161_()) {
            return;
        }
        Vec3 $$2 = p_34143_.m_20184_();
        double $$3 = p_34143_.m_20185_() + $$2.f_82479_ - this.m_20185_();
        double $$4 = p_34143_.m_20188_() - (double)1.1f - this.m_20186_();
        double $$5 = p_34143_.m_20189_() + $$2.f_82481_ - this.m_20189_();
        double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
        Potion $$7 = Potions.f_43582_;
        if (p_34143_ instanceof Raider) {
            $$7 = p_34143_.m_21223_() <= 4.0f ? Potions.f_43623_ : Potions.f_43587_;
            this.m_6710_(null);
        } else if ($$6 >= 8.0 && !p_34143_.m_21023_(MobEffects.f_19597_)) {
            $$7 = Potions.f_43615_;
        } else if (p_34143_.m_21223_() >= 8.0f && !p_34143_.m_21023_(MobEffects.f_19614_)) {
            $$7 = Potions.f_43584_;
        } else if ($$6 <= 3.0 && !p_34143_.m_21023_(MobEffects.f_19613_) && this.f_19796_.m_188501_() < 0.25f) {
            $$7 = Potions.f_43593_;
        }
        ThrownPotion $$8 = new ThrownPotion(this.f_19853_, this);
        $$8.m_37446_(PotionUtils.m_43549_(new ItemStack(Items.f_42736_), $$7));
        $$8.m_146926_($$8.m_146909_() - -20.0f);
        $$8.m_6686_($$3, $$4 + $$6 * 0.2, $$5, 0.75f, 8.0f);
        if (!this.m_20067_()) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12553_, this.m_5720_(), 1.0f, 0.8f + this.f_19796_.m_188501_() * 0.4f);
        }
        this.f_19853_.m_7967_($$8);
    }

    @Override
    protected float m_6431_(Pose p_34146_, EntityDimensions p_34147_) {
        return 1.62f;
    }

    @Override
    public void m_7895_(int p_34140_, boolean p_34141_) {
    }

    @Override
    public boolean m_7490_() {
        return false;
    }
}

