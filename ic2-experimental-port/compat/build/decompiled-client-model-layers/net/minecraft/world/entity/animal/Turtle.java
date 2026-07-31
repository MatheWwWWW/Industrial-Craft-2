/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Turtle
extends Animal {
    private static final EntityDataAccessor<BlockPos> f_30123_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135038_);
    private static final EntityDataAccessor<Boolean> f_30124_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_30125_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<BlockPos> f_30126_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135038_);
    private static final EntityDataAccessor<Boolean> f_30127_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_30128_ = SynchedEntityData.m_135353_(Turtle.class, EntityDataSerializers.f_135035_);
    public static final Ingredient f_149066_ = Ingredient.m_43929_(Blocks.f_50037_.m_5456_());
    int f_30129_;
    public static final Predicate<LivingEntity> f_30122_ = p_30226_ -> p_30226_.m_6162_() && !p_30226_.m_20069_();

    public Turtle(EntityType<? extends Turtle> p_30132_, Level p_30133_) {
        super((EntityType<? extends Animal>)p_30132_, p_30133_);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.m_21441_(BlockPathTypes.DOOR_IRON_CLOSED, -1.0f);
        this.m_21441_(BlockPathTypes.DOOR_WOOD_CLOSED, -1.0f);
        this.m_21441_(BlockPathTypes.DOOR_OPEN, -1.0f);
        this.f_21342_ = new TurtleMoveControl(this);
        this.f_19793_ = 1.0f;
    }

    public void m_30219_(BlockPos p_30220_) {
        this.f_19804_.m_135381_(f_30123_, p_30220_);
    }

    BlockPos m_30208_() {
        return this.f_19804_.m_135370_(f_30123_);
    }

    void m_30223_(BlockPos p_30224_) {
        this.f_19804_.m_135381_(f_30126_, p_30224_);
    }

    BlockPos m_30209_() {
        return this.f_19804_.m_135370_(f_30126_);
    }

    public boolean m_30205_() {
        return this.f_19804_.m_135370_(f_30124_);
    }

    void m_30234_(boolean p_30235_) {
        this.f_19804_.m_135381_(f_30124_, p_30235_);
    }

    public boolean m_30206_() {
        return this.f_19804_.m_135370_(f_30125_);
    }

    void m_30236_(boolean p_30237_) {
        this.f_30129_ = p_30237_ ? 1 : 0;
        this.f_19804_.m_135381_(f_30125_, p_30237_);
    }

    boolean m_30211_() {
        return this.f_19804_.m_135370_(f_30127_);
    }

    void m_30238_(boolean p_30239_) {
        this.f_19804_.m_135381_(f_30127_, p_30239_);
    }

    boolean m_30212_() {
        return this.f_19804_.m_135370_(f_30128_);
    }

    void m_30240_(boolean p_30241_) {
        this.f_19804_.m_135381_(f_30128_, p_30241_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30123_, BlockPos.f_121853_);
        this.f_19804_.m_135372_(f_30124_, false);
        this.f_19804_.m_135372_(f_30126_, BlockPos.f_121853_);
        this.f_19804_.m_135372_(f_30127_, false);
        this.f_19804_.m_135372_(f_30128_, false);
        this.f_19804_.m_135372_(f_30125_, false);
    }

    @Override
    public void m_7380_(CompoundTag p_30176_) {
        super.m_7380_(p_30176_);
        p_30176_.m_128405_("HomePosX", this.m_30208_().m_123341_());
        p_30176_.m_128405_("HomePosY", this.m_30208_().m_123342_());
        p_30176_.m_128405_("HomePosZ", this.m_30208_().m_123343_());
        p_30176_.m_128379_("HasEgg", this.m_30205_());
        p_30176_.m_128405_("TravelPosX", this.m_30209_().m_123341_());
        p_30176_.m_128405_("TravelPosY", this.m_30209_().m_123342_());
        p_30176_.m_128405_("TravelPosZ", this.m_30209_().m_123343_());
    }

    @Override
    public void m_7378_(CompoundTag p_30162_) {
        int $$1 = p_30162_.m_128451_("HomePosX");
        int $$2 = p_30162_.m_128451_("HomePosY");
        int $$3 = p_30162_.m_128451_("HomePosZ");
        this.m_30219_(new BlockPos($$1, $$2, $$3));
        super.m_7378_(p_30162_);
        this.m_30234_(p_30162_.m_128471_("HasEgg"));
        int $$4 = p_30162_.m_128451_("TravelPosX");
        int $$5 = p_30162_.m_128451_("TravelPosY");
        int $$6 = p_30162_.m_128451_("TravelPosZ");
        this.m_30223_(new BlockPos($$4, $$5, $$6));
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30153_, DifficultyInstance p_30154_, MobSpawnType p_30155_, @Nullable SpawnGroupData p_30156_, @Nullable CompoundTag p_30157_) {
        this.m_30219_(this.m_20183_());
        this.m_30223_(BlockPos.f_121853_);
        return super.m_6518_(p_30153_, p_30154_, p_30155_, p_30156_, p_30157_);
    }

    public static boolean m_218276_(EntityType<Turtle> p_218277_, LevelAccessor p_218278_, MobSpawnType p_218279_, BlockPos p_218280_, RandomSource p_218281_) {
        return p_218280_.m_123342_() < p_218278_.m_5736_() + 4 && TurtleEggBlock.m_57762_(p_218278_, p_218280_) && Turtle.m_186209_(p_218278_, p_218280_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(0, new TurtlePanicGoal(this, 1.2));
        this.f_21345_.m_25352_(1, new TurtleBreedGoal(this, 1.0));
        this.f_21345_.m_25352_(1, new TurtleLayEggGoal(this, 1.0));
        this.f_21345_.m_25352_(2, new TemptGoal(this, 1.1, f_149066_, false));
        this.f_21345_.m_25352_(3, new TurtleGoToWaterGoal(this, 1.0));
        this.f_21345_.m_25352_(4, new TurtleGoHomeGoal(this, 1.0));
        this.f_21345_.m_25352_(7, new TurtleTravelGoal(this, 1.0));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(9, new TurtleRandomStrollGoal(this, 1.0, 100));
    }

    public static AttributeSupplier.Builder m_30207_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0).m_22268_(Attributes.f_22279_, 0.25);
    }

    @Override
    public boolean m_6063_() {
        return false;
    }

    @Override
    public boolean m_6040_() {
        return true;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21644_;
    }

    @Override
    public int m_8100_() {
        return 200;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        if (!this.m_20069_() && this.f_19861_ && !this.m_6162_()) {
            return SoundEvents.f_12530_;
        }
        return super.m_7515_();
    }

    @Override
    protected void m_5625_(float p_30192_) {
        super.m_5625_(p_30192_ * 1.5f);
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_12489_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_30202_) {
        if (this.m_6162_()) {
            return SoundEvents.f_12485_;
        }
        return SoundEvents.f_12536_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        if (this.m_6162_()) {
            return SoundEvents.f_12532_;
        }
        return SoundEvents.f_12531_;
    }

    @Override
    protected void m_7355_(BlockPos p_30173_, BlockState p_30174_) {
        SoundEvent $$2 = this.m_6162_() ? SoundEvents.f_12488_ : SoundEvents.f_12487_;
        this.m_5496_($$2, 0.15f, 1.0f);
    }

    @Override
    public boolean m_5957_() {
        return super.m_5957_() && !this.m_30205_();
    }

    @Override
    protected float m_6059_() {
        return this.f_19788_ + 0.15f;
    }

    @Override
    public float m_6134_() {
        return this.m_6162_() ? 0.3f : 1.0f;
    }

    @Override
    protected PathNavigation m_6037_(Level p_30171_) {
        return new TurtlePathNavigation(this, p_30171_);
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_149068_, AgeableMob p_149069_) {
        return EntityType.f_20490_.m_20615_(p_149068_);
    }

    @Override
    public boolean m_6898_(ItemStack p_30231_) {
        return p_30231_.m_150930_(Blocks.f_50037_.m_5456_());
    }

    @Override
    public float m_5610_(BlockPos p_30159_, LevelReader p_30160_) {
        if (!this.m_30211_() && p_30160_.m_6425_(p_30159_).m_205070_(FluidTags.f_13131_)) {
            return 10.0f;
        }
        if (TurtleEggBlock.m_57762_(p_30160_, p_30159_)) {
            return 10.0f;
        }
        return p_30160_.m_220419_(p_30159_);
    }

    @Override
    public void m_8107_() {
        BlockPos $$0;
        super.m_8107_();
        if (this.m_6084_() && this.m_30206_() && this.f_30129_ >= 1 && this.f_30129_ % 5 == 0 && TurtleEggBlock.m_57762_(this.f_19853_, $$0 = this.m_20183_())) {
            this.f_19853_.m_46796_(2001, $$0, Block.m_49956_(this.f_19853_.m_8055_($$0.m_7495_())));
        }
    }

    @Override
    protected void m_30232_() {
        super.m_30232_();
        if (!this.m_6162_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.m_20000_(Items.f_42355_, 1);
        }
    }

    @Override
    public void m_7023_(Vec3 p_30218_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(0.1f, p_30218_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (!(this.m_5448_() != null || this.m_30211_() && this.m_30208_().m_203195_(this.m_20182_(), 20.0))) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
        } else {
            super.m_7023_(p_30218_);
        }
    }

    @Override
    public boolean m_6573_(Player p_30151_) {
        return false;
    }

    @Override
    public void m_8038_(ServerLevel p_30140_, LightningBolt p_30141_) {
        this.m_6469_(DamageSource.f_19306_, Float.MAX_VALUE);
    }

    static class TurtleMoveControl
    extends MoveControl {
        private final Turtle f_30284_;

        TurtleMoveControl(Turtle p_30286_) {
            super(p_30286_);
            this.f_30284_ = p_30286_;
        }

        private void m_30288_() {
            if (this.f_30284_.m_20069_()) {
                this.f_30284_.m_20256_(this.f_30284_.m_20184_().m_82520_(0.0, 0.005, 0.0));
                if (!this.f_30284_.m_30208_().m_203195_(this.f_30284_.m_20182_(), 16.0)) {
                    this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 2.0f, 0.08f));
                }
                if (this.f_30284_.m_6162_()) {
                    this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 3.0f, 0.06f));
                }
            } else if (this.f_30284_.f_19861_) {
                this.f_30284_.m_7910_(Math.max(this.f_30284_.m_6113_() / 2.0f, 0.06f));
            }
        }

        @Override
        public void m_8126_() {
            this.m_30288_();
            if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_30284_.m_21573_().m_26571_()) {
                this.f_30284_.m_7910_(0.0f);
                return;
            }
            double $$0 = this.f_24975_ - this.f_30284_.m_20185_();
            double $$1 = this.f_24976_ - this.f_30284_.m_20186_();
            double $$2 = this.f_24977_ - this.f_30284_.m_20189_();
            double $$3 = Math.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2);
            $$1 /= $$3;
            float $$4 = (float)(Mth.m_14136_($$2, $$0) * 57.2957763671875) - 90.0f;
            this.f_30284_.m_146922_(this.m_24991_(this.f_30284_.m_146908_(), $$4, 90.0f));
            this.f_30284_.f_20883_ = this.f_30284_.m_146908_();
            float $$5 = (float)(this.f_24978_ * this.f_30284_.m_21133_(Attributes.f_22279_));
            this.f_30284_.m_7910_(Mth.m_14179_(0.125f, this.f_30284_.m_6113_(), $$5));
            this.f_30284_.m_20256_(this.f_30284_.m_20184_().m_82520_(0.0, (double)this.f_30284_.m_6113_() * $$1 * 0.1, 0.0));
        }
    }

    static class TurtlePanicGoal
    extends PanicGoal {
        TurtlePanicGoal(Turtle p_30290_, double p_30291_) {
            super(p_30290_, p_30291_);
        }

        @Override
        public boolean m_8036_() {
            if (!this.m_202729_()) {
                return false;
            }
            BlockPos $$0 = this.m_198172_(this.f_25684_.f_19853_, this.f_25684_, 7);
            if ($$0 != null) {
                this.f_25686_ = $$0.m_123341_();
                this.f_25687_ = $$0.m_123342_();
                this.f_25688_ = $$0.m_123343_();
                return true;
            }
            return this.m_25702_();
        }
    }

    static class TurtleBreedGoal
    extends BreedGoal {
        private final Turtle f_30242_;

        TurtleBreedGoal(Turtle p_30244_, double p_30245_) {
            super(p_30244_, p_30245_);
            this.f_30242_ = p_30244_;
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && !this.f_30242_.m_30205_();
        }

        @Override
        protected void m_8026_() {
            ServerPlayer $$0 = this.f_25113_.m_27592_();
            if ($$0 == null && this.f_25115_.m_27592_() != null) {
                $$0 = this.f_25115_.m_27592_();
            }
            if ($$0 != null) {
                $$0.m_36220_(Stats.f_12937_);
                CriteriaTriggers.f_10581_.m_147278_($$0, this.f_25113_, this.f_25115_, null);
            }
            this.f_30242_.m_30234_(true);
            this.f_25113_.m_27594_();
            this.f_25115_.m_27594_();
            RandomSource $$1 = this.f_25113_.m_217043_();
            if (this.f_25114_.m_46469_().m_46207_(GameRules.f_46135_)) {
                this.f_25114_.m_7967_(new ExperienceOrb(this.f_25114_, this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), $$1.m_188503_(7) + 1));
            }
        }
    }

    static class TurtleLayEggGoal
    extends MoveToBlockGoal {
        private final Turtle f_30274_;

        TurtleLayEggGoal(Turtle p_30276_, double p_30277_) {
            super(p_30276_, p_30277_, 16);
            this.f_30274_ = p_30276_;
        }

        @Override
        public boolean m_8036_() {
            if (this.f_30274_.m_30205_() && this.f_30274_.m_30208_().m_203195_(this.f_30274_.m_20182_(), 9.0)) {
                return super.m_8036_();
            }
            return false;
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && this.f_30274_.m_30205_() && this.f_30274_.m_30208_().m_203195_(this.f_30274_.m_20182_(), 9.0);
        }

        @Override
        public void m_8037_() {
            super.m_8037_();
            BlockPos $$0 = this.f_30274_.m_20183_();
            if (!this.f_30274_.m_20069_() && this.m_25625_()) {
                if (this.f_30274_.f_30129_ < 1) {
                    this.f_30274_.m_30236_(true);
                } else if (this.f_30274_.f_30129_ > this.m_183277_(200)) {
                    Level $$1 = this.f_30274_.f_19853_;
                    $$1.m_5594_(null, $$0, SoundEvents.f_12486_, SoundSource.BLOCKS, 0.3f, 0.9f + $$1.f_46441_.m_188501_() * 0.2f);
                    $$1.m_7731_(this.f_25602_.m_7494_(), (BlockState)Blocks.f_50578_.m_49966_().m_61124_(TurtleEggBlock.f_57754_, this.f_30274_.f_19796_.m_188503_(4) + 1), 3);
                    this.f_30274_.m_30234_(false);
                    this.f_30274_.m_30236_(false);
                    this.f_30274_.m_27601_(600);
                }
                if (this.f_30274_.m_30206_()) {
                    ++this.f_30274_.f_30129_;
                }
            }
        }

        @Override
        protected boolean m_6465_(LevelReader p_30280_, BlockPos p_30281_) {
            if (!p_30280_.m_46859_(p_30281_.m_7494_())) {
                return false;
            }
            return TurtleEggBlock.m_57800_(p_30280_, p_30281_);
        }
    }

    static class TurtleGoToWaterGoal
    extends MoveToBlockGoal {
        private static final int f_149075_ = 1200;
        private final Turtle f_30260_;

        TurtleGoToWaterGoal(Turtle p_30262_, double p_30263_) {
            super(p_30262_, p_30262_.m_6162_() ? 2.0 : p_30263_, 24);
            this.f_30260_ = p_30262_;
            this.f_25603_ = -1;
        }

        @Override
        public boolean m_8045_() {
            return !this.f_30260_.m_20069_() && this.f_25601_ <= 1200 && this.m_6465_(this.f_30260_.f_19853_, this.f_25602_);
        }

        @Override
        public boolean m_8036_() {
            if (this.f_30260_.m_6162_() && !this.f_30260_.m_20069_()) {
                return super.m_8036_();
            }
            if (!(this.f_30260_.m_30211_() || this.f_30260_.m_20069_() || this.f_30260_.m_30205_())) {
                return super.m_8036_();
            }
            return false;
        }

        @Override
        public boolean m_8064_() {
            return this.f_25601_ % 160 == 0;
        }

        @Override
        protected boolean m_6465_(LevelReader p_30270_, BlockPos p_30271_) {
            return p_30270_.m_8055_(p_30271_).m_60713_(Blocks.f_49990_);
        }
    }

    static class TurtleGoHomeGoal
    extends Goal {
        private final Turtle f_30248_;
        private final double f_30249_;
        private boolean f_30250_;
        private int f_30251_;
        private static final int f_149074_ = 600;

        TurtleGoHomeGoal(Turtle p_30253_, double p_30254_) {
            this.f_30248_ = p_30253_;
            this.f_30249_ = p_30254_;
        }

        @Override
        public boolean m_8036_() {
            if (this.f_30248_.m_6162_()) {
                return false;
            }
            if (this.f_30248_.m_30205_()) {
                return true;
            }
            if (this.f_30248_.m_217043_().m_188503_(TurtleGoHomeGoal.m_186073_(700)) != 0) {
                return false;
            }
            return !this.f_30248_.m_30208_().m_203195_(this.f_30248_.m_20182_(), 64.0);
        }

        @Override
        public void m_8056_() {
            this.f_30248_.m_30238_(true);
            this.f_30250_ = false;
            this.f_30251_ = 0;
        }

        @Override
        public void m_8041_() {
            this.f_30248_.m_30238_(false);
        }

        @Override
        public boolean m_8045_() {
            return !this.f_30248_.m_30208_().m_203195_(this.f_30248_.m_20182_(), 7.0) && !this.f_30250_ && this.f_30251_ <= this.m_183277_(600);
        }

        @Override
        public void m_8037_() {
            BlockPos $$0 = this.f_30248_.m_30208_();
            boolean $$1 = $$0.m_203195_(this.f_30248_.m_20182_(), 16.0);
            if ($$1) {
                ++this.f_30251_;
            }
            if (this.f_30248_.m_21573_().m_26571_()) {
                Vec3 $$2 = Vec3.m_82539_($$0);
                Vec3 $$3 = DefaultRandomPos.m_148412_(this.f_30248_, 16, 3, $$2, 0.3141592741012573);
                if ($$3 == null) {
                    $$3 = DefaultRandomPos.m_148412_(this.f_30248_, 8, 7, $$2, 1.5707963705062866);
                }
                if ($$3 != null && !$$1 && !this.f_30248_.f_19853_.m_8055_(new BlockPos($$3)).m_60713_(Blocks.f_49990_)) {
                    $$3 = DefaultRandomPos.m_148412_(this.f_30248_, 16, 5, $$2, 1.5707963705062866);
                }
                if ($$3 == null) {
                    this.f_30250_ = true;
                    return;
                }
                this.f_30248_.m_21573_().m_26519_($$3.f_82479_, $$3.f_82480_, $$3.f_82481_, this.f_30249_);
            }
        }
    }

    static class TurtleTravelGoal
    extends Goal {
        private final Turtle f_30329_;
        private final double f_30330_;
        private boolean f_30331_;

        TurtleTravelGoal(Turtle p_30333_, double p_30334_) {
            this.f_30329_ = p_30333_;
            this.f_30330_ = p_30334_;
        }

        @Override
        public boolean m_8036_() {
            return !this.f_30329_.m_30211_() && !this.f_30329_.m_30205_() && this.f_30329_.m_20069_();
        }

        @Override
        public void m_8056_() {
            int $$0 = 512;
            int $$1 = 4;
            RandomSource $$2 = this.f_30329_.f_19796_;
            int $$3 = $$2.m_188503_(1025) - 512;
            int $$4 = $$2.m_188503_(9) - 4;
            int $$5 = $$2.m_188503_(1025) - 512;
            if ((double)$$4 + this.f_30329_.m_20186_() > (double)(this.f_30329_.f_19853_.m_5736_() - 1)) {
                $$4 = 0;
            }
            BlockPos $$6 = new BlockPos((double)$$3 + this.f_30329_.m_20185_(), (double)$$4 + this.f_30329_.m_20186_(), (double)$$5 + this.f_30329_.m_20189_());
            this.f_30329_.m_30223_($$6);
            this.f_30329_.m_30240_(true);
            this.f_30331_ = false;
        }

        @Override
        public void m_8037_() {
            if (this.f_30329_.m_21573_().m_26571_()) {
                Vec3 $$0 = Vec3.m_82539_(this.f_30329_.m_30209_());
                Vec3 $$1 = DefaultRandomPos.m_148412_(this.f_30329_, 16, 3, $$0, 0.3141592741012573);
                if ($$1 == null) {
                    $$1 = DefaultRandomPos.m_148412_(this.f_30329_, 8, 7, $$0, 1.5707963705062866);
                }
                if ($$1 != null) {
                    int $$2 = Mth.m_14107_($$1.f_82479_);
                    int $$3 = Mth.m_14107_($$1.f_82481_);
                    int $$4 = 34;
                    if (!this.f_30329_.f_19853_.m_151572_($$2 - 34, $$3 - 34, $$2 + 34, $$3 + 34)) {
                        $$1 = null;
                    }
                }
                if ($$1 == null) {
                    this.f_30331_ = true;
                    return;
                }
                this.f_30329_.m_21573_().m_26519_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_, this.f_30330_);
            }
        }

        @Override
        public boolean m_8045_() {
            return !this.f_30329_.m_21573_().m_26571_() && !this.f_30331_ && !this.f_30329_.m_30211_() && !this.f_30329_.m_27593_() && !this.f_30329_.m_30205_();
        }

        @Override
        public void m_8041_() {
            this.f_30329_.m_30240_(false);
            super.m_8041_();
        }
    }

    static class TurtleRandomStrollGoal
    extends RandomStrollGoal {
        private final Turtle f_30301_;

        TurtleRandomStrollGoal(Turtle p_30303_, double p_30304_, int p_30305_) {
            super(p_30303_, p_30304_, p_30305_);
            this.f_30301_ = p_30303_;
        }

        @Override
        public boolean m_8036_() {
            if (!(this.f_25725_.m_20069_() || this.f_30301_.m_30211_() || this.f_30301_.m_30205_())) {
                return super.m_8036_();
            }
            return false;
        }
    }

    static class TurtlePathNavigation
    extends AmphibiousPathNavigation {
        TurtlePathNavigation(Turtle p_30294_, Level p_30295_) {
            super(p_30294_, p_30295_);
        }

        @Override
        public boolean m_6342_(BlockPos p_30300_) {
            Turtle $$1;
            Mob mob = this.f_26494_;
            if (mob instanceof Turtle && ($$1 = (Turtle)mob).m_30212_()) {
                return this.f_26495_.m_8055_(p_30300_).m_60713_(Blocks.f_49990_);
            }
            return !this.f_26495_.m_8055_(p_30300_.m_7495_()).m_60795_();
        }
    }
}

