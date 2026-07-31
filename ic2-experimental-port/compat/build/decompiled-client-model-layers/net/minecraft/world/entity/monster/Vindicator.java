/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Vindicator
extends AbstractIllager {
    private static final String f_149867_ = "Johnny";
    static final Predicate<Difficulty> f_34070_ = p_34082_ -> p_34082_ == Difficulty.NORMAL || p_34082_ == Difficulty.HARD;
    boolean f_34071_;

    public Vindicator(EntityType<? extends Vindicator> p_34074_, Level p_34075_) {
        super((EntityType<? extends AbstractIllager>)p_34074_, p_34075_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new VindicatorBreakDoorGoal(this));
        this.f_21345_.m_25352_(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.f_21345_.m_25352_(3, new Raider.HoldGroundAttackGoal(this, this, 10.0f));
        this.f_21345_.m_25352_(4, new VindicatorMeleeAttackGoal(this));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
        this.f_21346_.m_25352_(4, new VindicatorJohnnyAttackGoal(this));
        this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6));
        this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
    }

    @Override
    protected void m_8024_() {
        if (!this.m_21525_() && GoalUtils.m_26894_(this)) {
            boolean $$0 = ((ServerLevel)this.f_19853_).m_8843_(this.m_20183_());
            ((GroundPathNavigation)this.m_21573_()).m_26477_($$0);
        }
        super.m_8024_();
    }

    public static AttributeSupplier.Builder m_34104_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.35f).m_22268_(Attributes.f_22277_, 12.0).m_22268_(Attributes.f_22276_, 24.0).m_22268_(Attributes.f_22281_, 5.0);
    }

    @Override
    public void m_7380_(CompoundTag p_34100_) {
        super.m_7380_(p_34100_);
        if (this.f_34071_) {
            p_34100_.m_128379_(f_149867_, true);
        }
    }

    @Override
    public AbstractIllager.IllagerArmPose m_6768_() {
        if (this.m_5912_()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        }
        if (this.m_37888_()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    @Override
    public void m_7378_(CompoundTag p_34094_) {
        super.m_7378_(p_34094_);
        if (p_34094_.m_128425_(f_149867_, 99)) {
            this.f_34071_ = p_34094_.m_128471_(f_149867_);
        }
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_12577_;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_34088_, DifficultyInstance p_34089_, MobSpawnType p_34090_, @Nullable SpawnGroupData p_34091_, @Nullable CompoundTag p_34092_) {
        SpawnGroupData $$5 = super.m_6518_(p_34088_, p_34089_, p_34090_, p_34091_, p_34092_);
        ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
        RandomSource $$6 = p_34088_.m_213780_();
        this.m_213945_($$6, p_34089_);
        this.m_213946_($$6, p_34089_);
        return $$5;
    }

    @Override
    protected void m_213945_(RandomSource p_219149_, DifficultyInstance p_219150_) {
        if (this.m_37885_() == null) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42386_));
        }
    }

    @Override
    public boolean m_7307_(Entity p_34110_) {
        if (super.m_7307_(p_34110_)) {
            return true;
        }
        if (p_34110_ instanceof LivingEntity && ((LivingEntity)p_34110_).m_6336_() == MobType.f_21643_) {
            return this.m_5647_() == null && p_34110_.m_5647_() == null;
        }
        return false;
    }

    @Override
    public void m_6593_(@Nullable Component p_34096_) {
        super.m_6593_(p_34096_);
        if (!this.f_34071_ && p_34096_ != null && p_34096_.getString().equals(f_149867_)) {
            this.f_34071_ = true;
        }
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12576_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12578_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34103_) {
        return SoundEvents.f_12579_;
    }

    @Override
    public void m_7895_(int p_34079_, boolean p_34080_) {
        boolean $$5;
        ItemStack $$2 = new ItemStack(Items.f_42386_);
        Raid $$3 = this.m_37885_();
        int $$4 = 1;
        if (p_34079_ > $$3.m_37724_(Difficulty.NORMAL)) {
            $$4 = 2;
        }
        boolean bl = $$5 = this.f_19796_.m_188501_() <= $$3.m_37783_();
        if ($$5) {
            HashMap $$6 = Maps.newHashMap();
            $$6.put(Enchantments.f_44977_, $$4);
            EnchantmentHelper.m_44865_($$6, $$2);
        }
        this.m_8061_(EquipmentSlot.MAINHAND, $$2);
    }

    static class VindicatorBreakDoorGoal
    extends BreakDoorGoal {
        public VindicatorBreakDoorGoal(Mob p_34112_) {
            super(p_34112_, 6, f_34070_);
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8045_() {
            Vindicator $$0 = (Vindicator)this.f_25189_;
            return $$0.m_37886_() && super.m_8045_();
        }

        @Override
        public boolean m_8036_() {
            Vindicator $$0 = (Vindicator)this.f_25189_;
            return $$0.m_37886_() && $$0.f_19796_.m_188503_(VindicatorBreakDoorGoal.m_186073_(10)) == 0 && super.m_8036_();
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            this.f_25189_.m_21310_(0);
        }
    }

    class VindicatorMeleeAttackGoal
    extends MeleeAttackGoal {
        public VindicatorMeleeAttackGoal(Vindicator p_34123_) {
            super(p_34123_, 1.0, false);
        }

        @Override
        protected double m_6639_(LivingEntity p_34125_) {
            if (this.f_25540_.m_20202_() instanceof Ravager) {
                float $$1 = this.f_25540_.m_20202_().m_20205_() - 0.1f;
                return $$1 * 2.0f * ($$1 * 2.0f) + p_34125_.m_20205_();
            }
            return super.m_6639_(p_34125_);
        }
    }

    static class VindicatorJohnnyAttackGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        public VindicatorJohnnyAttackGoal(Vindicator p_34117_) {
            super(p_34117_, LivingEntity.class, 0, true, true, LivingEntity::m_5789_);
        }

        @Override
        public boolean m_8036_() {
            return ((Vindicator)this.f_26135_).f_34071_ && super.m_8036_();
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            this.f_26135_.m_21310_(0);
        }
    }
}

