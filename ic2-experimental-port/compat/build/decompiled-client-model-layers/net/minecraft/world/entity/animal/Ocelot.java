/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Ocelot
extends Animal {
    public static final double f_148945_ = 0.6;
    public static final double f_148946_ = 0.8;
    public static final double f_148947_ = 1.33;
    private static final Ingredient f_28981_ = Ingredient.m_43929_(Items.f_42526_, Items.f_42527_);
    private static final EntityDataAccessor<Boolean> f_28982_ = SynchedEntityData.m_135353_(Ocelot.class, EntityDataSerializers.f_135035_);
    @Nullable
    private OcelotAvoidEntityGoal<Player> f_28983_;
    @Nullable
    private OcelotTemptGoal f_28984_;

    public Ocelot(EntityType<? extends Ocelot> p_28987_, Level p_28988_) {
        super((EntityType<? extends Animal>)p_28987_, p_28988_);
        this.m_29037_();
    }

    boolean m_29038_() {
        return this.f_19804_.m_135370_(f_28982_);
    }

    private void m_29045_(boolean p_29046_) {
        this.f_19804_.m_135381_(f_28982_, p_29046_);
        this.m_29037_();
    }

    @Override
    public void m_7380_(CompoundTag p_29024_) {
        super.m_7380_(p_29024_);
        p_29024_.m_128379_("Trusting", this.m_29038_());
    }

    @Override
    public void m_7378_(CompoundTag p_29013_) {
        super.m_7378_(p_29013_);
        this.m_29045_(p_29013_.m_128471_("Trusting"));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_28982_, false);
    }

    @Override
    protected void m_8099_() {
        this.f_28984_ = new OcelotTemptGoal(this, 0.6, f_28981_, true);
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(3, this.f_28984_);
        this.f_21345_.m_25352_(7, new LeapAtTargetGoal(this, 0.3f));
        this.f_21345_.m_25352_(8, new OcelotAttackGoal(this));
        this.f_21345_.m_25352_(9, new BreedGoal(this, 0.8));
        this.f_21345_.m_25352_(10, new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8, 1.0000001E-5f));
        this.f_21345_.m_25352_(11, new LookAtPlayerGoal(this, Player.class, 10.0f));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Chicken>((Mob)this, Chicken.class, false));
        this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<Turtle>(this, Turtle.class, 10, false, false, Turtle.f_30122_));
    }

    @Override
    public void m_8024_() {
        if (this.m_21566_().m_24995_()) {
            double $$0 = this.m_21566_().m_24999_();
            if ($$0 == 0.6) {
                this.m_20124_(Pose.CROUCHING);
                this.m_6858_(false);
            } else if ($$0 == 1.33) {
                this.m_20124_(Pose.STANDING);
                this.m_6858_(true);
            } else {
                this.m_20124_(Pose.STANDING);
                this.m_6858_(false);
            }
        } else {
            this.m_20124_(Pose.STANDING);
            this.m_6858_(false);
        }
    }

    @Override
    public boolean m_6785_(double p_29041_) {
        return !this.m_29038_() && this.f_19797_ > 2400;
    }

    public static AttributeSupplier.Builder m_29036_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22281_, 3.0);
    }

    @Override
    public boolean m_142535_(float p_148949_, float p_148950_, DamageSource p_148951_) {
        return false;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12173_;
    }

    @Override
    public int m_8100_() {
        return 900;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29035_) {
        return SoundEvents.f_12172_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12174_;
    }

    private float m_29039_() {
        return (float)this.m_21133_(Attributes.f_22281_);
    }

    @Override
    public boolean m_7327_(Entity p_28990_) {
        return p_28990_.m_6469_(DamageSource.m_19370_(this), this.m_29039_());
    }

    @Override
    public InteractionResult m_6071_(Player p_29021_, InteractionHand p_29022_) {
        ItemStack $$2 = p_29021_.m_21120_(p_29022_);
        if ((this.f_28984_ == null || this.f_28984_.m_25955_()) && !this.m_29038_() && this.m_6898_($$2) && p_29021_.m_20280_(this) < 9.0) {
            this.m_142075_(p_29021_, p_29022_, $$2);
            if (!this.f_19853_.f_46443_) {
                if (this.f_19796_.m_188503_(3) == 0) {
                    this.m_29045_(true);
                    this.m_29047_(true);
                    this.f_19853_.m_7605_(this, (byte)41);
                } else {
                    this.m_29047_(false);
                    this.f_19853_.m_7605_(this, (byte)40);
                }
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_29021_, p_29022_);
    }

    @Override
    public void m_7822_(byte p_28995_) {
        if (p_28995_ == 41) {
            this.m_29047_(true);
        } else if (p_28995_ == 40) {
            this.m_29047_(false);
        } else {
            super.m_7822_(p_28995_);
        }
    }

    private void m_29047_(boolean p_29048_) {
        SimpleParticleType $$1 = ParticleTypes.f_123750_;
        if (!p_29048_) {
            $$1 = ParticleTypes.f_123762_;
        }
        for (int $$2 = 0; $$2 < 7; ++$$2) {
            double $$3 = this.f_19796_.m_188583_() * 0.02;
            double $$4 = this.f_19796_.m_188583_() * 0.02;
            double $$5 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_($$1, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), $$3, $$4, $$5);
        }
    }

    protected void m_29037_() {
        if (this.f_28983_ == null) {
            this.f_28983_ = new OcelotAvoidEntityGoal<Player>(this, Player.class, 16.0f, 0.8, 1.33);
        }
        this.f_21345_.m_25363_(this.f_28983_);
        if (!this.m_29038_()) {
            this.f_21345_.m_25352_(4, this.f_28983_);
        }
    }

    @Override
    public Ocelot m_142606_(ServerLevel p_148956_, AgeableMob p_148957_) {
        return EntityType.f_20505_.m_20615_(p_148956_);
    }

    @Override
    public boolean m_6898_(ItemStack p_29043_) {
        return f_28981_.test(p_29043_);
    }

    public static boolean m_218206_(EntityType<Ocelot> p_218207_, LevelAccessor p_218208_, MobSpawnType p_218209_, BlockPos p_218210_, RandomSource p_218211_) {
        return p_218211_.m_188503_(3) != 0;
    }

    @Override
    public boolean m_6914_(LevelReader p_29005_) {
        if (p_29005_.m_45784_(this) && !p_29005_.m_46855_(this.m_20191_())) {
            BlockPos $$1 = this.m_20183_();
            if ($$1.m_123342_() < p_29005_.m_5736_()) {
                return false;
            }
            BlockState $$2 = p_29005_.m_8055_($$1.m_7495_());
            if ($$2.m_60713_(Blocks.f_50440_) || $$2.m_204336_(BlockTags.f_13035_)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_29007_, DifficultyInstance p_29008_, MobSpawnType p_29009_, @Nullable SpawnGroupData p_29010_, @Nullable CompoundTag p_29011_) {
        if (p_29010_ == null) {
            p_29010_ = new AgeableMob.AgeableMobGroupData(1.0f);
        }
        return super.m_6518_(p_29007_, p_29008_, p_29009_, p_29010_, p_29011_);
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.5f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    @Override
    public boolean m_20161_() {
        return this.m_6047_() || super.m_20161_();
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    static class OcelotTemptGoal
    extends TemptGoal {
        private final Ocelot f_29058_;

        public OcelotTemptGoal(Ocelot p_29060_, double p_29061_, Ingredient p_29062_, boolean p_29063_) {
            super(p_29060_, p_29061_, p_29062_, p_29063_);
            this.f_29058_ = p_29060_;
        }

        @Override
        protected boolean m_7497_() {
            return super.m_7497_() && !this.f_29058_.m_29038_();
        }
    }

    static class OcelotAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final Ocelot f_29049_;

        public OcelotAvoidEntityGoal(Ocelot p_29051_, Class<T> p_29052_, float p_29053_, double p_29054_, double p_29055_) {
            super(p_29051_, p_29052_, p_29053_, p_29054_, p_29055_, EntitySelector.f_20406_::test);
            this.f_29049_ = p_29051_;
        }

        @Override
        public boolean m_8036_() {
            return !this.f_29049_.m_29038_() && super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            return !this.f_29049_.m_29038_() && super.m_8045_();
        }
    }
}

