/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.google.common.collect.Sets;
import java.util.LinkedHashSet;
import javax.annotation.Nullable;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class Strider
extends Animal
implements ItemSteerable,
Saddleable {
    private static final float f_149854_ = 0.23f;
    private static final float f_149855_ = 0.66f;
    private static final float f_149856_ = 0.55f;
    private static final Ingredient f_33852_ = Ingredient.m_43929_(Items.f_41955_);
    private static final Ingredient f_33853_ = Ingredient.m_43929_(Items.f_41955_, Items.f_42685_);
    private static final EntityDataAccessor<Integer> f_33854_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_33855_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_33856_ = SynchedEntityData.m_135353_(Strider.class, EntityDataSerializers.f_135035_);
    private final ItemBasedSteering f_33857_;
    @Nullable
    private TemptGoal f_33858_;
    @Nullable
    private PanicGoal f_33859_;

    public Strider(EntityType<? extends Strider> p_33862_, Level p_33863_) {
        super((EntityType<? extends Animal>)p_33862_, p_33863_);
        this.f_33857_ = new ItemBasedSteering(this.f_19804_, f_33854_, f_33856_);
        this.f_19850_ = true;
        this.m_21441_(BlockPathTypes.WATER, -1.0f);
        this.m_21441_(BlockPathTypes.LAVA, 0.0f);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0f);
    }

    public static boolean m_219128_(EntityType<Strider> p_219129_, LevelAccessor p_219130_, MobSpawnType p_219131_, BlockPos p_219132_, RandomSource p_219133_) {
        BlockPos.MutableBlockPos $$5 = p_219132_.m_122032_();
        do {
            $$5.m_122173_(Direction.UP);
        } while (p_219130_.m_6425_($$5).m_205070_(FluidTags.f_13132_));
        return p_219130_.m_8055_($$5).m_60795_();
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_33900_) {
        if (f_33854_.equals(p_33900_) && this.f_19853_.f_46443_) {
            this.f_33857_.m_20844_();
        }
        super.m_7350_(p_33900_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33854_, 0);
        this.f_19804_.m_135372_(f_33855_, false);
        this.f_19804_.m_135372_(f_33856_, false);
    }

    @Override
    public void m_7380_(CompoundTag p_33918_) {
        super.m_7380_(p_33918_);
        this.f_33857_.m_20847_(p_33918_);
    }

    @Override
    public void m_7378_(CompoundTag p_33898_) {
        super.m_7378_(p_33898_);
        this.f_33857_.m_20852_(p_33898_);
    }

    @Override
    public boolean m_6254_() {
        return this.f_33857_.m_20851_();
    }

    @Override
    public boolean m_6741_() {
        return this.m_6084_() && !this.m_6162_();
    }

    @Override
    public void m_5853_(@Nullable SoundSource p_33878_) {
        this.f_33857_.m_20849_(true);
        if (p_33878_ != null) {
            this.f_19853_.m_6269_(null, this, SoundEvents.f_12466_, p_33878_, 0.5f, 1.0f);
        }
    }

    @Override
    protected void m_8099_() {
        this.f_33859_ = new PanicGoal(this, 1.65);
        this.f_21345_.m_25352_(1, this.f_33859_);
        this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0));
        this.f_33858_ = new TemptGoal(this, 1.4, f_33853_, false);
        this.f_21345_.m_25352_(3, this.f_33858_);
        this.f_21345_.m_25352_(4, new StriderGoToLavaGoal(this, 1.5));
        this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.1));
        this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0, 60));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Strider.class, 8.0f));
    }

    public void m_33951_(boolean p_33952_) {
        this.f_19804_.m_135381_(f_33855_, p_33952_);
    }

    public boolean m_33935_() {
        if (this.m_20202_() instanceof Strider) {
            return ((Strider)this.m_20202_()).m_33935_();
        }
        return this.f_19804_.m_135370_(f_33855_);
    }

    @Override
    public boolean m_203441_(FluidState p_204067_) {
        return p_204067_.m_205070_(FluidTags.f_13132_);
    }

    @Override
    public double m_6048_() {
        float $$0 = Math.min(0.25f, this.f_20924_);
        float $$1 = this.f_20925_;
        return (double)this.m_20206_() - 0.19 + (double)(0.12f * Mth.m_14089_($$1 * 1.5f) * 2.0f * $$0);
    }

    @Override
    public boolean m_6914_(LevelReader p_33880_) {
        return p_33880_.m_45784_(this);
    }

    @Override
    @Nullable
    public Entity m_6688_() {
        Entity $$0 = this.m_146895_();
        return $$0 != null && this.m_219126_($$0) ? $$0 : null;
    }

    private boolean m_219126_(Entity p_219127_) {
        if (p_219127_ instanceof Player) {
            Player $$1 = (Player)p_219127_;
            return $$1.m_21205_().m_150930_(Items.f_42685_) || $$1.m_21206_().m_150930_(Items.f_42685_);
        }
        return false;
    }

    @Override
    public Vec3 m_7688_(LivingEntity p_33908_) {
        Vec3[] $$1 = new Vec3[]{Strider.m_19903_(this.m_20205_(), p_33908_.m_20205_(), p_33908_.m_146908_()), Strider.m_19903_(this.m_20205_(), p_33908_.m_20205_(), p_33908_.m_146908_() - 22.5f), Strider.m_19903_(this.m_20205_(), p_33908_.m_20205_(), p_33908_.m_146908_() + 22.5f), Strider.m_19903_(this.m_20205_(), p_33908_.m_20205_(), p_33908_.m_146908_() - 45.0f), Strider.m_19903_(this.m_20205_(), p_33908_.m_20205_(), p_33908_.m_146908_() + 45.0f)};
        LinkedHashSet $$2 = Sets.newLinkedHashSet();
        double $$3 = this.m_20191_().f_82292_;
        double $$4 = this.m_20191_().f_82289_ - 0.5;
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (Vec3 $$6 : $$1) {
            $$5.m_122169_(this.m_20185_() + $$6.f_82479_, $$3, this.m_20189_() + $$6.f_82481_);
            for (double $$7 = $$3; $$7 > $$4; $$7 -= 1.0) {
                $$2.add($$5.m_7949_());
                $$5.m_122173_(Direction.DOWN);
            }
        }
        for (BlockPos $$8 : $$2) {
            double $$9;
            if (this.f_19853_.m_6425_($$8).m_205070_(FluidTags.f_13132_) || !DismountHelper.m_38439_($$9 = this.f_19853_.m_45573_($$8))) continue;
            Vec3 $$10 = Vec3.m_82514_($$8, $$9);
            for (Pose $$11 : p_33908_.m_7431_()) {
                AABB $$12 = p_33908_.m_21270_($$11);
                if (!DismountHelper.m_38456_(this.f_19853_, p_33908_, $$12.m_82383_($$10))) continue;
                p_33908_.m_20124_($$11);
                return $$10;
            }
        }
        return new Vec3(this.m_20185_(), this.m_20191_().f_82292_, this.m_20189_());
    }

    @Override
    public void m_7023_(Vec3 p_33943_) {
        this.m_7910_(this.m_33936_());
        this.m_20854_(this, this.f_33857_, p_33943_);
    }

    public float m_33936_() {
        return (float)this.m_21133_(Attributes.f_22279_) * (this.m_33935_() ? 0.66f : 1.0f);
    }

    @Override
    public float m_6748_() {
        return (float)this.m_21133_(Attributes.f_22279_) * (this.m_33935_() ? 0.23f : 0.55f);
    }

    @Override
    public void m_7760_(Vec3 p_33902_) {
        super.m_7023_(p_33902_);
    }

    @Override
    protected float m_6059_() {
        return this.f_19788_ + 0.6f;
    }

    @Override
    protected void m_7355_(BlockPos p_33915_, BlockState p_33916_) {
        this.m_5496_(this.m_20077_() ? SoundEvents.f_12464_ : SoundEvents.f_12463_, 1.0f, 1.0f);
    }

    @Override
    public boolean m_6746_() {
        return this.f_33857_.m_217032_(this.m_217043_());
    }

    @Override
    protected void m_7840_(double p_33870_, boolean p_33871_, BlockState p_33872_, BlockPos p_33873_) {
        this.m_20101_();
        if (this.m_20077_()) {
            this.m_183634_();
            return;
        }
        super.m_7840_(p_33870_, p_33871_, p_33872_, p_33873_);
    }

    @Override
    public void m_8119_() {
        if (this.m_33939_() && this.f_19796_.m_188503_(140) == 0) {
            this.m_5496_(SoundEvents.f_12459_, 1.0f, this.m_6100_());
        } else if (this.m_33938_() && this.f_19796_.m_188503_(60) == 0) {
            this.m_5496_(SoundEvents.f_12460_, 1.0f, this.m_6100_());
        }
        if (!this.m_21525_()) {
            BlockState $$0 = this.f_19853_.m_8055_(this.m_20183_());
            BlockState $$1 = this.m_217002_();
            boolean $$2 = $$0.m_204336_(BlockTags.f_13086_) || $$1.m_204336_(BlockTags.f_13086_) || this.m_204036_(FluidTags.f_13132_) > 0.0;
            this.m_33951_(!$$2);
        }
        super.m_8119_();
        this.m_33940_();
        this.m_20101_();
    }

    private boolean m_33938_() {
        return this.f_33859_ != null && this.f_33859_.m_25703_();
    }

    private boolean m_33939_() {
        return this.f_33858_ != null && this.f_33858_.m_25955_();
    }

    @Override
    protected boolean m_8091_() {
        return true;
    }

    private void m_33940_() {
        if (this.m_20077_()) {
            CollisionContext $$0 = CollisionContext.m_82750_(this);
            if (!$$0.m_6513_(LiquidBlock.f_54690_, this.m_20183_(), true) || this.f_19853_.m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13132_)) {
                this.m_20256_(this.m_20184_().m_82490_(0.5).m_82520_(0.0, 0.05, 0.0));
            } else {
                this.f_19861_ = true;
            }
        }
    }

    public static AttributeSupplier.Builder m_33937_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.175f).m_22268_(Attributes.f_22277_, 16.0);
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.m_33938_() || this.m_33939_()) {
            return null;
        }
        return SoundEvents.f_12458_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33934_) {
        return SoundEvents.f_12462_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12461_;
    }

    @Override
    protected boolean m_7310_(Entity p_33950_) {
        return !this.m_20160_() && !this.m_204029_(FluidTags.f_13132_);
    }

    @Override
    public boolean m_6126_() {
        return true;
    }

    @Override
    public boolean m_6060_() {
        return false;
    }

    @Override
    protected PathNavigation m_6037_(Level p_33913_) {
        return new StriderPathNavigation(this, p_33913_);
    }

    @Override
    public float m_5610_(BlockPos p_33895_, LevelReader p_33896_) {
        if (p_33896_.m_8055_(p_33895_).m_60819_().m_205070_(FluidTags.f_13132_)) {
            return 10.0f;
        }
        return this.m_20077_() ? Float.NEGATIVE_INFINITY : 0.0f;
    }

    @Override
    public Strider m_142606_(ServerLevel p_149861_, AgeableMob p_149862_) {
        return EntityType.f_20482_.m_20615_(p_149861_);
    }

    @Override
    public boolean m_6898_(ItemStack p_33946_) {
        return f_33852_.test(p_33946_);
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (this.m_6254_()) {
            this.m_19998_(Items.f_42450_);
        }
    }

    @Override
    public InteractionResult m_6071_(Player p_33910_, InteractionHand p_33911_) {
        boolean $$2 = this.m_6898_(p_33910_.m_21120_(p_33911_));
        if (!$$2 && this.m_6254_() && !this.m_20160_() && !p_33910_.m_36341_()) {
            if (!this.f_19853_.f_46443_) {
                p_33910_.m_20329_(this);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        InteractionResult $$3 = super.m_6071_(p_33910_, p_33911_);
        if (!$$3.m_19077_()) {
            ItemStack $$4 = p_33910_.m_21120_(p_33911_);
            if ($$4.m_150930_(Items.f_42450_)) {
                return $$4.m_41647_(p_33910_, this, p_33911_);
            }
            return InteractionResult.PASS;
        }
        if ($$2 && !this.m_20067_()) {
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12465_, this.m_5720_(), 1.0f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
        }
        return $$3;
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.6f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33887_, DifficultyInstance p_33888_, MobSpawnType p_33889_, @Nullable SpawnGroupData p_33890_, @Nullable CompoundTag p_33891_) {
        if (this.m_6162_()) {
            return super.m_6518_(p_33887_, p_33888_, p_33889_, p_33890_, p_33891_);
        }
        RandomSource $$5 = p_33887_.m_213780_();
        if ($$5.m_188503_(30) == 0) {
            Mob $$6 = EntityType.f_20531_.m_20615_(p_33887_.m_6018_());
            p_33890_ = this.m_33881_(p_33887_, p_33888_, $$6, new Zombie.ZombieGroupData(Zombie.m_219162_($$5), false));
            $$6.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42685_));
            this.m_5853_(null);
        } else if ($$5.m_188503_(10) == 0) {
            AgeableMob $$7 = EntityType.f_20482_.m_20615_(p_33887_.m_6018_());
            $$7.m_146762_(-24000);
            p_33890_ = this.m_33881_(p_33887_, p_33888_, $$7, null);
        } else {
            p_33890_ = new AgeableMob.AgeableMobGroupData(0.5f);
        }
        return super.m_6518_(p_33887_, p_33888_, p_33889_, p_33890_, p_33891_);
    }

    private SpawnGroupData m_33881_(ServerLevelAccessor p_33882_, DifficultyInstance p_33883_, Mob p_33884_, @Nullable SpawnGroupData p_33885_) {
        p_33884_.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0f);
        p_33884_.m_6518_(p_33882_, p_33883_, MobSpawnType.JOCKEY, p_33885_, null);
        p_33884_.m_7998_(this, true);
        return new AgeableMob.AgeableMobGroupData(0.0f);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    static class StriderGoToLavaGoal
    extends MoveToBlockGoal {
        private final Strider f_33953_;

        StriderGoToLavaGoal(Strider p_33955_, double p_33956_) {
            super(p_33955_, p_33956_, 8, 2);
            this.f_33953_ = p_33955_;
        }

        @Override
        public BlockPos m_6669_() {
            return this.f_25602_;
        }

        @Override
        public boolean m_8045_() {
            return !this.f_33953_.m_20077_() && this.m_6465_(this.f_33953_.f_19853_, this.f_25602_);
        }

        @Override
        public boolean m_8036_() {
            return !this.f_33953_.m_20077_() && super.m_8036_();
        }

        @Override
        public boolean m_8064_() {
            return this.f_25601_ % 20 == 0;
        }

        @Override
        protected boolean m_6465_(LevelReader p_33963_, BlockPos p_33964_) {
            return p_33963_.m_8055_(p_33964_).m_60713_(Blocks.f_49991_) && p_33963_.m_8055_(p_33964_.m_7494_()).m_60647_(p_33963_, p_33964_, PathComputationType.LAND);
        }
    }

    static class StriderPathNavigation
    extends GroundPathNavigation {
        StriderPathNavigation(Strider p_33969_, Level p_33970_) {
            super(p_33969_, p_33970_);
        }

        @Override
        protected PathFinder m_5532_(int p_33972_) {
            this.f_26508_ = new WalkNodeEvaluator();
            this.f_26508_.m_77351_(true);
            return new PathFinder(this.f_26508_, p_33972_);
        }

        @Override
        protected boolean m_7367_(BlockPathTypes p_33974_) {
            if (p_33974_ == BlockPathTypes.LAVA || p_33974_ == BlockPathTypes.DAMAGE_FIRE || p_33974_ == BlockPathTypes.DANGER_FIRE) {
                return true;
            }
            return super.m_7367_(p_33974_);
        }

        @Override
        public boolean m_6342_(BlockPos p_33976_) {
            return this.f_26495_.m_8055_(p_33976_).m_60713_(Blocks.f_49991_) || super.m_6342_(p_33976_);
        }
    }
}

