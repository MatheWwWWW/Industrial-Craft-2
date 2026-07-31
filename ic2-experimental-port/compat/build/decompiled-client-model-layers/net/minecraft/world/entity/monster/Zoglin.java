/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.world.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Zoglin
extends Monster
implements Enemy,
HoglinBase {
    private static final EntityDataAccessor<Boolean> f_34201_ = SynchedEntityData.m_135353_(Zoglin.class, EntityDataSerializers.f_135035_);
    private static final int f_149879_ = 40;
    private static final int f_149870_ = 1;
    private static final float f_149871_ = 0.6f;
    private static final int f_149872_ = 6;
    private static final float f_149873_ = 0.5f;
    private static final int f_149874_ = 40;
    private static final int f_149875_ = 15;
    private static final int f_149876_ = 200;
    private static final float f_149877_ = 0.3f;
    private static final float f_149878_ = 0.4f;
    private int f_34199_;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Zoglin>>> f_34198_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_);
    protected static final ImmutableList<? extends MemoryModuleType<?>> f_34200_ = ImmutableList.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_);

    public Zoglin(EntityType<? extends Zoglin> p_34204_, Level p_34205_) {
        super((EntityType<? extends Monster>)p_34204_, p_34205_);
        this.f_21364_ = 5;
    }

    protected Brain.Provider<Zoglin> m_5490_() {
        return Brain.m_21923_(f_34200_, f_34198_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_34221_) {
        Brain<Zoglin> $$1 = this.m_5490_().m_22073_(p_34221_);
        Zoglin.m_34216_($$1);
        Zoglin.m_34228_($$1);
        Zoglin.m_34236_($$1);
        $$1.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        $$1.m_21944_(Activity.f_37979_);
        $$1.m_21962_();
        return $$1;
    }

    private static void m_34216_(Brain<Zoglin> p_34217_) {
        p_34217_.m_21891_(Activity.f_37978_, 0, (ImmutableList<Behavior<Zoglin>>)ImmutableList.of((Object)new LookAtTargetSink(45, 90), (Object)new MoveToTargetSink()));
    }

    private static void m_34228_(Brain<Zoglin> p_34229_) {
        p_34229_.m_21891_(Activity.f_37979_, 10, (ImmutableList<Behavior<Zoglin>>)ImmutableList.of(new StartAttacking<Zoglin>(Zoglin::m_34251_), new RunSometimes<LivingEntity>(new SetEntityLookTarget(8.0f), UniformInt.m_146622_(30, 60)), new RunOne(ImmutableList.of((Object)Pair.of((Object)new RandomStroll(0.4f), (Object)2), (Object)Pair.of((Object)new SetWalkTargetFromLookTarget(0.4f, 3), (Object)2), (Object)Pair.of((Object)new DoNothing(30, 60), (Object)1)))));
    }

    private static void m_34236_(Brain<Zoglin> p_34237_) {
        p_34237_.m_21895_(Activity.f_37988_, 10, (ImmutableList<Behavior<Zoglin>>)ImmutableList.of((Object)new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0f), new RunIf<Mob>(Zoglin::m_34247_, new MeleeAttack(40)), new RunIf<Mob>(Zoglin::m_6162_, new MeleeAttack(15)), new StopAttackingIfTargetInvalid()), MemoryModuleType.f_26372_);
    }

    private Optional<? extends LivingEntity> m_34251_() {
        return this.m_6274_().m_21952_(MemoryModuleType.f_148205_).orElse(NearestVisibleLivingEntities.m_186106_()).m_186116_(this::m_34252_);
    }

    private boolean m_34252_(LivingEntity p_34253_) {
        EntityType<?> $$1 = p_34253_.m_6095_();
        return $$1 != EntityType.f_20500_ && $$1 != EntityType.f_20558_ && Sensor.m_148312_(this, p_34253_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_34201_, false);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_34225_) {
        super.m_7350_(p_34225_);
        if (f_34201_.equals(p_34225_)) {
            this.m_6210_();
        }
    }

    public static AttributeSupplier.Builder m_34257_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22278_, 0.6f).m_22268_(Attributes.f_22282_, 1.0).m_22268_(Attributes.f_22281_, 6.0);
    }

    public boolean m_34247_() {
        return !this.m_6162_();
    }

    @Override
    public boolean m_7327_(Entity p_34207_) {
        if (!(p_34207_ instanceof LivingEntity)) {
            return false;
        }
        this.f_34199_ = 10;
        this.f_19853_.m_7605_(this, (byte)4);
        this.m_5496_(SoundEvents.f_12594_, 1.0f, this.m_6100_());
        return HoglinBase.m_34642_(this, (LivingEntity)p_34207_);
    }

    @Override
    public boolean m_6573_(Player p_34219_) {
        return !this.m_21523_();
    }

    @Override
    protected void m_6731_(LivingEntity p_34246_) {
        if (!this.m_6162_()) {
            HoglinBase.m_34645_(this, p_34246_);
        }
    }

    @Override
    public double m_6048_() {
        return (double)this.m_20206_() - (this.m_6162_() ? 0.2 : 0.15);
    }

    @Override
    public boolean m_6469_(DamageSource p_34214_, float p_34215_) {
        boolean $$2 = super.m_6469_(p_34214_, p_34215_);
        if (this.f_19853_.f_46443_) {
            return false;
        }
        if (!$$2 || !(p_34214_.m_7639_() instanceof LivingEntity)) {
            return $$2;
        }
        LivingEntity $$3 = (LivingEntity)p_34214_.m_7639_();
        if (this.m_6779_($$3) && !BehaviorUtils.m_22598_(this, $$3, 4.0)) {
            this.m_34254_($$3);
        }
        return $$2;
    }

    private void m_34254_(LivingEntity p_34255_) {
        this.f_20939_.m_21936_(MemoryModuleType.f_26326_);
        this.f_20939_.m_21882_(MemoryModuleType.f_26372_, p_34255_, 200L);
    }

    public Brain<Zoglin> m_6274_() {
        return super.m_6274_();
    }

    protected void m_34248_() {
        Activity $$0 = this.f_20939_.m_21968_().orElse(null);
        this.f_20939_.m_21926_((List<Activity>)ImmutableList.of((Object)Activity.f_37988_, (Object)Activity.f_37979_));
        Activity $$1 = this.f_20939_.m_21968_().orElse(null);
        if ($$1 == Activity.f_37988_ && $$0 != Activity.f_37988_) {
            this.m_34250_();
        }
        this.m_21561_(this.f_20939_.m_21874_(MemoryModuleType.f_26372_));
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("zoglinBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.m_34248_();
    }

    @Override
    public void m_6863_(boolean p_34227_) {
        this.m_20088_().m_135381_(f_34201_, p_34227_);
        if (!this.f_19853_.f_46443_ && p_34227_) {
            this.m_21051_(Attributes.f_22281_).m_22100_(0.5);
        }
    }

    @Override
    public boolean m_6162_() {
        return this.m_20088_().m_135370_(f_34201_);
    }

    @Override
    public void m_8107_() {
        if (this.f_34199_ > 0) {
            --this.f_34199_;
        }
        super.m_8107_();
    }

    @Override
    public void m_7822_(byte p_34212_) {
        if (p_34212_ == 4) {
            this.f_34199_ = 10;
            this.m_5496_(SoundEvents.f_12594_, 1.0f, this.m_6100_());
        } else {
            super.m_7822_(p_34212_);
        }
    }

    @Override
    public int m_7575_() {
        return this.f_34199_;
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.f_19853_.f_46443_) {
            return null;
        }
        if (this.f_20939_.m_21874_(MemoryModuleType.f_26372_)) {
            return SoundEvents.f_12593_;
        }
        return SoundEvents.f_12592_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34244_) {
        return SoundEvents.f_12596_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12595_;
    }

    @Override
    protected void m_7355_(BlockPos p_34231_, BlockState p_34232_) {
        this.m_5496_(SoundEvents.f_12597_, 0.15f, 1.0f);
    }

    protected void m_34250_() {
        this.m_5496_(SoundEvents.f_12593_, 1.0f, this.m_6100_());
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21641_;
    }

    @Override
    public void m_7380_(CompoundTag p_34234_) {
        super.m_7380_(p_34234_);
        if (this.m_6162_()) {
            p_34234_.m_128379_("IsBaby", true);
        }
    }

    @Override
    public void m_7378_(CompoundTag p_34223_) {
        super.m_7378_(p_34223_);
        if (p_34223_.m_128471_("IsBaby")) {
            this.m_6863_(true);
        }
    }
}

