/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.frog;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.TadpoleAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Tadpole
extends AbstractFish {
    @VisibleForTesting
    public static int f_218678_ = Math.abs(-24000);
    public static float f_218681_ = 0.4f;
    public static float f_218682_ = 0.3f;
    private int f_218680_;
    protected static final ImmutableList<SensorType<? extends Sensor<? super Tadpole>>> f_218683_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26814_, SensorType.f_217822_);
    protected static final ImmutableList<MemoryModuleType<?>> f_218679_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_148205_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26331_, MemoryModuleType.f_148197_, MemoryModuleType.f_148198_, MemoryModuleType.f_148196_, MemoryModuleType.f_26375_, MemoryModuleType.f_217768_);

    public Tadpole(EntityType<? extends AbstractFish> p_218686_, Level p_218687_) {
        super(p_218686_, p_218687_);
        this.f_21342_ = new SmoothSwimmingMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.f_21365_ = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected PathNavigation m_6037_(Level p_218694_) {
        return new WaterBoundPathNavigation(this, p_218694_);
    }

    protected Brain.Provider<Tadpole> m_5490_() {
        return Brain.m_21923_(f_218679_, f_218683_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_218696_) {
        return TadpoleAi.m_218741_(this.m_5490_().m_22073_(p_218696_));
    }

    public Brain<Tadpole> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected SoundEvent m_5699_() {
        return SoundEvents.f_215759_;
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("tadpoleBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("tadpoleActivityUpdate");
        TadpoleAi.m_218743_(this);
        this.f_19853_.m_46473_().m_7238_();
        super.m_8024_();
    }

    public static AttributeSupplier.Builder m_218720_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 1.0).m_22268_(Attributes.f_22276_, 6.0);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_) {
            this.m_218710_(this.f_218680_ + 1);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_218709_) {
        super.m_7380_(p_218709_);
        p_218709_.m_128405_("Age", this.f_218680_);
    }

    @Override
    public void m_7378_(CompoundTag p_218698_) {
        super.m_7378_(p_218698_);
        this.m_218710_(p_218698_.m_128451_("Age"));
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_218713_) {
        return SoundEvents.f_215761_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_215758_;
    }

    @Override
    public InteractionResult m_6071_(Player p_218703_, InteractionHand p_218704_) {
        ItemStack $$2 = p_218703_.m_21120_(p_218704_);
        if (this.m_218726_($$2)) {
            this.m_218690_(p_218703_, $$2);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return Bucketable.m_148828_(p_218703_, p_218704_, this).orElse(super.m_6071_(p_218703_, p_218704_));
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public boolean m_27487_() {
        return true;
    }

    @Override
    public void m_27497_(boolean p_218732_) {
    }

    @Override
    public void m_6872_(ItemStack p_218725_) {
        Bucketable.m_148822_(this, p_218725_);
        CompoundTag $$1 = p_218725_.m_41784_();
        $$1.m_128405_("Age", this.m_218721_());
    }

    @Override
    public void m_142278_(CompoundTag p_218715_) {
        Bucketable.m_148825_(this, p_218715_);
        if (p_218715_.m_128441_("Age")) {
            this.m_218710_(p_218715_.m_128451_("Age"));
        }
    }

    @Override
    public ItemStack m_28282_() {
        return new ItemStack(Items.f_220210_);
    }

    @Override
    public SoundEvent m_142623_() {
        return SoundEvents.f_215674_;
    }

    private boolean m_218726_(ItemStack p_218727_) {
        return Frog.f_218455_.test(p_218727_);
    }

    private void m_218690_(Player p_218691_, ItemStack p_218692_) {
        this.m_218705_(p_218691_, p_218692_);
        this.m_218700_(AgeableMob.m_216967_(this.m_218723_()));
        this.f_19853_.m_7106_(ParticleTypes.f_123748_, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), 0.0, 0.0, 0.0);
    }

    private void m_218705_(Player p_218706_, ItemStack p_218707_) {
        if (!p_218706_.m_150110_().f_35937_) {
            p_218707_.m_41774_(1);
        }
    }

    private int m_218721_() {
        return this.f_218680_;
    }

    private void m_218700_(int p_218701_) {
        this.m_218710_(this.f_218680_ + p_218701_ * 20);
    }

    private void m_218710_(int p_218711_) {
        this.f_218680_ = p_218711_;
        if (this.f_218680_ >= f_218678_) {
            this.m_218722_();
        }
    }

    private void m_218722_() {
        Level level = this.f_19853_;
        if (level instanceof ServerLevel) {
            ServerLevel $$0 = (ServerLevel)level;
            Frog $$1 = EntityType.f_217012_.m_20615_(this.f_19853_);
            $$1.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            $$1.m_6518_($$0, this.f_19853_.m_6436_($$1.m_20183_()), MobSpawnType.CONVERSION, null, null);
            $$1.m_21557_(this.m_21525_());
            if (this.m_8077_()) {
                $$1.m_6593_(this.m_7770_());
                $$1.m_20340_(this.m_20151_());
            }
            $$1.m_21530_();
            this.m_5496_(SoundEvents.f_215760_, 0.15f, 1.0f);
            $$0.m_47205_($$1);
            this.m_146870_();
        }
    }

    private int m_218723_() {
        return Math.max(0, f_218678_ - this.f_218680_);
    }

    @Override
    public boolean m_6149_() {
        return false;
    }
}

