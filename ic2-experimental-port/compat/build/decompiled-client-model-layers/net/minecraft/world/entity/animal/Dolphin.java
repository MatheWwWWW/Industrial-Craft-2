/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class Dolphin
extends WaterAnimal {
    private static final EntityDataAccessor<BlockPos> f_28312_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135038_);
    private static final EntityDataAccessor<Boolean> f_28313_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_28310_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135028_);
    static final TargetingConditions f_28311_ = TargetingConditions.m_148353_().m_26883_(10.0).m_148355_();
    public static final int f_148892_ = 4800;
    private static final int f_148893_ = 2400;
    public static final Predicate<ItemEntity> f_28309_ = p_28369_ -> !p_28369_.m_32063_() && p_28369_.m_6084_() && p_28369_.m_20069_();

    public Dolphin(EntityType<? extends Dolphin> p_28316_, Level p_28317_) {
        super((EntityType<? extends WaterAnimal>)p_28316_, p_28317_);
        this.f_21342_ = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.f_21365_ = new SmoothSwimmingLookControl(this, 10);
        this.m_21553_(true);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_28332_, DifficultyInstance p_28333_, MobSpawnType p_28334_, @Nullable SpawnGroupData p_28335_, @Nullable CompoundTag p_28336_) {
        this.m_20301_(this.m_6062_());
        this.m_146926_(0.0f);
        return super.m_6518_(p_28332_, p_28333_, p_28334_, p_28335_, p_28336_);
    }

    @Override
    public boolean m_6040_() {
        return false;
    }

    @Override
    protected void m_6229_(int p_28326_) {
    }

    public void m_28384_(BlockPos p_28385_) {
        this.f_19804_.m_135381_(f_28312_, p_28385_);
    }

    public BlockPos m_28387_() {
        return this.f_19804_.m_135370_(f_28312_);
    }

    public boolean m_28377_() {
        return this.f_19804_.m_135370_(f_28313_);
    }

    public void m_28393_(boolean p_28394_) {
        this.f_19804_.m_135381_(f_28313_, p_28394_);
    }

    public int m_28378_() {
        return this.f_19804_.m_135370_(f_28310_);
    }

    public void m_28343_(int p_28344_) {
        this.f_19804_.m_135381_(f_28310_, p_28344_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_28312_, BlockPos.f_121853_);
        this.f_19804_.m_135372_(f_28313_, false);
        this.f_19804_.m_135372_(f_28310_, 2400);
    }

    @Override
    public void m_7380_(CompoundTag p_28364_) {
        super.m_7380_(p_28364_);
        p_28364_.m_128405_("TreasurePosX", this.m_28387_().m_123341_());
        p_28364_.m_128405_("TreasurePosY", this.m_28387_().m_123342_());
        p_28364_.m_128405_("TreasurePosZ", this.m_28387_().m_123343_());
        p_28364_.m_128379_("GotFish", this.m_28377_());
        p_28364_.m_128405_("Moistness", this.m_28378_());
    }

    @Override
    public void m_7378_(CompoundTag p_28340_) {
        int $$1 = p_28340_.m_128451_("TreasurePosX");
        int $$2 = p_28340_.m_128451_("TreasurePosY");
        int $$3 = p_28340_.m_128451_("TreasurePosZ");
        this.m_28384_(new BlockPos($$1, $$2, $$3));
        super.m_7378_(p_28340_);
        this.m_28393_(p_28340_.m_128471_("GotFish"));
        this.m_28343_(p_28340_.m_128451_("Moistness"));
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new BreathAirGoal(this));
        this.f_21345_.m_25352_(0, new TryFindWaterGoal(this));
        this.f_21345_.m_25352_(1, new DolphinSwimToTreasureGoal(this));
        this.f_21345_.m_25352_(2, new DolphinSwimWithPlayerGoal(this, 4.0));
        this.f_21345_.m_25352_(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
        this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.f_21345_.m_25352_(5, new DolphinJumpGoal(this, 10));
        this.f_21345_.m_25352_(6, new MeleeAttackGoal(this, 1.2f, true));
        this.f_21345_.m_25352_(8, new PlayWithItemsGoal());
        this.f_21345_.m_25352_(8, new FollowBoatGoal(this));
        this.f_21345_.m_25352_(9, new AvoidEntityGoal<Guardian>(this, Guardian.class, 8.0f, 1.0, 1.0));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Guardian.class).m_26044_(new Class[0]));
    }

    public static AttributeSupplier.Builder m_28379_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 1.2f).m_22268_(Attributes.f_22281_, 3.0);
    }

    @Override
    protected PathNavigation m_6037_(Level p_28362_) {
        return new WaterBoundPathNavigation(this, p_28362_);
    }

    @Override
    public boolean m_7327_(Entity p_28319_) {
        boolean $$1 = p_28319_.m_6469_(DamageSource.m_19370_(this), (int)this.m_21133_(Attributes.f_22281_));
        if ($$1) {
            this.m_19970_(this, p_28319_);
            this.m_5496_(SoundEvents.f_11801_, 1.0f, 1.0f);
        }
        return $$1;
    }

    @Override
    public int m_6062_() {
        return 4800;
    }

    @Override
    protected int m_7305_(int p_28389_) {
        return this.m_6062_();
    }

    @Override
    protected float m_6431_(Pose p_28352_, EntityDimensions p_28353_) {
        return 0.3f;
    }

    @Override
    public int m_8132_() {
        return 1;
    }

    @Override
    public int m_8085_() {
        return 1;
    }

    @Override
    protected boolean m_7341_(Entity p_28391_) {
        return true;
    }

    @Override
    public boolean m_7066_(ItemStack p_28376_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_28376_);
        if (!this.m_6844_($$1).m_41619_()) {
            return false;
        }
        return $$1 == EquipmentSlot.MAINHAND && super.m_7066_(p_28376_);
    }

    @Override
    protected void m_7581_(ItemEntity p_28357_) {
        ItemStack $$1;
        if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() && this.m_7252_($$1 = p_28357_.m_32055_())) {
            this.m_21053_(p_28357_);
            this.m_8061_(EquipmentSlot.MAINHAND, $$1);
            this.m_21508_(EquipmentSlot.MAINHAND);
            this.m_7938_(p_28357_, $$1.m_41613_());
            p_28357_.m_146870_();
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_21525_()) {
            this.m_20301_(this.m_6062_());
            return;
        }
        if (this.m_20071_()) {
            this.m_28343_(2400);
        } else {
            this.m_28343_(this.m_28378_() - 1);
            if (this.m_28378_() <= 0) {
                this.m_6469_(DamageSource.f_19324_, 1.0f);
            }
            if (this.f_19861_) {
                this.m_20256_(this.m_20184_().m_82520_((this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.2f, 0.5, (this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.2f));
                this.m_146922_(this.f_19796_.m_188501_() * 360.0f);
                this.f_19861_ = false;
                this.f_19812_ = true;
            }
        }
        if (this.f_19853_.f_46443_ && this.m_20069_() && this.m_20184_().m_82556_() > 0.03) {
            Vec3 $$0 = this.m_20252_(0.0f);
            float $$1 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)) * 0.3f;
            float $$2 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)) * 0.3f;
            float $$3 = 1.2f - this.f_19796_.m_188501_() * 0.7f;
            for (int $$4 = 0; $$4 < 2; ++$$4) {
                this.f_19853_.m_7106_(ParticleTypes.f_123776_, this.m_20185_() - $$0.f_82479_ * (double)$$3 + (double)$$1, this.m_20186_() - $$0.f_82480_, this.m_20189_() - $$0.f_82481_ * (double)$$3 + (double)$$2, 0.0, 0.0, 0.0);
                this.f_19853_.m_7106_(ParticleTypes.f_123776_, this.m_20185_() - $$0.f_82479_ * (double)$$3 - (double)$$1, this.m_20186_() - $$0.f_82480_, this.m_20189_() - $$0.f_82481_ * (double)$$3 - (double)$$2, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void m_7822_(byte p_28324_) {
        if (p_28324_ == 38) {
            this.m_28337_(ParticleTypes.f_123748_);
        } else {
            super.m_7822_(p_28324_);
        }
    }

    private void m_28337_(ParticleOptions p_28338_) {
        for (int $$1 = 0; $$1 < 7; ++$$1) {
            double $$2 = this.f_19796_.m_188583_() * 0.01;
            double $$3 = this.f_19796_.m_188583_() * 0.01;
            double $$4 = this.f_19796_.m_188583_() * 0.01;
            this.f_19853_.m_7106_(p_28338_, this.m_20208_(1.0), this.m_20187_() + 0.2, this.m_20262_(1.0), $$2, $$3, $$4);
        }
    }

    @Override
    protected InteractionResult m_6071_(Player p_28359_, InteractionHand p_28360_) {
        ItemStack $$2 = p_28359_.m_21120_(p_28360_);
        if (!$$2.m_41619_() && $$2.m_204117_(ItemTags.f_13156_)) {
            if (!this.f_19853_.f_46443_) {
                this.m_5496_(SoundEvents.f_11803_, 1.0f, 1.0f);
            }
            this.m_28393_(true);
            if (!p_28359_.m_150110_().f_35937_) {
                $$2.m_41774_(1);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_28359_, p_28360_);
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_28374_) {
        return SoundEvents.f_11804_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11802_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return this.m_20069_() ? SoundEvents.f_11800_ : SoundEvents.f_11799_;
    }

    @Override
    protected SoundEvent m_5509_() {
        return SoundEvents.f_11807_;
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_11808_;
    }

    protected boolean m_28380_() {
        BlockPos $$0 = this.m_21573_().m_26567_();
        if ($$0 != null) {
            return $$0.m_203195_(this.m_20182_(), 12.0);
        }
        return false;
    }

    @Override
    public void m_7023_(Vec3 p_28383_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(this.m_6113_(), p_28383_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (this.m_5448_() == null) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
        } else {
            super.m_7023_(p_28383_);
        }
    }

    @Override
    public boolean m_6573_(Player p_28330_) {
        return true;
    }

    static class DolphinSwimToTreasureGoal
    extends Goal {
        private final Dolphin f_28399_;
        private boolean f_28400_;

        DolphinSwimToTreasureGoal(Dolphin p_28402_) {
            this.f_28399_ = p_28402_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_6767_() {
            return false;
        }

        @Override
        public boolean m_8036_() {
            return this.f_28399_.m_28377_() && this.f_28399_.m_20146_() >= 100;
        }

        @Override
        public boolean m_8045_() {
            BlockPos $$0 = this.f_28399_.m_28387_();
            return !new BlockPos((double)$$0.m_123341_(), this.f_28399_.m_20186_(), (double)$$0.m_123343_()).m_203195_(this.f_28399_.m_20182_(), 4.0) && !this.f_28400_ && this.f_28399_.m_20146_() >= 100;
        }

        @Override
        public void m_8056_() {
            if (!(this.f_28399_.f_19853_ instanceof ServerLevel)) {
                return;
            }
            ServerLevel $$0 = (ServerLevel)this.f_28399_.f_19853_;
            this.f_28400_ = false;
            this.f_28399_.m_21573_().m_26573_();
            BlockPos $$1 = this.f_28399_.m_20183_();
            BlockPos $$2 = $$0.m_215011_(StructureTags.f_215883_, $$1, 50, false);
            if ($$2 == null) {
                this.f_28400_ = true;
                return;
            }
            this.f_28399_.m_28384_($$2);
            $$0.m_7605_(this.f_28399_, (byte)38);
        }

        @Override
        public void m_8041_() {
            BlockPos $$0 = this.f_28399_.m_28387_();
            if (new BlockPos((double)$$0.m_123341_(), this.f_28399_.m_20186_(), (double)$$0.m_123343_()).m_203195_(this.f_28399_.m_20182_(), 4.0) || this.f_28400_) {
                this.f_28399_.m_28393_(false);
            }
        }

        @Override
        public void m_8037_() {
            Level $$0 = this.f_28399_.f_19853_;
            if (this.f_28399_.m_28380_() || this.f_28399_.m_21573_().m_26571_()) {
                BlockPos $$3;
                Vec3 $$1 = Vec3.m_82512_(this.f_28399_.m_28387_());
                Vec3 $$2 = DefaultRandomPos.m_148412_(this.f_28399_, 16, 1, $$1, 0.3926991f);
                if ($$2 == null) {
                    $$2 = DefaultRandomPos.m_148412_(this.f_28399_, 8, 4, $$1, 1.5707963705062866);
                }
                if (!($$2 == null || $$0.m_6425_($$3 = new BlockPos($$2)).m_205070_(FluidTags.f_13131_) && $$0.m_8055_($$3).m_60647_($$0, $$3, PathComputationType.WATER))) {
                    $$2 = DefaultRandomPos.m_148412_(this.f_28399_, 8, 5, $$1, 1.5707963705062866);
                }
                if ($$2 == null) {
                    this.f_28400_ = true;
                    return;
                }
                this.f_28399_.m_21563_().m_24950_($$2.f_82479_, $$2.f_82480_, $$2.f_82481_, this.f_28399_.m_8085_() + 20, this.f_28399_.m_8132_());
                this.f_28399_.m_21573_().m_26519_($$2.f_82479_, $$2.f_82480_, $$2.f_82481_, 1.3);
                if ($$0.f_46441_.m_188503_(this.m_183277_(80)) == 0) {
                    $$0.m_7605_(this.f_28399_, (byte)38);
                }
            }
        }
    }

    static class DolphinSwimWithPlayerGoal
    extends Goal {
        private final Dolphin f_28409_;
        private final double f_28410_;
        @Nullable
        private Player f_28411_;

        DolphinSwimWithPlayerGoal(Dolphin p_28413_, double p_28414_) {
            this.f_28409_ = p_28413_;
            this.f_28410_ = p_28414_;
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            this.f_28411_ = this.f_28409_.f_19853_.m_45946_(f_28311_, this.f_28409_);
            if (this.f_28411_ == null) {
                return false;
            }
            return this.f_28411_.m_6069_() && this.f_28409_.m_5448_() != this.f_28411_;
        }

        @Override
        public boolean m_8045_() {
            return this.f_28411_ != null && this.f_28411_.m_6069_() && this.f_28409_.m_20280_(this.f_28411_) < 256.0;
        }

        @Override
        public void m_8056_() {
            this.f_28411_.m_147207_(new MobEffectInstance(MobEffects.f_19593_, 100), this.f_28409_);
        }

        @Override
        public void m_8041_() {
            this.f_28411_ = null;
            this.f_28409_.m_21573_().m_26573_();
        }

        @Override
        public void m_8037_() {
            this.f_28409_.m_21563_().m_24960_(this.f_28411_, this.f_28409_.m_8085_() + 20, this.f_28409_.m_8132_());
            if (this.f_28409_.m_20280_(this.f_28411_) < 6.25) {
                this.f_28409_.m_21573_().m_26573_();
            } else {
                this.f_28409_.m_21573_().m_5624_(this.f_28411_, this.f_28410_);
            }
            if (this.f_28411_.m_6069_() && this.f_28411_.f_19853_.f_46441_.m_188503_(6) == 0) {
                this.f_28411_.m_147207_(new MobEffectInstance(MobEffects.f_19593_, 100), this.f_28409_);
            }
        }
    }

    class PlayWithItemsGoal
    extends Goal {
        private int f_28421_;

        PlayWithItemsGoal() {
        }

        @Override
        public boolean m_8036_() {
            if (this.f_28421_ > Dolphin.this.f_19797_) {
                return false;
            }
            List<ItemEntity> $$0 = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28309_);
            return !$$0.isEmpty() || !Dolphin.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
        }

        @Override
        public void m_8056_() {
            List<ItemEntity> $$0 = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28309_);
            if (!$$0.isEmpty()) {
                Dolphin.this.m_21573_().m_5624_($$0.get(0), 1.2f);
                Dolphin.this.m_5496_(SoundEvents.f_11806_, 1.0f, 1.0f);
            }
            this.f_28421_ = 0;
        }

        @Override
        public void m_8041_() {
            ItemStack $$0 = Dolphin.this.m_6844_(EquipmentSlot.MAINHAND);
            if (!$$0.m_41619_()) {
                this.m_28428_($$0);
                Dolphin.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                this.f_28421_ = Dolphin.this.f_19797_ + Dolphin.this.f_19796_.m_188503_(100);
            }
        }

        @Override
        public void m_8037_() {
            List<ItemEntity> $$0 = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28309_);
            ItemStack $$1 = Dolphin.this.m_6844_(EquipmentSlot.MAINHAND);
            if (!$$1.m_41619_()) {
                this.m_28428_($$1);
                Dolphin.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            } else if (!$$0.isEmpty()) {
                Dolphin.this.m_21573_().m_5624_($$0.get(0), 1.2f);
            }
        }

        private void m_28428_(ItemStack p_28429_) {
            if (p_28429_.m_41619_()) {
                return;
            }
            double $$1 = Dolphin.this.m_20188_() - (double)0.3f;
            ItemEntity $$2 = new ItemEntity(Dolphin.this.f_19853_, Dolphin.this.m_20185_(), $$1, Dolphin.this.m_20189_(), p_28429_);
            $$2.m_32010_(40);
            $$2.m_32052_(Dolphin.this.m_20148_());
            float $$3 = 0.3f;
            float $$4 = Dolphin.this.f_19796_.m_188501_() * ((float)Math.PI * 2);
            float $$5 = 0.02f * Dolphin.this.f_19796_.m_188501_();
            $$2.m_20334_(0.3f * -Mth.m_14031_(Dolphin.this.m_146908_() * ((float)Math.PI / 180)) * Mth.m_14089_(Dolphin.this.m_146909_() * ((float)Math.PI / 180)) + Mth.m_14089_($$4) * $$5, 0.3f * Mth.m_14031_(Dolphin.this.m_146909_() * ((float)Math.PI / 180)) * 1.5f, 0.3f * Mth.m_14089_(Dolphin.this.m_146908_() * ((float)Math.PI / 180)) * Mth.m_14089_(Dolphin.this.m_146909_() * ((float)Math.PI / 180)) + Mth.m_14031_($$4) * $$5);
            Dolphin.this.f_19853_.m_7967_($$2);
        }
    }
}

