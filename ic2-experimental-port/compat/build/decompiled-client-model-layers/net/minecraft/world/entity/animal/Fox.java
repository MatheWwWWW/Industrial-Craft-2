/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Fox
extends Animal {
    private static final EntityDataAccessor<Integer> f_28437_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Byte> f_28438_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135027_);
    private static final int f_148899_ = 1;
    public static final int f_148896_ = 4;
    public static final int f_148897_ = 8;
    public static final int f_148898_ = 16;
    private static final int f_148900_ = 32;
    private static final int f_148901_ = 64;
    private static final int f_148902_ = 128;
    private static final EntityDataAccessor<Optional<UUID>> f_28439_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135041_);
    private static final EntityDataAccessor<Optional<UUID>> f_28440_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135041_);
    static final Predicate<ItemEntity> f_28441_ = p_28528_ -> !p_28528_.m_32063_() && p_28528_.m_6084_();
    private static final Predicate<Entity> f_28442_ = p_28521_ -> {
        if (p_28521_ instanceof LivingEntity) {
            LivingEntity $$1 = (LivingEntity)p_28521_;
            return $$1.m_21214_() != null && $$1.m_21215_() < $$1.f_19797_ + 600;
        }
        return false;
    };
    static final Predicate<Entity> f_28443_ = p_28498_ -> p_28498_ instanceof Chicken || p_28498_ instanceof Rabbit;
    private static final Predicate<Entity> f_28444_ = p_28463_ -> !p_28463_.m_20163_() && EntitySelector.f_20406_.test((Entity)p_28463_);
    private static final int f_148903_ = 600;
    private Goal f_28445_;
    private Goal f_28446_;
    private Goal f_28447_;
    private float f_28448_;
    private float f_28433_;
    float f_28434_;
    float f_28435_;
    private int f_28436_;

    public Fox(EntityType<? extends Fox> p_28451_, Level p_28452_) {
        super((EntityType<? extends Animal>)p_28451_, p_28452_);
        this.f_21365_ = new FoxLookControl();
        this.f_21342_ = new FoxMoveControl();
        this.m_21441_(BlockPathTypes.DANGER_OTHER, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_OTHER, 0.0f);
        this.m_21553_(true);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_28439_, Optional.empty());
        this.f_19804_.m_135372_(f_28440_, Optional.empty());
        this.f_19804_.m_135372_(f_28437_, 0);
        this.f_19804_.m_135372_(f_28438_, (byte)0);
    }

    @Override
    protected void m_8099_() {
        this.f_28445_ = new NearestAttackableTargetGoal<Animal>(this, Animal.class, 10, false, false, p_28604_ -> p_28604_ instanceof Chicken || p_28604_ instanceof Rabbit);
        this.f_28446_ = new NearestAttackableTargetGoal<Turtle>(this, Turtle.class, 10, false, false, Turtle.f_30122_);
        this.f_28447_ = new NearestAttackableTargetGoal<AbstractFish>(this, AbstractFish.class, 20, false, false, p_28600_ -> p_28600_ instanceof AbstractSchoolingFish);
        this.f_21345_.m_25352_(0, new FoxFloatGoal());
        this.f_21345_.m_25352_(0, new ClimbOnTopOfPowderSnowGoal(this, this.f_19853_));
        this.f_21345_.m_25352_(1, new FaceplantGoal());
        this.f_21345_.m_25352_(2, new FoxPanicGoal(2.2));
        this.f_21345_.m_25352_(3, new FoxBreedGoal(1.0));
        this.f_21345_.m_25352_(4, new AvoidEntityGoal<Player>(this, Player.class, 16.0f, 1.6, 1.4, p_28596_ -> f_28444_.test((Entity)p_28596_) && !this.m_28529_(p_28596_.m_20148_()) && !this.m_28567_()));
        this.f_21345_.m_25352_(4, new AvoidEntityGoal<Wolf>(this, Wolf.class, 8.0f, 1.6, 1.4, p_28590_ -> !((Wolf)p_28590_).m_21824_() && !this.m_28567_()));
        this.f_21345_.m_25352_(4, new AvoidEntityGoal<PolarBear>(this, PolarBear.class, 8.0f, 1.6, 1.4, p_28585_ -> !this.m_28567_()));
        this.f_21345_.m_25352_(5, new StalkPreyGoal());
        this.f_21345_.m_25352_(6, new FoxPounceGoal());
        this.f_21345_.m_25352_(6, new SeekShelterGoal(1.25));
        this.f_21345_.m_25352_(7, new FoxMeleeAttackGoal((double)1.2f, true));
        this.f_21345_.m_25352_(7, new SleepGoal());
        this.f_21345_.m_25352_(8, new FoxFollowParentGoal(this, 1.25));
        this.f_21345_.m_25352_(9, new FoxStrollThroughVillageGoal(32, 200));
        this.f_21345_.m_25352_(10, new FoxEatBerriesGoal((double)1.2f, 12, 1));
        this.f_21345_.m_25352_(10, new LeapAtTargetGoal(this, 0.4f));
        this.f_21345_.m_25352_(11, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.f_21345_.m_25352_(11, new FoxSearchForItemsGoal());
        this.f_21345_.m_25352_(12, new FoxLookAtPlayerGoal(this, Player.class, 24.0f));
        this.f_21345_.m_25352_(13, new PerchAndSearchGoal());
        this.f_21346_.m_25352_(3, new DefendTrustedTargetGoal(LivingEntity.class, false, false, p_28580_ -> f_28442_.test((Entity)p_28580_) && !this.m_28529_(p_28580_.m_20148_())));
    }

    @Override
    public SoundEvent m_7866_(ItemStack p_28540_) {
        return SoundEvents.f_11947_;
    }

    @Override
    public void m_8107_() {
        if (!this.f_19853_.f_46443_ && this.m_6084_() && this.m_6142_()) {
            LivingEntity $$2;
            ++this.f_28436_;
            ItemStack $$0 = this.m_6844_(EquipmentSlot.MAINHAND);
            if (this.m_28597_($$0)) {
                if (this.f_28436_ > 600) {
                    ItemStack $$1 = $$0.m_41671_(this.f_19853_, this);
                    if (!$$1.m_41619_()) {
                        this.m_8061_(EquipmentSlot.MAINHAND, $$1);
                    }
                    this.f_28436_ = 0;
                } else if (this.f_28436_ > 560 && this.f_19796_.m_188501_() < 0.1f) {
                    this.m_5496_(this.m_7866_($$0), 1.0f, 1.0f);
                    this.f_19853_.m_7605_(this, (byte)45);
                }
            }
            if (($$2 = this.m_5448_()) == null || !$$2.m_6084_()) {
                this.m_28614_(false);
                this.m_28616_(false);
            }
        }
        if (this.m_5803_() || this.m_6107_()) {
            this.f_20899_ = false;
            this.f_20900_ = 0.0f;
            this.f_20902_ = 0.0f;
        }
        super.m_8107_();
        if (this.m_28567_() && this.f_19796_.m_188501_() < 0.05f) {
            this.m_5496_(SoundEvents.f_11943_, 1.0f, 1.0f);
        }
    }

    @Override
    protected boolean m_6107_() {
        return this.m_21224_();
    }

    private boolean m_28597_(ItemStack p_28598_) {
        return p_28598_.m_41720_().m_41472_() && this.m_5448_() == null && this.f_19861_ && !this.m_5803_();
    }

    @Override
    protected void m_213945_(RandomSource p_218171_, DifficultyInstance p_218172_) {
        if (p_218171_.m_188501_() < 0.2f) {
            ItemStack $$8;
            float $$2 = p_218171_.m_188501_();
            if ($$2 < 0.05f) {
                ItemStack $$3 = new ItemStack(Items.f_42616_);
            } else if ($$2 < 0.2f) {
                ItemStack $$4 = new ItemStack(Items.f_42521_);
            } else if ($$2 < 0.4f) {
                ItemStack $$5 = p_218171_.m_188499_() ? new ItemStack(Items.f_42648_) : new ItemStack(Items.f_42649_);
            } else if ($$2 < 0.6f) {
                ItemStack $$6 = new ItemStack(Items.f_42405_);
            } else if ($$2 < 0.8f) {
                ItemStack $$7 = new ItemStack(Items.f_42454_);
            } else {
                $$8 = new ItemStack(Items.f_42402_);
            }
            this.m_8061_(EquipmentSlot.MAINHAND, $$8);
        }
    }

    @Override
    public void m_7822_(byte p_28456_) {
        if (p_28456_ == 45) {
            ItemStack $$1 = this.m_6844_(EquipmentSlot.MAINHAND);
            if (!$$1.m_41619_()) {
                for (int $$2 = 0; $$2 < 8; ++$$2) {
                    Vec3 $$3 = new Vec3(((double)this.f_19796_.m_188501_() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).m_82496_(-this.m_146909_() * ((float)Math.PI / 180)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180));
                    this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, $$1), this.m_20185_() + this.m_20154_().f_82479_ / 2.0, this.m_20186_(), this.m_20189_() + this.m_20154_().f_82481_ / 2.0, $$3.f_82479_, $$3.f_82480_ + 0.05, $$3.f_82481_);
                }
            }
        } else {
            super.m_7822_(p_28456_);
        }
    }

    public static AttributeSupplier.Builder m_28553_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22277_, 32.0).m_22268_(Attributes.f_22281_, 2.0);
    }

    @Override
    public Fox m_142606_(ServerLevel p_148912_, AgeableMob p_148913_) {
        Fox $$2 = EntityType.f_20452_.m_20615_(p_148912_);
        $$2.m_28464_(this.f_19796_.m_188499_() ? this.m_28554_() : ((Fox)p_148913_).m_28554_());
        return $$2;
    }

    public static boolean m_218175_(EntityType<Fox> p_218176_, LevelAccessor p_218177_, MobSpawnType p_218178_, BlockPos p_218179_, RandomSource p_218180_) {
        return p_218177_.m_8055_(p_218179_.m_7495_()).m_204336_(BlockTags.f_184235_) && Fox.m_186209_(p_218177_, p_218179_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_28487_, DifficultyInstance p_28488_, MobSpawnType p_28489_, @Nullable SpawnGroupData p_28490_, @Nullable CompoundTag p_28491_) {
        Holder<Biome> $$5 = p_28487_.m_204166_(this.m_20183_());
        Type $$6 = Type.m_204062_($$5);
        boolean $$7 = false;
        if (p_28490_ instanceof FoxGroupData) {
            FoxGroupData $$8 = (FoxGroupData)p_28490_;
            $$6 = $$8.f_28701_;
            if ($$8.m_146777_() >= 2) {
                $$7 = true;
            }
        } else {
            p_28490_ = new FoxGroupData($$6);
        }
        this.m_28464_($$6);
        if ($$7) {
            this.m_146762_(-24000);
        }
        if (p_28487_ instanceof ServerLevel) {
            this.m_28562_();
        }
        this.m_213945_(p_28487_.m_213780_(), p_28488_);
        return super.m_6518_(p_28487_, p_28488_, p_28489_, p_28490_, p_28491_);
    }

    private void m_28562_() {
        if (this.m_28554_() == Type.RED) {
            this.f_21346_.m_25352_(4, this.f_28445_);
            this.f_21346_.m_25352_(4, this.f_28446_);
            this.f_21346_.m_25352_(6, this.f_28447_);
        } else {
            this.f_21346_.m_25352_(4, this.f_28447_);
            this.f_21346_.m_25352_(6, this.f_28445_);
            this.f_21346_.m_25352_(6, this.f_28446_);
        }
    }

    @Override
    protected void m_142075_(Player p_148908_, InteractionHand p_148909_, ItemStack p_148910_) {
        if (this.m_6898_(p_148910_)) {
            this.m_5496_(this.m_7866_(p_148910_), 1.0f, 1.0f);
        }
        super.m_142075_(p_148908_, p_148909_, p_148910_);
    }

    @Override
    protected float m_6431_(Pose p_28500_, EntityDimensions p_28501_) {
        if (this.m_6162_()) {
            return p_28501_.f_20378_ * 0.85f;
        }
        return 0.4f;
    }

    public Type m_28554_() {
        return Type.m_28812_(this.f_19804_.m_135370_(f_28437_));
    }

    private void m_28464_(Type p_28465_) {
        this.f_19804_.m_135381_(f_28437_, p_28465_.m_28820_());
    }

    List<UUID> m_28566_() {
        ArrayList $$0 = Lists.newArrayList();
        $$0.add(this.f_19804_.m_135370_(f_28439_).orElse(null));
        $$0.add(this.f_19804_.m_135370_(f_28440_).orElse(null));
        return $$0;
    }

    void m_28515_(@Nullable UUID p_28516_) {
        if (this.f_19804_.m_135370_(f_28439_).isPresent()) {
            this.f_19804_.m_135381_(f_28440_, Optional.ofNullable(p_28516_));
        } else {
            this.f_19804_.m_135381_(f_28439_, Optional.ofNullable(p_28516_));
        }
    }

    @Override
    public void m_7380_(CompoundTag p_28518_) {
        super.m_7380_(p_28518_);
        List<UUID> $$1 = this.m_28566_();
        ListTag $$2 = new ListTag();
        for (UUID $$3 : $$1) {
            if ($$3 == null) continue;
            $$2.add(NbtUtils.m_129226_($$3));
        }
        p_28518_.m_128365_("Trusted", $$2);
        p_28518_.m_128379_("Sleeping", this.m_5803_());
        p_28518_.m_128359_("Type", this.m_28554_().m_28811_());
        p_28518_.m_128379_("Sitting", this.m_28555_());
        p_28518_.m_128379_("Crouching", this.m_6047_());
    }

    @Override
    public void m_7378_(CompoundTag p_28493_) {
        super.m_7378_(p_28493_);
        ListTag $$1 = p_28493_.m_128437_("Trusted", 11);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            this.m_28515_(NbtUtils.m_129233_($$1.get($$2)));
        }
        this.m_28626_(p_28493_.m_128471_("Sleeping"));
        this.m_28464_(Type.m_28816_(p_28493_.m_128461_("Type")));
        this.m_28610_(p_28493_.m_128471_("Sitting"));
        this.m_28614_(p_28493_.m_128471_("Crouching"));
        if (this.f_19853_ instanceof ServerLevel) {
            this.m_28562_();
        }
    }

    public boolean m_28555_() {
        return this.m_28608_(1);
    }

    public void m_28610_(boolean p_28611_) {
        this.m_28532_(1, p_28611_);
    }

    public boolean m_28556_() {
        return this.m_28608_(64);
    }

    void m_28618_(boolean p_28619_) {
        this.m_28532_(64, p_28619_);
    }

    boolean m_28567_() {
        return this.m_28608_(128);
    }

    void m_28622_(boolean p_28623_) {
        this.m_28532_(128, p_28623_);
    }

    @Override
    public boolean m_5803_() {
        return this.m_28608_(32);
    }

    void m_28626_(boolean p_28627_) {
        this.m_28532_(32, p_28627_);
    }

    private void m_28532_(int p_28533_, boolean p_28534_) {
        if (p_28534_) {
            this.f_19804_.m_135381_(f_28438_, (byte)(this.f_19804_.m_135370_(f_28438_) | p_28533_));
        } else {
            this.f_19804_.m_135381_(f_28438_, (byte)(this.f_19804_.m_135370_(f_28438_) & ~p_28533_));
        }
    }

    private boolean m_28608_(int p_28609_) {
        return (this.f_19804_.m_135370_(f_28438_) & p_28609_) != 0;
    }

    @Override
    public boolean m_7066_(ItemStack p_28552_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_28552_);
        if (!this.m_6844_($$1).m_41619_()) {
            return false;
        }
        return $$1 == EquipmentSlot.MAINHAND && super.m_7066_(p_28552_);
    }

    @Override
    public boolean m_7252_(ItemStack p_28578_) {
        Item $$1 = p_28578_.m_41720_();
        ItemStack $$2 = this.m_6844_(EquipmentSlot.MAINHAND);
        return $$2.m_41619_() || this.f_28436_ > 0 && $$1.m_41472_() && !$$2.m_41720_().m_41472_();
    }

    private void m_28601_(ItemStack p_28602_) {
        if (p_28602_.m_41619_() || this.f_19853_.f_46443_) {
            return;
        }
        ItemEntity $$1 = new ItemEntity(this.f_19853_, this.m_20185_() + this.m_20154_().f_82479_, this.m_20186_() + 1.0, this.m_20189_() + this.m_20154_().f_82481_, p_28602_);
        $$1.m_32010_(40);
        $$1.m_32052_(this.m_20148_());
        this.m_5496_(SoundEvents.f_11952_, 1.0f, 1.0f);
        this.f_19853_.m_7967_($$1);
    }

    private void m_28605_(ItemStack p_28606_) {
        ItemEntity $$1 = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_28606_);
        this.f_19853_.m_7967_($$1);
    }

    @Override
    protected void m_7581_(ItemEntity p_28514_) {
        ItemStack $$1 = p_28514_.m_32055_();
        if (this.m_7252_($$1)) {
            int $$2 = $$1.m_41613_();
            if ($$2 > 1) {
                this.m_28605_($$1.m_41620_($$2 - 1));
            }
            this.m_28601_(this.m_6844_(EquipmentSlot.MAINHAND));
            this.m_21053_(p_28514_);
            this.m_8061_(EquipmentSlot.MAINHAND, $$1.m_41620_(1));
            this.m_21508_(EquipmentSlot.MAINHAND);
            this.m_7938_(p_28514_, $$1.m_41613_());
            p_28514_.m_146870_();
            this.f_28436_ = 0;
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_6142_()) {
            boolean $$0 = this.m_20069_();
            if ($$0 || this.m_5448_() != null || this.f_19853_.m_46470_()) {
                this.m_28568_();
            }
            if ($$0 || this.m_5803_()) {
                this.m_28610_(false);
            }
            if (this.m_28556_() && this.f_19853_.f_46441_.m_188501_() < 0.2f) {
                BlockPos $$1 = this.m_20183_();
                BlockState $$2 = this.f_19853_.m_8055_($$1);
                this.f_19853_.m_46796_(2001, $$1, Block.m_49956_($$2));
            }
        }
        this.f_28433_ = this.f_28448_;
        this.f_28448_ = this.m_28559_() ? (this.f_28448_ += (1.0f - this.f_28448_) * 0.4f) : (this.f_28448_ += (0.0f - this.f_28448_) * 0.4f);
        this.f_28435_ = this.f_28434_;
        if (this.m_6047_()) {
            this.f_28434_ += 0.2f;
            if (this.f_28434_ > 3.0f) {
                this.f_28434_ = 3.0f;
            }
        } else {
            this.f_28434_ = 0.0f;
        }
    }

    @Override
    public boolean m_6898_(ItemStack p_28594_) {
        return p_28594_.m_204117_(ItemTags.f_144311_);
    }

    @Override
    protected void m_5502_(Player p_28481_, Mob p_28482_) {
        ((Fox)p_28482_).m_28515_(p_28481_.m_20148_());
    }

    public boolean m_28557_() {
        return this.m_28608_(16);
    }

    public void m_28612_(boolean p_28613_) {
        this.m_28532_(16, p_28613_);
    }

    public boolean m_148924_() {
        return this.f_20899_;
    }

    public boolean m_28558_() {
        return this.f_28434_ == 3.0f;
    }

    public void m_28614_(boolean p_28615_) {
        this.m_28532_(4, p_28615_);
    }

    @Override
    public boolean m_6047_() {
        return this.m_28608_(4);
    }

    public void m_28616_(boolean p_28617_) {
        this.m_28532_(8, p_28617_);
    }

    public boolean m_28559_() {
        return this.m_28608_(8);
    }

    public float m_28620_(float p_28621_) {
        return Mth.m_14179_(p_28621_, this.f_28433_, this.f_28448_) * 0.11f * (float)Math.PI;
    }

    public float m_28624_(float p_28625_) {
        return Mth.m_14179_(p_28625_, this.f_28435_, this.f_28434_);
    }

    @Override
    public void m_6710_(@Nullable LivingEntity p_28574_) {
        if (this.m_28567_() && p_28574_ == null) {
            this.m_28622_(false);
        }
        super.m_6710_(p_28574_);
    }

    @Override
    protected int m_5639_(float p_28545_, float p_28546_) {
        return Mth.m_14167_((p_28545_ - 5.0f) * p_28546_);
    }

    void m_28568_() {
        this.m_28626_(false);
    }

    void m_28569_() {
        this.m_28616_(false);
        this.m_28614_(false);
        this.m_28610_(false);
        this.m_28626_(false);
        this.m_28622_(false);
        this.m_28618_(false);
    }

    boolean m_28570_() {
        return !this.m_5803_() && !this.m_28555_() && !this.m_28556_();
    }

    @Override
    public void m_8032_() {
        SoundEvent $$0 = this.m_7515_();
        if ($$0 == SoundEvents.f_11949_) {
            this.m_5496_($$0, 2.0f, this.m_6100_());
        } else {
            super.m_8032_();
        }
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        List<Entity> $$0;
        if (this.m_5803_()) {
            return SoundEvents.f_11950_;
        }
        if (!this.f_19853_.m_46461_() && this.f_19796_.m_188501_() < 0.1f && ($$0 = this.f_19853_.m_6443_(Player.class, this.m_20191_().m_82377_(16.0, 16.0, 16.0), EntitySelector.f_20408_)).isEmpty()) {
            return SoundEvents.f_11949_;
        }
        return SoundEvents.f_11944_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_28548_) {
        return SoundEvents.f_11948_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11946_;
    }

    boolean m_28529_(UUID p_28530_) {
        return this.m_28566_().contains(p_28530_);
    }

    @Override
    protected void m_6668_(DamageSource p_28536_) {
        ItemStack $$1 = this.m_6844_(EquipmentSlot.MAINHAND);
        if (!$$1.m_41619_()) {
            this.m_19983_($$1);
            this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
        }
        super.m_6668_(p_28536_);
    }

    public static boolean m_28471_(Fox p_28472_, LivingEntity p_28473_) {
        double $$2 = p_28473_.m_20189_() - p_28472_.m_20189_();
        double $$3 = p_28473_.m_20185_() - p_28472_.m_20185_();
        double $$4 = $$2 / $$3;
        int $$5 = 6;
        for (int $$6 = 0; $$6 < 6; ++$$6) {
            double $$7 = $$4 == 0.0 ? 0.0 : $$2 * (double)((float)$$6 / 6.0f);
            double $$8 = $$4 == 0.0 ? $$3 * (double)((float)$$6 / 6.0f) : $$7 / $$4;
            for (int $$9 = 1; $$9 < 4; ++$$9) {
                if (p_28472_.f_19853_.m_8055_(new BlockPos(p_28472_.m_20185_() + $$8, p_28472_.m_20186_() + (double)$$9, p_28472_.m_20189_() + $$7)).m_60767_().m_76336_()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, 0.55f * this.m_20192_(), this.m_20205_() * 0.4f);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    public class FoxLookControl
    extends LookControl {
        public FoxLookControl() {
            super(Fox.this);
        }

        @Override
        public void m_8128_() {
            if (!Fox.this.m_5803_()) {
                super.m_8128_();
            }
        }

        @Override
        protected boolean m_8106_() {
            return !Fox.this.m_28557_() && !Fox.this.m_6047_() && !Fox.this.m_28559_() && !Fox.this.m_28556_();
        }
    }

    class FoxMoveControl
    extends MoveControl {
        public FoxMoveControl() {
            super(Fox.this);
        }

        @Override
        public void m_8126_() {
            if (Fox.this.m_28570_()) {
                super.m_8126_();
            }
        }
    }

    class FoxFloatGoal
    extends FloatGoal {
        public FoxFloatGoal() {
            super(Fox.this);
        }

        @Override
        public void m_8056_() {
            super.m_8056_();
            Fox.this.m_28569_();
        }

        @Override
        public boolean m_8036_() {
            return Fox.this.m_20069_() && Fox.this.m_204036_(FluidTags.f_13131_) > 0.25 || Fox.this.m_20077_();
        }
    }

    class FaceplantGoal
    extends Goal {
        int f_28640_;

        public FaceplantGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            return Fox.this.m_28556_();
        }

        @Override
        public boolean m_8045_() {
            return this.m_8036_() && this.f_28640_ > 0;
        }

        @Override
        public void m_8056_() {
            this.f_28640_ = this.m_183277_(40);
        }

        @Override
        public void m_8041_() {
            Fox.this.m_28618_(false);
        }

        @Override
        public void m_8037_() {
            --this.f_28640_;
        }
    }

    class FoxPanicGoal
    extends PanicGoal {
        public FoxPanicGoal(double p_28734_) {
            super(Fox.this, p_28734_);
        }

        @Override
        public boolean m_202729_() {
            return !Fox.this.m_28567_() && super.m_202729_();
        }
    }

    class FoxBreedGoal
    extends BreedGoal {
        public FoxBreedGoal(double p_28668_) {
            super(Fox.this, p_28668_);
        }

        @Override
        public void m_8056_() {
            ((Fox)this.f_25113_).m_28569_();
            ((Fox)this.f_25115_).m_28569_();
            super.m_8056_();
        }

        @Override
        protected void m_8026_() {
            ServerLevel $$0 = (ServerLevel)this.f_25114_;
            Fox $$1 = (Fox)this.f_25113_.m_142606_($$0, this.f_25115_);
            if ($$1 == null) {
                return;
            }
            ServerPlayer $$2 = this.f_25113_.m_27592_();
            ServerPlayer $$3 = this.f_25115_.m_27592_();
            ServerPlayer $$4 = $$2;
            if ($$2 != null) {
                $$1.m_28515_($$2.m_20148_());
            } else {
                $$4 = $$3;
            }
            if ($$3 != null && $$2 != $$3) {
                $$1.m_28515_($$3.m_20148_());
            }
            if ($$4 != null) {
                $$4.m_36220_(Stats.f_12937_);
                CriteriaTriggers.f_10581_.m_147278_($$4, this.f_25113_, this.f_25115_, $$1);
            }
            this.f_25113_.m_146762_(6000);
            this.f_25115_.m_146762_(6000);
            this.f_25113_.m_27594_();
            this.f_25115_.m_27594_();
            $$1.m_146762_(-24000);
            $$1.m_7678_(this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), 0.0f, 0.0f);
            $$0.m_47205_($$1);
            this.f_25114_.m_7605_(this.f_25113_, (byte)18);
            if (this.f_25114_.m_46469_().m_46207_(GameRules.f_46135_)) {
                this.f_25114_.m_7967_(new ExperienceOrb(this.f_25114_, this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), this.f_25113_.m_217043_().m_188503_(7) + 1));
            }
        }
    }

    class StalkPreyGoal
    extends Goal {
        public StalkPreyGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            if (Fox.this.m_5803_()) {
                return false;
            }
            LivingEntity $$0 = Fox.this.m_5448_();
            return $$0 != null && $$0.m_6084_() && f_28443_.test($$0) && Fox.this.m_20280_($$0) > 36.0 && !Fox.this.m_6047_() && !Fox.this.m_28559_() && !Fox.this.f_20899_;
        }

        @Override
        public void m_8056_() {
            Fox.this.m_28610_(false);
            Fox.this.m_28618_(false);
        }

        @Override
        public void m_8041_() {
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 != null && Fox.m_28471_(Fox.this, $$0)) {
                Fox.this.m_28616_(true);
                Fox.this.m_28614_(true);
                Fox.this.m_21573_().m_26573_();
                Fox.this.m_21563_().m_24960_($$0, Fox.this.m_8085_(), Fox.this.m_8132_());
            } else {
                Fox.this.m_28616_(false);
                Fox.this.m_28614_(false);
            }
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 == null) {
                return;
            }
            Fox.this.m_21563_().m_24960_($$0, Fox.this.m_8085_(), Fox.this.m_8132_());
            if (Fox.this.m_20280_($$0) <= 36.0) {
                Fox.this.m_28616_(true);
                Fox.this.m_28614_(true);
                Fox.this.m_21573_().m_26573_();
            } else {
                Fox.this.m_21573_().m_5624_($$0, 1.5);
            }
        }
    }

    public class FoxPounceGoal
    extends JumpGoal {
        @Override
        public boolean m_8036_() {
            if (!Fox.this.m_28558_()) {
                return false;
            }
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 == null || !$$0.m_6084_()) {
                return false;
            }
            if ($$0.m_6374_() != $$0.m_6350_()) {
                return false;
            }
            boolean $$1 = Fox.m_28471_(Fox.this, $$0);
            if (!$$1) {
                Fox.this.m_21573_().m_6570_($$0, 0);
                Fox.this.m_28614_(false);
                Fox.this.m_28616_(false);
            }
            return $$1;
        }

        @Override
        public boolean m_8045_() {
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 == null || !$$0.m_6084_()) {
                return false;
            }
            double $$1 = Fox.this.m_20184_().f_82480_;
            return !($$1 * $$1 < (double)0.05f && Math.abs(Fox.this.m_146909_()) < 15.0f && Fox.this.f_19861_ || Fox.this.m_28556_());
        }

        @Override
        public boolean m_6767_() {
            return false;
        }

        @Override
        public void m_8056_() {
            Fox.this.m_6862_(true);
            Fox.this.m_28612_(true);
            Fox.this.m_28616_(false);
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 != null) {
                Fox.this.m_21563_().m_24960_($$0, 60.0f, 30.0f);
                Vec3 $$1 = new Vec3($$0.m_20185_() - Fox.this.m_20185_(), $$0.m_20186_() - Fox.this.m_20186_(), $$0.m_20189_() - Fox.this.m_20189_()).m_82541_();
                Fox.this.m_20256_(Fox.this.m_20184_().m_82520_($$1.f_82479_ * 0.8, 0.9, $$1.f_82481_ * 0.8));
            }
            Fox.this.m_21573_().m_26573_();
        }

        @Override
        public void m_8041_() {
            Fox.this.m_28614_(false);
            Fox.this.f_28434_ = 0.0f;
            Fox.this.f_28435_ = 0.0f;
            Fox.this.m_28616_(false);
            Fox.this.m_28612_(false);
        }

        @Override
        public void m_8037_() {
            LivingEntity $$0 = Fox.this.m_5448_();
            if ($$0 != null) {
                Fox.this.m_21563_().m_24960_($$0, 60.0f, 30.0f);
            }
            if (!Fox.this.m_28556_()) {
                Vec3 $$1 = Fox.this.m_20184_();
                if ($$1.f_82480_ * $$1.f_82480_ < (double)0.03f && Fox.this.m_146909_() != 0.0f) {
                    Fox.this.m_146926_(Mth.m_14201_(Fox.this.m_146909_(), 0.0f, 0.2f));
                } else {
                    double $$2 = $$1.m_165924_();
                    double $$3 = Math.signum(-$$1.f_82480_) * Math.acos($$2 / $$1.m_82553_()) * 57.2957763671875;
                    Fox.this.m_146926_((float)$$3);
                }
            }
            if ($$0 != null && Fox.this.m_20270_($$0) <= 2.0f) {
                Fox.this.m_7327_($$0);
            } else if (Fox.this.m_146909_() > 0.0f && Fox.this.f_19861_ && (float)Fox.this.m_20184_().f_82480_ != 0.0f && Fox.this.f_19853_.m_8055_(Fox.this.m_20183_()).m_60713_(Blocks.f_50125_)) {
                Fox.this.m_146926_(60.0f);
                Fox.this.m_6710_(null);
                Fox.this.m_28618_(true);
            }
        }
    }

    class SeekShelterGoal
    extends FleeSunGoal {
        private int f_28774_;

        public SeekShelterGoal(double p_28777_) {
            super(Fox.this, p_28777_);
            this.f_28774_ = SeekShelterGoal.m_186073_(100);
        }

        @Override
        public boolean m_8036_() {
            if (Fox.this.m_5803_() || this.f_25214_.m_5448_() != null) {
                return false;
            }
            if (Fox.this.f_19853_.m_46470_() && Fox.this.f_19853_.m_45527_(this.f_25214_.m_20183_())) {
                return this.m_25226_();
            }
            if (this.f_28774_ > 0) {
                --this.f_28774_;
                return false;
            }
            this.f_28774_ = 100;
            BlockPos $$0 = this.f_25214_.m_20183_();
            return Fox.this.f_19853_.m_46461_() && Fox.this.f_19853_.m_45527_($$0) && !((ServerLevel)Fox.this.f_19853_).m_8802_($$0) && this.m_25226_();
        }

        @Override
        public void m_8056_() {
            Fox.this.m_28569_();
            super.m_8056_();
        }
    }

    class FoxMeleeAttackGoal
    extends MeleeAttackGoal {
        public FoxMeleeAttackGoal(double p_28720_, boolean p_28721_) {
            super(Fox.this, p_28720_, p_28721_);
        }

        @Override
        protected void m_6739_(LivingEntity p_28724_, double p_28725_) {
            double $$2 = this.m_6639_(p_28724_);
            if (p_28725_ <= $$2 && this.m_25564_()) {
                this.m_25563_();
                this.f_25540_.m_7327_(p_28724_);
                Fox.this.m_5496_(SoundEvents.f_11945_, 1.0f, 1.0f);
            }
        }

        @Override
        public void m_8056_() {
            Fox.this.m_28616_(false);
            super.m_8056_();
        }

        @Override
        public boolean m_8036_() {
            return !Fox.this.m_28555_() && !Fox.this.m_5803_() && !Fox.this.m_6047_() && !Fox.this.m_28556_() && super.m_8036_();
        }
    }

    class SleepGoal
    extends FoxBehaviorGoal {
        private static final int f_148930_ = SleepGoal.m_186073_(140);
        private int f_28781_;

        public SleepGoal() {
            this.f_28781_ = Fox.this.f_19796_.m_188503_(f_148930_);
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean m_8036_() {
            if (Fox.this.f_20900_ != 0.0f || Fox.this.f_20901_ != 0.0f || Fox.this.f_20902_ != 0.0f) {
                return false;
            }
            return this.m_28788_() || Fox.this.m_5803_();
        }

        @Override
        public boolean m_8045_() {
            return this.m_28788_();
        }

        private boolean m_28788_() {
            if (this.f_28781_ > 0) {
                --this.f_28781_;
                return false;
            }
            return Fox.this.f_19853_.m_46461_() && this.m_28663_() && !this.m_28664_() && !Fox.this.f_146808_;
        }

        @Override
        public void m_8041_() {
            this.f_28781_ = Fox.this.f_19796_.m_188503_(f_148930_);
            Fox.this.m_28569_();
        }

        @Override
        public void m_8056_() {
            Fox.this.m_28610_(false);
            Fox.this.m_28614_(false);
            Fox.this.m_28616_(false);
            Fox.this.m_6862_(false);
            Fox.this.m_28626_(true);
            Fox.this.m_21573_().m_26573_();
            Fox.this.m_21566_().m_6849_(Fox.this.m_20185_(), Fox.this.m_20186_(), Fox.this.m_20189_(), 0.0);
        }
    }

    class FoxFollowParentGoal
    extends FollowParentGoal {
        private final Fox f_28693_;

        public FoxFollowParentGoal(Fox p_28696_, double p_28697_) {
            super(p_28696_, p_28697_);
            this.f_28693_ = p_28696_;
        }

        @Override
        public boolean m_8036_() {
            return !this.f_28693_.m_28567_() && super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            return !this.f_28693_.m_28567_() && super.m_8045_();
        }

        @Override
        public void m_8056_() {
            this.f_28693_.m_28569_();
            super.m_8056_();
        }
    }

    class FoxStrollThroughVillageGoal
    extends StrollThroughVillageGoal {
        public FoxStrollThroughVillageGoal(int p_28754_, int p_28755_) {
            super(Fox.this, p_28755_);
        }

        @Override
        public void m_8056_() {
            Fox.this.m_28569_();
            super.m_8056_();
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && this.m_28759_();
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && this.m_28759_();
        }

        private boolean m_28759_() {
            return !Fox.this.m_5803_() && !Fox.this.m_28555_() && !Fox.this.m_28567_() && Fox.this.m_5448_() == null;
        }
    }

    public class FoxEatBerriesGoal
    extends MoveToBlockGoal {
        private static final int f_148925_ = 40;
        protected int f_28671_;

        public FoxEatBerriesGoal(double p_28675_, int p_28676_, int p_28677_) {
            super(Fox.this, p_28675_, p_28676_, p_28677_);
        }

        @Override
        public double m_8052_() {
            return 2.0;
        }

        @Override
        public boolean m_8064_() {
            return this.f_25601_ % 100 == 0;
        }

        @Override
        protected boolean m_6465_(LevelReader p_28680_, BlockPos p_28681_) {
            BlockState $$2 = p_28680_.m_8055_(p_28681_);
            return $$2.m_60713_(Blocks.f_50685_) && $$2.m_61143_(SweetBerryBushBlock.f_57244_) >= 2 || CaveVines.m_152951_($$2);
        }

        @Override
        public void m_8037_() {
            if (this.m_25625_()) {
                if (this.f_28671_ >= 40) {
                    this.m_28686_();
                } else {
                    ++this.f_28671_;
                }
            } else if (!this.m_25625_() && Fox.this.f_19796_.m_188501_() < 0.05f) {
                Fox.this.m_5496_(SoundEvents.f_11951_, 1.0f, 1.0f);
            }
            super.m_8037_();
        }

        protected void m_28686_() {
            if (!Fox.this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                return;
            }
            BlockState $$0 = Fox.this.f_19853_.m_8055_(this.f_25602_);
            if ($$0.m_60713_(Blocks.f_50685_)) {
                this.m_148928_($$0);
            } else if (CaveVines.m_152951_($$0)) {
                this.m_148926_($$0);
            }
        }

        private void m_148926_(BlockState p_148927_) {
            CaveVines.m_152953_(p_148927_, Fox.this.f_19853_, this.f_25602_);
        }

        private void m_148928_(BlockState p_148929_) {
            int $$1 = p_148929_.m_61143_(SweetBerryBushBlock.f_57244_);
            p_148929_.m_61124_(SweetBerryBushBlock.f_57244_, 1);
            int $$2 = 1 + Fox.this.f_19853_.f_46441_.m_188503_(2) + ($$1 == 3 ? 1 : 0);
            ItemStack $$3 = Fox.this.m_6844_(EquipmentSlot.MAINHAND);
            if ($$3.m_41619_()) {
                Fox.this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42780_));
                --$$2;
            }
            if ($$2 > 0) {
                Block.m_49840_(Fox.this.f_19853_, this.f_25602_, new ItemStack(Items.f_42780_, $$2));
            }
            Fox.this.m_5496_(SoundEvents.f_12457_, 1.0f, 1.0f);
            Fox.this.f_19853_.m_7731_(this.f_25602_, (BlockState)p_148929_.m_61124_(SweetBerryBushBlock.f_57244_, 1), 2);
        }

        @Override
        public boolean m_8036_() {
            return !Fox.this.m_5803_() && super.m_8036_();
        }

        @Override
        public void m_8056_() {
            this.f_28671_ = 0;
            Fox.this.m_28610_(false);
            super.m_8056_();
        }
    }

    class FoxSearchForItemsGoal
    extends Goal {
        public FoxSearchForItemsGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean m_8036_() {
            if (!Fox.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
                return false;
            }
            if (Fox.this.m_5448_() != null || Fox.this.m_21188_() != null) {
                return false;
            }
            if (!Fox.this.m_28570_()) {
                return false;
            }
            if (Fox.this.m_217043_().m_188503_(FoxSearchForItemsGoal.m_186073_(10)) != 0) {
                return false;
            }
            List<ItemEntity> $$0 = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28441_);
            return !$$0.isEmpty() && Fox.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
        }

        @Override
        public void m_8037_() {
            List<ItemEntity> $$0 = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28441_);
            ItemStack $$1 = Fox.this.m_6844_(EquipmentSlot.MAINHAND);
            if ($$1.m_41619_() && !$$0.isEmpty()) {
                Fox.this.m_21573_().m_5624_($$0.get(0), 1.2f);
            }
        }

        @Override
        public void m_8056_() {
            List<ItemEntity> $$0 = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_20191_().m_82377_(8.0, 8.0, 8.0), f_28441_);
            if (!$$0.isEmpty()) {
                Fox.this.m_21573_().m_5624_($$0.get(0), 1.2f);
            }
        }
    }

    class FoxLookAtPlayerGoal
    extends LookAtPlayerGoal {
        public FoxLookAtPlayerGoal(Mob p_28707_, Class<? extends LivingEntity> p_28708_, float p_28709_) {
            super(p_28707_, p_28708_, p_28709_);
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && !Fox.this.m_28556_() && !Fox.this.m_28559_();
        }

        @Override
        public boolean m_8045_() {
            return super.m_8045_() && !Fox.this.m_28556_() && !Fox.this.m_28559_();
        }
    }

    class PerchAndSearchGoal
    extends FoxBehaviorGoal {
        private double f_28761_;
        private double f_28762_;
        private int f_28763_;
        private int f_28764_;

        public PerchAndSearchGoal() {
            this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean m_8036_() {
            return Fox.this.m_21188_() == null && Fox.this.m_217043_().m_188501_() < 0.02f && !Fox.this.m_5803_() && Fox.this.m_5448_() == null && Fox.this.m_21573_().m_26571_() && !this.m_28664_() && !Fox.this.m_28557_() && !Fox.this.m_6047_();
        }

        @Override
        public boolean m_8045_() {
            return this.f_28764_ > 0;
        }

        @Override
        public void m_8056_() {
            this.m_28772_();
            this.f_28764_ = 2 + Fox.this.m_217043_().m_188503_(3);
            Fox.this.m_28610_(true);
            Fox.this.m_21573_().m_26573_();
        }

        @Override
        public void m_8041_() {
            Fox.this.m_28610_(false);
        }

        @Override
        public void m_8037_() {
            --this.f_28763_;
            if (this.f_28763_ <= 0) {
                --this.f_28764_;
                this.m_28772_();
            }
            Fox.this.m_21563_().m_24950_(Fox.this.m_20185_() + this.f_28761_, Fox.this.m_20188_(), Fox.this.m_20189_() + this.f_28762_, Fox.this.m_8085_(), Fox.this.m_8132_());
        }

        private void m_28772_() {
            double $$0 = Math.PI * 2 * Fox.this.m_217043_().m_188500_();
            this.f_28761_ = Math.cos($$0);
            this.f_28762_ = Math.sin($$0);
            this.f_28763_ = this.m_183277_(80 + Fox.this.m_217043_().m_188503_(20));
        }
    }

    class DefendTrustedTargetGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        @Nullable
        private LivingEntity f_28629_;
        @Nullable
        private LivingEntity f_28630_;
        private int f_28631_;

        public DefendTrustedTargetGoal(Class<LivingEntity> p_28634_, @Nullable boolean p_28635_, boolean p_28636_, Predicate<LivingEntity> p_28637_) {
            super(Fox.this, p_28634_, 10, p_28635_, p_28636_, p_28637_);
        }

        @Override
        public boolean m_8036_() {
            if (this.f_26049_ > 0 && this.f_26135_.m_217043_().m_188503_(this.f_26049_) != 0) {
                return false;
            }
            for (UUID $$0 : Fox.this.m_28566_()) {
                LivingEntity $$2;
                Entity $$1;
                if ($$0 == null || !(Fox.this.f_19853_ instanceof ServerLevel) || !(($$1 = ((ServerLevel)Fox.this.f_19853_).m_8791_($$0)) instanceof LivingEntity)) continue;
                this.f_28630_ = $$2 = (LivingEntity)$$1;
                this.f_28629_ = $$2.m_21188_();
                int $$3 = $$2.m_21213_();
                return $$3 != this.f_28631_ && this.m_26150_(this.f_28629_, this.f_26051_);
            }
            return false;
        }

        @Override
        public void m_8056_() {
            this.m_26070_(this.f_28629_);
            this.f_26050_ = this.f_28629_;
            if (this.f_28630_ != null) {
                this.f_28631_ = this.f_28630_.m_21213_();
            }
            Fox.this.m_5496_(SoundEvents.f_11943_, 1.0f, 1.0f);
            Fox.this.m_28622_(true);
            Fox.this.m_28568_();
            super.m_8056_();
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type RED = new Type(0, "red");
        public static final /* enum */ Type SNOW = new Type(1, "snow");
        private static final Type[] f_28798_;
        private static final Map<String, Type> f_28799_;
        private final int f_28800_;
        private final String f_28801_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_28824_) {
            return Enum.valueOf(Type.class, p_28824_);
        }

        private Type(int p_196658_, String p_196659_) {
            this.f_28800_ = p_196658_;
            this.f_28801_ = p_196659_;
        }

        public String m_28811_() {
            return this.f_28801_;
        }

        public int m_28820_() {
            return this.f_28800_;
        }

        public static Type m_28816_(String p_28817_) {
            return f_28799_.getOrDefault(p_28817_, RED);
        }

        public static Type m_28812_(int p_28813_) {
            if (p_28813_ < 0 || p_28813_ > f_28798_.length) {
                p_28813_ = 0;
            }
            return f_28798_[p_28813_];
        }

        public static Type m_204062_(Holder<Biome> p_204063_) {
            return p_204063_.m_203334_().m_47530_() == Biome.Precipitation.SNOW ? SNOW : RED;
        }

        private static /* synthetic */ Type[] m_148931_() {
            return new Type[]{RED, SNOW};
        }

        static {
            $VALUES = Type.m_148931_();
            f_28798_ = (Type[])Arrays.stream(Type.values()).sorted(Comparator.comparingInt(Type::m_28820_)).toArray(Type[]::new);
            f_28799_ = Arrays.stream(Type.values()).collect(Collectors.toMap(Type::m_28811_, p_28815_ -> p_28815_));
        }
    }

    public static class FoxGroupData
    extends AgeableMob.AgeableMobGroupData {
        public final Type f_28701_;

        public FoxGroupData(Type p_28703_) {
            super(false);
            this.f_28701_ = p_28703_;
        }
    }

    abstract class FoxBehaviorGoal
    extends Goal {
        private final TargetingConditions f_28657_;

        FoxBehaviorGoal() {
            this.f_28657_ = TargetingConditions.m_148352_().m_26883_(12.0).m_148355_().m_26888_(new FoxAlertableEntitiesSelector());
        }

        protected boolean m_28663_() {
            BlockPos $$0 = new BlockPos(Fox.this.m_20185_(), Fox.this.m_20191_().f_82292_, Fox.this.m_20189_());
            return !Fox.this.f_19853_.m_45527_($$0) && Fox.this.m_21692_($$0) >= 0.0f;
        }

        protected boolean m_28664_() {
            return !Fox.this.f_19853_.m_45971_(LivingEntity.class, this.f_28657_, Fox.this, Fox.this.m_20191_().m_82377_(12.0, 6.0, 12.0)).isEmpty();
        }
    }

    public class FoxAlertableEntitiesSelector
    implements Predicate<LivingEntity> {
        @Override
        public boolean test(LivingEntity p_28653_) {
            if (p_28653_ instanceof Fox) {
                return false;
            }
            if (p_28653_ instanceof Chicken || p_28653_ instanceof Rabbit || p_28653_ instanceof Monster) {
                return true;
            }
            if (p_28653_ instanceof TamableAnimal) {
                return !((TamableAnimal)p_28653_).m_21824_();
            }
            if (p_28653_ instanceof Player && (p_28653_.m_5833_() || ((Player)p_28653_).m_7500_())) {
                return false;
            }
            if (Fox.this.m_28529_(p_28653_.m_20148_())) {
                return false;
            }
            return !p_28653_.m_5803_() && !p_28653_.m_20163_();
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((LivingEntity)object);
        }
    }
}

