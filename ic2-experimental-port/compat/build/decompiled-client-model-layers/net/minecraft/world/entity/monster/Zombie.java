/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Zombie
extends Monster {
    private static final UUID f_34259_ = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier f_34267_ = new AttributeModifier(f_34259_, "Baby speed boost", 0.5, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final EntityDataAccessor<Boolean> f_34268_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_34260_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_34261_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135035_);
    public static final float f_149884_ = 0.05f;
    public static final int f_149880_ = 50;
    public static final int f_149881_ = 40;
    public static final int f_149882_ = 7;
    private static final float f_149883_ = 0.1f;
    private static final Predicate<Difficulty> f_34262_ = p_34284_ -> p_34284_ == Difficulty.HARD;
    private final BreakDoorGoal f_34263_ = new BreakDoorGoal(this, f_34262_);
    private boolean f_34264_;
    private int f_34265_;
    private int f_34266_;

    public Zombie(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super((EntityType<? extends Monster>)p_34271_, p_34272_);
    }

    public Zombie(Level p_34274_) {
        this((EntityType<? extends Zombie>)EntityType.f_20501_, p_34274_);
    }

    @Override
    protected void m_8099_() {
        this.f_21345_.m_25352_(4, new ZombieAttackTurtleEggGoal((PathfinderMob)this, 1.0, 3));
        this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
        this.m_6878_();
    }

    protected void m_6878_() {
        this.f_21345_.m_25352_(2, new ZombieAttackGoal(this, 1.0, false));
        this.f_21345_.m_25352_(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::m_34330_));
        this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(ZombifiedPiglin.class));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, false));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
        this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal<Turtle>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
    }

    public static AttributeSupplier.Builder m_34328_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22277_, 35.0).m_22268_(Attributes.f_22279_, 0.23f).m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22284_, 2.0).m_22266_(Attributes.f_22287_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.m_20088_().m_135372_(f_34268_, false);
        this.m_20088_().m_135372_(f_34260_, 0);
        this.m_20088_().m_135372_(f_34261_, false);
    }

    public boolean m_34329_() {
        return this.m_20088_().m_135370_(f_34261_);
    }

    public boolean m_34330_() {
        return this.f_34264_;
    }

    public void m_34336_(boolean p_34337_) {
        if (this.m_7586_() && GoalUtils.m_26894_(this)) {
            if (this.f_34264_ != p_34337_) {
                this.f_34264_ = p_34337_;
                ((GroundPathNavigation)this.m_21573_()).m_26477_(p_34337_);
                if (p_34337_) {
                    this.f_21345_.m_25352_(1, this.f_34263_);
                } else {
                    this.f_21345_.m_25363_(this.f_34263_);
                }
            }
        } else if (this.f_34264_) {
            this.f_21345_.m_25363_(this.f_34263_);
            this.f_34264_ = false;
        }
    }

    protected boolean m_7586_() {
        return true;
    }

    @Override
    public boolean m_6162_() {
        return this.m_20088_().m_135370_(f_34268_);
    }

    @Override
    public int m_213860_() {
        if (this.m_6162_()) {
            this.f_21364_ = (int)((double)this.f_21364_ * 2.5);
        }
        return super.m_213860_();
    }

    @Override
    public void m_6863_(boolean p_34309_) {
        this.m_20088_().m_135381_(f_34268_, p_34309_);
        if (this.f_19853_ != null && !this.f_19853_.f_46443_) {
            AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
            $$1.m_22130_(f_34267_);
            if (p_34309_) {
                $$1.m_22118_(f_34267_);
            }
        }
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_34307_) {
        if (f_34268_.equals(p_34307_)) {
            this.m_6210_();
        }
        super.m_7350_(p_34307_);
    }

    protected boolean m_7593_() {
        return true;
    }

    @Override
    public void m_8119_() {
        if (!this.f_19853_.f_46443_ && this.m_6084_() && !this.m_21525_()) {
            if (this.m_34329_()) {
                --this.f_34266_;
                if (this.f_34266_ < 0) {
                    this.m_7595_();
                }
            } else if (this.m_7593_()) {
                if (this.m_204029_(FluidTags.f_13131_)) {
                    ++this.f_34265_;
                    if (this.f_34265_ >= 600) {
                        this.m_34278_(300);
                    }
                } else {
                    this.f_34265_ = -1;
                }
            }
        }
        super.m_8119_();
    }

    @Override
    public void m_8107_() {
        if (this.m_6084_()) {
            boolean $$0;
            boolean bl = $$0 = this.m_5884_() && this.m_21527_();
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
        }
        super.m_8107_();
    }

    private void m_34278_(int p_34279_) {
        this.f_34266_ = p_34279_;
        this.m_20088_().m_135381_(f_34261_, true);
    }

    protected void m_7595_() {
        this.m_34310_(EntityType.f_20562_);
        if (!this.m_20067_()) {
            this.f_19853_.m_5898_(null, 1040, this.m_20183_(), 0);
        }
    }

    protected void m_34310_(EntityType<? extends Zombie> p_34311_) {
        Zombie $$1 = this.m_21406_(p_34311_, true);
        if ($$1 != null) {
            $$1.m_34339_($$1.f_19853_.m_6436_($$1.m_20183_()).m_19057_());
            $$1.m_34336_($$1.m_7586_() && this.m_34330_());
        }
    }

    protected boolean m_5884_() {
        return true;
    }

    @Override
    public boolean m_6469_(DamageSource p_34288_, float p_34289_) {
        if (!super.m_6469_(p_34288_, p_34289_)) {
            return false;
        }
        if (!(this.f_19853_ instanceof ServerLevel)) {
            return false;
        }
        ServerLevel $$2 = (ServerLevel)this.f_19853_;
        LivingEntity $$3 = this.m_5448_();
        if ($$3 == null && p_34288_.m_7639_() instanceof LivingEntity) {
            $$3 = (LivingEntity)p_34288_.m_7639_();
        }
        if ($$3 != null && this.f_19853_.m_46791_() == Difficulty.HARD && (double)this.f_19796_.m_188501_() < this.m_21133_(Attributes.f_22287_) && this.f_19853_.m_46469_().m_46207_(GameRules.f_46134_)) {
            int $$4 = Mth.m_14107_(this.m_20185_());
            int $$5 = Mth.m_14107_(this.m_20186_());
            int $$6 = Mth.m_14107_(this.m_20189_());
            Zombie $$7 = new Zombie(this.f_19853_);
            for (int $$8 = 0; $$8 < 50; ++$$8) {
                int $$9 = $$4 + Mth.m_216271_(this.f_19796_, 7, 40) * Mth.m_216271_(this.f_19796_, -1, 1);
                int $$10 = $$5 + Mth.m_216271_(this.f_19796_, 7, 40) * Mth.m_216271_(this.f_19796_, -1, 1);
                int $$11 = $$6 + Mth.m_216271_(this.f_19796_, 7, 40) * Mth.m_216271_(this.f_19796_, -1, 1);
                BlockPos $$12 = new BlockPos($$9, $$10, $$11);
                EntityType<?> $$13 = $$7.m_6095_();
                SpawnPlacements.Type $$14 = SpawnPlacements.m_21752_($$13);
                if (!NaturalSpawner.m_47051_($$14, this.f_19853_, $$12, $$13) || !SpawnPlacements.m_217074_($$13, $$2, MobSpawnType.REINFORCEMENT, $$12, this.f_19853_.f_46441_)) continue;
                $$7.m_6034_($$9, $$10, $$11);
                if (this.f_19853_.m_45914_($$9, $$10, $$11, 7.0) || !this.f_19853_.m_45784_($$7) || !this.f_19853_.m_45786_($$7) || this.f_19853_.m_46855_($$7.m_20191_())) continue;
                $$7.m_6710_($$3);
                $$7.m_6518_($$2, this.f_19853_.m_6436_($$7.m_20183_()), MobSpawnType.REINFORCEMENT, null, null);
                $$2.m_47205_($$7);
                this.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Zombie reinforcement caller charge", -0.05f, AttributeModifier.Operation.ADDITION));
                $$7.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Zombie reinforcement callee charge", -0.05f, AttributeModifier.Operation.ADDITION));
                break;
            }
        }
        return true;
    }

    @Override
    public boolean m_7327_(Entity p_34276_) {
        boolean $$1 = super.m_7327_(p_34276_);
        if ($$1) {
            float $$2 = this.f_19853_.m_6436_(this.m_20183_()).m_19056_();
            if (this.m_21205_().m_41619_() && this.m_6060_() && this.f_19796_.m_188501_() < $$2 * 0.3f) {
                p_34276_.m_20254_(2 * (int)$$2);
            }
        }
        return $$1;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12598_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34327_) {
        return SoundEvents.f_12608_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12603_;
    }

    protected SoundEvent m_7660_() {
        return SoundEvents.f_12614_;
    }

    @Override
    protected void m_7355_(BlockPos p_34316_, BlockState p_34317_) {
        this.m_5496_(this.m_7660_(), 0.15f, 1.0f);
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    protected void m_213945_(RandomSource p_219165_, DifficultyInstance p_219166_) {
        super.m_213945_(p_219165_, p_219166_);
        float f = p_219165_.m_188501_();
        float f2 = this.f_19853_.m_46791_() == Difficulty.HARD ? 0.05f : 0.01f;
        if (f < f2) {
            int $$2 = p_219165_.m_188503_(3);
            if ($$2 == 0) {
                this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42383_));
            } else {
                this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42384_));
            }
        }
    }

    @Override
    public void m_7380_(CompoundTag p_34319_) {
        super.m_7380_(p_34319_);
        p_34319_.m_128379_("IsBaby", this.m_6162_());
        p_34319_.m_128379_("CanBreakDoors", this.m_34330_());
        p_34319_.m_128405_("InWaterTime", this.m_20069_() ? this.f_34265_ : -1);
        p_34319_.m_128405_("DrownedConversionTime", this.m_34329_() ? this.f_34266_ : -1);
    }

    @Override
    public void m_7378_(CompoundTag p_34305_) {
        super.m_7378_(p_34305_);
        this.m_6863_(p_34305_.m_128471_("IsBaby"));
        this.m_34336_(p_34305_.m_128471_("CanBreakDoors"));
        this.f_34265_ = p_34305_.m_128451_("InWaterTime");
        if (p_34305_.m_128425_("DrownedConversionTime", 99) && p_34305_.m_128451_("DrownedConversionTime") > -1) {
            this.m_34278_(p_34305_.m_128451_("DrownedConversionTime"));
        }
    }

    @Override
    public boolean m_214076_(ServerLevel p_219160_, LivingEntity p_219161_) {
        boolean $$2 = super.m_214076_(p_219160_, p_219161_);
        if ((p_219160_.m_46791_() == Difficulty.NORMAL || p_219160_.m_46791_() == Difficulty.HARD) && p_219161_ instanceof Villager) {
            if (p_219160_.m_46791_() != Difficulty.HARD && this.f_19796_.m_188499_()) {
                return $$2;
            }
            Villager $$3 = (Villager)p_219161_;
            ZombieVillager $$4 = $$3.m_21406_(EntityType.f_20530_, false);
            $$4.m_6518_(p_219160_, p_219160_.m_6436_($$4.m_20183_()), MobSpawnType.CONVERSION, new ZombieGroupData(false, true), null);
            $$4.m_34375_($$3.m_7141_());
            $$4.m_34391_((Tag)$$3.m_35517_().m_26179_(NbtOps.f_128958_).getValue());
            $$4.m_34411_($$3.m_6616_().m_45388_());
            $$4.m_34373_($$3.m_7809_());
            if (!this.m_20067_()) {
                p_219160_.m_5898_(null, 1026, this.m_20183_(), 0);
            }
            $$2 = false;
        }
        return $$2;
    }

    @Override
    protected float m_6431_(Pose p_34313_, EntityDimensions p_34314_) {
        return this.m_6162_() ? 0.93f : 1.74f;
    }

    @Override
    public boolean m_7252_(ItemStack p_34332_) {
        if (p_34332_.m_150930_(Items.f_42521_) && this.m_6162_() && this.m_20159_()) {
            return false;
        }
        return super.m_7252_(p_34332_);
    }

    @Override
    public boolean m_7243_(ItemStack p_182400_) {
        if (p_182400_.m_150930_(Items.f_151056_)) {
            return false;
        }
        return super.m_7243_(p_182400_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
        RandomSource $$5 = p_34297_.m_213780_();
        p_34300_ = super.m_6518_(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
        float $$6 = p_34298_.m_19057_();
        this.m_21553_($$5.m_188501_() < 0.55f * $$6);
        if (p_34300_ == null) {
            p_34300_ = new ZombieGroupData(Zombie.m_219162_($$5), true);
        }
        if (p_34300_ instanceof ZombieGroupData) {
            ZombieGroupData $$7 = (ZombieGroupData)p_34300_;
            if ($$7.f_34354_) {
                this.m_6863_(true);
                if ($$7.f_34355_) {
                    if ((double)$$5.m_188501_() < 0.05) {
                        List<Entity> $$8 = p_34297_.m_6443_(Chicken.class, this.m_20191_().m_82377_(5.0, 3.0, 5.0), EntitySelector.f_20404_);
                        if (!$$8.isEmpty()) {
                            Chicken $$9 = (Chicken)$$8.get(0);
                            $$9.m_28273_(true);
                            this.m_20329_($$9);
                        }
                    } else if ((double)$$5.m_188501_() < 0.05) {
                        Chicken $$10 = EntityType.f_20555_.m_20615_(this.f_19853_);
                        $$10.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0f);
                        $$10.m_6518_(p_34297_, p_34298_, MobSpawnType.JOCKEY, null, null);
                        $$10.m_28273_(true);
                        this.m_20329_($$10);
                        p_34297_.m_7967_($$10);
                    }
                }
            }
            this.m_34336_(this.m_7586_() && $$5.m_188501_() < $$6 * 0.1f);
            this.m_213945_($$5, p_34298_);
            this.m_213946_($$5, p_34298_);
        }
        if (this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            LocalDate $$11 = LocalDate.now();
            int $$12 = $$11.get(ChronoField.DAY_OF_MONTH);
            int $$13 = $$11.get(ChronoField.MONTH_OF_YEAR);
            if ($$13 == 10 && $$12 == 31 && $$5.m_188501_() < 0.25f) {
                this.m_8061_(EquipmentSlot.HEAD, new ItemStack($$5.m_188501_() < 0.1f ? Blocks.f_50144_ : Blocks.f_50143_));
                this.f_21348_[EquipmentSlot.HEAD.m_20749_()] = 0.0f;
            }
        }
        this.m_34339_($$6);
        return p_34300_;
    }

    public static boolean m_219162_(RandomSource p_219163_) {
        return p_219163_.m_188501_() < 0.05f;
    }

    protected void m_34339_(float p_34340_) {
        this.m_7572_();
        this.m_21051_(Attributes.f_22278_).m_22125_(new AttributeModifier("Random spawn bonus", this.f_19796_.m_188500_() * (double)0.05f, AttributeModifier.Operation.ADDITION));
        double $$1 = this.f_19796_.m_188500_() * 1.5 * (double)p_34340_;
        if ($$1 > 1.0) {
            this.m_21051_(Attributes.f_22277_).m_22125_(new AttributeModifier("Random zombie-spawn bonus", $$1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (this.f_19796_.m_188501_() < p_34340_ * 0.05f) {
            this.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Leader zombie bonus", this.f_19796_.m_188500_() * 0.25 + 0.5, AttributeModifier.Operation.ADDITION));
            this.m_21051_(Attributes.f_22276_).m_22125_(new AttributeModifier("Leader zombie bonus", this.f_19796_.m_188500_() * 3.0 + 1.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
            this.m_34336_(this.m_7586_());
        }
    }

    protected void m_7572_() {
        this.m_21051_(Attributes.f_22287_).m_22100_(this.f_19796_.m_188500_() * (double)0.1f);
    }

    @Override
    public double m_6049_() {
        return this.m_6162_() ? 0.0 : -0.45;
    }

    @Override
    protected void m_7472_(DamageSource p_34291_, int p_34292_, boolean p_34293_) {
        ItemStack $$5;
        Creeper $$4;
        super.m_7472_(p_34291_, p_34292_, p_34293_);
        Entity $$3 = p_34291_.m_7639_();
        if ($$3 instanceof Creeper && ($$4 = (Creeper)$$3).m_32313_() && !($$5 = this.m_5728_()).m_41619_()) {
            $$4.m_32314_();
            this.m_19983_($$5);
        }
    }

    protected ItemStack m_5728_() {
        return new ItemStack(Items.f_42681_);
    }

    class ZombieAttackTurtleEggGoal
    extends RemoveBlockGoal {
        ZombieAttackTurtleEggGoal(PathfinderMob p_34344_, double p_34345_, int p_34346_) {
            super(Blocks.f_50578_, p_34344_, p_34345_, p_34346_);
        }

        @Override
        public void m_7659_(LevelAccessor p_34351_, BlockPos p_34352_) {
            p_34351_.m_5594_(null, p_34352_, SoundEvents.f_12604_, SoundSource.HOSTILE, 0.5f, 0.9f + Zombie.this.f_19796_.m_188501_() * 0.2f);
        }

        @Override
        public void m_5777_(Level p_34348_, BlockPos p_34349_) {
            p_34348_.m_5594_(null, p_34349_, SoundEvents.f_12533_, SoundSource.BLOCKS, 0.7f, 0.9f + p_34348_.f_46441_.m_188501_() * 0.2f);
        }

        @Override
        public double m_8052_() {
            return 1.14;
        }
    }

    public static class ZombieGroupData
    implements SpawnGroupData {
        public final boolean f_34354_;
        public final boolean f_34355_;

        public ZombieGroupData(boolean p_34357_, boolean p_34358_) {
            this.f_34354_ = p_34357_;
            this.f_34355_ = p_34358_;
        }
    }
}

