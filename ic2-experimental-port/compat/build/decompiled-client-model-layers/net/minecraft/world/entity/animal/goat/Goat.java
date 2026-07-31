/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.GoatAi;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Goat
extends Animal {
    public static final EntityDimensions f_149342_ = EntityDimensions.m_20395_(0.9f, 1.3f).m_20388_(0.7f);
    private static final int f_182382_ = 2;
    private static final int f_182383_ = 1;
    protected static final ImmutableList<SensorType<? extends Sensor<? super Goat>>> f_149343_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26822_, SensorType.f_26814_, SensorType.f_148317_);
    protected static final ImmutableList<MemoryModuleType<?>> f_149344_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_148205_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26355_, MemoryModuleType.f_26375_, MemoryModuleType.f_148199_, MemoryModuleType.f_148200_, MemoryModuleType.f_148196_, MemoryModuleType.f_26331_, MemoryModuleType.f_148197_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_148198_, MemoryModuleType.f_148202_, MemoryModuleType.f_148203_, MemoryModuleType.f_217768_});
    public static final int f_149345_ = 10;
    public static final double f_149346_ = 0.02;
    public static final double f_218749_ = (double)0.1f;
    private static final EntityDataAccessor<Boolean> f_149347_ = SynchedEntityData.m_135353_(Goat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_218750_ = SynchedEntityData.m_135353_(Goat.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_218751_ = SynchedEntityData.m_135353_(Goat.class, EntityDataSerializers.f_135035_);
    private boolean f_149348_;
    private int f_149349_;

    public Goat(EntityType<? extends Goat> p_149352_, Level p_149353_) {
        super((EntityType<? extends Animal>)p_149352_, p_149353_);
        this.m_21573_().m_7008_(true);
        this.m_21441_(BlockPathTypes.POWDER_SNOW, -1.0f);
        this.m_21441_(BlockPathTypes.DANGER_POWDER_SNOW, -1.0f);
    }

    public ItemStack m_218763_() {
        RandomSource $$0 = RandomSource.m_216335_(this.m_20148_().hashCode());
        TagKey<Instrument> $$1 = this.m_149397_() ? InstrumentTags.f_215857_ : InstrumentTags.f_215856_;
        HolderSet.Named<Instrument> $$2 = Registry.f_235738_.m_203561_($$1);
        return InstrumentItem.m_220107_(Items.f_220219_, $$2.m_213653_($$0).get());
    }

    protected Brain.Provider<Goat> m_5490_() {
        return Brain.m_21923_(f_149344_, f_149343_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_149371_) {
        return GoatAi.m_149447_(this.m_5490_().m_22073_(p_149371_));
    }

    public static AttributeSupplier.Builder m_149401_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22279_, 0.2f).m_22268_(Attributes.f_22281_, 2.0);
    }

    @Override
    protected void m_30232_() {
        if (this.m_6162_()) {
            this.m_21051_(Attributes.f_22281_).m_22100_(1.0);
            this.m_218762_();
        } else {
            this.m_21051_(Attributes.f_22281_).m_22100_(2.0);
            this.m_218761_();
        }
    }

    @Override
    protected int m_5639_(float p_149389_, float p_149390_) {
        return super.m_5639_(p_149389_, p_149390_) - 10;
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.m_149397_()) {
            return SoundEvents.f_144171_;
        }
        return SoundEvents.f_144163_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_149387_) {
        if (this.m_149397_()) {
            return SoundEvents.f_144146_;
        }
        return SoundEvents.f_144166_;
    }

    @Override
    protected SoundEvent m_5592_() {
        if (this.m_149397_()) {
            return SoundEvents.f_144172_;
        }
        return SoundEvents.f_144164_;
    }

    @Override
    protected void m_7355_(BlockPos p_149382_, BlockState p_149383_) {
        this.m_5496_(SoundEvents.f_144151_, 0.15f, 1.0f);
    }

    protected SoundEvent m_149403_() {
        if (this.m_149397_()) {
            return SoundEvents.f_144148_;
        }
        return SoundEvents.f_144168_;
    }

    @Override
    public Goat m_142606_(ServerLevel p_149376_, AgeableMob p_149377_) {
        Goat $$2 = EntityType.f_147035_.m_20615_(p_149376_);
        if ($$2 != null) {
            GoatAi.m_218764_($$2, p_149376_.m_213780_());
            boolean $$3 = p_149377_ instanceof Goat && ((Goat)p_149377_).m_149397_();
            $$2.m_149405_($$3 || p_149376_.m_213780_().m_188500_() < 0.02);
        }
        return $$2;
    }

    public Brain<Goat> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("goatBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("goatActivityUpdate");
        GoatAi.m_149455_(this);
        this.f_19853_.m_46473_().m_7238_();
        super.m_8024_();
    }

    @Override
    public int m_8085_() {
        return 15;
    }

    @Override
    public void m_5616_(float p_149400_) {
        int $$1 = this.m_8085_();
        float $$2 = Mth.m_14118_(this.f_20883_, p_149400_);
        float $$3 = Mth.m_14036_($$2, -$$1, $$1);
        super.m_5616_(this.f_20883_ + $$3);
    }

    @Override
    public SoundEvent m_7866_(ItemStack p_149394_) {
        return this.m_149397_() ? SoundEvents.f_144173_ : SoundEvents.f_144165_;
    }

    @Override
    public InteractionResult m_6071_(Player p_149379_, InteractionHand p_149380_) {
        ItemStack $$2 = p_149379_.m_21120_(p_149380_);
        if ($$2.m_150930_(Items.f_42446_) && !this.m_6162_()) {
            p_149379_.m_5496_(this.m_149403_(), 1.0f, 1.0f);
            ItemStack $$3 = ItemUtils.m_41813_($$2, p_149379_, Items.f_42455_.m_7968_());
            p_149379_.m_21008_(p_149380_, $$3);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        InteractionResult $$4 = super.m_6071_(p_149379_, p_149380_);
        if ($$4.m_19077_() && this.m_6898_($$2)) {
            this.f_19853_.m_6269_(null, this, this.m_7866_($$2), SoundSource.NEUTRAL, 1.0f, Mth.m_216283_(this.f_19853_.f_46441_, 0.8f, 1.2f));
        }
        return $$4;
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_149365_, DifficultyInstance p_149366_, MobSpawnType p_149367_, @Nullable SpawnGroupData p_149368_, @Nullable CompoundTag p_149369_) {
        RandomSource $$5 = p_149365_.m_213780_();
        GoatAi.m_218764_(this, $$5);
        this.m_149405_($$5.m_188500_() < 0.02);
        this.m_30232_();
        if (!this.m_6162_() && (double)$$5.m_188501_() < (double)0.1f) {
            EntityDataAccessor<Boolean> $$6 = $$5.m_188499_() ? f_218750_ : f_218751_;
            this.f_19804_.m_135381_($$6, false);
        }
        return super.m_6518_(p_149365_, p_149366_, p_149367_, p_149368_, p_149369_);
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public EntityDimensions m_6972_(Pose p_149361_) {
        return p_149361_ == Pose.LONG_JUMPING ? f_149342_.m_20388_(this.m_6134_()) : super.m_6972_(p_149361_);
    }

    @Override
    public void m_7380_(CompoundTag p_149385_) {
        super.m_7380_(p_149385_);
        p_149385_.m_128379_("IsScreamingGoat", this.m_149397_());
        p_149385_.m_128379_("HasLeftHorn", this.m_218758_());
        p_149385_.m_128379_("HasRightHorn", this.m_218759_());
    }

    @Override
    public void m_7378_(CompoundTag p_149373_) {
        super.m_7378_(p_149373_);
        this.m_149405_(p_149373_.m_128471_("IsScreamingGoat"));
        this.f_19804_.m_135381_(f_218750_, p_149373_.m_128471_("HasLeftHorn"));
        this.f_19804_.m_135381_(f_218751_, p_149373_.m_128471_("HasRightHorn"));
    }

    @Override
    public void m_7822_(byte p_149356_) {
        if (p_149356_ == 58) {
            this.f_149348_ = true;
        } else if (p_149356_ == 59) {
            this.f_149348_ = false;
        } else {
            super.m_7822_(p_149356_);
        }
    }

    @Override
    public void m_8107_() {
        this.f_149349_ = this.f_149348_ ? ++this.f_149349_ : (this.f_149349_ -= 2);
        this.f_149349_ = Mth.m_14045_(this.f_149349_, 0, 20);
        super.m_8107_();
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_149347_, false);
        this.f_19804_.m_135372_(f_218750_, true);
        this.f_19804_.m_135372_(f_218751_, true);
    }

    public boolean m_218758_() {
        return this.f_19804_.m_135370_(f_218750_);
    }

    public boolean m_218759_() {
        return this.f_19804_.m_135370_(f_218751_);
    }

    public boolean m_218760_() {
        EntityDataAccessor<Boolean> $$4;
        boolean $$0 = this.m_218758_();
        boolean $$1 = this.m_218759_();
        if (!$$0 && !$$1) {
            return false;
        }
        if (!$$0) {
            EntityDataAccessor<Boolean> $$2 = f_218751_;
        } else if (!$$1) {
            EntityDataAccessor<Boolean> $$3 = f_218750_;
        } else {
            $$4 = this.f_19796_.m_188499_() ? f_218750_ : f_218751_;
        }
        this.f_19804_.m_135381_($$4, false);
        Vec3 $$5 = this.m_20182_();
        ItemStack $$6 = this.m_218763_();
        double $$7 = Mth.m_216283_(this.f_19796_, -0.2f, 0.2f);
        double $$8 = Mth.m_216283_(this.f_19796_, 0.3f, 0.7f);
        double $$9 = Mth.m_216283_(this.f_19796_, -0.2f, 0.2f);
        ItemEntity $$10 = new ItemEntity(this.f_19853_, $$5.m_7096_(), $$5.m_7098_(), $$5.m_7094_(), $$6, $$7, $$8, $$9);
        this.f_19853_.m_7967_($$10);
        return true;
    }

    public void m_218761_() {
        this.f_19804_.m_135381_(f_218750_, true);
        this.f_19804_.m_135381_(f_218751_, true);
    }

    public void m_218762_() {
        this.f_19804_.m_135381_(f_218750_, false);
        this.f_19804_.m_135381_(f_218751_, false);
    }

    public boolean m_149397_() {
        return this.f_19804_.m_135370_(f_149347_);
    }

    public void m_149405_(boolean p_149406_) {
        this.f_19804_.m_135381_(f_149347_, p_149406_);
    }

    public float m_149398_() {
        return (float)this.f_149349_ / 20.0f * 30.0f * ((float)Math.PI / 180);
    }

    public static boolean m_218752_(EntityType<? extends Animal> p_218753_, LevelAccessor p_218754_, MobSpawnType p_218755_, BlockPos p_218756_, RandomSource p_218757_) {
        return p_218754_.m_8055_(p_218756_.m_7495_()).m_204336_(BlockTags.f_184230_) && Goat.m_186209_(p_218754_, p_218756_);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }
}

