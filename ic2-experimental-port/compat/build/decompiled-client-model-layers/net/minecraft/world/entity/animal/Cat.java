/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

public class Cat
extends TamableAnimal {
    public static final double f_148842_ = 0.6;
    public static final double f_148843_ = 0.8;
    public static final double f_148844_ = 1.33;
    private static final Ingredient f_28103_ = Ingredient.m_43929_(Items.f_42526_, Items.f_42527_);
    private static final EntityDataAccessor<CatVariant> f_218131_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_238114_);
    private static final EntityDataAccessor<Boolean> f_28105_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_28106_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_28107_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135028_);
    private CatAvoidEntityGoal<Player> f_28108_;
    @Nullable
    private TemptGoal f_28109_;
    private float f_28110_;
    private float f_28111_;
    private float f_28098_;
    private float f_28099_;
    private float f_28100_;
    private float f_28101_;

    public Cat(EntityType<? extends Cat> p_28114_, Level p_28115_) {
        super((EntityType<? extends TamableAnimal>)p_28114_, p_28115_);
    }

    public ResourceLocation m_28162_() {
        return this.m_218139_().f_218151_();
    }

    @Override
    protected void m_8099_() {
        this.f_28109_ = new CatTemptGoal(this, 0.6, f_28103_, true);
        this.f_21345_.m_25352_(1, new FloatGoal(this));
        this.f_21345_.m_25352_(1, new SitWhenOrderedToGoal(this));
        this.f_21345_.m_25352_(2, new CatRelaxOnOwnerGoal(this));
        this.f_21345_.m_25352_(3, this.f_28109_);
        this.f_21345_.m_25352_(5, new CatLieOnBedGoal(this, 1.1, 8));
        this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0, 10.0f, 5.0f, false));
        this.f_21345_.m_25352_(7, new CatSitOnBlockGoal(this, 0.8));
        this.f_21345_.m_25352_(8, new LeapAtTargetGoal(this, 0.3f));
        this.f_21345_.m_25352_(9, new OcelotAttackGoal(this));
        this.f_21345_.m_25352_(10, new BreedGoal(this, 0.8));
        this.f_21345_.m_25352_(11, new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8, 1.0000001E-5f));
        this.f_21345_.m_25352_(12, new LookAtPlayerGoal(this, Player.class, 10.0f));
        this.f_21346_.m_25352_(1, new NonTameRandomTargetGoal<Rabbit>(this, Rabbit.class, false, null));
        this.f_21346_.m_25352_(1, new NonTameRandomTargetGoal<Turtle>(this, Turtle.class, false, Turtle.f_30122_));
    }

    public CatVariant m_218139_() {
        return this.f_19804_.m_135370_(f_218131_);
    }

    public void m_218132_(CatVariant p_218133_) {
        this.f_19804_.m_135381_(f_218131_, p_218133_);
    }

    public void m_28181_(boolean p_28182_) {
        this.f_19804_.m_135381_(f_28105_, p_28182_);
    }

    public boolean m_28164_() {
        return this.f_19804_.m_135370_(f_28105_);
    }

    public void m_28185_(boolean p_28186_) {
        this.f_19804_.m_135381_(f_28106_, p_28186_);
    }

    public boolean m_28165_() {
        return this.f_19804_.m_135370_(f_28106_);
    }

    public DyeColor m_28166_() {
        return DyeColor.m_41053_(this.f_19804_.m_135370_(f_28107_));
    }

    public void m_28131_(DyeColor p_28132_) {
        this.f_19804_.m_135381_(f_28107_, p_28132_.m_41060_());
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_218131_, CatVariant.f_218141_);
        this.f_19804_.m_135372_(f_28105_, false);
        this.f_19804_.m_135372_(f_28106_, false);
        this.f_19804_.m_135372_(f_28107_, DyeColor.RED.m_41060_());
    }

    @Override
    public void m_7380_(CompoundTag p_28156_) {
        super.m_7380_(p_28156_);
        p_28156_.m_128359_("variant", Registry.f_235732_.m_7981_(this.m_218139_()).toString());
        p_28156_.m_128344_("CollarColor", (byte)this.m_28166_().m_41060_());
    }

    @Override
    public void m_7378_(CompoundTag p_28142_) {
        super.m_7378_(p_28142_);
        CatVariant $$1 = Registry.f_235732_.m_7745_(ResourceLocation.m_135820_(p_28142_.m_128461_("variant")));
        if ($$1 != null) {
            this.m_218132_($$1);
        }
        if (p_28142_.m_128425_("CollarColor", 99)) {
            this.m_28131_(DyeColor.m_41053_(p_28142_.m_128451_("CollarColor")));
        }
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
    @Nullable
    protected SoundEvent m_7515_() {
        if (this.m_21824_()) {
            if (this.m_27593_()) {
                return SoundEvents.f_11792_;
            }
            if (this.f_19796_.m_188503_(4) == 0) {
                return SoundEvents.f_11793_;
            }
            return SoundEvents.f_11785_;
        }
        return SoundEvents.f_11786_;
    }

    @Override
    public int m_8100_() {
        return 120;
    }

    public void m_28167_() {
        this.m_5496_(SoundEvents.f_11789_, this.m_6121_(), this.m_6100_());
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_28160_) {
        return SoundEvents.f_11791_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11787_;
    }

    public static AttributeSupplier.Builder m_28168_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22281_, 3.0);
    }

    @Override
    public boolean m_142535_(float p_148859_, float p_148860_, DamageSource p_148861_) {
        return false;
    }

    @Override
    protected void m_142075_(Player p_148866_, InteractionHand p_148867_, ItemStack p_148868_) {
        if (this.m_6898_(p_148868_)) {
            this.m_5496_(SoundEvents.f_11788_, 1.0f, 1.0f);
        }
        super.m_142075_(p_148866_, p_148867_, p_148868_);
    }

    private float m_28169_() {
        return (float)this.m_21133_(Attributes.f_22281_);
    }

    @Override
    public boolean m_7327_(Entity p_28119_) {
        return p_28119_.m_6469_(DamageSource.m_19370_(this), this.m_28169_());
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_28109_ != null && this.f_28109_.m_25955_() && !this.m_21824_() && this.f_19797_ % 100 == 0) {
            this.m_5496_(SoundEvents.f_11790_, 1.0f, 1.0f);
        }
        this.m_28170_();
    }

    private void m_28170_() {
        if ((this.m_28164_() || this.m_28165_()) && this.f_19797_ % 5 == 0) {
            this.m_5496_(SoundEvents.f_11792_, 0.6f + 0.4f * (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()), 1.0f);
        }
        this.m_28171_();
        this.m_28172_();
    }

    private void m_28171_() {
        this.f_28111_ = this.f_28110_;
        this.f_28099_ = this.f_28098_;
        if (this.m_28164_()) {
            this.f_28110_ = Math.min(1.0f, this.f_28110_ + 0.15f);
            this.f_28098_ = Math.min(1.0f, this.f_28098_ + 0.08f);
        } else {
            this.f_28110_ = Math.max(0.0f, this.f_28110_ - 0.22f);
            this.f_28098_ = Math.max(0.0f, this.f_28098_ - 0.13f);
        }
    }

    private void m_28172_() {
        this.f_28101_ = this.f_28100_;
        this.f_28100_ = this.m_28165_() ? Math.min(1.0f, this.f_28100_ + 0.1f) : Math.max(0.0f, this.f_28100_ - 0.13f);
    }

    public float m_28183_(float p_28184_) {
        return Mth.m_14179_(p_28184_, this.f_28111_, this.f_28110_);
    }

    public float m_28187_(float p_28188_) {
        return Mth.m_14179_(p_28188_, this.f_28099_, this.f_28098_);
    }

    public float m_28116_(float p_28117_) {
        return Mth.m_14179_(p_28117_, this.f_28101_, this.f_28100_);
    }

    @Override
    public Cat m_142606_(ServerLevel p_148870_, AgeableMob p_148871_) {
        Cat $$2 = EntityType.f_20553_.m_20615_(p_148870_);
        if (p_148871_ instanceof Cat) {
            if (this.f_19796_.m_188499_()) {
                $$2.m_218132_(this.m_218139_());
            } else {
                $$2.m_218132_(((Cat)p_148871_).m_218139_());
            }
            if (this.m_21824_()) {
                $$2.m_21816_(this.m_21805_());
                $$2.m_7105_(true);
                if (this.f_19796_.m_188499_()) {
                    $$2.m_28131_(this.m_28166_());
                } else {
                    $$2.m_28131_(((Cat)p_148871_).m_28166_());
                }
            }
        }
        return $$2;
    }

    @Override
    public boolean m_7848_(Animal p_28127_) {
        if (!this.m_21824_()) {
            return false;
        }
        if (!(p_28127_ instanceof Cat)) {
            return false;
        }
        Cat $$1 = (Cat)p_28127_;
        return $$1.m_21824_() && super.m_7848_(p_28127_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.m_6518_(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        boolean $$5 = p_28134_.m_46940_() > 0.9f;
        TagKey<CatVariant> $$6 = $$5 ? CatVariantTags.f_215842_ : CatVariantTags.f_215841_;
        Registry.f_235732_.m_203431_($$6).flatMap(p_218136_ -> p_218136_.m_213653_(p_28134_.m_213780_())).ifPresent(p_218138_ -> this.m_218132_((CatVariant)p_218138_.m_203334_()));
        ServerLevel $$7 = p_28134_.m_6018_();
        if ($$7.m_215010_().m_220491_(this.m_20183_(), StructureTags.f_215888_).m_73603_()) {
            this.m_218132_(CatVariant.f_218150_);
            this.m_21530_();
        }
        return p_28137_;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public InteractionResult m_6071_(Player p_28153_, InteractionHand p_28154_) {
        InteractionResult $$6;
        ItemStack $$2 = p_28153_.m_21120_(p_28154_);
        Item $$3 = $$2.m_41720_();
        if (this.f_19853_.f_46443_) {
            if (this.m_21824_() && this.m_21830_(p_28153_)) {
                return InteractionResult.SUCCESS;
            }
            if (this.m_6898_($$2) && (this.m_21223_() < this.m_21233_() || !this.m_21824_())) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (this.m_21824_()) {
            if (this.m_21830_(p_28153_)) {
                if ($$3 instanceof DyeItem) {
                    DyeColor $$4 = ((DyeItem)$$3).m_41089_();
                    if ($$4 != this.m_28166_()) {
                        this.m_28131_($$4);
                        if (!p_28153_.m_150110_().f_35937_) {
                            $$2.m_41774_(1);
                        }
                        this.m_21530_();
                        return InteractionResult.CONSUME;
                    }
                } else {
                    if ($$3.m_41472_() && this.m_6898_($$2) && this.m_21223_() < this.m_21233_()) {
                        this.m_142075_(p_28153_, p_28154_, $$2);
                        this.m_5634_($$3.m_41473_().m_38744_());
                        return InteractionResult.CONSUME;
                    }
                    InteractionResult $$5 = super.m_6071_(p_28153_, p_28154_);
                    if (!$$5.m_19077_() || this.m_6162_()) {
                        this.m_21839_(!this.m_21827_());
                    }
                    return $$5;
                }
            }
        } else if (this.m_6898_($$2)) {
            this.m_142075_(p_28153_, p_28154_, $$2);
            if (this.f_19796_.m_188503_(3) == 0) {
                this.m_21828_(p_28153_);
                this.m_21839_(true);
                this.f_19853_.m_7605_(this, (byte)7);
            } else {
                this.f_19853_.m_7605_(this, (byte)6);
            }
            this.m_21530_();
            return InteractionResult.CONSUME;
        }
        if (($$6 = super.m_6071_(p_28153_, p_28154_)).m_19077_()) {
            this.m_21530_();
        }
        return $$6;
    }

    @Override
    public boolean m_6898_(ItemStack p_28177_) {
        return f_28103_.test(p_28177_);
    }

    @Override
    protected float m_6431_(Pose p_28150_, EntityDimensions p_28151_) {
        return p_28151_.f_20378_ * 0.5f;
    }

    @Override
    public boolean m_6785_(double p_28174_) {
        return !this.m_21824_() && this.f_19797_ > 2400;
    }

    @Override
    protected void m_5849_() {
        if (this.f_28108_ == null) {
            this.f_28108_ = new CatAvoidEntityGoal<Player>(this, Player.class, 16.0f, 0.8, 1.33);
        }
        this.f_21345_.m_25363_(this.f_28108_);
        if (!this.m_21824_()) {
            this.f_21345_.m_25352_(4, this.f_28108_);
        }
    }

    @Override
    public boolean m_20161_() {
        return this.m_6047_() || super.m_20161_();
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    static class CatTemptGoal
    extends TemptGoal {
        @Nullable
        private Player f_28216_;
        private final Cat f_28217_;

        public CatTemptGoal(Cat p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
            super(p_28219_, p_28220_, p_28221_, p_28222_);
            this.f_28217_ = p_28219_;
        }

        @Override
        public void m_8037_() {
            super.m_8037_();
            if (this.f_28216_ == null && this.f_25924_.m_217043_().m_188503_(this.m_183277_(600)) == 0) {
                this.f_28216_ = this.f_25925_;
            } else if (this.f_25924_.m_217043_().m_188503_(this.m_183277_(500)) == 0) {
                this.f_28216_ = null;
            }
        }

        @Override
        protected boolean m_7497_() {
            if (this.f_28216_ != null && this.f_28216_.equals(this.f_25925_)) {
                return false;
            }
            return super.m_7497_();
        }

        @Override
        public boolean m_8036_() {
            return super.m_8036_() && !this.f_28217_.m_21824_();
        }
    }

    static class CatRelaxOnOwnerGoal
    extends Goal {
        private final Cat f_28198_;
        @Nullable
        private Player f_28199_;
        @Nullable
        private BlockPos f_28200_;
        private int f_28201_;

        public CatRelaxOnOwnerGoal(Cat p_28203_) {
            this.f_28198_ = p_28203_;
        }

        @Override
        public boolean m_8036_() {
            if (!this.f_28198_.m_21824_()) {
                return false;
            }
            if (this.f_28198_.m_21827_()) {
                return false;
            }
            LivingEntity $$0 = this.f_28198_.m_21826_();
            if ($$0 instanceof Player) {
                this.f_28199_ = (Player)$$0;
                if (!$$0.m_5803_()) {
                    return false;
                }
                if (this.f_28198_.m_20280_(this.f_28199_) > 100.0) {
                    return false;
                }
                BlockPos $$1 = this.f_28199_.m_20183_();
                BlockState $$2 = this.f_28198_.f_19853_.m_8055_($$1);
                if ($$2.m_204336_(BlockTags.f_13038_)) {
                    this.f_28200_ = $$2.m_61145_(BedBlock.f_54117_).map(p_28209_ -> $$1.m_121945_(p_28209_.m_122424_())).orElseGet(() -> new BlockPos($$1));
                    return !this.m_28214_();
                }
            }
            return false;
        }

        private boolean m_28214_() {
            List<Cat> $$0 = this.f_28198_.f_19853_.m_45976_(Cat.class, new AABB(this.f_28200_).m_82400_(2.0));
            for (Cat $$1 : $$0) {
                if ($$1 == this.f_28198_ || !$$1.m_28164_() && !$$1.m_28165_()) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean m_8045_() {
            return this.f_28198_.m_21824_() && !this.f_28198_.m_21827_() && this.f_28199_ != null && this.f_28199_.m_5803_() && this.f_28200_ != null && !this.m_28214_();
        }

        @Override
        public void m_8056_() {
            if (this.f_28200_ != null) {
                this.f_28198_.m_21837_(false);
                this.f_28198_.m_21573_().m_26519_(this.f_28200_.m_123341_(), this.f_28200_.m_123342_(), this.f_28200_.m_123343_(), 1.1f);
            }
        }

        @Override
        public void m_8041_() {
            this.f_28198_.m_28181_(false);
            float $$0 = this.f_28198_.f_19853_.m_46942_(1.0f);
            if (this.f_28199_.m_36318_() >= 100 && (double)$$0 > 0.77 && (double)$$0 < 0.8 && (double)this.f_28198_.f_19853_.m_213780_().m_188501_() < 0.7) {
                this.m_28215_();
            }
            this.f_28201_ = 0;
            this.f_28198_.m_28185_(false);
            this.f_28198_.m_21573_().m_26573_();
        }

        private void m_28215_() {
            RandomSource $$0 = this.f_28198_.m_217043_();
            BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
            $$1.m_122190_(this.f_28198_.m_20183_());
            this.f_28198_.m_20984_($$1.m_123341_() + $$0.m_188503_(11) - 5, $$1.m_123342_() + $$0.m_188503_(5) - 2, $$1.m_123343_() + $$0.m_188503_(11) - 5, false);
            $$1.m_122190_(this.f_28198_.m_20183_());
            LootTable $$2 = this.f_28198_.f_19853_.m_7654_().m_129898_().m_79217_(BuiltInLootTables.f_78724_);
            LootContext.Builder $$3 = new LootContext.Builder((ServerLevel)this.f_28198_.f_19853_).m_78972_(LootContextParams.f_81460_, this.f_28198_.m_20182_()).m_78972_(LootContextParams.f_81455_, this.f_28198_).m_230911_($$0);
            ObjectArrayList<ItemStack> $$4 = $$2.m_230922_($$3.m_78975_(LootContextParamSets.f_81416_));
            for (ItemStack $$5 : $$4) {
                this.f_28198_.f_19853_.m_7967_(new ItemEntity(this.f_28198_.f_19853_, (double)$$1.m_123341_() - (double)Mth.m_14031_(this.f_28198_.f_20883_ * ((float)Math.PI / 180)), $$1.m_123342_(), (double)$$1.m_123343_() + (double)Mth.m_14089_(this.f_28198_.f_20883_ * ((float)Math.PI / 180)), $$5));
            }
        }

        @Override
        public void m_8037_() {
            if (this.f_28199_ != null && this.f_28200_ != null) {
                this.f_28198_.m_21837_(false);
                this.f_28198_.m_21573_().m_26519_(this.f_28200_.m_123341_(), this.f_28200_.m_123342_(), this.f_28200_.m_123343_(), 1.1f);
                if (this.f_28198_.m_20280_(this.f_28199_) < 2.5) {
                    ++this.f_28201_;
                    if (this.f_28201_ > this.m_183277_(16)) {
                        this.f_28198_.m_28181_(true);
                        this.f_28198_.m_28185_(false);
                    } else {
                        this.f_28198_.m_21391_(this.f_28199_, 45.0f, 45.0f);
                        this.f_28198_.m_28185_(true);
                    }
                } else {
                    this.f_28198_.m_28181_(false);
                }
            }
        }
    }

    static class CatAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final Cat f_28189_;

        public CatAvoidEntityGoal(Cat p_28191_, Class<T> p_28192_, float p_28193_, double p_28194_, double p_28195_) {
            super(p_28191_, p_28192_, p_28193_, p_28194_, p_28195_, EntitySelector.f_20406_::test);
            this.f_28189_ = p_28191_;
        }

        @Override
        public boolean m_8036_() {
            return !this.f_28189_.m_21824_() && super.m_8036_();
        }

        @Override
        public boolean m_8045_() {
            return !this.f_28189_.m_21824_() && super.m_8045_();
        }
    }
}

