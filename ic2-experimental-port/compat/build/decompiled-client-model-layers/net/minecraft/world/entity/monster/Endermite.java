/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class Endermite
extends Monster {
    private static final int f_149695_ = 2400;
    private int f_32588_;

    public Endermite(EntityType<? extends Endermite> p_32591_, Level p_32592_) {
        super((EntityType<? extends Monster>)p_32591_, p_32592_);
        this.f_21364_ = 3;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new ClimbOnTopOfPowderSnowGoal(this, this.f_19853_));
        this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
    }

    @Override
    protected float m_6431_(Pose p_32604_, EntityDimensions p_32605_) {
        return 0.13f;
    }

    public static AttributeSupplier.Builder m_32619_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 8.0).m_22268_(Attributes.f_22279_, 0.25).m_22268_(Attributes.f_22281_, 2.0);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11853_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_32615_) {
        return SoundEvents.f_11855_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11854_;
    }

    @Override
    protected void m_7355_(BlockPos p_32607_, BlockState p_32608_) {
        this.m_5496_(SoundEvents.f_11856_, 0.15f, 1.0f);
    }

    @Override
    public void m_7378_(CompoundTag p_32595_) {
        super.m_7378_(p_32595_);
        this.f_32588_ = p_32595_.m_128451_("Lifetime");
    }

    @Override
    public void m_7380_(CompoundTag p_32610_) {
        super.m_7380_(p_32610_);
        p_32610_.m_128405_("Lifetime", this.f_32588_);
    }

    @Override
    public void m_8119_() {
        this.f_20883_ = this.m_146908_();
        super.m_8119_();
    }

    @Override
    public void m_5618_(float p_32621_) {
        this.m_146922_(p_32621_);
        super.m_5618_(p_32621_);
    }

    @Override
    public double m_6049_() {
        return 0.1;
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (this.f_19853_.f_46443_) {
            for (int $$0 = 0; $$0 < 2; ++$$0) {
                this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), (this.f_19796_.m_188500_() - 0.5) * 2.0, -this.f_19796_.m_188500_(), (this.f_19796_.m_188500_() - 0.5) * 2.0);
            }
        } else {
            if (!this.m_21532_()) {
                ++this.f_32588_;
            }
            if (this.f_32588_ >= 2400) {
                this.m_146870_();
            }
        }
    }

    public static boolean m_218968_(EntityType<Endermite> p_218969_, LevelAccessor p_218970_, MobSpawnType p_218971_, BlockPos p_218972_, RandomSource p_218973_) {
        if (Endermite.m_219019_(p_218969_, p_218970_, p_218971_, p_218972_, p_218973_)) {
            Player $$5 = p_218970_.m_45924_((double)p_218972_.m_123341_() + 0.5, (double)p_218972_.m_123342_() + 0.5, (double)p_218972_.m_123343_() + 0.5, 5.0, true);
            return $$5 == null;
        }
        return false;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21642_;
    }
}

