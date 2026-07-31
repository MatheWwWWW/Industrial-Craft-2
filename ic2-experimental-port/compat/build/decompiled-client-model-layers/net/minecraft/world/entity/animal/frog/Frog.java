/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class Frog
extends Animal {
    public static final Ingredient f_218455_ = Ingredient.m_43929_(Items.f_42518_);
    protected static final ImmutableList<SensorType<? extends Sensor<? super Frog>>> f_218456_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26814_, SensorType.f_217823_, SensorType.f_217822_, SensorType.f_217824_);
    protected static final ImmutableList<MemoryModuleType<?>> f_218457_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26375_, MemoryModuleType.f_148199_, MemoryModuleType.f_148200_, MemoryModuleType.f_26372_, MemoryModuleType.f_148196_, MemoryModuleType.f_148197_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_148198_, MemoryModuleType.f_26381_, MemoryModuleType.f_26382_, MemoryModuleType.f_148194_, MemoryModuleType.f_217766_, MemoryModuleType.f_217767_, MemoryModuleType.f_217768_, MemoryModuleType.f_238182_});
    private static final EntityDataAccessor<FrogVariant> f_218465_ = SynchedEntityData.m_135353_(Frog.class, EntityDataSerializers.f_238115_);
    private static final EntityDataAccessor<OptionalInt> f_218466_ = SynchedEntityData.m_135353_(Frog.class, EntityDataSerializers.f_135044_);
    private static final int f_218467_ = 5;
    public static final String f_218458_ = "variant";
    public final AnimationState f_218459_ = new AnimationState();
    public final AnimationState f_218460_ = new AnimationState();
    public final AnimationState f_218461_ = new AnimationState();
    public final AnimationState f_218462_ = new AnimationState();
    public final AnimationState f_218463_ = new AnimationState();
    public final AnimationState f_218464_ = new AnimationState();

    public Frog(EntityType<? extends Animal> p_218470_, Level p_218471_) {
        super(p_218470_, p_218471_);
        this.f_21365_ = new FrogLookControl(this);
        this.m_21441_(BlockPathTypes.WATER, 4.0f);
        this.m_21441_(BlockPathTypes.TRAPDOOR, -1.0f);
        this.f_21342_ = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.f_19793_ = 1.0f;
    }

    protected Brain.Provider<Frog> m_5490_() {
        return Brain.m_21923_(f_218457_, f_218456_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_218494_) {
        return FrogAi.m_218575_(this.m_5490_().m_22073_(p_218494_));
    }

    public Brain<Frog> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_218465_, FrogVariant.f_218185_);
        this.f_19804_.m_135372_(f_218466_, OptionalInt.empty());
    }

    public void m_218536_() {
        this.f_19804_.m_135381_(f_218466_, OptionalInt.empty());
    }

    public Optional<Entity> m_218538_() {
        return this.f_19804_.m_135370_(f_218466_).stream().mapToObj(this.f_19853_::m_6815_).filter(Objects::nonNull).findFirst();
    }

    public void m_218481_(Entity p_218482_) {
        this.f_19804_.m_135381_(f_218466_, OptionalInt.of(p_218482_.m_19879_()));
    }

    @Override
    public int m_21529_() {
        return 35;
    }

    @Override
    public int m_8085_() {
        return 5;
    }

    public FrogVariant m_218524_() {
        return this.f_19804_.m_135370_(f_218465_);
    }

    public void m_218483_(FrogVariant p_218484_) {
        this.f_19804_.m_135381_(f_218465_, p_218484_);
    }

    @Override
    public void m_7380_(CompoundTag p_218508_) {
        super.m_7380_(p_218508_);
        p_218508_.m_128359_(f_218458_, Registry.f_235734_.m_7981_(this.m_218524_()).toString());
    }

    @Override
    public void m_7378_(CompoundTag p_218496_) {
        super.m_7378_(p_218496_);
        FrogVariant $$1 = Registry.f_235734_.m_7745_(ResourceLocation.m_135820_(p_218496_.m_128461_(f_218458_)));
        if ($$1 != null) {
            this.m_218483_($$1);
        }
    }

    @Override
    public boolean m_6040_() {
        return true;
    }

    private boolean m_218526_() {
        return this.f_19861_ && this.m_20184_().m_165925_() > 1.0E-6 && !this.m_20072_();
    }

    private boolean m_218527_() {
        return this.m_20184_().m_165925_() > 1.0E-6 && this.m_20072_();
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("frogBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("frogActivityUpdate");
        FrogAi.m_218577_(this);
        this.f_19853_.m_46473_().m_7238_();
        super.m_8024_();
    }

    @Override
    public void m_8119_() {
        if (this.f_19853_.m_5776_()) {
            if (this.m_218526_()) {
                this.f_218462_.m_216982_(this.f_19797_);
            } else {
                this.f_218462_.m_216973_();
            }
            if (this.m_218527_()) {
                this.f_218464_.m_216973_();
                this.f_218463_.m_216982_(this.f_19797_);
            } else if (this.m_20072_()) {
                this.f_218463_.m_216973_();
                this.f_218464_.m_216982_(this.f_19797_);
            } else {
                this.f_218463_.m_216973_();
                this.f_218464_.m_216973_();
            }
        }
        super.m_8119_();
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_218498_) {
        if (f_19806_.equals(p_218498_)) {
            Pose $$1 = this.m_20089_();
            if ($$1 == Pose.LONG_JUMPING) {
                this.f_218459_.m_216977_(this.f_19797_);
            } else {
                this.f_218459_.m_216973_();
            }
            if ($$1 == Pose.CROAKING) {
                this.f_218460_.m_216977_(this.f_19797_);
            } else {
                this.f_218460_.m_216973_();
            }
            if ($$1 == Pose.USING_TONGUE) {
                this.f_218461_.m_216977_(this.f_19797_);
            } else {
                this.f_218461_.m_216973_();
            }
        }
        super.m_7350_(p_218498_);
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_218476_, AgeableMob p_218477_) {
        Frog $$2 = EntityType.f_217012_.m_20615_(p_218476_);
        if ($$2 != null) {
            FrogAi.m_218579_($$2, p_218476_.m_213780_());
        }
        return $$2;
    }

    @Override
    public boolean m_6162_() {
        return false;
    }

    @Override
    public void m_6863_(boolean p_218500_) {
    }

    @Override
    public void m_27563_(ServerLevel p_218479_, Animal p_218480_) {
        ServerPlayer $$2 = this.m_27592_();
        if ($$2 == null) {
            $$2 = p_218480_.m_27592_();
        }
        if ($$2 != null) {
            $$2.m_36220_(Stats.f_12937_);
            CriteriaTriggers.f_10581_.m_147278_($$2, this, p_218480_, null);
        }
        this.m_146762_(6000);
        p_218480_.m_146762_(6000);
        this.m_27594_();
        p_218480_.m_27594_();
        this.m_6274_().m_21879_(MemoryModuleType.f_217767_, Unit.INSTANCE);
        p_218479_.m_7605_(this, (byte)18);
        if (p_218479_.m_46469_().m_46207_(GameRules.f_46135_)) {
            p_218479_.m_7967_(new ExperienceOrb(p_218479_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_217043_().m_188503_(7) + 1));
        }
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_218488_, DifficultyInstance p_218489_, MobSpawnType p_218490_, @Nullable SpawnGroupData p_218491_, @Nullable CompoundTag p_218492_) {
        Holder<Biome> $$5 = p_218488_.m_204166_(this.m_20183_());
        if ($$5.m_203656_(BiomeTags.f_215808_)) {
            this.m_218483_(FrogVariant.f_218187_);
        } else if ($$5.m_203656_(BiomeTags.f_215809_)) {
            this.m_218483_(FrogVariant.f_218186_);
        } else {
            this.m_218483_(FrogVariant.f_218185_);
        }
        FrogAi.m_218579_(this, p_218488_.m_213780_());
        return super.m_6518_(p_218488_, p_218489_, p_218490_, p_218491_, p_218492_);
    }

    public static AttributeSupplier.Builder m_218525_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 1.0).m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22281_, 10.0);
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return SoundEvents.f_215690_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_218510_) {
        return SoundEvents.f_215693_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_215691_;
    }

    @Override
    protected void m_7355_(BlockPos p_218505_, BlockState p_218506_) {
        this.m_5496_(SoundEvents.f_215696_, 0.15f, 1.0f);
    }

    @Override
    public boolean m_6063_() {
        return false;
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    protected int m_5639_(float p_218519_, float p_218520_) {
        return super.m_5639_(p_218519_, p_218520_) - 5;
    }

    @Override
    public void m_7023_(Vec3 p_218530_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(this.m_6113_(), p_218530_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
        } else {
            super.m_7023_(p_218530_);
        }
    }

    @Override
    public boolean m_21481_(BlockPathTypes p_218503_) {
        return super.m_21481_(p_218503_) && p_218503_ != BlockPathTypes.WATER_BORDER;
    }

    public static boolean m_218532_(LivingEntity p_218533_) {
        Slime $$1;
        if (p_218533_ instanceof Slime && ($$1 = (Slime)p_218533_).m_33632_() != 1) {
            return false;
        }
        return p_218533_.m_6095_().m_204039_(EntityTypeTags.f_215847_);
    }

    @Override
    protected PathNavigation m_6037_(Level p_218486_) {
        return new FrogPathNavigation(this, p_218486_);
    }

    @Override
    public boolean m_6898_(ItemStack p_218535_) {
        return f_218455_.test(p_218535_);
    }

    public static boolean m_218511_(EntityType<? extends Animal> p_218512_, LevelAccessor p_218513_, MobSpawnType p_218514_, BlockPos p_218515_, RandomSource p_218516_) {
        return p_218513_.m_8055_(p_218515_.m_7495_()).m_204336_(BlockTags.f_215827_) && Frog.m_186209_(p_218513_, p_218515_);
    }

    class FrogLookControl
    extends LookControl {
        FrogLookControl(Mob p_218544_) {
            super(p_218544_);
        }

        @Override
        protected boolean m_8106_() {
            return Frog.this.m_218538_().isEmpty();
        }
    }

    static class FrogPathNavigation
    extends AmphibiousPathNavigation {
        FrogPathNavigation(Frog p_218556_, Level p_218557_) {
            super(p_218556_, p_218557_);
        }

        @Override
        protected PathFinder m_5532_(int p_218559_) {
            this.f_26508_ = new FrogNodeEvaluator(true);
            this.f_26508_.m_77351_(true);
            return new PathFinder(this.f_26508_, p_218559_);
        }
    }

    static class FrogNodeEvaluator
    extends AmphibiousNodeEvaluator {
        private final BlockPos.MutableBlockPos f_218546_ = new BlockPos.MutableBlockPos();

        public FrogNodeEvaluator(boolean p_218548_) {
            super(p_218548_);
        }

        @Override
        @Nullable
        public Node m_7171_() {
            return this.m_230631_(new BlockPos(Mth.m_14107_(this.f_77313_.m_20191_().f_82288_), Mth.m_14107_(this.f_77313_.m_20191_().f_82289_), Mth.m_14107_(this.f_77313_.m_20191_().f_82290_)));
        }

        @Override
        public BlockPathTypes m_8086_(BlockGetter p_218551_, int p_218552_, int p_218553_, int p_218554_) {
            this.f_218546_.m_122178_(p_218552_, p_218553_ - 1, p_218554_);
            BlockState $$4 = p_218551_.m_8055_(this.f_218546_);
            if ($$4.m_204336_(BlockTags.f_215837_)) {
                return BlockPathTypes.OPEN;
            }
            return super.m_8086_(p_218551_, p_218552_, p_218553_, p_218554_);
        }
    }
}

