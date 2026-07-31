/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Parrot
extends ShoulderRidingEntity
implements FlyingAnimal {
    private static final EntityDataAccessor<Integer> f_29354_ = SynchedEntityData.m_135353_(Parrot.class, EntityDataSerializers.f_135028_);
    private static final Predicate<Mob> f_29355_ = new Predicate<Mob>(){

        @Override
        public boolean test(@Nullable Mob p_29453_) {
            return p_29453_ != null && f_29358_.containsKey(p_29453_.m_6095_());
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((Mob)object);
        }
    };
    private static final Item f_29356_ = Items.f_42572_;
    private static final Set<Item> f_29357_ = Sets.newHashSet((Object[])new Item[]{Items.f_42404_, Items.f_42578_, Items.f_42577_, Items.f_42733_});
    private static final int f_148986_ = 5;
    static final Map<EntityType<?>, SoundEvent> f_29358_ = Util.m_137469_(Maps.newHashMap(), p_29398_ -> {
        p_29398_.put(EntityType.f_20551_, SoundEvents.f_12246_);
        p_29398_.put(EntityType.f_20554_, SoundEvents.f_12268_);
        p_29398_.put(EntityType.f_20558_, SoundEvents.f_12247_);
        p_29398_.put(EntityType.f_20562_, SoundEvents.f_12248_);
        p_29398_.put(EntityType.f_20563_, SoundEvents.f_12249_);
        p_29398_.put(EntityType.f_20565_, SoundEvents.f_12250_);
        p_29398_.put(EntityType.f_20567_, SoundEvents.f_12251_);
        p_29398_.put(EntityType.f_20568_, SoundEvents.f_12252_);
        p_29398_.put(EntityType.f_20453_, SoundEvents.f_12253_);
        p_29398_.put(EntityType.f_20455_, SoundEvents.f_12254_);
        p_29398_.put(EntityType.f_20456_, SoundEvents.f_12255_);
        p_29398_.put(EntityType.f_20458_, SoundEvents.f_12256_);
        p_29398_.put(EntityType.f_20459_, SoundEvents.f_12257_);
        p_29398_.put(EntityType.f_20468_, SoundEvents.f_12258_);
        p_29398_.put(EntityType.f_20509_, SoundEvents.f_12259_);
        p_29398_.put(EntityType.f_20511_, SoundEvents.f_12260_);
        p_29398_.put(EntityType.f_20512_, SoundEvents.f_12261_);
        p_29398_.put(EntityType.f_20513_, SoundEvents.f_12262_);
        p_29398_.put(EntityType.f_20518_, SoundEvents.f_12263_);
        p_29398_.put(EntityType.f_20521_, SoundEvents.f_12264_);
        p_29398_.put(EntityType.f_20523_, SoundEvents.f_12265_);
        p_29398_.put(EntityType.f_20524_, SoundEvents.f_12266_);
        p_29398_.put(EntityType.f_20526_, SoundEvents.f_12267_);
        p_29398_.put(EntityType.f_20479_, SoundEvents.f_12268_);
        p_29398_.put(EntityType.f_20481_, SoundEvents.f_12269_);
        p_29398_.put(EntityType.f_20491_, SoundEvents.f_12270_);
        p_29398_.put(EntityType.f_20493_, SoundEvents.f_12271_);
        p_29398_.put(EntityType.f_217015_, SoundEvents.f_215733_);
        p_29398_.put(EntityType.f_20495_, SoundEvents.f_12220_);
        p_29398_.put(EntityType.f_20496_, SoundEvents.f_12221_);
        p_29398_.put(EntityType.f_20497_, SoundEvents.f_12222_);
        p_29398_.put(EntityType.f_20500_, SoundEvents.f_12223_);
        p_29398_.put(EntityType.f_20501_, SoundEvents.f_12224_);
        p_29398_.put(EntityType.f_20530_, SoundEvents.f_12225_);
    });
    public float f_29350_;
    public float f_29351_;
    public float f_29352_;
    public float f_29353_;
    private float f_29359_ = 1.0f;
    private float f_148987_ = 1.0f;
    private boolean f_29348_;
    @Nullable
    private BlockPos f_29349_;

    public Parrot(EntityType<? extends Parrot> p_29362_, Level p_29363_) {
        super((EntityType<? extends ShoulderRidingEntity>)p_29362_, p_29363_);
        this.f_21342_ = new FlyingMoveControl(this, 10, false);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, -1.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -1.0f);
        this.m_21441_(BlockPathTypes.COCOA, -1.0f);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_29389_, DifficultyInstance p_29390_, MobSpawnType p_29391_, @Nullable SpawnGroupData p_29392_, @Nullable CompoundTag p_29393_) {
        this.m_29448_(p_29389_.m_213780_().m_188503_(5));
        if (p_29392_ == null) {
            p_29392_ = new AgeableMob.AgeableMobGroupData(false);
        }
        return super.m_6518_(p_29389_, p_29390_, p_29391_, p_29392_, p_29393_);
    }

    @Override
    public boolean m_6162_() {
        return false;
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new PanicGoal(this, 1.25));
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
        this.f_21345_.m_25352_(2, new FollowOwnerGoal(this, 1.0, 5.0f, 1.0f, true));
        this.f_21345_.m_25352_(2, new ParrotWanderGoal(this, 1.0));
        this.f_21345_.m_25352_(3, new LandOnOwnersShoulderGoal(this));
        this.f_21345_.m_25352_(3, new FollowMobGoal(this, 1.0, 3.0f, 7.0f));
    }

    public static AttributeSupplier.Builder m_29438_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 6.0).m_22268_(Attributes.f_22280_, 0.4f).m_22268_(Attributes.f_22279_, 0.2f);
    }

    @Override
    protected PathNavigation m_6037_(Level p_29417_) {
        FlyingPathNavigation $$1 = new FlyingPathNavigation(this, p_29417_);
        $$1.m_26440_(false);
        $$1.m_7008_(true);
        $$1.m_26443_(true);
        return $$1;
    }

    @Override
    protected float m_6431_(Pose p_29411_, EntityDimensions p_29412_) {
        return p_29412_.f_20378_ * 0.6f;
    }

    @Override
    public void m_8107_() {
        if (this.f_29349_ == null || !this.f_29349_.m_203195_(this.m_20182_(), 3.46) || !this.f_19853_.m_8055_(this.f_29349_).m_60713_(Blocks.f_50131_)) {
            this.f_29348_ = false;
            this.f_29349_ = null;
        }
        if (this.f_19853_.f_46441_.m_188503_(400) == 0) {
            Parrot.m_29382_(this.f_19853_, this);
        }
        super.m_8107_();
        this.m_29442_();
    }

    @Override
    public void m_6818_(BlockPos p_29395_, boolean p_29396_) {
        this.f_29349_ = p_29395_;
        this.f_29348_ = p_29396_;
    }

    public boolean m_29439_() {
        return this.f_29348_;
    }

    private void m_29442_() {
        this.f_29353_ = this.f_29350_;
        this.f_29352_ = this.f_29351_;
        this.f_29351_ += (float)(this.f_19861_ || this.m_20159_() ? -1 : 4) * 0.3f;
        this.f_29351_ = Mth.m_14036_(this.f_29351_, 0.0f, 1.0f);
        if (!this.f_19861_ && this.f_29359_ < 1.0f) {
            this.f_29359_ = 1.0f;
        }
        this.f_29359_ *= 0.9f;
        Vec3 $$0 = this.m_20184_();
        if (!this.f_19861_ && $$0.f_82480_ < 0.0) {
            this.m_20256_($$0.m_82542_(1.0, 0.6, 1.0));
        }
        this.f_29350_ += this.f_29359_ * 2.0f;
    }

    public static boolean m_29382_(Level p_29383_, Entity p_29384_) {
        Mob $$3;
        if (!p_29384_.m_6084_() || p_29384_.m_20067_() || p_29383_.f_46441_.m_188503_(2) != 0) {
            return false;
        }
        List<Mob> $$2 = p_29383_.m_6443_(Mob.class, p_29384_.m_20191_().m_82400_(20.0), f_29355_);
        if (!$$2.isEmpty() && !($$3 = $$2.get(p_29383_.f_46441_.m_188503_($$2.size()))).m_20067_()) {
            SoundEvent $$4 = Parrot.m_29408_($$3.m_6095_());
            p_29383_.m_6263_(null, p_29384_.m_20185_(), p_29384_.m_20186_(), p_29384_.m_20189_(), $$4, p_29384_.m_5720_(), 0.7f, Parrot.m_218236_(p_29383_.f_46441_));
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult m_6071_(Player p_29414_, InteractionHand p_29415_) {
        ItemStack $$2 = p_29414_.m_21120_(p_29415_);
        if (!this.m_21824_() && f_29357_.contains($$2.m_41720_())) {
            if (!p_29414_.m_150110_().f_35937_) {
                $$2.m_41774_(1);
            }
            if (!this.m_20067_()) {
                this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12190_, this.m_5720_(), 1.0f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
            }
            if (!this.f_19853_.f_46443_) {
                if (this.f_19796_.m_188503_(10) == 0) {
                    this.m_21828_(p_29414_);
                    this.f_19853_.m_7605_(this, (byte)7);
                } else {
                    this.f_19853_.m_7605_(this, (byte)6);
                }
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if ($$2.m_150930_(f_29356_)) {
            if (!p_29414_.m_150110_().f_35937_) {
                $$2.m_41774_(1);
            }
            this.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 900));
            if (p_29414_.m_7500_() || !this.m_20147_()) {
                this.m_6469_(DamageSource.m_19344_(p_29414_), Float.MAX_VALUE);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if (!this.m_29443_() && this.m_21824_() && this.m_21830_(p_29414_)) {
            if (!this.f_19853_.f_46443_) {
                this.m_21839_(!this.m_21827_());
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_29414_, p_29415_);
    }

    @Override
    public boolean m_6898_(ItemStack p_29446_) {
        return false;
    }

    public static boolean m_218241_(EntityType<Parrot> p_218242_, LevelAccessor p_218243_, MobSpawnType p_218244_, BlockPos p_218245_, RandomSource p_218246_) {
        return p_218243_.m_8055_(p_218245_.m_7495_()).m_204336_(BlockTags.f_184232_) && Parrot.m_186209_(p_218243_, p_218245_);
    }

    @Override
    public boolean m_142535_(float p_148989_, float p_148990_, DamageSource p_148991_) {
        return false;
    }

    @Override
    protected void m_7840_(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
    }

    @Override
    public boolean m_7848_(Animal p_29381_) {
        return false;
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_148993_, AgeableMob p_148994_) {
        return null;
    }

    @Override
    public boolean m_7327_(Entity p_29365_) {
        return p_29365_.m_6469_(DamageSource.m_19370_(this), 3.0f);
    }

    @Override
    @Nullable
    public SoundEvent m_7515_() {
        return Parrot.m_218238_(this.f_19853_, this.f_19853_.f_46441_);
    }

    public static SoundEvent m_218238_(Level p_218239_, RandomSource p_218240_) {
        if (p_218239_.m_46791_() != Difficulty.PEACEFUL && p_218240_.m_188503_(1000) == 0) {
            ArrayList $$2 = Lists.newArrayList(f_29358_.keySet());
            return Parrot.m_29408_((EntityType)$$2.get(p_218240_.m_188503_($$2.size())));
        }
        return SoundEvents.f_12188_;
    }

    private static SoundEvent m_29408_(EntityType<?> p_29409_) {
        return f_29358_.getOrDefault(p_29409_, SoundEvents.f_12188_);
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_29437_) {
        return SoundEvents.f_12192_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12189_;
    }

    @Override
    protected void m_7355_(BlockPos p_29419_, BlockState p_29420_) {
        this.m_5496_(SoundEvents.f_12226_, 0.15f, 1.0f);
    }

    @Override
    protected boolean m_142039_() {
        return this.f_146794_ > this.f_148987_;
    }

    @Override
    protected void m_142043_() {
        this.m_5496_(SoundEvents.f_12191_, 0.15f, 1.0f);
        this.f_148987_ = this.f_146794_ + this.f_29351_ / 2.0f;
    }

    @Override
    public float m_6100_() {
        return Parrot.m_218236_(this.f_19796_);
    }

    public static float m_218236_(RandomSource p_218237_) {
        return (p_218237_.m_188501_() - p_218237_.m_188501_()) * 0.2f + 1.0f;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public boolean m_6094_() {
        return true;
    }

    @Override
    protected void m_7324_(Entity p_29367_) {
        if (p_29367_ instanceof Player) {
            return;
        }
        super.m_7324_(p_29367_);
    }

    @Override
    public boolean m_6469_(DamageSource p_29378_, float p_29379_) {
        if (this.m_6673_(p_29378_)) {
            return false;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_21839_(false);
        }
        return super.m_6469_(p_29378_, p_29379_);
    }

    public int m_29440_() {
        return Mth.m_14045_(this.f_19804_.m_135370_(f_29354_), 0, 4);
    }

    public void m_29448_(int p_29449_) {
        this.f_19804_.m_135381_(f_29354_, p_29449_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_29354_, 0);
    }

    @Override
    public void m_7380_(CompoundTag p_29422_) {
        super.m_7380_(p_29422_);
        p_29422_.m_128405_("Variant", this.m_29440_());
    }

    @Override
    public void m_7378_(CompoundTag p_29402_) {
        super.m_7378_(p_29402_);
        this.m_29448_(p_29402_.m_128451_("Variant"));
    }

    @Override
    public boolean m_29443_() {
        return !this.f_19861_;
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.5f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    static class ParrotWanderGoal
    extends WaterAvoidingRandomFlyingGoal {
        public ParrotWanderGoal(PathfinderMob p_186224_, double p_186225_) {
            super(p_186224_, p_186225_);
        }

        @Override
        @Nullable
        protected Vec3 m_7037_() {
            Vec3 $$0 = null;
            if (this.f_25725_.m_20069_()) {
                $$0 = LandRandomPos.m_148488_(this.f_25725_, 15, 15);
            }
            if (this.f_25725_.m_217043_().m_188501_() >= this.f_25985_) {
                $$0 = this.m_186227_();
            }
            return $$0 == null ? super.m_7037_() : $$0;
        }

        @Nullable
        private Vec3 m_186227_() {
            BlockPos $$0 = this.f_25725_.m_20183_();
            BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
            Iterable<BlockPos> $$3 = BlockPos.m_121976_(Mth.m_14107_(this.f_25725_.m_20185_() - 3.0), Mth.m_14107_(this.f_25725_.m_20186_() - 6.0), Mth.m_14107_(this.f_25725_.m_20189_() - 3.0), Mth.m_14107_(this.f_25725_.m_20185_() + 3.0), Mth.m_14107_(this.f_25725_.m_20186_() + 6.0), Mth.m_14107_(this.f_25725_.m_20189_() + 3.0));
            for (BlockPos $$4 : $$3) {
                BlockState $$5;
                boolean $$6;
                if ($$0.equals($$4) || !($$6 = ($$5 = this.f_25725_.f_19853_.m_8055_($$2.m_122159_($$4, Direction.DOWN))).m_60734_() instanceof LeavesBlock || $$5.m_204336_(BlockTags.f_13106_)) || !this.f_25725_.f_19853_.m_46859_($$4) || !this.f_25725_.f_19853_.m_46859_($$1.m_122159_($$4, Direction.UP))) continue;
                return Vec3.m_82539_($$4);
            }
            return null;
        }
    }
}

