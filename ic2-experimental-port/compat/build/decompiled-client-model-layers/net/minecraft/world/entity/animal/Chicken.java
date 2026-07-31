/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Chicken
extends Animal {
    private static final Ingredient f_28233_ = Ingredient.m_43929_(Items.f_42404_, Items.f_42578_, Items.f_42577_, Items.f_42733_);
    public float f_28226_;
    public float f_28227_;
    public float f_28228_;
    public float f_28229_;
    public float f_28230_ = 1.0f;
    private float f_148873_ = 1.0f;
    public int f_28231_ = this.f_19796_.m_188503_(6000) + 6000;
    public boolean f_28232_;

    public Chicken(EntityType<? extends Chicken> p_28236_, Level p_28237_) {
        super((EntityType<? extends Animal>)p_28236_, p_28237_);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new PanicGoal(this, 1.4));
        this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new TemptGoal(this, 1.0, f_28233_, false));
        this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.1));
        this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected float m_6431_(Pose p_28251_, EntityDimensions p_28252_) {
        return this.m_6162_() ? p_28252_.f_20378_ * 0.85f : p_28252_.f_20378_ * 0.92f;
    }

    public static AttributeSupplier.Builder m_28263_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 4.0).m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        this.f_28229_ = this.f_28226_;
        this.f_28228_ = this.f_28227_;
        this.f_28227_ += (this.f_19861_ ? -1.0f : 4.0f) * 0.3f;
        this.f_28227_ = Mth.m_14036_(this.f_28227_, 0.0f, 1.0f);
        if (!this.f_19861_ && this.f_28230_ < 1.0f) {
            this.f_28230_ = 1.0f;
        }
        this.f_28230_ *= 0.9f;
        Vec3 $$0 = this.m_20184_();
        if (!this.f_19861_ && $$0.f_82480_ < 0.0) {
            this.m_20256_($$0.m_82542_(1.0, 0.6, 1.0));
        }
        this.f_28226_ += this.f_28230_ * 2.0f;
        if (!this.f_19853_.f_46443_ && this.m_6084_() && !this.m_6162_() && !this.m_28264_() && --this.f_28231_ <= 0) {
            this.m_5496_(SoundEvents.f_11752_, 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
            this.m_19998_(Items.f_42521_);
            this.m_146850_(GameEvent.f_157810_);
            this.f_28231_ = this.f_19796_.m_188503_(6000) + 6000;
        }
    }

    @Override
    protected boolean m_142039_() {
        return this.f_146794_ > this.f_148873_;
    }

    @Override
    protected void m_142043_() {
        this.f_148873_ = this.f_146794_ + this.f_28227_ / 2.0f;
    }

    @Override
    public boolean m_142535_(float p_148875_, float p_148876_, DamageSource p_148877_) {
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_11750_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_28262_) {
        return SoundEvents.f_11753_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11751_;
    }

    @Override
    protected void m_7355_(BlockPos p_28254_, BlockState p_28255_) {
        this.m_5496_(SoundEvents.f_11754_, 0.15f, 1.0f);
    }

    @Override
    public Chicken m_142606_(ServerLevel p_148884_, AgeableMob p_148885_) {
        return EntityType.f_20555_.m_20615_(p_148884_);
    }

    @Override
    public boolean m_6898_(ItemStack p_28271_) {
        return f_28233_.test(p_28271_);
    }

    @Override
    public int m_213860_() {
        if (this.m_28264_()) {
            return 10;
        }
        return super.m_213860_();
    }

    @Override
    public void m_7378_(CompoundTag p_28243_) {
        super.m_7378_(p_28243_);
        this.f_28232_ = p_28243_.m_128471_("IsChickenJockey");
        if (p_28243_.m_128441_("EggLayTime")) {
            this.f_28231_ = p_28243_.m_128451_("EggLayTime");
        }
    }

    @Override
    public void m_7380_(CompoundTag p_28257_) {
        super.m_7380_(p_28257_);
        p_28257_.m_128379_("IsChickenJockey", this.f_28232_);
        p_28257_.m_128405_("EggLayTime", this.f_28231_);
    }

    @Override
    public boolean m_6785_(double p_28266_) {
        return this.m_28264_();
    }

    @Override
    public void m_7332_(Entity p_28269_) {
        super.m_7332_(p_28269_);
        float $$1 = Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180));
        float $$2 = Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180));
        float $$3 = 0.1f;
        float $$4 = 0.0f;
        p_28269_.m_6034_(this.m_20185_() + (double)(0.1f * $$1), this.m_20227_(0.5) + p_28269_.m_6049_() + 0.0, this.m_20189_() - (double)(0.1f * $$2));
        if (p_28269_ instanceof LivingEntity) {
            ((LivingEntity)p_28269_).f_20883_ = this.f_20883_;
        }
    }

    public boolean m_28264_() {
        return this.f_28232_;
    }

    public void m_28273_(boolean p_28274_) {
        this.f_28232_ = p_28274_;
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }
}

